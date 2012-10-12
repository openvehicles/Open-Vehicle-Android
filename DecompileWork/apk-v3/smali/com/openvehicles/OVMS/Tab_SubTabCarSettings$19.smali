.class Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$19;
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
    iput-object p1, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$19;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    .line 450
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onPreferenceClick(Landroid/preference/Preference;)Z
    .locals 6
    .parameter "preference"

    .prologue
    const/4 v5, 0x1

    .line 452
    new-instance v1, Landroid/app/AlertDialog$Builder;

    iget-object v2, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$19;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->mContext:Landroid/content/Context;
    invoke-static {v2}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$10(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Landroid/content/Context;

    move-result-object v2

    invoke-direct {v1, v2}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 454
    .local v1, builder:Landroid/app/AlertDialog$Builder;
    invoke-static {}, Lcom/openvehicles/OVMS/DataLog;->getLog()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v2

    .line 455
    const-string v3, "TCP Log"

    invoke-virtual {v2, v3}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v2

    .line 456
    invoke-virtual {v2, v5}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    move-result-object v2

    .line 457
    const-string v3, "Close"

    .line 458
    new-instance v4, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$19$1;

    invoke-direct {v4, p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$19$1;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$19;)V

    .line 457
    invoke-virtual {v2, v3, v4}, Landroid/app/AlertDialog$Builder;->setPositiveButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 464
    invoke-virtual {v1}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v0

    .line 465
    .local v0, alertDialog:Landroid/app/AlertDialog;
    iget-object v2, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$19;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    invoke-virtual {v2}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->isFinishing()Z

    move-result v2

    if-nez v2, :cond_0

    .line 466
    invoke-virtual {v0}, Landroid/app/AlertDialog;->show()V

    .line 468
    :cond_0
    return v5
.end method
