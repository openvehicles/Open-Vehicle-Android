.class Lcom/openvehicles/OVMS/TabInfo$2$1;
.super Ljava/lang/Object;
.source "TabInfo.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/TabInfo$2;->handleMessage(Landroid/os/Message;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$1:Lcom/openvehicles/OVMS/TabInfo$2;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/TabInfo$2;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabInfo$2$1;->this$1:Lcom/openvehicles/OVMS/TabInfo$2;

    .line 440
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 1
    .parameter "dialog"
    .parameter "which"

    .prologue
    .line 444
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo$2$1;->this$1:Lcom/openvehicles/OVMS/TabInfo$2;

    #getter for: Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabInfo$2;->access$0(Lcom/openvehicles/OVMS/TabInfo$2;)Lcom/openvehicles/OVMS/TabInfo;

    move-result-object v0

    #calls: Lcom/openvehicles/OVMS/TabInfo;->downloadLayout()V
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabInfo;->access$6(Lcom/openvehicles/OVMS/TabInfo;)V

    .line 445
    invoke-interface {p1}, Landroid/content/DialogInterface;->dismiss()V

    .line 446
    return-void
.end method
