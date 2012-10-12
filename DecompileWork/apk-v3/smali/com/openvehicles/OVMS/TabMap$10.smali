.class Lcom/openvehicles/OVMS/TabMap$10;
.super Ljava/lang/Object;
.source "TabMap.java"

# interfaces
.implements Landroid/location/LocationListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/TabMap;->onCreate(Landroid/os/Bundle;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/openvehicles/OVMS/TabMap;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/TabMap;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabMap$10;->this$0:Lcom/openvehicles/OVMS/TabMap;

    .line 116
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onLocationChanged(Landroid/location/Location;)V
    .locals 8
    .parameter "location"

    .prologue
    const/4 v7, 0x1

    const/4 v6, 0x0

    const-wide v4, 0x412e848000000000L

    .line 120
    invoke-virtual {p1}, Landroid/location/Location;->getLatitude()D

    move-result-wide v2

    mul-double/2addr v2, v4

    double-to-int v0, v2

    .line 121
    .local v0, lat:I
    invoke-virtual {p1}, Landroid/location/Location;->getLongitude()D

    move-result-wide v2

    mul-double/2addr v2, v4

    double-to-int v1, v2

    .line 122
    .local v1, lng:I
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabMap$10;->this$0:Lcom/openvehicles/OVMS/TabMap;

    new-instance v3, Lcom/google/android/maps/GeoPoint;

    invoke-direct {v3, v0, v1}, Lcom/google/android/maps/GeoPoint;-><init>(II)V

    #setter for: Lcom/openvehicles/OVMS/TabMap;->lastKnownDeviceGeoPoint:Lcom/google/android/maps/GeoPoint;
    invoke-static {v2, v3}, Lcom/openvehicles/OVMS/TabMap;->access$23(Lcom/openvehicles/OVMS/TabMap;Lcom/google/android/maps/GeoPoint;)V

    .line 124
    const-string v2, "GPS"

    const-string v3, "lat: %s lng %s"

    const/4 v4, 0x2

    new-array v4, v4, [Ljava/lang/Object;

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    aput-object v5, v4, v6

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    aput-object v5, v4, v7

    invoke-static {v3, v4}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 127
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabMap$10;->this$0:Lcom/openvehicles/OVMS/TabMap;

    iget-object v2, v2, Lcom/openvehicles/OVMS/TabMap;->centeringMode:Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;

    invoke-virtual {v2}, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;->getMode()I

    move-result v2

    if-ne v2, v7, :cond_0

    .line 128
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabMap$10;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->refreshUIHandler:Landroid/os/Handler;
    invoke-static {v2}, Lcom/openvehicles/OVMS/TabMap;->access$22(Lcom/openvehicles/OVMS/TabMap;)Landroid/os/Handler;

    move-result-object v2

    invoke-virtual {v2, v6}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    .line 129
    :cond_0
    return-void
.end method

.method public onProviderDisabled(Ljava/lang/String;)V
    .locals 0
    .parameter "provider"

    .prologue
    .line 139
    return-void
.end method

.method public onProviderEnabled(Ljava/lang/String;)V
    .locals 0
    .parameter "provider"

    .prologue
    .line 136
    return-void
.end method

.method public onStatusChanged(Ljava/lang/String;ILandroid/os/Bundle;)V
    .locals 0
    .parameter "provider"
    .parameter "status"
    .parameter "extras"

    .prologue
    .line 133
    return-void
.end method
