package com.openvehicles.OVMS.api;

import com.openvehicles.OVMS.entities.CarData;

public interface ApiObserver {
    void update(CarData pCarData);
    void onServiceAvailable(ApiService pService);
}
