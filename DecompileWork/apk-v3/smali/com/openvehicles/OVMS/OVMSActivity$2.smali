.class Lcom/openvehicles/OVMS/OVMSActivity$2;
.super Ljava/lang/Object;
.source "OVMSActivity.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/openvehicles/OVMS/OVMSActivity;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/openvehicles/OVMS/OVMSActivity;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/OVMSActivity;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    .line 314
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method private notifyTabRefresh(Ljava/lang/String;)V
    .locals 4
    .parameter "currentActivityId"

    .prologue
    .line 329
    const-string v1, "Tab"

    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "Tab refresh: "

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 331
    if-eqz p1, :cond_0

    .line 332
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->getLocalActivityManager()Landroid/app/LocalActivityManager;

    move-result-object v1

    .line 333
    invoke-virtual {v1, p1}, Landroid/app/LocalActivityManager;->getActivity(Ljava/lang/String;)Landroid/app/Activity;

    move-result-object v1

    .line 332
    if-nez v1, :cond_1

    .line 403
    :cond_0
    :goto_0
    return-void

    .line 336
    :cond_1
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    iget v1, v1, Lcom/openvehicles/OVMS/OVMSActivity;->DeviceScreenSize:I

    const/4 v2, 0x4

    if-ne v1, v2, :cond_9

    .line 337
    const-string v1, "tabInfo_xlarge"

    invoke-virtual {p1, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_4

    .line 338
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->getLocalActivityManager()Landroid/app/LocalActivityManager;

    move-result-object v1

    .line 339
    invoke-virtual {v1, p1}, Landroid/app/LocalActivityManager;->getActivity(Ljava/lang/String;)Landroid/app/Activity;

    move-result-object v0

    .line 338
    check-cast v0, Lcom/openvehicles/OVMS/TabInfo_xlarge;

    .line 340
    .local v0, tab:Lcom/openvehicles/OVMS/TabInfo_xlarge;
    iget v1, v0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->CurrentScreenOrientation:I

    iget-object v2, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v2}, Lcom/openvehicles/OVMS/OVMSActivity;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    .line 341
    invoke-virtual {v2}, Landroid/content/res/Resources;->getConfiguration()Landroid/content/res/Configuration;

    move-result-object v2

    iget v2, v2, Landroid/content/res/Configuration;->orientation:I

    .line 340
    if-eq v1, v2, :cond_2

    .line 342
    invoke-virtual {v0}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->OrientationChanged()V

    .line 343
    :cond_2
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->access$0(Lcom/openvehicles/OVMS/OVMSActivity;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v1

    iget-object v2, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->isLoggedIn:Z
    invoke-static {v2}, Lcom/openvehicles/OVMS/OVMSActivity;->access$1(Lcom/openvehicles/OVMS/OVMSActivity;)Z

    move-result v2

    invoke-virtual {v0, v1, v2}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->Refresh(Lcom/openvehicles/OVMS/CarData;Z)V

    .line 402
    .end local v0           #tab:Lcom/openvehicles/OVMS/TabInfo_xlarge;
    :cond_3
    :goto_1
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->getTabHost()Landroid/widget/TabHost;

    move-result-object v1

    invoke-virtual {v1}, Landroid/widget/TabHost;->invalidate()V

    goto :goto_0

    .line 344
    :cond_4
    const-string v1, "tabMap"

    invoke-virtual {p1, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_5

    .line 345
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->getLocalActivityManager()Landroid/app/LocalActivityManager;

    move-result-object v1

    .line 346
    invoke-virtual {v1, p1}, Landroid/app/LocalActivityManager;->getActivity(Ljava/lang/String;)Landroid/app/Activity;

    move-result-object v0

    .line 345
    check-cast v0, Lcom/openvehicles/OVMS/TabMap;

    .line 347
    .local v0, tab:Lcom/openvehicles/OVMS/TabMap;
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->access$0(Lcom/openvehicles/OVMS/OVMSActivity;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v1

    iget-object v2, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->isLoggedIn:Z
    invoke-static {v2}, Lcom/openvehicles/OVMS/OVMSActivity;->access$1(Lcom/openvehicles/OVMS/OVMSActivity;)Z

    move-result v2

    invoke-virtual {v0, v1, v2}, Lcom/openvehicles/OVMS/TabMap;->Refresh(Lcom/openvehicles/OVMS/CarData;Z)V

    goto :goto_1

    .line 348
    .end local v0           #tab:Lcom/openvehicles/OVMS/TabMap;
    :cond_5
    const-string v1, "tabNotifications"

    invoke-virtual {p1, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_6

    .line 349
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->getLocalActivityManager()Landroid/app/LocalActivityManager;

    move-result-object v1

    invoke-virtual {v1, p1}, Landroid/app/LocalActivityManager;->getActivity(Ljava/lang/String;)Landroid/app/Activity;

    move-result-object v0

    check-cast v0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;

    .line 351
    .local v0, tab:Lcom/openvehicles/OVMS/Tab_SubTabNotifications;
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->access$0(Lcom/openvehicles/OVMS/OVMSActivity;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v1

    iget-object v2, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->isLoggedIn:Z
    invoke-static {v2}, Lcom/openvehicles/OVMS/OVMSActivity;->access$1(Lcom/openvehicles/OVMS/OVMSActivity;)Z

    move-result v2

    invoke-virtual {v0, v1, v2}, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->Refresh(Lcom/openvehicles/OVMS/CarData;Z)V

    goto :goto_1

    .line 353
    .end local v0           #tab:Lcom/openvehicles/OVMS/Tab_SubTabNotifications;
    :cond_6
    const-string v1, "tabDataUtilizations"

    invoke-virtual {p1, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_7

    .line 354
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->getLocalActivityManager()Landroid/app/LocalActivityManager;

    move-result-object v1

    invoke-virtual {v1, p1}, Landroid/app/LocalActivityManager;->getActivity(Ljava/lang/String;)Landroid/app/Activity;

    move-result-object v0

    check-cast v0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;

    .line 356
    .local v0, tab:Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->access$0(Lcom/openvehicles/OVMS/OVMSActivity;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v1

    iget-object v2, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->isLoggedIn:Z
    invoke-static {v2}, Lcom/openvehicles/OVMS/OVMSActivity;->access$1(Lcom/openvehicles/OVMS/OVMSActivity;)Z

    move-result v2

    invoke-virtual {v0, v1, v2}, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->Refresh(Lcom/openvehicles/OVMS/CarData;Z)V

    goto :goto_1

    .line 358
    .end local v0           #tab:Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;
    :cond_7
    const-string v1, "tabCarSettings"

    invoke-virtual {p1, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_8

    .line 359
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->getLocalActivityManager()Landroid/app/LocalActivityManager;

    move-result-object v1

    invoke-virtual {v1, p1}, Landroid/app/LocalActivityManager;->getActivity(Ljava/lang/String;)Landroid/app/Activity;

    move-result-object v0

    check-cast v0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    .line 361
    .local v0, tab:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->access$0(Lcom/openvehicles/OVMS/OVMSActivity;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v1

    iget-object v2, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->isLoggedIn:Z
    invoke-static {v2}, Lcom/openvehicles/OVMS/OVMSActivity;->access$1(Lcom/openvehicles/OVMS/OVMSActivity;)Z

    move-result v2

    invoke-virtual {v0, v1, v2}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->Refresh(Lcom/openvehicles/OVMS/CarData;Z)V

    goto/16 :goto_1

    .line 362
    .end local v0           #tab:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;
    :cond_8
    const-string v1, "tabCars"

    invoke-virtual {p1, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_3

    .line 363
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->getLocalActivityManager()Landroid/app/LocalActivityManager;

    move-result-object v1

    .line 364
    invoke-virtual {v1, p1}, Landroid/app/LocalActivityManager;->getActivity(Ljava/lang/String;)Landroid/app/Activity;

    move-result-object v0

    .line 363
    check-cast v0, Lcom/openvehicles/OVMS/TabCars;

    .line 365
    .local v0, tab:Lcom/openvehicles/OVMS/TabCars;
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->allSavedCars:Ljava/util/ArrayList;
    invoke-static {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->access$4(Lcom/openvehicles/OVMS/OVMSActivity;)Ljava/util/ArrayList;

    move-result-object v1

    invoke-virtual {v0, v1}, Lcom/openvehicles/OVMS/TabCars;->LoadCars(Ljava/util/ArrayList;)V

    goto/16 :goto_1

    .line 368
    .end local v0           #tab:Lcom/openvehicles/OVMS/TabCars;
    :cond_9
    const-string v1, "tabInfo"

    invoke-virtual {p1, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_b

    .line 369
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->getLocalActivityManager()Landroid/app/LocalActivityManager;

    move-result-object v1

    .line 370
    invoke-virtual {v1, p1}, Landroid/app/LocalActivityManager;->getActivity(Ljava/lang/String;)Landroid/app/Activity;

    move-result-object v0

    .line 369
    check-cast v0, Lcom/openvehicles/OVMS/TabInfo;

    .line 371
    .local v0, tab:Lcom/openvehicles/OVMS/TabInfo;
    iget v1, v0, Lcom/openvehicles/OVMS/TabInfo;->CurrentScreenOrientation:I

    iget-object v2, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v2}, Lcom/openvehicles/OVMS/OVMSActivity;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    .line 372
    invoke-virtual {v2}, Landroid/content/res/Resources;->getConfiguration()Landroid/content/res/Configuration;

    move-result-object v2

    iget v2, v2, Landroid/content/res/Configuration;->orientation:I

    .line 371
    if-eq v1, v2, :cond_a

    .line 373
    invoke-virtual {v0}, Lcom/openvehicles/OVMS/TabInfo;->OrientationChanged()V

    .line 374
    :cond_a
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->access$0(Lcom/openvehicles/OVMS/OVMSActivity;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v1

    iget-object v2, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->isLoggedIn:Z
    invoke-static {v2}, Lcom/openvehicles/OVMS/OVMSActivity;->access$1(Lcom/openvehicles/OVMS/OVMSActivity;)Z

    move-result v2

    invoke-virtual {v0, v1, v2}, Lcom/openvehicles/OVMS/TabInfo;->Refresh(Lcom/openvehicles/OVMS/CarData;Z)V

    goto/16 :goto_1

    .line 375
    .end local v0           #tab:Lcom/openvehicles/OVMS/TabInfo;
    :cond_b
    const-string v1, "tabCar"

    invoke-virtual {p1, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_d

    .line 376
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->getLocalActivityManager()Landroid/app/LocalActivityManager;

    move-result-object v1

    .line 377
    invoke-virtual {v1, p1}, Landroid/app/LocalActivityManager;->getActivity(Ljava/lang/String;)Landroid/app/Activity;

    move-result-object v0

    .line 376
    check-cast v0, Lcom/openvehicles/OVMS/TabCar;

    .line 378
    .local v0, tab:Lcom/openvehicles/OVMS/TabCar;
    iget v1, v0, Lcom/openvehicles/OVMS/TabCar;->CurrentScreenOrientation:I

    iget-object v2, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v2}, Lcom/openvehicles/OVMS/OVMSActivity;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    .line 379
    invoke-virtual {v2}, Landroid/content/res/Resources;->getConfiguration()Landroid/content/res/Configuration;

    move-result-object v2

    iget v2, v2, Landroid/content/res/Configuration;->orientation:I

    .line 378
    if-eq v1, v2, :cond_c

    .line 380
    invoke-virtual {v0}, Lcom/openvehicles/OVMS/TabCar;->OrientationChanged()V

    .line 381
    :cond_c
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->access$0(Lcom/openvehicles/OVMS/OVMSActivity;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v1

    iget-object v2, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->isLoggedIn:Z
    invoke-static {v2}, Lcom/openvehicles/OVMS/OVMSActivity;->access$1(Lcom/openvehicles/OVMS/OVMSActivity;)Z

    move-result v2

    invoke-virtual {v0, v1, v2}, Lcom/openvehicles/OVMS/TabCar;->Refresh(Lcom/openvehicles/OVMS/CarData;Z)V

    goto/16 :goto_1

    .line 382
    .end local v0           #tab:Lcom/openvehicles/OVMS/TabCar;
    :cond_d
    const-string v1, "tabMap"

    invoke-virtual {p1, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_e

    .line 383
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->getLocalActivityManager()Landroid/app/LocalActivityManager;

    move-result-object v1

    .line 384
    invoke-virtual {v1, p1}, Landroid/app/LocalActivityManager;->getActivity(Ljava/lang/String;)Landroid/app/Activity;

    move-result-object v0

    .line 383
    check-cast v0, Lcom/openvehicles/OVMS/TabMap;

    .line 385
    .local v0, tab:Lcom/openvehicles/OVMS/TabMap;
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->access$0(Lcom/openvehicles/OVMS/OVMSActivity;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v1

    iget-object v2, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->isLoggedIn:Z
    invoke-static {v2}, Lcom/openvehicles/OVMS/OVMSActivity;->access$1(Lcom/openvehicles/OVMS/OVMSActivity;)Z

    move-result v2

    invoke-virtual {v0, v1, v2}, Lcom/openvehicles/OVMS/TabMap;->Refresh(Lcom/openvehicles/OVMS/CarData;Z)V

    goto/16 :goto_1

    .line 386
    .end local v0           #tab:Lcom/openvehicles/OVMS/TabMap;
    :cond_e
    const-string v1, "tabMiscFeatures"

    invoke-virtual {p1, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_f

    .line 387
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->getLocalActivityManager()Landroid/app/LocalActivityManager;

    move-result-object v1

    .line 388
    invoke-virtual {v1, p1}, Landroid/app/LocalActivityManager;->getActivity(Ljava/lang/String;)Landroid/app/Activity;

    move-result-object v0

    .line 387
    check-cast v0, Lcom/openvehicles/OVMS/TabMiscFeatures;

    .line 389
    .local v0, tab:Lcom/openvehicles/OVMS/TabMiscFeatures;
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->access$0(Lcom/openvehicles/OVMS/OVMSActivity;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v1

    iget-object v2, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->isLoggedIn:Z
    invoke-static {v2}, Lcom/openvehicles/OVMS/OVMSActivity;->access$1(Lcom/openvehicles/OVMS/OVMSActivity;)Z

    move-result v2

    invoke-virtual {v0, v1, v2}, Lcom/openvehicles/OVMS/TabMiscFeatures;->Refresh(Lcom/openvehicles/OVMS/CarData;Z)V

    goto/16 :goto_1

    .line 390
    .end local v0           #tab:Lcom/openvehicles/OVMS/TabMiscFeatures;
    :cond_f
    const-string v1, "tabCars"

    invoke-virtual {p1, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_10

    .line 391
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->getLocalActivityManager()Landroid/app/LocalActivityManager;

    move-result-object v1

    .line 392
    invoke-virtual {v1, p1}, Landroid/app/LocalActivityManager;->getActivity(Ljava/lang/String;)Landroid/app/Activity;

    move-result-object v0

    .line 391
    check-cast v0, Lcom/openvehicles/OVMS/TabCars;

    .line 393
    .local v0, tab:Lcom/openvehicles/OVMS/TabCars;
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->allSavedCars:Ljava/util/ArrayList;
    invoke-static {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->access$4(Lcom/openvehicles/OVMS/OVMSActivity;)Ljava/util/ArrayList;

    move-result-object v1

    invoke-virtual {v0, v1}, Lcom/openvehicles/OVMS/TabCars;->LoadCars(Ljava/util/ArrayList;)V

    goto/16 :goto_1

    .line 397
    .end local v0           #tab:Lcom/openvehicles/OVMS/TabCars;
    :cond_10
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->getTabHost()Landroid/widget/TabHost;

    move-result-object v1

    const/4 v2, 0x0

    invoke-virtual {v1, v2}, Landroid/widget/TabHost;->setCurrentTab(I)V

    goto/16 :goto_1
.end method


# virtual methods
.method public run()V
    .locals 2

    .prologue
    .line 316
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->isLoggedIn:Z
    invoke-static {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->access$1(Lcom/openvehicles/OVMS/OVMSActivity;)Z

    move-result v1

    if-eqz v1, :cond_1

    .line 317
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->progressLogin:Landroid/app/ProgressDialog;
    invoke-static {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->access$2(Lcom/openvehicles/OVMS/OVMSActivity;)Landroid/app/ProgressDialog;

    move-result-object v1

    if-eqz v1, :cond_0

    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->progressLogin:Landroid/app/ProgressDialog;
    invoke-static {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->access$2(Lcom/openvehicles/OVMS/OVMSActivity;)Landroid/app/ProgressDialog;

    move-result-object v1

    invoke-virtual {v1}, Landroid/app/ProgressDialog;->isShowing()Z

    move-result v1

    if-eqz v1, :cond_0

    .line 318
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->progressLogin:Landroid/app/ProgressDialog;
    invoke-static {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->access$2(Lcom/openvehicles/OVMS/OVMSActivity;)Landroid/app/ProgressDialog;

    move-result-object v1

    invoke-virtual {v1}, Landroid/app/ProgressDialog;->dismiss()V

    .line 319
    :cond_0
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->alertDialog:Landroid/app/AlertDialog;
    invoke-static {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->access$3(Lcom/openvehicles/OVMS/OVMSActivity;)Landroid/app/AlertDialog;

    move-result-object v1

    if-eqz v1, :cond_1

    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->alertDialog:Landroid/app/AlertDialog;
    invoke-static {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->access$3(Lcom/openvehicles/OVMS/OVMSActivity;)Landroid/app/AlertDialog;

    move-result-object v1

    invoke-virtual {v1}, Landroid/app/AlertDialog;->isShowing()Z

    move-result v1

    if-eqz v1, :cond_1

    .line 320
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->alertDialog:Landroid/app/AlertDialog;
    invoke-static {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->access$3(Lcom/openvehicles/OVMS/OVMSActivity;)Landroid/app/AlertDialog;

    move-result-object v1

    invoke-virtual {v1}, Landroid/app/AlertDialog;->dismiss()V

    .line 323
    :cond_1
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$2;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->getLocalActivityManager()Landroid/app/LocalActivityManager;

    move-result-object v1

    invoke-virtual {v1}, Landroid/app/LocalActivityManager;->getCurrentId()Ljava/lang/String;

    move-result-object v1

    .line 324
    invoke-virtual {v1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v0

    .line 325
    .local v0, currentActivityId:Ljava/lang/String;
    invoke-direct {p0, v0}, Lcom/openvehicles/OVMS/OVMSActivity$2;->notifyTabRefresh(Ljava/lang/String;)V

    .line 326
    return-void
.end method
