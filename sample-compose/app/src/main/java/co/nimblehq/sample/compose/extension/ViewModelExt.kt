package co.nimblehq.sample.compose.extension

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*

/**
 * PLEASE READ THIS BEFORE IMPLEMENT:
 * Right now, there is no easy way to mock/fake the viewModel inside the Fragment when applying
 * the 'by viewModels()' Kotlin property delegate from the activity-ktx/fragment-ktx artifact.
 * After finding many ways to handle this issue, I end up with this solution that is to override the
 * loading mechanism of the delegate.
 * There is another way to resolve the issue as well and it is mentioned in the reference link.
 * Reference: https://proandroiddev.com/testing-the-untestable-the-case-of-the-viewmodel-delegate-975c09160993
 */
@MainThread
inline fun <reified VM : ViewModel> Fragment.provideViewModels(
    noinline ownerProducer: () -> ViewModelStoreOwner = { this },
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> = OverridableLazy(viewModels(ownerProducer, factoryProducer))

@MainThread
inline fun <reified VM : ViewModel> ComponentActivity.provideViewModels(
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> = OverridableLazy(viewModels(factoryProducer))

class OverridableLazy<T>(var implementation: Lazy<T>) : Lazy<T> {

    override val value
        get() = implementation.value

    override fun isInitialized() = implementation.isInitialized()
}
