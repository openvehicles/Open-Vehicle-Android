.class public Lcom/openvehicles/OVMS/TabInfo_xlarge;
.super Lcom/google/android/maps/MapActivity;
.source "TabInfo_xlarge.java"


# instance fields
.field private final CAR_MARKER_ANIMATION_DURATION_MS:I

.field private final CAR_MARKER_ANIMATION_FRAMES:I

.field public CurrentScreenOrientation:I

.field private DirectionalMarker:Landroid/graphics/Bitmap;

.field private final LABEL_SHADOW_XY:I

.field private final LABEL_TEXT_SIZE:I

.field private animateCarMarker:Ljava/lang/Runnable;

.field private carMarkerAnimationFrame:I

.field private carMarkerAnimationTimerHandler:Landroid/os/Handler;

.field private carMarkers:Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;

.field private currentVehicleID:Ljava/lang/String;

.field private data:Lcom/openvehicles/OVMS/CarData;

.field private downloadProgress:Landroid/app/ProgressDialog;

.field private downloadTask:Lcom/openvehicles/OVMS/ServerCommands$CarLayoutDownloader;

.field private handler:Landroid/os/Handler;

.field private isLoggedIn:Z

.field private lastCarGeoPoint:Lcom/google/android/maps/GeoPoint;

.field private lastUpdateTimer:Ljava/lang/Runnable;

.field private lastUpdateTimerHandler:Landroid/os/Handler;

.field private lastUpdatedDialog:Landroid/app/AlertDialog;

.field private mapOverlays:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List",
            "<",
            "Lcom/google/android/maps/Overlay;",
            ">;"
        }
    .end annotation
.end field

.field private mapView:Lcom/google/android/maps/MapView;

.field private mc:Lcom/google/android/maps/MapController;

.field private orientationChangedHandler:Landroid/os/Handler;

.field private softwareInformation:Landroid/app/AlertDialog;


