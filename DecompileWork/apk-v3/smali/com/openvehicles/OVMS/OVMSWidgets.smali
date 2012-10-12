.class public final Lcom/openvehicles/OVMS/OVMSWidgets;
.super Ljava/lang/Object;
.source "OVMSWidgets.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    .prologue
    .line 24
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static UpdateWidget(Landroid/content/Context;Landroid/appwidget/AppWidgetManager;I)V
    .locals 17
    .parameter "context"
    .parameter "appWidgetManager"
    .parameter "appWidgetId"

    .prologue
    .line 30
    const/4 v2, 0x0

    .line 32
    .local v2, allSavedCars:Ljava/util/ArrayList;,"Ljava/util/ArrayList<Lcom/openvehicles/OVMS/CarData;>;"
    :try_start_0
    const-string v10, "OVMSSavedCars.obj"

    .line 33
    .local v10, settingsFileName:Ljava/lang/String;
    const-string v12, "OVMS"

    const-string v13, "Loading saved cars from internal storage file: OVMSSavedCars.obj"

    invoke-static {v12, v13}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 35
    const-string v12, "OVMSSavedCars.obj"

    move-object/from16 v0, p0

    invoke-virtual {v0, v12}, Landroid/content/Context;->openFileInput(Ljava/lang/String;)Ljava/io/FileInputStream;

    move-result-object v5

    .line 36
    .local v5, fis:Ljava/io/FileInputStream;
    new-instance v7, Ljava/io/ObjectInputStream;

    invoke-direct {v7, v5}, Ljava/io/ObjectInputStream;-><init>(Ljava/io/InputStream;)V

    .line 37
    .local v7, is:Ljava/io/ObjectInputStream;
    invoke-virtual {v7}, Ljava/io/ObjectInputStream;->readObject()Ljava/lang/Object;

    move-result-object v12

    move-object v0, v12

    check-cast v0, Ljava/util/ArrayList;

    move-object v2, v0

    .line 38
    invoke-virtual {v7}, Ljava/io/ObjectInputStream;->close()V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 44
    const/4 v3, 0x0

    .line 46
    .local v3, carData:Lcom/openvehicles/OVMS/CarData;
    const-string v12, "OVMS"

    const/4 v13, 0x0

    move-object/from16 v0, p0

    invoke-virtual {v0, v12, v13}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v9

    .line 47
    .local v9, settings:Landroid/content/SharedPreferences;
    const-string v12, "LastVehicleID"

    const-string v13, ""

    invoke-interface {v9, v12, v13}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v12

    .line 48
    invoke-virtual {v12}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v8

    .line 49
    .local v8, lastVehicleID:Ljava/lang/String;
    invoke-virtual {v8}, Ljava/lang/String;->length()I

    move-result v12

    if-nez v12, :cond_1

    .line 52
    const/4 v12, 0x0

    invoke-virtual {v2, v12}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v3

    .end local v3           #carData:Lcom/openvehicles/OVMS/CarData;
    check-cast v3, Lcom/openvehicles/OVMS/CarData;

    .line 68
    .restart local v3       #carData:Lcom/openvehicles/OVMS/CarData;
    :cond_0
    :goto_0
    move-object/from16 v0, p0

    invoke-static {v0, v3}, Lcom/openvehicles/OVMS/OVMSWidgets;->getUpdatedRemoteViews(Landroid/content/Context;Lcom/openvehicles/OVMS/CarData;)Landroid/widget/RemoteViews;

    move-result-object v11

    .line 69
    .local v11, views:Landroid/widget/RemoteViews;
    if-nez v11, :cond_4

    .line 74
    .end local v3           #carData:Lcom/openvehicles/OVMS/CarData;
    .end local v5           #fis:Ljava/io/FileInputStream;
    .end local v7           #is:Ljava/io/ObjectInputStream;
    .end local v8           #lastVehicleID:Ljava/lang/String;
    .end local v9           #settings:Landroid/content/SharedPreferences;
    .end local v10           #settingsFileName:Ljava/lang/String;
    .end local v11           #views:Landroid/widget/RemoteViews;
    :goto_1
    return-void

    .line 39
    :catch_0
    move-exception v4

    .line 40
    .local v4, e:Ljava/lang/Exception;
    invoke-virtual {v4}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_1

    .line 55
    .end local v4           #e:Ljava/lang/Exception;
    .restart local v3       #carData:Lcom/openvehicles/OVMS/CarData;
    .restart local v5       #fis:Ljava/io/FileInputStream;
    .restart local v7       #is:Ljava/io/ObjectInputStream;
    .restart local v8       #lastVehicleID:Ljava/lang/String;
    .restart local v9       #settings:Landroid/content/SharedPreferences;
    .restart local v10       #settingsFileName:Ljava/lang/String;
    :cond_1
    const-string v12, "OVMS"

    .line 56
    const-string v13, "Loaded %s cars. Last used car is %s"

    const/4 v14, 0x2

    new-array v14, v14, [Ljava/lang/Object;

    const/4 v15, 0x0

    .line 57
    invoke-virtual {v2}, Ljava/util/ArrayList;->size()I

    move-result v16

    invoke-static/range {v16 .. v16}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v16

    aput-object v16, v14, v15

    const/4 v15, 0x1

    aput-object v8, v14, v15

    .line 55
    invoke-static {v13, v14}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v13

    invoke-static {v12, v13}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 58
    const/4 v6, 0x0

    .local v6, idx:I
    :goto_2
    invoke-virtual {v2}, Ljava/util/ArrayList;->size()I

    move-result v12

    if-lt v6, v12, :cond_2

    .line 64
    :goto_3
    if-nez v3, :cond_0

    .line 65
    const/4 v12, 0x0

    invoke-virtual {v2, v12}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v3

    .end local v3           #carData:Lcom/openvehicles/OVMS/CarData;
    check-cast v3, Lcom/openvehicles/OVMS/CarData;

    .restart local v3       #carData:Lcom/openvehicles/OVMS/CarData;
    goto :goto_0

    .line 59
    :cond_2
    invoke-virtual {v2, v6}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v12

    check-cast v12, Lcom/openvehicles/OVMS/CarData;

    iget-object v12, v12, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    invoke-virtual {v12, v8}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v12

    if-eqz v12, :cond_3

    .line 60
    invoke-virtual {v2, v6}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v3

    .end local v3           #carData:Lcom/openvehicles/OVMS/CarData;
    check-cast v3, Lcom/openvehicles/OVMS/CarData;

    .line 61
    .restart local v3       #carData:Lcom/openvehicles/OVMS/CarData;
    goto :goto_3

    .line 58
    :cond_3
    add-int/lit8 v6, v6, 0x1

    goto :goto_2

    .line 73
    .end local v6           #idx:I
    .restart local v11       #views:Landroid/widget/RemoteViews;
    :cond_4
    move-object/from16 v0, p1

    move/from16 v1, p2

    invoke-virtual {v0, v1, v11}, Landroid/appwidget/AppWidgetManager;->updateAppWidget(ILandroid/widget/RemoteViews;)V

    goto :goto_1
.end method

.method public static UpdateWidgets(Landroid/content/Context;)V
    .locals 18
    .parameter "context"

    .prologue
    .line 80
    const/4 v1, 0x0

    .line 82
    .local v1, allSavedCars:Ljava/util/ArrayList;,"Ljava/util/ArrayList<Lcom/openvehicles/OVMS/CarData;>;"
    :try_start_0
    const-string v10, "OVMSSavedCars.obj"

    .line 83
    .local v10, settingsFileName:Ljava/lang/String;
    const-string v13, "OVMS"

    const-string v14, "Loading saved cars from internal storage file: OVMSSavedCars.obj"

    invoke-static {v13, v14}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 85
    const-string v13, "OVMSSavedCars.obj"

    move-object/from16 v0, p0

    invoke-virtual {v0, v13}, Landroid/content/Context;->openFileInput(Ljava/lang/String;)Ljava/io/FileInputStream;

    move-result-object v5

    .line 86
    .local v5, fis:Ljava/io/FileInputStream;
    new-instance v7, Ljava/io/ObjectInputStream;

    invoke-direct {v7, v5}, Ljava/io/ObjectInputStream;-><init>(Ljava/io/InputStream;)V

    .line 87
    .local v7, is:Ljava/io/ObjectInputStream;
    invoke-virtual {v7}, Ljava/io/ObjectInputStream;->readObject()Ljava/lang/Object;

    move-result-object v13

    move-object v0, v13

    check-cast v0, Ljava/util/ArrayList;

    move-object v1, v0

    .line 88
    invoke-virtual {v7}, Ljava/io/ObjectInputStream;->close()V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 94
    const/4 v3, 0x0

    .line 96
    .local v3, carData:Lcom/openvehicles/OVMS/CarData;
    const-string v13, "OVMS"

    const/4 v14, 0x0

    move-object/from16 v0, p0

    invoke-virtual {v0, v13, v14}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v9

    .line 97
    .local v9, settings:Landroid/content/SharedPreferences;
    const-string v13, "LastVehicleID"

    const-string v14, ""

    invoke-interface {v9, v13, v14}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v13

    .line 98
    invoke-virtual {v13}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v8

    .line 99
    .local v8, lastVehicleID:Ljava/lang/String;
    invoke-virtual {v8}, Ljava/lang/String;->length()I

    move-result v13

    if-nez v13, :cond_1

    .line 102
    const/4 v13, 0x0

    invoke-virtual {v1, v13}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v3

    .end local v3           #carData:Lcom/openvehicles/OVMS/CarData;
    check-cast v3, Lcom/openvehicles/OVMS/CarData;

    .line 118
    .restart local v3       #carData:Lcom/openvehicles/OVMS/CarData;
    :cond_0
    :goto_0
    invoke-static/range {p0 .. p0}, Landroid/appwidget/AppWidgetManager;->getInstance(Landroid/content/Context;)Landroid/appwidget/AppWidgetManager;

    move-result-object v2

    .line 119
    .local v2, appWidgetManager:Landroid/appwidget/AppWidgetManager;
    new-instance v12, Landroid/content/ComponentName;

    const-class v13, Lcom/openvehicles/OVMS/OVMSWidgetProvider;

    move-object/from16 v0, p0

    invoke-direct {v12, v0, v13}, Landroid/content/ComponentName;-><init>(Landroid/content/Context;Ljava/lang/Class;)V

    .line 121
    .local v12, widgets:Landroid/content/ComponentName;
    move-object/from16 v0, p0

    invoke-static {v0, v3}, Lcom/openvehicles/OVMS/OVMSWidgets;->getUpdatedRemoteViews(Landroid/content/Context;Lcom/openvehicles/OVMS/CarData;)Landroid/widget/RemoteViews;

    move-result-object v11

    .line 122
    .local v11, views:Landroid/widget/RemoteViews;
    if-nez v11, :cond_4

    .line 127
    .end local v2           #appWidgetManager:Landroid/appwidget/AppWidgetManager;
    .end local v3           #carData:Lcom/openvehicles/OVMS/CarData;
    .end local v5           #fis:Ljava/io/FileInputStream;
    .end local v7           #is:Ljava/io/ObjectInputStream;
    .end local v8           #lastVehicleID:Ljava/lang/String;
    .end local v9           #settings:Landroid/content/SharedPreferences;
    .end local v10           #settingsFileName:Ljava/lang/String;
    .end local v11           #views:Landroid/widget/RemoteViews;
    .end local v12           #widgets:Landroid/content/ComponentName;
    :goto_1
    return-void

    .line 89
    :catch_0
    move-exception v4

    .line 90
    .local v4, e:Ljava/lang/Exception;
    invoke-virtual {v4}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_1

    .line 105
    .end local v4           #e:Ljava/lang/Exception;
    .restart local v3       #carData:Lcom/openvehicles/OVMS/CarData;
    .restart local v5       #fis:Ljava/io/FileInputStream;
    .restart local v7       #is:Ljava/io/ObjectInputStream;
    .restart local v8       #lastVehicleID:Ljava/lang/String;
    .restart local v9       #settings:Landroid/content/SharedPreferences;
    .restart local v10       #settingsFileName:Ljava/lang/String;
    :cond_1
    const-string v13, "OVMS"

    .line 106
    const-string v14, "Loaded %s cars. Last used car is %s"

    const/4 v15, 0x2

    new-array v15, v15, [Ljava/lang/Object;

    const/16 v16, 0x0

    .line 107
    invoke-virtual {v1}, Ljava/util/ArrayList;->size()I

    move-result v17

    invoke-static/range {v17 .. v17}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v17

    aput-object v17, v15, v16

    const/16 v16, 0x1

    aput-object v8, v15, v16

    .line 105
    invoke-static {v14, v15}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v14

    invoke-static {v13, v14}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 108
    const/4 v6, 0x0

    .local v6, idx:I
    :goto_2
    invoke-virtual {v1}, Ljava/util/ArrayList;->size()I

    move-result v13

    if-lt v6, v13, :cond_2

    .line 114
    :goto_3
    if-nez v3, :cond_0

    .line 115
    const/4 v13, 0x0

    invoke-virtual {v1, v13}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v3

    .end local v3           #carData:Lcom/openvehicles/OVMS/CarData;
    check-cast v3, Lcom/openvehicles/OVMS/CarData;

    .restart local v3       #carData:Lcom/openvehicles/OVMS/CarData;
    goto :goto_0

    .line 109
    :cond_2
    invoke-virtual {v1, v6}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v13

    check-cast v13, Lcom/openvehicles/OVMS/CarData;

    iget-object v13, v13, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    invoke-virtual {v13, v8}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v13

    if-eqz v13, :cond_3

    .line 110
    invoke-virtual {v1, v6}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v3

    .end local v3           #carData:Lcom/openvehicles/OVMS/CarData;
    check-cast v3, Lcom/openvehicles/OVMS/CarData;

    .line 111
    .restart local v3       #carData:Lcom/openvehicles/OVMS/CarData;
    goto :goto_3

    .line 108
    :cond_3
    add-int/lit8 v6, v6, 0x1

    goto :goto_2

    .line 126
    .end local v6           #idx:I
    .restart local v2       #appWidgetManager:Landroid/appwidget/AppWidgetManager;
    .restart local v11       #views:Landroid/widget/RemoteViews;
    .restart local v12       #widgets:Landroid/content/ComponentName;
    :cond_4
    invoke-virtual {v2, v12, v11}, Landroid/appwidget/AppWidgetManager;->updateAppWidget(Landroid/content/ComponentName;Landroid/widget/RemoteViews;)V

    goto :goto_1
.end method

.method private static getUpdatedRemoteViews(Landroid/content/Context;Lcom/openvehicles/OVMS/CarData;)Landroid/widget/RemoteViews;
    .locals 13
    .parameter "context"
    .parameter "carData"

    .prologue
    .line 130
    if-nez p1, :cond_0

    const/4 v0, 0x0

    .line 161
    :goto_0
    return-object v0

    .line 132
    :cond_0
    new-instance v0, Landroid/widget/RemoteViews;

    invoke-virtual {p0}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v1

    const v4, 0x7f030016

    invoke-direct {v0, v1, v4}, Landroid/widget/RemoteViews;-><init>(Ljava/lang/String;I)V

    .line 134
    .local v0, views:Landroid/widget/RemoteViews;
    const v1, 0x7f09009c

    const-string v4, "%s - "

    const/4 v5, 0x1

    new-array v5, v5, [Ljava/lang/Object;

    const/4 v9, 0x0

    iget v10, p1, Lcom/openvehicles/OVMS/CarData;->Data_EstimatedRange:I

    invoke-static {v10}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v10

    aput-object v10, v5, v9

    invoke-static {v4, v5}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v0, v1, v4}, Landroid/widget/RemoteViews;->setTextViewText(ILjava/lang/CharSequence;)V

    .line 135
    const v1, 0x7f09009d

    const-string v4, "%s"

    const/4 v5, 0x1

    new-array v5, v5, [Ljava/lang/Object;

    const/4 v9, 0x0

    iget v10, p1, Lcom/openvehicles/OVMS/CarData;->Data_IdealRange:I

    invoke-static {v10}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v10

    aput-object v10, v5, v9

    invoke-static {v4, v5}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v0, v1, v4}, Landroid/widget/RemoteViews;->setTextViewText(ILjava/lang/CharSequence;)V

    .line 136
    const v4, 0x7f09009e

    iget-object v1, p1, Lcom/openvehicles/OVMS/CarData;->Data_DistanceUnit:Ljava/lang/String;

    const-string v5, "M"

    invoke-virtual {v1, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_2

    const-string v1, " mi"

    :goto_1
    invoke-virtual {v0, v4, v1}, Landroid/widget/RemoteViews;->setTextViewText(ILjava/lang/CharSequence;)V

    .line 137
    const v1, 0x7f0900a5

    const-string v4, "%d%%"

    const/4 v5, 0x1

    new-array v5, v5, [Ljava/lang/Object;

    const/4 v9, 0x0

    iget v10, p1, Lcom/openvehicles/OVMS/CarData;->Data_SOC:I

    invoke-static {v10}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v10

    aput-object v10, v5, v9

    invoke-static {v4, v5}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v0, v1, v4}, Landroid/widget/RemoteViews;->setTextViewText(ILjava/lang/CharSequence;)V

    .line 138
    const v1, 0x7f09009b

    iget-object v4, p1, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    invoke-virtual {v0, v1, v4}, Landroid/widget/RemoteViews;->setTextViewText(ILjava/lang/CharSequence;)V

    .line 139
    const v4, 0x7f09009f

    iget-boolean v1, p1, Lcom/openvehicles/OVMS/CarData;->Data_CarPoweredON:Z

    if-nez v1, :cond_3

    iget-wide v9, p1, Lcom/openvehicles/OVMS/CarData;->Data_ParkedTime_raw:D

    const-wide/16 v11, 0x0

    cmpl-double v1, v9, v11

    if-lez v1, :cond_3

    const/4 v1, 0x0

    :goto_2
    invoke-virtual {v0, v4, v1}, Landroid/widget/RemoteViews;->setViewVisibility(II)V

    .line 140
    const v4, 0x7f0900a3

    iget-boolean v1, p1, Lcom/openvehicles/OVMS/CarData;->Data_Charging:Z

    if-eqz v1, :cond_4

    const/4 v1, 0x0

    :goto_3
    invoke-virtual {v0, v4, v1}, Landroid/widget/RemoteViews;->setViewVisibility(II)V

    .line 142
    const v1, 0x7f09009a

    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v4

    new-instance v5, Ljava/lang/StringBuilder;

    iget-object v9, p1, Lcom/openvehicles/OVMS/CarData;->VehicleImageDrawable:Ljava/lang/String;

    invoke-static {v9}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v9

    invoke-direct {v5, v9}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v9, "96x44"

    invoke-virtual {v5, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    const-string v9, "drawable"

    const-string v10, "com.openvehicles.OVMS"

    invoke-virtual {v4, v5, v9, v10}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v4

    invoke-virtual {v0, v1, v4}, Landroid/widget/RemoteViews;->setImageViewResource(II)V

    .line 143
    iget v1, p1, Lcom/openvehicles/OVMS/CarData;->Data_SOC:I

    if-lez v1, :cond_5

    .line 144
    const v1, 0x7f0900a4

    iget v4, p1, Lcom/openvehicles/OVMS/CarData;->Data_SOC:I

    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v5

    const v9, 0x7f020005

    invoke-static {v5, v9}, Landroid/graphics/BitmapFactory;->decodeResource(Landroid/content/res/Resources;I)Landroid/graphics/Bitmap;

    move-result-object v5

    invoke-static {v4, v5}, Lcom/openvehicles/OVMS/Utilities;->GetScaledBatteryOverlay(ILandroid/graphics/Bitmap;)Landroid/graphics/Bitmap;

    move-result-object v4

    invoke-virtual {v0, v1, v4}, Landroid/widget/RemoteViews;->setImageViewBitmap(ILandroid/graphics/Bitmap;)V

    .line 148
    :goto_4
    iget-object v1, p1, Lcom/openvehicles/OVMS/CarData;->Data_ParkedTime:Ljava/util/Date;

    if-eqz v1, :cond_1

    .line 150
    new-instance v7, Ljava/util/Date;

    invoke-direct {v7}, Ljava/util/Date;-><init>()V

    .line 151
    .local v7, now:Ljava/util/Date;
    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    move-result-wide v4

    invoke-virtual {v7}, Ljava/util/Date;->getTime()J

    move-result-wide v9

    iget-object v1, p1, Lcom/openvehicles/OVMS/CarData;->Data_ParkedTime:Ljava/util/Date;

    invoke-virtual {v1}, Ljava/util/Date;->getTime()J

    move-result-wide v11

    sub-long/2addr v9, v11

    sub-long v2, v4, v9

    .line 152
    .local v2, systemTimeParked:J
    const v1, 0x7f0900a0

    const/4 v4, 0x0

    const/4 v5, 0x1

    invoke-virtual/range {v0 .. v5}, Landroid/widget/RemoteViews;->setChronometer(IJLjava/lang/String;Z)V

    .line 156
    .end local v2           #systemTimeParked:J
    .end local v7           #now:Ljava/util/Date;
    :cond_1
    new-instance v6, Landroid/content/Intent;

    const-class v1, Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-direct {v6, p0, v1}, Landroid/content/Intent;-><init>(Landroid/content/Context;Ljava/lang/Class;)V

    .line 157
    .local v6, intent:Landroid/content/Intent;
    const/4 v1, 0x0

    .line 158
    const/4 v4, 0x0

    .line 157
    invoke-static {p0, v1, v6, v4}, Landroid/app/PendingIntent;->getActivity(Landroid/content/Context;ILandroid/content/Intent;I)Landroid/app/PendingIntent;

    move-result-object v8

    .line 159
    .local v8, pendingIntent:Landroid/app/PendingIntent;
    const v1, 0x7f090099

    invoke-virtual {v0, v1, v8}, Landroid/widget/RemoteViews;->setOnClickPendingIntent(ILandroid/app/PendingIntent;)V

    goto/16 :goto_0

    .line 136
    .end local v6           #intent:Landroid/content/Intent;
    .end local v8           #pendingIntent:Landroid/app/PendingIntent;
    :cond_2
    const-string v1, " km"

    goto/16 :goto_1

    .line 139
    :cond_3
    const/16 v1, 0x8

    goto/16 :goto_2

    .line 140
    :cond_4
    const/16 v1, 0x8

    goto/16 :goto_3

    .line 146
    :cond_5
    const v1, 0x7f0900a4

    const v4, 0x7f020002

    invoke-virtual {v0, v1, v4}, Landroid/widget/RemoteViews;->setImageViewResource(II)V

    goto :goto_4
.end method
