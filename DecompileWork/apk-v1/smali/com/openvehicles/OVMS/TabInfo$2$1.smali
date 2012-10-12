.class Lcom/openvehicles/OVMS/TabInfo$2$1;
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
    .line 140
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabInfo$2$1;->this$1:Lcom/openvehicles/OVMS/TabInfo$2;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 6
    .parameter "v"

    .prologue
    const/4 v5, 0x0

    .line 143
    new-instance v0, Landroid/app/AlertDialog$Builder;

    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo$2$1;->this$1:Lcom/openvehicles/OVMS/TabInfo$2;

    iget-object v1, v1, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    invoke-direct {v0, v1}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 145
    .local v0, builder:Landroid/app/AlertDialog$Builder;
    const-string v2, "Power: %s\nVIN: %s\nGSM Signal: %s\nHandbrake: %s\n\nCar Module: %s\nOVMS Server: %s"

    const/4 v1, 0x6

    new-array v3, v1, [Ljava/lang/Object;

    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo$2$1;->this$1:Lcom/openvehicles/OVMS/TabInfo$2;

    iget-object v1, v1, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabInfo;->access$300(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v1

    iget-boolean v1, v1, Lcom/openvehicles/OVMS/CarData;->Data_CarPoweredON:Z

    if-eqz v1, :cond_0

    const-string v1, "ON"

    :goto_0
    aput-object v1, v3, v5

    const/4 v1, 0x1

    iget-object v4, p0, Lcom/openvehicles/OVMS/TabInfo$2$1;->this$1:Lcom/openvehicles/OVMS/TabInfo$2;

    iget-object v4, v4, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v4}, Lcom/openvehicles/OVMS/TabInfo;->access$300(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v4

    iget-object v4, v4, Lcom/openvehicles/OVMS/CarData;->Data_VIN:Ljava/lang/String;

    aput-object v4, v3, v1

    const/4 v1, 0x2

    iget-object v4, p0, Lcom/openvehicles/OVMS/TabInfo$2$1;->this$1:Lcom/openvehicles/OVMS/TabInfo$2;

    iget-object v4, v4, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v4}, Lcom/openvehicles/OVMS/TabInfo;->access$300(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v4

    iget-object v4, v4, Lcom/openvehicles/OVMS/CarData;->Data_CarModuleGSMSignalLevel:Ljava/lang/String;

    aput-object v4, v3, v1

    const/4 v4, 0x3

    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo$2$1;->this$1:Lcom/openvehicles/OVMS/TabInfo$2;

    iget-object v1, v1, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabInfo;->access$300(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v1

    iget-boolean v1, v1, Lcom/openvehicles/OVMS/CarData;->Data_HandBrakeApplied:Z

    if-eqz v1, :cond_1

    const-string v1, "ENGAGED"

    :goto_1
    aput-object v1, v3, v4

    const/4 v1, 0x4

    iget-object v4, p0, Lcom/openvehicles/OVMS/TabInfo$2$1;->this$1:Lcom/openvehicles/OVMS/TabInfo$2;

    iget-object v4, v4, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v4}, Lcom/openvehicles/OVMS/TabInfo;->access$300(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v4

    iget-object v4, v4, Lcom/openvehicles/OVMS/CarData;->Data_CarModuleFirmwareVersion:Ljava/lang/String;

    aput-object v4, v3, v1

    const/4 v1, 0x5

    iget-object v4, p0, Lcom/openvehicles/OVMS/TabInfo$2$1;->this$1:Lcom/openvehicles/OVMS/TabInfo$2;

    iget-object v4, v4, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v4}, Lcom/openvehicles/OVMS/TabInfo;->access$300(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v4

    iget-object v4, v4, Lcom/openvehicles/OVMS/CarData;->Data_OVMSServerFirmwareVersion:Ljava/lang/String;

    aput-object v4, v3, v1

    invoke-static {v2, v3}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v1

    const-string v2, "Software Information"

    invoke-virtual {v1, v2}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v1

    invoke-virtual {v1, v5}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    move-result-object v1

    const-string v2, "Close"

    new-instance v3, Lcom/openvehicles/OVMS/TabInfo$2$1$1;

    invoke-direct {v3, p0}, Lcom/openvehicles/OVMS/TabInfo$2$1$1;-><init>(Lcom/openvehicles/OVMS/TabInfo$2$1;)V

    invoke-virtual {v1, v2, v3}, Landroid/app/AlertDialog$Builder;->setPositiveButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 155
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo$2$1;->this$1:Lcom/openvehicles/OVMS/TabInfo$2;

    iget-object v1, v1, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    invoke-virtual {v0}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v2

    #setter for: Lcom/openvehicles/OVMS/TabInfo;->softwareInformation:Landroid/app/AlertDialog;
    invoke-static {v1, v2}, Lcom/openvehicles/OVMS/TabInfo;->access$402(Lcom/openvehicles/OVMS/TabInfo;Landroid/app/AlertDialog;)Landroid/app/AlertDialog;

    .line 156
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo$2$1;->this$1:Lcom/openvehicles/OVMS/TabInfo$2;

    iget-object v1, v1, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->softwareInformation:Landroid/app/AlertDialog;
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabInfo;->access$400(Lcom/openvehicles/OVMS/TabInfo;)Landroid/app/AlertDialog;

    move-result-object v1

    invoke-virtual {v1}, Landroid/app/AlertDialog;->show()V

    .line 157
    return-void

    .line 145
    :cond_0
    const-string v1, "OFF"

    goto :goto_0

    :cond_1
    const-string v1, "DISENGAGED"

    goto :goto_1
.end method
