package com.cornellappdev.chimes.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cornellappdev.chimes.R
import com.cornellappdev.chimes.ui.components.HeaderButton
import com.cornellappdev.chimes.ui.theme.Montserrat

private val BG = Color(0xFFF7F7F7)
private val TEXT_DARK = Color(0xFF1A1A1A)
private val TEXT_MID = Color(0xFF4F4E4E)
private val TEXT_LIGHT = Color(0xFF9E9E9E)
private val CHIP_ACCENT = Color(0xFFD4A8AB)
private val DIVIDER = Color(0xFFE8E8E8)

private enum class NotifFilter {
    ALL, PERFORMANCE, GUESSING, ANNOUNCEMENTS, RSVP
}

private enum class NotifType { PERFORMANCE, GUESSING }

private data class NotifItem(
    val type: NotifType,
    val time: String,
    val isToday: Boolean
)

private val MOCK_NOTIFICATIONS = listOf(
    NotifItem(NotifType.PERFORMANCE, "4:30pm", true),
    NotifItem(NotifType.PERFORMANCE, "4:30pm", true),
    NotifItem(NotifType.PERFORMANCE, "4:30pm", true),
    NotifItem(NotifType.GUESSING,    "4:30pm", false),
    NotifItem(NotifType.GUESSING,    "4:30pm", false),
    NotifItem(NotifType.GUESSING,    "4:30pm", false),
    NotifItem(NotifType.GUESSING,    "4:30pm", false),
)

@OptIn(ExperimentalLayoutApi::class)
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun NotificationsScreen(onNavigateBack: () -> Unit) {
    var selectedFilter by remember { mutableStateOf(NotifFilter.ALL) }
    var filterExpanded by remember { mutableStateOf(false) }
    var quickSettingsOpen by remember { mutableStateOf(false) }

    val filtered = MOCK_NOTIFICATIONS.filter {
        when (selectedFilter) {
            NotifFilter.ALL          -> true
            NotifFilter.PERFORMANCE  -> it.type == NotifType.PERFORMANCE
            NotifFilter.GUESSING     -> it.type == NotifType.GUESSING
            else                     -> true
        }
    }
    val todayItems     = filtered.filter { it.isToday }
    val yesterdayItems = filtered.filter { !it.isToday }

    Box(modifier = Modifier.fillMaxSize().background(BG)) {

        Column(modifier = Modifier.fillMaxSize()) {

            // ── Header ──────────────────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 56.dp, start = 16.dp, end = 16.dp, bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                HeaderButton(R.drawable.ic_back) { onNavigateBack() }
                Text(
                    text = "Notifications",
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = TEXT_DARK
                )
                HeaderButton(R.drawable.ic_bell) { quickSettingsOpen = !quickSettingsOpen }
            }

            // ── Filters ─────────────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                if (!filterExpanded) {
                    // Collapsed: All | Performance | Guessing ⌄
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FilterChip(
                            label = "All",
                            selected = selectedFilter == NotifFilter.ALL,
                            onClick = { selectedFilter = NotifFilter.ALL }
                        )
                        FilterChip(
                            label = "Performance",
                            selected = selectedFilter == NotifFilter.PERFORMANCE,
                            onClick = { selectedFilter = NotifFilter.PERFORMANCE }
                        )
                        FilterChipWithChevron(
                            label = "Guessing",
                            selected = selectedFilter == NotifFilter.GUESSING,
                            expanded = false,
                            onClick = { selectedFilter = NotifFilter.GUESSING },
                            onChevronClick = { filterExpanded = true }
                        )
                    }
                } else {
                    // Expanded: all filters in wrapping row
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(4.dp, RoundedCornerShape(16.dp), spotColor = Color(0x20000000))
                            .background(Color.White, RoundedCornerShape(16.dp))
                            .padding(12.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                FilterChip(
                                    label = "All",
                                    selected = selectedFilter == NotifFilter.ALL,
                                    onClick = { selectedFilter = NotifFilter.ALL }
                                )
                                FilterChip(
                                    label = "Announcements",
                                    selected = selectedFilter == NotifFilter.ANNOUNCEMENTS,
                                    onClick = { selectedFilter = NotifFilter.ANNOUNCEMENTS }
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                // Collapse chevron
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .shadow(2.dp, RoundedCornerShape(50), spotColor = Color(0x20000000))
                                        .background(Color.White, RoundedCornerShape(50))
                                        .clickable(
                                            indication = null,
                                            interactionSource = remember { MutableInteractionSource() }
                                        ) { filterExpanded = false },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_forward),
                                        contentDescription = "Collapse",
                                        tint = TEXT_MID,
                                        modifier = Modifier
                                            .size(14.dp)
                                    )
                                }
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                FilterChip(
                                    label = "Performance",
                                    selected = selectedFilter == NotifFilter.PERFORMANCE,
                                    onClick = { selectedFilter = NotifFilter.PERFORMANCE }
                                )
                                FilterChip(
                                    label = "Guessing",
                                    selected = selectedFilter == NotifFilter.GUESSING,
                                    onClick = { selectedFilter = NotifFilter.GUESSING }
                                )
                            }
                            FilterChip(
                                label = "RSVP",
                                selected = selectedFilter == NotifFilter.RSVP,
                                onClick = { selectedFilter = NotifFilter.RSVP }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ── Notification list ────────────────────────────────────────────
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                if (todayItems.isNotEmpty()) {
                    SectionHeader("Today")
                    Spacer(modifier = Modifier.height(8.dp))
                    todayItems.forEach { NotificationRow(it) }
                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider(color = DIVIDER)
                    Spacer(modifier = Modifier.height(16.dp))
                }
                if (yesterdayItems.isNotEmpty()) {
                    SectionHeader("Yesterday")
                    Spacer(modifier = Modifier.height(8.dp))
                    yesterdayItems.forEach { NotificationRow(it) }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        // ── Quick settings panel ─────────────────────────────────────────────
        AnimatedVisibility(
            visible = quickSettingsOpen,
            modifier = Modifier.align(Alignment.BottomCenter),
            enter = fadeIn(tween(200)) + slideInVertically(tween(250)) { it / 2 },
            exit = fadeOut(tween(150)) + slideOutVertically(tween(200)) { it / 2 }
        ) {
            QuickSettingsPanel(onClose = { quickSettingsOpen = false })
        }
    }
}

