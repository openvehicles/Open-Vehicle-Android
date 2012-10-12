.class Lcom/openvehicles/OVMS/TabMap$1;
.super Ljava/lang/Object;
.source "TabMap.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/openvehicles/OVMS/TabMap;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/openvehicles/OVMS/TabMap;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/TabMap;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabMap$1;->this$0:Lcom/openvehicles/OVMS/TabMap;

    .line 536
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 4

    .prologue
    .line 539
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$1;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #calls: Lcom/openvehicles/OVMS/TabMap;->updateLastUpdatedView()V
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabMap;->access$0(Lcom/openvehicles/OVMS/TabMap;)V

    .line 542
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$1;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->lastUpdateTimerHandler:Landroid/os/Handler;
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabMap;->access$1(Lcom/openvehicles/OVMS/TabMap;)Landroid/os/Handler;

    move-result-object v0

    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap$1;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->lastUpdateTimer:Ljava/lang/Runnable;
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabMap;->access$2(Lcom/openvehicles/OVMS/TabMap;)Ljava/lang/Runnable;

    move-result-object v1

    const-wide/16 v2, 0x1388

    invoke-virtual {v0, v1, v2, v3}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    .line 543
    return-void
.end method
