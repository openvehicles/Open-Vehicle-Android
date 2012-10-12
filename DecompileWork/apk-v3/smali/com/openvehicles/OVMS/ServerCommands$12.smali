.class Lcom/openvehicles/OVMS/ServerCommands$12;
.super Ljava/lang/Object;
.source "ServerCommands.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/ServerCommands;->StartCharge(Landroid/content/Context;Lcom/openvehicles/OVMS/OVMSActivity;Landroid/widget/Toast;)Landroid/app/AlertDialog;
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
    iput-object p1, p0, Lcom/openvehicles/OVMS/ServerCommands$12;->val$mApp:Lcom/openvehicles/OVMS/OVMSActivity;

    iput-object p2, p0, Lcom/openvehicles/OVMS/ServerCommands$12;->val$toastDisplayed:Landroid/widget/Toast;

    iput-object p3, p0, Lcom/openvehicles/OVMS/ServerCommands$12;->val$mContext:Landroid/content/Context;

    .line 467
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 4
    .parameter "dialog"
    .parameter "id"

    .prologue
    .line 470
    iget-object v0, p0, Lcom/openvehicles/OVMS/ServerCommands$12;->val$mApp:Lcom/openvehicles/OVMS/OVMSActivity;

    .line 471
    const-string v1, "C11"

    .line 470
    invoke-virtual {v0, v1}, Lcom/openvehicles/OVMS/OVMSActivity;->SendServerCommand(Ljava/lang/String;)Z

    .line 473
    iget-object v0, p0, Lcom/openvehicles/OVMS/ServerCommands$12;->val$toastDisplayed:Landroid/widget/Toast;

    iget-object v1, p0, Lcom/openvehicles/OVMS/ServerCommands$12;->val$mContext:Landroid/content/Context;

    const-string v2, "Charge Starting..."

    const/4 v3, 0x0

    invoke-static {v0, v1, v2, v3}, Lcom/openvehicles/OVMS/ServerCommands;->makeToast(Landroid/widget/Toast;Landroid/content/Context;Ljava/lang/String;I)V

    .line 474
    invoke-interface {p1}, Landroid/content/DialogInterface;->dismiss()V

    .line 475
    return-void
.end method
