package com.biritum.pdf

/**
 * @param paperSize paper size for page, if null inherited from document
 */
data class Page(internal val content: PageContent, val paperSize: PaperSize? = null) {
    constructor(vararg content: PageContent, paperSize: PaperSize? = null) : this(
        GroupedContent(content.toList()),
        paperSize
    )
}

