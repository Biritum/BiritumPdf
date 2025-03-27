package com.biritum.pdf

import com.biritum.pdf.geometry.Pixel
import com.biritum.pdf.geometry.Point
import com.biritum.pdf.geometry.inch
import com.biritum.pdf.image.ImageData
import com.biritum.pdf.text.FontType

class MockContext : PageContext {
    val paperSize = PaperSize("ps", 5.inch, 8.inch)
    override fun toPixel(p: Point)= Pixel(p.x.toPixels(), (paperSize.height - p.y).toPixels())
    override fun findReference(imageData: ImageData) = "Image1"
    override fun findReference(font: FontType) = "Font1"
}