.class Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$24;
.super Ljava/lang/Object;
.source "Tab_SubTabCarSettings.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->commitSettings()V
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
    iput-object p1, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$24;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    .line 895
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 3
    .parameter "dialog"
    .parameter "id"

    .prologue
    .line 897
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$24;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    const-string v1, "Commit cancelled"

    const/4 v2, 0x1

    #calls: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->makeToast(Ljava/lang/String;I)V
    invoke-static {v0, v1, v2}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$12(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;Ljava/lang/String;I)V

    .line 898
    invoke-interface {p1}, Landroid/content/DialogInterface;->dismiss()V

    .line 899
    return-void
.end method
