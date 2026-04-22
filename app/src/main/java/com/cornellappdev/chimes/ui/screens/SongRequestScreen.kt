package com.cornellappdev.chimes.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cornellappdev.chimes.R
import com.cornellappdev.chimes.ui.components.HeaderButton
import com.cornellappdev.chimes.ui.theme.Montserrat

private val BG           = Color.White
private val BG_PINK      = Color(0xFFFBD3D3)
private val BG_CONFIRM   = Color(0xFFF9D0D0)
private val WARN_BG      = Color(0xFFF5DEDE)
private val RESULT_BG    = Color(0xFFF5E0E0)
private val TEXT_DARK    = Color(0xFF1A1A1A)
private val TEXT_MID     = Color(0xFF4F4E4E)
private val TEXT_LIGHT   = Color(0xFF9E9E9E)
private val ACCENT       = Color(0xFFC4949A)
private val ALBUM_BG     = Color(0xFF3A5A7A)

private data class SongItem(val title: String, val artist: String)

private val MOCK_RESULTS = List(6) { SongItem("SOS", "SZA") }
private val MOCK_RECENT  = List(3) { SongItem("SOS", "SZA") }

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun SongRequestScreen(onNavigateBack: () -> Unit) {
    var query         by remember { mutableStateOf("") }
    var selectedSong  by remember { mutableStateOf<SongItem?>(null) }
    var confirmed     by remember { mutableStateOf(false) }

    if (confirmed) {
        ConfirmationView(onClose = onNavigateBack)
        return
    }

    Box(modifier = Modifier.fillMaxSize().background(BG)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 96.dp)
        ) {
            // ── Header ──────────────────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 56.dp, start = 20.dp, end = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(
                        text = "Song Request",
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 26.sp,
                        color = TEXT_DARK
                    )
                    Text(
                        text = "Chime in with your own track!",
                        fontFamily = Montserrat,
                        fontSize = 13.sp,
                        color = TEXT_LIGHT
                    )
                }
                HeaderButton(R.drawable.ic_navigation) { onNavigateBack() }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ── Warning banner ───────────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(WARN_BG)
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_info),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "Warning: custom requests may not always be selected for performance!",
                    fontFamily = Montserrat,
                    fontSize = 11.sp,
                    color = Color(0xFF8B4444),
                    lineHeight = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ── Custom Song Request section ──────────────────────────────────
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Text(
                    text = "Custom Song Request",
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = TEXT_DARK
                )
                Text(
                    text = "Add your song to the tower playlist",
                    fontFamily = Montserrat,
                    fontSize = 12.sp,
                    color = TEXT_LIGHT
                )
                Spacer(modifier = Modifier.height(10.dp))

                if (selectedSong != null && query.isEmpty()) {
                    // ── Selected song card ──────────────────────────────────
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(4.dp, RoundedCornerShape(12.dp), spotColor = Color(0x20000000))
                            .background(Color.White, RoundedCornerShape(12.dp))
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        AlbumArt(size = 52)
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = selectedSong!!.title,
                                fontFamily = Montserrat,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp,
                                color = TEXT_DARK
                            )
                            Text(
                                text = selectedSong!!.artist,
                                fontFamily = Montserrat,
                                fontSize = 12.sp,
                                color = TEXT_LIGHT
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE0E0E0))
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) { selectedSong = null },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_close),
                                contentDescription = "Remove",
                                tint = TEXT_MID,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }
                } else {
                    // ── Search input ─────────────────────────────────────────
                    BasicTextField(
                        value = query,
                        onValueChange = { query = it; if (selectedSong != null) selectedSong = null },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(42.dp)
                            .border(1.dp, Color(0xFFE0D0D0), RoundedCornerShape(8.dp))
                            .background(Color(0xFFFAFAFA), RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp),
                        textStyle = LocalTextStyle.current.copy(
                            fontFamily = Montserrat,
                            fontSize = 14.sp,
                            color = TEXT_DARK
                        ),
                        decorationBox = { innerTextField ->
                            Box(contentAlignment = Alignment.CenterStart) {
                                if (query.isEmpty()) {
                                    Text(
                                        text = "Enter a song...",
                                        fontFamily = Montserrat,
                                        fontSize = 14.sp,
                                        color = TEXT_LIGHT
                                    )
                                }
                                innerTextField()
                            }
                        }
                    )

                    // ── Search results ───────────────────────────────────────
                    if (query.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
                        ) {
                            MOCK_RESULTS.forEachIndexed { index, song ->
                                SearchResultRow(
                                    song = song,
                                    selected = selectedSong == song && selectedSong != null,
                                    isLast = index == MOCK_RESULTS.lastIndex,
                                    onClick = {
                                        selectedSong = song
                                        query = ""
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // ── Recently Requested Songs (only when not searching) ───────────
            if (query.isEmpty()) {
                Spacer(modifier = Modifier.height(24.dp))
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Text(
                        text = "Recently Requested Songs",
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                        color = TEXT_DARK
                    )
                    Text(
                        text = "Fresh chimes from the crowd",
                        fontFamily = Montserrat,
                        fontSize = 12.sp,
                        color = TEXT_LIGHT
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        MOCK_RECENT.forEach { song ->
                            RecentSongCard(song)
                        }
                    }
                }
            }
        }

        // ── Request button ───────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            Button(
                onClick = { if (selectedSong != null) confirmed = true },
                enabled = selectedSong != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .shadow(
                        elevation = if (selectedSong != null) 6.dp else 0.dp,
                        shape = RoundedCornerShape(16.dp),
                        spotColor = Color(0x30000000)
                    ),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = TEXT_MID,
                    disabledContainerColor = Color(0xFFF5E8E8),
                    disabledContentColor = ACCENT
                ),
                contentPadding = PaddingValues(0.dp),
                elevation = ButtonDefaults.buttonElevation(0.dp)
            ) {
                Text(
                    text = "Request",
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
            }
        }
    }
}

