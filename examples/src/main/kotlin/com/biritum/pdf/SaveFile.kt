package com.biritum.pdf

import java.io.File

fun saveDocument(file: File, data: Sequence<Byte>) {
    file.parentFile.mkdirs()
    file.writeBytes(data.toList().toByteArray())
}
