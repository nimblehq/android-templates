package co.nimblehq.rxjava.domain.schedulers

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import javax.inject.Inject

interface BaseSchedulerProvider {
    fun computation(): Scheduler
    fun io(): Scheduler
    fun main(): Scheduler
}

class SchedulerProvider @Inject constructor() : BaseSchedulerProvider {
    override fun computation(): Scheduler = Schedulers.computation()
    override fun io(): Scheduler = Schedulers.io()
    override fun main(): Scheduler = AndroidSchedulers.mainThread()
}

object TrampolineSchedulerProvider : BaseSchedulerProvider {
    override fun computation(): Scheduler = Schedulers.trampoline()
    override fun io(): Scheduler = Schedulers.trampoline()
    override fun main(): Scheduler = Schedulers.trampoline()
}

class TestSchedulerProvider(private val scheduler: TestScheduler) : BaseSchedulerProvider {
    override fun computation(): Scheduler = scheduler
    override fun io(): Scheduler = scheduler
    override fun main(): Scheduler = scheduler
}
