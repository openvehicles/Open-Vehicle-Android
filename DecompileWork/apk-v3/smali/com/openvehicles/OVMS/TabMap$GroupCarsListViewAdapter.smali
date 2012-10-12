.class Lcom/openvehicles/OVMS/TabMap$GroupCarsListViewAdapter;
.super Landroid/widget/ArrayAdapter;
.source "TabMap.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/openvehicles/OVMS/TabMap;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "GroupCarsListViewAdapter"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Landroid/widget/ArrayAdapter",
        "<",
        "Ljava/lang/Object;",
        ">;"
    }
.end annotation


# instance fields
.field private items:[Ljava/lang/Object;

.field final synthetic this$0:Lcom/openvehicles/OVMS/TabMap;


# direct methods
.method public constructor <init>(Lcom/openvehicles/OVMS/TabMap;Landroid/content/Context;I[Ljava/lang/Object;)V
    .locals 0
    .parameter
    .parameter "context"
    .parameter "textViewResourceId"
    .parameter "items"

    .prologue
    .line 551
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabMap$GroupCarsListViewAdapter;->this$0:Lcom/openvehicles/OVMS/TabMap;

    .line 552
    invoke-direct {p0, p2, p3, p4}, Landroid/widget/ArrayAdapter;-><init>(Landroid/content/Context;I[Ljava/lang/Object;)V

    .line 553
    iput-object p4, p0, Lcom/openvehicles/OVMS/TabMap$GroupCarsListViewAdapter;->items:[Ljava/lang/Object;

    .line 554
    return-void
.end method


# virtual methods
.method public getView(ILandroid/view/View;Landroid/view/ViewGroup;)Landroid/view/View;
    .locals 15
    .parameter "position"
    .parameter "convertView"
    .parameter "parent"

    .prologue
    .line 558
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$GroupCarsListViewAdapter;->items:[Ljava/lang/Object;

    aget-object v10, v0, p1

    check-cast v10, Lcom/openvehicles/OVMS/CarData_Group;

    .line 559
    .local v10, it:Lcom/openvehicles/OVMS/CarData_Group;
    if-nez v10, :cond_0

    .line 560
    const/4 v13, 0x0

    .line 579
    :goto_0
    return-object v13

    .line 562
    :cond_0
    move-object/from16 v13, p2

    .line 563
    .local v13, v:Landroid/view/View;
    if-nez v13, :cond_1

    .line 564
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$GroupCarsListViewAdapter;->this$0:Lcom/openvehicles/OVMS/TabMap;

    const-string v1, "layout_inflater"

    invoke-virtual {v0, v1}, Lcom/openvehicles/OVMS/TabMap;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v14

    check-cast v14, Landroid/view/LayoutInflater;

    .line 565
    .local v14, vi:Landroid/view/LayoutInflater;
    const v0, 0x7f030012

    const/4 v1, 0x0

    invoke-virtual {v14, v0, v1}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object v13

    .line 568
    .end local v14           #vi:Landroid/view/LayoutInflater;
    :cond_1
    const v0, 0x7f090095

    invoke-virtual {v13, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v12

    check-cast v12, Landroid/widget/TextView;

    .line 569
    .local v12, tv:Landroid/widget/TextView;
    iget-object v0, v10, Lcom/openvehicles/OVMS/CarData_Group;->VehicleID:Ljava/lang/String;

    invoke-virtual {v12, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 570
    const v0, 0x7f090096

    invoke-virtual {v13, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v12

    .end local v12           #tv:Landroid/widget/TextView;
    check-cast v12, Landroid/widget/TextView;

    .line 571
    .restart local v12       #tv:Landroid/widget/TextView;
    const-string v1, "%s%%"

    const/4 v0, 0x2

    new-array v2, v0, [Ljava/lang/Object;

    const/4 v0, 0x0

    iget-wide v3, v10, Lcom/openvehicles/OVMS/CarData_Group;->SOC:D

    invoke-static {v3, v4}, Ljava/lang/Double;->valueOf(D)Ljava/lang/Double;

    move-result-object v3

    aput-object v3, v2, v0

    const/4 v3, 0x1

    iget-wide v4, v10, Lcom/openvehicles/OVMS/CarData_Group;->Speed:D

    const-wide/16 v6, 0x0

    cmpl-double v0, v4, v6

    if-lez v0, :cond_3

    const-string v4, " | %s%s"

    const/4 v0, 0x2

    new-array v5, v0, [Ljava/lang/Object;

    const/4 v0, 0x0

    iget-wide v6, v10, Lcom/openvehicles/OVMS/CarData_Group;->Speed:D

    invoke-static {v6, v7}, Ljava/lang/Double;->valueOf(D)Ljava/lang/Double;

    move-result-object v6

    aput-object v6, v5, v0

    const/4 v6, 0x1

    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$GroupCarsListViewAdapter;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabMap;->access$4(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v0

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->Data_DistanceUnit:Ljava/lang/String;

    const-string v7, "K"

    invoke-virtual {v0, v7}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_2

    const-string v0, "kph"

    :goto_1
    aput-object v0, v5, v6

    invoke-static {v4, v5}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    :goto_2
    aput-object v0, v2, v3

    invoke-static {v1, v2}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v12, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 572
    const v0, 0x7f090097

    invoke-virtual {v13, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v12

    .end local v12           #tv:Landroid/widget/TextView;
    check-cast v12, Landroid/widget/TextView;

    .line 573
    .restart local v12       #tv:Landroid/widget/TextView;
    iget-wide v0, v10, Lcom/openvehicles/OVMS/CarData_Group;->Latitude:D

    iget-wide v2, v10, Lcom/openvehicles/OVMS/CarData_Group;->Longitude:D

    iget-object v4, p0, Lcom/openvehicles/OVMS/TabMap$GroupCarsListViewAdapter;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v4}, Lcom/openvehicles/OVMS/TabMap;->access$4(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v4

    iget-wide v4, v4, Lcom/openvehicles/OVMS/CarData;->Data_Latitude:D

    iget-object v6, p0, Lcom/openvehicles/OVMS/TabMap$GroupCarsListViewAdapter;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabMap;->access$4(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-wide v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_Longitude:D

    invoke-static/range {v0 .. v7}, Lcom/openvehicles/OVMS/Utilities;->GetDistanceBetweenCoordinatesKM(DDDD)D

    move-result-wide v8

    .line 574
    .local v8, distanceKM:D
    const-string v1, "%.1f%n %s"

    const/4 v0, 0x2

    new-array v2, v0, [Ljava/lang/Object;

    const/4 v0, 0x0

    iget-object v3, p0, Lcom/openvehicles/OVMS/TabMap$GroupCarsListViewAdapter;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v3}, Lcom/openvehicles/OVMS/TabMap;->access$4(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v3

    iget-object v3, v3, Lcom/openvehicles/OVMS/CarData;->Data_DistanceUnit:Ljava/lang/String;

    const-string v4, "K"

    invoke-virtual {v3, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_4

    .end local v8           #distanceKM:D
    :goto_3
    invoke-static {v8, v9}, Ljava/lang/Double;->valueOf(D)Ljava/lang/Double;

    move-result-object v3

    aput-object v3, v2, v0

    const/4 v3, 0x1

    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$GroupCarsListViewAdapter;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #getter for: Lcom/openvehicles/OVMS/TabMap;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v0}, Lcom/openvehicles/OVMS/TabMap;->access$4(Lcom/openvehicles/OVMS/TabMap;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v0

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->Data_DistanceUnit:Ljava/lang/String;

    const-string v4, "K"

    invoke-virtual {v0, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_5

    const-string v0, "km"

    :goto_4
    aput-object v0, v2, v3

    invoke-static {v1, v2}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v12, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 576
    const v0, 0x7f090094

    invoke-virtual {v13, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v11

    check-cast v11, Landroid/widget/ImageView;

    .line 577
    .local v11, iv:Landroid/widget/ImageView;
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$GroupCarsListViewAdapter;->this$0:Lcom/openvehicles/OVMS/TabMap;

    invoke-virtual {v0}, Lcom/openvehicles/OVMS/TabMap;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    new-instance v1, Ljava/lang/StringBuilder;

    iget-object v2, v10, Lcom/openvehicles/OVMS/CarData_Group;->VehicleImageDrawable:Ljava/lang/String;

    invoke-static {v2}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v2, "96x44"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    const-string v2, "drawable"

    const-string v3, "com.openvehicles.OVMS"

    invoke-virtual {v0, v1, v2, v3}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    invoke-virtual {v11, v0}, Landroid/widget/ImageView;->setImageResource(I)V

    goto/16 :goto_0

    .line 571
    .end local v11           #iv:Landroid/widget/ImageView;
    :cond_2
    const-string v0, "mph"

    goto/16 :goto_1

    :cond_3
    const-string v0, ""

    goto/16 :goto_2

    .line 574
    .restart local v8       #distanceKM:D
    :cond_4
    const-wide v3, 0x3fe3e245d68a2112L

    mul-double/2addr v8, v3

    goto :goto_3

    .end local v8           #distanceKM:D
    :cond_5
    const-string v0, "mi"

    goto :goto_4
.end method
