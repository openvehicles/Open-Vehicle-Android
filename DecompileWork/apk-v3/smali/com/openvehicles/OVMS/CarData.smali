.class public Lcom/openvehicles/OVMS/CarData;
.super Ljava/lang/Object;
.source "CarData.java"

# interfaces
.implements Ljava/io/Serializable;


# static fields
.field private static final serialVersionUID:J = 0x7ddc55fdd8e00626L


# instance fields
.field public Data_Altitude:D

.field public Data_AmbientTemperatureDataStale:Z

.field public Data_BonnetOpen:Z

.field public Data_CANWriteEnabled:Z

.field public Data_CarLocked:Z

.field public Data_CarModuleFirmwareVersion:Ljava/lang/String;

.field public Data_CarModuleGSMSignalLevel:Ljava/lang/String;

.field public Data_CarPoweredON:Z

.field public Data_CarType:Ljava/lang/String;

.field public Data_CarsConnected:I

.field public Data_ChargeAmpsLimit:I

.field public Data_ChargeCurrent:I

.field public Data_ChargeMode:Ljava/lang/String;

.field public Data_ChargeMode_raw:I

.field public Data_ChargePortOpen:Z

.field public Data_ChargeState:Ljava/lang/String;

.field public Data_ChargeState_raw:I

.field public Data_ChargeSubstate:I

.field public Data_ChargerB4State:I

.field public Data_ChargerKWHConsumed:D

.field public Data_Charging:Z

.field public Data_CoolingPumpON_DoorState3:Z

.field public Data_Direction:D

.field public Data_DistanceUnit:Ljava/lang/String;

.field public Data_EstimatedRange:I

.field public Data_FLWheelPressure:D

.field public Data_FLWheelTemperature:D

.field public Data_FRWheelPressure:D

.field public Data_FRWheelTemperature:D

.field public Data_Features:Ljava/util/LinkedHashMap;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/LinkedHashMap",
            "<",
            "Ljava/lang/Integer;",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field public Data_Features_LastRefreshed:Ljava/util/Date;

.field public Data_GPRSUtilization:Lcom/openvehicles/OVMS/GPRSUtilization;

.field public Data_GPSDataStale:Z

.field public Data_GPSLocked:Z

.field public Data_HandBrakeApplied:Z

.field public Data_HeadlightsON:Z

.field public Data_IdealRange:I

.field public Data_LastCarUpdate:Ljava/util/Date;

.field public Data_LastCarUpdate_raw:J

.field public Data_Latitude:D

.field public Data_LeftDoorOpen:Z

.field public Data_LineVoltage:I

.field public Data_Longitude:D

.field public Data_OVMSServerFirmwareVersion:Ljava/lang/String;

.field public Data_Odometer:D

.field public Data_PEM_Motor_Battery_TemperaturesDataStale:Z

.field public Data_PINLocked:Z

.field public Data_Parameters:Ljava/util/LinkedHashMap;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/LinkedHashMap",
            "<",
            "Ljava/lang/Integer;",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field public Data_Parameters_LastRefreshed:Ljava/util/Date;

.field public Data_ParkedTime:Ljava/util/Date;

.field public Data_ParkedTime_raw:D

.field public Data_PilotPresent:Z

.field public Data_RLWheelPressure:D

.field public Data_RLWheelTemperature:D

.field public Data_RRWheelPressure:D

.field public Data_RRWheelTemperature:D

.field public Data_RightDoorOpen:Z

.field public Data_SOC:I

.field public Data_Speed:D

.field public Data_TPMSDataStale:Z

.field public Data_TemperatureAmbient:D

.field public Data_TemperatureBattery:D

.field public Data_TemperatureMotor:D

.field public Data_TemperaturePEM:D

.field public Data_TripMeter:D

.field public Data_TrunkOpen:Z

.field public Data_VIN:Ljava/lang/String;

.field public Data_ValetON:Z

.field public transient DontAskLayoutDownload:Z

.field public Group:Ljava/util/HashMap;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/HashMap",
            "<",
            "Ljava/lang/String;",
            "Lcom/openvehicles/OVMS/CarData_Group;",
            ">;"
        }
    .end annotation
.end field

.field public NetPass:Ljava/lang/String;

.field public ParanoidMode:Z

.field public RegPass:Ljava/lang/String;

.field public ServerNameOrIP:Ljava/lang/String;

.field public VehicleID:Ljava/lang/String;

