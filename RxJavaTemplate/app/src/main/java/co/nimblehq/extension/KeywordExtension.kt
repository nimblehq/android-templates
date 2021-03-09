package co.nimblehq.extension

/**
 * Provide syntactic sugar extensions that help the code flow cleaner and more readable
 */

inline fun unless(condition: Boolean, block: () -> Unit) {
    if(!condition) block()
}
