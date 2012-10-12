.class public Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;
.super Lcom/google/android/maps/ItemizedOverlay;
.source "Utilities.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/openvehicles/OVMS/Utilities;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x9
    name = "CarMarkerOverlay"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lcom/google/android/maps/ItemizedOverlay",
        "<",
        "Lcom/google/android/maps/OverlayItem;",
        ">;"
    }
.end annotation


# instance fields
.field private DirectionalMarker:Landroid/graphics/Bitmap;

.field private LABEL_SHADOW_XY:I

.field private _listeners:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList",
            "<",
            "Lcom/openvehicles/OVMS/Utilities$OnGroupCarTappedListener;",
            ">;"
        }
    .end annotation
.end field

.field private mContext:Landroid/content/Context;

.field private mLabelTextSize:I

.field private mOverlays:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList",
            "<",
            "Lcom/google/android/maps/OverlayItem;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method public constructor <init>(Landroid/graphics/drawable/Drawable;ILandroid/content/Context;Landroid/graphics/Bitmap;I)V
    .locals 1
    .parameter "defaultMarker"
    .parameter "labelTextSize"
    .parameter "context"
    .parameter "directionalMarker"
    .parameter "label_shadow_xy"

    .prologue
    .line 173
    invoke-static {p1}, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->boundCenterBottom(Landroid/graphics/drawable/Drawable;)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/google/android/maps/ItemizedOverlay;-><init>(Landroid/graphics/drawable/Drawable;)V

    .line 119
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->mOverlays:Ljava/util/ArrayList;

    .line 125
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->_listeners:Ljava/util/ArrayList;

    .line 174
    iput-object p3, p0, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->mContext:Landroid/content/Context;

    .line 175
    iput p2, p0, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->mLabelTextSize:I

    .line 176
    iput-object p4, p0, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->DirectionalMarker:Landroid/graphics/Bitmap;

    .line 177
    iput p5, p0, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->LABEL_SHADOW_XY:I

    .line 178
    return-void
.end method

.method private declared-synchronized fireGroupCarTappedEvent(Ljava/lang/String;)V
    .locals 2
    .parameter "groupVehicleID"

    .prologue
    .line 137
    monitor-enter p0

    :try_start_0
    iget-object v1, p0, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->_listeners:Ljava/util/ArrayList;

    invoke-virtual {v1}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v0

    .line 138
    .local v0, i:Ljava/util/Iterator;,"Ljava/util/Iterator<Lcom/openvehicles/OVMS/Utilities$OnGroupCarTappedListener;>;"
    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    move-result v1

    if-nez v1, :cond_0

    .line 140
    monitor-exit p0

    return-void

    .line 139
    :cond_0
    :try_start_1
    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/openvehicles/OVMS/Utilities$OnGroupCarTappedListener;

    invoke-interface {v1, p1}, Lcom/openvehicles/OVMS/Utilities$OnGroupCarTappedListener;->OnGroupCarTapped(Ljava/lang/String;)V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    goto :goto_0

    .line 137
    .end local v0           #i:Ljava/util/Iterator;,"Ljava/util/Iterator<Lcom/openvehicles/OVMS/Utilities$OnGroupCarTappedListener;>;"
    :catchall_0
    move-exception v1

    monitor-exit p0

    throw v1
.end method


