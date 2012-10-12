.class public Lcom/openvehicles/OVMS/TabMap;
.super Lcom/google/android/maps/MapActivity;
.source "TabMap.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/openvehicles/OVMS/TabMap$CustomSpinnerAdapter;,
        Lcom/openvehicles/OVMS/TabMap$GroupCarsListViewAdapter;,
        Lcom/openvehicles/OVMS/TabMap$OnCenteringModeChangedListener;,
        Lcom/openvehicles/OVMS/TabMap$TouchOverlay;,
        Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;
    }
.end annotation


# instance fields
.field private final CAR_MARKER_ANIMATION_DURATION_MS:I

.field private final CAR_MARKER_ANIMATION_FRAMES:I

.field private DirectionalMarker:Landroid/graphics/Bitmap;

.field private final LABEL_SHADOW_XY:I

.field private final LABEL_TEXT_SIZE:I

.field private final SYSTEM_OVERLAY_COUNT:I

.field private animateCarMarker:Ljava/lang/Runnable;

.field private availableCarColors:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList",
            "<",
            "Ljava/util/HashMap",
            "<",
            "Ljava/lang/String;",
            "Ljava/lang/Object;",
            ">;>;"
        }
    .end annotation
.end field

.field private availableCarColorsSpinnerAdapter:Lcom/openvehicles/OVMS/TabMap$CustomSpinnerAdapter;

.field private carMarkerAnimationFrame:I

.field private carMarkerAnimationTimerHandler:Landroid/os/Handler;

.field private carMarkers:Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;

.field centeringMode:Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;

.field private currentVehicleID:Ljava/lang/String;

.field private data:Lcom/openvehicles/OVMS/CarData;

.field private groupCarsListAdapter:Lcom/openvehicles/OVMS/TabMap$GroupCarsListViewAdapter;

.field final initializeMapCentering:Ljava/lang/Runnable;

.field private isLoggedIn:Z

.field private lastCarGeoPoints:[Lcom/google/android/maps/GeoPoint;

.field private lastKnownDeviceGeoPoint:Lcom/google/android/maps/GeoPoint;

.field private lastUpdateTimer:Ljava/lang/Runnable;

.field private lastUpdateTimerHandler:Landroid/os/Handler;

.field private locationListener:Landroid/location/LocationListener;

.field private locationManager:Landroid/location/LocationManager;

.field private mapDragLastX:F

.field private mapDragLastY:F

.field private mapOverlays:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List",
            "<",
            "Lcom/google/android/maps/Overlay;",
            ">;"
        }
    .end annotation
.end field

.field private mapView:Lcom/google/android/maps/MapView;

.field private mc:Lcom/google/android/maps/MapController;

.field private myLocationDisable:Ljava/lang/Runnable;

.field private myLocationEnable:Ljava/lang/Runnable;

.field private myLocationOverlay:Lcom/openvehicles/OVMS/MyLocationOverlayCustom;

.field private planWalkingDirection:Z

.field private refreshUIHandler:Landroid/os/Handler;

.field private routeError:Ljava/lang/Runnable;

.field final updateCenteringOptions:Ljava/lang/Runnable;


