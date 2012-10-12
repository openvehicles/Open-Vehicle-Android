.class public Lcom/openvehicles/OVMS/CarData;
.super Ljava/lang/Object;
.source "CarData.java"

# interfaces
.implements Ljava/io/Serializable;


# static fields
.field private static final serialVersionUID:J = 0x7ddc55fdd8e00626L


# instance fields
.field public CarPass:Ljava/lang/String;

.field public Data_BonnetOpen:Z

.field public Data_CarLocked:Z

.field public Data_CarModuleFirmwareVersion:Ljava/lang/String;

.field public Data_CarModuleGSMSignalLevel:Ljava/lang/String;

.field public Data_CarPoweredON:Z

.field public Data_CarsConnected:I

.field public Data_ChargeCurrent:I

.field public Data_ChargeMode:Ljava/lang/String;

.field public Data_ChargePortOpen:Z

.field public Data_ChargeState:Ljava/lang/String;

.field public Data_Charging:Z

.field public Data_DistanceUnit:Ljava/lang/String;

.field public Data_EstimatedRange:I

.field public Data_FLWheelPressure:D

.field public Data_FLWheelTemperature:D

.field public Data_FRWheelPressure:D

.field public Data_FRWheelTemperature:D

.field public Data_HandBrakeApplied:Z

.field public Data_IdealRange:I

.field public Data_LastCarUpdate:Ljava/util/Date;

.field public Data_LastCarUpdate_raw:J

.field public Data_Latitude:D

.field public Data_LeftDoorOpen:Z

.field public Data_LineVoltage:I

.field public Data_Longitude:D

.field public Data_OVMSServerFirmwareVersion:Ljava/lang/String;

.field public Data_Odometer:D

.field public Data_PilotPresent:Z

.field public Data_RLWheelPressure:D

.field public Data_RLWheelTemperature:D

.field public Data_RRWheelPressure:D

.field public Data_RRWheelTemperature:D

.field public Data_RightDoorOpen:Z

.field public Data_SOC:I

.field public Data_Speed:D

.field public Data_TemperatureBattery:D

.field public Data_TemperatureMotor:D

.field public Data_TemperaturePEM:D

.field public Data_TripMeter:D

.field public Data_TrunkOpen:Z

.field public Data_VIN:Ljava/lang/String;

.field public ParanoidMode:Z

.field public ServerNameOrIP:Ljava/lang/String;

.field public UserPass:Ljava/lang/String;

.field public VehicleID:Ljava/lang/String;

.field public VehicleImageDrawable:Ljava/lang/String;


# direct methods
.method public constructor <init>()V
    .locals 5

    .prologue
    const-wide/16 v3, 0x0

    const/4 v2, 0x0

    .line 6
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 11
    const-string v0, "www.openvehicles.com"

    iput-object v0, p0, Lcom/openvehicles/OVMS/CarData;->ServerNameOrIP:Ljava/lang/String;

    .line 19
    iput v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_CarsConnected:I

    .line 20
    iput v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_SOC:I

    .line 21
    const-string v0, ""

    iput-object v0, p0, Lcom/openvehicles/OVMS/CarData;->Data_DistanceUnit:Ljava/lang/String;

    .line 22
    iput v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_LineVoltage:I

    .line 23
    iput v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_ChargeCurrent:I

    .line 24
    const-string v0, ""

    iput-object v0, p0, Lcom/openvehicles/OVMS/CarData;->Data_ChargeState:Ljava/lang/String;

    .line 25
    const-string v0, ""

    iput-object v0, p0, Lcom/openvehicles/OVMS/CarData;->Data_ChargeMode:Ljava/lang/String;

    .line 26
    iput v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_IdealRange:I

    .line 27
    iput v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_EstimatedRange:I

    .line 29
    const-wide/16 v0, 0x0

    iput-wide v0, p0, Lcom/openvehicles/OVMS/CarData;->Data_LastCarUpdate_raw:J

    .line 32
    iput-wide v3, p0, Lcom/openvehicles/OVMS/CarData;->Data_Latitude:D

    .line 33
    iput-wide v3, p0, Lcom/openvehicles/OVMS/CarData;->Data_Longitude:D

    .line 36
    iput-boolean v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_LeftDoorOpen:Z

    .line 37
    iput-boolean v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_RightDoorOpen:Z

    .line 38
    iput-boolean v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_ChargePortOpen:Z

    .line 39
    iput-boolean v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_PilotPresent:Z

    .line 40
    iput-boolean v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_Charging:Z

    .line 41
    iput-boolean v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_HandBrakeApplied:Z

    .line 42
    iput-boolean v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_CarPoweredON:Z

    .line 45
    iput-boolean v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_BonnetOpen:Z

    .line 46
    iput-boolean v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_TrunkOpen:Z

    .line 49
    iput-boolean v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_CarLocked:Z

    .line 50
    iput-wide v3, p0, Lcom/openvehicles/OVMS/CarData;->Data_TemperaturePEM:D

    .line 51
    iput-wide v3, p0, Lcom/openvehicles/OVMS/CarData;->Data_TemperatureMotor:D

    .line 52
    iput-wide v3, p0, Lcom/openvehicles/OVMS/CarData;->Data_TemperatureBattery:D

    .line 53
    iput-wide v3, p0, Lcom/openvehicles/OVMS/CarData;->Data_TripMeter:D

    .line 54
    iput-wide v3, p0, Lcom/openvehicles/OVMS/CarData;->Data_Odometer:D

    .line 55
    iput-wide v3, p0, Lcom/openvehicles/OVMS/CarData;->Data_Speed:D

    .line 58
    const-string v0, ""

    iput-object v0, p0, Lcom/openvehicles/OVMS/CarData;->Data_CarModuleFirmwareVersion:Ljava/lang/String;

    .line 59
    const-string v0, ""

    iput-object v0, p0, Lcom/openvehicles/OVMS/CarData;->Data_VIN:Ljava/lang/String;

    .line 60
    const-string v0, ""

    iput-object v0, p0, Lcom/openvehicles/OVMS/CarData;->Data_CarModuleGSMSignalLevel:Ljava/lang/String;

    .line 63
    const-string v0, ""

    iput-object v0, p0, Lcom/openvehicles/OVMS/CarData;->Data_OVMSServerFirmwareVersion:Ljava/lang/String;

    .line 66
    iput-wide v3, p0, Lcom/openvehicles/OVMS/CarData;->Data_FRWheelPressure:D

    .line 67
    iput-wide v3, p0, Lcom/openvehicles/OVMS/CarData;->Data_FRWheelTemperature:D

    .line 68
    iput-wide v3, p0, Lcom/openvehicles/OVMS/CarData;->Data_RRWheelPressure:D

    .line 69
    iput-wide v3, p0, Lcom/openvehicles/OVMS/CarData;->Data_RRWheelTemperature:D

    .line 70
    iput-wide v3, p0, Lcom/openvehicles/OVMS/CarData;->Data_FLWheelPressure:D

    .line 71
    iput-wide v3, p0, Lcom/openvehicles/OVMS/CarData;->Data_FLWheelTemperature:D

    .line 72
    iput-wide v3, p0, Lcom/openvehicles/OVMS/CarData;->Data_RLWheelPressure:D

    .line 73
    iput-wide v3, p0, Lcom/openvehicles/OVMS/CarData;->Data_RLWheelTemperature:D

    .line 75
    iput-boolean v2, p0, Lcom/openvehicles/OVMS/CarData;->ParanoidMode:Z

    return-void
.end method
