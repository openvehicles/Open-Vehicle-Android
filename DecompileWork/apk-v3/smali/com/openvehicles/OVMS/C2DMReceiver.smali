.class public Lcom/openvehicles/OVMS/C2DMReceiver;
.super Landroid/content/BroadcastReceiver;
.source "C2DMReceiver.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    .prologue
    .line 17
    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    return-void
.end method

.method private handleMessage(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 25
    .parameter "context"
    .parameter "intent"

    .prologue
    .line 72
    const-string v21, "title"

    move-object/from16 v0, p2

    move-object/from16 v1, v21

    invoke-virtual {v0, v1}, Landroid/content/Intent;->hasExtra(Ljava/lang/String;)Z

    move-result v21

    if-eqz v21, :cond_0

    const-string v21, "message"

    move-object/from16 v0, p2

    move-object/from16 v1, v21

    invoke-virtual {v0, v1}, Landroid/content/Intent;->hasExtra(Ljava/lang/String;)Z

    move-result v21

    if-nez v21, :cond_1

    .line 74
    :cond_0
    const-string v21, "ERR"

    const-string v22, "An invalid C2DM message was received."

    invoke-static/range {v21 .. v22}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 136
    :goto_0
    return-void

    .line 78
    :cond_1
    const-string v21, "C2DM"

    const-string v22, "C2DM Message Received"

    invoke-static/range {v21 .. v22}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 81
    const/4 v3, 0x0

    .line 83
    .local v3, allSavedCars:Ljava/util/ArrayList;,"Ljava/util/ArrayList<Lcom/openvehicles/OVMS/CarData;>;"
    :try_start_0
    const-string v17, "OVMSSavedCars.obj"

    .line 84
    .local v17, settingsFileName:Ljava/lang/String;
    const-string v21, "OVMS"

    const-string v22, "Loading saved cars from internal storage file: OVMSSavedCars.obj"

    invoke-static/range {v21 .. v22}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 86
    const-string v21, "OVMSSavedCars.obj"

    move-object/from16 v0, p1

    move-object/from16 v1, v21

    invoke-virtual {v0, v1}, Landroid/content/Context;->openFileInput(Ljava/lang/String;)Ljava/io/FileInputStream;

    move-result-object v8

    .line 87
    .local v8, fis:Ljava/io/FileInputStream;
    new-instance v10, Ljava/io/ObjectInputStream;

    invoke-direct {v10, v8}, Ljava/io/ObjectInputStream;-><init>(Ljava/io/InputStream;)V

    .line 88
    .local v10, is:Ljava/io/ObjectInputStream;
    invoke-virtual {v10}, Ljava/io/ObjectInputStream;->readObject()Ljava/lang/Object;

    move-result-object v21

    move-object/from16 v0, v21

    check-cast v0, Ljava/util/ArrayList;

    move-object v3, v0

    .line 89
    invoke-virtual {v10}, Ljava/io/ObjectInputStream;->close()V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 95
    .end local v8           #fis:Ljava/io/FileInputStream;
    .end local v10           #is:Ljava/io/ObjectInputStream;
    .end local v17           #settingsFileName:Ljava/lang/String;
    :goto_1
    const-string v15, "notification"

    .line 96
    .local v15, ns:Ljava/lang/String;
    move-object/from16 v0, p1

    invoke-virtual {v0, v15}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v12

    check-cast v12, Landroid/app/NotificationManager;

    .line 98
    .local v12, mNotificationManager:Landroid/app/NotificationManager;
    const-string v21, "title"

    move-object/from16 v0, p2

    move-object/from16 v1, v21

    invoke-virtual {v0, v1}, Landroid/content/Intent;->getStringExtra(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v6

    .line 99
    .local v6, contentTitle:Ljava/lang/CharSequence;
    const-string v21, "message"

    move-object/from16 v0, p2

    move-object/from16 v1, v21

    invoke-virtual {v0, v1}, Landroid/content/Intent;->getStringExtra(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v5

    .line 100
    .local v5, contentText:Ljava/lang/CharSequence;
    move-object/from16 v18, v5

    .line 101
    .local v18, tickerText:Ljava/lang/CharSequence;
    const v9, 0x1080077

    .line 104
    .local v9, icon:I
    new-instance v16, Lcom/openvehicles/OVMS/OVMSNotifications;

    move-object/from16 v0, v16

    move-object/from16 v1, p1

    invoke-direct {v0, v1}, Lcom/openvehicles/OVMS/OVMSNotifications;-><init>(Landroid/content/Context;)V

    .line 105
    .local v16, savedList:Lcom/openvehicles/OVMS/OVMSNotifications;
    invoke-interface {v6}, Ljava/lang/CharSequence;->toString()Ljava/lang/String;

    move-result-object v21

    invoke-interface {v5}, Ljava/lang/CharSequence;->toString()Ljava/lang/String;

    move-result-object v22

    move-object/from16 v0, v16

    move-object/from16 v1, v21

    move-object/from16 v2, v22

    invoke-virtual {v0, v1, v2}, Lcom/openvehicles/OVMS/OVMSNotifications;->AddNotification(Ljava/lang/String;Ljava/lang/String;)V

    .line 106
    invoke-virtual/range {v16 .. v16}, Lcom/openvehicles/OVMS/OVMSNotifications;->Save()V

    .line 109
    if-eqz v3, :cond_3

    .line 111
    invoke-virtual {v3}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v21

    :cond_2
    invoke-interface/range {v21 .. v21}, Ljava/util/Iterator;->hasNext()Z

    move-result v22

    if-nez v22, :cond_4

    .line 122
    :cond_3
    :goto_2
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v19

    .line 123
    .local v19, when:J
    new-instance v13, Landroid/app/Notification;

    move-object/from16 v0, v18

    move-wide/from16 v1, v19

    invoke-direct {v13, v9, v0, v1, v2}, Landroid/app/Notification;-><init>(ILjava/lang/CharSequence;J)V

    .line 124
    .local v13, notification:Landroid/app/Notification;
    const/16 v21, 0x10

    move/from16 v0, v21

    iput v0, v13, Landroid/app/Notification;->flags:I

    .line 125
    const/16 v21, 0x7

    move/from16 v0, v21

    iput v0, v13, Landroid/app/Notification;->defaults:I

    .line 127
    new-instance v14, Landroid/content/Intent;

    const-class v21, Lcom/openvehicles/OVMS/OVMSActivity;

    move-object/from16 v0, p1

    move-object/from16 v1, v21

    invoke-direct {v14, v0, v1}, Landroid/content/Intent;-><init>(Landroid/content/Context;Ljava/lang/Class;)V

    .line 128
    .local v14, notificationIntent:Landroid/content/Intent;
    const-string v21, "SetTab"

    const-string v22, "tabInfo"

    move-object/from16 v0, v21

    move-object/from16 v1, v22

    invoke-virtual {v14, v0, v1}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    .line 129
    const-string v21, "com.openvehicles.OVMS.NOTIFICATIONS_CLICK"

    move-object/from16 v0, v21

    invoke-virtual {v14, v0}, Landroid/content/Intent;->setAction(Ljava/lang/String;)Landroid/content/Intent;

    .line 130
    const-string v21, "VehicleID"

    move-object/from16 v0, v21

    invoke-virtual {v14, v0, v6}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/lang/CharSequence;)Landroid/content/Intent;

    .line 131
    const/high16 v21, 0x2400

    move/from16 v0, v21

    invoke-virtual {v14, v0}, Landroid/content/Intent;->setFlags(I)Landroid/content/Intent;

    .line 132
    const/16 v21, 0x0

    const/high16 v22, 0x800

    move-object/from16 v0, p1

    move/from16 v1, v21

    move/from16 v2, v22

    invoke-static {v0, v1, v14, v2}, Landroid/app/PendingIntent;->getActivity(Landroid/content/Context;ILandroid/content/Intent;I)Landroid/app/PendingIntent;

    move-result-object v11

    .line 133
    .local v11, launchOVMSIntent:Landroid/app/PendingIntent;
    move-object/from16 v0, p1

    invoke-virtual {v13, v0, v6, v5, v11}, Landroid/app/Notification;->setLatestEventInfo(Landroid/content/Context;Ljava/lang/CharSequence;Ljava/lang/CharSequence;Landroid/app/PendingIntent;)V

    .line 135
    const/16 v21, 0x1

    move/from16 v0, v21

    invoke-virtual {v12, v0, v13}, Landroid/app/NotificationManager;->notify(ILandroid/app/Notification;)V

    goto/16 :goto_0

    .line 90
    .end local v5           #contentText:Ljava/lang/CharSequence;
    .end local v6           #contentTitle:Ljava/lang/CharSequence;
    .end local v9           #icon:I
    .end local v11           #launchOVMSIntent:Landroid/app/PendingIntent;
    .end local v12           #mNotificationManager:Landroid/app/NotificationManager;
    .end local v13           #notification:Landroid/app/Notification;
    .end local v14           #notificationIntent:Landroid/content/Intent;
    .end local v15           #ns:Ljava/lang/String;
    .end local v16           #savedList:Lcom/openvehicles/OVMS/OVMSNotifications;
    .end local v18           #tickerText:Ljava/lang/CharSequence;
    .end local v19           #when:J
    :catch_0
    move-exception v7

    .line 91
    .local v7, e:Ljava/lang/Exception;
    invoke-virtual {v7}, Ljava/lang/Exception;->printStackTrace()V

    goto/16 :goto_1

    .line 111
    .end local v7           #e:Ljava/lang/Exception;
    .restart local v5       #contentText:Ljava/lang/CharSequence;
    .restart local v6       #contentTitle:Ljava/lang/CharSequence;
    .restart local v9       #icon:I
    .restart local v12       #mNotificationManager:Landroid/app/NotificationManager;
    .restart local v15       #ns:Ljava/lang/String;
    .restart local v16       #savedList:Lcom/openvehicles/OVMS/OVMSNotifications;
    .restart local v18       #tickerText:Ljava/lang/CharSequence;
    :cond_4
    invoke-interface/range {v21 .. v21}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Lcom/openvehicles/OVMS/CarData;

    .line 114
    .local v4, car:Lcom/openvehicles/OVMS/CarData;
    iget-object v0, v4, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    move-object/from16 v22, v0

    move-object/from16 v0, v22

    invoke-virtual {v0, v6}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v22

    if-eqz v22, :cond_2

    .line 116
    invoke-virtual/range {p1 .. p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v21

    new-instance v22, Ljava/lang/StringBuilder;

    iget-object v0, v4, Lcom/openvehicles/OVMS/CarData;->VehicleImageDrawable:Ljava/lang/String;

    move-object/from16 v23, v0

    invoke-static/range {v23 .. v23}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v23

    invoke-direct/range {v22 .. v23}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v23, "32x32"

    invoke-virtual/range {v22 .. v23}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    invoke-virtual/range {v22 .. v22}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v22

    const-string v23, "drawable"

    const-string v24, "com.openvehicles.OVMS"

    invoke-virtual/range {v21 .. v24}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v9

    .line 117
    goto/16 :goto_2
.end method

.method private handleRegistration(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 6
    .parameter "context"
    .parameter "intent"

    .prologue
    .line 30
    const-string v3, "registration_id"

    invoke-virtual {p2, v3}, Landroid/content/Intent;->getStringExtra(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    .line 31
    .local v2, registration:Ljava/lang/String;
    const-string v3, "error"

    invoke-virtual {p2, v3}, Landroid/content/Intent;->getStringExtra(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    if-eqz v3, :cond_6

    .line 33
    const-string v3, "C2DM"

    const-string v4, "registration failed"

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 34
    const-string v3, "error"

    invoke-virtual {p2, v3}, Landroid/content/Intent;->getStringExtra(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 35
    .local v1, error:Ljava/lang/String;
    const-string v3, "SERVICE_NOT_AVAILABLE"

    if-ne v1, v3, :cond_1

    .line 36
    const-string v3, "C2DM"

    const-string v4, "SERVICE_NOT_AVAILABLE"

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 66
    .end local v1           #error:Ljava/lang/String;
    :cond_0
    :goto_0
    return-void

    .line 37
    .restart local v1       #error:Ljava/lang/String;
    :cond_1
    const-string v3, "ACCOUNT_MISSING"

    if-ne v1, v3, :cond_2

    .line 38
    const-string v3, "C2DM"

    const-string v4, "ACCOUNT_MISSING"

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0

    .line 39
    :cond_2
    const-string v3, "AUTHENTICATION_FAILED"

    if-ne v1, v3, :cond_3

    .line 40
    const-string v3, "C2DM"

    const-string v4, "AUTHENTICATION_FAILED"

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0

    .line 41
    :cond_3
    const-string v3, "TOO_MANY_REGISTRATIONS"

    if-ne v1, v3, :cond_4

    .line 42
    const-string v3, "C2DM"

    const-string v4, "TOO_MANY_REGISTRATIONS"

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0

    .line 43
    :cond_4
    const-string v3, "INVALID_SENDER"

    if-ne v1, v3, :cond_5

    .line 44
    const-string v3, "C2DM"

    const-string v4, "INVALID_SENDER"

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0

    .line 45
    :cond_5
    const-string v3, "PHONE_REGISTRATION_ERROR"

    if-ne v1, v3, :cond_0

    .line 46
    const-string v3, "C2DM"

    const-string v4, "PHONE_REGISTRATION_ERROR"

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0

    .line 48
    .end local v1           #error:Ljava/lang/String;
    :cond_6
    const-string v3, "unregistered"

    invoke-virtual {p2, v3}, Landroid/content/Intent;->getStringExtra(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    if-eqz v3, :cond_7

    .line 50
    const-string v3, "C2DM"

    const-string v4, "unregistered"

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0

    .line 52
    :cond_7
    if-eqz v2, :cond_0

    .line 53
    const-string v3, "C2DM"

    new-instance v4, Ljava/lang/StringBuilder;

    const-string v5, "New C2DM ID: "

    invoke-direct {v4, v5}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v4, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 54
    const-string v3, "C2DM"

    const/4 v4, 0x0

    invoke-virtual {p1, v3, v4}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v3

    invoke-interface {v3}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    .line 55
    .local v0, editor:Landroid/content/SharedPreferences$Editor;
    const-string v3, "RegID"

    invoke-interface {v0, v3, v2}, Landroid/content/SharedPreferences$Editor;->putString(Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    .line 56
    invoke-interface {v0}, Landroid/content/SharedPreferences$Editor;->commit()Z

    goto :goto_0
.end method


# virtual methods
.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 2
    .parameter "context"
    .parameter "intent"

    .prologue
    .line 22
    invoke-virtual {p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object v0

    const-string v1, "com.google.android.c2dm.intent.REGISTRATION"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_1

    .line 23
    invoke-direct {p0, p1, p2}, Lcom/openvehicles/OVMS/C2DMReceiver;->handleRegistration(Landroid/content/Context;Landroid/content/Intent;)V

    .line 27
    :cond_0
    :goto_0
    return-void

    .line 24
    :cond_1
    invoke-virtual {p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object v0

    const-string v1, "com.google.android.c2dm.intent.RECEIVE"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    .line 25
    invoke-direct {p0, p1, p2}, Lcom/openvehicles/OVMS/C2DMReceiver;->handleMessage(Landroid/content/Context;Landroid/content/Intent;)V

    goto :goto_0
.end method
