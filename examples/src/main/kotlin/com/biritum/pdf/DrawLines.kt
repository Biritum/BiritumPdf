package com.biritum.pdf

import com.biritum.pdf.geometry.Point
import com.biritum.pdf.geometry.Size
import com.biritum.pdf.geometry.mm
import com.biritum.pdf.line.*
import java.io.File

fun main() {
    drawLines()
}

fun drawLines() {
    val linesPage = Page(
        Line(Point(20.mm, 20.mm), Point(PaperSize.A4.width - 20.mm, 20.mm)),
        Line(Point(20.mm, 30.mm), Point(PaperSize.A4.width - 20.mm, 30.mm), Pen(0x0, 1.0, false)),
        Line(Point(20.mm, 40.mm), Point(PaperSize.A4.width - 20.mm, 50.mm), Pen(0xFF0000, 3.0, false)),
        Line(Point(20.mm, 50.mm), Point(PaperSize.A4.width - 20.mm, 40.mm), Pen(0xFF, 10.0, false)),

        Rectangle(Point(20.mm, 70.mm), Size(PaperSize.A4.width - 40.mm, 20.mm)),
        Rectangle(Point(20.mm, 100.mm), Size(PaperSize.A4.width - 40.mm, 36.mm), Pen(0xAA0000, 3.0)),

        PolyLine(
            listOf(
                Point(30.mm, 160.mm),
                Point(40.mm, 180.mm),
                Point(50.mm, 170.mm),
            ), close = true
        ),

        Path(Point(60.mm, 160.mm), Pen(0x900090, 0.0, true)).lineTo(Point(120.mm, 160.mm))
            .lineTo(Point(120.mm, 180.mm))
            .bezierTo(Point(40.mm, 200.mm), Point(60.mm, 210.mm), Point(60.mm, 160.mm)),

        BezierCurve(
            Point(40.mm, 210.mm),
            Point(PaperSize.A4.width / 2, PaperSize.A4.height - 40.mm),
            Point(PaperSize.A4.width / 2, PaperSize.A4.height - 40.mm),
            Point(PaperSize.A4.width - 40.mm, 210.mm),
            Pen(0xA00000, 0.0, false)
        ),
    )

    saveDocument(
        File("./out", "lines.pdf"), renderPdf(PaperSize.A4, linesPage)
    )
}
