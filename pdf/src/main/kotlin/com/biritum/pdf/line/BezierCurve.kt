package com.biritum.pdf.line

import com.biritum.pdf.PageContent
import com.biritum.pdf.PageContext
import com.biritum.pdf.geometry.Point

class BezierCurve(
    private val start: Point,
    private val p1: Point,
    private val p2: Point,
    private val end: Point,
    private val pen: Pen = Pen.BLACK
) : PageContent {
    override val usesDeviceRGB: Boolean get() = true
    override fun pdfContent(context: PageContext) =
        pen.draw {
            val pp0 = context.toPixel(start).toString()
            val pp1 = context.toPixel(p1).toString()
            val pp2 = context.toPixel(p2).toString()
            val pp3 = context.toPixel(end).toString()
            append("$pp0 m $pp1 $pp2 $pp3 c")
        }
}