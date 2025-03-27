package com.biritum.pdf.line

import com.biritum.pdf.MockContext
import com.biritum.pdf.geometry.Point
import com.biritum.pdf.geometry.Size
import com.biritum.pdf.geometry.mm
import kotlin.test.Test
import kotlin.test.assertEquals

class LineTest {
    @Test
    fun drawLine(){
        val line = Line(Point(10.mm,20.mm), Point(30.mm, 40.mm), Pen(0xFF0080, 2.1))

        val actual = line.pdfContent(MockContext())
        val expected = "1.0 0.0 0.5019607843137255 RG 2.1 w 28 519 m 85 463 l S"

        assertEquals(expected, actual)
    }

    @Test
    fun drawPolyLine(){
        val line = PolyLine(
            listOf( Point(10.mm,20.mm), Point(30.mm, 40.mm), Point(12.mm, 22.mm)),
            false,
            Pen(0xFF00FF, 2.1))

        val actual = line.pdfContent(MockContext())
        val expected = "1.0 0.0 1.0 RG 2.1 w 28 519 m 85 463 l 34 514 l  S"

        assertEquals(expected, actual)
    }

    @Test
    fun drawPath(){
        val path = Path(Point(10.mm,20.mm), Pen(0xFF00FF, 2.1, true) ).lineTo(Point(30.mm, 40.mm) ).lineTo(Point(12.mm, 22.mm) )

        val actual = path.pdfContent(MockContext())
        val expected = "1.0 0.0 1.0 rg 2.1 w 28 519 m 85 463 l 34 514 l  f"

        assertEquals(expected, actual)
    }


    @Test
    fun drawRectangle(){
        val rectangle = Rectangle(Point(10.mm,20.mm), Size(20.mm, 12.mm), Pen(0xFF00FF, 2.1) )

        val actual = rectangle.pdfContent(MockContext())
        val expected = "1.0 0.0 1.0 RG 2.1 w 28 485 57 34 re S"

        assertEquals(expected, actual)
    }
}