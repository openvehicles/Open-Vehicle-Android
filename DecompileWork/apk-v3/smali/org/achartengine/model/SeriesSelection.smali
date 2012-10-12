.class public Lorg/achartengine/model/SeriesSelection;
.super Ljava/lang/Object;
.source "SeriesSelection.java"


# instance fields
.field private mPointIndex:I

.field private mSeriesIndex:I

.field private mValue:D

.field private mXValue:D


# direct methods
.method public constructor <init>(IIDD)V
    .locals 0
    .parameter
    .parameter
    .parameter
    .parameter

    .prologue
    .line 27
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 28
    iput p1, p0, Lorg/achartengine/model/SeriesSelection;->mSeriesIndex:I

    .line 29
    iput p2, p0, Lorg/achartengine/model/SeriesSelection;->mPointIndex:I

    .line 30
    iput-wide p3, p0, Lorg/achartengine/model/SeriesSelection;->mXValue:D

    .line 31
    iput-wide p5, p0, Lorg/achartengine/model/SeriesSelection;->mValue:D

    .line 32
    return-void
.end method


# virtual methods
.method public getPointIndex()I
    .locals 1

    .prologue
    .line 39
    iget v0, p0, Lorg/achartengine/model/SeriesSelection;->mPointIndex:I

    return v0
.end method

.method public getSeriesIndex()I
    .locals 1

    .prologue
    .line 35
    iget v0, p0, Lorg/achartengine/model/SeriesSelection;->mSeriesIndex:I

    return v0
.end method

.method public getValue()D
    .locals 2

    .prologue
    .line 47
    iget-wide v0, p0, Lorg/achartengine/model/SeriesSelection;->mValue:D

    return-wide v0
.end method

.method public getXValue()D
    .locals 2

    .prologue
    .line 43
    iget-wide v0, p0, Lorg/achartengine/model/SeriesSelection;->mXValue:D

    return-wide v0
.end method
