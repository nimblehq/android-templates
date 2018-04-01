package template.nimbl3.services.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AppRequestInterceptor: Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // Do custom intercepting activities here, for example: appending accessToken header if required

        val afterIntercepted = originalRequest.newBuilder().build()
        return chain.proceed(afterIntercepted)
    }
}
