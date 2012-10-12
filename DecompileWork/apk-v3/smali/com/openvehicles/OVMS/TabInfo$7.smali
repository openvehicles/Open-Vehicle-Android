.class Lcom/openvehicles/OVMS/TabInfo$7;
.super Ljava/lang/Object;
.source "TabInfo.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/TabInfo;->initUI()V
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
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabInfo$7;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    .line 125
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 4
    .parameter "arg0"

    .prologue
    .line 128
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo$7;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo$7;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    .line 129
    invoke-virtual {v0}, Lcom/openvehicles/OVMS/TabInfo;->getParent()Landroid/app/Activity;

    move-result-object v0

    check-cast v0, Lcom/openvehicles/OVMS/OVMSActivity;

    const/4 v2, 0x0

    iget-object v3, p0, Lcom/openvehicles/OVMS/TabInfo$7;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v3}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v3

    iget v3, v3, Lcom/openvehicles/OVMS/CarData;->Data_ChargeAmpsLimit:I

    .line 128
    invoke-static {v1, v0, v2, v3}, Lcom/openvehicles/OVMS/ServerCommands;->SetChargeCurrent(Landroid/content/Context;Lcom/openvehicles/OVMS/OVMSActivity;Landroid/widget/Toast;I)Landroid/app/AlertDialog;

    .line 130
    return-void
.end method
