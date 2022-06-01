package com.gotov.getmeapp.app

import android.app.Application
import com.gotov.getmeapp.app.di.apiModule
import com.gotov.getmeapp.app.di.appPreferencesModule
import com.gotov.getmeapp.app.di.netModule
import com.gotov.getmeapp.app.di.repositoryModule
import com.gotov.getmeapp.app.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger(Level.DEBUG)
            modules(
                listOf(
                    viewModelModule,
                    repositoryModule,
                    netModule,
                    apiModule,
                    appPreferencesModule
                )
            )
        }
    }
}
