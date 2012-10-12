.class Lcom/openvehicles/OVMS/TabMap$11;
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
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabMap$11;->this$0:Lcom/openvehicles/OVMS/TabMap;

    .line 143
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 3
    .parameter "v"

    .prologue
    .line 145
    check-cast p1, Landroid/widget/RadioButton;

    .end local p1
    invoke-virtual {p1}, Landroid/widget/RadioButton;->isChecked()Z

    move-result v0

    if-nez v0, :cond_0

    .line 151
    :goto_0
    return-void

    .line 147
    :cond_0
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$11;->this$0:Lcom/openvehicles/OVMS/TabMap;

    const-string v1, "Car Location"

    const/4 v2, 0x0

    invoke-static {v0, v1, v2}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v0

    invoke-virtual {v0}, Landroid/widget/Toast;->show()V

    .line 148
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$11;->this$0:Lcom/openvehicles/OVMS/TabMap;

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap;->centeringMode:Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;

    const/4 v1, 0x2

    invoke-virtual {v0, v1}, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;->setMode(I)V

    .line 149
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$11;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->refreshUIHandler:Landroid/os/Handler;
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabMap;->access$22(Lcom/openvehicles/OVMS/TabMap;)Landroid/os/Handler;

    move-result-object v0

    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap$11;->this$0:Lcom/openvehicles/OVMS/TabMap;

    iget-object v1, v1, Lcom/openvehicles/OVMS/TabMap;->initializeMapCentering:Ljava/lang/Runnable;

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 150
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$11;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #calls: Lcom/openvehicles/OVMS/TabMap;->hidePopup()V
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabMap;->access$17(Lcom/openvehicles/OVMS/TabMap;)V

    goto :goto_0
.end method
