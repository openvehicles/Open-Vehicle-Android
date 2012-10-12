.class public Lcom/openvehicles/OVMS/ReversedSeekBar;
.super Landroid/widget/SeekBar;
.source "ReversedSeekBar.java"


# instance fields
.field public isReversed:Z


# direct methods
.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 1
    .parameter "context"
    .parameter "attrs"

    .prologue
    .line 15
    invoke-direct {p0, p1, p2}, Landroid/widget/SeekBar;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    .line 11
    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/openvehicles/OVMS/ReversedSeekBar;->isReversed:Z

    .line 16
    return-void
.end method


# virtual methods
.method protected onDraw(Landroid/graphics/Canvas;)V
    .locals 4
    .parameter "c"

    .prologue
    const/high16 v3, 0x4000

    .line 20
    iget-boolean v2, p0, Lcom/openvehicles/OVMS/ReversedSeekBar;->isReversed:Z

    if-eqz v2, :cond_0

    .line 22
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/ReversedSeekBar;->getWidth()I

    move-result v2

    int-to-float v2, v2

    div-float v0, v2, v3

    .line 23
    .local v0, px:F
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/ReversedSeekBar;->getHeight()I

    move-result v2

    int-to-float v2, v2

    div-float v1, v2, v3

    .line 25
    .local v1, py:F
    const/high16 v2, -0x4080

    const/high16 v3, 0x3f80

    invoke-virtual {p1, v2, v3, v0, v1}, Landroid/graphics/Canvas;->scale(FFFF)V

    .line 28
    .end local v0           #px:F
    .end local v1           #py:F
    :cond_0
    invoke-super {p0, p1}, Landroid/widget/SeekBar;->onDraw(Landroid/graphics/Canvas;)V

    .line 29
    return-void
.end method

.method public onTouchEvent(Landroid/view/MotionEvent;)Z
    .locals 2
    .parameter "event"

    .prologue
    .line 33
    iget-boolean v0, p0, Lcom/openvehicles/OVMS/ReversedSeekBar;->isReversed:Z

    if-eqz v0, :cond_0

    .line 35
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/ReversedSeekBar;->getWidth()I

    move-result v0

    int-to-float v0, v0

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getX()F

    move-result v1

    sub-float/2addr v0, v1

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getY()F

    move-result v1

    invoke-virtual {p1, v0, v1}, Landroid/view/MotionEvent;->setLocation(FF)V

    .line 38
    :cond_0
    invoke-super {p0, p1}, Landroid/widget/SeekBar;->onTouchEvent(Landroid/view/MotionEvent;)Z

    move-result v0

    return v0
.end method
