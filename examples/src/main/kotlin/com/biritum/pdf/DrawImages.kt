package com.biritum.pdf

import com.biritum.pdf.geometry.Point
import com.biritum.pdf.geometry.Size
import com.biritum.pdf.geometry.mm
import com.biritum.pdf.image.Image
import com.biritum.pdf.image.*
import java.io.File

fun main() {
    drawImages()
}

fun drawImages() {
    val imageData = intArrayOf(
        0xA00000, 0x00FF00, 0x000000, 0xFF0000,
        0xA00080, 0x00FF80, 0x000080, 0xFF0080,
        0xA000FF, 0x00FFFF, 0x0000FF, 0xFF00FF
    )

    val image1 = intArrayImageData(4, 3, imageData)
    val image2 = intArrayImageData(2, 6, imageData)

    val page = Page(
        Image(image1, Point(0.mm, 0.mm), Size(60.mm, 60.mm)),
        Image(colors, Point(80.mm, 0.mm), Size(60.mm, 60.mm)),
        Image(logo, Point(160.mm, 0.mm), Size(120.mm, 60.mm)),
        Image(image2, Point(0.mm, 100.mm), Size(20.mm, 80.mm)),
        Image(colors, Point(80.mm, 100.mm), Size(60.mm, 30.mm)),
        Image(logo, Point(160.mm, 100.mm), Size(60.mm, 60.mm)),
    )

    val pdf = renderPdf(PaperSize.A4_Landscape, page)

    saveDocument(File("./out", "image.pdf"), pdf)
}