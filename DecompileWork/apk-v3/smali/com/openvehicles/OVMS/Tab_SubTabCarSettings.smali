.class public Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;
.super Landroid/preference/PreferenceActivity;
.source "Tab_SubTabCarSettings.java"


# instance fields
.field private cachedUIPreferences:Landroid/content/SharedPreferences;

.field private data:Lcom/openvehicles/OVMS/CarData;

.field private delayedRequest:Landroid/os/Handler;

.field private dialog:Landroid/app/AlertDialog;

.field private downloadProgress:Landroid/app/ProgressDialog;

.field private downloadTask:Lcom/openvehicles/OVMS/ServerCommands$CarLayoutDownloader;

.field private handler:Landroid/os/Handler;

.field private isLoggedIn:Z

.field private lastDataRefreshed:Ljava/util/Date;

.field private lastVehicleID:Ljava/lang/String;

.field private mContext:Landroid/content/Context;

.field private mOVMSActivity:Lcom/openvehicles/OVMS/OVMSActivity;

.field private preferenceStorageMapping:Ljava/util/LinkedHashMap;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/LinkedHashMap",
            "<",
            "Ljava/lang/String;",
            "[",
            "Ljava/lang/Object;",
            ">;"
        }
    .end annotation
.end field

.field private toastDisplayed:Landroid/widget/Toast;


# direct methods
.method public constructor <init>()V
    .locals 2

    .prologue
    const/4 v1, 0x0

    .line 37
    invoke-direct {p0}, Landroid/preference/PreferenceActivity;-><init>()V

    .line 588
    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->isLoggedIn:Z

    .line 589
    const-string v0, ""

    iput-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->lastVehicleID:Ljava/lang/String;

    .line 590
    iput-object v1, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->lastDataRefreshed:Ljava/util/Date;

    .line 592
    iput-object v1, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->toastDisplayed:Landroid/widget/Toast;

    .line 593
    new-instance v0, Landroid/os/Handler;

    invoke-direct {v0}, Landroid/os/Handler;-><init>()V

    iput-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->delayedRequest:Landroid/os/Handler;

    .line 594
    new-instance v0, Ljava/util/LinkedHashMap;

    invoke-direct {v0}, Ljava/util/LinkedHashMap;-><init>()V

    iput-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    .line 618
    new-instance v0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$1;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$1;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->handler:Landroid/os/Handler;

    .line 37
    return-void
.end method

