package com.cornellappdev.chimes.ui.screens

import android.os.Build
import android.view.RoundedCorner
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.util.copy
import androidx.room3.util.copy
import com.cornellappdev.chimes.R
import com.cornellappdev.chimes.ui.components.ChimeCard
import com.cornellappdev.chimes.ui.components.ChimeKudos
import com.cornellappdev.chimes.ui.components.ChimePerformanceCard
import com.cornellappdev.chimes.ui.components.HeaderButton
import com.cornellappdev.chimes.ui.components.Slope

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun HomeScreen () {
    var song by remember {mutableStateOf("")}
    Box (modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .background(Color(0xFFFBD3D3))) {
        Slope()
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 45.dp, start = 21.dp, end = 21.dp),
            horizontalAlignment =  Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(28.dp))
        {
            Spacer(modifier = Modifier.height(50.dp))
            Row(
                modifier = Modifier
                    .height(50.dp)
                    .width(348.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Hi, Arielle ☀\uFE0F",
                    color = Color(0xFF4F4E4E),
                    modifier = Modifier.fillMaxHeight(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 36.sp
                )
                HeaderButton (R.drawable.ic_navigation) {  }
            }
            Spacer(modifier = Modifier.height(305.dp))
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(0.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .width(345.dp)
                        .padding(top = 0.dp),
                ){
                    ChimeCard(
                        paddingValues = PaddingValues(start = 20.dp, end = 20.dp, top = 18.dp, bottom = 12.dp),
                        modifier = Modifier
                            .width(351.dp)
                            .height(215.dp)

                    ){
                        Column (
                            verticalArrangement = Arrangement.spacedBy(18.dp)
                        ){
                            Column (
                                modifier = Modifier.height(50.dp),
                                verticalArrangement = Arrangement.spacedBy(7.dp)
                            ) {
                                Row(
                                    modifier = Modifier.height(25.dp),
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ){
                                    Text(
                                        text = "\uD83C\uDF89",
                                        modifier = Modifier.fillMaxHeight(),
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 18.sp
                                    )
                                    Text(
                                        text = "Time to chime in!",
                                        modifier = Modifier.fillMaxHeight(),
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 18.sp
                                    )
                                }
                                Text(
                                    text = "Guess the song that's currently playing",
                                    modifier = Modifier.fillMaxHeight(),
                                    fontSize = 15.sp,
                                )
                            }
                            Column (
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                horizontalAlignment = Alignment.End
                            ){
                                Column (
                                    modifier = Modifier.height(64.dp),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    TextField(
                                        value = song,
                                        onValueChange = { song = it },
                                        singleLine = true,
                                        modifier = Modifier
                                            .width(306.dp)
                                            .height(41.dp)
                                            .border(1.dp, Color(0xFFEFD3D3), shape = RoundedCornerShape(8.dp))
                                            .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(8.dp)),
                                        colors = TextFieldDefaults.colors().copy(
                                            focusedContainerColor = Color(0xFFFFFFFF),
                                            unfocusedContainerColor = Color(0xFFFFFFFF),
                                            focusedIndicatorColor = Color.Transparent,
                                            unfocusedIndicatorColor = Color.Transparent,
                                            disabledIndicatorColor = Color.Transparent
                                        ),
                                        shape = RoundedCornerShape(8.dp),)
                                    Text(
                                        text = "+ guess more",
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
                                       containerColor = Color(0xFFFFF3F3)),
                                    modifier = Modifier
                                        .width(87.dp)
                                        .height(33.dp)
                                ){
                                    Text(
                                        text = "submit",
                                        fontSize = 14.sp,
                                        color = Color(0xFF655454)
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(36.dp))
                    Column (
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ){
                        Text(
                            text = "Send Kudos",
                            fontSize = 18.sp
                        )
                        Row (
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(64.dp)
                                .padding(horizontal = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        )
                        {
                            ChimeKudos("⭐")
                            ChimeKudos("💖")
                            ChimeKudos("👍")
                            ChimeKudos("👏")
                        }
                    }
                    Spacer(modifier = Modifier.height(28.dp))
                    Column (
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Today's Performance",
                            fontSize = 18.sp
                        )
                        Column (
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ){
                            ChimePerformanceCard("Morning", "7:45 AM - 8:00 AM")
                            ChimePerformanceCard("Noon", "1:10 PM - 1:25 PM")
                            ChimePerformanceCard("Evening", "6:00 PM - 6:15 PM")
                        }
                    }
                }
            }
        }
    }
}