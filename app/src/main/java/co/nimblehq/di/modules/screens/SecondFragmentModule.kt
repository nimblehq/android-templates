package co.nimblehq.di.modules.screens

import androidx.lifecycle.ViewModel
import co.nimblehq.ui.screens.home.HomeViewModel
import co.nimblehq.ui.screens.home.HomeViewModelImpl
import co.nimblehq.ui.screens.second.SecondViewModel
import co.nimblehq.ui.screens.second.SecondViewModelImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.multibindings.IntoMap

@InstallIn(FragmentComponent::class)
@Module
interface SecondFragmentModule {

    @Binds
    fun secondViewModel(viewModel: SecondViewModelImpl): SecondViewModel
}