# virtual methods
.method public declared-synchronized addOnGroupCarTappedListener(Lcom/openvehicles/OVMS/Utilities$OnGroupCarTappedListener;)V
    .locals 1
    .parameter "listener"

    .prologue
    .line 128
    monitor-enter p0

    :try_start_0
    iget-object v0, p0, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->_listeners:Ljava/util/ArrayList;

    invoke-virtual {v0, p1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 129
    monitor-exit p0

    return-void

    .line 128
    :catchall_0
    move-exception v0

    monitor-exit p0

    throw v0
.end method

.method public addOverlay(Lcom/google/android/maps/OverlayItem;)V
    .locals 1
    .parameter "overlay"

    .prologue
    .line 147
    iget-object v0, p0, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->mOverlays:Ljava/util/ArrayList;

    invoke-virtual {v0, p1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 148
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->populate()V

    .line 149
    return-void
.end method

.method public clearItems()V
    .locals 1

    .prologue
    .line 143
    iget-object v0, p0, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->mOverlays:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->clear()V

    .line 144
    return-void
.end method

.method protected createItem(I)Lcom/google/android/maps/OverlayItem;
    .locals 1
    .parameter "i"

    .prologue
    .line 163
    iget-object v0, p0, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->mOverlays:Ljava/util/ArrayList;

    invoke-virtual {v0, p1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/google/android/maps/OverlayItem;

    return-object v0
.end method

.method public draw(Landroid/graphics/Canvas;Lcom/google/android/maps/MapView;Z)V
    .locals 10
    .parameter "canvas"
    .parameter "mapView"
    .parameter "shadow"

    .prologue
    const/4 v9, 0x0

    .line 202
    invoke-super {p0, p1, p2, p3}, Lcom/google/android/maps/ItemizedOverlay;->draw(Landroid/graphics/Canvas;Lcom/google/android/maps/MapView;Z)V

    .line 205
    const/4 v0, 0x0

    .local v0, index:I
    :goto_0
    iget-object v5, p0, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->mOverlays:Ljava/util/ArrayList;

    invoke-virtual {v5}, Ljava/util/ArrayList;->size()I

    move-result v5

    if-lt v0, v5, :cond_0

    .line 239
    return-void

    .line 206
    :cond_0
    iget-object v5, p0, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->mOverlays:Ljava/util/ArrayList;

    invoke-virtual {v5, v0}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/openvehicles/OVMS/Utilities$CarMarker;

    .line 209
    .local v1, item:Lcom/openvehicles/OVMS/Utilities$CarMarker;
    invoke-virtual {v1}, Lcom/openvehicles/OVMS/Utilities$CarMarker;->getPoint()Lcom/google/android/maps/GeoPoint;

    move-result-object v3

    .line 210
    .local v3, point:Lcom/google/android/maps/GeoPoint;
    new-instance v4, Landroid/graphics/Point;

    invoke-direct {v4}, Landroid/graphics/Point;-><init>()V

    .line 211
    .local v4, ptScreenCoord:Landroid/graphics/Point;
    invoke-virtual {p2}, Lcom/google/android/maps/MapView;->getProjection()Lcom/google/android/maps/Projection;

    move-result-object v5

    invoke-interface {v5, v3, v4}, Lcom/google/android/maps/Projection;->toPixels(Lcom/google/android/maps/GeoPoint;Landroid/graphics/Point;)Landroid/graphics/Point;

    .line 214
    new-instance v2, Landroid/graphics/Paint;

    invoke-direct {v2}, Landroid/graphics/Paint;-><init>()V

    .line 215
    .local v2, paint:Landroid/graphics/Paint;
    const/4 v5, 0x1

    invoke-virtual {v2, v5}, Landroid/graphics/Paint;->setAntiAlias(Z)V

    .line 216
    sget-object v5, Landroid/graphics/Paint$Align;->CENTER:Landroid/graphics/Paint$Align;

    invoke-virtual {v2, v5}, Landroid/graphics/Paint;->setTextAlign(Landroid/graphics/Paint$Align;)V

    .line 217
    iget v5, p0, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->mLabelTextSize:I

    int-to-float v5, v5

    invoke-virtual {v2, v5}, Landroid/graphics/Paint;->setTextSize(F)V

    .line 219
    if-eqz p3, :cond_1

    .line 221
    const/16 v5, 0x64

    invoke-virtual {v2, v5, v9, v9, v9}, Landroid/graphics/Paint;->setARGB(IIII)V

    .line 223
    invoke-virtual {v1}, Lcom/openvehicles/OVMS/Utilities$CarMarker;->getTitle()Ljava/lang/String;

    move-result-object v5

    iget v6, v4, Landroid/graphics/Point;->x:I

    .line 224
    iget v7, p0, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->LABEL_SHADOW_XY:I

    add-int/2addr v6, v7

    int-to-float v6, v6

    iget v7, v4, Landroid/graphics/Point;->y:I

    add-int/lit8 v7, v7, -0x20

    .line 225
    iget v8, p0, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->LABEL_SHADOW_XY:I

    add-int/2addr v7, v8

    int-to-float v7, v7

    .line 223
    invoke-virtual {p1, v5, v6, v7, v2}, Landroid/graphics/Canvas;->drawText(Ljava/lang/String;FFLandroid/graphics/Paint;)V

    .line 205
    :goto_1
    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    .line 228
    :cond_1
    const/16 v5, 0xff

    invoke-virtual {v2, v5, v9, v9, v9}, Landroid/graphics/Paint;->setARGB(IIII)V

    .line 230
    invoke-virtual {v1}, Lcom/openvehicles/OVMS/Utilities$CarMarker;->getTitle()Ljava/lang/String;

    move-result-object v5

    iget v6, v4, Landroid/graphics/Point;->x:I

    int-to-float v6, v6

    .line 231
    iget v7, v4, Landroid/graphics/Point;->y:I

    add-int/lit8 v7, v7, -0x20

    int-to-float v7, v7

    .line 230
    invoke-virtual {p1, v5, v6, v7, v2}, Landroid/graphics/Canvas;->drawText(Ljava/lang/String;FFLandroid/graphics/Paint;)V

    .line 235
    iget-object v5, p0, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->DirectionalMarker:Landroid/graphics/Bitmap;

    iget v6, v1, Lcom/openvehicles/OVMS/Utilities$CarMarker;->Direction:I

    int-to-float v6, v6

    invoke-static {v5, v6}, Lcom/openvehicles/OVMS/Utilities;->GetRotatedDirectionalMarker(Landroid/graphics/Bitmap;F)Landroid/graphics/Bitmap;

    move-result-object v5

    iget v6, v4, Landroid/graphics/Point;->x:I

    add-int/lit8 v6, v6, -0x37

    int-to-float v6, v6

    .line 236
    iget v7, v4, Landroid/graphics/Point;->y:I

    add-int/lit8 v7, v7, -0x4b

    int-to-float v7, v7

    .line 235
    invoke-virtual {p1, v5, v6, v7, v2}, Landroid/graphics/Canvas;->drawBitmap(Landroid/graphics/Bitmap;FFLandroid/graphics/Paint;)V

    goto :goto_1
.end method

.method protected onTap(I)Z
    .locals 3
    .parameter "index"

    .prologue
    .line 182
    iget-object v2, p0, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->mOverlays:Ljava/util/ArrayList;

    invoke-virtual {v2, p1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/google/android/maps/OverlayItem;

    .line 184
    .local v1, item:Lcom/google/android/maps/OverlayItem;
    invoke-virtual {v1}, Lcom/google/android/maps/OverlayItem;->getSnippet()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/String;->length()I

    move-result v2

    if-lez v2, :cond_0

    .line 187
    new-instance v0, Landroid/app/AlertDialog$Builder;

    iget-object v2, p0, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->mContext:Landroid/content/Context;

    invoke-direct {v0, v2}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 188
    .local v0, dialog:Landroid/app/AlertDialog$Builder;
    invoke-virtual {v1}, Lcom/google/android/maps/OverlayItem;->getTitle()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v0, v2}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    .line 189
    invoke-virtual {v1}, Lcom/google/android/maps/OverlayItem;->getSnippet()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v0, v2}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    .line 190
    invoke-virtual {v0}, Landroid/app/AlertDialog$Builder;->show()Landroid/app/AlertDialog;

    .line 194
    .end local v0           #dialog:Landroid/app/AlertDialog$Builder;
    :goto_0
    const/4 v2, 0x1

    return v2

    .line 192
    :cond_0
    invoke-virtual {v1}, Lcom/google/android/maps/OverlayItem;->getTitle()Ljava/lang/String;

    move-result-object v2

    invoke-direct {p0, v2}, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->fireGroupCarTappedEvent(Ljava/lang/String;)V

    goto :goto_0
.end method

.method public declared-synchronized removeOnGroupCarTappedListener(Lcom/openvehicles/OVMS/Utilities$OnGroupCarTappedListener;)V
    .locals 1
    .parameter "listener"

    .prologue
    .line 131
    monitor-enter p0

    :try_start_0
    iget-object v0, p0, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->_listeners:Ljava/util/ArrayList;

    invoke-virtual {v0, p1}, Ljava/util/ArrayList;->remove(Ljava/lang/Object;)Z
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 132
    monitor-exit p0

    return-void

    .line 131
    :catchall_0
    move-exception v0

    monitor-exit p0

    throw v0
.end method

.method public removeOverlayAt(I)V
    .locals 1
    .parameter "index"

    .prologue
    .line 157
    iget-object v0, p0, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->mOverlays:Ljava/util/ArrayList;

    invoke-virtual {v0, p1}, Ljava/util/ArrayList;->remove(I)Ljava/lang/Object;

    .line 159
    return-void
.end method

.method public setOverlay(ILcom/google/android/maps/OverlayItem;)V
    .locals 1
    .parameter "index"
    .parameter "overlay"

    .prologue
    .line 152
    iget-object v0, p0, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->mOverlays:Ljava/util/ArrayList;

    invoke-virtual {v0, p1, p2}, Ljava/util/ArrayList;->set(ILjava/lang/Object;)Ljava/lang/Object;

    .line 153
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->populate()V

    .line 154
    return-void
.end method

.method public size()I
    .locals 1

    .prologue
    .line 168
    iget-object v0, p0, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->mOverlays:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    move-result v0

    return v0
.end method
