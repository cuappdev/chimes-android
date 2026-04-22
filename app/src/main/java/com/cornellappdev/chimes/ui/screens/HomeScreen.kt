package com.cornellappdev.chimes.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cornellappdev.chimes.R
import com.cornellappdev.chimes.ui.components.ChimeCard
import com.cornellappdev.chimes.ui.components.ChimeKudos
import com.cornellappdev.chimes.ui.components.ChimePerformanceCard
import com.cornellappdev.chimes.ui.components.HeaderButton
import com.cornellappdev.chimes.ui.components.Slope
import com.cornellappdev.chimes.ui.theme.Montserrat

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun HomeScreen(
    onNavigateToRsvp: () -> Unit = {},
    onNavigateToNotifications: () -> Unit = {},
    onNavigateToSongRequest: () -> Unit = {}
) {
    var song by remember { mutableStateOf("") }
    var menuExpanded by remember { mutableStateOf(false) }

    val blurRadius by animateDpAsState(
        targetValue = if (menuExpanded) 12.dp else 0.dp,
        animationSpec = tween(250),
        label = "blur"
    )

    Box(modifier = Modifier.fillMaxSize()) {

        // Scrollable content — blurred when menu open
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color(0xFFFBD3D3))
                .blur(blurRadius)
                .then(
                    if (menuExpanded) Modifier.clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { menuExpanded = false } else Modifier
                )
        ) {
            Slope()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 45.dp, start = 21.dp, end = 21.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(28.dp)
            ) {
                // Space reserved for the sharp header overlay
                Spacer(modifier = Modifier.height(100.dp))
                Spacer(modifier = Modifier.height(305.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(0.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(modifier = Modifier.width(345.dp)) {
                        ChimeCard(
                            paddingValues = PaddingValues(start = 20.dp, end = 20.dp, top = 18.dp, bottom = 12.dp),
                            modifier = Modifier
                                .width(351.dp)
                                .height(215.dp)
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(18.dp)) {
                                Column(
                                    modifier = Modifier.height(50.dp),
                                    verticalArrangement = Arrangement.spacedBy(7.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.height(25.dp),
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Text(
                                            text = "\uD83C\uDF89",
                                            modifier = Modifier.fillMaxHeight(),
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 18.sp
                                        )
                                        Text(
                                            text = "Time to chime in!",
                                            modifier = Modifier.fillMaxHeight(),
                                            fontFamily = Montserrat,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 18.sp
                                        )
                                    }
                                    Text(
                                        text = "Guess the song that's currently playing",
                                        fontFamily = Montserrat,
                                        modifier = Modifier.fillMaxHeight(),
                                        fontSize = 15.sp,
                                    )
                                }
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(12.dp),
                                    horizontalAlignment = Alignment.End
                                ) {
                                    Column(
                                        modifier = Modifier.height(64.dp),
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        BasicTextField(
                                            value = song,
                                            onValueChange = { song = it },
                                            singleLine = true,
                                            modifier = Modifier
                                                .width(306.dp)
                                                .height(41.dp)
                                                .border(1.dp, Color(0xFFEFD3D3), shape = RoundedCornerShape(8.dp))
                                                .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(8.dp))
                                                .padding(horizontal = 12.dp),
                                            textStyle = LocalTextStyle.current.copy(
                                                fontFamily = Montserrat,
                                                fontSize = 14.sp,
                                                color = Color(0xFF4F4E4E)
                                            ),
                                            decorationBox = { innerTextField ->
                                                Box(contentAlignment = Alignment.CenterStart) {
                                                    if (song.isEmpty()) {
                                                        Text(
                                                            text = "enter a song...",
                                                            fontFamily = Montserrat,
                                                            fontSize = 14.sp,
                                                            color = Color(0xFFAAAAAA)
                                                        )
                                                    }
                                                    innerTextField()
                                                }
                                            }
                                        )
                                        Text(
                                            text = "+ guess more",
                                            fontFamily = Montserrat,
                                            modifier = Modifier.fillMaxHeight(),
                                            fontSize = 12.sp,
                                        )
                                    }
                                    Button(
                                        onClick = {},
                                        shape = RoundedCornerShape(8.dp),
                                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                                        border = BorderStroke((0.5f).dp, Color(0xFFE6BFBF)),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(0xFFFFF3F3)
                                        ),
                                        modifier = Modifier
                                            .width(87.dp)
                                            .height(33.dp)
                                    ) {
                                        Text(
                                            text = "submit!",
                                            fontFamily = Montserrat,
                                            fontSize = 14.sp,
                                            color = Color(0xFF655454)
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(36.dp))
                        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            Text(
                                text = "Send Kudos! \uD83D\uDC4F",
                                fontFamily = Montserrat,
                                fontSize = 18.sp
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(64.dp)
                                    .padding(horizontal = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                ChimeKudos("⭐")
                                ChimeKudos("💖")
                                ChimeKudos("👍")
                                ChimeKudos("👏")
                            }
                        }
                        Spacer(modifier = Modifier.height(28.dp))
                        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            Text(
                                text = "Today's Performance",
                                fontFamily = Montserrat,
                                fontSize = 18.sp
                            )
                            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                ChimePerformanceCard("Morning", "7:45 AM - 8:00 AM")
                                ChimePerformanceCard("Noon", "1:10 PM - 1:25 PM")
                                ChimePerformanceCard("Evening", "6:00 PM - 6:15 PM")
                            }
                        }
                    }
                }
            }
        }

        // Sharp header overlay — never blurred
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp, start = 21.dp, end = 21.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = "Hi, Arielle ☀\uFE0F",
                color = Color(0xFF4F4E4E),
                fontFamily = Montserrat,
                fontWeight = FontWeight.SemiBold,
                fontSize = 36.sp
            )

            NavMenuToggle(
                expanded = menuExpanded,
                onOpen = { menuExpanded = true },
                onClose = { menuExpanded = false },
                onNavigateToRsvp = { menuExpanded = false; onNavigateToRsvp() },
                onNavigateToNotifications = { menuExpanded = false; onNavigateToNotifications() },
                onNavigateToSongRequest = { menuExpanded = false; onNavigateToSongRequest() }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
private fun NavMenuToggle(
    expanded: Boolean,
    onOpen: () -> Unit,
    onClose: () -> Unit,
    onNavigateToRsvp: () -> Unit = {},
    onNavigateToNotifications: () -> Unit = {},
    onNavigateToSongRequest: () -> Unit = {}
) {
    Box(contentAlignment = Alignment.TopEnd) {
        AnimatedVisibility(
            visible = !expanded,
            enter = scaleIn(transformOrigin = TransformOrigin(1f, 0f), animationSpec = tween(150)) + fadeIn(tween(150)),
            exit = scaleOut(transformOrigin = TransformOrigin(1f, 0f), animationSpec = tween(100)) + fadeOut(tween(100))
        ) {
            HeaderButton(R.drawable.ic_navigation) { onOpen() }
        }
        AnimatedVisibility(
            visible = expanded,
            enter = scaleIn(transformOrigin = TransformOrigin(1f, 0f), animationSpec = tween(200)) + fadeIn(tween(200)),
            exit = scaleOut(transformOrigin = TransformOrigin(1f, 0f), animationSpec = tween(150)) + fadeOut(tween(150))
        ) {
            Column(
                modifier = Modifier
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(50.dp),
                        ambientColor = Color(0x40A86161),
                        spotColor = Color(0x40A86161)
                    )
                    .background(Color.White, shape = RoundedCornerShape(50.dp))
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NavMenuItem(iconId = R.drawable.ic_rsvp, label = "RSVP", showLabel = true) { onNavigateToRsvp() }
                NavMenuItem(iconId = R.drawable.ic_music_notes, label = "Music") { onNavigateToSongRequest() }
                NavMenuItem(iconId = R.drawable.ic_bell, label = "Bell") { onNavigateToNotifications() }
                NavMenuItem(iconId = R.drawable.ic_settings, label = "Settings") { onClose() }
            }
        }
    }
}

@Composable
private fun NavMenuItem(iconId: Int, label: String, showLabel: Boolean = false, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .size(44.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = label,
            tint = Color(0xFF757575),
            modifier = Modifier.size(if (showLabel) 20.dp else 24.dp)
        )
        if (showLabel) {
            Text(
                text = label,
                fontFamily = Montserrat,
                fontSize = 9.sp,
                color = Color(0xFF757575)
            )
        }
    }
}
