package co.nimblehq.sample.xml.ui.screens.second

import androidx.core.view.isVisible
import co.nimblehq.sample.xml.R
import co.nimblehq.sample.xml.databinding.FragmentSecondBinding
import co.nimblehq.sample.xml.model.UiModel
import co.nimblehq.sample.xml.test.getPrivateProperty
import co.nimblehq.sample.xml.test.replace
import co.nimblehq.sample.xml.ui.BaseFragmentTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.*
import java.util.*

@HiltAndroidTest
class SecondFragmentTest : BaseFragmentTest<SecondFragment, FragmentSecondBinding>() {

    private val mockViewModel = mockk<SecondViewModel>(relaxed = true)
    private val mockArgs = mockk<SecondFragmentArgs>(relaxed = true)

    private val uiModel = UiModel(UUID.randomUUID().toString())

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()

        every { mockArgs.uiModel } returns uiModel
    }

    @Test
    fun `When launching fragment, it displays the text view`() {
        launchFragment()
        fragment.binding.tvSecondId.isVisible.shouldBeTrue()
    }

    @Test
    fun `When launching fragment and view model with id, it displays the text view with id content`() {
        every { mockViewModel.id } returns MutableStateFlow(uiModel.id)

        launchFragment()
        fragment.binding.tvSecondId.text shouldBe fragment.getString(
            R.string.second_id_title,
            uiModel.id
        )
    }

    private fun launchFragment() {
        launchFragmentInHiltContainer<SecondFragment>(
            onInstantiate = {
                replace(getPrivateProperty("viewModel"), mockViewModel)
                replace(getPrivateProperty("args"), mockArgs)
            }
        ) {
            fragment = this
        }
    }
}
