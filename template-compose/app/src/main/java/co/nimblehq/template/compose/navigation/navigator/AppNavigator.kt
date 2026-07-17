package co.nimblehq.template.compose.navigation.navigator

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

typealias EntryProviderInstaller = EntryProviderScope<Any>.() -> Unit

@Serializable
data object Up: NavKey

interface AppNavigator {

    val backStack: SnapshotStateList<Any>

    fun goTo(destination: Any)

    fun goBack()

    fun goBackToLast(destinationClass: KClass<*>)
}
