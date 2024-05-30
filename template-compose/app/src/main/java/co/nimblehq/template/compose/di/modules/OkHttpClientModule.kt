package co.nimblehq.template.compose.di.modules

import android.content.Context
import co.nimblehq.template.compose.BuildConfig
import co.nimblehq.template.compose.data.remote.interceptor.AuthInterceptor
import co.nimblehq.template.compose.data.local.preferences.NetworkEncryptedSharedPreferences
import co.nimblehq.template.compose.data.remote.authenticator.RequestAuthenticator
import co.nimblehq.template.compose.data.util.DispatchersProvider
import co.nimblehq.template.compose.di.Authorized
import co.nimblehq.template.compose.di.Unauthorized
import co.nimblehq.template.compose.domain.usecases.GetAuthStatusUseCase
import co.nimblehq.template.compose.domain.usecases.RefreshTokenUseCase
import co.nimblehq.template.compose.domain.usecases.UpdateLoginTokensUseCase
import com.chuckerteam.chucker.api.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.*

private const val READ_TIME_OUT = 30L

@Module
@InstallIn(SingletonComponent::class)
class OkHttpClientModule {

    @Unauthorized
    @Provides
    fun provideOkHttpClient(
        chuckerInterceptor: ChuckerInterceptor
    ) = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            addInterceptor(chuckerInterceptor)
            readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
        }
    }.build()

    @Authorized
    @Provides
    fun provideAuthorizedOkHttpClient(
        authInterceptor: AuthInterceptor,
        chuckerInterceptor: ChuckerInterceptor,
        authenticator: RequestAuthenticator,
    ) = OkHttpClient.Builder().apply {
        addInterceptor(authInterceptor)
        authenticator(authenticator)
        if (BuildConfig.DEBUG) {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            addInterceptor(chuckerInterceptor)
            readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
        }
    }.build().apply {
        authenticator.okHttpClient = this
    }

    @Provides
    fun provideChuckerInterceptor(
        @ApplicationContext context: Context
    ): ChuckerInterceptor {
        val chuckerCollector = ChuckerCollector(
            context = context,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )

        return ChuckerInterceptor.Builder(context)
            .collector(chuckerCollector)
            .alwaysReadResponseBody(true)
            .build()
    }

    @Provides
    fun provideAuthInterceptor(
        encryptedSharedPreference: NetworkEncryptedSharedPreferences
    ): AuthInterceptor {
        return AuthInterceptor(encryptedSharedPreference)
    }

    @Provides
    fun provideNetworkEncryptedSharedPreferences(
        @ApplicationContext context: Context,
    ): NetworkEncryptedSharedPreferences {
        return NetworkEncryptedSharedPreferences(context)
    }

    @Provides
    fun provideRequestAuthenticator(
        dispatchersProvider: DispatchersProvider,
        getAuthStatusUseCase: GetAuthStatusUseCase,
        refreshTokenUseCase: RefreshTokenUseCase,
        updateLoginTokensUseCase: UpdateLoginTokensUseCase,
    ): RequestAuthenticator = RequestAuthenticator(
        dispatchersProvider = dispatchersProvider,
        getAuthStatusUseCase = getAuthStatusUseCase,
        refreshTokenUseCase = refreshTokenUseCase,
        updateLoginTokensUseCase = updateLoginTokensUseCase,
    )
}
