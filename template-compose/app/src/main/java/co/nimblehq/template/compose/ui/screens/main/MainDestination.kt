package co.nimblehq.template.compose.ui.screens.main

import co.nimblehq.template.compose.ui.base.BaseAppDestination

sealed class MainDestination {

    object Home : BaseAppDestination("home")
}
