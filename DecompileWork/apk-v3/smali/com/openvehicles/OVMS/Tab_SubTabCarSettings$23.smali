.class Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$23;
.super Ljava/lang/Object;
.source "Tab_SubTabCarSettings.java"

# interfaces
.implements Landroid/content/DialogInterface$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->commitSettings()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

.field private final synthetic val$changedSettings:Ljava/util/LinkedHashMap;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;Ljava/util/LinkedHashMap;)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$23;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    iput-object p2, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$23;->val$changedSettings:Ljava/util/LinkedHashMap;

    .line 866
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/content/DialogInterface;I)V
    .locals 7
    .parameter "dialog"
    .parameter "id"

    .prologue
    const/4 v6, 0x2

    const/4 v5, 0x0

    .line 868
    invoke-interface {p1}, Landroid/content/DialogInterface;->dismiss()V

    .line 870
    iget-object v2, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$23;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    const-string v3, "Commiting - please wait..."

    const/4 v4, 0x1

    #calls: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->makeToast(Ljava/lang/String;I)V
    invoke-static {v2, v3, v4}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$12(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;Ljava/lang/String;I)V

    .line 873
    iget-object v2, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$23;->val$changedSettings:Ljava/util/LinkedHashMap;

    invoke-virtual {v2}, Ljava/util/LinkedHashMap;->keySet()Ljava/util/Set;

    move-result-object v2

    invoke-interface {v2}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v3

    :cond_0
    :goto_0
    invoke-interface {v3}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-nez v2, :cond_1

    .line 891
    iget-object v2, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$23;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    const-string v3, "Completed"

    #calls: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->makeToast(Ljava/lang/String;I)V
    invoke-static {v2, v3, v5}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$12(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;Ljava/lang/String;I)V

    .line 892
    return-void

    .line 873
    :cond_1
    invoke-interface {v3}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/Integer;

    invoke-virtual {v2}, Ljava/lang/Integer;->intValue()I

    move-result v1

    .line 880
    .local v1, key:I
    const-string v0, ""

    .line 881
    .local v0, cmd:Ljava/lang/String;
    iget-object v2, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$23;->val$changedSettings:Ljava/util/LinkedHashMap;

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v4

    invoke-virtual {v2, v4}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, [Ljava/lang/String;

    aget-object v2, v2, v5

    const-string v4, "PARAM"

    invoke-virtual {v2, v4}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_2

    .line 882
    iget-object v2, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$23;->val$changedSettings:Ljava/util/LinkedHashMap;

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v4

    invoke-virtual {v2, v4}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, [Ljava/lang/String;

    aget-object v2, v2, v6

    invoke-static {v1, v2}, Lcom/openvehicles/OVMS/ServerCommands;->SET_PARAMETER(ILjava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 888
    :goto_1
    iget-object v2, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$23;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->mOVMSActivity:Lcom/openvehicles/OVMS/OVMSActivity;
    invoke-static {v2}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$11(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Lcom/openvehicles/OVMS/OVMSActivity;

    move-result-object v2

    invoke-virtual {v2, v0}, Lcom/openvehicles/OVMS/OVMSActivity;->SendServerCommand(Ljava/lang/String;)Z

    goto :goto_0

    .line 883
    :cond_2
    iget-object v2, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$23;->val$changedSettings:Ljava/util/LinkedHashMap;

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v4

    invoke-virtual {v2, v4}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, [Ljava/lang/String;

    aget-object v2, v2, v5

    const-string v4, "FEATURE"

    invoke-virtual {v2, v4}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_0

    .line 884
    iget-object v2, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$23;->val$changedSettings:Ljava/util/LinkedHashMap;

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v4

    invoke-virtual {v2, v4}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, [Ljava/lang/String;

    aget-object v2, v2, v6

    invoke-static {v1, v2}, Lcom/openvehicles/OVMS/ServerCommands;->SET_FEATURE(ILjava/lang/String;)Ljava/lang/String;

    move-result-object v0

    goto :goto_1
.end method
