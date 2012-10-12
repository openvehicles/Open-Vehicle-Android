.class Lcom/openvehicles/OVMS/TabMap$5;
.super Ljava/lang/Object;
.source "TabMap.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/openvehicles/OVMS/TabMap;
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
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabMap$5;->this$0:Lcom/openvehicles/OVMS/TabMap;

    .line 952
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 15

    .prologue
    const/4 v14, 0x1

    const/4 v13, 0x0

    .line 956
    const/4 v2, 0x0

    .line 957
    .local v2, groupCar:Lcom/openvehicles/OVMS/CarData_Group;
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$5;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabMap;->access$4(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v9

    iget-object v9, v9, Lcom/openvehicles/OVMS/CarData;->Group:Ljava/util/HashMap;

    invoke-virtual {v9}, Ljava/util/HashMap;->size()I

    move-result v9

    new-array v3, v9, [Ljava/lang/String;

    .line 958
    .local v3, groupCarVehicleIDs:[Ljava/lang/String;
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$5;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabMap;->access$4(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v9

    iget-object v9, v9, Lcom/openvehicles/OVMS/CarData;->Group:Ljava/util/HashMap;

    invoke-virtual {v9}, Ljava/util/HashMap;->keySet()Ljava/util/Set;

    move-result-object v9

    invoke-interface {v9, v3}, Ljava/util/Set;->toArray([Ljava/lang/Object;)[Ljava/lang/Object;

    .line 960
    const/4 v4, 0x0

    .local v4, i:I
    :goto_0
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$5;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->carMarkers:Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabMap;->access$5(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;

    move-result-object v9

    invoke-virtual {v9}, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->size()I

    move-result v9

    if-lt v4, v9, :cond_1

    .line 1026
    :goto_1
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$5;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->mapView:Lcom/google/android/maps/MapView;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabMap;->access$8(Lcom/openvehicles/OVMS/TabMap;)Lcom/google/android/maps/MapView;

    move-result-object v9

    invoke-virtual {v9}, Lcom/google/android/maps/MapView;->invalidate()V

    .line 1029
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$5;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->carMarkerAnimationFrame:I
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabMap;->access$7(Lcom/openvehicles/OVMS/TabMap;)I

    move-result v10

    add-int/lit8 v10, v10, 0x1

    #setter for: Lcom/openvehicles/OVMS/TabMap;->carMarkerAnimationFrame:I
    invoke-static {v9, v10}, Lcom/openvehicles/OVMS/TabMap;->access$9(Lcom/openvehicles/OVMS/TabMap;I)V

    const/16 v9, 0x28

    if-ge v10, v9, :cond_0

    .line 1030
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$5;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->carMarkerAnimationTimerHandler:Landroid/os/Handler;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabMap;->access$10(Lcom/openvehicles/OVMS/TabMap;)Landroid/os/Handler;

    move-result-object v9

    iget-object v10, p0, Lcom/openvehicles/OVMS/TabMap$5;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->animateCarMarker:Ljava/lang/Runnable;
    invoke-static {v10}, Lcom/openvehicles/OVMS/TabMap;->access$11(Lcom/openvehicles/OVMS/TabMap;)Ljava/lang/Runnable;

    move-result-object v10

    .line 1031
    const-wide/16 v11, 0x32

    .line 1030
    invoke-virtual {v9, v10, v11, v12}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    .line 1033
    :cond_0
    return-void

    .line 962
    :cond_1
    if-lez v4, :cond_3

    .line 964
    array-length v9, v3

    if-ge v9, v4, :cond_2

    .line 965
    const-string v9, "MAP"

    .line 967
    const-string v10, "ERROR! Found %s markers but only %s car data."

    const/4 v11, 0x2

    new-array v11, v11, [Ljava/lang/Object;

    .line 968
    iget-object v12, p0, Lcom/openvehicles/OVMS/TabMap$5;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->carMarkers:Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;
    invoke-static {v12}, Lcom/openvehicles/OVMS/TabMap;->access$5(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;

    move-result-object v12

    invoke-virtual {v12}, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->size()I

    move-result v12

    invoke-static {v12}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v12

    aput-object v12, v11, v13

    .line 969
    array-length v12, v3

    add-int/lit8 v12, v12, 0x1

    invoke-static {v12}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v12

    aput-object v12, v11, v14

    .line 966
    invoke-static {v10, v11}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v10

    .line 965
    invoke-static {v9, v10}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_1

    .line 973
    :cond_2
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$5;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabMap;->access$4(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v9

    iget-object v9, v9, Lcom/openvehicles/OVMS/CarData;->Group:Ljava/util/HashMap;

    add-int/lit8 v10, v4, -0x1

    aget-object v10, v3, v10

    invoke-virtual {v9, v10}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    .end local v2           #groupCar:Lcom/openvehicles/OVMS/CarData_Group;
    check-cast v2, Lcom/openvehicles/OVMS/CarData_Group;

    .line 976
    .restart local v2       #groupCar:Lcom/openvehicles/OVMS/CarData_Group;
    :cond_3
    const/4 v8, 0x0

    .line 978
    .local v8, transitionalPoint:Lcom/google/android/maps/GeoPoint;
    if-nez v4, :cond_4

    .line 979
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$5;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabMap;->access$4(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v9

    invoke-static {v9}, Lcom/openvehicles/OVMS/Utilities;->GetCarGeopoint(Lcom/openvehicles/OVMS/CarData;)Lcom/google/android/maps/GeoPoint;

    move-result-object v0

    .line 985
    .local v0, carActualGeopoint:Lcom/google/android/maps/GeoPoint;
    :goto_2
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$5;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->carMarkers:Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabMap;->access$5(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;

    move-result-object v9

    invoke-virtual {v9, v4}, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->getItem(I)Lcom/google/android/maps/OverlayItem;

    move-result-object v9

    invoke-virtual {v9}, Lcom/google/android/maps/OverlayItem;->getPoint()Lcom/google/android/maps/GeoPoint;

    move-result-object v9

    invoke-virtual {v9, v0}, Lcom/google/android/maps/GeoPoint;->equals(Ljava/lang/Object;)Z

    move-result v9

    if-eqz v9, :cond_5

    .line 960
    :goto_3
    add-int/lit8 v4, v4, 0x1

    goto/16 :goto_0

    .line 982
    .end local v0           #carActualGeopoint:Lcom/google/android/maps/GeoPoint;
    :cond_4
    iget-wide v9, v2, Lcom/openvehicles/OVMS/CarData_Group;->Latitude:D

    iget-wide v11, v2, Lcom/openvehicles/OVMS/CarData_Group;->Longitude:D

    .line 981
    invoke-static {v9, v10, v11, v12}, Lcom/openvehicles/OVMS/Utilities;->GetCarGeopoint(DD)Lcom/google/android/maps/GeoPoint;

    move-result-object v0

    .restart local v0       #carActualGeopoint:Lcom/google/android/maps/GeoPoint;
    goto :goto_2

    .line 989
    :cond_5
    invoke-virtual {v0}, Lcom/google/android/maps/GeoPoint;->getLatitudeE6()I

    move-result v9

    iget-object v10, p0, Lcom/openvehicles/OVMS/TabMap$5;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->lastCarGeoPoints:[Lcom/google/android/maps/GeoPoint;
    invoke-static {v10}, Lcom/openvehicles/OVMS/TabMap;->access$6(Lcom/openvehicles/OVMS/TabMap;)[Lcom/google/android/maps/GeoPoint;

    move-result-object v10

    aget-object v10, v10, v4

    .line 990
    invoke-virtual {v10}, Lcom/google/android/maps/GeoPoint;->getLatitudeE6()I

    move-result v10

    .line 989
    sub-int/2addr v9, v10

    div-int/lit8 v6, v9, 0x28

    .line 991
    .local v6, stepsLat:I
    invoke-virtual {v0}, Lcom/google/android/maps/GeoPoint;->getLongitudeE6()I

    move-result v9

    iget-object v10, p0, Lcom/openvehicles/OVMS/TabMap$5;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->lastCarGeoPoints:[Lcom/google/android/maps/GeoPoint;
    invoke-static {v10}, Lcom/openvehicles/OVMS/TabMap;->access$6(Lcom/openvehicles/OVMS/TabMap;)[Lcom/google/android/maps/GeoPoint;

    move-result-object v10

    aget-object v10, v10, v4

    .line 992
    invoke-virtual {v10}, Lcom/google/android/maps/GeoPoint;->getLongitudeE6()I

    move-result v10

    .line 991
    sub-int/2addr v9, v10

    div-int/lit8 v7, v9, 0x28

    .line 994
    .local v7, stepsLong:I
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$5;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->carMarkerAnimationFrame:I
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabMap;->access$7(Lcom/openvehicles/OVMS/TabMap;)I

    move-result v9

    const/16 v10, 0x27

    if-ne v9, v10, :cond_7

    .line 995
    move-object v8, v0

    .line 1003
    :goto_4
    const-string v9, "MAP"

    new-instance v10, Ljava/lang/StringBuilder;

    const-string v11, "Car Marker "

    invoke-direct {v10, v11}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v10, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v10

    const-string v11, " Transitional Point: "

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    .line 1004
    invoke-virtual {v8}, Lcom/google/android/maps/GeoPoint;->getLatitudeE6()I

    move-result v11

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v10

    const-string v11, ", "

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    .line 1005
    invoke-virtual {v8}, Lcom/google/android/maps/GeoPoint;->getLongitudeE6()I

    move-result v11

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    .line 1003
    invoke-static {v9, v10}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1008
    if-nez v4, :cond_8

    .line 1010
    const-string v5, "-"

    .line 1011
    .local v5, lastReportDate:Ljava/lang/String;
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$5;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabMap;->access$4(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v9

    iget-object v9, v9, Lcom/openvehicles/OVMS/CarData;->Data_LastCarUpdate:Ljava/util/Date;

    if-eqz v9, :cond_6

    .line 1012
    new-instance v9, Ljava/text/SimpleDateFormat;

    .line 1013
    const-string v10, "MMM d, K:mm:ss a"

    .line 1012
    invoke-direct {v9, v10}, Ljava/text/SimpleDateFormat;-><init>(Ljava/lang/String;)V

    .line 1014
    iget-object v10, p0, Lcom/openvehicles/OVMS/TabMap$5;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v10}, Lcom/openvehicles/OVMS/TabMap;->access$4(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v10

    iget-object v10, v10, Lcom/openvehicles/OVMS/CarData;->Data_LastCarUpdate:Ljava/util/Date;

    invoke-virtual {v9, v10}, Ljava/text/SimpleDateFormat;->format(Ljava/util/Date;)Ljava/lang/String;

    move-result-object v5

    .line 1016
    :cond_6
    new-instance v1, Lcom/openvehicles/OVMS/Utilities$CarMarker;

    .line 1017
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$5;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabMap;->access$4(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v9

    iget-object v9, v9, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    const-string v10, "Last reported: %s"

    new-array v11, v14, [Ljava/lang/Object;

    .line 1018
    aput-object v5, v11, v13

    .line 1017
    invoke-static {v10, v11}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v10

    .line 1018
    iget-object v11, p0, Lcom/openvehicles/OVMS/TabMap$5;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v11}, Lcom/openvehicles/OVMS/TabMap;->access$4(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v11

    iget-wide v11, v11, Lcom/openvehicles/OVMS/CarData;->Data_Direction:D

    double-to-int v11, v11

    .line 1016
    invoke-direct {v1, v8, v9, v10, v11}, Lcom/openvehicles/OVMS/Utilities$CarMarker;-><init>(Lcom/google/android/maps/GeoPoint;Ljava/lang/String;Ljava/lang/String;I)V

    .line 1024
    .end local v5           #lastReportDate:Ljava/lang/String;
    .local v1, carMarker:Lcom/openvehicles/OVMS/Utilities$CarMarker;
    :goto_5
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$5;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->carMarkers:Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabMap;->access$5(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;

    move-result-object v9

    invoke-virtual {v9, v4, v1}, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->setOverlay(ILcom/google/android/maps/OverlayItem;)V

    goto/16 :goto_3

    .line 997
    .end local v1           #carMarker:Lcom/openvehicles/OVMS/Utilities$CarMarker;
    :cond_7
    new-instance v8, Lcom/google/android/maps/GeoPoint;

    .line 998
    .end local v8           #transitionalPoint:Lcom/google/android/maps/GeoPoint;
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$5;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->lastCarGeoPoints:[Lcom/google/android/maps/GeoPoint;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabMap;->access$6(Lcom/openvehicles/OVMS/TabMap;)[Lcom/google/android/maps/GeoPoint;

    move-result-object v9

    aget-object v9, v9, v4

    invoke-virtual {v9}, Lcom/google/android/maps/GeoPoint;->getLatitudeE6()I

    move-result v9

    .line 999
    iget-object v10, p0, Lcom/openvehicles/OVMS/TabMap$5;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->carMarkerAnimationFrame:I
    invoke-static {v10}, Lcom/openvehicles/OVMS/TabMap;->access$7(Lcom/openvehicles/OVMS/TabMap;)I

    move-result v10

    mul-int/2addr v10, v6

    .line 998
    add-int/2addr v9, v10

    .line 1000
    iget-object v10, p0, Lcom/openvehicles/OVMS/TabMap$5;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->lastCarGeoPoints:[Lcom/google/android/maps/GeoPoint;
    invoke-static {v10}, Lcom/openvehicles/OVMS/TabMap;->access$6(Lcom/openvehicles/OVMS/TabMap;)[Lcom/google/android/maps/GeoPoint;

    move-result-object v10

    aget-object v10, v10, v4

    invoke-virtual {v10}, Lcom/google/android/maps/GeoPoint;->getLongitudeE6()I

    move-result v10

    .line 1001
    iget-object v11, p0, Lcom/openvehicles/OVMS/TabMap$5;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->carMarkerAnimationFrame:I
    invoke-static {v11}, Lcom/openvehicles/OVMS/TabMap;->access$7(Lcom/openvehicles/OVMS/TabMap;)I

    move-result v11

    mul-int/2addr v11, v7

    .line 1000
    add-int/2addr v10, v11

    .line 997
    invoke-direct {v8, v9, v10}, Lcom/google/android/maps/GeoPoint;-><init>(II)V

    .restart local v8       #transitionalPoint:Lcom/google/android/maps/GeoPoint;
    goto/16 :goto_4

    .line 1021
    :cond_8
    new-instance v1, Lcom/openvehicles/OVMS/Utilities$CarMarker;

    .line 1022
    iget-object v9, v2, Lcom/openvehicles/OVMS/CarData_Group;->VehicleID:Ljava/lang/String;

    const-string v10, ""

    iget-wide v11, v2, Lcom/openvehicles/OVMS/CarData_Group;->Direction:D

    double-to-int v11, v11

    .line 1021
    invoke-direct {v1, v8, v9, v10, v11}, Lcom/openvehicles/OVMS/Utilities$CarMarker;-><init>(Lcom/google/android/maps/GeoPoint;Ljava/lang/String;Ljava/lang/String;I)V

    .restart local v1       #carMarker:Lcom/openvehicles/OVMS/Utilities$CarMarker;
    goto :goto_5
.end method
