.class Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$21$2;
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
    iput-object p1, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$21$2;->this$1:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$21;

    .line 526
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 2
    .parameter "dialog"
    .parameter "which"

    .prologue
    .line 529
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$21$2;->this$1:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$21;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$21;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;
    invoke-static {v0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$21;->access$0(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$21;)Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    move-result-object v0

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$1(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v0

    const/4 v1, 0x1

    iput-boolean v1, v0, Lcom/openvehicles/OVMS/CarData;->DontAskLayoutDownload:Z

    .line 530
    invoke-interface {p1}, Landroid/content/DialogInterface;->dismiss()V

    .line 531
    return-void
.end method
