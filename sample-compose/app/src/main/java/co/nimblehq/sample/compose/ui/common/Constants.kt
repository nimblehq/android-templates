package co.nimblehq.sample.compose.ui.common

import co.nimblehq.sample.compose.ui.screens.details.DetailsScreen

internal const val PATH_BASE = "android://android.nimblehq.co"
internal const val PATH_SEARCH = "users/search"
internal val URL_SEARCH =
    "$PATH_BASE/$PATH_SEARCH?${DetailsScreen.Search::username.name}={${DetailsScreen.Search::username.name}}"
