package com.biritum.pdf.fontmetrics

import com.biritum.pdf.geometry.Length

interface FontMetrics {
    val fontSize : Int
    val writingDirection: WritingDirection
    val fontName: String
    val fullName: String?
    val familyName: String?
    val weight: String?
    val isFixedV: Boolean?

    val ascent: Length
    val descent: Length

    fun charWidth(char: Char) : Length
    fun charHeight(char: Char) : Length
    fun stringWidth(s : String) : Length
    fun bounds(s : String) : Bounds
}

enum class WritingDirection {
    Horizontal,
    Vertical,
    Both
}

data class Bounds(val width: Length, val ascent: Length, val descent: Length)

val FontMetrics.height : Length get() =ascent + descent
val Bounds.height : Length get() =ascent + descent
