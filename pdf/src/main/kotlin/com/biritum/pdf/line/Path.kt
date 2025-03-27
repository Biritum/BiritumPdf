package com.biritum.pdf.line

import com.biritum.pdf.PageContent
import com.biritum.pdf.PageContext
import com.biritum.pdf.geometry.Pixel
import com.biritum.pdf.geometry.Point

class Path private constructor(private val parts: List<PathPart>, private val pen: Pen) : PageContent {

    constructor(start: Point, pen: Pen = Pen.BLACK) : this(listOf(StartPoint(start)), pen)

    fun lineTo(point: Point): Path {
        return Path(parts + PathLine(point), pen)
    }

    fun bezierTo(p1: Point, p2: Point, end: Point): Path {
        return Path(parts + PathBezier(p1, p2, end), pen)
    }

    override val usesDeviceRGB: Boolean  = true
    override fun pdfContent(context: PageContext): String {
        return pen.draw {
            parts.forEach {
                append(it.pathPart(context::toPixel))
                append(" ")
            }
        }
    }

    interface PathPart {
        fun pathPart(toPixel: (Point) -> Pixel): String
    }

    data class StartPoint(val p: Point) : PathPart {
        override fun pathPart(toPixel: (Point) -> Pixel): String = "${toPixel(p)} m"
    }

    data class PathLine(val p: Point) : PathPart {
        override fun pathPart(toPixel: (Point) -> Pixel): String =
            "${toPixel(this.p)} l"
    }

    data class PathBezier(val p2: Point, val p3: Point, val p4: Point) : PathPart {
        override fun pathPart(toPixel: (Point) -> Pixel): String {
            val pp2 = toPixel(p2).toString()
            val pp3 = toPixel(p3).toString()
            val pp4 = toPixel(p4).toString()
            return "$pp2 $pp3 $pp4 c"
        }
    }
}