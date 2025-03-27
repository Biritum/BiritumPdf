package com.biritum.pdf

import com.biritum.pdf.geometry.Pixel
import com.biritum.pdf.geometry.Point
import com.biritum.pdf.image.ImageData
import com.biritum.pdf.text.BaseFont
import com.biritum.pdf.text.FontType

internal val pdfHeader = "%PDF-1.7\n%¥±ë\n".encodeToByteArray()


interface PageContent {
    val usesDeviceRGB: Boolean
    fun pdfContent(context: PageContext): String
}

interface PageContext {
    fun toPixel(p: Point): Pixel
    fun findReference(imageData: ImageData): String
    fun findReference(font: FontType): String
}

internal fun mediaBox(paperSize: PaperSize) =
    listOf(0, 0, paperSize.width.toPixels(), paperSize.height.toPixels()).toPdfArray()

internal class PageTree(private val paperSize: PaperSize) {
    private val pages: MutableList<Reference> = mutableListOf()

    fun add(pageRef: Reference) {
        pages.add(pageRef)
    }

    fun objectDataContent(): ByteArray {
        val dictionary = Dictionary()
        dictionary.add("Type", "Pages")
        dictionary.add("MediaBox", mediaBox(paperSize))
        dictionary.add("Kids", pages.toPdfArray())
        dictionary.add("Count", pages.size)
        return dictionary.objectDataContent()
    }
}

internal class ResourceReferences(private val refObjects: ReferenceList) {
    private val references = mutableMapOf<Any, Reference>()

    fun addIfNew(it: ImageData): Reference = references[it] ?: addNew(it)

    private var imageCounter = 1
    private fun addNew(it: ImageData): Reference {
        val name = "I${imageCounter++}"
        val ref = refObjects.add(name) { imageStream(name, it).content() }
        references[it] = ref
        return ref
    }

    fun addIfNew(it: FontType): Reference = references[it] ?: addNew(it)

    private var fontCounter = 1
    private fun addNew(it: FontType): Reference {
        val name = "F${fontCounter++}"
        val ref = refObjects.add(name) { (it as BaseFont).asResource(name).objectDataContent() }
        references[it] = ref
        return ref
    }
}


private fun BaseFont.asResource(name: String) = Dictionary().apply {
    add("Type", "Font")
    add("Subtype", "Type1")
    add("Name", name)
    add("BaseFont", baseType)
}

private fun imageStream(name: String, imageData: ImageData): PdfStream {
    val d = Dictionary().apply {
        add("Type", "XObject")
        add("Subtype", "Image")
        add("Name", name)
        add("Width", imageData.width)
        add("Height", imageData.height)
        add("BitsPerComponent", imageData.bitsPerComponent)
        add("ColorSpace", imageData.colorSpace)
        if (imageData.filter.isNotEmpty())
            add("Filter", imageData.filter)
        add("Length", imageData.pixelCount)
    }
    return PdfStream(d, imageData.data)
}

internal fun pageStream(
    content: PageContent,
    context: PageContext
): PdfStream {
    val stream = content.pdfContent(context)
    return PdfStream(Dictionary().apply { add("Length", stream.length) }, stream.toByteArray().asSequence())
}

internal class PdfStream(private val dictionary: Dictionary, private val data: Sequence<Byte>) {
    fun content(): ByteArray {
        return dictionary.objectDataContent() +
                "\nstream\n".encodeToByteArray() +
                data.toList() +
                "\nendstream".encodeToByteArray()
    }
}

internal class ObjectsWithXReferences(val size: Int, val xRefList: List<String>)

internal class ReferenceList(val paperSize: PaperSize, val pageMode: PageMode) {
    private val refObjects = mutableListOf<() -> ByteArray>()
    private var pageTree: PageTree = PageTree(paperSize)
    private var pageTreeRef = add("page dictionary") { pageTree.objectDataContent() }
    private val crossReferences = ResourceReferences(this)

    internal fun add(name: String, obj: () -> ByteArray): Reference {
        refObjects.add(obj)
        return Reference(name, refObjects.size)
    }

    fun addPage(page: Page) {
        val pagePaperSize = page.paperSize ?: paperSize
        val context = Context(pagePaperSize, crossReferences)
        val stream = pageStream(page.content, context).content()
        val pageResources = context.pageResources()
        val pageDescription = Dictionary().apply {
            add("Type", "Page")
            page.paperSize?.let {
                add("MediaBox", mediaBox(page.paperSize))
            }
            add("Parent", pageTreeRef)
            add("Contents", add("page content") { stream })
            add("Resources", pageResources)

            if (page.content.usesDeviceRGB) {
                add("Group", Dictionary().apply {
                    add("Type", "Group")
                    add("S", "Transparency")
                    add("I", true)
                    add("CS", "DeviceRGB")
                })
            }
        }.objectDataContent()

        // TODO check do we need to add rotate attribute if we use landscape.

        pageTree.add(add("page description") { pageDescription })
    }

