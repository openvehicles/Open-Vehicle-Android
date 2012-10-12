.class Lcom/openvehicles/OVMS/TabInfo_xlarge$9;
.super Ljava/lang/Object;
.source "TabInfo_xlarge.java"

# interfaces
.implements Landroid/widget/SeekBar$OnSeekBarChangeListener;


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
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$9;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

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
    .line 139
    invoke-virtual {p1, p2}, Landroid/widget/SeekBar;->setProgress(I)V

    .line 140
    return-void
.end method

.method public onStartTrackingTouch(Landroid/widget/SeekBar;)V
    .locals 0
    .parameter "seekBar"

    .prologue
    .line 144
    return-void
.end method

.method public onStopTrackingTouch(Landroid/widget/SeekBar;)V
    .locals 5
    .parameter "seekBar"

    .prologue
    const/4 v4, 0x0

    const/4 v1, 0x0

    .line 147
    invoke-virtual {p1}, Landroid/widget/SeekBar;->getProgress()I

    move-result v2

    const/16 v3, 0x19

    if-ge v2, v3, :cond_1

    .line 149
    invoke-virtual {p1, v1}, Landroid/widget/SeekBar;->setProgress(I)V

    .line 150
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$9;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v2}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$6(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v2

    iget-boolean v2, v2, Lcom/openvehicles/OVMS/CarData;->Data_Charging:Z

    if-eqz v2, :cond_0

    .line 151
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$9;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    .line 152
    const-string v3, "Already charging..."

    .line 151
    invoke-static {v2, v3, v1}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v1

    .line 153
    invoke-virtual {v1}, Landroid/widget/Toast;->show()V

    .line 192
    :goto_0
    return-void

    .line 158
    :cond_0
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$9;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    .line 159
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$9;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    invoke-virtual {v1}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->getParent()Landroid/app/Activity;

    move-result-object v1

    check-cast v1, Lcom/openvehicles/OVMS/OVMSActivity;

    .line 157
    invoke-static {v2, v1, v4}, Lcom/openvehicles/OVMS/ServerCommands;->StartCharge(Landroid/content/Context;Lcom/openvehicles/OVMS/OVMSActivity;Landroid/widget/Toast;)Landroid/app/AlertDialog;

    move-result-object v0

    .line 162
    .local v0, dialog:Landroid/app/AlertDialog;
    new-instance v1, Lcom/openvehicles/OVMS/TabInfo_xlarge$9$1;

    invoke-direct {v1, p0, p1}, Lcom/openvehicles/OVMS/TabInfo_xlarge$9$1;-><init>(Lcom/openvehicles/OVMS/TabInfo_xlarge$9;Landroid/widget/SeekBar;)V

    invoke-virtual {v0, v1}, Landroid/app/AlertDialog;->setOnCancelListener(Landroid/content/DialogInterface$OnCancelListener;)V

    goto :goto_0

    .line 168
    .end local v0           #dialog:Landroid/app/AlertDialog;
    :cond_1
    invoke-virtual {p1}, Landroid/widget/SeekBar;->getProgress()I

    move-result v2

    invoke-virtual {p1}, Landroid/widget/SeekBar;->getMax()I

    move-result v3

    add-int/lit8 v3, v3, -0x19

    if-le v2, v3, :cond_3

    .line 170
    invoke-virtual {p1}, Landroid/widget/SeekBar;->getMax()I

    move-result v2

    invoke-virtual {p1, v2}, Landroid/widget/SeekBar;->setProgress(I)V

    .line 171
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$9;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v2}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$6(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v2

    iget-boolean v2, v2, Lcom/openvehicles/OVMS/CarData;->Data_Charging:Z

    if-nez v2, :cond_2

    .line 172
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$9;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    .line 173
    const-string v3, "Already stopped..."

    .line 172
    invoke-static {v2, v3, v1}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v1

    .line 174
    invoke-virtual {v1}, Landroid/widget/Toast;->show()V

    goto :goto_0

    .line 178
    :cond_2
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$9;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    .line 179
    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$9;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    invoke-virtual {v1}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->getParent()Landroid/app/Activity;

    move-result-object v1

    check-cast v1, Lcom/openvehicles/OVMS/OVMSActivity;

    .line 177
    invoke-static {v2, v1, v4}, Lcom/openvehicles/OVMS/ServerCommands;->StopCharge(Landroid/content/Context;Lcom/openvehicles/OVMS/OVMSActivity;Landroid/widget/Toast;)Landroid/app/AlertDialog;

    move-result-object v0

    .line 182
    .restart local v0       #dialog:Landroid/app/AlertDialog;
    new-instance v1, Lcom/openvehicles/OVMS/TabInfo_xlarge$9$2;

    invoke-direct {v1, p0, p1}, Lcom/openvehicles/OVMS/TabInfo_xlarge$9$2;-><init>(Lcom/openvehicles/OVMS/TabInfo_xlarge$9;Landroid/widget/SeekBar;)V

    invoke-virtual {v0, v1}, Landroid/app/AlertDialog;->setOnCancelListener(Landroid/content/DialogInterface$OnCancelListener;)V

    goto :goto_0

    .line 191
    .end local v0           #dialog:Landroid/app/AlertDialog;
    :cond_3
    iget-object v2, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$9;->this$0:Lcom/openvehicles/OVMS/TabInfo_xlarge;

    #getter for: Lcom/openvehicles/OVMS/TabInfo_xlarge;->data:Lcom/openvehicles/OVMS/CarData;
    invoke-static {v2}, Lcom/openvehicles/OVMS/TabInfo_xlarge;->access$6(Lcom/openvehicles/OVMS/TabInfo_xlarge;)Lcom/openvehicles/OVMS/CarData;

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
