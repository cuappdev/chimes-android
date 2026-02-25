package com.cornellappdev.chimes.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.addOutline
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun HeaderButton (iconId: Int, onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .size(50.dp)
            .drawWithCache() {
                val path = Path().apply {
                    addOutline(CircleShape.createOutline(size, layoutDirection, this@drawWithCache))
                }

                onDrawBehind {
                    clipPath(path, clipOp = ClipOp.Difference) {
                        drawIntoCanvas { canvas ->
                            val paint = android.graphics.Paint().apply {
                                color = android.graphics.Color.TRANSPARENT
                                setShadowLayer(
                                    4.dp.toPx(),
                                    0.dp.toPx(),
                                    4.dp.toPx(),
                                    Color.Black.copy(alpha = 0.11f).toArgb()
                                )
                            }
                            canvas.nativeCanvas.drawPath(path.asAndroidPath(), paint)
                        }
                    }
                }
            }
            .border(
                width = 1.dp,
                brush =
                    Brush.linearGradient(listOf(Color(0xFFDEEAFA), Color(0xFFF7FAFF))),
                shape = CircleShape
            ),
                onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            Color(0x33BFCFE4),
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = "hell",
            tint = Color(0xFF757575)
        )
    }
}