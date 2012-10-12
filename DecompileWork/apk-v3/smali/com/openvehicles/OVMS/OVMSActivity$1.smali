.class Lcom/openvehicles/OVMS/OVMSActivity$1;
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
    iput-object p1, p0, Lcom/openvehicles/OVMS/OVMSActivity$1;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    .line 283
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 5

    .prologue
    .line 285
    iget-object v2, p0, Lcom/openvehicles/OVMS/OVMSActivity$1;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v2}, Lcom/openvehicles/OVMS/OVMSActivity;->getLocalActivityManager()Landroid/app/LocalActivityManager;

    move-result-object v2

    invoke-virtual {v2}, Landroid/app/LocalActivityManager;->getCurrentId()Ljava/lang/String;

    move-result-object v2

    .line 286
    invoke-virtual {v2}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v0

    .line 288
    .local v0, currentActivityId:Ljava/lang/String;
    const-string v2, "Tab"

    new-instance v3, Ljava/lang/StringBuilder;

    const-string v4, "Tab recreate: "

    invoke-direct {v3, v4}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 290
    if-eqz v0, :cond_0

    .line 291
    iget-object v2, p0, Lcom/openvehicles/OVMS/OVMSActivity$1;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v2}, Lcom/openvehicles/OVMS/OVMSActivity;->getLocalActivityManager()Landroid/app/LocalActivityManager;

    move-result-object v2

    .line 292
    invoke-virtual {v2, v0}, Landroid/app/LocalActivityManager;->getActivity(Ljava/lang/String;)Landroid/app/Activity;

    move-result-object v2

    .line 291
    if-nez v2, :cond_1

    .line 311
    :cond_0
    :goto_0
    return-void

    .line 295
    :cond_1
    const-string v2, "tabInfo_xlarge"

    invoke-virtual {v0, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_2

    .line 296
    iget-object v2, p0, Lcom/openvehicles/OVMS/OVMSActivity$1;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v2}, Lcom/openvehicles/OVMS/OVMSActivity;->getLocalActivityManager()Landroid/app/LocalActivityManager;

    move-result-object v2

    invoke-virtual {v2, v0}, Landroid/app/LocalActivityManager;->getActivity(Ljava/lang/String;)Landroid/app/Activity;

    move-result-object v1

    check-cast v1, Lcom/openvehicles/OVMS/TabInfo_xlarge;

    .line 298
    .local v1, tab:Lcom/openvehicles/OVMS/TabInfo_xlarge;
    invoke-virtual {v1}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->OrientationChanged()V

    .line 299
    iget-object v2, p0, Lcom/openvehicles/OVMS/OVMSActivity$1;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v2}, Lcom/openvehicles/OVMS/OVMSActivity;->access$0(Lcom/openvehicles/OVMS/OVMSActivity;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v2

    iget-object v3, p0, Lcom/openvehicles/OVMS/OVMSActivity$1;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->isLoggedIn:Z
    invoke-static {v3}, Lcom/openvehicles/OVMS/OVMSActivity;->access$1(Lcom/openvehicles/OVMS/OVMSActivity;)Z

    move-result v3

    invoke-virtual {v1, v2, v3}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->Refresh(Lcom/openvehicles/OVMS/CarData;Z)V

    goto :goto_0

    .line 300
    .end local v1           #tab:Lcom/openvehicles/OVMS/TabInfo_xlarge;
    :cond_2
    const-string v2, "tabInfo"

    invoke-virtual {v0, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_3

    .line 301
    iget-object v2, p0, Lcom/openvehicles/OVMS/OVMSActivity$1;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v2}, Lcom/openvehicles/OVMS/OVMSActivity;->getLocalActivityManager()Landroid/app/LocalActivityManager;

    move-result-object v2

    invoke-virtual {v2, v0}, Landroid/app/LocalActivityManager;->getActivity(Ljava/lang/String;)Landroid/app/Activity;

    move-result-object v1

    check-cast v1, Lcom/openvehicles/OVMS/TabInfo;

    .line 303
    .local v1, tab:Lcom/openvehicles/OVMS/TabInfo;
    invoke-virtual {v1}, Lcom/openvehicles/OVMS/TabInfo;->OrientationChanged()V

    .line 304
    iget-object v2, p0, Lcom/openvehicles/OVMS/OVMSActivity$1;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v2}, Lcom/openvehicles/OVMS/OVMSActivity;->access$0(Lcom/openvehicles/OVMS/OVMSActivity;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v2

    iget-object v3, p0, Lcom/openvehicles/OVMS/OVMSActivity$1;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->isLoggedIn:Z
    invoke-static {v3}, Lcom/openvehicles/OVMS/OVMSActivity;->access$1(Lcom/openvehicles/OVMS/OVMSActivity;)Z

    move-result v3

    invoke-virtual {v1, v2, v3}, Lcom/openvehicles/OVMS/TabInfo;->Refresh(Lcom/openvehicles/OVMS/CarData;Z)V

    goto :goto_0

    .line 305
    .end local v1           #tab:Lcom/openvehicles/OVMS/TabInfo;
    :cond_3
    const-string v2, "tabCar"

    invoke-virtual {v0, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_0

    .line 306
    iget-object v2, p0, Lcom/openvehicles/OVMS/OVMSActivity$1;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v2}, Lcom/openvehicles/OVMS/OVMSActivity;->getLocalActivityManager()Landroid/app/LocalActivityManager;

    move-result-object v2

    invoke-virtual {v2, v0}, Landroid/app/LocalActivityManager;->getActivity(Ljava/lang/String;)Landroid/app/Activity;

    move-result-object v1

    check-cast v1, Lcom/openvehicles/OVMS/TabCar;

    .line 308
    .local v1, tab:Lcom/openvehicles/OVMS/TabCar;
    invoke-virtual {v1}, Lcom/openvehicles/OVMS/TabCar;->OrientationChanged()V

    .line 309
    iget-object v2, p0, Lcom/openvehicles/OVMS/OVMSActivity$1;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v2}, Lcom/openvehicles/OVMS/OVMSActivity;->access$0(Lcom/openvehicles/OVMS/OVMSActivity;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v2

    iget-object v3, p0, Lcom/openvehicles/OVMS/OVMSActivity$1;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->isLoggedIn:Z
    invoke-static {v3}, Lcom/openvehicles/OVMS/OVMSActivity;->access$1(Lcom/openvehicles/OVMS/OVMSActivity;)Z

    move-result v3

    invoke-virtual {v1, v2, v3}, Lcom/openvehicles/OVMS/TabCar;->Refresh(Lcom/openvehicles/OVMS/CarData;Z)V

    goto :goto_0
.end method
