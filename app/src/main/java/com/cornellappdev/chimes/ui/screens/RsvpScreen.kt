package com.cornellappdev.chimes.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.Brush
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
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

private val BG_BLUE = Color(0xFFD3EEFF)
private val BG_BLUE_LIGHT = Color(0xFFF0F8FF)
private val TEXT_DARK = Color(0xFF2C2C2C)
private val TEXT_MID = Color(0xFF4F4E4E)
private val TEXT_LIGHT = Color(0xFF9E9E9E)
private val ACCENT = Color(0xFFC4949A)
private val ACCENT_LIGHT = Color(0xFFE8D5D5)
private val SUNDAY_COLOR = Color(0xFFD97070)
private val CARD_BG = Color.White

private enum class RsvpStep { CALENDAR, FORM, CONFIRMATION }

private data class TimeSlot(val label: String, val time: String, val amPm: String, val iconId: Int)

private val TIME_SLOTS = listOf(
    TimeSlot("Morning", "7:45 - 8:00", "AM", R.drawable.ic_sunrise),
    TimeSlot("Afternoon", "1:10 - 1:25", "PM", R.drawable.ic_sun),
    TimeSlot("Evening", "6:00 - 6:15", "PM", R.drawable.ic_cloud_weather)
)

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun RsvpScreen(onNavigateHome: () -> Unit) {
    var step by remember { mutableStateOf(RsvpStep.CALENDAR) }
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedSlot by remember { mutableStateOf<TimeSlot?>(null) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var attendees by remember { mutableStateOf(1) }
    var certChecked by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.linearGradient(listOf(BG_BLUE, BG_BLUE_LIGHT)))
    ) {
        when (step) {
            RsvpStep.CALENDAR -> CalendarStep(
                currentMonth = currentMonth,
                selectedDate = selectedDate,
                selectedSlot = selectedSlot,
                onMonthChange = { currentMonth = it },
                onDateSelect = { selectedDate = it; selectedSlot = null },
                onSlotSelect = { selectedSlot = it },
                onContinue = { step = RsvpStep.FORM },
                onNavigateHome = onNavigateHome
            )
            RsvpStep.FORM -> FormStep(
                selectedDate = selectedDate,
                selectedSlot = selectedSlot,
                name = name,
                email = email,
                attendees = attendees,
                certChecked = certChecked,
                onNameChange = { name = it },
                onEmailChange = { email = it },
                onAttendeesChange = { attendees = it },
                onCertChange = { certChecked = it },
                onBack = { step = RsvpStep.CALENDAR },
                onNavigateHome = onNavigateHome,
                onConfirm = { step = RsvpStep.CONFIRMATION }
            )
            RsvpStep.CONFIRMATION -> ConfirmationStep(
                selectedDate = selectedDate,
                selectedSlot = selectedSlot,
                attendees = attendees,
                onClose = onNavigateHome,
                onModify = { step = RsvpStep.CALENDAR }
            )
        }
    }
}

// ─── Step 1: Calendar ────────────────────────────────────────────────────────

@RequiresApi(Build.VERSION_CODES.S)
@Composable
private fun CalendarStep(
    currentMonth: YearMonth,
    selectedDate: LocalDate?,
    selectedSlot: TimeSlot?,
    onMonthChange: (YearMonth) -> Unit,
    onDateSelect: (LocalDate) -> Unit,
    onSlotSelect: (TimeSlot) -> Unit,
    onContinue: () -> Unit,
    onNavigateHome: () -> Unit
) {
    val canContinue = selectedDate != null && selectedSlot != null

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(start = 20.dp, end = 20.dp, top = 56.dp, bottom = 96.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                HeaderButton(R.drawable.ic_home) { onNavigateHome() }
            }
            Spacer(modifier = Modifier.height(16.dp))
            StepProgressBar(activeStep = 1)
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "RSVP",
                fontFamily = Montserrat,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 40.sp,
                color = TEXT_DARK
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Don't miss a note \u2014 tell us you're coming \uD83C\uDFB5*\u00B0",
                fontFamily = Montserrat,
                fontSize = 13.sp,
                color = TEXT_MID
            )
            Spacer(modifier = Modifier.height(20.dp))
            CalendarCard(
                currentMonth = currentMonth,
                selectedDate = selectedDate,
                onMonthChange = onMonthChange,
                onDateSelect = onDateSelect
            )
            if (selectedDate != null) {
                Spacer(modifier = Modifier.height(20.dp))
                val dateLabel = selectedDate.format(DateTimeFormatter.ofPattern("MMMM d", Locale.US))
                Text(
                    text = "Available times for $dateLabel",
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = TEXT_DARK
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    TIME_SLOTS.forEach { slot ->
                        TimeSlotCard(
                            slot = slot,
                            selected = slot == selectedSlot,
                            modifier = Modifier.weight(1f),
                            onClick = { onSlotSelect(slot) }
                        )
                    }
                }
            }
        }

        BottomActionButton(
            text = "Continue",
            enabled = canContinue,
            modifier = Modifier.align(Alignment.BottomCenter),
            onClick = onContinue
        )
    }
}

