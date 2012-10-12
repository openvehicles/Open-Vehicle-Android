.class Lcom/openvehicles/OVMS/TabCars$1;
.super Ljava/lang/Object;
.source "TabCars.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/TabCars;->onCreate(Landroid/os/Bundle;)V
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
    .line 46
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabCars$1;->this$0:Lcom/openvehicles/OVMS/TabCars;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 2
    .parameter "v"

    .prologue
    .line 48
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabCars$1;->this$0:Lcom/openvehicles/OVMS/TabCars;

    const/4 v1, 0x0

    #calls: Lcom/openvehicles/OVMS/TabCars;->editCar(Lcom/openvehicles/OVMS/CarData;)V
    invoke-static {v0, v1}, Lcom/openvehicles/OVMS/TabCars;->access$000(Lcom/openvehicles/OVMS/TabCars;Lcom/openvehicles/OVMS/CarData;)V

    .line 49
    return-void
.end method
