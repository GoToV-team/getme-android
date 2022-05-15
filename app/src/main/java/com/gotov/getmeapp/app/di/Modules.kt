package com.gotov.getmeapp.app.di

import android.app.Application
import com.fasterxml.jackson.databind.ObjectMapper
import com.gotov.getmeapp.sign_module.login.model.api.LoginApi
import com.gotov.getmeapp.sign_module.login.model.repository.LoginRepository
import com.gotov.getmeapp.sign_module.login.view_model.LoginViewModel
import com.gotov.getmeapp.utils.app.provideApi
import com.gotov.getmeapp.utils.app.provideRepository
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

private const val BASE_URL = "https://api.github.com/api/v1"

val viewModelModule = module {
    single { LoginViewModel(get()) }
}

val apiModule = module {
    single { provideApi<LoginApi>(get()) }
}

val netModule = module {
    fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    fun provideHttpClient(cache: Cache): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .cache(cache)

        return okHttpClientBuilder.build()
    }

    fun provideJackson(): ObjectMapper {
        return ObjectMapper()
    }

    fun provideRetrofit(mapper: ObjectMapper, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create(mapper))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }

    single { provideCache(androidApplication()) }
    single { provideHttpClient(get()) }
    single { provideJackson() }
    single { provideRetrofit(get(), get()) }
}

val repositoryModule = module {
    single { provideRepository<LoginRepository, LoginApi>(get()) }
}