// ─── Step 2: Form ─────────────────────────────────────────────────────────────

@RequiresApi(Build.VERSION_CODES.S)
@Composable
private fun FormStep(
    selectedDate: LocalDate?,
    selectedSlot: TimeSlot?,
    name: String,
    email: String,
    attendees: Int,
    certChecked: Boolean,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onAttendeesChange: (Int) -> Unit,
    onCertChange: (Boolean) -> Unit,
    onBack: () -> Unit,
    onNavigateHome: () -> Unit,
    onConfirm: () -> Unit
) {
    val canConfirm = name.isNotBlank() && email.isNotBlank() && certChecked
    val dateTitle = selectedDate?.format(DateTimeFormatter.ofPattern("EEE, MMM d", Locale.US)) ?: ""
    val timeTitle = selectedSlot?.let { "${it.time} ${it.amPm}" } ?: ""

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(start = 20.dp, end = 20.dp, top = 56.dp, bottom = 96.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                HeaderButton(R.drawable.ic_back) { onBack() }
                HeaderButton(R.drawable.ic_home) { onNavigateHome() }
            }
            Spacer(modifier = Modifier.height(16.dp))
            StepProgressBar(activeStep = 2)
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "$dateTitle \uD83C\uDFB5*\u00B0",
                fontFamily = Montserrat,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 34.sp,
                color = TEXT_DARK
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = timeTitle,
                fontFamily = Montserrat,
                fontSize = 14.sp,
                color = TEXT_MID
            )
            Spacer(modifier = Modifier.height(20.dp))
            // Form card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(16.dp), spotColor = Color(0x20000000))
                    .background(CARD_BG, RoundedCornerShape(16.dp))
                    .padding(20.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    FormField(label = "Name", value = name, placeholder = "John Doe", onValueChange = onNameChange)
                    FormField(label = "Email", value = email, placeholder = "netid@cornell.edu", onValueChange = onEmailChange)
                    AttendeesField(attendees = attendees, onAttendeesChange = onAttendeesChange)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Arrive at the base of the tower (near Olin Library) 10\u201315 minutes before the scheduled time.\n\nStay seated in the designated area during the performance at all times.",
                        fontFamily = Montserrat,
                        fontSize = 13.sp,
                        color = TEXT_MID,
                        lineHeight = 20.sp
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Checkbox(
                            checked = certChecked,
                            onCheckedChange = onCertChange,
                            colors = CheckboxDefaults.colors(
                                checkedColor = ACCENT,
                                uncheckedColor = Color(0xFFBBBBBB)
                            ),
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "I certify that I have read the expectations.",
                            fontFamily = Montserrat,
                            fontSize = 13.sp,
                            color = TEXT_MID
                        )
                    }
                }
            }
        }

        BottomActionButton(
            text = "Confirm RSVP",
            enabled = canConfirm,
            modifier = Modifier.align(Alignment.BottomCenter),
            onClick = onConfirm
        )
    }
}

// ─── Step 3: Confirmation ─────────────────────────────────────────────────────

