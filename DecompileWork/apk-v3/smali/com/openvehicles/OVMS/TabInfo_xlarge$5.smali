.class Lcom/openvehicles/OVMS/TabInfo_xlarge$5;
.super Ljava/lang/Object;
.source "TabInfo_xlarge.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/TabInfo_xlarge;->initInfoUI()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/TabInfo_xlarge;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$5;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    .line 58
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 5
    .parameter "arg0"

    .prologue
    .line 61
    const-string v1, "-"

    .line 62
    .local v1, lastReportDate:Ljava/lang/String;
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$5;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v2}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$6(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v2

    iget-object v2, v2, Lcom/openvehicles/OVMS/CarData;->Data_LastCarUpdate:Ljava/util/Date;

    if-eqz v2, :cond_0

    .line 63
    new-instance v2, Ljava/text/SimpleDateFormat;

    const-string v3, "MMM d, K:mm:ss a"

    invoke-direct {v2, v3}, Ljava/text/SimpleDateFormat;-><init>(Ljava/lang/String;)V

    .line 64
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$5;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v3}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$6(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v3

    iget-object v3, v3, Lcom/openvehicles/OVMS/CarData;->Data_LastCarUpdate:Ljava/util/Date;

    invoke-virtual {v2, v3}, Ljava/text/SimpleDateFormat;->format(Ljava/util/Date;)Ljava/lang/String;

    move-result-object v1

    .line 65
    new-instance v0, Landroid/app/AlertDialog$Builder;

    .line 66
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$5;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    .line 65
    invoke-direct {v0, v2}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 67
    .local v0, builder:Landroid/app/AlertDialog$Builder;
    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "Last update: "

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v0, v2}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v2

    .line 68
    const/4 v3, 0x1

    invoke-virtual {v2, v3}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    move-result-object v2

    .line 69
    const-string v3, "Close"

    .line 70
    new-instance v4, Lcom/openvehicles/OVMS/TabInfo_xlarge$5$1;

    invoke-direct {v4, p0}, Lcom/openvehicles/OVMS/TabInfo_xlarge$5$1;-><init>(Lcom/openvehicles/OVMS/TabInfo_xlarge$5;)V

    .line 69
    invoke-virtual {v2, v3, v4}, Landroid/app/AlertDialog$Builder;->setPositiveButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v2

    .line 76
    iget-object v3, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$5;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v3}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$6(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v3

    iget-object v3, v3, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    invoke-virtual {v2, v3}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    .line 77
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$5;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    invoke-virtual {v0}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v3

    #setter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->lastUpdatedDialog:Landroid/app/AlertDialog;
    invoke-static {v2, v3}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$14(Lcom/openvehicles/OVMS/TabInfo_xlarge;Landroid/app/AlertDialog;)V

    .line 78
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$5;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->lastUpdatedDialog:Landroid/app/AlertDialog;
    invoke-static {v2}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$15(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Landroid/app/AlertDialog;

    move-result-object v2

    invoke-virtual {v2}, Landroid/app/AlertDialog;->show()V

    .line 81
    .end local v0           #builder:Landroid/app/AlertDialog$Builder;
    :cond_0
    return-void
.end method
