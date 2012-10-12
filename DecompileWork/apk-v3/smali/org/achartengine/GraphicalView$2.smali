.class Lorg/achartengine/GraphicalView$2;
.super Ljava/lang/Object;
.source "GraphicalView.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lorg/achartengine/GraphicalView;->repaint(IIII)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lorg/achartengine/GraphicalView;

.field final synthetic val$bottom:I

.field final synthetic val$left:I

.field final synthetic val$right:I

.field final synthetic val$top:I


# direct methods
.method constructor <init>(Lorg/achartengine/GraphicalView;IIII)V
    .locals 0
    .parameter
    .parameter
    .parameter
    .parameter
    .parameter

    .prologue
    .line 311
    iput-object p1, p0, Lorg/achartengine/GraphicalView$2;->this$0:Lorg/achartengine/GraphicalView;

    iput p2, p0, Lorg/achartengine/GraphicalView$2;->val$left:I

    iput p3, p0, Lorg/achartengine/GraphicalView$2;->val$top:I

    iput p4, p0, Lorg/achartengine/GraphicalView$2;->val$right:I

    iput p5, p0, Lorg/achartengine/GraphicalView$2;->val$bottom:I

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 5

    .prologue
    .line 313
    iget-object v0, p0, Lorg/achartengine/GraphicalView$2;->this$0:Lorg/achartengine/GraphicalView;

    iget v1, p0, Lorg/achartengine/GraphicalView$2;->val$left:I

    iget v2, p0, Lorg/achartengine/GraphicalView$2;->val$top:I

    iget v3, p0, Lorg/achartengine/GraphicalView$2;->val$right:I

    iget v4, p0, Lorg/achartengine/GraphicalView$2;->val$bottom:I

    invoke-virtual {v0, v1, v2, v3, v4}, Lorg/achartengine/GraphicalView;->invalidate(IIII)V

    .line 314
    return-void
.end method
