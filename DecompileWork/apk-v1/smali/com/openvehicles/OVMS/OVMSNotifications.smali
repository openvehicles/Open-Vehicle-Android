.class public Lcom/openvehicles/OVMS/OVMSNotifications;
.super Ljava/lang/Object;
.source "OVMSNotifications.java"


# instance fields
.field public Notifications:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList",
            "<",
            "Lcom/openvehicles/OVMS/NotificationData;",
            ">;"
        }
    .end annotation
.end field

.field private mContext:Landroid/content/Context;

.field private final settingsFileName:Ljava/lang/String;


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .locals 8
    .parameter "context"

    .prologue
    .line 21
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 15
    const-string v3, "OVMSSavedNotifications.obj"

    iput-object v3, p0, Lcom/openvehicles/OVMS/OVMSNotifications;->settingsFileName:Ljava/lang/String;

    .line 22
    iput-object p1, p0, Lcom/openvehicles/OVMS/OVMSNotifications;->mContext:Landroid/content/Context;

    .line 24
    :try_start_0
    const-string v3, "OVMS"

    const-string v4, "Loading saved notifications list from internal storage file: OVMSSavedNotifications.obj"

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 26
    const-string v3, "OVMSSavedNotifications.obj"

    invoke-virtual {p1, v3}, Landroid/content/Context;->openFileInput(Ljava/lang/String;)Ljava/io/FileInputStream;

    move-result-object v1

    .line 27
    .local v1, fis:Ljava/io/FileInputStream;
    new-instance v2, Ljava/io/ObjectInputStream;

    invoke-direct {v2, v1}, Ljava/io/ObjectInputStream;-><init>(Ljava/io/InputStream;)V

    .line 28
    .local v2, is:Ljava/io/ObjectInputStream;
    invoke-virtual {v2}, Ljava/io/ObjectInputStream;->readObject()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/util/ArrayList;

    iput-object v3, p0, Lcom/openvehicles/OVMS/OVMSNotifications;->Notifications:Ljava/util/ArrayList;

    .line 29
    invoke-virtual {v2}, Ljava/io/ObjectInputStream;->close()V

    .line 30
    const-string v3, "OVMS"

    const-string v4, "Loaded %s saved notifications."

    const/4 v5, 0x1

    new-array v5, v5, [Ljava/lang/Object;

    const/4 v6, 0x0

    iget-object v7, p0, Lcom/openvehicles/OVMS/OVMSNotifications;->Notifications:Ljava/util/ArrayList;

    invoke-virtual {v7}, Ljava/util/ArrayList;->size()I

    move-result v7

    invoke-static {v7}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    aput-object v7, v5, v6

    invoke-static {v4, v5}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 42
    .end local v1           #fis:Ljava/io/FileInputStream;
    .end local v2           #is:Ljava/io/ObjectInputStream;
    :goto_0
    return-void

    .line 31
    :catch_0
    move-exception v0

    .line 33
    .local v0, e:Ljava/lang/Exception;
    const-string v3, "ERR"

    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 35
    const-string v3, "OVMS"

    const-string v4, "Initializing with save notifications list."

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 36
    new-instance v3, Ljava/util/ArrayList;

    invoke-direct {v3}, Ljava/util/ArrayList;-><init>()V

    iput-object v3, p0, Lcom/openvehicles/OVMS/OVMSNotifications;->Notifications:Ljava/util/ArrayList;

    .line 39
    const-string v3, "Push Notifications"

    const-string v4, "Push notifications received for your registered vehicles are archived here."

    invoke-virtual {p0, v3, v4}, Lcom/openvehicles/OVMS/OVMSNotifications;->AddNotification(Ljava/lang/String;Ljava/lang/String;)V

    .line 40
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSNotifications;->Save()V

    goto :goto_0
.end method


# virtual methods
.method public AddNotification(Lcom/openvehicles/OVMS/NotificationData;)V
    .locals 1
    .parameter "notification"

    .prologue
    .line 46
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSNotifications;->Notifications:Ljava/util/ArrayList;

    invoke-virtual {v0, p1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 47
    return-void
.end method

.method public AddNotification(Ljava/lang/String;Ljava/lang/String;)V
    .locals 3
    .parameter "title"
    .parameter "message"

    .prologue
    .line 51
    new-instance v0, Ljava/util/Date;

    invoke-direct {v0}, Ljava/util/Date;-><init>()V

    .line 52
    .local v0, timestamp:Ljava/util/Date;
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSNotifications;->Notifications:Ljava/util/ArrayList;

    new-instance v2, Lcom/openvehicles/OVMS/NotificationData;

    invoke-direct {v2, v0, p1, p2}, Lcom/openvehicles/OVMS/NotificationData;-><init>(Ljava/util/Date;Ljava/lang/String;Ljava/lang/String;)V

    invoke-virtual {v1, v2}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 53
    return-void
.end method

.method public Save()V
    .locals 8

    .prologue
    .line 58
    :try_start_0
    const-string v3, "OVMS"

    const-string v4, "Saving notifications list to interal storage..."

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 60
    iget-object v3, p0, Lcom/openvehicles/OVMS/OVMSNotifications;->mContext:Landroid/content/Context;

    const-string v4, "OVMSSavedNotifications.obj"

    const/4 v5, 0x0

    invoke-virtual {v3, v4, v5}, Landroid/content/Context;->openFileOutput(Ljava/lang/String;I)Ljava/io/FileOutputStream;

    move-result-object v1

    .line 62
    .local v1, fos:Ljava/io/FileOutputStream;
    new-instance v2, Ljava/io/ObjectOutputStream;

    invoke-direct {v2, v1}, Ljava/io/ObjectOutputStream;-><init>(Ljava/io/OutputStream;)V

    .line 63
    .local v2, os:Ljava/io/ObjectOutputStream;
    iget-object v3, p0, Lcom/openvehicles/OVMS/OVMSNotifications;->Notifications:Ljava/util/ArrayList;

    invoke-virtual {v2, v3}, Ljava/io/ObjectOutputStream;->writeObject(Ljava/lang/Object;)V

    .line 64
    invoke-virtual {v2}, Ljava/io/ObjectOutputStream;->close()V

    .line 65
    const-string v3, "OVMS"

    const-string v4, "Saved %s notifications."

    const/4 v5, 0x1

    new-array v5, v5, [Ljava/lang/Object;

    const/4 v6, 0x0

    iget-object v7, p0, Lcom/openvehicles/OVMS/OVMSNotifications;->Notifications:Ljava/util/ArrayList;

    invoke-virtual {v7}, Ljava/util/ArrayList;->size()I

    move-result v7

    invoke-static {v7}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    aput-object v7, v5, v6

    invoke-static {v4, v5}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 70
    .end local v1           #fos:Ljava/io/FileOutputStream;
    .end local v2           #os:Ljava/io/ObjectOutputStream;
    :goto_0
    return-void

    .line 66
    :catch_0
    move-exception v0

    .line 67
    .local v0, e:Ljava/lang/Exception;
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    .line 68
    const-string v3, "ERR"

    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0
.end method
