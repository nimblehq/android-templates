package co.nimblehq.domain.usecase

import co.nimblehq.domain.data.Data
import co.nimblehq.domain.data.error.DataError.GetDataError
import co.nimblehq.domain.repository.ApiRepository
import co.nimblehq.domain.schedulers.BaseSchedulerProvider
import co.nimblehq.domain.usecase.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetExampleDataUseCase @Inject constructor(
    schedulerProvider: BaseSchedulerProvider,
    private val repository: ApiRepository
) : SingleUseCase<Unit, Data>(
    schedulerProvider.io(),
    schedulerProvider.main(),
    ::GetDataError
) {

    override fun create(input: Unit): Single<Data> {
        return repository.exampleData()
    }
}
