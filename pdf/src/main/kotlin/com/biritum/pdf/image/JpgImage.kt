package com.biritum.pdf.image

fun jpgImage(data: ByteArray): ImageData? {
    val index = data.indexOfSubArray(byteArrayOf(0xFF.toByte(), 0xC0.toByte()))
    return when {
        index >= 0 -> {
            val width = data.intAt(index + 7)
            val height = data.intAt(index + 5)
            ImageData(width, height, data.asSequence(), "DCTDecode", 8, "DeviceRGB")
        }
        else -> null
    }
}

private fun ByteArray.intAt(index: Int) =
    (this[index].toUByte().toInt() shl 8) + this[index + 1].toUByte().toInt()

private fun ByteArray.indexOfSubArray(subArray: ByteArray): Int {
    // Ok performance for short sub arrays.
    for (i in this.indices) {
        if (i + subArray.size > this.size)
            return -1
        if (subArray.indices.all { this[it + i] == subArray[it] })
            return i
    }
    return -1
}
