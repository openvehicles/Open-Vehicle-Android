.class Lcom/openvehicles/OVMS/CarEditor$2$2;
.super Ljava/lang/Object;
.source "CarEditor.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/CarEditor$2;->onClick(Landroid/view/View;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$1:Lcom/openvehicles/OVMS/CarEditor$2;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/CarEditor$2;)V
    .locals 0
    .parameter

    .prologue
    .line 74
    iput-object p1, p0, Lcom/openvehicles/OVMS/CarEditor$2$2;->this$1:Lcom/openvehicles/OVMS/CarEditor$2;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 2
    .parameter "dialog"
    .parameter "id"

    .prologue
    .line 76
    iget-object v0, p0, Lcom/openvehicles/OVMS/CarEditor$2$2;->this$1:Lcom/openvehicles/OVMS/CarEditor$2;

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarEditor$2;->this$0:Lcom/openvehicles/OVMS/CarEditor;

    const-string v1, "DELETE"

    #calls: Lcom/openvehicles/OVMS/CarEditor;->closeEditor(Ljava/lang/String;)V
    invoke-static {v0, v1}, Lcom/openvehicles/OVMS/CarEditor;->access$000(Lcom/openvehicles/OVMS/CarEditor;Ljava/lang/String;)V

    .line 77
    return-void
.end method
