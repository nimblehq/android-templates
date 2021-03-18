package co.nimblehq.coroutine.ui.screens.main

import android.content.Context
import co.nimblehq.coroutine.R
import co.nimblehq.coroutine.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
        @ApplicationContext val context: Context
) : BaseViewModel() {

    val title: String
        get() = context.getString(R.string.app_name)
}
