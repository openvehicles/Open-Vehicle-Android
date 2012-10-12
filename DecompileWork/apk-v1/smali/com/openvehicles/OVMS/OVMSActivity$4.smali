.class Lcom/openvehicles/OVMS/OVMSActivity$4;
.super Ljava/lang/Object;
.source "OVMSActivity.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/openvehicles/OVMS/OVMSActivity;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/openvehicles/OVMS/OVMSActivity;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/OVMSActivity;)V
    .locals 0
    .parameter

    .prologue
    .line 243
    iput-object p1, p0, Lcom/openvehicles/OVMS/OVMSActivity$4;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 4

    .prologue
    .line 245
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity$4;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->isLoggedIn:Z
    invoke-static {v0}, Lcom/openvehicles/OVMS/OVMSActivity;->access$100(Lcom/openvehicles/OVMS/OVMSActivity;)Z

    move-result v0

    if-eqz v0, :cond_0

    .line 246
    const-string v0, "OVMS"

    const-string v1, "Pinging server..."

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 247
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity$4;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->tcpTask:Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;
    invoke-static {v0}, Lcom/openvehicles/OVMS/OVMSActivity;->access$200(Lcom/openvehicles/OVMS/OVMSActivity;)Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;

    move-result-object v0

    invoke-virtual {v0}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Ping()V

    .line 250
    :cond_0
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity$4;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->pingServerTimerHandler:Landroid/os/Handler;
    invoke-static {v0}, Lcom/openvehicles/OVMS/OVMSActivity;->access$400(Lcom/openvehicles/OVMS/OVMSActivity;)Landroid/os/Handler;

    move-result-object v0

    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$4;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->pingServer:Ljava/lang/Runnable;
    invoke-static {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->access$300(Lcom/openvehicles/OVMS/OVMSActivity;)Ljava/lang/Runnable;

    move-result-object v1

    const-wide/32 v2, 0xea60

    invoke-virtual {v0, v1, v2, v3}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    .line 251
    return-void
.end method
