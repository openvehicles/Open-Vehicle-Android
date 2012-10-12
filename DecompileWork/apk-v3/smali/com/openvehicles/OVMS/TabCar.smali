.class public Lcom/openvehicles/OVMS/TabCar;
.super Landroid/app/Activity;
.source "TabCar.java"


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


# direct methods
.method public constructor <init>()V
    .locals 1

    .prologue
    .line 35
    invoke-direct {p0}, Landroid/app/Activity;-><init>()V

    .line 114
    new-instance v0, Landroid/os/Handler;

    invoke-direct {v0}, Landroid/os/Handler;-><init>()V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabCar;->lastUpdateTimerHandler:Landroid/os/Handler;

    .line 120
    new-instance v0, Lcom/openvehicles/OVMS/TabCar$1;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/TabCar$1;-><init>(Lcom/openvehicles/OVMS/TabCar;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabCar;->lastUpdateTimer:Ljava/lang/Runnable;

    .line 187
    new-instance v0, Lcom/openvehicles/OVMS/TabCar$2;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/TabCar$2;-><init>(Lcom/openvehicles/OVMS/TabCar;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabCar;->handler:Landroid/os/Handler;

    .line 411
    new-instance v0, Lcom/openvehicles/OVMS/TabCar$3;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/TabCar$3;-><init>(Lcom/openvehicles/OVMS/TabCar;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabCar;->orientationChangedHandler:Landroid/os/Handler;

    .line 35
    return-void
.end method

.method static synthetic access$0(Lcom/openvehicles/OVMS/TabCar;)V
    .locals 0
    .parameter

    .prologue
    .line 158
    invoke-direct {p0}, Lcom/openvehicles/OVMS/TabCar;->updateLastUpdatedView()V

    return-void
.end method

.method static synthetic access$1(Lcom/openvehicles/OVMS/TabCar;)Landroid/os/Handler;
    .locals 1
    .parameter

    .prologue
    .line 114
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabCar;->lastUpdateTimerHandler:Landroid/os/Handler;

    return-object v0
.end method

.method static synthetic access$2(Lcom/openvehicles/OVMS/TabCar;)Ljava/lang/Runnable;
    .locals 1
    .parameter

    .prologue
    .line 120
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabCar;->lastUpdateTimer:Ljava/lang/Runnable;

    return-object v0
.end method

.method static synthetic access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;
    .locals 1
    .parameter

    .prologue
    .line 112
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;

    return-object v0
.end method

.method static synthetic access$4(Lcom/openvehicles/OVMS/TabCar;)Landroid/app/AlertDialog;
    .locals 1
    .parameter

    .prologue
    .line 115
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabCar;->lastUpdatedDialog:Landroid/app/AlertDialog;

    return-object v0
.end method

.method static synthetic access$5(Lcom/openvehicles/OVMS/TabCar;)V
    .locals 0
    .parameter

    .prologue
    .line 369
    invoke-direct {p0}, Lcom/openvehicles/OVMS/TabCar;->downloadLayout()V

    return-void
.end method

.method static synthetic access$6(Lcom/openvehicles/OVMS/TabCar;Landroid/app/AlertDialog;)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 115
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabCar;->lastUpdatedDialog:Landroid/app/AlertDialog;

    return-void
.end method

.method static synthetic access$7(Lcom/openvehicles/OVMS/TabCar;)Z
    .locals 1
    .parameter

    .prologue
    .line 113
    iget-boolean v0, p0, Lcom/openvehicles/OVMS/TabCar;->isLoggedIn:Z

    return v0
.end method

.method static synthetic access$8(Lcom/openvehicles/OVMS/TabCar;)V
    .locals 0
    .parameter

    .prologue
    .line 46
    invoke-direct {p0}, Lcom/openvehicles/OVMS/TabCar;->initUI()V

    return-void
.end method

.method private downloadLayout()V
    .locals 5

    .prologue
    const/4 v4, 0x1

    .line 370
    new-instance v0, Landroid/app/ProgressDialog;

    invoke-direct {v0, p0}, Landroid/app/ProgressDialog;-><init>(Landroid/content/Context;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabCar;->downloadProgress:Landroid/app/ProgressDialog;

    .line 371
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabCar;->downloadProgress:Landroid/app/ProgressDialog;

    const-string v1, "Downloading Hi-Res Graphics"

    invoke-virtual {v0, v1}, Landroid/app/ProgressDialog;->setMessage(Ljava/lang/CharSequence;)V

    .line 372
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabCar;->downloadProgress:Landroid/app/ProgressDialog;

    invoke-virtual {v0, v4}, Landroid/app/ProgressDialog;->setIndeterminate(Z)V

    .line 373
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabCar;->downloadProgress:Landroid/app/ProgressDialog;

    const/16 v1, 0x64

    invoke-virtual {v0, v1}, Landroid/app/ProgressDialog;->setMax(I)V

    .line 374
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabCar;->downloadProgress:Landroid/app/ProgressDialog;

    invoke-virtual {v0, v4}, Landroid/app/ProgressDialog;->setCancelable(Z)V

    .line 375
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabCar;->downloadProgress:Landroid/app/ProgressDialog;

    invoke-virtual {v0, v4}, Landroid/app/ProgressDialog;->setProgressStyle(I)V

    .line 376
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabCar;->downloadProgress:Landroid/app/ProgressDialog;

    invoke-virtual {v0}, Landroid/app/ProgressDialog;->show()V

    .line 378
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabCar;->downloadProgress:Landroid/app/ProgressDialog;

    new-instance v1, Lcom/openvehicles/OVMS/TabCar$8;

    invoke-direct {v1, p0}, Lcom/openvehicles/OVMS/TabCar$8;-><init>(Lcom/openvehicles/OVMS/TabCar;)V

    invoke-virtual {v0, v1}, Landroid/app/ProgressDialog;->setOnDismissListener(Landroid/content/DialogInterface$OnDismissListener;)V

    .line 395
    new-instance v0, Lcom/openvehicles/OVMS/ServerCommands$CarLayoutDownloader;

    iget-object v1, p0, Lcom/openvehicles/OVMS/TabCar;->downloadProgress:Landroid/app/ProgressDialog;

    invoke-direct {v0, v1}, Lcom/openvehicles/OVMS/ServerCommands$CarLayoutDownloader;-><init>(Landroid/app/ProgressDialog;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabCar;->downloadTask:Lcom/openvehicles/OVMS/ServerCommands$CarLayoutDownloader;

    .line 396
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabCar;->downloadTask:Lcom/openvehicles/OVMS/ServerCommands$CarLayoutDownloader;

    const/4 v1, 0x2

    new-array v1, v1, [Ljava/lang/String;

    const/4 v2, 0x0

    iget-object v3, p0, Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v3, v3, Lcom/openvehicles/OVMS/CarData;->VehicleImageDrawable:Ljava/lang/String;

    aput-object v3, v1, v2

    .line 397
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TabCar;->getCacheDir()Ljava/io/File;

    move-result-object v2

    invoke-virtual {v2}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v2

    aput-object v2, v1, v4

    .line 396
    invoke-virtual {v0, v1}, Lcom/openvehicles/OVMS/ServerCommands$CarLayoutDownloader;->execute([Ljava/lang/Object;)Landroid/os/AsyncTask;

    .line 398
    return-void
.end method

.method private initUI()V
    .locals 4

    .prologue
    .line 47
    const v3, 0x7f09001c

    invoke-virtual {p0, v3}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/widget/TextView;

    .line 48
    .local v2, tv:Landroid/widget/TextView;
    new-instance v3, Lcom/openvehicles/OVMS/TabCar$4;

    invoke-direct {v3, p0}, Lcom/openvehicles/OVMS/TabCar$4;-><init>(Lcom/openvehicles/OVMS/TabCar;)V

    invoke-virtual {v2, v3}, Landroid/widget/TextView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 72
    const v3, 0x7f090031

    invoke-virtual {p0, v3}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/FrameLayout;

    .line 73
    .local v0, hotspot:Landroid/widget/FrameLayout;
    new-instance v3, Lcom/openvehicles/OVMS/TabCar$5;

    invoke-direct {v3, p0}, Lcom/openvehicles/OVMS/TabCar$5;-><init>(Lcom/openvehicles/OVMS/TabCar;)V

    invoke-virtual {v0, v3}, Landroid/widget/FrameLayout;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 85
    const v3, 0x7f090032

    invoke-virtual {p0, v3}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v0

    .end local v0           #hotspot:Landroid/widget/FrameLayout;
    check-cast v0, Landroid/widget/FrameLayout;

    .line 86
    .restart local v0       #hotspot:Landroid/widget/FrameLayout;
    new-instance v3, Lcom/openvehicles/OVMS/TabCar$6;

    invoke-direct {v3, p0}, Lcom/openvehicles/OVMS/TabCar$6;-><init>(Lcom/openvehicles/OVMS/TabCar;)V

    invoke-virtual {v0, v3}, Landroid/widget/FrameLayout;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 98
    const v3, 0x7f090033

    invoke-virtual {p0, v3}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/LinearLayout;

    .line 99
    .local v1, temperatures:Landroid/widget/LinearLayout;
    new-instance v3, Lcom/openvehicles/OVMS/TabCar$7;

    invoke-direct {v3, p0}, Lcom/openvehicles/OVMS/TabCar$7;-><init>(Lcom/openvehicles/OVMS/TabCar;)V

    invoke-virtual {v1, v3}, Landroid/widget/LinearLayout;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 110
    return-void
.end method

.method private updateLastUpdatedView()V
    .locals 9

    .prologue
    .line 159
    iget-object v5, p0, Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;

    if-eqz v5, :cond_0

    iget-object v5, p0, Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v5, v5, Lcom/openvehicles/OVMS/CarData;->Data_LastCarUpdate:Ljava/util/Date;

    if-nez v5, :cond_1

    .line 185
    :cond_0
    :goto_0
    return-void

    .line 162
    :cond_1
    const v5, 0x7f09001c

    invoke-virtual {p0, v5}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v4

    check-cast v4, Landroid/widget/TextView;

    .line 163
    .local v4, tv:Landroid/widget/TextView;
    new-instance v3, Ljava/util/Date;

    invoke-direct {v3}, Ljava/util/Date;-><init>()V

    .line 164
    .local v3, now:Ljava/util/Date;
    invoke-virtual {v3}, Ljava/util/Date;->getTime()J

    move-result-wide v5

    iget-object v7, p0, Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v7, v7, Lcom/openvehicles/OVMS/CarData;->Data_LastCarUpdate:Ljava/util/Date;

    .line 165
    invoke-virtual {v7}, Ljava/util/Date;->getTime()J

    move-result-wide v7

    .line 164
    sub-long/2addr v5, v7

    .line 165
    const-wide/16 v7, 0x3e8

    .line 164
    div-long v1, v5, v7

    .line 167
    .local v1, lastUpdateSecondsAgo:J
    const-wide/16 v5, 0x3c

    cmp-long v5, v1, v5

    if-gez v5, :cond_2

    .line 168
    const-string v5, "live"

    invoke-virtual {v4, v5}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto :goto_0

    .line 169
    :cond_2
    const-wide/16 v5, 0xe10

    cmp-long v5, v1, v5

    if-gez v5, :cond_4

    .line 170
    const-wide/16 v5, 0x3c

    div-long v5, v1, v5

    long-to-double v5, v5

    invoke-static {v5, v6}, Ljava/lang/Math;->ceil(D)D

    move-result-wide v5

    double-to-int v0, v5

    .line 171
    .local v0, displayValue:I
    const-string v6, "Updated: %d min%s ago"

    const/4 v5, 0x2

    new-array v7, v5, [Ljava/lang/Object;

    const/4 v5, 0x0

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v8

    aput-object v8, v7, v5

    const/4 v8, 0x1

    .line 172
    const/4 v5, 0x1

    if-le v0, v5, :cond_3

    const-string v5, "s"

    :goto_1
    aput-object v5, v7, v8

    .line 171
    invoke-static {v6, v7}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v4, v5}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto :goto_0

    .line 172
    :cond_3
    const-string v5, ""

    goto :goto_1

    .line 173
    .end local v0           #displayValue:I
    :cond_4
    const-wide/32 v5, 0x15180

    cmp-long v5, v1, v5

    if-gez v5, :cond_6

    .line 174
    const-wide/16 v5, 0xe10

    div-long v5, v1, v5

    long-to-double v5, v5

    invoke-static {v5, v6}, Ljava/lang/Math;->ceil(D)D

    move-result-wide v5

    double-to-int v0, v5

    .line 175
    .restart local v0       #displayValue:I
    const-string v6, "Updated: %d hr%s ago"

    const/4 v5, 0x2

    new-array v7, v5, [Ljava/lang/Object;

    const/4 v5, 0x0

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v8

    aput-object v8, v7, v5

    const/4 v8, 0x1

    .line 176
    const/4 v5, 0x1

    if-le v0, v5, :cond_5

    const-string v5, "s"

    :goto_2
    aput-object v5, v7, v8

    .line 175
    invoke-static {v6, v7}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v4, v5}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_0

    .line 176
    :cond_5
    const-string v5, ""

    goto :goto_2

    .line 177
    .end local v0           #displayValue:I
    :cond_6
    const-wide/32 v5, 0xd2f00

    cmp-long v5, v1, v5

    if-gez v5, :cond_8

    .line 178
    const-wide/32 v5, 0x15180

    div-long v5, v1, v5

    long-to-double v5, v5

    invoke-static {v5, v6}, Ljava/lang/Math;->ceil(D)D

    move-result-wide v5

    double-to-int v0, v5

    .line 179
    .restart local v0       #displayValue:I
    const-string v6, "Updated: %d day%s ago"

    const/4 v5, 0x2

    new-array v7, v5, [Ljava/lang/Object;

    const/4 v5, 0x0

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v8

    aput-object v8, v7, v5

    const/4 v8, 0x1

    .line 180
    const/4 v5, 0x1

    if-le v0, v5, :cond_7

    const-string v5, "s"

    :goto_3
    aput-object v5, v7, v8

    .line 179
    invoke-static {v6, v7}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v4, v5}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_0

    .line 180
    :cond_7
    const-string v5, ""

    goto :goto_3

    .line 182
    .end local v0           #displayValue:I
    :cond_8
    const v5, 0x7f060003

    invoke-virtual {p0, v5}, Lcom/openvehicles/OVMS/TabCar;->getString(I)Ljava/lang/String;

    move-result-object v5

    const/4 v6, 0x1

    new-array v6, v6, [Ljava/lang/Object;

    const/4 v7, 0x0

    .line 183
    iget-object v8, p0, Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v8, v8, Lcom/openvehicles/OVMS/CarData;->Data_LastCarUpdate:Ljava/util/Date;

    aput-object v8, v6, v7

    .line 182
    invoke-static {v5, v6}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v4, v5}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_0
.end method


# virtual methods
.method public OrientationChanged()V
    .locals 2

    .prologue
    .line 408
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabCar;->orientationChangedHandler:Landroid/os/Handler;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    .line 409
    return-void
.end method

.method public Refresh(Lcom/openvehicles/OVMS/CarData;Z)V
    .locals 2
    .parameter "carData"
    .parameter "isLoggedIn"

    .prologue
    .line 401
    const-string v0, "Tab"

    const-string v1, "TabCar Refresh"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 402
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;

    .line 403
    iput-boolean p2, p0, Lcom/openvehicles/OVMS/TabCar;->isLoggedIn:Z

    .line 404
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabCar;->handler:Landroid/os/Handler;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    .line 405
    return-void
.end method

.method public onCreate(Landroid/os/Bundle;)V
    .locals 1
    .parameter "savedInstanceState"

    .prologue
    .line 40
    invoke-super {p0, p1}, Landroid/app/Activity;->onCreate(Landroid/os/Bundle;)V

    .line 41
    const v0, 0x7f03000a

    invoke-virtual {p0, v0}, Lcom/openvehicles/OVMS/TabCar;->setContentView(I)V

    .line 43
    invoke-direct {p0}, Lcom/openvehicles/OVMS/TabCar;->initUI()V

    .line 44
    return-void
.end method

.method protected onPause()V
    .locals 2

    .prologue
    .line 143
    invoke-super {p0}, Landroid/app/Activity;->onPause()V

    .line 146
    :try_start_0
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabCar;->lastUpdatedDialog:Landroid/app/AlertDialog;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/openvehicles/OVMS/TabCar;->lastUpdatedDialog:Landroid/app/AlertDialog;

    invoke-virtual {v0}, Landroid/app/AlertDialog;->isShowing()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 147
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabCar;->lastUpdatedDialog:Landroid/app/AlertDialog;

    invoke-virtual {v0}, Landroid/app/AlertDialog;->dismiss()V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 155
    :cond_0
    :goto_0
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabCar;->lastUpdateTimerHandler:Landroid/os/Handler;

    iget-object v1, p0, Lcom/openvehicles/OVMS/TabCar;->lastUpdateTimer:Ljava/lang/Runnable;

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeCallbacks(Ljava/lang/Runnable;)V

    .line 156
    return-void

    .line 148
    :catch_0
    move-exception v0

    goto :goto_0
.end method

.method protected onResume()V
    .locals 4

    .prologue
    .line 132
    invoke-super {p0}, Landroid/app/Activity;->onResume()V

    .line 138
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabCar;->lastUpdateTimerHandler:Landroid/os/Handler;

    iget-object v1, p0, Lcom/openvehicles/OVMS/TabCar;->lastUpdateTimer:Ljava/lang/Runnable;

    const-wide/16 v2, 0x1388

    invoke-virtual {v0, v1, v2, v3}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    .line 139
    return-void
.end method
