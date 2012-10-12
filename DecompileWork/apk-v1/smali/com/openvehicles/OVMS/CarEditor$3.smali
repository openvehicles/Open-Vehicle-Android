.class Lcom/openvehicles/OVMS/CarEditor$3;
.super Ljava/lang/Object;
.source "CarEditor.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/CarEditor;->onCreate(Landroid/os/Bundle;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/openvehicles/OVMS/CarEditor;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/CarEditor;)V
    .locals 0
    .parameter

    .prologue
    .line 90
    iput-object p1, p0, Lcom/openvehicles/OVMS/CarEditor$3;->this$0:Lcom/openvehicles/OVMS/CarEditor;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 9
    .parameter "arg0"

    .prologue
    const/4 v8, 0x1

    const/4 v7, 0x0

    .line 93
    iget-object v2, p0, Lcom/openvehicles/OVMS/CarEditor$3;->this$0:Lcom/openvehicles/OVMS/CarEditor;

    const/high16 v3, 0x7f06

    invoke-virtual {v2, v3}, Lcom/openvehicles/OVMS/CarEditor;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/TextView;

    .line 94
    .local v1, tv:Landroid/widget/TextView;
    invoke-virtual {v1}, Landroid/widget/TextView;->getText()Ljava/lang/CharSequence;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v0

    .line 96
    .local v0, newVehicleID:Ljava/lang/String;
    const-string v2, "OVMS"

    const-string v3, "newVehicleID %s, originalVehicleID %s, duplicated %s"

    const/4 v4, 0x3

    new-array v4, v4, [Ljava/lang/Object;

    aput-object v0, v4, v7

    iget-object v5, p0, Lcom/openvehicles/OVMS/CarEditor$3;->this$0:Lcom/openvehicles/OVMS/CarEditor;

    #getter for: Lcom/openvehicles/OVMS/CarEditor;->originalVehicleID:Ljava/lang/String;
    invoke-static {v5}, Lcom/openvehicles/OVMS/CarEditor;->access$100(Lcom/openvehicles/OVMS/CarEditor;)Ljava/lang/String;

    move-result-object v5

    aput-object v5, v4, v8

    const/4 v5, 0x2

    iget-object v6, p0, Lcom/openvehicles/OVMS/CarEditor$3;->this$0:Lcom/openvehicles/OVMS/CarEditor;

    #getter for: Lcom/openvehicles/OVMS/CarEditor;->existingVehicleIDs:Ljava/util/ArrayList;
    invoke-static {v6}, Lcom/openvehicles/OVMS/CarEditor;->access$200(Lcom/openvehicles/OVMS/CarEditor;)Ljava/util/ArrayList;

    move-result-object v6

    invoke-virtual {v6, v0}, Ljava/util/ArrayList;->contains(Ljava/lang/Object;)Z

    move-result v6

    invoke-static {v6}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v6

    aput-object v6, v4, v5

    invoke-static {v3, v4}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 97
    iget-object v2, p0, Lcom/openvehicles/OVMS/CarEditor$3;->this$0:Lcom/openvehicles/OVMS/CarEditor;

    #getter for: Lcom/openvehicles/OVMS/CarEditor;->originalVehicleID:Ljava/lang/String;
    invoke-static {v2}, Lcom/openvehicles/OVMS/CarEditor;->access$100(Lcom/openvehicles/OVMS/CarEditor;)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v0, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-nez v2, :cond_0

    iget-object v2, p0, Lcom/openvehicles/OVMS/CarEditor$3;->this$0:Lcom/openvehicles/OVMS/CarEditor;

    #getter for: Lcom/openvehicles/OVMS/CarEditor;->existingVehicleIDs:Ljava/util/ArrayList;
    invoke-static {v2}, Lcom/openvehicles/OVMS/CarEditor;->access$200(Lcom/openvehicles/OVMS/CarEditor;)Ljava/util/ArrayList;

    move-result-object v2

    invoke-virtual {v2, v0}, Ljava/util/ArrayList;->contains(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_0

    .line 99
    iget-object v2, p0, Lcom/openvehicles/OVMS/CarEditor$3;->this$0:Lcom/openvehicles/OVMS/CarEditor;

    invoke-virtual {v2}, Lcom/openvehicles/OVMS/CarEditor;->getBaseContext()Landroid/content/Context;

    move-result-object v2

    const-string v3, "Vehicle ID %s is already registered - Cancelling Save"

    new-array v4, v8, [Ljava/lang/Object;

    aput-object v0, v4, v7

    invoke-static {v3, v4}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v3

    const/16 v4, 0x3e8

    invoke-static {v2, v3, v4}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v2

    invoke-virtual {v2}, Landroid/widget/Toast;->show()V

    .line 104
    :goto_0
    return-void

    .line 103
    :cond_0
    iget-object v2, p0, Lcom/openvehicles/OVMS/CarEditor$3;->this$0:Lcom/openvehicles/OVMS/CarEditor;

    const-string v3, "SAVE"

    #calls: Lcom/openvehicles/OVMS/CarEditor;->closeEditor(Ljava/lang/String;)V
    invoke-static {v2, v3}, Lcom/openvehicles/OVMS/CarEditor;->access$000(Lcom/openvehicles/OVMS/CarEditor;Ljava/lang/String;)V

    goto :goto_0
.end method
