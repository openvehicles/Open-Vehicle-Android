.class Lcom/openvehicles/OVMS/Tab_SubTabNotifications$1;
.super Landroid/os/Handler;
.source "Tab_SubTabNotifications.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/openvehicles/OVMS/Tab_SubTabNotifications;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/openvehicles/OVMS/Tab_SubTabNotifications;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/Tab_SubTabNotifications;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabNotifications;

    .line 127
    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 8
    .parameter "msg"

    .prologue
    .line 129
    iget-object v2, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabNotifications;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->lastVehicleID:Ljava/lang/String;
    invoke-static {v2}, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->access$0(Lcom/openvehicles/OVMS/Tab_SubTabNotifications;)Ljava/lang/String;

    move-result-object v2

    iget-object v3, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabNotifications;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v3}, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->access$1(Lcom/openvehicles/OVMS/Tab_SubTabNotifications;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v3

    iget-object v3, v3, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    invoke-virtual {v2, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-nez v2, :cond_0

    iget-object v2, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabNotifications;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->notifications:Lcom/openvehicles/OVMS/OVMSNotifications;
    invoke-static {v2}, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->access$2(Lcom/openvehicles/OVMS/Tab_SubTabNotifications;)Lcom/openvehicles/OVMS/OVMSNotifications;

    move-result-object v2

    iget-object v2, v2, Lcom/openvehicles/OVMS/OVMSNotifications;->Notifications:Ljava/util/ArrayList;

    invoke-virtual {v2}, Ljava/util/ArrayList;->size()I

    move-result v2

    if-nez v2, :cond_0

    .line 130
    iget-object v2, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabNotifications;

    const-string v3, "No notifications received"

    const/4 v4, 0x0

    invoke-static {v2, v3, v4}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v2

    invoke-virtual {v2}, Landroid/widget/Toast;->show()V

    .line 132
    :cond_0
    iget-object v2, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabNotifications;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->lastVehicleID:Ljava/lang/String;
    invoke-static {v2}, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->access$0(Lcom/openvehicles/OVMS/Tab_SubTabNotifications;)Ljava/lang/String;

    move-result-object v2

    iget-object v3, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabNotifications;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v3}, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->access$1(Lcom/openvehicles/OVMS/Tab_SubTabNotifications;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v3

    iget-object v3, v3, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    invoke-virtual {v2, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_1

    iget-object v2, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabNotifications;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->notifications:Lcom/openvehicles/OVMS/OVMSNotifications;
    invoke-static {v2}, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->access$2(Lcom/openvehicles/OVMS/Tab_SubTabNotifications;)Lcom/openvehicles/OVMS/OVMSNotifications;

    move-result-object v2

    iget-object v2, v2, Lcom/openvehicles/OVMS/OVMSNotifications;->Notifications:Ljava/util/ArrayList;

    invoke-virtual {v2}, Ljava/util/ArrayList;->size()I

    move-result v2

    iget-object v3, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabNotifications;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->cachedData:[Lcom/openvehicles/OVMS/NotificationData;
    invoke-static {v3}, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->access$3(Lcom/openvehicles/OVMS/Tab_SubTabNotifications;)[Lcom/openvehicles/OVMS/NotificationData;

    move-result-object v3

    array-length v3, v3

    if-ne v2, v3, :cond_1

    .line 149
    :goto_0
    return-void

    .line 135
    :cond_1
    iget-object v2, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabNotifications;

    iget-object v3, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabNotifications;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v3}, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->access$1(Lcom/openvehicles/OVMS/Tab_SubTabNotifications;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v3

    iget-object v3, v3, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    #setter for: Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->lastVehicleID:Ljava/lang/String;
    invoke-static {v2, v3}, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->access$4(Lcom/openvehicles/OVMS/Tab_SubTabNotifications;Ljava/lang/String;)V

    .line 137
    iget-object v2, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabNotifications;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->notifications:Lcom/openvehicles/OVMS/OVMSNotifications;
    invoke-static {v2}, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->access$2(Lcom/openvehicles/OVMS/Tab_SubTabNotifications;)Lcom/openvehicles/OVMS/OVMSNotifications;

    move-result-object v2

    iget-object v2, v2, Lcom/openvehicles/OVMS/OVMSNotifications;->Notifications:Ljava/util/ArrayList;

    invoke-virtual {v2}, Ljava/util/ArrayList;->size()I

    move-result v2

    new-array v1, v2, [Lcom/openvehicles/OVMS/NotificationData;

    .line 138
    .local v1, notificationData:[Lcom/openvehicles/OVMS/NotificationData;
    iget-object v2, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabNotifications;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->notifications:Lcom/openvehicles/OVMS/OVMSNotifications;
    invoke-static {v2}, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->access$2(Lcom/openvehicles/OVMS/Tab_SubTabNotifications;)Lcom/openvehicles/OVMS/OVMSNotifications;

    move-result-object v2

    iget-object v2, v2, Lcom/openvehicles/OVMS/OVMSNotifications;->Notifications:Ljava/util/ArrayList;

    invoke-virtual {v2, v1}, Ljava/util/ArrayList;->toArray([Ljava/lang/Object;)[Ljava/lang/Object;

    .line 141
    iget-object v2, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabNotifications;

    array-length v3, v1

    new-array v3, v3, [Lcom/openvehicles/OVMS/NotificationData;

    #setter for: Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->cachedData:[Lcom/openvehicles/OVMS/NotificationData;
    invoke-static {v2, v3}, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->access$5(Lcom/openvehicles/OVMS/Tab_SubTabNotifications;[Lcom/openvehicles/OVMS/NotificationData;)V

    .line 142
    const/4 v0, 0x0

    .local v0, idx:I
    :goto_1
    iget-object v2, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabNotifications;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->cachedData:[Lcom/openvehicles/OVMS/NotificationData;
    invoke-static {v2}, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->access$3(Lcom/openvehicles/OVMS/Tab_SubTabNotifications;)[Lcom/openvehicles/OVMS/NotificationData;

    move-result-object v2

    array-length v2, v2

    if-lt v0, v2, :cond_2

    .line 145
    iget-object v2, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabNotifications;

    new-instance v3, Lcom/openvehicles/OVMS/Tab_SubTabNotifications$ItemsAdapter;

    iget-object v4, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabNotifications;

    iget-object v5, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabNotifications;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->mContext:Landroid/content/Context;
    invoke-static {v5}, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->access$6(Lcom/openvehicles/OVMS/Tab_SubTabNotifications;)Landroid/content/Context;

    move-result-object v5

    const v6, 0x7f030009

    .line 146
    iget-object v7, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabNotifications;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->cachedData:[Lcom/openvehicles/OVMS/NotificationData;
    invoke-static {v7}, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->access$3(Lcom/openvehicles/OVMS/Tab_SubTabNotifications;)[Lcom/openvehicles/OVMS/NotificationData;

    move-result-object v7

    invoke-direct {v3, v4, v5, v6, v7}, Lcom/openvehicles/OVMS/Tab_SubTabNotifications$ItemsAdapter;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabNotifications;Landroid/content/Context;I[Lcom/openvehicles/OVMS/NotificationData;)V

    .line 145
    #setter for: Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->adapter:Lcom/openvehicles/OVMS/Tab_SubTabNotifications$ItemsAdapter;
    invoke-static {v2, v3}, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->access$7(Lcom/openvehicles/OVMS/Tab_SubTabNotifications;Lcom/openvehicles/OVMS/Tab_SubTabNotifications$ItemsAdapter;)V

    .line 148
    iget-object v2, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabNotifications;

    iget-object v3, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabNotifications;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->adapter:Lcom/openvehicles/OVMS/Tab_SubTabNotifications$ItemsAdapter;
    invoke-static {v3}, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->access$8(Lcom/openvehicles/OVMS/Tab_SubTabNotifications;)Lcom/openvehicles/OVMS/Tab_SubTabNotifications$ItemsAdapter;

    move-result-object v3

    invoke-virtual {v2, v3}, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->setListAdapter(Landroid/widget/ListAdapter;)V

    goto :goto_0

    .line 143
    :cond_2
    iget-object v2, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabNotifications;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->cachedData:[Lcom/openvehicles/OVMS/NotificationData;
    invoke-static {v2}, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->access$3(Lcom/openvehicles/OVMS/Tab_SubTabNotifications;)[Lcom/openvehicles/OVMS/NotificationData;

    move-result-object v2

    array-length v3, v1

    add-int/lit8 v3, v3, -0x1

    sub-int/2addr v3, v0

    aget-object v3, v1, v3

    aput-object v3, v2, v0

    .line 142
    add-int/lit8 v0, v0, 0x1

    goto :goto_1
.end method
