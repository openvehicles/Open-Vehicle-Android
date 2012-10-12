.class public Lcom/openvehicles/OVMS/MyLocationOverlayCustom;
.super Lcom/google/android/maps/MyLocationOverlay;
.source "MyLocationOverlayCustom.java"


# instance fields
.field private handler:Landroid/os/Handler;

.field private locationMarker:Landroid/graphics/drawable/Drawable;

.field private final mapView:Lcom/google/android/maps/MapView;

.field private overlayAnimationTask:Ljava/lang/Runnable;

.field private point:Landroid/graphics/Point;

.field private rect:Landroid/graphics/Rect;

.field private savedFix:Landroid/location/Location;


# direct methods
.method public constructor <init>(Landroid/content/Context;Lcom/google/android/maps/MapView;)V
    .locals 4
    .parameter "context"
    .parameter "mapView"

    .prologue
    .line 29
    invoke-direct {p0, p1, p2}, Lcom/google/android/maps/MyLocationOverlay;-><init>(Landroid/content/Context;Lcom/google/android/maps/MapView;)V

    .line 22
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->savedFix:Landroid/location/Location;

    .line 23
    new-instance v0, Landroid/graphics/Point;

    invoke-direct {v0}, Landroid/graphics/Point;-><init>()V

    iput-object v0, p0, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->point:Landroid/graphics/Point;

    .line 24
    new-instance v0, Landroid/graphics/Rect;

    invoke-direct {v0}, Landroid/graphics/Rect;-><init>()V

    iput-object v0, p0, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->rect:Landroid/graphics/Rect;

    .line 25
    new-instance v0, Landroid/os/Handler;

    invoke-direct {v0}, Landroid/os/Handler;-><init>()V

    iput-object v0, p0, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->handler:Landroid/os/Handler;

    .line 30
    iput-object p2, p0, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->mapView:Lcom/google/android/maps/MapView;

    .line 32
    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v1, 0x7f020044

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    iput-object v0, p0, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->locationMarker:Landroid/graphics/drawable/Drawable;

    .line 46
    new-instance v0, Lcom/openvehicles/OVMS/MyLocationOverlayCustom$1;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/MyLocationOverlayCustom$1;-><init>(Lcom/openvehicles/OVMS/MyLocationOverlayCustom;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->overlayAnimationTask:Ljava/lang/Runnable;

    .line 57
    iget-object v0, p0, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->handler:Landroid/os/Handler;

    iget-object v1, p0, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->overlayAnimationTask:Ljava/lang/Runnable;

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeCallbacks(Ljava/lang/Runnable;)V

    .line 58
    iget-object v0, p0, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->handler:Landroid/os/Handler;

    iget-object v1, p0, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->overlayAnimationTask:Ljava/lang/Runnable;

    const-wide/16 v2, 0x64

    invoke-virtual {v0, v1, v2, v3}, Landroid/os/Handler;->postAtTime(Ljava/lang/Runnable;J)Z

    .line 59
    return-void
.end method

.method static synthetic access$0(Lcom/openvehicles/OVMS/MyLocationOverlayCustom;)Lcom/google/android/maps/MapView;
    .locals 1
    .parameter

    .prologue
    .line 21
    iget-object v0, p0, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->mapView:Lcom/google/android/maps/MapView;

    return-object v0
.end method

.method static synthetic access$1(Lcom/openvehicles/OVMS/MyLocationOverlayCustom;)Landroid/os/Handler;
    .locals 1
    .parameter

    .prologue
    .line 25
    iget-object v0, p0, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->handler:Landroid/os/Handler;

    return-object v0
.end method

.method static synthetic access$2(Lcom/openvehicles/OVMS/MyLocationOverlayCustom;)Ljava/lang/Runnable;
    .locals 1
    .parameter

    .prologue
    .line 26
    iget-object v0, p0, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->overlayAnimationTask:Ljava/lang/Runnable;

    return-object v0
.end method


# virtual methods
.method protected drawMyLocation(Landroid/graphics/Canvas;Lcom/google/android/maps/MapView;Landroid/location/Location;Lcom/google/android/maps/GeoPoint;J)V
    .locals 3
    .parameter "canvas"
    .parameter "mapView"
    .parameter "lastFix"
    .parameter "myLocation"
    .parameter "when"

    .prologue
    .line 63
    invoke-virtual {p2}, Lcom/google/android/maps/MapView;->getProjection()Lcom/google/android/maps/Projection;

    move-result-object v0

    iget-object v1, p0, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->point:Landroid/graphics/Point;

    invoke-interface {v0, p4, v1}, Lcom/google/android/maps/Projection;->toPixels(Lcom/google/android/maps/GeoPoint;Landroid/graphics/Point;)Landroid/graphics/Point;

    .line 64
    iget-object v0, p0, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->rect:Landroid/graphics/Rect;

    iget-object v1, p0, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->point:Landroid/graphics/Point;

    iget v1, v1, Landroid/graphics/Point;->x:I

    iget-object v2, p0, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->locationMarker:Landroid/graphics/drawable/Drawable;

    invoke-virtual {v2}, Landroid/graphics/drawable/Drawable;->getIntrinsicWidth()I

    move-result v2

    div-int/lit8 v2, v2, 0x2

    sub-int/2addr v1, v2

    iput v1, v0, Landroid/graphics/Rect;->left:I

    .line 65
    iget-object v0, p0, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->rect:Landroid/graphics/Rect;

    iget-object v1, p0, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->point:Landroid/graphics/Point;

    iget v1, v1, Landroid/graphics/Point;->y:I

    iget-object v2, p0, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->locationMarker:Landroid/graphics/drawable/Drawable;

    invoke-virtual {v2}, Landroid/graphics/drawable/Drawable;->getIntrinsicHeight()I

    move-result v2

    div-int/lit8 v2, v2, 0x2

    sub-int/2addr v1, v2

    iput v1, v0, Landroid/graphics/Rect;->top:I

    .line 66
    iget-object v0, p0, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->rect:Landroid/graphics/Rect;

    iget-object v1, p0, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->point:Landroid/graphics/Point;

    iget v1, v1, Landroid/graphics/Point;->x:I

    iget-object v2, p0, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->locationMarker:Landroid/graphics/drawable/Drawable;

    invoke-virtual {v2}, Landroid/graphics/drawable/Drawable;->getIntrinsicWidth()I

    move-result v2

    div-int/lit8 v2, v2, 0x2

    add-int/2addr v1, v2

    iput v1, v0, Landroid/graphics/Rect;->right:I

    .line 67
    iget-object v0, p0, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->rect:Landroid/graphics/Rect;

    iget-object v1, p0, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->point:Landroid/graphics/Point;

    iget v1, v1, Landroid/graphics/Point;->y:I

    iget-object v2, p0, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->locationMarker:Landroid/graphics/drawable/Drawable;

    invoke-virtual {v2}, Landroid/graphics/drawable/Drawable;->getIntrinsicHeight()I

    move-result v2

    div-int/lit8 v2, v2, 0x2

    add-int/2addr v1, v2

    iput v1, v0, Landroid/graphics/Rect;->bottom:I

    .line 68
    iget-object v0, p0, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->locationMarker:Landroid/graphics/drawable/Drawable;

    iget-object v1, p0, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->rect:Landroid/graphics/Rect;

    invoke-virtual {v0, v1}, Landroid/graphics/drawable/Drawable;->setBounds(Landroid/graphics/Rect;)V

    .line 69
    iget-object v0, p0, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->locationMarker:Landroid/graphics/drawable/Drawable;

    invoke-virtual {v0, p1}, Landroid/graphics/drawable/Drawable;->draw(Landroid/graphics/Canvas;)V

    .line 70
    return-void
.end method
