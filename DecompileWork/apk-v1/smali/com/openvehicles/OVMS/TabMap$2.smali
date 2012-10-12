.class Lcom/openvehicles/OVMS/TabMap$2;
.super Landroid/os/Handler;
.source "TabMap.java"


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
    .line 105
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabMap$2;->this$0:Lcom/openvehicles/OVMS/TabMap;

    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 9
    .parameter "msg"

    .prologue
    const-wide v7, 0x412e848000000000L

    .line 115
    const-string v5, "OVMS"

    const-string v6, "Centering Map"

    invoke-static {v5, v6}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 116
    iget-object v5, p0, Lcom/openvehicles/OVMS/TabMap$2;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v5}, Lcom/openvehicles/OVMS/TabMap;->access$100(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v5

    iget-wide v5, v5, Lcom/openvehicles/OVMS/CarData;->Data_Latitude:D

    mul-double/2addr v5, v7

    double-to-int v1, v5

    .line 117
    .local v1, lat:I
    iget-object v5, p0, Lcom/openvehicles/OVMS/TabMap$2;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v5}, Lcom/openvehicles/OVMS/TabMap;->access$100(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v5

    iget-wide v5, v5, Lcom/openvehicles/OVMS/CarData;->Data_Longitude:D

    mul-double/2addr v5, v7

    double-to-int v2, v5

    .line 118
    .local v2, lng:I
    new-instance v4, Lcom/google/android/maps/GeoPoint;

    invoke-direct {v4, v1, v2}, Lcom/google/android/maps/GeoPoint;-><init>(II)V

    .line 120
    .local v4, point:Lcom/google/android/maps/GeoPoint;
    iget-object v5, p0, Lcom/openvehicles/OVMS/TabMap$2;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->carMarkers:Lcom/openvehicles/OVMS/TabMap$CarMarkerOverlay;
    invoke-static {v5}, Lcom/openvehicles/OVMS/TabMap;->access$200(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/TabMap$CarMarkerOverlay;

    move-result-object v5

    invoke-virtual {v5}, Lcom/openvehicles/OVMS/TabMap$CarMarkerOverlay;->clearItems()V

    .line 121
    const-string v0, "-"

    .line 122
    .local v0, lastReportDate:Ljava/lang/String;
    iget-object v5, p0, Lcom/openvehicles/OVMS/TabMap$2;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v5}, Lcom/openvehicles/OVMS/TabMap;->access$100(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v5

    iget-object v5, v5, Lcom/openvehicles/OVMS/CarData;->Data_LastCarUpdate:Ljava/util/Date;

    if-eqz v5, :cond_0

    .line 123
    new-instance v5, Ljava/text/SimpleDateFormat;

    const-string v6, "MMM d, k:mm:ss"

    invoke-direct {v5, v6}, Ljava/text/SimpleDateFormat;-><init>(Ljava/lang/String;)V

    iget-object v6, p0, Lcom/openvehicles/OVMS/TabMap$2;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabMap;->access$100(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-object v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_LastCarUpdate:Ljava/util/Date;

    invoke-virtual {v5, v6}, Ljava/text/SimpleDateFormat;->format(Ljava/util/Date;)Ljava/lang/String;

    move-result-object v0

    .line 124
    :cond_0
    new-instance v3, Lcom/google/android/maps/OverlayItem;

    iget-object v5, p0, Lcom/openvehicles/OVMS/TabMap$2;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v5}, Lcom/openvehicles/OVMS/TabMap;->access$100(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v5

    iget-object v5, v5, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    const-string v6, "Last reported: %s"

    const/4 v7, 0x1

    new-array v7, v7, [Ljava/lang/Object;

    const/4 v8, 0x0

    aput-object v0, v7, v8

    invoke-static {v6, v7}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v6

    invoke-direct {v3, v4, v5, v6}, Lcom/google/android/maps/OverlayItem;-><init>(Lcom/google/android/maps/GeoPoint;Ljava/lang/String;Ljava/lang/String;)V

    .line 125
    .local v3, overlayitem:Lcom/google/android/maps/OverlayItem;
    iget-object v5, p0, Lcom/openvehicles/OVMS/TabMap$2;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->carMarkers:Lcom/openvehicles/OVMS/TabMap$CarMarkerOverlay;
    invoke-static {v5}, Lcom/openvehicles/OVMS/TabMap;->access$200(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/TabMap$CarMarkerOverlay;

    move-result-object v5

    invoke-virtual {v5, v3}, Lcom/openvehicles/OVMS/TabMap$CarMarkerOverlay;->addOverlay(Lcom/google/android/maps/OverlayItem;)V

    .line 127
    iget-object v5, p0, Lcom/openvehicles/OVMS/TabMap$2;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->mc:Lcom/google/android/maps/MapController;
    invoke-static {v5}, Lcom/openvehicles/OVMS/TabMap;->access$300(Lcom/openvehicles/OVMS/TabMap;)Lcom/google/android/maps/MapController;

    move-result-object v5

    invoke-virtual {v5, v4}, Lcom/google/android/maps/MapController;->setCenter(Lcom/google/android/maps/GeoPoint;)V

    .line 128
    iget-object v5, p0, Lcom/openvehicles/OVMS/TabMap$2;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->mc:Lcom/google/android/maps/MapController;
    invoke-static {v5}, Lcom/openvehicles/OVMS/TabMap;->access$300(Lcom/openvehicles/OVMS/TabMap;)Lcom/google/android/maps/MapController;

    move-result-object v5

    const/16 v6, 0x12

    invoke-virtual {v5, v6}, Lcom/google/android/maps/MapController;->setZoom(I)I

    .line 129
    iget-object v5, p0, Lcom/openvehicles/OVMS/TabMap$2;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->mapView:Lcom/google/android/maps/MapView;
    invoke-static {v5}, Lcom/openvehicles/OVMS/TabMap;->access$400(Lcom/openvehicles/OVMS/TabMap;)Lcom/google/android/maps/MapView;

    move-result-object v5

    invoke-virtual {v5}, Lcom/google/android/maps/MapView;->invalidate()V

    .line 130
    return-void
.end method
