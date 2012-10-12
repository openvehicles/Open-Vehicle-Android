.class public Lorg/achartengine/tools/ZoomEvent;
.super Ljava/lang/Object;
.source "ZoomEvent.java"


# instance fields
.field private mZoomIn:Z

.field private mZoomRate:F


# direct methods
.method public constructor <init>(ZF)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 34
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 35
    iput-boolean p1, p0, Lorg/achartengine/tools/ZoomEvent;->mZoomIn:Z

    .line 36
    iput p2, p0, Lorg/achartengine/tools/ZoomEvent;->mZoomRate:F

    .line 37
    return-void
.end method


# virtual methods
.method public getZoomRate()F
    .locals 1

    .prologue
    .line 54
    iget v0, p0, Lorg/achartengine/tools/ZoomEvent;->mZoomRate:F

    return v0
.end method

.method public isZoomIn()Z
    .locals 1

    .prologue
    .line 45
    iget-boolean v0, p0, Lorg/achartengine/tools/ZoomEvent;->mZoomIn:Z

    return v0
.end method
