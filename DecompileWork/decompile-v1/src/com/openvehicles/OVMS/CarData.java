// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.openvehicles.OVMS;

import java.io.Serializable;
import java.util.Date;

public class CarData
    implements Serializable
{

    public CarData()
    {
        ServerNameOrIP = "www.openvehicles.com";
        Data_CarsConnected = 0;
        Data_SOC = 0;
        Data_DistanceUnit = "";
        Data_LineVoltage = 0;
        Data_ChargeCurrent = 0;
        Data_ChargeState = "";
        Data_ChargeMode = "";
        Data_IdealRange = 0;
        Data_EstimatedRange = 0;
        Data_LastCarUpdate_raw = 0L;
        Data_Latitude = 0.0D;
        Data_Longitude = 0.0D;
        Data_LeftDoorOpen = false;
        Data_RightDoorOpen = false;
        Data_ChargePortOpen = false;
        Data_PilotPresent = false;
        Data_Charging = false;
        Data_HandBrakeApplied = false;
        Data_CarPoweredON = false;
        Data_BonnetOpen = false;
        Data_TrunkOpen = false;
        Data_CarLocked = false;
        Data_TemperaturePEM = 0.0D;
        Data_TemperatureMotor = 0.0D;
        Data_TemperatureBattery = 0.0D;
        Data_TripMeter = 0.0D;
        Data_Odometer = 0.0D;
        Data_Speed = 0.0D;
        Data_CarModuleFirmwareVersion = "";
        Data_VIN = "";
        Data_CarModuleGSMSignalLevel = "";
        Data_OVMSServerFirmwareVersion = "";
        Data_FRWheelPressure = 0.0D;
        Data_FRWheelTemperature = 0.0D;
        Data_RRWheelPressure = 0.0D;
        Data_RRWheelTemperature = 0.0D;
        Data_FLWheelPressure = 0.0D;
        Data_FLWheelTemperature = 0.0D;
        Data_RLWheelPressure = 0.0D;
        Data_RLWheelTemperature = 0.0D;
        ParanoidMode = false;
    }

    private static final long serialVersionUID = 0xd8e00626L;
    public String CarPass;
    public boolean Data_BonnetOpen;
    public boolean Data_CarLocked;
    public String Data_CarModuleFirmwareVersion;
    public String Data_CarModuleGSMSignalLevel;
    public boolean Data_CarPoweredON;
    public int Data_CarsConnected;
    public int Data_ChargeCurrent;
    public String Data_ChargeMode;
    public boolean Data_ChargePortOpen;
    public String Data_ChargeState;
    public boolean Data_Charging;
    public String Data_DistanceUnit;
    public int Data_EstimatedRange;
    public double Data_FLWheelPressure;
    public double Data_FLWheelTemperature;
    public double Data_FRWheelPressure;
    public double Data_FRWheelTemperature;
    public boolean Data_HandBrakeApplied;
    public int Data_IdealRange;
    public Date Data_LastCarUpdate;
    public long Data_LastCarUpdate_raw;
    public double Data_Latitude;
    public boolean Data_LeftDoorOpen;
    public int Data_LineVoltage;
    public double Data_Longitude;
    public String Data_OVMSServerFirmwareVersion;
    public double Data_Odometer;
    public boolean Data_PilotPresent;
    public double Data_RLWheelPressure;
    public double Data_RLWheelTemperature;
    public double Data_RRWheelPressure;
    public double Data_RRWheelTemperature;
    public boolean Data_RightDoorOpen;
    public int Data_SOC;
    public double Data_Speed;
    public double Data_TemperatureBattery;
    public double Data_TemperatureMotor;
    public double Data_TemperaturePEM;
    public double Data_TripMeter;
    public boolean Data_TrunkOpen;
    public String Data_VIN;
    public boolean ParanoidMode;
    public String ServerNameOrIP;
    public String UserPass;
    public String VehicleID;
    public String VehicleImageDrawable;
}
