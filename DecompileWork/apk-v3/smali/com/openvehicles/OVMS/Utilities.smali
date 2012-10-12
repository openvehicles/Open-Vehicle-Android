.class public final Lcom/openvehicles/OVMS/Utilities;
.super Ljava/lang/Object;
.source "Utilities.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/openvehicles/OVMS/Utilities$CarMarker;,
        Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;,
        Lcom/openvehicles/OVMS/Utilities$OnGroupCarTappedListener;
    }
.end annotation


# direct methods
.method public constructor <init>()V
    .locals 0

    .prologue
    .line 32
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static GetCarGeopoint(DD)Lcom/google/android/maps/GeoPoint;
    .locals 6
    .parameter "latitude"
    .parameter "longitude"

    .prologue
    const-wide v4, 0x412e848000000000L

    .line 41
    mul-double v2, p0, v4

    double-to-int v0, v2

    .line 42
    .local v0, lat:I
    mul-double v2, p2, v4

    double-to-int v1, v2

    .line 43
    .local v1, lng:I
    new-instance v2, Lcom/google/android/maps/GeoPoint;

    invoke-direct {v2, v0, v1}, Lcom/google/android/maps/GeoPoint;-><init>(II)V

    return-object v2
.end method

.method public static GetCarGeopoint(Lcom/openvehicles/OVMS/CarData;)Lcom/google/android/maps/GeoPoint;
    .locals 6
    .parameter "carData"

    .prologue
    const-wide v4, 0x412e848000000000L

    .line 35
    iget-wide v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_Latitude:D

    mul-double/2addr v2, v4

    double-to-int v0, v2

    .line 36
    .local v0, lat:I
    iget-wide v2, p0, Lcom/openvehicles/OVMS/CarData;->Data_Longitude:D

    mul-double/2addr v2, v4

    double-to-int v1, v2

    .line 37
    .local v1, lng:I
    new-instance v2, Lcom/google/android/maps/GeoPoint;

    invoke-direct {v2, v0, v1}, Lcom/google/android/maps/GeoPoint;-><init>(II)V

    return-object v2
.end method

.method public static GetDistanceBetweenCoordinatesKM(DDDD)D
    .locals 21
    .parameter "latitude1"
    .parameter "longitude1"
    .parameter "latitude2"
    .parameter "longitude2"

    .prologue
    .line 48
    const/16 v0, 0x18e3

    .line 49
    .local v0, R:I
    sub-double v15, p4, p0

    invoke-static/range {v15 .. v16}, Ljava/lang/Math;->toRadians(D)D

    move-result-wide v7

    .line 50
    .local v7, dLat:D
    sub-double v15, p6, p2

    invoke-static/range {v15 .. v16}, Ljava/lang/Math;->toRadians(D)D

    move-result-wide v9

    .line 51
    .local v9, dLon:D
    invoke-static/range {p0 .. p1}, Ljava/lang/Math;->toRadians(D)D

    move-result-wide v11

    .line 52
    .local v11, lat1:D
    invoke-static/range {p4 .. p5}, Ljava/lang/Math;->toRadians(D)D

    move-result-wide v13

    .line 54
    .local v13, lat2:D
    const-wide/high16 v15, 0x4000

    div-double v15, v7, v15

    invoke-static/range {v15 .. v16}, Ljava/lang/Math;->sin(D)D

    move-result-wide v15

    const-wide/high16 v17, 0x4000

    div-double v17, v7, v17

    invoke-static/range {v17 .. v18}, Ljava/lang/Math;->sin(D)D

    move-result-wide v17

    mul-double v15, v15, v17

    .line 55
    const-wide/high16 v17, 0x4000

    div-double v17, v9, v17

    invoke-static/range {v17 .. v18}, Ljava/lang/Math;->sin(D)D

    move-result-wide v17

    const-wide/high16 v19, 0x4000

    div-double v19, v9, v19

    invoke-static/range {v19 .. v20}, Ljava/lang/Math;->sin(D)D

    move-result-wide v19

    mul-double v17, v17, v19

    invoke-static {v11, v12}, Ljava/lang/Math;->cos(D)D

    move-result-wide v19

    mul-double v17, v17, v19

    invoke-static {v13, v14}, Ljava/lang/Math;->cos(D)D

    move-result-wide v19

    mul-double v17, v17, v19

    .line 54
    add-double v1, v15, v17

    .line 56
    .local v1, a:D
    const-wide/high16 v15, 0x4000

    invoke-static {v1, v2}, Ljava/lang/Math;->sqrt(D)D

    move-result-wide v17

    const-wide/high16 v19, 0x3ff0

    sub-double v19, v19, v1

    invoke-static/range {v19 .. v20}, Ljava/lang/Math;->sqrt(D)D

    move-result-wide v19

    invoke-static/range {v17 .. v20}, Ljava/lang/Math;->atan2(DD)D

    move-result-wide v17

    mul-double v3, v15, v17

    .line 57
    .local v3, c:D
    int-to-double v15, v0

    mul-double v5, v15, v3

    .line 59
    .local v5, d:D
    return-wide v5
