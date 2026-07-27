package com.freegrilly.app.model

import com.google.gson.annotations.SerializedName

/** GET /api/grill — overview + live probe temperatures */
data class Grill(
    val name: String? = null,
    @SerializedName("unique_id") val uniqueId: String? = null,
    @SerializedName("firmware_version") val firmwareVersion: String? = null,
    @SerializedName("battery_percentage") val batteryPercentage: Int? = null,
    @SerializedName("battery_charging") val batteryCharging: Boolean? = null,
    @SerializedName("wifi_connected") val wifiConnected: Boolean? = null,
    @SerializedName("wifi_ssid") val wifiSsid: String? = null,
    @SerializedName("wifi_signal") val wifiSignal: Int? = null,
    @SerializedName("wifi_ip") val wifiIp: String? = null,
    @SerializedName("temperature_unit") val temperatureUnit: String? = null,
    val probes: List<GrillProbe> = emptyList()
)

/** Probe entry as returned inline inside /api/grill */
data class GrillProbe(
    @SerializedName("probe_id") val probeId: Int,
    val name: String? = null,
    val temperature: Double? = null,
    @SerializedName("minimum_temperature") val minimumTemperature: Double? = null,
    @SerializedName("target_temperature") val targetTemperature: Double? = null,
    val connected: Boolean = false
)

/** GET/POST /api/probes — full probe configuration */
data class Probe(
    @SerializedName("probe_id") val probeId: Int,
    var name: String? = null,
    val temperature: Double? = null, // read-only
    @SerializedName("minimum_temperature") var minimumTemperature: Double? = null,
    @SerializedName("target_temperature") var targetTemperature: Double? = null,
    val connected: Boolean? = null, // read-only
    @SerializedName("probe_type") var probeType: String? = "grilleye_iris",
    @SerializedName("reference_kohm") var referenceKohm: Double? = null,
    @SerializedName("reference_celcius") var referenceCelcius: Double? = null,
    @SerializedName("reference_beta") var referenceBeta: Double? = null
)

val PROBE_TYPES = listOf("grilleye_iris", "ikea_fantast", "maverick_et733", "weber_igrill", "custom")

/** GET/POST /api/settings */
data class Settings(
    val name: String? = null,
    @SerializedName("unique_id") val uniqueId: String? = null,
    @SerializedName("firmware_version") val firmwareVersion: String? = null,
    @SerializedName("temperature_unit") var temperatureUnit: String? = null,
    @SerializedName("beep_enabled") var beepEnabled: Boolean? = null,
    @SerializedName("beep_on_ready") var beepOnReady: Boolean? = null,
    @SerializedName("beep_outside_target") var beepOutsideTarget: Boolean? = null,
    @SerializedName("beep_volume") var beepVolume: Int? = null,
    @SerializedName("beep_degrees_before") var beepDegreesBefore: Int? = null,
    @SerializedName("screen_timeout_minutes") var screenTimeoutMinutes: Int? = null,
    @SerializedName("backlight_timeout_minutes") var backlightTimeoutMinutes: Int? = null,
    @SerializedName("backlight_brightness") var backlightBrightness: Int? = null,
    @SerializedName("opengrill_server") var opengrillServer: String? = null,
    @SerializedName("mqtt_broker") var mqttBroker: String? = null,
    @SerializedName("mqtt_port") var mqttPort: Int? = null,
    @SerializedName("mqtt_topic") var mqttTopic: String? = null,
    @SerializedName("mqtt_user") var mqttUser: String? = null,
    @SerializedName("mqtt_password") var mqttPassword: String? = null,
    @SerializedName("wifi_ssid") var wifiSsid: String? = null,
    @SerializedName("wifi_password") var wifiPassword: String? = null,
    @SerializedName("wifi_ip") val wifiIp: String? = null,
    @SerializedName("wifi_subnet") val wifiSubnet: String? = null,
    @SerializedName("wifi_gateway") val wifiGateway: String? = null,
    @SerializedName("wifi_dns") val wifiDns: String? = null,
    @SerializedName("local_ap_ssid") val localApSsid: String? = null,
    @SerializedName("local_ap_password") val localApPassword: String? = null,
    @SerializedName("local_ap_ip") val localApIp: String? = null,
    @SerializedName("local_ap_subnet") val localApSubnet: String? = null,
    @SerializedName("local_ap_gateway") val localApGateway: String? = null
)

data class WifiNetwork(
    val ssid: String? = null,
    @SerializedName("signal_strength") val signalStrength: Int? = null,
    @SerializedName("auth_method") val authMethod: String? = null
)