.method static synthetic access$0(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Ljava/lang/String;
    .locals 1
    .parameter

    .prologue
    .line 589
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->lastVehicleID:Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$1(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Lcom/openvehicles/OVMS/CarData;
    .locals 1
    .parameter

    .prologue
    .line 587
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->data:Lcom/openvehicles/OVMS/CarData;

    return-object v0
.end method

.method static synthetic access$10(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Landroid/content/Context;
    .locals 1
    .parameter

    .prologue
    .line 585
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->mContext:Landroid/content/Context;

    return-object v0
.end method

.method static synthetic access$11(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Lcom/openvehicles/OVMS/OVMSActivity;
    .locals 1
    .parameter

    .prologue
    .line 586
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->mOVMSActivity:Lcom/openvehicles/OVMS/OVMSActivity;

    return-object v0
.end method

.method static synthetic access$12(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;Ljava/lang/String;I)V
    .locals 0
    .parameter
    .parameter
    .parameter

    .prologue
    .line 713
    invoke-direct {p0, p1, p2}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->makeToast(Ljava/lang/String;I)V

    return-void
.end method

.method static synthetic access$13(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)V
    .locals 0
    .parameter

    .prologue
    .line 954
    invoke-direct {p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->downloadLayout()V

    return-void
.end method

.method static synthetic access$14(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;Landroid/app/AlertDialog;)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 596
    iput-object p1, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->dialog:Landroid/app/AlertDialog;

    return-void
.end method

.method static synthetic access$15(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Landroid/app/AlertDialog;
    .locals 1
    .parameter

    .prologue
    .line 596
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->dialog:Landroid/app/AlertDialog;

    return-object v0
.end method

.method static synthetic access$2(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;Ljava/lang/String;)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 589
    iput-object p1, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->lastVehicleID:Ljava/lang/String;

    return-void
.end method

.method static synthetic access$3(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)V
    .locals 0
    .parameter

    .prologue
    .line 906
    invoke-direct {p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->requestSettings()V

    return-void
.end method

.method static synthetic access$4(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Ljava/util/Date;
    .locals 1
    .parameter

    .prologue
    .line 590
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->lastDataRefreshed:Ljava/util/Date;

    return-object v0
.end method

.method static synthetic access$5(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Landroid/widget/Toast;
    .locals 1
    .parameter

    .prologue
    .line 592
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->toastDisplayed:Landroid/widget/Toast;

    return-object v0
.end method

.method static synthetic access$6(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;Landroid/widget/Toast;)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 592
    iput-object p1, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->toastDisplayed:Landroid/widget/Toast;

    return-void
.end method

.method static synthetic access$7(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;Ljava/util/Date;)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 590
    iput-object p1, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->lastDataRefreshed:Ljava/util/Date;

    return-void
.end method

.method static synthetic access$8(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)Ljava/util/LinkedHashMap;
    .locals 1
    .parameter

    .prologue
    .line 594
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    return-object v0
.end method

.method static synthetic access$9(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)V
    .locals 0
    .parameter

    .prologue
    .line 731
    invoke-direct {p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->commitSettings()V

    return-void
.end method

.method private commitSettings()V
    .locals 19

    .prologue
    .line 733
    new-instance v5, Ljava/util/LinkedHashMap;

    invoke-direct {v5}, Ljava/util/LinkedHashMap;-><init>()V

    .line 736
    .local v5, changedSettings:Ljava/util/LinkedHashMap;,"Ljava/util/LinkedHashMap<Ljava/lang/Integer;[Ljava/lang/String;>;"
    move-object/from16 v0, p0

    iget-object v11, v0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    invoke-virtual {v11}, Ljava/util/LinkedHashMap;->keySet()Ljava/util/Set;

    move-result-object v11

    invoke-interface {v11}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v13

    :cond_0
    :goto_0
    invoke-interface {v13}, Ljava/util/Iterator;->hasNext()Z

    move-result v11

    if-nez v11, :cond_2

    .line 839
    invoke-virtual {v5}, Ljava/util/LinkedHashMap;->size()I

    move-result v11

    if-nez v11, :cond_14

    .line 840
    const-string v11, "Nothing has changed"

    const/4 v12, 0x0

    move-object/from16 v0, p0

    invoke-direct {v0, v11, v12}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->makeToast(Ljava/lang/String;I)V

    .line 841
    move-object/from16 v0, p0

    iget-object v11, v0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->toastDisplayed:Landroid/widget/Toast;

    invoke-virtual {v11}, Landroid/widget/Toast;->show()V

    .line 904
    :cond_1
    :goto_1
    return-void

    .line 736
    :cond_2
    invoke-interface {v13}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v7

    check-cast v7, Ljava/lang/String;

    .line 737
    .local v7, key:Ljava/lang/String;
    invoke-virtual/range {p0 .. p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->getPreferenceManager()Landroid/preference/PreferenceManager;

    move-result-object v11

    invoke-virtual {v11, v7}, Landroid/preference/PreferenceManager;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v9

    .line 738
    .local v9, p:Landroid/preference/Preference;
    const/4 v6, 0x0

    .line 739
    .local v6, dataSource:Ljava/util/LinkedHashMap;,"Ljava/util/LinkedHashMap<Ljava/lang/Integer;Ljava/lang/String;>;"
    const-string v11, "PARAM"

    invoke-virtual {v7, v11}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v11

    if-eqz v11, :cond_5

    .line 740
    move-object/from16 v0, p0

    iget-object v11, v0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v6, v11, Lcom/openvehicles/OVMS/CarData;->Data_Parameters:Ljava/util/LinkedHashMap;

    .line 749
    :goto_2
    move-object/from16 v0, p0

    iget-object v11, v0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    invoke-virtual {v11, v7}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    check-cast v11, [Ljava/lang/Object;

    const/4 v12, 0x0

    aget-object v11, v11, v12

    invoke-virtual {v6, v11}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    if-nez v11, :cond_3

    .line 750
    move-object/from16 v0, p0

    iget-object v11, v0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    .line 751
    invoke-virtual {v11, v7}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    check-cast v11, [Ljava/lang/Object;

    const/4 v12, 0x0

    aget-object v11, v11, v12

    invoke-virtual {v11}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v11

    .line 750
    invoke-static {v11}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v11

    invoke-static {v11}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v11

    .line 751
    const-string v12, ""

    .line 750
    invoke-virtual {v6, v11, v12}, Ljava/util/LinkedHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 754
    :cond_3
    move-object/from16 v0, p0

    iget-object v11, v0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    invoke-virtual {v11, v7}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    check-cast v11, [Ljava/lang/Object;

    const/4 v12, 0x1

    aget-object v11, v11, v12

    const-string v12, "String"

    invoke-virtual {v11, v12}, Ljava/lang/Object;->equals(Ljava/lang/Object;)Z

    move-result v11

    if-eqz v11, :cond_b

    move-object v11, v9

    .line 755
    check-cast v11, Landroid/preference/EditTextPreference;

    invoke-virtual {v11}, Landroid/preference/EditTextPreference;->getText()Ljava/lang/String;

    move-result-object v12

    .line 756
    move-object/from16 v0, p0

    iget-object v11, v0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    invoke-virtual {v11, v7}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    check-cast v11, [Ljava/lang/Object;

    const/4 v14, 0x0

    aget-object v11, v11, v14

    invoke-virtual {v6, v11}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    .line 755
    invoke-virtual {v12, v11}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v11

    if-nez v11, :cond_4

    .line 758
    move-object/from16 v0, p0

    iget-object v11, v0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    invoke-virtual {v11, v7}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    check-cast v11, [Ljava/lang/Object;

    const/4 v12, 0x0

    aget-object v11, v11, v12

    check-cast v11, Ljava/lang/Integer;

    .line 759
    const/4 v12, 0x3

    new-array v14, v12, [Ljava/lang/String;

    const/4 v12, 0x0

    .line 760
    aput-object v7, v14, v12

    const/4 v15, 0x1

    .line 763
    move-object/from16 v0, p0

    iget-object v12, v0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    .line 764
    invoke-virtual {v12, v7}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v12

    check-cast v12, [Ljava/lang/Object;

    const/16 v16, 0x0

    .line 763
    aget-object v12, v12, v16

    invoke-virtual {v6, v12}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v12

    check-cast v12, Ljava/lang/String;

    .line 765
    invoke-virtual {v12}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v12

    invoke-virtual {v12}, Ljava/lang/String;->length()I

    move-result v12

    .line 761
    if-nez v12, :cond_7

    .line 765
    const-string v12, "<empty>"

    .line 767
    :goto_3
    aput-object v12, v14, v15

    const/4 v15, 0x2

    move-object v12, v9

    .line 769
    check-cast v12, Landroid/preference/EditTextPreference;

    invoke-virtual {v12}, Landroid/preference/EditTextPreference;->getText()Ljava/lang/String;

    move-result-object v12

    .line 771
    invoke-virtual {v12}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v12

    invoke-virtual {v12}, Ljava/lang/String;->length()I

    move-result v12

    .line 769
    if-nez v12, :cond_8

    .line 771
    const-string v12, "<empty>"

    .line 773
    :goto_4
    aput-object v12, v14, v15

    .line 758
    invoke-virtual {v5, v11, v14}, Ljava/util/LinkedHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 774
    :cond_4
    const-string v12, "SETTINGS"

    .line 776
    const-string v14, "%s : %s -> %s"

    const/4 v11, 0x3

    new-array v15, v11, [Ljava/lang/Object;

    const/4 v11, 0x0

    .line 777
    aput-object v7, v15, v11

    const/16 v16, 0x1

    .line 779
    move-object/from16 v0, p0

    iget-object v11, v0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    invoke-virtual {v11, v7}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    check-cast v11, [Ljava/lang/Object;

    const/16 v17, 0x0

    aget-object v11, v11, v17

    invoke-virtual {v6, v11}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    check-cast v11, Ljava/lang/String;

    .line 780
    invoke-virtual {v11}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v11}, Ljava/lang/String;->length()I

    move-result v11

    .line 778
    if-nez v11, :cond_9

    .line 780
    const-string v11, "<empty>"

    .line 782
    :goto_5
    aput-object v11, v15, v16

    const/16 v16, 0x2

    move-object v11, v9

    .line 784
    check-cast v11, Landroid/preference/EditTextPreference;

    invoke-virtual {v11}, Landroid/preference/EditTextPreference;->getText()Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v11}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v11

    .line 785
    invoke-virtual {v11}, Ljava/lang/String;->length()I

    move-result v11

    .line 784
    if-nez v11, :cond_a

    .line 785
    const-string v11, "<empty>"

    .line 787
    .end local v9           #p:Landroid/preference/Preference;
    :goto_6
    aput-object v11, v15, v16

    .line 775
    invoke-static {v14, v15}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v11

    .line 774
    invoke-static {v12, v11}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_0

    .line 741
    .restart local v9       #p:Landroid/preference/Preference;
    :cond_5
    const-string v11, "FEATURE"

    invoke-virtual {v7, v11}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v11

    if-eqz v11, :cond_6

    .line 742
    move-object/from16 v0, p0

    iget-object v11, v0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v6, v11, Lcom/openvehicles/OVMS/CarData;->Data_Features:Ljava/util/LinkedHashMap;

    goto/16 :goto_2

    .line 744
    :cond_6
    const-string v11, "ERROR"

    new-instance v12, Ljava/lang/StringBuilder;

    const-string v14, "Unrecognized settings key: "

    invoke-direct {v12, v14}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v12, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v12

    invoke-virtual {v12}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v12

    invoke-static {v11, v12}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_0

    .line 767
    :cond_7
    move-object/from16 v0, p0

    iget-object v12, v0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    .line 768
    invoke-virtual {v12, v7}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v12

    check-cast v12, [Ljava/lang/Object;

    const/16 v16, 0x0

    .line 767
    aget-object v12, v12, v16

    invoke-virtual {v6, v12}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v12

    check-cast v12, Ljava/lang/String;

    goto/16 :goto_3

    :cond_8
    move-object v12, v9

    .line 772
    check-cast v12, Landroid/preference/EditTextPreference;

    .line 773
    invoke-virtual {v12}, Landroid/preference/EditTextPreference;->getText()Ljava/lang/String;

    move-result-object v12

    invoke-virtual {v12}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v12

    goto/16 :goto_4

    .line 782
    :cond_9
    move-object/from16 v0, p0

    iget-object v11, v0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    .line 783
    invoke-virtual {v11, v7}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    check-cast v11, [Ljava/lang/Object;

    const/16 v17, 0x0

    .line 782
    aget-object v11, v11, v17

    invoke-virtual {v6, v11}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    check-cast v11, Ljava/lang/String;

    goto :goto_5

    .line 786
    :cond_a
    check-cast v9, Landroid/preference/EditTextPreference;

    .end local v9           #p:Landroid/preference/Preference;
    invoke-virtual {v9}, Landroid/preference/EditTextPreference;->getText()Ljava/lang/String;

    move-result-object v11

    .line 787
    invoke-virtual {v11}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v11

    goto :goto_6

    .line 788
    .restart local v9       #p:Landroid/preference/Preference;
    :cond_b
    move-object/from16 v0, p0

    iget-object v11, v0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    invoke-virtual {v11, v7}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    check-cast v11, [Ljava/lang/Object;

    const/4 v12, 0x1

    aget-object v11, v11, v12

    const-string v12, "bool"

    invoke-virtual {v11, v12}, Ljava/lang/Object;->equals(Ljava/lang/Object;)Z

    move-result v11

    if-eqz v11, :cond_12

    .line 789
    move-object/from16 v0, p0

    iget-object v11, v0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    .line 790
    invoke-virtual {v11, v7}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    check-cast v11, [Ljava/lang/Object;

    const/4 v12, 0x0

    aget-object v11, v11, v12

    .line 789
    invoke-virtual {v6, v11}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v10

    .line 791
    .local v10, rawValue:Ljava/lang/Object;
    if-eqz v10, :cond_d

    .line 792
    invoke-virtual {v10}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v11}, Ljava/lang/String;->length()I

    move-result v11

    if-lez v11, :cond_d

    .line 793
    const-string v11, "0"

    invoke-virtual {v10, v11}, Ljava/lang/Object;->equals(Ljava/lang/Object;)Z

    move-result v11

    if-nez v11, :cond_d

    const/4 v8, 0x1

    .local v8, originalValue:Z
    :goto_7
    move-object v11, v9

    .line 794
    check-cast v11, Landroid/preference/CheckBoxPreference;

    invoke-virtual {v11}, Landroid/preference/CheckBoxPreference;->isChecked()Z

    move-result v11

    if-eq v11, v8, :cond_c

    .line 796
    move-object/from16 v0, p0

    iget-object v11, v0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    invoke-virtual {v11, v7}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    check-cast v11, [Ljava/lang/Object;

    const/4 v12, 0x0

    aget-object v11, v11, v12

    .line 797
    invoke-virtual {v11}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v11

    .line 796
    invoke-static {v11}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v11

    .line 797
    const/16 v12, 0xa

    .line 796
    if-ne v11, v12, :cond_f

    .line 799
    move-object/from16 v0, p0

    iget-object v11, v0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    invoke-virtual {v11, v7}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    check-cast v11, [Ljava/lang/Object;

    const/4 v12, 0x0

    aget-object v11, v11, v12

    check-cast v11, Ljava/lang/Integer;

    .line 800
    const/4 v12, 0x3

    new-array v14, v12, [Ljava/lang/String;

    const/4 v12, 0x0

    .line 801
    aput-object v7, v14, v12

    const/4 v15, 0x1

    .line 803
    move-object/from16 v0, p0

    iget-object v12, v0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    .line 804
    invoke-virtual {v12, v7}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v12

    check-cast v12, [Ljava/lang/Object;

    const/16 v16, 0x0

    .line 803
    aget-object v12, v12, v16

    invoke-virtual {v6, v12}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v12

    check-cast v12, Ljava/lang/String;

    aput-object v12, v14, v15

    const/4 v15, 0x2

    move-object v12, v9

    .line 805
    check-cast v12, Landroid/preference/CheckBoxPreference;

    .line 806
    invoke-virtual {v12}, Landroid/preference/CheckBoxPreference;->isChecked()Z

    move-result v12

    if-eqz v12, :cond_e

    const-string v12, "P"

    :goto_8
    aput-object v12, v14, v15

    .line 798
    invoke-virtual {v5, v11, v14}, Ljava/util/LinkedHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 819
    :cond_c
    :goto_9
    const-string v12, "SETTINGS"

    const-string v14, "%s : %s -> %s"

    const/4 v11, 0x3

    new-array v15, v11, [Ljava/lang/Object;

    const/4 v11, 0x0

    aput-object v7, v15, v11

    const/16 v16, 0x1

    .line 820
    move-object/from16 v0, p0

    iget-object v11, v0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    invoke-virtual {v11, v7}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    check-cast v11, [Ljava/lang/Object;

    const/16 v17, 0x0

    aget-object v11, v11, v17

    invoke-virtual {v6, v11}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    aput-object v11, v15, v16

    const/16 v16, 0x2

    .line 821
    check-cast v9, Landroid/preference/CheckBoxPreference;

    .end local v9           #p:Landroid/preference/Preference;
    invoke-virtual {v9}, Landroid/preference/CheckBoxPreference;->isChecked()Z

    move-result v11

    if-eqz v11, :cond_11

    const-string v11, "1"

    :goto_a
    aput-object v11, v15, v16

    .line 819
    invoke-static {v14, v15}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v11

    invoke-static {v12, v11}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_0

    .line 793
    .end local v8           #originalValue:Z
    .restart local v9       #p:Landroid/preference/Preference;
    :cond_d
    const/4 v8, 0x0

    goto/16 :goto_7

    .line 806
    .restart local v8       #originalValue:Z
    :cond_e
    const-string v12, ""

    goto :goto_8

    .line 809
    :cond_f
    move-object/from16 v0, p0

    iget-object v11, v0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    invoke-virtual {v11, v7}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    check-cast v11, [Ljava/lang/Object;

    const/4 v12, 0x0

    aget-object v11, v11, v12

    check-cast v11, Ljava/lang/Integer;

    .line 810
    const/4 v12, 0x3

    new-array v14, v12, [Ljava/lang/String;

    const/4 v12, 0x0

    .line 811
    aput-object v7, v14, v12

    const/4 v15, 0x1

    .line 813
    move-object/from16 v0, p0

    iget-object v12, v0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    .line 814
    invoke-virtual {v12, v7}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v12

    check-cast v12, [Ljava/lang/Object;

    const/16 v16, 0x0

    .line 813
    aget-object v12, v12, v16

    invoke-virtual {v6, v12}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v12

    check-cast v12, Ljava/lang/String;

    aput-object v12, v14, v15

    const/4 v15, 0x2

    move-object v12, v9

    .line 815
    check-cast v12, Landroid/preference/CheckBoxPreference;

    .line 817
    invoke-virtual {v12}, Landroid/preference/CheckBoxPreference;->isChecked()Z

    move-result v12

    if-eqz v12, :cond_10

    const-string v12, "1"

    :goto_b
    aput-object v12, v14, v15

    .line 808
    invoke-virtual {v5, v11, v14}, Ljava/util/LinkedHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    goto :goto_9

    .line 817
    :cond_10
    const-string v12, "0"

    goto :goto_b

    .line 821
    .end local v9           #p:Landroid/preference/Preference;
    :cond_11
    const-string v11, "0"

    goto :goto_a

    .line 822
    .end local v8           #originalValue:Z
    .end local v10           #rawValue:Ljava/lang/Object;
    .restart local v9       #p:Landroid/preference/Preference;
    :cond_12
    move-object/from16 v0, p0

    iget-object v11, v0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    invoke-virtual {v11, v7}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    check-cast v11, [Ljava/lang/Object;

    const/4 v12, 0x1

    aget-object v11, v11, v12

    const-string v12, "List"

    invoke-virtual {v11, v12}, Ljava/lang/Object;->equals(Ljava/lang/Object;)Z

    move-result v11

    if-eqz v11, :cond_0

    move-object v11, v9

    .line 823
    check-cast v11, Landroid/preference/ListPreference;

    invoke-virtual {v11}, Landroid/preference/ListPreference;->getValue()Ljava/lang/String;

    move-result-object v12

    .line 824
    move-object/from16 v0, p0

    iget-object v11, v0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    invoke-virtual {v11, v7}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    check-cast v11, [Ljava/lang/Object;

    const/4 v14, 0x0

    aget-object v11, v11, v14

    invoke-virtual {v6, v11}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    .line 823
    invoke-virtual {v12, v11}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v11

    if-nez v11, :cond_13

    .line 826
    move-object/from16 v0, p0

    iget-object v11, v0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    invoke-virtual {v11, v7}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    check-cast v11, [Ljava/lang/Object;

    const/4 v12, 0x0

    aget-object v11, v11, v12

    check-cast v11, Ljava/lang/Integer;

    .line 827
    const/4 v12, 0x3

    new-array v14, v12, [Ljava/lang/String;

    const/4 v12, 0x0

    .line 828
    aput-object v7, v14, v12

    const/4 v15, 0x1

    .line 829
    move-object/from16 v0, p0

    iget-object v12, v0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    .line 830
    invoke-virtual {v12, v7}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v12

    check-cast v12, [Ljava/lang/Object;

    const/16 v16, 0x0

    aget-object v12, v12, v16

    .line 829
    invoke-virtual {v6, v12}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v12

    check-cast v12, Ljava/lang/String;

    aput-object v12, v14, v15

    const/4 v15, 0x2

    move-object v12, v9

    .line 831
    check-cast v12, Landroid/preference/ListPreference;

    invoke-virtual {v12}, Landroid/preference/ListPreference;->getValue()Ljava/lang/String;

    move-result-object v12

    aput-object v12, v14, v15

    .line 825
    invoke-virtual {v5, v11, v14}, Ljava/util/LinkedHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 833
    :cond_13
    const-string v12, "SETTINGS"

    const-string v14, "%s : %s -> %s"

    const/4 v11, 0x3

    new-array v15, v11, [Ljava/lang/Object;

    const/4 v11, 0x0

    aput-object v7, v15, v11

    const/16 v16, 0x1

    .line 834
    move-object/from16 v0, p0

    iget-object v11, v0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    invoke-virtual {v11, v7}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    check-cast v11, [Ljava/lang/Object;

    const/16 v17, 0x0

    aget-object v11, v11, v17

    invoke-virtual {v6, v11}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    aput-object v11, v15, v16

    const/4 v11, 0x2

    .line 835
    check-cast v9, Landroid/preference/ListPreference;

    .end local v9           #p:Landroid/preference/Preference;
    invoke-virtual {v9}, Landroid/preference/ListPreference;->getValue()Ljava/lang/String;

    move-result-object v16

    aput-object v16, v15, v11

    .line 833
    invoke-static {v14, v15}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v11

    invoke-static {v12, v11}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_0

    .line 846
    .end local v6           #dataSource:Ljava/util/LinkedHashMap;,"Ljava/util/LinkedHashMap<Ljava/lang/Integer;Ljava/lang/String;>;"
    .end local v7           #key:Ljava/lang/String;
    :cond_14
    const-string v4, ""

    .line 847
    .local v4, changedFields:Ljava/lang/String;
    invoke-virtual {v5}, Ljava/util/LinkedHashMap;->keySet()Ljava/util/Set;

    move-result-object v11

    invoke-interface {v11}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v12

    :goto_c
    invoke-interface {v12}, Ljava/util/Iterator;->hasNext()Z

    move-result v11

    if-nez v11, :cond_15

    .line 861
    new-instance v3, Landroid/app/AlertDialog$Builder;

    move-object/from16 v0, p0

    iget-object v11, v0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->mContext:Landroid/content/Context;

    invoke-direct {v3, v11}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 862
    .local v3, builder:Landroid/app/AlertDialog$Builder;
    invoke-virtual {v3, v4}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v11

    .line 863
    invoke-virtual/range {p0 .. p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->getResources()Landroid/content/res/Resources;

    move-result-object v12

    const/high16 v13, 0x7f07

    invoke-virtual {v5}, Ljava/util/LinkedHashMap;->size()I

    move-result v14

    invoke-virtual {v12, v13, v14}, Landroid/content/res/Resources;->getQuantityString(II)Ljava/lang/String;

    move-result-object v12

    invoke-virtual {v11, v12}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v11

    .line 864
    const/4 v12, 0x1

    invoke-virtual {v11, v12}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    move-result-object v11

    .line 865
    const-string v12, "Commit"

    .line 866
    new-instance v13, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$23;

    move-object/from16 v0, p0

    invoke-direct {v13, v0, v5}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$23;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;Ljava/util/LinkedHashMap;)V

    .line 865
    invoke-virtual {v11, v12, v13}, Landroid/app/AlertDialog$Builder;->setPositiveButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v11

    .line 894
    const-string v12, "Cancel"

    .line 895
    new-instance v13, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$24;

    move-object/from16 v0, p0

    invoke-direct {v13, v0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$24;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)V

    .line 894
    invoke-virtual {v11, v12, v13}, Landroid/app/AlertDialog$Builder;->setNegativeButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 901
    invoke-virtual {v3}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v2

    .line 902
    .local v2, alertDialog:Landroid/app/AlertDialog;
    invoke-virtual/range {p0 .. p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->isFinishing()Z

    move-result v11

    if-nez v11, :cond_1

    .line 903
    invoke-virtual {v2}, Landroid/app/AlertDialog;->show()V

    goto/16 :goto_1

    .line 847
    .end local v2           #alertDialog:Landroid/app/AlertDialog;
    .end local v3           #builder:Landroid/app/AlertDialog$Builder;
    :cond_15
    invoke-interface {v12}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v11

    check-cast v11, Ljava/lang/Integer;

    invoke-virtual {v11}, Ljava/lang/Integer;->intValue()I

    move-result v7

    .line 848
    .local v7, key:I
    invoke-virtual {v4}, Ljava/lang/String;->length()I

    move-result v11

    if-lez v11, :cond_16

    .line 849
    new-instance v11, Ljava/lang/StringBuilder;

    invoke-static {v4}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v13

    invoke-direct {v11, v13}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v13, "\n"

    invoke-virtual {v11, v13}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual {v11}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    .line 850
    :cond_16
    new-instance v13, Ljava/lang/StringBuilder;

    invoke-static {v4}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v11

    invoke-direct {v13, v11}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 851
    const-string v14, "%s: %s > %s"

    const/4 v11, 0x3

    new-array v15, v11, [Ljava/lang/Object;

    const/16 v16, 0x0

    .line 852
    invoke-static {v7}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v11

    invoke-virtual {v5, v11}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    check-cast v11, [Ljava/lang/String;

    const/16 v17, 0x0

    aget-object v11, v11, v17

    const-string v17, "PARAM_"

    const-string v18, ""

    move-object/from16 v0, v17

    move-object/from16 v1, v18

    invoke-virtual {v11, v0, v1}, Ljava/lang/String;->replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;

    move-result-object v11

    .line 853
    const-string v17, "FEATURE_"

    const-string v18, ""

    .line 852
    move-object/from16 v0, v17

    move-object/from16 v1, v18

    invoke-virtual {v11, v0, v1}, Ljava/lang/String;->replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;

    move-result-object v11

    aput-object v11, v15, v16

    const/16 v16, 0x1

    .line 853
    invoke-static {v7}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v11

    invoke-virtual {v5, v11}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    check-cast v11, [Ljava/lang/String;

    const/16 v17, 0x1

    aget-object v11, v11, v17

    aput-object v11, v15, v16

    const/16 v16, 0x2

    .line 854
    invoke-static {v7}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v11

    invoke-virtual {v5, v11}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    check-cast v11, [Ljava/lang/String;

    const/16 v17, 0x2

    aget-object v11, v11, v17

    aput-object v11, v15, v16

    .line 850
    invoke-static {v14, v15}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v13, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual {v11}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    goto/16 :goto_c
.end method

.method private downloadLayout()V
    .locals 5

    .prologue
    const/4 v4, 0x1

    .line 956
    new-instance v0, Landroid/app/ProgressDialog;

    iget-object v1, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->mContext:Landroid/content/Context;

    invoke-direct {v0, v1}, Landroid/app/ProgressDialog;-><init>(Landroid/content/Context;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->downloadProgress:Landroid/app/ProgressDialog;

    .line 957
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->downloadProgress:Landroid/app/ProgressDialog;

    const-string v1, "Downloading Hi-Res Graphics"

    invoke-virtual {v0, v1}, Landroid/app/ProgressDialog;->setMessage(Ljava/lang/CharSequence;)V

    .line 958
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->downloadProgress:Landroid/app/ProgressDialog;

    invoke-virtual {v0, v4}, Landroid/app/ProgressDialog;->setIndeterminate(Z)V

    .line 959
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->downloadProgress:Landroid/app/ProgressDialog;

    const/16 v1, 0x64

    invoke-virtual {v0, v1}, Landroid/app/ProgressDialog;->setMax(I)V

    .line 960
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->downloadProgress:Landroid/app/ProgressDialog;

    invoke-virtual {v0, v4}, Landroid/app/ProgressDialog;->setCancelable(Z)V

    .line 961
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->downloadProgress:Landroid/app/ProgressDialog;

    invoke-virtual {v0, v4}, Landroid/app/ProgressDialog;->setProgressStyle(I)V

    .line 962
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->downloadProgress:Landroid/app/ProgressDialog;

    invoke-virtual {v0}, Landroid/app/ProgressDialog;->show()V

    .line 964
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->downloadProgress:Landroid/app/ProgressDialog;

    new-instance v1, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$27;

    invoke-direct {v1, p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$27;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)V

    invoke-virtual {v0, v1}, Landroid/app/ProgressDialog;->setOnDismissListener(Landroid/content/DialogInterface$OnDismissListener;)V

    .line 974
    new-instance v0, Lcom/openvehicles/OVMS/ServerCommands$CarLayoutDownloader;

    iget-object v1, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->downloadProgress:Landroid/app/ProgressDialog;

    invoke-direct {v0, v1}, Lcom/openvehicles/OVMS/ServerCommands$CarLayoutDownloader;-><init>(Landroid/app/ProgressDialog;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->downloadTask:Lcom/openvehicles/OVMS/ServerCommands$CarLayoutDownloader;

    .line 975
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->downloadTask:Lcom/openvehicles/OVMS/ServerCommands$CarLayoutDownloader;

    const/4 v1, 0x2

    new-array v1, v1, [Ljava/lang/String;

    const/4 v2, 0x0

    iget-object v3, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v3, v3, Lcom/openvehicles/OVMS/CarData;->VehicleImageDrawable:Ljava/lang/String;

    aput-object v3, v1, v2

    iget-object v2, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->mContext:Landroid/content/Context;

    invoke-virtual {v2}, Landroid/content/Context;->getCacheDir()Ljava/io/File;

    move-result-object v2

    invoke-virtual {v2}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v2

    aput-object v2, v1, v4

    invoke-virtual {v0, v1}, Lcom/openvehicles/OVMS/ServerCommands$CarLayoutDownloader;->execute([Ljava/lang/Object;)Landroid/os/AsyncTask;

    .line 976
    return-void
.end method

.method private forceContext(Landroid/content/Context;Landroid/preference/Preference;)V
    .locals 4
    .parameter "context"
    .parameter "p"

    .prologue
    .line 151
    :try_start_0
    const-class v2, Landroid/preference/Preference;

    const-string v3, "mContext"

    invoke-virtual {v2, v3}, Ljava/lang/Class;->getDeclaredField(Ljava/lang/String;)Ljava/lang/reflect/Field;

    move-result-object v1

    .line 152
    .local v1, field:Ljava/lang/reflect/Field;
    const/4 v2, 0x1

    invoke-virtual {v1, v2}, Ljava/lang/reflect/Field;->setAccessible(Z)V

    .line 153
    invoke-virtual {v1, p2, p1}, Ljava/lang/reflect/Field;->set(Ljava/lang/Object;Ljava/lang/Object;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 157
    .end local v1           #field:Ljava/lang/reflect/Field;
    :goto_0
    return-void

    .line 154
    :catch_0
    move-exception v0

    .line 155
    .local v0, e:Ljava/lang/Exception;
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_0
.end method

.method private getSharedPreference(Ljava/lang/String;)Ljava/lang/String;
    .locals 2
    .parameter "key"

    .prologue
    .line 600
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->cachedUIPreferences:Landroid/content/SharedPreferences;

    const/4 v1, 0x0

    invoke-interface {v0, p1, v1}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method private makeToast(Ljava/lang/String;I)V
    .locals 1
    .parameter "text"
    .parameter "duration"

    .prologue
    .line 715
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->toastDisplayed:Landroid/widget/Toast;

    if-eqz v0, :cond_0

    .line 717
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->toastDisplayed:Landroid/widget/Toast;

    invoke-virtual {v0}, Landroid/widget/Toast;->cancel()V

    .line 718
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->toastDisplayed:Landroid/widget/Toast;

    .line 720
    :cond_0
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->mContext:Landroid/content/Context;

    invoke-static {v0, p1, p2}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v0

    iput-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->toastDisplayed:Landroid/widget/Toast;

    .line 721
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->toastDisplayed:Landroid/widget/Toast;

    invoke-virtual {v0}, Landroid/widget/Toast;->show()V

    .line 722
    return-void
.end method

.method private requestSettings()V
    .locals 10

    .prologue
    const/4 v6, 0x5

    const/4 v9, 0x0

    .line 908
    const-string v2, ""

    .line 909
    .local v2, firmware:Ljava/lang/String;
    const/4 v3, 0x0

    .line 910
    .local v3, firmwareNumeric:I
    iget-object v5, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v5, v5, Lcom/openvehicles/OVMS/CarData;->Data_CarModuleFirmwareVersion:Ljava/lang/String;

    invoke-virtual {v5}, Ljava/lang/String;->length()I

    move-result v5

    if-lt v5, v6, :cond_0

    .line 911
    iget-object v5, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v5, v5, Lcom/openvehicles/OVMS/CarData;->Data_CarModuleFirmwareVersion:Ljava/lang/String;

    invoke-virtual {v5, v9, v6}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v2

    .line 913
    :try_start_0
    const-string v5, "OVMS"

    .line 914
    new-instance v6, Ljava/lang/StringBuilder;

    const-string v7, "Current Firmware: "

    invoke-direct {v6, v7}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v7, "\\."

    const-string v8, ""

    invoke-virtual {v2, v7, v8}, Ljava/lang/String;->replaceAll(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v7

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    .line 913
    invoke-static {v5, v6}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 915
    const-string v5, "\\."

    .line 916
    const-string v6, ""

    .line 915
    invoke-virtual {v2, v5, v6}, Ljava/lang/String;->replaceAll(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v5

    invoke-static {v5}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    move-result v3

    .line 921
    :cond_0
    :goto_0
    const/16 v5, 0x77

    if-ge v3, v5, :cond_2

    .line 923
    new-instance v1, Landroid/app/AlertDialog$Builder;

    iget-object v5, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->mContext:Landroid/content/Context;

    invoke-direct {v1, v5}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 925
    .local v1, builder:Landroid/app/AlertDialog$Builder;
    const-string v5, "Please upgrade vehicle module firmware to 1.1.9-exp3 or later."

    .line 924
    invoke-virtual {v1, v5}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v5

    .line 926
    const-string v6, "Unsupported Firmware"

    invoke-virtual {v5, v6}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v5

    .line 927
    invoke-virtual {v5, v9}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    move-result-object v5

    .line 928
    const-string v6, "Close"

    .line 929
    new-instance v7, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$25;

    invoke-direct {v7, p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$25;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)V

    .line 928
    invoke-virtual {v5, v6, v7}, Landroid/app/AlertDialog$Builder;->setPositiveButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 935
    invoke-virtual {v1}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v0

    .line 936
    .local v0, alertDialog:Landroid/app/AlertDialog;
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->isFinishing()Z

    move-result v5

    if-nez v5, :cond_1

    .line 937
    invoke-virtual {v0}, Landroid/app/AlertDialog;->show()V

    .line 951
    .end local v0           #alertDialog:Landroid/app/AlertDialog;
    .end local v1           #builder:Landroid/app/AlertDialog$Builder;
    :cond_1
    :goto_1
    return-void

    .line 941
    :cond_2
    const-string v5, "Requesting data from car..."

    const/4 v6, 0x1

    invoke-direct {p0, v5, v6}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->makeToast(Ljava/lang/String;I)V

    .line 943
    iget-object v5, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->mOVMSActivity:Lcom/openvehicles/OVMS/OVMSActivity;

    const-string v6, "C3"

    invoke-virtual {v5, v6}, Lcom/openvehicles/OVMS/OVMSActivity;->SendServerCommand(Ljava/lang/String;)Z

    .line 945
    new-instance v4, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$26;

    invoke-direct {v4, p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$26;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)V

    .line 950
    .local v4, r:Ljava/lang/Runnable;
    iget-object v5, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->delayedRequest:Landroid/os/Handler;

    const-wide/16 v6, 0xc8

    invoke-virtual {v5, v4, v6, v7}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    goto :goto_1

    .line 917
    .end local v4           #r:Ljava/lang/Runnable;
    :catch_0
    move-exception v5

    goto :goto_0
.end method

.method private setSharedPreference(Ljava/lang/String;Ljava/lang/String;)V
    .locals 2
    .parameter "key"
    .parameter "value"

    .prologue
    .line 604
    iget-object v1, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->cachedUIPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v1}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    .line 605
    .local v0, editor:Landroid/content/SharedPreferences$Editor;
    invoke-interface {v0, p1, p2}, Landroid/content/SharedPreferences$Editor;->putString(Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    .line 606
    invoke-interface {v0}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 607
    return-void
.end method

.method private wireUpDynamicMessage(Landroid/preference/Preference;Ljava/lang/String;)V
    .locals 1
    .parameter "p"
    .parameter "fieldType"

    .prologue
    .line 161
    new-instance v0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$4;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$4;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)V

    invoke-virtual {p1, v0}, Landroid/preference/Preference;->setOnPreferenceChangeListener(Landroid/preference/Preference$OnPreferenceChangeListener;)V

    .line 168
    const-string v0, "String"

    invoke-virtual {p2, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    move-object v0, p1

    .line 169
    check-cast v0, Landroid/preference/EditTextPreference;

    invoke-virtual {v0}, Landroid/preference/EditTextPreference;->getText()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/String;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/preference/Preference;->setSummary(Ljava/lang/CharSequence;)V

    .line 170
    :cond_0
    const-string v0, "List"

    invoke-virtual {p2, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_1

    move-object v0, p1

    .line 171
    check-cast v0, Landroid/preference/ListPreference;

    invoke-virtual {v0}, Landroid/preference/ListPreference;->getValue()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/String;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p1, v0}, Landroid/preference/Preference;->setSummary(Ljava/lang/CharSequence;)V

    .line 172
    :cond_1
    return-void
.end method

.method private wireUpPrefButtons()V
    .locals 3

    .prologue
    const/4 v2, 0x0

    .line 176
    const-string v1, "startcharge"

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v0

    .line 177
    .local v0, customPref:Landroid/preference/Preference;
    new-instance v1, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$5;

    invoke-direct {v1, p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$5;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)V

    invoke-virtual {v0, v1}, Landroid/preference/Preference;->setOnPreferenceClickListener(Landroid/preference/Preference$OnPreferenceClickListener;)V

    .line 188
    const-string v1, "stopcharge"

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v0

    .line 189
    new-instance v1, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$6;

    invoke-direct {v1, p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$6;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)V

    invoke-virtual {v0, v1}, Landroid/preference/Preference;->setOnPreferenceClickListener(Landroid/preference/Preference$OnPreferenceClickListener;)V

    .line 200
    const-string v1, "chargemode"

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v0

    .line 201
    new-instance v1, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$7;

    invoke-direct {v1, p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$7;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)V

    invoke-virtual {v0, v1}, Landroid/preference/Preference;->setOnPreferenceClickListener(Landroid/preference/Preference$OnPreferenceClickListener;)V

    .line 212
    const-string v1, "lockcar"

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v0

    .line 213
    new-instance v1, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$8;

    invoke-direct {v1, p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$8;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)V

    invoke-virtual {v0, v1}, Landroid/preference/Preference;->setOnPreferenceClickListener(Landroid/preference/Preference$OnPreferenceClickListener;)V

    .line 224
    const-string v1, "unlockcar"

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v0

    .line 225
    new-instance v1, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$9;

    invoke-direct {v1, p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$9;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)V

    invoke-virtual {v0, v1}, Landroid/preference/Preference;->setOnPreferenceClickListener(Landroid/preference/Preference$OnPreferenceClickListener;)V

    .line 236
    const-string v1, "valeton"

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v0

    .line 237
    new-instance v1, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$10;

    invoke-direct {v1, p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$10;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)V

    invoke-virtual {v0, v1}, Landroid/preference/Preference;->setOnPreferenceClickListener(Landroid/preference/Preference$OnPreferenceClickListener;)V

    .line 248
    const-string v1, "valetoff"

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v0

    .line 249
    new-instance v1, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$11;

    invoke-direct {v1, p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$11;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)V

    invoke-virtual {v0, v1}, Landroid/preference/Preference;->setOnPreferenceClickListener(Landroid/preference/Preference$OnPreferenceClickListener;)V

    .line 260
    const-string v1, "setchargecurrent"

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v0

    .line 261
    new-instance v1, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$12;

    invoke-direct {v1, p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$12;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)V

    invoke-virtual {v0, v1}, Landroid/preference/Preference;->setOnPreferenceClickListener(Landroid/preference/Preference$OnPreferenceClickListener;)V

    .line 272
    const-string v1, "wakeupcar"

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v0

    .line 273
    new-instance v1, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$13;

    invoke-direct {v1, p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$13;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)V

    invoke-virtual {v0, v1}, Landroid/preference/Preference;->setOnPreferenceClickListener(Landroid/preference/Preference$OnPreferenceClickListener;)V

    .line 284
    const-string v1, "wakeuptemps"

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v0

    .line 285
    new-instance v1, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$14;

    invoke-direct {v1, p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$14;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)V

    invoke-virtual {v0, v1}, Landroid/preference/Preference;->setOnPreferenceClickListener(Landroid/preference/Preference$OnPreferenceClickListener;)V

    .line 296
    const-string v1, "restartovms"

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v0

    .line 297
    new-instance v1, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$15;

    invoke-direct {v1, p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$15;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)V

    invoke-virtual {v0, v1}, Landroid/preference/Preference;->setOnPreferenceClickListener(Landroid/preference/Preference$OnPreferenceClickListener;)V

    .line 330
    const-string v1, "sendsms"

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v0

    .line 331
    new-instance v1, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16;

    invoke-direct {v1, p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$16;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)V

    invoke-virtual {v0, v1}, Landroid/preference/Preference;->setOnPreferenceClickListener(Landroid/preference/Preference$OnPreferenceClickListener;)V

    .line 377
    const-string v1, "sendussd"

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v0

    .line 378
    new-instance v1, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$17;

    invoke-direct {v1, p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$17;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)V

    invoke-virtual {v0, v1}, Landroid/preference/Preference;->setOnPreferenceClickListener(Landroid/preference/Preference$OnPreferenceClickListener;)V

    .line 413
    const-string v1, "sendat"

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v0

    .line 414
    new-instance v1, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$18;

    invoke-direct {v1, p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$18;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)V

    invoke-virtual {v0, v1}, Landroid/preference/Preference;->setOnPreferenceClickListener(Landroid/preference/Preference$OnPreferenceClickListener;)V

    .line 449
    const-string v1, "commslog"

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v0

    .line 450
    new-instance v1, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$19;

    invoke-direct {v1, p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$19;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)V

    invoke-virtual {v0, v1}, Landroid/preference/Preference;->setOnPreferenceClickListener(Landroid/preference/Preference$OnPreferenceClickListener;)V

    .line 472
    const-string v1, "downloadgraphics"

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v0

    .line 473
    new-instance v1, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$20;

    invoke-direct {v1, p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$20;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)V

    invoke-virtual {v0, v1}, Landroid/preference/Preference;->setOnPreferenceClickListener(Landroid/preference/Preference$OnPreferenceClickListener;)V

    .line 502
    const-string v1, "reinitializec2dm"

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v0

    .line 503
    new-instance v1, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$21;

    invoke-direct {v1, p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$21;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)V

    invoke-virtual {v0, v1}, Landroid/preference/Preference;->setOnPreferenceClickListener(Landroid/preference/Preference$OnPreferenceClickListener;)V

    .line 540
    const-string v1, "FEATURE_DEBUGMODEM"

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v0

    .line 541
    invoke-virtual {v0, v2}, Landroid/preference/Preference;->setEnabled(Z)V

    .line 543
    const-string v1, "resetovms"

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v0

    .line 545
    invoke-virtual {v0, v2}, Landroid/preference/Preference;->setEnabled(Z)V

    .line 546
    new-instance v1, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$22;

    invoke-direct {v1, p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$22;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)V

    invoke-virtual {v0, v1}, Landroid/preference/Preference;->setOnPreferenceClickListener(Landroid/preference/Preference$OnPreferenceClickListener;)V

    .line 583
    return-void
.end method


# virtual methods
.method public Refresh(Lcom/openvehicles/OVMS/CarData;Z)V
    .locals 2
    .parameter "carData"
    .parameter "isLoggedIn"

    .prologue
    .line 725
    iput-object p1, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->data:Lcom/openvehicles/OVMS/CarData;

    .line 728
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->handler:Landroid/os/Handler;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    .line 729
    return-void
.end method

.method public onCreate(Landroid/os/Bundle;)V
    .locals 13
    .parameter "savedInstanceState"

    .prologue
    const/16 v12, 0x8

    const/4 v11, 0x7

    const/4 v10, 0x2

    const/4 v9, 0x0

    const/4 v8, 0x1

    .line 42
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->getParent()Landroid/app/Activity;

    move-result-object v4

    iput-object v4, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->mContext:Landroid/content/Context;

    .line 44
    invoke-super {p0, p1}, Landroid/preference/PreferenceActivity;->onCreate(Landroid/os/Bundle;)V

    .line 46
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->getParent()Landroid/app/Activity;

    move-result-object v4

    invoke-virtual {v4}, Landroid/app/Activity;->getParent()Landroid/app/Activity;

    move-result-object v4

    check-cast v4, Lcom/openvehicles/OVMS/OVMSActivity;

    iput-object v4, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->mOVMSActivity:Lcom/openvehicles/OVMS/OVMSActivity;

    .line 47
    iget-object v4, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->mOVMSActivity:Lcom/openvehicles/OVMS/OVMSActivity;

    if-nez v4, :cond_0

    .line 48
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->getParent()Landroid/app/Activity;

    move-result-object v4

    check-cast v4, Lcom/openvehicles/OVMS/OVMSActivity;

    iput-object v4, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->mOVMSActivity:Lcom/openvehicles/OVMS/OVMSActivity;

    .line 49
    :cond_0
    iget-object v4, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->mOVMSActivity:Lcom/openvehicles/OVMS/OVMSActivity;

    if-nez v4, :cond_1

    .line 50
    const-string v4, "Unknown Layout Error"

    invoke-static {p0, v4, v8}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v4

    invoke-virtual {v4}, Landroid/widget/Toast;->show()V

    .line 53
    :cond_1
    iget-object v4, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    const-string v5, "PARAM_REGPHONE"

    new-array v6, v10, [Ljava/lang/Object;

    .line 54
    invoke-static {v9}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    aput-object v7, v6, v9

    const-string v7, "String"

    aput-object v7, v6, v8

    .line 53
    invoke-virtual {v4, v5, v6}, Ljava/util/LinkedHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 55
    iget-object v4, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    const-string v5, "PARAM_REGPASS"

    new-array v6, v10, [Ljava/lang/Object;

    .line 56
    invoke-static {v8}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    aput-object v7, v6, v9

    const-string v7, "String"

    aput-object v7, v6, v8

    .line 55
    invoke-virtual {v4, v5, v6}, Ljava/util/LinkedHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 57
    iget-object v4, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    const-string v5, "PARAM_MILESKM"

    new-array v6, v10, [Ljava/lang/Object;

    .line 58
    invoke-static {v10}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    aput-object v7, v6, v9

    const-string v7, "List"

    aput-object v7, v6, v8

    .line 57
    invoke-virtual {v4, v5, v6}, Ljava/util/LinkedHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 59
    iget-object v4, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    const-string v5, "PARAM_NOTIFIES"

    new-array v6, v10, [Ljava/lang/Object;

    .line 60
    const/4 v7, 0x3

    invoke-static {v7}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    aput-object v7, v6, v9

    const-string v7, "List"

    aput-object v7, v6, v8

    .line 59
    invoke-virtual {v4, v5, v6}, Ljava/util/LinkedHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 61
    iget-object v4, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    const-string v5, "PARAM_SERVERIP"

    new-array v6, v10, [Ljava/lang/Object;

    .line 62
    const/4 v7, 0x4

    invoke-static {v7}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    aput-object v7, v6, v9

    const-string v7, "String"

    aput-object v7, v6, v8

    .line 61
    invoke-virtual {v4, v5, v6}, Ljava/util/LinkedHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 63
    iget-object v4, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    const-string v5, "PARAM_GPRSAPN"

    new-array v6, v10, [Ljava/lang/Object;

    .line 64
    const/4 v7, 0x5

    invoke-static {v7}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    aput-object v7, v6, v9

    const-string v7, "String"

    aput-object v7, v6, v8

    .line 63
    invoke-virtual {v4, v5, v6}, Ljava/util/LinkedHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 65
    iget-object v4, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    const-string v5, "PARAM_GPRSUSER"

    new-array v6, v10, [Ljava/lang/Object;

    .line 66
    const/4 v7, 0x6

    invoke-static {v7}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    aput-object v7, v6, v9

    const-string v7, "String"

    aput-object v7, v6, v8

    .line 65
    invoke-virtual {v4, v5, v6}, Ljava/util/LinkedHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 67
    iget-object v4, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    const-string v5, "PARAM_GPRSPASS"

    new-array v6, v10, [Ljava/lang/Object;

    .line 68
    invoke-static {v11}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    aput-object v7, v6, v9

    const-string v7, "String"

    aput-object v7, v6, v8

    .line 67
    invoke-virtual {v4, v5, v6}, Ljava/util/LinkedHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 69
    iget-object v4, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    const-string v5, "PARAM_MYID"

    new-array v6, v10, [Ljava/lang/Object;

    .line 70
    invoke-static {v12}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    aput-object v7, v6, v9

    const-string v7, "String"

    aput-object v7, v6, v8

    .line 69
    invoke-virtual {v4, v5, v6}, Ljava/util/LinkedHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 71
    iget-object v4, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    const-string v5, "PARAM_NETPASS1"

    new-array v6, v10, [Ljava/lang/Object;

    .line 72
    const/16 v7, 0x9

    invoke-static {v7}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    aput-object v7, v6, v9

    const-string v7, "String"

    aput-object v7, v6, v8

    .line 71
    invoke-virtual {v4, v5, v6}, Ljava/util/LinkedHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 73
    iget-object v4, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    const-string v5, "PARAM_PARANOID"

    new-array v6, v10, [Ljava/lang/Object;

    .line 74
    const/16 v7, 0xa

    invoke-static {v7}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    aput-object v7, v6, v9

    const-string v7, "bool"

    aput-object v7, v6, v8

    .line 73
    invoke-virtual {v4, v5, v6}, Ljava/util/LinkedHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 75
    iget-object v4, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    const-string v5, "PARAM_S_GROUP"

    new-array v6, v10, [Ljava/lang/Object;

    .line 76
    const/16 v7, 0xb

    invoke-static {v7}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    aput-object v7, v6, v9

    const-string v7, "String"

    aput-object v7, v6, v8

    .line 75
    invoke-virtual {v4, v5, v6}, Ljava/util/LinkedHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 78
    iget-object v4, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    const-string v5, "FEATURE_SPEEDO"

    new-array v6, v10, [Ljava/lang/Object;

    .line 79
    invoke-static {v9}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    aput-object v7, v6, v9

    const-string v7, "bool"

    aput-object v7, v6, v8

    .line 78
    invoke-virtual {v4, v5, v6}, Ljava/util/LinkedHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 80
    iget-object v4, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    const-string v5, "FEATURE_DEBUGMODEM"

    new-array v6, v10, [Ljava/lang/Object;

    .line 81
    invoke-static {v11}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    aput-object v7, v6, v9

    const-string v7, "bool"

    aput-object v7, v6, v8

    .line 80
    invoke-virtual {v4, v5, v6}, Ljava/util/LinkedHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 82
    iget-object v4, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    const-string v5, "FEATURE_STREAM"

    new-array v6, v10, [Ljava/lang/Object;

    .line 83
    invoke-static {v12}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    aput-object v7, v6, v9

    const-string v7, "bool"

    aput-object v7, v6, v8

    .line 82
    invoke-virtual {v4, v5, v6}, Ljava/util/LinkedHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 84
    iget-object v4, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    const-string v5, "FEATURE_MINSOC"

    new-array v6, v10, [Ljava/lang/Object;

    .line 85
    const/16 v7, 0x9

    invoke-static {v7}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    aput-object v7, v6, v9

    const-string v7, "List"

    aput-object v7, v6, v8

    .line 84
    invoke-virtual {v4, v5, v6}, Ljava/util/LinkedHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 86
    iget-object v4, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    const-string v5, "FEATURE_CANWRITE"

    new-array v6, v10, [Ljava/lang/Object;

    .line 87
    const/16 v7, 0xf

    invoke-static {v7}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    aput-object v7, v6, v9

    const-string v7, "bool"

    aput-object v7, v6, v8

    .line 86
    invoke-virtual {v4, v5, v6}, Ljava/util/LinkedHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 91
    invoke-static {p0}, Landroid/preference/PreferenceManager;->getDefaultSharedPreferences(Landroid/content/Context;)Landroid/content/SharedPreferences;

    move-result-object v4

    .line 90
    iput-object v4, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->cachedUIPreferences:Landroid/content/SharedPreferences;

    .line 92
    const v4, 0x7f030006

    invoke-virtual {p0, v4}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->addPreferencesFromResource(I)V

    .line 93
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->getParent()Landroid/app/Activity;

    move-result-object v4

    invoke-static {v4}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object v4

    .line 94
    const v5, 0x7f030005

    const/4 v6, 0x0

    .line 93
    invoke-virtual {v4, v5, v6}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object v1

    .line 95
    .local v1, contentView:Landroid/view/View;
    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->setContentView(Landroid/view/View;)V

    .line 97
    const v4, 0x7f09000f

    invoke-virtual {p0, v4}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/Button;

    .line 98
    .local v0, btn:Landroid/widget/Button;
    new-instance v4, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$2;

    invoke-direct {v4, p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$2;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)V

    invoke-virtual {v0, v4}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 103
    const v4, 0x7f09000e

    invoke-virtual {p0, v4}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->findViewById(I)Landroid/view/View;

    move-result-object v0

    .end local v0           #btn:Landroid/widget/Button;
    check-cast v0, Landroid/widget/Button;

    .line 104
    .restart local v0       #btn:Landroid/widget/Button;
    new-instance v4, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$3;

    invoke-direct {v4, p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings$3;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;)V

    invoke-virtual {v0, v4}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 134
    iget-object v4, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    invoke-virtual {v4}, Ljava/util/LinkedHashMap;->keySet()Ljava/util/Set;

    move-result-object v4

    invoke-interface {v4}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v5

    :cond_2
    :goto_0
    invoke-interface {v5}, Ljava/util/Iterator;->hasNext()Z

    move-result v4

    if-nez v4, :cond_3

    .line 144
    invoke-direct {p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->wireUpPrefButtons()V

    .line 145
    return-void

    .line 134
    :cond_3
    invoke-interface {v5}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/String;

    .line 135
    .local v2, key:Ljava/lang/String;
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->getPreferenceManager()Landroid/preference/PreferenceManager;

    move-result-object v4

    invoke-virtual {v4, v2}, Landroid/preference/PreferenceManager;->findPreference(Ljava/lang/CharSequence;)Landroid/preference/Preference;

    move-result-object v3

    .line 136
    .local v3, p:Landroid/preference/Preference;
    iget-object v4, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->mContext:Landroid/content/Context;

    invoke-direct {p0, v4, v3}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->forceContext(Landroid/content/Context;Landroid/preference/Preference;)V

    .line 138
    iget-object v4, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    invoke-virtual {v4, v2}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, [Ljava/lang/Object;

    aget-object v4, v4, v8

    const-string v6, "String"

    invoke-virtual {v4, v6}, Ljava/lang/Object;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-nez v4, :cond_4

    .line 139
    iget-object v4, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    invoke-virtual {v4, v2}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, [Ljava/lang/Object;

    aget-object v4, v4, v8

    const-string v6, "List"

    invoke-virtual {v4, v6}, Ljava/lang/Object;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_2

    .line 140
    :cond_4
    iget-object v4, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->preferenceStorageMapping:Ljava/util/LinkedHashMap;

    invoke-virtual {v4, v2}, Ljava/util/LinkedHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, [Ljava/lang/Object;

    aget-object v4, v4, v8

    invoke-virtual {v4}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-direct {p0, v3, v4}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->wireUpDynamicMessage(Landroid/preference/Preference;Ljava/lang/String;)V

    goto :goto_0
.end method

.method protected onPause()V
    .locals 1

    .prologue
    .line 611
    invoke-super {p0}, Landroid/preference/PreferenceActivity;->onPause()V

    .line 612
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->toastDisplayed:Landroid/widget/Toast;

    if-eqz v0, :cond_0

    .line 613
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->toastDisplayed:Landroid/widget/Toast;

    invoke-virtual {v0}, Landroid/widget/Toast;->cancel()V

    .line 614
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->toastDisplayed:Landroid/widget/Toast;

    .line 616
    :cond_0
    return-void
.end method
