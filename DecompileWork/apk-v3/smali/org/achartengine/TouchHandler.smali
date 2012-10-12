.class public Lorg/achartengine/TouchHandler;
.super Ljava/lang/Object;
.source "TouchHandler.java"

# interfaces
.implements Lorg/achartengine/ITouchHandler;


# instance fields
.field private graphicalView:Lorg/achartengine/GraphicalView;

.field private mPan:Lorg/achartengine/tools/Pan;

.field private mPinchZoom:Lorg/achartengine/tools/Zoom;

.field private mRenderer:Lorg/achartengine/renderer/DefaultRenderer;

.field private oldX:F

.field private oldX2:F

.field private oldY:F

.field private oldY2:F

.field private zoomR:Landroid/graphics/RectF;


# direct methods
.method public constructor <init>(Lorg/achartengine/GraphicalView;Lorg/achartengine/chart/AbstractChart;)V
    .locals 3
    .parameter
    .parameter

    .prologue
    .line 59
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 45
    new-instance v0, Landroid/graphics/RectF;

    invoke-direct {v0}, Landroid/graphics/RectF;-><init>()V

    iput-object v0, p0, Lorg/achartengine/TouchHandler;->zoomR:Landroid/graphics/RectF;

    .line 60
    iput-object p1, p0, Lorg/achartengine/TouchHandler;->graphicalView:Lorg/achartengine/GraphicalView;

    .line 61
    iget-object v0, p0, Lorg/achartengine/TouchHandler;->graphicalView:Lorg/achartengine/GraphicalView;

    invoke-virtual {v0}, Lorg/achartengine/GraphicalView;->getZoomRectangle()Landroid/graphics/RectF;

    move-result-object v0

    iput-object v0, p0, Lorg/achartengine/TouchHandler;->zoomR:Landroid/graphics/RectF;

    .line 62
    instance-of v0, p2, Lorg/achartengine/chart/XYChart;

    if-eqz v0, :cond_2

    move-object v0, p2

    .line 63
    check-cast v0, Lorg/achartengine/chart/XYChart;

    invoke-virtual {v0}, Lorg/achartengine/chart/XYChart;->getRenderer()Lorg/achartengine/renderer/XYMultipleSeriesRenderer;

    move-result-object v0

    iput-object v0, p0, Lorg/achartengine/TouchHandler;->mRenderer:Lorg/achartengine/renderer/DefaultRenderer;

    .line 67
    :goto_0
    iget-object v0, p0, Lorg/achartengine/TouchHandler;->mRenderer:Lorg/achartengine/renderer/DefaultRenderer;

    invoke-virtual {v0}, Lorg/achartengine/renderer/DefaultRenderer;->isPanEnabled()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 68
    new-instance v0, Lorg/achartengine/tools/Pan;

    invoke-direct {v0, p2}, Lorg/achartengine/tools/Pan;-><init>(Lorg/achartengine/chart/AbstractChart;)V

    iput-object v0, p0, Lorg/achartengine/TouchHandler;->mPan:Lorg/achartengine/tools/Pan;

    .line 70
    :cond_0
    iget-object v0, p0, Lorg/achartengine/TouchHandler;->mRenderer:Lorg/achartengine/renderer/DefaultRenderer;

    invoke-virtual {v0}, Lorg/achartengine/renderer/DefaultRenderer;->isZoomEnabled()Z

    move-result v0

    if-eqz v0, :cond_1

    .line 71
    new-instance v0, Lorg/achartengine/tools/Zoom;

    const/4 v1, 0x1

    const/high16 v2, 0x3f80

    invoke-direct {v0, p2, v1, v2}, Lorg/achartengine/tools/Zoom;-><init>(Lorg/achartengine/chart/AbstractChart;ZF)V

    iput-object v0, p0, Lorg/achartengine/TouchHandler;->mPinchZoom:Lorg/achartengine/tools/Zoom;

    .line 73
    :cond_1
    return-void

    :cond_2
    move-object v0, p2

    .line 65
    check-cast v0, Lorg/achartengine/chart/RoundChart;

    invoke-virtual {v0}, Lorg/achartengine/chart/RoundChart;->getRenderer()Lorg/achartengine/renderer/DefaultRenderer;

    move-result-object v0

    iput-object v0, p0, Lorg/achartengine/TouchHandler;->mRenderer:Lorg/achartengine/renderer/DefaultRenderer;

    goto :goto_0
.end method


