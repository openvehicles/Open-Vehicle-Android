.class Lcom/openvehicles/OVMS/TabInfo_xlarge$1;
.super Ljava/lang/Object;
.source "TabInfo_xlarge.java"

# interfaces
.implements Ljava/lang/Runnable;


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
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$1;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    .line 312
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 4

    .prologue
    .line 315
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$1;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #calls: Lcom/openvehicles/OVMS/TabInfo_xlarge;->updateLastUpdatedView()V
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$0(Lcom/openvehicles/OVMS/TabInfo_xlarge;)V

    .line 318
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$1;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->lastUpdateTimerHandler:Landroid/os/Handler;
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$1(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Landroid/os/Handler;

    move-result-object v0

    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$1;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->lastUpdateTimer:Ljava/lang/Runnable;
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$2(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Ljava/lang/Runnable;

    move-result-object v1

    const-wide/16 v2, 0x1388

    invoke-virtual {v0, v1, v2, v3}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    .line 319
    return-void
.end method
