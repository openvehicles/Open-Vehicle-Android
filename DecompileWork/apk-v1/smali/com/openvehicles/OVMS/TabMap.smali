.class public Lcom/openvehicles/OVMS/TabMap;
.super Lcom/google/android/maps/MapActivity;
.source "TabMap.java"

# interfaces
.implements Lcom/openvehicles/OVMS/RefreshStatusCallBack;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/openvehicles/OVMS/TabMap$CarMarkerOverlay;
    }
.end annotation


# instance fields
.field private carMarkers:Lcom/openvehicles/OVMS/TabMap$CarMarkerOverlay;

.field private data:Lcom/openvehicles/OVMS/CarData;

.field private handler:Landroid/os/Handler;

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


# direct methods
.method public constructor <init>()V
    .locals 1

    .prologue
    .line 30
    invoke-direct {p0}, Lcom/google/android/maps/MapActivity;-><init>()V

    .line 105
    new-instance v0, Lcom/openvehicles/OVMS/TabMap$2;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/TabMap$2;-><init>(Lcom/openvehicles/OVMS/TabMap;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->handler:Landroid/os/Handler;

    return-void
.end method

.method static synthetic access$000(Lcom/openvehicles/OVMS/TabMap;)Landroid/os/Handler;
    .locals 1
    .parameter "x0"

    .prologue
    .line 30
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->handler:Landroid/os/Handler;

    return-object v0
.end method

.method static synthetic access$100(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;
    .locals 1
    .parameter "x0"

    .prologue
    .line 30
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;

    return-object v0
.end method

.method static synthetic access$200(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/TabMap$CarMarkerOverlay;
    .locals 1
    .parameter "x0"

    .prologue
    .line 30
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->carMarkers:Lcom/openvehicles/OVMS/TabMap$CarMarkerOverlay;

    return-object v0
.end method

.method static synthetic access$300(Lcom/openvehicles/OVMS/TabMap;)Lcom/google/android/maps/MapController;
    .locals 1
    .parameter "x0"

    .prologue
    .line 30
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->mc:Lcom/google/android/maps/MapController;

    return-object v0
.end method

.method static synthetic access$400(Lcom/openvehicles/OVMS/TabMap;)Lcom/google/android/maps/MapView;
    .locals 1
    .parameter "x0"

    .prologue
    .line 30
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap;->mapView:Lcom/google/android/maps/MapView;

    return-object v0
.end method


# virtual methods
.method public RefreshStatus(Lcom/openvehicles/OVMS/CarData;)V
    .locals 6
    .parameter "carData"

    .prologue
    .line 134
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;

    .line 137
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap;->mapView:Lcom/google/android/maps/MapView;

    invoke-virtual {v1}, Lcom/google/android/maps/MapView;->getOverlays()Ljava/util/List;

    move-result-object v1

    iput-object v1, p0, Lcom/openvehicles/OVMS/TabMap;->mapOverlays:Ljava/util/List;

    .line 138
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TabMap;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TabMap;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v4, p0, Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v4, v4, Lcom/openvehicles/OVMS/CarData;->VehicleImageDrawable:Ljava/lang/String;

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v4, "32x32"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    const-string v4, "drawable"

    const-string v5, "com.openvehicles.OVMS"

    invoke-virtual {v2, v3, v4, v5}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v2

    invoke-virtual {v1, v2}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    .line 140
    .local v0, drawable:Landroid/graphics/drawable/Drawable;
    new-instance v1, Lcom/openvehicles/OVMS/TabMap$CarMarkerOverlay;

    invoke-direct {v1, p0, v0, p0}, Lcom/openvehicles/OVMS/TabMap$CarMarkerOverlay;-><init>(Lcom/openvehicles/OVMS/TabMap;Landroid/graphics/drawable/Drawable;Landroid/content/Context;)V

    iput-object v1, p0, Lcom/openvehicles/OVMS/TabMap;->carMarkers:Lcom/openvehicles/OVMS/TabMap$CarMarkerOverlay;

    .line 141
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap;->mapOverlays:Ljava/util/List;

    invoke-interface {v1}, Ljava/util/List;->clear()V

    .line 142
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap;->mapOverlays:Ljava/util/List;

    iget-object v2, p0, Lcom/openvehicles/OVMS/TabMap;->carMarkers:Lcom/openvehicles/OVMS/TabMap$CarMarkerOverlay;

    invoke-interface {v1, v2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 144
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap;->handler:Landroid/os/Handler;

    const/4 v2, 0x0

    invoke-virtual {v1, v2}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    .line 146
    return-void
.end method

.method protected isRouteDisplayed()Z
    .locals 1

    .prologue
    .line 102
    const/4 v0, 0x0

    return v0
.end method

.method public onCreate(Landroid/os/Bundle;)V
    .locals 3
    .parameter "savedInstanceState"

    .prologue
    .line 35
    invoke-super {p0, p1}, Lcom/google/android/maps/MapActivity;->onCreate(Landroid/os/Bundle;)V

    .line 37
    const v1, 0x7f030009

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/TabMap;->setContentView(I)V

    .line 39
    const v1, 0x7f06003c

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/TabMap;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageButton;

    .line 40
    .local v0, btn:Landroid/widget/ImageButton;
    new-instance v1, Lcom/openvehicles/OVMS/TabMap$1;

    invoke-direct {v1, p0}, Lcom/openvehicles/OVMS/TabMap$1;-><init>(Lcom/openvehicles/OVMS/TabMap;)V

    invoke-virtual {v0, v1}, Landroid/widget/ImageButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 46
    const v1, 0x7f06003b

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/TabMap;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Lcom/google/android/maps/MapView;

    iput-object v1, p0, Lcom/openvehicles/OVMS/TabMap;->mapView:Lcom/google/android/maps/MapView;

    .line 47
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap;->mapView:Lcom/google/android/maps/MapView;

    invoke-virtual {v1}, Lcom/google/android/maps/MapView;->getController()Lcom/google/android/maps/MapController;

    move-result-object v1

    iput-object v1, p0, Lcom/openvehicles/OVMS/TabMap;->mc:Lcom/google/android/maps/MapController;

    .line 48
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap;->mapView:Lcom/google/android/maps/MapView;

    const/4 v2, 0x1

    invoke-virtual {v1, v2}, Lcom/google/android/maps/MapView;->setBuiltInZoomControls(Z)V

    .line 49
    return-void
.end method
