.class Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;
.super Landroid/os/AsyncTask;
.source "OVMSActivity.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/openvehicles/OVMS/OVMSActivity;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "TCPTask"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Landroid/os/AsyncTask",
        "<",
        "Ljava/lang/Void;",
        "Ljava/lang/Integer;",
        "Ljava/lang/Void;",
        ">;"
    }
.end annotation


# instance fields
.field private Inputstream:Ljava/io/BufferedReader;

.field private Outputstream:Ljava/io/PrintWriter;

.field public Sock:Ljava/net/Socket;

.field private carData:Lcom/openvehicles/OVMS/CarData;

.field private pmDigest:[B

.field private pmcipher:Ljavax/crypto/Cipher;

.field private rxcipher:Ljavax/crypto/Cipher;

.field final synthetic this$0:Lcom/openvehicles/OVMS/OVMSActivity;

.field private txcipher:Ljavax/crypto/Cipher;


# direct methods
.method public constructor <init>(Lcom/openvehicles/OVMS/OVMSActivity;Lcom/openvehicles/OVMS/CarData;)V
    .locals 1
    .parameter
    .parameter "car"

    .prologue
    .line 489
    iput-object p1, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-direct {p0}, Landroid/os/AsyncTask;-><init>()V

    .line 490
    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;
    invoke-static {p1}, Lcom/openvehicles/OVMS/OVMSActivity;->access$500(Lcom/openvehicles/OVMS/OVMSActivity;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v0

    iput-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    .line 491
    return-void
.end method

.method private ConnInit()V
    .locals 29

    .prologue
    .line 771
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    move-object/from16 v23, v0

    move-object/from16 v0, v23

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->CarPass:Ljava/lang/String;

    move-object/from16 v20, v0

    .line 772
    .local v20, shared_secret:Ljava/lang/String;
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    move-object/from16 v23, v0

    move-object/from16 v0, v23

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    move-object/from16 v22, v0

    .line 773
    .local v22, vehicleID:Ljava/lang/String;
    const-string v4, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"

    .line 774
    .local v4, b64tabString:Ljava/lang/String;
    invoke-virtual {v4}, Ljava/lang/String;->toCharArray()[C

    move-result-object v3

    .line 777
    .local v3, b64tab:[C
    new-instance v14, Ljava/util/Random;

    invoke-direct {v14}, Ljava/util/Random;-><init>()V

    .line 778
    .local v14, rnd:Ljava/util/Random;
    const-string v9, ""

    .line 779
    .local v9, client_tokenString:Ljava/lang/String;
    const/4 v10, 0x0

    .local v10, cnt:I
    :goto_0
    const/16 v23, 0x16

    move/from16 v0, v23

    if-ge v10, v0, :cond_0

    .line 780
    new-instance v23, Ljava/lang/StringBuilder;

    invoke-direct/range {v23 .. v23}, Ljava/lang/StringBuilder;-><init>()V

    move-object/from16 v0, v23

    invoke-virtual {v0, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v23

    array-length v0, v3

    move/from16 v24, v0

    add-int/lit8 v24, v24, -0x1

    move/from16 v0, v24

    invoke-virtual {v14, v0}, Ljava/util/Random;->nextInt(I)I

    move-result v24

    aget-char v24, v3, v24

    invoke-virtual/range {v23 .. v24}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    move-result-object v23

    invoke-virtual/range {v23 .. v23}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    .line 779
    add-int/lit8 v10, v10, 0x1

    goto :goto_0

    .line 782
    :cond_0
    invoke-virtual {v9}, Ljava/lang/String;->getBytes()[B

    move-result-object v8

    .line 784
    .local v8, client_token:[B
    :try_start_0
    const-string v23, "HmacMD5"

    invoke-static/range {v23 .. v23}, Ljavax/crypto/Mac;->getInstance(Ljava/lang/String;)Ljavax/crypto/Mac;

    move-result-object v6

    .line 785
    .local v6, client_hmac:Ljavax/crypto/Mac;
    new-instance v21, Ljavax/crypto/spec/SecretKeySpec;

    invoke-virtual/range {v20 .. v20}, Ljava/lang/String;->getBytes()[B

    move-result-object v23

    const-string v24, "HmacMD5"

    move-object/from16 v0, v21

    move-object/from16 v1, v23

    move-object/from16 v2, v24

    invoke-direct {v0, v1, v2}, Ljavax/crypto/spec/SecretKeySpec;-><init>([BLjava/lang/String;)V

    .line 787
    .local v21, sk:Ljavax/crypto/spec/SecretKeySpec;
    move-object/from16 v0, v21

    invoke-virtual {v6, v0}, Ljavax/crypto/Mac;->init(Ljava/security/Key;)V

    .line 788
    invoke-virtual {v6, v8}, Ljavax/crypto/Mac;->doFinal([B)[B

    move-result-object v12

    .line 789
    .local v12, hashedBytes:[B
    const/16 v23, 0x2

    move/from16 v0, v23

    invoke-static {v12, v0}, Landroid/util/Base64;->encodeToString([BI)Ljava/lang/String;

    move-result-object v5

    .line 792
    .local v5, client_digest:Ljava/lang/String;
    new-instance v23, Ljava/net/Socket;

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    move-object/from16 v24, v0

    move-object/from16 v0, v24

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->ServerNameOrIP:Ljava/lang/String;

    move-object/from16 v24, v0

    const/16 v25, 0x1ad3

    invoke-direct/range {v23 .. v25}, Ljava/net/Socket;-><init>(Ljava/lang/String;I)V

    move-object/from16 v0, v23

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Sock:Ljava/net/Socket;

    .line 794
    new-instance v23, Ljava/io/PrintWriter;

    new-instance v24, Ljava/io/BufferedWriter;

    new-instance v25, Ljava/io/OutputStreamWriter;

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Sock:Ljava/net/Socket;

    move-object/from16 v26, v0

    invoke-virtual/range {v26 .. v26}, Ljava/net/Socket;->getOutputStream()Ljava/io/OutputStream;

    move-result-object v26

    invoke-direct/range {v25 .. v26}, Ljava/io/OutputStreamWriter;-><init>(Ljava/io/OutputStream;)V

    invoke-direct/range {v24 .. v25}, Ljava/io/BufferedWriter;-><init>(Ljava/io/Writer;)V

    const/16 v25, 0x1

    invoke-direct/range {v23 .. v25}, Ljava/io/PrintWriter;-><init>(Ljava/io/Writer;Z)V

    move-object/from16 v0, v23

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Outputstream:Ljava/io/PrintWriter;

    .line 798
    const-string v23, "OVMS"

    const-string v24, "TX: MP-A 0 %s %s %s"

    const/16 v25, 0x3

    move/from16 v0, v25

    new-array v0, v0, [Ljava/lang/Object;

    move-object/from16 v25, v0

    const/16 v26, 0x0

    aput-object v9, v25, v26

    const/16 v26, 0x1

    aput-object v5, v25, v26

    const/16 v26, 0x2

    aput-object v22, v25, v26

    invoke-static/range {v24 .. v25}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v24

    invoke-static/range {v23 .. v24}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 801
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Outputstream:Ljava/io/PrintWriter;

    move-object/from16 v23, v0

    const-string v24, "MP-A 0 %s %s %s"

    const/16 v25, 0x3

    move/from16 v0, v25

    new-array v0, v0, [Ljava/lang/Object;

    move-object/from16 v25, v0

    const/16 v26, 0x0

    aput-object v9, v25, v26

    const/16 v26, 0x1

    aput-object v5, v25, v26

    const/16 v26, 0x2

    aput-object v22, v25, v26

    invoke-static/range {v24 .. v25}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v24

    invoke-virtual/range {v23 .. v24}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 804
    new-instance v23, Ljava/io/BufferedReader;

    new-instance v24, Ljava/io/InputStreamReader;

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Sock:Ljava/net/Socket;

    move-object/from16 v25, v0

    invoke-virtual/range {v25 .. v25}, Ljava/net/Socket;->getInputStream()Ljava/io/InputStream;

    move-result-object v25

    invoke-direct/range {v24 .. v25}, Ljava/io/InputStreamReader;-><init>(Ljava/io/InputStream;)V

    invoke-direct/range {v23 .. v24}, Ljava/io/BufferedReader;-><init>(Ljava/io/Reader;)V

    move-object/from16 v0, v23

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Inputstream:Ljava/io/BufferedReader;
    :try_end_0
    .catch Ljava/net/UnknownHostException; {:try_start_0 .. :try_end_0} :catch_1
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_2
    .catch Ljava/lang/NullPointerException; {:try_start_0 .. :try_end_0} :catch_3
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_4

    .line 808
    const/4 v15, 0x0

    .line 810
    .local v15, serverWelcomeMsg:[Ljava/lang/String;
    :try_start_1
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Inputstream:Ljava/io/BufferedReader;

    move-object/from16 v23, v0

    invoke-virtual/range {v23 .. v23}, Ljava/io/BufferedReader;->readLine()Ljava/lang/String;

    move-result-object v23

    invoke-virtual/range {v23 .. v23}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v23

    const-string v24, "[ ]+"

    invoke-virtual/range {v23 .. v24}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_0
    .catch Ljava/net/UnknownHostException; {:try_start_1 .. :try_end_1} :catch_1
    .catch Ljava/io/IOException; {:try_start_1 .. :try_end_1} :catch_2
    .catch Ljava/lang/NullPointerException; {:try_start_1 .. :try_end_1} :catch_3

    move-result-object v15

    .line 818
    :try_start_2
    const-string v23, "OVMS"

    const-string v24, "RX: %s %s %s %s"

    const/16 v25, 0x4

    move/from16 v0, v25

    new-array v0, v0, [Ljava/lang/Object;

    move-object/from16 v25, v0

    const/16 v26, 0x0

    const/16 v27, 0x0

    aget-object v27, v15, v27

    aput-object v27, v25, v26

    const/16 v26, 0x1

    const/16 v27, 0x1

    aget-object v27, v15, v27

    aput-object v27, v25, v26

    const/16 v26, 0x2

    const/16 v27, 0x2

    aget-object v27, v15, v27

    aput-object v27, v25, v26

    const/16 v26, 0x3

    const/16 v27, 0x3

    aget-object v27, v15, v27

    aput-object v27, v25, v26

    invoke-static/range {v24 .. v25}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v24

    invoke-static/range {v23 .. v24}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 822
    const/16 v23, 0x2

    aget-object v19, v15, v23

    .line 823
    .local v19, server_tokenString:Ljava/lang/String;
    invoke-virtual/range {v19 .. v19}, Ljava/lang/String;->getBytes()[B

    move-result-object v18

    .line 824
    .local v18, server_token:[B
    const/16 v23, 0x3

    aget-object v23, v15, v23

    const/16 v24, 0x0

    invoke-static/range {v23 .. v24}, Landroid/util/Base64;->decode(Ljava/lang/String;I)[B

    move-result-object v17

    .line 826
    .local v17, server_digest:[B
    move-object/from16 v0, v18

    invoke-virtual {v6, v0}, Ljavax/crypto/Mac;->doFinal([B)[B

    move-result-object v23

    move-object/from16 v0, v23

    move-object/from16 v1, v17

    invoke-static {v0, v1}, Ljava/util/Arrays;->equals([B[B)Z

    move-result v23

    if-nez v23, :cond_2

    .line 829
    const-string v23, "OVMS"

    const-string v24, "Server authentication failed. Expected %s Got %s"

    const/16 v25, 0x2

    move/from16 v0, v25

    new-array v0, v0, [Ljava/lang/Object;

    move-object/from16 v25, v0

    const/16 v26, 0x0

    const/16 v27, 0x2

    aget-object v27, v15, v27

    invoke-virtual/range {v27 .. v27}, Ljava/lang/String;->getBytes()[B

    move-result-object v27

    move-object/from16 v0, v27

    invoke-virtual {v6, v0}, Ljavax/crypto/Mac;->doFinal([B)[B

    move-result-object v27

    const/16 v28, 0x2

    invoke-static/range {v27 .. v28}, Landroid/util/Base64;->encodeToString([BI)Ljava/lang/String;

    move-result-object v27

    aput-object v27, v25, v26

    const/16 v26, 0x1

    const/16 v27, 0x3

    aget-object v27, v15, v27

    aput-object v27, v25, v26

    invoke-static/range {v24 .. v25}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v24

    invoke-static/range {v23 .. v24}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 839
    :goto_1
    new-instance v23, Ljava/lang/StringBuilder;

    invoke-direct/range {v23 .. v23}, Ljava/lang/StringBuilder;-><init>()V

    move-object/from16 v0, v23

    move-object/from16 v1, v19

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v23

    move-object/from16 v0, v23

    invoke-virtual {v0, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v23

    invoke-virtual/range {v23 .. v23}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v16

    .line 841
    .local v16, server_client_token:Ljava/lang/String;
    invoke-virtual/range {v16 .. v16}, Ljava/lang/String;->getBytes()[B

    move-result-object v23

    move-object/from16 v0, v23

    invoke-virtual {v6, v0}, Ljavax/crypto/Mac;->doFinal([B)[B

    move-result-object v7

    .line 844
    .local v7, client_key:[B
    const-string v23, "OVMS"

    const-string v24, "Client version of the shared key is %s - (%s) %s"

    const/16 v25, 0x3

    move/from16 v0, v25

    new-array v0, v0, [Ljava/lang/Object;

    move-object/from16 v25, v0

    const/16 v26, 0x0

    aput-object v16, v25, v26

    const/16 v26, 0x1

    move-object/from16 v0, p0

    invoke-direct {v0, v7}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->toHex([B)Ljava/lang/String;

    move-result-object v27

    invoke-virtual/range {v27 .. v27}, Ljava/lang/String;->toLowerCase()Ljava/lang/String;

    move-result-object v27

    aput-object v27, v25, v26

    const/16 v26, 0x2

    const/16 v27, 0x2

    move/from16 v0, v27

    invoke-static {v7, v0}, Landroid/util/Base64;->encodeToString([BI)Ljava/lang/String;

    move-result-object v27

    aput-object v27, v25, v26

    invoke-static/range {v24 .. v25}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v24

    invoke-static/range {v23 .. v24}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 850
    const-string v23, "RC4"

    invoke-static/range {v23 .. v23}, Ljavax/crypto/Cipher;->getInstance(Ljava/lang/String;)Ljavax/crypto/Cipher;

    move-result-object v23

    move-object/from16 v0, v23

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->rxcipher:Ljavax/crypto/Cipher;

    .line 851
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->rxcipher:Ljavax/crypto/Cipher;

    move-object/from16 v23, v0

    const/16 v24, 0x2

    new-instance v25, Ljavax/crypto/spec/SecretKeySpec;

    const-string v26, "RC4"

    move-object/from16 v0, v25

    move-object/from16 v1, v26

    invoke-direct {v0, v7, v1}, Ljavax/crypto/spec/SecretKeySpec;-><init>([BLjava/lang/String;)V

    invoke-virtual/range {v23 .. v25}, Ljavax/crypto/Cipher;->init(ILjava/security/Key;)V

    .line 854
    const-string v23, "RC4"

    invoke-static/range {v23 .. v23}, Ljavax/crypto/Cipher;->getInstance(Ljava/lang/String;)Ljavax/crypto/Cipher;

    move-result-object v23

    move-object/from16 v0, v23

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->txcipher:Ljavax/crypto/Cipher;

    .line 855
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->txcipher:Ljavax/crypto/Cipher;

    move-object/from16 v23, v0

    const/16 v24, 0x1

    new-instance v25, Ljavax/crypto/spec/SecretKeySpec;

    const-string v26, "RC4"

    move-object/from16 v0, v25

    move-object/from16 v1, v26

    invoke-direct {v0, v7, v1}, Ljavax/crypto/spec/SecretKeySpec;-><init>([BLjava/lang/String;)V

    invoke-virtual/range {v23 .. v25}, Ljavax/crypto/Cipher;->init(ILjava/security/Key;)V

    .line 859
    const-string v13, ""

    .line 860
    .local v13, primeData:Ljava/lang/String;
    const/4 v10, 0x0

    :goto_2
    const/16 v23, 0x400

    move/from16 v0, v23

    if-ge v10, v0, :cond_3

    .line 861
    new-instance v23, Ljava/lang/StringBuilder;

    invoke-direct/range {v23 .. v23}, Ljava/lang/StringBuilder;-><init>()V

    move-object/from16 v0, v23

    invoke-virtual {v0, v13}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v23

    const-string v24, "0"

    invoke-virtual/range {v23 .. v24}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v23

    invoke-virtual/range {v23 .. v23}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v13

    .line 860
    add-int/lit8 v10, v10, 0x1

    goto :goto_2

    .line 812
    .end local v7           #client_key:[B
    .end local v13           #primeData:Ljava/lang/String;
    .end local v16           #server_client_token:Ljava/lang/String;
    .end local v17           #server_digest:[B
    .end local v18           #server_token:[B
    .end local v19           #server_tokenString:Ljava/lang/String;
    :catch_0
    move-exception v11

    .line 814
    .local v11, e:Ljava/lang/Exception;
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    move-object/from16 v23, v0

    move-object/from16 v0, v23

    iget-boolean v0, v0, Lcom/openvehicles/OVMS/OVMSActivity;->SuppressServerErrorDialog:Z

    move/from16 v23, v0

    if-nez v23, :cond_1

    .line 815
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    move-object/from16 v23, v0

    move-object/from16 v0, v23

    #calls: Lcom/openvehicles/OVMS/OVMSActivity;->notifyServerSocketError(Ljava/lang/Exception;)V
    invoke-static {v0, v11}, Lcom/openvehicles/OVMS/OVMSActivity;->access$800(Lcom/openvehicles/OVMS/OVMSActivity;Ljava/lang/Exception;)V

    .line 889
    .end local v5           #client_digest:Ljava/lang/String;
    .end local v6           #client_hmac:Ljavax/crypto/Mac;
    .end local v11           #e:Ljava/lang/Exception;
    .end local v12           #hashedBytes:[B
    .end local v15           #serverWelcomeMsg:[Ljava/lang/String;
    .end local v21           #sk:Ljavax/crypto/spec/SecretKeySpec;
    :cond_1
    :goto_3
    return-void

    .line 835
    .restart local v5       #client_digest:Ljava/lang/String;
    .restart local v6       #client_hmac:Ljavax/crypto/Mac;
    .restart local v12       #hashedBytes:[B
    .restart local v15       #serverWelcomeMsg:[Ljava/lang/String;
    .restart local v17       #server_digest:[B
    .restart local v18       #server_token:[B
    .restart local v19       #server_tokenString:Ljava/lang/String;
    .restart local v21       #sk:Ljavax/crypto/spec/SecretKeySpec;
    :cond_2
    const-string v23, "OVMS"

    const-string v24, "Server authentication OK."

    invoke-static/range {v23 .. v24}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_2
    .catch Ljava/net/UnknownHostException; {:try_start_2 .. :try_end_2} :catch_1
    .catch Ljava/io/IOException; {:try_start_2 .. :try_end_2} :catch_2
    .catch Ljava/lang/NullPointerException; {:try_start_2 .. :try_end_2} :catch_3
    .catch Ljava/lang/Exception; {:try_start_2 .. :try_end_2} :catch_4

    goto/16 :goto_1

    .line 872
    .end local v5           #client_digest:Ljava/lang/String;
    .end local v6           #client_hmac:Ljavax/crypto/Mac;
    .end local v12           #hashedBytes:[B
    .end local v15           #serverWelcomeMsg:[Ljava/lang/String;
    .end local v17           #server_digest:[B
    .end local v18           #server_token:[B
    .end local v19           #server_tokenString:Ljava/lang/String;
    .end local v21           #sk:Ljavax/crypto/spec/SecretKeySpec;
    :catch_1
    move-exception v11

    .line 873
    .local v11, e:Ljava/net/UnknownHostException;
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    move-object/from16 v23, v0

    move-object/from16 v0, v23

    #calls: Lcom/openvehicles/OVMS/OVMSActivity;->notifyServerSocketError(Ljava/lang/Exception;)V
    invoke-static {v0, v11}, Lcom/openvehicles/OVMS/OVMSActivity;->access$800(Lcom/openvehicles/OVMS/OVMSActivity;Ljava/lang/Exception;)V

    .line 874
    invoke-virtual {v11}, Ljava/net/UnknownHostException;->printStackTrace()V

    goto :goto_3

    .line 863
    .end local v11           #e:Ljava/net/UnknownHostException;
    .restart local v5       #client_digest:Ljava/lang/String;
    .restart local v6       #client_hmac:Ljavax/crypto/Mac;
    .restart local v7       #client_key:[B
    .restart local v12       #hashedBytes:[B
    .restart local v13       #primeData:Ljava/lang/String;
    .restart local v15       #serverWelcomeMsg:[Ljava/lang/String;
    .restart local v16       #server_client_token:Ljava/lang/String;
    .restart local v17       #server_digest:[B
    .restart local v18       #server_token:[B
    .restart local v19       #server_tokenString:Ljava/lang/String;
    .restart local v21       #sk:Ljavax/crypto/spec/SecretKeySpec;
    :cond_3
    :try_start_3
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->rxcipher:Ljavax/crypto/Cipher;

    move-object/from16 v23, v0

    invoke-virtual {v13}, Ljava/lang/String;->getBytes()[B

    move-result-object v24

    invoke-virtual/range {v23 .. v24}, Ljavax/crypto/Cipher;->update([B)[B

    .line 864
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->txcipher:Ljavax/crypto/Cipher;

    move-object/from16 v23, v0

    invoke-virtual {v13}, Ljava/lang/String;->getBytes()[B

    move-result-object v24

    invoke-virtual/range {v23 .. v24}, Ljavax/crypto/Cipher;->update([B)[B

    .line 866
    const-string v23, "OVMS"

    const-string v24, "Connected to %s. Ciphers initialized. Listening..."

    const/16 v25, 0x1

    move/from16 v0, v25

    new-array v0, v0, [Ljava/lang/Object;

    move-object/from16 v25, v0

    const/16 v26, 0x0

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    move-object/from16 v27, v0

    move-object/from16 v0, v27

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->ServerNameOrIP:Ljava/lang/String;

    move-object/from16 v27, v0

    aput-object v27, v25, v26

    invoke-static/range {v24 .. v25}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v24

    invoke-static/range {v23 .. v24}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 870
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    move-object/from16 v23, v0

    #calls: Lcom/openvehicles/OVMS/OVMSActivity;->loginComplete()V
    invoke-static/range {v23 .. v23}, Lcom/openvehicles/OVMS/OVMSActivity;->access$900(Lcom/openvehicles/OVMS/OVMSActivity;)V
    :try_end_3
    .catch Ljava/net/UnknownHostException; {:try_start_3 .. :try_end_3} :catch_1
    .catch Ljava/io/IOException; {:try_start_3 .. :try_end_3} :catch_2
    .catch Ljava/lang/NullPointerException; {:try_start_3 .. :try_end_3} :catch_3
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_3} :catch_4

    goto :goto_3

    .line 875
    .end local v5           #client_digest:Ljava/lang/String;
    .end local v6           #client_hmac:Ljavax/crypto/Mac;
    .end local v7           #client_key:[B
    .end local v12           #hashedBytes:[B
    .end local v13           #primeData:Ljava/lang/String;
    .end local v15           #serverWelcomeMsg:[Ljava/lang/String;
    .end local v16           #server_client_token:Ljava/lang/String;
    .end local v17           #server_digest:[B
    .end local v18           #server_token:[B
    .end local v19           #server_tokenString:Ljava/lang/String;
    .end local v21           #sk:Ljavax/crypto/spec/SecretKeySpec;
    :catch_2
    move-exception v11

    .line 876
    .local v11, e:Ljava/io/IOException;
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    move-object/from16 v23, v0

    move-object/from16 v0, v23

    #calls: Lcom/openvehicles/OVMS/OVMSActivity;->notifyServerSocketError(Ljava/lang/Exception;)V
    invoke-static {v0, v11}, Lcom/openvehicles/OVMS/OVMSActivity;->access$800(Lcom/openvehicles/OVMS/OVMSActivity;Ljava/lang/Exception;)V

    .line 877
    invoke-virtual {v11}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_3

    .line 878
    .end local v11           #e:Ljava/io/IOException;
    :catch_3
    move-exception v11

    .line 880
    .local v11, e:Ljava/lang/NullPointerException;
    invoke-virtual {v11}, Ljava/lang/NullPointerException;->printStackTrace()V

    goto :goto_3

    .line 881
    .end local v11           #e:Ljava/lang/NullPointerException;
    :catch_4
    move-exception v11

    .line 882
    .local v11, e:Ljava/lang/Exception;
    invoke-virtual {v11}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_3
.end method

.method private HandleMessage(Ljava/lang/String;)V
    .locals 22
    .parameter "msg"

    .prologue
    .line 542
    const/4 v14, 0x0

    move-object/from16 v0, p1

    invoke-virtual {v0, v14}, Ljava/lang/String;->charAt(I)C

    move-result v4

    .line 543
    .local v4, code:C
    const/4 v14, 0x1

    move-object/from16 v0, p1

    invoke-virtual {v0, v14}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object v1

    .line 545
    .local v1, cmd:Ljava/lang/String;
    const/16 v14, 0x45

    if-ne v4, v14, :cond_0

    .line 547
    const/4 v14, 0x1

    move-object/from16 v0, p1

    invoke-virtual {v0, v14}, Ljava/lang/String;->charAt(I)C

    move-result v9

    .line 548
    .local v9, innercode:C
    const/16 v14, 0x54

    if-ne v9, v14, :cond_2

    .line 550
    const-string v14, "TCP"

    new-instance v15, Ljava/lang/StringBuilder;

    invoke-direct {v15}, Ljava/lang/StringBuilder;-><init>()V

    const-string v16, "ET MSG Received: "

    invoke-virtual/range {v15 .. v16}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v15

    move-object/from16 v0, p1

    invoke-virtual {v15, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v15

    invoke-virtual {v15}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v15

    invoke-static {v14, v15}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 553
    const/4 v14, 0x2

    :try_start_0
    move-object/from16 v0, p1

    invoke-virtual {v0, v14}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object v10

    .line 554
    .local v10, pmToken:Ljava/lang/String;
    const-string v14, "HmacMD5"

    invoke-static {v14}, Ljavax/crypto/Mac;->getInstance(Ljava/lang/String;)Ljavax/crypto/Mac;

    move-result-object v11

    .line 555
    .local v11, pm_hmac:Ljavax/crypto/Mac;
    new-instance v13, Ljavax/crypto/spec/SecretKeySpec;

    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    iget-object v14, v14, Lcom/openvehicles/OVMS/CarData;->UserPass:Ljava/lang/String;

    invoke-virtual {v14}, Ljava/lang/String;->getBytes()[B

    move-result-object v14

    const-string v15, "HmacMD5"

    invoke-direct {v13, v14, v15}, Ljavax/crypto/spec/SecretKeySpec;-><init>([BLjava/lang/String;)V

    .line 557
    .local v13, sk:Ljavax/crypto/spec/SecretKeySpec;
    invoke-virtual {v11, v13}, Ljavax/crypto/Mac;->init(Ljava/security/Key;)V

    .line 558
    invoke-virtual {v10}, Ljava/lang/String;->getBytes()[B

    move-result-object v14

    invoke-virtual {v11, v14}, Ljavax/crypto/Mac;->doFinal([B)[B

    move-result-object v14

    move-object/from16 v0, p0

    iput-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->pmDigest:[B

    .line 559
    const-string v14, "OVMS"

    const-string v15, "Paranoid Mode Token Accepted. Entering Privacy Mode."

    invoke-static {v14, v15}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 601
    .end local v9           #innercode:C
    .end local v10           #pmToken:Ljava/lang/String;
    .end local v11           #pm_hmac:Ljavax/crypto/Mac;
    .end local v13           #sk:Ljavax/crypto/spec/SecretKeySpec;
    :cond_0
    :goto_0
    const-string v14, "TCP"

    new-instance v15, Ljava/lang/StringBuilder;

    invoke-direct {v15}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v15, v4}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    move-result-object v15

    const-string v16, " MSG Received: "

    invoke-virtual/range {v15 .. v16}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v15

    invoke-virtual {v15, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v15

    invoke-virtual {v15}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v15

    invoke-static {v14, v15}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 602
    sparse-switch v4, :sswitch_data_0

    .line 736
    :cond_1
    :goto_1
    return-void

    .line 560
    .restart local v9       #innercode:C
    :catch_0
    move-exception v8

    .line 561
    .local v8, e:Ljava/lang/Exception;
    const-string v14, "ERR"

    invoke-virtual {v8}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v15

    invoke-static {v14, v15}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 562
    invoke-virtual {v8}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_0

    .line 564
    .end local v8           #e:Ljava/lang/Exception;
    :cond_2
    const/16 v14, 0x4d

    if-ne v9, v14, :cond_0

    .line 566
    const-string v14, "TCP"

    new-instance v15, Ljava/lang/StringBuilder;

    invoke-direct {v15}, Ljava/lang/StringBuilder;-><init>()V

    const-string v16, "EM MSG Received: "

    invoke-virtual/range {v15 .. v16}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v15

    move-object/from16 v0, p1

    invoke-virtual {v15, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v15

    invoke-virtual {v15}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v15

    invoke-static {v14, v15}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 568
    const/4 v14, 0x2

    move-object/from16 v0, p1

    invoke-virtual {v0, v14}, Ljava/lang/String;->charAt(I)C

    move-result v4

    .line 569
    const/4 v14, 0x3

    move-object/from16 v0, p1

    invoke-virtual {v0, v14}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object v1

    .line 571
    const/4 v14, 0x2

    invoke-static {v1, v14}, Landroid/util/Base64;->decode(Ljava/lang/String;I)[B

    move-result-object v7

    .line 575
    .local v7, decodedCmd:[B
    :try_start_1
    const-string v14, "RC4"

    invoke-static {v14}, Ljavax/crypto/Cipher;->getInstance(Ljava/lang/String;)Ljavax/crypto/Cipher;

    move-result-object v14

    move-object/from16 v0, p0

    iput-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->pmcipher:Ljavax/crypto/Cipher;

    .line 576
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->pmcipher:Ljavax/crypto/Cipher;

    const/4 v15, 0x2

    new-instance v16, Ljavax/crypto/spec/SecretKeySpec;

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->pmDigest:[B

    move-object/from16 v17, v0

    const-string v18, "RC4"

    invoke-direct/range {v16 .. v18}, Ljavax/crypto/spec/SecretKeySpec;-><init>([BLjava/lang/String;)V

    invoke-virtual/range {v14 .. v16}, Ljavax/crypto/Cipher;->init(ILjava/security/Key;)V

    .line 581
    const-string v12, ""

    .line 582
    .local v12, primeData:Ljava/lang/String;
    const/4 v3, 0x0

    .local v3, cnt:I
    :goto_2
    const/16 v14, 0x400

    if-ge v3, v14, :cond_3

    .line 583
    new-instance v14, Ljava/lang/StringBuilder;

    invoke-direct {v14}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v14, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v14

    const-string v15, "0"

    invoke-virtual {v14, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v14

    invoke-virtual {v14}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v12

    .line 582
    add-int/lit8 v3, v3, 0x1

    goto :goto_2

    .line 585
    :cond_3
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->pmcipher:Ljavax/crypto/Cipher;

    invoke-virtual {v12}, Ljava/lang/String;->getBytes()[B

    move-result-object v15

    invoke-virtual {v14, v15}, Ljavax/crypto/Cipher;->update([B)[B

    .line 586
    new-instance v2, Ljava/lang/String;

    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->pmcipher:Ljavax/crypto/Cipher;

    invoke-virtual {v14, v7}, Ljavax/crypto/Cipher;->update([B)[B

    move-result-object v14

    invoke-direct {v2, v14}, Ljava/lang/String;-><init>([B)V
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_1

    .end local v1           #cmd:Ljava/lang/String;
    .local v2, cmd:Ljava/lang/String;
    move-object v1, v2

    .line 593
    .end local v2           #cmd:Ljava/lang/String;
    .end local v3           #cnt:I
    .end local v12           #primeData:Ljava/lang/String;
    .restart local v1       #cmd:Ljava/lang/String;
    :goto_3
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v14, v14, Lcom/openvehicles/OVMS/CarData;->ParanoidMode:Z

    if-nez v14, :cond_0

    .line 594
    const-string v14, "OVMS"

    const-string v15, "Paranoid Mode Detected"

    invoke-static {v14, v15}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 595
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v15, 0x1

    iput-boolean v15, v14, Lcom/openvehicles/OVMS/CarData;->ParanoidMode:Z

    .line 596
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v14}, Lcom/openvehicles/OVMS/OVMSActivity;->UpdateStatus()V

    goto/16 :goto_0

    .line 587
    :catch_1
    move-exception v8

    .line 588
    .restart local v8       #e:Ljava/lang/Exception;
    const-string v14, "ERR"

    invoke-virtual {v8}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v15

    invoke-static {v14, v15}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 589
    invoke-virtual {v8}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_3

    .line 605
    .end local v7           #decodedCmd:[B
    .end local v8           #e:Ljava/lang/Exception;
    .end local v9           #innercode:C
    :sswitch_0
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    invoke-static {v1}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v15

    iput v15, v14, Lcom/openvehicles/OVMS/CarData;->Data_CarsConnected:I

    .line 606
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v14}, Lcom/openvehicles/OVMS/OVMSActivity;->UpdateStatus()V

    goto/16 :goto_1

    .line 611
    :sswitch_1
    const-string v14, ",\\s*"

    invoke-virtual {v1, v14}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v6

    .line 612
    .local v6, dataParts:[Ljava/lang/String;
    array-length v14, v6

    const/16 v15, 0x8

    if-lt v14, v15, :cond_4

    .line 613
    const-string v14, "TCP"

    const-string v15, "S MSG Validated"

    invoke-static {v14, v15}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 614
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v15, 0x0

    aget-object v15, v6, v15

    invoke-static {v15}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v15

    iput v15, v14, Lcom/openvehicles/OVMS/CarData;->Data_SOC:I

    .line 615
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v15, 0x1

    aget-object v15, v6, v15

    invoke-virtual {v15}, Ljava/lang/String;->toString()Ljava/lang/String;

    move-result-object v15

    iput-object v15, v14, Lcom/openvehicles/OVMS/CarData;->Data_DistanceUnit:Ljava/lang/String;

    .line 616
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v15, 0x2

    aget-object v15, v6, v15

    invoke-static {v15}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v15

    iput v15, v14, Lcom/openvehicles/OVMS/CarData;->Data_LineVoltage:I

    .line 617
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v15, 0x3

    aget-object v15, v6, v15

    invoke-static {v15}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v15

    iput v15, v14, Lcom/openvehicles/OVMS/CarData;->Data_ChargeCurrent:I

    .line 618
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v15, 0x4

    aget-object v15, v6, v15

    invoke-virtual {v15}, Ljava/lang/String;->toString()Ljava/lang/String;

    move-result-object v15

    iput-object v15, v14, Lcom/openvehicles/OVMS/CarData;->Data_ChargeState:Ljava/lang/String;

    .line 619
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v15, 0x5

    aget-object v15, v6, v15

    invoke-virtual {v15}, Ljava/lang/String;->toString()Ljava/lang/String;

    move-result-object v15

    iput-object v15, v14, Lcom/openvehicles/OVMS/CarData;->Data_ChargeMode:Ljava/lang/String;

    .line 620
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v15, 0x6

    aget-object v15, v6, v15

    invoke-static {v15}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v15

    iput v15, v14, Lcom/openvehicles/OVMS/CarData;->Data_IdealRange:I

    .line 621
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v15, 0x7

    aget-object v15, v6, v15

    invoke-static {v15}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v15

    iput v15, v14, Lcom/openvehicles/OVMS/CarData;->Data_EstimatedRange:I

    .line 625
    :cond_4
    const-string v14, "TCP"

    new-instance v15, Ljava/lang/StringBuilder;

    invoke-direct {v15}, Ljava/lang/StringBuilder;-><init>()V

    const-string v16, "Notify Vehicle Status Update: "

    invoke-virtual/range {v15 .. v16}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v15

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    move-object/from16 v16, v0

    move-object/from16 v0, v16

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    move-object/from16 v16, v0

    invoke-virtual/range {v15 .. v16}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v15

    invoke-virtual {v15}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v15

    invoke-static {v14, v15}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 626
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    if-eqz v14, :cond_1

    .line 627
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v14}, Lcom/openvehicles/OVMS/OVMSActivity;->UpdateStatus()V

    goto/16 :goto_1

    .line 632
    .end local v6           #dataParts:[Ljava/lang/String;
    :sswitch_2
    invoke-virtual {v1}, Ljava/lang/String;->length()I

    move-result v14

    if-lez v14, :cond_5

    .line 633
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    new-instance v15, Ljava/util/Date;

    new-instance v16, Ljava/util/Date;

    invoke-direct/range {v16 .. v16}, Ljava/util/Date;-><init>()V

    invoke-virtual/range {v16 .. v16}, Ljava/util/Date;->getTime()J

    move-result-wide v16

    invoke-static {v1}, Ljava/lang/Long;->parseLong(Ljava/lang/String;)J

    move-result-wide v18

    const-wide/16 v20, 0x3e8

    mul-long v18, v18, v20

    sub-long v16, v16, v18

    invoke-direct/range {v15 .. v17}, Ljava/util/Date;-><init>(J)V

    iput-object v15, v14, Lcom/openvehicles/OVMS/CarData;->Data_LastCarUpdate:Ljava/util/Date;

    .line 635
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    invoke-static {v1}, Ljava/lang/Long;->parseLong(Ljava/lang/String;)J

    move-result-wide v15

    iput-wide v15, v14, Lcom/openvehicles/OVMS/CarData;->Data_LastCarUpdate_raw:J

    .line 636
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v14}, Lcom/openvehicles/OVMS/OVMSActivity;->UpdateStatus()V

    goto/16 :goto_1

    .line 638
    :cond_5
    const-string v14, "TCP"

    const-string v15, "T MSG Invalid"

    invoke-static {v14, v15}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_1

    .line 643
    :sswitch_3
    const-string v14, ",\\s*"

    invoke-virtual {v1, v14}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v6

    .line 644
    .restart local v6       #dataParts:[Ljava/lang/String;
    array-length v14, v6

    const/4 v15, 0x2

    if-lt v14, v15, :cond_1

    .line 645
    const-string v14, "TCP"

    const-string v15, "L MSG Validated"

    invoke-static {v14, v15}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 646
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v15, 0x0

    aget-object v15, v6, v15

    invoke-static {v15}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v15

    iput-wide v15, v14, Lcom/openvehicles/OVMS/CarData;->Data_Latitude:D

    .line 647
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v15, 0x1

    aget-object v15, v6, v15

    invoke-static {v15}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v15

    iput-wide v15, v14, Lcom/openvehicles/OVMS/CarData;->Data_Longitude:D

    .line 649
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v14}, Lcom/openvehicles/OVMS/OVMSActivity;->UpdateStatus()V

    goto/16 :goto_1

    .line 655
    .end local v6           #dataParts:[Ljava/lang/String;
    :sswitch_4
    const-string v14, ",\\s*"

    invoke-virtual {v1, v14}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v6

    .line 656
    .restart local v6       #dataParts:[Ljava/lang/String;
    array-length v14, v6

    const/16 v15, 0x9

    if-lt v14, v15, :cond_1

    .line 657
    const-string v14, "TCP"

    const-string v15, "D MSG Validated"

    invoke-static {v14, v15}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 658
    const/4 v14, 0x0

    aget-object v14, v6, v14

    invoke-static {v14}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v5

    .line 659
    .local v5, dataField:I
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    and-int/lit8 v14, v5, 0x1

    const/16 v16, 0x1

    move/from16 v0, v16

    if-ne v14, v0, :cond_6

    const/4 v14, 0x1

    :goto_4
    iput-boolean v14, v15, Lcom/openvehicles/OVMS/CarData;->Data_LeftDoorOpen:Z

    .line 660
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    and-int/lit8 v14, v5, 0x2

    const/16 v16, 0x2

    move/from16 v0, v16

    if-ne v14, v0, :cond_7

    const/4 v14, 0x1

    :goto_5
    iput-boolean v14, v15, Lcom/openvehicles/OVMS/CarData;->Data_RightDoorOpen:Z

    .line 661
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    and-int/lit8 v14, v5, 0x4

    const/16 v16, 0x4

    move/from16 v0, v16

    if-ne v14, v0, :cond_8

    const/4 v14, 0x1

    :goto_6
    iput-boolean v14, v15, Lcom/openvehicles/OVMS/CarData;->Data_ChargePortOpen:Z

    .line 662
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    and-int/lit8 v14, v5, 0x8

    const/16 v16, 0x8

    move/from16 v0, v16

    if-ne v14, v0, :cond_9

    const/4 v14, 0x1

    :goto_7
    iput-boolean v14, v15, Lcom/openvehicles/OVMS/CarData;->Data_PilotPresent:Z

    .line 663
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    and-int/lit8 v14, v5, 0x10

    const/16 v16, 0x10

    move/from16 v0, v16

    if-ne v14, v0, :cond_a

    const/4 v14, 0x1

    :goto_8
    iput-boolean v14, v15, Lcom/openvehicles/OVMS/CarData;->Data_Charging:Z

    .line 665
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    and-int/lit8 v14, v5, 0x40

    const/16 v16, 0x40

    move/from16 v0, v16

    if-ne v14, v0, :cond_b

    const/4 v14, 0x1

    :goto_9
    iput-boolean v14, v15, Lcom/openvehicles/OVMS/CarData;->Data_HandBrakeApplied:Z

    .line 666
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    and-int/lit16 v14, v5, 0x80

    const/16 v16, 0x80

    move/from16 v0, v16

    if-ne v14, v0, :cond_c

    const/4 v14, 0x1

    :goto_a
    iput-boolean v14, v15, Lcom/openvehicles/OVMS/CarData;->Data_CarPoweredON:Z

    .line 668
    const/4 v14, 0x1

    aget-object v14, v6, v14

    invoke-static {v14}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v5

    .line 669
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    and-int/lit8 v14, v5, 0x40

    const/16 v16, 0x40

    move/from16 v0, v16

    if-ne v14, v0, :cond_d

    const/4 v14, 0x1

    :goto_b
    iput-boolean v14, v15, Lcom/openvehicles/OVMS/CarData;->Data_BonnetOpen:Z

    .line 670
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    and-int/lit16 v14, v5, 0x80

    const/16 v16, 0x80

    move/from16 v0, v16

    if-ne v14, v0, :cond_e

    const/4 v14, 0x1

    :goto_c
    iput-boolean v14, v15, Lcom/openvehicles/OVMS/CarData;->Data_TrunkOpen:Z

    .line 672
    const/4 v14, 0x2

    aget-object v14, v6, v14

    invoke-static {v14}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v5

    .line 673
    move-object/from16 v0, p0

    iget-object v15, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v14, 0x4

    if-ne v5, v14, :cond_f

    const/4 v14, 0x1

    :goto_d
    iput-boolean v14, v15, Lcom/openvehicles/OVMS/CarData;->Data_CarLocked:Z

    .line 675
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v15, 0x3

    aget-object v15, v6, v15

    invoke-static {v15}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v15

    iput-wide v15, v14, Lcom/openvehicles/OVMS/CarData;->Data_TemperaturePEM:D

    .line 676
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v15, 0x4

    aget-object v15, v6, v15

    invoke-static {v15}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v15

    iput-wide v15, v14, Lcom/openvehicles/OVMS/CarData;->Data_TemperatureMotor:D

    .line 677
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v15, 0x5

    aget-object v15, v6, v15

    invoke-static {v15}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v15

    iput-wide v15, v14, Lcom/openvehicles/OVMS/CarData;->Data_TemperatureBattery:D

    .line 678
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v15, 0x6

    aget-object v15, v6, v15

    invoke-static {v15}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v15

    iput-wide v15, v14, Lcom/openvehicles/OVMS/CarData;->Data_TripMeter:D

    .line 679
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v15, 0x7

    aget-object v15, v6, v15

    invoke-static {v15}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v15

    iput-wide v15, v14, Lcom/openvehicles/OVMS/CarData;->Data_Odometer:D

    .line 680
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/16 v15, 0x8

    aget-object v15, v6, v15

    invoke-static {v15}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v15

    iput-wide v15, v14, Lcom/openvehicles/OVMS/CarData;->Data_Speed:D

    .line 683
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v14}, Lcom/openvehicles/OVMS/OVMSActivity;->UpdateStatus()V

    goto/16 :goto_1

    .line 659
    :cond_6
    const/4 v14, 0x0

    goto/16 :goto_4

    .line 660
    :cond_7
    const/4 v14, 0x0

    goto/16 :goto_5

    .line 661
    :cond_8
    const/4 v14, 0x0

    goto/16 :goto_6

    .line 662
    :cond_9
    const/4 v14, 0x0

    goto/16 :goto_7

    .line 663
    :cond_a
    const/4 v14, 0x0

    goto/16 :goto_8

    .line 665
    :cond_b
    const/4 v14, 0x0

    goto/16 :goto_9

    .line 666
    :cond_c
    const/4 v14, 0x0

    goto/16 :goto_a

    .line 669
    :cond_d
    const/4 v14, 0x0

    goto/16 :goto_b

    .line 670
    :cond_e
    const/4 v14, 0x0

    goto/16 :goto_c

    .line 673
    :cond_f
    const/4 v14, 0x0

    goto :goto_d

    .line 689
    .end local v5           #dataField:I
    .end local v6           #dataParts:[Ljava/lang/String;
    :sswitch_5
    const-string v14, ",\\s*"

    invoke-virtual {v1, v14}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v6

    .line 690
    .restart local v6       #dataParts:[Ljava/lang/String;
    array-length v14, v6

    const/4 v15, 0x3

    if-lt v14, v15, :cond_10

    .line 691
    const-string v14, "TCP"

    const-string v15, "F MSG Validated"

    invoke-static {v14, v15}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 692
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v15, 0x0

    aget-object v15, v6, v15

    invoke-virtual {v15}, Ljava/lang/String;->toString()Ljava/lang/String;

    move-result-object v15

    iput-object v15, v14, Lcom/openvehicles/OVMS/CarData;->Data_CarModuleFirmwareVersion:Ljava/lang/String;

    .line 693
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v15, 0x1

    aget-object v15, v6, v15

    invoke-virtual {v15}, Ljava/lang/String;->toString()Ljava/lang/String;

    move-result-object v15

    iput-object v15, v14, Lcom/openvehicles/OVMS/CarData;->Data_VIN:Ljava/lang/String;

    .line 694
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v15, 0x2

    aget-object v15, v6, v15

    invoke-virtual {v15}, Ljava/lang/String;->toString()Ljava/lang/String;

    move-result-object v15

    iput-object v15, v14, Lcom/openvehicles/OVMS/CarData;->Data_CarModuleGSMSignalLevel:Ljava/lang/String;

    .line 697
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v14}, Lcom/openvehicles/OVMS/OVMSActivity;->UpdateStatus()V

    .line 702
    .end local v6           #dataParts:[Ljava/lang/String;
    :cond_10
    :sswitch_6
    const-string v14, ",\\s*"

    invoke-virtual {v1, v14}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v6

    .line 703
    .restart local v6       #dataParts:[Ljava/lang/String;
    array-length v14, v6

    const/4 v15, 0x1

    if-lt v14, v15, :cond_1

    .line 704
    const-string v14, "TCP"

    const-string v15, "f MSG Validated"

    invoke-static {v14, v15}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 705
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v15, 0x0

    aget-object v15, v6, v15

    invoke-virtual {v15}, Ljava/lang/String;->toString()Ljava/lang/String;

    move-result-object v15

    iput-object v15, v14, Lcom/openvehicles/OVMS/CarData;->Data_OVMSServerFirmwareVersion:Ljava/lang/String;

    .line 708
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v14}, Lcom/openvehicles/OVMS/OVMSActivity;->UpdateStatus()V

    goto/16 :goto_1

    .line 714
    .end local v6           #dataParts:[Ljava/lang/String;
    :sswitch_7
    const-string v14, ",\\s*"

    invoke-virtual {v1, v14}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v6

    .line 715
    .restart local v6       #dataParts:[Ljava/lang/String;
    array-length v14, v6

    const/16 v15, 0x8

    if-lt v14, v15, :cond_1

    .line 716
    const-string v14, "TCP"

    const-string v15, "W MSG Validated"

    invoke-static {v14, v15}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 717
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v15, 0x0

    aget-object v15, v6, v15

    invoke-static {v15}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v15

    iput-wide v15, v14, Lcom/openvehicles/OVMS/CarData;->Data_FRWheelPressure:D

    .line 718
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v15, 0x1

    aget-object v15, v6, v15

    invoke-static {v15}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v15

    iput-wide v15, v14, Lcom/openvehicles/OVMS/CarData;->Data_FRWheelTemperature:D

    .line 719
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v15, 0x2

    aget-object v15, v6, v15

    invoke-static {v15}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v15

    iput-wide v15, v14, Lcom/openvehicles/OVMS/CarData;->Data_RRWheelPressure:D

    .line 720
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v15, 0x3

    aget-object v15, v6, v15

    invoke-static {v15}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v15

    iput-wide v15, v14, Lcom/openvehicles/OVMS/CarData;->Data_RRWheelTemperature:D

    .line 721
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v15, 0x4

    aget-object v15, v6, v15

    invoke-static {v15}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v15

    iput-wide v15, v14, Lcom/openvehicles/OVMS/CarData;->Data_FLWheelPressure:D

    .line 722
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v15, 0x5

    aget-object v15, v6, v15

    invoke-static {v15}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v15

    iput-wide v15, v14, Lcom/openvehicles/OVMS/CarData;->Data_FLWheelTemperature:D

    .line 723
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v15, 0x6

    aget-object v15, v6, v15

    invoke-static {v15}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v15

    iput-wide v15, v14, Lcom/openvehicles/OVMS/CarData;->Data_RLWheelPressure:D

    .line 724
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v15, 0x7

    aget-object v15, v6, v15

    invoke-static {v15}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v15

    iput-wide v15, v14, Lcom/openvehicles/OVMS/CarData;->Data_RLWheelTemperature:D

    .line 727
    move-object/from16 v0, p0

    iget-object v14, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v14}, Lcom/openvehicles/OVMS/OVMSActivity;->UpdateStatus()V

    goto/16 :goto_1

    .line 732
    .end local v6           #dataParts:[Ljava/lang/String;
    :sswitch_8
    const-string v14, "TCP"

    const-string v15, "Server acknowleged ping"

    invoke-static {v14, v15}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_1

    .line 602
    nop

    :sswitch_data_0
    .sparse-switch
        0x44 -> :sswitch_4
        0x46 -> :sswitch_5
        0x4c -> :sswitch_3
        0x53 -> :sswitch_1
        0x54 -> :sswitch_2
        0x57 -> :sswitch_7
        0x5a -> :sswitch_0
        0x61 -> :sswitch_8
        0x66 -> :sswitch_6
    .end sparse-switch
.end method

.method private toHex([B)Ljava/lang/String;
    .locals 4
    .parameter "bytes"

    .prologue
    const/4 v3, 0x1

    .line 892
    new-instance v0, Ljava/math/BigInteger;

    invoke-direct {v0, v3, p1}, Ljava/math/BigInteger;-><init>(I[B)V

    .line 893
    .local v0, bi:Ljava/math/BigInteger;
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "%0"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    array-length v2, p1

    shl-int/lit8 v2, v2, 0x1

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, "X"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    new-array v2, v3, [Ljava/lang/Object;

    const/4 v3, 0x0

    aput-object v0, v2, v3

    invoke-static {v1, v2}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v1

    return-object v1
.end method


# virtual methods
.method public ConnClose()V
    .locals 2

    .prologue
    .line 747
    :try_start_0
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Sock:Ljava/net/Socket;

    if-eqz v1, :cond_0

    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Sock:Ljava/net/Socket;

    invoke-virtual {v1}, Ljava/net/Socket;->isConnected()Z

    move-result v1

    if-eqz v1, :cond_0

    .line 748
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Sock:Ljava/net/Socket;

    invoke-virtual {v1}, Ljava/net/Socket;->close()V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 752
    :cond_0
    :goto_0
    return-void

    .line 749
    :catch_0
    move-exception v0

    .line 750
    .local v0, e:Ljava/lang/Exception;
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_0
.end method

.method public Ping()V
    .locals 4

    .prologue
    .line 739
    const-string v0, "TX: MP-0 A"

    .line 740
    .local v0, msg:Ljava/lang/String;
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Outputstream:Ljava/io/PrintWriter;

    iget-object v2, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->txcipher:Ljavax/crypto/Cipher;

    invoke-virtual {v0}, Ljava/lang/String;->getBytes()[B

    move-result-object v3

    invoke-virtual {v2, v3}, Ljavax/crypto/Cipher;->update([B)[B

    move-result-object v2

    const/4 v3, 0x2

    invoke-static {v2, v3}, Landroid/util/Base64;->encodeToString([BI)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 742
    const-string v1, "OVMS"

    invoke-static {v1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 743
    return-void
.end method

.method public SendCommand(Ljava/lang/String;)Z
    .locals 4
    .parameter "command"

    .prologue
    .line 755
    const-string v1, "OVMS"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "TX: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 756
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->isLoggedIn:Z
    invoke-static {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->access$100(Lcom/openvehicles/OVMS/OVMSActivity;)Z

    move-result v1

    if-nez v1, :cond_0

    .line 757
    const-string v1, "OVMS"

    const-string v2, "Server not ready. TX aborted."

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 758
    const/4 v1, 0x0

    .line 767
    :goto_0
    return v1

    .line 762
    :cond_0
    :try_start_0
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Outputstream:Ljava/io/PrintWriter;

    iget-object v2, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->txcipher:Ljavax/crypto/Cipher;

    invoke-virtual {p1}, Ljava/lang/String;->getBytes()[B

    move-result-object v3

    invoke-virtual {v2, v3}, Ljavax/crypto/Cipher;->update([B)[B

    move-result-object v2

    const/4 v3, 0x2

    invoke-static {v2, v3}, Landroid/util/Base64;->encodeToString([BI)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 767
    :goto_1
    const/4 v1, 0x1

    goto :goto_0

    .line 764
    :catch_0
    move-exception v0

    .line 765
    .local v0, e:Ljava/lang/Exception;
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #calls: Lcom/openvehicles/OVMS/OVMSActivity;->notifyServerSocketError(Ljava/lang/Exception;)V
    invoke-static {v1, v0}, Lcom/openvehicles/OVMS/OVMSActivity;->access$800(Lcom/openvehicles/OVMS/OVMSActivity;Ljava/lang/Exception;)V

    goto :goto_1
.end method

.method protected bridge synthetic doInBackground([Ljava/lang/Object;)Ljava/lang/Object;
    .locals 1
    .parameter "x0"

    .prologue
    .line 476
    check-cast p1, [Ljava/lang/Void;

    .end local p1
    invoke-virtual {p0, p1}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->doInBackground([Ljava/lang/Void;)Ljava/lang/Void;

    move-result-object v0

    return-object v0
.end method

.method protected varargs doInBackground([Ljava/lang/Void;)Ljava/lang/Void;
    .locals 8
    .parameter "arg0"

    .prologue
    const/4 v7, 0x0

    .line 501
    :try_start_0
    invoke-direct {p0}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->ConnInit()V

    .line 504
    :goto_0
    iget-object v3, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Sock:Ljava/net/Socket;

    invoke-virtual {v3}, Ljava/net/Socket;->isConnected()Z

    move-result v3

    if-eqz v3, :cond_1

    .line 506
    iget-object v3, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Inputstream:Ljava/io/BufferedReader;

    invoke-virtual {v3}, Ljava/io/BufferedReader;->readLine()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v2

    .line 507
    .local v2, rx:Ljava/lang/String;
    new-instance v3, Ljava/lang/String;

    iget-object v4, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->rxcipher:Ljavax/crypto/Cipher;

    const/4 v5, 0x0

    invoke-static {v2, v5}, Landroid/util/Base64;->decode(Ljava/lang/String;I)[B

    move-result-object v5

    invoke-virtual {v4, v5}, Ljavax/crypto/Cipher;->update([B)[B

    move-result-object v4

    invoke-direct {v3, v4}, Ljava/lang/String;-><init>([B)V

    invoke-virtual {v3}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v1

    .line 509
    .local v1, msg:Ljava/lang/String;
    const-string v3, "OVMS"

    const-string v4, "RX: %s (%s)"

    const/4 v5, 0x2

    new-array v5, v5, [Ljava/lang/Object;

    const/4 v6, 0x0

    aput-object v1, v5, v6

    const/4 v6, 0x1

    aput-object v2, v5, v6

    invoke-static {v4, v5}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 510
    const/4 v3, 0x0

    const/4 v4, 0x5

    invoke-virtual {v1, v3, v4}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v3

    const-string v4, "MP-0 "

    invoke-virtual {v3, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_0

    .line 511
    const/4 v3, 0x5

    invoke-virtual {v1, v3}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object v3

    invoke-direct {p0, v3}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->HandleMessage(Ljava/lang/String;)V
    :try_end_0
    .catch Ljava/net/SocketException; {:try_start_0 .. :try_end_0} :catch_1
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_2
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_3

    .line 517
    :goto_1
    const-wide/16 v3, 0x64

    const/4 v5, 0x0

    :try_start_1
    invoke-static {v3, v4, v5}, Ljava/lang/Thread;->sleep(JI)V
    :try_end_1
    .catch Ljava/lang/InterruptedException; {:try_start_1 .. :try_end_1} :catch_0
    .catch Ljava/net/SocketException; {:try_start_1 .. :try_end_1} :catch_1
    .catch Ljava/io/IOException; {:try_start_1 .. :try_end_1} :catch_2
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_3

    goto :goto_0

    .line 518
    :catch_0
    move-exception v3

    goto :goto_0

    .line 513
    :cond_0
    :try_start_2
    const-string v3, "OVMS"

    const-string v4, "Unknown protection scheme"

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_2
    .catch Ljava/net/SocketException; {:try_start_2 .. :try_end_2} :catch_1
    .catch Ljava/io/IOException; {:try_start_2 .. :try_end_2} :catch_2
    .catch Ljava/lang/Exception; {:try_start_2 .. :try_end_2} :catch_3

    goto :goto_1

    .line 522
    .end local v1           #msg:Ljava/lang/String;
    .end local v2           #rx:Ljava/lang/String;
    :catch_1
    move-exception v0

    .line 525
    .local v0, e:Ljava/net/SocketException;
    :try_start_3
    iget-object v3, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Sock:Ljava/net/Socket;

    invoke-virtual {v3}, Ljava/net/Socket;->close()V

    .line 526
    const/4 v3, 0x0

    iput-object v3, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Sock:Ljava/net/Socket;
    :try_end_3
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_3} :catch_4

    .line 529
    :goto_2
    invoke-direct {p0}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->ConnInit()V

    .line 538
    .end local v0           #e:Ljava/net/SocketException;
    :cond_1
    :goto_3
    return-object v7

    .line 530
    :catch_2
    move-exception v0

    .line 531
    .local v0, e:Ljava/io/IOException;
    iget-object v3, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    iget-boolean v3, v3, Lcom/openvehicles/OVMS/OVMSActivity;->SuppressServerErrorDialog:Z

    if-nez v3, :cond_2

    .line 532
    iget-object v3, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #calls: Lcom/openvehicles/OVMS/OVMSActivity;->notifyServerSocketError(Ljava/lang/Exception;)V
    invoke-static {v3, v0}, Lcom/openvehicles/OVMS/OVMSActivity;->access$800(Lcom/openvehicles/OVMS/OVMSActivity;Ljava/lang/Exception;)V

    .line 533
    :cond_2
    invoke-virtual {v0}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_3

    .line 534
    .end local v0           #e:Ljava/io/IOException;
    :catch_3
    move-exception v0

    .line 535
    .local v0, e:Ljava/lang/Exception;
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_3

    .line 527
    .local v0, e:Ljava/net/SocketException;
    :catch_4
    move-exception v3

    goto :goto_2
.end method

.method protected varargs onProgressUpdate([Ljava/lang/Integer;)V
    .locals 0
    .parameter "values"

    .prologue
    .line 495
    return-void
.end method

.method protected bridge synthetic onProgressUpdate([Ljava/lang/Object;)V
    .locals 0
    .parameter "x0"

    .prologue
    .line 476
    check-cast p1, [Ljava/lang/Integer;

    .end local p1
    invoke-virtual {p0, p1}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->onProgressUpdate([Ljava/lang/Integer;)V

    return-void
.end method
