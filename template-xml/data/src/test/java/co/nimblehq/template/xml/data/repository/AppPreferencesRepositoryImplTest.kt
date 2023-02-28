package co.nimblehq.template.xml.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import co.nimblehq.template.xml.domain.repository.AppPreferencesRepository
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.IOException

private const val TEST_DATASTORE_NAME: String = "test_datastore"

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class AppPreferencesRepositoryImplTest {

    private val testContext: Context = ApplicationProvider.getApplicationContext()
    private val testCoroutineDispatcher = UnconfinedTestDispatcher()
    private val testCoroutineScope = TestScope(testCoroutineDispatcher + Job())
    private val testDataStore: DataStore<Preferences> = PreferenceDataStoreFactory.create(
        scope = testCoroutineScope,
        produceFile = { testContext.preferencesDataStoreFile(TEST_DATASTORE_NAME) }
    )

    private val mockDataStore: DataStore<Preferences> = mockk()
    private lateinit var repository: AppPreferencesRepository

    @Before
    fun setup() {
        repository = AppPreferencesRepositoryImpl(mockDataStore)
    }

    @Test
    fun `When calling isFirstTimeLaunch request successfully, it returns correct response`() =
        runTest {
            val mockPreferences = mockk<Preferences>()
            every { mockPreferences[booleanPreferencesKey("APP_PREFERENCES")] } returns false
            every { mockDataStore.data } returns flowOf(mockPreferences)

            repository.getAppPreferences().collect {
                it shouldBe false
            }
        }

    @Test
    fun `When calling isFirstTimeLaunch request failed with IOException, it returns true by default`() =
        runTest {
            every { mockDataStore.data } returns flow { throw IOException() }

            repository.getAppPreferences().collect {
                it shouldBe true
            }
        }

    @Test
    fun `When calling isFirstTimeLaunch request failed with other exceptions, it returns wrapped error`() =
        runTest {
            val expected = Exception()
            every { mockDataStore.data } returns flow { throw expected }

            repository.getAppPreferences().test {
                awaitError() shouldBe expected
            }
        }

    /**
     * FIXME Can't use MockK to mock DataStore.edit or DataStore.updateData, switch to use a test DataStore.
     */
    @Test
    fun `When calling updateFirstTimeLaunch, it updates ItSupportInfo into DataStore`() =
        runTest {
            repository = AppPreferencesRepositoryImpl(testDataStore)

            val expected = false

            repository.updateAppPreferences(expected)

            repository.getAppPreferences().test {
                expectMostRecentItem() shouldBe expected
            }
        }
}
