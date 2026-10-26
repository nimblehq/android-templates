package co.nimblehq.template.compose.navigation.navigator

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlin.reflect.KClass

@ActivityRetainedScoped
class AppNavigatorImpl(startDestination: Any) : AppNavigator {
    override val backStack: SnapshotStateList<Any> = mutableStateListOf(startDestination)

    override fun goTo(destination: Any) {
        backStack.add(destination)
    }

    override fun goBack() {
        backStack.removeLastOrNull()
    }

    override fun goBackToLast(destinationClass: KClass<*>) {
        val index = backStack.indexOfLast {
            destinationClass.isInstance(it)
        }

        if (index in backStack.indices) {
            backStack.removeRange(index + 1, backStack.size)
        }
    }
}
