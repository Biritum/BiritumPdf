package com.biritum.pdf

import kotlin.test.Test
import kotlin.test.assertEquals

class StreamTest{
    @Test
    fun stream(){
        val stream = PdfStream(Dictionary().apply { add("Key", "StreamTest") }, "data".toByteArray().asSequence())

        assertEquals("<< /Key /StreamTest\n>>\nstream\ndata\nendstream",  stream.content().decodeToString() )
    }
}