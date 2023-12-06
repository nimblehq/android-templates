package co.nimblehq.template.compose.ui

import co.nimblehq.template.compose.ui.base.BaseDestination

sealed class AppDestination {

    object RootNavGraph : BaseDestination("rootNavGraph")

    object MainNavGraph : BaseDestination("mainNavGraph")
}
