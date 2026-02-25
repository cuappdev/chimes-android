package com.cornellappdev.chimes.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.cornellappdev.chimes.ui.screens.HomeScreen

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun MainNavigation () {
    val backStack = remember { mutableStateListOf<Any>(NavigationItem.Home) }

    NavDisplay(
        backStack = backStack,
        onBack = {
            if (backStack.size > 1) backStack.removeLastOrNull()
        },
        entryProvider = entryProvider {
            entry<NavigationItem.Home> {
                HomeScreen()
            }
        }
    )
}