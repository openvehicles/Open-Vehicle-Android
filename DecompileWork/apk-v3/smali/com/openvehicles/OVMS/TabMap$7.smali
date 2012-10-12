.class Lcom/openvehicles/OVMS/TabMap$7;
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
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabMap$7;->this$0:Lcom/openvehicles/OVMS/TabMap;

    .line 1189
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 15

    .prologue
    .line 1191
    const-string v9, "OVMS"

    const-string v10, "Centering Map"

    invoke-static {v9, v10}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1193
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$7;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabMap;->access$4(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v9

    invoke-static {v9}, Lcom/openvehicles/OVMS/Utilities;->GetCarGeopoint(Lcom/openvehicles/OVMS/CarData;)Lcom/google/android/maps/GeoPoint;

    move-result-object v0

    .line 1194
    .local v0, carLocation:Lcom/google/android/maps/GeoPoint;
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$7;->this$0:Lcom/openvehicles/OVMS/TabMap;

    iget-object v9, v9, Lcom/openvehicles/OVMS/TabMap;->centeringMode:Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;

    invoke-virtual {v9}, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;->getMode()I

    move-result v9

    packed-switch v9, :pswitch_data_0

    .line 1269
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$7;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->mc:Lcom/google/android/maps/MapController;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabMap;->access$13(Lcom/openvehicles/OVMS/TabMap;)Lcom/google/android/maps/MapController;

    move-result-object v9

    invoke-virtual {v9, v0}, Lcom/google/android/maps/MapController;->animateTo(Lcom/google/android/maps/GeoPoint;)V

    .line 1270
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$7;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->mc:Lcom/google/android/maps/MapController;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabMap;->access$13(Lcom/openvehicles/OVMS/TabMap;)Lcom/google/android/maps/MapController;

    move-result-object v9

    const/16 v10, 0x12

    invoke-virtual {v9, v10}, Lcom/google/android/maps/MapController;->setZoom(I)I

    .line 1273
    :goto_0
    :pswitch_0
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$7;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->mapView:Lcom/google/android/maps/MapView;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabMap;->access$8(Lcom/openvehicles/OVMS/TabMap;)Lcom/google/android/maps/MapView;

    move-result-object v9

    invoke-virtual {v9}, Lcom/google/android/maps/MapView;->invalidate()V

    .line 1274
    return-void

    .line 1196
    :pswitch_1
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$7;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->myLocationOverlay:Lcom/openvehicles/OVMS/MyLocationOverlayCustom;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabMap;->access$3(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/MyLocationOverlayCustom;

    move-result-object v9

    invoke-virtual {v9}, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->getMyLocation()Lcom/google/android/maps/GeoPoint;

    move-result-object v9

    if-eqz v9, :cond_0

    .line 1197
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$7;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->mc:Lcom/google/android/maps/MapController;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabMap;->access$13(Lcom/openvehicles/OVMS/TabMap;)Lcom/google/android/maps/MapController;

    move-result-object v9

    iget-object v10, p0, Lcom/openvehicles/OVMS/TabMap$7;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->myLocationOverlay:Lcom/openvehicles/OVMS/MyLocationOverlayCustom;
    invoke-static {v10}, Lcom/openvehicles/OVMS/TabMap;->access$3(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/MyLocationOverlayCustom;

    move-result-object v10

    invoke-virtual {v10}, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->getMyLocation()Lcom/google/android/maps/GeoPoint;

    move-result-object v10

    invoke-virtual {v9, v10}, Lcom/google/android/maps/MapController;->animateTo(Lcom/google/android/maps/GeoPoint;)V

    .line 1198
    :cond_0
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$7;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->mc:Lcom/google/android/maps/MapController;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabMap;->access$13(Lcom/openvehicles/OVMS/TabMap;)Lcom/google/android/maps/MapController;

    move-result-object v9

    const/16 v10, 0x11

    invoke-virtual {v9, v10}, Lcom/google/android/maps/MapController;->setZoom(I)I

    goto :goto_0

    .line 1201
    :pswitch_2
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$7;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->mc:Lcom/google/android/maps/MapController;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabMap;->access$13(Lcom/openvehicles/OVMS/TabMap;)Lcom/google/android/maps/MapController;

    move-result-object v9

    invoke-virtual {v9, v0}, Lcom/google/android/maps/MapController;->animateTo(Lcom/google/android/maps/GeoPoint;)V

    .line 1202
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$7;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->mc:Lcom/google/android/maps/MapController;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabMap;->access$13(Lcom/openvehicles/OVMS/TabMap;)Lcom/google/android/maps/MapController;

    move-result-object v9

    const/16 v10, 0x11

    invoke-virtual {v9, v10}, Lcom/google/android/maps/MapController;->setZoom(I)I

    goto :goto_0

    .line 1206
    :pswitch_3
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$7;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->mapOverlays:Ljava/util/List;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabMap;->access$16(Lcom/openvehicles/OVMS/TabMap;)Ljava/util/List;

    move-result-object v9

    invoke-interface {v9}, Ljava/util/List;->size()I

    move-result v9

    const/4 v10, 0x3

    if-gt v9, v10, :cond_1

    .line 1208
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$7;->this$0:Lcom/openvehicles/OVMS/TabMap;

    iget-object v9, v9, Lcom/openvehicles/OVMS/TabMap;->centeringMode:Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;

    const/4 v10, 0x2

    invoke-virtual {v9, v10}, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;->setMode(I)V

    .line 1209
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$7;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->mc:Lcom/google/android/maps/MapController;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabMap;->access$13(Lcom/openvehicles/OVMS/TabMap;)Lcom/google/android/maps/MapController;

    move-result-object v9

    invoke-virtual {v9, v0}, Lcom/google/android/maps/MapController;->animateTo(Lcom/google/android/maps/GeoPoint;)V

    .line 1210
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$7;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->mc:Lcom/google/android/maps/MapController;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabMap;->access$13(Lcom/openvehicles/OVMS/TabMap;)Lcom/google/android/maps/MapController;

    move-result-object v9

    const/16 v10, 0x11

    invoke-virtual {v9, v10}, Lcom/google/android/maps/MapController;->setZoom(I)I

    goto :goto_0

    .line 1214
    :cond_1
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$7;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->mapOverlays:Ljava/util/List;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabMap;->access$16(Lcom/openvehicles/OVMS/TabMap;)Ljava/util/List;

    move-result-object v9

    .line 1215
    const/4 v10, 0x3

    invoke-interface {v9, v10}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/openvehicles/OVMS/RouteOverlay;

    .line 1216
    .local v1, firstWayPoint:Lcom/openvehicles/OVMS/RouteOverlay;
    iget-object v9, v1, Lcom/openvehicles/OVMS/RouteOverlay;->gp1:Lcom/google/android/maps/GeoPoint;

    invoke-virtual {v9}, Lcom/google/android/maps/GeoPoint;->getLatitudeE6()I

    move-result v5

    .line 1217
    .local v5, minLatitude:I
    iget-object v9, v1, Lcom/openvehicles/OVMS/RouteOverlay;->gp1:Lcom/google/android/maps/GeoPoint;

    invoke-virtual {v9}, Lcom/google/android/maps/GeoPoint;->getLongitudeE6()I

    move-result v6

    .line 1218
    .local v6, minLongitude:I
    iget-object v9, v1, Lcom/openvehicles/OVMS/RouteOverlay;->gp1:Lcom/google/android/maps/GeoPoint;

    invoke-virtual {v9}, Lcom/google/android/maps/GeoPoint;->getLatitudeE6()I

    move-result v3

    .line 1219
    .local v3, maxLatitude:I
    iget-object v9, v1, Lcom/openvehicles/OVMS/RouteOverlay;->gp1:Lcom/google/android/maps/GeoPoint;

    invoke-virtual {v9}, Lcom/google/android/maps/GeoPoint;->getLongitudeE6()I

    move-result v4

    .line 1221
    .local v4, maxLongitude:I
    const/4 v2, 0x3

    .local v2, i:I
    :goto_1
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$7;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->mapOverlays:Ljava/util/List;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabMap;->access$16(Lcom/openvehicles/OVMS/TabMap;)Ljava/util/List;

    move-result-object v9

    invoke-interface {v9}, Ljava/util/List;->size()I

    move-result v9

    if-lt v2, v9, :cond_2

    .line 1240
    const-string v9, "Map"

    const-string v10, "Zoom Span: %s %s %s %s"

    const/4 v11, 0x4

    new-array v11, v11, [Ljava/lang/Object;

    const/4 v12, 0x0

    .line 1241
    invoke-static {v5}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v13

    aput-object v13, v11, v12

    const/4 v12, 0x1

    invoke-static {v3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v13

    aput-object v13, v11, v12

    const/4 v12, 0x2

    invoke-static {v6}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v13

    aput-object v13, v11, v12

    const/4 v12, 0x3

    invoke-static {v4}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v13

    aput-object v13, v11, v12

    .line 1240
    invoke-static {v10, v11}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v10

    invoke-static {v9, v10}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1243
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$7;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->mapView:Lcom/google/android/maps/MapView;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabMap;->access$8(Lcom/openvehicles/OVMS/TabMap;)Lcom/google/android/maps/MapView;

    move-result-object v9

    invoke-virtual {v9}, Lcom/google/android/maps/MapView;->getController()Lcom/google/android/maps/MapController;

    move-result-object v9

    .line 1244
    sub-int v10, v3, v5

    add-int/lit8 v10, v10, 0x64

    .line 1245
    sub-int v11, v4, v6

    add-int/lit8 v11, v11, 0x64

    .line 1243
    invoke-virtual {v9, v10, v11}, Lcom/google/android/maps/MapController;->zoomToSpan(II)V

    .line 1247
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$7;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->mapView:Lcom/google/android/maps/MapView;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabMap;->access$8(Lcom/openvehicles/OVMS/TabMap;)Lcom/google/android/maps/MapView;

    move-result-object v9

    invoke-virtual {v9}, Lcom/google/android/maps/MapView;->getController()Lcom/google/android/maps/MapController;

    move-result-object v9

    .line 1248
    new-instance v10, Lcom/google/android/maps/GeoPoint;

    add-int v11, v3, v5

    div-int/lit8 v11, v11, 0x2

    .line 1249
    add-int v12, v4, v6

    div-int/lit8 v12, v12, 0x2

    .line 1248
    invoke-direct {v10, v11, v12}, Lcom/google/android/maps/GeoPoint;-><init>(II)V

    .line 1247
    invoke-virtual {v9, v10}, Lcom/google/android/maps/MapController;->animateTo(Lcom/google/android/maps/GeoPoint;)V

    .line 1251
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$7;->this$0:Lcom/openvehicles/OVMS/TabMap;

    const v10, 0x7f09008d

    invoke-virtual {v9, v10}, Lcom/openvehicles/OVMS/TabMap;->findViewById(I)Landroid/view/View;

    move-result-object v7

    check-cast v7, Landroid/widget/RadioButton;

    .line 1252
    .local v7, routeOff:Landroid/widget/RadioButton;
    const/4 v9, 0x0

    invoke-virtual {v7, v9}, Landroid/widget/RadioButton;->setChecked(Z)V

    goto/16 :goto_0

    .line 1222
    .end local v7           #routeOff:Landroid/widget/RadioButton;
    :cond_2
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$7;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->mapOverlays:Ljava/util/List;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabMap;->access$16(Lcom/openvehicles/OVMS/TabMap;)Ljava/util/List;

    move-result-object v9

    invoke-interface {v9, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v8

    check-cast v8, Lcom/openvehicles/OVMS/RouteOverlay;

    .line 1225
    .local v8, wayPoint:Lcom/openvehicles/OVMS/RouteOverlay;
    iget-object v9, v8, Lcom/openvehicles/OVMS/RouteOverlay;->gp1:Lcom/google/android/maps/GeoPoint;

    invoke-virtual {v9}, Lcom/google/android/maps/GeoPoint;->getLatitudeE6()I

    move-result v9

    if-le v5, v9, :cond_3

    iget-object v9, v8, Lcom/openvehicles/OVMS/RouteOverlay;->gp1:Lcom/google/android/maps/GeoPoint;

    .line 1226
    invoke-virtual {v9}, Lcom/google/android/maps/GeoPoint;->getLatitudeE6()I

    move-result v5

    .line 1227
    :cond_3
    iget-object v9, v8, Lcom/openvehicles/OVMS/RouteOverlay;->gp1:Lcom/google/android/maps/GeoPoint;

    invoke-virtual {v9}, Lcom/google/android/maps/GeoPoint;->getLatitudeE6()I

    move-result v9

    if-ge v3, v9, :cond_4

    iget-object v9, v8, Lcom/openvehicles/OVMS/RouteOverlay;->gp1:Lcom/google/android/maps/GeoPoint;

    .line 1228
    invoke-virtual {v9}, Lcom/google/android/maps/GeoPoint;->getLatitudeE6()I

    move-result v3

    .line 1232
    :cond_4
    iget-object v9, v8, Lcom/openvehicles/OVMS/RouteOverlay;->gp1:Lcom/google/android/maps/GeoPoint;

    .line 1233
    invoke-virtual {v9}, Lcom/google/android/maps/GeoPoint;->getLongitudeE6()I

    move-result v9

    if-le v6, v9, :cond_5

    iget-object v9, v8, Lcom/openvehicles/OVMS/RouteOverlay;->gp1:Lcom/google/android/maps/GeoPoint;

    invoke-virtual {v9}, Lcom/google/android/maps/GeoPoint;->getLongitudeE6()I

    move-result v6

    .line 1235
    :cond_5
    iget-object v9, v8, Lcom/openvehicles/OVMS/RouteOverlay;->gp1:Lcom/google/android/maps/GeoPoint;

    .line 1236
    invoke-virtual {v9}, Lcom/google/android/maps/GeoPoint;->getLongitudeE6()I

    move-result v9

    if-ge v4, v9, :cond_6

    iget-object v9, v8, Lcom/openvehicles/OVMS/RouteOverlay;->gp1:Lcom/google/android/maps/GeoPoint;

    invoke-virtual {v9}, Lcom/google/android/maps/GeoPoint;->getLongitudeE6()I

    move-result v4

    .line 1221
    :cond_6
    add-int/lit8 v2, v2, 0x1

    goto/16 :goto_1

    .line 1258
    .end local v1           #firstWayPoint:Lcom/openvehicles/OVMS/RouteOverlay;
    .end local v2           #i:I
    .end local v3           #maxLatitude:I
    .end local v4           #maxLongitude:I
    .end local v5           #minLatitude:I
    .end local v6           #minLongitude:I
    .end local v8           #wayPoint:Lcom/openvehicles/OVMS/RouteOverlay;
    :pswitch_4
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$7;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabMap;->access$4(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v9

    iget-object v9, v9, Lcom/openvehicles/OVMS/CarData;->Group:Ljava/util/HashMap;

    iget-object v10, p0, Lcom/openvehicles/OVMS/TabMap$7;->this$0:Lcom/openvehicles/OVMS/TabMap;

    iget-object v10, v10, Lcom/openvehicles/OVMS/TabMap;->centeringMode:Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;

    iget-object v10, v10, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;->GroupCar_VehicleID:Ljava/lang/String;

    invoke-virtual {v9, v10}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v9

    if-nez v9, :cond_7

    .line 1260
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$7;->this$0:Lcom/openvehicles/OVMS/TabMap;

    iget-object v9, v9, Lcom/openvehicles/OVMS/TabMap;->centeringMode:Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;

    const/4 v10, 0x4

    invoke-virtual {v9, v10}, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;->setMode(I)V

    goto/16 :goto_0

    .line 1264
    :cond_7
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$7;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->mc:Lcom/google/android/maps/MapController;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabMap;->access$13(Lcom/openvehicles/OVMS/TabMap;)Lcom/google/android/maps/MapController;

    move-result-object v10

    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$7;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabMap;->access$4(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v9

    iget-object v9, v9, Lcom/openvehicles/OVMS/CarData;->Group:Ljava/util/HashMap;

    iget-object v11, p0, Lcom/openvehicles/OVMS/TabMap$7;->this$0:Lcom/openvehicles/OVMS/TabMap;

    iget-object v11, v11, Lcom/openvehicles/OVMS/TabMap;->centeringMode:Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;

    iget-object v11, v11, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;->GroupCar_VehicleID:Ljava/lang/String;

    invoke-virtual {v9, v11}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v9

    check-cast v9, Lcom/openvehicles/OVMS/CarData_Group;

    iget-wide v11, v9, Lcom/openvehicles/OVMS/CarData_Group;->Latitude:D

    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$7;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabMap;->access$4(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v9

    iget-object v9, v9, Lcom/openvehicles/OVMS/CarData;->Group:Ljava/util/HashMap;

    iget-object v13, p0, Lcom/openvehicles/OVMS/TabMap$7;->this$0:Lcom/openvehicles/OVMS/TabMap;

    iget-object v13, v13, Lcom/openvehicles/OVMS/TabMap;->centeringMode:Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;

    iget-object v13, v13, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;->GroupCar_VehicleID:Ljava/lang/String;

    invoke-virtual {v9, v13}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v9

    check-cast v9, Lcom/openvehicles/OVMS/CarData_Group;

    iget-wide v13, v9, Lcom/openvehicles/OVMS/CarData_Group;->Longitude:D

    invoke-static {v11, v12, v13, v14}, Lcom/openvehicles/OVMS/Utilities;->GetCarGeopoint(DD)Lcom/google/android/maps/GeoPoint;

    move-result-object v9

    invoke-virtual {v10, v9}, Lcom/google/android/maps/MapController;->animateTo(Lcom/google/android/maps/GeoPoint;)V

    .line 1265
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabMap$7;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->mc:Lcom/google/android/maps/MapController;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabMap;->access$13(Lcom/openvehicles/OVMS/TabMap;)Lcom/google/android/maps/MapController;

    move-result-object v9

    const/16 v10, 0x12

    invoke-virtual {v9, v10}, Lcom/google/android/maps/MapController;->setZoom(I)I

    goto/16 :goto_0

    .line 1194
    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_1
        :pswitch_2
        :pswitch_3
        :pswitch_0
        :pswitch_4
    .end packed-switch
.end method
