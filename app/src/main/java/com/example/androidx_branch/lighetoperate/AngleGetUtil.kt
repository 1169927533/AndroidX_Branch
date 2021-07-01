package com.example.androidx_branch.lighetoperate

import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * @Author Yu
 * @Date 2021/7/1 10:09
 * @Description 角度获取器
 */
class AngleGetUtil {
    /**
     * 计算滑动的角度
     */
    private fun handleActionMove() {
      /*  var linA = measureLineLength(
            movePoint[0]!!,
            circleCenterPoint[0]!!,
            movePoint[1]!!,
            circleCenterPoint[1]!!
        )
        var linB = measureLineLength(
            behindPoint[0]!!,
            circleCenterPoint[0]!!,
            behindPoint[1]!!,
            circleCenterPoint[1]!!
        )
        var lineC = 0
        var lineCx = abs(movePoint[0]!! - behindPoint[0]!!).toDouble()
            .pow(2.toDouble()).toInt()
        var lineCy = abs(movePoint[1]!! - behindPoint[1]!!).toDouble()
            .pow(2.toDouble()).toInt()
        lineC = sqrt((lineCx + lineCy).toDouble()).toInt()

        angle = fixAngle(
            Math.toDegrees(
                acos(
                    (linA.pow(2.0) + linB.pow(2.0) - lineC.toDouble().pow(2.0)) / (2 * linA * linB)
                )
            )
                .toFloat()
        )
        if (isClockwise(movePoint[0]!!, movePoint[1]!!)) {
        } else {
            angle = -angle
        }
*/
    }
    // 根据两点计算两点间的长度
    private fun measureLineLength(pointA: FloatArray, pointB:FloatArray): Double {
        return sqrt(
            abs(pointA[0] - pointB[0]).toDouble().pow(2.toDouble()) +
                    abs((pointA[1] - pointB[1]).toDouble()).pow(2.toDouble())
        )
    }
}