// ─── Sub-composables ──────────────────────────────────────────────────────────

@Composable
private fun SearchResultRow(song: SongItem, selected: Boolean, isLast: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(RESULT_BG)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            )
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        AlbumArt(size = 44)
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = song.title,
                fontFamily = Montserrat,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                color = TEXT_DARK
            )
            Text(
                text = song.artist,
                fontFamily = Montserrat,
                fontSize = 11.sp,
                color = TEXT_LIGHT
            )
        }
        // Radio button
        Box(
            modifier = Modifier
                .size(20.dp)
                .border(1.5.dp, if (selected) ACCENT else Color(0xFFCCCCCC), CircleShape)
                .then(if (selected) Modifier.background(Color.White, CircleShape) else Modifier),
            contentAlignment = Alignment.Center
        ) {
            if (selected) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(ACCENT, CircleShape)
                )
            }
        }
    }
    if (!isLast) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(Color(0xFFE8D0D0))
        )
    }
}

@Composable
private fun RecentSongCard(song: SongItem) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        AlbumArt(size = 110, radius = 8)
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = song.title,
            fontFamily = Montserrat,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            color = TEXT_DARK
        )
        Text(
            text = song.artist,
            fontFamily = Montserrat,
            fontSize = 11.sp,
            color = TEXT_LIGHT
        )
    }
}

@Composable
private fun AlbumArt(size: Int, radius: Int = 6) {
    Box(
        modifier = Modifier
            .size(size.dp)
            .clip(RoundedCornerShape(radius.dp))
            .background(ALBUM_BG),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_music_note),
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.4f),
            modifier = Modifier.size((size * 0.4f).dp)
        )
    }
}

// ─── Confirmation screen ──────────────────────────────────────────────────────

@RequiresApi(Build.VERSION_CODES.S)
@Composable
private fun ConfirmationView(onClose: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BG_CONFIRM)
    ) {
        // Decorative music notes
        Icon(
            painter = painterResource(R.drawable.ic_music_note),
            contentDescription = null,
            tint = ACCENT,
            modifier = Modifier.size(24.dp).offset(x = 280.dp, y = 120.dp)
        )
        Icon(
            painter = painterResource(R.drawable.ic_music_notes),
            contentDescription = null,
            tint = ACCENT,
            modifier = Modifier.size(32.dp).offset(x = 40.dp, y = 260.dp)
        )
        Icon(
            painter = painterResource(R.drawable.ic_music_note),
            contentDescription = null,
            tint = ACCENT,
            modifier = Modifier.size(20.dp).offset(x = 60.dp, y = 460.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // X button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 56.dp),
                horizontalArrangement = Arrangement.End
            ) {
                HeaderButton(R.drawable.ic_close) { onClose() }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Clock illustration
            Box(
                modifier = Modifier.size(160.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_clock),
                    contentDescription = "Clock",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
                Image(
                    painter = painterResource(R.drawable.ic_hour_hand),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentScale = ContentScale.Fit
                )
                Image(
                    painter = painterResource(R.drawable.ic_minute_hand),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Your Song Has\nBeen Requested!",
                fontFamily = Montserrat,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 30.sp,
                color = TEXT_DARK,
                textAlign = TextAlign.Center,
                lineHeight = 38.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Keep an ear out for your favorite tune",
                fontFamily = Montserrat,
                fontSize = 14.sp,
                color = TEXT_MID,
                textAlign = TextAlign.Center
            )
        }
    }
}
