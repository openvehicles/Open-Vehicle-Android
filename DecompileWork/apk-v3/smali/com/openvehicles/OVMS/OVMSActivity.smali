.class public Lcom/openvehicles/OVMS/OVMSActivity;
.super Landroid/app/TabActivity;
.source "OVMSActivity.java"

# interfaces
.implements Landroid/widget/TabHost$OnTabChangeListener;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/openvehicles/OVMS/OVMSActivity$ServerCommandResponseHandler;,
        Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;
    }
.end annotation


# instance fields
.field public DeviceScreenSize:I

.field public final OVMS_CONFIG_FILE_VERSION:I

.field public final SCREENLAYOUT_SIZE_LARGE:I

.field public final SCREENLAYOUT_SIZE_XLARGE:I

.field public SuppressServerErrorDialog:Z

.field private UIHandler:Landroid/os/Handler;

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

.field private delayedRequest:Landroid/os/Handler;

.field private isLoggedIn:Z

.field private lastServerException:Ljava/lang/Exception;

.field private mCommandResponse:Lcom/openvehicles/OVMS/OVMSActivity$ServerCommandResponseHandler;

.field private mRecreateChildTabLayout:Ljava/lang/Runnable;

.field private mRefresh:Ljava/lang/Runnable;

.field private pingServer:Ljava/lang/Runnable;

.field private pingServerTimerHandler:Landroid/os/Handler;

