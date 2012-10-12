.class Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$15;
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
    iput-object p1, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$15;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    .line 297
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method static synthetic access$0(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$15;)Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;
    .locals 1
    .parameter

    .prologue
    .line 297
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$15;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    return-object v0
.end method


# virtual methods
.method public onPreferenceClick(Landroid/preference/Preference;)Z
    .locals 6
    .parameter "preference"

    .prologue
    const/4 v5, 0x1

    .line 299
    new-instance v1, Landroid/app/AlertDialog$Builder;

    iget-object v2, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$15;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->mContext:Landroid/content/Context;
    invoke-static {v2}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$10(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Landroid/content/Context;

    move-result-object v2

    invoke-direct {v1, v2}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 301
    .local v1, builder:Landroid/app/AlertDialog$Builder;
    const-string v2, "A full reboot will be performed on the car module."

    invoke-virtual {v1, v2}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v2

    .line 302
    const-string v3, "Reboot Car Module"

    invoke-virtual {v2, v3}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v2

    .line 303
    invoke-virtual {v2, v5}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    move-result-object v2

    .line 304
    const-string v3, "Reboot"

    .line 305
    new-instance v4, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$15$1;

    invoke-direct {v4, p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$15$1;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$15;)V

    .line 304
    invoke-virtual {v2, v3, v4}, Landroid/app/AlertDialog$Builder;->setPositiveButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v2

    .line 315
    const-string v3, "Cancel"

    .line 316
    new-instance v4, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$15$2;

    invoke-direct {v4, p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$15$2;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$15;)V

    .line 315
    invoke-virtual {v2, v3, v4}, Landroid/app/AlertDialog$Builder;->setNegativeButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 322
    invoke-virtual {v1}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v0

    .line 323
    .local v0, alertDialog:Landroid/app/AlertDialog;
    iget-object v2, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$15;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    invoke-virtual {v2}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->isFinishing()Z

    move-result v2

    if-nez v2, :cond_0

    .line 324
    invoke-virtual {v0}, Landroid/app/AlertDialog;->show()V

    .line 326
    :cond_0
    return v5
.end method
