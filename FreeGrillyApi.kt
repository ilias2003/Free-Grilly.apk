package com.freegrilly.app.api

import com.freegrilly.app.model.Grill
import com.freegrilly.app.model.Probe
import com.freegrilly.app.model.Settings
import com.freegrilly.app.model.WifiNetwork
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Matches docs/openapi.yaml from https://github.com/epiecs/free-grilly
 * Base URL is http://<device-ip>/api/  (default AP mode IP: 192.168.200.10)
 */
interface FreeGrillyApi {

    @GET("grill")
    suspend fun getGrill(): Grill

    @GET("probes")
    suspend fun getProbes(): List<Probe>

    /** You only need to send the probes you changed. */
    @POST("probes")
    suspend fun postProbes(@Body probes: List<Probe>): List<Probe>

    @GET("settings")
    suspend fun getSettings(): Settings

    @POST("settings")
    suspend fun postSettings(@Body settings: Settings): Settings

    /** Slow call (5-7s) — device scans nearby WiFi networks. */
    @GET("wifiscan")
    suspend fun wifiScan(): List<WifiNetwork>
}
