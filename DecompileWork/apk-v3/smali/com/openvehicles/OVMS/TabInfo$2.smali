.class Lcom/openvehicles/OVMS/TabInfo$2;
.super Landroid/os/Handler;
.source "TabInfo.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/openvehicles/OVMS/TabInfo;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/openvehicles/OVMS/TabInfo;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/TabInfo;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    .line 314
    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    return-void
.end method

.method static synthetic access$0(Lcom/openvehicles/OVMS/TabInfo$2;)Lcom/openvehicles/OVMS/TabInfo;
    .locals 1
    .parameter

    .prologue
    .line 314
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    return-object v0
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 15
    .parameter "msg"

    .prologue
    .line 316
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #calls: Lcom/openvehicles/OVMS/TabInfo;->updateLastUpdatedView()V
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabInfo;->access$0(Lcom/openvehicles/OVMS/TabInfo;)V

    .line 318
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    const v10, 0x7f090044

    invoke-virtual {v9, v10}, Lcom/openvehicles/OVMS/TabInfo;->findViewById(I)Landroid/view/View;

    move-result-object v8

    check-cast v8, Landroid/widget/TextView;

    .line 319
    .local v8, tv:Landroid/widget/TextView;
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v9

    iget-object v9, v9, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    invoke-virtual {v8, v9}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 321
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    const v10, 0x7f09004e

    invoke-virtual {v9, v10}, Lcom/openvehicles/OVMS/TabInfo;->findViewById(I)Landroid/view/View;

    move-result-object v8

    .end local v8           #tv:Landroid/widget/TextView;
    check-cast v8, Landroid/widget/TextView;

    .line 322
    .restart local v8       #tv:Landroid/widget/TextView;
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    const v10, 0x7f060004

    invoke-virtual {v9, v10}, Lcom/openvehicles/OVMS/TabInfo;->getString(I)Ljava/lang/String;

    move-result-object v9

    const/4 v10, 0x1

    new-array v10, v10, [Ljava/lang/Object;

    const/4 v11, 0x0

    iget-object v12, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v12}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v12

    iget v12, v12, Lcom/openvehicles/OVMS/CarData;->Data_SOC:I

    invoke-static {v12}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v12

    aput-object v12, v10, v11

    invoke-static {v9, v10}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v8, v9}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 324
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    const v10, 0x7f090046

    invoke-virtual {v9, v10}, Lcom/openvehicles/OVMS/TabInfo;->findViewById(I)Landroid/view/View;

    move-result-object v9

    if-eqz v9, :cond_5

    .line 326
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    const v10, 0x7f090046

    invoke-virtual {v9, v10}, Lcom/openvehicles/OVMS/TabInfo;->findViewById(I)Landroid/view/View;

    move-result-object v7

    check-cast v7, Landroid/widget/TableRow;

    .line 327
    .local v7, row:Landroid/widget/TableRow;
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v9

    iget-boolean v9, v9, Lcom/openvehicles/OVMS/CarData;->Data_ChargePortOpen:Z

    if-eqz v9, :cond_4

    const/4 v9, 0x0

    :goto_0
    invoke-virtual {v7, v9}, Landroid/widget/TableRow;->setVisibility(I)V

    .line 334
    .end local v7           #row:Landroid/widget/TableRow;
    :cond_0
    :goto_1
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    const v10, 0x7f09004a

    invoke-virtual {v9, v10}, Lcom/openvehicles/OVMS/TabInfo;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/SeekBar;

    .line 336
    .local v1, bar:Landroid/widget/SeekBar;
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    const v10, 0x7f090049

    invoke-virtual {v9, v10}, Lcom/openvehicles/OVMS/TabInfo;->findViewById(I)Landroid/view/View;

    move-result-object v8

    .end local v8           #tv:Landroid/widget/TextView;
    check-cast v8, Landroid/widget/TextView;

    .line 337
    .restart local v8       #tv:Landroid/widget/TextView;
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v9

    iget-object v9, v9, Lcom/openvehicles/OVMS/CarData;->Data_ChargeState:Ljava/lang/String;

    const-string v10, "charging"

    invoke-virtual {v9, v10}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v9

    if-eqz v9, :cond_7

    .line 338
    const-string v9, "Charging - %s"

    const/4 v10, 0x1

    new-array v10, v10, [Ljava/lang/Object;

    const/4 v11, 0x0

    .line 339
    iget-object v12, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v12}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v12

    iget-object v12, v12, Lcom/openvehicles/OVMS/CarData;->Data_ChargeMode:Ljava/lang/String;

    invoke-virtual {v12}, Ljava/lang/String;->toUpperCase()Ljava/lang/String;

    move-result-object v12

    aput-object v12, v10, v11

    .line 338
    invoke-static {v9, v10}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v8, v9}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 357
    :cond_1
    :goto_2
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    const v10, 0x7f090048

    invoke-virtual {v9, v10}, Lcom/openvehicles/OVMS/TabInfo;->findViewById(I)Landroid/view/View;

    move-result-object v8

    .end local v8           #tv:Landroid/widget/TextView;
    check-cast v8, Landroid/widget/TextView;

    .line 358
    .restart local v8       #tv:Landroid/widget/TextView;
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    const v10, 0x7f09004c

    invoke-virtual {v9, v10}, Lcom/openvehicles/OVMS/TabInfo;->findViewById(I)Landroid/view/View;

    move-result-object v6

    check-cast v6, Landroid/widget/ImageView;

    .line 360
    .local v6, iv:Landroid/widget/ImageView;
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v9

    iget-boolean v9, v9, Lcom/openvehicles/OVMS/CarData;->Data_Charging:Z

    if-eqz v9, :cond_c

    .line 363
    const/4 v9, 0x0

    invoke-virtual {v1, v9}, Landroid/widget/SeekBar;->setProgress(I)V

    .line 364
    const/4 v9, 0x0

    invoke-virtual {v6, v9}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 365
    const-string v9, "%sA|%sV"

    const/4 v10, 0x2

    new-array v10, v10, [Ljava/lang/Object;

    const/4 v11, 0x0

    iget-object v12, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v12}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v12

    iget v12, v12, Lcom/openvehicles/OVMS/CarData;->Data_ChargeCurrent:I

    invoke-static {v12}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v12

    aput-object v12, v10, v11

    const/4 v11, 0x1

    iget-object v12, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v12}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v12

    iget v12, v12, Lcom/openvehicles/OVMS/CarData;->Data_LineVoltage:I

    invoke-static {v12}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v12

    aput-object v12, v10, v11

    invoke-static {v9, v10}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v8, v9}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 375
    :goto_3
    const-string v5, " km"

    .line 376
    .local v5, distanceUnit:Ljava/lang/String;
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v9

    iget-object v9, v9, Lcom/openvehicles/OVMS/CarData;->Data_DistanceUnit:Ljava/lang/String;

    if-eqz v9, :cond_2

    .line 377
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v9

    iget-object v9, v9, Lcom/openvehicles/OVMS/CarData;->Data_DistanceUnit:Ljava/lang/String;

    const-string v10, "K"

    invoke-virtual {v9, v10}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v9

    if-nez v9, :cond_2

    .line 378
    const-string v5, " miles"

    .line 380
    :cond_2
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    const v10, 0x7f090050

    invoke-virtual {v9, v10}, Lcom/openvehicles/OVMS/TabInfo;->findViewById(I)Landroid/view/View;

    move-result-object v8

    .end local v8           #tv:Landroid/widget/TextView;
    check-cast v8, Landroid/widget/TextView;

    .line 381
    .restart local v8       #tv:Landroid/widget/TextView;
    new-instance v9, Ljava/lang/StringBuilder;

    iget-object v10, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v10}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v10

    iget v10, v10, Lcom/openvehicles/OVMS/CarData;->Data_IdealRange:I

    invoke-static {v10}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v10

    invoke-direct {v9, v10}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v9, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v8, v9}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 382
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    const v10, 0x7f09004f

    invoke-virtual {v9, v10}, Lcom/openvehicles/OVMS/TabInfo;->findViewById(I)Landroid/view/View;

    move-result-object v8

    .end local v8           #tv:Landroid/widget/TextView;
    check-cast v8, Landroid/widget/TextView;

    .line 383
    .restart local v8       #tv:Landroid/widget/TextView;
    new-instance v9, Ljava/lang/StringBuilder;

    iget-object v10, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v10}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v10

    iget v10, v10, Lcom/openvehicles/OVMS/CarData;->Data_EstimatedRange:I

    invoke-static {v10}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v10

    invoke-direct {v9, v10}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v9, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v8, v9}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 385
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    const v10, 0x7f090041

    invoke-virtual {v9, v10}, Lcom/openvehicles/OVMS/TabInfo;->findViewById(I)Landroid/view/View;

    move-result-object v6

    .end local v6           #iv:Landroid/widget/ImageView;
    check-cast v6, Landroid/widget/ImageView;

    .line 386
    .restart local v6       #iv:Landroid/widget/ImageView;
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->isLoggedIn:Z
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabInfo;->access$4(Lcom/openvehicles/OVMS/TabInfo;)Z

    move-result v9

    if-eqz v9, :cond_d

    const/16 v9, 0x8

    :goto_4
    invoke-virtual {v6, v9}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 388
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    const v10, 0x7f090042

    invoke-virtual {v9, v10}, Lcom/openvehicles/OVMS/TabInfo;->findViewById(I)Landroid/view/View;

    move-result-object v6

    .end local v6           #iv:Landroid/widget/ImageView;
    check-cast v6, Landroid/widget/ImageView;

    .line 389
    .restart local v6       #iv:Landroid/widget/ImageView;
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v9

    iget-boolean v9, v9, Lcom/openvehicles/OVMS/CarData;->ParanoidMode:Z

    if-eqz v9, :cond_e

    const/4 v9, 0x0

    :goto_5
    invoke-virtual {v6, v9}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 391
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    const v10, 0x7f09001d

    invoke-virtual {v9, v10}, Lcom/openvehicles/OVMS/TabInfo;->findViewById(I)Landroid/view/View;

    move-result-object v6

    .end local v6           #iv:Landroid/widget/ImageView;
    check-cast v6, Landroid/widget/ImageView;

    .line 394
    .restart local v6       #iv:Landroid/widget/ImageView;
    :try_start_0
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v9

    iget-object v9, v9, Lcom/openvehicles/OVMS/CarData;->Data_CarModuleGSMSignalLevel:Ljava/lang/String;

    invoke-static {v9}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v0

    .line 395
    .local v0, RSSI:I
    const/4 v9, 0x1

    if-ge v0, v9, :cond_f

    .line 396
    const v9, 0x7f020068

    invoke-virtual {v6, v9}, Landroid/widget/ImageView;->setImageResource(I)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 410
    .end local v0           #RSSI:I
    :goto_6
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    const v10, 0x7f09004d

    invoke-virtual {v9, v10}, Lcom/openvehicles/OVMS/TabInfo;->findViewById(I)Landroid/view/View;

    move-result-object v6

    .end local v6           #iv:Landroid/widget/ImageView;
    check-cast v6, Landroid/widget/ImageView;

    .line 411
    .restart local v6       #iv:Landroid/widget/ImageView;
    invoke-virtual {v6}, Landroid/widget/ImageView;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v9

    iget-object v10, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v10}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v10

    iget v10, v10, Lcom/openvehicles/OVMS/CarData;->Data_SOC:I

    mul-int/lit16 v10, v10, 0x10c

    div-int/lit8 v10, v10, 0x64

    iput v10, v9, Landroid/view/ViewGroup$LayoutParams;->width:I

    .line 414
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    const v10, 0x7f090045

    invoke-virtual {v9, v10}, Lcom/openvehicles/OVMS/TabInfo;->findViewById(I)Landroid/view/View;

    move-result-object v6

    .end local v6           #iv:Landroid/widget/ImageView;
    check-cast v6, Landroid/widget/ImageView;

    .line 416
    .restart local v6       #iv:Landroid/widget/ImageView;
    new-instance v9, Ljava/lang/StringBuilder;

    iget-object v10, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    .line 417
    invoke-virtual {v10}, Lcom/openvehicles/OVMS/TabInfo;->getCacheDir()Ljava/io/File;

    move-result-object v10

    invoke-virtual {v10}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v10

    invoke-static {v10}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v10

    invoke-direct {v9, v10}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 418
    const-string v10, "/%s.png"

    const/4 v11, 0x1

    new-array v11, v11, [Ljava/lang/Object;

    const/4 v12, 0x0

    iget-object v13, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v13}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v13

    iget-object v13, v13, Lcom/openvehicles/OVMS/CarData;->VehicleImageDrawable:Ljava/lang/String;

    aput-object v13, v11, v12

    invoke-static {v10, v11}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v10

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    .line 416
    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v9}, Landroid/graphics/BitmapFactory;->decodeFile(Ljava/lang/String;)Landroid/graphics/Bitmap;

    move-result-object v3

    .line 419
    .local v3, carLayout:Landroid/graphics/Bitmap;
    if-eqz v3, :cond_14

    .line 420
    invoke-virtual {v6, v3}, Landroid/widget/ImageView;->setImageBitmap(Landroid/graphics/Bitmap;)V

    .line 461
    :cond_3
    :goto_7
    new-instance v9, Lcom/openvehicles/OVMS/TabInfo$2$3;

    invoke-direct {v9, p0}, Lcom/openvehicles/OVMS/TabInfo$2$3;-><init>(Lcom/openvehicles/OVMS/TabInfo$2;)V

    invoke-virtual {v6, v9}, Landroid/widget/ImageView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 511
    return-void

    .line 327
    .end local v1           #bar:Landroid/widget/SeekBar;
    .end local v3           #carLayout:Landroid/graphics/Bitmap;
    .end local v5           #distanceUnit:Ljava/lang/String;
    .end local v6           #iv:Landroid/widget/ImageView;
    .restart local v7       #row:Landroid/widget/TableRow;
    :cond_4
    const/16 v9, 0x8

    goto/16 :goto_0

    .line 328
    .end local v7           #row:Landroid/widget/TableRow;
    :cond_5
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    const v10, 0x7f090051

    invoke-virtual {v9, v10}, Lcom/openvehicles/OVMS/TabInfo;->findViewById(I)Landroid/view/View;

    move-result-object v9

    if-eqz v9, :cond_0

    .line 330
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    const v10, 0x7f090051

    invoke-virtual {v9, v10}, Lcom/openvehicles/OVMS/TabInfo;->findViewById(I)Landroid/view/View;

    move-result-object v4

    check-cast v4, Landroid/widget/RelativeLayout;

    .line 331
    .local v4, charger:Landroid/widget/RelativeLayout;
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v9

    iget-boolean v9, v9, Lcom/openvehicles/OVMS/CarData;->Data_ChargePortOpen:Z

    if-eqz v9, :cond_6

    const/4 v9, 0x0

    :goto_8
    invoke-virtual {v4, v9}, Landroid/widget/RelativeLayout;->setVisibility(I)V

    goto/16 :goto_1

    :cond_6
    const/16 v9, 0x8

    goto :goto_8

    .line 340
    .end local v4           #charger:Landroid/widget/RelativeLayout;
    .restart local v1       #bar:Landroid/widget/SeekBar;
    :cond_7
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v9

    iget-object v9, v9, Lcom/openvehicles/OVMS/CarData;->Data_ChargeState:Ljava/lang/String;

    const-string v10, "prepare"

    invoke-virtual {v9, v10}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v9

    if-eqz v9, :cond_8

    .line 341
    const-string v9, "Preparing to Charge - %s"

    const/4 v10, 0x1

    new-array v10, v10, [Ljava/lang/Object;

    const/4 v11, 0x0

    .line 342
    iget-object v12, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v12}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v12

    iget-object v12, v12, Lcom/openvehicles/OVMS/CarData;->Data_ChargeMode:Ljava/lang/String;

    invoke-virtual {v12}, Ljava/lang/String;->toUpperCase()Ljava/lang/String;

    move-result-object v12

    aput-object v12, v10, v11

    .line 341
    invoke-static {v9, v10}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v8, v9}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_2

    .line 343
    :cond_8
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v9

    iget-object v9, v9, Lcom/openvehicles/OVMS/CarData;->Data_ChargeState:Ljava/lang/String;

    const-string v10, "heating"

    invoke-virtual {v9, v10}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v9

    if-eqz v9, :cond_9

    .line 344
    const-string v9, "Pre-Charge Battery Heating - %s"

    const/4 v10, 0x1

    new-array v10, v10, [Ljava/lang/Object;

    const/4 v11, 0x0

    .line 345
    iget-object v12, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v12}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v12

    iget-object v12, v12, Lcom/openvehicles/OVMS/CarData;->Data_ChargeMode:Ljava/lang/String;

    invoke-virtual {v12}, Ljava/lang/String;->toUpperCase()Ljava/lang/String;

    move-result-object v12

    aput-object v12, v10, v11

    .line 344
    invoke-static {v9, v10}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v8, v9}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_2

    .line 346
    :cond_9
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v9

    iget-object v9, v9, Lcom/openvehicles/OVMS/CarData;->Data_ChargeState:Ljava/lang/String;

    const-string v10, "topoff"

    invoke-virtual {v9, v10}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v9

    if-eqz v9, :cond_a

    .line 347
    const-string v9, "Topping Off - %s"

    const/4 v10, 0x1

    new-array v10, v10, [Ljava/lang/Object;

    const/4 v11, 0x0

    .line 348
    iget-object v12, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v12}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v12

    iget-object v12, v12, Lcom/openvehicles/OVMS/CarData;->Data_ChargeMode:Ljava/lang/String;

    invoke-virtual {v12}, Ljava/lang/String;->toUpperCase()Ljava/lang/String;

    move-result-object v12

    aput-object v12, v10, v11

    .line 347
    invoke-static {v9, v10}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v8, v9}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_2

    .line 349
    :cond_a
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v9

    iget-object v9, v9, Lcom/openvehicles/OVMS/CarData;->Data_ChargeState:Ljava/lang/String;

    const-string v10, "stopped"

    invoke-virtual {v9, v10}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v9

    if-eqz v9, :cond_b

    .line 350
    const-string v9, "Charge Interrupted - %s"

    const/4 v10, 0x1

    new-array v10, v10, [Ljava/lang/Object;

    const/4 v11, 0x0

    .line 351
    iget-object v12, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v12}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v12

    iget-object v12, v12, Lcom/openvehicles/OVMS/CarData;->Data_ChargeMode:Ljava/lang/String;

    invoke-virtual {v12}, Ljava/lang/String;->toUpperCase()Ljava/lang/String;

    move-result-object v12

    aput-object v12, v10, v11

    .line 350
    invoke-static {v9, v10}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v8, v9}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_2

    .line 352
    :cond_b
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v9

    iget-object v9, v9, Lcom/openvehicles/OVMS/CarData;->Data_ChargeState:Ljava/lang/String;

    const-string v10, "done"

    invoke-virtual {v9, v10}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v9

    if-eqz v9, :cond_1

    .line 353
    const-string v9, "Charge Completed - %s"

    const/4 v10, 0x1

    new-array v10, v10, [Ljava/lang/Object;

    const/4 v11, 0x0

    .line 354
    iget-object v12, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v12}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v12

    iget-object v12, v12, Lcom/openvehicles/OVMS/CarData;->Data_ChargeMode:Ljava/lang/String;

    invoke-virtual {v12}, Ljava/lang/String;->toUpperCase()Ljava/lang/String;

    move-result-object v12

    aput-object v12, v10, v11

    .line 353
    invoke-static {v9, v10}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v8, v9}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_2

    .line 370
    .restart local v6       #iv:Landroid/widget/ImageView;
    :cond_c
    const/16 v9, 0x64

    invoke-virtual {v1, v9}, Landroid/widget/SeekBar;->setProgress(I)V

    .line 371
    const/16 v9, 0x8

    invoke-virtual {v6, v9}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 372
    const-string v9, "%sA MAX"

    const/4 v10, 0x1

    new-array v10, v10, [Ljava/lang/Object;

    const/4 v11, 0x0

    iget-object v12, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v12}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v12

    iget v12, v12, Lcom/openvehicles/OVMS/CarData;->Data_ChargeAmpsLimit:I

    invoke-static {v12}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v12

    aput-object v12, v10, v11

    invoke-static {v9, v10}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v8, v9}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_3

    .line 386
    .restart local v5       #distanceUnit:Ljava/lang/String;
    :cond_d
    const/4 v9, 0x0

    goto/16 :goto_4

    .line 389
    :cond_e
    const/16 v9, 0x8

    goto/16 :goto_5

    .line 397
    .restart local v0       #RSSI:I
    :cond_f
    const/4 v9, 0x7

    if-ge v0, v9, :cond_10

    .line 398
    const v9, 0x7f020069

    :try_start_1
    invoke-virtual {v6, v9}, Landroid/widget/ImageView;->setImageResource(I)V

    goto/16 :goto_6

    .line 407
    .end local v0           #RSSI:I
    :catch_0
    move-exception v9

    goto/16 :goto_6

    .line 399
    .restart local v0       #RSSI:I
    :cond_10
    const/16 v9, 0xe

    if-ge v0, v9, :cond_11

    .line 400
    const v9, 0x7f02006a

    invoke-virtual {v6, v9}, Landroid/widget/ImageView;->setImageResource(I)V

    goto/16 :goto_6

    .line 401
    :cond_11
    const/16 v9, 0x15

    if-ge v0, v9, :cond_12

    .line 402
    const v9, 0x7f02006b

    invoke-virtual {v6, v9}, Landroid/widget/ImageView;->setImageResource(I)V

    goto/16 :goto_6

    .line 403
    :cond_12
    const/16 v9, 0x1c

    if-ge v0, v9, :cond_13

    .line 404
    const v9, 0x7f02006c

    invoke-virtual {v6, v9}, Landroid/widget/ImageView;->setImageResource(I)V

    goto/16 :goto_6

    .line 406
    :cond_13
    const v9, 0x7f02006d

    invoke-virtual {v6, v9}, Landroid/widget/ImageView;->setImageResource(I)V
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_0

    goto/16 :goto_6

    .line 422
    .end local v0           #RSSI:I
    .restart local v3       #carLayout:Landroid/graphics/Bitmap;
    :cond_14
    const-string v9, "OVMS"

    .line 423
    new-instance v10, Ljava/lang/StringBuilder;

    const-string v11, "** File Not Found: "

    invoke-direct {v10, v11}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 424
    iget-object v11, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    invoke-virtual {v11}, Lcom/openvehicles/OVMS/TabInfo;->getCacheDir()Ljava/io/File;

    move-result-object v11

    invoke-virtual {v11}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    .line 425
    const-string v11, "/%s.png"

    const/4 v12, 0x1

    new-array v12, v12, [Ljava/lang/Object;

    const/4 v13, 0x0

    .line 426
    iget-object v14, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v14}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v14

    iget-object v14, v14, Lcom/openvehicles/OVMS/CarData;->VehicleImageDrawable:Ljava/lang/String;

    aput-object v14, v12, v13

    .line 425
    invoke-static {v11, v12}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    .line 423
    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    .line 422
    invoke-static {v9, v10}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 429
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v9

    iget-boolean v9, v9, Lcom/openvehicles/OVMS/CarData;->DontAskLayoutDownload:Z

    if-nez v9, :cond_3

    .line 430
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->lastUpdatedDialog:Landroid/app/AlertDialog;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabInfo;->access$5(Lcom/openvehicles/OVMS/TabInfo;)Landroid/app/AlertDialog;

    move-result-object v9

    if-eqz v9, :cond_15

    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->lastUpdatedDialog:Landroid/app/AlertDialog;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabInfo;->access$5(Lcom/openvehicles/OVMS/TabInfo;)Landroid/app/AlertDialog;

    move-result-object v9

    .line 431
    invoke-virtual {v9}, Landroid/app/AlertDialog;->isShowing()Z

    move-result v9

    if-nez v9, :cond_3

    .line 432
    :cond_15
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v9

    const/4 v10, 0x1

    iput-boolean v10, v9, Lcom/openvehicles/OVMS/CarData;->DontAskLayoutDownload:Z

    .line 433
    new-instance v2, Landroid/app/AlertDialog$Builder;

    .line 434
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    .line 433
    invoke-direct {v2, v9}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 436
    .local v2, builder:Landroid/app/AlertDialog$Builder;
    const-string v9, "Would you like to download a set of high resolution car images specifically drawn for your car?\n\nThe download is approx. 300KB.\n\nNote: a manual download button is available in the car commands and settings tab."

    .line 435
    invoke-virtual {v2, v9}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v9

    .line 437
    const-string v10, "Download Graphics"

    invoke-virtual {v9, v10}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v9

    .line 438
    const/4 v10, 0x1

    invoke-virtual {v9, v10}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    move-result-object v9

    .line 439
    const-string v10, "Download Now"

    .line 440
    new-instance v11, Lcom/openvehicles/OVMS/TabInfo$2$1;

    invoke-direct {v11, p0}, Lcom/openvehicles/OVMS/TabInfo$2$1;-><init>(Lcom/openvehicles/OVMS/TabInfo$2;)V

    .line 439
    invoke-virtual {v9, v10, v11}, Landroid/app/AlertDialog$Builder;->setPositiveButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v9

    .line 448
    const-string v10, "Later"

    .line 449
    new-instance v11, Lcom/openvehicles/OVMS/TabInfo$2$2;

    invoke-direct {v11, p0}, Lcom/openvehicles/OVMS/TabInfo$2$2;-><init>(Lcom/openvehicles/OVMS/TabInfo$2;)V

    .line 448
    invoke-virtual {v9, v10, v11}, Landroid/app/AlertDialog$Builder;->setNegativeButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 456
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    invoke-virtual {v2}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v10

    #setter for: Lcom/openvehicles/OVMS/TabInfo;->lastUpdatedDialog:Landroid/app/AlertDialog;
    invoke-static {v9, v10}, Lcom/openvehicles/OVMS/TabInfo;->access$7(Lcom/openvehicles/OVMS/TabInfo;Landroid/app/AlertDialog;)V

    .line 457
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->lastUpdatedDialog:Landroid/app/AlertDialog;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabInfo;->access$5(Lcom/openvehicles/OVMS/TabInfo;)Landroid/app/AlertDialog;

    move-result-object v9

    invoke-virtual {v9}, Landroid/app/AlertDialog;->show()V

    goto/16 :goto_7
.end method
