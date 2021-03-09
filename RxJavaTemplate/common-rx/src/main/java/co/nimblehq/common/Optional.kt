package co.nimblehq.common

import java.util.*

/**
 * A ported version from the original Optional from java.util.Optional (requires java 8)
 */
class Optional<T> {
    /**
     * If non-null, the value; if null, indicates no value is present
     */
    private val value: T?

    /**
     * Constructs an empty instance.
     *
     * @implNote Generally only one empty instance, [Optional.EMPTY],
     * should exist per VM.
     */
    private constructor() {
        value = null
    }

    /**
     * Constructs an instance with the value present.
     *
     * @param value the non-null value to be present
     * @throws NullPointerException if value is null
     */
    private constructor(value: T) {
        this.value = requireNonNull(value)
    }

    private fun <T> requireNonNull(obj: T?): T {
        if (obj == null) {
            throw NullPointerException()
        }
        return obj
    }

    /**
     * If a value is present in this `Optional`, returns the value,
     * otherwise throws `NoSuchElementException`.
     *
     * @return the non-null value held by this `Optional`
     * @throws NoSuchElementException if there is no value present
     * @see Optional.isPresent
     */
    fun get(): T {
        if (value == null) {
            throw NoSuchElementException("No value present")
        }
        return value
    }

    /**
     * Return `true` if there is a value present, otherwise `false`.
     *
     * @return `true` if there is a value present, otherwise `false`
     */
    val isPresent: Boolean
        get() = value != null

    /**
     * Return the value if present, otherwise return `other`.
     *
     * @param other the value to be returned if there is no value present, may
     * be null
     * @return the value, if present, otherwise `other`
     */
    fun orElse(other: T): T {
        return value ?: other
    }

    /**
     * Indicates whether some other object is "equal to" this Optional. The
     * other object is considered equal if:
     *
     *  * it is also an `Optional` and;
     *  * both instances have no value present or;
     *  * the present values are "equal to" each other via `equals()`.
     *
     *
     * @param other an object to be tested for equality
     * @return {code true} if the other object is "equal to" this object
     * otherwise `false`
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other !is Optional<*>) {
            return false
        }
        return equals(value, other.value!!)
    }

    /**
     * Returns the hash code value of the present value, if any, or 0 (zero) if
     * no value is present.
     *
     * @return hash code value of the present value or 0 if no value is present
     */
    override fun hashCode(): Int {
        return value?.hashCode() ?: 0
    }

    /**
     * Returns a non-empty string representation of this Optional suitable for
     * debugging. The exact presentation format is unspecified and may vary
     * between implementations and versions.
     *
     * @return the string representation of this instance
     * @implSpec If a value is present the result must include its string
     * representation in the result. Empty and present Optionals must be
     * unambiguously differentiable.
     */
    override fun toString(): String {
        return if (value != null) String.format("Optional[%s]", value) else "Optional.empty"
    }

    companion object {
        /**
         * Common instance for `empty()`.
         */
        private val EMPTY: Optional<*> = Optional<Any>()

        /**
         * Returns an empty `Optional` instance.  No value is present for this
         * Optional.
         *
         * @param <T> Type of the non-existent value
         * @return an empty `Optional`
         * @apiNote Though it may be tempting to do so, avoid testing if an object
         * is empty by comparing with `==` against instances returned by
         * `Option.empty()`. There is no guarantee that it is a singleton.
         * Instead, use [.isPresent].
        </T> */
        fun <T> empty(): Optional<T> {
            return EMPTY as Optional<T>
        }

        /**
         * Returns an `Optional` with the specified present non-null value.
         *
         * @param <T>   the class of the value
         * @param value the value to be present, which must be non-null
         * @return an `Optional` with the value present
         * @throws NullPointerException if value is null
        </T> */
        fun <T> of(value: T): Optional<T> {
            return Optional(value)
        }

        fun equals(a: Any?, b: Any): Boolean {
            return (a == b) || (a != null && a == b)
        }
    }
}
