package co.nimblehq.template.xml.util

import kotlinx.coroutines.Dispatchers

class DispatchersProviderImpl : DispatchersProvider {

    override val io = Dispatchers.IO

    override val main = Dispatchers.Main

    override val default = Dispatchers.Default
}
