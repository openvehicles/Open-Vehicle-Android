.class Lcom/openvehicles/OVMS/TabMap$3;
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
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabMap$3;->this$0:Lcom/openvehicles/OVMS/TabMap;

    .line 835
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 1

    .prologue
    .line 837
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$3;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->myLocationOverlay:Lcom/openvehicles/OVMS/MyLocationOverlayCustom;
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabMap;->access$3(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/MyLocationOverlayCustom;

    move-result-object v0

    invoke-virtual {v0}, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->enableMyLocation()Z

    .line 838
    return-void
.end method
