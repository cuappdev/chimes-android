package com.cornellappdev.chimes.ui.navigation

sealed class NavigationItem(
    val route: String,
) {
    object Home : NavigationItem(
        route = Routes.HOME.name,
    )

    object Onboarding : NavigationItem(
        route = Routes.ONBOARDING.name,
    )
}

/**
 * All NavUnit must have a route (which specifies where to
 * navigate to).
 */
interface NavUnit {
    val route: String
}

/**
 * Contains information about all known routes. These should correspond to routes in our
 * NavHost/new routes should be added here. Routes can exist independent of tabs (like onboarding).
 */
enum class Routes(override var route: String) : NavUnit {
    HOME("home"),
    ONBOARDING("onboarding"),
}
