package com.cornellappdev.chimes.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cornellappdev.chimes.GlobalState
import com.cornellappdev.chimes.R
import com.cornellappdev.chimes.ui.components.HeaderButton

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun HomeScreen () {
    Box (modifier = Modifier
    .fillMaxSize()) {
        Column (modifier = Modifier
            .fillMaxSize()
            .background(brush =
                Brush.linearGradient(colors =
                    listOf(Color(0xFFD3EEFF), Color(0xFFF6FAFF))))
            .padding(top = 68.dp),
            horizontalAlignment =  Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(28.dp))
        {
            Row(
                modifier = Modifier.height(50.dp),
                horizontalArrangement = Arrangement.spacedBy(40.dp)
            ) {
                Text(
                    text = "Hi, ${GlobalState.userName.split(" ").firstOrNull() ?: ""} ☀️",
                    color = Color(0xFF4F4E4E),
                    modifier = Modifier.fillMaxHeight(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 36.sp
                )
                Row (
                    horizontalArrangement = Arrangement.spacedBy(17.dp)
                ){
                    HeaderButton (R.drawable.ic_music_note) {  }
                    HeaderButton (R.drawable.ic_rsvp) {  }
                }
            }
        }
        Image(
            painter = painterResource(R.drawable.ic_slope),
            contentDescription = "",
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .fillMaxWidth()
        )
    }
}