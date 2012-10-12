.class Lcom/openvehicles/OVMS/OVMSActivity$3$1;
.super Ljava/lang/Object;
.source "OVMSActivity.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/OVMSActivity$3;->run()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$1:Lcom/openvehicles/OVMS/OVMSActivity$3;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/OVMSActivity$3;)V
    .locals 0
    .parameter

    .prologue
    .line 230
    iput-object p1, p0, Lcom/openvehicles/OVMS/OVMSActivity$3$1;->this$1:Lcom/openvehicles/OVMS/OVMSActivity$3;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 2
    .parameter "dialog"
    .parameter "id"

    .prologue
    .line 233
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity$3$1;->this$1:Lcom/openvehicles/OVMS/OVMSActivity$3;

    iget-object v0, v0, Lcom/openvehicles/OVMS/OVMSActivity$3;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v0}, Lcom/openvehicles/OVMS/OVMSActivity;->getTabHost()Landroid/widget/TabHost;

    move-result-object v0

    const-string v1, "tabCars"

    invoke-virtual {v0, v1}, Landroid/widget/TabHost;->setCurrentTabByTag(Ljava/lang/String;)V

    .line 235
    return-void
.end method
