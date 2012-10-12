.class public Lcom/openvehicles/OVMS/TabMiscFeatures;
.super Landroid/app/TabActivity;
.source "TabMiscFeatures.java"

# interfaces
.implements Landroid/widget/TabHost$OnTabChangeListener;


# instance fields
.field alertDialog:Landroid/app/AlertDialog;

.field private data:Lcom/openvehicles/OVMS/CarData;

.field private handler:Landroid/os/Handler;

.field private isLoggedIn:Z


# direct methods
.method public constructor <init>()V
    .locals 1

    .prologue
    .line 15
    invoke-direct {p0}, Landroid/app/TabActivity;-><init>()V

    .line 60
    new-instance v0, Lcom/openvehicles/OVMS/TabMiscFeatures$1;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/TabMiscFeatures$1;-><init>(Lcom/openvehicles/OVMS/TabMiscFeatures;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TabMiscFeatures;->handler:Landroid/os/Handler;

    .line 15
    return-void
.end method

.method static synthetic access$0(Lcom/openvehicles/OVMS/TabMiscFeatures;Ljava/lang/String;)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 74
    invoke-direct {p0, p1}, Lcom/openvehicles/OVMS/TabMiscFeatures;->notifyTabRefresh(Ljava/lang/String;)V

    return-void
.end method

.method private notifyTabRefresh(Ljava/lang/String;)V
    .locals 5
    .parameter "currentActivityId"

    .prologue
    .line 75
    const-string v2, "Tab"

    new-instance v3, Ljava/lang/StringBuilder;

    const-string v4, "SubTab refresh: "

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 77
    if-eqz p1, :cond_0

    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TabMiscFeatures;->getLocalActivityManager()Landroid/app/LocalActivityManager;

    move-result-object v2

    invoke-virtual {v2, p1}, Landroid/app/LocalActivityManager;->getActivity(Ljava/lang/String;)Landroid/app/Activity;

    move-result-object v2

    if-nez v2, :cond_1

    .line 118
    :cond_0
    :goto_0
    return-void

    .line 80
    :cond_1
    const-string v2, "tabNotifications"

    invoke-virtual {p1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_2

    .line 81
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TabMiscFeatures;->getLocalActivityManager()Landroid/app/LocalActivityManager;

    move-result-object v2

    invoke-virtual {v2, p1}, Landroid/app/LocalActivityManager;->getActivity(Ljava/lang/String;)Landroid/app/Activity;

    move-result-object v1

    check-cast v1, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;

    .line 83
    .local v1, tab:Lcom/openvehicles/OVMS/Tab_SubTabNotifications;
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabMiscFeatures;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v3, p0, Lcom/openvehicles/OVMS/TabMiscFeatures;->isLoggedIn:Z

    invoke-virtual {v1, v2, v3}, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->Refresh(Lcom/openvehicles/OVMS/CarData;Z)V

    .line 117
    .end local v1           #tab:Lcom/openvehicles/OVMS/Tab_SubTabNotifications;
    :goto_1
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TabMiscFeatures;->getTabHost()Landroid/widget/TabHost;

    move-result-object v2

    invoke-virtual {v2}, Landroid/widget/TabHost;->invalidate()V

    goto :goto_0

    .line 85
    :cond_2
    const-string v2, "tabDataUtilizations"

    invoke-virtual {p1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_3

    .line 86
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TabMiscFeatures;->getLocalActivityManager()Landroid/app/LocalActivityManager;

    move-result-object v2

    invoke-virtual {v2, p1}, Landroid/app/LocalActivityManager;->getActivity(Ljava/lang/String;)Landroid/app/Activity;

    move-result-object v1

    check-cast v1, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;

    .line 88
    .local v1, tab:Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabMiscFeatures;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v3, p0, Lcom/openvehicles/OVMS/TabMiscFeatures;->isLoggedIn:Z

    invoke-virtual {v1, v2, v3}, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->Refresh(Lcom/openvehicles/OVMS/CarData;Z)V

    goto :goto_1

    .line 90
    .end local v1           #tab:Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;
    :cond_3
    const-string v2, "tabCarSettings"

    invoke-virtual {p1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_4

    .line 91
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TabMiscFeatures;->getLocalActivityManager()Landroid/app/LocalActivityManager;

    move-result-object v2

    invoke-virtual {v2, p1}, Landroid/app/LocalActivityManager;->getActivity(Ljava/lang/String;)Landroid/app/Activity;

    move-result-object v1

    check-cast v1, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    .line 93
    .local v1, tab:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabMiscFeatures;->data:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v3, p0, Lcom/openvehicles/OVMS/TabMiscFeatures;->isLoggedIn:Z

    invoke-virtual {v1, v2, v3}, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;->Refresh(Lcom/openvehicles/OVMS/CarData;Z)V

    goto :goto_1

    .line 99
    .end local v1           #tab:Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;
    :cond_4
    new-instance v0, Landroid/app/AlertDialog$Builder;

    invoke-direct {v0, p0}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 101
    .local v0, builder:Landroid/app/AlertDialog$Builder;
    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "(!) TAB NOT FOUND ERROR: "

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v0, v2}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v2

    .line 102
    const/4 v3, 0x0

    invoke-virtual {v2, v3}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    move-result-object v2

    .line 103
    const-string v3, "Close"

    .line 104
    new-instance v4, Lcom/openvehicles/OVMS/TabMiscFeatures$2;

    invoke-direct {v4, p0}, Lcom/openvehicles/OVMS/TabMiscFeatures$2;-><init>(Lcom/openvehicles/OVMS/TabMiscFeatures;)V

    .line 103
    invoke-virtual {v2, v3, v4}, Landroid/app/AlertDialog$Builder;->setPositiveButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 110
    invoke-virtual {v0}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v2

    iput-object v2, p0, Lcom/openvehicles/OVMS/TabMiscFeatures;->alertDialog:Landroid/app/AlertDialog;

    .line 111
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabMiscFeatures;->alertDialog:Landroid/app/AlertDialog;

    invoke-virtual {v2}, Landroid/app/AlertDialog;->show()V

    .line 113
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TabMiscFeatures;->getTabHost()Landroid/widget/TabHost;

    move-result-object v2

    const-string v3, "tabInfo"

    invoke-virtual {v2, v3}, Landroid/widget/TabHost;->setCurrentTabByTag(Ljava/lang/String;)V

    goto :goto_1
.end method


# virtual methods
.method public Refresh(Lcom/openvehicles/OVMS/CarData;Z)V
    .locals 2
    .parameter "carData"
    .parameter "isLoggedIn"

    .prologue
    .line 68
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabMiscFeatures;->data:Lcom/openvehicles/OVMS/CarData;

    .line 71
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMiscFeatures;->handler:Landroid/os/Handler;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    .line 72
    return-void
.end method

.method public onCreate(Landroid/os/Bundle;)V
    .locals 6
    .parameter "savedInstanceState"

    .prologue
    .line 20
    invoke-super {p0, p1}, Landroid/app/TabActivity;->onCreate(Landroid/os/Bundle;)V

    .line 21
    const v3, 0x7f030013

    invoke-virtual {p0, v3}, Lcom/openvehicles/OVMS/TabMiscFeatures;->setContentView(I)V

    .line 24
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TabMiscFeatures;->getTabHost()Landroid/widget/TabHost;

    move-result-object v2

    .line 28
    .local v2, tabHost:Landroid/widget/TabHost;
    new-instance v3, Landroid/content/Intent;

    invoke-direct {v3}, Landroid/content/Intent;-><init>()V

    const-class v4, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;

    invoke-virtual {v3, p0, v4}, Landroid/content/Intent;->setClass(Landroid/content/Context;Ljava/lang/Class;)Landroid/content/Intent;

    move-result-object v0

    .line 30
    .local v0, intent:Landroid/content/Intent;
    const-string v3, "tabNotifications"

    invoke-virtual {v2, v3}, Landroid/widget/TabHost;->newTabSpec(Ljava/lang/String;)Landroid/widget/TabHost$TabSpec;

    move-result-object v1

    .line 31
    .local v1, spec:Landroid/widget/TabHost$TabSpec;
    invoke-virtual {v1, v0}, Landroid/widget/TabHost$TabSpec;->setContent(Landroid/content/Intent;)Landroid/widget/TabHost$TabSpec;

    .line 32
    const-string v3, ""

    .line 33
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TabMiscFeatures;->getResources()Landroid/content/res/Resources;

    move-result-object v4

    const v5, 0x7f020055

    invoke-virtual {v4, v5}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v4

    .line 32
    invoke-virtual {v1, v3, v4}, Landroid/widget/TabHost$TabSpec;->setIndicator(Ljava/lang/CharSequence;Landroid/graphics/drawable/Drawable;)Landroid/widget/TabHost$TabSpec;

    .line 34
    invoke-virtual {v2, v1}, Landroid/widget/TabHost;->addTab(Landroid/widget/TabHost$TabSpec;)V

    .line 36
    new-instance v3, Landroid/content/Intent;

    invoke-direct {v3}, Landroid/content/Intent;-><init>()V

    const-class v4, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;

    invoke-virtual {v3, p0, v4}, Landroid/content/Intent;->setClass(Landroid/content/Context;Ljava/lang/Class;)Landroid/content/Intent;

    move-result-object v0

    .line 38
    const-string v3, "tabDataUtilizations"

    invoke-virtual {v2, v3}, Landroid/widget/TabHost;->newTabSpec(Ljava/lang/String;)Landroid/widget/TabHost$TabSpec;

    move-result-object v1

    .line 39
    invoke-virtual {v1, v0}, Landroid/widget/TabHost$TabSpec;->setContent(Landroid/content/Intent;)Landroid/widget/TabHost$TabSpec;

    .line 40
    const-string v3, ""

    .line 41
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TabMiscFeatures;->getResources()Landroid/content/res/Resources;

    move-result-object v4

    const v5, 0x7f020049

    invoke-virtual {v4, v5}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v4

    .line 40
    invoke-virtual {v1, v3, v4}, Landroid/widget/TabHost$TabSpec;->setIndicator(Ljava/lang/CharSequence;Landroid/graphics/drawable/Drawable;)Landroid/widget/TabHost$TabSpec;

    .line 42
    invoke-virtual {v2, v1}, Landroid/widget/TabHost;->addTab(Landroid/widget/TabHost$TabSpec;)V

    .line 44
    new-instance v3, Landroid/content/Intent;

    invoke-direct {v3}, Landroid/content/Intent;-><init>()V

    const-class v4, Lcom/openvehicles/OVMS/Tab_SubTabCarSettings;

    invoke-virtual {v3, p0, v4}, Landroid/content/Intent;->setClass(Landroid/content/Context;Ljava/lang/Class;)Landroid/content/Intent;

    move-result-object v0

    .line 46
    const-string v3, "tabCarSettings"

    invoke-virtual {v2, v3}, Landroid/widget/TabHost;->newTabSpec(Ljava/lang/String;)Landroid/widget/TabHost$TabSpec;

    move-result-object v1

    .line 47
    invoke-virtual {v1, v0}, Landroid/widget/TabHost$TabSpec;->setContent(Landroid/content/Intent;)Landroid/widget/TabHost$TabSpec;

    .line 48
    const-string v3, ""

    .line 49
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TabMiscFeatures;->getResources()Landroid/content/res/Resources;

    move-result-object v4

    const v5, 0x7f02004e

    invoke-virtual {v4, v5}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v4

    .line 48
    invoke-virtual {v1, v3, v4}, Landroid/widget/TabHost$TabSpec;->setIndicator(Ljava/lang/CharSequence;Landroid/graphics/drawable/Drawable;)Landroid/widget/TabHost$TabSpec;

    .line 50
    invoke-virtual {v2, v1}, Landroid/widget/TabHost;->addTab(Landroid/widget/TabHost$TabSpec;)V

    .line 52
    invoke-virtual {v2, p0}, Landroid/widget/TabHost;->setOnTabChangedListener(Landroid/widget/TabHost$OnTabChangeListener;)V

    .line 53
    return-void
.end method

.method public onTabChanged(Ljava/lang/String;)V
    .locals 2
    .parameter "currentActivityId"

    .prologue
    .line 121
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMiscFeatures;->handler:Landroid/os/Handler;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    .line 122
    return-void
.end method
