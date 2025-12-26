package co.nimblehq.sample.compose.navigation

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.EntryProviderScope

typealias EntryProviderInstaller = EntryProviderScope<Any>.() -> Unit

interface Navigator {

    val backStack : SnapshotStateList<Any>

    fun goTo(destination: Any)

    fun goBack()
}
