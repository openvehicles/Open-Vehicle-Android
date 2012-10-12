.class public Lcom/openvehicles/OVMS/TransparentPanel;
.super Landroid/widget/LinearLayout;
.source "TransparentPanel.java"


# instance fields
.field private borderPaint:Landroid/graphics/Paint;

.field private innerPaint:Landroid/graphics/Paint;


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .locals 0
    .parameter "context"

    .prologue
    .line 21
    invoke-direct {p0, p1}, Landroid/widget/LinearLayout;-><init>(Landroid/content/Context;)V

    .line 22
    invoke-direct {p0}, Lcom/openvehicles/OVMS/TransparentPanel;->init()V

    .line 23
    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 0
    .parameter "context"
    .parameter "attrs"

    .prologue
    .line 16
    invoke-direct {p0, p1, p2}, Landroid/widget/LinearLayout;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    .line 17
    invoke-direct {p0}, Lcom/openvehicles/OVMS/TransparentPanel;->init()V

    .line 18
    return-void
.end method

.method private init()V
    .locals 5

    .prologue
    const/4 v4, 0x1

    const/16 v3, 0x4b

    const/16 v2, 0xff

    .line 26
    new-instance v0, Landroid/graphics/Paint;

    invoke-direct {v0}, Landroid/graphics/Paint;-><init>()V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TransparentPanel;->innerPaint:Landroid/graphics/Paint;

    .line 27
    iget-object v0, p0, Lcom/openvehicles/OVMS/TransparentPanel;->innerPaint:Landroid/graphics/Paint;

    const/16 v1, 0xe1

    invoke-virtual {v0, v1, v3, v3, v3}, Landroid/graphics/Paint;->setARGB(IIII)V

    .line 28
    iget-object v0, p0, Lcom/openvehicles/OVMS/TransparentPanel;->innerPaint:Landroid/graphics/Paint;

    invoke-virtual {v0, v4}, Landroid/graphics/Paint;->setAntiAlias(Z)V

    .line 30
    new-instance v0, Landroid/graphics/Paint;

    invoke-direct {v0}, Landroid/graphics/Paint;-><init>()V

    iput-object v0, p0, Lcom/openvehicles/OVMS/TransparentPanel;->borderPaint:Landroid/graphics/Paint;

    .line 31
    iget-object v0, p0, Lcom/openvehicles/OVMS/TransparentPanel;->borderPaint:Landroid/graphics/Paint;

    invoke-virtual {v0, v2, v2, v2, v2}, Landroid/graphics/Paint;->setARGB(IIII)V

    .line 32
    iget-object v0, p0, Lcom/openvehicles/OVMS/TransparentPanel;->borderPaint:Landroid/graphics/Paint;

    invoke-virtual {v0, v4}, Landroid/graphics/Paint;->setAntiAlias(Z)V

    .line 33
    iget-object v0, p0, Lcom/openvehicles/OVMS/TransparentPanel;->borderPaint:Landroid/graphics/Paint;

    sget-object v1, Landroid/graphics/Paint$Style;->STROKE:Landroid/graphics/Paint$Style;

    invoke-virtual {v0, v1}, Landroid/graphics/Paint;->setStyle(Landroid/graphics/Paint$Style;)V

    .line 34
    iget-object v0, p0, Lcom/openvehicles/OVMS/TransparentPanel;->borderPaint:Landroid/graphics/Paint;

    const/high16 v1, 0x4000

    invoke-virtual {v0, v1}, Landroid/graphics/Paint;->setStrokeWidth(F)V

    .line 35
    return-void
.end method


# virtual methods
.method protected dispatchDraw(Landroid/graphics/Canvas;)V
    .locals 5
    .parameter "canvas"

    .prologue
    const/4 v4, 0x0

    const/high16 v3, 0x40a0

    .line 48
    new-instance v0, Landroid/graphics/RectF;

    invoke-direct {v0}, Landroid/graphics/RectF;-><init>()V

    .line 49
    .local v0, drawRect:Landroid/graphics/RectF;
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TransparentPanel;->getMeasuredWidth()I

    move-result v1

    int-to-float v1, v1

    invoke-virtual {p0}, Lcom/openvehicles/OVMS/TransparentPanel;->getMeasuredHeight()I

    move-result v2

    int-to-float v2, v2

    invoke-virtual {v0, v4, v4, v1, v2}, Landroid/graphics/RectF;->set(FFFF)V

    .line 51
    iget-object v1, p0, Lcom/openvehicles/OVMS/TransparentPanel;->innerPaint:Landroid/graphics/Paint;

    invoke-virtual {p1, v0, v3, v3, v1}, Landroid/graphics/Canvas;->drawRoundRect(Landroid/graphics/RectF;FFLandroid/graphics/Paint;)V

    .line 52
    iget-object v1, p0, Lcom/openvehicles/OVMS/TransparentPanel;->borderPaint:Landroid/graphics/Paint;

    invoke-virtual {p1, v0, v3, v3, v1}, Landroid/graphics/Canvas;->drawRoundRect(Landroid/graphics/RectF;FFLandroid/graphics/Paint;)V

    .line 54
    invoke-super {p0, p1}, Landroid/widget/LinearLayout;->dispatchDraw(Landroid/graphics/Canvas;)V

    .line 55
    return-void
.end method

.method public setBorderPaint(Landroid/graphics/Paint;)V
    .locals 0
    .parameter "borderPaint"

    .prologue
    .line 42
    iput-object p1, p0, Lcom/openvehicles/OVMS/TransparentPanel;->borderPaint:Landroid/graphics/Paint;

    .line 43
    return-void
.end method

.method public setInnerPaint(Landroid/graphics/Paint;)V
    .locals 0
    .parameter "innerPaint"

    .prologue
    .line 38
    iput-object p1, p0, Lcom/openvehicles/OVMS/TransparentPanel;->innerPaint:Landroid/graphics/Paint;

    .line 39
    return-void
.end method
