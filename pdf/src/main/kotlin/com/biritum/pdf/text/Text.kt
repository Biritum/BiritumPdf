package com.biritum.pdf.text

import com.biritum.pdf.PageContent
import com.biritum.pdf.PageContext
import com.biritum.pdf.geometry.Point
import com.biritum.pdf.line.Pen

class Text(val text: String, val font: Font, val position: Point, val pen: Pen = Pen.BLACK) :
    PageContent {
    override val usesDeviceRGB: Boolean = false
    override fun pdfContent(context: PageContext): String {
        return Pen(pen.rgb, pen.width, true).draw {
            append("BT /${context.findReference(font.fontType)} ${font.fontSize} Tf ${context.toPixel(position)} Td ($text) Tj ET")
        }
    }
}