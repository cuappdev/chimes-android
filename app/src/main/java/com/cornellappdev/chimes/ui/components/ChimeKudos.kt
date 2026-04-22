package com.cornellappdev.chimes.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChimeKudos(emoji: String, onClick: () -> Unit = {}) {
    Button(modifier = Modifier
        .shadow(
            elevation = 4.dp,
            shape = CircleShape,
            ambientColor = Color(0x59A86161),
            spotColor = Color(0x59A86161)
        )
        .clip(CircleShape)
        .size(64.dp),
        contentPadding = PaddingValues(0.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            Color(0xFFFEEEEE)),
    ){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = emoji,
                fontSize = 32.sp
            )
        }
    }
}