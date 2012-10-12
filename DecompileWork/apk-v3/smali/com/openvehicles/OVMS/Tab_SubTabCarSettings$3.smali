.class Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$3;
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
    iput-object p1, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$3;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    .line 104
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method static synthetic access$0(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$3;)Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;
    .locals 1
    .parameter

    .prologue
    .line 104
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$3;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    return-object v0
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 5
    .parameter "arg0"

    .prologue
    .line 107
    new-instance v1, Landroid/app/AlertDialog$Builder;

    iget-object v2, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$3;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->mContext:Landroid/content/Context;
    invoke-static {v2}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$10(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Landroid/content/Context;

    move-result-object v2

    invoke-direct {v1, v2}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 109
    .local v1, builder:Landroid/app/AlertDialog$Builder;
    const-string v2, "This will consume about 5KB of wireless data."

    .line 108
    invoke-virtual {v1, v2}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v2

    .line 110
    const-string v3, "Retrieve data from car?"

    invoke-virtual {v2, v3}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v2

    .line 111
    const/4 v3, 0x1

    invoke-virtual {v2, v3}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    move-result-object v2

    .line 112
    const-string v3, "Retrieve"

    .line 113
    new-instance v4, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$3$1;

    invoke-direct {v4, p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$3$1;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$3;)V

    .line 112
    invoke-virtual {v2, v3, v4}, Landroid/app/AlertDialog$Builder;->setPositiveButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v2

    .line 120
    const-string v3, "Cancel"

    .line 121
    new-instance v4, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$3$2;

    invoke-direct {v4, p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$3$2;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$3;)V

    .line 120
    invoke-virtual {v2, v3, v4}, Landroid/app/AlertDialog$Builder;->setNegativeButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 127
    invoke-virtual {v1}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v0

    .line 128
    .local v0, alertDialog:Landroid/app/AlertDialog;
    iget-object v2, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$3;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    invoke-virtual {v2}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->isFinishing()Z

    move-result v2

    if-nez v2, :cond_0

    .line 129
    invoke-virtual {v0}, Landroid/app/AlertDialog;->show()V

    .line 130
    :cond_0
    return-void
.end method
