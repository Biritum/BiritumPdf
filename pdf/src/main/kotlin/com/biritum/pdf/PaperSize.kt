package com.biritum.pdf

import com.biritum.pdf.geometry.Length
import com.biritum.pdf.geometry.inch
import com.biritum.pdf.geometry.mm

data class PaperSize(val name : String, val width : Length, val height : Length) : PdfObject {
    companion object{
        val A3 = PaperSize("A3", 297.mm, 420.mm)
        val A3_Landscape = A3.rotated()
        val A4 = PaperSize("A4", 210.mm, 297.mm)
        val A4_Landscape = A4.rotated()
        val A5 = PaperSize("A5", 148.mm, 210.mm)
        val A5_Landscape = A5.rotated()

        val Letter = PaperSize("Letter", 8.5.inch, 11.inch)
        val Letter_Landscape = Letter.rotated()
    }

    override fun stringContent() = toString()
}

internal fun PaperSize.rotated() : PaperSize = PaperSize(this.name + " Landscape", this.height, this.width)

