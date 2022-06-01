package com.gotov.getmeapp.app.di

import android.util.Log
import com.gotov.getmeapp.app.preference.AppPreferences
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.IOException

class AddCookiesInterceptor : Interceptor, KoinComponent {
    private val pref by inject<AppPreferences>()

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        val cookies = pref.getHashSet(AppPreferences.Cookies)
        cookies?.let {
            for (cookie in cookies) {
                builder.addHeader("Cookie", cookie)
            }
        }

        Log.v(
            "OkHttp",
            "Adding Header: $cookies"
        )
        val request = builder.build()
        val buffer = Buffer()
        request.body()?.writeTo(buffer)
        Log.v(
            "Request Body",
            buffer.inputStream().reader().readText()
        )
        return chain.proceed(request)
    }
}

class ReceivedCookiesInterceptor : Interceptor, KoinComponent {
    private val pref by inject<AppPreferences>()

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse: Response = chain.proceed(chain.request())
        if (originalResponse.headers("Set-Cookie").isNotEmpty()) {
            val tmp: HashSet<String> = hashSetOf()
            for (header in originalResponse.headers("Set-Cookie")) {
                tmp.add(header)
            }
            pref.putHashSet(AppPreferences.Cookies, tmp)
        }
        return originalResponse
    }
}
