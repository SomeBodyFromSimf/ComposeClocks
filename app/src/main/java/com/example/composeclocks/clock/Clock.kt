package com.example.composeclocks.clock

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

typealias TimePosition = Triple<Int, Int, Int>

@Composable
fun ClockComponent(
    modifier: Modifier = Modifier,
    time: TimePosition
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .drawBehind {
                val mainRadius = size.width / 3
                val epsilon = 0.028f * mainRadius
                val bigRadius = mainRadius + epsilon
                val smallRadius = mainRadius - epsilon

                drawBackgroundClock(
                    epsilon = epsilon,
                    bigRadius = bigRadius,
                    smallRadius = smallRadius)
                drawHourArrow(
                    epsilon = epsilon,
                    currentHour = time.first%12,
                    currentMinute = time.second,
                    smallRadius = smallRadius)
                drawMinuteArrow(
                    epsilon = epsilon,
                    currentMin = time.second,
                    smallRadius = smallRadius)
                drawSecondArrow(
                    currentMin = time.third,
                    smallRadius = smallRadius)

                drawCircle(Color.Black, radius = epsilon)

            }
    )

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Prev() {
    Column(verticalArrangement = Arrangement.Center) {
        ClockComponent(time = Triple(14,50,54))
    }
}

private fun DrawScope.drawBackgroundClock(
    epsilon: Float,
    bigRadius: Float,
    smallRadius: Float,
) {
    drawCircle(color = Color.Black, radius = bigRadius)
    drawCircle(color = Color.White, radius = smallRadius)

    (0 .. 12).forEach { currentHour ->
        val endXinRadius =(cos(PI/2 - (currentHour*PI/6)) *smallRadius).toFloat() + center.x
        val endYinRadius =(sin(PI/2 - (currentHour*PI/6)) *smallRadius).toFloat() + center.y


        val startPosL = center.betweenByPercent(
            Offset(endXinRadius, endYinRadius), 0.15f)
        val endPosL = center.betweenByPercent(
            Offset(endXinRadius, endYinRadius), 0.05f)
        drawLine(
            color = Color.Black,
            start = startPosL,
            end = endPosL,
            strokeWidth = epsilon)
    }
}

private fun DrawScope.drawHourArrow(
    epsilon: Float,
    currentHour: Int,
    currentMinute: Int,
    smallRadius: Float,
) {
    val allMinutes = currentHour * 60 + currentMinute
    val endXonR = (cos(PI * (allMinutes - 180) / 360) * smallRadius).toFloat() + center.x
    val endYonR = (sin(PI * (allMinutes - 180) / 360) * smallRadius).toFloat() + center.y

    val minuteArrowStartPos = center
    val minuteArrowEndPos = center.betweenByPercent(Offset(endXonR, endYonR), 0.5f)

    drawLine(
        Color.Black,
        start = minuteArrowStartPos,
        end = minuteArrowEndPos,
        strokeWidth = epsilon
    )
}

private fun DrawScope.drawMinuteArrow(
    epsilon: Float,
    currentMin: Int,
    smallRadius: Float,
) {
    val endXonR = (cos(PI * (currentMin - 15) / 30) * smallRadius).toFloat() + center.x
    val endYonR = (sin(PI * (currentMin - 15) / 30) * smallRadius).toFloat() + center.y

    val minuteArrowStartPos = center.betweenByPercent(Offset(endXonR, endYonR), 1.05f)
    val minuteArrowEndPos = center.betweenByPercent(Offset(endXonR, endYonR), 0.35f)

    drawLine(
        Color.Black,
        start = minuteArrowStartPos,
        end = minuteArrowEndPos,
        strokeWidth = epsilon/2
    )
}

private fun DrawScope.drawSecondArrow(
    currentMin: Int,
    smallRadius: Float,
) {
    val endXonR = (cos(PI * (currentMin - 15) / 30) * smallRadius).toFloat() + center.x
    val endYonR = (sin(PI * (currentMin - 15) / 30) * smallRadius).toFloat() + center.y

    val minuteArrowStartPos = center.betweenByPercent(Offset(endXonR, endYonR), 1.15f)
    val minuteArrowEndPos = center.betweenByPercent(Offset(endXonR, endYonR), 0.2f)

    drawLine(
        Color.Red,
        start = minuteArrowStartPos,
        end = minuteArrowEndPos
    )
}


private fun Offset.betweenByPercent(o: Offset, percent: Float) = Offset(
    o.x - ((o.x - x)*percent),
    o.y - ((o.y - y)*percent))