@RequiresApi(Build.VERSION_CODES.S)
@Composable
private fun ConfirmationStep(
    selectedDate: LocalDate?,
    selectedSlot: TimeSlot?,
    attendees: Int,
    onClose: () -> Unit,
    onModify: () -> Unit
) {
    val dateLabel = selectedDate?.format(DateTimeFormatter.ofPattern("EEE, MMM d", Locale.US)) ?: ""
    val timeLabel = selectedSlot?.let { "${it.time} ${it.amPm}" } ?: ""

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(start = 20.dp, end = 20.dp, top = 56.dp, bottom = 96.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                HeaderButton(R.drawable.ic_close) { onClose() }
            }

            // Tower illustration + music notes
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_music_note),
                    contentDescription = null,
                    modifier = Modifier
                        .size(28.dp)
                        .offset(x = 20.dp, y = 60.dp)
                )
                Image(
                    painter = painterResource(R.drawable.ic_music_notes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(36.dp)
                        .offset(x = 50.dp, y = 20.dp)
                )
                Image(
                    painter = painterResource(R.drawable.ic_clocktower_day),
                    contentDescription = "McGraw Tower",
                    modifier = Modifier
                        .height(180.dp)
                        .width(130.dp)
                        .align(Alignment.CenterEnd),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "CU soon\nArielle!",
                fontFamily = Montserrat,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 38.sp,
                color = TEXT_DARK,
                lineHeight = 46.sp
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Details card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(16.dp), spotColor = Color(0x20000000))
                    .background(CARD_BG, RoundedCornerShape(16.dp))
                    .padding(20.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    ConfirmationDetailRow(
                        iconId = R.drawable.ic_pin,
                        primary = "McGraw Tower",
                        secondary = "Tower Rd, Ithaca, NY 14850"
                    )
                    ConfirmationDetailRow(
                        iconId = R.drawable.ic_people,
                        primary = "$attendees ${if (attendees == 1) "Attendee" else "Attendees"}"
                    )
                    ConfirmationDetailRow(
                        iconId = R.drawable.ic_calendar_small,
                        primary = dateLabel,
                        secondary = timeLabel
                    )
                    HorizontalDivider(color = Color(0xFFEEEEEE))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { /* open Google Calendar */ }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_google_g),
                            contentDescription = "Google Calendar",
                            modifier = Modifier.size(22.dp),
                            tint = Color.Unspecified
                        )
                        Text(
                            text = "Add to Google Calendar",
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            color = Color(0xFF4285F4)
                        )
                    }
                }
            }
        }

        BottomActionButton(
            text = "Modify Reservation",
            enabled = true,
            modifier = Modifier.align(Alignment.BottomCenter),
            onClick = onModify
        )
    }
}

// ─── Shared components ────────────────────────────────────────────────────────

@Composable
private fun StepProgressBar(activeStep: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(2) { index ->
            val isActive = index + 1 == activeStep
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(3.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(if (isActive) ACCENT else ACCENT_LIGHT)
            )
        }
    }
}

@Composable
private fun CalendarCard(
    currentMonth: YearMonth,
    selectedDate: LocalDate?,
    onMonthChange: (YearMonth) -> Unit,
    onDateSelect: (LocalDate) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(16.dp), spotColor = Color(0x20000000))
            .background(CARD_BG, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            // Month navigation header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = currentMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale.US)),
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = TEXT_DARK
                )
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    MonthNavButton(iconId = R.drawable.ic_back) { onMonthChange(currentMonth.minusMonths(1)) }
                    MonthNavButton(iconId = R.drawable.ic_forward) {
                        onMonthChange(currentMonth.plusMonths(1))
                    }
                }
            }

            // Day-of-week headers
            val dayHeaders = listOf("M", "T", "W", "Th", "F", "S", "Su")
            Row(modifier = Modifier.fillMaxWidth()) {
                dayHeaders.forEach { day ->
                    Text(
                        text = day,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        color = TEXT_LIGHT
                    )
                }
            }

            CalendarGrid(
                yearMonth = currentMonth,
                selectedDate = selectedDate,
                today = LocalDate.now(),
                onDateSelect = onDateSelect
            )
        }
    }
}

