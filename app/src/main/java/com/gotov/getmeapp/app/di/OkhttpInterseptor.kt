package com.gotov.getmeapp.app.di

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.gotov.getmeapp.app.App
import kotlinx.coroutines.currentCoroutineContext
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.prefs.Preferences

object CookiesStore {
    private var cookies: String = ""

    fun setCookies(v: String) {
        cookies = v
    }

    fun getCookies(): String {
        return cookies
    }
}

class AddCookiesInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        val cookie = CookiesStore.getCookies()
        builder.addHeader("Cookie", cookie)
        Log.v(
            "OkHttp",
            "Adding Header: $cookie"
        ) // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
        return chain.proceed(builder.build())
    }
}

class ReceivedCookiesInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse: Response = chain.proceed(chain.request())
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            val cookies: HashSet<String> = HashSet()
            for (header in originalResponse.headers("Set-Cookie")) {
                cookies.add(header)
            }

            CookiesStore.setCookies(cookies.toString())
        }
        return originalResponse
    }
}