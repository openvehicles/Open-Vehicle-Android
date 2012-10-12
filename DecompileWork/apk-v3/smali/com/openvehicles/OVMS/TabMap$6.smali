.class Lcom/openvehicles/OVMS/TabMap$6;
.super Landroid/os/Handler;
.source "TabMap.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/openvehicles/OVMS/TabMap;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/openvehicles/OVMS/TabMap;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/TabMap;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    .line 1036
    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 24
    .parameter "msg"

    .prologue
    .line 1038
    const-string v18, "OVMS"

    const-string v19, "Refreshing Map"

    invoke-static/range {v18 .. v19}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1040
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static/range {v18 .. v18}, Lcom/openvehicles/OVMS/TabMap;->access$4(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v18

    invoke-static/range {v18 .. v18}, Lcom/openvehicles/OVMS/Utilities;->GetCarGeopoint(Lcom/openvehicles/OVMS/CarData;)Lcom/google/android/maps/GeoPoint;

    move-result-object v5

    .line 1042
    .local v5, carLocation:Lcom/google/android/maps/GeoPoint;
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->carMarkers:Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;
    invoke-static/range {v18 .. v18}, Lcom/openvehicles/OVMS/TabMap;->access$5(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;

    move-result-object v18

    invoke-virtual/range {v18 .. v18}, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->size()I

    move-result v18

    if-lez v18, :cond_4

    .line 1046
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v19, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->carMarkers:Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;
    invoke-static/range {v19 .. v19}, Lcom/openvehicles/OVMS/TabMap;->access$5(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;

    move-result-object v19

    invoke-virtual/range {v19 .. v19}, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->size()I

    move-result v19

    move/from16 v0, v19

    new-array v0, v0, [Lcom/google/android/maps/GeoPoint;

    move-object/from16 v19, v0

    #setter for: Lcom/openvehicles/OVMS/TabMap;->lastCarGeoPoints:[Lcom/google/android/maps/GeoPoint;
    invoke-static/range {v18 .. v19}, Lcom/openvehicles/OVMS/TabMap;->access$12(Lcom/openvehicles/OVMS/TabMap;[Lcom/google/android/maps/GeoPoint;)V

    .line 1047
    const/4 v12, 0x0

    .local v12, i:I
    :goto_0
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->carMarkers:Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;
    invoke-static/range {v18 .. v18}, Lcom/openvehicles/OVMS/TabMap;->access$5(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;

    move-result-object v18

    invoke-virtual/range {v18 .. v18}, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->size()I

    move-result v18

    move/from16 v0, v18

    if-lt v12, v0, :cond_3

    .line 1051
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->carMarkerAnimationTimerHandler:Landroid/os/Handler;
    invoke-static/range {v18 .. v18}, Lcom/openvehicles/OVMS/TabMap;->access$10(Lcom/openvehicles/OVMS/TabMap;)Landroid/os/Handler;

    move-result-object v18

    .line 1052
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v19, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->animateCarMarker:Ljava/lang/Runnable;
    invoke-static/range {v19 .. v19}, Lcom/openvehicles/OVMS/TabMap;->access$11(Lcom/openvehicles/OVMS/TabMap;)Ljava/lang/Runnable;

    move-result-object v19

    invoke-virtual/range {v18 .. v19}, Landroid/os/Handler;->removeCallbacks(Ljava/lang/Runnable;)V

    .line 1053
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    const/16 v19, 0x0

    #setter for: Lcom/openvehicles/OVMS/TabMap;->carMarkerAnimationFrame:I
    invoke-static/range {v18 .. v19}, Lcom/openvehicles/OVMS/TabMap;->access$9(Lcom/openvehicles/OVMS/TabMap;I)V

    .line 1054
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->carMarkerAnimationTimerHandler:Landroid/os/Handler;
    invoke-static/range {v18 .. v18}, Lcom/openvehicles/OVMS/TabMap;->access$10(Lcom/openvehicles/OVMS/TabMap;)Landroid/os/Handler;

    move-result-object v18

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v19, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->animateCarMarker:Ljava/lang/Runnable;
    invoke-static/range {v19 .. v19}, Lcom/openvehicles/OVMS/TabMap;->access$11(Lcom/openvehicles/OVMS/TabMap;)Ljava/lang/Runnable;

    move-result-object v19

    const-wide/16 v20, 0x0

    invoke-virtual/range {v18 .. v21}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    .line 1072
    .end local v12           #i:I
    :goto_1
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    const v19, 0x7f090083

    invoke-virtual/range {v18 .. v19}, Lcom/openvehicles/OVMS/TabMap;->findViewById(I)Landroid/view/View;

    move-result-object v16

    check-cast v16, Landroid/widget/TextView;

    .line 1073
    .local v16, tv:Landroid/widget/TextView;
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static/range {v18 .. v18}, Lcom/openvehicles/OVMS/TabMap;->access$4(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v18

    move-object/from16 v0, v18

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->Data_Parameters:Ljava/util/LinkedHashMap;

    move-object/from16 v18, v0

    .line 1074
    const/16 v19, 0xb

    invoke-static/range {v19 .. v19}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v19

    invoke-virtual/range {v18 .. v19}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    check-cast v11, Ljava/lang/String;

    .line 1075
    .local v11, groupName:Ljava/lang/String;
    if-eqz v11, :cond_8

    invoke-virtual {v11}, Ljava/lang/String;->length()I

    move-result v18

    if-lez v18, :cond_8

    .line 1076
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static/range {v18 .. v18}, Lcom/openvehicles/OVMS/TabMap;->access$4(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v18

    move-object/from16 v0, v18

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->Group:Ljava/util/HashMap;

    move-object/from16 v18, v0

    if-eqz v18, :cond_7

    .line 1077
    const-string v19, "Group: %s (%s vehicle%s)"

    const/16 v18, 0x3

    move/from16 v0, v18

    new-array v0, v0, [Ljava/lang/Object;

    move-object/from16 v20, v0

    const/16 v18, 0x0

    .line 1078
    aput-object v11, v20, v18

    const/16 v18, 0x1

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v21, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static/range {v21 .. v21}, Lcom/openvehicles/OVMS/TabMap;->access$4(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v21

    move-object/from16 v0, v21

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->Group:Ljava/util/HashMap;

    move-object/from16 v21, v0

    invoke-virtual/range {v21 .. v21}, Ljava/util/HashMap;->size()I

    move-result v21

    invoke-static/range {v21 .. v21}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v21

    aput-object v21, v20, v18

    const/16 v21, 0x2

    .line 1079
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static/range {v18 .. v18}, Lcom/openvehicles/OVMS/TabMap;->access$4(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v18

    move-object/from16 v0, v18

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->Group:Ljava/util/HashMap;

    move-object/from16 v18, v0

    invoke-virtual/range {v18 .. v18}, Ljava/util/HashMap;->size()I

    move-result v18

    const/16 v22, 0x1

    move/from16 v0, v18

    move/from16 v1, v22

    if-le v0, v1, :cond_6

    const-string v18, "s"

    :goto_2
    aput-object v18, v20, v21

    .line 1077
    invoke-static/range {v19 .. v20}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v18

    move-object/from16 v0, v16

    move-object/from16 v1, v18

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 1083
    :goto_3
    const/16 v18, 0x0

    move-object/from16 v0, v16

    move/from16 v1, v18

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setVisibility(I)V

    .line 1088
    :goto_4
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static/range {v18 .. v18}, Lcom/openvehicles/OVMS/TabMap;->access$4(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v18

    move-object/from16 v0, v18

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->Group:Ljava/util/HashMap;

    move-object/from16 v18, v0

    if-eqz v18, :cond_1

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static/range {v18 .. v18}, Lcom/openvehicles/OVMS/TabMap;->access$4(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v18

    move-object/from16 v0, v18

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->Group:Ljava/util/HashMap;

    move-object/from16 v18, v0

    invoke-virtual/range {v18 .. v18}, Ljava/util/HashMap;->size()I

    move-result v18

    if-lez v18, :cond_1

    .line 1089
    const/4 v9, 0x1

    .line 1090
    .local v9, groupCarIndex:I
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static/range {v18 .. v18}, Lcom/openvehicles/OVMS/TabMap;->access$4(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v18

    move-object/from16 v0, v18

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->Group:Ljava/util/HashMap;

    move-object/from16 v18, v0

    invoke-virtual/range {v18 .. v18}, Ljava/util/HashMap;->keySet()Ljava/util/Set;

    move-result-object v18

    invoke-interface/range {v18 .. v18}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v18

    :cond_0
    :goto_5
    invoke-interface/range {v18 .. v18}, Ljava/util/Iterator;->hasNext()Z

    move-result v19

    if-nez v19, :cond_9

    .line 1132
    .end local v9           #groupCarIndex:I
    :cond_1
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    move-object/from16 v0, v18

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap;->centeringMode:Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;

    move-object/from16 v18, v0

    invoke-virtual/range {v18 .. v18}, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;->getMode()I

    move-result v18

    packed-switch v18, :pswitch_data_0

    .line 1161
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->mc:Lcom/google/android/maps/MapController;
    invoke-static/range {v18 .. v18}, Lcom/openvehicles/OVMS/TabMap;->access$13(Lcom/openvehicles/OVMS/TabMap;)Lcom/google/android/maps/MapController;

    move-result-object v18

    move-object/from16 v0, v18

    invoke-virtual {v0, v5}, Lcom/google/android/maps/MapController;->animateTo(Lcom/google/android/maps/GeoPoint;)V

    .line 1162
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->mc:Lcom/google/android/maps/MapController;
    invoke-static/range {v18 .. v18}, Lcom/openvehicles/OVMS/TabMap;->access$13(Lcom/openvehicles/OVMS/TabMap;)Lcom/google/android/maps/MapController;

    move-result-object v18

    const/16 v19, 0x12

    invoke-virtual/range {v18 .. v19}, Lcom/google/android/maps/MapController;->setZoom(I)I

    .line 1167
    :goto_6
    :pswitch_0
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    const v19, 0x7f09008e

    invoke-virtual/range {v18 .. v19}, Lcom/openvehicles/OVMS/TabMap;->findViewById(I)Landroid/view/View;

    move-result-object v14

    check-cast v14, Landroid/widget/ListView;

    .line 1169
    .local v14, lv:Landroid/widget/ListView;
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static/range {v18 .. v18}, Lcom/openvehicles/OVMS/TabMap;->access$4(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v18

    move-object/from16 v0, v18

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->Group:Ljava/util/HashMap;

    move-object/from16 v18, v0

    if-eqz v18, :cond_f

    .line 1171
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static/range {v18 .. v18}, Lcom/openvehicles/OVMS/TabMap;->access$4(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v18

    move-object/from16 v0, v18

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->Group:Ljava/util/HashMap;

    move-object/from16 v18, v0

    invoke-virtual/range {v18 .. v18}, Ljava/util/HashMap;->clone()Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Ljava/util/HashMap;

    .line 1172
    .local v4, cachedGroupCarsData:Ljava/util/HashMap;,"Ljava/util/HashMap<Ljava/lang/String;Lcom/openvehicles/OVMS/CarData_Group;>;"
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static/range {v18 .. v18}, Lcom/openvehicles/OVMS/TabMap;->access$4(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v18

    move-object/from16 v0, v18

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    move-object/from16 v18, v0

    move-object/from16 v0, v18

    invoke-virtual {v4, v0}, Ljava/util/HashMap;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    .line 1174
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    new-instance v19, Lcom/openvehicles/OVMS/TabMap$GroupCarsListViewAdapter;

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v20, v0

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v21, v0

    const v22, 0x7f030012

    invoke-virtual {v4}, Ljava/util/HashMap;->values()Ljava/util/Collection;

    move-result-object v23

    invoke-interface/range {v23 .. v23}, Ljava/util/Collection;->toArray()[Ljava/lang/Object;

    move-result-object v23

    invoke-direct/range {v19 .. v23}, Lcom/openvehicles/OVMS/TabMap$GroupCarsListViewAdapter;-><init>(Lcom/openvehicles/OVMS/TabMap;Landroid/content/Context;I[Ljava/lang/Object;)V

    #setter for: Lcom/openvehicles/OVMS/TabMap;->groupCarsListAdapter:Lcom/openvehicles/OVMS/TabMap$GroupCarsListViewAdapter;
    invoke-static/range {v18 .. v19}, Lcom/openvehicles/OVMS/TabMap;->access$14(Lcom/openvehicles/OVMS/TabMap;Lcom/openvehicles/OVMS/TabMap$GroupCarsListViewAdapter;)V

    .line 1175
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->groupCarsListAdapter:Lcom/openvehicles/OVMS/TabMap$GroupCarsListViewAdapter;
    invoke-static/range {v18 .. v18}, Lcom/openvehicles/OVMS/TabMap;->access$15(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/TabMap$GroupCarsListViewAdapter;

    move-result-object v18

    move-object/from16 v0, v18

    invoke-virtual {v14, v0}, Landroid/widget/ListView;->setAdapter(Landroid/widget/ListAdapter;)V

    .line 1182
    .end local v4           #cachedGroupCarsData:Ljava/util/HashMap;,"Ljava/util/HashMap<Ljava/lang/String;Lcom/openvehicles/OVMS/CarData_Group;>;"
    :cond_2
    :goto_7
    const-string v18, "Routing"

    new-instance v19, Ljava/lang/StringBuilder;

    const-string v20, "Redrawing Map with "

    invoke-direct/range {v19 .. v20}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 1183
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v20, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->mapOverlays:Ljava/util/List;
    invoke-static/range {v20 .. v20}, Lcom/openvehicles/OVMS/TabMap;->access$16(Lcom/openvehicles/OVMS/TabMap;)Ljava/util/List;

    move-result-object v20

    invoke-interface/range {v20 .. v20}, Ljava/util/List;->size()I

    move-result v20

    add-int/lit8 v20, v20, -0x3

    invoke-virtual/range {v19 .. v20}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v19

    .line 1184
    const-string v20, " waypoints"

    invoke-virtual/range {v19 .. v20}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v19

    invoke-virtual/range {v19 .. v19}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v19

    .line 1182
    invoke-static/range {v18 .. v19}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1185
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->mapView:Lcom/google/android/maps/MapView;
    invoke-static/range {v18 .. v18}, Lcom/openvehicles/OVMS/TabMap;->access$8(Lcom/openvehicles/OVMS/TabMap;)Lcom/google/android/maps/MapView;

    move-result-object v18

    invoke-virtual/range {v18 .. v18}, Lcom/google/android/maps/MapView;->invalidate()V

    .line 1186
    return-void

    .line 1048
    .end local v11           #groupName:Ljava/lang/String;
    .end local v14           #lv:Landroid/widget/ListView;
    .end local v16           #tv:Landroid/widget/TextView;
    .restart local v12       #i:I
    :cond_3
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->lastCarGeoPoints:[Lcom/google/android/maps/GeoPoint;
    invoke-static/range {v18 .. v18}, Lcom/openvehicles/OVMS/TabMap;->access$6(Lcom/openvehicles/OVMS/TabMap;)[Lcom/google/android/maps/GeoPoint;

    move-result-object v18

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v19, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->carMarkers:Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;
    invoke-static/range {v19 .. v19}, Lcom/openvehicles/OVMS/TabMap;->access$5(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;

    move-result-object v19

    move-object/from16 v0, v19

    invoke-virtual {v0, v12}, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->getItem(I)Lcom/google/android/maps/OverlayItem;

    move-result-object v19

    invoke-virtual/range {v19 .. v19}, Lcom/google/android/maps/OverlayItem;->getPoint()Lcom/google/android/maps/GeoPoint;

    move-result-object v19

    aput-object v19, v18, v12

    .line 1047
    add-int/lit8 v12, v12, 0x1

    goto/16 :goto_0

    .line 1059
    .end local v12           #i:I
    :cond_4
    const-string v13, "-"

    .line 1060
    .local v13, lastReportDate:Ljava/lang/String;
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static/range {v18 .. v18}, Lcom/openvehicles/OVMS/TabMap;->access$4(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v18

    move-object/from16 v0, v18

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->Data_LastCarUpdate:Ljava/util/Date;

    move-object/from16 v18, v0

    if-eqz v18, :cond_5

    .line 1061
    new-instance v18, Ljava/text/SimpleDateFormat;

    const-string v19, "MMM d, K:mm:ss a"

    invoke-direct/range {v18 .. v19}, Ljava/text/SimpleDateFormat;-><init>(Ljava/lang/String;)V

    .line 1062
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v19, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static/range {v19 .. v19}, Lcom/openvehicles/OVMS/TabMap;->access$4(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v19

    move-object/from16 v0, v19

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->Data_LastCarUpdate:Ljava/util/Date;

    move-object/from16 v19, v0

    invoke-virtual/range {v18 .. v19}, Ljava/text/SimpleDateFormat;->format(Ljava/util/Date;)Ljava/lang/String;

    move-result-object v13

    .line 1064
    :cond_5
    new-instance v15, Lcom/openvehicles/OVMS/Utilities$CarMarker;

    .line 1065
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static/range {v18 .. v18}, Lcom/openvehicles/OVMS/TabMap;->access$4(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v18

    move-object/from16 v0, v18

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    move-object/from16 v18, v0

    .line 1066
    const-string v19, "Last reported: %s"

    const/16 v20, 0x1

    move/from16 v0, v20

    new-array v0, v0, [Ljava/lang/Object;

    move-object/from16 v20, v0

    const/16 v21, 0x0

    aput-object v13, v20, v21

    .line 1065
    invoke-static/range {v19 .. v20}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v19

    .line 1067
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v20, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static/range {v20 .. v20}, Lcom/openvehicles/OVMS/TabMap;->access$4(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v20

    move-object/from16 v0, v20

    iget-wide v0, v0, Lcom/openvehicles/OVMS/CarData;->Data_Direction:D

    move-wide/from16 v20, v0

    move-wide/from16 v0, v20

    double-to-int v0, v0

    move/from16 v20, v0

    .line 1064
    move-object/from16 v0, v18

    move-object/from16 v1, v19

    move/from16 v2, v20

    invoke-direct {v15, v5, v0, v1, v2}, Lcom/openvehicles/OVMS/Utilities$CarMarker;-><init>(Lcom/google/android/maps/GeoPoint;Ljava/lang/String;Ljava/lang/String;I)V

    .line 1068
    .local v15, overlayitem:Lcom/openvehicles/OVMS/Utilities$CarMarker;
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->carMarkers:Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;
    invoke-static/range {v18 .. v18}, Lcom/openvehicles/OVMS/TabMap;->access$5(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;

    move-result-object v18

    move-object/from16 v0, v18

    invoke-virtual {v0, v15}, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->addOverlay(Lcom/google/android/maps/OverlayItem;)V

    goto/16 :goto_1

    .line 1079
    .end local v13           #lastReportDate:Ljava/lang/String;
    .end local v15           #overlayitem:Lcom/openvehicles/OVMS/Utilities$CarMarker;
    .restart local v11       #groupName:Ljava/lang/String;
    .restart local v16       #tv:Landroid/widget/TextView;
    :cond_6
    const-string v18, ""

    goto/16 :goto_2

    .line 1081
    :cond_7
    const-string v18, "Group: %s"

    const/16 v19, 0x1

    move/from16 v0, v19

    new-array v0, v0, [Ljava/lang/Object;

    move-object/from16 v19, v0

    const/16 v20, 0x0

    aput-object v11, v19, v20

    invoke-static/range {v18 .. v19}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v18

    move-object/from16 v0, v16

    move-object/from16 v1, v18

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_3

    .line 1085
    :cond_8
    const/16 v18, 0x8

    move-object/from16 v0, v16

    move/from16 v1, v18

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setVisibility(I)V

    goto/16 :goto_4

    .line 1090
    .restart local v9       #groupCarIndex:I
    :cond_9
    invoke-interface/range {v18 .. v18}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v17

    check-cast v17, Ljava/lang/String;

    .line 1093
    .local v17, vehicleID:Ljava/lang/String;
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v19, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static/range {v19 .. v19}, Lcom/openvehicles/OVMS/TabMap;->access$4(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v19

    move-object/from16 v0, v19

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    move-object/from16 v19, v0

    move-object/from16 v0, v17

    move-object/from16 v1, v19

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v19

    if-nez v19, :cond_0

    .line 1096
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v19, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static/range {v19 .. v19}, Lcom/openvehicles/OVMS/TabMap;->access$4(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v19

    move-object/from16 v0, v19

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->Group:Ljava/util/HashMap;

    move-object/from16 v19, v0

    move-object/from16 v0, v19

    move-object/from16 v1, v17

    invoke-virtual {v0, v1}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v8

    check-cast v8, Lcom/openvehicles/OVMS/CarData_Group;

    .line 1098
    .local v8, groupCar:Lcom/openvehicles/OVMS/CarData_Group;
    iget-wide v0, v8, Lcom/openvehicles/OVMS/CarData_Group;->Latitude:D

    move-wide/from16 v19, v0

    iget-wide v0, v8, Lcom/openvehicles/OVMS/CarData_Group;->Longitude:D

    move-wide/from16 v21, v0

    .line 1097
    invoke-static/range {v19 .. v22}, Lcom/openvehicles/OVMS/Utilities;->GetCarGeopoint(DD)Lcom/google/android/maps/GeoPoint;

    move-result-object v10

    .line 1100
    .local v10, groupCarLocation:Lcom/google/android/maps/GeoPoint;
    new-instance v6, Lcom/openvehicles/OVMS/Utilities$CarMarker;

    .line 1101
    iget-object v0, v8, Lcom/openvehicles/OVMS/CarData_Group;->VehicleID:Ljava/lang/String;

    move-object/from16 v19, v0

    const-string v20, ""

    .line 1102
    iget-wide v0, v8, Lcom/openvehicles/OVMS/CarData_Group;->Direction:D

    move-wide/from16 v21, v0

    move-wide/from16 v0, v21

    double-to-int v0, v0

    move/from16 v21, v0

    .line 1100
    move-object/from16 v0, v19

    move-object/from16 v1, v20

    move/from16 v2, v21

    invoke-direct {v6, v10, v0, v1, v2}, Lcom/openvehicles/OVMS/Utilities$CarMarker;-><init>(Lcom/google/android/maps/GeoPoint;Ljava/lang/String;Ljava/lang/String;I)V

    .line 1104
    .local v6, carMarker:Lcom/openvehicles/OVMS/Utilities$CarMarker;
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v19, v0

    invoke-virtual/range {v19 .. v19}, Lcom/openvehicles/OVMS/TabMap;->getResources()Landroid/content/res/Resources;

    move-result-object v19

    .line 1105
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v20, v0

    invoke-virtual/range {v20 .. v20}, Lcom/openvehicles/OVMS/TabMap;->getResources()Landroid/content/res/Resources;

    move-result-object v20

    .line 1106
    new-instance v21, Ljava/lang/StringBuilder;

    iget-object v0, v8, Lcom/openvehicles/OVMS/CarData_Group;->VehicleImageDrawable:Ljava/lang/String;

    move-object/from16 v22, v0

    invoke-static/range {v22 .. v22}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v22

    invoke-direct/range {v21 .. v22}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v22, "32x32"

    invoke-virtual/range {v21 .. v22}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v21

    invoke-virtual/range {v21 .. v21}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v21

    .line 1107
    const-string v22, "drawable"

    const-string v23, "com.openvehicles.OVMS"

    .line 1105
    invoke-virtual/range {v20 .. v23}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v20

    .line 1104
    invoke-virtual/range {v19 .. v20}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v7

    .line 1110
    .local v7, drawable:Landroid/graphics/drawable/Drawable;
    if-nez v7, :cond_a

    .line 1111
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v19, v0

    invoke-virtual/range {v19 .. v19}, Lcom/openvehicles/OVMS/TabMap;->getResources()Landroid/content/res/Resources;

    move-result-object v19

    .line 1112
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v20, v0

    invoke-virtual/range {v20 .. v20}, Lcom/openvehicles/OVMS/TabMap;->getResources()Landroid/content/res/Resources;

    move-result-object v20

    .line 1113
    const-string v21, "car_roadster_arcticwhite32x32"

    .line 1114
    const-string v22, "drawable"

    const-string v23, "com.openvehicles.OVMS"

    .line 1112
    invoke-virtual/range {v20 .. v23}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v20

    .line 1111
    invoke-virtual/range {v19 .. v20}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v7

    .line 1117
    :cond_a
    invoke-virtual {v7}, Landroid/graphics/drawable/Drawable;->getIntrinsicWidth()I

    move-result v19

    move/from16 v0, v19

    neg-int v0, v0

    move/from16 v19, v0

    div-int/lit8 v19, v19, 0x2

    .line 1118
    invoke-virtual {v7}, Landroid/graphics/drawable/Drawable;->getIntrinsicHeight()I

    move-result v20

    move/from16 v0, v20

    neg-int v0, v0

    move/from16 v20, v0

    .line 1119
    invoke-virtual {v7}, Landroid/graphics/drawable/Drawable;->getIntrinsicWidth()I

    move-result v21

    div-int/lit8 v21, v21, 0x2

    const/16 v22, 0x0

    .line 1117
    move/from16 v0, v19

    move/from16 v1, v20

    move/from16 v2, v21

    move/from16 v3, v22

    invoke-virtual {v7, v0, v1, v2, v3}, Landroid/graphics/drawable/Drawable;->setBounds(IIII)V

    .line 1120
    invoke-virtual {v6, v7}, Lcom/openvehicles/OVMS/Utilities$CarMarker;->setMarker(Landroid/graphics/drawable/Drawable;)V

    .line 1122
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v19, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->carMarkers:Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;
    invoke-static/range {v19 .. v19}, Lcom/openvehicles/OVMS/TabMap;->access$5(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;

    move-result-object v19

    invoke-virtual/range {v19 .. v19}, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->size()I

    move-result v19

    move/from16 v0, v19

    if-le v0, v9, :cond_b

    .line 1123
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v19, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->carMarkers:Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;
    invoke-static/range {v19 .. v19}, Lcom/openvehicles/OVMS/TabMap;->access$5(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;

    move-result-object v19

    move-object/from16 v0, v19

    invoke-virtual {v0, v9, v6}, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->setOverlay(ILcom/google/android/maps/OverlayItem;)V

    .line 1127
    :goto_8
    add-int/lit8 v9, v9, 0x1

    goto/16 :goto_5

    .line 1125
    :cond_b
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v19, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->carMarkers:Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;
    invoke-static/range {v19 .. v19}, Lcom/openvehicles/OVMS/TabMap;->access$5(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;

    move-result-object v19

    move-object/from16 v0, v19

    invoke-virtual {v0, v6}, Lcom/openvehicles/OVMS/Utilities$CarMarkerOverlay;->addOverlay(Lcom/google/android/maps/OverlayItem;)V

    goto :goto_8

    .line 1134
    .end local v6           #carMarker:Lcom/openvehicles/OVMS/Utilities$CarMarker;
    .end local v7           #drawable:Landroid/graphics/drawable/Drawable;
    .end local v8           #groupCar:Lcom/openvehicles/OVMS/CarData_Group;
    .end local v9           #groupCarIndex:I
    .end local v10           #groupCarLocation:Lcom/google/android/maps/GeoPoint;
    .end local v17           #vehicleID:Ljava/lang/String;
    :pswitch_1
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->myLocationOverlay:Lcom/openvehicles/OVMS/MyLocationOverlayCustom;
    invoke-static/range {v18 .. v18}, Lcom/openvehicles/OVMS/TabMap;->access$3(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/MyLocationOverlayCustom;

    move-result-object v18

    invoke-virtual/range {v18 .. v18}, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->getMyLocation()Lcom/google/android/maps/GeoPoint;

    move-result-object v18

    if-eqz v18, :cond_c

    .line 1135
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->mc:Lcom/google/android/maps/MapController;
    invoke-static/range {v18 .. v18}, Lcom/openvehicles/OVMS/TabMap;->access$13(Lcom/openvehicles/OVMS/TabMap;)Lcom/google/android/maps/MapController;

    move-result-object v18

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v19, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->myLocationOverlay:Lcom/openvehicles/OVMS/MyLocationOverlayCustom;
    invoke-static/range {v19 .. v19}, Lcom/openvehicles/OVMS/TabMap;->access$3(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/MyLocationOverlayCustom;

    move-result-object v19

    invoke-virtual/range {v19 .. v19}, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->getMyLocation()Lcom/google/android/maps/GeoPoint;

    move-result-object v19

    invoke-virtual/range {v18 .. v19}, Lcom/google/android/maps/MapController;->animateTo(Lcom/google/android/maps/GeoPoint;)V

    goto/16 :goto_6

    .line 1137
    :cond_c
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    const-string v19, "Waiting for device location..."

    const/16 v20, 0x0

    invoke-static/range {v18 .. v20}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v18

    invoke-virtual/range {v18 .. v18}, Landroid/widget/Toast;->show()V

    goto/16 :goto_6

    .line 1140
    :pswitch_2
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->mc:Lcom/google/android/maps/MapController;
    invoke-static/range {v18 .. v18}, Lcom/openvehicles/OVMS/TabMap;->access$13(Lcom/openvehicles/OVMS/TabMap;)Lcom/google/android/maps/MapController;

    move-result-object v18

    move-object/from16 v0, v18

    invoke-virtual {v0, v5}, Lcom/google/android/maps/MapController;->animateTo(Lcom/google/android/maps/GeoPoint;)V

    goto/16 :goto_6

    .line 1143
    :pswitch_3
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->myLocationOverlay:Lcom/openvehicles/OVMS/MyLocationOverlayCustom;
    invoke-static/range {v18 .. v18}, Lcom/openvehicles/OVMS/TabMap;->access$3(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/MyLocationOverlayCustom;

    move-result-object v18

    invoke-virtual/range {v18 .. v18}, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->getMyLocation()Lcom/google/android/maps/GeoPoint;

    move-result-object v18

    if-eqz v18, :cond_d

    .line 1144
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->mc:Lcom/google/android/maps/MapController;
    invoke-static/range {v18 .. v18}, Lcom/openvehicles/OVMS/TabMap;->access$13(Lcom/openvehicles/OVMS/TabMap;)Lcom/google/android/maps/MapController;

    move-result-object v18

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v19, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->myLocationOverlay:Lcom/openvehicles/OVMS/MyLocationOverlayCustom;
    invoke-static/range {v19 .. v19}, Lcom/openvehicles/OVMS/TabMap;->access$3(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/MyLocationOverlayCustom;

    move-result-object v19

    invoke-virtual/range {v19 .. v19}, Lcom/openvehicles/OVMS/MyLocationOverlayCustom;->getMyLocation()Lcom/google/android/maps/GeoPoint;

    move-result-object v19

    invoke-virtual/range {v18 .. v19}, Lcom/google/android/maps/MapController;->animateTo(Lcom/google/android/maps/GeoPoint;)V

    goto/16 :goto_6

    .line 1146
    :cond_d
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    const-string v19, "Waiting for device location..."

    const/16 v20, 0x0

    invoke-static/range {v18 .. v20}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v18

    invoke-virtual/range {v18 .. v18}, Landroid/widget/Toast;->show()V

    goto/16 :goto_6

    .line 1151
    :pswitch_4
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static/range {v18 .. v18}, Lcom/openvehicles/OVMS/TabMap;->access$4(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v18

    move-object/from16 v0, v18

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->Group:Ljava/util/HashMap;

    move-object/from16 v18, v0

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v19, v0

    move-object/from16 v0, v19

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap;->centeringMode:Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;

    move-object/from16 v19, v0

    move-object/from16 v0, v19

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;->GroupCar_VehicleID:Ljava/lang/String;

    move-object/from16 v19, v0

    invoke-virtual/range {v18 .. v19}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v18

    if-nez v18, :cond_e

    .line 1153
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    move-object/from16 v0, v18

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap;->centeringMode:Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;

    move-object/from16 v18, v0

    const/16 v19, 0x4

    invoke-virtual/range {v18 .. v19}, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;->setMode(I)V

    goto/16 :goto_6

    .line 1157
    :cond_e
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->mc:Lcom/google/android/maps/MapController;
    invoke-static/range {v18 .. v18}, Lcom/openvehicles/OVMS/TabMap;->access$13(Lcom/openvehicles/OVMS/TabMap;)Lcom/google/android/maps/MapController;

    move-result-object v19

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static/range {v18 .. v18}, Lcom/openvehicles/OVMS/TabMap;->access$4(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v18

    move-object/from16 v0, v18

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->Group:Ljava/util/HashMap;

    move-object/from16 v18, v0

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v20, v0

    move-object/from16 v0, v20

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap;->centeringMode:Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;

    move-object/from16 v20, v0

    move-object/from16 v0, v20

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;->GroupCar_VehicleID:Ljava/lang/String;

    move-object/from16 v20, v0

    move-object/from16 v0, v18

    move-object/from16 v1, v20

    invoke-virtual {v0, v1}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v18

    check-cast v18, Lcom/openvehicles/OVMS/CarData_Group;

    move-object/from16 v0, v18

    iget-wide v0, v0, Lcom/openvehicles/OVMS/CarData_Group;->Latitude:D

    move-wide/from16 v20, v0

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static/range {v18 .. v18}, Lcom/openvehicles/OVMS/TabMap;->access$4(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v18

    move-object/from16 v0, v18

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->Group:Ljava/util/HashMap;

    move-object/from16 v18, v0

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v22, v0

    move-object/from16 v0, v22

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap;->centeringMode:Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;

    move-object/from16 v22, v0

    move-object/from16 v0, v22

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$mapCenteringMode;->GroupCar_VehicleID:Ljava/lang/String;

    move-object/from16 v22, v0

    move-object/from16 v0, v18

    move-object/from16 v1, v22

    invoke-virtual {v0, v1}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v18

    check-cast v18, Lcom/openvehicles/OVMS/CarData_Group;

    move-object/from16 v0, v18

    iget-wide v0, v0, Lcom/openvehicles/OVMS/CarData_Group;->Longitude:D

    move-wide/from16 v22, v0

    invoke-static/range {v20 .. v23}, Lcom/openvehicles/OVMS/Utilities;->GetCarGeopoint(DD)Lcom/google/android/maps/GeoPoint;

    move-result-object v18

    move-object/from16 v0, v19

    move-object/from16 v1, v18

    invoke-virtual {v0, v1}, Lcom/google/android/maps/MapController;->animateTo(Lcom/google/android/maps/GeoPoint;)V

    goto/16 :goto_6

    .line 1176
    .restart local v14       #lv:Landroid/widget/ListView;
    :cond_f
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->groupCarsListAdapter:Lcom/openvehicles/OVMS/TabMap$GroupCarsListViewAdapter;
    invoke-static/range {v18 .. v18}, Lcom/openvehicles/OVMS/TabMap;->access$15(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/TabMap$GroupCarsListViewAdapter;

    move-result-object v18

    if-eqz v18, :cond_2

    .line 1178
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->groupCarsListAdapter:Lcom/openvehicles/OVMS/TabMap$GroupCarsListViewAdapter;
    invoke-static/range {v18 .. v18}, Lcom/openvehicles/OVMS/TabMap;->access$15(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/TabMap$GroupCarsListViewAdapter;

    move-result-object v18

    invoke-virtual/range {v18 .. v18}, Lcom/openvehicles/OVMS/TabMap$GroupCarsListViewAdapter;->clear()V

    .line 1179
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/TabMap$6;->this$0:Lcom/openvehicles/OVMS/TabMap;

    move-object/from16 v18, v0

    #getter for: Lcom/openvehicles/OVMS/TabMap;->groupCarsListAdapter:Lcom/openvehicles/OVMS/TabMap$GroupCarsListViewAdapter;
    invoke-static/range {v18 .. v18}, Lcom/openvehicles/OVMS/TabMap;->access$15(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/TabMap$GroupCarsListViewAdapter;

    move-result-object v18

    move-object/from16 v0, v18

    invoke-virtual {v14, v0}, Landroid/widget/ListView;->setAdapter(Landroid/widget/ListAdapter;)V

    goto/16 :goto_7

    .line 1132
    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_1
        :pswitch_2
        :pswitch_3
        :pswitch_0
        :pswitch_4
    .end packed-switch
.end method
