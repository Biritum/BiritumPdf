package com.biritum.pdf.fontmetrics

import com.biritum.pdf.text.BaseFont
import com.biritum.pdf.text.Font
import java.io.File

class UnknownFileFormat : Exception()

fun Font.metrics(): FontMetrics = (this.fontType as BaseFont).metrics(this.fontSize)

fun BaseFont.metrics(fontSize: Int): FontMetrics{

    // font metrics for base 14 fonts can be found at
    // https://github.com/tecnickcom/tc-font-core14-afms

    val afm = "C:\\programming\\exampleprojects\\fontmetrics-core14-afms\\${this.baseType}.afm"
    return BaseFontFontMetrics( readFontMetrics(File(afm).readBytes()), fontSize)
}

fun readFontMetrics(afmContents: ByteArray): BaseFontAfm {

    val lines = afmContents.decodeToString().lines()
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .filter { !it.startsWith("Comment") }

    if (!lines.first().startsWith("StartFontMetrics") || !lines.last().startsWith("EndFontMetrics")) {
        throw UnknownFileFormat()
    }

    val writingDirection = lines.writingDirection()
    return BaseFontAfm(
        writingDirection = writingDirection,
        fontName = lines.value("FontName") ?: "",
        fullName = lines.value("FullName"),
        familyName = lines.value("FamilyName"),
        weight = lines.value("Weight"),
        encodingScheme = lines.value("EncodingScheme"),
        escChar = lines.intValue("EscChar"),
        characterSet = lines.value("CharacterSet"),
        characters = lines.intValue("Characters"),
        isBaseFont = lines.boolValue("IsBaseFont") ?: true,
        isFixedV = lines.boolValue("IsFixedV"),
        capHeight = lines.intValue("CapHeight"),
        xHeight = lines.intValue("xHeight"),
        ascent = lines.intValue("Ascender"),
        descent = lines.intValue("Descender"),
        lines.extractCharMetrics(writingDirection)
    )

}

private fun List<String>.extractCharMetrics(writingDirection: WritingDirection): CharMetrics {

    val startLine = firstOrNull { it.startsWith("StartCharMetrics") }

    val count = intValue("StartCharMetrics")

    return CharMetrics(
        when (count) {
            null -> emptyList()
            else -> drop(indexOf(startLine) + 1).take(count).map { it.toCharMetric(writingDirection) }.toList()
        }
    )
}

private fun String.toCharMetric(writingDirection: WritingDirection): CharMetric {
    val parts = this.split(";").map { it.trim() }.filter { it.isNotEmpty() }.associate {
        val index = it.indexOfFirst { c -> c == ' ' }
        Pair(it.take(index), it.drop(index).trim())
    }

    val name = parts["N"] ?: ""
    return CharMetric(name, charCode(parts), charWidth(parts), charHeight(parts))
}

private fun charCode(map: Map<String, String>) = map["C"]?.toInt() ?: map["CH"]?.toInt(16) ?: -1

private fun charWidth(map: Map<String, String>) = map["WX"]?.toInt() ?: map["W0X"]?.toInt() ?: map["W1X"]?.toInt() ?: 0
private fun charHeight(map: Map<String, String>) = map["WY"]?.toInt() ?: map["W0Y"]?.toInt() ?: map["W1Y"]?.toInt() ?: 0

private fun List<String>.writingDirection(): WritingDirection {
    return when (intValue("MetricsSets")) {
        1 -> WritingDirection.Vertical
        2 -> WritingDirection.Both
        else -> WritingDirection.Horizontal
    }
}

fun List<String>.value(key: String): String? {
    val line = firstOrNull { it.startsWith(key) }

    if (line == null) return null;

    val splitIndex = line.indexOfFirst { it == ' ' }
    return line.drop(splitIndex + 1)
}

fun List<String>.intValue(key: String): Int? {
    return value(key)?.toInt()
}

fun List<String>.boolValue(key: String): Boolean? {
    return when (value(key)) {
        "true" -> true
        "false" -> false
        else -> null
    }
}
