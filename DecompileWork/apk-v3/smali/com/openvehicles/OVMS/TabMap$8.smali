.class Lcom/openvehicles/OVMS/TabMap$8;
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
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabMap$8;->this$0:Lcom/openvehicles/OVMS/TabMap;

    .line 1277
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 4

    .prologue
    const/4 v3, 0x1

    .line 1282
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap$8;->this$0:Lcom/openvehicles/OVMS/TabMap;

    iget-object v1, v1, Lcom/openvehicles/OVMS/TabMap;->centeringMode:Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;

    invoke-virtual {v1}, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;->getMode()I

    move-result v1

    packed-switch v1, :pswitch_data_0

    .line 1304
    :goto_0
    return-void

    .line 1286
    :pswitch_0
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap$8;->this$0:Lcom/openvehicles/OVMS/TabMap;

    const v2, 0x7f090087

    invoke-virtual {v1, v2}, Lcom/openvehicles/OVMS/TabMap;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/RadioButton;

    .line 1287
    .local v0, option:Landroid/widget/RadioButton;
    invoke-virtual {v0, v3}, Landroid/widget/RadioButton;->setChecked(Z)V

    goto :goto_0

    .line 1290
    .end local v0           #option:Landroid/widget/RadioButton;
    :pswitch_1
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap$8;->this$0:Lcom/openvehicles/OVMS/TabMap;

    const v2, 0x7f090088

    invoke-virtual {v1, v2}, Lcom/openvehicles/OVMS/TabMap;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/RadioButton;

    .line 1291
    .restart local v0       #option:Landroid/widget/RadioButton;
    invoke-virtual {v0, v3}, Landroid/widget/RadioButton;->setChecked(Z)V

    goto :goto_0

    .line 1294
    .end local v0           #option:Landroid/widget/RadioButton;
    :pswitch_2
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap$8;->this$0:Lcom/openvehicles/OVMS/TabMap;

    const v2, 0x7f090089

    invoke-virtual {v1, v2}, Lcom/openvehicles/OVMS/TabMap;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/RadioButton;

    .line 1295
    .restart local v0       #option:Landroid/widget/RadioButton;
    invoke-virtual {v0, v3}, Landroid/widget/RadioButton;->setChecked(Z)V

    goto :goto_0

    .line 1299
    .end local v0           #option:Landroid/widget/RadioButton;
    :pswitch_3
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabMap$8;->this$0:Lcom/openvehicles/OVMS/TabMap;

    const v2, 0x7f09008a

    invoke-virtual {v1, v2}, Lcom/openvehicles/OVMS/TabMap;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/RadioButton;

    .line 1300
    .restart local v0       #option:Landroid/widget/RadioButton;
    invoke-virtual {v0, v3}, Landroid/widget/RadioButton;->setChecked(Z)V

    goto :goto_0

    .line 1282
    nop

    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_0
        :pswitch_1
        :pswitch_0
        :pswitch_2
        :pswitch_3
        :pswitch_3
    .end packed-switch
.end method
