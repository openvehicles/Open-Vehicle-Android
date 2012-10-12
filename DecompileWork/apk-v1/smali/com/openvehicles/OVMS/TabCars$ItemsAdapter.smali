.class Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;
.super Landroid/widget/ArrayAdapter;
.source "TabCars.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/openvehicles/OVMS/TabCars;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "ItemsAdapter"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Landroid/widget/ArrayAdapter",
        "<",
        "Lcom/openvehicles/OVMS/CarData;",
        ">;"
    }
.end annotation


# instance fields
.field private items:[Lcom/openvehicles/OVMS/CarData;

.field final synthetic this$0:Lcom/openvehicles/OVMS/TabCars;


# direct methods
.method public constructor <init>(Lcom/openvehicles/OVMS/TabCars;Landroid/content/Context;I[Lcom/openvehicles/OVMS/CarData;)V
    .locals 0
    .parameter
    .parameter "context"
    .parameter "textViewResourceId"
    .parameter "items"

    .prologue
    .line 196
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;->this$0:Lcom/openvehicles/OVMS/TabCars;

    .line 197
    invoke-direct {p0, p2, p3, p4}, Landroid/widget/ArrayAdapter;-><init>(Landroid/content/Context;I[Ljava/lang/Object;)V

    .line 198
    iput-object p4, p0, Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;->items:[Lcom/openvehicles/OVMS/CarData;

    .line 199
    return-void
.end method


# virtual methods
.method public getView(ILandroid/view/View;Landroid/view/ViewGroup;)Landroid/view/View;
    .locals 12
    .parameter "position"
    .parameter "convertView"
    .parameter "parent"

    .prologue
    const/4 v11, 0x1

    .line 203
    move-object v5, p2

    .line 204
    .local v5, v:Landroid/view/View;
    if-nez v5, :cond_0

    .line 205
    iget-object v7, p0, Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;->this$0:Lcom/openvehicles/OVMS/TabCars;

    const-string v8, "layout_inflater"

    invoke-virtual {v7, v8}, Lcom/openvehicles/OVMS/TabCars;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v6

    check-cast v6, Landroid/view/LayoutInflater;

    .line 206
    .local v6, vi:Landroid/view/LayoutInflater;
    const v7, 0x7f030007

    const/4 v8, 0x0

    invoke-virtual {v6, v7, v8}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object v5

    .line 209
    .end local v6           #vi:Landroid/view/LayoutInflater;
    :cond_0
    iget-object v7, p0, Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;->items:[Lcom/openvehicles/OVMS/CarData;

    aget-object v1, v7, p1

    .line 210
    .local v1, it:Lcom/openvehicles/OVMS/CarData;
    if-eqz v1, :cond_1

    .line 211
    const v7, 0x7f060028

    invoke-virtual {v5, v7}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/widget/ImageView;

    .line 213
    .local v2, iv:Landroid/widget/ImageView;
    iget-object v7, p0, Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;->this$0:Lcom/openvehicles/OVMS/TabCars;

    invoke-virtual {v7}, Lcom/openvehicles/OVMS/TabCars;->getResources()Landroid/content/res/Resources;

    move-result-object v7

    iget-object v8, v1, Lcom/openvehicles/OVMS/CarData;->VehicleImageDrawable:Ljava/lang/String;

    const-string v9, "drawable"

    const-string v10, "com.openvehicles.OVMS"

    invoke-virtual {v7, v8, v9, v10}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v3

    .line 214
    .local v3, resId:I
    invoke-virtual {v2, v3}, Landroid/widget/ImageView;->setImageResource(I)V

    .line 215
    const v7, 0x7f060029

    invoke-virtual {v5, v7}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v4

    check-cast v4, Landroid/widget/TextView;

    .line 216
    .local v4, tv:Landroid/widget/TextView;
    const-string v7, "%s\n@%s"

    const/4 v8, 0x2

    new-array v8, v8, [Ljava/lang/Object;

    const/4 v9, 0x0

    iget-object v10, v1, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    aput-object v10, v8, v9

    iget-object v9, v1, Lcom/openvehicles/OVMS/CarData;->ServerNameOrIP:Ljava/lang/String;

    aput-object v9, v8, v11

    invoke-static {v7, v8}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v7

    invoke-virtual {v4, v7}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 218
    const v7, 0x7f06002a

    invoke-virtual {v5, v7}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageButton;

    .line 219
    .local v0, btn:Landroid/widget/ImageButton;
    iget-object v7, v1, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    const-string v8, "DEMO"

    invoke-virtual {v7, v8}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v7

    if-eqz v7, :cond_2

    .line 220
    const/4 v7, 0x4

    invoke-virtual {v0, v7}, Landroid/widget/ImageButton;->setVisibility(I)V

    .line 236
    .end local v0           #btn:Landroid/widget/ImageButton;
    .end local v2           #iv:Landroid/widget/ImageView;
    .end local v3           #resId:I
    .end local v4           #tv:Landroid/widget/TextView;
    :cond_1
    :goto_0
    return-object v5

    .line 222
    .restart local v0       #btn:Landroid/widget/ImageButton;
    .restart local v2       #iv:Landroid/widget/ImageView;
    .restart local v3       #resId:I
    .restart local v4       #tv:Landroid/widget/TextView;
    :cond_2
    invoke-virtual {v0, v11}, Landroid/widget/ImageButton;->setClickable(Z)V

    .line 223
    invoke-virtual {v0, v1}, Landroid/widget/ImageButton;->setTag(Ljava/lang/Object;)V

    .line 224
    new-instance v7, Lcom/openvehicles/OVMS/TabCars$ItemsAdapter$1;

    invoke-direct {v7, p0}, Lcom/openvehicles/OVMS/TabCars$ItemsAdapter$1;-><init>(Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;)V

    invoke-virtual {v0, v7}, Landroid/widget/ImageButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    goto :goto_0
.end method
