.class Lcom/openvehicles/OVMS/TabInfo_xlarge$14;
.super Ljava/lang/Object;
.source "TabInfo_xlarge.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/TabInfo_xlarge;->updateCarLayoutUI()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/TabInfo_xlarge;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$14;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    .line 537
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 1
    .parameter "dialog"
    .parameter "which"

    .prologue
    .line 540
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$14;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #calls: Lcom/openvehicles/OVMS/TabInfo_xlarge;->downloadLayout()V
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$16(Lcom/openvehicles/OVMS/TabInfo_xlarge;)V

    .line 541
    invoke-interface {p1}, Landroid/content/DialogInterface;->dismiss()V

    .line 542
    return-void
.end method
