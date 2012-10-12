.class Lcom/openvehicles/OVMS/TabInfo$2$3;
.super Ljava/lang/Object;
.source "TabInfo.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/TabInfo$2;->handleMessage(Landroid/os/Message;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$1:Lcom/openvehicles/OVMS/TabInfo$2;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/TabInfo$2;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabInfo$2$3;->this$1:Lcom/openvehicles/OVMS/TabInfo$2;

    .line 461
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 6
    .parameter "v"

    .prologue
    const/4 v5, 0x1

    .line 464
    new-instance v0, Landroid/app/AlertDialog$Builder;

    .line 465
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo$2$3;->this$1:Lcom/openvehicles/OVMS/TabInfo$2;

    #getter for: Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabInfo$2;->access$0(Lcom/openvehicles/OVMS/TabInfo$2;)Lcom/openvehicles/OVMS/TabInfo;

    move-result-object v1

    .line 464
    invoke-direct {v0, v1}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 468
    .local v0, builder:Landroid/app/AlertDialog$Builder;
    const-string v2, "Power: %s\nVIN: %s\nGSM Signal: %s\nHandbrake: %s\nValet: %s\nLock: %s\nCooling Pump: %s\nGPS: %s\n\nCar Module: %s\nOVMS Server: %s"

    const/16 v1, 0xa

    new-array v3, v1, [Ljava/lang/Object;

    const/4 v4, 0x0

    .line 470
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo$2$3;->this$1:Lcom/openvehicles/OVMS/TabInfo$2;

    #getter for: Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabInfo$2;->access$0(Lcom/openvehicles/OVMS/TabInfo$2;)Lcom/openvehicles/OVMS/TabInfo;

    move-result-object v1

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v1

    iget-boolean v1, v1, Lcom/openvehicles/OVMS/CarData;->Data_CarPoweredON:Z

    if-eqz v1, :cond_0

    const-string v1, "ON"

    :goto_0
    aput-object v1, v3, v4

    .line 471
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo$2$3;->this$1:Lcom/openvehicles/OVMS/TabInfo$2;

    #getter for: Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabInfo$2;->access$0(Lcom/openvehicles/OVMS/TabInfo$2;)Lcom/openvehicles/OVMS/TabInfo;

    move-result-object v1

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v1

    iget-object v1, v1, Lcom/openvehicles/OVMS/CarData;->Data_VIN:Ljava/lang/String;

    aput-object v1, v3, v5

    const/4 v1, 0x2

    .line 472
    iget-object v4, p0, Lcom/openvehicles/OVMS/TabInfo$2$3;->this$1:Lcom/openvehicles/OVMS/TabInfo$2;

    #getter for: Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;
    invoke-static {v4}, Lcom/openvehicles/OVMS/TabInfo$2;->access$0(Lcom/openvehicles/OVMS/TabInfo$2;)Lcom/openvehicles/OVMS/TabInfo;

    move-result-object v4

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v4}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v4

    iget-object v4, v4, Lcom/openvehicles/OVMS/CarData;->Data_CarModuleGSMSignalLevel:Ljava/lang/String;

    aput-object v4, v3, v1

    const/4 v4, 0x3

    .line 473
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo$2$3;->this$1:Lcom/openvehicles/OVMS/TabInfo$2;

    #getter for: Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabInfo$2;->access$0(Lcom/openvehicles/OVMS/TabInfo$2;)Lcom/openvehicles/OVMS/TabInfo;

    move-result-object v1

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v1

    iget-boolean v1, v1, Lcom/openvehicles/OVMS/CarData;->Data_HandBrakeApplied:Z

    if-eqz v1, :cond_1

    const-string v1, "ENGAGED"

    .line 474
    :goto_1
    aput-object v1, v3, v4

    const/4 v4, 0x4

    .line 475
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo$2$3;->this$1:Lcom/openvehicles/OVMS/TabInfo$2;

    #getter for: Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabInfo$2;->access$0(Lcom/openvehicles/OVMS/TabInfo$2;)Lcom/openvehicles/OVMS/TabInfo;

    move-result-object v1

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v1

    iget-boolean v1, v1, Lcom/openvehicles/OVMS/CarData;->Data_ValetON:Z

    if-eqz v1, :cond_2

    const-string v1, "ON"

    .line 476
    :goto_2
    aput-object v1, v3, v4

    const/4 v4, 0x5

    .line 477
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo$2$3;->this$1:Lcom/openvehicles/OVMS/TabInfo$2;

    #getter for: Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabInfo$2;->access$0(Lcom/openvehicles/OVMS/TabInfo$2;)Lcom/openvehicles/OVMS/TabInfo;

    move-result-object v1

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v1

    iget-boolean v1, v1, Lcom/openvehicles/OVMS/CarData;->Data_PINLocked:Z

    if-eqz v1, :cond_3

    const-string v1, "ON"

    .line 478
    :goto_3
    aput-object v1, v3, v4

    const/4 v4, 0x6

    .line 479
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo$2$3;->this$1:Lcom/openvehicles/OVMS/TabInfo$2;

    #getter for: Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabInfo$2;->access$0(Lcom/openvehicles/OVMS/TabInfo$2;)Lcom/openvehicles/OVMS/TabInfo;

    move-result-object v1

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v1

    iget-boolean v1, v1, Lcom/openvehicles/OVMS/CarData;->Data_CoolingPumpON_DoorState3:Z

    if-eqz v1, :cond_4

    const-string v1, "ON"

    .line 480
    :goto_4
    aput-object v1, v3, v4

    const/4 v4, 0x7

    .line 481
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo$2$3;->this$1:Lcom/openvehicles/OVMS/TabInfo$2;

    #getter for: Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabInfo$2;->access$0(Lcom/openvehicles/OVMS/TabInfo$2;)Lcom/openvehicles/OVMS/TabInfo;

    move-result-object v1

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v1

    iget-boolean v1, v1, Lcom/openvehicles/OVMS/CarData;->Data_GPSLocked:Z

    if-eqz v1, :cond_5

    const-string v1, "LOCKED"

    .line 482
    :goto_5
    aput-object v1, v3, v4

    const/16 v1, 0x8

    .line 483
    iget-object v4, p0, Lcom/openvehicles/OVMS/TabInfo$2$3;->this$1:Lcom/openvehicles/OVMS/TabInfo$2;

    #getter for: Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;
    invoke-static {v4}, Lcom/openvehicles/OVMS/TabInfo$2;->access$0(Lcom/openvehicles/OVMS/TabInfo$2;)Lcom/openvehicles/OVMS/TabInfo;

    move-result-object v4

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v4}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v4

    iget-object v4, v4, Lcom/openvehicles/OVMS/CarData;->Data_CarModuleFirmwareVersion:Ljava/lang/String;

    aput-object v4, v3, v1

    const/16 v1, 0x9

    .line 484
    iget-object v4, p0, Lcom/openvehicles/OVMS/TabInfo$2$3;->this$1:Lcom/openvehicles/OVMS/TabInfo$2;

    #getter for: Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;
    invoke-static {v4}, Lcom/openvehicles/OVMS/TabInfo$2;->access$0(Lcom/openvehicles/OVMS/TabInfo$2;)Lcom/openvehicles/OVMS/TabInfo;

    move-result-object v4

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v4}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v4

    iget-object v4, v4, Lcom/openvehicles/OVMS/CarData;->Data_OVMSServerFirmwareVersion:Ljava/lang/String;

    aput-object v4, v3, v1

    .line 467
    invoke-static {v2, v3}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v1

    .line 466
    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v1

    .line 485
    const-string v2, "Vehicle Information"

    invoke-virtual {v1, v2}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v1

    .line 486
    invoke-virtual {v1, v5}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    move-result-object v1

    .line 487
    const-string v2, "Close"

    .line 488
    new-instance v3, Lcom/openvehicles/OVMS/TabInfo$2$3$1;

    invoke-direct {v3, p0}, Lcom/openvehicles/OVMS/TabInfo$2$3$1;-><init>(Lcom/openvehicles/OVMS/TabInfo$2$3;)V

    .line 487
    invoke-virtual {v1, v2, v3}, Landroid/app/AlertDialog$Builder;->setPositiveButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 494
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo$2$3;->this$1:Lcom/openvehicles/OVMS/TabInfo$2;

    #getter for: Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabInfo$2;->access$0(Lcom/openvehicles/OVMS/TabInfo$2;)Lcom/openvehicles/OVMS/TabInfo;

    move-result-object v1

    invoke-virtual {v0}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v2

    #setter for: Lcom/openvehicles/OVMS/TabInfo;->softwareInformation:Landroid/app/AlertDialog;
    invoke-static {v1, v2}, Lcom/openvehicles/OVMS/TabInfo;->access$8(Lcom/openvehicles/OVMS/TabInfo;Landroid/app/AlertDialog;)V

    .line 495
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo$2$3;->this$1:Lcom/openvehicles/OVMS/TabInfo$2;

    #getter for: Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabInfo$2;->access$0(Lcom/openvehicles/OVMS/TabInfo$2;)Lcom/openvehicles/OVMS/TabInfo;

    move-result-object v1

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->softwareInformation:Landroid/app/AlertDialog;
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabInfo;->access$9(Lcom/openvehicles/OVMS/TabInfo;)Landroid/app/AlertDialog;

    move-result-object v1

    invoke-virtual {v1}, Landroid/app/AlertDialog;->show()V

    .line 496
    return-void

    .line 470
    :cond_0
    const-string v1, "OFF"

    goto/16 :goto_0

    .line 474
    :cond_1
    const-string v1, "DISENGAGED"

    goto/16 :goto_1

    .line 476
    :cond_2
    const-string v1, "OFF"

    goto/16 :goto_2

    .line 478
    :cond_3
    const-string v1, "OFF"

    goto/16 :goto_3

    .line 480
    :cond_4
    const-string v1, "OFF"

    goto :goto_4

    .line 482
    :cond_5
    const-string v1, "(searching...)"

    goto :goto_5
.end method
