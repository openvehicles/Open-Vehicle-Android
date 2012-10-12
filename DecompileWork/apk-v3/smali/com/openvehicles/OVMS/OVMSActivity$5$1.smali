.class Lcom/openvehicles/OVMS/OVMSActivity$5$1;
.super Ljava/lang/Object;
.source "OVMSActivity.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/OVMSActivity$5;->run()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$1:Lcom/openvehicles/OVMS/OVMSActivity$5;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/OVMSActivity$5;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/OVMSActivity$5$1;->this$1:Lcom/openvehicles/OVMS/OVMSActivity$5;

    .line 476
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 2
    .parameter "dialog"
    .parameter "id"

    .prologue
    .line 479
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity$5$1;->this$1:Lcom/openvehicles/OVMS/OVMSActivity$5;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity$5;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;
    invoke-static {v0}, Lcom/openvehicles/OVMS/OVMSActivity$5;->access$0(Lcom/openvehicles/OVMS/OVMSActivity$5;)Lcom/openvehicles/OVMS/OVMSActivity;

    move-result-object v0

    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$5$1;->this$1:Lcom/openvehicles/OVMS/OVMSActivity$5;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity$5;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;
    invoke-static {v1}, Lcom/openvehicles/OVMS/OVMSActivity$5;->access$0(Lcom/openvehicles/OVMS/OVMSActivity$5;)Lcom/openvehicles/OVMS/OVMSActivity;

    move-result-object v1

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->access$0(Lcom/openvehicles/OVMS/OVMSActivity;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v1

    invoke-virtual {v0, v1}, Lcom/openvehicles/OVMS/OVMSActivity;->ChangeCar(Lcom/openvehicles/OVMS/CarData;)V

    .line 480
    invoke-interface {p1}, Landroid/content/DialogInterface;->dismiss()V

    .line 481
    return-void
.end method
