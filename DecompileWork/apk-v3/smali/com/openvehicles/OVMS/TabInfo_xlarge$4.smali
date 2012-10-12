.class Lcom/openvehicles/OVMS/TabInfo_xlarge$4;
.super Landroid/os/Handler;
.source "TabInfo_xlarge.java"


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
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$4;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    .line 932
    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 5
    .parameter "msg"

    .prologue
    const/4 v4, -0x2

    .line 934
    const-string v2, "Tab"

    const-string v3, "Relayout TabInfo_xlarge activity"

    invoke-static {v2, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 935
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$4;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    iget-object v3, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$4;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    invoke-virtual {v3}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->getResources()Landroid/content/res/Resources;

    move-result-object v3

    invoke-virtual {v3}, Landroid/content/res/Resources;->getConfiguration()Landroid/content/res/Configuration;

    move-result-object v3

    iget v3, v3, Landroid/content/res/Configuration;->orientation:I

    iput v3, v2, Lcom/openvehicles/OVMS/TabInfo_xlarge;->CurrentScreenOrientation:I

    .line 937
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$4;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    const v3, 0x7f090059

    invoke-virtual {v2, v3}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/TableLayout;

    .line 938
    .local v1, tabInfoXLTableVehicleInfo:Landroid/widget/TableLayout;
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$4;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    iget v2, v2, Lcom/openvehicles/OVMS/TabInfo_xlarge;->CurrentScreenOrientation:I

    const/4 v3, 0x2

    if-ne v2, v3, :cond_0

    .line 939
    new-instance v2, Landroid/widget/LinearLayout$LayoutParams;

    invoke-direct {v2, v4, v4}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    invoke-virtual {v1, v2}, Landroid/widget/TableLayout;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 942
    :goto_0
    invoke-virtual {v1}, Landroid/widget/TableLayout;->invalidate()V

    .line 944
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$4;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    const v3, 0x7f09007c

    invoke-virtual {v2, v3}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/LinearLayout;

    .line 945
    .local v0, hotspot:Landroid/widget/LinearLayout;
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$4;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    iget v2, v2, Lcom/openvehicles/OVMS/TabInfo_xlarge;->CurrentScreenOrientation:I

    const/4 v3, 0x1

    if-ne v2, v3, :cond_1

    const/16 v2, 0x8

    :goto_1
    invoke-virtual {v0, v2}, Landroid/widget/LinearLayout;->setVisibility(I)V

    .line 946
    invoke-virtual {v0}, Landroid/widget/LinearLayout;->invalidate()V

    .line 951
    return-void

    .line 941
    .end local v0           #hotspot:Landroid/widget/LinearLayout;
    :cond_0
    new-instance v2, Landroid/widget/LinearLayout$LayoutParams;

    const/16 v3, 0xf0

    invoke-direct {v2, v3, v4}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    invoke-virtual {v1, v2}, Landroid/widget/TableLayout;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    goto :goto_0

    .line 945
    .restart local v0       #hotspot:Landroid/widget/LinearLayout;
    :cond_1
    const/4 v2, 0x0

    goto :goto_1
.end method
