package com.nimbl3.ui.main.di

import androidx.lifecycle.ViewModel
import com.nimbl3.di.ViewModelKey
import com.nimbl3.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class MainActivityModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}
