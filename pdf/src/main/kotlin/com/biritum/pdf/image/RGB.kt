package com.biritum.pdf.image

val Int.r: Int get() = this shr 16 and 0xFF
val Int.g: Int get() = this shr 8 and 0xFF
val Int.b: Int get() = this and 0xFF
