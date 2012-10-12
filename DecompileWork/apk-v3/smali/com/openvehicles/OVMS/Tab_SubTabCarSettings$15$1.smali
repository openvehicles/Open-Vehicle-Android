.class Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$15$1;
.super Ljava/lang/Object;
.source "Tab_SubTabCarSettings.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$15;->onPreferenceClick(Landroid/preference/Preference;)Z
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$1:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$15;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$15;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$15$1;->this$1:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$15;

    .line 305
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 3
    .parameter "dialog"
    .parameter "id"

    .prologue
    .line 308
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$15$1;->this$1:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$15;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$15;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;
    invoke-static {v0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$15;->access$0(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$15;)Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    move-result-object v0

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->mOVMSActivity:Lcom/openvehicles/OVMS/OVMSActivity;
    invoke-static {v0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$11(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Lcom/openvehicles/OVMS/OVMSActivity;

    move-result-object v0

    .line 309
    const-string v1, "C5"

    .line 308
    invoke-virtual {v0, v1}, Lcom/openvehicles/OVMS/OVMSActivity;->SendServerCommand(Ljava/lang/String;)Z

    .line 311
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$15$1;->this$1:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$15;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$15;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;
    invoke-static {v0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$15;->access$0(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$15;)Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    move-result-object v0

    const-string v1, "Request sent"

    const/4 v2, 0x0

    #calls: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->makeToast(Ljava/lang/String;I)V
    invoke-static {v0, v1, v2}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$12(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;Ljava/lang/String;I)V

    .line 312
    invoke-interface {p1}, Landroid/content/DialogInterface;->dismiss()V

    .line 313
    return-void
.end method
