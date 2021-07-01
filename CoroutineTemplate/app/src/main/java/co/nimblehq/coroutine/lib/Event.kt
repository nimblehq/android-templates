package co.nimblehq.coroutine.lib

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents a single event.
 */
class Event<out T>(val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun proceedIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}
