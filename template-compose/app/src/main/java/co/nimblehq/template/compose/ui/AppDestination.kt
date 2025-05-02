package co.nimblehq.template.compose.ui

import co.nimblehq.template.compose.ui.base.BaseAppDestination

sealed class AppDestination {

    object RootNavGraph : BaseAppDestination("rootNavGraph")

    object MainNavGraph : BaseAppDestination("mainNavGraph")
}
