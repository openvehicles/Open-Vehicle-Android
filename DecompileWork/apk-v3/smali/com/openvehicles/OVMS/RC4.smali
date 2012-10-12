.class public Lcom/openvehicles/OVMS/RC4;
.super Ljava/lang/Object;
.source "RC4.java"


# instance fields
.field private state:[B

.field private x:I

.field private y:I


# direct methods
.method public constructor <init>(Ljava/lang/String;)V
    .locals 1
    .parameter "key"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/NullPointerException;
        }
    .end annotation

    .prologue
    .line 70
    invoke-virtual {p1}, Ljava/lang/String;->getBytes()[B

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/openvehicles/OVMS/RC4;-><init>([B)V

    .line 71
    return-void
.end method

.method public constructor <init>([B)V
    .locals 8
    .parameter "key"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/NullPointerException;
        }
    .end annotation

    .prologue
    const/4 v7, 0x0

    const/16 v6, 0x100

    .line 80
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 58
    new-array v4, v6, [B

    iput-object v4, p0, Lcom/openvehicles/OVMS/RC4;->state:[B

    .line 82
    const/4 v0, 0x0

    .local v0, i:I
    :goto_0
    if-lt v0, v6, :cond_1

    .line 86
    iput v7, p0, Lcom/openvehicles/OVMS/RC4;->x:I

    .line 87
    iput v7, p0, Lcom/openvehicles/OVMS/RC4;->y:I

    .line 89
    const/4 v1, 0x0

    .line 90
    .local v1, index1:I
    const/4 v2, 0x0

    .line 94
    .local v2, index2:I
    if-eqz p1, :cond_0

    array-length v4, p1

    if-nez v4, :cond_2

    .line 95
    :cond_0
    new-instance v4, Ljava/lang/NullPointerException;

    invoke-direct {v4}, Ljava/lang/NullPointerException;-><init>()V

    throw v4

    .line 83
    .end local v1           #index1:I
    .end local v2           #index2:I
    :cond_1
    iget-object v4, p0, Lcom/openvehicles/OVMS/RC4;->state:[B

    int-to-byte v5, v0

    aput-byte v5, v4, v0

    .line 82
    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    .line 98
    .restart local v1       #index1:I
    .restart local v2       #index2:I
    :cond_2
    const/4 v0, 0x0

    :goto_1
    if-lt v0, v6, :cond_3

    .line 111
    return-void

    .line 100
    :cond_3
    aget-byte v4, p1, v1

    and-int/lit16 v4, v4, 0xff

    iget-object v5, p0, Lcom/openvehicles/OVMS/RC4;->state:[B

    aget-byte v5, v5, v0

    and-int/lit16 v5, v5, 0xff

    add-int/2addr v4, v5

    add-int/2addr v4, v2

    and-int/lit16 v2, v4, 0xff

    .line 102
    iget-object v4, p0, Lcom/openvehicles/OVMS/RC4;->state:[B

    aget-byte v3, v4, v0

    .line 103
    .local v3, tmp:B
    iget-object v4, p0, Lcom/openvehicles/OVMS/RC4;->state:[B

    iget-object v5, p0, Lcom/openvehicles/OVMS/RC4;->state:[B

    aget-byte v5, v5, v2

    aput-byte v5, v4, v0

    .line 104
    iget-object v4, p0, Lcom/openvehicles/OVMS/RC4;->state:[B

    aput-byte v3, v4, v2

    .line 106
    add-int/lit8 v4, v1, 0x1

    array-length v5, p1

    rem-int v1, v4, v5

    .line 98
    add-int/lit8 v0, v0, 0x1

    goto :goto_1
.end method


# virtual methods
.method public rc4(Ljava/lang/String;)[B
    .locals 1
    .parameter "data"

    .prologue
    .line 121
    if-nez p1, :cond_0

    .line 122
    const/4 v0, 0x0

    .line 129
    :goto_0
    return-object v0

    .line 125
    :cond_0
    invoke-virtual {p1}, Ljava/lang/String;->getBytes()[B

    move-result-object v0

    .line 127
    .local v0, tmp:[B
    invoke-virtual {p0, v0}, Lcom/openvehicles/OVMS/RC4;->rc4([B)[B

    goto :goto_0
.end method

.method public rc4([B)[B
    .locals 8
    .parameter "buf"

    .prologue
    .line 146
    if-nez p1, :cond_1

    .line 147
    const/4 v1, 0x0

    .line 168
    :cond_0
    return-object v1

    .line 150
    :cond_1
    array-length v4, p1

    new-array v1, v4, [B

    .line 152
    .local v1, result:[B
    const/4 v0, 0x0

    .local v0, i:I
    :goto_0
    array-length v4, p1

    if-ge v0, v4, :cond_0

    .line 154
    iget v4, p0, Lcom/openvehicles/OVMS/RC4;->x:I

    add-int/lit8 v4, v4, 0x1

    and-int/lit16 v4, v4, 0xff

    iput v4, p0, Lcom/openvehicles/OVMS/RC4;->x:I

    .line 155
    iget-object v4, p0, Lcom/openvehicles/OVMS/RC4;->state:[B

    iget v5, p0, Lcom/openvehicles/OVMS/RC4;->x:I

    aget-byte v4, v4, v5

    and-int/lit16 v4, v4, 0xff

    iget v5, p0, Lcom/openvehicles/OVMS/RC4;->y:I

    add-int/2addr v4, v5

    and-int/lit16 v4, v4, 0xff

    iput v4, p0, Lcom/openvehicles/OVMS/RC4;->y:I

    .line 157
    iget-object v4, p0, Lcom/openvehicles/OVMS/RC4;->state:[B

    iget v5, p0, Lcom/openvehicles/OVMS/RC4;->x:I

    aget-byte v2, v4, v5

    .line 158
    .local v2, tmp:B
    iget-object v4, p0, Lcom/openvehicles/OVMS/RC4;->state:[B

    iget v5, p0, Lcom/openvehicles/OVMS/RC4;->x:I

    iget-object v6, p0, Lcom/openvehicles/OVMS/RC4;->state:[B

    iget v7, p0, Lcom/openvehicles/OVMS/RC4;->y:I

    aget-byte v6, v6, v7

    aput-byte v6, v4, v5

    .line 159
    iget-object v4, p0, Lcom/openvehicles/OVMS/RC4;->state:[B

    iget v5, p0, Lcom/openvehicles/OVMS/RC4;->y:I

    aput-byte v2, v4, v5

    .line 161
    iget-object v4, p0, Lcom/openvehicles/OVMS/RC4;->state:[B

    iget v5, p0, Lcom/openvehicles/OVMS/RC4;->x:I

    aget-byte v4, v4, v5

    and-int/lit16 v4, v4, 0xff

    iget-object v5, p0, Lcom/openvehicles/OVMS/RC4;->state:[B

    iget v6, p0, Lcom/openvehicles/OVMS/RC4;->y:I

    aget-byte v5, v5, v6

    and-int/lit16 v5, v5, 0xff

    add-int/2addr v4, v5

    and-int/lit16 v3, v4, 0xff

    .line 162
    .local v3, xorIndex:I
    aget-byte v4, p1, v0

    iget-object v5, p0, Lcom/openvehicles/OVMS/RC4;->state:[B

    aget-byte v5, v5, v3

    xor-int/2addr v4, v5

    int-to-byte v4, v4

    aput-byte v4, v1, v0

    .line 152
    add-int/lit8 v0, v0, 0x1

    goto :goto_0
.end method
