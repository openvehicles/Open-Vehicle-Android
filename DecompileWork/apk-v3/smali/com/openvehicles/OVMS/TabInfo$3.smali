.class Lcom/openvehicles/OVMS/TabInfo$3;
.super Landroid/os/Handler;
.source "TabInfo.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/openvehicles/OVMS/TabInfo;
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
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabInfo$3;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    .line 556
    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 2
    .parameter "msg"

    .prologue
    .line 558
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo$3;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    const v1, 0x7f03000e

    invoke-virtual {v0, v1}, Lcom/openvehicles/OVMS/TabInfo;->setContentView(I)V

    .line 559
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo$3;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo$3;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    invoke-virtual {v1}, Lcom/openvehicles/OVMS/TabInfo;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    invoke-virtual {v1}, Landroid/content/res/Resources;->getConfiguration()Landroid/content/res/Configuration;

    move-result-object v1

    iget v1, v1, Landroid/content/res/Configuration;->orientation:I

    iput v1, v0, Lcom/openvehicles/OVMS/TabInfo;->CurrentScreenOrientation:I

    .line 560
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo$3;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #calls: Lcom/openvehicles/OVMS/TabInfo;->initUI()V
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabInfo;->access$10(Lcom/openvehicles/OVMS/TabInfo;)V

    .line 561
    return-void
.end method
