package com.nimbl3.ui.second.di

import androidx.lifecycle.ViewModel
import com.nimbl3.di.ViewModelKey
import com.nimbl3.ui.second.SecondViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class SecondActivityModule {

    @Binds
    @IntoMap
    @ViewModelKey(SecondViewModel::class)
    abstract fun bindSecondViewModel(viewModel: SecondViewModel): ViewModel
}
