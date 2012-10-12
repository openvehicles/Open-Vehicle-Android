.class public Lcom/openvehicles/OVMS/GPRSUtilization;
.super Ljava/lang/Object;
.source "GPRSUtilization.java"

# interfaces
.implements Ljava/io/Serializable;


# static fields
.field public static final transient FLAG_APP_RX:I = 0x4

.field public static final transient FLAG_APP_TX:I = 0x8

.field public static final transient FLAG_CAR_RX:I = 0x1

.field public static final transient FLAG_CAR_TX:I = 0x2

.field private static final serialVersionUID:J = 0x2e75127a651bbc51L


# instance fields
.field public LastDataRefresh:Ljava/util/Date;

.field public Utilizations:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList",
            "<",
            "Lcom/openvehicles/OVMS/GPRSUtilizationData;",
            ">;"
        }
    .end annotation
.end field

.field private transient mContext:Landroid/content/Context;

.field private final settingsFileName:Ljava/lang/String;


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .locals 8
    .parameter "context"

    .prologue
    .line 33
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 20
    const-string v3, "OVMSSavedGPRSUtilization.obj"

    iput-object v3, p0, Lcom/openvehicles/OVMS/GPRSUtilization;->settingsFileName:Ljava/lang/String;

    .line 22
    const/4 v3, 0x0

    iput-object v3, p0, Lcom/openvehicles/OVMS/GPRSUtilization;->LastDataRefresh:Ljava/util/Date;

    .line 34
    iput-object p1, p0, Lcom/openvehicles/OVMS/GPRSUtilization;->mContext:Landroid/content/Context;

    .line 36
    :try_start_0
    const-string v3, "OVMS"

    .line 37
    const-string v4, "Loading saved GPRS utilizations from internal storage file: OVMSSavedGPRSUtilization.obj"

    .line 36
    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 39
    const-string v3, "OVMSSavedGPRSUtilization.obj"

    invoke-virtual {p1, v3}, Landroid/content/Context;->openFileInput(Ljava/lang/String;)Ljava/io/FileInputStream;

    move-result-object v1

    .line 40
    .local v1, fis:Ljava/io/FileInputStream;
    new-instance v2, Ljava/io/ObjectInputStream;

    invoke-direct {v2, v1}, Ljava/io/ObjectInputStream;-><init>(Ljava/io/InputStream;)V

    .line 41
    .local v2, is:Ljava/io/ObjectInputStream;
    invoke-virtual {v2}, Ljava/io/ObjectInputStream;->readObject()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/util/ArrayList;

    iput-object v3, p0, Lcom/openvehicles/OVMS/GPRSUtilization;->Utilizations:Ljava/util/ArrayList;

    .line 42
    invoke-virtual {v2}, Ljava/io/ObjectInputStream;->close()V

    .line 43
    const-string v3, "OVMS"

    .line 44
    const-string v4, "Loaded %s saved utilizations."

    const/4 v5, 0x1

    new-array v5, v5, [Ljava/lang/Object;

    const/4 v6, 0x0

    .line 45
    iget-object v7, p0, Lcom/openvehicles/OVMS/GPRSUtilization;->Utilizations:Ljava/util/ArrayList;

    invoke-virtual {v7}, Ljava/util/ArrayList;->size()I

    move-result v7

    invoke-static {v7}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    aput-object v7, v5, v6

    .line 44
    invoke-static {v4, v5}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v4

    .line 43
    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 54
    .end local v1           #fis:Ljava/io/FileInputStream;
    .end local v2           #is:Ljava/io/ObjectInputStream;
    :goto_0
    return-void

    .line 46
    :catch_0
    move-exception v0

    .line 48
    .local v0, e:Ljava/lang/Exception;
    const-string v3, "ERR"

    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 50
    const-string v3, "OVMS"

    const-string v4, "Initializing with utilization data."

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 51
    new-instance v3, Ljava/util/ArrayList;

    invoke-direct {v3}, Ljava/util/ArrayList;-><init>()V

    iput-object v3, p0, Lcom/openvehicles/OVMS/GPRSUtilization;->Utilizations:Ljava/util/ArrayList;

    .line 52
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/GPRSUtilization;->Save()V

    goto :goto_0
.end method


# virtual methods
.method public AddData(Lcom/openvehicles/OVMS/GPRSUtilizationData;)V
    .locals 1
    .parameter "util"

    .prologue
    .line 58
    iget-object v0, p0, Lcom/openvehicles/OVMS/GPRSUtilization;->Utilizations:Ljava/util/ArrayList;

    invoke-virtual {v0, p1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 73
    return-void
.end method

.method public AddData(Ljava/util/Date;JJJJ)V
    .locals 10
    .parameter "date"
    .parameter "car_rx"
    .parameter "car_tx"
    .parameter "app_rx"
    .parameter "app_tx"

    .prologue
    .line 77
    new-instance v0, Lcom/openvehicles/OVMS/GPRSUtilizationData;

    move-object v1, p1

    move-wide v2, p2

    move-wide v4, p4

    move-wide/from16 v6, p6

    move-wide/from16 v8, p8

    invoke-direct/range {v0 .. v9}, Lcom/openvehicles/OVMS/GPRSUtilizationData;-><init>(Ljava/util/Date;JJJJ)V

    invoke-virtual {p0, v0}, Lcom/openvehicles/OVMS/GPRSUtilization;->AddData(Lcom/openvehicles/OVMS/GPRSUtilizationData;)V

    .line 78
    return-void
.end method

.method public Clear()V
    .locals 1

    .prologue
    .line 81
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/openvehicles/OVMS/GPRSUtilization;->Utilizations:Ljava/util/ArrayList;

    .line 82
    return-void
.end method

.method public GetUtilizationBytes(Ljava/util/Date;I)J
    .locals 6
    .parameter "since"
    .parameter "flags"

    .prologue
    .line 90
    const-wide/16 v0, 0x0

    .line 91
    .local v0, bytesCount:J
    const/4 v2, 0x0

    .local v2, idx:I
    :goto_0
    iget-object v4, p0, Lcom/openvehicles/OVMS/GPRSUtilization;->Utilizations:Ljava/util/ArrayList;

    invoke-virtual {v4}, Ljava/util/ArrayList;->size()I

    move-result v4

    if-lt v2, v4, :cond_0

    .line 106
    return-wide v0

    .line 92
    :cond_0
    iget-object v4, p0, Lcom/openvehicles/OVMS/GPRSUtilization;->Utilizations:Ljava/util/ArrayList;

    invoke-virtual {v4, v2}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/openvehicles/OVMS/GPRSUtilizationData;

    .line 93
    .local v3, util:Lcom/openvehicles/OVMS/GPRSUtilizationData;
    iget-object v4, v3, Lcom/openvehicles/OVMS/GPRSUtilizationData;->DataDate:Ljava/util/Date;

    invoke-virtual {v4, p1}, Ljava/util/Date;->after(Ljava/util/Date;)Z

    move-result v4

    if-nez v4, :cond_1

    iget-object v4, v3, Lcom/openvehicles/OVMS/GPRSUtilizationData;->DataDate:Ljava/util/Date;

    invoke-virtual {v4, p1}, Ljava/util/Date;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_5

    .line 95
    :cond_1
    and-int/lit8 v4, p2, 0x1

    const/4 v5, 0x1

    if-ne v4, v5, :cond_2

    .line 96
    iget-wide v4, v3, Lcom/openvehicles/OVMS/GPRSUtilizationData;->Car_rx:J

    add-long/2addr v0, v4

    .line 97
    :cond_2
    and-int/lit8 v4, p2, 0x2

    const/4 v5, 0x2

    if-ne v4, v5, :cond_3

    .line 98
    iget-wide v4, v3, Lcom/openvehicles/OVMS/GPRSUtilizationData;->Car_tx:J

    add-long/2addr v0, v4

    .line 99
    :cond_3
    and-int/lit8 v4, p2, 0x4

    const/4 v5, 0x4

    if-ne v4, v5, :cond_4

    .line 100
    iget-wide v4, v3, Lcom/openvehicles/OVMS/GPRSUtilizationData;->App_rx:J

    add-long/2addr v0, v4

    .line 101
    :cond_4
    and-int/lit8 v4, p2, 0x8

    const/16 v5, 0x8

    if-ne v4, v5, :cond_5

    .line 102
    iget-wide v4, v3, Lcom/openvehicles/OVMS/GPRSUtilizationData;->App_tx:J

    add-long/2addr v0, v4

    .line 91
    :cond_5
    add-int/lit8 v2, v2, 0x1

    goto :goto_0
.end method

.method public Save()V
    .locals 8

    .prologue
    .line 111
    :try_start_0
    const-string v3, "OVMS"

    const-string v4, "Saving GPRS utilizations data to interal storage..."

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 113
    iget-object v3, p0, Lcom/openvehicles/OVMS/GPRSUtilization;->mContext:Landroid/content/Context;

    if-nez v3, :cond_0

    .line 114
    const-string v3, "OVMS"

    const-string v4, "Context == null. Saving aborted."

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 128
    :goto_0
    return-void

    .line 117
    :cond_0
    iget-object v3, p0, Lcom/openvehicles/OVMS/GPRSUtilization;->mContext:Landroid/content/Context;

    const-string v4, "OVMSSavedGPRSUtilization.obj"

    .line 118
    const/4 v5, 0x0

    .line 117
    invoke-virtual {v3, v4, v5}, Landroid/content/Context;->openFileOutput(Ljava/lang/String;I)Ljava/io/FileOutputStream;

    move-result-object v1

    .line 119
    .local v1, fos:Ljava/io/FileOutputStream;
    new-instance v2, Ljava/io/ObjectOutputStream;

    invoke-direct {v2, v1}, Ljava/io/ObjectOutputStream;-><init>(Ljava/io/OutputStream;)V

    .line 120
    .local v2, os:Ljava/io/ObjectOutputStream;
    iget-object v3, p0, Lcom/openvehicles/OVMS/GPRSUtilization;->Utilizations:Ljava/util/ArrayList;

    invoke-virtual {v2, v3}, Ljava/io/ObjectOutputStream;->writeObject(Ljava/lang/Object;)V

    .line 121
    invoke-virtual {v2}, Ljava/io/ObjectOutputStream;->close()V

    .line 122
    const-string v3, "OVMS"

    .line 123
    const-string v4, "Saved %s records."

    const/4 v5, 0x1

    new-array v5, v5, [Ljava/lang/Object;

    const/4 v6, 0x0

    iget-object v7, p0, Lcom/openvehicles/OVMS/GPRSUtilization;->Utilizations:Ljava/util/ArrayList;

    invoke-virtual {v7}, Ljava/util/ArrayList;->size()I

    move-result v7

    invoke-static {v7}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    aput-object v7, v5, v6

    invoke-static {v4, v5}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v4

    .line 122
    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    .line 124
    .end local v1           #fos:Ljava/io/FileOutputStream;
    .end local v2           #os:Ljava/io/ObjectOutputStream;
    :catch_0
    move-exception v0

    .line 125
    .local v0, e:Ljava/lang/Exception;
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    .line 126
    const-string v3, "ERR"

    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0
.end method

.method public Save(Landroid/content/Context;)V
    .locals 0
    .parameter "context"

    .prologue
    .line 85
    iput-object p1, p0, Lcom/openvehicles/OVMS/GPRSUtilization;->mContext:Landroid/content/Context;

    .line 86
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/GPRSUtilization;->Save()V

    .line 87
    return-void
.end method
