package co.nimblehq.template.compose.di.modules

import co.nimblehq.template.compose.ui.screens.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
}
