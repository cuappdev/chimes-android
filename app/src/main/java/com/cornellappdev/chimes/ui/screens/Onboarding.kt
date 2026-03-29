package com.cornellappdev.chimes.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cornellappdev.chimes.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val ChimesRed = Color(0xFFCC5555)
private val VeryLightPink = Color(0xFFFEF7F7)
private val VeryLightGray = Color(0xFFBCB2B2)
private var onLoginClick: () -> Unit = {}

@Composable
fun Onboarding(
    onLoginButtonClick: () -> Unit = {}
) {
    onLoginClick = onLoginButtonClick
    val bgAlpha = remember { Animatable(1f) }
    val whiteAlpha = remember { Animatable(0f) }
    val logoAlpha = remember { Animatable(0f) }
    val logoScale = remember { Animatable(1f) }
    val chimesAlpha = remember { Animatable(0f) }
    val headerOffsetY = remember { Animatable(0f) }
    val loginAlpha = remember { Animatable(0f) }
    val loginOffsetY = remember { Animatable(300f) } // Starts 300dp below
    var handsSpinStarted by remember { mutableStateOf(false) }
    val minuteHandRotation: Float
    val hourHandRotation: Float
    if (handsSpinStarted) {
        val handsRotationTransition = rememberInfiniteTransition(label = "handsRotationTransition")
        val minuteRotation by handsRotationTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 2_000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "minuteHandRotation"
        )
        val hourRotation by handsRotationTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 10_000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "hourHandRotation"
        )
        minuteHandRotation = minuteRotation
        hourHandRotation = hourRotation
    } else {
        minuteHandRotation = 0f
        hourHandRotation = 0f
    }

    LaunchedEffect(Unit) {
        delay(300)

        bgAlpha.animateTo(0f, tween(300, easing = LinearEasing))
        whiteAlpha.animateTo(1f, tween(300, easing = LinearEasing))
        logoAlpha.animateTo(1f, tween(300, easing = LinearEasing))

        delay(300)

        logoScale.animateTo(0.55f, tween(300, easing = FastOutSlowInEasing))
        handsSpinStarted = true

        delay(300)
        chimesAlpha.animateTo(1f, tween(300, easing = LinearEasing))

        delay(300)
        
        headerOffsetY.animateTo(-100f, tween(300, easing = FastOutSlowInEasing))
        
        delay(300)
        
        launch {
            loginOffsetY.animateTo(0f, tween(300, easing = FastOutSlowInEasing)) //slide
        }
        loginAlpha.animateTo(1f, tween(300, easing = LinearEasing)) //fade
    }

    Box(modifier = Modifier.fillMaxSize().background(VeryLightPink)) {

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
                minuteHandRotation = minuteHandRotation,
                hourHandRotation = hourHandRotation
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "chimes",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = ChimesRed
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
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                ) {
                    Spacer(modifier = Modifier.height(64.dp))

                    LoginButton(
                        text = "Log-in with Google",
                        iconRes = R.drawable.ic_google_g,
                        contentDescription = "Google logo"
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    LoginButton(
                        text = "Log-in with Cornell netID",
                        iconRes = R.drawable.ic_cornell_logo,
                        contentDescription = "Cornell logo"
                    )

                    Spacer(modifier = Modifier.height(45.dp))

                    HorizontalDivider(
                        color = VeryLightGray,
                        thickness = 1.dp,
                        modifier = Modifier
                            .width(200.dp)
                    )

                    Spacer(modifier = Modifier.height(45.dp))


                    TextButton(
                        onClick = onLoginClick,
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

@Composable
private fun LoginButton(
    text: String,
    iconRes: Int,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    val curve = 50.dp
    Button(
        modifier = modifier
            .height(54.dp)
            .border(
                width = 1.dp,
                color = VeryLightGray,
                shape = RoundedCornerShape(curve)
            )
            .fillMaxWidth(),
        onClick = onLoginClick,
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
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = contentDescription,
                modifier = Modifier.size(32.dp),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                ),
                fontSize = 14.sp
            )
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
                    val pivotFraction = 11f / 30f

                    translationX = (0.5f - pivotFraction) * size.width
                    translationY = (0.5f - pivotFraction) * size.height
                    
                    rotationZ = hourHandRotation
                    transformOrigin = TransformOrigin(pivotFraction, pivotFraction)
                }
        )

        Image(
            painter = painterResource(id = R.drawable.ic_minute_hand),
            contentDescription = "Minute hand",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(minuteHandWidth, minuteHandHeight)
                .graphicsLayer {
                    val pivotFractionX = 2.5f/25f
                    val pivotFractionY = 16.5f/25f

                    translationX = (0.5f - pivotFractionX) * size.width
                    translationY = (0.5f - pivotFractionY) * size.height

                    rotationZ = minuteHandRotation
                    transformOrigin = TransformOrigin(pivotFractionX, pivotFractionY)
                }
        )
    }
}

@Preview
@Composable
fun OnboardingPreview() {
    Onboarding()
}