# virtual methods
.method public addPanListener(Lorg/achartengine/tools/PanListener;)V
    .locals 1
    .parameter

    .prologue
    .line 169
    iget-object v0, p0, Lorg/achartengine/TouchHandler;->mPan:Lorg/achartengine/tools/Pan;

    if-eqz v0, :cond_0

    .line 170
    iget-object v0, p0, Lorg/achartengine/TouchHandler;->mPan:Lorg/achartengine/tools/Pan;

    invoke-virtual {v0, p1}, Lorg/achartengine/tools/Pan;->addPanListener(Lorg/achartengine/tools/PanListener;)V

    .line 172
    :cond_0
    return-void
.end method

.method public addZoomListener(Lorg/achartengine/tools/ZoomListener;)V
    .locals 1
    .parameter

    .prologue
    .line 147
    iget-object v0, p0, Lorg/achartengine/TouchHandler;->mPinchZoom:Lorg/achartengine/tools/Zoom;

    if-eqz v0, :cond_0

    .line 148
    iget-object v0, p0, Lorg/achartengine/TouchHandler;->mPinchZoom:Lorg/achartengine/tools/Zoom;

    invoke-virtual {v0, p1}, Lorg/achartengine/tools/Zoom;->addZoomListener(Lorg/achartengine/tools/ZoomListener;)V

    .line 150
    :cond_0
    return-void
.end method

