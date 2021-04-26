package co.nimblehq.rxjava.extension

import org.junit.Assert.assertEquals
import org.junit.Test

class KeywordExtensionKtTest {

    @Test
    fun `using unless should not execute if the condition match`() {
        var result = 3
        val condition: Int = -1

        unless(condition > -1) { result = 4 }
        assertEquals("block should exec", 4, result)

        unless(condition == -1) { result = 5 }
        assertEquals("block should NOT exec", 4, result)
    }
}
