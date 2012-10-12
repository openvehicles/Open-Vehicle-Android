.class Lcom/openvehicles/OVMS/TabMap$16;
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
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabMap$16;->this$0:Lcom/openvehicles/OVMS/TabMap;

    .line 235
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 1
    .parameter "v"

    .prologue
    .line 237
    check-cast p1, Landroid/widget/RadioButton;

    .end local p1
    invoke-virtual {p1}, Landroid/widget/RadioButton;->isChecked()Z

    move-result v0

    if-nez v0, :cond_0

    .line 242
    :goto_0
    return-void

    .line 240
    :cond_0
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$16;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #calls: Lcom/openvehicles/OVMS/TabMap;->cancelRoute()V
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabMap;->access$27(Lcom/openvehicles/OVMS/TabMap;)V

    .line 241
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$16;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #calls: Lcom/openvehicles/OVMS/TabMap;->hidePopup()V
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabMap;->access$17(Lcom/openvehicles/OVMS/TabMap;)V

    goto :goto_0
.end method
