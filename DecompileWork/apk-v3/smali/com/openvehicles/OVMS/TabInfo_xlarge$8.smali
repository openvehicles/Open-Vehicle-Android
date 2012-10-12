.class Lcom/openvehicles/OVMS/TabInfo_xlarge$8;
.super Ljava/lang/Object;
.source "TabInfo_xlarge.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/TabInfo_xlarge;->initInfoUI()V
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
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$8;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    .line 124
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 4
    .parameter "arg0"

    .prologue
    .line 127
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$8;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    .line 128
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$8;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    invoke-virtual {v0}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->getParent()Landroid/app/Activity;

    move-result-object v0

    check-cast v0, Lcom/openvehicles/OVMS/OVMSActivity;

    const/4 v2, 0x0

    .line 129
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$8;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v3}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$6(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v3

    iget v3, v3, Lcom/openvehicles/OVMS/CarData;->Data_ChargeAmpsLimit:I

    .line 127
    invoke-static {v1, v0, v2, v3}, Lcom/openvehicles/OVMS/ServerCommands;->SetChargeCurrent(Landroid/content/Context;Lcom/openvehicles/OVMS/OVMSActivity;Landroid/widget/Toast;I)Landroid/app/AlertDialog;

    .line 130
    return-void
.end method
