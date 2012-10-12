.class public Lcom/openvehicles/OVMS/CarEditor;
.super Landroid/app/Activity;
.source "CarEditor.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/openvehicles/OVMS/CarEditor$CustomSpinnerAdapter;
    }
.end annotation


# instance fields
.field private availableCarColors:Ljava/util/ArrayList;

.field private car:Lcom/openvehicles/OVMS/CarData;

.field private existingVehicleIDs:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private originalVehicleID:Ljava/lang/String;


# direct methods
.method public constructor <init>()V
    .locals 0

    .prologue
    .line 26
    invoke-direct {p0}, Landroid/app/Activity;-><init>()V

    .line 187
    return-void
.end method

.method static synthetic access$000(Lcom/openvehicles/OVMS/CarEditor;Ljava/lang/String;)V
    .locals 0
    .parameter "x0"
    .parameter "x1"

    .prologue
    .line 26
    invoke-direct {p0, p1}, Lcom/openvehicles/OVMS/CarEditor;->closeEditor(Ljava/lang/String;)V

    return-void
.end method

.method static synthetic access$100(Lcom/openvehicles/OVMS/CarEditor;)Ljava/lang/String;
    .locals 1
    .parameter "x0"

    .prologue
    .line 26
    iget-object v0, p0, Lcom/openvehicles/OVMS/CarEditor;->originalVehicleID:Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$200(Lcom/openvehicles/OVMS/CarEditor;)Ljava/util/ArrayList;
    .locals 1
    .parameter "x0"

    .prologue
    .line 26
    iget-object v0, p0, Lcom/openvehicles/OVMS/CarEditor;->existingVehicleIDs:Ljava/util/ArrayList;

    return-object v0
.end method

.method private closeEditor(Ljava/lang/String;)V
    .locals 6
    .parameter "actionCode"

    .prologue
    .line 161
    const/high16 v3, 0x7f06

    invoke-virtual {p0, v3}, Lcom/openvehicles/OVMS/CarEditor;->findViewById(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/widget/TextView;

    .line 162
    .local v2, tv:Landroid/widget/TextView;
    iget-object v3, p0, Lcom/openvehicles/OVMS/CarEditor;->car:Lcom/openvehicles/OVMS/CarData;

    invoke-virtual {v2}, Landroid/widget/TextView;->getText()Ljava/lang/CharSequence;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v4

    iput-object v4, v3, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    .line 163
    const v3, 0x7f060002

    invoke-virtual {p0, v3}, Lcom/openvehicles/OVMS/CarEditor;->findViewById(I)Landroid/view/View;

    move-result-object v2

    .end local v2           #tv:Landroid/widget/TextView;
    check-cast v2, Landroid/widget/TextView;

    .line 164
    .restart local v2       #tv:Landroid/widget/TextView;
    iget-object v3, p0, Lcom/openvehicles/OVMS/CarEditor;->car:Lcom/openvehicles/OVMS/CarData;

    invoke-virtual {v2}, Landroid/widget/TextView;->getText()Ljava/lang/CharSequence;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v4

    iput-object v4, v3, Lcom/openvehicles/OVMS/CarData;->CarPass:Ljava/lang/String;

    .line 165
    const v3, 0x7f060003

    invoke-virtual {p0, v3}, Lcom/openvehicles/OVMS/CarEditor;->findViewById(I)Landroid/view/View;

    move-result-object v2

    .end local v2           #tv:Landroid/widget/TextView;
    check-cast v2, Landroid/widget/TextView;

    .line 166
    .restart local v2       #tv:Landroid/widget/TextView;
    iget-object v3, p0, Lcom/openvehicles/OVMS/CarEditor;->car:Lcom/openvehicles/OVMS/CarData;

    invoke-virtual {v2}, Landroid/widget/TextView;->getText()Ljava/lang/CharSequence;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v4

    iput-object v4, v3, Lcom/openvehicles/OVMS/CarData;->UserPass:Ljava/lang/String;

    .line 167
    const v3, 0x7f060001

    invoke-virtual {p0, v3}, Lcom/openvehicles/OVMS/CarEditor;->findViewById(I)Landroid/view/View;

    move-result-object v2

    .end local v2           #tv:Landroid/widget/TextView;
    check-cast v2, Landroid/widget/TextView;

    .line 168
    .restart local v2       #tv:Landroid/widget/TextView;
    iget-object v3, p0, Lcom/openvehicles/OVMS/CarEditor;->car:Lcom/openvehicles/OVMS/CarData;

    invoke-virtual {v2}, Landroid/widget/TextView;->getText()Ljava/lang/CharSequence;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v4

    iput-object v4, v3, Lcom/openvehicles/OVMS/CarData;->ServerNameOrIP:Ljava/lang/String;

    .line 170
    const v3, 0x7f060004

    invoke-virtual {p0, v3}, Lcom/openvehicles/OVMS/CarEditor;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/Spinner;

    .line 171
    .local v1, spin:Landroid/widget/Spinner;
    iget-object v4, p0, Lcom/openvehicles/OVMS/CarEditor;->car:Lcom/openvehicles/OVMS/CarData;

    iget-object v3, p0, Lcom/openvehicles/OVMS/CarEditor;->availableCarColors:Ljava/util/ArrayList;

    invoke-virtual {v1}, Landroid/widget/Spinner;->getSelectedItemPosition()I

    move-result v5

    invoke-virtual {v3, v5}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/util/HashMap;

    const-string v5, "Name"

    invoke-virtual {v3, v5}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v3

    iput-object v3, v4, Lcom/openvehicles/OVMS/CarData;->VehicleImageDrawable:Ljava/lang/String;

    .line 173
    const-string v3, "Editor"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "Closing editor: "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 175
    new-instance v0, Landroid/content/Intent;

    invoke-direct {v0}, Landroid/content/Intent;-><init>()V

    .line 176
    .local v0, resultIntent:Landroid/content/Intent;
    const-string v3, "Car"

    iget-object v4, p0, Lcom/openvehicles/OVMS/CarEditor;->car:Lcom/openvehicles/OVMS/CarData;

    invoke-virtual {v0, v3, v4}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/io/Serializable;)Landroid/content/Intent;

    .line 177
    const-string v3, "ActionCode"

    invoke-virtual {v0, v3, p1}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    .line 178
    const-string v3, "OriginalVehicleID"

    iget-object v4, p0, Lcom/openvehicles/OVMS/CarEditor;->originalVehicleID:Ljava/lang/String;

    invoke-virtual {v0, v3, v4}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    .line 179
    const/4 v3, -0x1

    invoke-virtual {p0, v3, v0}, Lcom/openvehicles/OVMS/CarEditor;->setResult(ILandroid/content/Intent;)V

    .line 180
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/CarEditor;->finish()V

    .line 181
    return-void
