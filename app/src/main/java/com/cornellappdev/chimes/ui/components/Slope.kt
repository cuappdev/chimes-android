package com.cornellappdev.chimes.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.cornellappdev.chimes.R

@Composable
fun Slope() {
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Box(
            modifier = Modifier
                .height(411.dp)
                .fillMaxWidth()
                .background(brush =
                    Brush.linearGradient(colors =
                        listOf(Color(0xFFD3EEFF), Color(0xFFF6FAFF))))
        )
        Image(
            painter = painterResource(R.drawable.ic_music_notes),
            contentDescription = null,
            modifier = Modifier.offset(x = 32.dp, y = 181.dp)
        )
        Image(
            painter = painterResource(R.drawable.ic_trees_back),
            contentDescription = null,
            modifier = Modifier.
            offset(y = 346.dp)
        )
        Image(
            painter = painterResource(R.drawable.ic_slope),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp)
                .offset(y = 289.dp)
                .graphicsLayer(alpha = 0.99f)
                .drawWithCache {
                    onDrawWithContent {
                        drawContent()
                        drawRect(
                            brush = Brush.linearGradient(
                                colors = listOf(Color(0xFFF2B5B5), Color(0xFFFBD3D3)),
                                start = Offset(0f, 0f),
                                end = Offset(0f, size.height)),
                            blendMode = BlendMode.SrcAtop
                        )
                    }
                },
        )
        Image(
            painter = painterResource(R.drawable.ic_trees_front),
            contentDescription = null,
            modifier = Modifier.
            offset(y = 324.dp)
        )
        Image(
            painter = painterResource(R.drawable.ic_clocktower_day),
            contentDescription = null,
            modifier = Modifier.offset(x = 200.dp, y = 165.dp).height(224.dp).width(164.dp),
        )
    }
}
