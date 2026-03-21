package com.cornellappdev.chimes.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cornellappdev.chimes.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch


@Composable
fun Onboarding () {
    val bgAlpha = remember { Animatable(1f) }
    val whiteAlpha = remember { Animatable(0f) }
    val logoAlpha = remember { Animatable(0f) }
    val logoScale = remember { Animatable(1f) }
    val chimesAlpha = remember { Animatable(0f) }
    val headerOffsetY = remember { Animatable(0f) }
    val loginAlpha = remember { Animatable(0f) }
    val minuteHandRotation = remember { Animatable(0f) }
    val hourHandRotation = remember { Animatable(0f) }
    var handsSpinStarted by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(300)

        launch {
            bgAlpha.animateTo(0f, tween(300, easing = LinearEasing))
        }

        delay(300)
        launch {
            whiteAlpha.animateTo(1f, tween(300, easing = LinearEasing))
        }
        delay(300)
        logoAlpha.animateTo(1f, tween(300, easing = LinearEasing))

        delay(300)

        logoScale.animateTo(0.55f, tween(300, easing = FastOutSlowInEasing))
        handsSpinStarted = true


        delay(300)
        chimesAlpha.animateTo(1f, tween(300, easing = LinearEasing))

        delay(300)
        launch {
            headerOffsetY.animateTo(-90f, tween(300, easing = FastOutSlowInEasing))
        }
        delay(300)
        loginAlpha.animateTo(1f, tween(300, easing = LinearEasing))
    }

    LaunchedEffect(handsSpinStarted) {
        if (!handsSpinStarted) return@LaunchedEffect

        launch {
            while (isActive) {
                minuteHandRotation.animateTo(
                    minuteHandRotation.value + 360f,
                    animationSpec = tween(durationMillis = 2_000, easing = LinearEasing)
                )
            }
        }
        launch {
            while (isActive) {
                hourHandRotation.animateTo(
                    hourHandRotation.value + 360f,
                    animationSpec = tween(durationMillis = 10_000, easing = LinearEasing)
                )
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {

        Image(
            painter = painterResource(id = R.drawable.ic_onboarding_background),
            contentDescription = "Onboarding background",
            modifier = Modifier
                .fillMaxSize()
                .alpha(bgAlpha.value),
            contentScale = ContentScale.Crop
        )



        /*
        //with gradient fade
        Image(
            painter = painterResource(id = R.drawable.ic_onboarding_background),
            contentDescription = "Onboarding background",
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer{
                    compositingStrategy = CompositingStrategy.Offscreen
                }
                .drawWithContent{
                    drawContent()
                    val brush = Brush.verticalGradient(
                        0f to Color.White,
                        bgAlpha.value to Color.Transparent
                    )
                    drawRect(brush = brush, blendMode = BlendMode.DstIn)
                },
            contentScale = ContentScale.Crop
        )

         */

        /*
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .alpha(whiteAlpha.value)
        )

         */

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(headerOffsetY.value.dp))

            ClockLogo(
                modifier = Modifier
                    .size((300 * logoScale.value).dp)
                    .alpha(logoAlpha.value),
                minuteHandRotation = minuteHandRotation.value,
                hourHandRotation = hourHandRotation.value
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "chimes",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFE24B4B)
                ),
                modifier = Modifier.alpha(chimesAlpha.value),
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(140.dp))
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
private fun ClockLogo(
    modifier: Modifier = Modifier,
    minuteHandRotation: Float,
    hourHandRotation: Float
) {
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        val clockSize = maxWidth * (69f / 118f)
        // Adjust these coefficients to make the hands smaller/larger
        val minuteHandWidth = maxWidth * 0.21f
        val minuteHandHeight = maxWidth * 0.12f
        val hourHandWidth = maxWidth * 0.17f
        val hourHandHeight = maxWidth * 0.14f

        Image(
            painter = painterResource(id = R.drawable.ic_clock),
            contentDescription = "Clock base",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.size(clockSize)
        )

        Image(
            painter = painterResource(id = R.drawable.ic_hour_hand),
            contentDescription = "Hour hand",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(hourHandWidth, hourHandHeight)
                .graphicsLayer {
                    var value = 11f / 30f
                    translationX = (0.5f - value) * size.width
                    translationY = (0.5f - value) * size.height
                    
                    rotationZ = hourHandRotation
                    transformOrigin = TransformOrigin(value, value)
                }
        )

        Image(
            painter = painterResource(id = R.drawable.ic_minute_hand),
            contentDescription = "Minute hand",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(minuteHandWidth, minuteHandHeight)
                .graphicsLayer {
                    var xValue = 2.5f/25f
                    var yValue = 16.5f/25f

                    translationX = (0.5f - xValue) * size.width
                    translationY = (0.5f - yValue) * size.height

                    rotationZ = minuteHandRotation
                    transformOrigin = TransformOrigin(xValue, yValue)
                }
        )
    }
}
