package com.nimbl3.ui.verifyemail

import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.navArgs
import co.omise.gcpf.app.R
import co.omise.gcpf.app.extension.*
import co.omise.gcpf.app.lib.IsLoading
import co.omise.gcpf.app.ui.base.BaseFragment
import co.omise.gcpf.app.ui.base.BaseFragmentCallbacks
import com.nimbl3.ui.OnBoardingNavigator
import kotlinx.android.synthetic.main.fragment_verify_email.*
import kotlinx.android.synthetic.main.view_loading.*
import javax.inject.Inject

class VerifyEmailFragment : BaseFragment<VerifyEmailViewModel>(), BaseFragmentCallbacks {

    override val viewModelClass = VerifyEmailViewModel::class

    override val layoutRes = R.layout.fragment_verify_email

    override val toolbarLayoutRes = R.layout.toolbar_common

    @Inject lateinit var navigator: OnBoardingNavigator

    private val args: VerifyEmailFragmentArgs by navArgs()

    private val bundle: VerifyEmailBundle by lazy { args.bundle }

    override fun setupView() {
        viewModel.input.bundle(bundle)
        when (val bundle = this.bundle) {
            is VerifyEmailBundle.ForRegister -> {
                hideNavigationUp()
                etVerifyEmail.setText(bundle.email)
            }
        }
    }

    override fun bindViewEvents() {
        super.bindViewEvents()
        btVerifyEmailNext
            .subscribeOnClick(::verifyEmail)
            .addToDisposables()

        etVerifyEmail
            .doOnTextChanged { text, _, _, _ ->
                viewModel.input.email(
                    text.toString()
                )
            }
    }

    override fun bindViewModel() {
        viewModel.showLoading bindTo ::bindLoading
        viewModel.error bindTo ::bindError
        viewModel.enableNextButton bindTo btVerifyEmailNext::setEnabled
        viewModel.navigator bindTo navigator::navigate
    }

    private fun verifyEmail() {
        tvVerifyEmailError.visibleOrGone(false)

        viewModel.verifyEmail()
    }

    private fun bindLoading(isLoading: IsLoading) {
        pbLoading.visibleOrGone(isLoading)
        etVerifyEmail.isEnabled = !isLoading
        btVerifyEmailNext.isEnabled = !isLoading
    }

    private fun bindError(error: Throwable) {
        tvVerifyEmailError.visible()
        tvVerifyEmailError.text = error.userReadableMessage(requireContext())
    }

}
