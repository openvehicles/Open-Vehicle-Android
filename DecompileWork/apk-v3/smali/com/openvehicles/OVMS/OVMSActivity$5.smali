.class Lcom/openvehicles/OVMS/OVMSActivity$5;
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
    iput-object p1, p0, Lcom/openvehicles/OVMS/OVMSActivity$5;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    .line 453
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method static synthetic access$0(Lcom/openvehicles/OVMS/OVMSActivity$5;)Lcom/openvehicles/OVMS/OVMSActivity;
    .locals 1
    .parameter

    .prologue
    .line 453
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity$5;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    return-object v0
.end method


# virtual methods
.method public run()V
    .locals 4

    .prologue
    const/4 v3, 0x0

    .line 456
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$5;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    iget-boolean v1, v1, Lcom/openvehicles/OVMS/OVMSActivity;->SuppressServerErrorDialog:Z

    if-eqz v1, :cond_1

    .line 499
    :cond_0
    :goto_0
    return-void

    .line 458
    :cond_1
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$5;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->alertDialog:Landroid/app/AlertDialog;
    invoke-static {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->access$3(Lcom/openvehicles/OVMS/OVMSActivity;)Landroid/app/AlertDialog;

    move-result-object v1

    if-eqz v1, :cond_2

    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$5;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->alertDialog:Landroid/app/AlertDialog;
    invoke-static {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->access$3(Lcom/openvehicles/OVMS/OVMSActivity;)Landroid/app/AlertDialog;

    move-result-object v1

    invoke-virtual {v1}, Landroid/app/AlertDialog;->isShowing()Z

    move-result v1

    if-nez v1, :cond_0

    .line 463
    :cond_2
    :try_start_0
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$5;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->progressLogin:Landroid/app/ProgressDialog;
    invoke-static {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->access$2(Lcom/openvehicles/OVMS/OVMSActivity;)Landroid/app/ProgressDialog;

    move-result-object v1

    if-eqz v1, :cond_3

    .line 464
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$5;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->progressLogin:Landroid/app/ProgressDialog;
    invoke-static {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->access$2(Lcom/openvehicles/OVMS/OVMSActivity;)Landroid/app/ProgressDialog;

    move-result-object v1

    invoke-virtual {v1}, Landroid/app/ProgressDialog;->dismiss()V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_1

    .line 468
    :cond_3
    :goto_1
    new-instance v0, Landroid/app/AlertDialog$Builder;

    .line 469
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$5;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    .line 468
    invoke-direct {v0, v1}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 471
    .local v0, builder:Landroid/app/AlertDialog$Builder;
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$5;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->isLoggedIn:Z
    invoke-static {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->access$1(Lcom/openvehicles/OVMS/OVMSActivity;)Z

    move-result v1

    if-eqz v1, :cond_4

    const-string v1, "OVMS Server Connection Lost"

    new-array v2, v3, [Ljava/lang/Object;

    invoke-static {v1, v2}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v1

    .line 470
    :goto_2
    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v1

    .line 473
    const-string v2, "Connection Problem"

    invoke-virtual {v1, v2}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v1

    .line 474
    invoke-virtual {v1, v3}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    move-result-object v1

    .line 475
    const-string v2, "Retry"

    .line 476
    new-instance v3, Lcom/openvehicles/OVMS/OVMSActivity$5$1;

    invoke-direct {v3, p0}, Lcom/openvehicles/OVMS/OVMSActivity$5$1;-><init>(Lcom/openvehicles/OVMS/OVMSActivity$5;)V

    .line 475
    invoke-virtual {v1, v2, v3}, Landroid/app/AlertDialog$Builder;->setPositiveButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v1

    .line 483
    const-string v2, "Open Settings"

    .line 484
    new-instance v3, Lcom/openvehicles/OVMS/OVMSActivity$5$2;

    invoke-direct {v3, p0}, Lcom/openvehicles/OVMS/OVMSActivity$5$2;-><init>(Lcom/openvehicles/OVMS/OVMSActivity$5;)V

    .line 483
    invoke-virtual {v1, v2, v3}, Landroid/app/AlertDialog$Builder;->setNegativeButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 492
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$5;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v0}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v2

    #setter for: Lcom/openvehicles/OVMS/OVMSActivity;->alertDialog:Landroid/app/AlertDialog;
    invoke-static {v1, v2}, Lcom/openvehicles/OVMS/OVMSActivity;->access$6(Lcom/openvehicles/OVMS/OVMSActivity;Landroid/app/AlertDialog;)V

    .line 496
    :try_start_1
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$5;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->alertDialog:Landroid/app/AlertDialog;
    invoke-static {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->access$3(Lcom/openvehicles/OVMS/OVMSActivity;)Landroid/app/AlertDialog;

    move-result-object v1

    invoke-virtual {v1}, Landroid/app/AlertDialog;->show()V
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_0

    goto :goto_0

    .line 497
    :catch_0
    move-exception v1

    goto :goto_0

    .line 472
    :cond_4
    const-string v1, "Please check the following:\n1. OVMS Server address\n2. Your vehicle ID and passwords"

    new-array v2, v3, [Ljava/lang/Object;

    invoke-static {v1, v2}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v1

    goto :goto_2

    .line 465
    .end local v0           #builder:Landroid/app/AlertDialog$Builder;
    :catch_1
    move-exception v1

    goto :goto_1
.end method
