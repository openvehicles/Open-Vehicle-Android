.class Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$2;
.super Ljava/lang/Object;
.source "Tab_SubTabCarSettings.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->onCreate(Landroid/os/Bundle;)V
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
    iput-object p1, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$2;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    .line 98
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 1
    .parameter "arg0"

    .prologue
    .line 100
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$2;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    #calls: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->commitSettings()V
    invoke-static {v0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$9(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)V

    .line 101
    return-void
.end method
