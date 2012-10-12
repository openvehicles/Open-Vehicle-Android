.class Lcom/openvehicles/OVMS/MyLocationOverlayCustom$1;
.super Ljava/lang/Object;
.source "MyLocationOverlayCustom.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/MyLocationOverlayCustom;-><init>(Landroid/content/Context;Lcom/google/android/maps/MapView;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/openvehicles/OVMS/MyLocationOverlayCustom;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/MyLocationOverlayCustom;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/MyLocationOverlayCustom$1;->this$0:Lcom/openvehicles/OVMS/MyLocationOverlayCustom;

    .line 46
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 4

    .prologue
    .line 50
    iget-object v0, p0, Lcom/openvehicles/OVMS/MyLocationOverlayCustom$1;->this$0:Lcom/openvehicles/OVMS/MyLocationOverlayCustom;

    #getter for: Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->mapView:Lcom/google/android/maps/MapView;
    invoke-static {v0}, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->access$0(Lcom/openvehicles/OVMS/MyLocationOverlayCustom;)Lcom/google/android/maps/MapView;

    move-result-object v0

    invoke-virtual {v0}, Lcom/google/android/maps/MapView;->invalidate()V

    .line 51
    iget-object v0, p0, Lcom/openvehicles/OVMS/MyLocationOverlayCustom$1;->this$0:Lcom/openvehicles/OVMS/MyLocationOverlayCustom;

    #getter for: Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->handler:Landroid/os/Handler;
    invoke-static {v0}, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->access$1(Lcom/openvehicles/OVMS/MyLocationOverlayCustom;)Landroid/os/Handler;

    move-result-object v0

    iget-object v1, p0, Lcom/openvehicles/OVMS/MyLocationOverlayCustom$1;->this$0:Lcom/openvehicles/OVMS/MyLocationOverlayCustom;

    #getter for: Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->overlayAnimationTask:Ljava/lang/Runnable;
    invoke-static {v1}, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->access$2(Lcom/openvehicles/OVMS/MyLocationOverlayCustom;)Ljava/lang/Runnable;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeCallbacks(Ljava/lang/Runnable;)V

    .line 52
    iget-object v0, p0, Lcom/openvehicles/OVMS/MyLocationOverlayCustom$1;->this$0:Lcom/openvehicles/OVMS/MyLocationOverlayCustom;

    #getter for: Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->handler:Landroid/os/Handler;
    invoke-static {v0}, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->access$1(Lcom/openvehicles/OVMS/MyLocationOverlayCustom;)Landroid/os/Handler;

    move-result-object v0

    iget-object v1, p0, Lcom/openvehicles/OVMS/MyLocationOverlayCustom$1;->this$0:Lcom/openvehicles/OVMS/MyLocationOverlayCustom;

    #getter for: Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->overlayAnimationTask:Ljava/lang/Runnable;
    invoke-static {v1}, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->access$2(Lcom/openvehicles/OVMS/MyLocationOverlayCustom;)Ljava/lang/Runnable;

    move-result-object v1

    const-wide/16 v2, 0x3e8

    invoke-virtual {v0, v1, v2, v3}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    .line 54
    return-void
.end method
