.class Lcom/openvehicles/OVMS/TabMap$TouchOverlay;
.super Lcom/google/android/maps/Overlay;
.source "TabMap.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/openvehicles/OVMS/TabMap;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "TouchOverlay"
.end annotation


# instance fields
.field final synthetic this$0:Lcom/openvehicles/OVMS/TabMap;


# direct methods
.method private constructor <init>(Lcom/openvehicles/OVMS/TabMap;)V
    .locals 0
    .parameter

    .prologue
    .line 847
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabMap$TouchOverlay;->this$0:Lcom/openvehicles/OVMS/TabMap;

    invoke-direct {p0}, Lcom/google/android/maps/Overlay;-><init>()V

    return-void
.end method

.method synthetic constructor <init>(Lcom/openvehicles/OVMS/TabMap;Lcom/openvehicles/OVMS/TabMap$TouchOverlay;)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 847
    invoke-direct {p0, p1}, Lcom/openvehicles/OVMS/TabMap$TouchOverlay;-><init>(Lcom/openvehicles/OVMS/TabMap;)V

    return-void
.end method


# virtual methods
.method public onTouchEvent(Landroid/view/MotionEvent;Lcom/google/android/maps/MapView;)Z
    .locals 11
    .parameter "e"
    .parameter "mapView"

    .prologue
    const/4 v10, 0x4

    const-wide/high16 v8, 0x4000

    .line 850
    invoke-virtual {p1}, Landroid/view/MotionEvent;->getAction()I

    move-result v4

    const/4 v5, 0x1

    if-ne v4, v5, :cond_1

    .line 857
    iget-object v4, p0, Lcom/openvehicles/OVMS/TabMap$TouchOverlay;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #calls: Lcom/openvehicles/OVMS/TabMap;->hidePopup()V
    invoke-static {v4}, Lcom/openvehicles/OVMS/TabMap;->access$17(Lcom/openvehicles/OVMS/TabMap;)V

    .line 859
    iget-object v4, p0, Lcom/openvehicles/OVMS/TabMap$TouchOverlay;->this$0:Lcom/openvehicles/OVMS/TabMap;

    iget-object v4, v4, Lcom/openvehicles/OVMS/TabMap;->centeringMode:Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;

    invoke-virtual {v4}, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;->getMode()I

    move-result v4

    if-eq v4, v10, :cond_0

    .line 862
    invoke-virtual {p1}, Landroid/view/MotionEvent;->getX()F

    move-result v2

    .line 863
    .local v2, newX:F
    invoke-virtual {p1}, Landroid/view/MotionEvent;->getY()F

    move-result v3

    .line 866
    .local v3, newY:F
    iget-object v4, p0, Lcom/openvehicles/OVMS/TabMap$TouchOverlay;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->mapDragLastX:F
    invoke-static {v4}, Lcom/openvehicles/OVMS/TabMap;->access$18(Lcom/openvehicles/OVMS/TabMap;)F

    move-result v4

    sub-float v4, v2, v4

    float-to-double v4, v4

    invoke-static {v4, v5, v8, v9}, Ljava/lang/Math;->pow(DD)D

    move-result-wide v4

    iget-object v6, p0, Lcom/openvehicles/OVMS/TabMap$TouchOverlay;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->mapDragLastY:F
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabMap;->access$19(Lcom/openvehicles/OVMS/TabMap;)F

    move-result v6

    sub-float v6, v3, v6

    float-to-double v6, v6

    invoke-static {v6, v7, v8, v9}, Ljava/lang/Math;->pow(DD)D

    move-result-wide v6

    add-double/2addr v4, v6

    invoke-static {v4, v5}, Ljava/lang/Math;->sqrt(D)D

    move-result-wide v0

    .line 867
    .local v0, distance:D
    const-wide/high16 v4, 0x4059

    cmpl-double v4, v0, v4

    if-lez v4, :cond_0

    .line 868
    iget-object v4, p0, Lcom/openvehicles/OVMS/TabMap$TouchOverlay;->this$0:Lcom/openvehicles/OVMS/TabMap;

    iget-object v4, v4, Lcom/openvehicles/OVMS/TabMap;->centeringMode:Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;

    invoke-virtual {v4, v10}, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;->setMode(I)V

    .line 881
    .end local v0           #distance:D
    .end local v2           #newX:F
    .end local v3           #newY:F
    :cond_0
    :goto_0
    const/4 v4, 0x0

    return v4

    .line 870
    :cond_1
    invoke-virtual {p1}, Landroid/view/MotionEvent;->getAction()I

    move-result v4

    if-nez v4, :cond_0

    .line 872
    iget-object v4, p0, Lcom/openvehicles/OVMS/TabMap$TouchOverlay;->this$0:Lcom/openvehicles/OVMS/TabMap;

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getX()F

    move-result v5

    #setter for: Lcom/openvehicles/OVMS/TabMap;->mapDragLastX:F
    invoke-static {v4, v5}, Lcom/openvehicles/OVMS/TabMap;->access$20(Lcom/openvehicles/OVMS/TabMap;F)V

    .line 873
    iget-object v4, p0, Lcom/openvehicles/OVMS/TabMap$TouchOverlay;->this$0:Lcom/openvehicles/OVMS/TabMap;

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getY()F

    move-result v5

    #setter for: Lcom/openvehicles/OVMS/TabMap;->mapDragLastY:F
    invoke-static {v4, v5}, Lcom/openvehicles/OVMS/TabMap;->access$21(Lcom/openvehicles/OVMS/TabMap;F)V

    goto :goto_0
.end method
