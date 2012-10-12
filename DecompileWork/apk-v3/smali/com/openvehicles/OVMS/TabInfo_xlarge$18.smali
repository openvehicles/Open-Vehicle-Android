.class Lcom/openvehicles/OVMS/TabInfo_xlarge$18;
.super Ljava/lang/Object;
.source "TabInfo_xlarge.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/TabInfo_xlarge;->updateInfoUI()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/TabInfo_xlarge;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$18;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    .line 732
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 6
    .parameter "v"

    .prologue
    const/4 v5, 0x1

    .line 735
    new-instance v0, Landroid/app/AlertDialog$Builder;

    .line 736
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$18;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    .line 735
    invoke-direct {v0, v1}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 739
    .local v0, builder:Landroid/app/AlertDialog$Builder;
    const-string v2, "Power: %s\nVIN: %s\nGSM Signal: %s\nHandbrake: %s\nValet: %s\nLock: %s\nCooling Pump: %s\nGPS: %s\n\nCar Module: %s\nOVMS Server: %s"

    const/16 v1, 0xa

    new-array v3, v1, [Ljava/lang/Object;

    const/4 v4, 0x0

    .line 741
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$18;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$6(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v1

    iget-boolean v1, v1, Lcom/openvehicles/OVMS/CarData;->Data_CarPoweredON:Z

    if-eqz v1, :cond_0

    const-string v1, "ON"

    :goto_0
    aput-object v1, v3, v4

    .line 742
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$18;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$6(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v1

    iget-object v1, v1, Lcom/openvehicles/OVMS/CarData;->Data_VIN:Ljava/lang/String;

    aput-object v1, v3, v5

    const/4 v1, 0x2

    .line 743
    iget-object v4, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$18;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v4}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$6(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v4

    iget-object v4, v4, Lcom/openvehicles/OVMS/CarData;->Data_CarModuleGSMSignalLevel:Ljava/lang/String;

    aput-object v4, v3, v1

    const/4 v4, 0x3

    .line 744
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$18;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$6(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v1

    iget-boolean v1, v1, Lcom/openvehicles/OVMS/CarData;->Data_HandBrakeApplied:Z

    if-eqz v1, :cond_1

    const-string v1, "ENGAGED"

    .line 745
    :goto_1
    aput-object v1, v3, v4

    const/4 v4, 0x4

    .line 746
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$18;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$6(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v1

    iget-boolean v1, v1, Lcom/openvehicles/OVMS/CarData;->Data_ValetON:Z

    if-eqz v1, :cond_2

    const-string v1, "ON"

    :goto_2
    aput-object v1, v3, v4

    const/4 v4, 0x5

    .line 747
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$18;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$6(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v1

    iget-boolean v1, v1, Lcom/openvehicles/OVMS/CarData;->Data_PINLocked:Z

    if-eqz v1, :cond_3

    const-string v1, "ON"

    :goto_3
    aput-object v1, v3, v4

    const/4 v4, 0x6

    .line 748
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$18;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$6(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v1

    iget-boolean v1, v1, Lcom/openvehicles/OVMS/CarData;->Data_CoolingPumpON_DoorState3:Z

    if-eqz v1, :cond_4

    const-string v1, "ON"

    .line 749
    :goto_4
    aput-object v1, v3, v4

    const/4 v4, 0x7

    .line 750
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$18;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$6(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v1

    iget-boolean v1, v1, Lcom/openvehicles/OVMS/CarData;->Data_GPSLocked:Z

    if-eqz v1, :cond_5

    const-string v1, "LOCKED"

    .line 751
    :goto_5
    aput-object v1, v3, v4

    const/16 v1, 0x8

    .line 752
    iget-object v4, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$18;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v4}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$6(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v4

    iget-object v4, v4, Lcom/openvehicles/OVMS/CarData;->Data_CarModuleFirmwareVersion:Ljava/lang/String;

    aput-object v4, v3, v1

    const/16 v1, 0x9

    .line 753
    iget-object v4, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$18;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v4}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$6(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v4

    iget-object v4, v4, Lcom/openvehicles/OVMS/CarData;->Data_OVMSServerFirmwareVersion:Ljava/lang/String;

    aput-object v4, v3, v1

    .line 738
    invoke-static {v2, v3}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v1

    .line 737
    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v1

    .line 754
    const-string v2, "Vehicle Information"

    invoke-virtual {v1, v2}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v1

    .line 755
    invoke-virtual {v1, v5}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    move-result-object v1

    .line 756
    const-string v2, "Close"

    .line 757
    new-instance v3, Lcom/openvehicles/OVMS/TabInfo_xlarge$18$1;

    invoke-direct {v3, p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge$18$1;-><init>(Lcom/openvehicles/OVMS/TabInfo_xlarge$18;)V

    .line 756
    invoke-virtual {v1, v2, v3}, Landroid/app/AlertDialog$Builder;->setPositiveButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 763
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$18;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    invoke-virtual {v0}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v2

    #setter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->softwareInformation:Landroid/app/AlertDialog;
    invoke-static {v1, v2}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$17(Lcom/openvehicles/OVMS/TabInfo_xlarge;Landroid/app/AlertDialog;)V

    .line 764
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$18;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->softwareInformation:Landroid/app/AlertDialog;
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$18(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Landroid/app/AlertDialog;

    move-result-object v1

    invoke-virtual {v1}, Landroid/app/AlertDialog;->show()V

    .line 765
    return-void

    .line 741
    :cond_0
    const-string v1, "OFF"

    goto/16 :goto_0

    .line 745
    :cond_1
    const-string v1, "DISENGAGED"

    goto/16 :goto_1

    .line 746
    :cond_2
    const-string v1, "OFF"

    goto :goto_2

    .line 747
    :cond_3
    const-string v1, "OFF"

    goto :goto_3

    .line 749
    :cond_4
    const-string v1, "OFF"

    goto :goto_4

    .line 751
    :cond_5
    const-string v1, "(searching...)"

    goto :goto_5
.end method
