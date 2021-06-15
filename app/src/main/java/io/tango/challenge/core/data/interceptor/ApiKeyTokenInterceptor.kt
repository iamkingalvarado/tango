package io.tango.challenge.core.data.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyTokenInterceptor(val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var url = chain.request().url
        url = url.newBuilder()
            .addQueryParameter("api_key", apiKey)
            .build()
        val newRequest = chain.request()
            .newBuilder()
            .url(url)
            .build()
        return chain.proceed(newRequest)
    }
}
