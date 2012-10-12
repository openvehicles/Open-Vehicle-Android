.class Lcom/openvehicles/OVMS/TabInfo$8;
.super Ljava/lang/Object;
.source "TabInfo.java"

# interfaces
.implements Landroid/widget/SeekBar$OnSeekBarChangeListener;


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
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabInfo$8;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    .line 135
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onProgressChanged(Landroid/widget/SeekBar;IZ)V
    .locals 0
    .parameter "seekBar"
    .parameter "progress"
    .parameter "fromUser"

    .prologue
    .line 138
    invoke-virtual {p1, p2}, Landroid/widget/SeekBar;->setProgress(I)V

    .line 139
    return-void
.end method

.method public onStartTrackingTouch(Landroid/widget/SeekBar;)V
    .locals 0
    .parameter "seekBar"

    .prologue
    .line 143
    return-void
.end method

.method public onStopTrackingTouch(Landroid/widget/SeekBar;)V
    .locals 5
    .parameter "seekBar"

    .prologue
    const/4 v4, 0x0

    const/4 v1, 0x0

    .line 146
    invoke-virtual {p1}, Landroid/widget/SeekBar;->getProgress()I

    move-result v2

    const/16 v3, 0x19

    if-ge v2, v3, :cond_1

    .line 149
    invoke-virtual {p1, v1}, Landroid/widget/SeekBar;->setProgress(I)V

    .line 150
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabInfo$8;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v2}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v2

    iget-boolean v2, v2, Lcom/openvehicles/OVMS/CarData;->Data_Charging:Z

    if-eqz v2, :cond_0

    .line 151
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabInfo$8;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    const-string v3, "Already charging..."

    invoke-static {v2, v3, v1}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v1

    invoke-virtual {v1}, Landroid/widget/Toast;->show()V

    .line 186
    :goto_0
    return-void

    .line 155
    :cond_0
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabInfo$8;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo$8;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    .line 156
    invoke-virtual {v1}, Lcom/openvehicles/OVMS/TabInfo;->getParent()Landroid/app/Activity;

    move-result-object v1

    check-cast v1, Lcom/openvehicles/OVMS/OVMSActivity;

    .line 155
    invoke-static {v2, v1, v4}, Lcom/openvehicles/OVMS/ServerCommands;->StartCharge(Landroid/content/Context;Lcom/openvehicles/OVMS/OVMSActivity;Landroid/widget/Toast;)Landroid/app/AlertDialog;

    move-result-object v0

    .line 158
    .local v0, dialog:Landroid/app/AlertDialog;
    new-instance v1, Lcom/openvehicles/OVMS/TabInfo$8$1;

    invoke-direct {v1, p0, p1}, Lcom/openvehicles/OVMS/TabInfo$8$1;-><init>(Lcom/openvehicles/OVMS/TabInfo$8;Landroid/widget/SeekBar;)V

    invoke-virtual {v0, v1}, Landroid/app/AlertDialog;->setOnCancelListener(Landroid/content/DialogInterface$OnCancelListener;)V

    goto :goto_0

    .line 164
    .end local v0           #dialog:Landroid/app/AlertDialog;
    :cond_1
    invoke-virtual {p1}, Landroid/widget/SeekBar;->getProgress()I

    move-result v2

    invoke-virtual {p1}, Landroid/widget/SeekBar;->getMax()I

    move-result v3

    add-int/lit8 v3, v3, -0x19

    if-le v2, v3, :cond_3

    .line 167
    invoke-virtual {p1}, Landroid/widget/SeekBar;->getMax()I

    move-result v2

    invoke-virtual {p1, v2}, Landroid/widget/SeekBar;->setProgress(I)V

    .line 168
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabInfo$8;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v2}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v2

    iget-boolean v2, v2, Lcom/openvehicles/OVMS/CarData;->Data_Charging:Z

    if-nez v2, :cond_2

    .line 169
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabInfo$8;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    const-string v3, "Already stopped..."

    invoke-static {v2, v3, v1}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v1

    invoke-virtual {v1}, Landroid/widget/Toast;->show()V

    goto :goto_0

    .line 172
    :cond_2
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabInfo$8;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo$8;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    .line 173
    invoke-virtual {v1}, Lcom/openvehicles/OVMS/TabInfo;->getParent()Landroid/app/Activity;

    move-result-object v1

    check-cast v1, Lcom/openvehicles/OVMS/OVMSActivity;

    .line 172
    invoke-static {v2, v1, v4}, Lcom/openvehicles/OVMS/ServerCommands;->StopCharge(Landroid/content/Context;Lcom/openvehicles/OVMS/OVMSActivity;Landroid/widget/Toast;)Landroid/app/AlertDialog;

    move-result-object v0

    .line 175
    .restart local v0       #dialog:Landroid/app/AlertDialog;
    new-instance v1, Lcom/openvehicles/OVMS/TabInfo$8$2;

    invoke-direct {v1, p0, p1}, Lcom/openvehicles/OVMS/TabInfo$8$2;-><init>(Lcom/openvehicles/OVMS/TabInfo$8;Landroid/widget/SeekBar;)V

    invoke-virtual {v0, v1}, Landroid/app/AlertDialog;->setOnCancelListener(Landroid/content/DialogInterface$OnCancelListener;)V

    goto :goto_0

    .line 185
    .end local v0           #dialog:Landroid/app/AlertDialog;
    :cond_3
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabInfo$8;->this$0:Lcom/openvehicles/OVMS/TabInfo;

    #getter for: Lcom/openvehicles/OVMS/TabInfo;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v2}, Lcom/openvehicles/OVMS/TabInfo;->access$3(Lcom/openvehicles/OVMS/TabInfo;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v2

    iget-boolean v2, v2, Lcom/openvehicles/OVMS/CarData;->Data_Charging:Z

    if-eqz v2, :cond_4

    :goto_1
    invoke-virtual {p1, v1}, Landroid/widget/SeekBar;->setProgress(I)V

    goto :goto_0

    :cond_4
    const/16 v1, 0x64

    goto :goto_1
.end method
