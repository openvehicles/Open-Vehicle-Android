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
}
