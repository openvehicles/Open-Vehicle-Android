.class Lcom/openvehicles/OVMS/TabMap$18;
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
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabMap$18;->this$0:Lcom/openvehicles/OVMS/TabMap;

    iput-object p2, p0, Lcom/openvehicles/OVMS/TabMap$18;->val$groupCar:Lcom/openvehicles/OVMS/CarData_Group;

    iput-object p3, p0, Lcom/openvehicles/OVMS/TabMap$18;->val$spin:Landroid/widget/Spinner;

    .line 441
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 3
    .parameter "dialog"
    .parameter "id"

    .prologue
    .line 445
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$18;->val$groupCar:Lcom/openvehicles/OVMS/CarData_Group;

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData_Group;->VehicleImageDrawable:Ljava/lang/String;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$18;->val$groupCar:Lcom/openvehicles/OVMS/CarData_Group;

    iget-object v1, v0, Lcom/openvehicles/OVMS/CarData_Group;->VehicleImageDrawable:Ljava/lang/String;

    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$18;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->availableCarColors:Ljava/util/ArrayList;
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabMap;->access$29(Lcom/openvehicles/OVMS/TabMap;)Ljava/util/ArrayList;

    move-result-object v0

    iget-object v2, p0, Lcom/openvehicles/OVMS/TabMap$18;->val$spin:Landroid/widget/Spinner;

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

    .line 447
    :cond_0
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap$18;->val$groupCar:Lcom/openvehicles/OVMS/CarData_Group;

    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$18;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->availableCarColors:Ljava/util/ArrayList;
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabMap;->access$29(Lcom/openvehicles/OVMS/TabMap;)Ljava/util/ArrayList;

    move-result-object v0

    iget-object v2, p0, Lcom/openvehicles/OVMS/TabMap$18;->val$spin:Landroid/widget/Spinner;

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

    .line 448
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$18;->this$0:Lcom/openvehicles/OVMS/TabMap;

    invoke-virtual {v0}, Lcom/openvehicles/OVMS/TabMap;->getParent()Landroid/app/Activity;

    move-result-object v0

    check-cast v0, Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v0}, Lcom/openvehicles/OVMS/OVMSActivity;->saveCars()V

    .line 451
    :cond_1
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$18;->this$0:Lcom/openvehicles/OVMS/TabMap;

    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "Locating "

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    iget-object v2, p0, Lcom/openvehicles/OVMS/TabMap$18;->val$groupCar:Lcom/openvehicles/OVMS/CarData_Group;

    iget-object v2, v2, Lcom/openvehicles/OVMS/CarData_Group;->VehicleID:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    const/4 v2, 0x0

    invoke-static {v0, v1, v2}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v0

    invoke-virtual {v0}, Landroid/widget/Toast;->show()V

    .line 452
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$18;->this$0:Lcom/openvehicles/OVMS/TabMap;

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap;->centeringMode:Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;

    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap$18;->val$groupCar:Lcom/openvehicles/OVMS/CarData_Group;

    iget-object v1, v1, Lcom/openvehicles/OVMS/CarData_Group;->VehicleID:Ljava/lang/String;

    iput-object v1, v0, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;->GroupCar_VehicleID:Ljava/lang/String;

    .line 453
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$18;->this$0:Lcom/openvehicles/OVMS/TabMap;

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap;->centeringMode:Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;

    const/4 v1, 0x5

    invoke-virtual {v0, v1}, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;->setMode(I)V

    .line 455
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$18;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->refreshUIHandler:Landroid/os/Handler;
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabMap;->access$22(Lcom/openvehicles/OVMS/TabMap;)Landroid/os/Handler;

    move-result-object v0

    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap$18;->this$0:Lcom/openvehicles/OVMS/TabMap;

    iget-object v1, v1, Lcom/openvehicles/OVMS/TabMap;->initializeMapCentering:Ljava/lang/Runnable;

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 457
    invoke-interface {p1}, Landroid/content/DialogInterface;->dismiss()V

    .line 458
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$18;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #calls: Lcom/openvehicles/OVMS/TabMap;->hidePopup()V
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabMap;->access$17(Lcom/openvehicles/OVMS/TabMap;)V

    .line 459
    return-void
.end method
