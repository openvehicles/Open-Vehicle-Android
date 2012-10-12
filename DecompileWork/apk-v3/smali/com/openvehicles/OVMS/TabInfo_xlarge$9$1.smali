.class Lcom/openvehicles/OVMS/TabInfo_xlarge$9$1;
.super Ljava/lang/Object;
.source "TabInfo_xlarge.java"

# interfaces
.implements Landroid/content/DialogInterface$OnCancelListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/openvehicles/OVMS/TabInfo_xlarge$9;->onStopTrackingTouch(Landroid/widget/SeekBar;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$1:Lcom/openvehicles/OVMS/TabInfo_xlarge$9;

.field private final synthetic val$seekBar:Landroid/widget/SeekBar;


# direct methods
.method constructor <init>(Lcom/openvehicles/OVMS/TabInfo_xlarge$9;Landroid/widget/SeekBar;)V
    .locals 0
    .parameter
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$9$1;->this$1:Lcom/openvehicles/OVMS/TabInfo_xlarge$9;

    iput-object p2, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$9$1;->val$seekBar:Landroid/widget/SeekBar;

    .line 162
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onCancel(Landroid/content/DialogInterface;)V
    .locals 2
    .parameter "arg0"

    .prologue
    .line 165
    iget-object v0, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$9$1;->val$seekBar:Landroid/widget/SeekBar;

    iget-object v1, p0, Lcom/openvehicles/OVMS/TabInfo_xlarge$9$1;->val$seekBar:Landroid/widget/SeekBar;

    invoke-virtual {v1}, Landroid/widget/SeekBar;->getMax()I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/widget/SeekBar;->setProgress(I)V

    .line 166
    return-void
.end method
