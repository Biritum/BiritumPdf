package com.biritum.pdf.line

import com.biritum.pdf.PageContent
import com.biritum.pdf.PageContext
import com.biritum.pdf.geometry.Point
import com.biritum.pdf.geometry.Size

class Rectangle(upperLeft: Point, size: Size, private val pen: Pen = Pen.BLACK) : PageContent {
    private val upperRight: Point = Point(upperLeft.x + size.width, upperLeft.y)
    private val lowerLeft: Point = Point(upperLeft.x, upperLeft.y + size.height)
    override val usesDeviceRGB: Boolean = true
    override fun pdfContent(context: PageContext): String {
        val (lfx, lfy) = context.toPixel(lowerLeft)
        val (urx, ury) = context.toPixel(upperRight)
        return pen.draw {
            append("$lfx $lfy ${urx - lfx} ${ury - lfy} re")
        }
    }
}