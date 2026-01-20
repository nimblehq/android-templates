package co.nimblehq.sample.compose.ui.base

import androidx.navigation.NamedNavArgument

const val KeyResultOk = "keyResultOk"

/**
 * Use "class" over "object" for destinations with [parcelableArgument] usage or a [navArgument] with [defaultValue] set
 * to reset destination nav arguments.
 */
abstract class BaseAppDestination(val route: String = "") {

    open val arguments: List<NamedNavArgument> = emptyList()

    open val deepLinks: List<String> = listOf(
        "https://android.nimblehq.co/$route",
        "android://$route",
    )

    open var destination: String = route

    open var parcelableArgument: Pair<String, Any?>? = null

    data class Up(
        val results: HashMap<String, Any> = hashMapOf(),
    ) : BaseAppDestination() {

        fun put(key: String, value: Any) = apply {
            results[key] = value
        }
    }
}
