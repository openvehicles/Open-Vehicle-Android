.class Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16;
.super Ljava/lang/Object;
.source "Tab_SubTabCarSettings.java"

# interfaces
.implements Landroid/preference/Preference$OnPreferenceClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->wireUpPrefButtons()V
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
    iput-object p1, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    .line 331
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method static synthetic access$0(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16;)Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;
    .locals 1
    .parameter

    .prologue
    .line 331
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    return-object v0
.end method


# virtual methods
.method public onPreferenceClick(Landroid/preference/Preference;)Z
    .locals 10
    .parameter "preference"

    .prologue
    const/4 v9, 0x1

    .line 333
    iget-object v6, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->mContext:Landroid/content/Context;
    invoke-static {v6}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$10(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Landroid/content/Context;

    move-result-object v6

    invoke-static {v6}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object v2

    .line 334
    .local v2, factory:Landroid/view/LayoutInflater;
    const v6, 0x7f030004

    const/4 v7, 0x0

    invoke-virtual {v2, v6, v7}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object v5

    .line 335
    .local v5, smsView:Landroid/view/View;
    const v6, 0x7f09000c

    invoke-virtual {v5, v6}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v3

    check-cast v3, Landroid/widget/EditText;

    .line 336
    .local v3, input1:Landroid/widget/EditText;
    const v6, 0x7f09000d

    invoke-virtual {v5, v6}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v4

    check-cast v4, Landroid/widget/EditText;

    .line 338
    .local v4, input2:Landroid/widget/EditText;
    new-instance v1, Landroid/app/AlertDialog$Builder;

    iget-object v6, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->mContext:Landroid/content/Context;
    invoke-static {v6}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$10(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Landroid/content/Context;

    move-result-object v6

    invoke-direct {v1, v6}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 340
    .local v1, builder:Landroid/app/AlertDialog$Builder;
    const-string v6, "Send SMS"

    invoke-virtual {v1, v6}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v6

    .line 341
    invoke-virtual {v6, v5}, Landroid/app/AlertDialog$Builder;->setView(Landroid/view/View;)Landroid/app/AlertDialog$Builder;

    move-result-object v6

    .line 342
    invoke-virtual {v6, v9}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    move-result-object v6

    .line 343
    const-string v7, "Send"

    .line 344
    new-instance v8, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16$1;

    invoke-direct {v8, p0, v3, v4}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16$1;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16;Landroid/widget/EditText;Landroid/widget/EditText;)V

    .line 343
    invoke-virtual {v6, v7, v8}, Landroid/app/AlertDialog$Builder;->setPositiveButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v6

    .line 361
    const-string v7, "Cancel"

    .line 362
    new-instance v8, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16$2;

    invoke-direct {v8, p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16$2;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16;)V

    .line 361
    invoke-virtual {v6, v7, v8}, Landroid/app/AlertDialog$Builder;->setNegativeButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 368
    invoke-virtual {v1}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v0

    .line 369
    .local v0, alertDialog:Landroid/app/AlertDialog;
    iget-object v6, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    invoke-virtual {v6}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->isFinishing()Z

    move-result v6

    if-nez v6, :cond_0

    .line 370
    invoke-virtual {v0}, Landroid/app/AlertDialog;->show()V

    .line 372
    :cond_0
    return v9
.end method
