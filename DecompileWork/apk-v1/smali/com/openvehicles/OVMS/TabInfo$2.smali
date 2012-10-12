.class Lcom/openvehicles/OVMS/TabInfo$2;
.super Landroid/os/Handler;
.source "TabInfo.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/openvehicles/OVMS/TabInfo;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/openvehicles/OVMS/TabInfo;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/TabInfo;)V
    .locals 0
    .parameter

    .prologue
    .line 94
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 12
    .parameter "msg"

    .prologue
    const/4 v6, 0x4

    const/4 v10, 0x2

    const/4 v11, 0x1

    const/4 v5, 0x0

    .line 96
    iget-object v7, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #calls: Lcom/openvehicles/OVMS/TabInfo;->updateLastUpdatedView()V
    invoke-static {v7}, Lcom/openvehicles/OVMS/TabInfo;->access$000(Lcom/openvehicles/OVMS/TabInfo;)V

    .line 98
    iget-object v7, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    const v8, 0x7f060030

    invoke-virtual {v7, v8}, Lcom/openvehicles/OVMS/TabInfo;->findViewById(I)Landroid/view/View;

    move-result-object v4

    check-cast v4, Landroid/widget/TextView;

    .line 99
    .local v4, tv:Landroid/widget/TextView;
    iget-object v7, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v7}, Lcom/openvehicles/OVMS/TabInfo;->access$300(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v7

    iget-object v7, v7, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    invoke-virtual {v4, v7}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 101
    iget-object v7, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    const v8, 0x7f060036

    invoke-virtual {v7, v8}, Lcom/openvehicles/OVMS/TabInfo;->findViewById(I)Landroid/view/View;

    move-result-object v4

    .end local v4           #tv:Landroid/widget/TextView;
    check-cast v4, Landroid/widget/TextView;

    .line 102
    .restart local v4       #tv:Landroid/widget/TextView;
    iget-object v7, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    const v8, 0x7f040005

    invoke-virtual {v7, v8}, Lcom/openvehicles/OVMS/TabInfo;->getString(I)Ljava/lang/String;

    move-result-object v7

    new-array v8, v11, [Ljava/lang/Object;

    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabInfo;->access$300(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v9

    iget v9, v9, Lcom/openvehicles/OVMS/CarData;->Data_SOC:I

    invoke-static {v9}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v9

    aput-object v9, v8, v5

    invoke-static {v7, v8}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v7

    invoke-virtual {v4, v7}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 104
    iget-object v7, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    const v8, 0x7f060038

    invoke-virtual {v7, v8}, Lcom/openvehicles/OVMS/TabInfo;->findViewById(I)Landroid/view/View;

    move-result-object v4

    .end local v4           #tv:Landroid/widget/TextView;
    check-cast v4, Landroid/widget/TextView;

    .line 105
    .restart local v4       #tv:Landroid/widget/TextView;
    iget-object v7, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v7}, Lcom/openvehicles/OVMS/TabInfo;->access$300(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v7

    iget-object v7, v7, Lcom/openvehicles/OVMS/CarData;->Data_ChargeState:Ljava/lang/String;

    const-string v8, "charging"

    invoke-virtual {v7, v8}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v7

    if-eqz v7, :cond_1

    .line 106
    const-string v7, "Charging - %s (%sV %sA)"

    const/4 v8, 0x3

    new-array v8, v8, [Ljava/lang/Object;

    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabInfo;->access$300(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v9

    iget-object v9, v9, Lcom/openvehicles/OVMS/CarData;->Data_ChargeMode:Ljava/lang/String;

    aput-object v9, v8, v5

    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabInfo;->access$300(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v9

    iget v9, v9, Lcom/openvehicles/OVMS/CarData;->Data_LineVoltage:I

    invoke-static {v9}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v9

    aput-object v9, v8, v11

    iget-object v9, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v9}, Lcom/openvehicles/OVMS/TabInfo;->access$300(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v9

    iget v9, v9, Lcom/openvehicles/OVMS/CarData;->Data_ChargeCurrent:I

    invoke-static {v9}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v9

    aput-object v9, v8, v10

    invoke-static {v7, v8}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v7

    invoke-virtual {v4, v7}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 110
    :goto_0
    iget-object v7, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    const v8, 0x7f06003a

    invoke-virtual {v7, v8}, Lcom/openvehicles/OVMS/TabInfo;->findViewById(I)Landroid/view/View;

    move-result-object v4

    .end local v4           #tv:Landroid/widget/TextView;
    check-cast v4, Landroid/widget/TextView;

    .line 111
    .restart local v4       #tv:Landroid/widget/TextView;
    const-string v0, " km"

    .line 112
    .local v0, distanceUnit:Ljava/lang/String;
    const-string v3, " kph"

    .line 113
    .local v3, speedUnit:Ljava/lang/String;
    iget-object v7, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v7}, Lcom/openvehicles/OVMS/TabInfo;->access$300(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v7

    iget-object v7, v7, Lcom/openvehicles/OVMS/CarData;->Data_DistanceUnit:Ljava/lang/String;

    if-eqz v7, :cond_0

    iget-object v7, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v7}, Lcom/openvehicles/OVMS/TabInfo;->access$300(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v7

    iget-object v7, v7, Lcom/openvehicles/OVMS/CarData;->Data_DistanceUnit:Ljava/lang/String;

    const-string v8, "K"

    invoke-virtual {v7, v8}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v7

    if-nez v7, :cond_0

    .line 115
    const-string v0, " miles"

    .line 116
    const-string v3, " mph"

    .line 118
    :cond_0
    iget-object v7, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    const v8, 0x7f040003

    invoke-virtual {v7, v8}, Lcom/openvehicles/OVMS/TabInfo;->getString(I)Ljava/lang/String;

    move-result-object v7

    new-array v8, v10, [Ljava/lang/Object;

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v10, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v10}, Lcom/openvehicles/OVMS/TabInfo;->access$300(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v10

    iget v10, v10, Lcom/openvehicles/OVMS/CarData;->Data_IdealRange:I

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    aput-object v9, v8, v5

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v10, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v10}, Lcom/openvehicles/OVMS/TabInfo;->access$300(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v10

    iget v10, v10, Lcom/openvehicles/OVMS/CarData;->Data_EstimatedRange:I

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    aput-object v9, v8, v11

    invoke-static {v7, v8}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v7

    invoke-virtual {v4, v7}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 126
    iget-object v7, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    const v8, 0x7f06002c

    invoke-virtual {v7, v8}, Lcom/openvehicles/OVMS/TabInfo;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/ImageView;

    .line 127
    .local v1, iv:Landroid/widget/ImageView;
    invoke-virtual {v1, v6}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 129
    iget-object v7, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    const v8, 0x7f06002d

    invoke-virtual {v7, v8}, Lcom/openvehicles/OVMS/TabInfo;->findViewById(I)Landroid/view/View;

    move-result-object v1

    .end local v1           #iv:Landroid/widget/ImageView;
    check-cast v1, Landroid/widget/ImageView;

    .line 130
    .restart local v1       #iv:Landroid/widget/ImageView;
    iget-object v7, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v7}, Lcom/openvehicles/OVMS/TabInfo;->access$300(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v7

    iget-boolean v7, v7, Lcom/openvehicles/OVMS/CarData;->ParanoidMode:Z

    if-eqz v7, :cond_2

    :goto_1
    invoke-virtual {v1, v5}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 132
    iget-object v5, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    const v6, 0x7f060035

    invoke-virtual {v5, v6}, Lcom/openvehicles/OVMS/TabInfo;->findViewById(I)Landroid/view/View;

    move-result-object v1

    .end local v1           #iv:Landroid/widget/ImageView;
    check-cast v1, Landroid/widget/ImageView;

    .line 133
    .restart local v1       #iv:Landroid/widget/ImageView;
    invoke-virtual {v1}, Landroid/widget/ImageView;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v5

    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabInfo;->access$300(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_SOC:I

    mul-int/lit16 v6, v6, 0x10c

    div-int/lit8 v6, v6, 0x64

    iput v6, v5, Landroid/view/ViewGroup$LayoutParams;->width:I

    .line 136
    iget-object v5, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    const v6, 0x7f060032

    invoke-virtual {v5, v6}, Lcom/openvehicles/OVMS/TabInfo;->findViewById(I)Landroid/view/View;

    move-result-object v1

    .end local v1           #iv:Landroid/widget/ImageView;
    check-cast v1, Landroid/widget/ImageView;

    .line 137
    .restart local v1       #iv:Landroid/widget/ImageView;
    iget-object v5, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    invoke-virtual {v5}, Lcom/openvehicles/OVMS/TabInfo;->getResources()Landroid/content/res/Resources;

    move-result-object v5

    iget-object v6, p0, Lcom/openvehicles/OVMS/TabInfo$2;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/TabInfo;->access$300(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-object v6, v6, Lcom/openvehicles/OVMS/CarData;->VehicleImageDrawable:Ljava/lang/String;

    const-string v7, "drawable"

    const-string v8, "com.openvehicles.OVMS"

    invoke-virtual {v5, v6, v7, v8}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v2

    .line 138
    .local v2, resId:I
    invoke-virtual {v1, v2}, Landroid/widget/ImageView;->setImageResource(I)V

    .line 140
    new-instance v5, Lcom/openvehicles/OVMS/TabInfo$2$1;

    invoke-direct {v5, p0}, Lcom/openvehicles/OVMS/TabInfo$2$1;-><init>(Lcom/openvehicles/OVMS/TabInfo$2;)V

    invoke-virtual {v1, v5}, Landroid/widget/ImageView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 180
    return-void

    .line 108
    .end local v0           #distanceUnit:Ljava/lang/String;
    .end local v1           #iv:Landroid/widget/ImageView;
    .end local v2           #resId:I
    .end local v3           #speedUnit:Ljava/lang/String;
    :cond_1
    const-string v7, ""

    invoke-virtual {v4, v7}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_0

    .restart local v0       #distanceUnit:Ljava/lang/String;
    .restart local v1       #iv:Landroid/widget/ImageView;
    .restart local v3       #speedUnit:Ljava/lang/String;
    :cond_2
    move v5, v6

    .line 130
    goto :goto_1
.end method
