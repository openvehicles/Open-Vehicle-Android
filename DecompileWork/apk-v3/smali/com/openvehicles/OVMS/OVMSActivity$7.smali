.class Lcom/openvehicles/OVMS/OVMSActivity$7;
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
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/OVMSActivity$7;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    .line 518
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 8

    .prologue
    const/4 v6, 0x0

    .line 521
    iget-object v4, p0, Lcom/openvehicles/OVMS/OVMSActivity$7;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->tcpTask:Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;
    invoke-static {v4}, Lcom/openvehicles/OVMS/OVMSActivity;->access$7(Lcom/openvehicles/OVMS/OVMSActivity;)Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;

    move-result-object v4

    if-nez v4, :cond_1

    .line 557
    :cond_0
    :goto_0
    return-void

    .line 524
    :cond_1
    iget-object v4, p0, Lcom/openvehicles/OVMS/OVMSActivity$7;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    const-string v5, "C2DM"

    invoke-virtual {v4, v5, v6}, Lcom/openvehicles/OVMS/OVMSActivity;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v3

    .line 525
    .local v3, settings:Landroid/content/SharedPreferences;
    const-string v4, "RegID"

    const-string v5, ""

    invoke-interface {v3, v4, v5}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 528
    .local v1, c2DMRegistrationID:Ljava/lang/String;
    const-string v4, "UUID"

    invoke-interface {v3, v4}, Landroid/content/SharedPreferences;->contains(Ljava/lang/String;)Z

    move-result v4

    if-nez v4, :cond_2

    .line 530
    invoke-static {}, Ljava/util/UUID;->randomUUID()Ljava/util/UUID;

    move-result-object v4

    invoke-virtual {v4}, Ljava/util/UUID;->toString()Ljava/lang/String;

    move-result-object v0

    .line 531
    .local v0, appID:Ljava/lang/String;
    iget-object v4, p0, Lcom/openvehicles/OVMS/OVMSActivity$7;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    const-string v5, "C2DM"

    invoke-virtual {v4, v5, v6}, Lcom/openvehicles/OVMS/OVMSActivity;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v4

    .line 532
    invoke-interface {v4}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v2

    .line 533
    .local v2, editor:Landroid/content/SharedPreferences$Editor;
    const-string v4, "UUID"

    invoke-interface {v2, v4, v0}, Landroid/content/SharedPreferences$Editor;->putString(Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    .line 534
    invoke-interface {v2}, Landroid/content/SharedPreferences$Editor;->commit()Z

    .line 536
    const-string v4, "OVMS"

    new-instance v5, Ljava/lang/StringBuilder;

    const-string v6, "Generated New App ID: "

    invoke-direct {v5, v6}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v5, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 544
    .end local v2           #editor:Landroid/content/SharedPreferences$Editor;
    :goto_1
    invoke-virtual {v1}, Ljava/lang/String;->length()I

    move-result v4

    if-nez v4, :cond_3

    .line 546
    const-string v4, "C2DM"

    const-string v5, "C2DM registration ID not found. Rescheduling."

    invoke-static {v4, v5}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 547
    iget-object v4, p0, Lcom/openvehicles/OVMS/OVMSActivity$7;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->c2dmReportTimerHandler:Landroid/os/Handler;
    invoke-static {v4}, Lcom/openvehicles/OVMS/OVMSActivity;->access$10(Lcom/openvehicles/OVMS/OVMSActivity;)Landroid/os/Handler;

    move-result-object v4

    iget-object v5, p0, Lcom/openvehicles/OVMS/OVMSActivity$7;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->reportC2DMRegistrationID:Ljava/lang/Runnable;
    invoke-static {v5}, Lcom/openvehicles/OVMS/OVMSActivity;->access$11(Lcom/openvehicles/OVMS/OVMSActivity;)Ljava/lang/Runnable;

    move-result-object v5

    .line 548
    const-wide/16 v6, 0x3a98

    .line 547
    invoke-virtual {v4, v5, v6, v7}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    goto :goto_0

    .line 538
    .end local v0           #appID:Ljava/lang/String;
    :cond_2
    const-string v4, "UUID"

    const-string v5, ""

    invoke-interface {v3, v4, v5}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 539
    .restart local v0       #appID:Ljava/lang/String;
    const-string v4, "OVMS"

    new-instance v5, Ljava/lang/StringBuilder;

    const-string v6, "Loaded Saved App ID: "

    invoke-direct {v5, v6}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v5, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_1

    .line 549
    :cond_3
    iget-object v4, p0, Lcom/openvehicles/OVMS/OVMSActivity$7;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    .line 550
    iget-object v5, p0, Lcom/openvehicles/OVMS/OVMSActivity$7;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v5}, Lcom/openvehicles/OVMS/OVMSActivity;->access$0(Lcom/openvehicles/OVMS/OVMSActivity;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v5

    iget-object v5, v5, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    .line 551
    iget-object v6, p0, Lcom/openvehicles/OVMS/OVMSActivity$7;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v6}, Lcom/openvehicles/OVMS/OVMSActivity;->access$0(Lcom/openvehicles/OVMS/OVMSActivity;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v6

    iget-object v6, v6, Lcom/openvehicles/OVMS/CarData;->NetPass:Ljava/lang/String;

    invoke-virtual {v1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v7

    .line 550
    invoke-static {v0, v5, v6, v7}, Lcom/openvehicles/OVMS/ServerCommands;->SUBSCRIBE_PUSH_NOTIFICATIONS(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v5

    .line 549
    invoke-virtual {v4, v5}, Lcom/openvehicles/OVMS/OVMSActivity;->SendServerCommand(Ljava/lang/String;)Z

    move-result v4

    if-nez v4, :cond_0

    .line 553
    const-string v4, "OVMS"

    const-string v5, "Reporting C2DM ID failed. Rescheduling."

    invoke-static {v4, v5}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 554
    iget-object v4, p0, Lcom/openvehicles/OVMS/OVMSActivity$7;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->c2dmReportTimerHandler:Landroid/os/Handler;
    invoke-static {v4}, Lcom/openvehicles/OVMS/OVMSActivity;->access$10(Lcom/openvehicles/OVMS/OVMSActivity;)Landroid/os/Handler;

    move-result-object v4

    iget-object v5, p0, Lcom/openvehicles/OVMS/OVMSActivity$7;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->reportC2DMRegistrationID:Ljava/lang/Runnable;
    invoke-static {v5}, Lcom/openvehicles/OVMS/OVMSActivity;->access$11(Lcom/openvehicles/OVMS/OVMSActivity;)Ljava/lang/Runnable;

    move-result-object v5

    .line 555
    const-wide/16 v6, 0x1388

    .line 554
    invoke-virtual {v4, v5, v6, v7}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    goto/16 :goto_0
.end method
