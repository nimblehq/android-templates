package com.nimbl3.testutil

import com.nimbl3.data.lib.schedulers.SchedulersProvider
import io.reactivex.schedulers.Schedulers

object MockSchedulersProvider : SchedulersProvider {
    override fun io() = Schedulers.trampoline()

    override fun computation() = Schedulers.trampoline()

    override fun main() = Schedulers.trampoline()
}