package com.biritum.pdf

import com.biritum.pdf.geometry.Point
import com.biritum.pdf.geometry.mm
import com.biritum.pdf.line.BezierCurve
import com.biritum.pdf.line.Line
import kotlin.test.Test
import kotlin.test.assertEquals

class CompletePagesTest {
    @Test
    fun oneEmptyPage() {
        val doc = renderPdf(PaperSize.A4, listOf())
        assertEquals(pdfWithOneEmptyPage, doc.decodeToString())
    }

    @Test
    fun crossLines() {
        val doc = renderPdf(
            PaperSize.A4,
            Page(
                GroupedContent(
                    Line(
                        Point(20.mm, 20.mm),
                        Point(PaperSize.A4.width - 20.mm, PaperSize.A4.height - 20.mm)
                    ),
                    Line(
                        Point(20.mm, PaperSize.A4.height - 20.mm),
                        Point(PaperSize.A4.width - 20.mm, 20.mm)
                    )
                )
            )
        )
        assertEquals(pdfWithCross, doc.decodeToString())
    }

    @Test
    fun bezierLines() {
        val doc = renderPdf(
            PaperSize.A4,
            Page(
                GroupedContent(
                    BezierCurve(
                        Point(20.mm, 20.mm),
                        Point(PaperSize.A4.width / 2, PaperSize.A4.height / 2),
                        Point(PaperSize.A4.width / 2, PaperSize.A4.height / 2),
                        Point(PaperSize.A4.width - 20.mm, 20.mm)

                    ),
                    BezierCurve(
                        Point(20.mm, (PaperSize.A4.height.toMillimeter() - 20).mm),
                        Point(PaperSize.A4.width / 2, PaperSize.A4.height / 2),
                        Point(PaperSize.A4.width / 2, PaperSize.A4.height / 2),
                        Point(PaperSize.A4.width - 20.mm, PaperSize.A4.height - 20.mm)
                    )
                )
            ),
            pageMode = PageMode.UseThumbs
        )
        assertEquals(pdfWithBezier, doc.decodeToString())
    }

    @Test
    fun twoPages() {
        val pageContent = GroupedContent(
            Line(
                Point(20.mm, 20.mm),
                Point((PaperSize.A4.width.toMillimeter() - 20).mm, (PaperSize.A4.height.toMillimeter() - 20).mm)
            ),
            Line(
                Point(20.mm, (PaperSize.A4.height.toMillimeter() - 20).mm),
                Point((PaperSize.A4.width.toMillimeter() - 20).mm, 20.mm)
            )
        )

        val doc = renderPdf(
            PaperSize.A4,
            Page(pageContent),
            Page(pageContent),
            pageMode = PageMode.UseOutlines
        )

        assertEquals(pdfWithTwoPages, doc.decodeToString())
    }
}
