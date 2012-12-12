package com.openvehicles.OVMS.api;

import com.openvehicles.OVMS.entities.CarData;

public interface ApiStstusObserver {
    void update(CarData pCarData);
}
