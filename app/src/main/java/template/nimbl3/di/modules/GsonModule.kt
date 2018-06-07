package template.nimbl3.di.modules

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GsonModule {
    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()
}