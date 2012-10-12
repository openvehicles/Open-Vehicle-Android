.class public Lorg/achartengine/tools/Pan;
.super Lorg/achartengine/tools/AbstractTool;
.source "Pan.java"


# instance fields
.field private mPanListeners:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List",
            "<",
            "Lorg/achartengine/tools/PanListener;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method public constructor <init>(Lorg/achartengine/chart/AbstractChart;)V
    .locals 1
    .parameter

    .prologue
    .line 38
    invoke-direct {p0, p1}, Lorg/achartengine/tools/AbstractTool;-><init>(Lorg/achartengine/chart/AbstractChart;)V

    .line 30
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lorg/achartengine/tools/Pan;->mPanListeners:Ljava/util/List;

    .line 39
    return-void
.end method

.method private declared-synchronized notifyPanListeners()V
    .locals 2

    .prologue
    .line 103
    monitor-enter p0

    :try_start_0
    iget-object v0, p0, Lorg/achartengine/tools/Pan;->mPanListeners:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v1

    :goto_0
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lorg/achartengine/tools/PanListener;

    .line 104
    invoke-interface {v0}, Lorg/achartengine/tools/PanListener;->panApplied()V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    goto :goto_0

    .line 103
    :catchall_0
    move-exception v0

    monitor-exit p0

    throw v0

    .line 106
    :cond_0
    monitor-exit p0

    return-void
.end method


# virtual methods
.method public declared-synchronized addPanListener(Lorg/achartengine/tools/PanListener;)V
    .locals 1
    .parameter

    .prologue
    .line 114
    monitor-enter p0

    :try_start_0
    iget-object v0, p0, Lorg/achartengine/tools/Pan;->mPanListeners:Ljava/util/List;

    invoke-interface {v0, p1}, Ljava/util/List;->add(Ljava/lang/Object;)Z
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 115
    monitor-exit p0

    return-void

    .line 114
    :catchall_0
    move-exception v0

    monitor-exit p0

    throw v0
.end method

