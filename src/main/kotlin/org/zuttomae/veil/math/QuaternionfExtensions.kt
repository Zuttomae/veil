package org.zuttomae.veil.math

import org.joml.*

public inline fun quaternionf(block: Quaternionf.() -> Unit = {}): Quaternionf = Quaternionf().apply(block)

public inline fun quaternionf(
    x: Double,
    y: Double,
    z: Double,
    w: Double,
    block: Quaternionf.() -> Unit = {}
): Quaternionf = Quaternionf(x, y, z, w).apply(block)

public inline fun quaternionf(
    x: Float,
    y: Float,
    z: Float,
    w: Float,
    block: Quaternionf.() -> Unit = {}
): Quaternionf = Quaternionf(x, y, z, w).apply(block)

public inline fun quaternionf(source: Quaternionfc, block: Quaternionf.() -> Unit = {}): Quaternionf =
    Quaternionf(source).apply(block)

public inline fun quaternionf(source: Quaterniondc, block: Quaternionf.() -> Unit = {}): Quaternionf =
    Quaternionf(source).apply(block)

public inline fun quaternionf(axisAngle: AxisAngle4f, block: Quaternionf.() -> Unit = {}): Quaternionf =
    Quaternionf(axisAngle).apply(block)

public inline fun quaternionf(axisAngle: AxisAngle4d, block: Quaternionf.() -> Unit = {}): Quaternionf =
    Quaternionf(axisAngle).apply(block)