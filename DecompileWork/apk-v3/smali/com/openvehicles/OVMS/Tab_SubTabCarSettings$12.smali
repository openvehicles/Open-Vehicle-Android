.class Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$12;
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
    iput-object p1, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$12;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    .line 261
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onPreferenceClick(Landroid/preference/Preference;)Z
    .locals 5
    .parameter "preference"

    .prologue
    const/4 v4, 0x1

    .line 263
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$12;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    invoke-virtual {v0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->isFinishing()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 268
    :goto_0
    return v4

    .line 266
    :cond_0
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$12;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->mContext:Landroid/content/Context;
    invoke-static {v0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$10(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Landroid/content/Context;

    move-result-object v0

    iget-object v1, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$12;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->mOVMSActivity:Lcom/openvehicles/OVMS/OVMSActivity;
    invoke-static {v1}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$11(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Lcom/openvehicles/OVMS/OVMSActivity;

    move-result-object v1

    iget-object v2, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$12;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->toastDisplayed:Landroid/widget/Toast;
    invoke-static {v2}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$5(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Landroid/widget/Toast;

    move-result-object v2

    iget-object v3, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$12;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v3}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$1(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v3

    iget v3, v3, Lcom/openvehicles/OVMS/CarData;->Data_ChargeAmpsLimit:I

    invoke-static {v0, v1, v2, v3}, Lcom/openvehicles/OVMS/ServerCommands;->SetChargeCurrent(Landroid/content/Context;Lcom/openvehicles/OVMS/OVMSActivity;Landroid/widget/Toast;I)Landroid/app/AlertDialog;

    goto :goto_0
.end method
