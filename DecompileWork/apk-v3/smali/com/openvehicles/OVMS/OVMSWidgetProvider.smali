.class public Lcom/openvehicles/OVMS/OVMSWidgetProvider;
.super Landroid/appwidget/AppWidgetProvider;
.source "OVMSWidgetProvider.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    .prologue
    .line 9
    invoke-direct {p0}, Landroid/appwidget/AppWidgetProvider;-><init>()V

    return-void
.end method


# virtual methods
.method public onUpdate(Landroid/content/Context;Landroid/appwidget/AppWidgetManager;[I)V
    .locals 6
    .parameter "context"
    .parameter "appWidgetManager"
    .parameter "appWidgetIds"

    .prologue
    .line 13
    array-length v0, p3

    .line 14
    .local v0, N:I
    const-string v2, "OVMSWidget"

    .line 15
    new-instance v3, Ljava/lang/StringBuilder;

    const-string v4, "Updating widgets "

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const/4 v4, 0x1

    new-array v4, v4, [[I

    const/4 v5, 0x0

    aput-object p3, v4, v5

    invoke-static {v4}, Ljava/util/Arrays;->asList([Ljava/lang/Object;)Ljava/util/List;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    .line 14
    invoke-static {v2, v3}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 19
    const/4 v1, 0x0

    .local v1, i:I
    :goto_0
    if-lt v1, v0, :cond_0

    .line 22
    return-void

    .line 20
    :cond_0
    aget v2, p3, v1

    invoke-static {p1, p2, v2}, Lcom/openvehicles/OVMS/OVMSWidgets;->UpdateWidget(Landroid/content/Context;Landroid/appwidget/AppWidgetManager;I)V

    .line 19
    add-int/lit8 v1, v1, 0x1

    goto :goto_0
.end method
