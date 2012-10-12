// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.openvehicles.OVMS;

import java.io.Serializable;

public class CarData_Group
    implements Serializable
{

    public CarData_Group()
    {
        VehicleID = "";
        SOC = 0.0D;
        Speed = 0.0D;
        Direction = 0.0D;
        Altitude = 0.0D;
        GPSLocked = false;
        GPSDataStale = false;
        Latitude = 0.0D;
        Longitude = 0.0D;
        VehicleImageDrawable = "car_roadster_arcticwhite";
    }

    private static final long serialVersionUID = 0x9e20e380L;
    public double Altitude;
    public double Direction;
    public boolean GPSDataStale;
    public boolean GPSLocked;
    public double Latitude;
    public double Longitude;
    public double SOC;
    public double Speed;
    public String VehicleID;
    public String VehicleImageDrawable;
}
