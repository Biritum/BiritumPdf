package com.biritum.pdf.fontmetrics

import com.biritum.pdf.geometry.Length
import com.biritum.pdf.geometry.points


data class BaseFontAfm(
    val writingDirection: WritingDirection,
    val fontName: String,
    val fullName: String?,
    val familyName: String?,
    val weight: String?,
    val encodingScheme: String?,
    val escChar: Int?,
    val characterSet: String?,
    val characters: Int?,
    val isBaseFont: Boolean,
    val isFixedV: Boolean?,
    val capHeight: Int?,
    val xHeight: Int?,
    val ascent: Int?,
    val descent: Int?,
    val charMetrics: CharMetrics
) {
    fun charWidth(char: Char) = charMetrics.map[char.code]?.width ?: 0
    fun charHeight(char: Char) =
        charMetrics.map[char.code]?.height ?: if (ascent != null && descent != null) ascent + descent else 0
}

class BaseFontFontMetrics(private val afm: BaseFontAfm, override val fontSize: Int) : FontMetrics {
    private val scaleFactor = fontSize/1000.0

    override val writingDirection: WritingDirection = afm.writingDirection
    override val fontName: String = afm.fontName
    override val fullName: String? = afm.fullName
    override val familyName: String? = afm.familyName
    override val weight: String? = afm.weight
    override val isFixedV: Boolean? = afm.isFixedV
    override val ascent: Length = (if (afm.ascent == null) fontSize else afm.ascent * scaleFactor).points
    override val descent: Length = ((afm.descent ?: 0) * -scaleFactor).points

    override fun charWidth(char: Char) = afm.charWidth(char).points
    override fun charHeight(char: Char) = afm.charHeight(char).points
    override fun stringWidth(s: String) = (s.map { afm.charWidth(it) }.sum() * scaleFactor).points
    override fun bounds(s: String) = Bounds(stringWidth(s), ascent, descent)
}

class CharMetrics(chars: List<CharMetric>) {
    val map: Map<Int, CharMetric> = chars.associateBy { it.code }
}

data class CharMetric(val name: String, val code: Int, val width: Int, val height: Int)

