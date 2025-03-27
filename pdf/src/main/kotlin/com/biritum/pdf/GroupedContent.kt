package com.biritum.pdf

class GroupedContent(private val content: List<PageContent>) : PageContent {
    constructor(vararg content: PageContent) : this(content.asList())

    override val usesDeviceRGB: Boolean get() = content.any { it.usesDeviceRGB }

    override fun pdfContent(context: PageContext): String = StringBuilder().apply {
        content.map { it.pdfContent(context) }.forEach { appendLine(it) }
    }.toString()
}