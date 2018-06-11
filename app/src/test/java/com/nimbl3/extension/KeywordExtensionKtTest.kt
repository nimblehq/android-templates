package com.nimbl3.extension

import org.junit.Test

import org.junit.Assert.*

@Suppress("IllegalIdentifier")
class KeywordExtensionKtTest {

    @Test
    fun `using unless should not execute if the condition match`() {
        var result = 3
        val condition: Int = -1

        unless(condition > -1, { result = 4})
        assertEquals("block should exec", 4, result)

        unless(condition == -1, { result = 5})
        assertEquals("block should NOT exec", 4, result)
    }
}