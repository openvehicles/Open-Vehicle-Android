.class Lcom/openvehicles/OVMS/TabCar$2$1;
.super Ljava/lang/Object;
.source "TabCar.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/TabCar$2;->handleMessage(Landroid/os/Message;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$1:Lcom/openvehicles/OVMS/TabCar$2;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/TabCar$2;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabCar$2$1;->this$1:Lcom/openvehicles/OVMS/TabCar$2;

    .line 298
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 1
    .parameter "dialog"
    .parameter "which"

    .prologue
    .line 302
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabCar$2$1;->this$1:Lcom/openvehicles/OVMS/TabCar$2;

    #getter for: Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabCar$2;->access$0(Lcom/openvehicles/OVMS/TabCar$2;)Lcom/openvehicles/OVMS/TabCar;

    move-result-object v0

    #calls: Lcom/openvehicles/OVMS/TabCar;->downloadLayout()V
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabCar;->access$5(Lcom/openvehicles/OVMS/TabCar;)V

    .line 303
    invoke-interface {p1}, Landroid/content/DialogInterface;->dismiss()V

    .line 304
    return-void
.end method
