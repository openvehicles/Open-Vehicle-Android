.class Lcom/openvehicles/OVMS/TabMap$19;
.super Ljava/lang/Object;
.source "TabMap.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/TabMap;->showGroupCarPopup(Ljava/lang/String;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/openvehicles/OVMS/TabMap;

.field private final synthetic val$groupCar:Lcom/openvehicles/OVMS/CarData_Group;

.field private final synthetic val$spin:Landroid/widget/Spinner;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/TabMap;Lcom/openvehicles/OVMS/CarData_Group;Landroid/widget/Spinner;)V
    .locals 0
    .parameter
    .parameter
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabMap$19;->this$0:Lcom/openvehicles/OVMS/TabMap;

    iput-object p2, p0, Lcom/openvehicles/OVMS/TabMap$19;->val$groupCar:Lcom/openvehicles/OVMS/CarData_Group;

    iput-object p3, p0, Lcom/openvehicles/OVMS/TabMap$19;->val$spin:Landroid/widget/Spinner;

    .line 462
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 3
    .parameter "dialog"
    .parameter "id"

    .prologue
    .line 466
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$19;->val$groupCar:Lcom/openvehicles/OVMS/CarData_Group;

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData_Group;->VehicleImageDrawable:Ljava/lang/String;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$19;->val$groupCar:Lcom/openvehicles/OVMS/CarData_Group;

    iget-object v1, v0, Lcom/openvehicles/OVMS/CarData_Group;->VehicleImageDrawable:Ljava/lang/String;

    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$19;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->availableCarColors:Ljava/util/ArrayList;
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabMap;->access$29(Lcom/openvehicles/OVMS/TabMap;)Ljava/util/ArrayList;

    move-result-object v0

    iget-object v2, p0, Lcom/openvehicles/OVMS/TabMap$19;->val$spin:Landroid/widget/Spinner;

    invoke-virtual {v2}, Landroid/widget/Spinner;->getSelectedItemPosition()I

    move-result v2

    invoke-virtual {v0, v2}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/util/HashMap;

    const-string v2, "Name"

    invoke-virtual {v0, v2}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_1

    .line 468
    :cond_0
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap$19;->val$groupCar:Lcom/openvehicles/OVMS/CarData_Group;

    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$19;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->availableCarColors:Ljava/util/ArrayList;
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabMap;->access$29(Lcom/openvehicles/OVMS/TabMap;)Ljava/util/ArrayList;

    move-result-object v0

    iget-object v2, p0, Lcom/openvehicles/OVMS/TabMap$19;->val$spin:Landroid/widget/Spinner;

    invoke-virtual {v2}, Landroid/widget/Spinner;->getSelectedItemPosition()I

    move-result v2

    invoke-virtual {v0, v2}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/util/HashMap;

    const-string v2, "Name"

    invoke-virtual {v0, v2}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v0

    iput-object v0, v1, Lcom/openvehicles/OVMS/CarData_Group;->VehicleImageDrawable:Ljava/lang/String;

    .line 469
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$19;->this$0:Lcom/openvehicles/OVMS/TabMap;

    invoke-virtual {v0}, Lcom/openvehicles/OVMS/TabMap;->getParent()Landroid/app/Activity;

    move-result-object v0

    check-cast v0, Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v0}, Lcom/openvehicles/OVMS/OVMSActivity;->saveCars()V

    .line 471
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$19;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->refreshUIHandler:Landroid/os/Handler;
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabMap;->access$22(Lcom/openvehicles/OVMS/TabMap;)Landroid/os/Handler;

    move-result-object v0

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    .line 474
    :cond_1
    invoke-interface {p1}, Landroid/content/DialogInterface;->dismiss()V

    .line 475
    return-void
.end method
