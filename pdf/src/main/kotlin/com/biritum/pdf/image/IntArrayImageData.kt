package com.biritum.pdf.image


fun intArrayImageData(width: Int, height: Int, data: IntArray): ImageData {
    if (data.size != width * height)
        throw Exception("data.size != width * height")

    fun toBytes(x: Int): Sequence<Byte> = sequence {
        yield(x.r.toByte())
        yield(x.g.toByte())
        yield(x.b.toByte())
    }

    return ImageData(width, height, data.asSequence().flatMap(::toBytes), "", 8, "DeviceRGB")
}

