package com.biritum.pdf.image

data class ImageData(
    val width: Int,
    val height: Int,
    val data: Sequence<Byte>,
    val filter: String,
    val bitsPerComponent: Int,
    val colorSpace: String
) {
    val pixelCount = width * height
}

