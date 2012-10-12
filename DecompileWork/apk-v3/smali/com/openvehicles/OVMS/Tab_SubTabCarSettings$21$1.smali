.class Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$21$1;
.super Ljava/lang/Object;
.source "Tab_SubTabCarSettings.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$21;->onPreferenceClick(Landroid/preference/Preference;)Z
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$1:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$21;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$21;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$21$1;->this$1:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$21;

    .line 510
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 4
    .parameter "dialog"
    .parameter "which"

    .prologue
    .line 515
    iget-object v1, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$21$1;->this$1:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$21;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$21;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;
    invoke-static {v1}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$21;->access$0(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$21;)Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    move-result-object v1

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->mOVMSActivity:Lcom/openvehicles/OVMS/OVMSActivity;
    invoke-static {v1}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$11(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Lcom/openvehicles/OVMS/OVMSActivity;

    move-result-object v1

    const-string v2, "C2DM"

    const/4 v3, 0x0

    invoke-virtual {v1, v2, v3}, Lcom/openvehicles/OVMS/OVMSActivity;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v1

    invoke-interface {v1}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    .line 516
    .local v0, editor:Landroid/content/SharedPreferences$Editor;
    const-string v1, "RegID"

    invoke-interface {v0, v1}, Landroid/content/SharedPreferences$Editor;->remove(Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    .line 517
    invoke-interface {v0}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 519
    iget-object v1, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$21$1;->this$1:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$21;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$21;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;
    invoke-static {v1}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$21;->access$0(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$21;)Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    move-result-object v1

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->mOVMSActivity:Lcom/openvehicles/OVMS/OVMSActivity;
    invoke-static {v1}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$11(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Lcom/openvehicles/OVMS/OVMSActivity;

    move-result-object v1

    invoke-static {v1}, Lcom/openvehicles/OVMS/ServerCommands;->RequestC2DMRegistrationID(Landroid/content/Context;)V

    .line 520
    iget-object v1, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$21$1;->this$1:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$21;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$21;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;
    invoke-static {v1}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$21;->access$0(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$21;)Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    move-result-object v1

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->mOVMSActivity:Lcom/openvehicles/OVMS/OVMSActivity;
    invoke-static {v1}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$11(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Lcom/openvehicles/OVMS/OVMSActivity;

    move-result-object v1

    invoke-virtual {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->ReportC2DMRegistrationID()V

    .line 522
    invoke-interface {p1}, Landroid/content/DialogInterface;->dismiss()V

    .line 523
    return-void
.end method