.field private progressLogin:Landroid/app/ProgressDialog;

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
    .line 48
    invoke-direct {p0}, Landroid/app/TabActivity;-><init>()V

    .line 249
    const/4 v0, 0x3

    iput v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->SCREENLAYOUT_SIZE_LARGE:I

    .line 250
    const/4 v0, 0x4

    iput v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->SCREENLAYOUT_SIZE_XLARGE:I

    .line 255
    const-string v0, "OVMSSavedCars.obj"

    iput-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->settingsFileName:Ljava/lang/String;

    .line 257
    new-instance v0, Landroid/os/Handler;

    invoke-direct {v0}, Landroid/os/Handler;-><init>()V

    iput-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->c2dmReportTimerHandler:Landroid/os/Handler;

    .line 258
    new-instance v0, Landroid/os/Handler;

    invoke-direct {v0}, Landroid/os/Handler;-><init>()V

    iput-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->pingServerTimerHandler:Landroid/os/Handler;

    .line 259
    new-instance v0, Landroid/os/Handler;

    invoke-direct {v0}, Landroid/os/Handler;-><init>()V

    iput-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->UIHandler:Landroid/os/Handler;

    .line 260
    new-instance v0, Landroid/os/Handler;

    invoke-direct {v0}, Landroid/os/Handler;-><init>()V

    iput-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->delayedRequest:Landroid/os/Handler;

    .line 264
    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->SuppressServerErrorDialog:Z

    .line 265
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->progressLogin:Landroid/app/ProgressDialog;

    .line 266
    const/4 v0, 0x1

    iput v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->OVMS_CONFIG_FILE_VERSION:I

    .line 283
    new-instance v0, Lcom/openvehicles/OVMS/OVMSActivity$1;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/OVMSActivity$1;-><init>(Lcom/openvehicles/OVMS/OVMSActivity;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->mRecreateChildTabLayout:Ljava/lang/Runnable;

    .line 314
    new-instance v0, Lcom/openvehicles/OVMS/OVMSActivity$2;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/OVMSActivity$2;-><init>(Lcom/openvehicles/OVMS/OVMSActivity;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->mRefresh:Ljava/lang/Runnable;

    .line 406
    new-instance v0, Lcom/openvehicles/OVMS/OVMSActivity$3;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/OVMSActivity$3;-><init>(Lcom/openvehicles/OVMS/OVMSActivity;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->progressLoginCloseDialog:Ljava/lang/Runnable;

    .line 417
    new-instance v0, Lcom/openvehicles/OVMS/OVMSActivity$4;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/OVMSActivity$4;-><init>(Lcom/openvehicles/OVMS/OVMSActivity;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->progressLoginShowDialog:Ljava/lang/Runnable;

    .line 453
    new-instance v0, Lcom/openvehicles/OVMS/OVMSActivity$5;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/OVMSActivity$5;-><init>(Lcom/openvehicles/OVMS/OVMSActivity;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->serverSocketErrorDialog:Ljava/lang/Runnable;

    .line 503
    new-instance v0, Lcom/openvehicles/OVMS/OVMSActivity$6;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/OVMSActivity$6;-><init>(Lcom/openvehicles/OVMS/OVMSActivity;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->pingServer:Ljava/lang/Runnable;

    .line 518
    new-instance v0, Lcom/openvehicles/OVMS/OVMSActivity$7;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/OVMSActivity$7;-><init>(Lcom/openvehicles/OVMS/OVMSActivity;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->reportC2DMRegistrationID:Ljava/lang/Runnable;

    .line 48
    return-void
.end method

.method static synthetic access$0(Lcom/openvehicles/OVMS/OVMSActivity;)Lcom/openvehicles/OVMS/CarData;
    .locals 1
    .parameter

    .prologue
    .line 254
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;

    return-object v0
.end method

.method static synthetic access$1(Lcom/openvehicles/OVMS/OVMSActivity;)Z
    .locals 1
    .parameter

    .prologue
    .line 256
    iget-boolean v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->isLoggedIn:Z

    return v0
.end method

.method static synthetic access$10(Lcom/openvehicles/OVMS/OVMSActivity;)Landroid/os/Handler;
    .locals 1
    .parameter

    .prologue
    .line 257
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->c2dmReportTimerHandler:Landroid/os/Handler;

    return-object v0
.end method

.method static synthetic access$11(Lcom/openvehicles/OVMS/OVMSActivity;)Ljava/lang/Runnable;
    .locals 1
    .parameter

    .prologue
    .line 518
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->reportC2DMRegistrationID:Ljava/lang/Runnable;

    return-object v0
.end method

.method static synthetic access$12(Lcom/openvehicles/OVMS/OVMSActivity;Ljava/lang/Exception;)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 632
    invoke-direct {p0, p1}, Lcom/openvehicles/OVMS/OVMSActivity;->notifyServerSocketError(Ljava/lang/Exception;)V

    return-void
.end method

.method static synthetic access$13(Lcom/openvehicles/OVMS/OVMSActivity;)Landroid/os/Handler;
    .locals 1
    .parameter

    .prologue
    .line 259
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->UIHandler:Landroid/os/Handler;

    return-object v0
.end method

.method static synthetic access$14(Lcom/openvehicles/OVMS/OVMSActivity;)Ljava/lang/Runnable;
    .locals 1
    .parameter

    .prologue
    .line 314
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->mRefresh:Ljava/lang/Runnable;

    return-object v0
.end method

.method static synthetic access$15(Lcom/openvehicles/OVMS/OVMSActivity;Lcom/openvehicles/OVMS/OVMSActivity$ServerCommandResponseHandler;)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 281
    iput-object p1, p0, Lcom/openvehicles/OVMS/OVMSActivity;->mCommandResponse:Lcom/openvehicles/OVMS/OVMSActivity$ServerCommandResponseHandler;

    return-void
.end method

.method static synthetic access$16(Lcom/openvehicles/OVMS/OVMSActivity;)Lcom/openvehicles/OVMS/OVMSActivity$ServerCommandResponseHandler;
    .locals 1
    .parameter

    .prologue
    .line 281
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->mCommandResponse:Lcom/openvehicles/OVMS/OVMSActivity$ServerCommandResponseHandler;

    return-object v0
.end method

.method static synthetic access$17(Lcom/openvehicles/OVMS/OVMSActivity;Z)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 256
    iput-boolean p1, p0, Lcom/openvehicles/OVMS/OVMSActivity;->isLoggedIn:Z

    return-void
.end method

.method static synthetic access$18(Lcom/openvehicles/OVMS/OVMSActivity;)V
    .locals 0
    .parameter

    .prologue
    .line 570
    invoke-direct {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->loginComplete()V

    return-void
.end method

.method static synthetic access$2(Lcom/openvehicles/OVMS/OVMSActivity;)Landroid/app/ProgressDialog;
    .locals 1
    .parameter

    .prologue
    .line 265
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->progressLogin:Landroid/app/ProgressDialog;

    return-object v0
.end method

.method static synthetic access$3(Lcom/openvehicles/OVMS/OVMSActivity;)Landroid/app/AlertDialog;
    .locals 1
    .parameter

    .prologue
    .line 263
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->alertDialog:Landroid/app/AlertDialog;

    return-object v0
.end method

.method static synthetic access$4(Lcom/openvehicles/OVMS/OVMSActivity;)Ljava/util/ArrayList;
    .locals 1
    .parameter

    .prologue
    .line 253
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->allSavedCars:Ljava/util/ArrayList;

    return-object v0
.end method

.method static synthetic access$5(Lcom/openvehicles/OVMS/OVMSActivity;Landroid/app/ProgressDialog;)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 265
    iput-object p1, p0, Lcom/openvehicles/OVMS/OVMSActivity;->progressLogin:Landroid/app/ProgressDialog;

    return-void
.end method

.method static synthetic access$6(Lcom/openvehicles/OVMS/OVMSActivity;Landroid/app/AlertDialog;)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 263
    iput-object p1, p0, Lcom/openvehicles/OVMS/OVMSActivity;->alertDialog:Landroid/app/AlertDialog;

    return-void
.end method

.method static synthetic access$7(Lcom/openvehicles/OVMS/OVMSActivity;)Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;
    .locals 1
    .parameter

    .prologue
    .line 252
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->tcpTask:Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;

    return-object v0
.end method

.method static synthetic access$8(Lcom/openvehicles/OVMS/OVMSActivity;)Landroid/os/Handler;
    .locals 1
    .parameter

    .prologue
    .line 258
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->pingServerTimerHandler:Landroid/os/Handler;

    return-object v0
.end method

.method static synthetic access$9(Lcom/openvehicles/OVMS/OVMSActivity;)Ljava/lang/Runnable;
    .locals 1
    .parameter

    .prologue
    .line 503
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->pingServer:Ljava/lang/Runnable;

    return-object v0
.end method

.method private initializeSavedCars()V
    .locals 3

    .prologue
    .line 750
    const-string v1, "OVMS"

    const-string v2, "Invalid save file. Initializing with demo car."

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 752
    new-instance v1, Ljava/util/ArrayList;

    invoke-direct {v1}, Ljava/util/ArrayList;-><init>()V

    iput-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity;->allSavedCars:Ljava/util/ArrayList;

    .line 753
    new-instance v0, Lcom/openvehicles/OVMS/CarData;

    invoke-direct {v0}, Lcom/openvehicles/OVMS/CarData;-><init>()V

    .line 754
    .local v0, demoCar:Lcom/openvehicles/OVMS/CarData;
    const-string v1, "DEMO"

    iput-object v1, v0, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    .line 755
    const-string v1, "DEMO"

    iput-object v1, v0, Lcom/openvehicles/OVMS/CarData;->RegPass:Ljava/lang/String;

    .line 756
    const-string v1, "DEMO"

    iput-object v1, v0, Lcom/openvehicles/OVMS/CarData;->NetPass:Ljava/lang/String;

    .line 757
    const-string v1, "tmc.openvehicles.com"

    iput-object v1, v0, Lcom/openvehicles/OVMS/CarData;->ServerNameOrIP:Ljava/lang/String;

    .line 758
    const-string v1, "car_models_signaturered"

    iput-object v1, v0, Lcom/openvehicles/OVMS/CarData;->VehicleImageDrawable:Ljava/lang/String;

    .line 759
    const/4 v1, 0x1

    iput v1, v0, Lcom/openvehicles/OVMS/CarData;->lastResetVersion:I

    .line 760
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity;->allSavedCars:Ljava/util/ArrayList;

    invoke-virtual {v1, v0}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 762
    iput-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;

    .line 764
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->saveCars()V

    .line 765
    return-void
.end method

.method private loadCars()V
    .locals 13

    .prologue
    const/4 v11, 0x1

    .line 692
    :try_start_0
    const-string v8, "OVMS"

    const-string v9, "Loading saved cars from internal storage file: OVMSSavedCars.obj"

    invoke-static {v8, v9}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 694
    const-string v8, "OVMSSavedCars.obj"

    invoke-virtual {p0, v8}, Lcom/openvehicles/OVMS/OVMSActivity;->openFileInput(Ljava/lang/String;)Ljava/io/FileInputStream;

    move-result-object v2

    .line 695
    .local v2, fis:Ljava/io/FileInputStream;
    new-instance v4, Ljava/io/ObjectInputStream;

    invoke-direct {v4, v2}, Ljava/io/ObjectInputStream;-><init>(Ljava/io/InputStream;)V

    .line 696
    .local v4, is:Ljava/io/ObjectInputStream;
    invoke-virtual {v4}, Ljava/io/ObjectInputStream;->readObject()Ljava/lang/Object;

    move-result-object v8

    check-cast v8, Ljava/util/ArrayList;

    iput-object v8, p0, Lcom/openvehicles/OVMS/OVMSActivity;->allSavedCars:Ljava/util/ArrayList;

    .line 697
    invoke-virtual {v4}, Ljava/io/ObjectInputStream;->close()V

    .line 700
    iget-object v8, p0, Lcom/openvehicles/OVMS/OVMSActivity;->allSavedCars:Ljava/util/ArrayList;

    invoke-virtual {v8}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v8

    :cond_0
    :goto_0
    invoke-interface {v8}, Ljava/util/Iterator;->hasNext()Z

    move-result v9

    if-nez v9, :cond_2

    .line 720
    const-string v8, "OVMS"

    const/4 v9, 0x0

    invoke-virtual {p0, v8, v9}, Lcom/openvehicles/OVMS/OVMSActivity;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v7

    .line 721
    .local v7, settings:Landroid/content/SharedPreferences;
    const-string v8, "LastVehicleID"

    const-string v9, ""

    invoke-interface {v7, v8, v9}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v8

    .line 722
    invoke-virtual {v8}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v5

    .line 723
    .local v5, lastVehicleID:Ljava/lang/String;
    invoke-virtual {v5}, Ljava/lang/String;->length()I

    move-result v8

    if-nez v8, :cond_5

    .line 726
    iget-object v8, p0, Lcom/openvehicles/OVMS/OVMSActivity;->allSavedCars:Ljava/util/ArrayList;

    const/4 v9, 0x0

    invoke-virtual {v8, v9}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v8

    check-cast v8, Lcom/openvehicles/OVMS/CarData;

    iput-object v8, p0, Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;

    .line 747
    .end local v2           #fis:Ljava/io/FileInputStream;
    .end local v4           #is:Ljava/io/ObjectInputStream;
    .end local v5           #lastVehicleID:Ljava/lang/String;
    .end local v7           #settings:Landroid/content/SharedPreferences;
    :cond_1
    :goto_1
    return-void

    .line 700
    .restart local v2       #fis:Ljava/io/FileInputStream;
    .restart local v4       #is:Ljava/io/ObjectInputStream;
    :cond_2
    invoke-interface {v8}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/openvehicles/OVMS/CarData;

    .line 701
    .local v0, car:Lcom/openvehicles/OVMS/CarData;
    iget-object v9, v0, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    if-eqz v9, :cond_3

    iget-object v9, v0, Lcom/openvehicles/OVMS/CarData;->RegPass:Ljava/lang/String;

    if-eqz v9, :cond_3

    .line 702
    iget-object v9, v0, Lcom/openvehicles/OVMS/CarData;->NetPass:Ljava/lang/String;

    if-eqz v9, :cond_3

    .line 703
    iget-object v9, v0, Lcom/openvehicles/OVMS/CarData;->ServerNameOrIP:Ljava/lang/String;

    if-eqz v9, :cond_3

    .line 704
    iget-object v9, v0, Lcom/openvehicles/OVMS/CarData;->VehicleImageDrawable:Ljava/lang/String;

    if-nez v9, :cond_4

    .line 705
    :cond_3
    invoke-direct {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->initializeSavedCars()V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    .line 742
    .end local v0           #car:Lcom/openvehicles/OVMS/CarData;
    .end local v2           #fis:Ljava/io/FileInputStream;
    .end local v4           #is:Ljava/io/ObjectInputStream;
    :catch_0
    move-exception v1

    .line 743
    .local v1, e:Ljava/lang/Exception;
    invoke-virtual {v1}, Ljava/lang/Exception;->printStackTrace()V

    .line 745
    invoke-direct {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->initializeSavedCars()V

    goto :goto_1

    .line 706
    .end local v1           #e:Ljava/lang/Exception;
    .restart local v0       #car:Lcom/openvehicles/OVMS/CarData;
    .restart local v2       #fis:Ljava/io/FileInputStream;
    .restart local v4       #is:Ljava/io/ObjectInputStream;
    :cond_4
    :try_start_1
    iget v9, v0, Lcom/openvehicles/OVMS/CarData;->lastResetVersion:I

    if-eq v9, v11, :cond_0

    .line 708
    new-instance v6, Lcom/openvehicles/OVMS/CarData;

    invoke-direct {v6}, Lcom/openvehicles/OVMS/CarData;-><init>()V

    .line 709
    .local v6, newCarFile:Lcom/openvehicles/OVMS/CarData;
    iget-object v9, v0, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    iput-object v9, v6, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    .line 710
    iget-object v9, v0, Lcom/openvehicles/OVMS/CarData;->RegPass:Ljava/lang/String;

    iput-object v9, v6, Lcom/openvehicles/OVMS/CarData;->RegPass:Ljava/lang/String;

    .line 711
    iget-object v9, v0, Lcom/openvehicles/OVMS/CarData;->NetPass:Ljava/lang/String;

    iput-object v9, v6, Lcom/openvehicles/OVMS/CarData;->NetPass:Ljava/lang/String;

    .line 712
    iget-object v9, v0, Lcom/openvehicles/OVMS/CarData;->ServerNameOrIP:Ljava/lang/String;

    iput-object v9, v6, Lcom/openvehicles/OVMS/CarData;->ServerNameOrIP:Ljava/lang/String;

    .line 713
    iget-object v9, v0, Lcom/openvehicles/OVMS/CarData;->VehicleImageDrawable:Ljava/lang/String;

    iput-object v9, v6, Lcom/openvehicles/OVMS/CarData;->VehicleImageDrawable:Ljava/lang/String;

    .line 714
    const/4 v9, 0x1

    iput v9, v6, Lcom/openvehicles/OVMS/CarData;->lastResetVersion:I

    .line 715
    iget-object v9, p0, Lcom/openvehicles/OVMS/OVMSActivity;->allSavedCars:Ljava/util/ArrayList;

    iget-object v10, p0, Lcom/openvehicles/OVMS/OVMSActivity;->allSavedCars:Ljava/util/ArrayList;

    invoke-virtual {v10, v0}, Ljava/util/ArrayList;->indexOf(Ljava/lang/Object;)I

    move-result v10

    invoke-virtual {v9, v10, v6}, Ljava/util/ArrayList;->set(ILjava/lang/Object;)Ljava/lang/Object;

    goto :goto_0

    .line 729
    .end local v0           #car:Lcom/openvehicles/OVMS/CarData;
    .end local v6           #newCarFile:Lcom/openvehicles/OVMS/CarData;
    .restart local v5       #lastVehicleID:Ljava/lang/String;
    .restart local v7       #settings:Landroid/content/SharedPreferences;
    :cond_5
    const-string v8, "OVMS"

    .line 730
    const-string v9, "Loaded %s cars. Last used car is %s"

    const/4 v10, 0x2

    new-array v10, v10, [Ljava/lang/Object;

    const/4 v11, 0x0

    .line 731
    iget-object v12, p0, Lcom/openvehicles/OVMS/OVMSActivity;->allSavedCars:Ljava/util/ArrayList;

    invoke-virtual {v12}, Ljava/util/ArrayList;->size()I

    move-result v12

    invoke-static {v12}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v12

    aput-object v12, v10, v11

    const/4 v11, 0x1

    aput-object v5, v10, v11

    .line 729
    invoke-static {v9, v10}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v9

    invoke-static {v8, v9}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 732
    const/4 v3, 0x0

    .local v3, idx:I
    :goto_2
    iget-object v8, p0, Lcom/openvehicles/OVMS/OVMSActivity;->allSavedCars:Ljava/util/ArrayList;

    invoke-virtual {v8}, Ljava/util/ArrayList;->size()I

    move-result v8

    if-lt v3, v8, :cond_6

    .line 738
    :goto_3
    iget-object v8, p0, Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;

    if-nez v8, :cond_1

    .line 739
    iget-object v8, p0, Lcom/openvehicles/OVMS/OVMSActivity;->allSavedCars:Ljava/util/ArrayList;

    const/4 v9, 0x0

    invoke-virtual {v8, v9}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v8

    check-cast v8, Lcom/openvehicles/OVMS/CarData;

    iput-object v8, p0, Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;

    goto/16 :goto_1

    .line 733
    :cond_6
    iget-object v8, p0, Lcom/openvehicles/OVMS/OVMSActivity;->allSavedCars:Ljava/util/ArrayList;

    invoke-virtual {v8, v3}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v8

    check-cast v8, Lcom/openvehicles/OVMS/CarData;

    iget-object v8, v8, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    invoke-virtual {v8, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v8

    if-eqz v8, :cond_7

    .line 734
    iget-object v8, p0, Lcom/openvehicles/OVMS/OVMSActivity;->allSavedCars:Ljava/util/ArrayList;

    invoke-virtual {v8, v3}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v8

    check-cast v8, Lcom/openvehicles/OVMS/CarData;

    iput-object v8, p0, Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_0

    goto :goto_3

    .line 732
    :cond_7
    add-int/lit8 v3, v3, 0x1

    goto :goto_2
.end method

.method private loginComplete()V
    .locals 4

    .prologue
    const/16 v3, 0xb

    .line 571
    const/4 v1, 0x1

    iput-boolean v1, p0, Lcom/openvehicles/OVMS/OVMSActivity;->isLoggedIn:Z

    .line 573
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity;->UIHandler:Landroid/os/Handler;

    iget-object v2, p0, Lcom/openvehicles/OVMS/OVMSActivity;->progressLoginCloseDialog:Ljava/lang/Runnable;

    invoke-virtual {v1, v2}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 575
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->ReportC2DMRegistrationID()V

    .line 578
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;

    iget-object v1, v1, Lcom/openvehicles/OVMS/CarData;->Data_Parameters:Ljava/util/LinkedHashMap;

    invoke-static {v3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/String;

    invoke-virtual {v1}, Ljava/lang/String;->length()I

    move-result v1

    if-lez v1, :cond_0

    .line 579
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;

    iget-object v1, v1, Lcom/openvehicles/OVMS/CarData;->Data_Parameters:Ljava/util/LinkedHashMap;

    invoke-static {v3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/String;

    invoke-static {v1}, Lcom/openvehicles/OVMS/ServerCommands;->SUBSCRIBE_GROUP(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/OVMSActivity;->SendServerCommand(Ljava/lang/String;)Z

    .line 590
    :goto_0
    return-void

    .line 582
    :cond_0
    const-string v1, "C3"

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/OVMSActivity;->SendServerCommand(Ljava/lang/String;)Z

    .line 583
    new-instance v0, Lcom/openvehicles/OVMS/OVMSActivity$8;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/OVMSActivity$8;-><init>(Lcom/openvehicles/OVMS/OVMSActivity;)V

    .line 588
    .local v0, r:Ljava/lang/Runnable;
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity;->delayedRequest:Landroid/os/Handler;

    const-wide/16 v2, 0xc8

    invoke-virtual {v1, v0, v2, v3}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    goto :goto_0
.end method

.method private notifyServerSocketError(Ljava/lang/Exception;)V
    .locals 2
    .parameter "e"

    .prologue
    .line 634
    iput-object p1, p0, Lcom/openvehicles/OVMS/OVMSActivity;->lastServerException:Ljava/lang/Exception;

    .line 635
    if-eqz p1, :cond_0

    .line 636
    invoke-virtual {p1}, Ljava/lang/Exception;->printStackTrace()V

    .line 637
    :cond_0
    iget-boolean v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->SuppressServerErrorDialog:Z

    if-nez v0, :cond_1

    .line 639
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->UIHandler:Landroid/os/Handler;

    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity;->serverSocketErrorDialog:Ljava/lang/Runnable;

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 640
    :cond_1
    return-void
.end method

.method private notifyTabRefresh()V
    .locals 2

    .prologue
    .line 629
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->UIHandler:Landroid/os/Handler;

    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity;->mRefresh:Ljava/lang/Runnable;

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 630
    return-void
.end method


# virtual methods
.method public ChangeCar(Lcom/openvehicles/OVMS/CarData;)V
    .locals 1
    .parameter "car"

    .prologue
    .line 593
    const-string v0, "tabInfo"

    invoke-virtual {p0, p1, v0}, Lcom/openvehicles/OVMS/OVMSActivity;->ChangeCar(Lcom/openvehicles/OVMS/CarData;Ljava/lang/String;)V

    .line 594
    return-void
.end method

.method public ChangeCar(Lcom/openvehicles/OVMS/CarData;Ljava/lang/String;)V
    .locals 4
    .parameter "car"
    .parameter "initialTabTag"

    .prologue
    const/4 v3, 0x0

    .line 598
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->UIHandler:Landroid/os/Handler;

    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity;->progressLoginShowDialog:Ljava/lang/Runnable;

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 600
    const-string v0, "OVMS"

    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "Changed car to: "

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    iget-object v2, p1, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 602
    iput-boolean v3, p0, Lcom/openvehicles/OVMS/OVMSActivity;->isLoggedIn:Z

    .line 605
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->tcpTask:Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;

    if-eqz v0, :cond_0

    .line 606
    const-string v0, "TCP"

    const-string v1, "Shutting down pervious TCP connection (ChangeCar())"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 607
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->tcpTask:Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;

    invoke-virtual {v0}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->ConnClose()V

    .line 608
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->tcpTask:Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;

    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->cancel(Z)Z

    .line 609
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->tcpTask:Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;

    .line 613
    :cond_0
    iput-object p1, p0, Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;

    .line 614
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->Data_GPRSUtilization:Lcom/openvehicles/OVMS/GPRSUtilization;

    if-nez v0, :cond_1

    .line 615
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;

    new-instance v1, Lcom/openvehicles/OVMS/GPRSUtilization;

    invoke-direct {v1, p0}, Lcom/openvehicles/OVMS/GPRSUtilization;-><init>(Landroid/content/Context;)V

    iput-object v1, v0, Lcom/openvehicles/OVMS/CarData;->Data_GPRSUtilization:Lcom/openvehicles/OVMS/GPRSUtilization;

    .line 617
    :cond_1
    invoke-direct {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->notifyTabRefresh()V

    .line 620
    iput-boolean v3, p1, Lcom/openvehicles/OVMS/CarData;->ParanoidMode:Z

    .line 621
    new-instance v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;

    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;

    invoke-direct {v0, p0, v1}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;-><init>(Lcom/openvehicles/OVMS/OVMSActivity;Lcom/openvehicles/OVMS/CarData;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->tcpTask:Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;

    .line 622
    const-string v0, "TCP"

    const-string v1, "Starting TCP Connection (ChangeCar())"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 623
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->tcpTask:Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;

    new-array v1, v3, [Ljava/lang/Void;

    invoke-virtual {v0, v1}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->execute([Ljava/lang/Object;)Landroid/os/AsyncTask;

    .line 624
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->getTabHost()Landroid/widget/TabHost;

    move-result-object v0

    invoke-virtual {v0, p2}, Landroid/widget/TabHost;->setCurrentTabByTag(Ljava/lang/String;)V

    .line 625
    return-void
.end method

.method public ReportC2DMRegistrationID()V
    .locals 4

    .prologue
    .line 515
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->c2dmReportTimerHandler:Landroid/os/Handler;

    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity;->reportC2DMRegistrationID:Ljava/lang/Runnable;

    const-wide/16 v2, 0x1f4

    invoke-virtual {v0, v1, v2, v3}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    .line 516
    return-void
.end method

.method public SendServerCommand(Ljava/lang/String;)Z
    .locals 1
    .parameter "command"

    .prologue
    .line 561
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->tcpTask:Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;

    invoke-virtual {v0, p1}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->SendCommand(Ljava/lang/String;)Z

    move-result v0

    return v0
.end method

.method public onConfigurationChanged(Landroid/content/res/Configuration;)V
    .locals 2
    .parameter "newConfig"

    .prologue
    .line 676
    invoke-super {p0, p1}, Landroid/app/TabActivity;->onConfigurationChanged(Landroid/content/res/Configuration;)V

    .line 678
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->UIHandler:Landroid/os/Handler;

    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity;->mRecreateChildTabLayout:Ljava/lang/Runnable;

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 679
    return-void
.end method

.method public onCreate(Landroid/os/Bundle;)V
    .locals 11
    .parameter "savedInstanceState"

    .prologue
    .line 52
    invoke-super {p0, p1}, Landroid/app/TabActivity;->onCreate(Landroid/os/Bundle;)V

    .line 53
    const v6, 0x7f030002

    invoke-virtual {p0, v6}, Lcom/openvehicles/OVMS/OVMSActivity;->setContentView(I)V

    .line 56
    invoke-direct {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->loadCars()V

    .line 60
    const-string v6, "C2DM"

    const/4 v7, 0x0

    invoke-virtual {p0, v6, v7}, Lcom/openvehicles/OVMS/OVMSActivity;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v3

    .line 61
    .local v3, settings:Landroid/content/SharedPreferences;
    const-string v6, "RegID"

    const-string v7, ""

    invoke-interface {v3, v6, v7}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    .line 62
    .local v2, registrationID:Ljava/lang/String;
    invoke-virtual {v2}, Ljava/lang/String;->length()I

    move-result v6

    if-nez v6, :cond_2

    .line 63
    const-string v6, "C2DM"

    const-string v7, "Doing first time registration."

    invoke-static {v6, v7}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 66
    invoke-static {p0}, Lcom/openvehicles/OVMS/ServerCommands;->RequestC2DMRegistrationID(Landroid/content/Context;)V

    .line 75
    :goto_0
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->getResources()Landroid/content/res/Resources;

    move-result-object v6

    invoke-virtual {v6}, Landroid/content/res/Resources;->getConfiguration()Landroid/content/res/Configuration;

    move-result-object v6

    iget v6, v6, Landroid/content/res/Configuration;->screenLayout:I

    and-int/lit8 v6, v6, 0xf

    iput v6, p0, Lcom/openvehicles/OVMS/OVMSActivity;->DeviceScreenSize:I

    .line 76
    const-string v6, "window"

    invoke-virtual {p0, v6}, Lcom/openvehicles/OVMS/OVMSActivity;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v6

    check-cast v6, Landroid/view/WindowManager;

    invoke-interface {v6}, Landroid/view/WindowManager;->getDefaultDisplay()Landroid/view/Display;

    move-result-object v0

    .line 78
    .local v0, display:Landroid/view/Display;
    const-string v6, "INIT"

    const-string v7, "Screen size: %d x %d"

    const/4 v8, 0x2

    new-array v8, v8, [Ljava/lang/Object;

    const/4 v9, 0x0

    invoke-virtual {v0}, Landroid/view/Display;->getWidth()I

    move-result v10

    invoke-static {v10}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v10

    aput-object v10, v8, v9

    const/4 v9, 0x1

    invoke-virtual {v0}, Landroid/view/Display;->getHeight()I

    move-result v10

    invoke-static {v10}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v10

    aput-object v10, v8, v9

    invoke-static {v7, v8}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 80
    invoke-virtual {v0}, Landroid/view/Display;->getWidth()I

    move-result v6

    const/16 v7, 0x3d0

    if-ge v6, v7, :cond_0

    invoke-virtual {v0}, Landroid/view/Display;->getHeight()I

    move-result v6

    const/16 v7, 0x3d0

    if-lt v6, v7, :cond_1

    .line 81
    :cond_0
    const/4 v6, 0x4

    iput v6, p0, Lcom/openvehicles/OVMS/OVMSActivity;->DeviceScreenSize:I

    .line 83
    :cond_1
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->getTabHost()Landroid/widget/TabHost;

    move-result-object v5

    .line 88
    .local v5, tabHost:Landroid/widget/TabHost;
    iget v6, p0, Lcom/openvehicles/OVMS/OVMSActivity;->DeviceScreenSize:I

    const/4 v7, 0x4

    if-ne v6, v7, :cond_3

    .line 91
    new-instance v6, Landroid/content/Intent;

    invoke-direct {v6}, Landroid/content/Intent;-><init>()V

    const-class v7, Lcom/openvehicles/OVMS/TabInfo_xlarge;

    invoke-virtual {v6, p0, v7}, Landroid/content/Intent;->setClass(Landroid/content/Context;Ljava/lang/Class;)Landroid/content/Intent;

    move-result-object v1

    .line 93
    .local v1, intent:Landroid/content/Intent;
    const-string v6, "tabInfo_xlarge"

    invoke-virtual {v5, v6}, Landroid/widget/TabHost;->newTabSpec(Ljava/lang/String;)Landroid/widget/TabHost$TabSpec;

    move-result-object v4

    .line 94
    .local v4, spec:Landroid/widget/TabHost$TabSpec;
    invoke-virtual {v4, v1}, Landroid/widget/TabHost$TabSpec;->setContent(Landroid/content/Intent;)Landroid/widget/TabHost$TabSpec;

    .line 95
    const-string v6, ""

    .line 96
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->getResources()Landroid/content/res/Resources;

    move-result-object v7

    const v8, 0x7f020045

    invoke-virtual {v7, v8}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v7

    .line 95
    invoke-virtual {v4, v6, v7}, Landroid/widget/TabHost$TabSpec;->setIndicator(Ljava/lang/CharSequence;Landroid/graphics/drawable/Drawable;)Landroid/widget/TabHost$TabSpec;

    .line 97
    invoke-virtual {v5, v4}, Landroid/widget/TabHost;->addTab(Landroid/widget/TabHost$TabSpec;)V

    .line 99
    new-instance v6, Landroid/content/Intent;

    invoke-direct {v6}, Landroid/content/Intent;-><init>()V

    const-class v7, Lcom/openvehicles/OVMS/TabMap;

    invoke-virtual {v6, p0, v7}, Landroid/content/Intent;->setClass(Landroid/content/Context;Ljava/lang/Class;)Landroid/content/Intent;

    move-result-object v1

    .line 101
    const-string v6, "tabMap"

    invoke-virtual {v5, v6}, Landroid/widget/TabHost;->newTabSpec(Ljava/lang/String;)Landroid/widget/TabHost$TabSpec;

    move-result-object v4

    .line 102
    invoke-virtual {v4, v1}, Landroid/widget/TabHost$TabSpec;->setContent(Landroid/content/Intent;)Landroid/widget/TabHost$TabSpec;

    .line 103
    const-string v6, ""

    .line 104
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->getResources()Landroid/content/res/Resources;

    move-result-object v7

    const v8, 0x7f02004b

    invoke-virtual {v7, v8}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v7

    .line 103
    invoke-virtual {v4, v6, v7}, Landroid/widget/TabHost$TabSpec;->setIndicator(Ljava/lang/CharSequence;Landroid/graphics/drawable/Drawable;)Landroid/widget/TabHost$TabSpec;

    .line 105
    invoke-virtual {v5, v4}, Landroid/widget/TabHost;->addTab(Landroid/widget/TabHost$TabSpec;)V

    .line 107
    new-instance v6, Landroid/content/Intent;

    invoke-direct {v6}, Landroid/content/Intent;-><init>()V

    const-class v7, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;

    invoke-virtual {v6, p0, v7}, Landroid/content/Intent;->setClass(Landroid/content/Context;Ljava/lang/Class;)Landroid/content/Intent;

    move-result-object v1

    .line 109
    const-string v6, "tabNotifications"

    invoke-virtual {v5, v6}, Landroid/widget/TabHost;->newTabSpec(Ljava/lang/String;)Landroid/widget/TabHost$TabSpec;

    move-result-object v4

    .line 110
    invoke-virtual {v4, v1}, Landroid/widget/TabHost$TabSpec;->setContent(Landroid/content/Intent;)Landroid/widget/TabHost$TabSpec;

    .line 111
    const-string v6, ""

    .line 112
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->getResources()Landroid/content/res/Resources;

    move-result-object v7

    const v8, 0x7f020055

    invoke-virtual {v7, v8}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v7

    .line 111
    invoke-virtual {v4, v6, v7}, Landroid/widget/TabHost$TabSpec;->setIndicator(Ljava/lang/CharSequence;Landroid/graphics/drawable/Drawable;)Landroid/widget/TabHost$TabSpec;

    .line 113
    invoke-virtual {v5, v4}, Landroid/widget/TabHost;->addTab(Landroid/widget/TabHost$TabSpec;)V

    .line 115
    new-instance v6, Landroid/content/Intent;

    invoke-direct {v6}, Landroid/content/Intent;-><init>()V

    const-class v7, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;

    invoke-virtual {v6, p0, v7}, Landroid/content/Intent;->setClass(Landroid/content/Context;Ljava/lang/Class;)Landroid/content/Intent;

    move-result-object v1

    .line 117
    const-string v6, "tabDataUtilizations"

    invoke-virtual {v5, v6}, Landroid/widget/TabHost;->newTabSpec(Ljava/lang/String;)Landroid/widget/TabHost$TabSpec;

    move-result-object v4

    .line 118
    invoke-virtual {v4, v1}, Landroid/widget/TabHost$TabSpec;->setContent(Landroid/content/Intent;)Landroid/widget/TabHost$TabSpec;

    .line 119
    const-string v6, ""

    .line 120
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->getResources()Landroid/content/res/Resources;

    move-result-object v7

    const v8, 0x7f020049

    invoke-virtual {v7, v8}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v7

    .line 119
    invoke-virtual {v4, v6, v7}, Landroid/widget/TabHost$TabSpec;->setIndicator(Ljava/lang/CharSequence;Landroid/graphics/drawable/Drawable;)Landroid/widget/TabHost$TabSpec;

    .line 121
    invoke-virtual {v5, v4}, Landroid/widget/TabHost;->addTab(Landroid/widget/TabHost$TabSpec;)V

    .line 123
    new-instance v6, Landroid/content/Intent;

    invoke-direct {v6}, Landroid/content/Intent;-><init>()V

    const-class v7, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    invoke-virtual {v6, p0, v7}, Landroid/content/Intent;->setClass(Landroid/content/Context;Ljava/lang/Class;)Landroid/content/Intent;

    move-result-object v1

    .line 125
    const-string v6, "tabCarSettings"

    invoke-virtual {v5, v6}, Landroid/widget/TabHost;->newTabSpec(Ljava/lang/String;)Landroid/widget/TabHost$TabSpec;

    move-result-object v4

    .line 126
    invoke-virtual {v4, v1}, Landroid/widget/TabHost$TabSpec;->setContent(Landroid/content/Intent;)Landroid/widget/TabHost$TabSpec;

    .line 127
    const-string v6, ""

    .line 128
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->getResources()Landroid/content/res/Resources;

    move-result-object v7

    const v8, 0x7f02004e

    invoke-virtual {v7, v8}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v7

    .line 127
    invoke-virtual {v4, v6, v7}, Landroid/widget/TabHost$TabSpec;->setIndicator(Ljava/lang/CharSequence;Landroid/graphics/drawable/Drawable;)Landroid/widget/TabHost$TabSpec;

    .line 129
    invoke-virtual {v5, v4}, Landroid/widget/TabHost;->addTab(Landroid/widget/TabHost$TabSpec;)V

    .line 131
    new-instance v6, Landroid/content/Intent;

    invoke-direct {v6}, Landroid/content/Intent;-><init>()V

    const-class v7, Lcom/openvehicles/OVMS/TabCars;

    invoke-virtual {v6, p0, v7}, Landroid/content/Intent;->setClass(Landroid/content/Context;Ljava/lang/Class;)Landroid/content/Intent;

    move-result-object v1

    .line 133
    const-string v6, "tabCars"

    invoke-virtual {v5, v6}, Landroid/widget/TabHost;->newTabSpec(Ljava/lang/String;)Landroid/widget/TabHost$TabSpec;

    move-result-object v4

    .line 134
    invoke-virtual {v4, v1}, Landroid/widget/TabHost$TabSpec;->setContent(Landroid/content/Intent;)Landroid/widget/TabHost$TabSpec;

    .line 135
    const-string v6, ""

    .line 136
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->getResources()Landroid/content/res/Resources;

    move-result-object v7

    const v8, 0x7f02005a

    invoke-virtual {v7, v8}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v7

    .line 135
    invoke-virtual {v4, v6, v7}, Landroid/widget/TabHost$TabSpec;->setIndicator(Ljava/lang/CharSequence;Landroid/graphics/drawable/Drawable;)Landroid/widget/TabHost$TabSpec;

    .line 137
    invoke-virtual {v5, v4}, Landroid/widget/TabHost;->addTab(Landroid/widget/TabHost$TabSpec;)V

    .line 186
    :goto_1
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->getTabHost()Landroid/widget/TabHost;

    move-result-object v6

    invoke-virtual {v6, p0}, Landroid/widget/TabHost;->setOnTabChangedListener(Landroid/widget/TabHost$OnTabChangeListener;)V

    .line 187
    return-void

    .line 68
    .end local v0           #display:Landroid/view/Display;
    .end local v1           #intent:Landroid/content/Intent;
    .end local v4           #spec:Landroid/widget/TabHost$TabSpec;
    .end local v5           #tabHost:Landroid/widget/TabHost;
    :cond_2
    const-string v6, "C2DM"

    new-instance v7, Ljava/lang/StringBuilder;

    const-string v8, "Loaded Saved C2DM registration ID: "

    invoke-direct {v7, v8}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 69
    invoke-virtual {v7, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    .line 68
    invoke-static {v6, v7}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_0

    .line 143
    .restart local v0       #display:Landroid/view/Display;
    .restart local v5       #tabHost:Landroid/widget/TabHost;
    :cond_3
    new-instance v6, Landroid/content/Intent;

    invoke-direct {v6}, Landroid/content/Intent;-><init>()V

    const-class v7, Lcom/openvehicles/OVMS/TabInfo;

    invoke-virtual {v6, p0, v7}, Landroid/content/Intent;->setClass(Landroid/content/Context;Ljava/lang/Class;)Landroid/content/Intent;

    move-result-object v1

    .line 145
    .restart local v1       #intent:Landroid/content/Intent;
    const-string v6, "tabInfo"

    invoke-virtual {v5, v6}, Landroid/widget/TabHost;->newTabSpec(Ljava/lang/String;)Landroid/widget/TabHost$TabSpec;

    move-result-object v4

    .line 146
    .restart local v4       #spec:Landroid/widget/TabHost$TabSpec;
    invoke-virtual {v4, v1}, Landroid/widget/TabHost$TabSpec;->setContent(Landroid/content/Intent;)Landroid/widget/TabHost$TabSpec;

    .line 147
    const-string v6, ""

    .line 148
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->getResources()Landroid/content/res/Resources;

    move-result-object v7

    const v8, 0x7f020045

    invoke-virtual {v7, v8}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v7

    .line 147
    invoke-virtual {v4, v6, v7}, Landroid/widget/TabHost$TabSpec;->setIndicator(Ljava/lang/CharSequence;Landroid/graphics/drawable/Drawable;)Landroid/widget/TabHost$TabSpec;

    .line 149
    invoke-virtual {v5, v4}, Landroid/widget/TabHost;->addTab(Landroid/widget/TabHost$TabSpec;)V

    .line 151
    new-instance v6, Landroid/content/Intent;

    invoke-direct {v6}, Landroid/content/Intent;-><init>()V

    const-class v7, Lcom/openvehicles/OVMS/TabCar;

    invoke-virtual {v6, p0, v7}, Landroid/content/Intent;->setClass(Landroid/content/Context;Ljava/lang/Class;)Landroid/content/Intent;

    move-result-object v1

    .line 153
    const-string v6, "tabCar"

    invoke-virtual {v5, v6}, Landroid/widget/TabHost;->newTabSpec(Ljava/lang/String;)Landroid/widget/TabHost$TabSpec;

    move-result-object v4

    .line 154
    invoke-virtual {v4, v1}, Landroid/widget/TabHost$TabSpec;->setContent(Landroid/content/Intent;)Landroid/widget/TabHost$TabSpec;

    .line 155
    const-string v6, ""

    .line 156
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->getResources()Landroid/content/res/Resources;

    move-result-object v7

    const v8, 0x7f020046

    invoke-virtual {v7, v8}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v7

    .line 155
    invoke-virtual {v4, v6, v7}, Landroid/widget/TabHost$TabSpec;->setIndicator(Ljava/lang/CharSequence;Landroid/graphics/drawable/Drawable;)Landroid/widget/TabHost$TabSpec;

    .line 157
    invoke-virtual {v5, v4}, Landroid/widget/TabHost;->addTab(Landroid/widget/TabHost$TabSpec;)V

    .line 159
    new-instance v6, Landroid/content/Intent;

    invoke-direct {v6}, Landroid/content/Intent;-><init>()V

    const-class v7, Lcom/openvehicles/OVMS/TabMap;

    invoke-virtual {v6, p0, v7}, Landroid/content/Intent;->setClass(Landroid/content/Context;Ljava/lang/Class;)Landroid/content/Intent;

    move-result-object v1

    .line 161
    const-string v6, "tabMap"

    invoke-virtual {v5, v6}, Landroid/widget/TabHost;->newTabSpec(Ljava/lang/String;)Landroid/widget/TabHost$TabSpec;

    move-result-object v4

    .line 162
    invoke-virtual {v4, v1}, Landroid/widget/TabHost$TabSpec;->setContent(Landroid/content/Intent;)Landroid/widget/TabHost$TabSpec;

    .line 163
    const-string v6, ""

    .line 164
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->getResources()Landroid/content/res/Resources;

    move-result-object v7

    const v8, 0x7f02004b

    invoke-virtual {v7, v8}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v7

    .line 163
    invoke-virtual {v4, v6, v7}, Landroid/widget/TabHost$TabSpec;->setIndicator(Ljava/lang/CharSequence;Landroid/graphics/drawable/Drawable;)Landroid/widget/TabHost$TabSpec;

    .line 165
    invoke-virtual {v5, v4}, Landroid/widget/TabHost;->addTab(Landroid/widget/TabHost$TabSpec;)V

    .line 167
    new-instance v6, Landroid/content/Intent;

    invoke-direct {v6}, Landroid/content/Intent;-><init>()V

    const-class v7, Lcom/openvehicles/OVMS/TabMiscFeatures;

    invoke-virtual {v6, p0, v7}, Landroid/content/Intent;->setClass(Landroid/content/Context;Ljava/lang/Class;)Landroid/content/Intent;

    move-result-object v1

    .line 169
    const-string v6, "tabMiscFeatures"

    invoke-virtual {v5, v6}, Landroid/widget/TabHost;->newTabSpec(Ljava/lang/String;)Landroid/widget/TabHost$TabSpec;

    move-result-object v4

    .line 170
    invoke-virtual {v4, v1}, Landroid/widget/TabHost$TabSpec;->setContent(Landroid/content/Intent;)Landroid/widget/TabHost$TabSpec;

    .line 171
    const-string v6, ""

    .line 172
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->getResources()Landroid/content/res/Resources;

    move-result-object v7

    const v8, 0x7f020054

    invoke-virtual {v7, v8}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v7

    .line 171
    invoke-virtual {v4, v6, v7}, Landroid/widget/TabHost$TabSpec;->setIndicator(Ljava/lang/CharSequence;Landroid/graphics/drawable/Drawable;)Landroid/widget/TabHost$TabSpec;

    .line 173
    invoke-virtual {v5, v4}, Landroid/widget/TabHost;->addTab(Landroid/widget/TabHost$TabSpec;)V

    .line 175
    new-instance v6, Landroid/content/Intent;

    invoke-direct {v6}, Landroid/content/Intent;-><init>()V

    const-class v7, Lcom/openvehicles/OVMS/TabCars;

    invoke-virtual {v6, p0, v7}, Landroid/content/Intent;->setClass(Landroid/content/Context;Ljava/lang/Class;)Landroid/content/Intent;

    move-result-object v1

    .line 177
    const-string v6, "tabCars"

    invoke-virtual {v5, v6}, Landroid/widget/TabHost;->newTabSpec(Ljava/lang/String;)Landroid/widget/TabHost$TabSpec;

    move-result-object v4

    .line 178
    invoke-virtual {v4, v1}, Landroid/widget/TabHost$TabSpec;->setContent(Landroid/content/Intent;)Landroid/widget/TabHost$TabSpec;

    .line 179
    const-string v6, ""

    .line 180
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->getResources()Landroid/content/res/Resources;

    move-result-object v7

    const v8, 0x7f02005a

    invoke-virtual {v7, v8}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v7

    .line 179
    invoke-virtual {v4, v6, v7}, Landroid/widget/TabHost$TabSpec;->setIndicator(Ljava/lang/CharSequence;Landroid/graphics/drawable/Drawable;)Landroid/widget/TabHost$TabSpec;

    .line 181
    invoke-virtual {v5, v4}, Landroid/widget/TabHost;->addTab(Landroid/widget/TabHost$TabSpec;)V

    goto/16 :goto_1
.end method

.method public onCreateOptionsMenu(Landroid/view/Menu;)Z
    .locals 2
    .parameter "menu"

    .prologue
    .line 225
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->getMenuInflater()Landroid/view/MenuInflater;

    move-result-object v0

    .line 226
    .local v0, inflater:Landroid/view/MenuInflater;
    const v1, 0x7f030003

    invoke-virtual {v0, v1, p1}, Landroid/view/MenuInflater;->inflate(ILandroid/view/Menu;)V

    .line 227
    const/4 v1, 0x1

    return v1
.end method

.method protected onDestory()V
    .locals 2

    .prologue
    .line 682
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->tcpTask:Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;

    if-eqz v0, :cond_0

    .line 683
    const-string v0, "TCP"

    const-string v1, "Shutting down TCP connection (OnDestroy())"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 684
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->tcpTask:Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;

    invoke-virtual {v0}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->ConnClose()V

    .line 685
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->tcpTask:Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;

    .line 687
    :cond_0
    return-void
.end method

.method public onNewIntent(Landroid/content/Intent;)V
    .locals 6
    .parameter "launchingIntent"

    .prologue
    .line 191
    const-string v3, "EVENT"

    const-string v4, "onNewIntent"

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 192
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->getTabHost()Landroid/widget/TabHost;

    move-result-object v2

    .line 193
    .local v2, tabHost:Landroid/widget/TabHost;
    if-eqz p1, :cond_1

    .line 194
    const-string v3, "VehicleID"

    invoke-virtual {p1, v3}, Landroid/content/Intent;->hasExtra(Ljava/lang/String;)Z

    move-result v3

    if-eqz v3, :cond_4

    .line 195
    const/4 v1, 0x0

    .line 196
    .local v1, newCar:Lcom/openvehicles/OVMS/CarData;
    iget-object v3, p0, Lcom/openvehicles/OVMS/OVMSActivity;->allSavedCars:Ljava/util/ArrayList;

    invoke-virtual {v3}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v3

    :cond_0
    invoke-interface {v3}, Ljava/util/Iterator;->hasNext()Z

    move-result v4

    if-nez v4, :cond_2

    .line 204
    :goto_0
    if-eqz v1, :cond_1

    .line 205
    const-string v3, "EVENT"

    new-instance v4, Ljava/lang/StringBuilder;

    const-string v5, "Launching with default car set to: "

    invoke-direct {v4, v5}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 206
    iget-object v5, v1, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    .line 205
    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 208
    const-string v3, "SetTab"

    invoke-virtual {p1, v3}, Landroid/content/Intent;->hasExtra(Ljava/lang/String;)Z

    move-result v3

    if-eqz v3, :cond_3

    .line 210
    const-string v3, "SetTab"

    invoke-virtual {p1, v3}, Landroid/content/Intent;->getStringExtra(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    .line 209
    invoke-virtual {p0, v1, v3}, Lcom/openvehicles/OVMS/OVMSActivity;->ChangeCar(Lcom/openvehicles/OVMS/CarData;Ljava/lang/String;)V

    .line 221
    .end local v1           #newCar:Lcom/openvehicles/OVMS/CarData;
    :cond_1
    :goto_1
    return-void

    .line 196
    .restart local v1       #newCar:Lcom/openvehicles/OVMS/CarData;
    :cond_2
    invoke-interface {v3}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/openvehicles/OVMS/CarData;

    .line 197
    .local v0, car:Lcom/openvehicles/OVMS/CarData;
    iget-object v4, v0, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    .line 198
    const-string v5, "VehicleID"

    invoke-virtual {p1, v5}, Landroid/content/Intent;->getStringExtra(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v5

    .line 197
    invoke-virtual {v4, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_0

    .line 199
    move-object v1, v0

    .line 200
    goto :goto_0

    .line 212
    .end local v0           #car:Lcom/openvehicles/OVMS/CarData;
    :cond_3
    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/OVMSActivity;->ChangeCar(Lcom/openvehicles/OVMS/CarData;)V

    goto :goto_1

    .line 215
    .end local v1           #newCar:Lcom/openvehicles/OVMS/CarData;
    :cond_4
    const-string v3, "SetTab"

    invoke-virtual {p1, v3}, Landroid/content/Intent;->hasExtra(Ljava/lang/String;)Z

    move-result v3

    if-eqz v3, :cond_5

    .line 217
    const-string v3, "SetTab"

    invoke-virtual {p1, v3}, Landroid/content/Intent;->getStringExtra(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    .line 216
    invoke-virtual {v2, v3}, Landroid/widget/TabHost;->setCurrentTabByTag(Ljava/lang/String;)V

    goto :goto_1

    .line 219
    :cond_5
    const-string v3, "tabInfo"

    invoke-virtual {v2, v3}, Landroid/widget/TabHost;->setCurrentTabByTag(Ljava/lang/String;)V

    goto :goto_1
.end method

.method public onOptionsItemSelected(Landroid/view/MenuItem;)Z
    .locals 3
    .parameter "item"

    .prologue
    const/4 v1, 0x1

    .line 233
    invoke-interface {p1}, Landroid/view/MenuItem;->getItemId()I

    move-result v2

    packed-switch v2, :pswitch_data_0

    .line 244
    invoke-super {p0, p1}, Landroid/app/TabActivity;->onOptionsItemSelected(Landroid/view/MenuItem;)Z

    move-result v1

    :goto_0
    return v1

    .line 235
    :pswitch_0
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->finish()V

    goto :goto_0

    .line 238
    :pswitch_1
    new-instance v0, Lcom/openvehicles/OVMS/OVMSNotifications;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/OVMSNotifications;-><init>(Landroid/content/Context;)V

    .line 239
    .local v0, notifications:Lcom/openvehicles/OVMS/OVMSNotifications;
    invoke-virtual {v0}, Lcom/openvehicles/OVMS/OVMSNotifications;->Clear()V

    .line 240
    invoke-virtual {v0}, Lcom/openvehicles/OVMS/OVMSNotifications;->Save()V

    .line 241
    invoke-direct {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->notifyTabRefresh()V

    goto :goto_0

    .line 233
    :pswitch_data_0
    .packed-switch 0x7f09000a
        :pswitch_0
        :pswitch_1
    .end packed-switch
.end method

.method protected onPause()V
    .locals 2

    .prologue
    .line 657
    invoke-super {p0}, Landroid/app/TabActivity;->onPause()V

    .line 661
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->tcpTask:Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;

    if-eqz v0, :cond_0

    .line 662
    const-string v0, "TCP"

    const-string v1, "Shutting down TCP connection (OnPause())"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 663
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->tcpTask:Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;

    invoke-virtual {v0}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->ConnClose()V

    .line 664
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->tcpTask:Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;

    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->cancel(Z)Z

    .line 665
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->tcpTask:Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;

    .line 668
    :cond_0
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->saveCars()V

    .line 671
    invoke-static {p0}, Lcom/openvehicles/OVMS/OVMSWidgets;->UpdateWidgets(Landroid/content/Context;)V

    .line 672
    return-void
.end method

.method protected onResume()V
    .locals 3

    .prologue
    .line 644
    invoke-super {p0}, Landroid/app/TabActivity;->onResume()V

    .line 647
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity;->tcpTask:Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;

    if-nez v1, :cond_0

    .line 649
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity;->UIHandler:Landroid/os/Handler;

    iget-object v2, p0, Lcom/openvehicles/OVMS/OVMSActivity;->progressLoginCloseDialog:Ljava/lang/Runnable;

    invoke-virtual {v1, v2}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 650
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/OVMSActivity;->getTabHost()Landroid/widget/TabHost;

    move-result-object v1

    invoke-virtual {v1}, Landroid/widget/TabHost;->getCurrentTabTag()Ljava/lang/String;

    move-result-object v0

    .line 651
    .local v0, currentTabTag:Ljava/lang/String;
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;

    invoke-virtual {p0, v1, v0}, Lcom/openvehicles/OVMS/OVMSActivity;->ChangeCar(Lcom/openvehicles/OVMS/CarData;Ljava/lang/String;)V

    .line 653
    .end local v0           #currentTabTag:Ljava/lang/String;
    :cond_0
    return-void
.end method

.method public onTabChanged(Ljava/lang/String;)V
    .locals 2
    .parameter "currentActivityId"

    .prologue
    .line 567
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity;->UIHandler:Landroid/os/Handler;

    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity;->mRefresh:Ljava/lang/Runnable;

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 568
    return-void
.end method

.method public saveCars()V
    .locals 6

    .prologue
    .line 769
    :try_start_0
    const-string v4, "OVMS"

    const-string v5, "Saving cars to interal storage..."

    invoke-static {v4, v5}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 772
    const-string v4, "OVMS"

    const/4 v5, 0x0

    invoke-virtual {p0, v4, v5}, Lcom/openvehicles/OVMS/OVMSActivity;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v4

    invoke-interface {v4}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v1

    .line 773
    .local v1, editor:Landroid/content/SharedPreferences$Editor;
    const-string v4, "LastVehicleID"

    iget-object v5, p0, Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;

    iget-object v5, v5, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    invoke-interface {v1, v4, v5}, Landroid/content/SharedPreferences$Editor;->putString(Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    .line 774
    invoke-interface {v1}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 776
    const-string v4, "OVMSSavedCars.obj"

    .line 777
    const/4 v5, 0x0

    .line 776
    invoke-virtual {p0, v4, v5}, Lcom/openvehicles/OVMS/OVMSActivity;->openFileOutput(Ljava/lang/String;I)Ljava/io/FileOutputStream;

    move-result-object v2

    .line 778
    .local v2, fos:Ljava/io/FileOutputStream;
    new-instance v3, Ljava/io/ObjectOutputStream;

    invoke-direct {v3, v2}, Ljava/io/ObjectOutputStream;-><init>(Ljava/io/OutputStream;)V

    .line 779
    .local v3, os:Ljava/io/ObjectOutputStream;
    iget-object v4, p0, Lcom/openvehicles/OVMS/OVMSActivity;->allSavedCars:Ljava/util/ArrayList;

    invoke-virtual {v3, v4}, Ljava/io/ObjectOutputStream;->writeObject(Ljava/lang/Object;)V

    .line 780
    invoke-virtual {v3}, Ljava/io/ObjectOutputStream;->close()V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 784
    .end local v1           #editor:Landroid/content/SharedPreferences$Editor;
    .end local v2           #fos:Ljava/io/FileOutputStream;
    .end local v3           #os:Ljava/io/ObjectOutputStream;
    :goto_0
    return-void

    .line 781
    :catch_0
    move-exception v0

    .line 782
    .local v0, e:Ljava/lang/Exception;
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_0
.end method
