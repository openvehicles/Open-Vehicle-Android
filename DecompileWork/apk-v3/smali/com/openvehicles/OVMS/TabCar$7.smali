.class Lcom/openvehicles/OVMS/TabCar$7;
.super Ljava/lang/Object;
.source "TabCar.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/TabCar;->initUI()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/openvehicles/OVMS/TabCar;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/TabCar;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabCar$7;->this$0:Lcom/openvehicles/OVMS/TabCar;

    .line 99
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 4
    .parameter "arg0"

    .prologue
    .line 102
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabCar$7;->this$0:Lcom/openvehicles/OVMS/TabCar;

    invoke-virtual {v0}, Lcom/openvehicles/OVMS/TabCar;->isFinishing()Z

    move-result v0

    if-nez v0, :cond_0

    iget-object v0, p0, Lcom/openvehicles/OVMS/TabCar$7;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v0

    iget-boolean v0, v0, Lcom/openvehicles/OVMS/CarData;->Data_CoolingPumpON_DoorState3:Z

    if-eqz v0, :cond_1

    .line 107
    :cond_0
    :goto_0
    return-void

    .line 105
    :cond_1
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabCar$7;->this$0:Lcom/openvehicles/OVMS/TabCar;

    .line 106
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabCar$7;->this$0:Lcom/openvehicles/OVMS/TabCar;

    invoke-virtual {v0}, Lcom/openvehicles/OVMS/TabCar;->getParent()Landroid/app/Activity;

    move-result-object v0

    check-cast v0, Lcom/openvehicles/OVMS/OVMSActivity;

    const/4 v2, 0x0

    const/4 v3, 0x1

    .line 105
    invoke-static {v1, v0, v2, v3}, Lcom/openvehicles/OVMS/ServerCommands;->WakeUp(Landroid/content/Context;Lcom/openvehicles/OVMS/OVMSActivity;Landroid/widget/Toast;Z)Landroid/app/AlertDialog;

    goto :goto_0
.end method
