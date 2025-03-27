package com.biritum.pdf

import com.biritum.pdf.fontmetrics.height
import com.biritum.pdf.fontmetrics.metrics
import com.biritum.pdf.geometry.Point
import com.biritum.pdf.geometry.mm
import com.biritum.pdf.line.Pen
import com.biritum.pdf.text.BaseFont
import com.biritum.pdf.text.Font
import com.biritum.pdf.text.Text
import java.io.File

// font metrics for base 14 fonts can be found at
// https://github.com/tecnickcom/tc-font-core14-afms

fun main() {
    drawText()
}

val paper = PaperSize.A5_Landscape

fun drawText() {
    val page = Page(
        headingText(),
        leftSideText(),
        rightSideText(),
        bottomText(),
    )

    val pdf = renderPdf(paper, page)

    saveDocument(File("./out", "text.pdf"), pdf)
}

fun headingText(): PageContent {
    val font = Font(BaseFont.Courier, 36)
    val courierFm = font.metrics()
    val heading = "Examples of drawing text"
    val headingBounds = courierFm.bounds(heading)
    return Text(heading, font, Point(paper.width / 2 - headingBounds.width / 2, 20.mm))
}

fun leftSideText(): PageContent {
    val font = Font(BaseFont.HelveticaBold, 14)

    val metrics = font.metrics()
    val text = "Text on left side line "
    return GroupedContent(
        (1..5).map {
            Text(
                text + it, font, Point(
                    10.mm, 30.mm + (metrics.height * it)
                ),
                Pen(0xFF1111)
            )
        }.toList()
    )
}

fun rightSideText(): PageContent {
    val font = Font(BaseFont.TimesRoman, 12)

    val metrics = font.metrics()
    val text = "Text on right side with different lengths  "
    return GroupedContent((1..15).map { text + "#".repeat(it) }
        .mapIndexed { index, it ->
            val bounds = metrics.bounds(it)
            Text(
                it, font, Point(
                    paper.width-10.mm-bounds.width, 40.mm + (metrics.height * index)
                ),
                Pen(0x00FF00 + index*0x10)
            )
        }.toList())
}

fun bottomText(): PageContent {
    val font = Font(BaseFont.TimesItalic, 9)
    val metrics = font.metrics()
    val heading = "A text aligned to bottom"
    val bounds = metrics.bounds(heading)
    return Text(heading, font, Point(paper.width - bounds.width - 10.mm, paper.height - bounds.height*2), Pen(0x008800))
}
