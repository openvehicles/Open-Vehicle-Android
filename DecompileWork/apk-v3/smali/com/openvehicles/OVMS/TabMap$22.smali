.class Lcom/openvehicles/OVMS/TabMap$22;
.super Ljava/lang/Thread;
.source "TabMap.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/TabMap;->planRoute()V
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
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabMap$22;->this$0:Lcom/openvehicles/OVMS/TabMap;

    .line 627
    invoke-direct {p0}, Ljava/lang/Thread;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 2

    .prologue
    .line 630
    const-string v0, "Route"

    const-string v1, "Starting routing thread"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 632
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$22;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #calls: Lcom/openvehicles/OVMS/TabMap;->updateRoute()V
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabMap;->access$30(Lcom/openvehicles/OVMS/TabMap;)V

    .line 634
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$22;->this$0:Lcom/openvehicles/OVMS/TabMap;

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap;->centeringMode:Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;

    const/4 v1, 0x3

    invoke-virtual {v0, v1}, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;->setMode(I)V

    .line 635
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$22;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->refreshUIHandler:Landroid/os/Handler;
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabMap;->access$22(Lcom/openvehicles/OVMS/TabMap;)Landroid/os/Handler;

    move-result-object v0

    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap$22;->this$0:Lcom/openvehicles/OVMS/TabMap;

    iget-object v1, v1, Lcom/openvehicles/OVMS/TabMap;->initializeMapCentering:Ljava/lang/Runnable;

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 636
    return-void
.end method
