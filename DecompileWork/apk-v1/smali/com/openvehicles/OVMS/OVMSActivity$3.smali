.class Lcom/openvehicles/OVMS/OVMSActivity$3;
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
    .line 211
    iput-object p1, p0, Lcom/openvehicles/OVMS/OVMSActivity$3;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 4

    .prologue
    const/4 v3, 0x0

    .line 214
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$3;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    iget-boolean v1, v1, Lcom/openvehicles/OVMS/OVMSActivity;->SuppressServerErrorDialog:Z

    if-eqz v1, :cond_1

    .line 239
    :cond_0
    :goto_0
    return-void

    .line 216
    :cond_1
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$3;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->alertDialog:Landroid/app/AlertDialog;
    invoke-static {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->access$000(Lcom/openvehicles/OVMS/OVMSActivity;)Landroid/app/AlertDialog;

    move-result-object v1

    if-eqz v1, :cond_2

    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$3;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->alertDialog:Landroid/app/AlertDialog;
    invoke-static {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->access$000(Lcom/openvehicles/OVMS/OVMSActivity;)Landroid/app/AlertDialog;

    move-result-object v1

    invoke-virtual {v1}, Landroid/app/AlertDialog;->isShowing()Z

    move-result v1

    if-nez v1, :cond_0

    .line 219
    :cond_2
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$3;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    iget-object v1, v1, Lcom/openvehicles/OVMS/OVMSActivity;->progressLogin:Landroid/app/ProgressDialog;

    if-eqz v1, :cond_3

    .line 220
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$3;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    iget-object v1, v1, Lcom/openvehicles/OVMS/OVMSActivity;->progressLogin:Landroid/app/ProgressDialog;

    invoke-virtual {v1}, Landroid/app/ProgressDialog;->hide()V

    .line 222
    :cond_3
    new-instance v0, Landroid/app/AlertDialog$Builder;

    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$3;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-direct {v0, v1}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 224
    .local v0, builder:Landroid/app/AlertDialog$Builder;
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$3;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->isLoggedIn:Z
    invoke-static {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->access$100(Lcom/openvehicles/OVMS/OVMSActivity;)Z

    move-result v1

    if-eqz v1, :cond_4

    const-string v1, "OVMS Server Connection Lost"

    new-array v2, v3, [Ljava/lang/Object;

    invoke-static {v1, v2}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v1

    :goto_1
    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v1

    const-string v2, "Communications Problem"

    invoke-virtual {v1, v2}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v1

    invoke-virtual {v1, v3}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    move-result-object v1

    const-string v2, "Open Settings"

    new-instance v3, Lcom/openvehicles/OVMS/OVMSActivity$3$1;

    invoke-direct {v3, p0}, Lcom/openvehicles/OVMS/OVMSActivity$3$1;-><init>(Lcom/openvehicles/OVMS/OVMSActivity$3;)V

    invoke-virtual {v1, v2, v3}, Landroid/app/AlertDialog$Builder;->setPositiveButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 237
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$3;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v0}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v2

    #setter for: Lcom/openvehicles/OVMS/OVMSActivity;->alertDialog:Landroid/app/AlertDialog;
    invoke-static {v1, v2}, Lcom/openvehicles/OVMS/OVMSActivity;->access$002(Lcom/openvehicles/OVMS/OVMSActivity;Landroid/app/AlertDialog;)Landroid/app/AlertDialog;

    .line 238
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$3;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->alertDialog:Landroid/app/AlertDialog;
    invoke-static {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->access$000(Lcom/openvehicles/OVMS/OVMSActivity;)Landroid/app/AlertDialog;

    move-result-object v1

    invoke-virtual {v1}, Landroid/app/AlertDialog;->show()V

    goto :goto_0

    .line 224
    :cond_4
    const-string v1, "Please check the following:\n1. OVMS Server address\n2. Your vehicle ID and passwords"

    new-array v2, v3, [Ljava/lang/Object;

    invoke-static {v1, v2}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v1

    goto :goto_1
.end method