// ─── Sub-composables ──────────────────────────────────────────────────────────

@Composable
private fun SectionHeader(label: String) {
    Text(
        text = label,
        fontFamily = Montserrat,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp,
        color = TEXT_DARK
    )
}

@Composable
private fun NotificationRow(item: NotifItem) {
    val iconId = when (item.type) {
        NotifType.PERFORMANCE -> R.drawable.ic_music_notes
        NotifType.GUESSING    -> R.drawable.ic_bar_chart
    }
    val title = when (item.type) {
        NotifType.PERFORMANCE -> "Performance"
        NotifType.GUESSING    -> "Guessing"
    }
    val body = when (item.type) {
        NotifType.PERFORMANCE -> "The show is on \u2014 guess the song now!"
        NotifType.GUESSING    -> "Results are out \u2014 see if you guessed right!"
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            painter = painterResource(iconId),
            contentDescription = title,
            tint = TEXT_LIGHT,
            modifier = Modifier.size(28.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontFamily = Montserrat,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = TEXT_DARK
            )
            Text(
                text = body,
                fontFamily = Montserrat,
                fontSize = 12.sp,
                color = TEXT_LIGHT
            )
        }
        Text(
            text = item.time,
            fontFamily = Montserrat,
            fontSize = 11.sp,
            color = TEXT_LIGHT
        )
    }
}

@Composable
private fun FilterChip(label: String, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .then(
                if (selected) Modifier.background(CHIP_ACCENT)
                else Modifier
                    .background(Color.White)
                    .border(1.dp, Color(0xFFDDDDDD), RoundedCornerShape(50))
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            )
            .padding(horizontal = 14.dp, vertical = 7.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontFamily = Montserrat,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            fontSize = 13.sp,
            color = if (selected) Color.White else TEXT_MID
        )
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
private fun FilterChipWithChevron(
    label: String,
    selected: Boolean,
    expanded: Boolean,
    onClick: () -> Unit,
    onChevronClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .then(
                if (selected) Modifier.background(CHIP_ACCENT)
                else Modifier
                    .background(Color.White)
                    .border(1.dp, Color(0xFFDDDDDD), RoundedCornerShape(50))
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = onClick
                )
                .padding(start = 14.dp, top = 7.dp, bottom = 7.dp, end = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                fontFamily = Montserrat,
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                fontSize = 13.sp,
                color = if (selected) Color.White else TEXT_MID
            )
        }
        Box(
            modifier = Modifier
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = onChevronClick
                )
                .padding(start = 2.dp, end = 10.dp, top = 7.dp, bottom = 7.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_forward),
                contentDescription = "Expand filters",
                tint = if (selected) Color.White else TEXT_MID,
                modifier = Modifier.size(12.dp)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
private fun QuickSettingsPanel(onClose: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(12.dp, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp), spotColor = Color(0x30000000))
            .background(Color.White, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .padding(20.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            // Title row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Quick notification settings",
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = TEXT_DARK
                )
                HeaderButton(R.drawable.ic_close) { onClose() }
            }

            // Pause row
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_bell_slash),
                    contentDescription = "Pause notifications",
                    tint = TEXT_MID,
                    modifier = Modifier.size(22.dp)
                )
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Pause all notifications",
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = TEXT_DARK
                    )
                    Text(
                        text = "You can view all the notifications even if you pause push notifications.",
                        fontFamily = Montserrat,
                        fontSize = 12.sp,
                        color = TEXT_LIGHT,
                        lineHeight = 18.sp
                    )
                }
            }

            // Push settings row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_settings),
                    contentDescription = "Push notification settings",
                    tint = TEXT_MID,
                    modifier = Modifier.size(22.dp)
                )
                Text(
                    text = "Push notification settings",
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = TEXT_DARK
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
