.class Lcom/openvehicles/OVMS/TabMap$14;
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
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabMap$14;->this$0:Lcom/openvehicles/OVMS/TabMap;

    .line 188
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 4
    .parameter "v"

    .prologue
    .line 190
    check-cast p1, Landroid/widget/RadioButton;

    .end local p1
    invoke-virtual {p1}, Landroid/widget/RadioButton;->isChecked()Z

    move-result v1

    if-nez v1, :cond_0

    .line 207
    :goto_0
    return-void

    .line 194
    :cond_0
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap$14;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->mapOverlays:Ljava/util/List;
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabMap;->access$16(Lcom/openvehicles/OVMS/TabMap;)Ljava/util/List;

    move-result-object v1

    invoke-interface {v1}, Ljava/util/List;->size()I

    move-result v1

    const/4 v2, 0x3

    if-le v1, v2, :cond_1

    .line 195
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap$14;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #calls: Lcom/openvehicles/OVMS/TabMap;->clearRoute()V
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabMap;->access$25(Lcom/openvehicles/OVMS/TabMap;)V

    .line 198
    :cond_1
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap$14;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->lastKnownDeviceGeoPoint:Lcom/google/android/maps/GeoPoint;
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabMap;->access$24(Lcom/openvehicles/OVMS/TabMap;)Lcom/google/android/maps/GeoPoint;

    move-result-object v1

    if-nez v1, :cond_2

    .line 199
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap$14;->this$0:Lcom/openvehicles/OVMS/TabMap;

    const v2, 0x7f09008d

    invoke-virtual {v1, v2}, Lcom/openvehicles/OVMS/TabMap;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/RadioButton;

    .line 200
    .local v0, routeOff:Landroid/widget/RadioButton;
    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Landroid/widget/RadioButton;->setChecked(Z)V

    .line 201
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap$14;->this$0:Lcom/openvehicles/OVMS/TabMap;

    const-string v2, "Waiting for device location..."

    const/4 v3, 0x0

    invoke-static {v1, v2, v3}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v1

    invoke-virtual {v1}, Landroid/widget/Toast;->show()V

    goto :goto_0

    .line 204
    .end local v0           #routeOff:Landroid/widget/RadioButton;
    :cond_2
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap$14;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #calls: Lcom/openvehicles/OVMS/TabMap;->planRoute()V
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabMap;->access$26(Lcom/openvehicles/OVMS/TabMap;)V

    .line 206
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap$14;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #calls: Lcom/openvehicles/OVMS/TabMap;->hidePopup()V
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabMap;->access$17(Lcom/openvehicles/OVMS/TabMap;)V

    goto :goto_0
.end method
