.class Lcom/openvehicles/OVMS/TabMiscFeatures$1;
.super Landroid/os/Handler;
.source "TabMiscFeatures.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/openvehicles/OVMS/TabMiscFeatures;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/openvehicles/OVMS/TabMiscFeatures;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/TabMiscFeatures;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabMiscFeatures$1;->this$0:Lcom/openvehicles/OVMS/TabMiscFeatures;

    .line 60
    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 2
    .parameter "msg"

    .prologue
    .line 62
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMiscFeatures$1;->this$0:Lcom/openvehicles/OVMS/TabMiscFeatures;

    invoke-virtual {v1}, Lcom/openvehicles/OVMS/TabMiscFeatures;->getLocalActivityManager()Landroid/app/LocalActivityManager;

    move-result-object v1

    invoke-virtual {v1}, Landroid/app/LocalActivityManager;->getCurrentId()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v0

    .line 63
    .local v0, currentActivityId:Ljava/lang/String;
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMiscFeatures$1;->this$0:Lcom/openvehicles/OVMS/TabMiscFeatures;

    #calls: Lcom/openvehicles/OVMS/TabMiscFeatures;->notifyTabRefresh(Ljava/lang/String;)V
    invoke-static {v1, v0}, Lcom/openvehicles/OVMS/TabMiscFeatures;->access$0(Lcom/openvehicles/OVMS/TabMiscFeatures;Ljava/lang/String;)V

    .line 64
    return-void
.end method
