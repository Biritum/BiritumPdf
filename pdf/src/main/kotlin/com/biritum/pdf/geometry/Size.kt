package com.biritum.pdf.geometry
data class Size(val width: Length, val height: Length) {
    init {
        if (width.toMillimeter() < 0) throw Exception("negative width")
        if (height.toMillimeter() < 0) throw Exception("negative height")
    }
}
