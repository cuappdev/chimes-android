package com.cornellappdev.chimes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object GlobalState {
    var userName by mutableStateOf("Ithacan")
}
