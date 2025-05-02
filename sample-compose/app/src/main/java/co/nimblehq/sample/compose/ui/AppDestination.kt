package co.nimblehq.sample.compose.ui

import co.nimblehq.sample.compose.ui.base.BaseAppDestination

sealed class AppDestination {

    object RootNavGraph : BaseAppDestination("rootNavGraph")

    object MainNavGraph : BaseAppDestination("mainNavGraph")
}
