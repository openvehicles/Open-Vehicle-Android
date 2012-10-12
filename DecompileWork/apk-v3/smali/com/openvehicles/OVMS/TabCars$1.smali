.class Lcom/openvehicles/OVMS/TabCars$1;
.super Landroid/os/Handler;
.source "TabCars.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/openvehicles/OVMS/TabCars;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/openvehicles/OVMS/TabCars;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/TabCars;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabCars$1;->this$0:Lcom/openvehicles/OVMS/TabCars;

    .line 262
    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 6
    .parameter "msg"

    .prologue
    .line 264
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabCars$1;->this$0:Lcom/openvehicles/OVMS/TabCars;

    new-instance v1, Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;

    iget-object v2, p0, Lcom/openvehicles/OVMS/TabCars$1;->this$0:Lcom/openvehicles/OVMS/TabCars;

    iget-object v3, p0, Lcom/openvehicles/OVMS/TabCars$1;->this$0:Lcom/openvehicles/OVMS/TabCars;

    #getter for: Lcom/openvehicles/OVMS/TabCars;->mContext:Landroid/content/Context;
    invoke-static {v3}, Lcom/openvehicles/OVMS/TabCars;->access$0(Lcom/openvehicles/OVMS/TabCars;)Landroid/content/Context;

    move-result-object v3

    const v4, 0x7f03000d

    .line 265
    iget-object v5, p0, Lcom/openvehicles/OVMS/TabCars$1;->this$0:Lcom/openvehicles/OVMS/TabCars;

    #getter for: Lcom/openvehicles/OVMS/TabCars;->carsList:[Lcom/openvehicles/OVMS/CarData;
    invoke-static {v5}, Lcom/openvehicles/OVMS/TabCars;->access$1(Lcom/openvehicles/OVMS/TabCars;)[Lcom/openvehicles/OVMS/CarData;

    move-result-object v5

    invoke-direct {v1, v2, v3, v4, v5}, Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;-><init>(Lcom/openvehicles/OVMS/TabCars;Landroid/content/Context;I[Lcom/openvehicles/OVMS/CarData;)V

    .line 264
    #setter for: Lcom/openvehicles/OVMS/TabCars;->adapter:Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;
    invoke-static {v0, v1}, Lcom/openvehicles/OVMS/TabCars;->access$2(Lcom/openvehicles/OVMS/TabCars;Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;)V

    .line 266
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabCars$1;->this$0:Lcom/openvehicles/OVMS/TabCars;

    iget-object v1, p0, Lcom/openvehicles/OVMS/TabCars$1;->this$0:Lcom/openvehicles/OVMS/TabCars;

    #getter for: Lcom/openvehicles/OVMS/TabCars;->adapter:Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabCars;->access$3(Lcom/openvehicles/OVMS/TabCars;)Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;

    move-result-object v1

    invoke-virtual {v0, v1}, Lcom/openvehicles/OVMS/TabCars;->setListAdapter(Landroid/widget/ListAdapter;)V

    .line 267
    return-void
.end method
