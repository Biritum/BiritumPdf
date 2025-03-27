package com.biritum.pdf.image

import com.biritum.pdf.MockContext
import com.biritum.pdf.geometry.Point
import com.biritum.pdf.geometry.Size
import com.biritum.pdf.geometry.inch
import kotlin.test.Test
import kotlin.test.assertEquals

class ImageTest {
    @Test
    fun imageFromPixelData (){
        val imageData = intArrayOf(
            0xA00000, 0x00FF00, 0x000000, 0xFF0000,
            0xA00080, 0x00FF80, 0x000080, 0xFF0080,
            0xA000FF, 0x00FFFF, 0x0000FF, 0xFF00FF
        )

        val data = intArrayImageData(4, 3, imageData)
        val image = Image(data, Point(1.inch,2.inch), Size(3.inch,4.inch))

        val actual  = image.pdfContent(MockContext())
        val expected = "q 216 0 0 288 72 144 cm /Image1 Do Q"

        assertEquals(expected, actual)
    }
}
