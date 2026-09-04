package co.nimblehq.template.compose.navigation.navigator

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.EntryProviderScope
import kotlin.reflect.KClass

typealias EntryProviderInstaller = EntryProviderScope<Any>.() -> Unit

interface AppNavigator {

    val backStack: SnapshotStateList<Any>

    fun goTo(destination: Any)

    fun goBack()

    /**
     * Pops the back stack up to and including the last (most recent) entry that is an instance
     * of [destinationClass].
     *
     * Use this to return to a screen that is already on the back stack without needing to know
     * how many steps to pop — for example, returning to a list screen after a multi-step
     * create/edit flow. No-op if [destinationClass] is not present on the back stack.
     */
    fun goBackToLast(destinationClass: KClass<*>)
}
