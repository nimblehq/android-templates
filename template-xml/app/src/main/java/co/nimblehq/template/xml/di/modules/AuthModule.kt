package co.nimblehq.template.xml.di.modules

import co.nimblehq.template.xml.data.repository.SessionManagerImpl
import co.nimblehq.template.xml.data.repository.TokenRefresherImpl
import co.nimblehq.template.xml.data.service.AuthService
import co.nimblehq.template.xml.data.service.SessionManager
import co.nimblehq.template.xml.data.service.authenticator.TokenRefresher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Provides
    fun provideAuthService(authService: AuthService): TokenRefresher = TokenRefresherImpl(authService)

    @Provides
    fun provideSessionManager(): SessionManager = SessionManagerImpl()
}
