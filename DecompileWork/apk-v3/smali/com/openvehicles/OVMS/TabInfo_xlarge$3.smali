.class Lcom/openvehicles/OVMS/TabInfo_xlarge$3;
.super Ljava/lang/Object;
.source "TabInfo_xlarge.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/openvehicles/OVMS/TabInfo_xlarge;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/TabInfo_xlarge;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$3;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    .line 872
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 11

    .prologue
    const/4 v10, 0x0

    .line 877
    const-string v1, "-"

    .line 878
    .local v1, lastReportDate:Ljava/lang/String;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$3;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$6(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-object v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_LastCarUpdate:Ljava/util/Date;

    if-eqz v6, :cond_0

    .line 879
    new-instance v6, Ljava/text/SimpleDateFormat;

    const-string v7, "MMM d, K:mm:ss a"

    invoke-direct {v6, v7}, Ljava/text/SimpleDateFormat;-><init>(Ljava/lang/String;)V

    .line 880
    iget-object v7, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$3;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v7}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$6(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v7

    iget-object v7, v7, Lcom/openvehicles/OVMS/CarData;->Data_LastCarUpdate:Ljava/util/Date;

    invoke-virtual {v6, v7}, Ljava/text/SimpleDateFormat;->format(Ljava/util/Date;)Ljava/lang/String;

    move-result-object v1

    .line 882
    :cond_0
    const/4 v5, 0x0

    .line 883
    .local v5, transitionalPoint:Lcom/google/android/maps/GeoPoint;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$3;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$6(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    invoke-static {v6}, Lcom/openvehicles/OVMS/Utilities;->GetCarGeopoint(Lcom/openvehicles/OVMS/CarData;)Lcom/google/android/maps/GeoPoint;

    move-result-object v0

    .line 885
    .local v0, currentCarGeopoint:Lcom/google/android/maps/GeoPoint;
    invoke-virtual {v0}, Lcom/google/android/maps/GeoPoint;->getLatitudeE6()I

    move-result v6

    iget-object v7, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$3;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->lastCarGeoPoint:Lcom/google/android/maps/GeoPoint;
    invoke-static {v7}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$7(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Lcom/google/android/maps/GeoPoint;

    move-result-object v7

    .line 886
    invoke-virtual {v7}, Lcom/google/android/maps/GeoPoint;->getLatitudeE6()I

    move-result v7

    .line 885
    sub-int/2addr v6, v7

    div-int/lit8 v3, v6, 0x28

    .line 887
    .local v3, stepsLat:I
    invoke-virtual {v0}, Lcom/google/android/maps/GeoPoint;->getLongitudeE6()I

    move-result v6

    iget-object v7, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$3;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->lastCarGeoPoint:Lcom/google/android/maps/GeoPoint;
    invoke-static {v7}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$7(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Lcom/google/android/maps/GeoPoint;

    move-result-object v7

    .line 888
    invoke-virtual {v7}, Lcom/google/android/maps/GeoPoint;->getLongitudeE6()I

    move-result v7

    .line 887
    sub-int/2addr v6, v7

    div-int/lit8 v4, v6, 0x28

    .line 890
    .local v4, stepsLong:I
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$3;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->carMarkerAnimationFrame:I
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$8(Lcom/openvehicles/OVMS/TabInfo_xlarge;)I

    move-result v6

    const/16 v7, 0x27

    if-ne v6, v7, :cond_2

    .line 892
    move-object v5, v0

    .line 900
    :goto_0
    new-instance v2, Lcom/openvehicles/OVMS/Utilities$CarMarker;

    .line 901
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$3;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$6(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-object v6, v6, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    .line 902
    const-string v7, "Last reported: %s"

    const/4 v8, 0x1

    new-array v8, v8, [Ljava/lang/Object;

    aput-object v1, v8, v10

    .line 901
    invoke-static {v7, v8}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v7

    .line 903
    iget-object v8, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$3;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v8}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$6(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v8

    iget-wide v8, v8, Lcom/openvehicles/OVMS/CarData;->Data_Direction:D

    double-to-int v8, v8

    .line 900
    invoke-direct {v2, v5, v6, v7, v8}, Lcom/openvehicles/OVMS/Utilities$CarMarker;-><init>(Lcom/google/android/maps/GeoPoint;Ljava/lang/String;Ljava/lang/String;I)V

    .line 905
    .local v2, overlayitem:Lcom/openvehicles/OVMS/Utilities$CarMarker;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$3;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->carMarkers:Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$9(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;

    move-result-object v6

    invoke-virtual {v6, v10, v2}, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->setOverlay(ILcom/google/android/maps/OverlayItem;)V

    .line 906
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$3;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->mapView:Lcom/google/android/maps/MapView;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$10(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Lcom/google/android/maps/MapView;

    move-result-object v6

    invoke-virtual {v6}, Lcom/google/android/maps/MapView;->invalidate()V

    .line 909
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$3;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->carMarkerAnimationFrame:I
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$8(Lcom/openvehicles/OVMS/TabInfo_xlarge;)I

    move-result v7

    add-int/lit8 v7, v7, 0x1

    #setter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->carMarkerAnimationFrame:I
    invoke-static {v6, v7}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$11(Lcom/openvehicles/OVMS/TabInfo_xlarge;I)V

    const/16 v6, 0x28

    if-ge v7, v6, :cond_1

    .line 910
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$3;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->carMarkerAnimationTimerHandler:Landroid/os/Handler;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$12(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Landroid/os/Handler;

    move-result-object v6

    iget-object v7, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$3;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->animateCarMarker:Ljava/lang/Runnable;
    invoke-static {v7}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$13(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Ljava/lang/Runnable;

    move-result-object v7

    .line 911
    const-wide/16 v8, 0x32

    .line 910
    invoke-virtual {v6, v7, v8, v9}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    .line 913
    :cond_1
    return-void

    .line 894
    .end local v2           #overlayitem:Lcom/openvehicles/OVMS/Utilities$CarMarker;
    :cond_2
    new-instance v5, Lcom/google/android/maps/GeoPoint;

    .line 895
    .end local v5           #transitionalPoint:Lcom/google/android/maps/GeoPoint;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$3;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->lastCarGeoPoint:Lcom/google/android/maps/GeoPoint;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$7(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Lcom/google/android/maps/GeoPoint;

    move-result-object v6

    invoke-virtual {v6}, Lcom/google/android/maps/GeoPoint;->getLatitudeE6()I

    move-result v6

    .line 896
    iget-object v7, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$3;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->carMarkerAnimationFrame:I
    invoke-static {v7}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$8(Lcom/openvehicles/OVMS/TabInfo_xlarge;)I

    move-result v7

    mul-int/2addr v7, v3

    .line 895
    add-int/2addr v6, v7

    .line 897
    iget-object v7, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$3;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->lastCarGeoPoint:Lcom/google/android/maps/GeoPoint;
    invoke-static {v7}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$7(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Lcom/google/android/maps/GeoPoint;

    move-result-object v7

    invoke-virtual {v7}, Lcom/google/android/maps/GeoPoint;->getLongitudeE6()I

    move-result v7

    .line 898
    iget-object v8, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$3;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->carMarkerAnimationFrame:I
    invoke-static {v8}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$8(Lcom/openvehicles/OVMS/TabInfo_xlarge;)I

    move-result v8

    mul-int/2addr v8, v4

    .line 897
    add-int/2addr v7, v8

    .line 894
    invoke-direct {v5, v6, v7}, Lcom/google/android/maps/GeoPoint;-><init>(II)V

    .restart local v5       #transitionalPoint:Lcom/google/android/maps/GeoPoint;
    goto :goto_0
.end method
