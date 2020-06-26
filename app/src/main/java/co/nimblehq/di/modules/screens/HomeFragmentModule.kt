package co.nimblehq.di.modules.screens

import androidx.lifecycle.ViewModel
import co.nimblehq.di.FragmentScope
import co.nimblehq.di.ViewModelKey
import co.nimblehq.ui.screens.home.HomeViewModel
import co.nimblehq.ui.screens.home.HomeViewModelImpl
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface HomeFragmentModule {

    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(HomeViewModel::class)
    fun homeViewModel(viewModel: HomeViewModelImpl): ViewModel

}
