package com.biritum.pdf.line

import com.biritum.pdf.image.b
import com.biritum.pdf.image.g
import com.biritum.pdf.image.r

class Pen(val rgb: Int, val width: Double = 0.0, val fill: Boolean = false) {
    private val pdfString = "${rgb.r / 255.0} ${rgb.g / 255.0} ${rgb.b / 255.0} ${if (fill) "rg" else "RG"} $width w"

    companion object {
        val BLACK = Pen(0)
    }

    fun draw( f: StringBuilder.() -> Unit): String = StringBuilder().apply {
        append(pdfString)
        append(" ")
        f(this)
        append(" ")
        append(if (fill) "f" else "S")
    }.toString()
}