.field public VehicleImageDrawable:Ljava/lang/String;

.field public lastResetVersion:I


# direct methods
.method public constructor <init>()V
    .locals 6

    .prologue
    const/4 v5, 0x0

    const-wide/16 v3, 0x0

    const/4 v2, 0x0

    .line 8
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 13
    iput v2, p0, Lcom/openvehicles/OVMS/CarData;->lastResetVersion:I

    .line 15
    const-string v0, "tmc.openvehicles.com"

    iput-object v0, p0, Lcom/openvehicles/OVMS/CarData;->ServerNameOrIP:Ljava/lang/String;

    .line 16
    const-string v0, ""

    iput-object v0, p0, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    .line 17
    const-string v0, ""

    iput-object v0, p0, Lcom/openvehicles/OVMS/CarData;->RegPass:Ljava/lang/String;

    .line 18
    const-string v0, ""

    iput-object v0, p0, Lcom/openvehicles/OVMS/CarData;->NetPass:Ljava/lang/String;

    .line 25
    iput-boolean v2, p0, Lcom/openvehicles/OVMS/CarData;->DontAskLayoutDownload:Z

    .line 28
    iput v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_CarsConnected:I

    .line 31
    iput v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_SOC:I

    .line 32
    const-string v0, ""

    iput-object v0, p0, Lcom/openvehicles/OVMS/CarData;->Data_DistanceUnit:Ljava/lang/String;

    .line 33
    iput v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_LineVoltage:I

    .line 34
    iput v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_ChargeCurrent:I

    .line 35
    const-string v0, ""

    iput-object v0, p0, Lcom/openvehicles/OVMS/CarData;->Data_ChargeState:Ljava/lang/String;

    .line 36
    const-string v0, ""

    iput-object v0, p0, Lcom/openvehicles/OVMS/CarData;->Data_ChargeMode:Ljava/lang/String;

    .line 37
    iput v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_IdealRange:I

    .line 38
    iput v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_EstimatedRange:I

    .line 39
    iput v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_ChargeAmpsLimit:I

    .line 40
    iput v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_ChargerB4State:I

    .line 41
    iput-wide v3, p0, Lcom/openvehicles/OVMS/CarData;->Data_ChargerKWHConsumed:D

    .line 42
    iput v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_ChargeSubstate:I

    .line 43
    iput v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_ChargeState_raw:I

    .line 44
    iput v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_ChargeMode_raw:I

    .line 48
    const-wide/16 v0, 0x0

    iput-wide v0, p0, Lcom/openvehicles/OVMS/CarData;->Data_LastCarUpdate_raw:J

    .line 51
    iput-wide v3, p0, Lcom/openvehicles/OVMS/CarData;->Data_Latitude:D

    .line 52
    iput-wide v3, p0, Lcom/openvehicles/OVMS/CarData;->Data_Longitude:D

    .line 53
    iput-wide v3, p0, Lcom/openvehicles/OVMS/CarData;->Data_Direction:D

    .line 54
    iput-wide v3, p0, Lcom/openvehicles/OVMS/CarData;->Data_Altitude:D

    .line 55
    iput-boolean v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_GPSLocked:Z

    .line 56
    iput-boolean v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_GPSDataStale:Z

    .line 59
    iput-boolean v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_LeftDoorOpen:Z

    .line 60
    iput-boolean v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_RightDoorOpen:Z

    .line 61
    iput-boolean v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_ChargePortOpen:Z

    .line 62
    iput-boolean v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_PilotPresent:Z

    .line 63
    iput-boolean v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_Charging:Z

    .line 64
    iput-boolean v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_HandBrakeApplied:Z

    .line 65
    iput-boolean v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_CarPoweredON:Z

    .line 68
    iput-boolean v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_PINLocked:Z

    .line 69
    iput-boolean v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_ValetON:Z

    .line 70
    iput-boolean v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_HeadlightsON:Z

    .line 71
    iput-boolean v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_BonnetOpen:Z

    .line 72
    iput-boolean v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_TrunkOpen:Z

    .line 75
    iput-boolean v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_CarLocked:Z

    .line 76
    iput-wide v3, p0, Lcom/openvehicles/OVMS/CarData;->Data_TemperaturePEM:D

    .line 77
    iput-wide v3, p0, Lcom/openvehicles/OVMS/CarData;->Data_TemperatureMotor:D

    .line 78
    iput-wide v3, p0, Lcom/openvehicles/OVMS/CarData;->Data_TemperatureBattery:D

    .line 79
    iput-wide v3, p0, Lcom/openvehicles/OVMS/CarData;->Data_TripMeter:D

    .line 80
    iput-wide v3, p0, Lcom/openvehicles/OVMS/CarData;->Data_Odometer:D

    .line 81
    iput-wide v3, p0, Lcom/openvehicles/OVMS/CarData;->Data_Speed:D

    .line 83
    iput-wide v3, p0, Lcom/openvehicles/OVMS/CarData;->Data_ParkedTime_raw:D

    .line 84
    iput-wide v3, p0, Lcom/openvehicles/OVMS/CarData;->Data_TemperatureAmbient:D

    .line 85
    iput-boolean v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_CoolingPumpON_DoorState3:Z

    .line 86
    iput-boolean v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_PEM_Motor_Battery_TemperaturesDataStale:Z

    .line 87
    iput-boolean v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_AmbientTemperatureDataStale:Z

    .line 90
    const-string v0, ""

    iput-object v0, p0, Lcom/openvehicles/OVMS/CarData;->Data_CarModuleFirmwareVersion:Ljava/lang/String;

    .line 91
    const-string v0, ""

    iput-object v0, p0, Lcom/openvehicles/OVMS/CarData;->Data_VIN:Ljava/lang/String;

    .line 92
    const-string v0, ""

    iput-object v0, p0, Lcom/openvehicles/OVMS/CarData;->Data_CarModuleGSMSignalLevel:Ljava/lang/String;

    .line 93
    iput-boolean v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_CANWriteEnabled:Z

    .line 94
    const-string v0, ""

    iput-object v0, p0, Lcom/openvehicles/OVMS/CarData;->Data_CarType:Ljava/lang/String;

    .line 97
    const-string v0, ""

    iput-object v0, p0, Lcom/openvehicles/OVMS/CarData;->Data_OVMSServerFirmwareVersion:Ljava/lang/String;

    .line 100
    iput-wide v3, p0, Lcom/openvehicles/OVMS/CarData;->Data_FRWheelPressure:D

    .line 101
    iput-wide v3, p0, Lcom/openvehicles/OVMS/CarData;->Data_FRWheelTemperature:D

    .line 102
    iput-wide v3, p0, Lcom/openvehicles/OVMS/CarData;->Data_RRWheelPressure:D

    .line 103
    iput-wide v3, p0, Lcom/openvehicles/OVMS/CarData;->Data_RRWheelTemperature:D

    .line 104
    iput-wide v3, p0, Lcom/openvehicles/OVMS/CarData;->Data_FLWheelPressure:D

    .line 105
    iput-wide v3, p0, Lcom/openvehicles/OVMS/CarData;->Data_FLWheelTemperature:D

    .line 106
    iput-wide v3, p0, Lcom/openvehicles/OVMS/CarData;->Data_RLWheelPressure:D

    .line 107
    iput-wide v3, p0, Lcom/openvehicles/OVMS/CarData;->Data_RLWheelTemperature:D

    .line 108
    iput-boolean v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_TPMSDataStale:Z

    .line 114
    new-instance v0, Ljava/util/LinkedHashMap;

    invoke-direct {v0}, Ljava/util/LinkedHashMap;-><init>()V

    iput-object v0, p0, Lcom/openvehicles/OVMS/CarData;->Data_Features:Ljava/util/LinkedHashMap;

    .line 115
    iput-object v5, p0, Lcom/openvehicles/OVMS/CarData;->Data_Features_LastRefreshed:Ljava/util/Date;

    .line 118
    new-instance v0, Ljava/util/LinkedHashMap;

    invoke-direct {v0}, Ljava/util/LinkedHashMap;-><init>()V

    iput-object v0, p0, Lcom/openvehicles/OVMS/CarData;->Data_Parameters:Ljava/util/LinkedHashMap;

    .line 119
    iput-object v5, p0, Lcom/openvehicles/OVMS/CarData;->Data_Parameters_LastRefreshed:Ljava/util/Date;

    .line 122
    iput-object v5, p0, Lcom/openvehicles/OVMS/CarData;->Data_GPRSUtilization:Lcom/openvehicles/OVMS/GPRSUtilization;

    .line 124
    iput-boolean v2, p0, Lcom/openvehicles/OVMS/CarData;->ParanoidMode:Z

    .line 8
    return-void
.end method
