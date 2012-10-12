.class public Lcom/openvehicles/OVMS/TabNotifications;
.super Landroid/app/ListActivity;
.source "TabNotifications.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/openvehicles/OVMS/TabNotifications$ItemsAdapter;
    }
.end annotation


# instance fields
.field private adapter:Lcom/openvehicles/OVMS/TabNotifications$ItemsAdapter;

.field private cachedData:[Lcom/openvehicles/OVMS/NotificationData;

.field private handler:Landroid/os/Handler;

.field private mContext:Landroid/content/Context;

.field private notifications:Lcom/openvehicles/OVMS/OVMSNotifications;


# direct methods
.method public constructor <init>()V
    .locals 1

    .prologue
    .line 38
    invoke-direct {p0}, Landroid/app/ListActivity;-><init>()V

    .line 109
    new-instance v0, Lcom/openvehicles/OVMS/TabNotifications$2;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/TabNotifications$2;-><init>(Lcom/openvehicles/OVMS/TabNotifications;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabNotifications;->handler:Landroid/os/Handler;

    return-void
.end method

.method static synthetic access$000(Lcom/openvehicles/OVMS/TabNotifications;)Lcom/openvehicles/OVMS/OVMSNotifications;
    .locals 1
    .parameter "x0"

    .prologue
    .line 38
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabNotifications;->notifications:Lcom/openvehicles/OVMS/OVMSNotifications;

    return-object v0
.end method

.method static synthetic access$100(Lcom/openvehicles/OVMS/TabNotifications;)[Lcom/openvehicles/OVMS/NotificationData;
    .locals 1
    .parameter "x0"

    .prologue
    .line 38
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabNotifications;->cachedData:[Lcom/openvehicles/OVMS/NotificationData;

    return-object v0
.end method

.method static synthetic access$102(Lcom/openvehicles/OVMS/TabNotifications;[Lcom/openvehicles/OVMS/NotificationData;)[Lcom/openvehicles/OVMS/NotificationData;
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 38
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabNotifications;->cachedData:[Lcom/openvehicles/OVMS/NotificationData;

    return-object p1
.end method

.method static synthetic access$200(Lcom/openvehicles/OVMS/TabNotifications;)Lcom/openvehicles/OVMS/TabNotifications$ItemsAdapter;
    .locals 1
    .parameter "x0"

    .prologue
    .line 38
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabNotifications;->adapter:Lcom/openvehicles/OVMS/TabNotifications$ItemsAdapter;

    return-object v0
.end method

.method static synthetic access$202(Lcom/openvehicles/OVMS/TabNotifications;Lcom/openvehicles/OVMS/TabNotifications$ItemsAdapter;)Lcom/openvehicles/OVMS/TabNotifications$ItemsAdapter;
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 38
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabNotifications;->adapter:Lcom/openvehicles/OVMS/TabNotifications$ItemsAdapter;

    return-object p1
.end method

.method static synthetic access$300(Lcom/openvehicles/OVMS/TabNotifications;)Landroid/content/Context;
    .locals 1
    .parameter "x0"

    .prologue
    .line 38
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabNotifications;->mContext:Landroid/content/Context;

    return-object v0
.end method


# virtual methods
.method public Refresh()V
    .locals 2

    .prologue
    .line 126
    new-instance v0, Lcom/openvehicles/OVMS/OVMSNotifications;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/OVMSNotifications;-><init>(Landroid/content/Context;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabNotifications;->notifications:Lcom/openvehicles/OVMS/OVMSNotifications;

    .line 127
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabNotifications;->handler:Landroid/os/Handler;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    .line 129
    return-void
.end method

.method public onCreate(Landroid/os/Bundle;)V
    .locals 1
    .parameter "savedInstanceState"

    .prologue
    .line 43
    invoke-super {p0, p1}, Landroid/app/ListActivity;->onCreate(Landroid/os/Bundle;)V

    .line 44
    const v0, 0x7f03000a

    invoke-virtual {p0, v0}, Lcom/openvehicles/OVMS/TabNotifications;->setContentView(I)V

    .line 46
    new-instance v0, Lcom/openvehicles/OVMS/OVMSNotifications;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/OVMSNotifications;-><init>(Landroid/content/Context;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabNotifications;->notifications:Lcom/openvehicles/OVMS/OVMSNotifications;

    .line 47
    iput-object p0, p0, Lcom/openvehicles/OVMS/TabNotifications;->mContext:Landroid/content/Context;

    .line 48
    return-void
.end method

.method protected onListItemClick(Landroid/widget/ListView;Landroid/view/View;IJ)V
    .locals 4
    .parameter "l"
    .parameter "v"
    .parameter "position"
    .parameter "id"

    .prologue
    .line 92
    const-string v1, "OVMS"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Displaying notification: #"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 94
    new-instance v0, Landroid/app/AlertDialog$Builder;

    invoke-direct {v0, p0}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 96
    .local v0, builder:Landroid/app/AlertDialog$Builder;
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabNotifications;->cachedData:[Lcom/openvehicles/OVMS/NotificationData;

    aget-object v1, v1, p3

    iget-object v1, v1, Lcom/openvehicles/OVMS/NotificationData;->Message:Ljava/lang/String;

    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v1

    iget-object v2, p0, Lcom/openvehicles/OVMS/TabNotifications;->cachedData:[Lcom/openvehicles/OVMS/NotificationData;

    aget-object v2, v2, p3

    iget-object v2, v2, Lcom/openvehicles/OVMS/NotificationData;->Title:Ljava/lang/String;

    invoke-virtual {v1, v2}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v1

    const/4 v2, 0x0

    invoke-virtual {v1, v2}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    move-result-object v1

    const-string v2, "Close"

    new-instance v3, Lcom/openvehicles/OVMS/TabNotifications$1;

    invoke-direct {v3, p0}, Lcom/openvehicles/OVMS/TabNotifications$1;-><init>(Lcom/openvehicles/OVMS/TabNotifications;)V

    invoke-virtual {v1, v2, v3}, Landroid/app/AlertDialog$Builder;->setPositiveButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 106
    invoke-virtual {v0}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v1

    invoke-virtual {v1}, Landroid/app/AlertDialog;->show()V

    .line 107
    return-void
.end method
