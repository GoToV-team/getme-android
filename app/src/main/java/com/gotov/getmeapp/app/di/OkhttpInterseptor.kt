package com.gotov.getmeapp.app.di

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

object CookiesStore {
    private var cookies: HashSet<String> = hashSetOf()

    fun setCookies(v: String) {
        cookies.add(v)
    }

    fun getCookies(): HashSet<String> {
        return cookies
    }
}

class AddCookiesInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        val cookies = CookiesStore.getCookies()
        for (cookie in cookies) {
            builder.addHeader("Cookie", cookie)
        }
        Log.v(
            "OkHttp",
            "Adding Header: $cookies"
        ) // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
        return chain.proceed(builder.build())
    }
}

class ReceivedCookiesInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse: Response = chain.proceed(chain.request())
        if (originalResponse.headers("Set-Cookie").isNotEmpty()) {
            for (header in originalResponse.headers("Set-Cookie")) {
                CookiesStore.setCookies(header)
            }
        }
        return originalResponse
    }
}