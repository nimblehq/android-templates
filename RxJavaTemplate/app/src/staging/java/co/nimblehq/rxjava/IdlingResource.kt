package co.nimblehq.rxjava

import androidx.test.espresso.idling.CountingIdlingResource

object IdlingResource {

    private const val RESOURCE = "global"

    @JvmField
    val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increaseOrDecrease(increase: Boolean) {
        countingIdlingResource.run {
            if (increase) {
                increment()
            } else {
                if (!isIdleNow) decrement()
            }
        }
    }
}
