package com.cornellappdev.chimes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChimeCard (paddingValues: PaddingValues, modifier: Modifier, children: @Composable () -> Unit){
    Column(
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color(0x59A86161),
                spotColor = Color(0x59A86161)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(brush =
            Brush.linearGradient(
                colors =
                    listOf(Color(0xFFFFF5F0), Color(0xFFFFE9E9)),))
            .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(18.dp)
    ){
        children()
    }
}