.class Lcom/openvehicles/OVMS/ServerCommands$7;
.super Ljava/lang/Object;
.source "ServerCommands.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/ServerCommands;->SetChargeCurrent(Landroid/content/Context;Lcom/openvehicles/OVMS/OVMSActivity;Landroid/widget/Toast;I)Landroid/app/AlertDialog;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field private final synthetic val$input:Landroid/widget/EditText;

.field private final synthetic val$mApp:Lcom/openvehicles/OVMS/OVMSActivity;

.field private final synthetic val$mContext:Landroid/content/Context;

.field private final synthetic val$toastDisplayed:Landroid/widget/Toast;


# direct methods
.method constructor <init>(Landroid/widget/EditText;Lcom/openvehicles/OVMS/OVMSActivity;Landroid/widget/Toast;Landroid/content/Context;)V
    .locals 0
    .parameter
    .parameter
    .parameter
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/ServerCommands$7;->val$input:Landroid/widget/EditText;

    iput-object p2, p0, Lcom/openvehicles/OVMS/ServerCommands$7;->val$mApp:Lcom/openvehicles/OVMS/OVMSActivity;

    iput-object p3, p0, Lcom/openvehicles/OVMS/ServerCommands$7;->val$toastDisplayed:Landroid/widget/Toast;

    iput-object p4, p0, Lcom/openvehicles/OVMS/ServerCommands$7;->val$mContext:Landroid/content/Context;

    .line 378
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 5
    .parameter "dialog"
    .parameter "id"

    .prologue
    const/4 v4, 0x0

    .line 381
    iget-object v1, p0, Lcom/openvehicles/OVMS/ServerCommands$7;->val$input:Landroid/widget/EditText;

    invoke-virtual {v1}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v1

    invoke-interface {v1}, Landroid/text/Editable;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v1}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v0

    .line 382
    .local v0, amps:I
    const/16 v1, 0xa

    if-lt v0, v1, :cond_0

    const/16 v1, 0x46

    if-gt v0, v1, :cond_0

    .line 384
    iget-object v1, p0, Lcom/openvehicles/OVMS/ServerCommands$7;->val$mApp:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-static {v0}, Lcom/openvehicles/OVMS/ServerCommands;->SET_CHARGE_CURRENT(I)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Lcom/openvehicles/OVMS/OVMSActivity;->SendServerCommand(Ljava/lang/String;)Z

    .line 385
    iget-object v1, p0, Lcom/openvehicles/OVMS/ServerCommands$7;->val$toastDisplayed:Landroid/widget/Toast;

    iget-object v2, p0, Lcom/openvehicles/OVMS/ServerCommands$7;->val$mContext:Landroid/content/Context;

    const-string v3, "Changing Max Current..."

    invoke-static {v1, v2, v3, v4}, Lcom/openvehicles/OVMS/ServerCommands;->makeToast(Landroid/widget/Toast;Landroid/content/Context;Ljava/lang/String;I)V

    .line 386
    invoke-interface {p1}, Landroid/content/DialogInterface;->dismiss()V

    .line 389
    :goto_0
    return-void

    .line 388
    :cond_0
    iget-object v1, p0, Lcom/openvehicles/OVMS/ServerCommands$7;->val$toastDisplayed:Landroid/widget/Toast;

    iget-object v2, p0, Lcom/openvehicles/OVMS/ServerCommands$7;->val$mContext:Landroid/content/Context;

    const-string v3, "Amps must be between 10 and 70"

    invoke-static {v1, v2, v3, v4}, Lcom/openvehicles/OVMS/ServerCommands;->makeToast(Landroid/widget/Toast;Landroid/content/Context;Ljava/lang/String;I)V

    goto :goto_0
.end method
