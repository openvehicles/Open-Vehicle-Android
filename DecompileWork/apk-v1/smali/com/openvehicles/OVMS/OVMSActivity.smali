.class public Lcom/openvehicles/OVMS/OVMSActivity;
.super Landroid/app/TabActivity;
.source "OVMSActivity.java"

# interfaces
.implements Landroid/widget/TabHost$OnTabChangeListener;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;
    }
.end annotation


# instance fields
.field public SuppressServerErrorDialog:Z

.field private alertDialog:Landroid/app/AlertDialog;

.field private allSavedCars:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList",
            "<",
            "Lcom/openvehicles/OVMS/CarData;",
            ">;"
        }
    .end annotation
.end field

.field private c2dmReportTimerHandler:Landroid/os/Handler;

.field private carData:Lcom/openvehicles/OVMS/CarData;

.field private isLoggedIn:Z

.field private lastServerException:Ljava/lang/Exception;

.field private pingServer:Ljava/lang/Runnable;

.field private pingServerTimerHandler:Landroid/os/Handler;

.field progressLogin:Landroid/app/ProgressDialog;

.field private progressLoginCloseDialog:Ljava/lang/Runnable;

.field private progressLoginShowDialog:Ljava/lang/Runnable;

.field private reportC2DMRegistrationID:Ljava/lang/Runnable;

.field private serverSocketErrorDialog:Ljava/lang/Runnable;

.field private final settingsFileName:Ljava/lang/String;

.field private tcpTask:Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;


