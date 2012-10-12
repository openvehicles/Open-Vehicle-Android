.class Lcom/openvehicles/OVMS/TabCar$2;
.super Landroid/os/Handler;
.source "TabCar.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/openvehicles/OVMS/TabCar;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/openvehicles/OVMS/TabCar;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/TabCar;)V
    .locals 0
    .parameter

    .prologue
    .line 84
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 12
    .parameter "msg"

    .prologue
    const/4 v11, 0x2

    const/4 v10, 0x1

    const/4 v5, 0x4

    const/4 v4, 0x0

    .line 86
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #calls: Lcom/openvehicles/OVMS/TabCar;->updateLastUpdatedView()V
    invoke-static {v3}, Lcom/openvehicles/OVMS/TabCar;->access$000(Lcom/openvehicles/OVMS/TabCar;)V

    .line 88
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v6, 0x7f060013

    invoke-virtual {v3, v6}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/widget/TextView;

    .line 89
    .local v2, tv:Landroid/widget/TextView;
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v3}, Lcom/openvehicles/OVMS/TabCar;->access$300(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v3

    iget-object v3, v3, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    invoke-virtual {v2, v3}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 91
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v6, 0x7f06001f

    invoke-virtual {v3, v6}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v2

    .end local v2           #tv:Landroid/widget/TextView;
    check-cast v2, Landroid/widget/TextView;

    .line 92
    .restart local v2       #tv:Landroid/widget/TextView;
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v3}, Lcom/openvehicles/OVMS/TabCar;->access$300(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v3

    iget-boolean v3, v3, Lcom/openvehicles/OVMS/CarData;->Data_LeftDoorOpen:Z

    if-eqz v3, :cond_0

    move v3, v4

    :goto_0
    invoke-virtual {v2, v3}, Landroid/widget/TextView;->setVisibility(I)V

    .line 94
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v6, 0x7f060020

    invoke-virtual {v3, v6}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v2

    .end local v2           #tv:Landroid/widget/TextView;
    check-cast v2, Landroid/widget/TextView;

    .line 95
    .restart local v2       #tv:Landroid/widget/TextView;
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v3}, Lcom/openvehicles/OVMS/TabCar;->access$300(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v3

    iget-boolean v3, v3, Lcom/openvehicles/OVMS/CarData;->Data_RightDoorOpen:Z

    if-eqz v3, :cond_1

    move v3, v4

    :goto_1
    invoke-virtual {v2, v3}, Landroid/widget/TextView;->setVisibility(I)V

    .line 97
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v6, 0x7f060024

    invoke-virtual {v3, v6}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v2

    .end local v2           #tv:Landroid/widget/TextView;
    check-cast v2, Landroid/widget/TextView;

    .line 98
    .restart local v2       #tv:Landroid/widget/TextView;
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v3}, Lcom/openvehicles/OVMS/TabCar;->access$300(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v3

    iget-boolean v3, v3, Lcom/openvehicles/OVMS/CarData;->Data_ChargePortOpen:Z

    if-eqz v3, :cond_2

    move v3, v4

    :goto_2
    invoke-virtual {v2, v3}, Landroid/widget/TextView;->setVisibility(I)V

    .line 100
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v6, 0x7f060021

    invoke-virtual {v3, v6}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v2

    .end local v2           #tv:Landroid/widget/TextView;
    check-cast v2, Landroid/widget/TextView;

    .line 101
    .restart local v2       #tv:Landroid/widget/TextView;
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v3}, Lcom/openvehicles/OVMS/TabCar;->access$300(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v3

    iget-boolean v3, v3, Lcom/openvehicles/OVMS/CarData;->Data_BonnetOpen:Z

    if-eqz v3, :cond_3

    move v3, v4

    :goto_3
    invoke-virtual {v2, v3}, Landroid/widget/TextView;->setVisibility(I)V

    .line 103
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v6, 0x7f060025

    invoke-virtual {v3, v6}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v2

    .end local v2           #tv:Landroid/widget/TextView;
    check-cast v2, Landroid/widget/TextView;

    .line 104
    .restart local v2       #tv:Landroid/widget/TextView;
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v3}, Lcom/openvehicles/OVMS/TabCar;->access$300(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v3

    iget-boolean v3, v3, Lcom/openvehicles/OVMS/CarData;->Data_TrunkOpen:Z

    if-eqz v3, :cond_4

    move v3, v4

    :goto_4
    invoke-virtual {v2, v3}, Landroid/widget/TextView;->setVisibility(I)V

    .line 107
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v6, 0x7f060023

    invoke-virtual {v3, v6}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v2

    .end local v2           #tv:Landroid/widget/TextView;
    check-cast v2, Landroid/widget/TextView;

    .line 108
    .restart local v2       #tv:Landroid/widget/TextView;
    const-string v3, "PEM: %d\u00baC\nMotor: %d\u00baC\nBatt: %d\u00baC\nSpeed: %dkph"

    new-array v6, v5, [Ljava/lang/Object;

    iget-object v7, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v7}, Lcom/openvehicles/OVMS/TabCar;->access$300(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v7

    iget-wide v7, v7, Lcom/openvehicles/OVMS/CarData;->Data_TemperaturePEM:D

    double-to-int v7, v7

    invoke-static {v7}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    aput-object v7, v6, v4

    iget-object v7, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v7}, Lcom/openvehicles/OVMS/TabCar;->access$300(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v7

    iget-wide v7, v7, Lcom/openvehicles/OVMS/CarData;->Data_TemperatureMotor:D

    double-to-int v7, v7

    invoke-static {v7}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    aput-object v7, v6, v10

    iget-object v7, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v7}, Lcom/openvehicles/OVMS/TabCar;->access$300(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v7

    iget-wide v7, v7, Lcom/openvehicles/OVMS/CarData;->Data_TemperatureBattery:D

    double-to-int v7, v7

    invoke-static {v7}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    aput-object v7, v6, v11

    const/4 v7, 0x3

    iget-object v8, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v8}, Lcom/openvehicles/OVMS/TabCar;->access$300(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v8

    iget-wide v8, v8, Lcom/openvehicles/OVMS/CarData;->Data_Speed:D

    double-to-int v8, v8

    invoke-static {v8}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v8

    aput-object v8, v6, v7

    invoke-static {v3, v6}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 119
    const-string v1, "%.1fpsi\n%.0f\u00baC"

    .line 121
    .local v1, tirePressureDisplayFormat:Ljava/lang/String;
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v6, 0x7f06001b

    invoke-virtual {v3, v6}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v2

    .end local v2           #tv:Landroid/widget/TextView;
    check-cast v2, Landroid/widget/TextView;

    .line 122
    .restart local v2       #tv:Landroid/widget/TextView;
    new-array v3, v11, [Ljava/lang/Object;

    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$300(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-wide v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_FLWheelPressure:D

    invoke-static {v6, v7}, Ljava/lang/Double;->valueOf(D)Ljava/lang/Double;

    move-result-object v6

    aput-object v6, v3, v4

    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$300(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-wide v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_FLWheelTemperature:D

    invoke-static {v6, v7}, Ljava/lang/Double;->valueOf(D)Ljava/lang/Double;

    move-result-object v6

    aput-object v6, v3, v10

    invoke-static {v1, v3}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 124
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v6, 0x7f06001c

    invoke-virtual {v3, v6}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v2

    .end local v2           #tv:Landroid/widget/TextView;
    check-cast v2, Landroid/widget/TextView;

    .line 125
    .restart local v2       #tv:Landroid/widget/TextView;
    new-array v3, v11, [Ljava/lang/Object;

    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$300(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-wide v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_FRWheelPressure:D

    invoke-static {v6, v7}, Ljava/lang/Double;->valueOf(D)Ljava/lang/Double;

    move-result-object v6

    aput-object v6, v3, v4

    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$300(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-wide v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_FRWheelTemperature:D

    invoke-static {v6, v7}, Ljava/lang/Double;->valueOf(D)Ljava/lang/Double;

    move-result-object v6

    aput-object v6, v3, v10

    invoke-static {v1, v3}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 127
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v6, 0x7f06001d

    invoke-virtual {v3, v6}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v2

    .end local v2           #tv:Landroid/widget/TextView;
    check-cast v2, Landroid/widget/TextView;

    .line 128
    .restart local v2       #tv:Landroid/widget/TextView;
    new-array v3, v11, [Ljava/lang/Object;

    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$300(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-wide v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_RLWheelPressure:D

    invoke-static {v6, v7}, Ljava/lang/Double;->valueOf(D)Ljava/lang/Double;

    move-result-object v6

    aput-object v6, v3, v4

    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$300(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-wide v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_RLWheelTemperature:D

    invoke-static {v6, v7}, Ljava/lang/Double;->valueOf(D)Ljava/lang/Double;

    move-result-object v6

    aput-object v6, v3, v10

    invoke-static {v1, v3}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 130
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v6, 0x7f06001e

    invoke-virtual {v3, v6}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v2

    .end local v2           #tv:Landroid/widget/TextView;
    check-cast v2, Landroid/widget/TextView;

    .line 131
    .restart local v2       #tv:Landroid/widget/TextView;
    new-array v3, v11, [Ljava/lang/Object;

    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$300(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-wide v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_RRWheelPressure:D

    invoke-static {v6, v7}, Ljava/lang/Double;->valueOf(D)Ljava/lang/Double;

    move-result-object v6

    aput-object v6, v3, v4

    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$300(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-wide v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_RRWheelTemperature:D

    invoke-static {v6, v7}, Ljava/lang/Double;->valueOf(D)Ljava/lang/Double;

    move-result-object v6

    aput-object v6, v3, v10

    invoke-static {v1, v3}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 134
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v6, 0x7f060016

    invoke-virtual {v3, v6}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    .line 135
    .local v0, iv:Landroid/widget/ImageView;
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v3}, Lcom/openvehicles/OVMS/TabCar;->access$300(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v3

    iget-boolean v3, v3, Lcom/openvehicles/OVMS/CarData;->Data_ChargePortOpen:Z

    if-eqz v3, :cond_5

    move v3, v4

    :goto_5
    invoke-virtual {v0, v3}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 137
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v6, 0x7f060019

    invoke-virtual {v3, v6}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v0

    .end local v0           #iv:Landroid/widget/ImageView;
    check-cast v0, Landroid/widget/ImageView;

    .line 138
    .restart local v0       #iv:Landroid/widget/ImageView;
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v3}, Lcom/openvehicles/OVMS/TabCar;->access$300(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v3

    iget-boolean v3, v3, Lcom/openvehicles/OVMS/CarData;->Data_BonnetOpen:Z

    if-eqz v3, :cond_6

    move v3, v4

    :goto_6
    invoke-virtual {v0, v3}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 140
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v6, 0x7f06001a

    invoke-virtual {v3, v6}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v0

    .end local v0           #iv:Landroid/widget/ImageView;
    check-cast v0, Landroid/widget/ImageView;

    .line 141
    .restart local v0       #iv:Landroid/widget/ImageView;
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v3}, Lcom/openvehicles/OVMS/TabCar;->access$300(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v3

    iget-boolean v3, v3, Lcom/openvehicles/OVMS/CarData;->Data_LeftDoorOpen:Z

    if-eqz v3, :cond_7

    move v3, v4

    :goto_7
    invoke-virtual {v0, v3}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 143
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v6, 0x7f060018

    invoke-virtual {v3, v6}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v0

    .end local v0           #iv:Landroid/widget/ImageView;
    check-cast v0, Landroid/widget/ImageView;

    .line 144
    .restart local v0       #iv:Landroid/widget/ImageView;
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v3}, Lcom/openvehicles/OVMS/TabCar;->access$300(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v3

    iget-boolean v3, v3, Lcom/openvehicles/OVMS/CarData;->Data_RightDoorOpen:Z

    if-eqz v3, :cond_8

    move v3, v4

    :goto_8
    invoke-virtual {v0, v3}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 146
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v6, 0x7f060017

    invoke-virtual {v3, v6}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v0

    .end local v0           #iv:Landroid/widget/ImageView;
    check-cast v0, Landroid/widget/ImageView;

    .line 147
    .restart local v0       #iv:Landroid/widget/ImageView;
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v3}, Lcom/openvehicles/OVMS/TabCar;->access$300(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v3

    iget-boolean v3, v3, Lcom/openvehicles/OVMS/CarData;->Data_TrunkOpen:Z

    if-eqz v3, :cond_9

    move v3, v4

    :goto_9
    invoke-virtual {v0, v3}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 150
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v6, 0x7f060022

    invoke-virtual {v3, v6}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v0

    .end local v0           #iv:Landroid/widget/ImageView;
    check-cast v0, Landroid/widget/ImageView;

    .line 151
    .restart local v0       #iv:Landroid/widget/ImageView;
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v3}, Lcom/openvehicles/OVMS/TabCar;->access$300(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v3

    iget-boolean v3, v3, Lcom/openvehicles/OVMS/CarData;->Data_CarLocked:Z

    if-eqz v3, :cond_a

    const v3, 0x7f020032

    :goto_a
    invoke-virtual {v0, v3}, Landroid/widget/ImageView;->setImageResource(I)V

    .line 154
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v6, 0x7f060010

    invoke-virtual {v3, v6}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v0

    .end local v0           #iv:Landroid/widget/ImageView;
    check-cast v0, Landroid/widget/ImageView;

    .line 155
    .restart local v0       #iv:Landroid/widget/ImageView;
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v3}, Lcom/openvehicles/OVMS/TabCar;->access$300(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v3

    iget-boolean v3, v3, Lcom/openvehicles/OVMS/CarData;->ParanoidMode:Z

    if-eqz v3, :cond_b

    :goto_b
    invoke-virtual {v0, v4}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 158
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v4, 0x7f06000f

    invoke-virtual {v3, v4}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v0

    .end local v0           #iv:Landroid/widget/ImageView;
    check-cast v0, Landroid/widget/ImageView;

    .line 159
    .restart local v0       #iv:Landroid/widget/ImageView;
    invoke-virtual {v0, v5}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 160
    return-void

    .end local v0           #iv:Landroid/widget/ImageView;
    .end local v1           #tirePressureDisplayFormat:Ljava/lang/String;
    :cond_0
    move v3, v5

    .line 92
    goto/16 :goto_0

    :cond_1
    move v3, v5

    .line 95
    goto/16 :goto_1

    :cond_2
    move v3, v5

    .line 98
    goto/16 :goto_2

    :cond_3
    move v3, v5

    .line 101
    goto/16 :goto_3

    :cond_4
    move v3, v5

    .line 104
    goto/16 :goto_4

    .restart local v0       #iv:Landroid/widget/ImageView;
    .restart local v1       #tirePressureDisplayFormat:Ljava/lang/String;
    :cond_5
    move v3, v5

    .line 135
    goto/16 :goto_5

    :cond_6
    move v3, v5

    .line 138
    goto/16 :goto_6

    :cond_7
    move v3, v5

    .line 141
    goto/16 :goto_7

    :cond_8
    move v3, v5

    .line 144
    goto :goto_8

    :cond_9
    move v3, v5

    .line 147
    goto :goto_9

    .line 151
    :cond_a
    const v3, 0x7f020033

    goto :goto_a

    :cond_b
    move v4, v5

    .line 155
    goto :goto_b
.end method
