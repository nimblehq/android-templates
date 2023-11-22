package co.nimblehq.template.compose.ui.screens.main

import co.nimblehq.template.compose.ui.base.BaseDestination

sealed class MainDestination {

    object Home : BaseDestination("home")
}