# direct methods
.method public constructor <init>()V
    .locals 1

    .prologue
    .line 51
    invoke-direct {p0}, Landroid/app/TabActivity;-><init>()V

    .line 187
    const-string v0, "OVMSSavedCars.obj"

    iput-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->settingsFileName:Ljava/lang/String;

    .line 189
    new-instance v0, Landroid/os/Handler;

    invoke-direct {v0}, Landroid/os/Handler;-><init>()V

    iput-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->c2dmReportTimerHandler:Landroid/os/Handler;

    .line 190
    new-instance v0, Landroid/os/Handler;

    invoke-direct {v0}, Landroid/os/Handler;-><init>()V

    iput-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->pingServerTimerHandler:Landroid/os/Handler;

    .line 193
    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->SuppressServerErrorDialog:Z

    .line 194
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->progressLogin:Landroid/app/ProgressDialog;

    .line 196
    new-instance v0, Lcom/openvehicles/OVMS/OVMSActivity$1;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/OVMSActivity$1;-><init>(Lcom/openvehicles/OVMS/OVMSActivity;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->progressLoginCloseDialog:Ljava/lang/Runnable;

    .line 204
    new-instance v0, Lcom/openvehicles/OVMS/OVMSActivity$2;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/OVMSActivity$2;-><init>(Lcom/openvehicles/OVMS/OVMSActivity;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->progressLoginShowDialog:Ljava/lang/Runnable;

    .line 211
    new-instance v0, Lcom/openvehicles/OVMS/OVMSActivity$3;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/OVMSActivity$3;-><init>(Lcom/openvehicles/OVMS/OVMSActivity;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->serverSocketErrorDialog:Ljava/lang/Runnable;

    .line 243
    new-instance v0, Lcom/openvehicles/OVMS/OVMSActivity$4;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/OVMSActivity$4;-><init>(Lcom/openvehicles/OVMS/OVMSActivity;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->pingServer:Ljava/lang/Runnable;

    .line 254
    new-instance v0, Lcom/openvehicles/OVMS/OVMSActivity$5;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/OVMSActivity$5;-><init>(Lcom/openvehicles/OVMS/OVMSActivity;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->reportC2DMRegistrationID:Ljava/lang/Runnable;

    .line 476
    return-void
.end method

.method static synthetic access$000(Lcom/openvehicles/OVMS/OVMSActivity;)Landroid/app/AlertDialog;
    .locals 1
    .parameter "x0"

    .prologue
    .line 51
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->alertDialog:Landroid/app/AlertDialog;

    return-object v0
.end method

.method static synthetic access$002(Lcom/openvehicles/OVMS/OVMSActivity;Landroid/app/AlertDialog;)Landroid/app/AlertDialog;
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 51
    iput-object p1, p0, Lcom/openvehicles/OVMS/OVMSActivity;->alertDialog:Landroid/app/AlertDialog;

    return-object p1
.end method

.method static synthetic access$100(Lcom/openvehicles/OVMS/OVMSActivity;)Z
    .locals 1
    .parameter "x0"

    .prologue
    .line 51
    iget-boolean v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->isLoggedIn:Z

    return v0
.end method

.method static synthetic access$200(Lcom/openvehicles/OVMS/OVMSActivity;)Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;
    .locals 1
    .parameter "x0"

    .prologue
    .line 51
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->tcpTask:Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;

    return-object v0
.end method

.method static synthetic access$300(Lcom/openvehicles/OVMS/OVMSActivity;)Ljava/lang/Runnable;
    .locals 1
    .parameter "x0"

    .prologue
    .line 51
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->pingServer:Ljava/lang/Runnable;

    return-object v0
.end method

.method static synthetic access$400(Lcom/openvehicles/OVMS/OVMSActivity;)Landroid/os/Handler;
    .locals 1
    .parameter "x0"

    .prologue
    .line 51
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->pingServerTimerHandler:Landroid/os/Handler;

    return-object v0
.end method

.method static synthetic access$500(Lcom/openvehicles/OVMS/OVMSActivity;)Lcom/openvehicles/OVMS/CarData;
    .locals 1
    .parameter "x0"

    .prologue
    .line 51
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;

    return-object v0
.end method

.method static synthetic access$600(Lcom/openvehicles/OVMS/OVMSActivity;)Ljava/lang/Runnable;
    .locals 1
    .parameter "x0"

    .prologue
    .line 51
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->reportC2DMRegistrationID:Ljava/lang/Runnable;

    return-object v0
.end method

.method static synthetic access$700(Lcom/openvehicles/OVMS/OVMSActivity;)Landroid/os/Handler;
    .locals 1
    .parameter "x0"

    .prologue
    .line 51
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->c2dmReportTimerHandler:Landroid/os/Handler;

    return-object v0
.end method

.method static synthetic access$800(Lcom/openvehicles/OVMS/OVMSActivity;Ljava/lang/Exception;)V
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 51
    invoke-direct {p0, p1}, Lcom/openvehicles/OVMS/OVMSActivity;->notifyServerSocketError(Ljava/lang/Exception;)V

    return-void
.end method

.method static synthetic access$900(Lcom/openvehicles/OVMS/OVMSActivity;)V
    .locals 0
    .parameter "x0"

    .prologue
    .line 51
    invoke-direct {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->loginComplete()V

    return-void
.end method

.method private loadCars()V
    .locals 12

    .prologue
    .line 405
    :try_start_0
    const-string v7, "OVMS"

    const-string v8, "Loading saved cars from internal storage file: OVMSSavedCars.obj"

    invoke-static {v7, v8}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 407
    const-string v7, "OVMSSavedCars.obj"

    invoke-virtual {p0, v7}, Lcom/openvehicles/OVMS/OVMSActivity;->openFileInput(Ljava/lang/String;)Ljava/io/FileInputStream;

    move-result-object v2

    .line 408
    .local v2, fis:Ljava/io/FileInputStream;
    new-instance v4, Ljava/io/ObjectInputStream;

    invoke-direct {v4, v2}, Ljava/io/ObjectInputStream;-><init>(Ljava/io/InputStream;)V

    .line 409
    .local v4, is:Ljava/io/ObjectInputStream;
    invoke-virtual {v4}, Ljava/io/ObjectInputStream;->readObject()Ljava/lang/Object;

    move-result-object v7

    check-cast v7, Ljava/util/ArrayList;

    iput-object v7, p0, Lcom/openvehicles/OVMS/OVMSActivity;->allSavedCars:Ljava/util/ArrayList;

    .line 410
    invoke-virtual {v4}, Ljava/io/ObjectInputStream;->close()V

    .line 412
    const-string v7, "OVMS"

    const/4 v8, 0x0

    invoke-virtual {p0, v7, v8}, Lcom/openvehicles/OVMS/OVMSActivity;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v6

    .line 413
    .local v6, settings:Landroid/content/SharedPreferences;
    const-string v7, "LastVehicleID"

    const-string v8, ""

    invoke-interface {v6, v7, v8}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v5

    .line 415
    .local v5, lastVehicleID:Ljava/lang/String;
    invoke-virtual {v5}, Ljava/lang/String;->length()I

    move-result v7

    if-nez v7, :cond_1

    .line 418
    iget-object v7, p0, Lcom/openvehicles/OVMS/OVMSActivity;->allSavedCars:Ljava/util/ArrayList;

    const/4 v8, 0x0

    invoke-virtual {v7, v8}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v7

    check-cast v7, Lcom/openvehicles/OVMS/CarData;

    iput-object v7, p0, Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;

    .line 453
    .end local v2           #fis:Ljava/io/FileInputStream;
    .end local v4           #is:Ljava/io/ObjectInputStream;
    .end local v5           #lastVehicleID:Ljava/lang/String;
    .end local v6           #settings:Landroid/content/SharedPreferences;
    :cond_0
    :goto_0
    return-void

    .line 421
    .restart local v2       #fis:Ljava/io/FileInputStream;
    .restart local v4       #is:Ljava/io/ObjectInputStream;
    .restart local v5       #lastVehicleID:Ljava/lang/String;
    .restart local v6       #settings:Landroid/content/SharedPreferences;
    :cond_1
    const-string v7, "OVMS"

    const-string v8, "Loaded %s cars. Last used car is "

    const/4 v9, 0x2

    new-array v9, v9, [Ljava/lang/Object;

    const/4 v10, 0x0

    iget-object v11, p0, Lcom/openvehicles/OVMS/OVMSActivity;->allSavedCars:Ljava/util/ArrayList;

    invoke-virtual {v11}, Ljava/util/ArrayList;->size()I

    move-result v11

    invoke-static {v11}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v11

    aput-object v11, v9, v10

    const/4 v10, 0x1

    aput-object v5, v9, v10

    invoke-static {v8, v9}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v8

    invoke-static {v7, v8}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 424
    const/4 v3, 0x0

    .local v3, idx:I
    :goto_1
    iget-object v7, p0, Lcom/openvehicles/OVMS/OVMSActivity;->allSavedCars:Ljava/util/ArrayList;

    invoke-virtual {v7}, Ljava/util/ArrayList;->size()I

    move-result v7

    if-ge v3, v7, :cond_2

    .line 425
    iget-object v7, p0, Lcom/openvehicles/OVMS/OVMSActivity;->allSavedCars:Ljava/util/ArrayList;

    invoke-virtual {v7, v3}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v7

    check-cast v7, Lcom/openvehicles/OVMS/CarData;

    iget-object v7, v7, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    invoke-virtual {v7, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v7

    if-eqz v7, :cond_3

    .line 426
    iget-object v7, p0, Lcom/openvehicles/OVMS/OVMSActivity;->allSavedCars:Ljava/util/ArrayList;

    invoke-virtual {v7, v3}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v7

    check-cast v7, Lcom/openvehicles/OVMS/CarData;

    iput-object v7, p0, Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;

    .line 430
    :cond_2
    iget-object v7, p0, Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;

    if-nez v7, :cond_0

    .line 431
    iget-object v7, p0, Lcom/openvehicles/OVMS/OVMSActivity;->allSavedCars:Ljava/util/ArrayList;

    const/4 v8, 0x0

    invoke-virtual {v7, v8}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v7

    check-cast v7, Lcom/openvehicles/OVMS/CarData;

    iput-object v7, p0, Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    .line 434
    .end local v2           #fis:Ljava/io/FileInputStream;
    .end local v3           #idx:I
    .end local v4           #is:Ljava/io/ObjectInputStream;
    .end local v5           #lastVehicleID:Ljava/lang/String;
    .end local v6           #settings:Landroid/content/SharedPreferences;
    :catch_0
    move-exception v1

    .line 435
    .local v1, e:Ljava/lang/Exception;
    invoke-virtual {v1}, Ljava/lang/Exception;->printStackTrace()V

    .line 436
    const-string v7, "ERR"

    invoke-virtual {v1}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v8

    invoke-static {v7, v8}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 438
    const-string v7, "OVMS"

    const-string v8, "Invalid save file. Initializing with demo car."

    invoke-static {v7, v8}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 440
    new-instance v7, Ljava/util/ArrayList;

    invoke-direct {v7}, Ljava/util/ArrayList;-><init>()V

    iput-object v7, p0, Lcom/openvehicles/OVMS/OVMSActivity;->allSavedCars:Ljava/util/ArrayList;

    .line 441
    new-instance v0, Lcom/openvehicles/OVMS/CarData;

    invoke-direct {v0}, Lcom/openvehicles/OVMS/CarData;-><init>()V

    .line 442
    .local v0, demoCar:Lcom/openvehicles/OVMS/CarData;
    const-string v7, "DEMO"

    iput-object v7, v0, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    .line 443
    const-string v7, "DEMO"

    iput-object v7, v0, Lcom/openvehicles/OVMS/CarData;->CarPass:Ljava/lang/String;

    .line 444
    const-string v7, "DEMO"

    iput-object v7, v0, Lcom/openvehicles/OVMS/CarData;->UserPass:Ljava/lang/String;

    .line 445
    const-string v7, "www.openvehicles.com"

    iput-object v7, v0, Lcom/openvehicles/OVMS/CarData;->ServerNameOrIP:Ljava/lang/String;

    .line 446
    const-string v7, "car_models_signaturered"

    iput-object v7, v0, Lcom/openvehicles/OVMS/CarData;->VehicleImageDrawable:Ljava/lang/String;

    .line 447
    iget-object v7, p0, Lcom/openvehicles/OVMS/OVMSActivity;->allSavedCars:Ljava/util/ArrayList;

    invoke-virtual {v7, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 449
    iput-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;

    .line 451
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->saveCars()V

    goto/16 :goto_0

    .line 424
    .end local v0           #demoCar:Lcom/openvehicles/OVMS/CarData;
    .end local v1           #e:Ljava/lang/Exception;
    .restart local v2       #fis:Ljava/io/FileInputStream;
    .restart local v3       #idx:I
    .restart local v4       #is:Ljava/io/ObjectInputStream;
    .restart local v5       #lastVehicleID:Ljava/lang/String;
    .restart local v6       #settings:Landroid/content/SharedPreferences;
    :cond_3
    add-int/lit8 v3, v3, 0x1

    goto :goto_1
.end method

.method private loginComplete()V
    .locals 1

    .prologue
    .line 299
    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->isLoggedIn:Z

    .line 300
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->progressLoginCloseDialog:Ljava/lang/Runnable;

    invoke-virtual {p0, v0}, Lcom/openvehicles/OVMS/OVMSActivity;->runOnUiThread(Ljava/lang/Runnable;)V

    .line 301
    return-void
.end method

.method private notifyServerSocketError(Ljava/lang/Exception;)V
    .locals 1
    .parameter "e"

    .prologue
    .line 368
    iput-object p1, p0, Lcom/openvehicles/OVMS/OVMSActivity;->lastServerException:Ljava/lang/Exception;

    .line 369
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->serverSocketErrorDialog:Ljava/lang/Runnable;

    invoke-virtual {p0, v0}, Lcom/openvehicles/OVMS/OVMSActivity;->runOnUiThread(Ljava/lang/Runnable;)V

    .line 370
    return-void
.end method

.method private notifyTabUpdate(Ljava/lang/String;)V
    .locals 4
    .parameter "currentActivityId"

    .prologue
    .line 339
    const-string v1, "Tab"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Tab change to: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 341
    const-string v1, "tabInfo"

    if-ne p1, v1, :cond_0

    .line 342
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->getLocalActivityManager()Landroid/app/LocalActivityManager;

    move-result-object v1

    invoke-virtual {v1, p1}, Landroid/app/LocalActivityManager;->getActivity(Ljava/lang/String;)Landroid/app/Activity;

    move-result-object v0

    check-cast v0, Lcom/openvehicles/OVMS/TabInfo;

    .line 344
    .local v0, tab:Lcom/openvehicles/OVMS/TabInfo;
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;

    invoke-virtual {v0, v1}, Lcom/openvehicles/OVMS/TabInfo;->RefreshStatus(Lcom/openvehicles/OVMS/CarData;)V

    .line 364
    .end local v0           #tab:Lcom/openvehicles/OVMS/TabInfo;
    :goto_0
    return-void

    .line 345
    :cond_0
    const-string v1, "tabCar"

    if-ne p1, v1, :cond_1

    .line 346
    const-string v1, "Tab"

    const-string v2, "Telling tabCar to update"

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 347
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->getLocalActivityManager()Landroid/app/LocalActivityManager;

    move-result-object v1

    invoke-virtual {v1, p1}, Landroid/app/LocalActivityManager;->getActivity(Ljava/lang/String;)Landroid/app/Activity;

    move-result-object v0

    check-cast v0, Lcom/openvehicles/OVMS/TabCar;

    .line 349
    .local v0, tab:Lcom/openvehicles/OVMS/TabCar;
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;

    invoke-virtual {v0, v1}, Lcom/openvehicles/OVMS/TabCar;->RefreshStatus(Lcom/openvehicles/OVMS/CarData;)V

    goto :goto_0

    .line 350
    .end local v0           #tab:Lcom/openvehicles/OVMS/TabCar;
    :cond_1
    const-string v1, "tabMap"

    if-ne p1, v1, :cond_2

    .line 351
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->getLocalActivityManager()Landroid/app/LocalActivityManager;

    move-result-object v1

    invoke-virtual {v1, p1}, Landroid/app/LocalActivityManager;->getActivity(Ljava/lang/String;)Landroid/app/Activity;

    move-result-object v0

    check-cast v0, Lcom/openvehicles/OVMS/TabMap;

    .line 353
    .local v0, tab:Lcom/openvehicles/OVMS/TabMap;
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;

    invoke-virtual {v0, v1}, Lcom/openvehicles/OVMS/TabMap;->RefreshStatus(Lcom/openvehicles/OVMS/CarData;)V

    goto :goto_0

    .line 354
    .end local v0           #tab:Lcom/openvehicles/OVMS/TabMap;
    :cond_2
    const-string v1, "tabNotifications"

    if-ne p1, v1, :cond_3

    .line 355
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->getLocalActivityManager()Landroid/app/LocalActivityManager;

    move-result-object v1

    invoke-virtual {v1, p1}, Landroid/app/LocalActivityManager;->getActivity(Ljava/lang/String;)Landroid/app/Activity;

    move-result-object v0

    check-cast v0, Lcom/openvehicles/OVMS/TabNotifications;

    .line 357
    .local v0, tab:Lcom/openvehicles/OVMS/TabNotifications;
    invoke-virtual {v0}, Lcom/openvehicles/OVMS/TabNotifications;->Refresh()V

    goto :goto_0

    .line 358
    .end local v0           #tab:Lcom/openvehicles/OVMS/TabNotifications;
    :cond_3
    const-string v1, "tabCars"

    if-ne p1, v1, :cond_4

    .line 359
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->getLocalActivityManager()Landroid/app/LocalActivityManager;

    move-result-object v1

    invoke-virtual {v1, p1}, Landroid/app/LocalActivityManager;->getActivity(Ljava/lang/String;)Landroid/app/Activity;

    move-result-object v0

    check-cast v0, Lcom/openvehicles/OVMS/TabCars;

    .line 361
    .local v0, tab:Lcom/openvehicles/OVMS/TabCars;
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity;->allSavedCars:Ljava/util/ArrayList;

    invoke-virtual {v0, v1}, Lcom/openvehicles/OVMS/TabCars;->LoadCars(Ljava/util/ArrayList;)V

    goto :goto_0

    .line 363
    .end local v0           #tab:Lcom/openvehicles/OVMS/TabCars;
    :cond_4
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->getTabHost()Landroid/widget/TabHost;

    move-result-object v1

    const-string v2, "tabInfo"

    invoke-virtual {v1, v2}, Landroid/widget/TabHost;->setCurrentTabByTag(Ljava/lang/String;)V

    goto :goto_0
.end method


# virtual methods
.method public ChangeCar(Lcom/openvehicles/OVMS/CarData;)V
    .locals 5
    .parameter "car"

    .prologue
    const/4 v4, 0x1

    const/4 v3, 0x0

    .line 304
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->progressLoginShowDialog:Ljava/lang/Runnable;

    invoke-virtual {p0, v0}, Lcom/openvehicles/OVMS/OVMSActivity;->runOnUiThread(Ljava/lang/Runnable;)V

    .line 306
    const-string v0, "OVMS"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "Changed car to: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    iget-object v2, p1, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 308
    iput-boolean v3, p0, Lcom/openvehicles/OVMS/OVMSActivity;->isLoggedIn:Z

    .line 311
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->tcpTask:Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;

    if-eqz v0, :cond_0

    .line 312
    const-string v0, "TCP"

    const-string v1, "Shutting down pervious TCP connection (ChangeCar())"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 313
    iput-boolean v4, p0, Lcom/openvehicles/OVMS/OVMSActivity;->SuppressServerErrorDialog:Z

    .line 314
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->tcpTask:Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;

    invoke-virtual {v0}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->ConnClose()V

    .line 315
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->tcpTask:Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;

    invoke-virtual {v0, v4}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->cancel(Z)Z

    .line 316
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->tcpTask:Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;

    .line 317
    iput-boolean v3, p0, Lcom/openvehicles/OVMS/OVMSActivity;->SuppressServerErrorDialog:Z

    .line 321
    :cond_0
    iput-object p1, p0, Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;

    .line 324
    iput-boolean v3, p1, Lcom/openvehicles/OVMS/CarData;->ParanoidMode:Z

    .line 325
    new-instance v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;

    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;

    invoke-direct {v0, p0, v1}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;-><init>(Lcom/openvehicles/OVMS/OVMSActivity;Lcom/openvehicles/OVMS/CarData;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->tcpTask:Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;

    .line 326
    const-string v0, "TCP"

    const-string v1, "Starting TCP Connection (ChangeCar())"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 327
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->tcpTask:Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;

    new-array v1, v3, [Ljava/lang/Void;

    invoke-virtual {v0, v1}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->execute([Ljava/lang/Object;)Landroid/os/AsyncTask;

    .line 328
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->getTabHost()Landroid/widget/TabHost;

    move-result-object v0

    const-string v1, "tabInfo"

    invoke-virtual {v0, v1}, Landroid/widget/TabHost;->setCurrentTabByTag(Ljava/lang/String;)V

    .line 329
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->UpdateStatus()V

    .line 330
    return-void
.end method

.method public UpdateStatus()V
    .locals 3

    .prologue
    .line 333
    const-string v1, "OVMS"

    const-string v2, "Status Update"

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 334
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->getLocalActivityManager()Landroid/app/LocalActivityManager;

    move-result-object v1

    invoke-virtual {v1}, Landroid/app/LocalActivityManager;->getCurrentId()Ljava/lang/String;

    move-result-object v0

    .line 335
    .local v0, currentActivityId:Ljava/lang/String;
    invoke-direct {p0, v0}, Lcom/openvehicles/OVMS/OVMSActivity;->notifyTabUpdate(Ljava/lang/String;)V

    .line 336
    return-void
.end method

.method public onCreate(Landroid/os/Bundle;)V
    .locals 12
    .parameter "savedInstanceState"

    .prologue
    const-wide/16 v10, 0x7d0

    const/4 v9, 0x0

    .line 55
    invoke-super {p0, p1}, Landroid/app/TabActivity;->onCreate(Landroid/os/Bundle;)V

    .line 56
    const v7, 0x7f030002

    invoke-virtual {p0, v7}, Lcom/openvehicles/OVMS/OVMSActivity;->setContentView(I)V

    .line 59
    invoke-direct {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->loadCars()V

    .line 63
    const-string v7, "C2DM"

    invoke-virtual {p0, v7, v9}, Lcom/openvehicles/OVMS/OVMSActivity;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v4

    .line 64
    .local v4, settings:Landroid/content/SharedPreferences;
    const-string v7, "RegID"

    const-string v8, ""

    invoke-interface {v4, v7, v8}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    .line 65
    .local v2, registrationID:Ljava/lang/String;
    invoke-virtual {v2}, Ljava/lang/String;->length()I

    move-result v7

    if-nez v7, :cond_1

    .line 66
    const-string v7, "C2DM"

    const-string v8, "Doing first time registration."

    invoke-static {v7, v8}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 69
    const-string v7, "Push Notification Network"

    const-string v8, "Sending one-time registration..."

    invoke-static {p0, v7, v8}, Landroid/app/ProgressDialog;->show(Landroid/content/Context;Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Landroid/app/ProgressDialog;

    move-result-object v1

    .line 72
    .local v1, progress:Landroid/app/ProgressDialog;
    new-instance v3, Landroid/content/Intent;

    const-string v7, "com.google.android.c2dm.intent.REGISTER"

    invoke-direct {v3, v7}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 74
    .local v3, registrationIntent:Landroid/content/Intent;
    const-string v7, "app"

    new-instance v8, Landroid/content/Intent;

    invoke-direct {v8}, Landroid/content/Intent;-><init>()V

    invoke-static {p0, v9, v8, v9}, Landroid/app/PendingIntent;->getBroadcast(Landroid/content/Context;ILandroid/content/Intent;I)Landroid/app/PendingIntent;

    move-result-object v8

    invoke-virtual {v3, v7, v8}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Landroid/os/Parcelable;)Landroid/content/Intent;

    .line 76
    const-string v7, "sender"

    const-string v8, "openvehicles@gmail.com"

    invoke-virtual {v3, v7, v8}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    .line 77
    invoke-virtual {p0, v3}, Lcom/openvehicles/OVMS/OVMSActivity;->startService(Landroid/content/Intent;)Landroid/content/ComponentName;

    .line 78
    invoke-virtual {v1}, Landroid/app/ProgressDialog;->dismiss()V

    .line 80
    iget-object v7, p0, Lcom/openvehicles/OVMS/OVMSActivity;->c2dmReportTimerHandler:Landroid/os/Handler;

    iget-object v8, p0, Lcom/openvehicles/OVMS/OVMSActivity;->reportC2DMRegistrationID:Ljava/lang/Runnable;

    invoke-virtual {v7, v8, v10, v11}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    .line 95
    .end local v1           #progress:Landroid/app/ProgressDialog;
    .end local v3           #registrationIntent:Landroid/content/Intent;
    :goto_0
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->getTabHost()Landroid/widget/TabHost;

    move-result-object v6

    .line 99
    .local v6, tabHost:Landroid/widget/TabHost;
    new-instance v7, Landroid/content/Intent;

    invoke-direct {v7}, Landroid/content/Intent;-><init>()V

    const-class v8, Lcom/openvehicles/OVMS/TabInfo;

    invoke-virtual {v7, p0, v8}, Landroid/content/Intent;->setClass(Landroid/content/Context;Ljava/lang/Class;)Landroid/content/Intent;

    move-result-object v0

    .line 101
    .local v0, intent:Landroid/content/Intent;
    const-string v7, "tabInfo"

    invoke-virtual {v6, v7}, Landroid/widget/TabHost;->newTabSpec(Ljava/lang/String;)Landroid/widget/TabHost$TabSpec;

    move-result-object v5

    .line 102
    .local v5, spec:Landroid/widget/TabHost$TabSpec;
    invoke-virtual {v5, v0}, Landroid/widget/TabHost$TabSpec;->setContent(Landroid/content/Intent;)Landroid/widget/TabHost$TabSpec;

    .line 103
    const-string v7, ""

    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->getResources()Landroid/content/res/Resources;

    move-result-object v8

    const v9, 0x1080041

    invoke-virtual {v8, v9}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v8

    invoke-virtual {v5, v7, v8}, Landroid/widget/TabHost$TabSpec;->setIndicator(Ljava/lang/CharSequence;Landroid/graphics/drawable/Drawable;)Landroid/widget/TabHost$TabSpec;

    .line 105
    invoke-virtual {v6, v5}, Landroid/widget/TabHost;->addTab(Landroid/widget/TabHost$TabSpec;)V

    .line 107
    new-instance v7, Landroid/content/Intent;

    invoke-direct {v7}, Landroid/content/Intent;-><init>()V

    const-class v8, Lcom/openvehicles/OVMS/TabCar;

    invoke-virtual {v7, p0, v8}, Landroid/content/Intent;->setClass(Landroid/content/Context;Ljava/lang/Class;)Landroid/content/Intent;

    move-result-object v0

    .line 109
    const-string v7, "tabCar"

    invoke-virtual {v6, v7}, Landroid/widget/TabHost;->newTabSpec(Ljava/lang/String;)Landroid/widget/TabHost$TabSpec;

    move-result-object v5

    .line 110
    invoke-virtual {v5, v0}, Landroid/widget/TabHost$TabSpec;->setContent(Landroid/content/Intent;)Landroid/widget/TabHost$TabSpec;

    .line 111
    const-string v7, ""

    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->getResources()Landroid/content/res/Resources;

    move-result-object v8

    const v9, 0x1080049

    invoke-virtual {v8, v9}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v8

    invoke-virtual {v5, v7, v8}, Landroid/widget/TabHost$TabSpec;->setIndicator(Ljava/lang/CharSequence;Landroid/graphics/drawable/Drawable;)Landroid/widget/TabHost$TabSpec;

    .line 113
    invoke-virtual {v6, v5}, Landroid/widget/TabHost;->addTab(Landroid/widget/TabHost$TabSpec;)V

    .line 115
    new-instance v7, Landroid/content/Intent;

    invoke-direct {v7}, Landroid/content/Intent;-><init>()V

    const-class v8, Lcom/openvehicles/OVMS/TabMap;

    invoke-virtual {v7, p0, v8}, Landroid/content/Intent;->setClass(Landroid/content/Context;Ljava/lang/Class;)Landroid/content/Intent;

    move-result-object v0

    .line 117
    const-string v7, "tabMap"

    invoke-virtual {v6, v7}, Landroid/widget/TabHost;->newTabSpec(Ljava/lang/String;)Landroid/widget/TabHost$TabSpec;

    move-result-object v5

    .line 118
    invoke-virtual {v5, v0}, Landroid/widget/TabHost$TabSpec;->setContent(Landroid/content/Intent;)Landroid/widget/TabHost$TabSpec;

    .line 119
    const-string v7, ""

    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->getResources()Landroid/content/res/Resources;

    move-result-object v8

    const v9, 0x1080039

    invoke-virtual {v8, v9}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v8

    invoke-virtual {v5, v7, v8}, Landroid/widget/TabHost$TabSpec;->setIndicator(Ljava/lang/CharSequence;Landroid/graphics/drawable/Drawable;)Landroid/widget/TabHost$TabSpec;

    .line 121
    invoke-virtual {v6, v5}, Landroid/widget/TabHost;->addTab(Landroid/widget/TabHost$TabSpec;)V

    .line 123
    new-instance v7, Landroid/content/Intent;

    invoke-direct {v7}, Landroid/content/Intent;-><init>()V

    const-class v8, Lcom/openvehicles/OVMS/TabNotifications;

    invoke-virtual {v7, p0, v8}, Landroid/content/Intent;->setClass(Landroid/content/Context;Ljava/lang/Class;)Landroid/content/Intent;

    move-result-object v0

    .line 125
    const-string v7, "tabNotifications"

    invoke-virtual {v6, v7}, Landroid/widget/TabHost;->newTabSpec(Ljava/lang/String;)Landroid/widget/TabHost$TabSpec;

    move-result-object v5

    .line 126
    invoke-virtual {v5, v0}, Landroid/widget/TabHost$TabSpec;->setContent(Landroid/content/Intent;)Landroid/widget/TabHost$TabSpec;

    .line 127
    const-string v7, ""

    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->getResources()Landroid/content/res/Resources;

    move-result-object v8

    const v9, 0x1080034

    invoke-virtual {v8, v9}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v8

    invoke-virtual {v5, v7, v8}, Landroid/widget/TabHost$TabSpec;->setIndicator(Ljava/lang/CharSequence;Landroid/graphics/drawable/Drawable;)Landroid/widget/TabHost$TabSpec;

    .line 129
    invoke-virtual {v6, v5}, Landroid/widget/TabHost;->addTab(Landroid/widget/TabHost$TabSpec;)V

    .line 131
    new-instance v7, Landroid/content/Intent;

    invoke-direct {v7}, Landroid/content/Intent;-><init>()V

    const-class v8, Lcom/openvehicles/OVMS/TabCars;

    invoke-virtual {v7, p0, v8}, Landroid/content/Intent;->setClass(Landroid/content/Context;Ljava/lang/Class;)Landroid/content/Intent;

    move-result-object v0

    .line 133
    const-string v7, "tabCars"

    invoke-virtual {v6, v7}, Landroid/widget/TabHost;->newTabSpec(Ljava/lang/String;)Landroid/widget/TabHost$TabSpec;

    move-result-object v5

    .line 134
    invoke-virtual {v5, v0}, Landroid/widget/TabHost$TabSpec;->setContent(Landroid/content/Intent;)Landroid/widget/TabHost$TabSpec;

    .line 135
    const-string v7, ""

    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->getResources()Landroid/content/res/Resources;

    move-result-object v8

    const v9, 0x1080042

    invoke-virtual {v8, v9}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v8

    invoke-virtual {v5, v7, v8}, Landroid/widget/TabHost$TabSpec;->setIndicator(Ljava/lang/CharSequence;Landroid/graphics/drawable/Drawable;)Landroid/widget/TabHost$TabSpec;

    .line 137
    invoke-virtual {v6, v5}, Landroid/widget/TabHost;->addTab(Landroid/widget/TabHost$TabSpec;)V

    .line 143
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->getTabHost()Landroid/widget/TabHost;

    move-result-object v7

    invoke-virtual {v7, p0}, Landroid/widget/TabHost;->setOnTabChangedListener(Landroid/widget/TabHost$OnTabChangeListener;)V

    .line 145
    invoke-virtual {v6}, Landroid/widget/TabHost;->getCurrentTabTag()Ljava/lang/String;

    move-result-object v7

    const-string v8, ""

    if-ne v7, v8, :cond_0

    .line 146
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->getTabHost()Landroid/widget/TabHost;

    move-result-object v7

    const-string v8, "tabInfo"

    invoke-virtual {v7, v8}, Landroid/widget/TabHost;->setCurrentTabByTag(Ljava/lang/String;)V

    .line 147
    :cond_0
    return-void

    .line 89
    .end local v0           #intent:Landroid/content/Intent;
    .end local v5           #spec:Landroid/widget/TabHost$TabSpec;
    .end local v6           #tabHost:Landroid/widget/TabHost;
    :cond_1
    const-string v7, "C2DM"

    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "Loaded Saved C2DM registration ID: "

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    invoke-static {v7, v8}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 91
    iget-object v7, p0, Lcom/openvehicles/OVMS/OVMSActivity;->c2dmReportTimerHandler:Landroid/os/Handler;

    iget-object v8, p0, Lcom/openvehicles/OVMS/OVMSActivity;->reportC2DMRegistrationID:Ljava/lang/Runnable;

    invoke-virtual {v7, v8, v10, v11}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    goto/16 :goto_0
.end method

.method public onCreateOptionsMenu(Landroid/view/Menu;)Z
    .locals 2
    .parameter "menu"

    .prologue
    .line 161
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->getMenuInflater()Landroid/view/MenuInflater;

    move-result-object v0

    .line 162
    .local v0, inflater:Landroid/view/MenuInflater;
    const v1, 0x7f030003

    invoke-virtual {v0, v1, p1}, Landroid/view/MenuInflater;->inflate(ILandroid/view/Menu;)V

    .line 163
    const/4 v1, 0x1

    return v1
.end method

.method protected onDestory()V
    .locals 0

    .prologue
    .line 401
    return-void
.end method

.method public onNewIntent(Landroid/content/Intent;)V
    .locals 2
    .parameter "launchingIntent"

    .prologue
    .line 152
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->getTabHost()Landroid/widget/TabHost;

    move-result-object v0

    .line 153
    .local v0, tabHost:Landroid/widget/TabHost;
    if-eqz p1, :cond_0

    const-string v1, "SetTab"

    invoke-virtual {p1, v1}, Landroid/content/Intent;->hasExtra(Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_0

    .line 154
    const-string v1, "SetTab"

    invoke-virtual {p1, v1}, Landroid/content/Intent;->getStringExtra(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/TabHost;->setCurrentTabByTag(Ljava/lang/String;)V

    .line 157
    :goto_0
    return-void

    .line 156
    :cond_0
    const-string v1, "tabInfo"

    invoke-virtual {v0, v1}, Landroid/widget/TabHost;->setCurrentTabByTag(Ljava/lang/String;)V

    goto :goto_0
.end method

.method public onOptionsItemSelected(Landroid/view/MenuItem;)Z
    .locals 3
    .parameter "item"

    .prologue
    const/4 v1, 0x1

    .line 169
    invoke-interface {p1}, Landroid/view/MenuItem;->getItemId()I

    move-result v2

    packed-switch v2, :pswitch_data_0

    .line 180
    invoke-super {p0, p1}, Landroid/app/TabActivity;->onOptionsItemSelected(Landroid/view/MenuItem;)Z

    move-result v1

    :goto_0
    return v1

    .line 171
    :pswitch_0
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->finish()V

    goto :goto_0

    .line 174
    :pswitch_1
    new-instance v0, Lcom/openvehicles/OVMS/OVMSNotifications;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/OVMSNotifications;-><init>(Landroid/content/Context;)V

    .line 175
    .local v0, notifications:Lcom/openvehicles/OVMS/OVMSNotifications;
    new-instance v2, Ljava/util/ArrayList;

    invoke-direct {v2}, Ljava/util/ArrayList;-><init>()V

    iput-object v2, v0, Lcom/openvehicles/OVMS/OVMSNotifications;->Notifications:Ljava/util/ArrayList;

    .line 176
    invoke-virtual {v0}, Lcom/openvehicles/OVMS/OVMSNotifications;->Save()V

    .line 177
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->UpdateStatus()V

    goto :goto_0

    .line 169
    :pswitch_data_0
    .packed-switch 0x7f06000b
        :pswitch_0
        :pswitch_1
    .end packed-switch
.end method

.method protected onPause()V
    .locals 2

    .prologue
    .line 382
    invoke-super {p0}, Landroid/app/TabActivity;->onPause()V

    .line 387
    :try_start_0
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->tcpTask:Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;

    if-eqz v0, :cond_0

    .line 388
    const-string v0, "TCP"

    const-string v1, "Shutting down TCP connection"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 389
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->tcpTask:Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;

    invoke-virtual {v0}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->ConnClose()V

    .line 390
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->tcpTask:Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;

    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->cancel(Z)Z

    .line 391
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->tcpTask:Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 396
    :cond_0
    :goto_0
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->saveCars()V

    .line 397
    return-void

    .line 393
    :catch_0
    move-exception v0

    goto :goto_0
.end method

.method protected onResume()V
    .locals 1

    .prologue
    .line 374
    invoke-super {p0}, Landroid/app/TabActivity;->onResume()V

    .line 377
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;

    invoke-virtual {p0, v0}, Lcom/openvehicles/OVMS/OVMSActivity;->ChangeCar(Lcom/openvehicles/OVMS/CarData;)V

    .line 378
    return-void
.end method

.method public onTabChanged(Ljava/lang/String;)V
    .locals 0
    .parameter "currentActivityId"

    .prologue
    .line 295
    invoke-direct {p0, p1}, Lcom/openvehicles/OVMS/OVMSActivity;->notifyTabUpdate(Ljava/lang/String;)V

    .line 296
    return-void
.end method

.method public saveCars()V
    .locals 7

    .prologue
    .line 457
    :try_start_0
    const-string v5, "OVMS"

    const-string v6, "Saving cars to interal storage..."

    invoke-static {v5, v6}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 460
    const-string v5, "OVMS"

    const/4 v6, 0x0

    invoke-virtual {p0, v5, v6}, Lcom/openvehicles/OVMS/OVMSActivity;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v4

    .line 461
    .local v4, settings:Landroid/content/SharedPreferences;
    invoke-interface {v4}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v1

    .line 462
    .local v1, editor:Landroid/content/SharedPreferences$Editor;
    const-string v5, "LastVehicleID"

    iget-object v6, p0, Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;

    iget-object v6, v6, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    invoke-interface {v1, v5, v6}, Landroid/content/SharedPreferences$Editor;->putString(Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    .line 463
    invoke-interface {v1}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 465
    const-string v5, "OVMSSavedCars.obj"

    const/4 v6, 0x0

    invoke-virtual {p0, v5, v6}, Lcom/openvehicles/OVMS/OVMSActivity;->openFileOutput(Ljava/lang/String;I)Ljava/io/FileOutputStream;

    move-result-object v2

    .line 467
    .local v2, fos:Ljava/io/FileOutputStream;
    new-instance v3, Ljava/io/ObjectOutputStream;

    invoke-direct {v3, v2}, Ljava/io/ObjectOutputStream;-><init>(Ljava/io/OutputStream;)V

    .line 468
    .local v3, os:Ljava/io/ObjectOutputStream;
    iget-object v5, p0, Lcom/openvehicles/OVMS/OVMSActivity;->allSavedCars:Ljava/util/ArrayList;

    invoke-virtual {v3, v5}, Ljava/io/ObjectOutputStream;->writeObject(Ljava/lang/Object;)V

    .line 469
    invoke-virtual {v3}, Ljava/io/ObjectOutputStream;->close()V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 474
    .end local v1           #editor:Landroid/content/SharedPreferences$Editor;
    .end local v2           #fos:Ljava/io/FileOutputStream;
    .end local v3           #os:Ljava/io/ObjectOutputStream;
    .end local v4           #settings:Landroid/content/SharedPreferences;
    :goto_0
    return-void

    .line 470
    :catch_0
    move-exception v0

    .line 471
    .local v0, e:Ljava/lang/Exception;
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    .line 472
    const-string v5, "ERR"

    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0
.end method
