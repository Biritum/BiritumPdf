package com.biritum.pdf.line

import com.biritum.pdf.PageContent
import com.biritum.pdf.PageContext
import com.biritum.pdf.geometry.Point

class PolyLine(private val points: List<Point>, private val close: Boolean = false, private val pen: Pen = Pen.BLACK) :
    PageContent {
    override val usesDeviceRGB: Boolean  = true
    override fun pdfContent(context : PageContext): String {
        if (points.size <= 1)
            return ""

        return pen.draw {
            var s = "m"
            points.forEach {
                append("${context.toPixel(it)} $s ")
                s = "l"
            }
            if (close)
                append("${context.toPixel(points[0])} $s ")
        }
    }
}