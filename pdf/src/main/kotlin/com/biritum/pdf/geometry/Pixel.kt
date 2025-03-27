package com.biritum.pdf.geometry

data class Pixel(val x : Int, val y : Int) {
    override fun toString(): String {
        return "$x $y"
    }
}
