.class Lcom/openvehicles/OVMS/ServerCommands$5;
.super Ljava/lang/Object;
.source "ServerCommands.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/ServerCommands;->WakeUp(Landroid/content/Context;Lcom/openvehicles/OVMS/OVMSActivity;Landroid/widget/Toast;Z)Landroid/app/AlertDialog;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field private final synthetic val$mApp:Lcom/openvehicles/OVMS/OVMSActivity;

.field private final synthetic val$mContext:Landroid/content/Context;

.field private final synthetic val$toastDisplayed:Landroid/widget/Toast;

.field private final synthetic val$wakeUpSensorsOnly:Z


# direct methods
.method constructor <init>(ZLcom/openvehicles/OVMS/OVMSActivity;Landroid/widget/Toast;Landroid/content/Context;)V
    .locals 0
    .parameter
    .parameter
    .parameter
    .parameter

    .prologue
    .line 1
    iput-boolean p1, p0, Lcom/openvehicles/OVMS/ServerCommands$5;->val$wakeUpSensorsOnly:Z

    iput-object p2, p0, Lcom/openvehicles/OVMS/ServerCommands$5;->val$mApp:Lcom/openvehicles/OVMS/OVMSActivity;

    iput-object p3, p0, Lcom/openvehicles/OVMS/ServerCommands$5;->val$toastDisplayed:Landroid/widget/Toast;

    iput-object p4, p0, Lcom/openvehicles/OVMS/ServerCommands$5;->val$mContext:Landroid/content/Context;

    .line 330
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 4
    .parameter "dialog"
    .parameter "id"

    .prologue
    .line 333
    iget-boolean v0, p0, Lcom/openvehicles/OVMS/ServerCommands$5;->val$wakeUpSensorsOnly:Z

    if-eqz v0, :cond_0

    .line 334
    iget-object v0, p0, Lcom/openvehicles/OVMS/ServerCommands$5;->val$mApp:Lcom/openvehicles/OVMS/OVMSActivity;

    const-string v1, "C19"

    invoke-virtual {v0, v1}, Lcom/openvehicles/OVMS/OVMSActivity;->SendServerCommand(Ljava/lang/String;)Z

    .line 338
    :goto_0
    iget-object v0, p0, Lcom/openvehicles/OVMS/ServerCommands$5;->val$toastDisplayed:Landroid/widget/Toast;

    iget-object v1, p0, Lcom/openvehicles/OVMS/ServerCommands$5;->val$mContext:Landroid/content/Context;

    const-string v2, "Waking Up..."

    const/4 v3, 0x0

    invoke-static {v0, v1, v2, v3}, Lcom/openvehicles/OVMS/ServerCommands;->makeToast(Landroid/widget/Toast;Landroid/content/Context;Ljava/lang/String;I)V

    .line 339
    invoke-interface {p1}, Landroid/content/DialogInterface;->dismiss()V

    .line 340
    return-void

    .line 336
    :cond_0
    iget-object v0, p0, Lcom/openvehicles/OVMS/ServerCommands$5;->val$mApp:Lcom/openvehicles/OVMS/OVMSActivity;

    const-string v1, "C18"

    invoke-virtual {v0, v1}, Lcom/openvehicles/OVMS/OVMSActivity;->SendServerCommand(Ljava/lang/String;)Z

    goto :goto_0
.end method
