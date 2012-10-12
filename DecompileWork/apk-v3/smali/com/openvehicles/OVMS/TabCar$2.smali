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
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    .line 187
    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    return-void
.end method

.method static synthetic access$0(Lcom/openvehicles/OVMS/TabCar$2;)Lcom/openvehicles/OVMS/TabCar;
    .locals 1
    .parameter

    .prologue
    .line 187
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    return-object v0
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 12
    .parameter "msg"

    .prologue
    .line 189
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #calls: Lcom/openvehicles/OVMS/TabCar;->updateLastUpdatedView()V
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$0(Lcom/openvehicles/OVMS/TabCar;)V

    .line 191
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v7, 0x7f090019

    invoke-virtual {v6, v7}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v5

    check-cast v5, Landroid/widget/TextView;

    .line 192
    .local v5, tv:Landroid/widget/TextView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-object v6, v6, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 194
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v7, 0x7f09002b

    invoke-virtual {v6, v7}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v5

    .end local v5           #tv:Landroid/widget/TextView;
    check-cast v5, Landroid/widget/TextView;

    .line 195
    .restart local v5       #tv:Landroid/widget/TextView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_LeftDoorOpen:Z

    if-eqz v6, :cond_6

    const/4 v6, 0x0

    :goto_0
    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setVisibility(I)V

    .line 196
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v7, 0x7f09002c

    invoke-virtual {v6, v7}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v5

    .end local v5           #tv:Landroid/widget/TextView;
    check-cast v5, Landroid/widget/TextView;

    .line 197
    .restart local v5       #tv:Landroid/widget/TextView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_RightDoorOpen:Z

    if-eqz v6, :cond_7

    const/4 v6, 0x0

    :goto_1
    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setVisibility(I)V

    .line 198
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v7, 0x7f09002d

    invoke-virtual {v6, v7}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v5

    .end local v5           #tv:Landroid/widget/TextView;
    check-cast v5, Landroid/widget/TextView;

    .line 199
    .restart local v5       #tv:Landroid/widget/TextView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_ChargePortOpen:Z

    if-eqz v6, :cond_8

    const/4 v6, 0x0

    :goto_2
    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setVisibility(I)V

    .line 200
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v7, 0x7f09002f

    invoke-virtual {v6, v7}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v5

    .end local v5           #tv:Landroid/widget/TextView;
    check-cast v5, Landroid/widget/TextView;

    .line 201
    .restart local v5       #tv:Landroid/widget/TextView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_BonnetOpen:Z

    if-eqz v6, :cond_9

    const/4 v6, 0x0

    :goto_3
    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setVisibility(I)V

    .line 202
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v7, 0x7f09002e

    invoke-virtual {v6, v7}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v5

    .end local v5           #tv:Landroid/widget/TextView;
    check-cast v5, Landroid/widget/TextView;

    .line 203
    .restart local v5       #tv:Landroid/widget/TextView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_TrunkOpen:Z

    if-eqz v6, :cond_a

    const/4 v6, 0x0

    :goto_4
    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setVisibility(I)V

    .line 205
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v7, 0x7f090030

    invoke-virtual {v6, v7}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v5

    .end local v5           #tv:Landroid/widget/TextView;
    check-cast v5, Landroid/widget/TextView;

    .line 213
    .restart local v5       #tv:Landroid/widget/TextView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-wide v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_Speed:D

    const-wide/16 v8, 0x0

    cmpl-double v6, v6, v8

    if-lez v6, :cond_c

    const-string v7, "%d %s"

    const/4 v6, 0x2

    new-array v8, v6, [Ljava/lang/Object;

    const/4 v6, 0x0

    .line 214
    iget-object v9, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v9

    iget-wide v9, v9, Lcom/openvehicles/OVMS/CarData;->Data_Speed:D

    double-to-int v9, v9

    invoke-static {v9}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v9

    aput-object v9, v8, v6

    const/4 v9, 0x1

    .line 215
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-object v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_DistanceUnit:Ljava/lang/String;

    const-string v10, "K"

    invoke-virtual {v6, v10}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v6

    if-eqz v6, :cond_b

    const-string v6, "kph"

    :goto_5
    aput-object v6, v8, v9

    .line 213
    invoke-static {v7, v8}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v6

    :goto_6
    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 218
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v7, 0x7f090034

    invoke-virtual {v6, v7}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v5

    .end local v5           #tv:Landroid/widget/TextView;
    check-cast v5, Landroid/widget/TextView;

    .line 219
    .restart local v5       #tv:Landroid/widget/TextView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_CarPoweredON:Z

    if-nez v6, :cond_d

    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_CoolingPumpON_DoorState3:Z

    if-nez v6, :cond_d

    const v6, -0xbbbbbc

    :goto_7
    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setTextColor(I)V

    .line 221
    const-string v6, "%d\u00b0C"

    const/4 v7, 0x1

    new-array v7, v7, [Ljava/lang/Object;

    const/4 v8, 0x0

    iget-object v9, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v9

    iget-wide v9, v9, Lcom/openvehicles/OVMS/CarData;->Data_TemperaturePEM:D

    double-to-int v9, v9

    invoke-static {v9}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v9

    aput-object v9, v7, v8

    invoke-static {v6, v7}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 222
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v7, 0x7f090035

    invoke-virtual {v6, v7}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v5

    .end local v5           #tv:Landroid/widget/TextView;
    check-cast v5, Landroid/widget/TextView;

    .line 223
    .restart local v5       #tv:Landroid/widget/TextView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_CarPoweredON:Z

    if-nez v6, :cond_e

    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_CoolingPumpON_DoorState3:Z

    if-nez v6, :cond_e

    const v6, -0xbbbbbc

    :goto_8
    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setTextColor(I)V

    .line 225
    const-string v6, "%d\u00b0C"

    const/4 v7, 0x1

    new-array v7, v7, [Ljava/lang/Object;

    const/4 v8, 0x0

    iget-object v9, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v9

    iget-wide v9, v9, Lcom/openvehicles/OVMS/CarData;->Data_TemperatureMotor:D

    double-to-int v9, v9

    invoke-static {v9}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v9

    aput-object v9, v7, v8

    invoke-static {v6, v7}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 226
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v7, 0x7f090036

    invoke-virtual {v6, v7}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v5

    .end local v5           #tv:Landroid/widget/TextView;
    check-cast v5, Landroid/widget/TextView;

    .line 227
    .restart local v5       #tv:Landroid/widget/TextView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_CarPoweredON:Z

    if-nez v6, :cond_f

    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_CoolingPumpON_DoorState3:Z

    if-nez v6, :cond_f

    const v6, -0xbbbbbc

    :goto_9
    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setTextColor(I)V

    .line 230
    const-string v6, "%d\u00b0C"

    const/4 v7, 0x1

    new-array v7, v7, [Ljava/lang/Object;

    const/4 v8, 0x0

    iget-object v9, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v9

    iget-wide v9, v9, Lcom/openvehicles/OVMS/CarData;->Data_TemperatureBattery:D

    double-to-int v9, v9

    invoke-static {v9}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v9

    aput-object v9, v7, v8

    invoke-static {v6, v7}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v6

    .line 229
    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 231
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v7, 0x7f090037

    invoke-virtual {v6, v7}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v5

    .end local v5           #tv:Landroid/widget/TextView;
    check-cast v5, Landroid/widget/TextView;

    .line 232
    .restart local v5       #tv:Landroid/widget/TextView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_AmbientTemperatureDataStale:Z

    if-nez v6, :cond_0

    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_CarPoweredON:Z

    if-nez v6, :cond_10

    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_CoolingPumpON_DoorState3:Z

    if-nez v6, :cond_10

    :cond_0
    const v6, -0xbbbbbc

    :goto_a
    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setTextColor(I)V

    .line 235
    const-string v6, "%d\u00b0C"

    const/4 v7, 0x1

    new-array v7, v7, [Ljava/lang/Object;

    const/4 v8, 0x0

    iget-object v9, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v9

    iget-wide v9, v9, Lcom/openvehicles/OVMS/CarData;->Data_TemperatureAmbient:D

    double-to-int v9, v9

    invoke-static {v9}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v9

    aput-object v9, v7, v8

    invoke-static {v6, v7}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v6

    .line 234
    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 237
    const-string v4, "%.1fpsi\n%.0f\u00b0C"

    .line 239
    .local v4, tirePressureDisplayFormat:Ljava/lang/String;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v7, 0x7f090027

    invoke-virtual {v6, v7}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v5

    .end local v5           #tv:Landroid/widget/TextView;
    check-cast v5, Landroid/widget/TextView;

    .line 240
    .restart local v5       #tv:Landroid/widget/TextView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_TPMSDataStale:Z

    if-eqz v6, :cond_11

    const v6, -0xbbbbbc

    :goto_b
    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setTextColor(I)V

    .line 242
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-wide v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_FLWheelPressure:D

    const-wide/16 v8, 0x0

    cmpl-double v6, v6, v8

    if-nez v6, :cond_1

    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-wide v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_FLWheelTemperature:D

    const-wide/16 v8, 0x0

    cmpl-double v6, v6, v8

    if-eqz v6, :cond_12

    :cond_1
    const/4 v6, 0x0

    :goto_c
    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setVisibility(I)V

    .line 244
    const/4 v6, 0x2

    new-array v6, v6, [Ljava/lang/Object;

    const/4 v7, 0x0

    .line 245
    iget-object v8, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v8}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v8

    iget-wide v8, v8, Lcom/openvehicles/OVMS/CarData;->Data_FLWheelPressure:D

    invoke-static {v8, v9}, Ljava/lang/Double;->valueOf(D)Ljava/lang/Double;

    move-result-object v8

    aput-object v8, v6, v7

    const/4 v7, 0x1

    iget-object v8, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v8}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v8

    iget-wide v8, v8, Lcom/openvehicles/OVMS/CarData;->Data_FLWheelTemperature:D

    invoke-static {v8, v9}, Ljava/lang/Double;->valueOf(D)Ljava/lang/Double;

    move-result-object v8

    aput-object v8, v6, v7

    .line 244
    invoke-static {v4, v6}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 247
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v7, 0x7f090028

    invoke-virtual {v6, v7}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v5

    .end local v5           #tv:Landroid/widget/TextView;
    check-cast v5, Landroid/widget/TextView;

    .line 248
    .restart local v5       #tv:Landroid/widget/TextView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_TPMSDataStale:Z

    if-eqz v6, :cond_13

    const v6, -0xbbbbbc

    :goto_d
    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setTextColor(I)V

    .line 250
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-wide v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_FRWheelPressure:D

    const-wide/16 v8, 0x0

    cmpl-double v6, v6, v8

    if-nez v6, :cond_2

    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-wide v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_FRWheelTemperature:D

    const-wide/16 v8, 0x0

    cmpl-double v6, v6, v8

    if-eqz v6, :cond_14

    :cond_2
    const/4 v6, 0x0

    :goto_e
    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setVisibility(I)V

    .line 252
    const/4 v6, 0x2

    new-array v6, v6, [Ljava/lang/Object;

    const/4 v7, 0x0

    .line 253
    iget-object v8, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v8}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v8

    iget-wide v8, v8, Lcom/openvehicles/OVMS/CarData;->Data_FRWheelPressure:D

    invoke-static {v8, v9}, Ljava/lang/Double;->valueOf(D)Ljava/lang/Double;

    move-result-object v8

    aput-object v8, v6, v7

    const/4 v7, 0x1

    iget-object v8, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v8}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v8

    iget-wide v8, v8, Lcom/openvehicles/OVMS/CarData;->Data_FRWheelTemperature:D

    invoke-static {v8, v9}, Ljava/lang/Double;->valueOf(D)Ljava/lang/Double;

    move-result-object v8

    aput-object v8, v6, v7

    .line 252
    invoke-static {v4, v6}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 255
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v7, 0x7f090029

    invoke-virtual {v6, v7}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v5

    .end local v5           #tv:Landroid/widget/TextView;
    check-cast v5, Landroid/widget/TextView;

    .line 256
    .restart local v5       #tv:Landroid/widget/TextView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_TPMSDataStale:Z

    if-eqz v6, :cond_15

    const v6, -0xbbbbbc

    :goto_f
    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setTextColor(I)V

    .line 258
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-wide v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_RLWheelPressure:D

    const-wide/16 v8, 0x0

    cmpl-double v6, v6, v8

    if-nez v6, :cond_3

    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-wide v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_RLWheelTemperature:D

    const-wide/16 v8, 0x0

    cmpl-double v6, v6, v8

    if-eqz v6, :cond_16

    :cond_3
    const/4 v6, 0x0

    :goto_10
    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setVisibility(I)V

    .line 260
    const/4 v6, 0x2

    new-array v6, v6, [Ljava/lang/Object;

    const/4 v7, 0x0

    .line 261
    iget-object v8, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v8}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v8

    iget-wide v8, v8, Lcom/openvehicles/OVMS/CarData;->Data_RLWheelPressure:D

    invoke-static {v8, v9}, Ljava/lang/Double;->valueOf(D)Ljava/lang/Double;

    move-result-object v8

    aput-object v8, v6, v7

    const/4 v7, 0x1

    iget-object v8, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v8}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v8

    iget-wide v8, v8, Lcom/openvehicles/OVMS/CarData;->Data_RLWheelTemperature:D

    invoke-static {v8, v9}, Ljava/lang/Double;->valueOf(D)Ljava/lang/Double;

    move-result-object v8

    aput-object v8, v6, v7

    .line 260
    invoke-static {v4, v6}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 263
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v7, 0x7f09002a

    invoke-virtual {v6, v7}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v5

    .end local v5           #tv:Landroid/widget/TextView;
    check-cast v5, Landroid/widget/TextView;

    .line 264
    .restart local v5       #tv:Landroid/widget/TextView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_TPMSDataStale:Z

    if-eqz v6, :cond_17

    const v6, -0xbbbbbc

    :goto_11
    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setTextColor(I)V

    .line 266
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-wide v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_RRWheelPressure:D

    const-wide/16 v8, 0x0

    cmpl-double v6, v6, v8

    if-nez v6, :cond_4

    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-wide v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_RRWheelTemperature:D

    const-wide/16 v8, 0x0

    cmpl-double v6, v6, v8

    if-eqz v6, :cond_18

    :cond_4
    const/4 v6, 0x0

    :goto_12
    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setVisibility(I)V

    .line 268
    const/4 v6, 0x2

    new-array v6, v6, [Ljava/lang/Object;

    const/4 v7, 0x0

    .line 269
    iget-object v8, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v8}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v8

    iget-wide v8, v8, Lcom/openvehicles/OVMS/CarData;->Data_RRWheelPressure:D

    invoke-static {v8, v9}, Ljava/lang/Double;->valueOf(D)Ljava/lang/Double;

    move-result-object v8

    aput-object v8, v6, v7

    const/4 v7, 0x1

    iget-object v8, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v8}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v8

    iget-wide v8, v8, Lcom/openvehicles/OVMS/CarData;->Data_RRWheelTemperature:D

    invoke-static {v8, v9}, Ljava/lang/Double;->valueOf(D)Ljava/lang/Double;

    move-result-object v8

    aput-object v8, v6, v7

    .line 268
    invoke-static {v4, v6}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 271
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v7, 0x7f09001e

    invoke-virtual {v6, v7}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v3

    check-cast v3, Landroid/widget/ImageView;

    .line 274
    .local v3, iv:Landroid/widget/ImageView;
    new-instance v6, Ljava/lang/StringBuilder;

    iget-object v7, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    .line 275
    invoke-virtual {v7}, Lcom/openvehicles/OVMS/TabCar;->getCacheDir()Ljava/io/File;

    move-result-object v7

    invoke-virtual {v7}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v7

    invoke-static {v7}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v7

    invoke-direct {v6, v7}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 276
    const-string v7, "/ol_%s.png"

    const/4 v8, 0x1

    new-array v8, v8, [Ljava/lang/Object;

    const/4 v9, 0x0

    iget-object v10, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v10}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v10

    iget-object v10, v10, Lcom/openvehicles/OVMS/CarData;->VehicleImageDrawable:Ljava/lang/String;

    aput-object v10, v8, v9

    invoke-static {v7, v8}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v7

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    .line 274
    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v6}, Landroid/graphics/BitmapFactory;->decodeFile(Ljava/lang/String;)Landroid/graphics/Bitmap;

    move-result-object v2

    .line 277
    .local v2, carLayout:Landroid/graphics/Bitmap;
    if-eqz v2, :cond_19

    .line 278
    invoke-virtual {v3, v2}, Landroid/widget/ImageView;->setImageBitmap(Landroid/graphics/Bitmap;)V

    .line 319
    :cond_5
    :goto_13
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v7, 0x7f09001f

    invoke-virtual {v6, v7}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v3

    .end local v3           #iv:Landroid/widget/ImageView;
    check-cast v3, Landroid/widget/ImageView;

    .line 320
    .restart local v3       #iv:Landroid/widget/ImageView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_ChargePortOpen:Z

    if-eqz v6, :cond_1b

    const/4 v6, 0x0

    :goto_14
    invoke-virtual {v3, v6}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 322
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v7, 0x7f090022

    invoke-virtual {v6, v7}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v3

    .end local v3           #iv:Landroid/widget/ImageView;
    check-cast v3, Landroid/widget/ImageView;

    .line 323
    .restart local v3       #iv:Landroid/widget/ImageView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_BonnetOpen:Z

    if-eqz v6, :cond_1c

    const/4 v6, 0x0

    :goto_15
    invoke-virtual {v3, v6}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 324
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v7, 0x7f090023

    invoke-virtual {v6, v7}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v3

    .end local v3           #iv:Landroid/widget/ImageView;
    check-cast v3, Landroid/widget/ImageView;

    .line 325
    .restart local v3       #iv:Landroid/widget/ImageView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_LeftDoorOpen:Z

    if-eqz v6, :cond_1d

    const/4 v6, 0x0

    :goto_16
    invoke-virtual {v3, v6}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 326
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v7, 0x7f090021

    invoke-virtual {v6, v7}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v3

    .end local v3           #iv:Landroid/widget/ImageView;
    check-cast v3, Landroid/widget/ImageView;

    .line 327
    .restart local v3       #iv:Landroid/widget/ImageView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_RightDoorOpen:Z

    if-eqz v6, :cond_1e

    const/4 v6, 0x0

    :goto_17
    invoke-virtual {v3, v6}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 328
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v7, 0x7f090020

    invoke-virtual {v6, v7}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v3

    .end local v3           #iv:Landroid/widget/ImageView;
    check-cast v3, Landroid/widget/ImageView;

    .line 329
    .restart local v3       #iv:Landroid/widget/ImageView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_TrunkOpen:Z

    if-eqz v6, :cond_1f

    const/4 v6, 0x0

    :goto_18
    invoke-virtual {v3, v6}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 331
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v7, 0x7f090024

    invoke-virtual {v6, v7}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v3

    .end local v3           #iv:Landroid/widget/ImageView;
    check-cast v3, Landroid/widget/ImageView;

    .line 332
    .restart local v3       #iv:Landroid/widget/ImageView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_CarLocked:Z

    if-eqz v6, :cond_20

    const v6, 0x7f02003b

    :goto_19
    invoke-virtual {v3, v6}, Landroid/widget/ImageView;->setImageResource(I)V

    .line 334
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v7, 0x7f090025

    invoke-virtual {v6, v7}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v3

    .end local v3           #iv:Landroid/widget/ImageView;
    check-cast v3, Landroid/widget/ImageView;

    .line 335
    .restart local v3       #iv:Landroid/widget/ImageView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_ValetON:Z

    if-eqz v6, :cond_21

    const v6, 0x7f02003e

    :goto_1a
    invoke-virtual {v3, v6}, Landroid/widget/ImageView;->setImageResource(I)V

    .line 337
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v7, 0x7f090026

    invoke-virtual {v6, v7}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v3

    .end local v3           #iv:Landroid/widget/ImageView;
    check-cast v3, Landroid/widget/ImageView;

    .line 338
    .restart local v3       #iv:Landroid/widget/ImageView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_HeadlightsON:Z

    if-eqz v6, :cond_22

    const/4 v6, 0x0

    :goto_1b
    invoke-virtual {v3, v6}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 340
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v7, 0x7f09001b

    invoke-virtual {v6, v7}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v3

    .end local v3           #iv:Landroid/widget/ImageView;
    check-cast v3, Landroid/widget/ImageView;

    .line 341
    .restart local v3       #iv:Landroid/widget/ImageView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->ParanoidMode:Z

    if-eqz v6, :cond_23

    const/4 v6, 0x0

    :goto_1c
    invoke-virtual {v3, v6}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 343
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v7, 0x7f09001d

    invoke-virtual {v6, v7}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v3

    .end local v3           #iv:Landroid/widget/ImageView;
    check-cast v3, Landroid/widget/ImageView;

    .line 347
    .restart local v3       #iv:Landroid/widget/ImageView;
    :try_start_0
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-object v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_CarModuleGSMSignalLevel:Ljava/lang/String;

    invoke-static {v6}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v0

    .line 348
    .local v0, RSSI:I
    const/4 v6, 0x1

    if-ge v0, v6, :cond_24

    .line 349
    const v6, 0x7f020068

    invoke-virtual {v3, v6}, Landroid/widget/ImageView;->setImageResource(I)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 363
    .end local v0           #RSSI:I
    :goto_1d
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    const v7, 0x7f09001a

    invoke-virtual {v6, v7}, Lcom/openvehicles/OVMS/TabCar;->findViewById(I)Landroid/view/View;

    move-result-object v3

    .end local v3           #iv:Landroid/widget/ImageView;
    check-cast v3, Landroid/widget/ImageView;

    .line 364
    .restart local v3       #iv:Landroid/widget/ImageView;
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->isLoggedIn:Z
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$7(Lcom/openvehicles/OVMS/TabCar;)Z

    move-result v6

    if-eqz v6, :cond_29

    const/16 v6, 0x8

    :goto_1e
    invoke-virtual {v3, v6}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 366
    return-void

    .line 195
    .end local v2           #carLayout:Landroid/graphics/Bitmap;
    .end local v3           #iv:Landroid/widget/ImageView;
    .end local v4           #tirePressureDisplayFormat:Ljava/lang/String;
    :cond_6
    const/4 v6, 0x4

    goto/16 :goto_0

    .line 197
    :cond_7
    const/4 v6, 0x4

    goto/16 :goto_1

    .line 199
    :cond_8
    const/4 v6, 0x4

    goto/16 :goto_2

    .line 201
    :cond_9
    const/4 v6, 0x4

    goto/16 :goto_3

    .line 203
    :cond_a
    const/4 v6, 0x4

    goto/16 :goto_4

    .line 215
    :cond_b
    const-string v6, "mph"

    goto/16 :goto_5

    :cond_c
    const-string v6, ""

    goto/16 :goto_6

    .line 220
    :cond_d
    const/4 v6, -0x1

    goto/16 :goto_7

    .line 224
    :cond_e
    const/4 v6, -0x1

    goto/16 :goto_8

    .line 228
    :cond_f
    const/4 v6, -0x1

    goto/16 :goto_9

    .line 233
    :cond_10
    const/4 v6, -0x1

    goto/16 :goto_a

    .line 241
    .restart local v4       #tirePressureDisplayFormat:Ljava/lang/String;
    :cond_11
    const/4 v6, -0x1

    goto/16 :goto_b

    .line 243
    :cond_12
    const/4 v6, 0x4

    goto/16 :goto_c

    .line 249
    :cond_13
    const/4 v6, -0x1

    goto/16 :goto_d

    .line 251
    :cond_14
    const/4 v6, 0x4

    goto/16 :goto_e

    .line 257
    :cond_15
    const/4 v6, -0x1

    goto/16 :goto_f

    .line 259
    :cond_16
    const/4 v6, 0x4

    goto/16 :goto_10

    .line 265
    :cond_17
    const/4 v6, -0x1

    goto/16 :goto_11

    .line 267
    :cond_18
    const/4 v6, 0x4

    goto/16 :goto_12

    .line 280
    .restart local v2       #carLayout:Landroid/graphics/Bitmap;
    .restart local v3       #iv:Landroid/widget/ImageView;
    :cond_19
    const-string v6, "OVMS"

    .line 281
    new-instance v7, Ljava/lang/StringBuilder;

    const-string v8, "** File Not Found: "

    invoke-direct {v7, v8}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 282
    iget-object v8, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    invoke-virtual {v8}, Lcom/openvehicles/OVMS/TabCar;->getCacheDir()Ljava/io/File;

    move-result-object v8

    invoke-virtual {v8}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    .line 283
    const-string v8, "/ol_%s.png"

    const/4 v9, 0x1

    new-array v9, v9, [Ljava/lang/Object;

    const/4 v10, 0x0

    .line 284
    iget-object v11, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v11}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v11

    iget-object v11, v11, Lcom/openvehicles/OVMS/CarData;->VehicleImageDrawable:Ljava/lang/String;

    aput-object v11, v9, v10

    .line 283
    invoke-static {v8, v9}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    .line 281
    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    .line 280
    invoke-static {v6, v7}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 287
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->DontAskLayoutDownload:Z

    if-nez v6, :cond_5

    .line 288
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->lastUpdatedDialog:Landroid/app/AlertDialog;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$4(Lcom/openvehicles/OVMS/TabCar;)Landroid/app/AlertDialog;

    move-result-object v6

    if-eqz v6, :cond_1a

    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->lastUpdatedDialog:Landroid/app/AlertDialog;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$4(Lcom/openvehicles/OVMS/TabCar;)Landroid/app/AlertDialog;

    move-result-object v6

    .line 289
    invoke-virtual {v6}, Landroid/app/AlertDialog;->isShowing()Z

    move-result v6

    if-nez v6, :cond_5

    .line 290
    :cond_1a
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$3(Lcom/openvehicles/OVMS/TabCar;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    const/4 v7, 0x1

    iput-boolean v7, v6, Lcom/openvehicles/OVMS/CarData;->DontAskLayoutDownload:Z

    .line 291
    new-instance v1, Landroid/app/AlertDialog$Builder;

    .line 292
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    .line 291
    invoke-direct {v1, v6}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 294
    .local v1, builder:Landroid/app/AlertDialog$Builder;
    const-string v6, "Would you like to download a set of high resolution car images specifically drawn for your car?\n\nThe download is approx. 300KB.\n\nNote: a manual download button is available in the car commands and settings tab."

    .line 293
    invoke-virtual {v1, v6}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v6

    .line 295
    const-string v7, "Download Graphics"

    invoke-virtual {v6, v7}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v6

    .line 296
    const/4 v7, 0x1

    invoke-virtual {v6, v7}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    move-result-object v6

    .line 297
    const-string v7, "Download Now"

    .line 298
    new-instance v8, Lcom/openvehicles/OVMS/TabCar$2$1;

    invoke-direct {v8, p0}, Lcom/openvehicles/OVMS/TabCar$2$1;-><init>(Lcom/openvehicles/OVMS/TabCar$2;)V

    .line 297
    invoke-virtual {v6, v7, v8}, Landroid/app/AlertDialog$Builder;->setPositiveButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v6

    .line 306
    const-string v7, "Later"

    .line 307
    new-instance v8, Lcom/openvehicles/OVMS/TabCar$2$2;

    invoke-direct {v8, p0}, Lcom/openvehicles/OVMS/TabCar$2$2;-><init>(Lcom/openvehicles/OVMS/TabCar$2;)V

    .line 306
    invoke-virtual {v6, v7, v8}, Landroid/app/AlertDialog$Builder;->setNegativeButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 314
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    invoke-virtual {v1}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v7

    #setter for: Lcom/openvehicles/OVMS/TabCar;->lastUpdatedDialog:Landroid/app/AlertDialog;
    invoke-static {v6, v7}, Lcom/openvehicles/OVMS/TabCar;->access$6(Lcom/openvehicles/OVMS/TabCar;Landroid/app/AlertDialog;)V

    .line 315
    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCar$2;->this$0:Lcom/openvehicles/OVMS/TabCar;

    #getter for: Lcom/openvehicles/OVMS/TabCar;->lastUpdatedDialog:Landroid/app/AlertDialog;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabCar;->access$4(Lcom/openvehicles/OVMS/TabCar;)Landroid/app/AlertDialog;

    move-result-object v6

    invoke-virtual {v6}, Landroid/app/AlertDialog;->show()V

    goto/16 :goto_13

    .line 321
    .end local v1           #builder:Landroid/app/AlertDialog$Builder;
    :cond_1b
    const/16 v6, 0x8

    goto/16 :goto_14

    .line 323
    :cond_1c
    const/16 v6, 0x8

    goto/16 :goto_15

    .line 325
    :cond_1d
    const/16 v6, 0x8

    goto/16 :goto_16

    .line 327
    :cond_1e
    const/16 v6, 0x8

    goto/16 :goto_17

    .line 329
    :cond_1f
    const/16 v6, 0x8

    goto/16 :goto_18

    .line 333
    :cond_20
    const v6, 0x7f02003c

    goto/16 :goto_19

    .line 336
    :cond_21
    const v6, 0x7f02003d

    goto/16 :goto_1a

    .line 338
    :cond_22
    const/16 v6, 0x8

    goto/16 :goto_1b

    .line 341
    :cond_23
    const/16 v6, 0x8

    goto/16 :goto_1c

    .line 350
    .restart local v0       #RSSI:I
    :cond_24
    const/4 v6, 0x7

    if-ge v0, v6, :cond_25

    .line 351
    const v6, 0x7f020069

    :try_start_1
    invoke-virtual {v3, v6}, Landroid/widget/ImageView;->setImageResource(I)V

    goto/16 :goto_1d

    .line 360
    .end local v0           #RSSI:I
    :catch_0
    move-exception v6

    goto/16 :goto_1d

    .line 352
    .restart local v0       #RSSI:I
    :cond_25
    const/16 v6, 0xe

    if-ge v0, v6, :cond_26

    .line 353
    const v6, 0x7f02006a

    invoke-virtual {v3, v6}, Landroid/widget/ImageView;->setImageResource(I)V

    goto/16 :goto_1d

    .line 354
    :cond_26
    const/16 v6, 0x15

    if-ge v0, v6, :cond_27

    .line 355
    const v6, 0x7f02006b

    invoke-virtual {v3, v6}, Landroid/widget/ImageView;->setImageResource(I)V

    goto/16 :goto_1d

    .line 356
    :cond_27
    const/16 v6, 0x1c

    if-ge v0, v6, :cond_28

    .line 357
    const v6, 0x7f02006c

    invoke-virtual {v3, v6}, Landroid/widget/ImageView;->setImageResource(I)V

    goto/16 :goto_1d

    .line 359
    :cond_28
    const v6, 0x7f02006d

    invoke-virtual {v3, v6}, Landroid/widget/ImageView;->setImageResource(I)V
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_0

    goto/16 :goto_1d

    .line 364
    .end local v0           #RSSI:I
    :cond_29
    const/4 v6, 0x0

    goto/16 :goto_1e
.end method