.end method

.method public static GetRotatedDirectionalMarker(Landroid/graphics/Bitmap;F)Landroid/graphics/Bitmap;
    .locals 5
    .parameter "directionalMarker"
    .parameter "angle"

    .prologue
    .line 81
    sget-object v3, Landroid/graphics/Bitmap$Config;->ARGB_8888:Landroid/graphics/Bitmap$Config;

    const/4 v4, 0x1

    invoke-virtual {p0, v3, v4}, Landroid/graphics/Bitmap;->copy(Landroid/graphics/Bitmap$Config;Z)Landroid/graphics/Bitmap;

    move-result-object v1

    .line 82
    .local v1, canvasBitmap:Landroid/graphics/Bitmap;
    const/4 v3, 0x0

    invoke-virtual {v1, v3}, Landroid/graphics/Bitmap;->eraseColor(I)V

    .line 85
    new-instance v0, Landroid/graphics/Canvas;

    invoke-direct {v0, v1}, Landroid/graphics/Canvas;-><init>(Landroid/graphics/Bitmap;)V

    .line 88
    .local v0, canvas:Landroid/graphics/Canvas;
    new-instance v2, Landroid/graphics/Matrix;

    invoke-direct {v2}, Landroid/graphics/Matrix;-><init>()V

    .line 89
    .local v2, rotateMatrix:Landroid/graphics/Matrix;
    invoke-virtual {v0}, Landroid/graphics/Canvas;->getWidth()I

    move-result v3

    div-int/lit8 v3, v3, 0x2

    int-to-float v3, v3

    invoke-virtual {v0}, Landroid/graphics/Canvas;->getHeight()I

    move-result v4

    div-int/lit8 v4, v4, 0x2

    int-to-float v4, v4

    invoke-virtual {v2, p1, v3, v4}, Landroid/graphics/Matrix;->setRotate(FFF)V

    .line 92
    const/4 v3, 0x0

    invoke-virtual {v0, p0, v2, v3}, Landroid/graphics/Canvas;->drawBitmap(Landroid/graphics/Bitmap;Landroid/graphics/Matrix;Landroid/graphics/Paint;)V

    .line 94
    return-object v1
.end method

.method public static GetScaledBatteryOverlay(ILandroid/graphics/Bitmap;)Landroid/graphics/Bitmap;
    .locals 7
    .parameter "SOC"
    .parameter "originalOverlay"

    .prologue
    const/4 v0, 0x0

    const/4 v1, 0x0

    .line 64
    if-nez p1, :cond_1

    .line 66
    const-string v1, "OVMS"

    const-string v2, "!!! Battery overlay resource not found !!!"

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 75
    :cond_0
    :goto_0
    return-object v0

    .line 68
    :cond_1
    if-lez p0, :cond_0

    .line 72
    new-instance v5, Landroid/graphics/Matrix;

    invoke-direct {v5}, Landroid/graphics/Matrix;-><init>()V

    .line 73
    .local v5, matrix:Landroid/graphics/Matrix;
    int-to-float v0, p0

    const/high16 v2, 0x42c8

    div-float/2addr v0, v2

    const/high16 v2, 0x3f80

    invoke-virtual {v5, v0, v2}, Landroid/graphics/Matrix;->postScale(FF)Z

    .line 75
    invoke-virtual {p1}, Landroid/graphics/Bitmap;->getWidth()I

    move-result v3

    invoke-virtual {p1}, Landroid/graphics/Bitmap;->getHeight()I

    move-result v4

    move-object v0, p1

    move v2, v1

    move v6, v1

    invoke-static/range {v0 .. v6}, Landroid/graphics/Bitmap;->createBitmap(Landroid/graphics/Bitmap;IIIILandroid/graphics/Matrix;Z)Landroid/graphics/Bitmap;

    move-result-object v0

    goto :goto_0
.end method
