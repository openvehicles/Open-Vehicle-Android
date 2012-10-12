.class public Lcom/openvehicles/OVMS/Tab_SubTabNotifications;
.super Landroid/app/ListActivity;
.source "Tab_SubTabNotifications.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/openvehicles/OVMS/Tab_SubTabNotifications$ItemsAdapter;
    }
.end annotation


# instance fields
.field private adapter:Lcom/openvehicles/OVMS/Tab_SubTabNotifications$ItemsAdapter;

.field private cachedData:[Lcom/openvehicles/OVMS/NotificationData;

.field private data:Lcom/openvehicles/OVMS/CarData;

.field private handler:Landroid/os/Handler;

.field private isLoggedIn:Z

.field private lastVehicleID:Ljava/lang/String;

.field private mContext:Landroid/content/Context;

.field private mOVMSActivity:Lcom/openvehicles/OVMS/OVMSActivity;

.field private notifications:Lcom/openvehicles/OVMS/OVMSNotifications;


# direct methods
.method public constructor <init>()V
    .locals 1

    .prologue
    .line 38
    invoke-direct {p0}, Landroid/app/ListActivity;-><init>()V

    .line 63
    const-string v0, ""

    iput-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->lastVehicleID:Ljava/lang/String;

    .line 127
    new-instance v0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications$1;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/Tab_SubTabNotifications$1;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabNotifications;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->handler:Landroid/os/Handler;

    .line 38
    return-void
.end method

.method static synthetic access$0(Lcom/openvehicles/OVMS/Tab_SubTabNotifications;)Ljava/lang/String;
    .locals 1
    .parameter

    .prologue
    .line 63
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->lastVehicleID:Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$1(Lcom/openvehicles/OVMS/Tab_SubTabNotifications;)Lcom/openvehicles/OVMS/CarData;
    .locals 1
    .parameter

    .prologue
    .line 61
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->data:Lcom/openvehicles/OVMS/CarData;

    return-object v0
.end method

.method static synthetic access$2(Lcom/openvehicles/OVMS/Tab_SubTabNotifications;)Lcom/openvehicles/OVMS/OVMSNotifications;
    .locals 1
    .parameter

    .prologue
    .line 58
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->notifications:Lcom/openvehicles/OVMS/OVMSNotifications;

    return-object v0
.end method

.method static synthetic access$3(Lcom/openvehicles/OVMS/Tab_SubTabNotifications;)[Lcom/openvehicles/OVMS/NotificationData;
    .locals 1
    .parameter

    .prologue
    .line 57
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->cachedData:[Lcom/openvehicles/OVMS/NotificationData;

    return-object v0
.end method

.method static synthetic access$4(Lcom/openvehicles/OVMS/Tab_SubTabNotifications;Ljava/lang/String;)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 63
    iput-object p1, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->lastVehicleID:Ljava/lang/String;

    return-void
.end method

.method static synthetic access$5(Lcom/openvehicles/OVMS/Tab_SubTabNotifications;[Lcom/openvehicles/OVMS/NotificationData;)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 57
    iput-object p1, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->cachedData:[Lcom/openvehicles/OVMS/NotificationData;

    return-void
.end method

.method static synthetic access$6(Lcom/openvehicles/OVMS/Tab_SubTabNotifications;)Landroid/content/Context;
    .locals 1
    .parameter

    .prologue
    .line 59
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->mContext:Landroid/content/Context;

    return-object v0
.end method

.method static synthetic access$7(Lcom/openvehicles/OVMS/Tab_SubTabNotifications;Lcom/openvehicles/OVMS/Tab_SubTabNotifications$ItemsAdapter;)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 56
    iput-object p1, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->adapter:Lcom/openvehicles/OVMS/Tab_SubTabNotifications$ItemsAdapter;

    return-void
.end method

.method static synthetic access$8(Lcom/openvehicles/OVMS/Tab_SubTabNotifications;)Lcom/openvehicles/OVMS/Tab_SubTabNotifications$ItemsAdapter;
    .locals 1
    .parameter

    .prologue
    .line 56
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->adapter:Lcom/openvehicles/OVMS/Tab_SubTabNotifications$ItemsAdapter;

    return-object v0
.end method


# virtual methods
.method public Refresh(Lcom/openvehicles/OVMS/CarData;Z)V
    .locals 2
    .parameter "carData"
    .parameter "isLoggedIn"

    .prologue
    .line 153
    iput-object p1, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->data:Lcom/openvehicles/OVMS/CarData;

    .line 156
    new-instance v0, Lcom/openvehicles/OVMS/OVMSNotifications;

    iget-object v1, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v1, v1, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    invoke-direct {v0, p0, v1}, Lcom/openvehicles/OVMS/OVMSNotifications;-><init>(Landroid/content/Context;Ljava/lang/String;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->notifications:Lcom/openvehicles/OVMS/OVMSNotifications;

    .line 157
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->handler:Landroid/os/Handler;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    .line 159
    return-void
.end method

.method public onCreate(Landroid/os/Bundle;)V
    .locals 2
    .parameter "savedInstanceState"

    .prologue
    .line 43
    invoke-super {p0, p1}, Landroid/app/ListActivity;->onCreate(Landroid/os/Bundle;)V

    .line 44
    const v0, 0x7f030008

    invoke-virtual {p0, v0}, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->setContentView(I)V

    .line 46
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->getParent()Landroid/app/Activity;

    move-result-object v0

    invoke-virtual {v0}, Landroid/app/Activity;->getParent()Landroid/app/Activity;

    move-result-object v0

    check-cast v0, Lcom/openvehicles/OVMS/OVMSActivity;

    iput-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->mOVMSActivity:Lcom/openvehicles/OVMS/OVMSActivity;

    .line 47
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->mOVMSActivity:Lcom/openvehicles/OVMS/OVMSActivity;

    if-nez v0, :cond_0

    .line 48
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->getParent()Landroid/app/Activity;

    move-result-object v0

    check-cast v0, Lcom/openvehicles/OVMS/OVMSActivity;

    iput-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->mOVMSActivity:Lcom/openvehicles/OVMS/OVMSActivity;

    .line 49
    :cond_0
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->mOVMSActivity:Lcom/openvehicles/OVMS/OVMSActivity;

    if-nez v0, :cond_1

    .line 50
    const-string v0, "Unknown Layout Error"

    const/4 v1, 0x1

    invoke-static {p0, v0, v1}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v0

    invoke-virtual {v0}, Landroid/widget/Toast;->show()V

    .line 52
    :cond_1
    new-instance v0, Lcom/openvehicles/OVMS/OVMSNotifications;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/OVMSNotifications;-><init>(Landroid/content/Context;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->notifications:Lcom/openvehicles/OVMS/OVMSNotifications;

    .line 53
    iput-object p0, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->mContext:Landroid/content/Context;

    .line 54
    return-void
.end method

.method protected onListItemClick(Landroid/widget/ListView;Landroid/view/View;IJ)V
    .locals 4
    .parameter "l"
    .parameter "v"
    .parameter "position"
    .parameter "id"

    .prologue
    .line 111
    const-string v1, "OVMS"

    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "Displaying notification: #"

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, p3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 113
    new-instance v0, Landroid/app/AlertDialog$Builder;

    invoke-virtual {p0}, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->getParent()Landroid/app/Activity;

    move-result-object v1

    invoke-direct {v0, v1}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 114
    .local v0, builder:Landroid/app/AlertDialog$Builder;
    iget-object v1, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->cachedData:[Lcom/openvehicles/OVMS/NotificationData;

    aget-object v1, v1, p3

    iget-object v1, v1, Lcom/openvehicles/OVMS/NotificationData;->Message:Ljava/lang/String;

    invoke-virtual {v0, v1}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v1

    .line 115
    iget-object v2, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->cachedData:[Lcom/openvehicles/OVMS/NotificationData;

    aget-object v2, v2, p3

    iget-object v2, v2, Lcom/openvehicles/OVMS/NotificationData;->Title:Ljava/lang/String;

    invoke-virtual {v1, v2}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v1

    .line 116
    const/4 v2, 0x0

    invoke-virtual {v1, v2}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    move-result-object v1

    .line 117
    const-string v2, "Close"

    .line 118
    new-instance v3, Lcom/openvehicles/OVMS/Tab_SubTabNotifications$2;

    invoke-direct {v3, p0}, Lcom/openvehicles/OVMS/Tab_SubTabNotifications$2;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabNotifications;)V

    .line 117
    invoke-virtual {v1, v2, v3}, Landroid/app/AlertDialog$Builder;->setPositiveButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 124
    invoke-virtual {v0}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v1

    invoke-virtual {v1}, Landroid/app/AlertDialog;->show()V

    .line 125
    return-void
.end method
