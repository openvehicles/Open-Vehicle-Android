.class public Lcom/openvehicles/OVMS/TabInfo;
.super Landroid/app/Activity;
.source "TabInfo.java"


# instance fields
.field public CurrentScreenOrientation:I

.field private data:Lcom/openvehicles/OVMS/CarData;

.field private downloadProgress:Landroid/app/ProgressDialog;

.field private downloadTask:Lcom/openvehicles/OVMS/ServerCommands$CarLayoutDownloader;

.field private handler:Landroid/os/Handler;

.field private isLoggedIn:Z

.field private lastUpdateTimer:Ljava/lang/Runnable;

.field private lastUpdateTimerHandler:Landroid/os/Handler;

.field private lastUpdatedDialog:Landroid/app/AlertDialog;

.field private orientationChangedHandler:Landroid/os/Handler;

.field private softwareInformation:Landroid/app/AlertDialog;


# direct methods
.method public constructor <init>()V
    .locals 1

    .prologue
    .line 38
    invoke-direct {p0}, Landroid/app/Activity;-><init>()V

    .line 201
    new-instance v0, Landroid/os/Handler;

    invoke-direct {v0}, Landroid/os/Handler;-><init>()V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->lastUpdateTimerHandler:Landroid/os/Handler;

    .line 202
    new-instance v0, Lcom/openvehicles/OVMS/TabInfo$1;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/TabInfo$1;-><init>(Lcom/openvehicles/OVMS/TabInfo;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->lastUpdateTimer:Ljava/lang/Runnable;

    .line 314
    new-instance v0, Lcom/openvehicles/OVMS/TabInfo$2;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/TabInfo$2;-><init>(Lcom/openvehicles/OVMS/TabInfo;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->handler:Landroid/os/Handler;

    .line 556
    new-instance v0, Lcom/openvehicles/OVMS/TabInfo$3;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/TabInfo$3;-><init>(Lcom/openvehicles/OVMS/TabInfo;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->orientationChangedHandler:Landroid/os/Handler;

    .line 38
    return-void
.end method

.method static synthetic access$0(Lcom/openvehicles/OVMS/TabInfo;)V
    .locals 0
    .parameter

    .prologue
    .line 247
    invoke-direct {p0}, Lcom/openvehicles/OVMS/TabInfo;->updateLastUpdatedView()V

    return-void
.end method

.method static synthetic access$1(Lcom/openvehicles/OVMS/TabInfo;)Landroid/os/Handler;
    .locals 1
    .parameter

    .prologue
    .line 201
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->lastUpdateTimerHandler:Landroid/os/Handler;

    return-object v0
.end method

.method static synthetic access$10(Lcom/openvehicles/OVMS/TabInfo;)V
    .locals 0
    .parameter

    .prologue
    .line 50
    invoke-direct {p0}, Lcom/openvehicles/OVMS/TabInfo;->initUI()V

    return-void
.end method

.method static synthetic access$2(Lcom/openvehicles/OVMS/TabInfo;)Ljava/lang/Runnable;
    .locals 1
    .parameter

    .prologue
    .line 202
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->lastUpdateTimer:Ljava/lang/Runnable;

    return-object v0
.end method

.method static synthetic access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;
    .locals 1
    .parameter

    .prologue
    .line 192
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;

    return-object v0
.end method

.method static synthetic access$4(Lcom/openvehicles/OVMS/TabInfo;)Z
    .locals 1
    .parameter

    .prologue
    .line 193
    iget-boolean v0, p0, Lcom/openvehicles/OVMS/TabInfo;->isLoggedIn:Z

    return v0
.end method

.method static synthetic access$5(Lcom/openvehicles/OVMS/TabInfo;)Landroid/app/AlertDialog;
    .locals 1
    .parameter

    .prologue
    .line 195
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->lastUpdatedDialog:Landroid/app/AlertDialog;

    return-object v0
.end method

.method static synthetic access$6(Lcom/openvehicles/OVMS/TabInfo;)V
    .locals 0
    .parameter

    .prologue
    .line 515
    invoke-direct {p0}, Lcom/openvehicles/OVMS/TabInfo;->downloadLayout()V

    return-void
.end method

.method static synthetic access$7(Lcom/openvehicles/OVMS/TabInfo;Landroid/app/AlertDialog;)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 195
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabInfo;->lastUpdatedDialog:Landroid/app/AlertDialog;

    return-void
.end method

.method static synthetic access$8(Lcom/openvehicles/OVMS/TabInfo;Landroid/app/AlertDialog;)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 194
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabInfo;->softwareInformation:Landroid/app/AlertDialog;

    return-void
.end method

.method static synthetic access$9(Lcom/openvehicles/OVMS/TabInfo;)Landroid/app/AlertDialog;
    .locals 1
    .parameter

    .prologue
    .line 194
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->softwareInformation:Landroid/app/AlertDialog;

    return-object v0
.end method

.method private downloadLayout()V
    .locals 5

    .prologue
    const/4 v4, 0x1

    .line 516
    new-instance v0, Landroid/app/ProgressDialog;

    invoke-direct {v0, p0}, Landroid/app/ProgressDialog;-><init>(Landroid/content/Context;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->downloadProgress:Landroid/app/ProgressDialog;

    .line 517
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->downloadProgress:Landroid/app/ProgressDialog;

    const-string v1, "Downloading Hi-Res Graphics"

    invoke-virtual {v0, v1}, Landroid/app/ProgressDialog;->setMessage(Ljava/lang/CharSequence;)V

    .line 518
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->downloadProgress:Landroid/app/ProgressDialog;

    invoke-virtual {v0, v4}, Landroid/app/ProgressDialog;->setIndeterminate(Z)V

    .line 519
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->downloadProgress:Landroid/app/ProgressDialog;

    const/16 v1, 0x64

    invoke-virtual {v0, v1}, Landroid/app/ProgressDialog;->setMax(I)V

    .line 520
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->downloadProgress:Landroid/app/ProgressDialog;

    invoke-virtual {v0, v4}, Landroid/app/ProgressDialog;->setCancelable(Z)V

    .line 521
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->downloadProgress:Landroid/app/ProgressDialog;

    invoke-virtual {v0, v4}, Landroid/app/ProgressDialog;->setProgressStyle(I)V

    .line 522
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->downloadProgress:Landroid/app/ProgressDialog;

    invoke-virtual {v0}, Landroid/app/ProgressDialog;->show()V

    .line 524
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->downloadProgress:Landroid/app/ProgressDialog;

    new-instance v1, Lcom/openvehicles/OVMS/TabInfo$9;

    invoke-direct {v1, p0}, Lcom/openvehicles/OVMS/TabInfo$9;-><init>(Lcom/openvehicles/OVMS/TabInfo;)V

    invoke-virtual {v0, v1}, Landroid/app/ProgressDialog;->setOnDismissListener(Landroid/content/DialogInterface$OnDismissListener;)V

    .line 541
    new-instance v0, Lcom/openvehicles/OVMS/ServerCommands$CarLayoutDownloader;

    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo;->downloadProgress:Landroid/app/ProgressDialog;

    invoke-direct {v0, v1}, Lcom/openvehicles/OVMS/ServerCommands$CarLayoutDownloader;-><init>(Landroid/app/ProgressDialog;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->downloadTask:Lcom/openvehicles/OVMS/ServerCommands$CarLayoutDownloader;

    .line 542
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->downloadTask:Lcom/openvehicles/OVMS/ServerCommands$CarLayoutDownloader;

    const/4 v1, 0x2

    new-array v1, v1, [Ljava/lang/String;

    const/4 v2, 0x0

    iget-object v3, p0, Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v3, v3, Lcom/openvehicles/OVMS/CarData;->VehicleImageDrawable:Ljava/lang/String;

    aput-object v3, v1, v2

    .line 543
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TabInfo;->getCacheDir()Ljava/io/File;

    move-result-object v2

    invoke-virtual {v2}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v2

    aput-object v2, v1, v4

    .line 542
    invoke-virtual {v0, v1}, Lcom/openvehicles/OVMS/ServerCommands$CarLayoutDownloader;->execute([Ljava/lang/Object;)Landroid/os/AsyncTask;

    .line 544
    return-void
.end method

.method private initUI()V
    .locals 4

    .prologue
    .line 53
    const-string v1, "-"

    .line 58
    .local v1, lastReportDate:Ljava/lang/String;
    const v3, 0x7f090043

    invoke-virtual {p0, v3}, Lcom/openvehicles/OVMS/TabInfo;->findViewById(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/widget/TextView;

    .line 59
    .local v2, tv:Landroid/widget/TextView;
    new-instance v3, Lcom/openvehicles/OVMS/TabInfo$4;

    invoke-direct {v3, p0}, Lcom/openvehicles/OVMS/TabInfo$4;-><init>(Lcom/openvehicles/OVMS/TabInfo;)V

    invoke-virtual {v2, v3}, Landroid/widget/TextView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 86
    const v3, 0x7f090040

    invoke-virtual {p0, v3}, Lcom/openvehicles/OVMS/TabInfo;->findViewById(I)Landroid/view/View;

    move-result-object v2

    .end local v2           #tv:Landroid/widget/TextView;
    check-cast v2, Landroid/widget/TextView;

    .line 87
    .restart local v2       #tv:Landroid/widget/TextView;
    new-instance v3, Lcom/openvehicles/OVMS/TabInfo$5;

    invoke-direct {v3, p0}, Lcom/openvehicles/OVMS/TabInfo$5;-><init>(Lcom/openvehicles/OVMS/TabInfo;)V

    invoke-virtual {v2, v3}, Landroid/widget/TextView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 114
    const v3, 0x7f090049

    invoke-virtual {p0, v3}, Lcom/openvehicles/OVMS/TabInfo;->findViewById(I)Landroid/view/View;

    move-result-object v2

    .end local v2           #tv:Landroid/widget/TextView;
    check-cast v2, Landroid/widget/TextView;

    .line 115
    .restart local v2       #tv:Landroid/widget/TextView;
    new-instance v3, Lcom/openvehicles/OVMS/TabInfo$6;

    invoke-direct {v3, p0}, Lcom/openvehicles/OVMS/TabInfo$6;-><init>(Lcom/openvehicles/OVMS/TabInfo;)V

    invoke-virtual {v2, v3}, Landroid/widget/TextView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 124
    const v3, 0x7f09004e

    invoke-virtual {p0, v3}, Lcom/openvehicles/OVMS/TabInfo;->findViewById(I)Landroid/view/View;

    move-result-object v2

    .end local v2           #tv:Landroid/widget/TextView;
    check-cast v2, Landroid/widget/TextView;

    .line 125
    .restart local v2       #tv:Landroid/widget/TextView;
    new-instance v3, Lcom/openvehicles/OVMS/TabInfo$7;

    invoke-direct {v3, p0}, Lcom/openvehicles/OVMS/TabInfo$7;-><init>(Lcom/openvehicles/OVMS/TabInfo;)V

    invoke-virtual {v2, v3}, Landroid/widget/TextView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 134
    const v3, 0x7f09004a

    invoke-virtual {p0, v3}, Lcom/openvehicles/OVMS/TabInfo;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/SeekBar;

    .line 135
    .local v0, bar:Landroid/widget/SeekBar;
    new-instance v3, Lcom/openvehicles/OVMS/TabInfo$8;

    invoke-direct {v3, p0}, Lcom/openvehicles/OVMS/TabInfo$8;-><init>(Lcom/openvehicles/OVMS/TabInfo;)V

    invoke-virtual {v0, v3}, Landroid/widget/SeekBar;->setOnSeekBarChangeListener(Landroid/widget/SeekBar$OnSeekBarChangeListener;)V

    .line 189
    return-void
.end method

.method private updateLastUpdatedView()V
    .locals 17

    .prologue
    .line 248
    move-object/from16 v0, p0

    iget-object v11, v0, Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;

    if-eqz v11, :cond_0

    move-object/from16 v0, p0

    iget-object v11, v0, Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v11, v11, Lcom/openvehicles/OVMS/CarData;->Data_LastCarUpdate:Ljava/util/Date;

    if-nez v11, :cond_1

    .line 312
    :cond_0
    :goto_0
    return-void

    .line 251
    :cond_1
    const v11, 0x7f090043

    move-object/from16 v0, p0

    invoke-virtual {v0, v11}, Lcom/openvehicles/OVMS/TabInfo;->findViewById(I)Landroid/view/View;

    move-result-object v10

    check-cast v10, Landroid/widget/TextView;

    .line 252
    .local v10, tv:Landroid/widget/TextView;
    new-instance v5, Ljava/util/Date;

    invoke-direct {v5}, Ljava/util/Date;-><init>()V

    .line 253
    .local v5, now:Ljava/util/Date;
    invoke-virtual {v5}, Ljava/util/Date;->getTime()J

    move-result-wide v11

    move-object/from16 v0, p0

    iget-object v13, v0, Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v13, v13, Lcom/openvehicles/OVMS/CarData;->Data_LastCarUpdate:Ljava/util/Date;

    .line 254
    invoke-virtual {v13}, Ljava/util/Date;->getTime()J

    move-result-wide v13

    .line 253
    sub-long/2addr v11, v13

    .line 254
    const-wide/16 v13, 0x3e8

    .line 253
    div-long v3, v11, v13

    .line 256
    .local v3, lastUpdateSecondsAgo:J
    const-wide/16 v11, 0x3c

    cmp-long v11, v3, v11

    if-gez v11, :cond_2

    .line 257
    const-string v11, "live"

    invoke-virtual {v10, v11}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 274
    :goto_1
    const v11, 0x7f090040

    move-object/from16 v0, p0

    invoke-virtual {v0, v11}, Lcom/openvehicles/OVMS/TabInfo;->findViewById(I)Landroid/view/View;

    move-result-object v10

    .end local v10           #tv:Landroid/widget/TextView;
    check-cast v10, Landroid/widget/TextView;

    .line 275
    .restart local v10       #tv:Landroid/widget/TextView;
    const v11, 0x7f09003e

    move-object/from16 v0, p0

    invoke-virtual {v0, v11}, Lcom/openvehicles/OVMS/TabInfo;->findViewById(I)Landroid/view/View;

    move-result-object v7

    check-cast v7, Landroid/widget/LinearLayout;

    .line 276
    .local v7, parkingRow:Landroid/widget/LinearLayout;
    move-object/from16 v0, p0

    iget-object v11, v0, Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v11, v11, Lcom/openvehicles/OVMS/CarData;->Data_CarPoweredON:Z

    if-nez v11, :cond_12

    move-object/from16 v0, p0

    iget-object v11, v0, Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;

    iget-wide v11, v11, Lcom/openvehicles/OVMS/CarData;->Data_ParkedTime_raw:D

    const-wide/16 v13, 0x0

    cmpl-double v11, v11, v13

    if-eqz v11, :cond_12

    .line 277
    const-string v6, ""

    .line 278
    .local v6, parkedTimeString:Ljava/lang/String;
    move-object/from16 v0, p0

    iget-object v11, v0, Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;

    iget-wide v11, v11, Lcom/openvehicles/OVMS/CarData;->Data_ParkedTime_raw:D

    double-to-long v11, v11

    add-long v8, v11, v3

    .line 281
    .local v8, timeElapsed:J
    move-object/from16 v0, p0

    iget-object v11, v0, Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;

    new-instance v12, Ljava/util/Date;

    new-instance v13, Ljava/util/Date;

    invoke-direct {v13}, Ljava/util/Date;-><init>()V

    invoke-virtual {v13}, Ljava/util/Date;->getTime()J

    move-result-wide v13

    const-wide/16 v15, 0x3e8

    mul-long/2addr v15, v8

    sub-long/2addr v13, v15

    invoke-direct {v12, v13, v14}, Ljava/util/Date;-><init>(J)V

    iput-object v12, v11, Lcom/openvehicles/OVMS/CarData;->Data_ParkedTime:Ljava/util/Date;

    .line 283
    const-wide/16 v11, 0x3c

    cmp-long v11, v8, v11

    if-gez v11, :cond_9

    .line 286
    const-string v11, "just now"

    invoke-virtual {v10, v11}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 308
    :goto_2
    const/4 v11, 0x0

    invoke-virtual {v7, v11}, Landroid/widget/LinearLayout;->setVisibility(I)V

    goto/16 :goto_0

    .line 258
    .end local v6           #parkedTimeString:Ljava/lang/String;
    .end local v7           #parkingRow:Landroid/widget/LinearLayout;
    .end local v8           #timeElapsed:J
    :cond_2
    const-wide/16 v11, 0xe10

    cmp-long v11, v3, v11

    if-gez v11, :cond_4

    .line 259
    const-wide/16 v11, 0x3c

    div-long v11, v3, v11

    long-to-double v11, v11

    invoke-static {v11, v12}, Ljava/lang/Math;->ceil(D)D

    move-result-wide v11

    double-to-int v1, v11

    .line 260
    .local v1, displayValue:I
    const-string v12, "Updated: %d min%s ago"

    const/4 v11, 0x2

    new-array v13, v11, [Ljava/lang/Object;

    const/4 v11, 0x0

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v14

    aput-object v14, v13, v11

    const/4 v14, 0x1

    .line 261
    const/4 v11, 0x1

    if-le v1, v11, :cond_3

    const-string v11, "s"

    :goto_3
    aput-object v11, v13, v14

    .line 260
    invoke-static {v12, v13}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v10, v11}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_1

    .line 261
    :cond_3
    const-string v11, ""

    goto :goto_3

    .line 262
    .end local v1           #displayValue:I
    :cond_4
    const-wide/32 v11, 0x15180

    cmp-long v11, v3, v11

    if-gez v11, :cond_6

    .line 263
    const-wide/16 v11, 0xe10

    div-long v11, v3, v11

    long-to-double v11, v11

    invoke-static {v11, v12}, Ljava/lang/Math;->ceil(D)D

    move-result-wide v11

    double-to-int v1, v11

    .line 264
    .restart local v1       #displayValue:I
    const-string v12, "Updated: %d hr%s ago"

    const/4 v11, 0x2

    new-array v13, v11, [Ljava/lang/Object;

    const/4 v11, 0x0

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v14

    aput-object v14, v13, v11

    const/4 v14, 0x1

    .line 265
    const/4 v11, 0x1

    if-le v1, v11, :cond_5

    const-string v11, "s"

    :goto_4
    aput-object v11, v13, v14

    .line 264
    invoke-static {v12, v13}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v10, v11}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_1

    .line 265
    :cond_5
    const-string v11, ""

    goto :goto_4

    .line 266
    .end local v1           #displayValue:I
    :cond_6
    const-wide/32 v11, 0xd2f00

    cmp-long v11, v3, v11

    if-gez v11, :cond_8

    .line 267
    const-wide/32 v11, 0x15180

    div-long v11, v3, v11

    long-to-double v11, v11

    invoke-static {v11, v12}, Ljava/lang/Math;->ceil(D)D

    move-result-wide v11

    double-to-int v1, v11

    .line 268
    .restart local v1       #displayValue:I
    const-string v12, "Updated: %d day%s ago"

    const/4 v11, 0x2

    new-array v13, v11, [Ljava/lang/Object;

    const/4 v11, 0x0

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v14

    aput-object v14, v13, v11

    const/4 v14, 0x1

    .line 269
    const/4 v11, 0x1

    if-le v1, v11, :cond_7

    const-string v11, "s"

    :goto_5
    aput-object v11, v13, v14

    .line 268
    invoke-static {v12, v13}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v10, v11}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_1

    .line 269
    :cond_7
    const-string v11, ""

    goto :goto_5

    .line 271
    .end local v1           #displayValue:I
    :cond_8
    const v11, 0x7f060003

    move-object/from16 v0, p0

    invoke-virtual {v0, v11}, Lcom/openvehicles/OVMS/TabInfo;->getString(I)Ljava/lang/String;

    move-result-object v11

    const/4 v12, 0x1

    new-array v12, v12, [Ljava/lang/Object;

    const/4 v13, 0x0

    .line 272
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v14, v14, Lcom/openvehicles/OVMS/CarData;->Data_LastCarUpdate:Ljava/util/Date;

    aput-object v14, v12, v13

    .line 271
    invoke-static {v11, v12}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v10, v11}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_1

    .line 287
    .restart local v6       #parkedTimeString:Ljava/lang/String;
    .restart local v7       #parkingRow:Landroid/widget/LinearLayout;
    .restart local v8       #timeElapsed:J
    :cond_9
    const-wide/16 v11, 0xe10

    cmp-long v11, v8, v11

    if-gez v11, :cond_b

    .line 288
    const-wide/16 v11, 0x3c

    div-long v11, v8, v11

    long-to-double v11, v11

    invoke-static {v11, v12}, Ljava/lang/Math;->ceil(D)D

    move-result-wide v11

    double-to-int v1, v11

    .line 289
    .restart local v1       #displayValue:I
    const-string v12, "%d min%s"

    const/4 v11, 0x2

    new-array v13, v11, [Ljava/lang/Object;

    const/4 v11, 0x0

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v14

    aput-object v14, v13, v11

    const/4 v14, 0x1

    .line 290
    const/4 v11, 0x1

    if-le v1, v11, :cond_a

    const-string v11, "s"

    :goto_6
    aput-object v11, v13, v14

    .line 289
    invoke-static {v12, v13}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v10, v11}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_2

    .line 290
    :cond_a
    const-string v11, ""

    goto :goto_6

    .line 291
    .end local v1           #displayValue:I
    :cond_b
    const-wide/32 v11, 0x15180

    cmp-long v11, v8, v11

    if-gez v11, :cond_e

    .line 292
    const-wide/16 v11, 0xe10

    div-long v11, v8, v11

    long-to-double v11, v11

    invoke-static {v11, v12}, Ljava/lang/Math;->floor(D)D

    move-result-wide v11

    double-to-int v1, v11

    .line 294
    .restart local v1       #displayValue:I
    mul-int/lit16 v11, v1, 0xe10

    int-to-long v11, v11

    sub-long v11, v8, v11

    const-wide/16 v13, 0x3c

    div-long/2addr v11, v13

    invoke-static {v11, v12}, Ljava/lang/Math;->abs(J)J

    move-result-wide v11

    long-to-double v11, v11

    .line 293
    invoke-static {v11, v12}, Ljava/lang/Math;->ceil(D)D

    move-result-wide v11

    double-to-int v2, v11

    .line 295
    .local v2, displayValue2:I
    const-string v12, "%d hr%s %d min%s"

    const/4 v11, 0x4

    new-array v13, v11, [Ljava/lang/Object;

    const/4 v11, 0x0

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v14

    aput-object v14, v13, v11

    const/4 v14, 0x1

    .line 296
    const/4 v11, 0x1

    if-le v1, v11, :cond_c

    const-string v11, "s"

    :goto_7
    aput-object v11, v13, v14

    const/4 v11, 0x2

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v14

    aput-object v14, v13, v11

    const/4 v14, 0x3

    .line 297
    const/4 v11, 0x1

    if-le v2, v11, :cond_d

    const-string v11, "s"

    :goto_8
    aput-object v11, v13, v14

    .line 295
    invoke-static {v12, v13}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v10, v11}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_2

    .line 296
    :cond_c
    const-string v11, ""

    goto :goto_7

    .line 297
    :cond_d
    const-string v11, ""

    goto :goto_8

    .line 298
    .end local v1           #displayValue:I
    .end local v2           #displayValue2:I
    :cond_e
    const-wide/32 v11, 0xd2f00

    cmp-long v11, v8, v11

    if-gez v11, :cond_11

    .line 299
    const-wide/32 v11, 0x15180

    div-long v11, v8, v11

    long-to-double v11, v11

    invoke-static {v11, v12}, Ljava/lang/Math;->floor(D)D

    move-result-wide v11

    double-to-int v1, v11

    .line 301
    .restart local v1       #displayValue:I
    const v11, 0x15180

    mul-int/2addr v11, v1

    int-to-long v11, v11

    sub-long v11, v8, v11

    const-wide/16 v13, 0xe10

    div-long/2addr v11, v13

    invoke-static {v11, v12}, Ljava/lang/Math;->abs(J)J

    move-result-wide v11

    long-to-double v11, v11

    .line 300
    invoke-static {v11, v12}, Ljava/lang/Math;->ceil(D)D

    move-result-wide v11

    double-to-int v2, v11

    .line 302
    .restart local v2       #displayValue2:I
    const-string v12, "%d day%s %d hr%s"

    const/4 v11, 0x4

    new-array v13, v11, [Ljava/lang/Object;

    const/4 v11, 0x0

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v14

    aput-object v14, v13, v11

    const/4 v14, 0x1

    .line 303
    const/4 v11, 0x1

    if-le v1, v11, :cond_f

    const-string v11, "s"

    :goto_9
    aput-object v11, v13, v14

    const/4 v11, 0x2

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v14

    aput-object v14, v13, v11

    const/4 v14, 0x3

    .line 304
    const/4 v11, 0x1

    if-le v2, v11, :cond_10

    const-string v11, "s"

    :goto_a
    aput-object v11, v13, v14

    .line 302
    invoke-static {v12, v13}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v10, v11}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_2

    .line 303
    :cond_f
    const-string v11, ""

    goto :goto_9

    .line 304
    :cond_10
    const-string v11, ""

    goto :goto_a

    .line 306
    .end local v1           #displayValue:I
    .end local v2           #displayValue2:I
    :cond_11
    const-string v11, "%1$tD %1$tT"

    const/4 v12, 0x1

    new-array v12, v12, [Ljava/lang/Object;

    const/4 v13, 0x0

    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v14, v14, Lcom/openvehicles/OVMS/CarData;->Data_ParkedTime:Ljava/util/Date;

    aput-object v14, v12, v13

    invoke-static {v11, v12}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v10, v11}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_2

    .line 310
    .end local v6           #parkedTimeString:Ljava/lang/String;
    .end local v8           #timeElapsed:J
    :cond_12
    const/16 v11, 0x8

    invoke-virtual {v7, v11}, Landroid/widget/LinearLayout;->setVisibility(I)V

    goto/16 :goto_0
.end method


# virtual methods
.method public OrientationChanged()V
    .locals 2

    .prologue
    .line 553
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->orientationChangedHandler:Landroid/os/Handler;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    .line 554
    return-void
.end method

.method public Refresh(Lcom/openvehicles/OVMS/CarData;Z)V
    .locals 2
    .parameter "carData"
    .parameter "isLoggedIn"

    .prologue
    .line 547
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;

    .line 548
    iput-boolean p2, p0, Lcom/openvehicles/OVMS/TabInfo;->isLoggedIn:Z

    .line 549
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->handler:Landroid/os/Handler;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    .line 550
    return-void
.end method

.method public onCreate(Landroid/os/Bundle;)V
    .locals 1
    .parameter "savedInstanceState"

    .prologue
    .line 43
    invoke-super {p0, p1}, Landroid/app/Activity;->onCreate(Landroid/os/Bundle;)V

    .line 44
    const v0, 0x7f03000e

    invoke-virtual {p0, v0}, Lcom/openvehicles/OVMS/TabInfo;->setContentView(I)V

    .line 46
    invoke-direct {p0}, Lcom/openvehicles/OVMS/TabInfo;->initUI()V

    .line 48
    return-void
.end method

.method protected onPause()V
    .locals 2

    .prologue
    .line 225
    invoke-super {p0}, Landroid/app/Activity;->onPause()V

    .line 231
    :try_start_0
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->softwareInformation:Landroid/app/AlertDialog;

    if-eqz v0, :cond_0

    .line 232
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->softwareInformation:Landroid/app/AlertDialog;

    invoke-virtual {v0}, Landroid/app/AlertDialog;->isShowing()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 233
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->softwareInformation:Landroid/app/AlertDialog;

    invoke-virtual {v0}, Landroid/app/AlertDialog;->dismiss()V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_1

    .line 238
    :cond_0
    :goto_0
    :try_start_1
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->lastUpdatedDialog:Landroid/app/AlertDialog;

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->lastUpdatedDialog:Landroid/app/AlertDialog;

    invoke-virtual {v0}, Landroid/app/AlertDialog;->isShowing()Z

    move-result v0

    if-eqz v0, :cond_1

    .line 239
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->lastUpdatedDialog:Landroid/app/AlertDialog;

    invoke-virtual {v0}, Landroid/app/AlertDialog;->dismiss()V
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_0

    .line 244
    :cond_1
    :goto_1
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->lastUpdateTimerHandler:Landroid/os/Handler;

    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo;->lastUpdateTimer:Ljava/lang/Runnable;

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeCallbacks(Ljava/lang/Runnable;)V

    .line 245
    return-void

    .line 240
    :catch_0
    move-exception v0

    goto :goto_1

    .line 234
    :catch_1
    move-exception v0

    goto :goto_0
.end method

.method protected onResume()V
    .locals 4

    .prologue
    .line 214
    invoke-super {p0}, Landroid/app/Activity;->onResume()V

    .line 220
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->lastUpdateTimerHandler:Landroid/os/Handler;

    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo;->lastUpdateTimer:Ljava/lang/Runnable;

    const-wide/16 v2, 0x1388

    invoke-virtual {v0, v1, v2, v3}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    .line 221
    return-void
.end method
