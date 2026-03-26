package com.cornellappdev.chimes.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.style.TextDecoration
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
    val loginOffsetY = remember { Animatable(300f) } // Starts 300dp below
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
            headerOffsetY.animateTo(-100f, tween(300, easing = FastOutSlowInEasing))
        }
        delay(300)
        launch {
            loginOffsetY.animateTo(0f, tween(300, easing = FastOutSlowInEasing)) //slide
        }
        loginAlpha.animateTo(1f, tween(300, easing = LinearEasing)) //fade
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

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFFEF7F7))) {

        Image(
            painter = painterResource(id = R.drawable.ic_onboarding_background),
            contentDescription = "Onboarding background",
            modifier = Modifier
                .fillMaxSize()
                .alpha(bgAlpha.value),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .offset(y = headerOffsetY.value.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.weight(1f)) //top half of screen

            ClockLogo(
                modifier = Modifier
                    .size((150 * logoScale.value).dp)
                    .alpha(logoAlpha.value),
                minuteHandRotation = minuteHandRotation.value,
                hourHandRotation = hourHandRotation.value
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "chimes",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFCC5555)
                ),
                modifier = Modifier.alpha(chimesAlpha.value),
                fontSize = 40.sp
            )

            // this box takes up the bottom half of the screen, so the login buttons won't push the logo up
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .alpha(loginAlpha.value)
                        .offset(y = loginOffsetY.value.dp)
                        .width(333.dp)
                ) {
                    val curve = 50.dp

                    Spacer(modifier = Modifier.height(64.dp))

                    Button(
                        modifier = Modifier
                            .height(54.dp)
                            .border(
                                width = 1.dp,
                                color = Color(0XFFBCB2B2),
                                shape = RoundedCornerShape(curve)
                            )
                            .fillMaxWidth(),
                        onClick = {},
                        shape = RoundedCornerShape(curve),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        contentPadding = PaddingValues(horizontal = 20.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_google_g),
                                contentDescription = "Google logo",
                                modifier = Modifier.size(32.dp),
                                contentScale = ContentScale.Fit
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Log-in with Google",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Normal,
                                    color = Color.Black
                                ),
                                fontSize = 14.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    Button(
                        modifier = Modifier
                            .height(54.dp)
                            .border(
                                width = 1.dp,
                                color = Color(0XFFBCB2B2),
                                shape = RoundedCornerShape(curve)
                            )
                            .fillMaxWidth(),
                        onClick = {},
                        shape = RoundedCornerShape(curve),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        contentPadding = PaddingValues(horizontal = 20.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_cornell_logo),
                                contentDescription = "Cornell logo",
                                modifier = Modifier.size(32.dp),
                                contentScale = ContentScale.Fit
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Log-in with Cornell netID",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Normal,
                                    color = Color.Black
                                ),
                                fontSize = 14.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(45.dp))

                    HorizontalDivider(
                        color = Color(0XFFBCB2B2),
                        thickness = 1.dp,
                        modifier = Modifier
                            .width(200.dp)
                    )

                    Spacer(modifier = Modifier.height(45.dp))

                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                        ),
                        shape = RoundedCornerShape(0.dp),
                        modifier = Modifier.height(25.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = "log in without an account",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Thin,
                                color = Color.Black,
                                textDecoration = TextDecoration.Underline
                            ),
                            fontSize = 8.sp
                        )
                    }
                }
            }
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
        val clockSize = maxWidth
        
        val minuteHandWidth = maxWidth * 0.36f
        val minuteHandHeight = maxWidth * 0.21f
        val hourHandWidth = maxWidth * 0.29f
        val hourHandHeight = maxWidth * 0.24f

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
                    val value = 11f / 30f

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
                    val xValue = 2.5f/25f
                    val yValue = 16.5f/25f

                    translationX = (0.5f - xValue) * size.width
                    translationY = (0.5f - yValue) * size.height

                    rotationZ = minuteHandRotation
                    transformOrigin = TransformOrigin(xValue, yValue)
                }
        )
    }
}