.method public handleTouch(Landroid/view/MotionEvent;)Z
    .locals 11
    .parameter

    .prologue
    const/high16 v5, 0x4040

    const/high16 v4, -0x4080

    const/4 v1, 0x0

    const/4 v0, 0x1

    const/4 v6, 0x0

    .line 81
    invoke-virtual {p1}, Landroid/view/MotionEvent;->getAction()I

    move-result v2

    .line 82
    iget-object v3, p0, Lorg/achartengine/TouchHandler;->mRenderer:Lorg/achartengine/renderer/DefaultRenderer;

    if-eqz v3, :cond_7

    const/4 v3, 0x2

    if-ne v2, v3, :cond_7

    .line 83
    iget v2, p0, Lorg/achartengine/TouchHandler;->oldX:F

    cmpl-float v2, v2, v6

    if-gez v2, :cond_0

    iget v2, p0, Lorg/achartengine/TouchHandler;->oldY:F

    cmpl-float v2, v2, v6

    if-ltz v2, :cond_c

    .line 84
    :cond_0
    invoke-virtual {p1, v1}, Landroid/view/MotionEvent;->getX(I)F

    move-result v2

    .line 85
    invoke-virtual {p1, v1}, Landroid/view/MotionEvent;->getY(I)F

    move-result v3

    .line 86
    invoke-virtual {p1}, Landroid/view/MotionEvent;->getPointerCount()I

    move-result v1

    if-le v1, v0, :cond_6

    iget v1, p0, Lorg/achartengine/TouchHandler;->oldX2:F

    cmpl-float v1, v1, v6

    if-gez v1, :cond_1

    iget v1, p0, Lorg/achartengine/TouchHandler;->oldY2:F

    cmpl-float v1, v1, v6

    if-ltz v1, :cond_6

    :cond_1
    iget-object v1, p0, Lorg/achartengine/TouchHandler;->mRenderer:Lorg/achartengine/renderer/DefaultRenderer;

    invoke-virtual {v1}, Lorg/achartengine/renderer/DefaultRenderer;->isZoomEnabled()Z

    move-result v1

    if-eqz v1, :cond_6

    .line 87
    invoke-virtual {p1, v0}, Landroid/view/MotionEvent;->getX(I)F

    move-result v4

    .line 88
    invoke-virtual {p1, v0}, Landroid/view/MotionEvent;->getY(I)F

    move-result v5

    .line 89
    sub-float v1, v2, v4

    invoke-static {v1}, Ljava/lang/Math;->abs(F)F

    move-result v1

    .line 90
    sub-float v6, v3, v5

    invoke-static {v6}, Ljava/lang/Math;->abs(F)F

    move-result v6

    .line 91
    iget v7, p0, Lorg/achartengine/TouchHandler;->oldX:F

    iget v8, p0, Lorg/achartengine/TouchHandler;->oldX2:F

    sub-float/2addr v7, v8

    invoke-static {v7}, Ljava/lang/Math;->abs(F)F

    move-result v7

    .line 92
    iget v8, p0, Lorg/achartengine/TouchHandler;->oldY:F

    iget v9, p0, Lorg/achartengine/TouchHandler;->oldY2:F

    sub-float/2addr v8, v9

    invoke-static {v8}, Ljava/lang/Math;->abs(F)F

    move-result v8

    .line 94
    iget v9, p0, Lorg/achartengine/TouchHandler;->oldX:F

    sub-float v9, v2, v9

    invoke-static {v9}, Ljava/lang/Math;->abs(F)F

    move-result v9

    iget v10, p0, Lorg/achartengine/TouchHandler;->oldY:F

    sub-float v10, v3, v10

    invoke-static {v10}, Ljava/lang/Math;->abs(F)F

    move-result v10

    cmpl-float v9, v9, v10

    if-ltz v9, :cond_5

    .line 95
    div-float/2addr v1, v7

    .line 99
    :goto_0
    float-to-double v6, v1

    const-wide v8, 0x3fed16872b020c4aL

    cmpl-double v6, v6, v8

    if-lez v6, :cond_2

    float-to-double v6, v1

    const-wide v8, 0x3ff199999999999aL

    cmpg-double v6, v6, v8

    if-gez v6, :cond_2

    .line 100
    iget-object v6, p0, Lorg/achartengine/TouchHandler;->mPinchZoom:Lorg/achartengine/tools/Zoom;

    invoke-virtual {v6, v1}, Lorg/achartengine/tools/Zoom;->setZoomRate(F)V

    .line 101
    iget-object v1, p0, Lorg/achartengine/TouchHandler;->mPinchZoom:Lorg/achartengine/tools/Zoom;

    invoke-virtual {v1}, Lorg/achartengine/tools/Zoom;->apply()V

    .line 103
    :cond_2
    iput v4, p0, Lorg/achartengine/TouchHandler;->oldX2:F

    .line 104
    iput v5, p0, Lorg/achartengine/TouchHandler;->oldY2:F

    .line 110
    :cond_3
    :goto_1
    iput v2, p0, Lorg/achartengine/TouchHandler;->oldX:F

    .line 111
    iput v3, p0, Lorg/achartengine/TouchHandler;->oldY:F

    .line 112
    iget-object v1, p0, Lorg/achartengine/TouchHandler;->graphicalView:Lorg/achartengine/GraphicalView;

    invoke-virtual {v1}, Lorg/achartengine/GraphicalView;->repaint()V

    .line 138
    :cond_4
    :goto_2
    return v0

    .line 97
    :cond_5
    div-float v1, v6, v8

    goto :goto_0

    .line 105
    :cond_6
    iget-object v1, p0, Lorg/achartengine/TouchHandler;->mRenderer:Lorg/achartengine/renderer/DefaultRenderer;

    invoke-virtual {v1}, Lorg/achartengine/renderer/DefaultRenderer;->isPanEnabled()Z

    move-result v1

    if-eqz v1, :cond_3

    .line 106
    iget-object v1, p0, Lorg/achartengine/TouchHandler;->mPan:Lorg/achartengine/tools/Pan;

    iget v4, p0, Lorg/achartengine/TouchHandler;->oldX:F

    iget v5, p0, Lorg/achartengine/TouchHandler;->oldY:F

    invoke-virtual {v1, v4, v5, v2, v3}, Lorg/achartengine/tools/Pan;->apply(FFFF)V

    .line 107
    iput v6, p0, Lorg/achartengine/TouchHandler;->oldX2:F

    .line 108
    iput v6, p0, Lorg/achartengine/TouchHandler;->oldY2:F

    goto :goto_1

    .line 115
    :cond_7
    if-nez v2, :cond_a

    .line 116
    invoke-virtual {p1, v1}, Landroid/view/MotionEvent;->getX(I)F

    move-result v2

    iput v2, p0, Lorg/achartengine/TouchHandler;->oldX:F

    .line 117
    invoke-virtual {p1, v1}, Landroid/view/MotionEvent;->getY(I)F

    move-result v2

    iput v2, p0, Lorg/achartengine/TouchHandler;->oldY:F

    .line 118
    iget-object v2, p0, Lorg/achartengine/TouchHandler;->mRenderer:Lorg/achartengine/renderer/DefaultRenderer;

    if-eqz v2, :cond_c

    iget-object v2, p0, Lorg/achartengine/TouchHandler;->mRenderer:Lorg/achartengine/renderer/DefaultRenderer;

    invoke-virtual {v2}, Lorg/achartengine/renderer/DefaultRenderer;->isZoomEnabled()Z

    move-result v2

    if-eqz v2, :cond_c

    iget-object v2, p0, Lorg/achartengine/TouchHandler;->zoomR:Landroid/graphics/RectF;

    iget v3, p0, Lorg/achartengine/TouchHandler;->oldX:F

    iget v4, p0, Lorg/achartengine/TouchHandler;->oldY:F

    invoke-virtual {v2, v3, v4}, Landroid/graphics/RectF;->contains(FF)Z

    move-result v2

    if-eqz v2, :cond_c

    .line 119
    iget v1, p0, Lorg/achartengine/TouchHandler;->oldX:F

    iget-object v2, p0, Lorg/achartengine/TouchHandler;->zoomR:Landroid/graphics/RectF;

    iget v2, v2, Landroid/graphics/RectF;->left:F

    iget-object v3, p0, Lorg/achartengine/TouchHandler;->zoomR:Landroid/graphics/RectF;

    invoke-virtual {v3}, Landroid/graphics/RectF;->width()F

    move-result v3

    div-float/2addr v3, v5

    add-float/2addr v2, v3

    cmpg-float v1, v1, v2

    if-gez v1, :cond_8

    .line 120
    iget-object v1, p0, Lorg/achartengine/TouchHandler;->graphicalView:Lorg/achartengine/GraphicalView;

    invoke-virtual {v1}, Lorg/achartengine/GraphicalView;->zoomIn()V

    goto :goto_2

    .line 121
    :cond_8
    iget v1, p0, Lorg/achartengine/TouchHandler;->oldX:F

    iget-object v2, p0, Lorg/achartengine/TouchHandler;->zoomR:Landroid/graphics/RectF;

    iget v2, v2, Landroid/graphics/RectF;->left:F

    iget-object v3, p0, Lorg/achartengine/TouchHandler;->zoomR:Landroid/graphics/RectF;

    invoke-virtual {v3}, Landroid/graphics/RectF;->width()F

    move-result v3

    const/high16 v4, 0x4000

    mul-float/2addr v3, v4

    div-float/2addr v3, v5

    add-float/2addr v2, v3

    cmpg-float v1, v1, v2

    if-gez v1, :cond_9

    .line 122
    iget-object v1, p0, Lorg/achartengine/TouchHandler;->graphicalView:Lorg/achartengine/GraphicalView;

    invoke-virtual {v1}, Lorg/achartengine/GraphicalView;->zoomOut()V

    goto :goto_2

    .line 124
    :cond_9
    iget-object v1, p0, Lorg/achartengine/TouchHandler;->graphicalView:Lorg/achartengine/GraphicalView;

    invoke-virtual {v1}, Lorg/achartengine/GraphicalView;->zoomReset()V

    goto :goto_2

    .line 128
    :cond_a
    if-eq v2, v0, :cond_b

    const/4 v3, 0x6

    if-ne v2, v3, :cond_c

    .line 129
    :cond_b
    iput v6, p0, Lorg/achartengine/TouchHandler;->oldX:F

    .line 130
    iput v6, p0, Lorg/achartengine/TouchHandler;->oldY:F

    .line 131
    iput v6, p0, Lorg/achartengine/TouchHandler;->oldX2:F

    .line 132
    iput v6, p0, Lorg/achartengine/TouchHandler;->oldY2:F

    .line 133
    const/4 v3, 0x6

    if-ne v2, v3, :cond_c

    .line 134
    iput v4, p0, Lorg/achartengine/TouchHandler;->oldX:F

    .line 135
    iput v4, p0, Lorg/achartengine/TouchHandler;->oldY:F

    .line 138
    :cond_c
    iget-object v2, p0, Lorg/achartengine/TouchHandler;->mRenderer:Lorg/achartengine/renderer/DefaultRenderer;

    invoke-virtual {v2}, Lorg/achartengine/renderer/DefaultRenderer;->isClickEnabled()Z

    move-result v2

    if-eqz v2, :cond_4

    move v0, v1

    goto/16 :goto_2
