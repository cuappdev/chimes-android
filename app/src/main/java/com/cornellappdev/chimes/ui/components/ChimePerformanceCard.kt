package com.cornellappdev.chimes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cornellappdev.chimes.R
import com.cornellappdev.chimes.ui.theme.Montserrat

@Composable
fun ChimePerformanceCard (timePeriod: String, timeRange: String) {
    ChimeCard(
        paddingValues = PaddingValues(horizontal = 22.dp, vertical = 11.dp),
        modifier = Modifier
            .width(351.dp)
            .height(51.dp)
    ){
        Row (
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = timePeriod,
                fontSize = 18.sp,
                fontFamily = Montserrat,
                modifier = Modifier.width(75.dp)
            )
            Box(modifier = Modifier.fillMaxHeight().width(0.5.dp).background(Color(0x4D635858)))
            Text(
                text = timeRange,
                fontFamily = Montserrat,
                fontSize = 18.sp
            )
        }
    }
}