.class Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$20;
.super Ljava/lang/Object;
.source "Tab_SubTabCarSettings.java"

# interfaces
.implements Landroid/preference/Preference$OnPreferenceClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->wireUpPrefButtons()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$20;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    .line 473
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method static synthetic access$0(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$20;)Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;
    .locals 1
    .parameter

    .prologue
    .line 473
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$20;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    return-object v0
.end method


# virtual methods
.method public onPreferenceClick(Landroid/preference/Preference;)Z
    .locals 5
    .parameter "preference"

    .prologue
    const/4 v4, 0x1

    .line 475
    new-instance v0, Landroid/app/AlertDialog$Builder;

    iget-object v1, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$20;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->mContext:Landroid/content/Context;
    invoke-static {v1}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$10(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Landroid/content/Context;

    move-result-object v1

    invoke-direct {v0, v1}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 476
    .local v0, builder:Landroid/app/AlertDialog$Builder;
    const-string v1, "Re-download high resolution graphics now?\n\nThe download is approx. 300KB."

    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v1

    .line 477
    const-string v2, "Download Graphics"

    invoke-virtual {v1, v2}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v1

    .line 478
    invoke-virtual {v1, v4}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    move-result-object v1

    .line 479
    const-string v2, "Download"

    .line 480
    new-instance v3, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$20$1;

    invoke-direct {v3, p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$20$1;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$20;)V

    .line 479
    invoke-virtual {v1, v2, v3}, Landroid/app/AlertDialog$Builder;->setPositiveButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v1

    .line 487
    const-string v2, "Cancel"

    .line 488
    new-instance v3, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$20$2;

    invoke-direct {v3, p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$20$2;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$20;)V

    .line 487
    invoke-virtual {v1, v2, v3}, Landroid/app/AlertDialog$Builder;->setNegativeButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 495
    iget-object v1, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$20;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    invoke-virtual {v0}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v2

    #setter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->dialog:Landroid/app/AlertDialog;
    invoke-static {v1, v2}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$14(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;Landroid/app/AlertDialog;)V

    .line 496
    iget-object v1, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$20;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->dialog:Landroid/app/AlertDialog;
    invoke-static {v1}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$15(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Landroid/app/AlertDialog;

    move-result-object v1

    invoke-virtual {v1}, Landroid/app/AlertDialog;->show()V

    .line 498
    return v4
.end method
