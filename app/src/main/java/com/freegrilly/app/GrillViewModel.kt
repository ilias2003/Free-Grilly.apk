package com.freegrilly.app

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.freegrilly.app.api.FreeGrillyApi
import com.freegrilly.app.api.RetrofitClient
import com.freegrilly.app.model.Grill
import com.freegrilly.app.model.Probe
import com.freegrilly.app.model.Settings
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val PREFS = "free_grilly_prefs"
private const val KEY_IP = "device_ip"
private const val DEFAULT_IP = "192.168.200.10" // Free-Grilly AP mode default

sealed class ConnectionState {
    object Disconnected : ConnectionState()
    object Connecting : ConnectionState()
    data class Connected(val grill: Grill) : ConnectionState()
    data class Error(val message: String) : ConnectionState()
}

class GrillViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    private var api: FreeGrillyApi? = null
    private var pollingJob: Job? = null

    private val _connectionState = MutableStateFlow<ConnectionState>(ConnectionState.Disconnected)
    val connectionState: StateFlow<ConnectionState> = _connectionState.asStateFlow()

    private val _lastIp = MutableStateFlow(prefs.getString(KEY_IP, DEFAULT_IP) ?: DEFAULT_IP)
    val lastIp: StateFlow<String> = _lastIp.asStateFlow()

    private val _settings = MutableStateFlow<Settings?>(null)
    val settings: StateFlow<Settings?> = _settings.asStateFlow()

    private val _probes = MutableStateFlow<List<Probe>>(emptyList())
    val probes: StateFlow<List<Probe>> = _probes.asStateFlow()

    private val _actionMessage = MutableStateFlow<String?>(null)
    val actionMessage: StateFlow<String?> = _actionMessage.asStateFlow()

    fun clearActionMessage() {
        _actionMessage.value = null
    }

    fun connect(ip: String) {
        prefs.edit().putString(KEY_IP, ip).apply()
        _lastIp.value = ip
        api = RetrofitClient.create(ip)

        pollingJob?.cancel()
        _connectionState.value = ConnectionState.Connecting

        pollingJob = viewModelScope.launch {
            var first = true
            while (true) {
                try {
                    val grill = api!!.getGrill()
                    _connectionState.value = ConnectionState.Connected(grill)
                    if (first) {
                        refreshProbes()
                        refreshSettings()
                        first = false
                    }
                } catch (e: Exception) {
                    _connectionState.value = ConnectionState.Error(
                        e.message ?: "Could not reach the device at $ip"
                    )
                }
                delay(3000)
            }
        }
    }

    fun disconnect() {
        pollingJob?.cancel()
        api = null
        _connectionState.value = ConnectionState.Disconnected
    }

    fun refreshProbes() {
        val client = api ?: return
        viewModelScope.launch {
            try {
                _probes.value = client.getProbes()
            } catch (_: Exception) { /* surfaced via connectionState already */ }
        }
    }

    fun refreshSettings() {
        val client = api ?: return
        viewModelScope.launch {
            try {
                _settings.value = client.getSettings()
            } catch (_: Exception) { }
        }
    }

    fun updateProbe(updated: Probe) {
        val client = api ?: return
        viewModelScope.launch {
            try {
                client.postProbes(listOf(updated))
                _actionMessage.value = "Probe ${updated.probeId} updated"
                refreshProbes()
            } catch (e: Exception) {
                _actionMessage.value = "Failed to update probe: ${e.message}"
            }
        }
    }

    fun updateSettings(updated: Settings) {
        val client = api ?: return
        viewModelScope.launch {
            try {
                val result = client.postSettings(updated)
                _settings.value = result
                _actionMessage.value = "Settings saved"
            } catch (e: Exception) {
                _actionMessage.value = "Failed to save settings: ${e.message}"
            }
        }
    }
}
