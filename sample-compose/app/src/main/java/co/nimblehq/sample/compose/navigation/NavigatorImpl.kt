package co.nimblehq.sample.compose.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import dagger.hilt.android.scopes.ActivityRetainedScoped

@ActivityRetainedScoped
class NavigatorImpl(startDestination: Any): Navigator {
    override val backStack: SnapshotStateList<Any> = mutableStateListOf(startDestination)

    override fun goTo(destination: Any) {
        backStack.add(destination)
    }

    override fun goBack() {
        backStack.removeLastOrNull()
    }
}
