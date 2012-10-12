.class Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations$1;
.super Landroid/os/Handler;
.source "Tab_SubTabDataUtilizations.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;

    .line 318
    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 4
    .parameter "msg"

    .prologue
    .line 322
    :try_start_0
    iget-object v1, p0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;

    #calls: Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->refreshChart()V
    invoke-static {v1}, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->access$0(Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 331
    :goto_0
    return-void

    .line 323
    :catch_0
    move-exception v0

    .line 326
    .local v0, e:Ljava/lang/Exception;
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    .line 327
    iget-object v1, p0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v1}, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->access$1(Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v1

    iget-object v1, v1, Lcom/openvehicles/OVMS/CarData;->Data_GPRSUtilization:Lcom/openvehicles/OVMS/GPRSUtilization;

    if-eqz v1, :cond_0

    .line 328
    iget-object v1, p0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v1}, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->access$1(Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v1

    iget-object v1, v1, Lcom/openvehicles/OVMS/CarData;->Data_GPRSUtilization:Lcom/openvehicles/OVMS/GPRSUtilization;

    invoke-virtual {v1}, Lcom/openvehicles/OVMS/GPRSUtilization;->Clear()V

    .line 329
    :cond_0
    iget-object v1, p0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;

    const-string v2, "Tap refresh button to update data"

    const/4 v3, 0x0

    invoke-static {v1, v2, v3}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v1

    invoke-virtual {v1}, Landroid/widget/Toast;->show()V

    goto :goto_0
.end method
