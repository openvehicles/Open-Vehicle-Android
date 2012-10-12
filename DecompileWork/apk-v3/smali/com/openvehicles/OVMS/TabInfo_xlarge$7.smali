.class Lcom/openvehicles/OVMS/TabInfo_xlarge$7;
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
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$7;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    .line 114
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 3
    .parameter "arg0"

    .prologue
    .line 117
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$7;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    .line 118
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$7;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    invoke-virtual {v0}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->getParent()Landroid/app/Activity;

    move-result-object v0

    check-cast v0, Lcom/openvehicles/OVMS/OVMSActivity;

    const/4 v2, 0x0

    .line 117
    invoke-static {v1, v0, v2}, Lcom/openvehicles/OVMS/ServerCommands;->SetChargeMode(Landroid/content/Context;Lcom/openvehicles/OVMS/OVMSActivity;Landroid/widget/Toast;)Landroid/app/AlertDialog;

    .line 119
    return-void
.end method
