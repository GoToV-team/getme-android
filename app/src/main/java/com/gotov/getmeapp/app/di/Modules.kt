package com.gotov.getmeapp.app.di

import android.app.Application
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.gotov.getmeapp.main.plan.model.api.PlanApi
import com.gotov.getmeapp.main.plan.model.repository.PlanRepository
import com.gotov.getmeapp.main.plan.viewmodel.PlanViewModel
import com.gotov.getmeapp.main.plans.model.api.PlansApi
import com.gotov.getmeapp.main.plans.model.repository.PlansRepository
import com.gotov.getmeapp.main.plans.viewmodel.PlansViewModel
import com.gotov.getmeapp.main.profile.model.api.ProfileApi
import com.gotov.getmeapp.main.profile.model.repository.ProfileRepository
import com.gotov.getmeapp.main.profile.viewmodel.ProfileViewModel
import com.gotov.getmeapp.main.search.model.api.SearchApi
import com.gotov.getmeapp.main.search.model.repository.SearchRepository
import com.gotov.getmeapp.main.search.viewmodel.SearchViewModel
import com.gotov.getmeapp.main.task.model.api.TaskApi
import com.gotov.getmeapp.main.task.model.repository.TaskRepository
import com.gotov.getmeapp.main.task.viewmodel.TaskViewModel
import com.gotov.getmeapp.sign.login.model.api.LoginApi
import com.gotov.getmeapp.sign.login.model.repository.LoginRepository
import com.gotov.getmeapp.sign.login.viewmodel.LoginViewModel
import com.gotov.getmeapp.sign.signup.model.api.RegisterApi
import com.gotov.getmeapp.sign.signup.model.repository.RegisterRepository
import com.gotov.getmeapp.sign.signup.viewmodel.RegisterViewModel
import com.gotov.getmeapp.utils.app.provideApi
import com.gotov.getmeapp.utils.app.provideRepository
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

private const val BASE_URL = "http://android.glidemess.pw/api/v1/"
private const val cacheSize = 10 * 1024 * 1024

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { PlanViewModel(get()) }
    viewModel { PlansViewModel(get()) }
    viewModel { TaskViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
}

val apiModule = module {
    single { provideApi<LoginApi>(get()) }
    single { provideApi<SearchApi>(get()) }
    single { provideApi<ProfileApi>(get()) }
    single { provideApi<PlanApi>(get()) }
    single { provideApi<PlansApi>(get()) }
    single { provideApi<TaskApi>(get()) }
    single { provideApi<RegisterApi>(get()) }
}

val netModule = module {
    fun provideCache(application: Application): Cache {
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    fun provideHttpClient(cache: Cache): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor(AddCookiesInterceptor())
            .addInterceptor(ReceivedCookiesInterceptor())
            .cache(cache)

        return okHttpClientBuilder.build()
    }

    fun provideJackson(): ObjectMapper {
        return ObjectMapper()
    }

    fun provideRetrofit(mapper: ObjectMapper, client: OkHttpClient): Retrofit {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
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
    single { provideRepository<PlanRepository, PlanApi>(get()) }
    single { provideRepository<PlansRepository, PlansApi>(get()) }
    single { provideRepository<TaskRepository, TaskApi>(get()) }
    single { provideRepository<RegisterRepository, RegisterApi>(get()) }
}
