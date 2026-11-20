package com.openvehicles.OVMS.utils

import com.openvehicles.OVMS.entities.CarData
import java.util.Locale

object VehicleConfigHelper {
    private const val PREF_PREFIX_KEY = "pref_vehicle_config_prefix_"

    /**
     * Returns the default vehicle prefix for configuration.
     * Default is "x" + carData.car_type in lowercase.
     */
    fun getDefaultPrefix(carData: CarData?): String {
        val carType = carData?.car_type?.trim() ?: ""
        return if (carType.isNotEmpty()) {
            "x" + carType.lowercase(Locale.ROOT)
        } else {
            "x"
        }
    }

    /**
     * Gets the configured vehicle prefix from preferences or falls back to default prefix.
     */
    fun getConfigPrefix(appPrefs: AppPrefs, carData: CarData?): String {
        val vehicleId = carData?.sel_vehicleid ?: return ""
        val defaultPrefix = getDefaultPrefix(carData)
        val saved = appPrefs.getData(PREF_PREFIX_KEY + vehicleId, "") ?: ""
        return if (saved.isNotBlank()) saved.trim().lowercase(Locale.ROOT) else defaultPrefix
    }

    /**
     * Sets the configured vehicle prefix for the given vehicle ID.
     */
    fun setConfigPrefix(appPrefs: AppPrefs, vehicleId: String?, prefix: String) {
        if (vehicleId.isNullOrBlank()) return
        appPrefs.saveData(PREF_PREFIX_KEY + vehicleId, prefix.trim().lowercase(Locale.ROOT))
    }

    const val MIN_SUPPORTED_FIRMWARE = "3.3.006-435"

    /**
     * Checks if the vehicle firmware meets the minimum version required for JavaScript (MP Cmd 8).
     */
    fun isFirmwareSupported(carData: CarData?, minVersion: String = MIN_SUPPORTED_FIRMWARE): Boolean {
        val fw = carData?.car_firmware?.trim() ?: return false
        if (fw.isEmpty()) return false

        val token = fw.split('/', ' ').firstOrNull { it.isNotBlank() } ?: return false
        val dashParts = token.split('-')
        val verPart = dashParts[0]
        val subVer = if (dashParts.size > 1) dashParts[1].takeWhile { it.isDigit() }.toIntOrNull() ?: 0 else 0

        val actualNums = verPart.split('.').mapNotNull { it.toIntOrNull() }.toMutableList()
        while (actualNums.size < 4) {
            actualNums.add(0)
        }

        val minToken = minVersion.split('-')
        val minVerPart = minToken[0]
        val minSubVer = if (minToken.size > 1) minToken[1].takeWhile { it.isDigit() }.toIntOrNull() ?: 0 else 0
        val targetNums = minVerPart.split('.').mapNotNull { it.toIntOrNull() }.toMutableList()
        while (targetNums.size < 4) {
            targetNums.add(0)
        }

        for (i in 0 until 4) {
            val a = actualNums[i]
            val t = targetNums[i]
            if (a > t) return true
            if (a < t) return false
        }
        return subVer >= minSubVer
    }
}
