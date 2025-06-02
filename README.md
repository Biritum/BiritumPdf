# BiritumPdf

[YouTube intro](https://www.youtube.com/watch?v=KLd_Hrz2ld0)

## What is it?
A PDF generation library implemented in pure Kotlin. i.e. there is no 
dependencies on any target specific libraries so it should be possible
to use it for any Kotlin target (JVM, Android, WASM and so on.)

PDFs are generated into a Sequence of bytes.

## What is supported?
* Text using the base 14 fonts in any size and color
* Images in Jpg format or in raw RGB
* Lines, Poly lines and Bézier curves with any colors and line thickness
* Multiple pages in any PaperSize (Some predefined paper sizes exist such as Letter and A4)

## What is not supported?
* Embedding of fonts, for now only the base 14 fonts are supported
* Rotation or advanced transformations of text or images
* Advanced layout or typesetting

## Examples
* The examples folder contains examples on how to use the library.
* The afmConverter folder contains example on how to read a FontMetric files (afm) to measure strings of text.

## Example code
```
    // A page with text using Courier font placed at 0.3 inches from top and left side.
    val page1 = Page(Text("Hello world", Font(BaseFont.Courier, 12), Point( 0.3.inch, 0.3.inch )))

    // A page with a image creating from RGB data placed at top left corner and rendered in a 60 by 60 milimeter square.
    val imageData = intArrayOf(
        0xA00000, 0x00FF00, 0x000000, 0xFF0000,
        0xA00080, 0x00FF80, 0x000080, 0xFF0080,
        0xA000FF, 0x00FFFF, 0x0000FF, 0xFF00FF
    )
    val image1 = intArrayImageData(4, 3, imageData)
    val page2 = Page(Image(image1, Point(0.mm, 0.mm), Size(60.mm, 60.mm)))

    // render pages with paper size Letter
    val pdfBytes = renderPdf(PaperSize.Letter, page1, page2)
```
