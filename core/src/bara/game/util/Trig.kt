package bara.game.util

import kotlin.math.PI

const val INV_PI_180 : Float = (180 / PI).toFloat()
const val PI_OVER_180 : Float = (PI / 180).toFloat()

inline fun radiansToDegrees(radians: Float) : Float {
    return radians * INV_PI_180
}

inline fun degreesToRadians(degrees: Float) : Float {
    return degrees * PI_OVER_180
}

inline fun sinf(f: Float) : Float {
    return Math.sin(f.toDouble()).toFloat()
}

inline fun cosf(f: Float) : Float {
    return Math.cos(f.toDouble()).toFloat()
}

inline fun tanf(f: Float) : Float {
    return Math.tan(f.toDouble()).toFloat()
}