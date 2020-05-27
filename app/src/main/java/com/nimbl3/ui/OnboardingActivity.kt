package com.nimbl3.ui

import android.content.Context
import android.content.Intent
import co.omise.gcpf.app.R
import co.omise.gcpf.app.ui.base.BaseActivity

class OnboardingActivity : BaseActivity<OnboardingViewModel>() {

    override val viewModelClass = OnboardingViewModel::class

    override val layoutRes: Int = R.layout.activity_onboarding

    override fun bindViewModel() {}

    companion object {

        fun show(from: Context) {
            val intent = Intent(from, OnboardingActivity::class.java)
            from.startActivity(intent)
        }
    }
}
