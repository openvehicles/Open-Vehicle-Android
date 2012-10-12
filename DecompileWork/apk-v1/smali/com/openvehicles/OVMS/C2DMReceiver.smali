.class public Lcom/openvehicles/OVMS/C2DMReceiver;
.super Landroid/content/BroadcastReceiver;
.source "C2DMReceiver.java"


# static fields
.field private static KEY:Ljava/lang/String;

.field private static REGISTRATION_KEY:Ljava/lang/String;


# instance fields
.field private context:Landroid/content/Context;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .prologue
    .line 20
    const-string v0, "C2DM"

    sput-object v0, Lcom/openvehicles/OVMS/C2DMReceiver;->KEY:Ljava/lang/String;

    .line 21
    const-string v0, "RegID"

    sput-object v0, Lcom/openvehicles/OVMS/C2DMReceiver;->REGISTRATION_KEY:Ljava/lang/String;

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .prologue
    .line 19
    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    return-void
.end method

.method private handleMessage(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 26
    .parameter "context"
    .parameter "intent"

    .prologue
    .line 76
    const-string v22, "title"

    move-object/from16 v0, p2

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Landroid/content/Intent;->hasExtra(Ljava/lang/String;)Z

    move-result v22

    if-eqz v22, :cond_0

    const-string v22, "message"

    move-object/from16 v0, p2

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Landroid/content/Intent;->hasExtra(Ljava/lang/String;)Z

    move-result v22

    if-nez v22, :cond_1

    .line 78
    :cond_0
    const-string v22, "ERR"

    const-string v23, "An invalid C2DM message was received."

    invoke-static/range {v22 .. v23}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 135
    :goto_0
    return-void

    .line 83
    :cond_1
    const/4 v3, 0x0

    .line 85
    .local v3, allSavedCars:Ljava/util/ArrayList;,"Ljava/util/ArrayList<Lcom/openvehicles/OVMS/CarData;>;"
    :try_start_0
    const-string v18, "OVMSSavedCars.obj"

    .line 86
    .local v18, settingsFileName:Ljava/lang/String;
    const-string v22, "OVMS"

    const-string v23, "Loading saved cars from internal storage file: OVMSSavedCars.obj"

    invoke-static/range {v22 .. v23}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 88
    const-string v22, "OVMSSavedCars.obj"

    move-object/from16 v0, p1

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Landroid/content/Context;->openFileInput(Ljava/lang/String;)Ljava/io/FileInputStream;

    move-result-object v8

    .line 89
    .local v8, fis:Ljava/io/FileInputStream;
    new-instance v11, Ljava/io/ObjectInputStream;

    invoke-direct {v11, v8}, Ljava/io/ObjectInputStream;-><init>(Ljava/io/InputStream;)V

    .line 90
    .local v11, is:Ljava/io/ObjectInputStream;
    invoke-virtual {v11}, Ljava/io/ObjectInputStream;->readObject()Ljava/lang/Object;

    move-result-object v22

    move-object/from16 v0, v22

    check-cast v0, Ljava/util/ArrayList;

    move-object v3, v0

    .line 91
    invoke-virtual {v11}, Ljava/io/ObjectInputStream;->close()V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 97
    .end local v8           #fis:Ljava/io/FileInputStream;
    .end local v11           #is:Ljava/io/ObjectInputStream;
    .end local v18           #settingsFileName:Ljava/lang/String;
    :goto_1
    const-string v16, "notification"

    .line 98
    .local v16, ns:Ljava/lang/String;
    move-object/from16 v0, p1

    move-object/from16 v1, v16

    invoke-virtual {v0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v13

    check-cast v13, Landroid/app/NotificationManager;

    .line 100
    .local v13, mNotificationManager:Landroid/app/NotificationManager;
    const-string v22, "title"

    move-object/from16 v0, p2

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Landroid/content/Intent;->getStringExtra(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v6

    .line 101
    .local v6, contentTitle:Ljava/lang/CharSequence;
    const-string v22, "message"

    move-object/from16 v0, p2

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Landroid/content/Intent;->getStringExtra(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v5

    .line 102
    .local v5, contentText:Ljava/lang/CharSequence;
    move-object/from16 v19, v5

    .line 103
    .local v19, tickerText:Ljava/lang/CharSequence;
    const v10, 0x108002e

    .line 106
    .local v10, icon:I
    new-instance v17, Lcom/openvehicles/OVMS/OVMSNotifications;

    move-object/from16 v0, v17

    move-object/from16 v1, p1

    invoke-direct {v0, v1}, Lcom/openvehicles/OVMS/OVMSNotifications;-><init>(Landroid/content/Context;)V

    .line 107
    .local v17, savedList:Lcom/openvehicles/OVMS/OVMSNotifications;
    invoke-virtual {v6}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v22

    invoke-virtual {v5}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v23

    move-object/from16 v0, v17

    move-object/from16 v1, v22

    move-object/from16 v2, v23

    invoke-virtual {v0, v1, v2}, Lcom/openvehicles/OVMS/OVMSNotifications;->AddNotification(Ljava/lang/String;Ljava/lang/String;)V

    .line 108
    invoke-virtual/range {v17 .. v17}, Lcom/openvehicles/OVMS/OVMSNotifications;->Save()V

    .line 111
    if-eqz v3, :cond_3

    .line 113
    invoke-virtual {v3}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v9

    .local v9, i$:Ljava/util/Iterator;
    :cond_2
    invoke-interface {v9}, Ljava/util/Iterator;->hasNext()Z

    move-result v22

    if-eqz v22, :cond_3

    invoke-interface {v9}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Lcom/openvehicles/OVMS/CarData;

    .line 116
    .local v4, car:Lcom/openvehicles/OVMS/CarData;
    iget-object v0, v4, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    move-object/from16 v22, v0

    move-object/from16 v0, v22

    invoke-virtual {v0, v6}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v22

    if-eqz v22, :cond_2

    .line 118
    invoke-virtual/range {p1 .. p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v22

    new-instance v23, Ljava/lang/StringBuilder;

    invoke-direct/range {v23 .. v23}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v0, v4, Lcom/openvehicles/OVMS/CarData;->VehicleImageDrawable:Ljava/lang/String;

    move-object/from16 v24, v0

    invoke-virtual/range {v23 .. v24}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v23

    const-string v24, "32x32"

    invoke-virtual/range {v23 .. v24}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v23

    invoke-virtual/range {v23 .. v23}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v23

    const-string v24, "drawable"

    const-string v25, "com.openvehicles.OVMS"

    invoke-virtual/range {v22 .. v25}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v10

    .line 124
    .end local v4           #car:Lcom/openvehicles/OVMS/CarData;
    .end local v9           #i$:Ljava/util/Iterator;
    :cond_3
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v20

    .line 125
    .local v20, when:J
    new-instance v14, Landroid/app/Notification;

    move-object/from16 v0, v19

    move-wide/from16 v1, v20

    invoke-direct {v14, v10, v0, v1, v2}, Landroid/app/Notification;-><init>(ILjava/lang/CharSequence;J)V

    .line 126
    .local v14, notification:Landroid/app/Notification;
    const/16 v22, 0x10

    move/from16 v0, v22

    iput v0, v14, Landroid/app/Notification;->flags:I

    .line 127
    const/16 v22, 0x7

    move/from16 v0, v22

    iput v0, v14, Landroid/app/Notification;->defaults:I

    .line 129
    new-instance v15, Landroid/content/Intent;

    const-class v22, Lcom/openvehicles/OVMS/OVMSActivity;

    move-object/from16 v0, p1

    move-object/from16 v1, v22

    invoke-direct {v15, v0, v1}, Landroid/content/Intent;-><init>(Landroid/content/Context;Ljava/lang/Class;)V

    .line 130
    .local v15, notificationIntent:Landroid/content/Intent;
    const-string v22, "SetTab"

    const-string v23, "tabNotifications"

    move-object/from16 v0, v22

    move-object/from16 v1, v23

    invoke-virtual {v15, v0, v1}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    .line 131
    const/16 v22, 0x0

    const/16 v23, 0x0

    move-object/from16 v0, p1

    move/from16 v1, v22

    move/from16 v2, v23

    invoke-static {v0, v1, v15, v2}, Landroid/app/PendingIntent;->getActivity(Landroid/content/Context;ILandroid/content/Intent;I)Landroid/app/PendingIntent;

    move-result-object v12

    .line 132
    .local v12, launchOVMSIntent:Landroid/app/PendingIntent;
    move-object/from16 v0, p1

    invoke-virtual {v14, v0, v6, v5, v12}, Landroid/app/Notification;->setLatestEventInfo(Landroid/content/Context;Ljava/lang/CharSequence;Ljava/lang/CharSequence;Landroid/app/PendingIntent;)V

    .line 134
    const/16 v22, 0x1

    move/from16 v0, v22

    invoke-virtual {v13, v0, v14}, Landroid/app/NotificationManager;->notify(ILandroid/app/Notification;)V

    goto/16 :goto_0

    .line 92
    .end local v5           #contentText:Ljava/lang/CharSequence;
    .end local v6           #contentTitle:Ljava/lang/CharSequence;
    .end local v10           #icon:I
    .end local v12           #launchOVMSIntent:Landroid/app/PendingIntent;
    .end local v13           #mNotificationManager:Landroid/app/NotificationManager;
    .end local v14           #notification:Landroid/app/Notification;
    .end local v15           #notificationIntent:Landroid/content/Intent;
    .end local v16           #ns:Ljava/lang/String;
    .end local v17           #savedList:Lcom/openvehicles/OVMS/OVMSNotifications;
    .end local v19           #tickerText:Ljava/lang/CharSequence;
    .end local v20           #when:J
    :catch_0
    move-exception v7

    .line 93
    .local v7, e:Ljava/lang/Exception;
    invoke-virtual {v7}, Ljava/lang/Exception;->printStackTrace()V

    goto/16 :goto_1
.end method

.method private handleRegistration(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 5
    .parameter "context"
    .parameter "intent"

    .prologue
    .line 35
    const-string v3, "registration_id"

    invoke-virtual {p2, v3}, Landroid/content/Intent;->getStringExtra(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    .line 36
    .local v2, registration:Ljava/lang/String;
    const-string v3, "error"

    invoke-virtual {p2, v3}, Landroid/content/Intent;->getStringExtra(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    if-eqz v3, :cond_6

    .line 38
    const-string v3, "c2dm"

    const-string v4, "registration failed"

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 39
    const-string v3, "error"

    invoke-virtual {p2, v3}, Landroid/content/Intent;->getStringExtra(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 40
    .local v1, error:Ljava/lang/String;
    const-string v3, "SERVICE_NOT_AVAILABLE"

    if-ne v1, v3, :cond_1

    .line 41
    const-string v3, "c2dm"

    const-string v4, "SERVICE_NOT_AVAILABLE"

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 71
    .end local v1           #error:Ljava/lang/String;
    :cond_0
    :goto_0
    return-void

    .line 42
    .restart local v1       #error:Ljava/lang/String;
    :cond_1
    const-string v3, "ACCOUNT_MISSING"

    if-ne v1, v3, :cond_2

    .line 43
    const-string v3, "c2dm"

    const-string v4, "ACCOUNT_MISSING"

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0

    .line 44
    :cond_2
    const-string v3, "AUTHENTICATION_FAILED"

    if-ne v1, v3, :cond_3

    .line 45
    const-string v3, "c2dm"

    const-string v4, "AUTHENTICATION_FAILED"

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0

    .line 46
    :cond_3
    const-string v3, "TOO_MANY_REGISTRATIONS"

    if-ne v1, v3, :cond_4

    .line 47
    const-string v3, "c2dm"

    const-string v4, "TOO_MANY_REGISTRATIONS"

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0

    .line 48
    :cond_4
    const-string v3, "INVALID_SENDER"

    if-ne v1, v3, :cond_5

    .line 49
    const-string v3, "c2dm"

    const-string v4, "INVALID_SENDER"

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0

    .line 50
    :cond_5
    const-string v3, "PHONE_REGISTRATION_ERROR"

    if-ne v1, v3, :cond_0

    .line 51
    const-string v3, "c2dm"

    const-string v4, "PHONE_REGISTRATION_ERROR"

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0

    .line 53
    .end local v1           #error:Ljava/lang/String;
    :cond_6
    const-string v3, "unregistered"

    invoke-virtual {p2, v3}, Landroid/content/Intent;->getStringExtra(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    if-eqz v3, :cond_7

    .line 55
    const-string v3, "c2dm"

    const-string v4, "unregistered"

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0

    .line 57
    :cond_7
    if-eqz v2, :cond_0

    .line 58
    const-string v3, "c2dm"

    invoke-static {v3, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 59
    sget-object v3, Lcom/openvehicles/OVMS/C2DMReceiver;->KEY:Ljava/lang/String;

    const/4 v4, 0x0

    invoke-virtual {p1, v3, v4}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v3

    invoke-interface {v3}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    .line 60
    .local v0, editor:Landroid/content/SharedPreferences$Editor;
    sget-object v3, Lcom/openvehicles/OVMS/C2DMReceiver;->REGISTRATION_KEY:Ljava/lang/String;

    invoke-interface {v0, v3, v2}, Landroid/content/SharedPreferences$Editor;->putString(Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    .line 61
    invoke-interface {v0}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 63
    const-string v3, "Push Notification Registered"

    const/16 v4, 0x7d0

    invoke-static {p1, v3, v4}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    goto :goto_0
.end method


# virtual methods
.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 2
    .parameter "context"
    .parameter "intent"

    .prologue
    .line 26
    iput-object p1, p0, Lcom/openvehicles/OVMS/C2DMReceiver;->context:Landroid/content/Context;

    .line 27
    invoke-virtual {p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object v0

    const-string v1, "com.google.android.c2dm.intent.REGISTRATION"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_1

    .line 28
    invoke-direct {p0, p1, p2}, Lcom/openvehicles/OVMS/C2DMReceiver;->handleRegistration(Landroid/content/Context;Landroid/content/Intent;)V

    .line 32
    :cond_0
    :goto_0
    return-void

    .line 29
    :cond_1
    invoke-virtual {p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object v0

    const-string v1, "com.google.android.c2dm.intent.RECEIVE"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    .line 30
    invoke-direct {p0, p1, p2}, Lcom/openvehicles/OVMS/C2DMReceiver;->handleMessage(Landroid/content/Context;Landroid/content/Intent;)V

    goto :goto_0
.end method
