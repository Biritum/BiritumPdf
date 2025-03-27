package com.biritum.pdf

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class DictionaryTest {
    @Test
    fun keysAreNames() {
        var d = Dictionary().apply { add("Key", "Name") }
        assertEquals("<< /Key /Name\n>>", d.stringContent())
    }

    @Test
    fun illegalKeys() {

        assertFailsWith<IllegalArgumentException> {
            Dictionary().apply { add("lowercaseKey", "Name") }
        }
        assertFailsWith<IllegalArgumentException> {
            Dictionary().apply { add("Key%", "Name") }
        }
        assertFailsWith<IllegalArgumentException> {
            Dictionary().apply { add("Key<", "Name") }
        }
        assertFailsWith<IllegalArgumentException> {
            Dictionary().apply { add("Key]", "Name") }
        }
        assertFailsWith<IllegalArgumentException> {
            Dictionary().apply { add("Ke\ry", "Name") }
        }
    }

    @Test
    fun illegalNames() {
        assertFailsWith<IllegalArgumentException> {
            Dictionary().apply { add("Key", "name") }.stringContent()
        }
        assertFailsWith<IllegalArgumentException> {
            Dictionary().apply { add("Key", "Name%") }.stringContent()
        }
        assertFailsWith<IllegalArgumentException> {
            Dictionary().apply { add("Key", "Name>") }.stringContent()
        }
        assertFailsWith<IllegalArgumentException> {
            Dictionary().apply { add("Key", "Name}") }.stringContent()
        }
        assertFailsWith<IllegalArgumentException> {
            Dictionary().apply { add("Key", "Name,") }.stringContent()
        }
    }

    @Test
    fun arrays() {
        assertEquals(
            "<< /OneArray [1 3.1 /Toy true]\n>>",
            Dictionary().apply { add("OneArray", listOf(1, 3.1f, "Toy", true).toPdfArray()) }.stringContent()
        )
    }

    @Test
    fun nestedDictionary() {
        assertEquals(
            "<< /OtherDictionary << /Key /Value\n>>\n>>",
            Dictionary().apply { add("OtherDictionary", Dictionary().apply { add("Key", "Value") }) }.stringContent()
        )
    }
}

