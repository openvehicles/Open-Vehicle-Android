.class public Lcom/openvehicles/OVMS/GPRSUtilizationData;
.super Ljava/lang/Object;
.source "GPRSUtilizationData.java"

# interfaces
.implements Ljava/io/Serializable;


# static fields
.field private static final serialVersionUID:J = 0xfd0aecd74db1108L


# instance fields
.field public App_rx:J

.field public App_tx:J

.field public Car_rx:J

.field public Car_tx:J

.field public DataDate:Ljava/util/Date;


# direct methods
.method public constructor <init>()V
    .locals 3

    .prologue
    const-wide/16 v1, 0x0

    .line 17
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 18
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/openvehicles/OVMS/GPRSUtilizationData;->DataDate:Ljava/util/Date;

    .line 19
    iput-wide v1, p0, Lcom/openvehicles/OVMS/GPRSUtilizationData;->Car_tx:J

    .line 20
    iput-wide v1, p0, Lcom/openvehicles/OVMS/GPRSUtilizationData;->Car_rx:J

    .line 21
    iput-wide v1, p0, Lcom/openvehicles/OVMS/GPRSUtilizationData;->App_tx:J

    .line 22
    iput-wide v1, p0, Lcom/openvehicles/OVMS/GPRSUtilizationData;->App_rx:J

    .line 23
    return-void
.end method

.method public constructor <init>(Ljava/util/Date;JJJJ)V
    .locals 0
    .parameter "date"
    .parameter "car_rx"
    .parameter "car_tx"
    .parameter "app_rx"
    .parameter "app_tx"

    .prologue
    .line 25
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 26
    iput-object p1, p0, Lcom/openvehicles/OVMS/GPRSUtilizationData;->DataDate:Ljava/util/Date;

    .line 27
    iput-wide p4, p0, Lcom/openvehicles/OVMS/GPRSUtilizationData;->Car_tx:J

    .line 28
    iput-wide p2, p0, Lcom/openvehicles/OVMS/GPRSUtilizationData;->Car_rx:J

    .line 29
    iput-wide p8, p0, Lcom/openvehicles/OVMS/GPRSUtilizationData;->App_tx:J

    .line 30
    iput-wide p6, p0, Lcom/openvehicles/OVMS/GPRSUtilizationData;->App_rx:J

    .line 31
    return-void
.end method
