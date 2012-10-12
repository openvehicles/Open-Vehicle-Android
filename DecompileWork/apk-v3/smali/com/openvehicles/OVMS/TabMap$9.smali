.class Lcom/openvehicles/OVMS/TabMap$9;
.super Ljava/lang/Object;
.source "TabMap.java"

# interfaces
.implements Lcom/openvehicles/OVMS/TabMap$OnCenteringModeChangedListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/TabMap;->onCreate(Landroid/os/Bundle;)V
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
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabMap$9;->this$0:Lcom/openvehicles/OVMS/TabMap;

    .line 82
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public OnCenteringModeChanged(I)V
    .locals 2
    .parameter "newMode"

    .prologue
    .line 85
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$9;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->refreshUIHandler:Landroid/os/Handler;
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabMap;->access$22(Lcom/openvehicles/OVMS/TabMap;)Landroid/os/Handler;

    move-result-object v0

    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap$9;->this$0:Lcom/openvehicles/OVMS/TabMap;

    iget-object v1, v1, Lcom/openvehicles/OVMS/TabMap;->updateCenteringOptions:Ljava/lang/Runnable;

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 86
    return-void
.end method
