.class Lcom/openvehicles/OVMS/OVMSActivity$4;
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
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/OVMSActivity$4;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    .line 417
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 3

    .prologue
    .line 421
    :try_start_0
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity$4;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->progressLogin:Landroid/app/ProgressDialog;
    invoke-static {v0}, Lcom/openvehicles/OVMS/OVMSActivity;->access$2(Lcom/openvehicles/OVMS/OVMSActivity;)Landroid/app/ProgressDialog;

    move-result-object v0

    if-eqz v0, :cond_0

    .line 422
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity$4;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->progressLogin:Landroid/app/ProgressDialog;
    invoke-static {v0}, Lcom/openvehicles/OVMS/OVMSActivity;->access$2(Lcom/openvehicles/OVMS/OVMSActivity;)Landroid/app/ProgressDialog;

    move-result-object v0

    invoke-virtual {v0}, Landroid/app/ProgressDialog;->dismiss()V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_2

    .line 428
    :cond_0
    :goto_0
    :try_start_1
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity$4;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->alertDialog:Landroid/app/AlertDialog;
    invoke-static {v0}, Lcom/openvehicles/OVMS/OVMSActivity;->access$3(Lcom/openvehicles/OVMS/OVMSActivity;)Landroid/app/AlertDialog;

    move-result-object v0

    if-eqz v0, :cond_1

    .line 429
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity$4;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->alertDialog:Landroid/app/AlertDialog;
    invoke-static {v0}, Lcom/openvehicles/OVMS/OVMSActivity;->access$3(Lcom/openvehicles/OVMS/OVMSActivity;)Landroid/app/AlertDialog;

    move-result-object v0

    invoke-virtual {v0}, Landroid/app/AlertDialog;->dismiss()V
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_1

    .line 436
    :cond_1
    :goto_1
    :try_start_2
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity$4;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    new-instance v1, Landroid/app/ProgressDialog;

    iget-object v2, p0, Lcom/openvehicles/OVMS/OVMSActivity$4;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-direct {v1, v2}, Landroid/app/ProgressDialog;-><init>(Landroid/content/Context;)V

    #setter for: Lcom/openvehicles/OVMS/OVMSActivity;->progressLogin:Landroid/app/ProgressDialog;
    invoke-static {v0, v1}, Lcom/openvehicles/OVMS/OVMSActivity;->access$5(Lcom/openvehicles/OVMS/OVMSActivity;Landroid/app/ProgressDialog;)V

    .line 437
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity$4;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->progressLogin:Landroid/app/ProgressDialog;
    invoke-static {v0}, Lcom/openvehicles/OVMS/OVMSActivity;->access$2(Lcom/openvehicles/OVMS/OVMSActivity;)Landroid/app/ProgressDialog;

    move-result-object v0

    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Landroid/app/ProgressDialog;->setIndeterminate(Z)V

    .line 438
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity$4;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->progressLogin:Landroid/app/ProgressDialog;
    invoke-static {v0}, Lcom/openvehicles/OVMS/OVMSActivity;->access$2(Lcom/openvehicles/OVMS/OVMSActivity;)Landroid/app/ProgressDialog;

    move-result-object v0

    const-string v1, "Connecting to OVMS Server..."

    invoke-virtual {v0, v1}, Landroid/app/ProgressDialog;->setMessage(Ljava/lang/CharSequence;)V

    .line 441
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity$4;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->progressLogin:Landroid/app/ProgressDialog;
    invoke-static {v0}, Lcom/openvehicles/OVMS/OVMSActivity;->access$2(Lcom/openvehicles/OVMS/OVMSActivity;)Landroid/app/ProgressDialog;

    move-result-object v0

    invoke-virtual {v0}, Landroid/app/ProgressDialog;->getWindow()Landroid/view/Window;

    move-result-object v0

    .line 442
    const/4 v1, 0x2

    .line 441
    invoke-virtual {v0, v1}, Landroid/view/Window;->clearFlags(I)V

    .line 447
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity$4;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->progressLogin:Landroid/app/ProgressDialog;
    invoke-static {v0}, Lcom/openvehicles/OVMS/OVMSActivity;->access$2(Lcom/openvehicles/OVMS/OVMSActivity;)Landroid/app/ProgressDialog;

    move-result-object v0

    invoke-virtual {v0}, Landroid/app/ProgressDialog;->show()V
    :try_end_2
    .catch Ljava/lang/Exception; {:try_start_2 .. :try_end_2} :catch_0

    .line 450
    :goto_2
    return-void

    .line 448
    :catch_0
    move-exception v0

    goto :goto_2

    .line 430
    :catch_1
    move-exception v0

    goto :goto_1

    .line 423
    :catch_2
    move-exception v0

    goto :goto_0
.end method
