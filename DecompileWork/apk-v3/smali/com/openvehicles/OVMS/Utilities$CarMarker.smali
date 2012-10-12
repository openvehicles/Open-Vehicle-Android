.class public Lcom/openvehicles/OVMS/Utilities$CarMarker;
.super Lcom/google/android/maps/OverlayItem;
.source "Utilities.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/openvehicles/OVMS/Utilities;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x9
    name = "CarMarker"
.end annotation


# instance fields
.field public Direction:I


# direct methods
.method public constructor <init>(Lcom/google/android/maps/GeoPoint;Ljava/lang/String;Ljava/lang/String;I)V
    .locals 0
    .parameter "point"
    .parameter "title"
    .parameter "snippet"
    .parameter "direction"

    .prologue
    .line 109
    invoke-direct {p0, p1, p2, p3}, Lcom/google/android/maps/OverlayItem;-><init>(Lcom/google/android/maps/GeoPoint;Ljava/lang/String;Ljava/lang/String;)V

    .line 110
    iput p4, p0, Lcom/openvehicles/OVMS/Utilities$CarMarker;->Direction:I

    .line 111
    return-void
.end method
