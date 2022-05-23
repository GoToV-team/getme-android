package com.gotov.getmeapp.app.di

import android.app.Application
import com.fasterxml.jackson.databind.ObjectMapper
import com.gotov.getmeapp.main.profile.model.api.ProfileApi
import com.gotov.getmeapp.main.profile.model.repository.ProfileRepository
import com.gotov.getmeapp.main.profile.viewmodel.ProfileViewModel
import com.gotov.getmeapp.main.search.model.api.SearchApi
import com.gotov.getmeapp.main.search.model.repository.SearchRepository
import com.gotov.getmeapp.main.search.viewmodel.SearchViewModel
import com.gotov.getmeapp.sign.login.model.api.LoginApi
import com.gotov.getmeapp.sign.login.model.repository.LoginRepository
import com.gotov.getmeapp.sign.login.viewmodel.LoginViewModel
import com.gotov.getmeapp.utils.app.provideApi
import com.gotov.getmeapp.utils.app.provideRepository
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

private const val BASE_URL = "https://glidemess.pw/api/v1/"

val viewModelModule = module {
    single { LoginViewModel(get()) }
    single { SearchViewModel(get()) }
    single { ProfileViewModel(get()) }
}

val apiModule = module {
    single { provideApi<LoginApi>(get()) }
    single { provideApi<SearchApi>(get()) }
    single { provideApi<ProfileApi>(get()) }
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
    single { provideRepository<SearchRepository, SearchApi>(get()) }
    single { provideRepository<ProfileRepository, ProfileApi>(get()) }
}
