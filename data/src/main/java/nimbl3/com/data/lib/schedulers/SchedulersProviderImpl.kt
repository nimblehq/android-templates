package nimbl3.com.data.lib.schedulers

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import nimbl3.com.data.lib.schedulers.SchedulersProvider

class SchedulersProviderImpl: SchedulersProvider {
    override fun io(): Scheduler = Schedulers.io()

    override fun computation(): Scheduler = Schedulers.computation()

    override fun main(): Scheduler = AndroidSchedulers.mainThread()
}