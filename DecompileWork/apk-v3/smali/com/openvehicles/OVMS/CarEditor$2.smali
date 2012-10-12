.class Lcom/openvehicles/OVMS/CarEditor$2;
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
    iput-object p1, p0, Lcom/openvehicles/OVMS/CarEditor$2;->this$0:Lcom/openvehicles/OVMS/CarEditor;

    .line 72
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method static synthetic access$0(Lcom/openvehicles/OVMS/CarEditor$2;)Lcom/openvehicles/OVMS/CarEditor;
    .locals 1
    .parameter

    .prologue
    .line 72
    iget-object v0, p0, Lcom/openvehicles/OVMS/CarEditor$2;->this$0:Lcom/openvehicles/OVMS/CarEditor;

    return-object v0
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 4
    .parameter "arg0"

    .prologue
    .line 74
    new-instance v0, Landroid/app/AlertDialog$Builder;

    iget-object v1, p0, Lcom/openvehicles/OVMS/CarEditor$2;->this$0:Lcom/openvehicles/OVMS/CarEditor;

    invoke-direct {v0, v1}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 75
    .local v0, builder:Landroid/app/AlertDialog$Builder;
    const-string v1, "Delete this car?"

    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v1

    .line 76
    const/4 v2, 0x0

    invoke-virtual {v1, v2}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    move-result-object v1

    .line 77
    const-string v2, "Yes"

    new-instance v3, Lcom/openvehicles/OVMS/CarEditor$2$1;

    invoke-direct {v3, p0}, Lcom/openvehicles/OVMS/CarEditor$2$1;-><init>(Lcom/openvehicles/OVMS/CarEditor$2;)V

    invoke-virtual {v1, v2, v3}, Landroid/app/AlertDialog$Builder;->setPositiveButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v1

    .line 82
    const-string v2, "No"

    new-instance v3, Lcom/openvehicles/OVMS/CarEditor$2$2;

    invoke-direct {v3, p0}, Lcom/openvehicles/OVMS/CarEditor$2$2;-><init>(Lcom/openvehicles/OVMS/CarEditor$2;)V

    invoke-virtual {v1, v2, v3}, Landroid/app/AlertDialog$Builder;->setNegativeButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 87
    invoke-virtual {v0}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v1

    invoke-virtual {v1}, Landroid/app/AlertDialog;->show()V

    .line 88
    return-void
.end method