@Composable
private fun CalendarGrid(
    yearMonth: YearMonth,
    selectedDate: LocalDate?,
    today: LocalDate,
    onDateSelect: (LocalDate) -> Unit
) {
    val firstDay = yearMonth.atDay(1)
    // Monday-first: Mon=1..Sun=7 → offset 0..6
    val offset = firstDay.dayOfWeek.value - 1
    val daysInMonth = yearMonth.lengthOfMonth()
    val totalCells = offset + daysInMonth
    val rows = (totalCells + 6) / 7

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        for (row in 0 until rows) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (col in 0 until 7) {
                    val dayNum = row * 7 + col - offset + 1
                    if (dayNum < 1 || dayNum > daysInMonth) {
                        Spacer(modifier = Modifier.weight(1f))
                    } else {
                        val date = yearMonth.atDay(dayNum)
                        val isSunday = date.dayOfWeek == DayOfWeek.SUNDAY
                        val isSelected = date == selectedDate
                        val isToday = date == today

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(36.dp)
                                .then(
                                    if (isSelected) Modifier
                                        .clip(CircleShape)
                                        .background(ACCENT)
                                    else if (isToday) Modifier
                                        .clip(CircleShape)
                                        .border(1.5.dp, ACCENT, CircleShape)
                                    else Modifier
                                )
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) { onDateSelect(date) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = dayNum.toString(),
                                fontFamily = Montserrat,
                                fontSize = 13.sp,
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                color = when {
                                    isSelected -> Color.White
                                    isSunday -> SUNDAY_COLOR
                                    else -> TEXT_DARK
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
private fun MonthNavButton(iconId: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .shadow(2.dp, CircleShape, spotColor = Color(0x20A86161))
            .background(Color.White, CircleShape)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(iconId),
            contentDescription = null,
            tint = TEXT_MID,
            modifier = Modifier.size(14.dp)
        )
    }
}

@Composable
private fun TimeSlotCard(slot: TimeSlot, selected: Boolean, modifier: Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .shadow(if (selected) 4.dp else 2.dp, RoundedCornerShape(12.dp), spotColor = Color(0x20000000))
            .background(CARD_BG, RoundedCornerShape(12.dp))
            .border(
                width = if (selected) 1.5.dp else 0.5.dp,
                color = if (selected) ACCENT else Color(0xFFEEEEEE),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            )
            .padding(vertical = 12.dp, horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(slot.iconId),
                contentDescription = slot.label,
                tint = if (selected) ACCENT else TEXT_LIGHT,
                modifier = Modifier.size(22.dp)
            )
            Text(
                text = slot.label,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp,
                color = if (selected) ACCENT else TEXT_DARK
            )
            Text(
                text = slot.time,
                fontFamily = Montserrat,
                fontSize = 10.sp,
                color = TEXT_LIGHT,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun FormField(label: String, value: String, placeholder: String, onValueChange: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Row {
            Text(
                text = label,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = TEXT_DARK
            )
            Text(text = "*", color = ACCENT, fontSize = 14.sp, fontFamily = Montserrat)
        }
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .border(1.dp, Color(0xFFDDDDDD), RoundedCornerShape(8.dp))
                .background(Color(0xFFFAFAFA), RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp),
            textStyle = LocalTextStyle.current.copy(
                fontFamily = Montserrat,
                fontSize = 14.sp,
                color = TEXT_DARK
            ),
            decorationBox = { innerTextField ->
                Box(contentAlignment = Alignment.CenterStart) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            fontFamily = Montserrat,
                            fontSize = 14.sp,
                            color = TEXT_LIGHT
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}

@Composable
private fun AttendeesField(attendees: Int, onAttendeesChange: (Int) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Text(
                text = "Attendees",
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = TEXT_DARK
            )
            Text(text = "*", color = ACCENT, fontSize = 14.sp, fontFamily = Montserrat)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            StepperButton(label = "\u2212") { if (attendees > 1) onAttendeesChange(attendees - 1) }
            Box(
                modifier = Modifier
                    .width(44.dp)
                    .height(36.dp)
                    .border(1.dp, Color(0xFFDDDDDD)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = attendees.toString(),
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    color = TEXT_DARK
                )
            }
            StepperButton(label = "+") { onAttendeesChange(attendees + 1) }
        }
    }
}

@Composable
private fun StepperButton(label: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .border(1.dp, Color(0xFFDDDDDD), RoundedCornerShape(4.dp))
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontFamily = Montserrat,
            fontSize = 18.sp,
            color = TEXT_MID
        )
    }
}

@Composable
private fun ConfirmationDetailRow(iconId: Int, primary: String, secondary: String? = null) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(iconId),
            contentDescription = null,
            tint = TEXT_MID,
            modifier = Modifier.size(20.dp)
        )
        Column {
            Text(
                text = primary,
                fontFamily = Montserrat,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = TEXT_DARK
            )
            if (secondary != null) {
                Text(
                    text = secondary,
                    fontFamily = Montserrat,
                    fontSize = 12.sp,
                    color = TEXT_LIGHT
                )
            }
        }
    }
}

@Composable
private fun BottomActionButton(text: String, enabled: Boolean, modifier: Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
        Button(
            onClick = onClick,
            enabled = enabled,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .shadow(
                    elevation = if (enabled) 6.dp else 0.dp,
                    shape = RoundedCornerShape(16.dp),
                    spotColor = Color(0x30000000)
                ),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = TEXT_MID,
                disabledContainerColor = Color(0xFFF0F0F0),
                disabledContentColor = Color(0xFFBBBBBB)
            ),
            contentPadding = PaddingValues(0.dp),
            elevation = ButtonDefaults.buttonElevation(0.dp)
        ) {
            Text(
                text = text,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
        }
    }
}
