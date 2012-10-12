.class Lcom/openvehicles/OVMS/TabMap$CarMarkerOverlay;
.super Lcom/google/android/maps/ItemizedOverlay;
.source "TabMap.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/openvehicles/OVMS/TabMap;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "CarMarkerOverlay"
.end annotation


# instance fields
.field private mContext:Landroid/content/Context;

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

.field final synthetic this$0:Lcom/openvehicles/OVMS/TabMap;


# direct methods
.method public constructor <init>(Lcom/openvehicles/OVMS/TabMap;Landroid/graphics/drawable/Drawable;Landroid/content/Context;)V
    .locals 1
    .parameter
    .parameter "defaultMarker"
    .parameter "context"

    .prologue
    .line 83
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabMap$CarMarkerOverlay;->this$0:Lcom/openvehicles/OVMS/TabMap;

    .line 84
    invoke-static {p2}, Lcom/openvehicles/OVMS/TabMap$CarMarkerOverlay;->boundCenterBottom(Landroid/graphics/drawable/Drawable;)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/google/android/maps/ItemizedOverlay;-><init>(Landroid/graphics/drawable/Drawable;)V

    .line 58
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabMap$CarMarkerOverlay;->mOverlays:Ljava/util/ArrayList;

    .line 85
    iput-object p3, p0, Lcom/openvehicles/OVMS/TabMap$CarMarkerOverlay;->mContext:Landroid/content/Context;

    .line 86
    return-void
.end method


# virtual methods
.method public addOverlay(Lcom/google/android/maps/OverlayItem;)V
    .locals 1
    .parameter "overlay"

    .prologue
    .line 67
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$CarMarkerOverlay;->mOverlays:Ljava/util/ArrayList;

    invoke-virtual {v0, p1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 68
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TabMap$CarMarkerOverlay;->populate()V

    .line 69
    return-void
.end method

.method public clearItems()V
    .locals 1

    .prologue
    .line 63
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$CarMarkerOverlay;->mOverlays:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->clear()V

    .line 64
    return-void
.end method

.method protected createItem(I)Lcom/google/android/maps/OverlayItem;
    .locals 1
    .parameter "i"

    .prologue
    .line 74
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$CarMarkerOverlay;->mOverlays:Ljava/util/ArrayList;

    invoke-virtual {v0, p1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/google/android/maps/OverlayItem;

    return-object v0
.end method

.method protected onTap(I)Z
    .locals 3
    .parameter "index"

    .prologue
    .line 90
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabMap$CarMarkerOverlay;->mOverlays:Ljava/util/ArrayList;

    invoke-virtual {v2, p1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/google/android/maps/OverlayItem;

    .line 91
    .local v1, item:Lcom/google/android/maps/OverlayItem;
    new-instance v0, Landroid/app/AlertDialog$Builder;

    iget-object v2, p0, Lcom/openvehicles/OVMS/TabMap$CarMarkerOverlay;->mContext:Landroid/content/Context;

    invoke-direct {v0, v2}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 92
    .local v0, dialog:Landroid/app/AlertDialog$Builder;
    invoke-virtual {v1}, Lcom/google/android/maps/OverlayItem;->getTitle()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v0, v2}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    .line 93
    invoke-virtual {v1}, Lcom/google/android/maps/OverlayItem;->getSnippet()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v0, v2}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    .line 94
    invoke-virtual {v0}, Landroid/app/AlertDialog$Builder;->show()Landroid/app/AlertDialog;

    .line 95
    const/4 v2, 0x1

    return v2
.end method

.method public size()I
    .locals 1

    .prologue
    .line 80
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$CarMarkerOverlay;->mOverlays:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    move-result v0

    return v0
.end method
