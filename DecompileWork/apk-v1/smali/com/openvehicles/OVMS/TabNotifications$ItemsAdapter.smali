.class Lcom/openvehicles/OVMS/TabNotifications$ItemsAdapter;
.super Landroid/widget/ArrayAdapter;
.source "TabNotifications.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/openvehicles/OVMS/TabNotifications;
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
.field private items:[Lcom/openvehicles/OVMS/NotificationData;

.field final synthetic this$0:Lcom/openvehicles/OVMS/TabNotifications;


# direct methods
.method public constructor <init>(Lcom/openvehicles/OVMS/TabNotifications;Landroid/content/Context;I[Lcom/openvehicles/OVMS/NotificationData;)V
    .locals 0
    .parameter
    .parameter "context"
    .parameter "textViewResourceId"
    .parameter "items"

    .prologue
    .line 60
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabNotifications$ItemsAdapter;->this$0:Lcom/openvehicles/OVMS/TabNotifications;

    .line 61
    invoke-direct {p0, p2, p3, p4}, Landroid/widget/ArrayAdapter;-><init>(Landroid/content/Context;I[Ljava/lang/Object;)V

    .line 62
    iput-object p4, p0, Lcom/openvehicles/OVMS/TabNotifications$ItemsAdapter;->items:[Lcom/openvehicles/OVMS/NotificationData;

    .line 63
    return-void
.end method


# virtual methods
.method public getView(ILandroid/view/View;Landroid/view/ViewGroup;)Landroid/view/View;
    .locals 7
    .parameter "position"
    .parameter "convertView"
    .parameter "parent"

    .prologue
    .line 67
    move-object v3, p2

    .line 68
    .local v3, v:Landroid/view/View;
    if-nez v3, :cond_0

    .line 69
    iget-object v5, p0, Lcom/openvehicles/OVMS/TabNotifications$ItemsAdapter;->this$0:Lcom/openvehicles/OVMS/TabNotifications;

    const-string v6, "layout_inflater"

    invoke-virtual {v5, v6}, Lcom/openvehicles/OVMS/TabNotifications;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Landroid/view/LayoutInflater;

    .line 70
    .local v4, vi:Landroid/view/LayoutInflater;
    const v5, 0x7f03000b

    const/4 v6, 0x0

    invoke-virtual {v4, v5, v6}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object v3

    .line 73
    .end local v4           #vi:Landroid/view/LayoutInflater;
    :cond_0
    iget-object v5, p0, Lcom/openvehicles/OVMS/TabNotifications$ItemsAdapter;->items:[Lcom/openvehicles/OVMS/NotificationData;

    aget-object v1, v5, p1

    .line 74
    .local v1, it:Lcom/openvehicles/OVMS/NotificationData;
    if-eqz v1, :cond_1

    .line 75
    const v5, 0x7f06003d

    invoke-virtual {v3, v5}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/widget/TextView;

    .line 76
    .local v2, tv:Landroid/widget/TextView;
    iget-object v5, v1, Lcom/openvehicles/OVMS/NotificationData;->Title:Ljava/lang/String;

    invoke-virtual {v2, v5}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 77
    const v5, 0x7f06003f

    invoke-virtual {v3, v5}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v2

    .end local v2           #tv:Landroid/widget/TextView;
    check-cast v2, Landroid/widget/TextView;

    .line 78
    .restart local v2       #tv:Landroid/widget/TextView;
    iget-object v5, v1, Lcom/openvehicles/OVMS/NotificationData;->Message:Ljava/lang/String;

    invoke-virtual {v2, v5}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 80
    new-instance v0, Ljava/text/SimpleDateFormat;

    const-string v5, "MMM d, k:mm"

    invoke-direct {v0, v5}, Ljava/text/SimpleDateFormat;-><init>(Ljava/lang/String;)V

    .line 81
    .local v0, fmt:Ljava/text/SimpleDateFormat;
    const v5, 0x7f06003e

    invoke-virtual {v3, v5}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v2

    .end local v2           #tv:Landroid/widget/TextView;
    check-cast v2, Landroid/widget/TextView;

    .line 82
    .restart local v2       #tv:Landroid/widget/TextView;
    iget-object v5, v1, Lcom/openvehicles/OVMS/NotificationData;->Timestamp:Ljava/util/Date;

    invoke-virtual {v0, v5}, Ljava/text/SimpleDateFormat;->format(Ljava/util/Date;)Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v2, v5}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 86
    .end local v0           #fmt:Ljava/text/SimpleDateFormat;
    .end local v2           #tv:Landroid/widget/TextView;
    :cond_1
    return-object v3
.end method
