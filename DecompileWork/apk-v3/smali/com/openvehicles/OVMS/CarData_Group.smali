.class public Lcom/openvehicles/OVMS/CarData_Group;
.super Ljava/lang/Object;
.source "CarData_Group.java"

# interfaces
.implements Ljava/io/Serializable;


# static fields
.field private static final serialVersionUID:J = -0x1aade02761df1c80L


# instance fields
.field public Altitude:D

.field public Direction:D

.field public GPSDataStale:Z

.field public GPSLocked:Z

.field public Latitude:D

.field public Longitude:D

.field public SOC:D

.field public Speed:D

.field public VehicleID:Ljava/lang/String;

.field public VehicleImageDrawable:Ljava/lang/String;


# direct methods
.method public constructor <init>()V
    .locals 4

    .prologue
    const/4 v3, 0x0

    const-wide/16 v1, 0x0

    .line 5
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 12
    const-string v0, ""

    iput-object v0, p0, Lcom/openvehicles/OVMS/CarData_Group;->VehicleID:Ljava/lang/String;

    .line 13
    iput-wide v1, p0, Lcom/openvehicles/OVMS/CarData_Group;->SOC:D

    .line 14
    iput-wide v1, p0, Lcom/openvehicles/OVMS/CarData_Group;->Speed:D

    .line 15
    iput-wide v1, p0, Lcom/openvehicles/OVMS/CarData_Group;->Direction:D

    .line 16
    iput-wide v1, p0, Lcom/openvehicles/OVMS/CarData_Group;->Altitude:D

    .line 17
    iput-boolean v3, p0, Lcom/openvehicles/OVMS/CarData_Group;->GPSLocked:Z

    .line 18
    iput-boolean v3, p0, Lcom/openvehicles/OVMS/CarData_Group;->GPSDataStale:Z

    .line 19
    iput-wide v1, p0, Lcom/openvehicles/OVMS/CarData_Group;->Latitude:D

    .line 20
    iput-wide v1, p0, Lcom/openvehicles/OVMS/CarData_Group;->Longitude:D

    .line 22
    const-string v0, "car_roadster_arcticwhite"

    iput-object v0, p0, Lcom/openvehicles/OVMS/CarData_Group;->VehicleImageDrawable:Ljava/lang/String;

    .line 5
    return-void
.end method
