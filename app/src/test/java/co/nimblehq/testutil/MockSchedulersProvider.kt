package co.nimblehq.testutil

import co.nimblehq.domain.schedulers.BaseSchedulerProvider
import io.reactivex.schedulers.Schedulers

object MockSchedulersProvider : BaseSchedulerProvider {
    override fun io() = Schedulers.trampoline()

    override fun computation() = Schedulers.trampoline()

    override fun main() = Schedulers.trampoline()
}