    fun asPageNodes(): Pair<Reference, List<ByteArray>> {
        return Pair(
            createPageCatalog(pageMode),
            refObjects.map { it() }.mapIndexed(::createPart)
        )
    }

    private fun createPart(i: Int, it: ByteArray) =
        "\n${i + 1} 0 obj\n".encodeToByteArray() + it + "\nendobj\n".encodeToByteArray()


    private fun createPageCatalog(mode: PageMode): Reference {
        val root = Dictionary().apply {
            add("Type", "Catalog")
            add("Pages", pageTreeRef)
            add("PageMode", mode.name)
        }
        return this.add("catalog") { root.objectDataContent() }
    }
}

private class Context(val paperSize: PaperSize, val crossReferences: ResourceReferences) : PageContext {

    private val accessedReferences = mutableListOf<Reference>()
    private val accessedFonts = mutableListOf<Reference>()

    override fun toPixel(p: Point): Pixel = Pixel(p.x.toPixels(), (paperSize.height - p.y).toPixels())

    override fun findReference(imageData: ImageData): String {
        val ref = crossReferences.addIfNew(imageData)
        accessedReferences.add(ref)
        return ref.name
    }

    override fun findReference(font: FontType): String {
        val ref = crossReferences.addIfNew(font)
        accessedFonts.add(ref)
        return ref.name
    }

    fun pageResources(): Dictionary {
        val resources = Dictionary()
        val procSet = mutableListOf<String>("PDF")

        val xReferences = Dictionary().apply {
            accessedReferences.distinct().forEach { add(it.name, it) }
        }
        if (xReferences.any()) {
            procSet.add("ImageC")
            resources.add("XObject", xReferences)
        }

        val usedFonts = Dictionary().apply {
            accessedFonts.distinct().forEach { add(it.name, it) }
        }
        if (usedFonts.any()) {
            procSet.add("Text")
            resources.add("Font", usedFonts)
        }

        resources.add("ProcSet", procSet.toPdfArray())
        return resources
    }
}

internal interface PdfObject {
    fun stringContent(): String
}

internal data class Reference(val name: String, val refSize: Int) : PdfObject {
    override fun stringContent(): String {
        return "$refSize 0 R"
    }
}

internal data class Name(val name: String) : PdfObject {
    init {
        checkLegalName(name)
    }

    private fun checkLegalName(s: String) {
        val illegalNAmeCharacters = "\r%,<>[]{}"
        if (!s.first().isUpperCase())
            throw IllegalArgumentException("Keys and Names must start with upper case letter")
        if (s.any { it in illegalNAmeCharacters })
            throw IllegalArgumentException("Keys aor Names contain illegal character")
    }

    override fun stringContent(): String {
        return "/$name"
    }
}

internal data class IntObject(val value: Int) : PdfObject {
    override fun stringContent() = value.toString()
}

internal data class FloatObject(val value: Float) : PdfObject {
    override fun stringContent() = value.toString()
}

internal data class BooleanObject(val value: Boolean) : PdfObject {
    override fun stringContent() = value.toString()
}

internal fun List<Any>.toPdfArray(): PdfArray {
    return PdfArray(this)
}

internal data class PdfArray(val values: List<Any>) : PdfObject {
    override fun stringContent(): String {
        return "[${values.map { toPdfObject(it) }.map { it.stringContent() }.joinToString(" ") { it }}]"
    }

    private fun toPdfObject(it: Any): PdfObject {
        return when (it) {
            is PdfObject -> it
            is String -> Name(it)
            is Int -> IntObject(it)
            is Float -> FloatObject(it)
            is Boolean -> BooleanObject(it)
            else -> throw IllegalArgumentException("not supported type $it")
        }
    }
}

internal class Dictionary : PdfObject {
    private var values: MutableMap<String, () -> String> = mutableMapOf()

    fun any() = values.any()

    fun add(name: String, value: String) = add(name, Name(value))
    fun add(name: String, value: Int) = add(name, IntObject(value))
    fun add(name: String, value: Float) = add(name, FloatObject(value))
    fun add(name: String, value: Boolean) = add(name, BooleanObject(value))

    fun add(name: String, pdfObject: PdfObject) {
        add(name) { pdfObject.stringContent() }
    }

    private fun add(name: String, value: () -> String) {
        values[Name(name).stringContent()] = value
    }

    override fun stringContent(): String {
        val sb = StringBuilder()
        appendContent(sb)
        return sb.toString()
    }

    internal fun appendContent(sb: StringBuilder) {
        sb.append("<<")
        values.map { " ${it.key} ${it.value()}" }.forEach { sb.appendLine(it) }
        sb.append(">>")
    }

    fun objectDataContent(): ByteArray = stringContent().encodeToByteArray()
}

internal fun StringBuilder.appendDictionary(dictionary: Dictionary): StringBuilder {
    dictionary.appendContent(this)
    appendLine()
    return this
}