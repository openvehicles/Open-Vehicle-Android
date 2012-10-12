.class public Lcom/openvehicles/OVMS/TabInfo;
.super Landroid/app/Activity;
.source "TabInfo.java"


# instance fields
.field private data:Lcom/openvehicles/OVMS/CarData;

.field private handler:Landroid/os/Handler;

.field private lastUpdateTimer:Ljava/lang/Runnable;

.field private lastUpdateTimerHandler:Landroid/os/Handler;

.field private softwareInformation:Landroid/app/AlertDialog;


# direct methods
.method public constructor <init>()V
    .locals 1

    .prologue
    .line 18
    invoke-direct {p0}, Landroid/app/Activity;-><init>()V

    .line 31
    new-instance v0, Landroid/os/Handler;

    invoke-direct {v0}, Landroid/os/Handler;-><init>()V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->lastUpdateTimerHandler:Landroid/os/Handler;

    .line 34
    new-instance v0, Lcom/openvehicles/OVMS/TabInfo$1;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/TabInfo$1;-><init>(Lcom/openvehicles/OVMS/TabInfo;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->lastUpdateTimer:Ljava/lang/Runnable;

    .line 94
    new-instance v0, Lcom/openvehicles/OVMS/TabInfo$2;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/TabInfo$2;-><init>(Lcom/openvehicles/OVMS/TabInfo;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->handler:Landroid/os/Handler;

    return-void
.end method

.method static synthetic access$000(Lcom/openvehicles/OVMS/TabInfo;)V
    .locals 0
    .parameter "x0"

    .prologue
    .line 18
    invoke-direct {p0}, Lcom/openvehicles/OVMS/TabInfo;->updateLastUpdatedView()V

    return-void
.end method

.method static synthetic access$100(Lcom/openvehicles/OVMS/TabInfo;)Ljava/lang/Runnable;
    .locals 1
    .parameter "x0"

    .prologue
    .line 18
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->lastUpdateTimer:Ljava/lang/Runnable;

    return-object v0
.end method

.method static synthetic access$200(Lcom/openvehicles/OVMS/TabInfo;)Landroid/os/Handler;
    .locals 1
    .parameter "x0"

    .prologue
    .line 18
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->lastUpdateTimerHandler:Landroid/os/Handler;

    return-object v0
.end method

.method static synthetic access$300(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;
    .locals 1
    .parameter "x0"

    .prologue
    .line 18
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;

    return-object v0
.end method

.method static synthetic access$400(Lcom/openvehicles/OVMS/TabInfo;)Landroid/app/AlertDialog;
    .locals 1
    .parameter "x0"

    .prologue
    .line 18
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->softwareInformation:Landroid/app/AlertDialog;

    return-object v0
.end method

.method static synthetic access$402(Lcom/openvehicles/OVMS/TabInfo;Landroid/app/AlertDialog;)Landroid/app/AlertDialog;
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 18
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabInfo;->softwareInformation:Landroid/app/AlertDialog;

    return-object p1
.end method

.method private updateLastUpdatedView()V
    .locals 15

    .prologue
    const-wide/16 v13, 0xe10

    const-wide/16 v11, 0x3c

    const/4 v10, 0x2

    const/4 v9, 0x0

    const/4 v8, 0x1

    .line 64
    iget-object v5, p0, Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;

    if-eqz v5, :cond_0

    iget-object v5, p0, Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v5, v5, Lcom/openvehicles/OVMS/CarData;->Data_LastCarUpdate:Ljava/util/Date;

    if-nez v5, :cond_1

    .line 92
    :cond_0
    :goto_0
    return-void

    .line 67
    :cond_1
    const v5, 0x7f06002e

    invoke-virtual {p0, v5}, Lcom/openvehicles/OVMS/TabInfo;->findViewById(I)Landroid/view/View;

    move-result-object v4

    check-cast v4, Landroid/widget/TextView;

    .line 68
    .local v4, tv:Landroid/widget/TextView;
    new-instance v3, Ljava/util/Date;

    invoke-direct {v3}, Ljava/util/Date;-><init>()V

    .line 69
    .local v3, now:Ljava/util/Date;
    invoke-virtual {v3}, Ljava/util/Date;->getDate()I

    move-result v5

    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_LastCarUpdate:Ljava/util/Date;

    invoke-virtual {v6}, Ljava/util/Date;->getDate()I

    move-result v6

    sub-int/2addr v5, v6

    div-int/lit16 v5, v5, 0x3e8

    int-to-long v1, v5

    .line 70
    .local v1, lastUpdateSecondsAgo:J
    const-string v5, "OVMS"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "Last updated: "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v1, v2}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, " secs ago"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 72
    cmp-long v5, v1, v11

    if-gez v5, :cond_2

    .line 73
    const-string v5, "live"

    invoke-virtual {v4, v5}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto :goto_0

    .line 74
    :cond_2
    cmp-long v5, v1, v13

    if-gez v5, :cond_4

    .line 76
    div-long v5, v1, v11

    long-to-double v5, v5

    invoke-static {v5, v6}, Ljava/lang/Math;->ceil(D)D

    move-result-wide v5

    double-to-int v0, v5

    .line 77
    .local v0, displayValue:I
    const-string v6, "Updated: %d minute%s ago"

    new-array v7, v10, [Ljava/lang/Object;

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    aput-object v5, v7, v9

    if-le v0, v8, :cond_3

    const-string v5, "s"

    :goto_1
    aput-object v5, v7, v8

    invoke-static {v6, v7}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v4, v5}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto :goto_0

    :cond_3
    const-string v5, ""

    goto :goto_1

    .line 79
    .end local v0           #displayValue:I
    :cond_4
    const-wide/32 v5, 0x15180

    cmp-long v5, v1, v5

    if-gez v5, :cond_6

    .line 81
    div-long v5, v1, v13

    long-to-double v5, v5

    invoke-static {v5, v6}, Ljava/lang/Math;->ceil(D)D

    move-result-wide v5

    double-to-int v0, v5

    .line 82
    .restart local v0       #displayValue:I
    const-string v6, "Updated: %d hour%s ago"

    new-array v7, v10, [Ljava/lang/Object;

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    aput-object v5, v7, v9

    if-le v0, v8, :cond_5

    const-string v5, "s"

    :goto_2
    aput-object v5, v7, v8

    invoke-static {v6, v7}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v4, v5}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_0

    :cond_5
    const-string v5, ""

    goto :goto_2

    .line 84
    .end local v0           #displayValue:I
    :cond_6
    const-wide/32 v5, 0xd2f00

    cmp-long v5, v1, v5

    if-gez v5, :cond_8

    .line 86
    const-wide/32 v5, 0x15180

    div-long v5, v1, v5

    long-to-double v5, v5

    invoke-static {v5, v6}, Ljava/lang/Math;->ceil(D)D

    move-result-wide v5

    double-to-int v0, v5

    .line 87
    .restart local v0       #displayValue:I
    const-string v6, "Updated: %d day%s ago"

    new-array v7, v10, [Ljava/lang/Object;

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    aput-object v5, v7, v9

    if-le v0, v8, :cond_7

    const-string v5, "s"

    :goto_3
    aput-object v5, v7, v8

    invoke-static {v6, v7}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v4, v5}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_0

    :cond_7
    const-string v5, ""

    goto :goto_3

    .line 90
    .end local v0           #displayValue:I
    :cond_8
    const v5, 0x7f040004

    invoke-virtual {p0, v5}, Lcom/openvehicles/OVMS/TabInfo;->getString(I)Ljava/lang/String;

    move-result-object v5

    new-array v6, v8, [Ljava/lang/Object;

    iget-object v7, p0, Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v7, v7, Lcom/openvehicles/OVMS/CarData;->Data_LastCarUpdate:Ljava/util/Date;

    aput-object v7, v6, v9

    invoke-static {v5, v6}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v4, v5}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_0
.end method


# virtual methods
.method public RefreshStatus(Lcom/openvehicles/OVMS/CarData;)V
    .locals 2
    .parameter "carData"

    .prologue
    .line 184
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;

    .line 185
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->handler:Landroid/os/Handler;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    .line 187
    return-void
.end method

.method public onCreate(Landroid/os/Bundle;)V
    .locals 1
    .parameter "savedInstanceState"

    .prologue
    .line 23
    invoke-super {p0, p1}, Landroid/app/Activity;->onCreate(Landroid/os/Bundle;)V

    .line 24
    const v0, 0x7f030008

    invoke-virtual {p0, v0}, Lcom/openvehicles/OVMS/TabInfo;->setContentView(I)V

    .line 27
    return-void
.end method

.method protected onPause()V
    .locals 2

    .prologue
    .line 54
    invoke-super {p0}, Landroid/app/Activity;->onPause()V

    .line 56
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->softwareInformation:Landroid/app/AlertDialog;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->softwareInformation:Landroid/app/AlertDialog;

    invoke-virtual {v0}, Landroid/app/AlertDialog;->isShowing()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 57
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->softwareInformation:Landroid/app/AlertDialog;

    invoke-virtual {v0}, Landroid/app/AlertDialog;->dismiss()V

    .line 60
    :cond_0
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->lastUpdateTimerHandler:Landroid/os/Handler;

    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo;->lastUpdateTimer:Ljava/lang/Runnable;

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeCallbacks(Ljava/lang/Runnable;)V

    .line 61
    return-void
.end method

.method protected onResume()V
    .locals 4

    .prologue
    .line 46
    invoke-super {p0}, Landroid/app/Activity;->onResume()V

    .line 49
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo;->lastUpdateTimerHandler:Landroid/os/Handler;

    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo;->lastUpdateTimer:Ljava/lang/Runnable;

    const-wide/16 v2, 0x1388

    invoke-virtual {v0, v1, v2, v3}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    .line 50
    return-void
.end method
