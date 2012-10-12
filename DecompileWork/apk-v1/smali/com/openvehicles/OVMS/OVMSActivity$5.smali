.class Lcom/openvehicles/OVMS/OVMSActivity$5;
.super Ljava/lang/Object;
.source "OVMSActivity.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/openvehicles/OVMS/OVMSActivity;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/openvehicles/OVMS/OVMSActivity;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/OVMSActivity;)V
    .locals 0
    .parameter

    .prologue
    .line 254
    iput-object p1, p0, Lcom/openvehicles/OVMS/OVMSActivity$5;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 9

    .prologue
    const/4 v7, 0x0

    .line 257
    iget-object v4, p0, Lcom/openvehicles/OVMS/OVMSActivity$5;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->tcpTask:Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;
    invoke-static {v4}, Lcom/openvehicles/OVMS/OVMSActivity;->access$200(Lcom/openvehicles/OVMS/OVMSActivity;)Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;

    move-result-object v4

    if-nez v4, :cond_1

    .line 291
    :cond_0
    :goto_0
    return-void

    .line 260
    :cond_1
    iget-object v4, p0, Lcom/openvehicles/OVMS/OVMSActivity$5;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    const-string v5, "C2DM"

    invoke-virtual {v4, v5, v7}, Lcom/openvehicles/OVMS/OVMSActivity;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v2

    .line 261
    .local v2, settings:Landroid/content/SharedPreferences;
    const-string v4, "RegID"

    const-string v5, ""

    invoke-interface {v2, v4, v5}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 264
    .local v1, registrationID:Ljava/lang/String;
    const-string v4, "UUID"

    invoke-interface {v2, v4}, Landroid/content/SharedPreferences;->contains(Ljava/lang/String;)Z

    move-result v4

    if-nez v4, :cond_3

    .line 266
    invoke-static {}, Ljava/util/UUID;->randomUUID()Ljava/util/UUID;

    move-result-object v4

    invoke-virtual {v4}, Ljava/util/UUID;->toString()Ljava/lang/String;

    move-result-object v3

    .line 267
    .local v3, uuid:Ljava/lang/String;
    iget-object v4, p0, Lcom/openvehicles/OVMS/OVMSActivity$5;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    const-string v5, "C2DM"

    invoke-virtual {v4, v5, v7}, Lcom/openvehicles/OVMS/OVMSActivity;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v4

    invoke-interface {v4}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    .line 269
    .local v0, editor:Landroid/content/SharedPreferences$Editor;
    const-string v4, "UUID"

    invoke-interface {v0, v4, v3}, Landroid/content/SharedPreferences$Editor;->putString(Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    .line 270
    invoke-interface {v0}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 272
    const-string v4, "OVMS"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "Generated New C2DM UUID: "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 280
    .end local v0           #editor:Landroid/content/SharedPreferences$Editor;
    :goto_1
    invoke-virtual {v1}, Ljava/lang/String;->length()I

    move-result v4

    if-eqz v4, :cond_2

    iget-object v4, p0, Lcom/openvehicles/OVMS/OVMSActivity$5;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->tcpTask:Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;
    invoke-static {v4}, Lcom/openvehicles/OVMS/OVMSActivity;->access$200(Lcom/openvehicles/OVMS/OVMSActivity;)Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;

    move-result-object v4

    const-string v5, "MP-0 p%s,c2dm,production,%s,%s,%s"

    const/4 v6, 0x4

    new-array v6, v6, [Ljava/lang/Object;

    aput-object v3, v6, v7

    const/4 v7, 0x1

    iget-object v8, p0, Lcom/openvehicles/OVMS/OVMSActivity$5;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v8}, Lcom/openvehicles/OVMS/OVMSActivity;->access$500(Lcom/openvehicles/OVMS/OVMSActivity;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v8

    iget-object v8, v8, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    aput-object v8, v6, v7

    const/4 v7, 0x2

    iget-object v8, p0, Lcom/openvehicles/OVMS/OVMSActivity$5;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v8}, Lcom/openvehicles/OVMS/OVMSActivity;->access$500(Lcom/openvehicles/OVMS/OVMSActivity;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v8

    iget-object v8, v8, Lcom/openvehicles/OVMS/CarData;->CarPass:Ljava/lang/String;

    aput-object v8, v6, v7

    const/4 v7, 0x3

    aput-object v1, v6, v7

    invoke-static {v5, v6}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v4, v5}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->SendCommand(Ljava/lang/String;)Z

    move-result v4

    if-nez v4, :cond_0

    .line 287
    :cond_2
    const-string v4, "OVMS"

    const-string v5, "Reporting C2DM ID failed. Rescheduling."

    invoke-static {v4, v5}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 288
    iget-object v4, p0, Lcom/openvehicles/OVMS/OVMSActivity$5;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->c2dmReportTimerHandler:Landroid/os/Handler;
    invoke-static {v4}, Lcom/openvehicles/OVMS/OVMSActivity;->access$700(Lcom/openvehicles/OVMS/OVMSActivity;)Landroid/os/Handler;

    move-result-object v4

    iget-object v5, p0, Lcom/openvehicles/OVMS/OVMSActivity$5;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->reportC2DMRegistrationID:Ljava/lang/Runnable;
    invoke-static {v5}, Lcom/openvehicles/OVMS/OVMSActivity;->access$600(Lcom/openvehicles/OVMS/OVMSActivity;)Ljava/lang/Runnable;

    move-result-object v5

    const-wide/16 v6, 0x1388

    invoke-virtual {v4, v5, v6, v7}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    goto/16 :goto_0

    .line 274
    .end local v3           #uuid:Ljava/lang/String;
    :cond_3
    const-string v4, "UUID"

    const-string v5, ""

    invoke-interface {v2, v4, v5}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    .line 275
    .restart local v3       #uuid:Ljava/lang/String;
    const-string v4, "OVMS"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "Loaded Saved C2DM UUID: "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_1
.end method
