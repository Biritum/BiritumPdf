package com.biritum.pdf.line

import com.biritum.pdf.PageContent
import com.biritum.pdf.PageContext
import com.biritum.pdf.geometry.Point

class Line(private val start: Point, private val end: Point, private val pen: Pen = Pen.BLACK) : PageContent {
    override val usesDeviceRGB: Boolean = true
    override fun pdfContent(context: PageContext) =
        pen.draw {
            append("${context.toPixel(start)} m ${context.toPixel(end)} l")
        }
}