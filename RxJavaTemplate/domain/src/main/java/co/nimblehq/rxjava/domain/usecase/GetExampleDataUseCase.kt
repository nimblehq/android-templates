package co.nimblehq.rxjava.domain.usecase

import co.nimblehq.rxjava.domain.data.Data
import co.nimblehq.rxjava.domain.data.error.DataError.GetDataError
import co.nimblehq.rxjava.domain.repository.ApiRepository
import co.nimblehq.rxjava.domain.schedulers.BaseSchedulerProvider
import co.nimblehq.rxjava.domain.usecase.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetExampleDataUseCase @Inject constructor(
    schedulerProvider: BaseSchedulerProvider,
    private val repository: ApiRepository
) : SingleUseCase<Unit, List<Data>>(
    schedulerProvider.io(),
    schedulerProvider.main(),
    ::GetDataError
) {

    override fun create(input: Unit): Single<List<Data>> {
        return repository.exampleData()
    }
}
