.class Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$20$1;
.super Ljava/lang/Object;
.source "Tab_SubTabCarSettings.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$20;->onPreferenceClick(Landroid/preference/Preference;)Z
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$1:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$20;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$20;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$20$1;->this$1:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$20;

    .line 480
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 1
    .parameter "dialog"
    .parameter "which"

    .prologue
    .line 483
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$20$1;->this$1:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$20;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$20;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;
    invoke-static {v0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$20;->access$0(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$20;)Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    move-result-object v0

    #calls: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->downloadLayout()V
    invoke-static {v0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$13(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)V

    .line 484
    invoke-interface {p1}, Landroid/content/DialogInterface;->dismiss()V

    .line 485
    return-void
.end method
