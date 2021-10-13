package co.nimblehq.rxjava.ui.screens.second

import android.os.Bundle
import co.nimblehq.rxjava.databinding.FragmentSecondBinding
import co.nimblehq.rxjava.di.modules.RxPermissionsModule
import co.nimblehq.rxjava.domain.data.Data
import co.nimblehq.rxjava.test.TestNavigatorModule.mockMainNavigator
import co.nimblehq.rxjava.test.getPrivateProperty
import co.nimblehq.rxjava.test.replace
import co.nimblehq.rxjava.ui.screens.BaseFragmentTest
import com.tbruyelle.rxpermissions2.RxPermissions
import dagger.hilt.android.testing.*
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldNotBeNull
import org.junit.*

@HiltAndroidTest
@UninstallModules(RxPermissionsModule::class)
class SecondFragmentUnitTest : BaseFragmentTest<SecondFragment, FragmentSecondBinding>() {

    private val mockViewModel = mockk<SecondViewModel>(relaxed = true)
    private val bundle = SecondBundle(
        data = Data(
            title = "title",
            author = "author",
            thumbnail = "",
            url = ""
        )
    )

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @BindValue
    @JvmField
    val mockRxPermissions = mockk<RxPermissions>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun `When initializing SecondFragment, its view displays the corresponding data from bundle`() {
        every { mockViewModel.data } returns Observable.just(bundle.data)
        launchFragment()
        fragment.binding.btOpenPost.text.toString() shouldBeEqualTo "Open Post"
        fragment.binding.tvSecondTitle.text.toString() shouldBeEqualTo bundle.data.title
        fragment.binding.tvSecondAuthor.text.toString() shouldBeEqualTo bundle.data.author
    }

    private fun launchFragment() {
        val args = Bundle().apply {
            putParcelable(
                "bundle", bundle
            )
        }
        launchFragmentInHiltContainer<SecondFragment>(
            onInstantiate = {
                replace(getPrivateProperty("viewModel"), mockViewModel)
                navigator = mockMainNavigator
                rxPermissions = mockRxPermissions
            },
            fragmentArgs = args
        ) {
            fragment = this
            fragment.navigator.shouldNotBeNull()
            fragment.rxPermissions.shouldNotBeNull()
        }
    }
}

