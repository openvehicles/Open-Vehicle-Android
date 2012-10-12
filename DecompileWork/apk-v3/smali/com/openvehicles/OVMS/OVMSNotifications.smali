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
    .locals 1
    .parameter "context"

    .prologue
    .line 20
    const/4 v0, 0x0

    invoke-direct {p0, p1, v0}, Lcom/openvehicles/OVMS/OVMSNotifications;-><init>(Landroid/content/Context;Ljava/lang/String;)V

    .line 21
    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Ljava/lang/String;)V
    .locals 9
    .parameter "context"
    .parameter "VehicleIDFilter"

    .prologue
    .line 24
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 14
    const-string v4, "OVMSSavedNotifications.obj"

    iput-object v4, p0, Lcom/openvehicles/OVMS/OVMSNotifications;->settingsFileName:Ljava/lang/String;

    .line 25
    iput-object p1, p0, Lcom/openvehicles/OVMS/OVMSNotifications;->mContext:Landroid/content/Context;

    .line 27
    :try_start_0
    const-string v4, "OVMS"

    const-string v5, "Loading saved notifications list from internal storage file: OVMSSavedNotifications.obj"

    invoke-static {v4, v5}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 29
    const-string v4, "OVMSSavedNotifications.obj"

    invoke-virtual {p1, v4}, Landroid/content/Context;->openFileInput(Ljava/lang/String;)Ljava/io/FileInputStream;

    move-result-object v1

    .line 30
    .local v1, fis:Ljava/io/FileInputStream;
    new-instance v3, Ljava/io/ObjectInputStream;

    invoke-direct {v3, v1}, Ljava/io/ObjectInputStream;-><init>(Ljava/io/InputStream;)V

    .line 31
    .local v3, is:Ljava/io/ObjectInputStream;
    invoke-virtual {v3}, Ljava/io/ObjectInputStream;->readObject()Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Ljava/util/ArrayList;

    iput-object v4, p0, Lcom/openvehicles/OVMS/OVMSNotifications;->Notifications:Ljava/util/ArrayList;

    .line 32
    invoke-virtual {v3}, Ljava/io/ObjectInputStream;->close()V

    .line 34
    if-eqz p2, :cond_0

    .line 35
    iget-object v4, p0, Lcom/openvehicles/OVMS/OVMSNotifications;->Notifications:Ljava/util/ArrayList;

    invoke-virtual {v4}, Ljava/util/ArrayList;->size()I

    move-result v4

    add-int/lit8 v2, v4, -0x1

    .local v2, index:I
    :goto_0
    if-gez v2, :cond_1

    .line 39
    .end local v2           #index:I
    :cond_0
    const-string v4, "OVMS"

    const-string v5, "Loaded %s saved notifications."

    const/4 v6, 0x1

    new-array v6, v6, [Ljava/lang/Object;

    const/4 v7, 0x0

    iget-object v8, p0, Lcom/openvehicles/OVMS/OVMSNotifications;->Notifications:Ljava/util/ArrayList;

    invoke-virtual {v8}, Ljava/util/ArrayList;->size()I

    move-result v8

    invoke-static {v8}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v8

    aput-object v8, v6, v7

    invoke-static {v5, v6}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 51
    .end local v1           #fis:Ljava/io/FileInputStream;
    .end local v3           #is:Ljava/io/ObjectInputStream;
    :goto_1
    return-void

    .line 36
    .restart local v1       #fis:Ljava/io/FileInputStream;
    .restart local v2       #index:I
    .restart local v3       #is:Ljava/io/ObjectInputStream;
    :cond_1
    iget-object v4, p0, Lcom/openvehicles/OVMS/OVMSNotifications;->Notifications:Ljava/util/ArrayList;

    invoke-virtual {v4, v2}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Lcom/openvehicles/OVMS/NotificationData;

    iget-object v4, v4, Lcom/openvehicles/OVMS/NotificationData;->Title:Ljava/lang/String;

    invoke-virtual {v4, p2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-nez v4, :cond_2

    .line 37
    iget-object v4, p0, Lcom/openvehicles/OVMS/OVMSNotifications;->Notifications:Ljava/util/ArrayList;

    invoke-virtual {v4, v2}, Ljava/util/ArrayList;->remove(I)Ljava/lang/Object;
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 35
    :cond_2
    add-int/lit8 v2, v2, -0x1

    goto :goto_0

    .line 40
    .end local v1           #fis:Ljava/io/FileInputStream;
    .end local v2           #index:I
    .end local v3           #is:Ljava/io/ObjectInputStream;
    :catch_0
    move-exception v0

    .line 42
    .local v0, e:Ljava/lang/Exception;
    const-string v4, "ERR"

    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 44
    const-string v4, "OVMS"

    const-string v5, "Initializing with save notifications list."

    invoke-static {v4, v5}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 45
    new-instance v4, Ljava/util/ArrayList;

    invoke-direct {v4}, Ljava/util/ArrayList;-><init>()V

    iput-object v4, p0, Lcom/openvehicles/OVMS/OVMSNotifications;->Notifications:Ljava/util/ArrayList;

    .line 48
    const-string v4, "Push Notifications"

    const-string v5, "Push notifications received for your registered vehicles are archived here."

    invoke-virtual {p0, v4, v5}, Lcom/openvehicles/OVMS/OVMSNotifications;->AddNotification(Ljava/lang/String;Ljava/lang/String;)V

    .line 49
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSNotifications;->Save()V

    goto :goto_1
.end method


# virtual methods
.method public AddNotification(Lcom/openvehicles/OVMS/NotificationData;)V
    .locals 1
    .parameter "notification"

    .prologue
    .line 55
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSNotifications;->Notifications:Ljava/util/ArrayList;

    invoke-virtual {v0, p1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 56
    return-void
.end method

.method public AddNotification(Ljava/lang/String;Ljava/lang/String;)V
    .locals 3
    .parameter "title"
    .parameter "message"

    .prologue
    .line 65
    new-instance v0, Ljava/util/Date;

    invoke-direct {v0}, Ljava/util/Date;-><init>()V

    .line 66
    .local v0, timestamp:Ljava/util/Date;
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSNotifications;->Notifications:Ljava/util/ArrayList;

    new-instance v2, Lcom/openvehicles/OVMS/NotificationData;

    invoke-direct {v2, v0, p1, p2}, Lcom/openvehicles/OVMS/NotificationData;-><init>(Ljava/util/Date;Ljava/lang/String;Ljava/lang/String;)V

    invoke-virtual {v1, v2}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 67
    return-void
.end method

.method public Clear()V
    .locals 1

    .prologue
    .line 60
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/openvehicles/OVMS/OVMSNotifications;->Notifications:Ljava/util/ArrayList;

    .line 61
    return-void
.end method

.method public Save()V
    .locals 8

    .prologue
    .line 72
    :try_start_0
    const-string v3, "OVMS"

    const-string v4, "Saving notifications list to interal storage..."

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 74
    iget-object v3, p0, Lcom/openvehicles/OVMS/OVMSNotifications;->mContext:Landroid/content/Context;

    const-string v4, "OVMSSavedNotifications.obj"

    .line 75
    const/4 v5, 0x0

    .line 74
    invoke-virtual {v3, v4, v5}, Landroid/content/Context;->openFileOutput(Ljava/lang/String;I)Ljava/io/FileOutputStream;

    move-result-object v1

    .line 76
    .local v1, fos:Ljava/io/FileOutputStream;
    new-instance v2, Ljava/io/ObjectOutputStream;

    invoke-direct {v2, v1}, Ljava/io/ObjectOutputStream;-><init>(Ljava/io/OutputStream;)V

    .line 77
    .local v2, os:Ljava/io/ObjectOutputStream;
    iget-object v3, p0, Lcom/openvehicles/OVMS/OVMSNotifications;->Notifications:Ljava/util/ArrayList;

    invoke-virtual {v2, v3}, Ljava/io/ObjectOutputStream;->writeObject(Ljava/lang/Object;)V

    .line 78
    invoke-virtual {v2}, Ljava/io/ObjectOutputStream;->close()V

    .line 79
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

    .line 84
    .end local v1           #fos:Ljava/io/FileOutputStream;
    .end local v2           #os:Ljava/io/ObjectOutputStream;
    :goto_0
    return-void

    .line 80
    :catch_0
    move-exception v0

    .line 81
    .local v0, e:Ljava/lang/Exception;
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    .line 82
    const-string v3, "ERR"

    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0
.end method