.end method


# virtual methods
.method public onCreate(Landroid/os/Bundle;)V
    .locals 14
    .parameter "savedInstanceState"

    .prologue
    .line 31
    invoke-super {p0, p1}, Landroid/app/Activity;->onCreate(Landroid/os/Bundle;)V

    .line 32
    const/high16 v1, 0x7f03

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/CarEditor;->setContentView(I)V

    .line 34
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/CarEditor;->getIntent()Landroid/content/Intent;

    move-result-object v1

    invoke-virtual {v1}, Landroid/content/Intent;->getExtras()Landroid/os/Bundle;

    move-result-object v1

    const-string v2, "ExistingVehicleIDs"

    invoke-virtual {v1, v2}, Landroid/os/Bundle;->getSerializable(Ljava/lang/String;)Ljava/io/Serializable;

    move-result-object v1

    check-cast v1, Ljava/util/ArrayList;

    iput-object v1, p0, Lcom/openvehicles/OVMS/CarEditor;->existingVehicleIDs:Ljava/util/ArrayList;

    .line 35
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/CarEditor;->getIntent()Landroid/content/Intent;

    move-result-object v1

    invoke-virtual {v1}, Landroid/content/Intent;->getExtras()Landroid/os/Bundle;

    move-result-object v1

    const-string v2, "Car"

    invoke-virtual {v1, v2}, Landroid/os/Bundle;->containsKey(Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_1

    .line 38
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/CarEditor;->getIntent()Landroid/content/Intent;

    move-result-object v1

    invoke-virtual {v1}, Landroid/content/Intent;->getExtras()Landroid/os/Bundle;

    move-result-object v1

    const-string v2, "Car"

    invoke-virtual {v1, v2}, Landroid/os/Bundle;->getSerializable(Ljava/lang/String;)Ljava/io/Serializable;

    move-result-object v1

    check-cast v1, Lcom/openvehicles/OVMS/CarData;

    iput-object v1, p0, Lcom/openvehicles/OVMS/CarEditor;->car:Lcom/openvehicles/OVMS/CarData;

    .line 39
    iget-object v1, p0, Lcom/openvehicles/OVMS/CarEditor;->car:Lcom/openvehicles/OVMS/CarData;

    iget-object v1, v1, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    iput-object v1, p0, Lcom/openvehicles/OVMS/CarEditor;->originalVehicleID:Ljava/lang/String;

    .line 47
    :goto_0
    const v1, 0x7f060002

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/CarEditor;->findViewById(I)Landroid/view/View;

    move-result-object v13

    check-cast v13, Landroid/widget/TextView;

    .line 48
    .local v13, tv:Landroid/widget/TextView;
    iget-object v1, p0, Lcom/openvehicles/OVMS/CarEditor;->car:Lcom/openvehicles/OVMS/CarData;

    iget-object v1, v1, Lcom/openvehicles/OVMS/CarData;->CarPass:Ljava/lang/String;

    invoke-virtual {v13, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 49
    const v1, 0x7f060003

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/CarEditor;->findViewById(I)Landroid/view/View;

    move-result-object v13

    .end local v13           #tv:Landroid/widget/TextView;
    check-cast v13, Landroid/widget/TextView;

    .line 50
    .restart local v13       #tv:Landroid/widget/TextView;
    iget-object v1, p0, Lcom/openvehicles/OVMS/CarEditor;->car:Lcom/openvehicles/OVMS/CarData;

    iget-object v1, v1, Lcom/openvehicles/OVMS/CarData;->UserPass:Ljava/lang/String;

    invoke-virtual {v13, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 51
    const v1, 0x7f060001

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/CarEditor;->findViewById(I)Landroid/view/View;

    move-result-object v13

    .end local v13           #tv:Landroid/widget/TextView;
    check-cast v13, Landroid/widget/TextView;

    .line 52
    .restart local v13       #tv:Landroid/widget/TextView;
    iget-object v1, p0, Lcom/openvehicles/OVMS/CarEditor;->car:Lcom/openvehicles/OVMS/CarData;

    iget-object v1, v1, Lcom/openvehicles/OVMS/CarData;->ServerNameOrIP:Ljava/lang/String;

    invoke-virtual {v13, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 53
    const/high16 v1, 0x7f06

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/CarEditor;->findViewById(I)Landroid/view/View;

    move-result-object v13

    .end local v13           #tv:Landroid/widget/TextView;
    check-cast v13, Landroid/widget/TextView;

    .line 54
    .restart local v13       #tv:Landroid/widget/TextView;
    iget-object v1, p0, Lcom/openvehicles/OVMS/CarEditor;->car:Lcom/openvehicles/OVMS/CarData;

    iget-object v1, v1, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    invoke-virtual {v13, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 56
    const v1, 0x7f060006

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/CarEditor;->findViewById(I)Landroid/view/View;

    move-result-object v8

    check-cast v8, Landroid/widget/Button;

    .line 57
    .local v8, btn:Landroid/widget/Button;
    new-instance v1, Lcom/openvehicles/OVMS/CarEditor$1;

    invoke-direct {v1, p0}, Lcom/openvehicles/OVMS/CarEditor$1;-><init>(Lcom/openvehicles/OVMS/CarEditor;)V

    invoke-virtual {v8, v1}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 63
    const v1, 0x7f060007

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/CarEditor;->findViewById(I)Landroid/view/View;

    move-result-object v8

    .end local v8           #btn:Landroid/widget/Button;
    check-cast v8, Landroid/widget/Button;

    .line 64
    .restart local v8       #btn:Landroid/widget/Button;
    iget-object v1, p0, Lcom/openvehicles/OVMS/CarEditor;->originalVehicleID:Ljava/lang/String;

    const-string v2, ""

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_2

    .line 67
    const/4 v1, 0x4

    invoke-virtual {v8, v1}, Landroid/widget/Button;->setVisibility(I)V

    .line 89
    :goto_1
    const v1, 0x7f060008

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/CarEditor;->findViewById(I)Landroid/view/View;

    move-result-object v8

    .end local v8           #btn:Landroid/widget/Button;
    check-cast v8, Landroid/widget/Button;

    .line 90
    .restart local v8       #btn:Landroid/widget/Button;
    new-instance v1, Lcom/openvehicles/OVMS/CarEditor$3;

    invoke-direct {v1, p0}, Lcom/openvehicles/OVMS/CarEditor$3;-><init>(Lcom/openvehicles/OVMS/CarEditor;)V

    invoke-virtual {v8, v1}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 108
    const/16 v1, 0x17

    new-array v7, v1, [Ljava/lang/String;

    const/4 v1, 0x0

    const-string v2, "car_roadster_arcticwhite"

    aput-object v2, v7, v1

    const/4 v1, 0x1

    const-string v2, "car_roadster_brilliantyellow"

    aput-object v2, v7, v1

    const/4 v1, 0x2

    const-string v2, "car_roadster_electricblue"

    aput-object v2, v7, v1

    const/4 v1, 0x3

    const-string v2, "car_roadster_fushionred"

    aput-object v2, v7, v1

    const/4 v1, 0x4

    const-string v2, "car_roadster_glacierblue"

    aput-object v2, v7, v1

    const/4 v1, 0x5

    const-string v2, "car_roadster_jetblack"

    aput-object v2, v7, v1

    const/4 v1, 0x6

    const-string v2, "car_roadster_lightninggreen"

    aput-object v2, v7, v1

    const/4 v1, 0x7

    const-string v2, "car_roadster_obsidianblack"

    aput-object v2, v7, v1

    const/16 v1, 0x8

    const-string v2, "car_roadster_racinggreen"

    aput-object v2, v7, v1

    const/16 v1, 0x9

    const-string v2, "car_roadster_radiantred"

    aput-object v2, v7, v1

    const/16 v1, 0xa

    const-string v2, "car_roadster_sterlingsilver"

    aput-object v2, v7, v1

    const/16 v1, 0xb

    const-string v2, "car_roadster_thundergray"

    aput-object v2, v7, v1

    const/16 v1, 0xc

    const-string v2, "car_roadster_twilightblue"

    aput-object v2, v7, v1

    const/16 v1, 0xd

    const-string v2, "car_roadster_veryorange"

    aput-object v2, v7, v1

    const/16 v1, 0xe

    const-string v2, "car_models_anzabrown"

    aput-object v2, v7, v1

    const/16 v1, 0xf

    const-string v2, "car_models_catalinawhite"

    aput-object v2, v7, v1

    const/16 v1, 0x10

    const-string v2, "car_models_montereyblue"

    aput-object v2, v7, v1

    const/16 v1, 0x11

    const-string v2, "car_models_sansimeonsilver"

    aput-object v2, v7, v1

    const/16 v1, 0x12

    const-string v2, "car_models_sequolagreen"

    aput-object v2, v7, v1

    const/16 v1, 0x13

    const-string v2, "car_models_shastapearlwhite"

    aput-object v2, v7, v1

    const/16 v1, 0x14

    const-string v2, "car_models_sierrablack"

    aput-object v2, v7, v1

    const/16 v1, 0x15

    const-string v2, "car_models_signaturered"

    aput-object v2, v7, v1

    const/16 v1, 0x16

    const-string v2, "car_models_tiburongrey"

    aput-object v2, v7, v1

    .line 136
    .local v7, availableColors:[Ljava/lang/String;
    new-instance v1, Ljava/util/ArrayList;

    invoke-direct {v1}, Ljava/util/ArrayList;-><init>()V

    iput-object v1, p0, Lcom/openvehicles/OVMS/CarEditor;->availableCarColors:Ljava/util/ArrayList;

    .line 137
    const/4 v9, 0x0

    .line 138
    .local v9, currentCarDrawable:I
    const/4 v10, 0x0

    .local v10, i:I
    :goto_2
    array-length v1, v7

    if-ge v10, v1, :cond_3

    .line 139
    new-instance v11, Ljava/util/HashMap;

    invoke-direct {v11}, Ljava/util/HashMap;-><init>()V

    .line 141
    .local v11, item:Ljava/util/HashMap;,"Ljava/util/HashMap<Ljava/lang/String;Ljava/lang/Object;>;"
    aget-object v1, v7, v10

    iget-object v2, p0, Lcom/openvehicles/OVMS/CarEditor;->car:Lcom/openvehicles/OVMS/CarData;

    iget-object v2, v2, Lcom/openvehicles/OVMS/CarData;->VehicleImageDrawable:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    .line 142
    move v9, v10

    .line 144
    :cond_0
    const-string v1, "Name"

    aget-object v2, v7, v10

    invoke-virtual {v11, v1, v2}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 145
    const-string v1, "Icon"

    invoke-virtual {p0}, Lcom/openvehicles/OVMS/CarEditor;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    aget-object v3, v7, v10

    const-string v4, "drawable"

    const-string v5, "com.openvehicles.OVMS"

    invoke-virtual {v2, v3, v4, v5}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v2

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-virtual {v11, v1, v2}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 146
    iget-object v1, p0, Lcom/openvehicles/OVMS/CarEditor;->availableCarColors:Ljava/util/ArrayList;

    invoke-virtual {v1, v11}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 138
    add-int/lit8 v10, v10, 0x1

    goto :goto_2

    .line 43
    .end local v7           #availableColors:[Ljava/lang/String;
    .end local v8           #btn:Landroid/widget/Button;
    .end local v9           #currentCarDrawable:I
    .end local v10           #i:I
    .end local v11           #item:Ljava/util/HashMap;,"Ljava/util/HashMap<Ljava/lang/String;Ljava/lang/Object;>;"
    .end local v13           #tv:Landroid/widget/TextView;
    :cond_1
    new-instance v1, Lcom/openvehicles/OVMS/CarData;

    invoke-direct {v1}, Lcom/openvehicles/OVMS/CarData;-><init>()V

    iput-object v1, p0, Lcom/openvehicles/OVMS/CarEditor;->car:Lcom/openvehicles/OVMS/CarData;

    .line 44
    const-string v1, ""

    iput-object v1, p0, Lcom/openvehicles/OVMS/CarEditor;->originalVehicleID:Ljava/lang/String;

    goto/16 :goto_0

    .line 69
    .restart local v8       #btn:Landroid/widget/Button;
    .restart local v13       #tv:Landroid/widget/TextView;
    :cond_2
    new-instance v1, Lcom/openvehicles/OVMS/CarEditor$2;

    invoke-direct {v1, p0}, Lcom/openvehicles/OVMS/CarEditor$2;-><init>(Lcom/openvehicles/OVMS/CarEditor;)V

    invoke-virtual {v8, v1}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    goto/16 :goto_1

    .line 149
    .restart local v7       #availableColors:[Ljava/lang/String;
    .restart local v9       #currentCarDrawable:I
    .restart local v10       #i:I
    :cond_3
    const v1, 0x7f060004

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/CarEditor;->findViewById(I)Landroid/view/View;

    move-result-object v12

    check-cast v12, Landroid/widget/Spinner;

    .line 150
    .local v12, spin:Landroid/widget/Spinner;
    new-instance v0, Lcom/openvehicles/OVMS/CarEditor$CustomSpinnerAdapter;

    iget-object v3, p0, Lcom/openvehicles/OVMS/CarEditor;->availableCarColors:Ljava/util/ArrayList;

    const v4, 0x7f030005

    const/4 v1, 0x1

    new-array v5, v1, [Ljava/lang/String;

    const/4 v1, 0x0

    const-string v2, "Icon"

    aput-object v2, v5, v1

    const/4 v1, 0x1

    new-array v6, v1, [I

    const/4 v1, 0x0

    const v2, 0x7f060026

    aput v2, v6, v1

    move-object v1, p0

    move-object v2, p0

    invoke-direct/range {v0 .. v6}, Lcom/openvehicles/OVMS/CarEditor$CustomSpinnerAdapter;-><init>(Lcom/openvehicles/OVMS/CarEditor;Landroid/content/Context;Ljava/util/List;I[Ljava/lang/String;[I)V

    .line 152
    .local v0, adapter:Lcom/openvehicles/OVMS/CarEditor$CustomSpinnerAdapter;
    invoke-virtual {v12, v0}, Landroid/widget/Spinner;->setAdapter(Landroid/widget/SpinnerAdapter;)V

    .line 153
    invoke-virtual {v12, v9}, Landroid/widget/Spinner;->setSelection(I)V

    .line 154
    return-void
.end method
