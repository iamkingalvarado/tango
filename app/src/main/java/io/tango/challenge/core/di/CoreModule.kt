package io.tango.challenge.core.di

import android.content.Context
import io.tango.challenge.BuildConfig
import io.tango.challenge.features.movies.data.database.MoviesDatabase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.tango.challenge.core.data.interceptor.ApiKeyTokenInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@InstallIn(SingletonComponent::class)
@Module
object CoreModule {

    @Provides
    fun bindInterceptor(): Interceptor {
        return ApiKeyTokenInterceptor(apiKey = BuildConfig.API_KEY)
    }

    @Provides
    fun bindRetrofit(apiKeyInterceptor: Interceptor): Retrofit {
        return provideRetrofit(
            apiKeyInterceptor = apiKeyInterceptor
        )
    }

    @Provides
    fun bindDatabase(@ApplicationContext context: Context): MoviesDatabase {
        return MoviesDatabase(context = context)
    }

    private fun provideRetrofit(apiKeyInterceptor: Interceptor): Retrofit {
        val builder = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
        builder.client(client(apiKeyInterceptor))
        return builder.build()
    }

    private fun client(apiKeyInterceptor: Interceptor): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        var clientBuilder = if (BuildConfig.DEBUG) {
            OkHttpClient.Builder()
                .addInterceptor(interceptor)
        } else {
            OkHttpClient.Builder()
        }
        clientBuilder = clientBuilder.addInterceptor(apiKeyInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .callTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
        return clientBuilder.build()
    }
}