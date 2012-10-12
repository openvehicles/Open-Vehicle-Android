.class Lcom/openvehicles/OVMS/ServerCommands$9;
.super Ljava/lang/Object;
.source "ServerCommands.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/ServerCommands;->SetChargeMode(Landroid/content/Context;Lcom/openvehicles/OVMS/OVMSActivity;Landroid/widget/Toast;)Landroid/app/AlertDialog;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field private final synthetic val$mApp:Lcom/openvehicles/OVMS/OVMSActivity;

.field private final synthetic val$mContext:Landroid/content/Context;

.field private final synthetic val$toastDisplayed:Landroid/widget/Toast;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/OVMSActivity;Landroid/widget/Toast;Landroid/content/Context;)V
    .locals 0
    .parameter
    .parameter
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/ServerCommands$9;->val$mApp:Lcom/openvehicles/OVMS/OVMSActivity;

    iput-object p2, p0, Lcom/openvehicles/OVMS/ServerCommands$9;->val$toastDisplayed:Landroid/widget/Toast;

    iput-object p3, p0, Lcom/openvehicles/OVMS/ServerCommands$9;->val$mContext:Landroid/content/Context;

    .line 412
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 4
    .parameter "dialog"
    .parameter "item"

    .prologue
    .line 414
    iget-object v0, p0, Lcom/openvehicles/OVMS/ServerCommands$9;->val$mApp:Lcom/openvehicles/OVMS/OVMSActivity;

    .line 415
    const/4 v1, 0x2

    if-lt p2, v1, :cond_0

    add-int/lit8 p2, p2, 0x1

    .end local p2
    :cond_0
    invoke-static {p2}, Lcom/openvehicles/OVMS/ServerCommands;->SET_CHARGE_MODE(I)Ljava/lang/String;

    move-result-object v1

    .line 414
    invoke-virtual {v0, v1}, Lcom/openvehicles/OVMS/OVMSActivity;->SendServerCommand(Ljava/lang/String;)Z

    .line 417
    iget-object v0, p0, Lcom/openvehicles/OVMS/ServerCommands$9;->val$toastDisplayed:Landroid/widget/Toast;

    iget-object v1, p0, Lcom/openvehicles/OVMS/ServerCommands$9;->val$mContext:Landroid/content/Context;

    const-string v2, "Changing Mode..."

    const/4 v3, 0x0

    invoke-static {v0, v1, v2, v3}, Lcom/openvehicles/OVMS/ServerCommands;->makeToast(Landroid/widget/Toast;Landroid/content/Context;Ljava/lang/String;I)V

    .line 418
    invoke-interface {p1}, Landroid/content/DialogInterface;->dismiss()V

    .line 419
    return-void
.end method
