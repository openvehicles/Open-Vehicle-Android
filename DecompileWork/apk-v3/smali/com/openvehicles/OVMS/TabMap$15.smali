.class Lcom/openvehicles/OVMS/TabMap$15;
.super Ljava/lang/Object;
.source "TabMap.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


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
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabMap$15;->this$0:Lcom/openvehicles/OVMS/TabMap;

    .line 212
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 3
    .parameter "v"

    .prologue
    .line 214
    check-cast p1, Landroid/widget/RadioButton;

    .end local p1
    invoke-virtual {p1}, Landroid/widget/RadioButton;->isChecked()Z

    move-result v0

    if-nez v0, :cond_0

    .line 231
    :goto_0
    return-void

    .line 218
    :cond_0
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$15;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->mapOverlays:Ljava/util/List;
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabMap;->access$16(Lcom/openvehicles/OVMS/TabMap;)Ljava/util/List;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v0

    const/4 v1, 0x3

    if-le v0, v1, :cond_1

    .line 219
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$15;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #calls: Lcom/openvehicles/OVMS/TabMap;->clearRoute()V
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabMap;->access$25(Lcom/openvehicles/OVMS/TabMap;)V

    .line 222
    :cond_1
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$15;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->lastKnownDeviceGeoPoint:Lcom/google/android/maps/GeoPoint;
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabMap;->access$24(Lcom/openvehicles/OVMS/TabMap;)Lcom/google/android/maps/GeoPoint;

    move-result-object v0

    if-nez v0, :cond_2

    .line 223
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$15;->this$0:Lcom/openvehicles/OVMS/TabMap;

    .line 224
    const-string v1, "Waiting for device location..."

    .line 225
    const/4 v2, 0x0

    .line 223
    invoke-static {v0, v1, v2}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v0

    .line 225
    invoke-virtual {v0}, Landroid/widget/Toast;->show()V

    goto :goto_0

    .line 228
    :cond_2
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$15;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #calls: Lcom/openvehicles/OVMS/TabMap;->planRoute()V
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabMap;->access$26(Lcom/openvehicles/OVMS/TabMap;)V

    .line 230
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$15;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #calls: Lcom/openvehicles/OVMS/TabMap;->hidePopup()V
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabMap;->access$17(Lcom/openvehicles/OVMS/TabMap;)V

    goto :goto_0
.end method