.method public apply(FFFF)V
    .locals 17
    .parameter
    .parameter
    .parameter
    .parameter

    .prologue
    .line 50
    move-object/from16 v0, p0

    iget-object v2, v0, Lorg/achartengine/tools/Pan;->mChart:Lorg/achartengine/chart/AbstractChart;

    instance-of v2, v2, Lorg/achartengine/chart/XYChart;

    if-eqz v2, :cond_c

    .line 51
    move-object/from16 v0, p0

    iget-object v2, v0, Lorg/achartengine/tools/Pan;->mRenderer:Lorg/achartengine/renderer/XYMultipleSeriesRenderer;

    invoke-virtual {v2}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->getScalesCount()I

    move-result v10

    .line 52
    move-object/from16 v0, p0

    iget-object v2, v0, Lorg/achartengine/tools/Pan;->mRenderer:Lorg/achartengine/renderer/XYMultipleSeriesRenderer;

    invoke-virtual {v2}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->getPanLimits()[D

    move-result-object v11

    .line 53
    if-eqz v11, :cond_2

    array-length v2, v11

    const/4 v3, 0x4

    if-ne v2, v3, :cond_2

    const/4 v2, 0x1

    move v9, v2

    .line 54
    :goto_0
    move-object/from16 v0, p0

    iget-object v2, v0, Lorg/achartengine/tools/Pan;->mChart:Lorg/achartengine/chart/AbstractChart;

    move-object v8, v2

    check-cast v8, Lorg/achartengine/chart/XYChart;

    .line 55
    const/4 v7, 0x0

    :goto_1
    if-ge v7, v10, :cond_d

    .line 56
    move-object/from16 v0, p0

    invoke-virtual {v0, v7}, Lorg/achartengine/tools/Pan;->getRange(I)[D

    move-result-object v12

    .line 57
    invoke-virtual {v8, v7}, Lorg/achartengine/chart/XYChart;->getCalcRange(I)[D

    move-result-object v2

    .line 58
    const/4 v3, 0x0

    aget-wide v3, v12, v3

    const/4 v5, 0x1

    aget-wide v5, v12, v5

    cmpl-double v3, v3, v5

    if-nez v3, :cond_0

    const/4 v3, 0x0

    aget-wide v3, v2, v3

    const/4 v5, 0x1

    aget-wide v5, v2, v5

    cmpl-double v3, v3, v5

    if-eqz v3, :cond_1

    :cond_0
    const/4 v3, 0x2

    aget-wide v3, v12, v3

    const/4 v5, 0x3

    aget-wide v5, v12, v5

    cmpl-double v3, v3, v5

    if-nez v3, :cond_3

    const/4 v3, 0x2

    aget-wide v3, v2, v3

    const/4 v5, 0x3

    aget-wide v5, v2, v5

    cmpl-double v2, v3, v5

    if-nez v2, :cond_3

    .line 97
    :cond_1
    :goto_2
    return-void

    .line 53
    :cond_2
    const/4 v2, 0x0

    move v9, v2

    goto :goto_0

    .line 62
    :cond_3
    move-object/from16 v0, p0

    invoke-virtual {v0, v12, v7}, Lorg/achartengine/tools/Pan;->checkRange([DI)V

    .line 64
    move/from16 v0, p1

    move/from16 v1, p2

    invoke-virtual {v8, v0, v1, v7}, Lorg/achartengine/chart/XYChart;->toRealPoint(FFI)[D

    move-result-object v2

    .line 65
    move/from16 v0, p3

    move/from16 v1, p4

    invoke-virtual {v8, v0, v1, v7}, Lorg/achartengine/chart/XYChart;->toRealPoint(FFI)[D

    move-result-object v3

    .line 66
    const/4 v4, 0x0

    aget-wide v4, v2, v4

    const/4 v6, 0x0

    aget-wide v13, v3, v6

    sub-double v5, v4, v13

    .line 67
    const/4 v4, 0x1

    aget-wide v13, v2, v4

    const/4 v2, 0x1

    aget-wide v2, v3, v2

    sub-double/2addr v13, v2

    .line 68
    move-object/from16 v0, p0

    iget-object v2, v0, Lorg/achartengine/tools/Pan;->mRenderer:Lorg/achartengine/renderer/XYMultipleSeriesRenderer;

    invoke-virtual {v2}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->isPanXEnabled()Z

    move-result v2

    if-eqz v2, :cond_4

    .line 69
    if-eqz v9, :cond_8

    .line 70
    const/4 v2, 0x0

    aget-wide v2, v11, v2

    const/4 v4, 0x0

    aget-wide v15, v12, v4

    add-double/2addr v15, v5

    cmpg-double v2, v2, v15

    if-gtz v2, :cond_6

    const/4 v2, 0x1

    .line 71
    :goto_3
    const/4 v3, 0x1

    aget-wide v3, v11, v3

    const/4 v15, 0x1

    aget-wide v15, v12, v15

    add-double/2addr v15, v5

    cmpl-double v3, v3, v15

    if-ltz v3, :cond_7

    const/4 v3, 0x1

    .line 72
    :goto_4
    if-eqz v2, :cond_4

    if-eqz v3, :cond_4

    .line 73
    const/4 v2, 0x0

    aget-wide v2, v12, v2

    add-double v3, v2, v5

    const/4 v2, 0x1

    aget-wide v15, v12, v2

    add-double/2addr v5, v15

    move-object/from16 v2, p0

    invoke-virtual/range {v2 .. v7}, Lorg/achartengine/tools/Pan;->setXRange(DDI)V

    .line 79
    :cond_4
    :goto_5
    move-object/from16 v0, p0

    iget-object v2, v0, Lorg/achartengine/tools/Pan;->mRenderer:Lorg/achartengine/renderer/XYMultipleSeriesRenderer;

    invoke-virtual {v2}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->isPanYEnabled()Z

    move-result v2

    if-eqz v2, :cond_5

    .line 80
    if-eqz v9, :cond_b

    .line 81
    const/4 v2, 0x2

    aget-wide v2, v11, v2

    const/4 v4, 0x2

    aget-wide v4, v12, v4

    add-double/2addr v4, v13

    cmpg-double v2, v2, v4

    if-gtz v2, :cond_9

    const/4 v2, 0x1

    .line 82
    :goto_6
    const/4 v3, 0x3

    aget-wide v3, v11, v3

    const/4 v5, 0x3

    aget-wide v5, v12, v5

    add-double/2addr v5, v13

    cmpg-double v3, v3, v5

    if-gez v3, :cond_a

    const/4 v3, 0x1

    .line 83
    :goto_7
    if-eqz v2, :cond_5

    if-nez v3, :cond_5

    .line 84
    const/4 v2, 0x2

    aget-wide v2, v12, v2

    add-double v3, v2, v13

    const/4 v2, 0x3

    aget-wide v5, v12, v2

    add-double/2addr v5, v13

    move-object/from16 v2, p0

    invoke-virtual/range {v2 .. v7}, Lorg/achartengine/tools/Pan;->setYRange(DDI)V

    .line 55
    :cond_5
    :goto_8
    add-int/lit8 v7, v7, 0x1

    goto/16 :goto_1

    .line 70
    :cond_6
    const/4 v2, 0x0

    goto :goto_3

    .line 71
    :cond_7
    const/4 v3, 0x0

    goto :goto_4

    .line 76
    :cond_8
    const/4 v2, 0x0

    aget-wide v2, v12, v2

    add-double v3, v2, v5

    const/4 v2, 0x1

    aget-wide v15, v12, v2

    add-double/2addr v5, v15

    move-object/from16 v2, p0

    invoke-virtual/range {v2 .. v7}, Lorg/achartengine/tools/Pan;->setXRange(DDI)V

    goto :goto_5

    .line 81
    :cond_9
    const/4 v2, 0x0

    goto :goto_6

    .line 82
    :cond_a
    const/4 v3, 0x0

    goto :goto_7

    .line 87
    :cond_b
    const/4 v2, 0x2

    aget-wide v2, v12, v2

    add-double v3, v2, v13

    const/4 v2, 0x3

    aget-wide v5, v12, v2

    add-double/2addr v5, v13

    move-object/from16 v2, p0

    invoke-virtual/range {v2 .. v7}, Lorg/achartengine/tools/Pan;->setYRange(DDI)V

    goto :goto_8

    .line 92
    :cond_c
    move-object/from16 v0, p0

    iget-object v2, v0, Lorg/achartengine/tools/Pan;->mChart:Lorg/achartengine/chart/AbstractChart;

    check-cast v2, Lorg/achartengine/chart/RoundChart;

    .line 93
    invoke-virtual {v2}, Lorg/achartengine/chart/RoundChart;->getCenterX()I

    move-result v3

    sub-float v4, p3, p1

    float-to-int v4, v4

    add-int/2addr v3, v4

    invoke-virtual {v2, v3}, Lorg/achartengine/chart/RoundChart;->setCenterX(I)V

    .line 94
    invoke-virtual {v2}, Lorg/achartengine/chart/RoundChart;->getCenterY()I

    move-result v3

    sub-float v4, p4, p2

    float-to-int v4, v4

    add-int/2addr v3, v4

    invoke-virtual {v2, v3}, Lorg/achartengine/chart/RoundChart;->setCenterY(I)V

    .line 96
    :cond_d
    invoke-direct/range {p0 .. p0}, Lorg/achartengine/tools/Pan;->notifyPanListeners()V

    goto/16 :goto_2
.end method

.method public declared-synchronized removePanListener(Lorg/achartengine/tools/PanListener;)V
    .locals 1
    .parameter

    .prologue
    .line 123
    monitor-enter p0

    :try_start_0
    iget-object v0, p0, Lorg/achartengine/tools/Pan;->mPanListeners:Ljava/util/List;

    invoke-interface {v0, p1}, Ljava/util/List;->add(Ljava/lang/Object;)Z
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 124
    monitor-exit p0

    return-void

    .line 123
    :catchall_0
    move-exception v0

    monitor-exit p0

    throw v0
.end method
