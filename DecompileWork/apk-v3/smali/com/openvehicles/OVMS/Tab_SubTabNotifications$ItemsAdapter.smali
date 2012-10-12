.class Lcom/openvehicles/OVMS/Tab_SubTabNotifications$ItemsAdapter;
.super Landroid/widget/ArrayAdapter;
.source "Tab_SubTabNotifications.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/openvehicles/OVMS/Tab_SubTabNotifications;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "ItemsAdapter"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Landroid/widget/ArrayAdapter",
        "<",
        "Lcom/openvehicles/OVMS/NotificationData;",
        ">;"
    }
.end annotation


# instance fields
.field private dateFormatter:Ljava/text/SimpleDateFormat;

.field private items:[Lcom/openvehicles/OVMS/NotificationData;

.field final synthetic this$0:Lcom/openvehicles/OVMS/Tab_SubTabNotifications;

.field private timeFormatter:Ljava/text/SimpleDateFormat;


# direct methods
.method public constructor <init>(Lcom/openvehicles/OVMS/Tab_SubTabNotifications;Landroid/content/Context;I[Lcom/openvehicles/OVMS/NotificationData;)V
    .locals 2
    .parameter
    .parameter "context"
    .parameter "textViewResourceId"
    .parameter "items"

    .prologue
    .line 72
    iput-object p1, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications$ItemsAdapter;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabNotifications;

    .line 73
    invoke-direct {p0, p2, p3, p4}, Landroid/widget/ArrayAdapter;-><init>(Landroid/content/Context;I[Ljava/lang/Object;)V

    .line 74
    iput-object p4, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications$ItemsAdapter;->items:[Lcom/openvehicles/OVMS/NotificationData;

    .line 75
    new-instance v0, Ljava/text/SimpleDateFormat;

    const-string v1, "MMMMM d"

    invoke-direct {v0, v1}, Ljava/text/SimpleDateFormat;-><init>(Ljava/lang/String;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications$ItemsAdapter;->dateFormatter:Ljava/text/SimpleDateFormat;

    .line 76
    new-instance v0, Ljava/text/SimpleDateFormat;

    const-string v1, "h:mm a"

    invoke-direct {v0, v1}, Ljava/text/SimpleDateFormat;-><init>(Ljava/lang/String;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications$ItemsAdapter;->timeFormatter:Ljava/text/SimpleDateFormat;

    .line 77
    return-void
.end method


# virtual methods
.method public getView(ILandroid/view/View;Landroid/view/ViewGroup;)Landroid/view/View;
    .locals 7
    .parameter "position"
    .parameter "convertView"
    .parameter "parent"

    .prologue
    .line 81
    move-object v2, p2

    .line 82
    .local v2, v:Landroid/view/View;
    if-nez v2, :cond_0

    .line 83
    iget-object v4, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications$ItemsAdapter;->this$0:Lcom/openvehicles/OVMS/Tab_SubTabNotifications;

    const-string v5, "layout_inflater"

    invoke-virtual {v4, v5}, Lcom/openvehicles/OVMS/Tab_SubTabNotifications;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Landroid/view/LayoutInflater;

    .line 84
    .local v3, vi:Landroid/view/LayoutInflater;
    const v4, 0x7f030009

    const/4 v5, 0x0

    invoke-virtual {v3, v4, v5}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object v2

    .line 87
    .end local v3           #vi:Landroid/view/LayoutInflater;
    :cond_0
    iget-object v4, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications$ItemsAdapter;->items:[Lcom/openvehicles/OVMS/NotificationData;

    aget-object v0, v4, p1

    .line 88
    .local v0, it:Lcom/openvehicles/OVMS/NotificationData;
    if-eqz v0, :cond_2

    .line 89
    const v4, 0x7f090016

    invoke-virtual {v2, v4}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/TextView;

    .line 90
    .local v1, tv:Landroid/widget/TextView;
    if-eqz p1, :cond_1

    iget-object v4, v0, Lcom/openvehicles/OVMS/NotificationData;->Timestamp:Ljava/util/Date;

    invoke-virtual {v4}, Ljava/util/Date;->getDate()I

    move-result v4

    iget-object v5, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications$ItemsAdapter;->items:[Lcom/openvehicles/OVMS/NotificationData;

    add-int/lit8 v6, p1, -0x1

    aget-object v5, v5, v6

    iget-object v5, v5, Lcom/openvehicles/OVMS/NotificationData;->Timestamp:Ljava/util/Date;

    invoke-virtual {v5}, Ljava/util/Date;->getDate()I

    move-result v5

    if-eq v4, v5, :cond_3

    .line 92
    :cond_1
    iget-object v4, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications$ItemsAdapter;->dateFormatter:Ljava/text/SimpleDateFormat;

    iget-object v5, v0, Lcom/openvehicles/OVMS/NotificationData;->Timestamp:Ljava/util/Date;

    invoke-virtual {v4, v5}, Ljava/text/SimpleDateFormat;->format(Ljava/util/Date;)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v1, v4}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 93
    const/4 v4, 0x0

    invoke-virtual {v1, v4}, Landroid/widget/TextView;->setVisibility(I)V

    .line 97
    :goto_0
    const v4, 0x7f090018

    invoke-virtual {v2, v4}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    .end local v1           #tv:Landroid/widget/TextView;
    check-cast v1, Landroid/widget/TextView;

    .line 98
    .restart local v1       #tv:Landroid/widget/TextView;
    iget-object v4, v0, Lcom/openvehicles/OVMS/NotificationData;->Message:Ljava/lang/String;

    invoke-virtual {v1, v4}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 100
    const v4, 0x7f090017

    invoke-virtual {v2, v4}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    .end local v1           #tv:Landroid/widget/TextView;
    check-cast v1, Landroid/widget/TextView;

    .line 101
    .restart local v1       #tv:Landroid/widget/TextView;
    iget-object v4, p0, Lcom/openvehicles/OVMS/Tab_SubTabNotifications$ItemsAdapter;->timeFormatter:Ljava/text/SimpleDateFormat;

    iget-object v5, v0, Lcom/openvehicles/OVMS/NotificationData;->Timestamp:Ljava/util/Date;

    invoke-virtual {v4, v5}, Ljava/text/SimpleDateFormat;->format(Ljava/util/Date;)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v1, v4}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 105
    .end local v1           #tv:Landroid/widget/TextView;
    :cond_2
    return-object v2

    .line 95
    .restart local v1       #tv:Landroid/widget/TextView;
    :cond_3
    const/4 v4, 0x4

    invoke-virtual {v1, v4}, Landroid/widget/TextView;->setVisibility(I)V

    goto :goto_0
.end method
