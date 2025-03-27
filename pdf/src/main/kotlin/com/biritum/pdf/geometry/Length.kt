package com.biritum.pdf.geometry

import kotlin.math.round

val Number.mm: Length get() = BaseLength(this.toDouble(), LengthUnit.Millimeter)
val Number.inch: Length get() = BaseLength(this.toDouble(), LengthUnit.Inches)
val Number.points: Length get() = BaseLength(this.toDouble(), LengthUnit.Points)

const val mmPerInch = 25.4
const val unitsPerInch = 72

enum class LengthUnit(val lengthInMillimeter : Double) {
    Millimeter(1.0),
    Inches(mmPerInch),
    Points(mmPerInch/ unitsPerInch)
}

sealed interface Length {
    fun toPixels() = toPixels(unitsPerInch)
    fun toPixels(pointsPerInch: Int): Int
    fun toMillimeter(): Double
    fun toMeasurement(measurement: LengthUnit) = toMillimeter() / measurement.lengthInMillimeter

    operator fun plus(other: Length): Length = AddedLength(this, other)
    operator fun minus(other: Length): Length = SubtractedLength(this, other)
    operator fun times(factor: Number): Length = TimesLength(this, factor.toDouble())
    operator fun div(factor: Number): Length = TimesLength(this, 1/factor.toDouble())
}

private class BaseLength(private val amount : Double, private val unit : LengthUnit) : Length {
    override fun toPixels(pointsPerInch: Int): Int =  round(toMillimeter() / mmPerInch * pointsPerInch).toInt()
    override fun toMillimeter() : Double = amount * unit.lengthInMillimeter

    override fun toString(): String = "[${toMeasurement(LengthUnit.Points)} points]"
}

private class AddedLength(private val first: Length, private val second: Length) : Length {
    override fun toPixels(pointsPerInch: Int): Int = first.toPixels(pointsPerInch) + second.toPixels(pointsPerInch)
    override fun toMillimeter(): Double = first.toMillimeter() + second.toMillimeter()

    override fun toString(): String = "[${toMeasurement(LengthUnit.Points)} points]"
}

private class SubtractedLength(private val first: Length, private val second: Length) : Length {
    override fun toPixels(pointsPerInch: Int): Int = first.toPixels(pointsPerInch) - second.toPixels(pointsPerInch)
    override fun toMillimeter(): Double = first.toMillimeter() - second.toMillimeter()

    override fun toString(): String = "[${toMeasurement(LengthUnit.Points)} points]"
}

private class TimesLength(private val first: Length, private val factor: Double) : Length {
    override fun toPixels(pointsPerInch: Int): Int = round( first.toPixels(pointsPerInch) * factor).toInt()
    override fun toMillimeter(): Double = first.toMillimeter() * factor

    override fun toString(): String = "[${toMeasurement(LengthUnit.Points)} points]"
}
