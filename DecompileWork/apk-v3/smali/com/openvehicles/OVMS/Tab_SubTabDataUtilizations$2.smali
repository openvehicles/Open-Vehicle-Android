.class Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations$2;
.super Ljava/lang/Object;
.source "Tab_SubTabDataUtilizations.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->onCreate(Landroid/os/Bundle;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations$2;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;

    .line 55
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 1
    .parameter "v"

    .prologue
    .line 57
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations$2;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;

    #calls: Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->requestData()V
    invoke-static {v0}, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->access$2(Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;)V

    .line 58
    return-void
.end method
