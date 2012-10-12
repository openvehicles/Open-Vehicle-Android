.class public Lcom/openvehicles/OVMS/HMAC;
.super Ljava/lang/Object;
.source "HMAC.java"


# static fields
.field private static final IPAD:B = 0x36t

.field private static final OPAD:B = 0x5ct

.field private static final PADLEN:B = 0x40t


# instance fields
.field digest:Ljava/security/MessageDigest;

.field private ipad:[B

.field private opad:[B


# direct methods
.method public constructor <init>(Ljava/lang/String;[B)V
    .locals 4
    .parameter "digestName"
    .parameter "key"

    .prologue
    .line 62
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 64
    :try_start_0
    invoke-static {p1}, Ljava/security/MessageDigest;->getInstance(Ljava/lang/String;)Ljava/security/MessageDigest;

    move-result-object v1

    iput-object v1, p0, Lcom/openvehicles/OVMS/HMAC;->digest:Ljava/security/MessageDigest;
    :try_end_0
    .catch Ljava/security/NoSuchAlgorithmException; {:try_start_0 .. :try_end_0} :catch_0

    .line 69
    invoke-direct {p0, p2}, Lcom/openvehicles/OVMS/HMAC;->init([B)V

    .line 70
    return-void

    .line 65
    :catch_0
    move-exception v0

    .line 66
    .local v0, e:Ljava/security/NoSuchAlgorithmException;
    new-instance v1, Ljava/lang/IllegalArgumentException;

    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "unknown digest algorithm "

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 67
    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    .line 66
    invoke-direct {v1, v2}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw v1
.end method

.method public constructor <init>(Ljava/security/MessageDigest;[B)V
    .locals 0
    .parameter "digest"
    .parameter "key"

    .prologue
    .line 50
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 51
    invoke-virtual {p1}, Ljava/security/MessageDigest;->reset()V

    .line 52
    iput-object p1, p0, Lcom/openvehicles/OVMS/HMAC;->digest:Ljava/security/MessageDigest;

    .line 53
    invoke-direct {p0, p2}, Lcom/openvehicles/OVMS/HMAC;->init([B)V

    .line 54
    return-void
.end method

.method private init([B)V
    .locals 4
    .parameter "key"

    .prologue
    const/16 v3, 0x40

    .line 27
    array-length v1, p1

    if-le v1, v3, :cond_0

    .line 28
    iget-object v1, p0, Lcom/openvehicles/OVMS/HMAC;->digest:Ljava/security/MessageDigest;

    invoke-virtual {v1, p1}, Ljava/security/MessageDigest;->digest([B)[B

    move-result-object p1

    .line 29
    iget-object v1, p0, Lcom/openvehicles/OVMS/HMAC;->digest:Ljava/security/MessageDigest;

    invoke-virtual {v1}, Ljava/security/MessageDigest;->reset()V

    .line 31
    :cond_0
    new-array v1, v3, [B

    iput-object v1, p0, Lcom/openvehicles/OVMS/HMAC;->ipad:[B

    .line 32
    new-array v1, v3, [B

    iput-object v1, p0, Lcom/openvehicles/OVMS/HMAC;->opad:[B

    .line 33
    const/4 v0, 0x0

    .local v0, i:I
    :goto_0
    array-length v1, p1

    if-lt v0, v1, :cond_1

    .line 37
    :goto_1
    if-lt v0, v3, :cond_2

    .line 41
    iget-object v1, p0, Lcom/openvehicles/OVMS/HMAC;->digest:Ljava/security/MessageDigest;

    iget-object v2, p0, Lcom/openvehicles/OVMS/HMAC;->ipad:[B

    invoke-virtual {v1, v2}, Ljava/security/MessageDigest;->update([B)V

    .line 42
    return-void

    .line 34
    :cond_1
    iget-object v1, p0, Lcom/openvehicles/OVMS/HMAC;->ipad:[B

    aget-byte v2, p1, v0

    xor-int/lit8 v2, v2, 0x36

    int-to-byte v2, v2

    aput-byte v2, v1, v0

    .line 35
    iget-object v1, p0, Lcom/openvehicles/OVMS/HMAC;->opad:[B

    aget-byte v2, p1, v0

    xor-int/lit8 v2, v2, 0x5c

    int-to-byte v2, v2

    aput-byte v2, v1, v0

    .line 33
    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    .line 38
    :cond_2
    iget-object v1, p0, Lcom/openvehicles/OVMS/HMAC;->ipad:[B

    const/16 v2, 0x36

    aput-byte v2, v1, v0

    .line 39
    iget-object v1, p0, Lcom/openvehicles/OVMS/HMAC;->opad:[B

    const/16 v2, 0x5c

    aput-byte v2, v1, v0

    .line 37
    add-int/lit8 v0, v0, 0x1

    goto :goto_1
.end method


# virtual methods
.method public clear()V
    .locals 2

    .prologue
    .line 119
    iget-object v0, p0, Lcom/openvehicles/OVMS/HMAC;->digest:Ljava/security/MessageDigest;

    invoke-virtual {v0}, Ljava/security/MessageDigest;->reset()V

    .line 120
    iget-object v0, p0, Lcom/openvehicles/OVMS/HMAC;->digest:Ljava/security/MessageDigest;

    iget-object v1, p0, Lcom/openvehicles/OVMS/HMAC;->ipad:[B

    invoke-virtual {v0, v1}, Ljava/security/MessageDigest;->update([B)V

    .line 121
    return-void
.end method

.method public sign()[B
    .locals 3

    .prologue
    .line 98
    iget-object v1, p0, Lcom/openvehicles/OVMS/HMAC;->digest:Ljava/security/MessageDigest;

    invoke-virtual {v1}, Ljava/security/MessageDigest;->digest()[B

    move-result-object v0

    .line 99
    .local v0, output:[B
    iget-object v1, p0, Lcom/openvehicles/OVMS/HMAC;->digest:Ljava/security/MessageDigest;

    invoke-virtual {v1}, Ljava/security/MessageDigest;->reset()V

    .line 100
    iget-object v1, p0, Lcom/openvehicles/OVMS/HMAC;->digest:Ljava/security/MessageDigest;

    iget-object v2, p0, Lcom/openvehicles/OVMS/HMAC;->opad:[B

    invoke-virtual {v1, v2}, Ljava/security/MessageDigest;->update([B)V

    .line 101
    iget-object v1, p0, Lcom/openvehicles/OVMS/HMAC;->digest:Ljava/security/MessageDigest;

    invoke-virtual {v1, v0}, Ljava/security/MessageDigest;->digest([B)[B

    move-result-object v1

    return-object v1
.end method

.method public update([B)V
    .locals 1
    .parameter "b"

    .prologue
    .line 89
    iget-object v0, p0, Lcom/openvehicles/OVMS/HMAC;->digest:Ljava/security/MessageDigest;

    invoke-virtual {v0, p1}, Ljava/security/MessageDigest;->update([B)V

    .line 90
    return-void
.end method

.method public update([BII)V
    .locals 1
    .parameter "b"
    .parameter "offset"
    .parameter "length"

    .prologue
    .line 80
    iget-object v0, p0, Lcom/openvehicles/OVMS/HMAC;->digest:Ljava/security/MessageDigest;

    invoke-virtual {v0, p1, p2, p3}, Ljava/security/MessageDigest;->update([BII)V

    .line 81
    return-void
.end method

.method public verify([B)Z
    .locals 1
    .parameter "signature"

    .prologue
    .line 111
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/HMAC;->sign()[B

    move-result-object v0

    invoke-static {p1, v0}, Ljava/util/Arrays;->equals([B[B)Z

    move-result v0

    return v0
.end method
