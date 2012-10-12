.class Lcom/openvehicles/OVMS/TabCars$ItemsAdapter$1;
.super Ljava/lang/Object;
.source "TabCars.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;->getView(ILandroid/view/View;Landroid/view/ViewGroup;)Landroid/view/View;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$1:Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabCars$ItemsAdapter$1;->this$1:Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;

    .line 240
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 4
    .parameter "arg0"

    .prologue
    .line 243
    invoke-virtual {p1}, Landroid/view/View;->getTag()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/openvehicles/OVMS/CarData;

    .line 244
    .local v0, car:Lcom/openvehicles/OVMS/CarData;
    const-string v1, "OVMS"

    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "Editing car: "

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    iget-object v3, v0, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 245
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabCars$ItemsAdapter$1;->this$1:Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;

    #getter for: Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;->this$0:Lcom/openvehicles/OVMS/TabCars;
    invoke-static {v1}, Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;->access$0(Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;)Lcom/openvehicles/OVMS/TabCars;

    move-result-object v1

    #calls: Lcom/openvehicles/OVMS/TabCars;->editCar(Lcom/openvehicles/OVMS/CarData;)V
    invoke-static {v1, v0}, Lcom/openvehicles/OVMS/TabCars;->access$4(Lcom/openvehicles/OVMS/TabCars;Lcom/openvehicles/OVMS/CarData;)V

    .line 246
    return-void
.end method
