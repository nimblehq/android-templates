package co.nimblehq.template.xml.test

import androidx.navigation.NavArgs
import co.nimblehq.template.xml.extension.OverridableLazy
import kotlin.reflect.KProperty1
import kotlin.reflect.jvm.isAccessible

fun <Arg : NavArgs, T> T.replace(
    argumentDelegate: KProperty1<T, Arg>,
    argument: Arg
) {
    argumentDelegate.isAccessible = true
    (argumentDelegate.getDelegate(this) as OverridableLazy<Arg>).implementation = lazy { argument }
}
