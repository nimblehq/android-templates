package co.nimblehq.testutil

import io.reactivex.schedulers.Schedulers

object MockSchedulersProvider : SchedulersProvider {
    override fun io() = Schedulers.trampoline()

    override fun computation() = Schedulers.trampoline()

    override fun main() = Schedulers.trampoline()
}
