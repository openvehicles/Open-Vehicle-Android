.class public Lorg/achartengine/tools/Zoom;
.super Lorg/achartengine/tools/AbstractTool;
.source "Zoom.java"


# instance fields
.field private mZoomIn:Z

.field private mZoomListeners:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List",
            "<",
            "Lorg/achartengine/tools/ZoomListener;",
            ">;"
        }
    .end annotation
.end field

.field private mZoomRate:F


# direct methods
.method public constructor <init>(Lorg/achartengine/chart/AbstractChart;ZF)V
    .locals 1
    .parameter
    .parameter
    .parameter

    .prologue
    .line 45
    invoke-direct {p0, p1}, Lorg/achartengine/tools/AbstractTool;-><init>(Lorg/achartengine/chart/AbstractChart;)V

    .line 35
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lorg/achartengine/tools/Zoom;->mZoomListeners:Ljava/util/List;

    .line 46
    iput-boolean p2, p0, Lorg/achartengine/tools/Zoom;->mZoomIn:Z

    .line 47
    invoke-virtual {p0, p3}, Lorg/achartengine/tools/Zoom;->setZoomRate(F)V

    .line 48
    return-void
.end method

.method private declared-synchronized notifyZoomListeners(Lorg/achartengine/tools/ZoomEvent;)V
    .locals 2
    .parameter

    .prologue
    .line 122
    monitor-enter p0

    :try_start_0
    iget-object v0, p0, Lorg/achartengine/tools/Zoom;->mZoomListeners:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v1

    :goto_0
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lorg/achartengine/tools/ZoomListener;

    .line 123
    invoke-interface {v0, p1}, Lorg/achartengine/tools/ZoomListener;->zoomApplied(Lorg/achartengine/tools/ZoomEvent;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    goto :goto_0

    .line 122
    :catchall_0
    move-exception v0

    monitor-exit p0

    throw v0

    .line 125
    :cond_0
    monitor-exit p0

    return-void
.end method


# virtual methods
.method public declared-synchronized addZoomListener(Lorg/achartengine/tools/ZoomListener;)V
    .locals 1
    .parameter

    .prologue
    .line 142
    monitor-enter p0

    :try_start_0
    iget-object v0, p0, Lorg/achartengine/tools/Zoom;->mZoomListeners:Ljava/util/List;

    invoke-interface {v0, p1}, Ljava/util/List;->add(Ljava/lang/Object;)Z
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 143
    monitor-exit p0

    return-void

    .line 142
    :catchall_0
    move-exception v0

    monitor-exit p0

    throw v0
.end method

.method public apply()V
    .locals 18

    .prologue
    .line 63
    move-object/from16 v0, p0

    iget-object v1, v0, Lorg/achartengine/tools/Zoom;->mChart:Lorg/achartengine/chart/AbstractChart;

    instance-of v1, v1, Lorg/achartengine/chart/XYChart;

    if-eqz v1, :cond_8

    .line 64
    move-object/from16 v0, p0

    iget-object v1, v0, Lorg/achartengine/tools/Zoom;->mRenderer:Lorg/achartengine/renderer/XYMultipleSeriesRenderer;

    invoke-virtual {v1}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->getScalesCount()I

    move-result v10

    .line 65
    const/4 v6, 0x0

    :goto_0
    if-ge v6, v10, :cond_9

    .line 66
    move-object/from16 v0, p0

    invoke-virtual {v0, v6}, Lorg/achartengine/tools/Zoom;->getRange(I)[D

    move-result-object v3

    .line 67
    move-object/from16 v0, p0

    invoke-virtual {v0, v3, v6}, Lorg/achartengine/tools/Zoom;->checkRange([DI)V

    .line 68
    move-object/from16 v0, p0

    iget-object v1, v0, Lorg/achartengine/tools/Zoom;->mRenderer:Lorg/achartengine/renderer/XYMultipleSeriesRenderer;

    invoke-virtual {v1}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->getZoomLimits()[D

    move-result-object v11

    .line 69
    if-eqz v11, :cond_5

    array-length v1, v11

    const/4 v2, 0x4

    if-ne v1, v2, :cond_5

    const/4 v1, 0x1

    move v7, v1

    .line 71
    :goto_1
    const/4 v1, 0x0

    aget-wide v1, v3, v1

    const/4 v4, 0x1

    aget-wide v4, v3, v4

    add-double/2addr v1, v4

    const-wide/high16 v4, 0x4000

    div-double v12, v1, v4

    .line 72
    const/4 v1, 0x2

    aget-wide v1, v3, v1

    const/4 v4, 0x3

    aget-wide v4, v3, v4

    add-double/2addr v1, v4

    const-wide/high16 v4, 0x4000

    div-double v14, v1, v4

    .line 73
    const/4 v1, 0x1

    aget-wide v1, v3, v1

    const/4 v4, 0x0

    aget-wide v4, v3, v4

    sub-double/2addr v1, v4

    .line 74
    const/4 v4, 0x3

    aget-wide v4, v3, v4

    const/4 v8, 0x2

    aget-wide v8, v3, v8

    sub-double v3, v4, v8

    .line 75
    move-object/from16 v0, p0

    iget-boolean v5, v0, Lorg/achartengine/tools/Zoom;->mZoomIn:Z

    if-eqz v5, :cond_6

    .line 76
    move-object/from16 v0, p0

    iget-object v5, v0, Lorg/achartengine/tools/Zoom;->mRenderer:Lorg/achartengine/renderer/XYMultipleSeriesRenderer;

    invoke-virtual {v5}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->isZoomXEnabled()Z

    move-result v5

    if-eqz v5, :cond_0

    .line 77
    move-object/from16 v0, p0

    iget v5, v0, Lorg/achartengine/tools/Zoom;->mZoomRate:F

    float-to-double v8, v5

    div-double/2addr v1, v8

    .line 79
    :cond_0
    move-object/from16 v0, p0

    iget-object v5, v0, Lorg/achartengine/tools/Zoom;->mRenderer:Lorg/achartengine/renderer/XYMultipleSeriesRenderer;

    invoke-virtual {v5}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->isZoomYEnabled()Z

    move-result v5

    if-eqz v5, :cond_b

    .line 80
    move-object/from16 v0, p0

    iget v5, v0, Lorg/achartengine/tools/Zoom;->mZoomRate:F

    float-to-double v8, v5

    div-double/2addr v3, v8

    move-wide v8, v3

    move-wide v4, v1

    .line 91
    :goto_2
    move-object/from16 v0, p0

    iget-object v1, v0, Lorg/achartengine/tools/Zoom;->mRenderer:Lorg/achartengine/renderer/XYMultipleSeriesRenderer;

    invoke-virtual {v1}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->isZoomXEnabled()Z

    move-result v1

    if-eqz v1, :cond_2

    .line 92
    const-wide/high16 v1, 0x4000

    div-double v1, v4, v1

    sub-double v2, v12, v1

    .line 93
    const-wide/high16 v16, 0x4000

    div-double v4, v4, v16

    add-double/2addr v4, v12

    .line 94
    if-eqz v7, :cond_1

    const/4 v1, 0x0

    aget-wide v12, v11, v1

    cmpg-double v1, v12, v2

    if-gtz v1, :cond_2

    const/4 v1, 0x1

    aget-wide v12, v11, v1

    cmpl-double v1, v12, v4

    if-ltz v1, :cond_2

    :cond_1
    move-object/from16 v1, p0

    .line 95
    invoke-virtual/range {v1 .. v6}, Lorg/achartengine/tools/Zoom;->setXRange(DDI)V

    .line 98
    :cond_2
    move-object/from16 v0, p0

    iget-object v1, v0, Lorg/achartengine/tools/Zoom;->mRenderer:Lorg/achartengine/renderer/XYMultipleSeriesRenderer;

    invoke-virtual {v1}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->isZoomYEnabled()Z

    move-result v1

    if-eqz v1, :cond_4

    .line 99
    const-wide/high16 v1, 0x4000

    div-double v1, v8, v1

    sub-double v2, v14, v1

    .line 100
    const-wide/high16 v4, 0x4000

    div-double v4, v8, v4

    add-double/2addr v4, v14

    .line 101
    if-eqz v7, :cond_3

    const/4 v1, 0x2

    aget-wide v7, v11, v1

    cmpg-double v1, v7, v2

    if-gtz v1, :cond_4

    const/4 v1, 0x3

    aget-wide v7, v11, v1

    cmpl-double v1, v7, v4

    if-ltz v1, :cond_4

    :cond_3
    move-object/from16 v1, p0

    .line 102
    invoke-virtual/range {v1 .. v6}, Lorg/achartengine/tools/Zoom;->setYRange(DDI)V

    .line 65
    :cond_4
    add-int/lit8 v6, v6, 0x1

    goto/16 :goto_0

    .line 69
    :cond_5
    const/4 v1, 0x0

    move v7, v1

    goto/16 :goto_1

    .line 83
    :cond_6
    move-object/from16 v0, p0

    iget-object v5, v0, Lorg/achartengine/tools/Zoom;->mRenderer:Lorg/achartengine/renderer/XYMultipleSeriesRenderer;

    invoke-virtual {v5}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->isZoomXEnabled()Z

    move-result v5

    if-eqz v5, :cond_7

    .line 84
    move-object/from16 v0, p0

    iget v5, v0, Lorg/achartengine/tools/Zoom;->mZoomRate:F

    float-to-double v8, v5

    mul-double/2addr v1, v8

    .line 86
    :cond_7
    move-object/from16 v0, p0

    iget-object v5, v0, Lorg/achartengine/tools/Zoom;->mRenderer:Lorg/achartengine/renderer/XYMultipleSeriesRenderer;

    invoke-virtual {v5}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->isZoomYEnabled()Z

    move-result v5

    if-eqz v5, :cond_b

    .line 87
    move-object/from16 v0, p0

    iget v5, v0, Lorg/achartengine/tools/Zoom;->mZoomRate:F

    float-to-double v8, v5

    mul-double/2addr v3, v8

    move-wide v8, v3

    move-wide v4, v1

    goto :goto_2

    .line 107
    :cond_8
    move-object/from16 v0, p0

    iget-object v1, v0, Lorg/achartengine/tools/Zoom;->mChart:Lorg/achartengine/chart/AbstractChart;

    check-cast v1, Lorg/achartengine/chart/RoundChart;

    invoke-virtual {v1}, Lorg/achartengine/chart/RoundChart;->getRenderer()Lorg/achartengine/renderer/DefaultRenderer;

    move-result-object v1

    .line 108
    move-object/from16 v0, p0

    iget-boolean v2, v0, Lorg/achartengine/tools/Zoom;->mZoomIn:Z

    if-eqz v2, :cond_a

    .line 109
    invoke-virtual {v1}, Lorg/achartengine/renderer/DefaultRenderer;->getScale()F

    move-result v2

    move-object/from16 v0, p0

    iget v3, v0, Lorg/achartengine/tools/Zoom;->mZoomRate:F

    mul-float/2addr v2, v3

    invoke-virtual {v1, v2}, Lorg/achartengine/renderer/DefaultRenderer;->setScale(F)V

    .line 114
    :cond_9
    :goto_3
    new-instance v1, Lorg/achartengine/tools/ZoomEvent;

    move-object/from16 v0, p0

    iget-boolean v2, v0, Lorg/achartengine/tools/Zoom;->mZoomIn:Z

    move-object/from16 v0, p0

    iget v3, v0, Lorg/achartengine/tools/Zoom;->mZoomRate:F

    invoke-direct {v1, v2, v3}, Lorg/achartengine/tools/ZoomEvent;-><init>(ZF)V

    move-object/from16 v0, p0

    invoke-direct {v0, v1}, Lorg/achartengine/tools/Zoom;->notifyZoomListeners(Lorg/achartengine/tools/ZoomEvent;)V

    .line 115
    return-void

    .line 111
    :cond_a
    invoke-virtual {v1}, Lorg/achartengine/renderer/DefaultRenderer;->getScale()F

    move-result v2

    move-object/from16 v0, p0

    iget v3, v0, Lorg/achartengine/tools/Zoom;->mZoomRate:F

    div-float/2addr v2, v3

    invoke-virtual {v1, v2}, Lorg/achartengine/renderer/DefaultRenderer;->setScale(F)V

    goto :goto_3

    :cond_b
    move-wide v8, v3

    move-wide v4, v1

    goto/16 :goto_2
.end method

.method public declared-synchronized notifyZoomResetListeners()V
    .locals 2

    .prologue
    .line 131
    monitor-enter p0

    :try_start_0
    iget-object v0, p0, Lorg/achartengine/tools/Zoom;->mZoomListeners:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v1

    :goto_0
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lorg/achartengine/tools/ZoomListener;

    .line 132
    invoke-interface {v0}, Lorg/achartengine/tools/ZoomListener;->zoomReset()V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    goto :goto_0

    .line 131
    :catchall_0
    move-exception v0

    monitor-exit p0

    throw v0

    .line 134
    :cond_0
    monitor-exit p0

    return-void
.end method

.method public declared-synchronized removeZoomListener(Lorg/achartengine/tools/ZoomListener;)V
    .locals 1
    .parameter

    .prologue
    .line 151
    monitor-enter p0

    :try_start_0
    iget-object v0, p0, Lorg/achartengine/tools/Zoom;->mZoomListeners:Ljava/util/List;

    invoke-interface {v0, p1}, Ljava/util/List;->add(Ljava/lang/Object;)Z
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 152
    monitor-exit p0

    return-void

    .line 151
    :catchall_0
    move-exception v0

    monitor-exit p0

    throw v0
.end method

.method public setZoomRate(F)V
    .locals 0
    .parameter

    .prologue
    .line 56
    iput p1, p0, Lorg/achartengine/tools/Zoom;->mZoomRate:F

    .line 57
    return-void
.end method