.end method

.method public removePanListener(Lorg/achartengine/tools/PanListener;)V
    .locals 1
    .parameter

    .prologue
    .line 180
    iget-object v0, p0, Lorg/achartengine/TouchHandler;->mPan:Lorg/achartengine/tools/Pan;

    if-eqz v0, :cond_0

    .line 181
    iget-object v0, p0, Lorg/achartengine/TouchHandler;->mPan:Lorg/achartengine/tools/Pan;

    invoke-virtual {v0, p1}, Lorg/achartengine/tools/Pan;->removePanListener(Lorg/achartengine/tools/PanListener;)V

    .line 183
    :cond_0
    return-void
.end method

.method public removeZoomListener(Lorg/achartengine/tools/ZoomListener;)V
    .locals 1
    .parameter

    .prologue
    .line 158
    iget-object v0, p0, Lorg/achartengine/TouchHandler;->mPinchZoom:Lorg/achartengine/tools/Zoom;

    if-eqz v0, :cond_0

    .line 159
    iget-object v0, p0, Lorg/achartengine/TouchHandler;->mPinchZoom:Lorg/achartengine/tools/Zoom;

    invoke-virtual {v0, p1}, Lorg/achartengine/tools/Zoom;->removeZoomListener(Lorg/achartengine/tools/ZoomListener;)V

    .line 161
    :cond_0
    return-void
.end method
