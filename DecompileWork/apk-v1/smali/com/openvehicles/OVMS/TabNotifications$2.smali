.class Lcom/openvehicles/OVMS/TabNotifications$2;
.super Landroid/os/Handler;
.source "TabNotifications.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/openvehicles/OVMS/TabNotifications;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/openvehicles/OVMS/TabNotifications;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/TabNotifications;)V
    .locals 0
    .parameter

    .prologue
    .line 109
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabNotifications$2;->this$0:Lcom/openvehicles/OVMS/TabNotifications;

    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 8
    .parameter "msg"

    .prologue
    .line 111
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabNotifications$2;->this$0:Lcom/openvehicles/OVMS/TabNotifications;

    #getter for: Lcom/openvehicles/OVMS/TabNotifications;->notifications:Lcom/openvehicles/OVMS/OVMSNotifications;
    invoke-static {v2}, Lcom/openvehicles/OVMS/TabNotifications;->access$000(Lcom/openvehicles/OVMS/TabNotifications;)Lcom/openvehicles/OVMS/OVMSNotifications;

    move-result-object v2

    iget-object v2, v2, Lcom/openvehicles/OVMS/OVMSNotifications;->Notifications:Ljava/util/ArrayList;

    invoke-virtual {v2}, Ljava/util/ArrayList;->size()I

    move-result v2

    new-array v0, v2, [Lcom/openvehicles/OVMS/NotificationData;

    .line 112
    .local v0, data:[Lcom/openvehicles/OVMS/NotificationData;
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabNotifications$2;->this$0:Lcom/openvehicles/OVMS/TabNotifications;

    #getter for: Lcom/openvehicles/OVMS/TabNotifications;->notifications:Lcom/openvehicles/OVMS/OVMSNotifications;
    invoke-static {v2}, Lcom/openvehicles/OVMS/TabNotifications;->access$000(Lcom/openvehicles/OVMS/TabNotifications;)Lcom/openvehicles/OVMS/OVMSNotifications;

    move-result-object v2

    iget-object v2, v2, Lcom/openvehicles/OVMS/OVMSNotifications;->Notifications:Ljava/util/ArrayList;

    invoke-virtual {v2, v0}, Ljava/util/ArrayList;->toArray([Ljava/lang/Object;)[Ljava/lang/Object;

    .line 115
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabNotifications$2;->this$0:Lcom/openvehicles/OVMS/TabNotifications;

    array-length v3, v0

    new-array v3, v3, [Lcom/openvehicles/OVMS/NotificationData;

    #setter for: Lcom/openvehicles/OVMS/TabNotifications;->cachedData:[Lcom/openvehicles/OVMS/NotificationData;
    invoke-static {v2, v3}, Lcom/openvehicles/OVMS/TabNotifications;->access$102(Lcom/openvehicles/OVMS/TabNotifications;[Lcom/openvehicles/OVMS/NotificationData;)[Lcom/openvehicles/OVMS/NotificationData;

    .line 116
    const/4 v1, 0x0

    .local v1, idx:I
    :goto_0
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabNotifications$2;->this$0:Lcom/openvehicles/OVMS/TabNotifications;

    #getter for: Lcom/openvehicles/OVMS/TabNotifications;->cachedData:[Lcom/openvehicles/OVMS/NotificationData;
    invoke-static {v2}, Lcom/openvehicles/OVMS/TabNotifications;->access$100(Lcom/openvehicles/OVMS/TabNotifications;)[Lcom/openvehicles/OVMS/NotificationData;

    move-result-object v2

    array-length v2, v2

    if-ge v1, v2, :cond_0

    .line 117
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabNotifications$2;->this$0:Lcom/openvehicles/OVMS/TabNotifications;

    #getter for: Lcom/openvehicles/OVMS/TabNotifications;->cachedData:[Lcom/openvehicles/OVMS/NotificationData;
    invoke-static {v2}, Lcom/openvehicles/OVMS/TabNotifications;->access$100(Lcom/openvehicles/OVMS/TabNotifications;)[Lcom/openvehicles/OVMS/NotificationData;

    move-result-object v2

    array-length v3, v0

    add-int/lit8 v3, v3, -0x1

    sub-int/2addr v3, v1

    aget-object v3, v0, v3

    aput-object v3, v2, v1

    .line 116
    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    .line 119
    :cond_0
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabNotifications$2;->this$0:Lcom/openvehicles/OVMS/TabNotifications;

    new-instance v3, Lcom/openvehicles/OVMS/TabNotifications$ItemsAdapter;

    iget-object v4, p0, Lcom/openvehicles/OVMS/TabNotifications$2;->this$0:Lcom/openvehicles/OVMS/TabNotifications;

    iget-object v5, p0, Lcom/openvehicles/OVMS/TabNotifications$2;->this$0:Lcom/openvehicles/OVMS/TabNotifications;

    #getter for: Lcom/openvehicles/OVMS/TabNotifications;->mContext:Landroid/content/Context;
    invoke-static {v5}, Lcom/openvehicles/OVMS/TabNotifications;->access$300(Lcom/openvehicles/OVMS/TabNotifications;)Landroid/content/Context;

    move-result-object v5

    const v6, 0x7f03000b

    iget-object v7, p0, Lcom/openvehicles/OVMS/TabNotifications$2;->this$0:Lcom/openvehicles/OVMS/TabNotifications;

    #getter for: Lcom/openvehicles/OVMS/TabNotifications;->cachedData:[Lcom/openvehicles/OVMS/NotificationData;
    invoke-static {v7}, Lcom/openvehicles/OVMS/TabNotifications;->access$100(Lcom/openvehicles/OVMS/TabNotifications;)[Lcom/openvehicles/OVMS/NotificationData;

    move-result-object v7

    invoke-direct {v3, v4, v5, v6, v7}, Lcom/openvehicles/OVMS/TabNotifications$ItemsAdapter;-><init>(Lcom/openvehicles/OVMS/TabNotifications;Landroid/content/Context;I[Lcom/openvehicles/OVMS/NotificationData;)V

    #setter for: Lcom/openvehicles/OVMS/TabNotifications;->adapter:Lcom/openvehicles/OVMS/TabNotifications$ItemsAdapter;
    invoke-static {v2, v3}, Lcom/openvehicles/OVMS/TabNotifications;->access$202(Lcom/openvehicles/OVMS/TabNotifications;Lcom/openvehicles/OVMS/TabNotifications$ItemsAdapter;)Lcom/openvehicles/OVMS/TabNotifications$ItemsAdapter;

    .line 121
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabNotifications$2;->this$0:Lcom/openvehicles/OVMS/TabNotifications;

    iget-object v3, p0, Lcom/openvehicles/OVMS/TabNotifications$2;->this$0:Lcom/openvehicles/OVMS/TabNotifications;

    #getter for: Lcom/openvehicles/OVMS/TabNotifications;->adapter:Lcom/openvehicles/OVMS/TabNotifications$ItemsAdapter;
    invoke-static {v3}, Lcom/openvehicles/OVMS/TabNotifications;->access$200(Lcom/openvehicles/OVMS/TabNotifications;)Lcom/openvehicles/OVMS/TabNotifications$ItemsAdapter;

    move-result-object v3

    invoke-virtual {v2, v3}, Lcom/openvehicles/OVMS/TabNotifications;->setListAdapter(Landroid/widget/ListAdapter;)V

    .line 122
    return-void
.end method
