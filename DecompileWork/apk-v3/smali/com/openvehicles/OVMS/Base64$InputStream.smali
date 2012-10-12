.class public Lcom/openvehicles/OVMS/Base64$InputStream;
.super Ljava/io/FilterInputStream;
.source "Base64.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/openvehicles/OVMS/Base64;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x9
    name = "InputStream"
.end annotation


# instance fields
.field private breakLines:Z

.field private buffer:[B

.field private bufferLength:I

.field private decodabet:[B

.field private encode:Z

.field private lineLength:I

.field private numSigBytes:I

.field private options:I

.field private position:I


# direct methods
.method public constructor <init>(Ljava/io/InputStream;)V
    .locals 1
    .parameter "in"

    .prologue
    .line 1662
    const/4 v0, 0x0

    invoke-direct {p0, p1, v0}, Lcom/openvehicles/OVMS/Base64$InputStream;-><init>(Ljava/io/InputStream;I)V

    .line 1663
    return-void
.end method

.method public constructor <init>(Ljava/io/InputStream;I)V
    .locals 3
    .parameter "in"
    .parameter "options"

    .prologue
    const/4 v1, 0x1

    const/4 v2, 0x0

    .line 1688
    invoke-direct {p0, p1}, Ljava/io/FilterInputStream;-><init>(Ljava/io/InputStream;)V

    .line 1689
    iput p2, p0, Lcom/openvehicles/OVMS/Base64$InputStream;->options:I

    .line 1690
    and-int/lit8 v0, p2, 0x8

    if-lez v0, :cond_0

    move v0, v1

    :goto_0
    iput-boolean v0, p0, Lcom/openvehicles/OVMS/Base64$InputStream;->breakLines:Z

    .line 1691
    and-int/lit8 v0, p2, 0x1

    if-lez v0, :cond_1

    :goto_1
    iput-boolean v1, p0, Lcom/openvehicles/OVMS/Base64$InputStream;->encode:Z

    .line 1692
    iget-boolean v0, p0, Lcom/openvehicles/OVMS/Base64$InputStream;->encode:Z

    if-eqz v0, :cond_2

    const/4 v0, 0x4

    :goto_2
    iput v0, p0, Lcom/openvehicles/OVMS/Base64$InputStream;->bufferLength:I

    .line 1693
    iget v0, p0, Lcom/openvehicles/OVMS/Base64$InputStream;->bufferLength:I

    new-array v0, v0, [B

    iput-object v0, p0, Lcom/openvehicles/OVMS/Base64$InputStream;->buffer:[B

    .line 1694
    const/4 v0, -0x1

    iput v0, p0, Lcom/openvehicles/OVMS/Base64$InputStream;->position:I

    .line 1695
    iput v2, p0, Lcom/openvehicles/OVMS/Base64$InputStream;->lineLength:I

    .line 1696
    #calls: Lcom/openvehicles/OVMS/Base64;->getDecodabet(I)[B
    invoke-static {p2}, Lcom/openvehicles/OVMS/Base64;->access$0(I)[B

    move-result-object v0

    iput-object v0, p0, Lcom/openvehicles/OVMS/Base64$InputStream;->decodabet:[B

    .line 1697
    return-void

    :cond_0
    move v0, v2

    .line 1690
    goto :goto_0

    :cond_1
    move v1, v2

    .line 1691
    goto :goto_1

    .line 1692
    :cond_2
    const/4 v0, 0x3

    goto :goto_2
.end method


# virtual methods
.method public read()I
    .locals 11
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .prologue
    const/4 v4, 0x3

    const/4 v10, 0x4

    const/4 v9, -0x1

    const/4 v1, 0x0

    .line 1710
    iget v3, p0, Lcom/openvehicles/OVMS/Base64$InputStream;->position:I

    if-gez v3, :cond_1

    .line 1711
    iget-boolean v3, p0, Lcom/openvehicles/OVMS/Base64$InputStream;->encode:Z

    if-eqz v3, :cond_4

    .line 1712
    new-array v0, v4, [B

    .line 1713
    .local v0, b3:[B
    const/4 v2, 0x0

    .line 1714
    .local v2, numBinaryBytes:I
    const/4 v8, 0x0

    .local v8, i:I
    :goto_0
    if-lt v8, v4, :cond_2

    .line 1727
    :cond_0
    if-lez v2, :cond_3

    .line 1728
    iget-object v3, p0, Lcom/openvehicles/OVMS/Base64$InputStream;->buffer:[B

    iget v5, p0, Lcom/openvehicles/OVMS/Base64$InputStream;->options:I

    move v4, v1

    #calls: Lcom/openvehicles/OVMS/Base64;->encode3to4([BII[BII)[B
    invoke-static/range {v0 .. v5}, Lcom/openvehicles/OVMS/Base64;->access$1([BII[BII)[B

    .line 1729
    iput v1, p0, Lcom/openvehicles/OVMS/Base64$InputStream;->position:I

    .line 1730
    iput v10, p0, Lcom/openvehicles/OVMS/Base64$InputStream;->numSigBytes:I

    .line 1770
    .end local v0           #b3:[B
    .end local v2           #numBinaryBytes:I
    .end local v8           #i:I
    :cond_1
    :goto_1
    iget v3, p0, Lcom/openvehicles/OVMS/Base64$InputStream;->position:I

    if-ltz v3, :cond_e

    .line 1772
    iget v3, p0, Lcom/openvehicles/OVMS/Base64$InputStream;->position:I

    iget v4, p0, Lcom/openvehicles/OVMS/Base64$InputStream;->numSigBytes:I

    if-lt v3, v4, :cond_b

    move v1, v9

    .line 1791
    :goto_2
    return v1

    .line 1715
    .restart local v0       #b3:[B
    .restart local v2       #numBinaryBytes:I
    .restart local v8       #i:I
    :cond_2
    iget-object v3, p0, Lcom/openvehicles/OVMS/Base64$InputStream;->in:Ljava/io/InputStream;

    invoke-virtual {v3}, Ljava/io/InputStream;->read()I

    move-result v6

    .line 1718
    .local v6, b:I
    if-ltz v6, :cond_0

    .line 1719
    int-to-byte v3, v6

    aput-byte v3, v0, v8

    .line 1720
    add-int/lit8 v2, v2, 0x1

    .line 1714
    add-int/lit8 v8, v8, 0x1

    goto :goto_0

    .end local v6           #b:I
    :cond_3
    move v1, v9

    .line 1733
    goto :goto_2

    .line 1739
    .end local v0           #b3:[B
    .end local v2           #numBinaryBytes:I
    .end local v8           #i:I
    :cond_4
    new-array v7, v10, [B

    .line 1740
    .local v7, b4:[B
    const/4 v8, 0x0

    .line 1741
    .restart local v8       #i:I
    const/4 v8, 0x0

    :goto_3
    if-lt v8, v10, :cond_6

    .line 1754
    :cond_5
    if-ne v8, v10, :cond_9

    .line 1755
    iget-object v3, p0, Lcom/openvehicles/OVMS/Base64$InputStream;->buffer:[B

    iget v4, p0, Lcom/openvehicles/OVMS/Base64$InputStream;->options:I

    #calls: Lcom/openvehicles/OVMS/Base64;->decode4to3([BI[BII)I
    invoke-static {v7, v1, v3, v1, v4}, Lcom/openvehicles/OVMS/Base64;->access$2([BI[BII)I

    move-result v3

    iput v3, p0, Lcom/openvehicles/OVMS/Base64$InputStream;->numSigBytes:I

    .line 1756
    iput v1, p0, Lcom/openvehicles/OVMS/Base64$InputStream;->position:I

    goto :goto_1

    .line 1743
    :cond_6
    const/4 v6, 0x0

    .line 1744
    .restart local v6       #b:I
    :cond_7
    iget-object v3, p0, Lcom/openvehicles/OVMS/Base64$InputStream;->in:Ljava/io/InputStream;

    invoke-virtual {v3}, Ljava/io/InputStream;->read()I

    move-result v6

    .line 1745
    if-ltz v6, :cond_8

    iget-object v3, p0, Lcom/openvehicles/OVMS/Base64$InputStream;->decodabet:[B

    and-int/lit8 v4, v6, 0x7f

    aget-byte v3, v3, v4

    const/4 v4, -0x5

    if-le v3, v4, :cond_7

    .line 1747
    :cond_8
    if-ltz v6, :cond_5

    .line 1751
    int-to-byte v3, v6

    aput-byte v3, v7, v8

    .line 1741
    add-int/lit8 v8, v8, 0x1

    goto :goto_3

    .line 1758
    .end local v6           #b:I
    :cond_9
    if-nez v8, :cond_a

    move v1, v9

    .line 1759
    goto :goto_2

    .line 1763
    :cond_a
    new-instance v1, Ljava/io/IOException;

    const-string v3, "Improperly padded Base64 input."

    invoke-direct {v1, v3}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v1

    .line 1776
    .end local v7           #b4:[B
    .end local v8           #i:I
    :cond_b
    iget-boolean v3, p0, Lcom/openvehicles/OVMS/Base64$InputStream;->encode:Z

    if-eqz v3, :cond_c

    iget-boolean v3, p0, Lcom/openvehicles/OVMS/Base64$InputStream;->breakLines:Z

    if-eqz v3, :cond_c

    iget v3, p0, Lcom/openvehicles/OVMS/Base64$InputStream;->lineLength:I

    const/16 v4, 0x4c

    if-lt v3, v4, :cond_c

    .line 1777
    iput v1, p0, Lcom/openvehicles/OVMS/Base64$InputStream;->lineLength:I

    .line 1778
    const/16 v1, 0xa

    goto :goto_2

    .line 1781
    :cond_c
    iget v1, p0, Lcom/openvehicles/OVMS/Base64$InputStream;->lineLength:I

    add-int/lit8 v1, v1, 0x1

    iput v1, p0, Lcom/openvehicles/OVMS/Base64$InputStream;->lineLength:I

    .line 1785
    iget-object v1, p0, Lcom/openvehicles/OVMS/Base64$InputStream;->buffer:[B

    iget v3, p0, Lcom/openvehicles/OVMS/Base64$InputStream;->position:I

    add-int/lit8 v4, v3, 0x1

    iput v4, p0, Lcom/openvehicles/OVMS/Base64$InputStream;->position:I

    aget-byte v6, v1, v3

    .line 1787
    .restart local v6       #b:I
    iget v1, p0, Lcom/openvehicles/OVMS/Base64$InputStream;->position:I

    iget v3, p0, Lcom/openvehicles/OVMS/Base64$InputStream;->bufferLength:I

    if-lt v1, v3, :cond_d

    .line 1788
    iput v9, p0, Lcom/openvehicles/OVMS/Base64$InputStream;->position:I

    .line 1791
    :cond_d
    and-int/lit16 v1, v6, 0xff

    goto :goto_2

    .line 1798
    .end local v6           #b:I
    :cond_e
    new-instance v1, Ljava/io/IOException;

    const-string v3, "Error in Base64 code reading stream."

    invoke-direct {v1, v3}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v1
.end method

.method public read([BII)I
    .locals 4
    .parameter "dest"
    .parameter "off"
    .parameter "len"
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .prologue
    .line 1820
    const/4 v1, 0x0

    .local v1, i:I
    :goto_0
    if-lt v1, p3, :cond_1

    .line 1833
    .end local v1           #i:I
    :cond_0
    :goto_1
    return v1

    .line 1821
    .restart local v1       #i:I
    :cond_1
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/Base64$InputStream;->read()I

    move-result v0

    .line 1823
    .local v0, b:I
    if-ltz v0, :cond_2

    .line 1824
    add-int v2, p2, v1

    int-to-byte v3, v0

    aput-byte v3, p1, v2

    .line 1820
    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    .line 1826
    :cond_2
    if-nez v1, :cond_0

    .line 1827
    const/4 v1, -0x1

    goto :goto_1
.end method
