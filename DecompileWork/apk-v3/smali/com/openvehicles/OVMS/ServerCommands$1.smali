.class Lcom/openvehicles/OVMS/ServerCommands$1;
.super Ljava/lang/Object;
.source "ServerCommands.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/ServerCommands;->LockUnlockCar(Landroid/content/Context;Lcom/openvehicles/OVMS/OVMSActivity;Landroid/widget/Toast;Z)Landroid/app/AlertDialog;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field private final synthetic val$input:Landroid/widget/EditText;

.field private final synthetic val$lock:Z

.field private final synthetic val$mApp:Lcom/openvehicles/OVMS/OVMSActivity;

.field private final synthetic val$mContext:Landroid/content/Context;

.field private final synthetic val$toastDisplayed:Landroid/widget/Toast;


# direct methods
.method constructor <init>(ZLcom/openvehicles/OVMS/OVMSActivity;Landroid/widget/EditText;Landroid/widget/Toast;Landroid/content/Context;)V
    .locals 0
    .parameter
    .parameter
    .parameter
    .parameter
    .parameter

    .prologue
    .line 1
    iput-boolean p1, p0, Lcom/openvehicles/OVMS/ServerCommands$1;->val$lock:Z

    iput-object p2, p0, Lcom/openvehicles/OVMS/ServerCommands$1;->val$mApp:Lcom/openvehicles/OVMS/OVMSActivity;

    iput-object p3, p0, Lcom/openvehicles/OVMS/ServerCommands$1;->val$input:Landroid/widget/EditText;

    iput-object p4, p0, Lcom/openvehicles/OVMS/ServerCommands$1;->val$toastDisplayed:Landroid/widget/Toast;

    iput-object p5, p0, Lcom/openvehicles/OVMS/ServerCommands$1;->val$mContext:Landroid/content/Context;

    .line 234
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 4
    .parameter "dialog"
    .parameter "id"

    .prologue
    .line 237
    iget-boolean v0, p0, Lcom/openvehicles/OVMS/ServerCommands$1;->val$lock:Z

    if-eqz v0, :cond_0

    .line 238
    iget-object v0, p0, Lcom/openvehicles/OVMS/ServerCommands$1;->val$mApp:Lcom/openvehicles/OVMS/OVMSActivity;

    .line 239
    iget-object v1, p0, Lcom/openvehicles/OVMS/ServerCommands$1;->val$input:Landroid/widget/EditText;

    invoke-virtual {v1}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v1

    .line 240
    invoke-interface {v1}, Landroid/text/Editable;->toString()Ljava/lang/String;

    move-result-object v1

    .line 239
    invoke-static {v1}, Lcom/openvehicles/OVMS/ServerCommands;->LOCK_CAR(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 238
    invoke-virtual {v0, v1}, Lcom/openvehicles/OVMS/OVMSActivity;->SendServerCommand(Ljava/lang/String;)Z

    .line 246
    :goto_0
    iget-object v1, p0, Lcom/openvehicles/OVMS/ServerCommands$1;->val$toastDisplayed:Landroid/widget/Toast;

    iget-object v2, p0, Lcom/openvehicles/OVMS/ServerCommands$1;->val$mContext:Landroid/content/Context;

    iget-boolean v0, p0, Lcom/openvehicles/OVMS/ServerCommands$1;->val$lock:Z

    if-eqz v0, :cond_1

    const-string v0, "Locking..."

    :goto_1
    const/4 v3, 0x0

    invoke-static {v1, v2, v0, v3}, Lcom/openvehicles/OVMS/ServerCommands;->makeToast(Landroid/widget/Toast;Landroid/content/Context;Ljava/lang/String;I)V

    .line 247
    invoke-interface {p1}, Landroid/content/DialogInterface;->dismiss()V

    .line 248
    return-void

    .line 242
    :cond_0
    iget-object v0, p0, Lcom/openvehicles/OVMS/ServerCommands$1;->val$mApp:Lcom/openvehicles/OVMS/OVMSActivity;

    .line 243
    iget-object v1, p0, Lcom/openvehicles/OVMS/ServerCommands$1;->val$input:Landroid/widget/EditText;

    invoke-virtual {v1}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v1

    .line 244
    invoke-interface {v1}, Landroid/text/Editable;->toString()Ljava/lang/String;

    move-result-object v1

    .line 243
    invoke-static {v1}, Lcom/openvehicles/OVMS/ServerCommands;->UNLOCK_CAR(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 242
    invoke-virtual {v0, v1}, Lcom/openvehicles/OVMS/OVMSActivity;->SendServerCommand(Ljava/lang/String;)Z

    goto :goto_0

    .line 246
    :cond_1
    const-string v0, "Unlocking..."

    goto :goto_1
.end method
