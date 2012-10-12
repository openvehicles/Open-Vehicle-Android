.class Lcom/openvehicles/OVMS/OVMSActivity$ServerCommandResponseHandler;
.super Ljava/lang/Object;
.source "OVMSActivity.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/openvehicles/OVMS/OVMSActivity;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "ServerCommandResponseHandler"
.end annotation


# instance fields
.field message:Ljava/lang/String;

.field final synthetic this$0:Lcom/openvehicles/OVMS/OVMSActivity;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/OVMSActivity;Ljava/lang/String;)V
    .locals 0
    .parameter
    .parameter "s"

    .prologue
    .line 271
    iput-object p1, p0, Lcom/openvehicles/OVMS/OVMSActivity$ServerCommandResponseHandler;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 272
    iput-object p2, p0, Lcom/openvehicles/OVMS/OVMSActivity$ServerCommandResponseHandler;->message:Ljava/lang/String;

    .line 273
    return-void
.end method


# virtual methods
.method public run()V
    .locals 3

    .prologue
    .line 276
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity$ServerCommandResponseHandler;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$ServerCommandResponseHandler;->message:Ljava/lang/String;

    const/4 v2, 0x0

    invoke-static {v0, v1, v2}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v0

    .line 277
    invoke-virtual {v0}, Landroid/widget/Toast;->show()V

    .line 278
    return-void
.end method
