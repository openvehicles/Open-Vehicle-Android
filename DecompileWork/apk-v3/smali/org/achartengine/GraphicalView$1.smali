.class Lorg/achartengine/GraphicalView$1;
.super Ljava/lang/Object;
.source "GraphicalView.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lorg/achartengine/GraphicalView;->repaint()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lorg/achartengine/GraphicalView;


# direct methods
.method constructor <init>(Lorg/achartengine/GraphicalView;)V
    .locals 0
    .parameter

    .prologue
    .line 295
    iput-object p1, p0, Lorg/achartengine/GraphicalView$1;->this$0:Lorg/achartengine/GraphicalView;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 1

    .prologue
    .line 297
    iget-object v0, p0, Lorg/achartengine/GraphicalView$1;->this$0:Lorg/achartengine/GraphicalView;

    invoke-virtual {v0}, Lorg/achartengine/GraphicalView;->invalidate()V

    .line 298
    return-void
.end method
