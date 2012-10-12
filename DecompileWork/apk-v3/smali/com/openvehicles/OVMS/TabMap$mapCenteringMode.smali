.class Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;
.super Ljava/lang/Object;
.source "TabMap.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/openvehicles/OVMS/TabMap;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "mapCenteringMode"
.end annotation


# static fields
.field public static final CAR:I = 0x2

.field public static final CUSTOM:I = 0x4

.field public static final DEFAULT:I = 0x0

.field public static final DEVICE:I = 0x1

.field public static final GROUPCAR:I = 0x5

.field public static final ROUTE:I = 0x3


# instance fields
.field public GroupCar_VehicleID:Ljava/lang/String;

.field private _centeringMode:I

.field private _listener:Lcom/openvehicles/OVMS/TabMap$OnCenteringModeChangedListener;

.field final synthetic this$0:Lcom/openvehicles/OVMS/TabMap;


# direct methods
.method private constructor <init>(Lcom/openvehicles/OVMS/TabMap;)V
    .locals 1
    .parameter

    .prologue
    .line 348
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;->this$0:Lcom/openvehicles/OVMS/TabMap;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 355
    const/4 v0, 0x0

    iput v0, p0, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;->_centeringMode:I

    .line 357
    const-string v0, ""

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;->GroupCar_VehicleID:Ljava/lang/String;

    .line 359
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;->_listener:Lcom/openvehicles/OVMS/TabMap$OnCenteringModeChangedListener;

    return-void
.end method

.method synthetic constructor <init>(Lcom/openvehicles/OVMS/TabMap;Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 348
    invoke-direct {p0, p1}, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;-><init>(Lcom/openvehicles/OVMS/TabMap;)V

    return-void
.end method

.method private declared-synchronized fireCenteringModeChangedEvent()V
    .locals 2

    .prologue
    .line 371
    monitor-enter p0

    :try_start_0
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;->_listener:Lcom/openvehicles/OVMS/TabMap$OnCenteringModeChangedListener;

    if-eqz v0, :cond_0

    .line 372
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;->_listener:Lcom/openvehicles/OVMS/TabMap$OnCenteringModeChangedListener;

    iget v1, p0, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;->_centeringMode:I

    invoke-interface {v0, v1}, Lcom/openvehicles/OVMS/TabMap$OnCenteringModeChangedListener;->OnCenteringModeChanged(I)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 373
    :cond_0
    monitor-exit p0

    return-void

    .line 371
    :catchall_0
    move-exception v0

    monitor-exit p0

    throw v0
.end method


# virtual methods
.method public getMode()I
    .locals 1

    .prologue
    .line 376
    iget v0, p0, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;->_centeringMode:I

    return v0
.end method

.method public declared-synchronized removeOnGroupCarTappedListener()V
    .locals 1

    .prologue
    .line 365
    monitor-enter p0

    const/4 v0, 0x0

    :try_start_0
    iput-object v0, p0, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;->_listener:Lcom/openvehicles/OVMS/TabMap$OnCenteringModeChangedListener;
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 366
    monitor-exit p0

    return-void

    .line 365
    :catchall_0
    move-exception v0

    monitor-exit p0

    throw v0
.end method

.method public setMode(I)V
    .locals 1
    .parameter "newCenteringMode"

    .prologue
    .line 380
    iget v0, p0, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;->_centeringMode:I

    if-ne v0, p1, :cond_0

    .line 385
    :goto_0
    return-void

    .line 383
    :cond_0
    iput p1, p0, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;->_centeringMode:I

    .line 384
    invoke-direct {p0}, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;->fireCenteringModeChangedEvent()V

    goto :goto_0
.end method

.method public declared-synchronized setOnMapCenteringModeChangedListener(Lcom/openvehicles/OVMS/TabMap$OnCenteringModeChangedListener;)V
    .locals 1
    .parameter "listener"

    .prologue
    .line 362
    monitor-enter p0

    :try_start_0
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;->_listener:Lcom/openvehicles/OVMS/TabMap$OnCenteringModeChangedListener;
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 363
    monitor-exit p0

    return-void

    .line 362
    :catchall_0
    move-exception v0

    monitor-exit p0

    throw v0
.end method
