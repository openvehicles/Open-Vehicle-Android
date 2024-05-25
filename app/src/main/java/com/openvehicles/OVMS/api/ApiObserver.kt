package com.openvehicles.OVMS.api

import com.openvehicles.OVMS.entities.CarData

interface ApiObserver {

    fun update(carData: CarData?)

    fun onServiceAvailable(service: ApiService)

    fun onServiceLoggedIn(service: ApiService?, isLoggedIn: Boolean)

}