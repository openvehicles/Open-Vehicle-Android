.class Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$1;
.super Landroid/os/Handler;
.source "Tab_SubTabCarSettings.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    .line 618
    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 14
    .parameter "msg"

    .prologue
    const/4 v13, 0x2

    const/4 v6, 0x1

    const/16 v12, 0x9

    const/4 v11, 0x3

    const/4 v7, 0x0

    .line 621
    iget-object v5, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->lastVehicleID:Ljava/lang/String;
    invoke-static {v5}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$0(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Ljava/lang/String;

    move-result-object v5

    iget-object v8, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v8}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$1(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v8

    iget-object v8, v8, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    invoke-virtual {v5, v8}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-nez v5, :cond_2

    .line 623
    iget-object v5, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    iget-object v8, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v8}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$1(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v8

    iget-object v8, v8, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    #setter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->lastVehicleID:Ljava/lang/String;
    invoke-static {v5, v8}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$2(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;Ljava/lang/String;)V

    .line 626
    iget-object v5, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v5}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$1(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v5

    iget-object v5, v5, Lcom/openvehicles/OVMS/CarData;->Data_Parameters:Ljava/util/LinkedHashMap;

    const/16 v8, 0x8

    invoke-static {v8}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v8

    invoke-virtual {v5, v8}, Ljava/util/LinkedHashMap;->containsKey(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_0

    .line 627
    iget-object v5, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v5}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$1(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v5

    iget-object v5, v5, Lcom/openvehicles/OVMS/CarData;->Data_Features:Ljava/util/LinkedHashMap;

    const/16 v8, 0xf

    invoke-static {v8}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v8

    invoke-virtual {v5, v8}, Ljava/util/LinkedHashMap;->containsKey(Ljava/lang/Object;)Z

    move-result v5

    if-nez v5, :cond_3

    .line 628
    :cond_0
    const-string v5, "SETTINGS"

    const-string v6, "No cached data found. Requesting data from module."

    invoke-static {v5, v6}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 629
    iget-object v5, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    #calls: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->requestSettings()V
    invoke-static {v5}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$3(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)V

    .line 710
    :cond_1
    return-void

    .line 632
    :cond_2
    iget-object v5, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->lastDataRefreshed:Ljava/util/Date;
    invoke-static {v5}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$4(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Ljava/util/Date;

    move-result-object v5

    iget-object v8, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v8}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$1(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v8

    iget-object v8, v8, Lcom/openvehicles/OVMS/CarData;->Data_Features_LastRefreshed:Ljava/util/Date;

    if-eq v5, v8, :cond_1

    .line 635
    :cond_3
    iget-object v5, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->toastDisplayed:Landroid/widget/Toast;
    invoke-static {v5}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$5(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Landroid/widget/Toast;

    move-result-object v5

    if-eqz v5, :cond_4

    .line 636
    iget-object v5, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->toastDisplayed:Landroid/widget/Toast;
    invoke-static {v5}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$5(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Landroid/widget/Toast;

    move-result-object v5

    invoke-virtual {v5}, Landroid/widget/Toast;->cancel()V

    .line 637
    iget-object v5, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    const/4 v8, 0x0

    #setter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->toastDisplayed:Landroid/widget/Toast;
    invoke-static {v5, v8}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$6(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;Landroid/widget/Toast;)V

    .line 640
    :cond_4
    iget-object v5, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    iget-object v8, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v8}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$1(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v8

    iget-object v8, v8, Lcom/openvehicles/OVMS/CarData;->Data_Features_LastRefreshed:Ljava/util/Date;

    #setter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->lastDataRefreshed:Ljava/util/Date;
    invoke-static {v5, v8}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$7(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;Ljava/util/Date;)V

    .line 641
    iget-object v5, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    const v8, 0x7f090010

    invoke-virtual {v5, v8}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->findViewById(I)Landroid/view/View;

    move-result-object v4

    check-cast v4, Landroid/widget/TextView;

    .line 642
    .local v4, tv:Landroid/widget/TextView;
    iget-object v5, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v5}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$1(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v5

    iget-object v5, v5, Lcom/openvehicles/OVMS/CarData;->Data_Parameters_LastRefreshed:Ljava/util/Date;

    if-eqz v5, :cond_6

    .line 643
    new-instance v5, Ljava/text/SimpleDateFormat;

    const-string v8, "MMM d, K:mm:ss a"

    invoke-direct {v5, v8}, Ljava/text/SimpleDateFormat;-><init>(Ljava/lang/String;)V

    .line 644
    iget-object v8, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v8}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$1(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v8

    iget-object v8, v8, Lcom/openvehicles/OVMS/CarData;->Data_Parameters_LastRefreshed:Ljava/util/Date;

    invoke-virtual {v5, v8}, Ljava/text/SimpleDateFormat;->format(Ljava/util/Date;)Ljava/lang/String;

    move-result-object v5

    .line 643
    invoke-virtual {v4, v5}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 649
    :goto_0
    iget-object v5, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;
    invoke-static {v5}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$8(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Ljava/util/LinkedHashMap;

    move-result-object v5

    invoke-virtual {v5}, Ljava/util/LinkedHashMap;->keySet()Ljava/util/Set;

    move-result-object v5

    invoke-interface {v5}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v8

    :cond_5
    :goto_1
    invoke-interface {v8}, Ljava/util/Iterator;->hasNext()Z

    move-result v5

    if-eqz v5, :cond_1

    invoke-interface {v8}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/String;

    .line 650
    .local v1, key:Ljava/lang/String;
    iget-object v5, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    invoke-virtual {v5}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->getPreferenceManager()Landroid/preference/PreferenceManager;

    move-result-object v5

    invoke-virtual {v5, v1}, Landroid/preference/PreferenceManager;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v2

    .line 651
    .local v2, p:Landroid/preference/Preference;
    const/4 v0, 0x0

    .line 652
    .local v0, dataSource:Ljava/util/LinkedHashMap;,"Ljava/util/LinkedHashMap<Ljava/lang/Integer;Ljava/lang/String;>;"
    const-string v5, "PARAM"

    invoke-virtual {v1, v5}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v5

    if-eqz v5, :cond_7

    .line 653
    iget-object v5, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v5}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$1(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v5

    iget-object v0, v5, Lcom/openvehicles/OVMS/CarData;->Data_Parameters:Ljava/util/LinkedHashMap;

    .line 662
    :goto_2
    iget-object v5, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;
    invoke-static {v5}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$8(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Ljava/util/LinkedHashMap;

    move-result-object v5

    invoke-virtual {v5, v1}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, [Ljava/lang/Object;

    aget-object v5, v5, v6

    const-string v9, "String"

    invoke-virtual {v5, v9}, Ljava/lang/Object;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_a

    .line 663
    iget-object v5, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;
    invoke-static {v5}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$8(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Ljava/util/LinkedHashMap;

    move-result-object v5

    invoke-virtual {v5, v1}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, [Ljava/lang/Object;

    aget-object v5, v5, v7

    invoke-virtual {v0, v5}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    if-nez v5, :cond_9

    .line 664
    check-cast v2, Landroid/preference/EditTextPreference;

    .end local v2           #p:Landroid/preference/Preference;
    const-string v5, ""

    invoke-virtual {v2, v5}, Landroid/preference/EditTextPreference;->setText(Ljava/lang/String;)V

    goto :goto_1

    .line 646
    .end local v0           #dataSource:Ljava/util/LinkedHashMap;,"Ljava/util/LinkedHashMap<Ljava/lang/Integer;Ljava/lang/String;>;"
    .end local v1           #key:Ljava/lang/String;
    :cond_6
    const-string v5, "Never"

    invoke-virtual {v4, v5}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto :goto_0

    .line 654
    .restart local v0       #dataSource:Ljava/util/LinkedHashMap;,"Ljava/util/LinkedHashMap<Ljava/lang/Integer;Ljava/lang/String;>;"
    .restart local v1       #key:Ljava/lang/String;
    .restart local v2       #p:Landroid/preference/Preference;
    :cond_7
    const-string v5, "FEATURE"

    invoke-virtual {v1, v5}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v5

    if-eqz v5, :cond_8

    .line 655
    iget-object v5, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v5}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$1(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v5

    iget-object v0, v5, Lcom/openvehicles/OVMS/CarData;->Data_Features:Ljava/util/LinkedHashMap;

    goto :goto_2

    .line 657
    :cond_8
    const-string v5, "ERROR"

    new-instance v9, Ljava/lang/StringBuilder;

    const-string v10, "Unrecognized settings key: "

    invoke-direct {v9, v10}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v9, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v5, v9}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_1

    .line 666
    :cond_9
    check-cast v2, Landroid/preference/EditTextPreference;

    .line 667
    .end local v2           #p:Landroid/preference/Preference;
    iget-object v5, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;
    invoke-static {v5}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$8(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Ljava/util/LinkedHashMap;

    move-result-object v5

    invoke-virtual {v5, v1}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, [Ljava/lang/Object;

    aget-object v5, v5, v7

    invoke-virtual {v0, v5}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/lang/String;

    .line 666
    invoke-virtual {v2, v5}, Landroid/preference/EditTextPreference;->setText(Ljava/lang/String;)V

    goto/16 :goto_1

    .line 668
    .restart local v2       #p:Landroid/preference/Preference;
    :cond_a
    iget-object v5, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;
    invoke-static {v5}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$8(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Ljava/util/LinkedHashMap;

    move-result-object v5

    invoke-virtual {v5, v1}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, [Ljava/lang/Object;

    aget-object v5, v5, v6

    const-string v9, "bool"

    invoke-virtual {v5, v9}, Ljava/lang/Object;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_c

    .line 669
    iget-object v5, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;
    invoke-static {v5}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$8(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Ljava/util/LinkedHashMap;

    move-result-object v5

    .line 670
    invoke-virtual {v5, v1}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, [Ljava/lang/Object;

    aget-object v5, v5, v7

    .line 669
    invoke-virtual {v0, v5}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    .line 671
    .local v3, rawValue:Ljava/lang/Object;
    check-cast v2, Landroid/preference/CheckBoxPreference;

    .end local v2           #p:Landroid/preference/Preference;
    if-eqz v3, :cond_b

    .line 672
    invoke-virtual {v3}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/String;->length()I

    move-result v5

    if-lez v5, :cond_b

    .line 673
    const-string v5, "0"

    invoke-virtual {v3, v5}, Ljava/lang/Object;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-nez v5, :cond_b

    move v5, v6

    .line 671
    :goto_3
    invoke-virtual {v2, v5}, Landroid/preference/CheckBoxPreference;->setChecked(Z)V

    goto/16 :goto_1

    :cond_b
    move v5, v7

    .line 673
    goto :goto_3

    .line 676
    .end local v3           #rawValue:Ljava/lang/Object;
    .restart local v2       #p:Landroid/preference/Preference;
    :cond_c
    iget-object v5, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;
    invoke-static {v5}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$8(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Ljava/util/LinkedHashMap;

    move-result-object v5

    invoke-virtual {v5, v1}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, [Ljava/lang/Object;

    aget-object v5, v5, v7

    .line 677
    invoke-virtual {v5}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v5

    .line 676
    invoke-static {v5}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v5

    if-ne v5, v11, :cond_10

    .line 678
    invoke-static {v11}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    invoke-virtual {v0, v5}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    if-eqz v5, :cond_d

    .line 680
    invoke-static {v11}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    .line 679
    invoke-virtual {v0, v5}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/lang/String;

    .line 681
    const-string v9, "IP"

    invoke-virtual {v5, v9}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v5

    if-eqz v5, :cond_e

    .line 682
    invoke-static {v11}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    .line 681
    invoke-virtual {v0, v5}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/lang/String;

    .line 683
    const-string v9, "SMS"

    invoke-virtual {v5, v9}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v5

    if-eqz v5, :cond_e

    .line 684
    :cond_d
    check-cast v2, Landroid/preference/ListPreference;

    .end local v2           #p:Landroid/preference/Preference;
    const-string v5, "SMS,IP"

    invoke-virtual {v2, v5}, Landroid/preference/ListPreference;->setValue(Ljava/lang/String;)V

    goto/16 :goto_1

    .line 685
    .restart local v2       #p:Landroid/preference/Preference;
    :cond_e
    invoke-static {v11}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    invoke-virtual {v0, v5}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/lang/String;

    .line 686
    const-string v9, "SMS"

    invoke-virtual {v5, v9}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v5

    if-eqz v5, :cond_f

    .line 687
    check-cast v2, Landroid/preference/ListPreference;

    .end local v2           #p:Landroid/preference/Preference;
    const-string v5, "SMS"

    invoke-virtual {v2, v5}, Landroid/preference/ListPreference;->setValue(Ljava/lang/String;)V

    goto/16 :goto_1

    .line 689
    .restart local v2       #p:Landroid/preference/Preference;
    :cond_f
    check-cast v2, Landroid/preference/ListPreference;

    .end local v2           #p:Landroid/preference/Preference;
    const-string v5, "IP"

    invoke-virtual {v2, v5}, Landroid/preference/ListPreference;->setValue(Ljava/lang/String;)V

    goto/16 :goto_1

    .line 690
    .restart local v2       #p:Landroid/preference/Preference;
    :cond_10
    iget-object v5, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;
    invoke-static {v5}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$8(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Ljava/util/LinkedHashMap;

    move-result-object v5

    .line 691
    invoke-virtual {v5, v1}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, [Ljava/lang/Object;

    .line 690
    aget-object v5, v5, v7

    .line 691
    invoke-virtual {v5}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v5

    .line 690
    invoke-static {v5}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v5

    if-ne v5, v13, :cond_13

    .line 692
    invoke-static {v13}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    invoke-virtual {v0, v5}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    if-eqz v5, :cond_11

    .line 693
    invoke-static {v13}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    invoke-virtual {v0, v5}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/lang/String;

    .line 694
    const-string v9, "K"

    invoke-virtual {v5, v9}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_12

    .line 695
    :cond_11
    check-cast v2, Landroid/preference/ListPreference;

    .end local v2           #p:Landroid/preference/Preference;
    const-string v5, "K"

    invoke-virtual {v2, v5}, Landroid/preference/ListPreference;->setValue(Ljava/lang/String;)V

    goto/16 :goto_1

    .line 697
    .restart local v2       #p:Landroid/preference/Preference;
    :cond_12
    check-cast v2, Landroid/preference/ListPreference;

    .end local v2           #p:Landroid/preference/Preference;
    const-string v5, "M"

    invoke-virtual {v2, v5}, Landroid/preference/ListPreference;->setValue(Ljava/lang/String;)V

    goto/16 :goto_1

    .line 698
    .restart local v2       #p:Landroid/preference/Preference;
    :cond_13
    iget-object v5, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$1;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    #getter for: Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;
    invoke-static {v5}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->access$8(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Ljava/util/LinkedHashMap;

    move-result-object v5

    .line 699
    invoke-virtual {v5, v1}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, [Ljava/lang/Object;

    .line 698
    aget-object v5, v5, v7

    .line 699
    invoke-virtual {v5}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v5

    .line 698
    invoke-static {v5}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v5

    if-ne v5, v12, :cond_5

    .line 700
    invoke-static {v12}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    invoke-virtual {v0, v5}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    if-eqz v5, :cond_14

    .line 702
    invoke-static {v12}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    .line 701
    invoke-virtual {v0, v5}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/lang/String;

    .line 702
    invoke-virtual {v5}, Ljava/lang/String;->length()I

    move-result v5

    if-lez v5, :cond_14

    .line 703
    check-cast v2, Landroid/preference/ListPreference;

    .line 704
    .end local v2           #p:Landroid/preference/Preference;
    invoke-static {v12}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    invoke-virtual {v0, v5}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/lang/String;

    .line 703
    invoke-virtual {v2, v5}, Landroid/preference/ListPreference;->setValue(Ljava/lang/String;)V

    goto/16 :goto_1

    .line 706
    .restart local v2       #p:Landroid/preference/Preference;
    :cond_14
    check-cast v2, Landroid/preference/ListPreference;

    .end local v2           #p:Landroid/preference/Preference;
    const-string v5, "30"

    invoke-virtual {v2, v5}, Landroid/preference/ListPreference;->setValue(Ljava/lang/String;)V

    goto/16 :goto_1
.end method
