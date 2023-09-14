package co.nimblehq.sample.xml.extension

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs

@MainThread
inline fun <reified Args : NavArgs> Fragment.provideNavArgs(): Lazy<Args> =
    OverridableLazy(navArgs())
