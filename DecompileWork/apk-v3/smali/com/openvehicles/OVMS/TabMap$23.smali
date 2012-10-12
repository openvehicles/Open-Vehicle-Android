.class Lcom/openvehicles/OVMS/TabMap$23;
.super Ljava/lang/Object;
.source "TabMap.java"

# interfaces
.implements Lcom/openvehicles/OVMS/Utilities$OnGroupCarTappedListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/TabMap;->Refresh(Lcom/openvehicles/OVMS/CarData;Z)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/openvehicles/OVMS/TabMap;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/TabMap;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabMap$23;->this$0:Lcom/openvehicles/OVMS/TabMap;

    .line 1329
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public OnGroupCarTapped(Ljava/lang/String;)V
    .locals 1
    .parameter "GroupVehicleID"

    .prologue
    .line 1332
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabMap$23;->this$0:Lcom/openvehicles/OVMS/TabMap;

    #calls: Lcom/openvehicles/OVMS/TabMap;->showGroupCarPopup(Ljava/lang/String;)V
    invoke-static {v0, p1}, Lcom/openvehicles/OVMS/TabMap;->access$28(Lcom/openvehicles/OVMS/TabMap;Ljava/lang/String;)V

    .line 1333
    return-void
.end method
