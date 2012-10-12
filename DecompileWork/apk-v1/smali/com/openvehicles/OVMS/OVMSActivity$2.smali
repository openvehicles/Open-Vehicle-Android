.class Lcom/openvehicles/OVMS/OVMSActivity$2;
.super Ljava/lang/Object;
.source "OVMSActivity.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/openvehicles/OVMS/OVMSActivity;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/openvehicles/OVMS/OVMSActivity;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/OVMSActivity;)V
    .locals 0
    .parameter

    .prologue
    .line 204
    iput-object p1, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 4

    .prologue
    .line 206
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    const-string v2, ""

    const-string v3, "Connecting to OVMS Server..."

    invoke-static {v1, v2, v3}, Landroid/app/ProgressDialog;->show(Landroid/content/Context;Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Landroid/app/ProgressDialog;

    move-result-object v1

    iput-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity;->progressLogin:Landroid/app/ProgressDialog;

    .line 208
    return-void
.end method
