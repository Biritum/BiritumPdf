package com.biritum.pdf

import com.biritum.pdf.geometry.Point
import com.biritum.pdf.geometry.Size
import com.biritum.pdf.geometry.mm
import com.biritum.pdf.image.Image
import com.biritum.pdf.line.*
import java.io.File

fun main() {
    multiPage()
}

fun multiPage() {
    val output = File("./out")
    output.mkdirs()

    val page1 = Page(
            Image(logo, Point(20.mm, 20.mm), Size(20.mm, 20.mm)),
            Line(
                Point(20.mm, 20.mm),
                Point(PaperSize.A4.width - 20.mm, PaperSize.A4.height - 20.mm)
            ),
            Line(
                Point(20.mm, PaperSize.A4.height - 20.mm),
                Point(PaperSize.A4.width - 20.mm, 20.mm)
            ),
            Image(logo, Point(40.mm, 40.mm), Size(83.mm, 56.mm))
    )

    val image = Image(colors, Point(20.mm, 60.mm), Size(90.mm, 90.mm))

    val content = GroupedContent(image, Line( Point(0.mm, 0.mm), Point(110.mm, 110.mm)))
    val page2 = Page(content, PaperSize.A5)
    val page3 = Page(content, PaperSize.A5_Landscape)

    val pdf = renderPdf(PaperSize.A4, page1, page2, page3, pageMode = PageMode.UseThumbs)
    saveDocument(File("./out", "multiplePages.pdf"), pdf)
}