# direct methods
.method public constructor <init>()V
    .locals 1

    .prologue
    .line 68
    invoke-direct {p0}, Lcom/google/android/maps/MapActivity;-><init>()V

    .line 304
    const-string v0, ""

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->currentVehicleID:Ljava/lang/String;

    .line 317
    new-instance v0, Landroid/os/Handler;

    invoke-direct {v0}, Landroid/os/Handler;-><init>()V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->lastUpdateTimerHandler:Landroid/os/Handler;

    .line 319
    const/4 v0, 0x3

    iput v0, p0, Lcom/openvehicles/OVMS/TabMap;->SYSTEM_OVERLAY_COUNT:I

    .line 325
    const/16 v0, 0x14

    iput v0, p0, Lcom/openvehicles/OVMS/TabMap;->LABEL_TEXT_SIZE:I

    .line 326
    const/4 v0, 0x1

    iput v0, p0, Lcom/openvehicles/OVMS/TabMap;->LABEL_SHADOW_XY:I

    .line 333
    new-instance v0, Landroid/os/Handler;

    invoke-direct {v0}, Landroid/os/Handler;-><init>()V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->carMarkerAnimationTimerHandler:Landroid/os/Handler;

    .line 334
    const/4 v0, 0x0

    iput v0, p0, Lcom/openvehicles/OVMS/TabMap;->carMarkerAnimationFrame:I

    .line 336
    const/16 v0, 0x7d0

    iput v0, p0, Lcom/openvehicles/OVMS/TabMap;->CAR_MARKER_ANIMATION_DURATION_MS:I

    .line 337
    const/16 v0, 0x28

    iput v0, p0, Lcom/openvehicles/OVMS/TabMap;->CAR_MARKER_ANIMATION_FRAMES:I

    .line 536
    new-instance v0, Lcom/openvehicles/OVMS/TabMap$1;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/TabMap$1;-><init>(Lcom/openvehicles/OVMS/TabMap;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->lastUpdateTimer:Ljava/lang/Runnable;

    .line 828
    new-instance v0, Lcom/openvehicles/OVMS/TabMap$2;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/TabMap$2;-><init>(Lcom/openvehicles/OVMS/TabMap;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->routeError:Ljava/lang/Runnable;

    .line 835
    new-instance v0, Lcom/openvehicles/OVMS/TabMap$3;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/TabMap$3;-><init>(Lcom/openvehicles/OVMS/TabMap;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->myLocationEnable:Ljava/lang/Runnable;

    .line 841
    new-instance v0, Lcom/openvehicles/OVMS/TabMap$4;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/TabMap$4;-><init>(Lcom/openvehicles/OVMS/TabMap;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->myLocationDisable:Ljava/lang/Runnable;

    .line 952
    new-instance v0, Lcom/openvehicles/OVMS/TabMap$5;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/TabMap$5;-><init>(Lcom/openvehicles/OVMS/TabMap;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->animateCarMarker:Ljava/lang/Runnable;

    .line 1036
    new-instance v0, Lcom/openvehicles/OVMS/TabMap$6;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/TabMap$6;-><init>(Lcom/openvehicles/OVMS/TabMap;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->refreshUIHandler:Landroid/os/Handler;

    .line 1189
    new-instance v0, Lcom/openvehicles/OVMS/TabMap$7;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/TabMap$7;-><init>(Lcom/openvehicles/OVMS/TabMap;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->initializeMapCentering:Ljava/lang/Runnable;

    .line 1277
    new-instance v0, Lcom/openvehicles/OVMS/TabMap$8;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/TabMap$8;-><init>(Lcom/openvehicles/OVMS/TabMap;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->updateCenteringOptions:Ljava/lang/Runnable;

    .line 68
    return-void
.end method

.method static synthetic access$0(Lcom/openvehicles/OVMS/TabMap;)V
    .locals 0
    .parameter

    .prologue
    .line 583
    invoke-direct {p0}, Lcom/openvehicles/OVMS/TabMap;->updateLastUpdatedView()V

    return-void
.end method

.method static synthetic access$1(Lcom/openvehicles/OVMS/TabMap;)Landroid/os/Handler;
    .locals 1
    .parameter

    .prologue
    .line 317
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->lastUpdateTimerHandler:Landroid/os/Handler;

    return-object v0
.end method

.method static synthetic access$10(Lcom/openvehicles/OVMS/TabMap;)Landroid/os/Handler;
    .locals 1
    .parameter

    .prologue
    .line 333
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->carMarkerAnimationTimerHandler:Landroid/os/Handler;

    return-object v0
.end method

.method static synthetic access$11(Lcom/openvehicles/OVMS/TabMap;)Ljava/lang/Runnable;
    .locals 1
    .parameter

    .prologue
    .line 952
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->animateCarMarker:Ljava/lang/Runnable;

    return-object v0
.end method

.method static synthetic access$12(Lcom/openvehicles/OVMS/TabMap;[Lcom/google/android/maps/GeoPoint;)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 335
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabMap;->lastCarGeoPoints:[Lcom/google/android/maps/GeoPoint;

    return-void
.end method

.method static synthetic access$13(Lcom/openvehicles/OVMS/TabMap;)Lcom/google/android/maps/MapController;
    .locals 1
    .parameter

    .prologue
    .line 310
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->mc:Lcom/google/android/maps/MapController;

    return-object v0
.end method

.method static synthetic access$14(Lcom/openvehicles/OVMS/TabMap;Lcom/openvehicles/OVMS/TabMap$GroupCarsListViewAdapter;)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 340
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabMap;->groupCarsListAdapter:Lcom/openvehicles/OVMS/TabMap$GroupCarsListViewAdapter;

    return-void
.end method

.method static synthetic access$15(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/TabMap$GroupCarsListViewAdapter;
    .locals 1
    .parameter

    .prologue
    .line 340
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->groupCarsListAdapter:Lcom/openvehicles/OVMS/TabMap$GroupCarsListViewAdapter;

    return-object v0
.end method

.method static synthetic access$16(Lcom/openvehicles/OVMS/TabMap;)Ljava/util/List;
    .locals 1
    .parameter

    .prologue
    .line 306
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->mapOverlays:Ljava/util/List;

    return-object v0
.end method

.method static synthetic access$17(Lcom/openvehicles/OVMS/TabMap;)V
    .locals 0
    .parameter

    .prologue
    .line 523
    invoke-direct {p0}, Lcom/openvehicles/OVMS/TabMap;->hidePopup()V

    return-void
.end method

.method static synthetic access$18(Lcom/openvehicles/OVMS/TabMap;)F
    .locals 1
    .parameter

    .prologue
    .line 328
    iget v0, p0, Lcom/openvehicles/OVMS/TabMap;->mapDragLastX:F

    return v0
.end method

.method static synthetic access$19(Lcom/openvehicles/OVMS/TabMap;)F
    .locals 1
    .parameter

    .prologue
    .line 328
    iget v0, p0, Lcom/openvehicles/OVMS/TabMap;->mapDragLastY:F

    return v0
.end method

.method static synthetic access$2(Lcom/openvehicles/OVMS/TabMap;)Ljava/lang/Runnable;
    .locals 1
    .parameter

    .prologue
    .line 536
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->lastUpdateTimer:Ljava/lang/Runnable;

    return-object v0
.end method

.method static synthetic access$20(Lcom/openvehicles/OVMS/TabMap;F)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 328
    iput p1, p0, Lcom/openvehicles/OVMS/TabMap;->mapDragLastX:F

    return-void
.end method

.method static synthetic access$21(Lcom/openvehicles/OVMS/TabMap;F)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 328
    iput p1, p0, Lcom/openvehicles/OVMS/TabMap;->mapDragLastY:F

    return-void
.end method

.method static synthetic access$22(Lcom/openvehicles/OVMS/TabMap;)Landroid/os/Handler;
    .locals 1
    .parameter

    .prologue
    .line 1036
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->refreshUIHandler:Landroid/os/Handler;

    return-object v0
.end method

.method static synthetic access$23(Lcom/openvehicles/OVMS/TabMap;Lcom/google/android/maps/GeoPoint;)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 315
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabMap;->lastKnownDeviceGeoPoint:Lcom/google/android/maps/GeoPoint;

    return-void
.end method

.method static synthetic access$24(Lcom/openvehicles/OVMS/TabMap;)Lcom/google/android/maps/GeoPoint;
    .locals 1
    .parameter

    .prologue
    .line 315
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->lastKnownDeviceGeoPoint:Lcom/google/android/maps/GeoPoint;

    return-object v0
.end method

.method static synthetic access$25(Lcom/openvehicles/OVMS/TabMap;)V
    .locals 0
    .parameter

    .prologue
    .line 658
    invoke-direct {p0}, Lcom/openvehicles/OVMS/TabMap;->clearRoute()V

    return-void
.end method

.method static synthetic access$26(Lcom/openvehicles/OVMS/TabMap;)V
    .locals 0
    .parameter

    .prologue
    .line 617
    invoke-direct {p0}, Lcom/openvehicles/OVMS/TabMap;->planRoute()V

    return-void
.end method

.method static synthetic access$27(Lcom/openvehicles/OVMS/TabMap;)V
    .locals 0
    .parameter

    .prologue
    .line 641
    invoke-direct {p0}, Lcom/openvehicles/OVMS/TabMap;->cancelRoute()V

    return-void
.end method

.method static synthetic access$28(Lcom/openvehicles/OVMS/TabMap;Ljava/lang/String;)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 389
    invoke-direct {p0, p1}, Lcom/openvehicles/OVMS/TabMap;->showGroupCarPopup(Ljava/lang/String;)V

    return-void
.end method

.method static synthetic access$29(Lcom/openvehicles/OVMS/TabMap;)Ljava/util/ArrayList;
    .locals 1
    .parameter

    .prologue
    .line 341
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->availableCarColors:Ljava/util/ArrayList;

    return-object v0
.end method

.method static synthetic access$3(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/MyLocationOverlayCustom;
    .locals 1
    .parameter

    .prologue
    .line 330
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->myLocationOverlay:Lcom/openvehicles/OVMS/MyLocationOverlayCustom;

    return-object v0
.end method

.method static synthetic access$30(Lcom/openvehicles/OVMS/TabMap;)V
    .locals 0
    .parameter

    .prologue
    .line 679
    invoke-direct {p0}, Lcom/openvehicles/OVMS/TabMap;->updateRoute()V

    return-void
.end method

.method static synthetic access$4(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;
    .locals 1
    .parameter

    .prologue
    .line 311
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;

    return-object v0
.end method

.method static synthetic access$5(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;
    .locals 1
    .parameter

    .prologue
    .line 308
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->carMarkers:Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;

    return-object v0
.end method

.method static synthetic access$6(Lcom/openvehicles/OVMS/TabMap;)[Lcom/google/android/maps/GeoPoint;
    .locals 1
    .parameter

    .prologue
    .line 335
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->lastCarGeoPoints:[Lcom/google/android/maps/GeoPoint;

    return-object v0
.end method

.method static synthetic access$7(Lcom/openvehicles/OVMS/TabMap;)I
    .locals 1
    .parameter

    .prologue
    .line 334
    iget v0, p0, Lcom/openvehicles/OVMS/TabMap;->carMarkerAnimationFrame:I

    return v0
.end method

.method static synthetic access$8(Lcom/openvehicles/OVMS/TabMap;)Lcom/google/android/maps/MapView;
    .locals 1
    .parameter

    .prologue
    .line 309
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->mapView:Lcom/google/android/maps/MapView;

    return-object v0
.end method

.method static synthetic access$9(Lcom/openvehicles/OVMS/TabMap;I)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 334
    iput p1, p0, Lcom/openvehicles/OVMS/TabMap;->carMarkerAnimationFrame:I

    return-void
.end method

.method private cancelRoute()V
    .locals 4

    .prologue
    const/4 v3, 0x0

    .line 642
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap;->centeringMode:Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;

    invoke-virtual {v1}, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;->getMode()I

    move-result v1

    const/4 v2, 0x4

    if-eq v1, v2, :cond_0

    .line 643
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap;->centeringMode:Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;

    invoke-virtual {v1}, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;->getMode()I

    move-result v1

    const/4 v2, 0x3

    if-ne v1, v2, :cond_1

    .line 644
    :cond_0
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap;->centeringMode:Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;

    const/4 v2, 0x2

    invoke-virtual {v1, v2}, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;->setMode(I)V

    .line 646
    :cond_1
    invoke-direct {p0}, Lcom/openvehicles/OVMS/TabMap;->clearRoute()V

    .line 649
    const v1, 0x7f09008d

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/TabMap;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/RadioButton;

    .line 650
    .local v0, routeOff:Landroid/widget/RadioButton;
    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Landroid/widget/RadioButton;->setChecked(Z)V

    .line 652
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap;->refreshUIHandler:Landroid/os/Handler;

    invoke-virtual {v1, v3}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    .line 653
    const-string v1, "Route Cancelled"

    invoke-static {p0, v1, v3}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v1

    .line 654
    invoke-virtual {v1}, Landroid/widget/Toast;->show()V

    .line 656
    return-void
.end method

.method private clearRoute()V
    .locals 2

    .prologue
    .line 659
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap;->mapOverlays:Ljava/util/List;

    invoke-interface {v1}, Ljava/util/List;->size()I

    move-result v1

    add-int/lit8 v0, v1, -0x1

    .local v0, i:I
    :goto_0
    const/4 v1, 0x3

    if-ge v0, v1, :cond_0

    .line 662
    return-void

    .line 661
    :cond_0
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap;->mapOverlays:Ljava/util/List;

    invoke-interface {v1, v0}, Ljava/util/List;->remove(I)Ljava/lang/Object;

    .line 659
    add-int/lit8 v0, v0, -0x1

    goto :goto_0
.end method

.method private drawRoute(Ljava/util/List;I)V
    .locals 5
    .parameter
    .parameter "color"
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List",
            "<",
            "Lcom/google/android/maps/GeoPoint;",
            ">;I)V"
        }
    .end annotation

    .prologue
    .line 665
    .local p1, geoPoints:Ljava/util/List;,"Ljava/util/List<Lcom/google/android/maps/GeoPoint;>;"
    const-string v1, "Route"

    const-string v2, "Creating overlay"

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 667
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap;->mapOverlays:Ljava/util/List;

    invoke-interface {v1}, Ljava/util/List;->size()I

    move-result v1

    const/4 v2, 0x3

    if-le v1, v2, :cond_0

    .line 669
    invoke-direct {p0}, Lcom/openvehicles/OVMS/TabMap;->clearRoute()V

    .line 673
    :cond_0
    const/4 v0, 0x1

    .local v0, i:I
    :goto_0
    invoke-interface {p1}, Ljava/util/List;->size()I

    move-result v1

    if-lt v0, v1, :cond_1

    .line 677
    return-void

    .line 674
    :cond_1
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabMap;->mapOverlays:Ljava/util/List;

    new-instance v4, Lcom/openvehicles/OVMS/RouteOverlay;

    add-int/lit8 v1, v0, -0x1

    invoke-interface {p1, v1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/google/android/maps/GeoPoint;

    .line 675
    invoke-interface {p1, v0}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/google/android/maps/GeoPoint;

    invoke-direct {v4, v1, v2, p2}, Lcom/openvehicles/OVMS/RouteOverlay;-><init>(Lcom/google/android/maps/GeoPoint;Lcom/google/android/maps/GeoPoint;I)V

    .line 674
    invoke-interface {v3, v4}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 673
    add-int/lit8 v0, v0, 0x1

    goto :goto_0
.end method

.method private getRouteGeoPoints()Ljava/util/List;
    .locals 30
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/List",
            "<",
            "Lcom/google/android/maps/GeoPoint;",
            ">;"
        }
    .end annotation

    .prologue
    .line 701
    const-string v24, "Route"

    const-string v25, "Getting waypoints from google"

    invoke-static/range {v24 .. v25}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 702
    const/16 v23, 0x0

    .line 704
    .local v23, url:Ljava/lang/String;
    :try_start_0
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap;->lastKnownDeviceGeoPoint:Lcom/google/android/maps/GeoPoint;

    move-object/from16 v24, v0

    .line 705
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;

    move-object/from16 v25, v0

    invoke-static/range {v25 .. v25}, Lcom/openvehicles/OVMS/Utilities;->GetCarGeopoint(Lcom/openvehicles/OVMS/CarData;)Lcom/google/android/maps/GeoPoint;

    move-result-object v25

    move-object/from16 v0, p0

    iget-boolean v0, v0, Lcom/openvehicles/OVMS/TabMap;->planWalkingDirection:Z

    move/from16 v26, v0

    .line 704
    move-object/from16 v0, p0

    move-object/from16 v1, v24

    move-object/from16 v2, v25

    move/from16 v3, v26

    invoke-virtual {v0, v1, v2, v3}, Lcom/openvehicles/OVMS/TabMap;->getMapKMLUrl(Lcom/google/android/maps/GeoPoint;Lcom/google/android/maps/GeoPoint;Z)Ljava/lang/String;
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    move-result-object v23

    .line 710
    const-string v24, "Route"

    new-instance v25, Ljava/lang/StringBuilder;

    const-string v26, "Request URL: "

    invoke-direct/range {v25 .. v26}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    move-object/from16 v0, v25

    move-object/from16 v1, v23

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v25

    invoke-virtual/range {v25 .. v25}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v25

    invoke-static/range {v24 .. v25}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 711
    new-instance v8, Lorg/apache/http/client/methods/HttpGet;

    move-object/from16 v0, v23

    invoke-direct {v8, v0}, Lorg/apache/http/client/methods/HttpGet;-><init>(Ljava/lang/String;)V

    .line 712
    .local v8, httpGet:Lorg/apache/http/client/methods/HttpGet;
    new-instance v9, Lorg/apache/http/impl/client/DefaultHttpClient;

    invoke-direct {v9}, Lorg/apache/http/impl/client/DefaultHttpClient;-><init>()V

    .line 714
    .local v9, httpclient:Lorg/apache/http/client/HttpClient;
    const/16 v21, 0x0

    .line 716
    .local v21, response:Lorg/apache/http/HttpResponse;
    :try_start_1
    invoke-interface {v9, v8}, Lorg/apache/http/client/HttpClient;->execute(Lorg/apache/http/client/methods/HttpUriRequest;)Lorg/apache/http/HttpResponse;
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_1

    move-result-object v21

    .line 722
    const-string v11, ""

    .line 724
    .local v11, kml:Ljava/lang/String;
    :try_start_2
    new-instance v10, Ljava/io/BufferedInputStream;

    .line 725
    invoke-interface/range {v21 .. v21}, Lorg/apache/http/HttpResponse;->getEntity()Lorg/apache/http/HttpEntity;

    move-result-object v24

    invoke-interface/range {v24 .. v24}, Lorg/apache/http/HttpEntity;->getContent()Ljava/io/InputStream;

    move-result-object v24

    .line 724
    move-object/from16 v0, v24

    invoke-direct {v10, v0}, Ljava/io/BufferedInputStream;-><init>(Ljava/io/InputStream;)V

    .line 727
    .local v10, in:Ljava/io/BufferedInputStream;
    new-instance v20, Ljava/io/BufferedReader;

    new-instance v24, Ljava/io/InputStreamReader;

    move-object/from16 v0, v24

    invoke-direct {v0, v10}, Ljava/io/InputStreamReader;-><init>(Ljava/io/InputStream;)V

    .line 728
    const v25, 0xa000

    .line 727
    move-object/from16 v0, v20

    move-object/from16 v1, v24

    move/from16 v2, v25

    invoke-direct {v0, v1, v2}, Ljava/io/BufferedReader;-><init>(Ljava/io/Reader;I)V

    .line 730
    .local v20, rd:Ljava/io/BufferedReader;
    new-instance v22, Ljava/lang/StringBuilder;

    invoke-direct/range {v22 .. v22}, Ljava/lang/StringBuilder;-><init>()V

    .line 731
    .local v22, sb:Ljava/lang/StringBuilder;
    :goto_0
    invoke-virtual/range {v20 .. v20}, Ljava/io/BufferedReader;->readLine()Ljava/lang/String;

    move-result-object v14

    .local v14, line:Ljava/lang/String;
    if-nez v14, :cond_1

    .line 734
    invoke-virtual/range {v20 .. v20}, Ljava/io/BufferedReader;->close()V

    .line 736
    invoke-virtual {v10}, Ljava/io/BufferedInputStream;->close()V

    .line 738
    invoke-virtual/range {v22 .. v22}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;
    :try_end_2
    .catch Ljava/lang/Exception; {:try_start_2 .. :try_end_2} :catch_2

    move-result-object v11

    .line 745
    const-string v24, "Route"

    new-instance v25, Ljava/lang/StringBuilder;

    const-string v26, "KML Data Length: "

    invoke-direct/range {v25 .. v26}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v11}, Ljava/lang/String;->length()I

    move-result v26

    invoke-virtual/range {v25 .. v26}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v25

    invoke-virtual/range {v25 .. v25}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v25

    invoke-static/range {v24 .. v25}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 746
    invoke-virtual {v11}, Ljava/lang/String;->length()I

    move-result v24

    if-lez v24, :cond_0

    const-string v24, "<LineString>"

    move-object/from16 v0, v24

    invoke-virtual {v11, v0}, Ljava/lang/String;->indexOf(Ljava/lang/String;)I

    move-result v24

    if-gez v24, :cond_2

    .line 747
    :cond_0
    const/16 v19, 0x0

    .line 792
    .end local v8           #httpGet:Lorg/apache/http/client/methods/HttpGet;
    .end local v9           #httpclient:Lorg/apache/http/client/HttpClient;
    .end local v10           #in:Ljava/io/BufferedInputStream;
    .end local v11           #kml:Ljava/lang/String;
    .end local v14           #line:Ljava/lang/String;
    .end local v20           #rd:Ljava/io/BufferedReader;
    .end local v21           #response:Lorg/apache/http/HttpResponse;
    .end local v22           #sb:Ljava/lang/StringBuilder;
    :goto_1
    return-object v19

    .line 706
    :catch_0
    move-exception v5

    .line 707
    .local v5, e:Ljava/lang/Exception;
    invoke-virtual {v5}, Ljava/lang/Exception;->printStackTrace()V

    .line 708
    const/16 v19, 0x0

    goto :goto_1

    .line 717
    .end local v5           #e:Ljava/lang/Exception;
    .restart local v8       #httpGet:Lorg/apache/http/client/methods/HttpGet;
    .restart local v9       #httpclient:Lorg/apache/http/client/HttpClient;
    .restart local v21       #response:Lorg/apache/http/HttpResponse;
    :catch_1
    move-exception v5

    .line 718
    .restart local v5       #e:Ljava/lang/Exception;
    invoke-virtual {v5}, Ljava/lang/Exception;->printStackTrace()V

    .line 719
    const/16 v19, 0x0

    goto :goto_1

    .line 732
    .end local v5           #e:Ljava/lang/Exception;
    .restart local v10       #in:Ljava/io/BufferedInputStream;
    .restart local v11       #kml:Ljava/lang/String;
    .restart local v14       #line:Ljava/lang/String;
    .restart local v20       #rd:Ljava/io/BufferedReader;
    .restart local v22       #sb:Ljava/lang/StringBuilder;
    :cond_1
    :try_start_3
    move-object/from16 v0, v22

    invoke-virtual {v0, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    :try_end_3
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_3} :catch_2

    goto :goto_0

    .line 739
    .end local v10           #in:Ljava/io/BufferedInputStream;
    .end local v14           #line:Ljava/lang/String;
    .end local v20           #rd:Ljava/io/BufferedReader;
    .end local v22           #sb:Ljava/lang/StringBuilder;
    :catch_2
    move-exception v5

    .line 740
    .restart local v5       #e:Ljava/lang/Exception;
    const/16 v19, 0x0

    goto :goto_1

    .line 750
    .end local v5           #e:Ljava/lang/Exception;
    .restart local v10       #in:Ljava/io/BufferedInputStream;
    .restart local v14       #line:Ljava/lang/String;
    .restart local v20       #rd:Ljava/io/BufferedReader;
    .restart local v22       #sb:Ljava/lang/StringBuilder;
    :cond_2
    invoke-static {}, Ljavax/xml/parsers/DocumentBuilderFactory;->newInstance()Ljavax/xml/parsers/DocumentBuilderFactory;

    move-result-object v6

    .line 751
    .local v6, factory:Ljavax/xml/parsers/DocumentBuilderFactory;
    const/4 v4, 0x0

    .line 752
    .local v4, builder:Ljavax/xml/parsers/DocumentBuilder;
    const/4 v12, 0x0

    .line 754
    .local v12, kmlDoc:Lorg/w3c/dom/Document;
    :try_start_4
    invoke-virtual {v6}, Ljavax/xml/parsers/DocumentBuilderFactory;->newDocumentBuilder()Ljavax/xml/parsers/DocumentBuilder;
    :try_end_4
    .catch Ljavax/xml/parsers/ParserConfigurationException; {:try_start_4 .. :try_end_4} :catch_3

    move-result-object v4

    .line 759
    :goto_2
    :try_start_5
    new-instance v24, Lorg/xml/sax/InputSource;

    new-instance v25, Ljava/io/StringReader;

    move-object/from16 v0, v25

    invoke-direct {v0, v11}, Ljava/io/StringReader;-><init>(Ljava/lang/String;)V

    invoke-direct/range {v24 .. v25}, Lorg/xml/sax/InputSource;-><init>(Ljava/io/Reader;)V

    move-object/from16 v0, v24

    invoke-virtual {v4, v0}, Ljavax/xml/parsers/DocumentBuilder;->parse(Lorg/xml/sax/InputSource;)Lorg/w3c/dom/Document;
    :try_end_5
    .catch Lorg/xml/sax/SAXException; {:try_start_5 .. :try_end_5} :catch_4
    .catch Ljava/io/IOException; {:try_start_5 .. :try_end_5} :catch_5

    move-result-object v12

    .line 766
    :goto_3
    const-string v15, ""

    .line 768
    .local v15, lineStrings:Ljava/lang/String;
    :try_start_6
    const-string v24, "LineString"

    move-object/from16 v0, v24

    invoke-interface {v12, v0}, Lorg/w3c/dom/Document;->getElementsByTagName(Ljava/lang/String;)Lorg/w3c/dom/NodeList;

    move-result-object v24

    const/16 v25, 0x0

    invoke-interface/range {v24 .. v25}, Lorg/w3c/dom/NodeList;->item(I)Lorg/w3c/dom/Node;

    move-result-object v24

    .line 769
    invoke-interface/range {v24 .. v24}, Lorg/w3c/dom/Node;->getChildNodes()Lorg/w3c/dom/NodeList;

    move-result-object v24

    const/16 v25, 0x0

    invoke-interface/range {v24 .. v25}, Lorg/w3c/dom/NodeList;->item(I)Lorg/w3c/dom/Node;

    move-result-object v24

    invoke-interface/range {v24 .. v24}, Lorg/w3c/dom/Node;->getFirstChild()Lorg/w3c/dom/Node;

    move-result-object v24

    invoke-interface/range {v24 .. v24}, Lorg/w3c/dom/Node;->getNodeValue()Ljava/lang/String;
    :try_end_6
    .catchall {:try_start_6 .. :try_end_6} :catchall_0
    .catch Ljava/lang/Exception; {:try_start_6 .. :try_end_6} :catch_6

    move-result-object v15

    .line 773
    invoke-virtual {v15}, Ljava/lang/String;->length()I

    move-result v24

    if-nez v24, :cond_5

    .line 774
    const/16 v19, 0x0

    goto :goto_1

    .line 755
    .end local v15           #lineStrings:Ljava/lang/String;
    :catch_3
    move-exception v5

    .line 756
    .local v5, e:Ljavax/xml/parsers/ParserConfigurationException;
    invoke-virtual {v5}, Ljavax/xml/parsers/ParserConfigurationException;->printStackTrace()V

    goto :goto_2

    .line 760
    .end local v5           #e:Ljavax/xml/parsers/ParserConfigurationException;
    :catch_4
    move-exception v5

    .line 761
    .local v5, e:Lorg/xml/sax/SAXException;
    invoke-virtual {v5}, Lorg/xml/sax/SAXException;->printStackTrace()V

    goto :goto_3

    .line 762
    .end local v5           #e:Lorg/xml/sax/SAXException;
    :catch_5
    move-exception v5

    .line 763
    .local v5, e:Ljava/io/IOException;
    invoke-virtual {v5}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_3

    .line 770
    .end local v5           #e:Ljava/io/IOException;
    .restart local v15       #lineStrings:Ljava/lang/String;
    :catch_6
    move-exception v5

    .line 773
    .local v5, e:Ljava/lang/Exception;
    invoke-virtual {v15}, Ljava/lang/String;->length()I

    move-result v24

    if-nez v24, :cond_3

    .line 774
    const/16 v19, 0x0

    goto :goto_1

    .line 771
    :cond_3
    const/16 v19, 0x0

    goto :goto_1

    .line 772
    .end local v5           #e:Ljava/lang/Exception;
    :catchall_0
    move-exception v24

    .line 773
    invoke-virtual {v15}, Ljava/lang/String;->length()I

    move-result v25

    if-nez v25, :cond_4

    .line 774
    const/16 v19, 0x0

    goto/16 :goto_1

    .line 775
    :cond_4
    throw v24

    .line 776
    :cond_5
    const-string v24, "Route"

    new-instance v25, Ljava/lang/StringBuilder;

    const-string v26, "KML lineStrings: "

    invoke-direct/range {v25 .. v26}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    move-object/from16 v0, v25

    invoke-virtual {v0, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v25

    invoke-virtual/range {v25 .. v25}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v25

    invoke-static/range {v24 .. v25}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 777
    const-string v24, " "

    move-object/from16 v0, v24

    invoke-virtual {v15, v0}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v7

    .line 779
    .local v7, geoPoints:[Ljava/lang/String;
    new-instance v19, Ljava/util/ArrayList;

    invoke-direct/range {v19 .. v19}, Ljava/util/ArrayList;-><init>()V

    .line 780
    .local v19, poly:Ljava/util/List;,"Ljava/util/List<Lcom/google/android/maps/GeoPoint;>;"
    const/4 v13, 0x0

    .local v13, lat:I
    const/16 v16, 0x0

    .line 781
    .local v16, lng:I
    array-length v0, v7

    move/from16 v25, v0

    const/16 v24, 0x0

    :goto_4
    move/from16 v0, v24

    move/from16 v1, v25

    if-lt v0, v1, :cond_6

    .line 790
    const-string v24, "Route"

    new-instance v25, Ljava/lang/StringBuilder;

    const-string v26, "Waypoint count: "

    invoke-direct/range {v25 .. v26}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-interface/range {v19 .. v19}, Ljava/util/List;->size()I

    move-result v26

    invoke-virtual/range {v25 .. v26}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v25

    invoke-virtual/range {v25 .. v25}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v25

    invoke-static/range {v24 .. v25}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_1

    .line 781
    :cond_6
    aget-object v18, v7, v24

    .line 782
    .local v18, point:Ljava/lang/String;
    const-string v26, ","

    move-object/from16 v0, v18

    move-object/from16 v1, v26

    invoke-virtual {v0, v1}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v26

    const/16 v27, 0x0

    aget-object v26, v26, v27

    invoke-static/range {v26 .. v26}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v26

    const-wide v28, 0x412e848000000000L

    mul-double v26, v26, v28

    move-wide/from16 v0, v26

    double-to-int v0, v0

    move/from16 v16, v0

    .line 783
    const-string v26, ","

    move-object/from16 v0, v18

    move-object/from16 v1, v26

    invoke-virtual {v0, v1}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v26

    const/16 v27, 0x1

    aget-object v26, v26, v27

    invoke-static/range {v26 .. v26}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v26

    const-wide v28, 0x412e848000000000L

    mul-double v26, v26, v28

    move-wide/from16 v0, v26

    double-to-int v13, v0

    .line 784
    new-instance v17, Lcom/google/android/maps/GeoPoint;

    move-object/from16 v0, v17

    move/from16 v1, v16

    invoke-direct {v0, v13, v1}, Lcom/google/android/maps/GeoPoint;-><init>(II)V

    .line 787
    .local v17, p:Lcom/google/android/maps/GeoPoint;
    move-object/from16 v0, v19

    move-object/from16 v1, v17

    invoke-interface {v0, v1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 781
    add-int/lit8 v24, v24, 0x1

    goto :goto_4
.end method

.method private hidePopup()V
    .locals 2

    .prologue
    .line 524
    .line 525
    const v1, 0x7f090084

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/TabMap;->findViewById(I)Landroid/view/View;

    move-result-object v0

    .line 524
    check-cast v0, Landroid/widget/SlidingDrawer;

    .line 526
    .local v0, drawer:Landroid/widget/SlidingDrawer;
    invoke-virtual {v0}, Landroid/widget/SlidingDrawer;->isOpened()Z

    move-result v1

    if-eqz v1, :cond_0

    .line 527
    invoke-virtual {v0}, Landroid/widget/SlidingDrawer;->close()V

    .line 528
    :cond_0
    return-void
.end method

.method private initPopup()V
    .locals 2

    .prologue
    .line 503
    .line 504
    const v1, 0x7f090084

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/TabMap;->findViewById(I)Landroid/view/View;

    move-result-object v0

    .line 503
    check-cast v0, Landroid/widget/SlidingDrawer;

    .line 507
    .local v0, drawer:Landroid/widget/SlidingDrawer;
    new-instance v1, Lcom/openvehicles/OVMS/TabMap$20;

    invoke-direct {v1, p0}, Lcom/openvehicles/OVMS/TabMap$20;-><init>(Lcom/openvehicles/OVMS/TabMap;)V

    invoke-virtual {v0, v1}, Landroid/widget/SlidingDrawer;->setOnDrawerOpenListener(Landroid/widget/SlidingDrawer$OnDrawerOpenListener;)V

    .line 515
    new-instance v1, Lcom/openvehicles/OVMS/TabMap$21;

    invoke-direct {v1, p0}, Lcom/openvehicles/OVMS/TabMap$21;-><init>(Lcom/openvehicles/OVMS/TabMap;)V

    invoke-virtual {v0, v1}, Landroid/widget/SlidingDrawer;->setOnDrawerCloseListener(Landroid/widget/SlidingDrawer$OnDrawerCloseListener;)V

    .line 521
    return-void
.end method

.method private planRoute()V
    .locals 3

    .prologue
    .line 618
    const-string v1, "Routing..."

    const/4 v2, 0x0

    invoke-static {p0, v1, v2}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v1

    invoke-virtual {v1}, Landroid/widget/Toast;->show()V

    .line 622
    const-wide/16 v1, 0x64

    :try_start_0
    invoke-static {v1, v2}, Ljava/lang/Thread;->sleep(J)V
    :try_end_0
    .catch Ljava/lang/InterruptedException; {:try_start_0 .. :try_end_0} :catch_0

    .line 627
    :goto_0
    new-instance v1, Lcom/openvehicles/OVMS/TabMap$22;

    invoke-direct {v1, p0}, Lcom/openvehicles/OVMS/TabMap$22;-><init>(Lcom/openvehicles/OVMS/TabMap;)V

    .line 637
    invoke-virtual {v1}, Lcom/openvehicles/OVMS/TabMap$22;->start()V

    .line 638
    return-void

    .line 623
    :catch_0
    move-exception v0

    .line 624
    .local v0, e:Ljava/lang/InterruptedException;
    invoke-virtual {v0}, Ljava/lang/InterruptedException;->printStackTrace()V

    goto :goto_0
.end method

.method private showGroupCarPopup(Ljava/lang/String;)V
    .locals 21
    .parameter "GroupCarVehicleID"

    .prologue
    .line 391
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;

    move-object/from16 v16, v0

    move-object/from16 v0, v16

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->Group:Ljava/util/HashMap;

    move-object/from16 v16, v0

    if-nez v16, :cond_1

    .line 486
    :cond_0
    :goto_0
    return-void

    .line 395
    :cond_1
    const/4 v11, 0x0

    .line 396
    .local v11, groupCarTemp:Lcom/openvehicles/OVMS/CarData_Group;
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;

    move-object/from16 v16, v0

    move-object/from16 v0, v16

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->Group:Ljava/util/HashMap;

    move-object/from16 v16, v0

    invoke-virtual/range {v16 .. v16}, Ljava/util/HashMap;->values()Ljava/util/Collection;

    move-result-object v16

    invoke-interface/range {v16 .. v16}, Ljava/util/Collection;->iterator()Ljava/util/Iterator;

    move-result-object v16

    :cond_2
    invoke-interface/range {v16 .. v16}, Ljava/util/Iterator;->hasNext()Z

    move-result v17

    if-nez v17, :cond_5

    .line 403
    :goto_1
    move-object v10, v11

    .line 405
    .local v10, groupCar:Lcom/openvehicles/OVMS/CarData_Group;
    invoke-static/range {p0 .. p0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object v8

    .line 406
    .local v8, factory:Landroid/view/LayoutInflater;
    const v16, 0x7f030011

    const/16 v17, 0x0

    move/from16 v0, v16

    move-object/from16 v1, v17

    invoke-virtual {v8, v0, v1}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object v5

    .line 407
    .local v5, detailsView:Landroid/view/View;
    const v16, 0x7f09008f

    move/from16 v0, v16

    invoke-virtual {v5, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v15

    check-cast v15, Landroid/widget/Spinner;

    .line 408
    .local v15, spin:Landroid/widget/Spinner;
    const v16, 0x7f090090

    move/from16 v0, v16

    invoke-virtual {v5, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/widget/TextView;

    .line 409
    .local v2, SOC:Landroid/widget/TextView;
    const v16, 0x7f090091

    move/from16 v0, v16

    invoke-virtual {v5, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v14

    check-cast v14, Landroid/widget/TextView;

    .line 410
    .local v14, speed:Landroid/widget/TextView;
    const v16, 0x7f090092

    move/from16 v0, v16

    invoke-virtual {v5, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v6

    check-cast v6, Landroid/widget/TextView;

    .line 411
    .local v6, direction:Landroid/widget/TextView;
    const v16, 0x7f090093

    move/from16 v0, v16

    invoke-virtual {v5, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v9

    check-cast v9, Landroid/widget/TextView;

    .line 414
    .local v9, gps:Landroid/widget/TextView;
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap;->availableCarColorsSpinnerAdapter:Lcom/openvehicles/OVMS/TabMap$CustomSpinnerAdapter;

    move-object/from16 v16, v0

    invoke-virtual/range {v15 .. v16}, Landroid/widget/Spinner;->setAdapter(Landroid/widget/SpinnerAdapter;)V

    .line 415
    iget-object v0, v10, Lcom/openvehicles/OVMS/CarData_Group;->VehicleImageDrawable:Ljava/lang/String;

    move-object/from16 v16, v0

    if-eqz v16, :cond_3

    iget-object v0, v10, Lcom/openvehicles/OVMS/CarData_Group;->VehicleImageDrawable:Ljava/lang/String;

    move-object/from16 v16, v0

    invoke-virtual/range {v16 .. v16}, Ljava/lang/String;->length()I

    move-result v16

    if-nez v16, :cond_6

    .line 416
    :cond_3
    const/16 v16, 0x0

    invoke-virtual/range {v15 .. v16}, Landroid/widget/Spinner;->setSelection(I)V

    .line 430
    :cond_4
    :goto_2
    const-string v16, "%s%%"

    const/16 v17, 0x1

    move/from16 v0, v17

    new-array v0, v0, [Ljava/lang/Object;

    move-object/from16 v17, v0

    const/16 v18, 0x0

    iget-wide v0, v10, Lcom/openvehicles/OVMS/CarData_Group;->SOC:D

    move-wide/from16 v19, v0

    invoke-static/range {v19 .. v20}, Ljava/lang/Double;->valueOf(D)Ljava/lang/Double;

    move-result-object v19

    aput-object v19, v17, v18

    invoke-static/range {v16 .. v17}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v16

    move-object/from16 v0, v16

    invoke-virtual {v2, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 431
    const-string v17, "%s %s"

    const/16 v16, 0x2

    move/from16 v0, v16

    new-array v0, v0, [Ljava/lang/Object;

    move-object/from16 v18, v0

    const/16 v16, 0x0

    iget-wide v0, v10, Lcom/openvehicles/OVMS/CarData_Group;->Speed:D

    move-wide/from16 v19, v0

    invoke-static/range {v19 .. v20}, Ljava/lang/Double;->valueOf(D)Ljava/lang/Double;

    move-result-object v19

    aput-object v19, v18, v16

    const/16 v19, 0x1

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;

    move-object/from16 v16, v0

    move-object/from16 v0, v16

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->Data_DistanceUnit:Ljava/lang/String;

    move-object/from16 v16, v0

    const-string v20, "K"

    move-object/from16 v0, v16

    move-object/from16 v1, v20

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v16

    if-eqz v16, :cond_8

    const-string v16, "kph"

    :goto_3
    aput-object v16, v18, v19

    invoke-static/range {v17 .. v18}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v16

    move-object/from16 v0, v16

    invoke-virtual {v14, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 432
    const-string v17, "%s\' %s%s"

    const/16 v16, 0x3

    move/from16 v0, v16

    new-array v0, v0, [Ljava/lang/Object;

    move-object/from16 v18, v0

    const/16 v16, 0x0

    iget-wide v0, v10, Lcom/openvehicles/OVMS/CarData_Group;->Direction:D

    move-wide/from16 v19, v0

    invoke-static/range {v19 .. v20}, Ljava/lang/Double;->valueOf(D)Ljava/lang/Double;

    move-result-object v19

    aput-object v19, v18, v16

    const/16 v16, 0x1

    iget-wide v0, v10, Lcom/openvehicles/OVMS/CarData_Group;->Altitude:D

    move-wide/from16 v19, v0

    invoke-static/range {v19 .. v20}, Ljava/lang/Double;->valueOf(D)Ljava/lang/Double;

    move-result-object v19

    aput-object v19, v18, v16

    const/16 v19, 0x2

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;

    move-object/from16 v16, v0

    move-object/from16 v0, v16

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->Data_DistanceUnit:Ljava/lang/String;

    move-object/from16 v16, v0

    const-string v20, "K"

    move-object/from16 v0, v16

    move-object/from16 v1, v20

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v16

    if-eqz v16, :cond_9

    const-string v16, "m"

    :goto_4
    aput-object v16, v18, v19

    invoke-static/range {v17 .. v18}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v16

    move-object/from16 v0, v16

    invoke-virtual {v6, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 433
    const-string v17, "%s %s"

    const/16 v16, 0x2

    move/from16 v0, v16

    new-array v0, v0, [Ljava/lang/Object;

    move-object/from16 v18, v0

    const/16 v19, 0x0

    iget-boolean v0, v10, Lcom/openvehicles/OVMS/CarData_Group;->GPSLocked:Z

    move/from16 v16, v0

    if-eqz v16, :cond_a

    const-string v16, "LOCK"

    :goto_5
    aput-object v16, v18, v19

    const/16 v19, 0x1

    iget-boolean v0, v10, Lcom/openvehicles/OVMS/CarData_Group;->GPSDataStale:Z

    move/from16 v16, v0

    if-eqz v16, :cond_b

    const-string v16, "(STALE)"

    :goto_6
    aput-object v16, v18, v19

    invoke-static/range {v17 .. v18}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v16

    move-object/from16 v0, v16

    invoke-virtual {v9, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 435
    new-instance v4, Landroid/app/AlertDialog$Builder;

    move-object/from16 v0, p0

    invoke-direct {v4, v0}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 437
    .local v4, builder:Landroid/app/AlertDialog$Builder;
    const-string v16, "%s (%s)"

    const/16 v17, 0x2

    move/from16 v0, v17

    new-array v0, v0, [Ljava/lang/Object;

    move-object/from16 v17, v0

    const/16 v18, 0x0

    iget-object v0, v10, Lcom/openvehicles/OVMS/CarData_Group;->VehicleID:Ljava/lang/String;

    move-object/from16 v19, v0

    aput-object v19, v17, v18

    const/16 v18, 0x1

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;

    move-object/from16 v19, v0

    move-object/from16 v0, v19

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->Data_Parameters:Ljava/util/LinkedHashMap;

    move-object/from16 v19, v0

    const/16 v20, 0xb

    invoke-static/range {v20 .. v20}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v20

    invoke-virtual/range {v19 .. v20}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v19

    aput-object v19, v17, v18

    invoke-static/range {v16 .. v17}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v16

    move-object/from16 v0, v16

    invoke-virtual {v4, v0}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v16

    .line 438
    move-object/from16 v0, v16

    invoke-virtual {v0, v5}, Landroid/app/AlertDialog$Builder;->setView(Landroid/view/View;)Landroid/app/AlertDialog$Builder;

    move-result-object v16

    .line 439
    const/16 v17, 0x1

    invoke-virtual/range {v16 .. v17}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    move-result-object v16

    .line 440
    const-string v17, "Goto"

    .line 441
    new-instance v18, Lcom/openvehicles/OVMS/TabMap$18;

    move-object/from16 v0, v18

    move-object/from16 v1, p0

    invoke-direct {v0, v1, v10, v15}, Lcom/openvehicles/OVMS/TabMap$18;-><init>(Lcom/openvehicles/OVMS/TabMap;Lcom/openvehicles/OVMS/CarData_Group;Landroid/widget/Spinner;)V

    .line 440
    invoke-virtual/range {v16 .. v18}, Landroid/app/AlertDialog$Builder;->setPositiveButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v16

    .line 461
    const-string v17, "Close"

    .line 462
    new-instance v18, Lcom/openvehicles/OVMS/TabMap$19;

    move-object/from16 v0, v18

    move-object/from16 v1, p0

    invoke-direct {v0, v1, v10, v15}, Lcom/openvehicles/OVMS/TabMap$19;-><init>(Lcom/openvehicles/OVMS/TabMap;Lcom/openvehicles/OVMS/CarData_Group;Landroid/widget/Spinner;)V

    .line 461
    invoke-virtual/range {v16 .. v18}, Landroid/app/AlertDialog$Builder;->setNegativeButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 483
    invoke-virtual {v4}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v3

    .line 484
    .local v3, alertDialog:Landroid/app/AlertDialog;
    invoke-virtual/range {p0 .. p0}, Lcom/openvehicles/OVMS/TabMap;->isFinishing()Z

    move-result v16

    if-nez v16, :cond_0

    .line 485
    invoke-virtual {v3}, Landroid/app/AlertDialog;->show()V

    goto/16 :goto_0

    .line 396
    .end local v2           #SOC:Landroid/widget/TextView;
    .end local v3           #alertDialog:Landroid/app/AlertDialog;
    .end local v4           #builder:Landroid/app/AlertDialog$Builder;
    .end local v5           #detailsView:Landroid/view/View;
    .end local v6           #direction:Landroid/widget/TextView;
    .end local v8           #factory:Landroid/view/LayoutInflater;
    .end local v9           #gps:Landroid/widget/TextView;
    .end local v10           #groupCar:Lcom/openvehicles/OVMS/CarData_Group;
    .end local v14           #speed:Landroid/widget/TextView;
    .end local v15           #spin:Landroid/widget/Spinner;
    :cond_5
    invoke-interface/range {v16 .. v16}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v7

    check-cast v7, Lcom/openvehicles/OVMS/CarData_Group;

    .line 397
    .local v7, entry:Lcom/openvehicles/OVMS/CarData_Group;
    iget-object v0, v7, Lcom/openvehicles/OVMS/CarData_Group;->VehicleID:Ljava/lang/String;

    move-object/from16 v17, v0

    move-object/from16 v0, v17

    move-object/from16 v1, p1

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v17

    if-eqz v17, :cond_2

    .line 399
    move-object v11, v7

    .line 400
    goto/16 :goto_1

    .line 420
    .end local v7           #entry:Lcom/openvehicles/OVMS/CarData_Group;
    .restart local v2       #SOC:Landroid/widget/TextView;
    .restart local v5       #detailsView:Landroid/view/View;
    .restart local v6       #direction:Landroid/widget/TextView;
    .restart local v8       #factory:Landroid/view/LayoutInflater;
    .restart local v9       #gps:Landroid/widget/TextView;
    .restart local v10       #groupCar:Lcom/openvehicles/OVMS/CarData_Group;
    .restart local v14       #speed:Landroid/widget/TextView;
    .restart local v15       #spin:Landroid/widget/Spinner;
    :cond_6
    const/4 v12, 0x0

    .local v12, i:I
    :goto_7
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap;->availableCarColors:Ljava/util/ArrayList;

    move-object/from16 v16, v0

    invoke-virtual/range {v16 .. v16}, Ljava/util/ArrayList;->size()I

    move-result v16

    move/from16 v0, v16

    if-ge v12, v0, :cond_4

    .line 421
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap;->availableCarColors:Ljava/util/ArrayList;

    move-object/from16 v16, v0

    move-object/from16 v0, v16

    invoke-virtual {v0, v12}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v13

    check-cast v13, Ljava/util/HashMap;

    .line 422
    .local v13, item:Ljava/util/HashMap;,"Ljava/util/HashMap<Ljava/lang/String;Ljava/lang/Object;>;"
    const-string v16, "Name"

    move-object/from16 v0, v16

    invoke-virtual {v13, v0}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v16

    invoke-virtual/range {v16 .. v16}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v16

    iget-object v0, v10, Lcom/openvehicles/OVMS/CarData_Group;->VehicleImageDrawable:Ljava/lang/String;

    move-object/from16 v17, v0

    invoke-virtual/range {v16 .. v17}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v16

    if-eqz v16, :cond_7

    .line 424
    invoke-virtual {v15, v12}, Landroid/widget/Spinner;->setSelection(I)V

    goto/16 :goto_2

    .line 420
    :cond_7
    add-int/lit8 v12, v12, 0x1

    goto :goto_7

    .line 431
    .end local v12           #i:I
    .end local v13           #item:Ljava/util/HashMap;,"Ljava/util/HashMap<Ljava/lang/String;Ljava/lang/Object;>;"
    :cond_8
    const-string v16, "mph"

    goto/16 :goto_3

    .line 432
    :cond_9
    const-string v16, "ft"

    goto/16 :goto_4

    .line 433
    :cond_a
    const-string v16, "Searching..."

    goto/16 :goto_5

    :cond_b
    const-string v16, ""

    goto/16 :goto_6
.end method

.method private showPopup()V
    .locals 2

    .prologue
    .line 531
    .line 532
    const v1, 0x7f090084

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/TabMap;->findViewById(I)Landroid/view/View;

    move-result-object v0

    .line 531
    check-cast v0, Landroid/widget/SlidingDrawer;

    .line 533
    .local v0, drawer:Landroid/widget/SlidingDrawer;
    invoke-virtual {v0}, Landroid/widget/SlidingDrawer;->open()V

    .line 534
    return-void
.end method

.method private updateLastUpdatedView()V
    .locals 0

    .prologue
    .line 615
    return-void
.end method

.method private updateRoute()V
    .locals 5

    .prologue
    .line 686
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap;->myLocationOverlay:Lcom/openvehicles/OVMS/MyLocationOverlayCustom;

    invoke-virtual {v1}, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->disableMyLocation()V

    .line 688
    invoke-direct {p0}, Lcom/openvehicles/OVMS/TabMap;->getRouteGeoPoints()Ljava/util/List;

    move-result-object v0

    .line 689
    .local v0, geoPoints:Ljava/util/List;,"Ljava/util/List<Lcom/google/android/maps/GeoPoint;>;"
    if-eqz v0, :cond_0

    .line 690
    const v1, -0xff0100

    invoke-direct {p0, v0, v1}, Lcom/openvehicles/OVMS/TabMap;->drawRoute(Ljava/util/List;I)V

    .line 695
    :goto_0
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap;->refreshUIHandler:Landroid/os/Handler;

    iget-object v2, p0, Lcom/openvehicles/OVMS/TabMap;->myLocationEnable:Ljava/lang/Runnable;

    const-wide/16 v3, 0xc8

    invoke-virtual {v1, v2, v3, v4}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    .line 697
    const-string v1, "Route"

    const-string v2, "Route complete"

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 698
    return-void

    .line 692
    :cond_0
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap;->routeError:Ljava/lang/Runnable;

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/TabMap;->runOnUiThread(Ljava/lang/Runnable;)V

    goto :goto_0
.end method


# virtual methods
.method public Refresh(Lcom/openvehicles/OVMS/CarData;Z)V
    .locals 7
    .parameter "carData"
    .parameter "isLoggedIn"

    .prologue
    const/4 v6, 0x0

    .line 1308
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;

    .line 1309
    iput-boolean p2, p0, Lcom/openvehicles/OVMS/TabMap;->isLoggedIn:Z

    .line 1312
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->Group:Ljava/util/HashMap;

    if-nez v0, :cond_0

    .line 1313
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;

    new-instance v2, Ljava/util/HashMap;

    invoke-direct {v2}, Ljava/util/HashMap;-><init>()V

    iput-object v2, v0, Lcom/openvehicles/OVMS/CarData;->Group:Ljava/util/HashMap;

    .line 1316
    :cond_0
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->currentVehicleID:Ljava/lang/String;

    iget-object v2, p1, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    invoke-virtual {v0, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_1

    .line 1317
    iget-object v0, p1, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->currentVehicleID:Ljava/lang/String;

    .line 1321
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TabMap;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    .line 1322
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TabMap;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    .line 1323
    new-instance v3, Ljava/lang/StringBuilder;

    iget-object v4, p0, Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v4, v4, Lcom/openvehicles/OVMS/CarData;->VehicleImageDrawable:Ljava/lang/String;

    invoke-static {v4}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v4

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v4, "32x32"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    const-string v4, "drawable"

    .line 1324
    const-string v5, "com.openvehicles.OVMS"

    .line 1322
    invoke-virtual {v2, v3, v4, v5}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v2

    .line 1321
    invoke-virtual {v0, v2}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    .line 1326
    .local v1, drawable:Landroid/graphics/drawable/Drawable;
    new-instance v0, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;

    .line 1327
    const/16 v2, 0x14

    iget-object v4, p0, Lcom/openvehicles/OVMS/TabMap;->DirectionalMarker:Landroid/graphics/Bitmap;

    const/4 v5, 0x1

    move-object v3, p0

    invoke-direct/range {v0 .. v5}, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;-><init>(Landroid/graphics/drawable/Drawable;ILandroid/content/Context;Landroid/graphics/Bitmap;I)V

    .line 1326
    iput-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->carMarkers:Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;

    .line 1328
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->mapOverlays:Ljava/util/List;

    iget-object v2, p0, Lcom/openvehicles/OVMS/TabMap;->carMarkers:Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;

    invoke-interface {v0, v6, v2}, Ljava/util/List;->set(ILjava/lang/Object;)Ljava/lang/Object;

    .line 1329
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->carMarkers:Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;

    new-instance v2, Lcom/openvehicles/OVMS/TabMap$23;

    invoke-direct {v2, p0}, Lcom/openvehicles/OVMS/TabMap$23;-><init>(Lcom/openvehicles/OVMS/TabMap;)V

    invoke-virtual {v0, v2}, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->addOnGroupCarTappedListener(Lcom/openvehicles/OVMS/Utilities$OnGroupCarTappedListener;)V

    .line 1338
    .end local v1           #drawable:Landroid/graphics/drawable/Drawable;
    :cond_1
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->refreshUIHandler:Landroid/os/Handler;

    invoke-virtual {v0, v6}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    .line 1340
    return-void
.end method

.method public getMapKMLUrl(Lcom/google/android/maps/GeoPoint;Lcom/google/android/maps/GeoPoint;Z)Ljava/lang/String;
    .locals 5
    .parameter "src"
    .parameter "dest"
    .parameter "planWalkingRoute"

    .prologue
    const-wide v3, 0x412e848000000000L

    .line 798
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    .line 800
    .local v0, urlString:Ljava/lang/StringBuilder;
    const-string v1, "http://maps.google.com/maps?f=d&hl=en"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 801
    const-string v1, "&saddr="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 802
    invoke-virtual {p1}, Lcom/google/android/maps/GeoPoint;->getLatitudeE6()I

    move-result v1

    int-to-double v1, v1

    div-double/2addr v1, v3

    invoke-static {v1, v2}, Ljava/lang/Double;->toString(D)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 803
    const-string v1, ","

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 805
    invoke-virtual {p1}, Lcom/google/android/maps/GeoPoint;->getLongitudeE6()I

    move-result v1

    int-to-double v1, v1

    div-double/2addr v1, v3

    invoke-static {v1, v2}, Ljava/lang/Double;->toString(D)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 806
    const-string v1, "&daddr="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 808
    invoke-virtual {p2}, Lcom/google/android/maps/GeoPoint;->getLatitudeE6()I

    move-result v1

    int-to-double v1, v1

    div-double/2addr v1, v3

    invoke-static {v1, v2}, Ljava/lang/Double;->toString(D)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 809
    const-string v1, ","

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 811
    invoke-virtual {p2}, Lcom/google/android/maps/GeoPoint;->getLongitudeE6()I

    move-result v1

    int-to-double v1, v1

    div-double/2addr v1, v3

    invoke-static {v1, v2}, Ljava/lang/Double;->toString(D)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 812
    const-string v1, "&ie=UTF8&0&om=0&output=kml"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 813
    if-eqz p3, :cond_0

    .line 814
    const-string v1, "&dirflg=w"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 825
    :cond_0
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    return-object v1
.end method

.method protected isRouteDisplayed()Z
    .locals 1

    .prologue
    .line 887
    const/4 v0, 0x0

    return v0
.end method

.method public onBackPressed()V
    .locals 2

    .prologue
    .line 490
    .line 491
    const v1, 0x7f090084

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/TabMap;->findViewById(I)Landroid/view/View;

    move-result-object v0

    .line 490
    check-cast v0, Landroid/widget/SlidingDrawer;

    .line 492
    .local v0, drawer:Landroid/widget/SlidingDrawer;
    invoke-virtual {v0}, Landroid/widget/SlidingDrawer;->isOpened()Z

    move-result v1

    if-eqz v1, :cond_0

    .line 494
    invoke-virtual {v0}, Landroid/widget/SlidingDrawer;->close()V

    .line 498
    :goto_0
    return-void

    .line 497
    :cond_0
    invoke-super {p0}, Lcom/google/android/maps/MapActivity;->onBackPressed()V

    goto :goto_0
.end method

.method public onCreate(Landroid/os/Bundle;)V
    .locals 15
    .parameter "savedInstanceState"

    .prologue
    .line 73
    invoke-super/range {p0 .. p1}, Lcom/google/android/maps/MapActivity;->onCreate(Landroid/os/Bundle;)V

    .line 75
    const v0, 0x7f030010

    invoke-virtual {p0, v0}, Lcom/openvehicles/OVMS/TabMap;->setContentView(I)V

    .line 77
    const v0, 0x7f090082

    invoke-virtual {p0, v0}, Lcom/openvehicles/OVMS/TabMap;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Lcom/google/android/maps/MapView;

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->mapView:Lcom/google/android/maps/MapView;

    .line 78
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->mapView:Lcom/google/android/maps/MapView;

    invoke-virtual {v0}, Lcom/google/android/maps/MapView;->getController()Lcom/google/android/maps/MapController;

    move-result-object v0

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->mc:Lcom/google/android/maps/MapController;

    .line 79
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->mapView:Lcom/google/android/maps/MapView;

    const/4 v2, 0x1

    invoke-virtual {v0, v2}, Lcom/google/android/maps/MapView;->setBuiltInZoomControls(Z)V

    .line 81
    new-instance v0, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;

    const/4 v2, 0x0

    invoke-direct {v0, p0, v2}, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;-><init>(Lcom/openvehicles/OVMS/TabMap;Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->centeringMode:Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;

    .line 82
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->centeringMode:Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;

    new-instance v2, Lcom/openvehicles/OVMS/TabMap$9;

    invoke-direct {v2, p0}, Lcom/openvehicles/OVMS/TabMap$9;-><init>(Lcom/openvehicles/OVMS/TabMap;)V

    invoke-virtual {v0, v2}, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;->setOnMapCenteringModeChangedListener(Lcom/openvehicles/OVMS/TabMap$OnCenteringModeChangedListener;)V

    .line 91
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TabMap;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    .line 92
    const v2, 0x7f020008

    .line 91
    invoke-static {v0, v2}, Landroid/graphics/BitmapFactory;->decodeResource(Landroid/content/res/Resources;I)Landroid/graphics/Bitmap;

    move-result-object v0

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->DirectionalMarker:Landroid/graphics/Bitmap;

    .line 95
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->mapView:Lcom/google/android/maps/MapView;

    invoke-virtual {v0}, Lcom/google/android/maps/MapView;->getOverlays()Ljava/util/List;

    move-result-object v0

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->mapOverlays:Ljava/util/List;

    .line 96
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TabMap;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    .line 97
    const v2, 0x7f02001e

    .line 96
    invoke-virtual {v0, v2}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    .line 100
    .local v1, drawable:Landroid/graphics/drawable/Drawable;
    new-instance v0, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;

    const/16 v2, 0x14

    .line 101
    iget-object v4, p0, Lcom/openvehicles/OVMS/TabMap;->DirectionalMarker:Landroid/graphics/Bitmap;

    const/4 v5, 0x1

    move-object v3, p0

    invoke-direct/range {v0 .. v5}, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;-><init>(Landroid/graphics/drawable/Drawable;ILandroid/content/Context;Landroid/graphics/Bitmap;I)V

    .line 100
    iput-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->carMarkers:Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;

    .line 102
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->mapOverlays:Ljava/util/List;

    const/4 v2, 0x0

    iget-object v3, p0, Lcom/openvehicles/OVMS/TabMap;->carMarkers:Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;

    invoke-interface {v0, v2, v3}, Ljava/util/List;->add(ILjava/lang/Object;)V

    .line 105
    new-instance v0, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;

    iget-object v2, p0, Lcom/openvehicles/OVMS/TabMap;->mapView:Lcom/google/android/maps/MapView;

    invoke-direct {v0, p0, v2}, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;-><init>(Landroid/content/Context;Lcom/google/android/maps/MapView;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->myLocationOverlay:Lcom/openvehicles/OVMS/MyLocationOverlayCustom;

    .line 106
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->mapOverlays:Ljava/util/List;

    const/4 v2, 0x1

    iget-object v3, p0, Lcom/openvehicles/OVMS/TabMap;->myLocationOverlay:Lcom/openvehicles/OVMS/MyLocationOverlayCustom;

    invoke-interface {v0, v2, v3}, Ljava/util/List;->add(ILjava/lang/Object;)V

    .line 109
    new-instance v14, Lcom/openvehicles/OVMS/TabMap$TouchOverlay;

    const/4 v0, 0x0

    invoke-direct {v14, p0, v0}, Lcom/openvehicles/OVMS/TabMap$TouchOverlay;-><init>(Lcom/openvehicles/OVMS/TabMap;Lcom/openvehicles/OVMS/TabMap$TouchOverlay;)V

    .line 110
    .local v14, touchOverlay:Lcom/openvehicles/OVMS/TabMap$TouchOverlay;
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->mapOverlays:Ljava/util/List;

    const/4 v2, 0x2

    invoke-interface {v0, v2, v14}, Ljava/util/List;->add(ILjava/lang/Object;)V

    .line 114
    const-string v0, "location"

    invoke-virtual {p0, v0}, Lcom/openvehicles/OVMS/TabMap;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/location/LocationManager;

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->locationManager:Landroid/location/LocationManager;

    .line 116
    new-instance v0, Lcom/openvehicles/OVMS/TabMap$10;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/TabMap$10;-><init>(Lcom/openvehicles/OVMS/TabMap;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->locationListener:Landroid/location/LocationListener;

    .line 142
    const v0, 0x7f090087

    invoke-virtual {p0, v0}, Lcom/openvehicles/OVMS/TabMap;->findViewById(I)Landroid/view/View;

    move-result-object v13

    check-cast v13, Landroid/widget/RadioButton;

    .line 143
    .local v13, option:Landroid/widget/RadioButton;
    new-instance v0, Lcom/openvehicles/OVMS/TabMap$11;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/TabMap$11;-><init>(Lcom/openvehicles/OVMS/TabMap;)V

    invoke-virtual {v13, v0}, Landroid/widget/RadioButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 154
    const/4 v0, 0x1

    invoke-virtual {v13, v0}, Landroid/widget/RadioButton;->setChecked(Z)V

    .line 156
    const v0, 0x7f090088

    invoke-virtual {p0, v0}, Lcom/openvehicles/OVMS/TabMap;->findViewById(I)Landroid/view/View;

    move-result-object v13

    .end local v13           #option:Landroid/widget/RadioButton;
    check-cast v13, Landroid/widget/RadioButton;

    .line 157
    .restart local v13       #option:Landroid/widget/RadioButton;
    new-instance v0, Lcom/openvehicles/OVMS/TabMap$12;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/TabMap$12;-><init>(Lcom/openvehicles/OVMS/TabMap;)V

    invoke-virtual {v13, v0}, Landroid/widget/RadioButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 172
    const v0, 0x7f090089

    invoke-virtual {p0, v0}, Lcom/openvehicles/OVMS/TabMap;->findViewById(I)Landroid/view/View;

    move-result-object v13

    .end local v13           #option:Landroid/widget/RadioButton;
    check-cast v13, Landroid/widget/RadioButton;

    .line 173
    .restart local v13       #option:Landroid/widget/RadioButton;
    new-instance v0, Lcom/openvehicles/OVMS/TabMap$13;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/TabMap$13;-><init>(Lcom/openvehicles/OVMS/TabMap;)V

    invoke-virtual {v13, v0}, Landroid/widget/RadioButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 187
    const v0, 0x7f09008c

    invoke-virtual {p0, v0}, Lcom/openvehicles/OVMS/TabMap;->findViewById(I)Landroid/view/View;

    move-result-object v13

    .end local v13           #option:Landroid/widget/RadioButton;
    check-cast v13, Landroid/widget/RadioButton;

    .line 188
    .restart local v13       #option:Landroid/widget/RadioButton;
    new-instance v0, Lcom/openvehicles/OVMS/TabMap$14;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/TabMap$14;-><init>(Lcom/openvehicles/OVMS/TabMap;)V

    invoke-virtual {v13, v0}, Landroid/widget/RadioButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 211
    const v0, 0x7f09008b

    invoke-virtual {p0, v0}, Lcom/openvehicles/OVMS/TabMap;->findViewById(I)Landroid/view/View;

    move-result-object v13

    .end local v13           #option:Landroid/widget/RadioButton;
    check-cast v13, Landroid/widget/RadioButton;

    .line 212
    .restart local v13       #option:Landroid/widget/RadioButton;
    new-instance v0, Lcom/openvehicles/OVMS/TabMap$15;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/TabMap$15;-><init>(Lcom/openvehicles/OVMS/TabMap;)V

    invoke-virtual {v13, v0}, Landroid/widget/RadioButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 234
    const v0, 0x7f09008d

    invoke-virtual {p0, v0}, Lcom/openvehicles/OVMS/TabMap;->findViewById(I)Landroid/view/View;

    move-result-object v13

    .end local v13           #option:Landroid/widget/RadioButton;
    check-cast v13, Landroid/widget/RadioButton;

    .line 235
    .restart local v13       #option:Landroid/widget/RadioButton;
    new-instance v0, Lcom/openvehicles/OVMS/TabMap$16;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/TabMap$16;-><init>(Lcom/openvehicles/OVMS/TabMap;)V

    invoke-virtual {v13, v0}, Landroid/widget/RadioButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 245
    const/4 v0, 0x1

    invoke-virtual {v13, v0}, Landroid/widget/RadioButton;->setChecked(Z)V

    .line 247
    const v0, 0x7f09008e

    invoke-virtual {p0, v0}, Lcom/openvehicles/OVMS/TabMap;->findViewById(I)Landroid/view/View;

    move-result-object v12

    check-cast v12, Landroid/widget/ListView;

    .line 248
    .local v12, lv:Landroid/widget/ListView;
    new-instance v0, Lcom/openvehicles/OVMS/TabMap$17;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/TabMap$17;-><init>(Lcom/openvehicles/OVMS/TabMap;)V

    invoke-virtual {v12, v0}, Landroid/widget/ListView;->setOnItemClickListener(Landroid/widget/AdapterView$OnItemClickListener;)V

    .line 259
    invoke-direct {p0}, Lcom/openvehicles/OVMS/TabMap;->initPopup()V

    .line 262
    const/16 v0, 0x17

    new-array v9, v0, [Ljava/lang/String;

    const/4 v0, 0x0

    .line 263
    const-string v2, "car_roadster_arcticwhite"

    aput-object v2, v9, v0

    const/4 v0, 0x1

    .line 264
    const-string v2, "car_roadster_brilliantyellow"

    aput-object v2, v9, v0

    const/4 v0, 0x2

    .line 265
    const-string v2, "car_roadster_electricblue"

    aput-object v2, v9, v0

    const/4 v0, 0x3

    .line 266
    const-string v2, "car_roadster_fushionred"

    aput-object v2, v9, v0

    const/4 v0, 0x4

    .line 267
    const-string v2, "car_roadster_glacierblue"

    aput-object v2, v9, v0

    const/4 v0, 0x5

    .line 268
    const-string v2, "car_roadster_jetblack"

    aput-object v2, v9, v0

    const/4 v0, 0x6

    .line 269
    const-string v2, "car_roadster_lightninggreen"

    aput-object v2, v9, v0

    const/4 v0, 0x7

    .line 270
    const-string v2, "car_roadster_obsidianblack"

    aput-object v2, v9, v0

    const/16 v0, 0x8

    .line 271
    const-string v2, "car_roadster_racinggreen"

    aput-object v2, v9, v0

    const/16 v0, 0x9

    .line 272
    const-string v2, "car_roadster_radiantred"

    aput-object v2, v9, v0

    const/16 v0, 0xa

    .line 273
    const-string v2, "car_roadster_sterlingsilver"

    aput-object v2, v9, v0

    const/16 v0, 0xb

    .line 274
    const-string v2, "car_roadster_thundergray"

    aput-object v2, v9, v0

    const/16 v0, 0xc

    .line 275
    const-string v2, "car_roadster_twilightblue"

    aput-object v2, v9, v0

    const/16 v0, 0xd

    .line 276
    const-string v2, "car_roadster_veryorange"

    aput-object v2, v9, v0

    const/16 v0, 0xe

    .line 277
    const-string v2, "car_models_anzabrown"

    aput-object v2, v9, v0

    const/16 v0, 0xf

    .line 278
    const-string v2, "car_models_catalinawhite"

    aput-object v2, v9, v0

    const/16 v0, 0x10

    .line 279
    const-string v2, "car_models_montereyblue"

    aput-object v2, v9, v0

    const/16 v0, 0x11

    .line 280
    const-string v2, "car_models_sansimeonsilver"

    aput-object v2, v9, v0

    const/16 v0, 0x12

    .line 281
    const-string v2, "car_models_sequolagreen"

    aput-object v2, v9, v0

    const/16 v0, 0x13

    .line 282
    const-string v2, "car_models_shastapearlwhite"

    aput-object v2, v9, v0

    const/16 v0, 0x14

    .line 283
    const-string v2, "car_models_sierrablack"

    aput-object v2, v9, v0

    const/16 v0, 0x15

    .line 284
    const-string v2, "car_models_signaturered"

    aput-object v2, v9, v0

    const/16 v0, 0x16

    .line 285
    const-string v2, "car_models_tiburongrey"

    aput-object v2, v9, v0

    .line 291
    .local v9, availableColorsCache:[Ljava/lang/String;
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->availableCarColors:Ljava/util/ArrayList;

    .line 292
    const/4 v10, 0x0

    .local v10, i:I
    :goto_0
    array-length v0, v9

    if-lt v10, v0, :cond_0

    .line 300
    new-instance v2, Lcom/openvehicles/OVMS/TabMap$CustomSpinnerAdapter;

    .line 301
    iget-object v5, p0, Lcom/openvehicles/OVMS/TabMap;->availableCarColors:Ljava/util/ArrayList;

    const v6, 0x7f03000b

    const/4 v0, 0x1

    new-array v7, v0, [Ljava/lang/String;

    const/4 v0, 0x0

    const-string v3, "Icon"

    aput-object v3, v7, v0

    const/4 v0, 0x1

    new-array v8, v0, [I

    const/4 v0, 0x0

    const v3, 0x7f090038

    aput v3, v8, v0

    move-object v3, p0

    move-object v4, p0

    invoke-direct/range {v2 .. v8}, Lcom/openvehicles/OVMS/TabMap$CustomSpinnerAdapter;-><init>(Lcom/openvehicles/OVMS/TabMap;Landroid/content/Context;Ljava/util/List;I[Ljava/lang/String;[I)V

    .line 300
    iput-object v2, p0, Lcom/openvehicles/OVMS/TabMap;->availableCarColorsSpinnerAdapter:Lcom/openvehicles/OVMS/TabMap$CustomSpinnerAdapter;

    .line 302
    return-void

    .line 293
    :cond_0
    new-instance v11, Ljava/util/HashMap;

    invoke-direct {v11}, Ljava/util/HashMap;-><init>()V

    .line 294
    .local v11, item:Ljava/util/HashMap;,"Ljava/util/HashMap<Ljava/lang/String;Ljava/lang/Object;>;"
    const-string v0, "Name"

    aget-object v2, v9, v10

    invoke-virtual {v11, v0, v2}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 296
    const-string v0, "Icon"

    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TabMap;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    const-string v3, "%s96x44"

    const/4 v4, 0x1

    new-array v4, v4, [Ljava/lang/Object;

    const/4 v5, 0x0

    aget-object v6, v9, v10

    aput-object v6, v4, v5

    invoke-static {v3, v4}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v3

    const-string v4, "drawable"

    const-string v5, "com.openvehicles.OVMS"

    invoke-virtual {v2, v3, v4, v5}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v2

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-virtual {v11, v0, v2}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 297
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->availableCarColors:Ljava/util/ArrayList;

    invoke-virtual {v0, v11}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 292
    add-int/lit8 v10, v10, 0x1

    goto :goto_0
.end method

.method protected onPause()V
    .locals 2

    .prologue
    .line 917
    :try_start_0
    const-string v0, "GPS"

    const-string v1, "OFF"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 918
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->locationManager:Landroid/location/LocationManager;

    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap;->locationListener:Landroid/location/LocationListener;

    invoke-virtual {v0, v1}, Landroid/location/LocationManager;->removeUpdates(Landroid/location/LocationListener;)V

    .line 919
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->myLocationOverlay:Lcom/openvehicles/OVMS/MyLocationOverlayCustom;

    invoke-virtual {v0}, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->disableCompass()V

    .line 920
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->myLocationOverlay:Lcom/openvehicles/OVMS/MyLocationOverlayCustom;

    invoke-virtual {v0}, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->disableMyLocation()V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 925
    :goto_0
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->lastUpdateTimerHandler:Landroid/os/Handler;

    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap;->lastUpdateTimer:Ljava/lang/Runnable;

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeCallbacks(Ljava/lang/Runnable;)V

    .line 927
    invoke-super {p0}, Lcom/google/android/maps/MapActivity;->onPause()V

    .line 929
    return-void

    .line 921
    :catch_0
    move-exception v0

    goto :goto_0
.end method

.method public onRestoreInstanceState(Landroid/os/Bundle;)V
    .locals 2
    .parameter "savedInstanceState"

    .prologue
    .line 944
    invoke-super {p0, p1}, Lcom/google/android/maps/MapActivity;->onRestoreInstanceState(Landroid/os/Bundle;)V

    .line 948
    const-string v0, "planWalkingDirection"

    invoke-virtual {p1, v0}, Landroid/os/Bundle;->getBoolean(Ljava/lang/String;)Z

    move-result v0

    .line 947
    iput-boolean v0, p0, Lcom/openvehicles/OVMS/TabMap;->planWalkingDirection:Z

    .line 949
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->centeringMode:Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;

    const-string v1, "centeringMode"

    invoke-virtual {p1, v1}, Landroid/os/Bundle;->getInt(Ljava/lang/String;)I

    move-result v1

    invoke-virtual {v0, v1}, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;->setMode(I)V

    .line 950
    return-void
.end method

.method protected onResume()V
    .locals 6

    .prologue
    const/high16 v4, 0x40a0

    const-wide/16 v2, 0x1388

    .line 892
    invoke-super {p0}, Lcom/google/android/maps/MapActivity;->onResume()V

    .line 895
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->locationManager:Landroid/location/LocationManager;

    const-string v1, "gps"

    invoke-virtual {v0, v1}, Landroid/location/LocationManager;->isProviderEnabled(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_0

    .line 896
    const-string v0, "GPS"

    const-string v1, "ON"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 897
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->locationManager:Landroid/location/LocationManager;

    .line 898
    const-string v1, "network"

    .line 899
    iget-object v5, p0, Lcom/openvehicles/OVMS/TabMap;->locationListener:Landroid/location/LocationListener;

    .line 898
    invoke-virtual/range {v0 .. v5}, Landroid/location/LocationManager;->requestLocationUpdates(Ljava/lang/String;JFLandroid/location/LocationListener;)V

    .line 900
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->locationManager:Landroid/location/LocationManager;

    .line 901
    const-string v1, "gps"

    iget-object v5, p0, Lcom/openvehicles/OVMS/TabMap;->locationListener:Landroid/location/LocationListener;

    .line 900
    invoke-virtual/range {v0 .. v5}, Landroid/location/LocationManager;->requestLocationUpdates(Ljava/lang/String;JFLandroid/location/LocationListener;)V

    .line 904
    :cond_0
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->lastKnownDeviceGeoPoint:Lcom/google/android/maps/GeoPoint;

    .line 905
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->myLocationOverlay:Lcom/openvehicles/OVMS/MyLocationOverlayCustom;

    invoke-virtual {v0}, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->enableCompass()Z

    .line 906
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->myLocationOverlay:Lcom/openvehicles/OVMS/MyLocationOverlayCustom;

    invoke-virtual {v0}, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->enableMyLocation()Z

    .line 909
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->lastUpdateTimerHandler:Landroid/os/Handler;

    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap;->lastUpdateTimer:Ljava/lang/Runnable;

    invoke-virtual {v0, v1, v2, v3}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    .line 911
    return-void
.end method

.method public onSaveInstanceState(Landroid/os/Bundle;)V
    .locals 2
    .parameter "savedInstanceState"

    .prologue
    .line 936
    const-string v0, "planWalkingDirection"

    .line 937
    iget-boolean v1, p0, Lcom/openvehicles/OVMS/TabMap;->planWalkingDirection:Z

    .line 936
    invoke-virtual {p1, v0, v1}, Landroid/os/Bundle;->putBoolean(Ljava/lang/String;Z)V

    .line 938
    const-string v0, "centeringMode"

    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap;->centeringMode:Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;

    invoke-virtual {v1}, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;->getMode()I

    move-result v1

    invoke-virtual {p1, v0, v1}, Landroid/os/Bundle;->putInt(Ljava/lang/String;I)V

    .line 939
    invoke-super {p0, p1}, Lcom/google/android/maps/MapActivity;->onSaveInstanceState(Landroid/os/Bundle;)V

    .line 940
    return-void
.end method
