.class Lcom/openvehicles/OVMS/TabInfo$1;
.super Ljava/lang/Object;
.source "TabInfo.java"

# interfaces
.implements Ljava/lang/Runnable;


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
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabInfo$1;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    .line 202
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 4

    .prologue
    .line 205
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo$1;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #calls: Lcom/openvehicles/OVMS/TabInfo;->updateLastUpdatedView()V
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabInfo;->access$0(Lcom/openvehicles/OVMS/TabInfo;)V

    .line 208
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo$1;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->lastUpdateTimerHandler:Landroid/os/Handler;
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabInfo;->access$1(Lcom/openvehicles/OVMS/TabInfo;)Landroid/os/Handler;

    move-result-object v0

    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo$1;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->lastUpdateTimer:Ljava/lang/Runnable;
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabInfo;->access$2(Lcom/openvehicles/OVMS/TabInfo;)Ljava/lang/Runnable;

    move-result-object v1

    const-wide/16 v2, 0x1388

    invoke-virtual {v0, v1, v2, v3}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    .line 209
    return-void
.end method
