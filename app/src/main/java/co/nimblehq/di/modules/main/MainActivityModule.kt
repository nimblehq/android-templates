package co.nimblehq.di.modules.main

import androidx.lifecycle.ViewModel
import co.nimblehq.di.ActivityScope
import co.nimblehq.di.ViewModelKey
import co.nimblehq.domain.repository.ApiRepository
import co.nimblehq.domain.repository.ApiRepositoryImpl
import co.nimblehq.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface MainActivityModule {

    @Binds
    @IntoMap
    @ActivityScope
    @ViewModelKey(MainViewModel::class)
    fun mainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @ActivityScope
    fun apiRepositoryImpl(repository: ApiRepositoryImpl): ApiRepository
}
