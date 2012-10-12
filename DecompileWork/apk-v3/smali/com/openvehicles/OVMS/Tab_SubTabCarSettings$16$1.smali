.class Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16$1;
.super Ljava/lang/Object;
.source "Tab_SubTabCarSettings.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16;->onPreferenceClick(Landroid/preference/Preference;)Z
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$1:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16;

.field private final synthetic val$input1:Landroid/widget/EditText;

.field private final synthetic val$input2:Landroid/widget/EditText;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16;Landroid/widget/EditText;Landroid/widget/EditText;)V
    .locals 0
    .parameter
    .parameter
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16$1;->this$1:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16;

    iput-object p2, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16$1;->val$input1:Landroid/widget/EditText;

    iput-object p3, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16$1;->val$input2:Landroid/widget/EditText;

    .line 344
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 5
    .parameter "dialog"
    .parameter "id"

    .prologue
    const/4 v4, 0x0

    .line 348
    iget-object v2, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16$1;->val$input1:Landroid/widget/EditText;

    invoke-virtual {v2}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v2

    invoke-interface {v2}, Landroid/text/Editable;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v0

    .line 349
    .local v0, destination_phone_number:Ljava/lang/String;
    iget-object v2, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16$1;->val$input2:Landroid/widget/EditText;

    invoke-virtual {v2}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v2

    invoke-interface {v2}, Landroid/text/Editable;->toString()Ljava/lang/String;

    move-result-object v1

    .line 350
    .local v1, message_content:Ljava/lang/String;
    invoke-virtual {v0}, Ljava/lang/String;->length()I

    move-result v2

    if-lez v2, :cond_0

    .line 352
    iget-object v2, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16$1;->this$1:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;
    invoke-static {v2}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16;->access$0(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16;)Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    move-result-object v2

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->mOVMSActivity:Lcom/openvehicles/OVMS/OVMSActivity;
    invoke-static {v2}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$11(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Lcom/openvehicles/OVMS/OVMSActivity;

    move-result-object v2

    .line 353
    invoke-static {v0, v1}, Lcom/openvehicles/OVMS/ServerCommands;->SEND_SMS(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    .line 352
    invoke-virtual {v2, v3}, Lcom/openvehicles/OVMS/OVMSActivity;->SendServerCommand(Ljava/lang/String;)Z

    .line 355
    iget-object v2, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16$1;->this$1:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;
    invoke-static {v2}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16;->access$0(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16;)Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    move-result-object v2

    const-string v3, "Request sent"

    #calls: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->makeToast(Ljava/lang/String;I)V
    invoke-static {v2, v3, v4}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$12(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;Ljava/lang/String;I)V

    .line 356
    invoke-interface {p1}, Landroid/content/DialogInterface;->dismiss()V

    .line 359
    :goto_0
    return-void

    .line 358
    :cond_0
    iget-object v2, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16$1;->this$1:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;
    invoke-static {v2}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16;->access$0(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16;)Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    move-result-object v2

    const-string v3, "Invalid format"

    #calls: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->makeToast(Ljava/lang/String;I)V
    invoke-static {v2, v3, v4}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$12(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;Ljava/lang/String;I)V

    goto :goto_0
.end method
