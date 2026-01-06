package co.nimblehq.sample.compose.ui.common

import co.nimblehq.sample.compose.ui.screens.details.DetailsScreen

internal const val STRING_LITERAL_USERS = "users"
internal const val STRING_LITERAL_SEARCH = "search"
internal const val PATH_BASE = "https://www.android.nimblehq.co"
internal const val PATH_SEARCH = "$STRING_LITERAL_USERS/$STRING_LITERAL_SEARCH"
internal val URL_SEARCH = "$PATH_BASE/$PATH_SEARCH" +
        "?${DetailsScreen.Search::username.name}={${DetailsScreen.Search::username.name}}"
