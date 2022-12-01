package co.nimblehq.template.compose.test

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import co.nimblehq.template.compose.extension.OverridableLazy
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

fun <VM : ViewModel, T> T.replace(
    viewModelDelegate: KProperty1<T, VM>,
    viewModel: VM
) {
    viewModelDelegate.isAccessible = true
    (viewModelDelegate.getDelegate(this) as OverridableLazy<VM>).implementation = lazy { viewModel }
}

inline fun <reified T : Fragment, VM> T.getPrivateProperty(name: String): KProperty1<T, VM> =
    T::class
        .memberProperties
        .first { it.name == name } as KProperty1<T, VM>
