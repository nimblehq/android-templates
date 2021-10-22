package co.nimblehq.rxjava.extension

import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Test

class KeywordExtensionKtTest {

    /**
     * TODO Temporarily disable this test because currently there is an issue with inline function
     * in tested code of AGP 6.x.x.
     * Reference issue: https://issuetracker.google.com/issues/197065758
     * Solution: Update to AGP > 7.1 will fix the issue
     */
    @Ignore("need AGP > 7.1")
    @Test
    fun `using unless doesn't execute if the condition is satisfied`() {
        var result = 3
        val condition: Int = -1

        unless(condition > -1) { result = 4 }
        assertEquals("block should exec", 4, result)

        unless(condition == -1) { result = 5 }
        assertEquals("block should NOT exec", 4, result)
    }
}
