.class Lcom/openvehicles/OVMS/CarEditor$1;
.super Ljava/lang/Object;
.source "CarEditor.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/CarEditor;->onCreate(Landroid/os/Bundle;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/openvehicles/OVMS/CarEditor;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/CarEditor;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/CarEditor$1;->this$0:Lcom/openvehicles/OVMS/CarEditor;

    .line 60
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 2
    .parameter "arg0"

    .prologue
    .line 62
    iget-object v0, p0, Lcom/openvehicles/OVMS/CarEditor$1;->this$0:Lcom/openvehicles/OVMS/CarEditor;

    const-string v1, "CANCEL"

    #calls: Lcom/openvehicles/OVMS/CarEditor;->closeEditor(Ljava/lang/String;)V
    invoke-static {v0, v1}, Lcom/openvehicles/OVMS/CarEditor;->access$0(Lcom/openvehicles/OVMS/CarEditor;Ljava/lang/String;)V

    .line 63
    return-void
.end method
