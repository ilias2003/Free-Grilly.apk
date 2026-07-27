package com.freegrilly.app.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    /**
     * Builds a fresh API client for the given device IP or hostname.
     * The device typically lives at 192.168.200.10 while in its own AP mode,
     * or at whatever IP your router assigns it once it joins your home WiFi
     * (visible in the Free-Grilly web UI, or via the wifi_ip field of GET /grill).
     */
    fun create(ipOrHost: String): FreeGrillyApi {
        val baseUrl = "http://${ipOrHost.trim().removePrefix("http://").removePrefix("https://").trimEnd('/')}/api/"

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(FreeGrillyApi::class.java)
    }
}
