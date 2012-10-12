.class Lcom/openvehicles/OVMS/TabCar$3;
.super Landroid/os/Handler;
.source "TabCar.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/openvehicles/OVMS/TabCar;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/openvehicles/OVMS/TabCar;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/TabCar;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabCar$3;->this$0:Lcom/openvehicles/OVMS/TabCar;

    .line 411
    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 2
    .parameter "msg"

    .prologue
    .line 413
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabCar$3;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v1, 0x7f03000a

    invoke-virtual {v0, v1}, Lcom/openvehicles/OVMS/TabCar;->setContentView(I)V

    .line 414
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabCar$3;->this$0:Lcom/openvehicles/OVMS/TabCar;

    iget-object v1, p0, Lcom/openvehicles/OVMS/TabCar$3;->this$0:Lcom/openvehicles/OVMS/TabCar;

    invoke-virtual {v1}, Lcom/openvehicles/OVMS/TabCar;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    invoke-virtual {v1}, Landroid/content/res/Resources;->getConfiguration()Landroid/content/res/Configuration;

    move-result-object v1

    iget v1, v1, Landroid/content/res/Configuration;->orientation:I

    iput v1, v0, Lcom/openvehicles/OVMS/TabCar;->CurrentScreenOrientation:I

    .line 415
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabCar$3;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #calls: Lcom/openvehicles/OVMS/TabCar;->initUI()V
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabCar;->access$8(Lcom/openvehicles/OVMS/TabCar;)V

    .line 416
    return-void
.end method
