// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.openvehicles.OVMS;

import java.io.Serializable;
import java.util.*;

// Referenced classes of package com.openvehicles.OVMS:
//            GPRSUtilization

public class CarData
    implements Serializable
{

    public CarData()
    {
        lastResetVersion = 0;
        ServerNameOrIP = "tmc.openvehicles.com";
        VehicleID = "";
        RegPass = "";
        NetPass = "";
        DontAskLayoutDownload = false;
        Data_CarsConnected = 0;
        Data_SOC = 0;
        Data_DistanceUnit = "";
        Data_LineVoltage = 0;
        Data_ChargeCurrent = 0;
        Data_ChargeState = "";
        Data_ChargeMode = "";
        Data_IdealRange = 0;
        Data_EstimatedRange = 0;
        Data_ChargeAmpsLimit = 0;
        Data_ChargerB4State = 0;
        Data_ChargerKWHConsumed = 0.0D;
        Data_ChargeSubstate = 0;
        Data_ChargeState_raw = 0;
        Data_ChargeMode_raw = 0;
        Data_LastCarUpdate_raw = 0L;
        Data_Latitude = 0.0D;
        Data_Longitude = 0.0D;
        Data_Direction = 0.0D;
        Data_Altitude = 0.0D;
        Data_GPSLocked = false;
        Data_GPSDataStale = false;
        Data_LeftDoorOpen = false;
        Data_RightDoorOpen = false;
        Data_ChargePortOpen = false;
        Data_PilotPresent = false;
        Data_Charging = false;
        Data_HandBrakeApplied = false;
        Data_CarPoweredON = false;
        Data_PINLocked = false;
        Data_ValetON = false;
        Data_HeadlightsON = false;
        Data_BonnetOpen = false;
        Data_TrunkOpen = false;
        Data_CarLocked = false;
        Data_TemperaturePEM = 0.0D;
        Data_TemperatureMotor = 0.0D;
        Data_TemperatureBattery = 0.0D;
        Data_TripMeter = 0.0D;
        Data_Odometer = 0.0D;
        Data_Speed = 0.0D;
        Data_ParkedTime_raw = 0.0D;
        Data_TemperatureAmbient = 0.0D;
        Data_CoolingPumpON_DoorState3 = false;
        Data_PEM_Motor_Battery_TemperaturesDataStale = false;
        Data_AmbientTemperatureDataStale = false;
        Data_CarModuleFirmwareVersion = "";
        Data_VIN = "";
        Data_CarModuleGSMSignalLevel = "";
        Data_CANWriteEnabled = false;
        Data_CarType = "";
        Data_OVMSServerFirmwareVersion = "";
        Data_FRWheelPressure = 0.0D;
        Data_FRWheelTemperature = 0.0D;
        Data_RRWheelPressure = 0.0D;
        Data_RRWheelTemperature = 0.0D;
        Data_FLWheelPressure = 0.0D;
        Data_FLWheelTemperature = 0.0D;
        Data_RLWheelPressure = 0.0D;
        Data_RLWheelTemperature = 0.0D;
        Data_TPMSDataStale = false;
        Data_Features = new LinkedHashMap();
        Data_Features_LastRefreshed = null;
        Data_Parameters = new LinkedHashMap();
        Data_Parameters_LastRefreshed = null;
        Data_GPRSUtilization = null;
        ParanoidMode = false;
    }

    private static final long serialVersionUID = 0xd8e00626L;
    public double Data_Altitude;
    public boolean Data_AmbientTemperatureDataStale;
    public boolean Data_BonnetOpen;
    public boolean Data_CANWriteEnabled;
    public boolean Data_CarLocked;
    public String Data_CarModuleFirmwareVersion;
    public String Data_CarModuleGSMSignalLevel;
    public boolean Data_CarPoweredON;
    public String Data_CarType;
    public int Data_CarsConnected;
    public int Data_ChargeAmpsLimit;
    public int Data_ChargeCurrent;
    public String Data_ChargeMode;
    public int Data_ChargeMode_raw;
    public boolean Data_ChargePortOpen;
    public String Data_ChargeState;
    public int Data_ChargeState_raw;
    public int Data_ChargeSubstate;
    public int Data_ChargerB4State;
    public double Data_ChargerKWHConsumed;
    public boolean Data_Charging;
    public boolean Data_CoolingPumpON_DoorState3;
    public double Data_Direction;
    public String Data_DistanceUnit;
    public int Data_EstimatedRange;
    public double Data_FLWheelPressure;
    public double Data_FLWheelTemperature;
    public double Data_FRWheelPressure;
    public double Data_FRWheelTemperature;
    public LinkedHashMap Data_Features;
    public Date Data_Features_LastRefreshed;
    public GPRSUtilization Data_GPRSUtilization;
    public boolean Data_GPSDataStale;
    public boolean Data_GPSLocked;
    public boolean Data_HandBrakeApplied;
    public boolean Data_HeadlightsON;
    public int Data_IdealRange;
    public Date Data_LastCarUpdate;
    public long Data_LastCarUpdate_raw;
    public double Data_Latitude;
    public boolean Data_LeftDoorOpen;
    public int Data_LineVoltage;
    public double Data_Longitude;
    public String Data_OVMSServerFirmwareVersion;
    public double Data_Odometer;
    public boolean Data_PEM_Motor_Battery_TemperaturesDataStale;
    public boolean Data_PINLocked;
    public LinkedHashMap Data_Parameters;
    public Date Data_Parameters_LastRefreshed;
    public Date Data_ParkedTime;
    public double Data_ParkedTime_raw;
    public boolean Data_PilotPresent;
    public double Data_RLWheelPressure;
    public double Data_RLWheelTemperature;
    public double Data_RRWheelPressure;
    public double Data_RRWheelTemperature;
    public boolean Data_RightDoorOpen;
    public int Data_SOC;
    public double Data_Speed;
    public boolean Data_TPMSDataStale;
    public double Data_TemperatureAmbient;
    public double Data_TemperatureBattery;
    public double Data_TemperatureMotor;
    public double Data_TemperaturePEM;
    public double Data_TripMeter;
    public boolean Data_TrunkOpen;
    public String Data_VIN;
    public boolean Data_ValetON;
    public transient boolean DontAskLayoutDownload;
    public HashMap Group;
    public String NetPass;
    public boolean ParanoidMode;
    public String RegPass;
    public String ServerNameOrIP;
    public String VehicleID;
    public String VehicleImageDrawable;
    public int lastResetVersion;
}
