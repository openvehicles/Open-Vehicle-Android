.class Lcom/openvehicles/OVMS/TabInfo_xlarge$2;
.super Landroid/os/Handler;
.source "TabInfo_xlarge.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/openvehicles/OVMS/TabInfo_xlarge;
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
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$2;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    .line 425
    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 1
    .parameter "msg"

    .prologue
    .line 427
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$2;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #calls: Lcom/openvehicles/OVMS/TabInfo_xlarge;->updateLastUpdatedView()V
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$0(Lcom/openvehicles/OVMS/TabInfo_xlarge;)V

    .line 429
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$2;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #calls: Lcom/openvehicles/OVMS/TabInfo_xlarge;->updateInfoUI()V
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$3(Lcom/openvehicles/OVMS/TabInfo_xlarge;)V

    .line 430
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$2;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #calls: Lcom/openvehicles/OVMS/TabInfo_xlarge;->updateCarLayoutUI()V
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$4(Lcom/openvehicles/OVMS/TabInfo_xlarge;)V

    .line 431
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$2;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #calls: Lcom/openvehicles/OVMS/TabInfo_xlarge;->updateMapUI()V
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$5(Lcom/openvehicles/OVMS/TabInfo_xlarge;)V

    .line 432
    return-void
.end method
