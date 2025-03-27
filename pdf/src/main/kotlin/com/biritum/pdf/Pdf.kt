package com.biritum.pdf

private val emptyPage = listOf(Page(GroupedContent()))

fun renderPdf(paperSize: PaperSize, vararg pages: Page, pageMode: PageMode = PageMode.UseNone): Sequence<Byte> =
    renderPdf(paperSize, pages.toList(), pageMode)

fun renderPdf(paperSize: PaperSize, pages: List<Page>, pageMode: PageMode = PageMode.UseNone): Sequence<Byte> {
    return sequence {
        val refObjects = ReferenceList(paperSize, pageMode)
        pages.ifEmpty { emptyPage }.forEach {
            refObjects.addPage(it)
        }
        val (catalogRef, nodes) = refObjects.asPageNodes()

        yieldAll(pdfHeader.asSequence())
        nodes.forEach { yieldAll(it.asSequence()) }
        val xReferences = buildCrossReferenceList(nodes, pdfHeader.size)
        yieldAll(buildCrossReferenceTable(xReferences))
        yieldAll(buildTrailer(catalogRef, xReferences))
    }
}

private fun buildCrossReferenceList(
    parts: List<ByteArray>,
    headerSize: Int
): ObjectsWithXReferences {
    val xReferences = parts.fold(
        ObjectsWithXReferences(headerSize, listOf(xReference(0, 65535, true)))
    ) { acc, ref ->
        ObjectsWithXReferences(acc.size + ref.size, acc.xRefList + xReference(acc.size, 0, false))
    }
    return xReferences
}

private fun xReference(offset: Int, generation: Int, free: Boolean): String {
    val o = offset.toString().padStart(10, '0')
    val g = generation.toString().padStart(5, '0')
    val f = if (free) 'f' else 'n'
    return "$o $g $f "
}

private fun buildCrossReferenceTable(objectsWithXReferences: ObjectsWithXReferences): Sequence<Byte> {
    val sb = StringBuilder().apply {
        appendLine("\nxref")
        appendLine("0 ${objectsWithXReferences.xRefList.size}")
        objectsWithXReferences.xRefList.forEach {
            appendLine(it)
        }
    }
    return sb.toString().encodeToByteArray().asSequence()
}

private fun buildTrailer(
    rootRef: Reference,
    objectsWithXReferences: ObjectsWithXReferences
): Sequence<Byte> {
    val sb = StringBuilder().apply {
        appendLine("\ntrailer")
        appendDictionary(Dictionary().apply {
            add("Root", rootRef)
            add("Size", objectsWithXReferences.xRefList.size)
            add("Info", object : PdfObject {
                override fun stringContent() = "<</Producer(BiritumPdf)/Creator(BiritumPdf)>>"
            })
        })
        appendLine("\nstartxref")
        appendLine(objectsWithXReferences.size)
        append("%%EOF")
    }
    return sb.toString().encodeToByteArray().asSequence()
}