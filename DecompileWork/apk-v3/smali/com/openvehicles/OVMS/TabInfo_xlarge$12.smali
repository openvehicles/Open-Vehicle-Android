.class Lcom/openvehicles/OVMS/TabInfo_xlarge$12;
.super Ljava/lang/Object;
.source "TabInfo_xlarge.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/TabInfo_xlarge;->initCarLayoutUI()V
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
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$12;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    .line 258
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 4
    .parameter "arg0"

    .prologue
    .line 261
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$12;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    invoke-virtual {v0}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->isFinishing()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 267
    :goto_0
    return-void

    .line 264
    :cond_0
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$12;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    .line 265
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$12;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    invoke-virtual {v0}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->getParent()Landroid/app/Activity;

    move-result-object v0

    check-cast v0, Lcom/openvehicles/OVMS/OVMSActivity;

    const/4 v3, 0x0

    .line 266
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$12;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$6(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v1

    iget-boolean v1, v1, Lcom/openvehicles/OVMS/CarData;->Data_ValetON:Z

    if-eqz v1, :cond_1

    const/4 v1, 0x0

    .line 264
    :goto_1
    invoke-static {v2, v0, v3, v1}, Lcom/openvehicles/OVMS/ServerCommands;->ValetModeOnOff(Landroid/content/Context;Lcom/openvehicles/OVMS/OVMSActivity;Landroid/widget/Toast;Z)Landroid/app/AlertDialog;

    goto :goto_0

    .line 266
    :cond_1
    const/4 v1, 0x1

    goto :goto_1
.end method
