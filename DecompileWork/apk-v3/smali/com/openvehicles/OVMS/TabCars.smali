.class public Lcom/openvehicles/OVMS/TabCars;
.super Landroid/app/ListActivity;
.source "TabCars.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;
    }
.end annotation


# instance fields
.field private _allSavedCars:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList",
            "<",
            "Lcom/openvehicles/OVMS/CarData;",
            ">;"
        }
    .end annotation
.end field

.field private adapter:Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;

.field private carsList:[Lcom/openvehicles/OVMS/CarData;

.field private handler:Landroid/os/Handler;

.field private mContext:Landroid/content/Context;


# direct methods
.method public constructor <init>()V
    .locals 1

    .prologue
    .line 37
    invoke-direct {p0}, Landroid/app/ListActivity;-><init>()V

    .line 262
    new-instance v0, Lcom/openvehicles/OVMS/TabCars$1;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/TabCars$1;-><init>(Lcom/openvehicles/OVMS/TabCars;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabCars;->handler:Landroid/os/Handler;

    .line 37
    return-void
.end method

.method static synthetic access$0(Lcom/openvehicles/OVMS/TabCars;)Landroid/content/Context;
    .locals 1
    .parameter

    .prologue
    .line 80
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabCars;->mContext:Landroid/content/Context;

    return-object v0
.end method

.method static synthetic access$1(Lcom/openvehicles/OVMS/TabCars;)[Lcom/openvehicles/OVMS/CarData;
    .locals 1
    .parameter

    .prologue
    .line 79
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabCars;->carsList:[Lcom/openvehicles/OVMS/CarData;

    return-object v0
.end method

.method static synthetic access$2(Lcom/openvehicles/OVMS/TabCars;Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 77
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabCars;->adapter:Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;

    return-void
.end method

.method static synthetic access$3(Lcom/openvehicles/OVMS/TabCars;)Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;
    .locals 1
    .parameter

    .prologue
    .line 77
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabCars;->adapter:Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;

    return-object v0
.end method

.method static synthetic access$4(Lcom/openvehicles/OVMS/TabCars;Lcom/openvehicles/OVMS/CarData;)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 87
    invoke-direct {p0, p1}, Lcom/openvehicles/OVMS/TabCars;->editCar(Lcom/openvehicles/OVMS/CarData;)V

    return-void
.end method

.method private carClicked(Lcom/openvehicles/OVMS/CarData;)V
    .locals 1
    .parameter "carSelected"

    .prologue
    .line 84
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TabCars;->getParent()Landroid/app/Activity;

    move-result-object v0

    check-cast v0, Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v0, p1}, Lcom/openvehicles/OVMS/OVMSActivity;->ChangeCar(Lcom/openvehicles/OVMS/CarData;)V

    .line 85
    return-void
.end method

.method private editCar(Lcom/openvehicles/OVMS/CarData;)V
    .locals 8
    .parameter "car"

    .prologue
    const/4 v7, 0x0

    .line 90
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    .line 91
    .local v0, existingVehicleIDs:Ljava/util/ArrayList;,"Ljava/util/ArrayList<Ljava/lang/String;>;"
    const/4 v1, 0x0

    .local v1, idx:I
    :goto_0
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabCars;->_allSavedCars:Ljava/util/ArrayList;

    invoke-virtual {v3}, Ljava/util/ArrayList;->size()I

    move-result v3

    if-lt v1, v3, :cond_1

    .line 94
    const-string v3, "OVMS"

    const-string v4, "Starting car editor (%s in existing cars list)"

    const/4 v5, 0x1

    new-array v5, v5, [Ljava/lang/Object;

    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    move-result v6

    invoke-static {v6}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v6

    aput-object v6, v5, v7

    invoke-static {v4, v5}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 96
    new-instance v2, Landroid/content/Intent;

    iget-object v3, p0, Lcom/openvehicles/OVMS/TabCars;->mContext:Landroid/content/Context;

    const-class v4, Lcom/openvehicles/OVMS/CarEditor;

    invoke-direct {v2, v3, v4}, Landroid/content/Intent;-><init>(Landroid/content/Context;Ljava/lang/Class;)V

    .line 97
    .local v2, intent:Landroid/content/Intent;
    const-string v3, "ExistingVehicleIDs"

    invoke-virtual {v2, v3, v0}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/io/Serializable;)Landroid/content/Intent;

    .line 98
    if-eqz p1, :cond_0

    .line 101
    iget-object v3, p1, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    invoke-virtual {v0, v3}, Ljava/util/ArrayList;->remove(Ljava/lang/Object;)Z

    .line 102
    const-string v3, "Car"

    invoke-virtual {v2, v3, p1}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/io/Serializable;)Landroid/content/Intent;

    .line 104
    :cond_0
    invoke-virtual {p0, v2, v7}, Lcom/openvehicles/OVMS/TabCars;->startActivityForResult(Landroid/content/Intent;I)V

    .line 105
    return-void

    .line 92
    .end local v2           #intent:Landroid/content/Intent;
    :cond_1
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabCars;->_allSavedCars:Ljava/util/ArrayList;

    invoke-virtual {v3, v1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/openvehicles/OVMS/CarData;

    iget-object v3, v3, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    invoke-virtual {v0, v3}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 91
    add-int/lit8 v1, v1, 0x1

    goto :goto_0
.end method


# virtual methods
.method public LoadCars(Ljava/util/ArrayList;)V
    .locals 3
    .parameter
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/ArrayList",
            "<",
            "Lcom/openvehicles/OVMS/CarData;",
            ">;)V"
        }
    .end annotation

    .prologue
    .line 271
    .local p1, allSavedCars:Ljava/util/ArrayList;,"Ljava/util/ArrayList<Lcom/openvehicles/OVMS/CarData;>;"
    invoke-virtual {p1}, Ljava/util/ArrayList;->size()I

    move-result v1

    new-array v0, v1, [Lcom/openvehicles/OVMS/CarData;

    .line 272
    .local v0, carArray:[Lcom/openvehicles/OVMS/CarData;
    invoke-virtual {p1, v0}, Ljava/util/ArrayList;->toArray([Ljava/lang/Object;)[Ljava/lang/Object;

    .line 274
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabCars;->_allSavedCars:Ljava/util/ArrayList;

    .line 275
    iput-object v0, p0, Lcom/openvehicles/OVMS/TabCars;->carsList:[Lcom/openvehicles/OVMS/CarData;

    .line 276
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabCars;->handler:Landroid/os/Handler;

    const/4 v2, 0x0

    invoke-virtual {v1, v2}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    .line 278
    return-void
.end method

.method protected onActivityResult(IILandroid/content/Intent;)V
    .locals 12
    .parameter "requestCode"
    .parameter "resultCode"
    .parameter "data"

    .prologue
    const/16 v11, 0x3e8

    const/4 v10, 0x1

    const/4 v9, 0x0

    .line 109
    invoke-super {p0, p1, p2, p3}, Landroid/app/ListActivity;->onActivityResult(IILandroid/content/Intent;)V

    .line 110
    if-nez p3, :cond_0

    .line 112
    const-string v5, "OVMS"

    const-string v6, "Editor cancelled."

    invoke-static {v5, v6}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 191
    :goto_0
    return-void

    .line 116
    :cond_0
    invoke-virtual {p3}, Landroid/content/Intent;->getExtras()Landroid/os/Bundle;

    move-result-object v5

    const-string v6, "Car"

    invoke-virtual {v5, v6}, Landroid/os/Bundle;->getSerializable(Ljava/lang/String;)Ljava/io/Serializable;

    move-result-object v2

    check-cast v2, Lcom/openvehicles/OVMS/CarData;

    .line 117
    .local v2, editedCar:Lcom/openvehicles/OVMS/CarData;
    invoke-virtual {p3}, Landroid/content/Intent;->getExtras()Landroid/os/Bundle;

    move-result-object v5

    const-string v6, "ActionCode"

    invoke-virtual {v5, v6}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 118
    .local v0, actionCode:Ljava/lang/String;
    invoke-virtual {p3}, Landroid/content/Intent;->getExtras()Landroid/os/Bundle;

    move-result-object v5

    const-string v6, "OriginalVehicleID"

    invoke-virtual {v5, v6}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v4

    .line 120
    .local v4, originalVehicleID:Ljava/lang/String;
    const-string v5, "OVMS"

    const-string v6, "Editor closed with result action: %s %s"

    const/4 v7, 0x2

    new-array v7, v7, [Ljava/lang/Object;

    aput-object v0, v7, v9

    aput-object v4, v7, v10

    invoke-static {v6, v7}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 123
    const-string v5, "CANCEL"

    invoke-virtual {v0, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_1

    .line 125
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TabCars;->getBaseContext()Landroid/content/Context;

    move-result-object v5

    const-string v6, "Cancelled"

    invoke-static {v5, v6, v11}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v5

    invoke-virtual {v5}, Landroid/widget/Toast;->show()V

    goto :goto_0

    .line 128
    :cond_1
    const-string v5, "SAVE"

    invoke-virtual {v0, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_9

    .line 131
    iget-object v5, v2, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    iget-object v6, v2, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    invoke-virtual {v5, v6}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-nez v5, :cond_2

    .line 133
    const/4 v3, 0x0

    .local v3, idx:I
    :goto_1
    iget-object v5, p0, Lcom/openvehicles/OVMS/TabCars;->_allSavedCars:Ljava/util/ArrayList;

    invoke-virtual {v5}, Ljava/util/ArrayList;->size()I

    move-result v5

    if-lt v3, v5, :cond_4

    .line 144
    .end local v3           #idx:I
    :cond_2
    invoke-virtual {v4}, Ljava/lang/String;->length()I

    move-result v5

    if-lez v5, :cond_8

    .line 146
    const/4 v3, 0x0

    .restart local v3       #idx:I
    :goto_2
    iget-object v5, p0, Lcom/openvehicles/OVMS/TabCars;->_allSavedCars:Ljava/util/ArrayList;

    invoke-virtual {v5}, Ljava/util/ArrayList;->size()I

    move-result v5

    if-lt v3, v5, :cond_6

    .line 180
    .end local v3           #idx:I
    :cond_3
    :goto_3
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TabCars;->getParent()Landroid/app/Activity;

    move-result-object v5

    check-cast v5, Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v5}, Lcom/openvehicles/OVMS/OVMSActivity;->saveCars()V

    .line 184
    iget-object v5, p0, Lcom/openvehicles/OVMS/TabCars;->_allSavedCars:Ljava/util/ArrayList;

    invoke-virtual {v5}, Ljava/util/ArrayList;->size()I

    move-result v5

    new-array v1, v5, [Lcom/openvehicles/OVMS/CarData;

    .line 185
    .local v1, carArray:[Lcom/openvehicles/OVMS/CarData;
    iget-object v5, p0, Lcom/openvehicles/OVMS/TabCars;->_allSavedCars:Ljava/util/ArrayList;

    invoke-virtual {v5, v1}, Ljava/util/ArrayList;->toArray([Ljava/lang/Object;)[Ljava/lang/Object;

    .line 187
    iput-object v1, p0, Lcom/openvehicles/OVMS/TabCars;->carsList:[Lcom/openvehicles/OVMS/CarData;

    .line 188
    new-instance v5, Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;

    iget-object v6, p0, Lcom/openvehicles/OVMS/TabCars;->mContext:Landroid/content/Context;

    const v7, 0x7f03000d

    .line 189
    iget-object v8, p0, Lcom/openvehicles/OVMS/TabCars;->carsList:[Lcom/openvehicles/OVMS/CarData;

    invoke-direct {v5, p0, v6, v7, v8}, Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;-><init>(Lcom/openvehicles/OVMS/TabCars;Landroid/content/Context;I[Lcom/openvehicles/OVMS/CarData;)V

    .line 188
    iput-object v5, p0, Lcom/openvehicles/OVMS/TabCars;->adapter:Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;

    .line 190
    iget-object v5, p0, Lcom/openvehicles/OVMS/TabCars;->adapter:Lcom/openvehicles/OVMS/TabCars$ItemsAdapter;

    invoke-virtual {p0, v5}, Lcom/openvehicles/OVMS/TabCars;->setListAdapter(Landroid/widget/ListAdapter;)V

    goto/16 :goto_0

    .line 135
    .end local v1           #carArray:[Lcom/openvehicles/OVMS/CarData;
    .restart local v3       #idx:I
    :cond_4
    iget-object v5, p0, Lcom/openvehicles/OVMS/TabCars;->_allSavedCars:Ljava/util/ArrayList;

    invoke-virtual {v5, v3}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Lcom/openvehicles/OVMS/CarData;

    iget-object v5, v5, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    iget-object v6, v2, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    invoke-virtual {v5, v6}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_5

    .line 137
    const-string v5, "OVMS"

    const-string v6, "Vehicle ID duplicated. Aborting save."

    new-array v7, v9, [Ljava/lang/Object;

    invoke-static {v6, v7}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 138
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TabCars;->getBaseContext()Landroid/content/Context;

    move-result-object v5

    const-string v6, "Duplicated vehicle ID - Changes not saved"

    invoke-static {v5, v6, v11}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v5

    invoke-virtual {v5}, Landroid/widget/Toast;->show()V

    goto/16 :goto_0

    .line 133
    :cond_5
    add-int/lit8 v3, v3, 0x1

    goto :goto_1

    .line 149
    :cond_6
    iget-object v5, p0, Lcom/openvehicles/OVMS/TabCars;->_allSavedCars:Ljava/util/ArrayList;

    invoke-virtual {v5, v3}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Lcom/openvehicles/OVMS/CarData;

    iget-object v5, v5, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    invoke-virtual {v5, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_7

    .line 151
    const-string v5, "OVMS"

    const-string v6, "Saved: %s"

    new-array v7, v10, [Ljava/lang/Object;

    aput-object v4, v7, v9

    invoke-static {v6, v7}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 152
    iget-object v5, p0, Lcom/openvehicles/OVMS/TabCars;->_allSavedCars:Ljava/util/ArrayList;

    invoke-virtual {v5, v3, v2}, Ljava/util/ArrayList;->set(ILjava/lang/Object;)Ljava/lang/Object;

    .line 153
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TabCars;->getBaseContext()Landroid/content/Context;

    move-result-object v5

    const-string v6, "%s saved"

    new-array v7, v10, [Ljava/lang/Object;

    iget-object v8, v2, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    aput-object v8, v7, v9

    invoke-static {v6, v7}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6, v11}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v5

    invoke-virtual {v5}, Landroid/widget/Toast;->show()V

    goto/16 :goto_3

    .line 146
    :cond_7
    add-int/lit8 v3, v3, 0x1

    goto/16 :goto_2

    .line 160
    .end local v3           #idx:I
    :cond_8
    const-string v5, "OVMS"

    const-string v6, "Added: %s"

    new-array v7, v10, [Ljava/lang/Object;

    iget-object v8, v2, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    aput-object v8, v7, v9

    invoke-static {v6, v7}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 161
    iget-object v5, p0, Lcom/openvehicles/OVMS/TabCars;->_allSavedCars:Ljava/util/ArrayList;

    invoke-virtual {v5, v2}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 162
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TabCars;->getBaseContext()Landroid/content/Context;

    move-result-object v5

    const-string v6, "%s added"

    new-array v7, v10, [Ljava/lang/Object;

    iget-object v8, v2, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    aput-object v8, v7, v9

    invoke-static {v6, v7}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6, v11}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v5

    invoke-virtual {v5}, Landroid/widget/Toast;->show()V

    goto/16 :goto_3

    .line 165
    :cond_9
    const-string v5, "DELETE"

    invoke-virtual {v0, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_3

    .line 167
    const/4 v3, 0x0

    .restart local v3       #idx:I
    :goto_4
    iget-object v5, p0, Lcom/openvehicles/OVMS/TabCars;->_allSavedCars:Ljava/util/ArrayList;

    invoke-virtual {v5}, Ljava/util/ArrayList;->size()I

    move-result v5

    if-ge v3, v5, :cond_3

    .line 169
    iget-object v5, p0, Lcom/openvehicles/OVMS/TabCars;->_allSavedCars:Ljava/util/ArrayList;

    invoke-virtual {v5, v3}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Lcom/openvehicles/OVMS/CarData;

    iget-object v5, v5, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    invoke-virtual {v5, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_a

    .line 171
    const-string v5, "OVMS"

    const-string v6, "Deleted: %s"

    new-array v7, v10, [Ljava/lang/Object;

    aput-object v4, v7, v9

    invoke-static {v6, v7}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 172
    iget-object v5, p0, Lcom/openvehicles/OVMS/TabCars;->_allSavedCars:Ljava/util/ArrayList;

    invoke-virtual {v5, v3}, Ljava/util/ArrayList;->remove(I)Ljava/lang/Object;

    .line 173
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TabCars;->getBaseContext()Landroid/content/Context;

    move-result-object v5

    const-string v6, "%s deleted"

    new-array v7, v10, [Ljava/lang/Object;

    iget-object v8, v2, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    aput-object v8, v7, v9

    invoke-static {v6, v7}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6, v11}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v5

    invoke-virtual {v5}, Landroid/widget/Toast;->show()V

    goto/16 :goto_3

    .line 167
    :cond_a
    add-int/lit8 v3, v3, 0x1

    goto :goto_4
.end method

.method public onContextItemSelected(Landroid/view/MenuItem;)Z
    .locals 3
    .parameter "item"

    .prologue
    const/4 v1, 0x1

    .line 66
    invoke-interface {p1}, Landroid/view/MenuItem;->getMenuInfo()Landroid/view/ContextMenu$ContextMenuInfo;

    move-result-object v0

    check-cast v0, Landroid/widget/AdapterView$AdapterContextMenuInfo;

    .line 67
    .local v0, info:Landroid/widget/AdapterView$AdapterContextMenuInfo;,"Landroid/widget/AdapterView$AdapterContextMenuInfo;"
    invoke-interface {p1}, Landroid/view/MenuItem;->getItemId()I

    move-result v2

    packed-switch v2, :pswitch_data_0

    .line 73
    invoke-super {p0, p1}, Landroid/app/ListActivity;->onContextItemSelected(Landroid/view/MenuItem;)Z

    move-result v1

    :pswitch_0
    return v1

    .line 67
    nop

    :pswitch_data_0
    .packed-switch 0x7f090008
        :pswitch_0
        :pswitch_0
    .end packed-switch
.end method

.method public onCreate(Landroid/os/Bundle;)V
    .locals 2
    .parameter "savedInstanceState"

    .prologue
    .line 42
    invoke-super {p0, p1}, Landroid/app/ListActivity;->onCreate(Landroid/os/Bundle;)V

    .line 43
    const v1, 0x7f03000c

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/TabCars;->setContentView(I)V

    .line 45
    iput-object p0, p0, Lcom/openvehicles/OVMS/TabCars;->mContext:Landroid/content/Context;

    .line 47
    const v1, 0x7f090039

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/TabCars;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageButton;

    .line 48
    .local v0, btn:Landroid/widget/ImageButton;
    new-instance v1, Lcom/openvehicles/OVMS/TabCars$2;

    invoke-direct {v1, p0}, Lcom/openvehicles/OVMS/TabCars$2;-><init>(Lcom/openvehicles/OVMS/TabCars;)V

    invoke-virtual {v0, v1}, Landroid/widget/ImageButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 54
    return-void
.end method

.method public onCreateContextMenu(Landroid/view/ContextMenu;Landroid/view/View;Landroid/view/ContextMenu$ContextMenuInfo;)V
    .locals 2
    .parameter "menu"
    .parameter "v"
    .parameter "menuInfo"

    .prologue
    .line 59
    invoke-super {p0, p1, p2, p3}, Landroid/app/ListActivity;->onCreateContextMenu(Landroid/view/ContextMenu;Landroid/view/View;Landroid/view/ContextMenu$ContextMenuInfo;)V

    .line 60
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TabCars;->getMenuInflater()Landroid/view/MenuInflater;

    move-result-object v0

    .line 61
    .local v0, inflater:Landroid/view/MenuInflater;
    const v1, 0x7f030001

    invoke-virtual {v0, v1, p1}, Landroid/view/MenuInflater;->inflate(ILandroid/view/Menu;)V

    .line 62
    return-void
.end method

.method protected onListItemClick(Landroid/widget/ListView;Landroid/view/View;IJ)V
    .locals 3
    .parameter "l"
    .parameter "v"
    .parameter "position"
    .parameter "id"

    .prologue
    .line 258
    const-string v0, "OVMS"

    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "Changing car to: "

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    iget-object v2, p0, Lcom/openvehicles/OVMS/TabCars;->carsList:[Lcom/openvehicles/OVMS/CarData;

    aget-object v2, v2, p3

    iget-object v2, v2, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 259
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabCars;->carsList:[Lcom/openvehicles/OVMS/CarData;

    aget-object v0, v0, p3

    invoke-direct {p0, v0}, Lcom/openvehicles/OVMS/TabCars;->carClicked(Lcom/openvehicles/OVMS/CarData;)V

    .line 260
    return-void
.end method
