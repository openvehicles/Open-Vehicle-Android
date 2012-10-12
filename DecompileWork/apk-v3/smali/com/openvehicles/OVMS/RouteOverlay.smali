.class public Lcom/openvehicles/OVMS/RouteOverlay;
.super Lcom/google/android/maps/Overlay;
.source "RouteOverlay.java"


# instance fields
.field private color:I

.field public gp1:Lcom/google/android/maps/GeoPoint;

.field public gp2:Lcom/google/android/maps/GeoPoint;


# direct methods
.method public constructor <init>(Lcom/google/android/maps/GeoPoint;Lcom/google/android/maps/GeoPoint;I)V
    .locals 0
    .parameter "gp1"
    .parameter "gp2"
    .parameter "color"

    .prologue
    .line 17
    invoke-direct {p0}, Lcom/google/android/maps/Overlay;-><init>()V

    .line 18
    iput-object p1, p0, Lcom/openvehicles/OVMS/RouteOverlay;->gp1:Lcom/google/android/maps/GeoPoint;

    .line 19
    iput-object p2, p0, Lcom/openvehicles/OVMS/RouteOverlay;->gp2:Lcom/google/android/maps/GeoPoint;

    .line 20
    iput p3, p0, Lcom/openvehicles/OVMS/RouteOverlay;->color:I

    .line 21
    return-void
.end method


# virtual methods
.method public draw(Landroid/graphics/Canvas;Lcom/google/android/maps/MapView;Z)V
    .locals 9
    .parameter "canvas"
    .parameter "mapView"
    .parameter "shadow"

    .prologue
    .line 26
    invoke-virtual {p2}, Lcom/google/android/maps/MapView;->getProjection()Lcom/google/android/maps/Projection;

    move-result-object v8

    .line 27
    .local v8, projection:Lcom/google/android/maps/Projection;
    new-instance v5, Landroid/graphics/Paint;

    invoke-direct {v5}, Landroid/graphics/Paint;-><init>()V

    .line 28
    .local v5, paint:Landroid/graphics/Paint;
    new-instance v6, Landroid/graphics/Point;

    invoke-direct {v6}, Landroid/graphics/Point;-><init>()V

    .line 29
    .local v6, point:Landroid/graphics/Point;
    iget-object v0, p0, Lcom/openvehicles/OVMS/RouteOverlay;->gp1:Lcom/google/android/maps/GeoPoint;

    invoke-interface {v8, v0, v6}, Lcom/google/android/maps/Projection;->toPixels(Lcom/google/android/maps/GeoPoint;Landroid/graphics/Point;)Landroid/graphics/Point;

    .line 30
    iget v0, p0, Lcom/openvehicles/OVMS/RouteOverlay;->color:I

    invoke-virtual {v5, v0}, Landroid/graphics/Paint;->setColor(I)V

    .line 31
    new-instance v7, Landroid/graphics/Point;

    invoke-direct {v7}, Landroid/graphics/Point;-><init>()V

    .line 32
    .local v7, point2:Landroid/graphics/Point;
    iget-object v0, p0, Lcom/openvehicles/OVMS/RouteOverlay;->gp2:Lcom/google/android/maps/GeoPoint;

    invoke-interface {v8, v0, v7}, Lcom/google/android/maps/Projection;->toPixels(Lcom/google/android/maps/GeoPoint;Landroid/graphics/Point;)Landroid/graphics/Point;

    .line 33
    const/high16 v0, 0x40a0

    invoke-virtual {v5, v0}, Landroid/graphics/Paint;->setStrokeWidth(F)V

    .line 34
    const/16 v0, 0x78

    invoke-virtual {v5, v0}, Landroid/graphics/Paint;->setAlpha(I)V

    .line 35
    iget v0, v6, Landroid/graphics/Point;->x:I

    int-to-float v1, v0

    iget v0, v6, Landroid/graphics/Point;->y:I

    int-to-float v2, v0

    iget v0, v7, Landroid/graphics/Point;->x:I

    int-to-float v3, v0

    iget v0, v7, Landroid/graphics/Point;->y:I

    int-to-float v4, v0

    move-object v0, p1

    invoke-virtual/range {v0 .. v5}, Landroid/graphics/Canvas;->drawLine(FFFFLandroid/graphics/Paint;)V

    .line 36
    invoke-super {p0, p1, p2, p3}, Lcom/google/android/maps/Overlay;->draw(Landroid/graphics/Canvas;Lcom/google/android/maps/MapView;Z)V

    .line 37
    return-void
.end method
