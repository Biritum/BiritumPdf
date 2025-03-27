package com.biritum.pdf.text

sealed interface FontType

enum class BaseFont(val baseType: String) : FontType {
    Courier("Courier"),
    CourierBold("Courier-Bold"),
    CourierOblique("Courier-Oblique"),
    CourierBoldOblique("Courier-BoldOblique"),
    Helvetica("Helvetica"),
    HelveticaBold("Helvetica-Bold"),
    HelveticaBoldOblique("Helvetica-BoldOblique"),
    HelveticaOblique(" Helvetica-Oblique"),
    Symbol("Symbol"),
    TimesRoman("Times-Roman"),
    TimesBold("Times-Bold"),
    TimesItalic("Times-Italic"),
    TimesBoldItalic("Times-BoldItalic"),
    ZapfDingbats("ZapfDingbats")
}
