package co.nimblehq.template.compose.ui.screens

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import co.nimblehq.template.compose.navigation.Navigator
import co.nimblehq.template.compose.ui.screens.main.home.Home
import kotlin.reflect.KClass

class FakeNavigator(startDestination: Any = Home) : Navigator {
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

    fun addToBackStack(listOfPastDestinations: List<Any>) {
        backStack.addAll(listOfPastDestinations)
    }

    fun currentScreen(): Any? = backStack.lastOrNull()

    fun currentScreenClass(): KClass<*>? = backStack.lastOrNull()?.let { it::class }
}
