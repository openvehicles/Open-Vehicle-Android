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
    .line 198
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;->this$0:Lcom/openvehicles/OVMS/TabCars;

    .line 199
    invoke-direct {p0, p2, p3, p4}, Landroid/widget/ArrayAdapter;-><init>(Landroid/content/Context;I[Ljava/lang/Object;)V

    .line 200
    iput-object p4, p0, Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;->items:[Lcom/openvehicles/OVMS/CarData;

    .line 201
    return-void
.end method

.method static synthetic access$0(Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;)Lcom/openvehicles/OVMS/TabCars;
    .locals 1
    .parameter

    .prologue
    .line 193
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;->this$0:Lcom/openvehicles/OVMS/TabCars;

    return-object v0
.end method


# virtual methods
.method public getView(ILandroid/view/View;Landroid/view/ViewGroup;)Landroid/view/View;
    .locals 13
    .parameter "position"
    .parameter "convertView"
    .parameter "parent"

    .prologue
    .line 205
    move-object v6, p2

    .line 206
    .local v6, v:Landroid/view/View;
    if-nez v6, :cond_0

    .line 207
    iget-object v8, p0, Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;->this$0:Lcom/openvehicles/OVMS/TabCars;

    const-string v9, "layout_inflater"

    invoke-virtual {v8, v9}, Lcom/openvehicles/OVMS/TabCars;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v7

    check-cast v7, Landroid/view/LayoutInflater;

    .line 208
    .local v7, vi:Landroid/view/LayoutInflater;
    const v8, 0x7f03000d

    const/4 v9, 0x0

    invoke-virtual {v7, v8, v9}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object v6

    .line 211
    .end local v7           #vi:Landroid/view/LayoutInflater;
    :cond_0
    iget-object v8, p0, Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;->items:[Lcom/openvehicles/OVMS/CarData;

    aget-object v2, v8, p1

    .line 212
    .local v2, it:Lcom/openvehicles/OVMS/CarData;
    if-eqz v2, :cond_1

    .line 214
    const v8, 0x7f09003a

    invoke-virtual {v6, v8}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v3

    .line 213
    check-cast v3, Landroid/widget/ImageView;

    .line 217
    .local v3, iv:Landroid/widget/ImageView;
    new-instance v8, Ljava/lang/StringBuilder;

    iget-object v9, p0, Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;->this$0:Lcom/openvehicles/OVMS/TabCars;

    .line 218
    invoke-virtual {v9}, Lcom/openvehicles/OVMS/TabCars;->getCacheDir()Ljava/io/File;

    move-result-object v9

    invoke-virtual {v9}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v9

    invoke-static {v9}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v9

    invoke-direct {v8, v9}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 219
    const-string v9, "/%s.png"

    const/4 v10, 0x1

    new-array v10, v10, [Ljava/lang/Object;

    const/4 v11, 0x0

    iget-object v12, v2, Lcom/openvehicles/OVMS/CarData;->VehicleImageDrawable:Ljava/lang/String;

    aput-object v12, v10, v11

    invoke-static {v9, v10}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    .line 217
    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    invoke-static {v8}, Landroid/graphics/BitmapFactory;->decodeFile(Ljava/lang/String;)Landroid/graphics/Bitmap;

    move-result-object v1

    .line 220
    .local v1, carImage:Landroid/graphics/Bitmap;
    if-eqz v1, :cond_2

    .line 221
    invoke-virtual {v3, v1}, Landroid/widget/ImageView;->setImageBitmap(Landroid/graphics/Bitmap;)V

    .line 228
    :goto_0
    const v8, 0x7f09003b

    invoke-virtual {v6, v8}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v5

    check-cast v5, Landroid/widget/TextView;

    .line 229
    .local v5, tv:Landroid/widget/TextView;
    iget-object v8, v2, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    invoke-virtual {v5, v8}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 231
    const v8, 0x7f09003c

    invoke-virtual {v6, v8}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v5

    .end local v5           #tv:Landroid/widget/TextView;
    check-cast v5, Landroid/widget/TextView;

    .line 232
    .restart local v5       #tv:Landroid/widget/TextView;
    const-string v8, "@%s"

    const/4 v9, 0x1

    new-array v9, v9, [Ljava/lang/Object;

    const/4 v10, 0x0

    iget-object v11, v2, Lcom/openvehicles/OVMS/CarData;->ServerNameOrIP:Ljava/lang/String;

    aput-object v11, v9, v10

    invoke-static {v8, v9}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v5, v8}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 234
    const v8, 0x7f09003d

    invoke-virtual {v6, v8}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageButton;

    .line 235
    .local v0, btn:Landroid/widget/ImageButton;
    iget-object v8, v2, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    const-string v9, "DEMO"

    invoke-virtual {v8, v9}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v8

    if-eqz v8, :cond_3

    .line 236
    const/4 v8, 0x4

    invoke-virtual {v0, v8}, Landroid/widget/ImageButton;->setVisibility(I)V

    .line 252
    .end local v0           #btn:Landroid/widget/ImageButton;
    .end local v1           #carImage:Landroid/graphics/Bitmap;
    .end local v3           #iv:Landroid/widget/ImageView;
    .end local v5           #tv:Landroid/widget/TextView;
    :cond_1
    :goto_1
    return-object v6

    .line 225
    .restart local v1       #carImage:Landroid/graphics/Bitmap;
    .restart local v3       #iv:Landroid/widget/ImageView;
    :cond_2
    iget-object v8, p0, Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;->this$0:Lcom/openvehicles/OVMS/TabCars;

    invoke-virtual {v8}, Lcom/openvehicles/OVMS/TabCars;->getResources()Landroid/content/res/Resources;

    move-result-object v8

    const-string v9, "%s96x44"

    const/4 v10, 0x1

    new-array v10, v10, [Ljava/lang/Object;

    const/4 v11, 0x0

    iget-object v12, v2, Lcom/openvehicles/OVMS/CarData;->VehicleImageDrawable:Ljava/lang/String;

    aput-object v12, v10, v11

    invoke-static {v9, v10}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v9

    const-string v10, "drawable"

    const-string v11, "com.openvehicles.OVMS"

    invoke-virtual {v8, v9, v10, v11}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v4

    .line 226
    .local v4, resId:I
    invoke-virtual {v3, v4}, Landroid/widget/ImageView;->setImageResource(I)V

    goto :goto_0

    .line 238
    .end local v4           #resId:I
    .restart local v0       #btn:Landroid/widget/ImageButton;
    .restart local v5       #tv:Landroid/widget/TextView;
    :cond_3
    const/4 v8, 0x1

    invoke-virtual {v0, v8}, Landroid/widget/ImageButton;->setClickable(Z)V

    .line 239
    invoke-virtual {v0, v2}, Landroid/widget/ImageButton;->setTag(Ljava/lang/Object;)V

    .line 240
    new-instance v8, Lcom/openvehicles/OVMS/TabCars$ItemsAdapter$1;

    invoke-direct {v8, p0}, Lcom/openvehicles/OVMS/TabCars$ItemsAdapter$1;-><init>(Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;)V

    invoke-virtual {v0, v8}, Landroid/widget/ImageButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    goto :goto_1
.end method
