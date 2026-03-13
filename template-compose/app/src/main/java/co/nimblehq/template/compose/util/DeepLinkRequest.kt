package co.nimblehq.template.compose.util

import android.net.Uri

/**
 * Parse the requested Uri and store it in a easily readable format
 *
 * **Template note:** Navigation 3 does not natively support deep links. This is part of a
 * custom deep link implementation provided as template boilerplate.
 *
 * @param uri the target deeplink uri to link to
 */
internal class DeepLinkRequest(
    val uri: Uri
) {
    /**
     * A list of path segments
     */
    val pathSegments: List<String> = uri.pathSegments

    /**
     * A map of query name to query value
     */
    val queries = buildMap {
        uri.queryParameterNames.forEach { argName ->
            val value = uri.getQueryParameter(argName)
            if (value != null) this[argName] = value
        }
    }

    // TODO add parsing for other Uri components, i.e. fragments, mimeType, action
}
