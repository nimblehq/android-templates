package co.nimblehq.template.compose.navigation.navigator

import io.kotest.matchers.shouldBe
import org.junit.Before
import org.junit.Test

private object DestinationA
private object DestinationB
private data class DestinationC(val id: Int)

class AppNavigatorImplTest {

    private lateinit var navigator: AppNavigatorImpl

    @Before
    fun setUp() {
        navigator = AppNavigatorImpl(startDestination = DestinationA)
    }

    @Test
    fun `When goTo is called, it appends the destination to the back stack`() {
        navigator.goTo(DestinationB)

        navigator.backStack shouldBe listOf(DestinationA, DestinationB)
    }

    @Test
    fun `When goBack is called, it removes the last destination from the back stack`() {
        navigator.goTo(DestinationB)

        navigator.goBack()

        navigator.backStack shouldBe listOf(DestinationA)
    }

    @Test
    fun `When goBack is called on a single-item back stack, it does not throw`() {
        navigator.goBack()

        navigator.backStack shouldBe emptyList()
    }

    @Test
    fun `When goBackToLast is called with a class present on the back stack, it truncates back to that entry`() {
        navigator.goTo(DestinationB)
        navigator.goTo(DestinationC(1))
        navigator.goTo(DestinationB)

        navigator.goBackToLast(DestinationC::class)

        navigator.backStack shouldBe listOf(DestinationA, DestinationB, DestinationC(1))
    }

    @Test
    fun `When goBackToLast is called with a class not present on the back stack, it does nothing`() {
        navigator.goTo(DestinationB)

        navigator.goBackToLast(DestinationC::class)

        navigator.backStack shouldBe listOf(DestinationA, DestinationB)
    }
}
