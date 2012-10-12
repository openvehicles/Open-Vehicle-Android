.class Lcom/openvehicles/OVMS/TabInfo$5;
.super Ljava/lang/Object;
.source "TabInfo.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/TabInfo;->initUI()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/openvehicles/OVMS/TabInfo;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/TabInfo;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabInfo$5;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    .line 87
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 5
    .parameter "arg0"

    .prologue
    .line 90
    const-string v1, "-"

    .line 91
    .local v1, lastReportDate:Ljava/lang/String;
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabInfo$5;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v2}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v2

    iget-object v2, v2, Lcom/openvehicles/OVMS/CarData;->Data_ParkedTime:Ljava/util/Date;

    if-eqz v2, :cond_0

    .line 92
    new-instance v2, Ljava/text/SimpleDateFormat;

    const-string v3, "MMM d, K:mm:ss a"

    invoke-direct {v2, v3}, Ljava/text/SimpleDateFormat;-><init>(Ljava/lang/String;)V

    .line 93
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabInfo$5;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v3}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v3

    iget-object v3, v3, Lcom/openvehicles/OVMS/CarData;->Data_ParkedTime:Ljava/util/Date;

    invoke-virtual {v2, v3}, Ljava/text/SimpleDateFormat;->format(Ljava/util/Date;)Ljava/lang/String;

    move-result-object v1

    .line 94
    new-instance v0, Landroid/app/AlertDialog$Builder;

    .line 95
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabInfo$5;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    .line 94
    invoke-direct {v0, v2}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 96
    .local v0, builder:Landroid/app/AlertDialog$Builder;
    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "Parked since: "

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v0, v2}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v2

    .line 97
    const/4 v3, 0x1

    invoke-virtual {v2, v3}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    move-result-object v2

    .line 98
    const-string v3, "Close"

    .line 99
    new-instance v4, Lcom/openvehicles/OVMS/TabInfo$5$1;

    invoke-direct {v4, p0}, Lcom/openvehicles/OVMS/TabInfo$5$1;-><init>(Lcom/openvehicles/OVMS/TabInfo$5;)V

    .line 98
    invoke-virtual {v2, v3, v4}, Landroid/app/AlertDialog$Builder;->setPositiveButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v2

    .line 105
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabInfo$5;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v3}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v3

    iget-object v3, v3, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    invoke-virtual {v2, v3}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    .line 106
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabInfo$5;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    invoke-virtual {v0}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v3

    #setter for: Lcom/openvehicles/OVMS/TabInfo;->lastUpdatedDialog:Landroid/app/AlertDialog;
    invoke-static {v2, v3}, Lcom/openvehicles/OVMS/TabInfo;->access$7(Lcom/openvehicles/OVMS/TabInfo;Landroid/app/AlertDialog;)V

    .line 107
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabInfo$5;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->lastUpdatedDialog:Landroid/app/AlertDialog;
    invoke-static {v2}, Lcom/openvehicles/OVMS/TabInfo;->access$5(Lcom/openvehicles/OVMS/TabInfo;)Landroid/app/AlertDialog;

    move-result-object v2

    invoke-virtual {v2}, Landroid/app/AlertDialog;->show()V

    .line 110
    .end local v0           #builder:Landroid/app/AlertDialog$Builder;
    :cond_0
    return-void
.end method