# direct methods
.method public constructor <init>()V
    .locals 1

    .prologue
    .line 41
    invoke-direct {p0}, Lcom/google/android/maps/MapActivity;-><init>()V

    .line 299
    new-instance v0, Landroid/os/Handler;

    invoke-direct {v0}, Landroid/os/Handler;-><init>()V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->carMarkerAnimationTimerHandler:Landroid/os/Handler;

    .line 300
    const/4 v0, 0x0

    iput v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->carMarkerAnimationFrame:I

    .line 302
    const/16 v0, 0x7d0

    iput v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->CAR_MARKER_ANIMATION_DURATION_MS:I

    .line 303
    const/16 v0, 0x28

    iput v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->CAR_MARKER_ANIMATION_FRAMES:I

    .line 305
    const/16 v0, 0x14

    iput v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->LABEL_TEXT_SIZE:I

    .line 306
    const/4 v0, 0x1

    iput v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->LABEL_SHADOW_XY:I

    .line 311
    new-instance v0, Landroid/os/Handler;

    invoke-direct {v0}, Landroid/os/Handler;-><init>()V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->lastUpdateTimerHandler:Landroid/os/Handler;

    .line 312
    new-instance v0, Lcom/openvehicles/OVMS/TabInfo_xlarge$1;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge$1;-><init>(Lcom/openvehicles/OVMS/TabInfo_xlarge;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->lastUpdateTimer:Ljava/lang/Runnable;

    .line 425
    new-instance v0, Lcom/openvehicles/OVMS/TabInfo_xlarge$2;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge$2;-><init>(Lcom/openvehicles/OVMS/TabInfo_xlarge;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->handler:Landroid/os/Handler;

    .line 872
    new-instance v0, Lcom/openvehicles/OVMS/TabInfo_xlarge$3;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge$3;-><init>(Lcom/openvehicles/OVMS/TabInfo_xlarge;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->animateCarMarker:Ljava/lang/Runnable;

    .line 932
    new-instance v0, Lcom/openvehicles/OVMS/TabInfo_xlarge$4;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge$4;-><init>(Lcom/openvehicles/OVMS/TabInfo_xlarge;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->orientationChangedHandler:Landroid/os/Handler;

    .line 41
    return-void
.end method

.method static synthetic access$0(Lcom/openvehicles/OVMS/TabInfo_xlarge;)V
    .locals 0
    .parameter

    .prologue
    .line 357
    invoke-direct {p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->updateLastUpdatedView()V

    return-void
.end method

.method static synthetic access$1(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Landroid/os/Handler;
    .locals 1
    .parameter

    .prologue
    .line 311
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->lastUpdateTimerHandler:Landroid/os/Handler;

    return-object v0
.end method

.method static synthetic access$10(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Lcom/google/android/maps/MapView;
    .locals 1
    .parameter

    .prologue
    .line 296
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->mapView:Lcom/google/android/maps/MapView;

    return-object v0
.end method

.method static synthetic access$11(Lcom/openvehicles/OVMS/TabInfo_xlarge;I)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 300
    iput p1, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->carMarkerAnimationFrame:I

    return-void
.end method

.method static synthetic access$12(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Landroid/os/Handler;
    .locals 1
    .parameter

    .prologue
    .line 299
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->carMarkerAnimationTimerHandler:Landroid/os/Handler;

    return-object v0
.end method

.method static synthetic access$13(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Ljava/lang/Runnable;
    .locals 1
    .parameter

    .prologue
    .line 872
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->animateCarMarker:Ljava/lang/Runnable;

    return-object v0
.end method

.method static synthetic access$14(Lcom/openvehicles/OVMS/TabInfo_xlarge;Landroid/app/AlertDialog;)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 289
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->lastUpdatedDialog:Landroid/app/AlertDialog;

    return-void
.end method

.method static synthetic access$15(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Landroid/app/AlertDialog;
    .locals 1
    .parameter

    .prologue
    .line 289
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->lastUpdatedDialog:Landroid/app/AlertDialog;

    return-object v0
.end method

.method static synthetic access$16(Lcom/openvehicles/OVMS/TabInfo_xlarge;)V
    .locals 0
    .parameter

    .prologue
    .line 769
    invoke-direct {p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->downloadLayout()V

    return-void
.end method

.method static synthetic access$17(Lcom/openvehicles/OVMS/TabInfo_xlarge;Landroid/app/AlertDialog;)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 288
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->softwareInformation:Landroid/app/AlertDialog;

    return-void
.end method

.method static synthetic access$18(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Landroid/app/AlertDialog;
    .locals 1
    .parameter

    .prologue
    .line 288
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->softwareInformation:Landroid/app/AlertDialog;

    return-object v0
.end method

.method static synthetic access$2(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Ljava/lang/Runnable;
    .locals 1
    .parameter

    .prologue
    .line 312
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->lastUpdateTimer:Ljava/lang/Runnable;

    return-object v0
.end method

.method static synthetic access$3(Lcom/openvehicles/OVMS/TabInfo_xlarge;)V
    .locals 0
    .parameter

    .prologue
    .line 596
    invoke-direct {p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->updateInfoUI()V

    return-void
.end method

.method static synthetic access$4(Lcom/openvehicles/OVMS/TabInfo_xlarge;)V
    .locals 0
    .parameter

    .prologue
    .line 435
    invoke-direct {p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->updateCarLayoutUI()V

    return-void
.end method

.method static synthetic access$5(Lcom/openvehicles/OVMS/TabInfo_xlarge;)V
    .locals 0
    .parameter

    .prologue
    .line 810
    invoke-direct {p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->updateMapUI()V

    return-void
.end method

.method static synthetic access$6(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Lcom/openvehicles/OVMS/CarData;
    .locals 1
    .parameter

    .prologue
    .line 286
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    return-object v0
.end method

.method static synthetic access$7(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Lcom/google/android/maps/GeoPoint;
    .locals 1
    .parameter

    .prologue
    .line 301
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->lastCarGeoPoint:Lcom/google/android/maps/GeoPoint;

    return-object v0
.end method

.method static synthetic access$8(Lcom/openvehicles/OVMS/TabInfo_xlarge;)I
    .locals 1
    .parameter

    .prologue
    .line 300
    iget v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->carMarkerAnimationFrame:I

    return v0
.end method

.method static synthetic access$9(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;
    .locals 1
    .parameter

    .prologue
    .line 295
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->carMarkers:Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;

    return-object v0
.end method

.method private downloadLayout()V
    .locals 5

    .prologue
    const/4 v4, 0x1

    .line 770
    new-instance v0, Landroid/app/ProgressDialog;

    invoke-direct {v0, p0}, Landroid/app/ProgressDialog;-><init>(Landroid/content/Context;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->downloadProgress:Landroid/app/ProgressDialog;

    .line 771
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->downloadProgress:Landroid/app/ProgressDialog;

    const-string v1, "Downloading Hi-Res Graphics"

    invoke-virtual {v0, v1}, Landroid/app/ProgressDialog;->setMessage(Ljava/lang/CharSequence;)V

    .line 772
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->downloadProgress:Landroid/app/ProgressDialog;

    invoke-virtual {v0, v4}, Landroid/app/ProgressDialog;->setIndeterminate(Z)V

    .line 773
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->downloadProgress:Landroid/app/ProgressDialog;

    const/16 v1, 0x64

    invoke-virtual {v0, v1}, Landroid/app/ProgressDialog;->setMax(I)V

    .line 774
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->downloadProgress:Landroid/app/ProgressDialog;

    invoke-virtual {v0, v4}, Landroid/app/ProgressDialog;->setCancelable(Z)V

    .line 775
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->downloadProgress:Landroid/app/ProgressDialog;

    invoke-virtual {v0, v4}, Landroid/app/ProgressDialog;->setProgressStyle(I)V

    .line 776
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->downloadProgress:Landroid/app/ProgressDialog;

    invoke-virtual {v0}, Landroid/app/ProgressDialog;->show()V

    .line 778
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->downloadProgress:Landroid/app/ProgressDialog;

    new-instance v1, Lcom/openvehicles/OVMS/TabInfo_xlarge$19;

    invoke-direct {v1, p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge$19;-><init>(Lcom/openvehicles/OVMS/TabInfo_xlarge;)V

    invoke-virtual {v0, v1}, Landroid/app/ProgressDialog;->setOnDismissListener(Landroid/content/DialogInterface$OnDismissListener;)V

    .line 805
    new-instance v0, Lcom/openvehicles/OVMS/ServerCommands$CarLayoutDownloader;

    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->downloadProgress:Landroid/app/ProgressDialog;

    invoke-direct {v0, v1}, Lcom/openvehicles/OVMS/ServerCommands$CarLayoutDownloader;-><init>(Landroid/app/ProgressDialog;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->downloadTask:Lcom/openvehicles/OVMS/ServerCommands$CarLayoutDownloader;

    .line 806
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->downloadTask:Lcom/openvehicles/OVMS/ServerCommands$CarLayoutDownloader;

    const/4 v1, 0x2

    new-array v1, v1, [Ljava/lang/String;

    const/4 v2, 0x0

    iget-object v3, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v3, v3, Lcom/openvehicles/OVMS/CarData;->VehicleImageDrawable:Ljava/lang/String;

    aput-object v3, v1, v2

    .line 807
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->getCacheDir()Ljava/io/File;

    move-result-object v2

    invoke-virtual {v2}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v2

    aput-object v2, v1, v4

    .line 806
    invoke-virtual {v0, v1}, Lcom/openvehicles/OVMS/ServerCommands$CarLayoutDownloader;->execute([Ljava/lang/Object;)Landroid/os/AsyncTask;

    .line 808
    return-void
.end method

.method private initCarLayoutUI()V
    .locals 4

    .prologue
    .line 218
    const v3, 0x7f090057

    invoke-virtual {p0, v3}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/widget/TextView;

    .line 219
    .local v2, tv:Landroid/widget/TextView;
    new-instance v3, Lcom/openvehicles/OVMS/TabInfo_xlarge$10;

    invoke-direct {v3, p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge$10;-><init>(Lcom/openvehicles/OVMS/TabInfo_xlarge;)V

    invoke-virtual {v2, v3}, Landroid/widget/TextView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 243
    const v3, 0x7f09007a

    invoke-virtual {p0, v3}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/FrameLayout;

    .line 244
    .local v0, hotspot:Landroid/widget/FrameLayout;
    new-instance v3, Lcom/openvehicles/OVMS/TabInfo_xlarge$11;

    invoke-direct {v3, p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge$11;-><init>(Lcom/openvehicles/OVMS/TabInfo_xlarge;)V

    invoke-virtual {v0, v3}, Landroid/widget/FrameLayout;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 257
    const v3, 0x7f09007b

    invoke-virtual {p0, v3}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v0

    .end local v0           #hotspot:Landroid/widget/FrameLayout;
    check-cast v0, Landroid/widget/FrameLayout;

    .line 258
    .restart local v0       #hotspot:Landroid/widget/FrameLayout;
    new-instance v3, Lcom/openvehicles/OVMS/TabInfo_xlarge$12;

    invoke-direct {v3, p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge$12;-><init>(Lcom/openvehicles/OVMS/TabInfo_xlarge;)V

    invoke-virtual {v0, v3}, Landroid/widget/FrameLayout;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 271
    const v3, 0x7f09007c

    invoke-virtual {p0, v3}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/LinearLayout;

    .line 272
    .local v1, temperatures:Landroid/widget/LinearLayout;
    new-instance v3, Lcom/openvehicles/OVMS/TabInfo_xlarge$13;

    invoke-direct {v3, p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge$13;-><init>(Lcom/openvehicles/OVMS/TabInfo_xlarge;)V

    invoke-virtual {v1, v3}, Landroid/widget/LinearLayout;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 284
    return-void
.end method

.method private initInfoUI()V
    .locals 4

    .prologue
    .line 55
    const-string v1, "-"

    .line 57
    .local v1, lastReportDate:Ljava/lang/String;
    const v3, 0x7f090057

    invoke-virtual {p0, v3}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/widget/TextView;

    .line 58
    .local v2, tv:Landroid/widget/TextView;
    new-instance v3, Lcom/openvehicles/OVMS/TabInfo_xlarge$5;

    invoke-direct {v3, p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge$5;-><init>(Lcom/openvehicles/OVMS/TabInfo_xlarge;)V

    invoke-virtual {v2, v3}, Landroid/widget/TextView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 85
    const v3, 0x7f090054

    invoke-virtual {p0, v3}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v2

    .end local v2           #tv:Landroid/widget/TextView;
    check-cast v2, Landroid/widget/TextView;

    .line 86
    .restart local v2       #tv:Landroid/widget/TextView;
    new-instance v3, Lcom/openvehicles/OVMS/TabInfo_xlarge$6;

    invoke-direct {v3, p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge$6;-><init>(Lcom/openvehicles/OVMS/TabInfo_xlarge;)V

    invoke-virtual {v2, v3}, Landroid/widget/TextView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 113
    const v3, 0x7f09005f

    invoke-virtual {p0, v3}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v2

    .end local v2           #tv:Landroid/widget/TextView;
    check-cast v2, Landroid/widget/TextView;

    .line 114
    .restart local v2       #tv:Landroid/widget/TextView;
    new-instance v3, Lcom/openvehicles/OVMS/TabInfo_xlarge$7;

    invoke-direct {v3, p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge$7;-><init>(Lcom/openvehicles/OVMS/TabInfo_xlarge;)V

    invoke-virtual {v2, v3}, Landroid/widget/TextView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 123
    const v3, 0x7f090064

    invoke-virtual {p0, v3}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v2

    .end local v2           #tv:Landroid/widget/TextView;
    check-cast v2, Landroid/widget/TextView;

    .line 124
    .restart local v2       #tv:Landroid/widget/TextView;
    new-instance v3, Lcom/openvehicles/OVMS/TabInfo_xlarge$8;

    invoke-direct {v3, p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge$8;-><init>(Lcom/openvehicles/OVMS/TabInfo_xlarge;)V

    invoke-virtual {v2, v3}, Landroid/widget/TextView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 134
    const v3, 0x7f090060

    invoke-virtual {p0, v3}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/SeekBar;

    .line 135
    .local v0, bar:Landroid/widget/SeekBar;
    new-instance v3, Lcom/openvehicles/OVMS/TabInfo_xlarge$9;

    invoke-direct {v3, p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge$9;-><init>(Lcom/openvehicles/OVMS/TabInfo_xlarge;)V

    invoke-virtual {v0, v3}, Landroid/widget/SeekBar;->setOnSeekBarChangeListener(Landroid/widget/SeekBar$OnSeekBarChangeListener;)V

    .line 195
    return-void
.end method

.method private initMapUI()V
    .locals 6

    .prologue
    const/4 v5, 0x1

    .line 199
    const v0, 0x7f090081

    invoke-virtual {p0, v0}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Lcom/google/android/maps/MapView;

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->mapView:Lcom/google/android/maps/MapView;

    .line 200
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->mapView:Lcom/google/android/maps/MapView;

    invoke-virtual {v0}, Lcom/google/android/maps/MapView;->getController()Lcom/google/android/maps/MapController;

    move-result-object v0

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->mc:Lcom/google/android/maps/MapController;

    .line 201
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->mapView:Lcom/google/android/maps/MapView;

    invoke-virtual {v0, v5}, Lcom/google/android/maps/MapView;->setBuiltInZoomControls(Z)V

    .line 204
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    .line 205
    const v2, 0x7f020008

    .line 204
    invoke-static {v0, v2}, Landroid/graphics/BitmapFactory;->decodeResource(Landroid/content/res/Resources;I)Landroid/graphics/Bitmap;

    move-result-object v0

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->DirectionalMarker:Landroid/graphics/Bitmap;

    .line 208
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->mapView:Lcom/google/android/maps/MapView;

    invoke-virtual {v0}, Lcom/google/android/maps/MapView;->getOverlays()Ljava/util/List;

    move-result-object v0

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->mapOverlays:Ljava/util/List;

    .line 209
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    .line 210
    const v2, 0x7f02001e

    .line 209
    invoke-virtual {v0, v2}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    .line 211
    .local v1, drawable:Landroid/graphics/drawable/Drawable;
    new-instance v0, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;

    const/16 v2, 0x14

    .line 212
    iget-object v4, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->DirectionalMarker:Landroid/graphics/Bitmap;

    move-object v3, p0

    invoke-direct/range {v0 .. v5}, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;-><init>(Landroid/graphics/drawable/Drawable;ILandroid/content/Context;Landroid/graphics/Bitmap;I)V

    .line 211
    iput-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->carMarkers:Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;

    .line 214
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->mapOverlays:Ljava/util/List;

    const/4 v2, 0x0

    iget-object v3, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->carMarkers:Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;

    invoke-interface {v0, v2, v3}, Ljava/util/List;->add(ILjava/lang/Object;)V

    .line 215
    return-void
.end method

.method private updateCarLayoutUI()V
    .locals 12

    .prologue
    .line 437
    const v6, 0x7f090074

    invoke-virtual {p0, v6}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v5

    check-cast v5, Landroid/widget/TextView;

    .line 438
    .local v5, tv:Landroid/widget/TextView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_LeftDoorOpen:Z

    if-eqz v6, :cond_9

    const/4 v6, 0x0

    :goto_0
    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setVisibility(I)V

    .line 439
    const v6, 0x7f090075

    invoke-virtual {p0, v6}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v5

    .end local v5           #tv:Landroid/widget/TextView;
    check-cast v5, Landroid/widget/TextView;

    .line 440
    .restart local v5       #tv:Landroid/widget/TextView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_RightDoorOpen:Z

    if-eqz v6, :cond_a

    const/4 v6, 0x0

    :goto_1
    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setVisibility(I)V

    .line 442
    const v6, 0x7f090076

    invoke-virtual {p0, v6}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v5

    .end local v5           #tv:Landroid/widget/TextView;
    check-cast v5, Landroid/widget/TextView;

    .line 443
    .restart local v5       #tv:Landroid/widget/TextView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_ChargePortOpen:Z

    if-eqz v6, :cond_b

    const/4 v6, 0x0

    :goto_2
    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setVisibility(I)V

    .line 445
    const v6, 0x7f090078

    invoke-virtual {p0, v6}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v5

    .end local v5           #tv:Landroid/widget/TextView;
    check-cast v5, Landroid/widget/TextView;

    .line 446
    .restart local v5       #tv:Landroid/widget/TextView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_BonnetOpen:Z

    if-eqz v6, :cond_c

    const/4 v6, 0x0

    :goto_3
    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setVisibility(I)V

    .line 447
    const v6, 0x7f090077

    invoke-virtual {p0, v6}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v5

    .end local v5           #tv:Landroid/widget/TextView;
    check-cast v5, Landroid/widget/TextView;

    .line 448
    .restart local v5       #tv:Landroid/widget/TextView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_TrunkOpen:Z

    if-eqz v6, :cond_d

    const/4 v6, 0x0

    :goto_4
    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setVisibility(I)V

    .line 450
    const v6, 0x7f090079

    invoke-virtual {p0, v6}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v5

    .end local v5           #tv:Landroid/widget/TextView;
    check-cast v5, Landroid/widget/TextView;

    .line 457
    .restart local v5       #tv:Landroid/widget/TextView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-wide v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_Speed:D

    const-wide/16 v8, 0x0

    cmpl-double v6, v6, v8

    if-lez v6, :cond_f

    const-string v7, "%d %s"

    const/4 v6, 0x2

    new-array v8, v6, [Ljava/lang/Object;

    const/4 v6, 0x0

    .line 458
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-wide v9, v9, Lcom/openvehicles/OVMS/CarData;->Data_Speed:D

    double-to-int v9, v9

    invoke-static {v9}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v9

    aput-object v9, v8, v6

    const/4 v9, 0x1

    .line 459
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_DistanceUnit:Ljava/lang/String;

    const-string v10, "K"

    invoke-virtual {v6, v10}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v6

    if-eqz v6, :cond_e

    const-string v6, "kph"

    :goto_5
    aput-object v6, v8, v9

    .line 457
    invoke-static {v7, v8}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v6

    :goto_6
    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 461
    const v6, 0x7f09007d

    invoke-virtual {p0, v6}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v5

    .end local v5           #tv:Landroid/widget/TextView;
    check-cast v5, Landroid/widget/TextView;

    .line 462
    .restart local v5       #tv:Landroid/widget/TextView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_PEM_Motor_Battery_TemperaturesDataStale:Z

    if-nez v6, :cond_0

    .line 463
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_CarPoweredON:Z

    if-nez v6, :cond_10

    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_CoolingPumpON_DoorState3:Z

    if-nez v6, :cond_10

    :cond_0
    const v6, -0xbbbbbc

    .line 462
    :goto_7
    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setTextColor(I)V

    .line 465
    const-string v6, "%d\u00b0C"

    const/4 v7, 0x1

    new-array v7, v7, [Ljava/lang/Object;

    const/4 v8, 0x0

    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-wide v9, v9, Lcom/openvehicles/OVMS/CarData;->Data_TemperaturePEM:D

    double-to-int v9, v9

    invoke-static {v9}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v9

    aput-object v9, v7, v8

    invoke-static {v6, v7}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 466
    const v6, 0x7f09007e

    invoke-virtual {p0, v6}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v5

    .end local v5           #tv:Landroid/widget/TextView;
    check-cast v5, Landroid/widget/TextView;

    .line 467
    .restart local v5       #tv:Landroid/widget/TextView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_PEM_Motor_Battery_TemperaturesDataStale:Z

    if-nez v6, :cond_1

    .line 468
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_CarPoweredON:Z

    if-nez v6, :cond_11

    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_CoolingPumpON_DoorState3:Z

    if-nez v6, :cond_11

    :cond_1
    const v6, -0xbbbbbc

    .line 467
    :goto_8
    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setTextColor(I)V

    .line 470
    const-string v6, "%d\u00b0C"

    const/4 v7, 0x1

    new-array v7, v7, [Ljava/lang/Object;

    const/4 v8, 0x0

    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-wide v9, v9, Lcom/openvehicles/OVMS/CarData;->Data_TemperatureMotor:D

    double-to-int v9, v9

    invoke-static {v9}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v9

    aput-object v9, v7, v8

    invoke-static {v6, v7}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 471
    const v6, 0x7f09007f

    invoke-virtual {p0, v6}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v5

    .end local v5           #tv:Landroid/widget/TextView;
    check-cast v5, Landroid/widget/TextView;

    .line 472
    .restart local v5       #tv:Landroid/widget/TextView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_PEM_Motor_Battery_TemperaturesDataStale:Z

    if-nez v6, :cond_2

    .line 473
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_CarPoweredON:Z

    if-nez v6, :cond_12

    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_CoolingPumpON_DoorState3:Z

    if-nez v6, :cond_12

    :cond_2
    const v6, -0xbbbbbc

    .line 472
    :goto_9
    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setTextColor(I)V

    .line 475
    const-string v6, "%d\u00b0C"

    const/4 v7, 0x1

    new-array v7, v7, [Ljava/lang/Object;

    const/4 v8, 0x0

    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-wide v9, v9, Lcom/openvehicles/OVMS/CarData;->Data_TemperatureBattery:D

    double-to-int v9, v9

    invoke-static {v9}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v9

    aput-object v9, v7, v8

    invoke-static {v6, v7}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 476
    const v6, 0x7f090080

    invoke-virtual {p0, v6}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v5

    .end local v5           #tv:Landroid/widget/TextView;
    check-cast v5, Landroid/widget/TextView;

    .line 477
    .restart local v5       #tv:Landroid/widget/TextView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_AmbientTemperatureDataStale:Z

    if-nez v6, :cond_3

    .line 478
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_CarPoweredON:Z

    if-nez v6, :cond_13

    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_CoolingPumpON_DoorState3:Z

    if-nez v6, :cond_13

    :cond_3
    const v6, -0xbbbbbc

    .line 477
    :goto_a
    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setTextColor(I)V

    .line 480
    const-string v6, "%d\u00b0C"

    const/4 v7, 0x1

    new-array v7, v7, [Ljava/lang/Object;

    const/4 v8, 0x0

    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-wide v9, v9, Lcom/openvehicles/OVMS/CarData;->Data_TemperatureAmbient:D

    double-to-int v9, v9

    invoke-static {v9}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v9

    aput-object v9, v7, v8

    invoke-static {v6, v7}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 482
    const-string v4, "%.1fpsi\n%.0f\u00b0C"

    .line 484
    .local v4, tirePressureDisplayFormat:Ljava/lang/String;
    const v6, 0x7f090070

    invoke-virtual {p0, v6}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v5

    .end local v5           #tv:Landroid/widget/TextView;
    check-cast v5, Landroid/widget/TextView;

    .line 485
    .restart local v5       #tv:Landroid/widget/TextView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_TPMSDataStale:Z

    if-eqz v6, :cond_14

    const v6, -0xbbbbbc

    :goto_b
    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setTextColor(I)V

    .line 486
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-wide v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_FLWheelPressure:D

    const-wide/16 v8, 0x0

    cmpl-double v6, v6, v8

    if-nez v6, :cond_4

    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-wide v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_FLWheelTemperature:D

    const-wide/16 v8, 0x0

    cmpl-double v6, v6, v8

    if-eqz v6, :cond_15

    :cond_4
    const/4 v6, 0x0

    :goto_c
    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setVisibility(I)V

    .line 488
    const/4 v6, 0x2

    new-array v6, v6, [Ljava/lang/Object;

    const/4 v7, 0x0

    .line 489
    iget-object v8, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-wide v8, v8, Lcom/openvehicles/OVMS/CarData;->Data_FLWheelPressure:D

    invoke-static {v8, v9}, Ljava/lang/Double;->valueOf(D)Ljava/lang/Double;

    move-result-object v8

    aput-object v8, v6, v7

    const/4 v7, 0x1

    iget-object v8, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-wide v8, v8, Lcom/openvehicles/OVMS/CarData;->Data_FLWheelTemperature:D

    invoke-static {v8, v9}, Ljava/lang/Double;->valueOf(D)Ljava/lang/Double;

    move-result-object v8

    aput-object v8, v6, v7

    .line 488
    invoke-static {v4, v6}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 491
    const v6, 0x7f090071

    invoke-virtual {p0, v6}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v5

    .end local v5           #tv:Landroid/widget/TextView;
    check-cast v5, Landroid/widget/TextView;

    .line 492
    .restart local v5       #tv:Landroid/widget/TextView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_TPMSDataStale:Z

    if-eqz v6, :cond_16

    const v6, -0xbbbbbc

    :goto_d
    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setTextColor(I)V

    .line 493
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-wide v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_FRWheelPressure:D

    const-wide/16 v8, 0x0

    cmpl-double v6, v6, v8

    if-nez v6, :cond_5

    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-wide v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_FRWheelTemperature:D

    const-wide/16 v8, 0x0

    cmpl-double v6, v6, v8

    if-eqz v6, :cond_17

    :cond_5
    const/4 v6, 0x0

    :goto_e
    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setVisibility(I)V

    .line 495
    const/4 v6, 0x2

    new-array v6, v6, [Ljava/lang/Object;

    const/4 v7, 0x0

    .line 496
    iget-object v8, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-wide v8, v8, Lcom/openvehicles/OVMS/CarData;->Data_FRWheelPressure:D

    invoke-static {v8, v9}, Ljava/lang/Double;->valueOf(D)Ljava/lang/Double;

    move-result-object v8

    aput-object v8, v6, v7

    const/4 v7, 0x1

    iget-object v8, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-wide v8, v8, Lcom/openvehicles/OVMS/CarData;->Data_FRWheelTemperature:D

    invoke-static {v8, v9}, Ljava/lang/Double;->valueOf(D)Ljava/lang/Double;

    move-result-object v8

    aput-object v8, v6, v7

    .line 495
    invoke-static {v4, v6}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 498
    const v6, 0x7f090072

    invoke-virtual {p0, v6}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v5

    .end local v5           #tv:Landroid/widget/TextView;
    check-cast v5, Landroid/widget/TextView;

    .line 499
    .restart local v5       #tv:Landroid/widget/TextView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_TPMSDataStale:Z

    if-eqz v6, :cond_18

    const v6, -0xbbbbbc

    :goto_f
    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setTextColor(I)V

    .line 500
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-wide v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_RLWheelPressure:D

    const-wide/16 v8, 0x0

    cmpl-double v6, v6, v8

    if-nez v6, :cond_6

    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-wide v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_RLWheelTemperature:D

    const-wide/16 v8, 0x0

    cmpl-double v6, v6, v8

    if-eqz v6, :cond_19

    :cond_6
    const/4 v6, 0x0

    :goto_10
    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setVisibility(I)V

    .line 502
    const/4 v6, 0x2

    new-array v6, v6, [Ljava/lang/Object;

    const/4 v7, 0x0

    .line 503
    iget-object v8, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-wide v8, v8, Lcom/openvehicles/OVMS/CarData;->Data_RLWheelPressure:D

    invoke-static {v8, v9}, Ljava/lang/Double;->valueOf(D)Ljava/lang/Double;

    move-result-object v8

    aput-object v8, v6, v7

    const/4 v7, 0x1

    iget-object v8, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-wide v8, v8, Lcom/openvehicles/OVMS/CarData;->Data_RLWheelTemperature:D

    invoke-static {v8, v9}, Ljava/lang/Double;->valueOf(D)Ljava/lang/Double;

    move-result-object v8

    aput-object v8, v6, v7

    .line 502
    invoke-static {v4, v6}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 505
    const v6, 0x7f090073

    invoke-virtual {p0, v6}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v5

    .end local v5           #tv:Landroid/widget/TextView;
    check-cast v5, Landroid/widget/TextView;

    .line 506
    .restart local v5       #tv:Landroid/widget/TextView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_TPMSDataStale:Z

    if-eqz v6, :cond_1a

    const v6, -0xbbbbbc

    :goto_11
    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setTextColor(I)V

    .line 507
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-wide v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_RRWheelPressure:D

    const-wide/16 v8, 0x0

    cmpl-double v6, v6, v8

    if-nez v6, :cond_7

    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-wide v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_RRWheelTemperature:D

    const-wide/16 v8, 0x0

    cmpl-double v6, v6, v8

    if-eqz v6, :cond_1b

    :cond_7
    const/4 v6, 0x0

    :goto_12
    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setVisibility(I)V

    .line 509
    const/4 v6, 0x2

    new-array v6, v6, [Ljava/lang/Object;

    const/4 v7, 0x0

    .line 510
    iget-object v8, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-wide v8, v8, Lcom/openvehicles/OVMS/CarData;->Data_RRWheelPressure:D

    invoke-static {v8, v9}, Ljava/lang/Double;->valueOf(D)Ljava/lang/Double;

    move-result-object v8

    aput-object v8, v6, v7

    const/4 v7, 0x1

    iget-object v8, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-wide v8, v8, Lcom/openvehicles/OVMS/CarData;->Data_RRWheelTemperature:D

    invoke-static {v8, v9}, Ljava/lang/Double;->valueOf(D)Ljava/lang/Double;

    move-result-object v8

    aput-object v8, v6, v7

    .line 509
    invoke-static {v4, v6}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 512
    const v6, 0x7f090067

    invoke-virtual {p0, v6}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v3

    check-cast v3, Landroid/widget/ImageView;

    .line 515
    .local v3, iv:Landroid/widget/ImageView;
    new-instance v6, Ljava/lang/StringBuilder;

    .line 516
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->getCacheDir()Ljava/io/File;

    move-result-object v7

    invoke-virtual {v7}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v7

    invoke-static {v7}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v7

    invoke-direct {v6, v7}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 517
    const-string v7, "/ol_%s.png"

    const/4 v8, 0x1

    new-array v8, v8, [Ljava/lang/Object;

    const/4 v9, 0x0

    iget-object v10, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v10, v10, Lcom/openvehicles/OVMS/CarData;->VehicleImageDrawable:Ljava/lang/String;

    aput-object v10, v8, v9

    invoke-static {v7, v8}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v7

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    .line 515
    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v6}, Landroid/graphics/BitmapFactory;->decodeFile(Ljava/lang/String;)Landroid/graphics/Bitmap;

    move-result-object v2

    .line 518
    .local v2, carLayout:Landroid/graphics/Bitmap;
    if-eqz v2, :cond_1c

    .line 519
    invoke-virtual {v3, v2}, Landroid/widget/ImageView;->setImageBitmap(Landroid/graphics/Bitmap;)V

    .line 556
    :cond_8
    :goto_13
    const v6, 0x7f090068

    invoke-virtual {p0, v6}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v3

    .end local v3           #iv:Landroid/widget/ImageView;
    check-cast v3, Landroid/widget/ImageView;

    .line 557
    .restart local v3       #iv:Landroid/widget/ImageView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_ChargePortOpen:Z

    if-eqz v6, :cond_1e

    const/4 v6, 0x0

    :goto_14
    invoke-virtual {v3, v6}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 558
    const v6, 0x7f09006b

    invoke-virtual {p0, v6}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v3

    .end local v3           #iv:Landroid/widget/ImageView;
    check-cast v3, Landroid/widget/ImageView;

    .line 559
    .restart local v3       #iv:Landroid/widget/ImageView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_BonnetOpen:Z

    if-eqz v6, :cond_1f

    const/4 v6, 0x0

    :goto_15
    invoke-virtual {v3, v6}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 560
    const v6, 0x7f09006c

    invoke-virtual {p0, v6}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v3

    .end local v3           #iv:Landroid/widget/ImageView;
    check-cast v3, Landroid/widget/ImageView;

    .line 561
    .restart local v3       #iv:Landroid/widget/ImageView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_LeftDoorOpen:Z

    if-eqz v6, :cond_20

    const/4 v6, 0x0

    :goto_16
    invoke-virtual {v3, v6}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 562
    const v6, 0x7f09006a

    invoke-virtual {p0, v6}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v3

    .end local v3           #iv:Landroid/widget/ImageView;
    check-cast v3, Landroid/widget/ImageView;

    .line 563
    .restart local v3       #iv:Landroid/widget/ImageView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_RightDoorOpen:Z

    if-eqz v6, :cond_21

    const/4 v6, 0x0

    :goto_17
    invoke-virtual {v3, v6}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 564
    const v6, 0x7f090069

    invoke-virtual {p0, v6}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v3

    .end local v3           #iv:Landroid/widget/ImageView;
    check-cast v3, Landroid/widget/ImageView;

    .line 565
    .restart local v3       #iv:Landroid/widget/ImageView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_TrunkOpen:Z

    if-eqz v6, :cond_22

    const/4 v6, 0x0

    :goto_18
    invoke-virtual {v3, v6}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 567
    const v6, 0x7f09006d

    invoke-virtual {p0, v6}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v3

    .end local v3           #iv:Landroid/widget/ImageView;
    check-cast v3, Landroid/widget/ImageView;

    .line 568
    .restart local v3       #iv:Landroid/widget/ImageView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_CarLocked:Z

    if-eqz v6, :cond_23

    const v6, 0x7f02003b

    :goto_19
    invoke-virtual {v3, v6}, Landroid/widget/ImageView;->setImageResource(I)V

    .line 570
    const v6, 0x7f09006e

    invoke-virtual {p0, v6}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v3

    .end local v3           #iv:Landroid/widget/ImageView;
    check-cast v3, Landroid/widget/ImageView;

    .line 571
    .restart local v3       #iv:Landroid/widget/ImageView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_ValetON:Z

    if-eqz v6, :cond_24

    const v6, 0x7f02003e

    :goto_1a
    invoke-virtual {v3, v6}, Landroid/widget/ImageView;->setImageResource(I)V

    .line 573
    const v6, 0x7f09006f

    invoke-virtual {p0, v6}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v3

    .end local v3           #iv:Landroid/widget/ImageView;
    check-cast v3, Landroid/widget/ImageView;

    .line 574
    .restart local v3       #iv:Landroid/widget/ImageView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_HeadlightsON:Z

    if-eqz v6, :cond_25

    const/4 v6, 0x0

    :goto_1b
    invoke-virtual {v3, v6}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 579
    :try_start_0
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_CarModuleGSMSignalLevel:Ljava/lang/String;

    invoke-static {v6}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v0

    .line 580
    .local v0, RSSI:I
    const/4 v6, 0x1

    if-ge v0, v6, :cond_26

    .line 581
    const v6, 0x7f020068

    invoke-virtual {v3, v6}, Landroid/widget/ImageView;->setImageResource(I)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 594
    .end local v0           #RSSI:I
    :goto_1c
    return-void

    .line 438
    .end local v2           #carLayout:Landroid/graphics/Bitmap;
    .end local v3           #iv:Landroid/widget/ImageView;
    .end local v4           #tirePressureDisplayFormat:Ljava/lang/String;
    :cond_9
    const/4 v6, 0x4

    goto/16 :goto_0

    .line 441
    :cond_a
    const/4 v6, 0x4

    goto/16 :goto_1

    .line 444
    :cond_b
    const/4 v6, 0x4

    goto/16 :goto_2

    .line 446
    :cond_c
    const/4 v6, 0x4

    goto/16 :goto_3

    .line 448
    :cond_d
    const/4 v6, 0x4

    goto/16 :goto_4

    .line 459
    :cond_e
    const-string v6, "mph"

    goto/16 :goto_5

    :cond_f
    const-string v6, ""

    goto/16 :goto_6

    .line 464
    :cond_10
    const/4 v6, -0x1

    goto/16 :goto_7

    .line 469
    :cond_11
    const/4 v6, -0x1

    goto/16 :goto_8

    .line 474
    :cond_12
    const/4 v6, -0x1

    goto/16 :goto_9

    .line 479
    :cond_13
    const/4 v6, -0x1

    goto/16 :goto_a

    .line 485
    .restart local v4       #tirePressureDisplayFormat:Ljava/lang/String;
    :cond_14
    const/4 v6, -0x1

    goto/16 :goto_b

    .line 487
    :cond_15
    const/4 v6, 0x4

    goto/16 :goto_c

    .line 492
    :cond_16
    const/4 v6, -0x1

    goto/16 :goto_d

    .line 494
    :cond_17
    const/4 v6, 0x4

    goto/16 :goto_e

    .line 499
    :cond_18
    const/4 v6, -0x1

    goto/16 :goto_f

    .line 501
    :cond_19
    const/4 v6, 0x4

    goto/16 :goto_10

    .line 506
    :cond_1a
    const/4 v6, -0x1

    goto/16 :goto_11

    .line 508
    :cond_1b
    const/4 v6, 0x4

    goto/16 :goto_12

    .line 521
    .restart local v2       #carLayout:Landroid/graphics/Bitmap;
    .restart local v3       #iv:Landroid/widget/ImageView;
    :cond_1c
    const-string v6, "OVMS"

    new-instance v7, Ljava/lang/StringBuilder;

    const-string v8, "** File Not Found: "

    invoke-direct {v7, v8}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 522
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->getCacheDir()Ljava/io/File;

    move-result-object v8

    invoke-virtual {v8}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    .line 523
    const-string v8, "/ol_%s.png"

    const/4 v9, 0x1

    new-array v9, v9, [Ljava/lang/Object;

    const/4 v10, 0x0

    iget-object v11, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v11, v11, Lcom/openvehicles/OVMS/CarData;->VehicleImageDrawable:Ljava/lang/String;

    aput-object v11, v9, v10

    invoke-static {v8, v9}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    .line 521
    invoke-static {v6, v7}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 526
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->DontAskLayoutDownload:Z

    if-nez v6, :cond_8

    .line 527
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->lastUpdatedDialog:Landroid/app/AlertDialog;

    if-eqz v6, :cond_1d

    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->lastUpdatedDialog:Landroid/app/AlertDialog;

    .line 528
    invoke-virtual {v6}, Landroid/app/AlertDialog;->isShowing()Z

    move-result v6

    if-nez v6, :cond_8

    .line 529
    :cond_1d
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    const/4 v7, 0x1

    iput-boolean v7, v6, Lcom/openvehicles/OVMS/CarData;->DontAskLayoutDownload:Z

    .line 530
    new-instance v1, Landroid/app/AlertDialog$Builder;

    invoke-direct {v1, p0}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 533
    .local v1, builder:Landroid/app/AlertDialog$Builder;
    const-string v6, "Would you like to download a set of high resolution car images specifically drawn for your car?\n\nThe download is approx. 300KB.\n\nNote: a manual download button is available in the car commands and settings tab."

    .line 532
    invoke-virtual {v1, v6}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v6

    .line 534
    const-string v7, "Download Graphics"

    invoke-virtual {v6, v7}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v6

    .line 535
    const/4 v7, 0x1

    invoke-virtual {v6, v7}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    move-result-object v6

    .line 536
    const-string v7, "Download Now"

    .line 537
    new-instance v8, Lcom/openvehicles/OVMS/TabInfo_xlarge$14;

    invoke-direct {v8, p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge$14;-><init>(Lcom/openvehicles/OVMS/TabInfo_xlarge;)V

    .line 536
    invoke-virtual {v6, v7, v8}, Landroid/app/AlertDialog$Builder;->setPositiveButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v6

    .line 544
    const-string v7, "Later"

    .line 545
    new-instance v8, Lcom/openvehicles/OVMS/TabInfo_xlarge$15;

    invoke-direct {v8, p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge$15;-><init>(Lcom/openvehicles/OVMS/TabInfo_xlarge;)V

    .line 544
    invoke-virtual {v6, v7, v8}, Landroid/app/AlertDialog$Builder;->setNegativeButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 551
    invoke-virtual {v1}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v6

    iput-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->lastUpdatedDialog:Landroid/app/AlertDialog;

    .line 552
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->lastUpdatedDialog:Landroid/app/AlertDialog;

    invoke-virtual {v6}, Landroid/app/AlertDialog;->show()V

    goto/16 :goto_13

    .line 557
    .end local v1           #builder:Landroid/app/AlertDialog$Builder;
    :cond_1e
    const/16 v6, 0x8

    goto/16 :goto_14

    .line 559
    :cond_1f
    const/16 v6, 0x8

    goto/16 :goto_15

    .line 561
    :cond_20
    const/16 v6, 0x8

    goto/16 :goto_16

    .line 563
    :cond_21
    const/16 v6, 0x8

    goto/16 :goto_17

    .line 565
    :cond_22
    const/16 v6, 0x8

    goto/16 :goto_18

    .line 569
    :cond_23
    const v6, 0x7f02003c

    goto/16 :goto_19

    .line 572
    :cond_24
    const v6, 0x7f02003d

    goto/16 :goto_1a

    .line 574
    :cond_25
    const/16 v6, 0x8

    goto/16 :goto_1b

    .line 582
    .restart local v0       #RSSI:I
    :cond_26
    const/4 v6, 0x7

    if-ge v0, v6, :cond_27

    .line 583
    const v6, 0x7f020069

    :try_start_1
    invoke-virtual {v3, v6}, Landroid/widget/ImageView;->setImageResource(I)V

    goto/16 :goto_1c

    .line 592
    .end local v0           #RSSI:I
    :catch_0
    move-exception v6

    goto/16 :goto_1c

    .line 584
    .restart local v0       #RSSI:I
    :cond_27
    const/16 v6, 0xe

    if-ge v0, v6, :cond_28

    .line 585
    const v6, 0x7f02006a

    invoke-virtual {v3, v6}, Landroid/widget/ImageView;->setImageResource(I)V

    goto/16 :goto_1c

    .line 586
    :cond_28
    const/16 v6, 0x15

    if-ge v0, v6, :cond_29

    .line 587
    const v6, 0x7f02006b

    invoke-virtual {v3, v6}, Landroid/widget/ImageView;->setImageResource(I)V

    goto/16 :goto_1c

    .line 588
    :cond_29
    const/16 v6, 0x1c

    if-ge v0, v6, :cond_2a

    .line 589
    const v6, 0x7f02006c

    invoke-virtual {v3, v6}, Landroid/widget/ImageView;->setImageResource(I)V

    goto/16 :goto_1c

    .line 591
    :cond_2a
    const v6, 0x7f02006d

    invoke-virtual {v3, v6}, Landroid/widget/ImageView;->setImageResource(I)V
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_0

    goto/16 :goto_1c
.end method

.method private updateInfoUI()V
    .locals 15

    .prologue
    const v13, 0x7f09005c

    const/16 v10, 0x8

    const/4 v14, 0x1

    const/4 v9, 0x0

    .line 598
    const v8, 0x7f09005a

    invoke-virtual {p0, v8}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v7

    check-cast v7, Landroid/widget/TextView;

    .line 599
    .local v7, tv:Landroid/widget/TextView;
    iget-object v8, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v8, v8, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    invoke-virtual {v7, v8}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 601
    const v8, 0x7f090064

    invoke-virtual {p0, v8}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v7

    .end local v7           #tv:Landroid/widget/TextView;
    check-cast v7, Landroid/widget/TextView;

    .line 602
    .restart local v7       #tv:Landroid/widget/TextView;
    const v8, 0x7f060004

    invoke-virtual {p0, v8}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->getString(I)Ljava/lang/String;

    move-result-object v8

    new-array v11, v14, [Ljava/lang/Object;

    iget-object v12, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget v12, v12, Lcom/openvehicles/OVMS/CarData;->Data_SOC:I

    invoke-static {v12}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v12

    aput-object v12, v11, v9

    invoke-static {v8, v11}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v7, v8}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 604
    invoke-virtual {p0, v13}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v8

    if-eqz v8, :cond_0

    .line 605
    invoke-virtual {p0, v13}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v6

    check-cast v6, Landroid/widget/TableRow;

    .line 606
    .local v6, row:Landroid/widget/TableRow;
    iget-object v8, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v8, v8, Lcom/openvehicles/OVMS/CarData;->Data_ChargePortOpen:Z

    if-eqz v8, :cond_5

    move v8, v9

    :goto_0
    invoke-virtual {v6, v8}, Landroid/widget/TableRow;->setVisibility(I)V

    .line 610
    .end local v6           #row:Landroid/widget/TableRow;
    :cond_0
    const v8, 0x7f090060

    invoke-virtual {p0, v8}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/SeekBar;

    .line 612
    .local v1, bar:Landroid/widget/SeekBar;
    const v8, 0x7f09005f

    invoke-virtual {p0, v8}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v7

    .end local v7           #tv:Landroid/widget/TextView;
    check-cast v7, Landroid/widget/TextView;

    .line 613
    .restart local v7       #tv:Landroid/widget/TextView;
    iget-object v8, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v8, v8, Lcom/openvehicles/OVMS/CarData;->Data_ChargeState:Ljava/lang/String;

    const-string v11, "charging"

    invoke-virtual {v8, v11}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v8

    if-eqz v8, :cond_6

    .line 614
    const-string v8, "Charging - %s"

    new-array v11, v14, [Ljava/lang/Object;

    .line 615
    iget-object v12, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v12, v12, Lcom/openvehicles/OVMS/CarData;->Data_ChargeMode:Ljava/lang/String;

    invoke-virtual {v12}, Ljava/lang/String;->toUpperCase()Ljava/lang/String;

    move-result-object v12

    aput-object v12, v11, v9

    .line 614
    invoke-static {v8, v11}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v7, v8}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 633
    :cond_1
    :goto_1
    const v8, 0x7f09005e

    invoke-virtual {p0, v8}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v7

    .end local v7           #tv:Landroid/widget/TextView;
    check-cast v7, Landroid/widget/TextView;

    .line 634
    .restart local v7       #tv:Landroid/widget/TextView;
    const v8, 0x7f090062

    invoke-virtual {p0, v8}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v5

    check-cast v5, Landroid/widget/ImageView;

    .line 636
    .local v5, iv:Landroid/widget/ImageView;
    iget-object v8, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v8, v8, Lcom/openvehicles/OVMS/CarData;->Data_Charging:Z

    if-eqz v8, :cond_b

    .line 638
    invoke-virtual {v1, v9}, Landroid/widget/SeekBar;->setProgress(I)V

    .line 639
    invoke-virtual {v5, v9}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 640
    const-string v8, "%sA|%sV"

    const/4 v11, 0x2

    new-array v11, v11, [Ljava/lang/Object;

    iget-object v12, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget v12, v12, Lcom/openvehicles/OVMS/CarData;->Data_ChargeCurrent:I

    invoke-static {v12}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v12

    aput-object v12, v11, v9

    .line 641
    iget-object v12, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget v12, v12, Lcom/openvehicles/OVMS/CarData;->Data_LineVoltage:I

    invoke-static {v12}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v12

    aput-object v12, v11, v14

    .line 640
    invoke-static {v8, v11}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v7, v8}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 649
    :goto_2
    const-string v4, " km"

    .line 650
    .local v4, distanceUnit:Ljava/lang/String;
    iget-object v8, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v8, v8, Lcom/openvehicles/OVMS/CarData;->Data_DistanceUnit:Ljava/lang/String;

    if-eqz v8, :cond_2

    .line 651
    iget-object v8, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v8, v8, Lcom/openvehicles/OVMS/CarData;->Data_DistanceUnit:Ljava/lang/String;

    const-string v11, "K"

    invoke-virtual {v8, v11}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v8

    if-nez v8, :cond_2

    .line 652
    const-string v4, " miles"

    .line 654
    :cond_2
    const v8, 0x7f090066

    invoke-virtual {p0, v8}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v7

    .end local v7           #tv:Landroid/widget/TextView;
    check-cast v7, Landroid/widget/TextView;

    .line 655
    .restart local v7       #tv:Landroid/widget/TextView;
    new-instance v8, Ljava/lang/StringBuilder;

    iget-object v11, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget v11, v11, Lcom/openvehicles/OVMS/CarData;->Data_IdealRange:I

    invoke-static {v11}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v11

    invoke-direct {v8, v11}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v8, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v7, v8}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 656
    const v8, 0x7f090065

    invoke-virtual {p0, v8}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v7

    .end local v7           #tv:Landroid/widget/TextView;
    check-cast v7, Landroid/widget/TextView;

    .line 657
    .restart local v7       #tv:Landroid/widget/TextView;
    new-instance v8, Ljava/lang/StringBuilder;

    iget-object v11, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget v11, v11, Lcom/openvehicles/OVMS/CarData;->Data_EstimatedRange:I

    invoke-static {v11}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v11

    invoke-direct {v8, v11}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v8, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v7, v8}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 659
    const v8, 0x7f090055

    invoke-virtual {p0, v8}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v5

    .end local v5           #iv:Landroid/widget/ImageView;
    check-cast v5, Landroid/widget/ImageView;

    .line 660
    .restart local v5       #iv:Landroid/widget/ImageView;
    iget-boolean v8, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->isLoggedIn:Z

    if-eqz v8, :cond_c

    move v8, v10

    :goto_3
    invoke-virtual {v5, v8}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 662
    const v8, 0x7f090056

    invoke-virtual {p0, v8}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v5

    .end local v5           #iv:Landroid/widget/ImageView;
    check-cast v5, Landroid/widget/ImageView;

    .line 663
    .restart local v5       #iv:Landroid/widget/ImageView;
    iget-object v8, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v8, v8, Lcom/openvehicles/OVMS/CarData;->ParanoidMode:Z

    if-eqz v8, :cond_3

    move v10, v9

    :cond_3
    invoke-virtual {v5, v10}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 665
    const v8, 0x7f090058

    invoke-virtual {p0, v8}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v5

    .end local v5           #iv:Landroid/widget/ImageView;
    check-cast v5, Landroid/widget/ImageView;

    .line 668
    .restart local v5       #iv:Landroid/widget/ImageView;
    :try_start_0
    iget-object v8, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v8, v8, Lcom/openvehicles/OVMS/CarData;->Data_CarModuleGSMSignalLevel:Ljava/lang/String;

    invoke-static {v8}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v0

    .line 669
    .local v0, RSSI:I
    if-ge v0, v14, :cond_d

    .line 670
    const v8, 0x7f020068

    invoke-virtual {v5, v8}, Landroid/widget/ImageView;->setImageResource(I)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 684
    .end local v0           #RSSI:I
    :goto_4
    const v8, 0x7f090063

    invoke-virtual {p0, v8}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v5

    .end local v5           #iv:Landroid/widget/ImageView;
    check-cast v5, Landroid/widget/ImageView;

    .line 685
    .restart local v5       #iv:Landroid/widget/ImageView;
    iget-object v8, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget v8, v8, Lcom/openvehicles/OVMS/CarData;->Data_SOC:I

    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->getResources()Landroid/content/res/Resources;

    move-result-object v10

    const v11, 0x7f020004

    invoke-static {v10, v11}, Landroid/graphics/BitmapFactory;->decodeResource(Landroid/content/res/Resources;I)Landroid/graphics/Bitmap;

    move-result-object v10

    invoke-static {v8, v10}, Lcom/openvehicles/OVMS/Utilities;->GetScaledBatteryOverlay(ILandroid/graphics/Bitmap;)Landroid/graphics/Bitmap;

    move-result-object v8

    invoke-virtual {v5, v8}, Landroid/widget/ImageView;->setImageBitmap(Landroid/graphics/Bitmap;)V

    .line 689
    const v8, 0x7f09005b

    invoke-virtual {p0, v8}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v5

    .end local v5           #iv:Landroid/widget/ImageView;
    check-cast v5, Landroid/widget/ImageView;

    .line 691
    .restart local v5       #iv:Landroid/widget/ImageView;
    new-instance v8, Ljava/lang/StringBuilder;

    .line 692
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->getCacheDir()Ljava/io/File;

    move-result-object v10

    invoke-virtual {v10}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v10

    invoke-static {v10}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v10

    invoke-direct {v8, v10}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 693
    const-string v10, "/%s.png"

    new-array v11, v14, [Ljava/lang/Object;

    iget-object v12, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v12, v12, Lcom/openvehicles/OVMS/CarData;->VehicleImageDrawable:Ljava/lang/String;

    aput-object v12, v11, v9

    invoke-static {v10, v11}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v10

    invoke-virtual {v8, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    .line 691
    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    invoke-static {v8}, Landroid/graphics/BitmapFactory;->decodeFile(Ljava/lang/String;)Landroid/graphics/Bitmap;

    move-result-object v3

    .line 694
    .local v3, carLayout:Landroid/graphics/Bitmap;
    if-eqz v3, :cond_12

    .line 695
    invoke-virtual {v5, v3}, Landroid/widget/ImageView;->setImageBitmap(Landroid/graphics/Bitmap;)V

    .line 732
    :cond_4
    :goto_5
    new-instance v8, Lcom/openvehicles/OVMS/TabInfo_xlarge$18;

    invoke-direct {v8, p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge$18;-><init>(Lcom/openvehicles/OVMS/TabInfo_xlarge;)V

    invoke-virtual {v5, v8}, Landroid/widget/ImageView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 767
    return-void

    .end local v1           #bar:Landroid/widget/SeekBar;
    .end local v3           #carLayout:Landroid/graphics/Bitmap;
    .end local v4           #distanceUnit:Ljava/lang/String;
    .end local v5           #iv:Landroid/widget/ImageView;
    .restart local v6       #row:Landroid/widget/TableRow;
    :cond_5
    move v8, v10

    .line 607
    goto/16 :goto_0

    .line 616
    .end local v6           #row:Landroid/widget/TableRow;
    .restart local v1       #bar:Landroid/widget/SeekBar;
    :cond_6
    iget-object v8, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v8, v8, Lcom/openvehicles/OVMS/CarData;->Data_ChargeState:Ljava/lang/String;

    const-string v11, "prepare"

    invoke-virtual {v8, v11}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v8

    if-eqz v8, :cond_7

    .line 617
    const-string v8, "Preparing to Charge - %s"

    new-array v11, v14, [Ljava/lang/Object;

    .line 618
    iget-object v12, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v12, v12, Lcom/openvehicles/OVMS/CarData;->Data_ChargeMode:Ljava/lang/String;

    invoke-virtual {v12}, Ljava/lang/String;->toUpperCase()Ljava/lang/String;

    move-result-object v12

    aput-object v12, v11, v9

    .line 617
    invoke-static {v8, v11}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v7, v8}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_1

    .line 619
    :cond_7
    iget-object v8, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v8, v8, Lcom/openvehicles/OVMS/CarData;->Data_ChargeState:Ljava/lang/String;

    const-string v11, "heating"

    invoke-virtual {v8, v11}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v8

    if-eqz v8, :cond_8

    .line 620
    const-string v8, "Pre-Charge Battery Heating - %s"

    new-array v11, v14, [Ljava/lang/Object;

    .line 621
    iget-object v12, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v12, v12, Lcom/openvehicles/OVMS/CarData;->Data_ChargeMode:Ljava/lang/String;

    invoke-virtual {v12}, Ljava/lang/String;->toUpperCase()Ljava/lang/String;

    move-result-object v12

    aput-object v12, v11, v9

    .line 620
    invoke-static {v8, v11}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v7, v8}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_1

    .line 622
    :cond_8
    iget-object v8, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v8, v8, Lcom/openvehicles/OVMS/CarData;->Data_ChargeState:Ljava/lang/String;

    const-string v11, "topoff"

    invoke-virtual {v8, v11}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v8

    if-eqz v8, :cond_9

    .line 623
    const-string v8, "Topping Off - %s"

    new-array v11, v14, [Ljava/lang/Object;

    .line 624
    iget-object v12, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v12, v12, Lcom/openvehicles/OVMS/CarData;->Data_ChargeMode:Ljava/lang/String;

    invoke-virtual {v12}, Ljava/lang/String;->toUpperCase()Ljava/lang/String;

    move-result-object v12

    aput-object v12, v11, v9

    .line 623
    invoke-static {v8, v11}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v7, v8}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_1

    .line 625
    :cond_9
    iget-object v8, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v8, v8, Lcom/openvehicles/OVMS/CarData;->Data_ChargeState:Ljava/lang/String;

    const-string v11, "stopped"

    invoke-virtual {v8, v11}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v8

    if-eqz v8, :cond_a

    .line 626
    const-string v8, "Charge Interrupted - %s"

    new-array v11, v14, [Ljava/lang/Object;

    .line 627
    iget-object v12, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v12, v12, Lcom/openvehicles/OVMS/CarData;->Data_ChargeMode:Ljava/lang/String;

    invoke-virtual {v12}, Ljava/lang/String;->toUpperCase()Ljava/lang/String;

    move-result-object v12

    aput-object v12, v11, v9

    .line 626
    invoke-static {v8, v11}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v7, v8}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_1

    .line 628
    :cond_a
    iget-object v8, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v8, v8, Lcom/openvehicles/OVMS/CarData;->Data_ChargeState:Ljava/lang/String;

    const-string v11, "done"

    invoke-virtual {v8, v11}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v8

    if-eqz v8, :cond_1

    .line 629
    const-string v8, "Charge Completed - %s"

    new-array v11, v14, [Ljava/lang/Object;

    .line 630
    iget-object v12, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v12, v12, Lcom/openvehicles/OVMS/CarData;->Data_ChargeMode:Ljava/lang/String;

    invoke-virtual {v12}, Ljava/lang/String;->toUpperCase()Ljava/lang/String;

    move-result-object v12

    aput-object v12, v11, v9

    .line 629
    invoke-static {v8, v11}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v7, v8}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_1

    .line 644
    .restart local v5       #iv:Landroid/widget/ImageView;
    :cond_b
    const/16 v8, 0x64

    invoke-virtual {v1, v8}, Landroid/widget/SeekBar;->setProgress(I)V

    .line 645
    invoke-virtual {v5, v10}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 646
    const-string v8, "%sA MAX"

    new-array v11, v14, [Ljava/lang/Object;

    iget-object v12, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget v12, v12, Lcom/openvehicles/OVMS/CarData;->Data_ChargeAmpsLimit:I

    invoke-static {v12}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v12

    aput-object v12, v11, v9

    invoke-static {v8, v11}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v7, v8}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_2

    .restart local v4       #distanceUnit:Ljava/lang/String;
    :cond_c
    move v8, v9

    .line 660
    goto/16 :goto_3

    .line 671
    .restart local v0       #RSSI:I
    :cond_d
    const/4 v8, 0x7

    if-ge v0, v8, :cond_e

    .line 672
    const v8, 0x7f020069

    :try_start_1
    invoke-virtual {v5, v8}, Landroid/widget/ImageView;->setImageResource(I)V

    goto/16 :goto_4

    .line 681
    .end local v0           #RSSI:I
    :catch_0
    move-exception v8

    goto/16 :goto_4

    .line 673
    .restart local v0       #RSSI:I
    :cond_e
    const/16 v8, 0xe

    if-ge v0, v8, :cond_f

    .line 674
    const v8, 0x7f02006a

    invoke-virtual {v5, v8}, Landroid/widget/ImageView;->setImageResource(I)V

    goto/16 :goto_4

    .line 675
    :cond_f
    const/16 v8, 0x15

    if-ge v0, v8, :cond_10

    .line 676
    const v8, 0x7f02006b

    invoke-virtual {v5, v8}, Landroid/widget/ImageView;->setImageResource(I)V

    goto/16 :goto_4

    .line 677
    :cond_10
    const/16 v8, 0x1c

    if-ge v0, v8, :cond_11

    .line 678
    const v8, 0x7f02006c

    invoke-virtual {v5, v8}, Landroid/widget/ImageView;->setImageResource(I)V

    goto/16 :goto_4

    .line 680
    :cond_11
    const v8, 0x7f02006d

    invoke-virtual {v5, v8}, Landroid/widget/ImageView;->setImageResource(I)V
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_0

    goto/16 :goto_4

    .line 697
    .end local v0           #RSSI:I
    .restart local v3       #carLayout:Landroid/graphics/Bitmap;
    :cond_12
    const-string v8, "OVMS"

    new-instance v10, Ljava/lang/StringBuilder;

    const-string v11, "** File Not Found: "

    invoke-direct {v10, v11}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 698
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->getCacheDir()Ljava/io/File;

    move-result-object v11

    invoke-virtual {v11}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    .line 699
    const-string v11, "/%s.png"

    new-array v12, v14, [Ljava/lang/Object;

    iget-object v13, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v13, v13, Lcom/openvehicles/OVMS/CarData;->VehicleImageDrawable:Ljava/lang/String;

    aput-object v13, v12, v9

    invoke-static {v11, v12}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v10, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    .line 697
    invoke-static {v8, v9}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 702
    iget-object v8, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v8, v8, Lcom/openvehicles/OVMS/CarData;->DontAskLayoutDownload:Z

    if-nez v8, :cond_4

    .line 703
    iget-object v8, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->lastUpdatedDialog:Landroid/app/AlertDialog;

    if-eqz v8, :cond_13

    iget-object v8, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->lastUpdatedDialog:Landroid/app/AlertDialog;

    .line 704
    invoke-virtual {v8}, Landroid/app/AlertDialog;->isShowing()Z

    move-result v8

    if-nez v8, :cond_4

    .line 705
    :cond_13
    iget-object v8, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iput-boolean v14, v8, Lcom/openvehicles/OVMS/CarData;->DontAskLayoutDownload:Z

    .line 706
    new-instance v2, Landroid/app/AlertDialog$Builder;

    invoke-direct {v2, p0}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 709
    .local v2, builder:Landroid/app/AlertDialog$Builder;
    const-string v8, "Would you like to download a set of high resolution car images specifically drawn for your car?\n\nThe download is approx. 300KB.\n\nNote: a manual download button is available in the car commands and settings tab."

    .line 708
    invoke-virtual {v2, v8}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v8

    .line 710
    const-string v9, "Download Graphics"

    invoke-virtual {v8, v9}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v8

    .line 711
    invoke-virtual {v8, v14}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    move-result-object v8

    .line 712
    const-string v9, "Download Now"

    .line 713
    new-instance v10, Lcom/openvehicles/OVMS/TabInfo_xlarge$16;

    invoke-direct {v10, p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge$16;-><init>(Lcom/openvehicles/OVMS/TabInfo_xlarge;)V

    .line 712
    invoke-virtual {v8, v9, v10}, Landroid/app/AlertDialog$Builder;->setPositiveButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v8

    .line 720
    const-string v9, "Later"

    .line 721
    new-instance v10, Lcom/openvehicles/OVMS/TabInfo_xlarge$17;

    invoke-direct {v10, p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge$17;-><init>(Lcom/openvehicles/OVMS/TabInfo_xlarge;)V

    .line 720
    invoke-virtual {v8, v9, v10}, Landroid/app/AlertDialog$Builder;->setNegativeButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 727
    invoke-virtual {v2}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v8

    iput-object v8, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->lastUpdatedDialog:Landroid/app/AlertDialog;

    .line 728
    iget-object v8, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->lastUpdatedDialog:Landroid/app/AlertDialog;

    invoke-virtual {v8}, Landroid/app/AlertDialog;->show()V

    goto/16 :goto_5
.end method

.method private updateLastUpdatedView()V
    .locals 17

    .prologue
    .line 358
    move-object/from16 v0, p0

    iget-object v11, v0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    if-eqz v11, :cond_0

    move-object/from16 v0, p0

    iget-object v11, v0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v11, v11, Lcom/openvehicles/OVMS/CarData;->Data_LastCarUpdate:Ljava/util/Date;

    if-nez v11, :cond_1

    .line 423
    :cond_0
    :goto_0
    return-void

    .line 361
    :cond_1
    const v11, 0x7f090057

    move-object/from16 v0, p0

    invoke-virtual {v0, v11}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v10

    check-cast v10, Landroid/widget/TextView;

    .line 362
    .local v10, tv:Landroid/widget/TextView;
    new-instance v5, Ljava/util/Date;

    invoke-direct {v5}, Ljava/util/Date;-><init>()V

    .line 363
    .local v5, now:Ljava/util/Date;
    invoke-virtual {v5}, Ljava/util/Date;->getTime()J

    move-result-wide v11

    move-object/from16 v0, p0

    iget-object v13, v0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v13, v13, Lcom/openvehicles/OVMS/CarData;->Data_LastCarUpdate:Ljava/util/Date;

    .line 364
    invoke-virtual {v13}, Ljava/util/Date;->getTime()J

    move-result-wide v13

    .line 363
    sub-long/2addr v11, v13

    .line 364
    const-wide/16 v13, 0x3e8

    .line 363
    div-long v3, v11, v13

    .line 366
    .local v3, lastUpdateSecondsAgo:J
    const-wide/16 v11, 0x3c

    cmp-long v11, v3, v11

    if-gez v11, :cond_2

    .line 367
    const-string v11, "live"

    invoke-virtual {v10, v11}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 384
    :goto_1
    const v11, 0x7f090054

    move-object/from16 v0, p0

    invoke-virtual {v0, v11}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v10

    .end local v10           #tv:Landroid/widget/TextView;
    check-cast v10, Landroid/widget/TextView;

    .line 385
    .restart local v10       #tv:Landroid/widget/TextView;
    const v11, 0x7f090052

    move-object/from16 v0, p0

    invoke-virtual {v0, v11}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->findViewById(I)Landroid/view/View;

    move-result-object v7

    check-cast v7, Landroid/widget/LinearLayout;

    .line 386
    .local v7, parkingRow:Landroid/widget/LinearLayout;
    move-object/from16 v0, p0

    iget-object v11, v0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v11, v11, Lcom/openvehicles/OVMS/CarData;->Data_CarPoweredON:Z

    if-nez v11, :cond_12

    move-object/from16 v0, p0

    iget-object v11, v0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-wide v11, v11, Lcom/openvehicles/OVMS/CarData;->Data_ParkedTime_raw:D

    const-wide/16 v13, 0x0

    cmpl-double v11, v11, v13

    if-eqz v11, :cond_12

    .line 387
    const-string v6, ""

    .line 388
    .local v6, parkedTimeString:Ljava/lang/String;
    move-object/from16 v0, p0

    iget-object v11, v0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-wide v11, v11, Lcom/openvehicles/OVMS/CarData;->Data_ParkedTime_raw:D

    double-to-long v11, v11

    add-long v8, v11, v3

    .line 391
    .local v8, timeElapsed:J
    move-object/from16 v0, p0

    iget-object v11, v0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    new-instance v12, Ljava/util/Date;

    new-instance v13, Ljava/util/Date;

    invoke-direct {v13}, Ljava/util/Date;-><init>()V

    invoke-virtual {v13}, Ljava/util/Date;->getTime()J

    move-result-wide v13

    .line 392
    const-wide/16 v15, 0x3e8

    mul-long/2addr v15, v8

    sub-long/2addr v13, v15

    invoke-direct {v12, v13, v14}, Ljava/util/Date;-><init>(J)V

    .line 391
    iput-object v12, v11, Lcom/openvehicles/OVMS/CarData;->Data_ParkedTime:Ljava/util/Date;

    .line 394
    const-wide/16 v11, 0x3c

    cmp-long v11, v8, v11

    if-gez v11, :cond_9

    .line 397
    const-string v11, "just now"

    invoke-virtual {v10, v11}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 419
    :goto_2
    const/4 v11, 0x0

    invoke-virtual {v7, v11}, Landroid/widget/LinearLayout;->setVisibility(I)V

    goto/16 :goto_0

    .line 368
    .end local v6           #parkedTimeString:Ljava/lang/String;
    .end local v7           #parkingRow:Landroid/widget/LinearLayout;
    .end local v8           #timeElapsed:J
    :cond_2
    const-wide/16 v11, 0xe10

    cmp-long v11, v3, v11

    if-gez v11, :cond_4

    .line 369
    const-wide/16 v11, 0x3c

    div-long v11, v3, v11

    long-to-double v11, v11

    invoke-static {v11, v12}, Ljava/lang/Math;->ceil(D)D

    move-result-wide v11

    double-to-int v1, v11

    .line 370
    .local v1, displayValue:I
    const-string v12, "Updated: %d min%s ago"

    const/4 v11, 0x2

    new-array v13, v11, [Ljava/lang/Object;

    const/4 v11, 0x0

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v14

    aput-object v14, v13, v11

    const/4 v14, 0x1

    .line 371
    const/4 v11, 0x1

    if-le v1, v11, :cond_3

    const-string v11, "s"

    :goto_3
    aput-object v11, v13, v14

    .line 370
    invoke-static {v12, v13}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v10, v11}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_1

    .line 371
    :cond_3
    const-string v11, ""

    goto :goto_3

    .line 372
    .end local v1           #displayValue:I
    :cond_4
    const-wide/32 v11, 0x15180

    cmp-long v11, v3, v11

    if-gez v11, :cond_6

    .line 373
    const-wide/16 v11, 0xe10

    div-long v11, v3, v11

    long-to-double v11, v11

    invoke-static {v11, v12}, Ljava/lang/Math;->ceil(D)D

    move-result-wide v11

    double-to-int v1, v11

    .line 374
    .restart local v1       #displayValue:I
    const-string v12, "Updated: %d hr%s ago"

    const/4 v11, 0x2

    new-array v13, v11, [Ljava/lang/Object;

    const/4 v11, 0x0

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v14

    aput-object v14, v13, v11

    const/4 v14, 0x1

    .line 375
    const/4 v11, 0x1

    if-le v1, v11, :cond_5

    const-string v11, "s"

    :goto_4
    aput-object v11, v13, v14

    .line 374
    invoke-static {v12, v13}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v10, v11}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_1

    .line 375
    :cond_5
    const-string v11, ""

    goto :goto_4

    .line 376
    .end local v1           #displayValue:I
    :cond_6
    const-wide/32 v11, 0xd2f00

    cmp-long v11, v3, v11

    if-gez v11, :cond_8

    .line 377
    const-wide/32 v11, 0x15180

    div-long v11, v3, v11

    long-to-double v11, v11

    invoke-static {v11, v12}, Ljava/lang/Math;->ceil(D)D

    move-result-wide v11

    double-to-int v1, v11

    .line 378
    .restart local v1       #displayValue:I
    const-string v12, "Updated: %d day%s ago"

    const/4 v11, 0x2

    new-array v13, v11, [Ljava/lang/Object;

    const/4 v11, 0x0

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v14

    aput-object v14, v13, v11

    const/4 v14, 0x1

    .line 379
    const/4 v11, 0x1

    if-le v1, v11, :cond_7

    const-string v11, "s"

    :goto_5
    aput-object v11, v13, v14

    .line 378
    invoke-static {v12, v13}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v10, v11}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_1

    .line 379
    :cond_7
    const-string v11, ""

    goto :goto_5

    .line 381
    .end local v1           #displayValue:I
    :cond_8
    const v11, 0x7f060003

    move-object/from16 v0, p0

    invoke-virtual {v0, v11}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->getString(I)Ljava/lang/String;

    move-result-object v11

    const/4 v12, 0x1

    new-array v12, v12, [Ljava/lang/Object;

    const/4 v13, 0x0

    .line 382
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v14, v14, Lcom/openvehicles/OVMS/CarData;->Data_LastCarUpdate:Ljava/util/Date;

    aput-object v14, v12, v13

    .line 381
    invoke-static {v11, v12}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v10, v11}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_1

    .line 398
    .restart local v6       #parkedTimeString:Ljava/lang/String;
    .restart local v7       #parkingRow:Landroid/widget/LinearLayout;
    .restart local v8       #timeElapsed:J
    :cond_9
    const-wide/16 v11, 0xe10

    cmp-long v11, v8, v11

    if-gez v11, :cond_b

    .line 399
    const-wide/16 v11, 0x3c

    div-long v11, v8, v11

    long-to-double v11, v11

    invoke-static {v11, v12}, Ljava/lang/Math;->ceil(D)D

    move-result-wide v11

    double-to-int v1, v11

    .line 400
    .restart local v1       #displayValue:I
    const-string v12, "%d min%s"

    const/4 v11, 0x2

    new-array v13, v11, [Ljava/lang/Object;

    const/4 v11, 0x0

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v14

    aput-object v14, v13, v11

    const/4 v14, 0x1

    .line 401
    const/4 v11, 0x1

    if-le v1, v11, :cond_a

    const-string v11, "s"

    :goto_6
    aput-object v11, v13, v14

    .line 400
    invoke-static {v12, v13}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v10, v11}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_2

    .line 401
    :cond_a
    const-string v11, ""

    goto :goto_6

    .line 402
    .end local v1           #displayValue:I
    :cond_b
    const-wide/32 v11, 0x15180

    cmp-long v11, v8, v11

    if-gez v11, :cond_e

    .line 403
    const-wide/16 v11, 0xe10

    div-long v11, v8, v11

    long-to-double v11, v11

    invoke-static {v11, v12}, Ljava/lang/Math;->floor(D)D

    move-result-wide v11

    double-to-int v1, v11

    .line 405
    .restart local v1       #displayValue:I
    mul-int/lit16 v11, v1, 0xe10

    int-to-long v11, v11

    sub-long v11, v8, v11

    const-wide/16 v13, 0x3c

    div-long/2addr v11, v13

    invoke-static {v11, v12}, Ljava/lang/Math;->abs(J)J

    move-result-wide v11

    long-to-double v11, v11

    .line 404
    invoke-static {v11, v12}, Ljava/lang/Math;->ceil(D)D

    move-result-wide v11

    double-to-int v2, v11

    .line 406
    .local v2, displayValue2:I
    const-string v12, "%d hr%s %d min%s"

    const/4 v11, 0x4

    new-array v13, v11, [Ljava/lang/Object;

    const/4 v11, 0x0

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v14

    aput-object v14, v13, v11

    const/4 v14, 0x1

    .line 407
    const/4 v11, 0x1

    if-le v1, v11, :cond_c

    const-string v11, "s"

    :goto_7
    aput-object v11, v13, v14

    const/4 v11, 0x2

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v14

    aput-object v14, v13, v11

    const/4 v14, 0x3

    .line 408
    const/4 v11, 0x1

    if-le v2, v11, :cond_d

    const-string v11, "s"

    :goto_8
    aput-object v11, v13, v14

    .line 406
    invoke-static {v12, v13}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v10, v11}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_2

    .line 407
    :cond_c
    const-string v11, ""

    goto :goto_7

    .line 408
    :cond_d
    const-string v11, ""

    goto :goto_8

    .line 409
    .end local v1           #displayValue:I
    .end local v2           #displayValue2:I
    :cond_e
    const-wide/32 v11, 0xd2f00

    cmp-long v11, v8, v11

    if-gez v11, :cond_11

    .line 410
    const-wide/32 v11, 0x15180

    div-long v11, v8, v11

    long-to-double v11, v11

    invoke-static {v11, v12}, Ljava/lang/Math;->floor(D)D

    move-result-wide v11

    double-to-int v1, v11

    .line 412
    .restart local v1       #displayValue:I
    const v11, 0x15180

    mul-int/2addr v11, v1

    int-to-long v11, v11

    sub-long v11, v8, v11

    const-wide/16 v13, 0xe10

    div-long/2addr v11, v13

    invoke-static {v11, v12}, Ljava/lang/Math;->abs(J)J

    move-result-wide v11

    long-to-double v11, v11

    .line 411
    invoke-static {v11, v12}, Ljava/lang/Math;->ceil(D)D

    move-result-wide v11

    double-to-int v2, v11

    .line 413
    .restart local v2       #displayValue2:I
    const-string v12, "%d day%s %d hr%s"

    const/4 v11, 0x4

    new-array v13, v11, [Ljava/lang/Object;

    const/4 v11, 0x0

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v14

    aput-object v14, v13, v11

    const/4 v14, 0x1

    .line 414
    const/4 v11, 0x1

    if-le v1, v11, :cond_f

    const-string v11, "s"

    :goto_9
    aput-object v11, v13, v14

    const/4 v11, 0x2

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v14

    aput-object v14, v13, v11

    const/4 v14, 0x3

    .line 415
    const/4 v11, 0x1

    if-le v2, v11, :cond_10

    const-string v11, "s"

    :goto_a
    aput-object v11, v13, v14

    .line 413
    invoke-static {v12, v13}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v10, v11}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_2

    .line 414
    :cond_f
    const-string v11, ""

    goto :goto_9

    .line 415
    :cond_10
    const-string v11, ""

    goto :goto_a

    .line 417
    .end local v1           #displayValue:I
    .end local v2           #displayValue2:I
    :cond_11
    const-string v11, "%1$tD %1$tT"

    const/4 v12, 0x1

    new-array v12, v12, [Ljava/lang/Object;

    const/4 v13, 0x0

    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v14, v14, Lcom/openvehicles/OVMS/CarData;->Data_ParkedTime:Ljava/util/Date;

    aput-object v14, v12, v13

    invoke-static {v11, v12}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v10, v11}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_2

    .line 421
    .end local v6           #parkedTimeString:Ljava/lang/String;
    .end local v8           #timeElapsed:J
    :cond_12
    const/16 v11, 0x8

    invoke-virtual {v7, v11}, Landroid/widget/LinearLayout;->setVisibility(I)V

    goto/16 :goto_0
.end method

.method private updateMapUI()V
    .locals 11

    .prologue
    const/4 v10, 0x1

    const/4 v9, 0x0

    .line 811
    const-string v0, "OVMS"

    const-string v2, "Refreshing Map"

    invoke-static {v0, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 815
    :try_start_0
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->currentVehicleID:Ljava/lang/String;

    iget-object v2, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v2, v2, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    invoke-virtual {v0, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_0

    .line 816
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->currentVehicleID:Ljava/lang/String;

    .line 820
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    .line 821
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    .line 822
    new-instance v3, Ljava/lang/StringBuilder;

    iget-object v4, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v4, v4, Lcom/openvehicles/OVMS/CarData;->VehicleImageDrawable:Ljava/lang/String;

    invoke-static {v4}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v4

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v4, "32x32"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    const-string v4, "drawable"

    .line 823
    const-string v5, "com.openvehicles.OVMS"

    .line 821
    invoke-virtual {v2, v3, v4, v5}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v2

    .line 820
    invoke-virtual {v0, v2}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    .line 825
    .local v1, drawable:Landroid/graphics/drawable/Drawable;
    new-instance v0, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;

    const/16 v2, 0x14

    iget-object v4, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->DirectionalMarker:Landroid/graphics/Bitmap;

    const/4 v5, 0x1

    move-object v3, p0

    invoke-direct/range {v0 .. v5}, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;-><init>(Landroid/graphics/drawable/Drawable;ILandroid/content/Context;Landroid/graphics/Bitmap;I)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->carMarkers:Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;

    .line 826
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->mapOverlays:Ljava/util/List;

    const/4 v2, 0x0

    iget-object v3, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->carMarkers:Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;

    invoke-interface {v0, v2, v3}, Ljava/util/List;->set(ILjava/lang/Object;)Ljava/lang/Object;
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 830
    .end local v1           #drawable:Landroid/graphics/drawable/Drawable;
    :cond_0
    :goto_0
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    invoke-static {v0}, Lcom/openvehicles/OVMS/Utilities;->GetCarGeopoint(Lcom/openvehicles/OVMS/CarData;)Lcom/google/android/maps/GeoPoint;

    move-result-object v6

    .line 832
    .local v6, carLocation:Lcom/google/android/maps/GeoPoint;
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->carMarkers:Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;

    invoke-virtual {v0}, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->size()I

    move-result v0

    if-eqz v0, :cond_1

    .line 833
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->carMarkers:Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;

    invoke-virtual {v0, v9}, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->getItem(I)Lcom/google/android/maps/OverlayItem;

    move-result-object v0

    invoke-virtual {v0}, Lcom/google/android/maps/OverlayItem;->getPoint()Lcom/google/android/maps/GeoPoint;

    move-result-object v0

    invoke-virtual {v0, v6}, Lcom/google/android/maps/GeoPoint;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_2

    .line 835
    :cond_1
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->carMarkers:Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;

    invoke-virtual {v0}, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->size()I

    move-result v0

    if-lez v0, :cond_3

    .line 839
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->carMarkers:Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;

    invoke-virtual {v0, v9}, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->getItem(I)Lcom/google/android/maps/OverlayItem;

    move-result-object v0

    invoke-virtual {v0}, Lcom/google/android/maps/OverlayItem;->getPoint()Lcom/google/android/maps/GeoPoint;

    move-result-object v0

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->lastCarGeoPoint:Lcom/google/android/maps/GeoPoint;

    .line 842
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->carMarkerAnimationTimerHandler:Landroid/os/Handler;

    .line 843
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->animateCarMarker:Ljava/lang/Runnable;

    invoke-virtual {v0, v2}, Landroid/os/Handler;->removeCallbacks(Ljava/lang/Runnable;)V

    .line 846
    iput v9, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->carMarkerAnimationFrame:I

    .line 847
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->carMarkerAnimationTimerHandler:Landroid/os/Handler;

    iget-object v2, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->animateCarMarker:Ljava/lang/Runnable;

    const-wide/16 v3, 0x0

    invoke-virtual {v0, v2, v3, v4}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    .line 867
    :cond_2
    :goto_1
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->mc:Lcom/google/android/maps/MapController;

    invoke-virtual {v0, v6}, Lcom/google/android/maps/MapController;->animateTo(Lcom/google/android/maps/GeoPoint;)V

    .line 868
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->mc:Lcom/google/android/maps/MapController;

    const/16 v2, 0x11

    invoke-virtual {v0, v2}, Lcom/google/android/maps/MapController;->setZoom(I)I

    .line 869
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->mapView:Lcom/google/android/maps/MapView;

    invoke-virtual {v0}, Lcom/google/android/maps/MapView;->invalidate()V

    .line 870
    return-void

    .line 853
    :cond_3
    const-string v7, "-"

    .line 854
    .local v7, lastReportDate:Ljava/lang/String;
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->Data_LastCarUpdate:Ljava/util/Date;

    if-eqz v0, :cond_4

    .line 855
    new-instance v0, Ljava/text/SimpleDateFormat;

    const-string v2, "MMM d, K:mm:ss a"

    invoke-direct {v0, v2}, Ljava/text/SimpleDateFormat;-><init>(Ljava/lang/String;)V

    .line 856
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v2, v2, Lcom/openvehicles/OVMS/CarData;->Data_LastCarUpdate:Ljava/util/Date;

    invoke-virtual {v0, v2}, Ljava/text/SimpleDateFormat;->format(Ljava/util/Date;)Ljava/lang/String;

    move-result-object v7

    .line 858
    :cond_4
    new-instance v8, Lcom/openvehicles/OVMS/Utilities$CarMarker;

    .line 859
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    .line 860
    const-string v2, "Last reported: %s"

    new-array v3, v10, [Ljava/lang/Object;

    aput-object v7, v3, v9

    .line 859
    invoke-static {v2, v3}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    .line 861
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    iget-wide v3, v3, Lcom/openvehicles/OVMS/CarData;->Data_Direction:D

    double-to-int v3, v3

    .line 858
    invoke-direct {v8, v6, v0, v2, v3}, Lcom/openvehicles/OVMS/Utilities$CarMarker;-><init>(Lcom/google/android/maps/GeoPoint;Ljava/lang/String;Ljava/lang/String;I)V

    .line 862
    .local v8, overlayitem:Lcom/openvehicles/OVMS/Utilities$CarMarker;
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->carMarkers:Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;

    invoke-virtual {v0, v8}, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->addOverlay(Lcom/google/android/maps/OverlayItem;)V

    goto :goto_1

    .line 828
    .end local v6           #carLocation:Lcom/google/android/maps/GeoPoint;
    .end local v7           #lastReportDate:Ljava/lang/String;
    .end local v8           #overlayitem:Lcom/openvehicles/OVMS/Utilities$CarMarker;
    :catch_0
    move-exception v0

    goto/16 :goto_0
.end method


# virtual methods
.method public OrientationChanged()V
    .locals 2

    .prologue
    .line 929
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->orientationChangedHandler:Landroid/os/Handler;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    .line 930
    return-void
.end method

.method public Refresh(Lcom/openvehicles/OVMS/CarData;Z)V
    .locals 2
    .parameter "carData"
    .parameter "isLoggedIn"

    .prologue
    .line 922
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;

    .line 923
    iput-boolean p2, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->isLoggedIn:Z

    .line 924
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->handler:Landroid/os/Handler;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    .line 926
    return-void
.end method

.method protected isRouteDisplayed()Z
    .locals 1

    .prologue
    .line 918
    const/4 v0, 0x0

    return v0
.end method

.method public onCreate(Landroid/os/Bundle;)V
    .locals 1
    .parameter "savedInstanceState"

    .prologue
    .line 46
    invoke-super {p0, p1}, Lcom/google/android/maps/MapActivity;->onCreate(Landroid/os/Bundle;)V

    .line 47
    const v0, 0x7f03000f

    invoke-virtual {p0, v0}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->setContentView(I)V

    .line 49
    invoke-direct {p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->initInfoUI()V

    .line 50
    invoke-direct {p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->initCarLayoutUI()V

    .line 51
    invoke-direct {p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->initMapUI()V

    .line 52
    return-void
.end method

.method protected onPause()V
    .locals 2

    .prologue
    .line 335
    invoke-super {p0}, Lcom/google/android/maps/MapActivity;->onPause()V

    .line 341
    :try_start_0
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->softwareInformation:Landroid/app/AlertDialog;

    if-eqz v0, :cond_0

    .line 342
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->softwareInformation:Landroid/app/AlertDialog;

    invoke-virtual {v0}, Landroid/app/AlertDialog;->isShowing()Z

    move-result v0

    if-eqz v0, :cond_0

    .line 343
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->softwareInformation:Landroid/app/AlertDialog;

    invoke-virtual {v0}, Landroid/app/AlertDialog;->dismiss()V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_1

    .line 348
    :cond_0
    :goto_0
    :try_start_1
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->lastUpdatedDialog:Landroid/app/AlertDialog;

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->lastUpdatedDialog:Landroid/app/AlertDialog;

    invoke-virtual {v0}, Landroid/app/AlertDialog;->isShowing()Z

    move-result v0

    if-eqz v0, :cond_1

    .line 349
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->lastUpdatedDialog:Landroid/app/AlertDialog;

    invoke-virtual {v0}, Landroid/app/AlertDialog;->dismiss()V
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_0

    .line 354
    :cond_1
    :goto_1
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->lastUpdateTimerHandler:Landroid/os/Handler;

    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->lastUpdateTimer:Ljava/lang/Runnable;

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeCallbacks(Ljava/lang/Runnable;)V

    .line 355
    return-void

    .line 350
    :catch_0
    move-exception v0

    goto :goto_1

    .line 344
    :catch_1
    move-exception v0

    goto :goto_0
.end method

.method protected onResume()V
    .locals 4

    .prologue
    .line 324
    invoke-super {p0}, Lcom/google/android/maps/MapActivity;->onResume()V

    .line 330
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->lastUpdateTimerHandler:Landroid/os/Handler;

    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge;->lastUpdateTimer:Ljava/lang/Runnable;

    const-wide/16 v2, 0x1388

    invoke-virtual {v0, v1, v2, v3}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    .line 331
    return-void
.end method
