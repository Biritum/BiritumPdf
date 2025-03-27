package com.biritum.pdf.image

import com.biritum.pdf.geometry.Point
import com.biritum.pdf.PageContent
import com.biritum.pdf.PageContext
import com.biritum.pdf.geometry.Size
import kotlin.math.abs
import kotlin.math.min

data class Image(val imageData: ImageData, val upperLeft: Point, val size: Size) : PageContent {
    private val lowerRight: Point = Point(upperLeft.x + size.width, upperLeft.y + size.height)
    override val usesDeviceRGB: Boolean = false

    override fun pdfContent(context : PageContext): String {
        val imageRef = context.findReference(imageData)
        val (ax, ay) = context.toPixel(upperLeft)
        val (bx, by) = context.toPixel(lowerRight)

        val width = abs(ax - bx)
        val height = abs(ay - by)
        val left = min(ax, bx)
        val bottom = min(ay, by)

        return "q $width 0 0 $height $left $bottom cm /$imageRef Do Q"
    }
}