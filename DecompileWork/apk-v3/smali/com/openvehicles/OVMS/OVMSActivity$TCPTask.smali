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

.field private pmcipher:Lcom/openvehicles/OVMS/RC4;

.field private rxcipher:Lcom/openvehicles/OVMS/RC4;

.field private socketMarkedClosed:Z

.field final synthetic this$0:Lcom/openvehicles/OVMS/OVMSActivity;

.field private txcipher:Lcom/openvehicles/OVMS/RC4;


# direct methods
.method public constructor <init>(Lcom/openvehicles/OVMS/OVMSActivity;Lcom/openvehicles/OVMS/CarData;)V
    .locals 1
    .parameter
    .parameter "car"

    .prologue
    .line 802
    iput-object p1, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-direct {p0}, Landroid/os/AsyncTask;-><init>()V

    .line 803
    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->carData:Lcom/openvehicles/OVMS/CarData;
    invoke-static {p1}, Lcom/openvehicles/OVMS/OVMSActivity;->access$0(Lcom/openvehicles/OVMS/OVMSActivity;)Lcom/openvehicles/OVMS/CarData;

    move-result-object v0

    iput-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    .line 804
    return-void
.end method

.method private ConnInit()V
    .locals 26

    .prologue
    .line 1495
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    move-object/from16 v21, v0

    move-object/from16 v0, v21

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->NetPass:Ljava/lang/String;

    move-object/from16 v19, v0

    .line 1496
    .local v19, shared_secret:Ljava/lang/String;
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    move-object/from16 v21, v0

    move-object/from16 v0, v21

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    move-object/from16 v20, v0

    .line 1497
    .local v20, vehicleID:Ljava/lang/String;
    const-string v3, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"

    .line 1498
    .local v3, b64tabString:Ljava/lang/String;
    invoke-virtual {v3}, Ljava/lang/String;->toCharArray()[C

    move-result-object v2

    .line 1501
    .local v2, b64tab:[C
    new-instance v13, Ljava/util/Random;

    invoke-direct {v13}, Ljava/util/Random;-><init>()V

    .line 1502
    .local v13, rnd:Ljava/util/Random;
    const-string v8, ""

    .line 1503
    .local v8, client_tokenString:Ljava/lang/String;
    const/4 v9, 0x0

    .local v9, cnt:I
    :goto_0
    const/16 v21, 0x16

    move/from16 v0, v21

    if-lt v9, v0, :cond_0

    .line 1506
    invoke-virtual {v8}, Ljava/lang/String;->getBytes()[B

    move-result-object v7

    .line 1508
    .local v7, client_token:[B
    :try_start_0
    new-instance v5, Lcom/openvehicles/OVMS/HMAC;

    const-string v21, "MD5"

    invoke-virtual/range {v19 .. v19}, Ljava/lang/String;->getBytes()[B

    move-result-object v22

    move-object/from16 v0, v21

    move-object/from16 v1, v22

    invoke-direct {v5, v0, v1}, Lcom/openvehicles/OVMS/HMAC;-><init>(Ljava/lang/String;[B)V

    .line 1510
    .local v5, client_hmac:Lcom/openvehicles/OVMS/HMAC;
    invoke-virtual {v5, v7}, Lcom/openvehicles/OVMS/HMAC;->update([B)V

    .line 1511
    invoke-virtual {v5}, Lcom/openvehicles/OVMS/HMAC;->sign()[B

    move-result-object v11

    .line 1512
    .local v11, hashedBytes:[B
    invoke-static {v11}, Lcom/openvehicles/OVMS/Base64;->encodeBytes([B)Ljava/lang/String;

    move-result-object v4

    .line 1514
    .local v4, client_digest:Ljava/lang/String;
    const-string v21, "TCP"

    new-instance v22, Ljava/lang/StringBuilder;

    const-string v23, "Connecting "

    invoke-direct/range {v22 .. v23}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    move-object/from16 v23, v0

    move-object/from16 v0, v23

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->ServerNameOrIP:Ljava/lang/String;

    move-object/from16 v23, v0

    invoke-virtual/range {v22 .. v23}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v22

    invoke-virtual/range {v22 .. v22}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v22

    invoke-static/range {v21 .. v22}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1515
    new-instance v21, Ljava/net/Socket;

    invoke-direct/range {v21 .. v21}, Ljava/net/Socket;-><init>()V

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Sock:Ljava/net/Socket;

    .line 1516
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Sock:Ljava/net/Socket;

    move-object/from16 v21, v0

    const/16 v22, 0x2710

    invoke-virtual/range {v21 .. v22}, Ljava/net/Socket;->setSoTimeout(I)V

    .line 1517
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Sock:Ljava/net/Socket;

    move-object/from16 v21, v0

    .line 1518
    new-instance v22, Ljava/net/InetSocketAddress;

    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    move-object/from16 v23, v0

    move-object/from16 v0, v23

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->ServerNameOrIP:Ljava/lang/String;

    move-object/from16 v23, v0

    const/16 v24, 0x1ad3

    invoke-direct/range {v22 .. v24}, Ljava/net/InetSocketAddress;-><init>(Ljava/lang/String;I)V

    .line 1519
    const/16 v23, 0x1388

    .line 1517
    invoke-virtual/range {v21 .. v23}, Ljava/net/Socket;->connect(Ljava/net/SocketAddress;I)V

    .line 1521
    new-instance v21, Ljava/io/PrintWriter;

    .line 1522
    new-instance v22, Ljava/io/BufferedWriter;

    .line 1523
    new-instance v23, Ljava/io/OutputStreamWriter;

    .line 1524
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Sock:Ljava/net/Socket;

    move-object/from16 v24, v0

    invoke-virtual/range {v24 .. v24}, Ljava/net/Socket;->getOutputStream()Ljava/io/OutputStream;

    move-result-object v24

    .line 1523
    invoke-direct/range {v23 .. v24}, Ljava/io/OutputStreamWriter;-><init>(Ljava/io/OutputStream;)V

    .line 1522
    invoke-direct/range {v22 .. v23}, Ljava/io/BufferedWriter;-><init>(Ljava/io/Writer;)V

    .line 1524
    const/16 v23, 0x1

    invoke-direct/range {v21 .. v23}, Ljava/io/PrintWriter;-><init>(Ljava/io/Writer;Z)V

    .line 1521
    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Outputstream:Ljava/io/PrintWriter;

    .line 1525
    const-string v21, "OVMS"

    const-string v22, "TX: MP-A 0 %s %s %s"

    const/16 v23, 0x3

    move/from16 v0, v23

    new-array v0, v0, [Ljava/lang/Object;

    move-object/from16 v23, v0

    const/16 v24, 0x0

    .line 1526
    aput-object v8, v23, v24

    const/16 v24, 0x1

    aput-object v4, v23, v24

    const/16 v24, 0x2

    aput-object v20, v23, v24

    .line 1525
    invoke-static/range {v22 .. v23}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v22

    invoke-static/range {v21 .. v22}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1528
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Outputstream:Ljava/io/PrintWriter;

    move-object/from16 v21, v0

    const-string v22, "MP-A 0 %s %s %s"

    const/16 v23, 0x3

    move/from16 v0, v23

    new-array v0, v0, [Ljava/lang/Object;

    move-object/from16 v23, v0

    const/16 v24, 0x0

    .line 1529
    aput-object v8, v23, v24

    const/16 v24, 0x1

    aput-object v4, v23, v24

    const/16 v24, 0x2

    aput-object v20, v23, v24

    .line 1528
    invoke-static/range {v22 .. v23}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v22

    invoke-virtual/range {v21 .. v22}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 1531
    new-instance v21, Ljava/io/BufferedReader;

    new-instance v22, Ljava/io/InputStreamReader;

    .line 1532
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Sock:Ljava/net/Socket;

    move-object/from16 v23, v0

    invoke-virtual/range {v23 .. v23}, Ljava/net/Socket;->getInputStream()Ljava/io/InputStream;

    move-result-object v23

    invoke-direct/range {v22 .. v23}, Ljava/io/InputStreamReader;-><init>(Ljava/io/InputStream;)V

    invoke-direct/range {v21 .. v22}, Ljava/io/BufferedReader;-><init>(Ljava/io/Reader;)V

    .line 1531
    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Inputstream:Ljava/io/BufferedReader;
    :try_end_0
    .catch Ljava/net/UnknownHostException; {:try_start_0 .. :try_end_0} :catch_1
    .catch Ljava/net/SocketTimeoutException; {:try_start_0 .. :try_end_0} :catch_2
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_3

    .line 1535
    const/4 v14, 0x0

    .line 1537
    .local v14, serverWelcomeMsg:[Ljava/lang/String;
    :try_start_1
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Inputstream:Ljava/io/BufferedReader;

    move-object/from16 v21, v0

    invoke-virtual/range {v21 .. v21}, Ljava/io/BufferedReader;->readLine()Ljava/lang/String;

    move-result-object v21

    invoke-virtual/range {v21 .. v21}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v21

    .line 1538
    const-string v22, "[ ]+"

    invoke-virtual/range {v21 .. v22}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_0
    .catch Ljava/net/UnknownHostException; {:try_start_1 .. :try_end_1} :catch_1
    .catch Ljava/net/SocketTimeoutException; {:try_start_1 .. :try_end_1} :catch_2

    move-result-object v14

    .line 1544
    :try_start_2
    const-string v21, "OVMS"

    const-string v22, "RX: %s %s %s %s"

    const/16 v23, 0x4

    move/from16 v0, v23

    new-array v0, v0, [Ljava/lang/Object;

    move-object/from16 v23, v0

    const/16 v24, 0x0

    .line 1545
    const/16 v25, 0x0

    aget-object v25, v14, v25

    aput-object v25, v23, v24

    const/16 v24, 0x1

    const/16 v25, 0x1

    aget-object v25, v14, v25

    aput-object v25, v23, v24

    const/16 v24, 0x2

    .line 1546
    const/16 v25, 0x2

    aget-object v25, v14, v25

    aput-object v25, v23, v24

    const/16 v24, 0x3

    const/16 v25, 0x3

    aget-object v25, v14, v25

    aput-object v25, v23, v24

    .line 1544
    invoke-static/range {v22 .. v23}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v22

    invoke-static/range {v21 .. v22}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1548
    const/16 v21, 0x2

    aget-object v18, v14, v21

    .line 1549
    .local v18, server_tokenString:Ljava/lang/String;
    invoke-virtual/range {v18 .. v18}, Ljava/lang/String;->getBytes()[B

    move-result-object v17

    .line 1550
    .local v17, server_token:[B
    const/16 v21, 0x3

    aget-object v21, v14, v21

    invoke-static/range {v21 .. v21}, Lcom/openvehicles/OVMS/Base64;->decode(Ljava/lang/String;)[B

    move-result-object v16

    .line 1552
    .local v16, server_digest:[B
    invoke-virtual {v5}, Lcom/openvehicles/OVMS/HMAC;->clear()V

    .line 1553
    move-object/from16 v0, v17

    invoke-virtual {v5, v0}, Lcom/openvehicles/OVMS/HMAC;->update([B)V

    .line 1554
    invoke-virtual {v5}, Lcom/openvehicles/OVMS/HMAC;->sign()[B

    move-result-object v21

    move-object/from16 v0, v21

    move-object/from16 v1, v16

    invoke-static {v0, v1}, Ljava/util/Arrays;->equals([B[B)Z

    move-result v21

    if-nez v21, :cond_1

    .line 1556
    const-string v21, "OVMS"

    .line 1557
    const-string v22, "Server authentication failed. Expected %s Got %s"

    const/16 v23, 0x2

    move/from16 v0, v23

    new-array v0, v0, [Ljava/lang/Object;

    move-object/from16 v23, v0

    const/16 v24, 0x0

    .line 1558
    invoke-virtual {v5}, Lcom/openvehicles/OVMS/HMAC;->sign()[B

    move-result-object v25

    invoke-static/range {v25 .. v25}, Lcom/openvehicles/OVMS/Base64;->encodeBytes([B)Ljava/lang/String;

    move-result-object v25

    aput-object v25, v23, v24

    const/16 v24, 0x1

    .line 1559
    const/16 v25, 0x3

    aget-object v25, v14, v25

    aput-object v25, v23, v24

    .line 1556
    invoke-static/range {v22 .. v23}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v22

    invoke-static/range {v21 .. v22}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1565
    :goto_1
    invoke-virtual {v5}, Lcom/openvehicles/OVMS/HMAC;->clear()V

    .line 1566
    new-instance v21, Ljava/lang/StringBuilder;

    invoke-static/range {v18 .. v18}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v22

    invoke-direct/range {v21 .. v22}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 1567
    move-object/from16 v0, v21

    invoke-virtual {v0, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v21

    .line 1566
    invoke-virtual/range {v21 .. v21}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v15

    .line 1568
    .local v15, server_client_token:Ljava/lang/String;
    invoke-virtual {v15}, Ljava/lang/String;->getBytes()[B

    move-result-object v21

    move-object/from16 v0, v21

    invoke-virtual {v5, v0}, Lcom/openvehicles/OVMS/HMAC;->update([B)V

    .line 1569
    invoke-virtual {v5}, Lcom/openvehicles/OVMS/HMAC;->sign()[B

    move-result-object v6

    .line 1571
    .local v6, client_key:[B
    const-string v21, "OVMS"

    .line 1572
    const-string v22, "Client version of the shared key is %s - (%s) %s"

    const/16 v23, 0x3

    move/from16 v0, v23

    new-array v0, v0, [Ljava/lang/Object;

    move-object/from16 v23, v0

    const/16 v24, 0x0

    .line 1573
    aput-object v15, v23, v24

    const/16 v24, 0x1

    move-object/from16 v0, p0

    invoke-direct {v0, v6}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->toHex([B)Ljava/lang/String;

    move-result-object v25

    invoke-virtual/range {v25 .. v25}, Ljava/lang/String;->toLowerCase()Ljava/lang/String;

    move-result-object v25

    aput-object v25, v23, v24

    const/16 v24, 0x2

    .line 1574
    invoke-static {v6}, Lcom/openvehicles/OVMS/Base64;->encodeBytes([B)Ljava/lang/String;

    move-result-object v25

    aput-object v25, v23, v24

    .line 1571
    invoke-static/range {v22 .. v23}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v22

    invoke-static/range {v21 .. v22}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1577
    new-instance v21, Lcom/openvehicles/OVMS/RC4;

    move-object/from16 v0, v21

    invoke-direct {v0, v6}, Lcom/openvehicles/OVMS/RC4;-><init>([B)V

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->rxcipher:Lcom/openvehicles/OVMS/RC4;

    .line 1578
    new-instance v21, Lcom/openvehicles/OVMS/RC4;

    move-object/from16 v0, v21

    invoke-direct {v0, v6}, Lcom/openvehicles/OVMS/RC4;-><init>([B)V

    move-object/from16 v0, v21

    move-object/from16 v1, p0

    iput-object v0, v1, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->txcipher:Lcom/openvehicles/OVMS/RC4;

    .line 1581
    const-string v12, ""

    .line 1582
    .local v12, primeData:Ljava/lang/String;
    const/4 v9, 0x0

    :goto_2
    const/16 v21, 0x400

    move/from16 v0, v21

    if-lt v9, v0, :cond_2

    .line 1585
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->rxcipher:Lcom/openvehicles/OVMS/RC4;

    move-object/from16 v21, v0

    invoke-virtual {v12}, Ljava/lang/String;->getBytes()[B

    move-result-object v22

    invoke-virtual/range {v21 .. v22}, Lcom/openvehicles/OVMS/RC4;->rc4([B)[B

    .line 1586
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->txcipher:Lcom/openvehicles/OVMS/RC4;

    move-object/from16 v21, v0

    invoke-virtual {v12}, Ljava/lang/String;->getBytes()[B

    move-result-object v22

    invoke-virtual/range {v21 .. v22}, Lcom/openvehicles/OVMS/RC4;->rc4([B)[B

    .line 1588
    const-string v21, "OVMS"

    .line 1589
    const-string v22, "Connected to %s. Ciphers initialized. Listening..."

    const/16 v23, 0x1

    move/from16 v0, v23

    new-array v0, v0, [Ljava/lang/Object;

    move-object/from16 v23, v0

    const/16 v24, 0x0

    .line 1590
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    move-object/from16 v25, v0

    move-object/from16 v0, v25

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->ServerNameOrIP:Ljava/lang/String;

    move-object/from16 v25, v0

    aput-object v25, v23, v24

    .line 1588
    invoke-static/range {v22 .. v23}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v22

    invoke-static/range {v21 .. v22}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1592
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    move-object/from16 v21, v0

    #calls: Lcom/openvehicles/OVMS/OVMSActivity;->loginComplete()V
    invoke-static/range {v21 .. v21}, Lcom/openvehicles/OVMS/OVMSActivity;->access$18(Lcom/openvehicles/OVMS/OVMSActivity;)V
    :try_end_2
    .catch Ljava/net/UnknownHostException; {:try_start_2 .. :try_end_2} :catch_1
    .catch Ljava/net/SocketTimeoutException; {:try_start_2 .. :try_end_2} :catch_2
    .catch Ljava/lang/Exception; {:try_start_2 .. :try_end_2} :catch_3

    .line 1601
    .end local v4           #client_digest:Ljava/lang/String;
    .end local v5           #client_hmac:Lcom/openvehicles/OVMS/HMAC;
    .end local v6           #client_key:[B
    .end local v11           #hashedBytes:[B
    .end local v12           #primeData:Ljava/lang/String;
    .end local v14           #serverWelcomeMsg:[Ljava/lang/String;
    .end local v15           #server_client_token:Ljava/lang/String;
    .end local v16           #server_digest:[B
    .end local v17           #server_token:[B
    .end local v18           #server_tokenString:Ljava/lang/String;
    :goto_3
    return-void

    .line 1504
    .end local v7           #client_token:[B
    :cond_0
    new-instance v21, Ljava/lang/StringBuilder;

    invoke-static {v8}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v22

    invoke-direct/range {v21 .. v22}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    array-length v0, v2

    move/from16 v22, v0

    add-int/lit8 v22, v22, -0x1

    move/from16 v0, v22

    invoke-virtual {v13, v0}, Ljava/util/Random;->nextInt(I)I

    move-result v22

    aget-char v22, v2, v22

    invoke-virtual/range {v21 .. v22}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    move-result-object v21

    invoke-virtual/range {v21 .. v21}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    .line 1503
    add-int/lit8 v9, v9, 0x1

    goto/16 :goto_0

    .line 1539
    .restart local v4       #client_digest:Ljava/lang/String;
    .restart local v5       #client_hmac:Lcom/openvehicles/OVMS/HMAC;
    .restart local v7       #client_token:[B
    .restart local v11       #hashedBytes:[B
    .restart local v14       #serverWelcomeMsg:[Ljava/lang/String;
    :catch_0
    move-exception v10

    .line 1541
    .local v10, e:Ljava/lang/Exception;
    :try_start_3
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    move-object/from16 v21, v0

    move-object/from16 v0, v21

    #calls: Lcom/openvehicles/OVMS/OVMSActivity;->notifyServerSocketError(Ljava/lang/Exception;)V
    invoke-static {v0, v10}, Lcom/openvehicles/OVMS/OVMSActivity;->access$12(Lcom/openvehicles/OVMS/OVMSActivity;Ljava/lang/Exception;)V
    :try_end_3
    .catch Ljava/net/UnknownHostException; {:try_start_3 .. :try_end_3} :catch_1
    .catch Ljava/net/SocketTimeoutException; {:try_start_3 .. :try_end_3} :catch_2
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_3} :catch_3

    goto :goto_3

    .line 1594
    .end local v4           #client_digest:Ljava/lang/String;
    .end local v5           #client_hmac:Lcom/openvehicles/OVMS/HMAC;
    .end local v10           #e:Ljava/lang/Exception;
    .end local v11           #hashedBytes:[B
    .end local v14           #serverWelcomeMsg:[Ljava/lang/String;
    :catch_1
    move-exception v10

    .line 1595
    .local v10, e:Ljava/net/UnknownHostException;
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    move-object/from16 v21, v0

    move-object/from16 v0, v21

    #calls: Lcom/openvehicles/OVMS/OVMSActivity;->notifyServerSocketError(Ljava/lang/Exception;)V
    invoke-static {v0, v10}, Lcom/openvehicles/OVMS/OVMSActivity;->access$12(Lcom/openvehicles/OVMS/OVMSActivity;Ljava/lang/Exception;)V

    goto :goto_3

    .line 1561
    .end local v10           #e:Ljava/net/UnknownHostException;
    .restart local v4       #client_digest:Ljava/lang/String;
    .restart local v5       #client_hmac:Lcom/openvehicles/OVMS/HMAC;
    .restart local v11       #hashedBytes:[B
    .restart local v14       #serverWelcomeMsg:[Ljava/lang/String;
    .restart local v16       #server_digest:[B
    .restart local v17       #server_token:[B
    .restart local v18       #server_tokenString:Ljava/lang/String;
    :cond_1
    :try_start_4
    const-string v21, "OVMS"

    const-string v22, "Server authentication OK."

    invoke-static/range {v21 .. v22}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_4
    .catch Ljava/net/UnknownHostException; {:try_start_4 .. :try_end_4} :catch_1
    .catch Ljava/net/SocketTimeoutException; {:try_start_4 .. :try_end_4} :catch_2
    .catch Ljava/lang/Exception; {:try_start_4 .. :try_end_4} :catch_3

    goto/16 :goto_1

    .line 1596
    .end local v4           #client_digest:Ljava/lang/String;
    .end local v5           #client_hmac:Lcom/openvehicles/OVMS/HMAC;
    .end local v11           #hashedBytes:[B
    .end local v14           #serverWelcomeMsg:[Ljava/lang/String;
    .end local v16           #server_digest:[B
    .end local v17           #server_token:[B
    .end local v18           #server_tokenString:Ljava/lang/String;
    :catch_2
    move-exception v10

    .line 1597
    .local v10, e:Ljava/net/SocketTimeoutException;
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    move-object/from16 v21, v0

    move-object/from16 v0, v21

    #calls: Lcom/openvehicles/OVMS/OVMSActivity;->notifyServerSocketError(Ljava/lang/Exception;)V
    invoke-static {v0, v10}, Lcom/openvehicles/OVMS/OVMSActivity;->access$12(Lcom/openvehicles/OVMS/OVMSActivity;Ljava/lang/Exception;)V

    goto :goto_3

    .line 1583
    .end local v10           #e:Ljava/net/SocketTimeoutException;
    .restart local v4       #client_digest:Ljava/lang/String;
    .restart local v5       #client_hmac:Lcom/openvehicles/OVMS/HMAC;
    .restart local v6       #client_key:[B
    .restart local v11       #hashedBytes:[B
    .restart local v12       #primeData:Ljava/lang/String;
    .restart local v14       #serverWelcomeMsg:[Ljava/lang/String;
    .restart local v15       #server_client_token:Ljava/lang/String;
    .restart local v16       #server_digest:[B
    .restart local v17       #server_token:[B
    .restart local v18       #server_tokenString:Ljava/lang/String;
    :cond_2
    :try_start_5
    new-instance v21, Ljava/lang/StringBuilder;

    invoke-static {v12}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v22

    invoke-direct/range {v21 .. v22}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v22, "0"

    invoke-virtual/range {v21 .. v22}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v21

    invoke-virtual/range {v21 .. v21}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;
    :try_end_5
    .catch Ljava/net/UnknownHostException; {:try_start_5 .. :try_end_5} :catch_1
    .catch Ljava/net/SocketTimeoutException; {:try_start_5 .. :try_end_5} :catch_2
    .catch Ljava/lang/Exception; {:try_start_5 .. :try_end_5} :catch_3

    move-result-object v12

    .line 1582
    add-int/lit8 v9, v9, 0x1

    goto/16 :goto_2

    .line 1598
    .end local v4           #client_digest:Ljava/lang/String;
    .end local v5           #client_hmac:Lcom/openvehicles/OVMS/HMAC;
    .end local v6           #client_key:[B
    .end local v11           #hashedBytes:[B
    .end local v12           #primeData:Ljava/lang/String;
    .end local v14           #serverWelcomeMsg:[Ljava/lang/String;
    .end local v15           #server_client_token:Ljava/lang/String;
    .end local v16           #server_digest:[B
    .end local v17           #server_token:[B
    .end local v18           #server_tokenString:Ljava/lang/String;
    :catch_3
    move-exception v10

    .line 1599
    .local v10, e:Ljava/lang/Exception;
    move-object/from16 v0, p0

    iget-object v0, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    move-object/from16 v21, v0

    move-object/from16 v0, v21

    #calls: Lcom/openvehicles/OVMS/OVMSActivity;->notifyServerSocketError(Ljava/lang/Exception;)V
    invoke-static {v0, v10}, Lcom/openvehicles/OVMS/OVMSActivity;->access$12(Lcom/openvehicles/OVMS/OVMSActivity;Ljava/lang/Exception;)V

    goto :goto_3
.end method

.method private notifyCommandResponse(Ljava/lang/String;)V
    .locals 3
    .parameter "message"

    .prologue
    .line 896
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    if-eqz v0, :cond_0

    .line 897
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    new-instance v1, Lcom/openvehicles/OVMS/OVMSActivity$ServerCommandResponseHandler;

    iget-object v2, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-direct {v1, v2, p1}, Lcom/openvehicles/OVMS/OVMSActivity$ServerCommandResponseHandler;-><init>(Lcom/openvehicles/OVMS/OVMSActivity;Ljava/lang/String;)V

    #setter for: Lcom/openvehicles/OVMS/OVMSActivity;->mCommandResponse:Lcom/openvehicles/OVMS/OVMSActivity$ServerCommandResponseHandler;
    invoke-static {v0, v1}, Lcom/openvehicles/OVMS/OVMSActivity;->access$15(Lcom/openvehicles/OVMS/OVMSActivity;Lcom/openvehicles/OVMS/OVMSActivity$ServerCommandResponseHandler;)V

    .line 898
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->UIHandler:Landroid/os/Handler;
    invoke-static {v0}, Lcom/openvehicles/OVMS/OVMSActivity;->access$13(Lcom/openvehicles/OVMS/OVMSActivity;)Landroid/os/Handler;

    move-result-object v0

    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->mCommandResponse:Lcom/openvehicles/OVMS/OVMSActivity$ServerCommandResponseHandler;
    invoke-static {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->access$16(Lcom/openvehicles/OVMS/OVMSActivity;)Lcom/openvehicles/OVMS/OVMSActivity$ServerCommandResponseHandler;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 900
    :cond_0
    return-void
.end method

.method private processMessage(Ljava/lang/String;)V
    .locals 30
    .parameter "msg"

    .prologue
    .line 903
    const/4 v1, 0x0

    move-object/from16 v0, p1

    invoke-virtual {v0, v1}, Ljava/lang/String;->charAt(I)C

    move-result v16

    .line 904
    .local v16, code:C
    const/4 v1, 0x1

    move-object/from16 v0, p1

    invoke-virtual {v0, v1}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object v11

    .line 906
    .local v11, cmd:Ljava/lang/String;
    const/16 v1, 0x45

    move/from16 v0, v16

    if-ne v0, v1, :cond_0

    .line 908
    const/4 v1, 0x1

    move-object/from16 v0, p1

    invoke-virtual {v0, v1}, Ljava/lang/String;->charAt(I)C

    move-result v24

    .line 909
    .local v24, innercode:C
    const/16 v1, 0x54

    move/from16 v0, v24

    if-ne v0, v1, :cond_2

    .line 911
    const-string v1, "TCP"

    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "ET MSG Received: "

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    move-object/from16 v0, p1

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 914
    const/4 v1, 0x2

    :try_start_0
    move-object/from16 v0, p1

    invoke-virtual {v0, v1}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object v26

    .line 915
    .local v26, pmToken:Ljava/lang/String;
    new-instance v27, Lcom/openvehicles/OVMS/HMAC;

    const-string v1, "MD5"

    .line 916
    move-object/from16 v0, p0

    iget-object v2, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    iget-object v2, v2, Lcom/openvehicles/OVMS/CarData;->RegPass:Ljava/lang/String;

    invoke-virtual {v2}, Ljava/lang/String;->getBytes()[B

    move-result-object v2

    .line 915
    move-object/from16 v0, v27

    invoke-direct {v0, v1, v2}, Lcom/openvehicles/OVMS/HMAC;-><init>(Ljava/lang/String;[B)V

    .line 917
    .local v27, pm_hmac:Lcom/openvehicles/OVMS/HMAC;
    invoke-virtual/range {v26 .. v26}, Ljava/lang/String;->getBytes()[B

    move-result-object v1

    move-object/from16 v0, v27

    invoke-virtual {v0, v1}, Lcom/openvehicles/OVMS/HMAC;->update([B)V

    .line 918
    invoke-virtual/range {v27 .. v27}, Lcom/openvehicles/OVMS/HMAC;->sign()[B

    move-result-object v1

    move-object/from16 v0, p0

    iput-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->pmDigest:[B

    .line 919
    const-string v1, "OVMS"

    .line 920
    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "Paranoid Mode Token Accepted. Entering Privacy Mode. (pmDigest = "

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 921
    move-object/from16 v0, p0

    iget-object v3, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->pmDigest:[B

    invoke-static {v3}, Lcom/openvehicles/OVMS/Base64;->encodeBytes([B)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, ")"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    .line 920
    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    .line 919
    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 959
    .end local v24           #innercode:C
    .end local v26           #pmToken:Ljava/lang/String;
    .end local v27           #pm_hmac:Lcom/openvehicles/OVMS/HMAC;
    :cond_0
    :goto_0
    const-string v1, "TCP"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-static/range {v16 .. v16}, Ljava/lang/String;->valueOf(C)Ljava/lang/String;

    move-result-object v3

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v3, " MSG Received: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 960
    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "[RX] "

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    move/from16 v0, v16

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v1}, Lcom/openvehicles/OVMS/DataLog;->Log(Ljava/lang/String;)V

    .line 962
    sparse-switch v16, :sswitch_data_0

    .line 1402
    :cond_1
    :goto_1
    return-void

    .line 922
    .restart local v24       #innercode:C
    :catch_0
    move-exception v21

    .line 923
    .local v21, e:Ljava/lang/Exception;
    const-string v1, "ERR"

    invoke-virtual/range {v21 .. v21}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 924
    invoke-virtual/range {v21 .. v21}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_0

    .line 926
    .end local v21           #e:Ljava/lang/Exception;
    :cond_2
    const/16 v1, 0x4d

    move/from16 v0, v24

    if-ne v0, v1, :cond_0

    .line 928
    const-string v1, "TCP"

    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "EM MSG Received: "

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    move-object/from16 v0, p1

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 930
    const/4 v1, 0x2

    move-object/from16 v0, p1

    invoke-virtual {v0, v1}, Ljava/lang/String;->charAt(I)C

    move-result v16

    .line 931
    const/4 v1, 0x3

    move-object/from16 v0, p1

    invoke-virtual {v0, v1}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object v11

    .line 934
    :try_start_1
    invoke-static {v11}, Lcom/openvehicles/OVMS/Base64;->decode(Ljava/lang/String;)[B

    move-result-object v20

    .line 936
    .local v20, decodedCmd:[B
    new-instance v1, Lcom/openvehicles/OVMS/RC4;

    move-object/from16 v0, p0

    iget-object v2, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->pmDigest:[B

    invoke-direct {v1, v2}, Lcom/openvehicles/OVMS/RC4;-><init>([B)V

    move-object/from16 v0, p0

    iput-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->pmcipher:Lcom/openvehicles/OVMS/RC4;

    .line 939
    const-string v28, ""

    .line 940
    .local v28, primeData:Ljava/lang/String;
    const/4 v15, 0x0

    .local v15, cnt:I
    :goto_2
    const/16 v1, 0x400

    if-lt v15, v1, :cond_3

    .line 943
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->pmcipher:Lcom/openvehicles/OVMS/RC4;

    invoke-virtual/range {v28 .. v28}, Ljava/lang/String;->getBytes()[B

    move-result-object v2

    invoke-virtual {v1, v2}, Lcom/openvehicles/OVMS/RC4;->rc4([B)[B

    .line 944
    new-instance v12, Ljava/lang/String;

    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->pmcipher:Lcom/openvehicles/OVMS/RC4;

    move-object/from16 v0, v20

    invoke-virtual {v1, v0}, Lcom/openvehicles/OVMS/RC4;->rc4([B)[B

    move-result-object v1

    invoke-direct {v12, v1}, Ljava/lang/String;-><init>([B)V
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_1

    .end local v11           #cmd:Ljava/lang/String;
    .local v12, cmd:Ljava/lang/String;
    move-object v11, v12

    .line 951
    .end local v12           #cmd:Ljava/lang/String;
    .end local v15           #cnt:I
    .end local v20           #decodedCmd:[B
    .end local v28           #primeData:Ljava/lang/String;
    .restart local v11       #cmd:Ljava/lang/String;
    :goto_3
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v1, v1, Lcom/openvehicles/OVMS/CarData;->ParanoidMode:Z

    if-nez v1, :cond_0

    .line 952
    const-string v1, "OVMS"

    const-string v2, "Paranoid Mode Detected"

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 953
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v2, 0x1

    iput-boolean v2, v1, Lcom/openvehicles/OVMS/CarData;->ParanoidMode:Z

    .line 954
    invoke-direct/range {p0 .. p0}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->refreshUI()V

    goto/16 :goto_0

    .line 941
    .restart local v15       #cnt:I
    .restart local v20       #decodedCmd:[B
    .restart local v28       #primeData:Ljava/lang/String;
    :cond_3
    :try_start_2
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-static/range {v28 .. v28}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v2, "0"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;
    :try_end_2
    .catch Ljava/lang/Exception; {:try_start_2 .. :try_end_2} :catch_1

    move-result-object v28

    .line 940
    add-int/lit8 v15, v15, 0x1

    goto :goto_2

    .line 945
    .end local v15           #cnt:I
    .end local v20           #decodedCmd:[B
    .end local v28           #primeData:Ljava/lang/String;
    :catch_1
    move-exception v21

    .line 946
    .restart local v21       #e:Ljava/lang/Exception;
    const-string v1, "ERR"

    invoke-virtual/range {v21 .. v21}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 947
    invoke-virtual/range {v21 .. v21}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_3

    .line 965
    .end local v21           #e:Ljava/lang/Exception;
    .end local v24           #innercode:C
    :sswitch_0
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    invoke-static {v11}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v2

    iput v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_CarsConnected:I

    .line 966
    invoke-direct/range {p0 .. p0}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->refreshUI()V

    goto/16 :goto_1

    .line 971
    :sswitch_1
    const-string v1, ",\\s*"

    invoke-virtual {v11, v1}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v18

    .line 972
    .local v18, dataParts:[Ljava/lang/String;
    move-object/from16 v0, v18

    array-length v1, v0

    const/16 v2, 0x8

    if-lt v1, v2, :cond_4

    .line 973
    const-string v1, "TCP"

    const-string v2, "S MSG Validated"

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 974
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v2, 0x0

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v2

    iput v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_SOC:I

    .line 975
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v2, 0x1

    aget-object v2, v18, v2

    invoke-virtual {v2}, Ljava/lang/String;->toString()Ljava/lang/String;

    move-result-object v2

    iput-object v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_DistanceUnit:Ljava/lang/String;

    .line 976
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v2, 0x2

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v2

    iput v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_LineVoltage:I

    .line 977
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v2, 0x3

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v2

    iput v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_ChargeCurrent:I

    .line 978
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v2, 0x4

    aget-object v2, v18, v2

    invoke-virtual {v2}, Ljava/lang/String;->toString()Ljava/lang/String;

    move-result-object v2

    iput-object v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_ChargeState:Ljava/lang/String;

    .line 979
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v2, 0x5

    aget-object v2, v18, v2

    invoke-virtual {v2}, Ljava/lang/String;->toString()Ljava/lang/String;

    move-result-object v2

    iput-object v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_ChargeMode:Ljava/lang/String;

    .line 980
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v2, 0x6

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v2

    iput v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_IdealRange:I

    .line 981
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    .line 982
    const/4 v2, 0x7

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v2

    .line 981
    iput v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_EstimatedRange:I

    .line 985
    :cond_4
    move-object/from16 v0, v18

    array-length v1, v0

    const/16 v2, 0xe

    if-lt v1, v2, :cond_5

    .line 986
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    .line 987
    const/16 v2, 0x8

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v2

    .line 986
    iput v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_ChargeAmpsLimit:I

    .line 988
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    .line 989
    const/16 v2, 0x9

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v2

    .line 988
    iput v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_ChargerB4State:I

    .line 990
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    .line 991
    const/16 v2, 0xa

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v2

    .line 990
    iput-wide v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_ChargerKWHConsumed:D

    .line 992
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    .line 993
    const/16 v2, 0xb

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v2

    .line 992
    iput v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_ChargeSubstate:I

    .line 994
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    .line 995
    const/16 v2, 0xc

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v2

    .line 994
    iput v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_ChargeState_raw:I

    .line 996
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    .line 997
    const/16 v2, 0xd

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v2

    .line 996
    iput v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_ChargeMode_raw:I

    .line 1000
    :cond_5
    invoke-direct/range {p0 .. p0}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->refreshUI()V

    goto/16 :goto_1

    .line 1005
    .end local v18           #dataParts:[Ljava/lang/String;
    :sswitch_2
    invoke-virtual {v11}, Ljava/lang/String;->length()I

    move-result v1

    if-lez v1, :cond_7

    .line 1006
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    invoke-static {v11}, Ljava/lang/Long;->parseLong(Ljava/lang/String;)J

    move-result-wide v2

    iput-wide v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_LastCarUpdate_raw:J

    .line 1007
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    new-instance v2, Ljava/util/Date;

    .line 1008
    new-instance v3, Ljava/util/Date;

    invoke-direct {v3}, Ljava/util/Date;-><init>()V

    invoke-virtual {v3}, Ljava/util/Date;->getTime()J

    move-result-wide v3

    .line 1009
    move-object/from16 v0, p0

    iget-object v5, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    iget-wide v5, v5, Lcom/openvehicles/OVMS/CarData;->Data_LastCarUpdate_raw:J

    .line 1010
    const-wide/16 v7, 0x3e8

    .line 1009
    mul-long/2addr v5, v7

    .line 1008
    sub-long/2addr v3, v5

    invoke-direct {v2, v3, v4}, Ljava/util/Date;-><init>(J)V

    .line 1007
    iput-object v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_LastCarUpdate:Ljava/util/Date;

    .line 1013
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    iget-wide v1, v1, Lcom/openvehicles/OVMS/CarData;->Data_ParkedTime_raw:D

    const-wide/16 v3, 0x0

    cmpl-double v1, v1, v3

    if-lez v1, :cond_6

    .line 1014
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    new-instance v2, Ljava/util/Date;

    .line 1015
    move-object/from16 v0, p0

    iget-object v3, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    iget-object v3, v3, Lcom/openvehicles/OVMS/CarData;->Data_LastCarUpdate:Ljava/util/Date;

    invoke-virtual {v3}, Ljava/util/Date;->getTime()J

    move-result-wide v3

    .line 1016
    move-object/from16 v0, p0

    iget-object v5, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    iget-wide v5, v5, Lcom/openvehicles/OVMS/CarData;->Data_ParkedTime_raw:D

    double-to-long v5, v5

    const-wide/16 v7, 0x3e8

    mul-long/2addr v5, v7

    .line 1015
    sub-long/2addr v3, v5

    invoke-direct {v2, v3, v4}, Ljava/util/Date;-><init>(J)V

    .line 1014
    iput-object v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_ParkedTime:Ljava/util/Date;

    .line 1018
    :cond_6
    invoke-direct/range {p0 .. p0}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->refreshUI()V

    goto/16 :goto_1

    .line 1020
    :cond_7
    const-string v1, "TCP"

    const-string v2, "T MSG Invalid"

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_1

    .line 1025
    :sswitch_3
    const-string v1, ",\\s*"

    invoke-virtual {v11, v1}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v18

    .line 1026
    .restart local v18       #dataParts:[Ljava/lang/String;
    move-object/from16 v0, v18

    array-length v1, v0

    const/4 v2, 0x2

    if-lt v1, v2, :cond_8

    .line 1027
    const-string v1, "TCP"

    const-string v2, "L MSG Validated"

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1028
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v2, 0x0

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v2

    iput-wide v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_Latitude:D

    .line 1029
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v2, 0x1

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v2

    iput-wide v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_Longitude:D

    .line 1032
    :cond_8
    move-object/from16 v0, v18

    array-length v1, v0

    const/4 v2, 0x6

    if-lt v1, v2, :cond_9

    .line 1033
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v2, 0x2

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v2

    iput-wide v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_Direction:D

    .line 1034
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v2, 0x3

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v2

    iput-wide v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_Altitude:D

    .line 1035
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v2, 0x4

    aget-object v2, v18, v2

    invoke-virtual {v2}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v2

    const-string v3, "1"

    invoke-virtual {v2, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    iput-boolean v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_GPSLocked:Z

    .line 1036
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v2, 0x5

    aget-object v2, v18, v2

    invoke-virtual {v2}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v2

    const-string v3, "0"

    invoke-virtual {v2, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    iput-boolean v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_GPSDataStale:Z

    .line 1039
    :cond_9
    invoke-direct/range {p0 .. p0}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->refreshUI()V

    goto/16 :goto_1

    .line 1044
    .end local v18           #dataParts:[Ljava/lang/String;
    :sswitch_4
    const-string v1, ",\\s*"

    invoke-virtual {v11, v1}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v18

    .line 1045
    .restart local v18       #dataParts:[Ljava/lang/String;
    move-object/from16 v0, v18

    array-length v1, v0

    const/16 v2, 0x9

    if-lt v1, v2, :cond_1

    .line 1046
    const-string v1, "TCP"

    const-string v2, "D MSG Validated"

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1047
    const/4 v1, 0x0

    aget-object v1, v18, v1

    invoke-static {v1}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v17

    .line 1048
    .local v17, dataField:I
    move-object/from16 v0, p0

    iget-object v2, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    and-int/lit8 v1, v17, 0x1

    if-lez v1, :cond_d

    const/4 v1, 0x1

    :goto_4
    iput-boolean v1, v2, Lcom/openvehicles/OVMS/CarData;->Data_LeftDoorOpen:Z

    .line 1049
    move-object/from16 v0, p0

    iget-object v2, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    and-int/lit8 v1, v17, 0x2

    if-lez v1, :cond_e

    const/4 v1, 0x1

    :goto_5
    iput-boolean v1, v2, Lcom/openvehicles/OVMS/CarData;->Data_RightDoorOpen:Z

    .line 1050
    move-object/from16 v0, p0

    iget-object v2, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    and-int/lit8 v1, v17, 0x4

    if-lez v1, :cond_f

    const/4 v1, 0x1

    :goto_6
    iput-boolean v1, v2, Lcom/openvehicles/OVMS/CarData;->Data_ChargePortOpen:Z

    .line 1051
    move-object/from16 v0, p0

    iget-object v2, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    and-int/lit8 v1, v17, 0x8

    if-lez v1, :cond_10

    const/4 v1, 0x1

    :goto_7
    iput-boolean v1, v2, Lcom/openvehicles/OVMS/CarData;->Data_PilotPresent:Z

    .line 1052
    move-object/from16 v0, p0

    iget-object v2, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    and-int/lit8 v1, v17, 0x10

    if-lez v1, :cond_11

    const/4 v1, 0x1

    :goto_8
    iput-boolean v1, v2, Lcom/openvehicles/OVMS/CarData;->Data_Charging:Z

    .line 1054
    move-object/from16 v0, p0

    iget-object v2, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    and-int/lit8 v1, v17, 0x40

    if-lez v1, :cond_12

    const/4 v1, 0x1

    :goto_9
    iput-boolean v1, v2, Lcom/openvehicles/OVMS/CarData;->Data_HandBrakeApplied:Z

    .line 1055
    move-object/from16 v0, p0

    iget-object v2, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    move/from16 v0, v17

    and-int/lit16 v1, v0, 0x80

    const/4 v3, 0x1

    if-le v1, v3, :cond_13

    const/4 v1, 0x1

    :goto_a
    iput-boolean v1, v2, Lcom/openvehicles/OVMS/CarData;->Data_CarPoweredON:Z

    .line 1057
    const/4 v1, 0x1

    aget-object v1, v18, v1

    invoke-static {v1}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v17

    .line 1058
    move-object/from16 v0, p0

    iget-object v2, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    and-int/lit8 v1, v17, 0x8

    if-lez v1, :cond_14

    const/4 v1, 0x1

    :goto_b
    iput-boolean v1, v2, Lcom/openvehicles/OVMS/CarData;->Data_PINLocked:Z

    .line 1059
    move-object/from16 v0, p0

    iget-object v2, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    and-int/lit8 v1, v17, 0x10

    if-lez v1, :cond_15

    const/4 v1, 0x1

    :goto_c
    iput-boolean v1, v2, Lcom/openvehicles/OVMS/CarData;->Data_ValetON:Z

    .line 1060
    move-object/from16 v0, p0

    iget-object v2, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    and-int/lit8 v1, v17, 0x20

    if-lez v1, :cond_16

    const/4 v1, 0x1

    :goto_d
    iput-boolean v1, v2, Lcom/openvehicles/OVMS/CarData;->Data_HeadlightsON:Z

    .line 1061
    move-object/from16 v0, p0

    iget-object v2, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    and-int/lit8 v1, v17, 0x40

    if-lez v1, :cond_17

    const/4 v1, 0x1

    :goto_e
    iput-boolean v1, v2, Lcom/openvehicles/OVMS/CarData;->Data_BonnetOpen:Z

    .line 1062
    move-object/from16 v0, p0

    iget-object v2, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    move/from16 v0, v17

    and-int/lit16 v1, v0, 0x80

    if-lez v1, :cond_18

    const/4 v1, 0x1

    :goto_f
    iput-boolean v1, v2, Lcom/openvehicles/OVMS/CarData;->Data_TrunkOpen:Z

    .line 1064
    const/4 v1, 0x2

    aget-object v1, v18, v1

    invoke-static {v1}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v17

    .line 1065
    move-object/from16 v0, p0

    iget-object v2, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v1, 0x4

    move/from16 v0, v17

    if-ne v0, v1, :cond_19

    const/4 v1, 0x1

    :goto_10
    iput-boolean v1, v2, Lcom/openvehicles/OVMS/CarData;->Data_CarLocked:Z

    .line 1067
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    .line 1068
    const/4 v2, 0x3

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v2

    .line 1067
    iput-wide v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_TemperaturePEM:D

    .line 1069
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    .line 1070
    const/4 v2, 0x4

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v2

    .line 1069
    iput-wide v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_TemperatureMotor:D

    .line 1071
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    .line 1072
    const/4 v2, 0x5

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v2

    .line 1071
    iput-wide v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_TemperatureBattery:D

    .line 1073
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v2, 0x6

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v2

    iput-wide v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_TripMeter:D

    .line 1074
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v2, 0x7

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v2

    iput-wide v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_Odometer:D

    .line 1075
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/16 v2, 0x8

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v2

    iput-wide v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_Speed:D

    .line 1078
    move-object/from16 v0, v18

    array-length v1, v0

    const/16 v2, 0xa

    if-lt v1, v2, :cond_a

    .line 1079
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    .line 1080
    const/16 v2, 0x9

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v2

    .line 1079
    iput-wide v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_ParkedTime_raw:D

    .line 1081
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    iget-object v1, v1, Lcom/openvehicles/OVMS/CarData;->Data_LastCarUpdate:Ljava/util/Date;

    if-nez v1, :cond_1a

    .line 1082
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v2, 0x0

    iput-object v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_ParkedTime:Ljava/util/Date;

    .line 1091
    :cond_a
    :goto_11
    move-object/from16 v0, v18

    array-length v1, v0

    const/16 v2, 0xb

    if-lt v1, v2, :cond_b

    .line 1092
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    .line 1093
    const/16 v2, 0xa

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v2

    .line 1092
    iput-wide v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_TemperatureAmbient:D

    .line 1096
    :cond_b
    move-object/from16 v0, v18

    array-length v1, v0

    const/16 v2, 0xe

    if-lt v1, v2, :cond_c

    .line 1097
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/16 v2, 0xb

    aget-object v2, v18, v2

    .line 1098
    invoke-virtual {v2}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v2

    const-string v3, "1"

    invoke-virtual {v2, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    .line 1097
    iput-boolean v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_CoolingPumpON_DoorState3:Z

    .line 1099
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/16 v2, 0xc

    aget-object v2, v18, v2

    .line 1100
    invoke-virtual {v2}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v2

    const-string v3, "0"

    invoke-virtual {v2, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    .line 1099
    iput-boolean v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_PEM_Motor_Battery_TemperaturesDataStale:Z

    .line 1101
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/16 v2, 0xd

    aget-object v2, v18, v2

    .line 1102
    invoke-virtual {v2}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v2

    const-string v3, "0"

    invoke-virtual {v2, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    .line 1101
    iput-boolean v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_AmbientTemperatureDataStale:Z

    .line 1106
    :cond_c
    invoke-direct/range {p0 .. p0}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->refreshUI()V

    goto/16 :goto_1

    .line 1048
    :cond_d
    const/4 v1, 0x0

    goto/16 :goto_4

    .line 1049
    :cond_e
    const/4 v1, 0x0

    goto/16 :goto_5

    .line 1050
    :cond_f
    const/4 v1, 0x0

    goto/16 :goto_6

    .line 1051
    :cond_10
    const/4 v1, 0x0

    goto/16 :goto_7

    .line 1052
    :cond_11
    const/4 v1, 0x0

    goto/16 :goto_8

    .line 1054
    :cond_12
    const/4 v1, 0x0

    goto/16 :goto_9

    .line 1055
    :cond_13
    const/4 v1, 0x0

    goto/16 :goto_a

    .line 1058
    :cond_14
    const/4 v1, 0x0

    goto/16 :goto_b

    .line 1059
    :cond_15
    const/4 v1, 0x0

    goto/16 :goto_c

    .line 1060
    :cond_16
    const/4 v1, 0x0

    goto/16 :goto_d

    .line 1061
    :cond_17
    const/4 v1, 0x0

    goto/16 :goto_e

    .line 1062
    :cond_18
    const/4 v1, 0x0

    goto/16 :goto_f

    .line 1065
    :cond_19
    const/4 v1, 0x0

    goto/16 :goto_10

    .line 1084
    :cond_1a
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    new-instance v2, Ljava/util/Date;

    .line 1085
    move-object/from16 v0, p0

    iget-object v3, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    iget-object v3, v3, Lcom/openvehicles/OVMS/CarData;->Data_LastCarUpdate:Ljava/util/Date;

    invoke-virtual {v3}, Ljava/util/Date;->getTime()J

    move-result-wide v3

    .line 1086
    move-object/from16 v0, p0

    iget-object v5, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    iget-wide v5, v5, Lcom/openvehicles/OVMS/CarData;->Data_ParkedTime_raw:D

    double-to-long v5, v5

    .line 1087
    const-wide/16 v7, 0x3e8

    .line 1086
    mul-long/2addr v5, v7

    .line 1085
    sub-long/2addr v3, v5

    invoke-direct {v2, v3, v4}, Ljava/util/Date;-><init>(J)V

    .line 1084
    iput-object v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_ParkedTime:Ljava/util/Date;

    goto/16 :goto_11

    .line 1112
    .end local v17           #dataField:I
    .end local v18           #dataParts:[Ljava/lang/String;
    :sswitch_5
    const-string v1, ",\\s*"

    invoke-virtual {v11, v1}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v18

    .line 1113
    .restart local v18       #dataParts:[Ljava/lang/String;
    move-object/from16 v0, v18

    array-length v1, v0

    const/4 v2, 0x3

    if-lt v1, v2, :cond_1d

    .line 1114
    const-string v1, "TCP"

    const-string v2, "F MSG Validated"

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1115
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v2, 0x0

    aget-object v2, v18, v2

    .line 1116
    invoke-virtual {v2}, Ljava/lang/String;->toString()Ljava/lang/String;

    move-result-object v2

    .line 1115
    iput-object v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_CarModuleFirmwareVersion:Ljava/lang/String;

    .line 1117
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v2, 0x1

    aget-object v2, v18, v2

    invoke-virtual {v2}, Ljava/lang/String;->toString()Ljava/lang/String;

    move-result-object v2

    iput-object v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_VIN:Ljava/lang/String;

    .line 1118
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v2, 0x2

    aget-object v2, v18, v2

    .line 1119
    invoke-virtual {v2}, Ljava/lang/String;->toString()Ljava/lang/String;

    move-result-object v2

    .line 1118
    iput-object v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_CarModuleGSMSignalLevel:Ljava/lang/String;

    .line 1120
    move-object/from16 v0, v18

    array-length v1, v0

    const/4 v2, 0x4

    if-lt v1, v2, :cond_1b

    .line 1122
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    iget-object v1, v1, Lcom/openvehicles/OVMS/CarData;->Data_Features:Ljava/util/LinkedHashMap;

    .line 1123
    const/16 v2, 0xf

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    .line 1124
    const/4 v3, 0x3

    aget-object v3, v18, v3

    invoke-virtual {v3}, Ljava/lang/String;->toString()Ljava/lang/String;

    move-result-object v3

    .line 1122
    invoke-virtual {v1, v2, v3}, Ljava/util/LinkedHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 1125
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v2, 0x3

    aget-object v2, v18, v2

    invoke-virtual {v2}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v2

    .line 1126
    const-string v3, "1"

    invoke-virtual {v2, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    .line 1125
    iput-boolean v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_CANWriteEnabled:Z

    .line 1130
    :cond_1b
    move-object/from16 v0, v18

    array-length v1, v0

    const/4 v2, 0x5

    if-lt v1, v2, :cond_1c

    .line 1131
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v2, 0x4

    aget-object v2, v18, v2

    invoke-virtual {v2}, Ljava/lang/String;->toString()Ljava/lang/String;

    move-result-object v2

    iput-object v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_CarType:Ljava/lang/String;

    .line 1135
    :cond_1c
    invoke-direct/range {p0 .. p0}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->refreshUI()V

    .line 1140
    .end local v18           #dataParts:[Ljava/lang/String;
    :cond_1d
    :sswitch_6
    const-string v1, ",\\s*"

    invoke-virtual {v11, v1}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v18

    .line 1141
    .restart local v18       #dataParts:[Ljava/lang/String;
    move-object/from16 v0, v18

    array-length v1, v0

    const/4 v2, 0x1

    if-lt v1, v2, :cond_1

    .line 1142
    const-string v1, "TCP"

    const-string v2, "f MSG Validated"

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1143
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/4 v2, 0x0

    aget-object v2, v18, v2

    .line 1144
    invoke-virtual {v2}, Ljava/lang/String;->toString()Ljava/lang/String;

    move-result-object v2

    .line 1143
    iput-object v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_OVMSServerFirmwareVersion:Ljava/lang/String;

    .line 1147
    invoke-direct/range {p0 .. p0}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->refreshUI()V

    goto/16 :goto_1

    .line 1153
    .end local v18           #dataParts:[Ljava/lang/String;
    :sswitch_7
    const-string v1, ",\\s*"

    invoke-virtual {v11, v1}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v18

    .line 1154
    .restart local v18       #dataParts:[Ljava/lang/String;
    move-object/from16 v0, v18

    array-length v1, v0

    const/16 v2, 0x8

    if-lt v1, v2, :cond_1

    .line 1155
    const-string v1, "TCP"

    const-string v2, "W MSG Validated"

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1156
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    .line 1157
    const/4 v2, 0x0

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v2

    .line 1156
    iput-wide v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_FRWheelPressure:D

    .line 1158
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    .line 1159
    const/4 v2, 0x1

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v2

    .line 1158
    iput-wide v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_FRWheelTemperature:D

    .line 1160
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    .line 1161
    const/4 v2, 0x2

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v2

    .line 1160
    iput-wide v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_RRWheelPressure:D

    .line 1162
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    .line 1163
    const/4 v2, 0x3

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v2

    .line 1162
    iput-wide v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_RRWheelTemperature:D

    .line 1164
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    .line 1165
    const/4 v2, 0x4

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v2

    .line 1164
    iput-wide v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_FLWheelPressure:D

    .line 1166
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    .line 1167
    const/4 v2, 0x5

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v2

    .line 1166
    iput-wide v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_FLWheelTemperature:D

    .line 1168
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    .line 1169
    const/4 v2, 0x6

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v2

    .line 1168
    iput-wide v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_RLWheelPressure:D

    .line 1170
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    .line 1171
    const/4 v2, 0x7

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v2

    .line 1170
    iput-wide v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_RLWheelTemperature:D

    .line 1174
    move-object/from16 v0, v18

    array-length v1, v0

    const/16 v2, 0x9

    if-lt v1, v2, :cond_1e

    .line 1175
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    const/16 v2, 0x8

    aget-object v2, v18, v2

    invoke-virtual {v2}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v2

    .line 1176
    const-string v3, "0"

    invoke-virtual {v2, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    .line 1175
    iput-boolean v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_TPMSDataStale:Z

    .line 1179
    :cond_1e
    invoke-direct/range {p0 .. p0}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->refreshUI()V

    goto/16 :goto_1

    .line 1185
    .end local v18           #dataParts:[Ljava/lang/String;
    :sswitch_8
    const-string v1, ",\\s*"

    invoke-virtual {v11, v1}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v18

    .line 1186
    .restart local v18       #dataParts:[Ljava/lang/String;
    move-object/from16 v0, v18

    array-length v1, v0

    const/16 v2, 0x9

    if-lt v1, v2, :cond_1

    .line 1187
    const-string v1, "TCP"

    const-string v2, "g MSG Validated"

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1188
    new-instance v22, Lcom/openvehicles/OVMS/CarData_Group;

    invoke-direct/range {v22 .. v22}, Lcom/openvehicles/OVMS/CarData_Group;-><init>()V

    .line 1189
    .local v22, groupCar:Lcom/openvehicles/OVMS/CarData_Group;
    const/4 v1, 0x0

    aget-object v1, v18, v1

    move-object/from16 v0, v22

    iput-object v1, v0, Lcom/openvehicles/OVMS/CarData_Group;->VehicleID:Ljava/lang/String;

    .line 1190
    const/4 v1, 0x2

    aget-object v1, v18, v1

    invoke-static {v1}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v1

    move-object/from16 v0, v22

    iput-wide v1, v0, Lcom/openvehicles/OVMS/CarData_Group;->SOC:D

    .line 1191
    const/4 v1, 0x3

    aget-object v1, v18, v1

    invoke-static {v1}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v1

    move-object/from16 v0, v22

    iput-wide v1, v0, Lcom/openvehicles/OVMS/CarData_Group;->Speed:D

    .line 1192
    const/4 v1, 0x4

    aget-object v1, v18, v1

    invoke-static {v1}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v1

    move-object/from16 v0, v22

    iput-wide v1, v0, Lcom/openvehicles/OVMS/CarData_Group;->Direction:D

    .line 1193
    const/4 v1, 0x5

    aget-object v1, v18, v1

    invoke-static {v1}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v1

    move-object/from16 v0, v22

    iput-wide v1, v0, Lcom/openvehicles/OVMS/CarData_Group;->Altitude:D

    .line 1194
    const/4 v1, 0x6

    aget-object v1, v18, v1

    invoke-virtual {v1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v1

    const-string v2, "1"

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    move-object/from16 v0, v22

    iput-boolean v1, v0, Lcom/openvehicles/OVMS/CarData_Group;->GPSLocked:Z

    .line 1195
    const/4 v1, 0x7

    aget-object v1, v18, v1

    invoke-virtual {v1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v1

    const-string v2, "0"

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    move-object/from16 v0, v22

    iput-boolean v1, v0, Lcom/openvehicles/OVMS/CarData_Group;->GPSDataStale:Z

    .line 1196
    const/16 v1, 0x8

    aget-object v1, v18, v1

    invoke-static {v1}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v1

    move-object/from16 v0, v22

    iput-wide v1, v0, Lcom/openvehicles/OVMS/CarData_Group;->Latitude:D

    .line 1197
    const/16 v1, 0x9

    aget-object v1, v18, v1

    invoke-static {v1}, Ljava/lang/Double;->parseDouble(Ljava/lang/String;)D

    move-result-wide v1

    move-object/from16 v0, v22

    iput-wide v1, v0, Lcom/openvehicles/OVMS/CarData_Group;->Longitude:D

    .line 1199
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    iget-object v1, v1, Lcom/openvehicles/OVMS/CarData;->Group:Ljava/util/HashMap;

    if-nez v1, :cond_1f

    .line 1200
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    new-instance v2, Ljava/util/HashMap;

    invoke-direct {v2}, Ljava/util/HashMap;-><init>()V

    iput-object v2, v1, Lcom/openvehicles/OVMS/CarData;->Group:Ljava/util/HashMap;

    .line 1203
    :cond_1f
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    iget-object v1, v1, Lcom/openvehicles/OVMS/CarData;->Group:Ljava/util/HashMap;

    move-object/from16 v0, v22

    iget-object v2, v0, Lcom/openvehicles/OVMS/CarData_Group;->VehicleID:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v25

    check-cast v25, Lcom/openvehicles/OVMS/CarData_Group;

    .line 1204
    .local v25, oldData:Lcom/openvehicles/OVMS/CarData_Group;
    if-eqz v25, :cond_20

    .line 1205
    move-object/from16 v0, v25

    iget-object v1, v0, Lcom/openvehicles/OVMS/CarData_Group;->VehicleImageDrawable:Ljava/lang/String;

    move-object/from16 v0, v22

    iput-object v1, v0, Lcom/openvehicles/OVMS/CarData_Group;->VehicleImageDrawable:Ljava/lang/String;

    .line 1207
    :cond_20
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    iget-object v1, v1, Lcom/openvehicles/OVMS/CarData;->Group:Ljava/util/HashMap;

    const/4 v2, 0x0

    aget-object v2, v18, v2

    move-object/from16 v0, v22

    invoke-virtual {v1, v2, v0}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 1209
    invoke-direct/range {p0 .. p0}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->refreshUI()V

    goto/16 :goto_1

    .line 1215
    .end local v18           #dataParts:[Ljava/lang/String;
    .end local v22           #groupCar:Lcom/openvehicles/OVMS/CarData_Group;
    .end local v25           #oldData:Lcom/openvehicles/OVMS/CarData_Group;
    :sswitch_9
    const-string v1, "TCP"

    const-string v2, "Server acknowleged ping"

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_1

    .line 1221
    :sswitch_a
    invoke-virtual {v11}, Ljava/lang/String;->length()I

    move-result v1

    if-nez v1, :cond_21

    .line 1222
    const-string v1, "TCP"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-static/range {v16 .. v16}, Ljava/lang/String;->valueOf(C)Ljava/lang/String;

    move-result-object v3

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v3, " MSG Code Invalid"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_1

    .line 1228
    :cond_21
    const/4 v13, -0x1

    .line 1229
    .local v13, cmdCode:I
    const-string v14, ""

    .line 1231
    .local v14, cmdData:Ljava/lang/String;
    const/16 v1, 0x2c

    :try_start_3
    invoke-virtual {v11, v1}, Ljava/lang/String;->indexOf(I)I

    move-result v1

    if-lez v1, :cond_22

    .line 1233
    const/4 v1, 0x0

    .line 1234
    const/16 v2, 0x2c

    invoke-virtual {v11, v2}, Ljava/lang/String;->indexOf(I)I

    move-result v2

    .line 1233
    invoke-virtual {v11, v1, v2}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v1

    invoke-static {v1}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v13

    .line 1235
    const/16 v1, 0x2c

    invoke-virtual {v11, v1}, Ljava/lang/String;->indexOf(I)I

    move-result v1

    add-int/lit8 v1, v1, 0x1

    invoke-virtual {v11, v1}, Ljava/lang/String;->substring(I)Ljava/lang/String;
    :try_end_3
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_3} :catch_2

    move-result-object v14

    .line 1245
    :goto_12
    sparse-switch v13, :sswitch_data_1

    .line 1375
    const-string v1, ",\\s*"

    invoke-virtual {v14, v1}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v18

    .line 1378
    .restart local v18       #dataParts:[Ljava/lang/String;
    const/4 v1, 0x0

    aget-object v1, v18, v1

    const-string v2, "0"

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_2f

    .line 1380
    const-string v1, "Server Acknowledged %s"

    const/4 v2, 0x1

    new-array v2, v2, [Ljava/lang/Object;

    const/4 v3, 0x0

    .line 1381
    invoke-static {v13}, Lcom/openvehicles/OVMS/ServerCommands;->toString(I)Ljava/lang/String;

    move-result-object v4

    aput-object v4, v2, v3

    .line 1379
    invoke-static {v1, v2}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v1

    move-object/from16 v0, p0

    invoke-direct {v0, v1}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->notifyCommandResponse(Ljava/lang/String;)V

    goto/16 :goto_1

    .line 1238
    .end local v18           #dataParts:[Ljava/lang/String;
    :cond_22
    :try_start_4
    invoke-static {v11}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I
    :try_end_4
    .catch Ljava/lang/Exception; {:try_start_4 .. :try_end_4} :catch_2

    move-result v13

    goto :goto_12

    .line 1240
    :catch_2
    move-exception v21

    .line 1241
    .restart local v21       #e:Ljava/lang/Exception;
    const-string v1, "TCP"

    new-instance v2, Ljava/lang/StringBuilder;

    const-string v3, "!!! "

    invoke-direct {v2, v3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    move/from16 v0, v16

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, " message is invalid."

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_1

    .line 1251
    .end local v21           #e:Ljava/lang/Exception;
    :sswitch_b
    const-string v1, ","

    invoke-virtual {v14, v1}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v18

    .line 1253
    .restart local v18       #dataParts:[Ljava/lang/String;
    move-object/from16 v0, v18

    array-length v1, v0

    const/4 v2, 0x4

    if-le v1, v2, :cond_26

    .line 1255
    const-string v29, ""

    .line 1256
    .local v29, value:Ljava/lang/String;
    const/16 v23, 0x3

    .local v23, i:I
    :goto_13
    move-object/from16 v0, v18

    array-length v1, v0

    move/from16 v0, v23

    if-lt v0, v1, :cond_24

    .line 1260
    const-string v1, "TCP"

    const-string v2, "FEATURE %s = %s"

    const/4 v3, 0x2

    new-array v3, v3, [Ljava/lang/Object;

    const/4 v4, 0x0

    .line 1261
    const/4 v5, 0x1

    aget-object v5, v18, v5

    aput-object v5, v3, v4

    const/4 v4, 0x1

    aput-object v29, v3, v4

    .line 1260
    invoke-static {v2, v3}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1262
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    iget-object v1, v1, Lcom/openvehicles/OVMS/CarData;->Data_Features:Ljava/util/LinkedHashMap;

    .line 1263
    const/4 v2, 0x1

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v2

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    .line 1262
    move-object/from16 v0, v29

    invoke-virtual {v1, v2, v0}, Ljava/util/LinkedHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 1277
    .end local v23           #i:I
    .end local v29           #value:Ljava/lang/String;
    :cond_23
    :goto_14
    const/4 v1, 0x1

    aget-object v1, v18, v1

    invoke-static {v1}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v1

    .line 1278
    const/4 v2, 0x2

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v2

    .line 1277
    add-int/lit8 v2, v2, -0x1

    if-ne v1, v2, :cond_1

    .line 1279
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    new-instance v2, Ljava/util/Date;

    invoke-direct {v2}, Ljava/util/Date;-><init>()V

    iput-object v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_Features_LastRefreshed:Ljava/util/Date;

    .line 1280
    invoke-direct/range {p0 .. p0}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->refreshUI()V

    goto/16 :goto_1

    .line 1257
    .restart local v23       #i:I
    .restart local v29       #value:Ljava/lang/String;
    :cond_24
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-static/range {v29 .. v29}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v1

    invoke-direct {v2, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual/range {v29 .. v29}, Ljava/lang/String;->length()I

    move-result v1

    if-lez v1, :cond_25

    const-string v1, ","

    :goto_15
    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    .line 1258
    aget-object v2, v18, v23

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    .line 1257
    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v29

    .line 1256
    add-int/lit8 v23, v23, 0x1

    goto :goto_13

    .line 1257
    :cond_25
    const-string v1, ""

    goto :goto_15

    .line 1264
    .end local v23           #i:I
    .end local v29           #value:Ljava/lang/String;
    :cond_26
    move-object/from16 v0, v18

    array-length v1, v0

    const/4 v2, 0x4

    if-ne v1, v2, :cond_27

    .line 1265
    const-string v1, "TCP"

    const-string v2, "FEATURE %s = %s"

    const/4 v3, 0x2

    new-array v3, v3, [Ljava/lang/Object;

    const/4 v4, 0x0

    .line 1266
    const/4 v5, 0x1

    aget-object v5, v18, v5

    aput-object v5, v3, v4

    const/4 v4, 0x1

    const/4 v5, 0x3

    aget-object v5, v18, v5

    aput-object v5, v3, v4

    .line 1265
    invoke-static {v2, v3}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1267
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    iget-object v1, v1, Lcom/openvehicles/OVMS/CarData;->Data_Features:Ljava/util/LinkedHashMap;

    .line 1268
    const/4 v2, 0x1

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v2

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    const/4 v3, 0x3

    aget-object v3, v18, v3

    .line 1267
    invoke-virtual {v1, v2, v3}, Ljava/util/LinkedHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    goto :goto_14

    .line 1269
    :cond_27
    move-object/from16 v0, v18

    array-length v1, v0

    const/4 v2, 0x2

    if-lt v1, v2, :cond_23

    .line 1270
    const-string v1, "TCP"

    const-string v2, "FEATURE %s = EMPTY"

    const/4 v3, 0x1

    new-array v3, v3, [Ljava/lang/Object;

    const/4 v4, 0x0

    .line 1271
    const/4 v5, 0x1

    aget-object v5, v18, v5

    aput-object v5, v3, v4

    .line 1270
    invoke-static {v2, v3}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1272
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    iget-object v1, v1, Lcom/openvehicles/OVMS/CarData;->Data_Features:Ljava/util/LinkedHashMap;

    .line 1273
    const/4 v2, 0x1

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v2

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    const-string v3, ""

    .line 1272
    invoke-virtual {v1, v2, v3}, Ljava/util/LinkedHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    goto/16 :goto_14

    .line 1293
    .end local v18           #dataParts:[Ljava/lang/String;
    :sswitch_c
    const-string v1, ","

    invoke-virtual {v14, v1}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v18

    .line 1294
    .restart local v18       #dataParts:[Ljava/lang/String;
    move-object/from16 v0, v18

    array-length v1, v0

    const/4 v2, 0x4

    if-le v1, v2, :cond_2b

    .line 1296
    const-string v29, ""

    .line 1297
    .restart local v29       #value:Ljava/lang/String;
    const/16 v23, 0x3

    .restart local v23       #i:I
    :goto_16
    move-object/from16 v0, v18

    array-length v1, v0

    move/from16 v0, v23

    if-lt v0, v1, :cond_29

    .line 1301
    const-string v1, "TCP"

    const-string v2, "PARAMETER %s = %s"

    const/4 v3, 0x2

    new-array v3, v3, [Ljava/lang/Object;

    const/4 v4, 0x0

    .line 1302
    const/4 v5, 0x1

    aget-object v5, v18, v5

    aput-object v5, v3, v4

    const/4 v4, 0x1

    aput-object v29, v3, v4

    .line 1301
    invoke-static {v2, v3}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1303
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    iget-object v1, v1, Lcom/openvehicles/OVMS/CarData;->Data_Parameters:Ljava/util/LinkedHashMap;

    .line 1304
    const/4 v2, 0x1

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v2

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    .line 1303
    move-object/from16 v0, v29

    invoke-virtual {v1, v2, v0}, Ljava/util/LinkedHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 1318
    .end local v23           #i:I
    .end local v29           #value:Ljava/lang/String;
    :cond_28
    :goto_17
    const/4 v1, 0x1

    aget-object v1, v18, v1

    invoke-static {v1}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v1

    .line 1319
    const/4 v2, 0x2

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v2

    .line 1318
    add-int/lit8 v2, v2, -0x1

    if-ne v1, v2, :cond_1

    .line 1320
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    new-instance v2, Ljava/util/Date;

    invoke-direct {v2}, Ljava/util/Date;-><init>()V

    iput-object v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_Parameters_LastRefreshed:Ljava/util/Date;

    .line 1321
    invoke-direct/range {p0 .. p0}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->refreshUI()V

    goto/16 :goto_1

    .line 1298
    .restart local v23       #i:I
    .restart local v29       #value:Ljava/lang/String;
    :cond_29
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-static/range {v29 .. v29}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v1

    invoke-direct {v2, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual/range {v29 .. v29}, Ljava/lang/String;->length()I

    move-result v1

    if-lez v1, :cond_2a

    const-string v1, ","

    :goto_18
    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    .line 1299
    aget-object v2, v18, v23

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    .line 1298
    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v29

    .line 1297
    add-int/lit8 v23, v23, 0x1

    goto :goto_16

    .line 1298
    :cond_2a
    const-string v1, ""

    goto :goto_18

    .line 1305
    .end local v23           #i:I
    .end local v29           #value:Ljava/lang/String;
    :cond_2b
    move-object/from16 v0, v18

    array-length v1, v0

    const/4 v2, 0x4

    if-ne v1, v2, :cond_2c

    .line 1306
    const-string v1, "TCP"

    const-string v2, "PARAMETER %s = %s"

    const/4 v3, 0x2

    new-array v3, v3, [Ljava/lang/Object;

    const/4 v4, 0x0

    .line 1307
    const/4 v5, 0x1

    aget-object v5, v18, v5

    aput-object v5, v3, v4

    const/4 v4, 0x1

    const/4 v5, 0x3

    aget-object v5, v18, v5

    aput-object v5, v3, v4

    .line 1306
    invoke-static {v2, v3}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1308
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    iget-object v1, v1, Lcom/openvehicles/OVMS/CarData;->Data_Parameters:Ljava/util/LinkedHashMap;

    .line 1309
    const/4 v2, 0x1

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v2

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    const/4 v3, 0x3

    aget-object v3, v18, v3

    .line 1308
    invoke-virtual {v1, v2, v3}, Ljava/util/LinkedHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    goto :goto_17

    .line 1310
    :cond_2c
    move-object/from16 v0, v18

    array-length v1, v0

    const/4 v2, 0x2

    if-lt v1, v2, :cond_28

    .line 1311
    const-string v1, "TCP"

    const-string v2, "PARAMETER %s = EMPTY"

    const/4 v3, 0x1

    new-array v3, v3, [Ljava/lang/Object;

    const/4 v4, 0x0

    .line 1312
    const/4 v5, 0x1

    aget-object v5, v18, v5

    aput-object v5, v3, v4

    .line 1311
    invoke-static {v2, v3}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1313
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    iget-object v1, v1, Lcom/openvehicles/OVMS/CarData;->Data_Parameters:Ljava/util/LinkedHashMap;

    .line 1314
    const/4 v2, 0x1

    aget-object v2, v18, v2

    invoke-static {v2}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v2

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    const-string v3, ""

    .line 1313
    invoke-virtual {v1, v2, v3}, Ljava/util/LinkedHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    goto/16 :goto_17

    .line 1332
    .end local v18           #dataParts:[Ljava/lang/String;
    :sswitch_d
    const-string v1, ",\\s*"

    invoke-virtual {v14, v1}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v18

    .line 1333
    .restart local v18       #dataParts:[Ljava/lang/String;
    move-object/from16 v0, v18

    array-length v1, v0

    const/4 v2, 0x3

    if-lt v1, v2, :cond_1

    .line 1334
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    iget-object v1, v1, Lcom/openvehicles/OVMS/CarData;->Data_GPRSUtilization:Lcom/openvehicles/OVMS/GPRSUtilization;

    if-nez v1, :cond_2d

    .line 1335
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    new-instance v2, Lcom/openvehicles/OVMS/GPRSUtilization;

    .line 1336
    move-object/from16 v0, p0

    iget-object v3, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-direct {v2, v3}, Lcom/openvehicles/OVMS/GPRSUtilization;-><init>(Landroid/content/Context;)V

    .line 1335
    iput-object v2, v1, Lcom/openvehicles/OVMS/CarData;->Data_GPRSUtilization:Lcom/openvehicles/OVMS/GPRSUtilization;

    .line 1338
    :cond_2d
    const/4 v1, 0x1

    aget-object v1, v18, v1

    const-string v2, "1"

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_2e

    .line 1339
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    iget-object v1, v1, Lcom/openvehicles/OVMS/CarData;->Data_GPRSUtilization:Lcom/openvehicles/OVMS/GPRSUtilization;

    invoke-virtual {v1}, Lcom/openvehicles/OVMS/GPRSUtilization;->Clear()V

    .line 1341
    :cond_2e
    new-instance v19, Ljava/text/SimpleDateFormat;

    .line 1342
    const-string v1, "yyyy-MM-dd"

    .line 1341
    move-object/from16 v0, v19

    invoke-direct {v0, v1}, Ljava/text/SimpleDateFormat;-><init>(Ljava/lang/String;)V

    .line 1344
    .local v19, dateParser:Ljava/text/SimpleDateFormat;
    :try_start_5
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    iget-object v1, v1, Lcom/openvehicles/OVMS/CarData;->Data_GPRSUtilization:Lcom/openvehicles/OVMS/GPRSUtilization;

    .line 1345
    const/4 v2, 0x3

    aget-object v2, v18, v2

    move-object/from16 v0, v19

    invoke-virtual {v0, v2}, Ljava/text/SimpleDateFormat;->parse(Ljava/lang/String;)Ljava/util/Date;

    move-result-object v2

    .line 1346
    const/4 v3, 0x4

    aget-object v3, v18, v3

    invoke-static {v3}, Ljava/lang/Long;->parseLong(Ljava/lang/String;)J

    move-result-wide v3

    .line 1347
    const/4 v5, 0x5

    aget-object v5, v18, v5

    invoke-static {v5}, Ljava/lang/Long;->parseLong(Ljava/lang/String;)J

    move-result-wide v5

    .line 1348
    const/4 v7, 0x6

    aget-object v7, v18, v7

    invoke-static {v7}, Ljava/lang/Long;->parseLong(Ljava/lang/String;)J

    move-result-wide v7

    .line 1349
    const/4 v9, 0x7

    aget-object v9, v18, v9

    invoke-static {v9}, Ljava/lang/Long;->parseLong(Ljava/lang/String;)J

    move-result-wide v9

    .line 1344
    invoke-virtual/range {v1 .. v10}, Lcom/openvehicles/OVMS/GPRSUtilization;->AddData(Ljava/util/Date;JJJJ)V

    .line 1350
    const-string v1, "TCP"

    .line 1352
    const-string v2, "GPRS UTIL [%s/%s] %s: car_rx %s car_tx %s app_rx %s app_tx %s"

    const/4 v3, 0x7

    new-array v3, v3, [Ljava/lang/Object;

    const/4 v4, 0x0

    .line 1353
    const/4 v5, 0x1

    aget-object v5, v18, v5

    aput-object v5, v3, v4

    const/4 v4, 0x1

    const/4 v5, 0x2

    aget-object v5, v18, v5

    aput-object v5, v3, v4

    const/4 v4, 0x2

    .line 1354
    const/4 v5, 0x3

    aget-object v5, v18, v5

    move-object/from16 v0, v19

    invoke-virtual {v0, v5}, Ljava/text/SimpleDateFormat;->parse(Ljava/lang/String;)Ljava/util/Date;

    move-result-object v5

    .line 1355
    invoke-virtual {v5}, Ljava/util/Date;->toLocaleString()Ljava/lang/String;

    move-result-object v5

    aput-object v5, v3, v4

    const/4 v4, 0x3

    .line 1356
    const/4 v5, 0x4

    aget-object v5, v18, v5

    aput-object v5, v3, v4

    const/4 v4, 0x4

    const/4 v5, 0x5

    aget-object v5, v18, v5

    aput-object v5, v3, v4

    const/4 v4, 0x5

    .line 1357
    const/4 v5, 0x6

    aget-object v5, v18, v5

    aput-object v5, v3, v4

    const/4 v4, 0x6

    const/4 v5, 0x7

    aget-object v5, v18, v5

    aput-object v5, v3, v4

    .line 1351
    invoke-static {v2, v3}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    .line 1350
    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_5
    .catch Ljava/lang/NumberFormatException; {:try_start_5 .. :try_end_5} :catch_3
    .catch Ljava/text/ParseException; {:try_start_5 .. :try_end_5} :catch_4

    .line 1365
    :goto_19
    const/4 v1, 0x1

    aget-object v1, v18, v1

    const/4 v2, 0x2

    aget-object v2, v18, v2

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_1

    .line 1366
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    iget-object v1, v1, Lcom/openvehicles/OVMS/CarData;->Data_GPRSUtilization:Lcom/openvehicles/OVMS/GPRSUtilization;

    new-instance v2, Ljava/util/Date;

    invoke-direct {v2}, Ljava/util/Date;-><init>()V

    iput-object v2, v1, Lcom/openvehicles/OVMS/GPRSUtilization;->LastDataRefresh:Ljava/util/Date;

    .line 1367
    move-object/from16 v0, p0

    iget-object v1, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    iget-object v1, v1, Lcom/openvehicles/OVMS/CarData;->Data_GPRSUtilization:Lcom/openvehicles/OVMS/GPRSUtilization;

    .line 1368
    move-object/from16 v0, p0

    iget-object v2, v0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    invoke-virtual {v1, v2}, Lcom/openvehicles/OVMS/GPRSUtilization;->Save(Landroid/content/Context;)V

    .line 1369
    invoke-direct/range {p0 .. p0}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->refreshUI()V

    goto/16 :goto_1

    .line 1358
    :catch_3
    move-exception v21

    .line 1359
    .local v21, e:Ljava/lang/NumberFormatException;
    invoke-virtual/range {v21 .. v21}, Ljava/lang/NumberFormatException;->printStackTrace()V

    goto :goto_19

    .line 1360
    .end local v21           #e:Ljava/lang/NumberFormatException;
    :catch_4
    move-exception v21

    .line 1361
    .local v21, e:Ljava/text/ParseException;
    invoke-virtual/range {v21 .. v21}, Ljava/text/ParseException;->printStackTrace()V

    goto :goto_19

    .line 1382
    .end local v19           #dateParser:Ljava/text/SimpleDateFormat;
    .end local v21           #e:Ljava/text/ParseException;
    :cond_2f
    const/4 v1, 0x0

    aget-object v1, v18, v1

    const-string v2, "1"

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_1

    .line 1383
    move-object/from16 v0, v18

    array-length v1, v0

    const/4 v2, 0x1

    if-le v1, v2, :cond_30

    .line 1385
    const-string v1, "[ERROR] %s\n%s\nTry turning on CAN_WRITE in the settings tab."

    const/4 v2, 0x2

    new-array v2, v2, [Ljava/lang/Object;

    const/4 v3, 0x0

    .line 1386
    invoke-static {v13}, Lcom/openvehicles/OVMS/ServerCommands;->toString(I)Ljava/lang/String;

    move-result-object v4

    aput-object v4, v2, v3

    const/4 v3, 0x1

    .line 1387
    const/4 v4, 0x1

    aget-object v4, v18, v4

    aput-object v4, v2, v3

    .line 1384
    invoke-static {v1, v2}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v1

    move-object/from16 v0, p0

    invoke-direct {v0, v1}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->notifyCommandResponse(Ljava/lang/String;)V

    goto/16 :goto_1

    .line 1390
    :cond_30
    const-string v1, "[ERROR] %s\nTry turning on CAN_WRITE in the settings tab."

    const/4 v2, 0x1

    new-array v2, v2, [Ljava/lang/Object;

    const/4 v3, 0x0

    .line 1391
    invoke-static {v13}, Lcom/openvehicles/OVMS/ServerCommands;->toString(I)Ljava/lang/String;

    move-result-object v4

    aput-object v4, v2, v3

    .line 1390
    invoke-static {v1, v2}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v1

    move-object/from16 v0, p0

    invoke-direct {v0, v1}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->notifyCommandResponse(Ljava/lang/String;)V

    goto/16 :goto_1

    .line 962
    :sswitch_data_0
    .sparse-switch
        0x43 -> :sswitch_a
        0x44 -> :sswitch_4
        0x46 -> :sswitch_5
        0x4c -> :sswitch_3
        0x53 -> :sswitch_1
        0x54 -> :sswitch_2
        0x57 -> :sswitch_7
        0x5a -> :sswitch_0
        0x61 -> :sswitch_9
        0x63 -> :sswitch_a
        0x66 -> :sswitch_6
        0x67 -> :sswitch_8
    .end sparse-switch

    .line 1245
    :sswitch_data_1
    .sparse-switch
        0x1 -> :sswitch_b
        0x3 -> :sswitch_c
        0x1e -> :sswitch_d
    .end sparse-switch
.end method

.method private refreshUI()V
    .locals 2

    .prologue
    .line 891
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    if-eqz v0, :cond_0

    .line 892
    iget-object v0, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->UIHandler:Landroid/os/Handler;
    invoke-static {v0}, Lcom/openvehicles/OVMS/OVMSActivity;->access$13(Lcom/openvehicles/OVMS/OVMSActivity;)Landroid/os/Handler;

    move-result-object v0

    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->mRefresh:Ljava/lang/Runnable;
    invoke-static {v1}, Lcom/openvehicles/OVMS/OVMSActivity;->access$14(Lcom/openvehicles/OVMS/OVMSActivity;)Ljava/lang/Runnable;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 893
    :cond_0
    return-void
.end method

.method private toHex([B)Ljava/lang/String;
    .locals 4
    .parameter "bytes"

    .prologue
    const/4 v3, 0x1

    .line 1604
    new-instance v0, Ljava/math/BigInteger;

    invoke-direct {v0, v3, p1}, Ljava/math/BigInteger;-><init>(I[B)V

    .line 1605
    .local v0, bi:Ljava/math/BigInteger;
    new-instance v1, Ljava/lang/StringBuilder;

    const-string v2, "%0"

    invoke-direct {v1, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

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
    .locals 3

    .prologue
    .line 1414
    const/4 v1, 0x1

    :try_start_0
    iput-boolean v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->socketMarkedClosed:Z

    .line 1415
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    const/4 v2, 0x1

    iput-boolean v2, v1, Lcom/openvehicles/OVMS/OVMSActivity;->SuppressServerErrorDialog:Z

    .line 1416
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    const/4 v2, 0x0

    #setter for: Lcom/openvehicles/OVMS/OVMSActivity;->isLoggedIn:Z
    invoke-static {v1, v2}, Lcom/openvehicles/OVMS/OVMSActivity;->access$17(Lcom/openvehicles/OVMS/OVMSActivity;Z)V

    .line 1421
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Sock:Ljava/net/Socket;

    if-eqz v1, :cond_0

    .line 1422
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Sock:Ljava/net/Socket;

    invoke-virtual {v1}, Ljava/net/Socket;->close()V
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    .line 1425
    :cond_0
    const-wide/16 v1, 0xc8

    :try_start_1
    invoke-static {v1, v2}, Ljava/lang/Thread;->sleep(J)V
    :try_end_1
    .catch Ljava/lang/InterruptedException; {:try_start_1 .. :try_end_1} :catch_1
    .catch Ljava/io/IOException; {:try_start_1 .. :try_end_1} :catch_0

    .line 1428
    :goto_0
    const/4 v1, 0x0

    :try_start_2
    iput-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Sock:Ljava/net/Socket;

    .line 1429
    iget-object v1, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    const/4 v2, 0x0

    iput-boolean v2, v1, Lcom/openvehicles/OVMS/OVMSActivity;->SuppressServerErrorDialog:Z
    :try_end_2
    .catch Ljava/io/IOException; {:try_start_2 .. :try_end_2} :catch_0

    .line 1433
    :goto_1
    return-void

    .line 1430
    :catch_0
    move-exception v0

    .line 1431
    .local v0, e:Ljava/io/IOException;
    invoke-virtual {v0}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_1

    .line 1426
    .end local v0           #e:Ljava/io/IOException;
    :catch_1
    move-exception v1

    goto :goto_0
.end method

.method public Ping()V
    .locals 1

    .prologue
    .line 1405
    const-string v0, "A"

    invoke-virtual {p0, v0}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->SendCommand(Ljava/lang/String;)Z

    .line 1410
    return-void
.end method

.method public SendCommand(Ljava/lang/String;)Z
    .locals 9
    .parameter "command"

    .prologue
    .line 1436
    iget-object v6, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->isLoggedIn:Z
    invoke-static {v6}, Lcom/openvehicles/OVMS/OVMSActivity;->access$1(Lcom/openvehicles/OVMS/OVMSActivity;)Z

    move-result v6

    if-nez v6, :cond_0

    .line 1437
    const-string v6, "TCP"

    const-string v7, "Server not ready. TX aborted."

    invoke-static {v6, v7}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1438
    const/4 v6, 0x0

    .line 1491
    :goto_0
    return v6

    .line 1441
    :cond_0
    new-instance v6, Ljava/lang/StringBuilder;

    const-string v7, "[TX] "

    invoke-direct {v6, v7}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v6, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v6}, Lcom/openvehicles/OVMS/DataLog;->Log(Ljava/lang/String;)V

    .line 1445
    :try_start_0
    const-string v5, ""

    .line 1448
    .local v5, tx_String:Ljava/lang/String;
    iget-object v6, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->carData:Lcom/openvehicles/OVMS/CarData;

    iget-boolean v6, v6, Lcom/openvehicles/OVMS/CarData;->ParanoidMode:Z

    if-eqz v6, :cond_2

    .line 1449
    const-string v6, "A"

    invoke-virtual {p1, v6}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v6

    if-nez v6, :cond_2

    .line 1450
    const-string v6, "C"

    invoke-virtual {p1, v6}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v6

    if-nez v6, :cond_2

    .line 1452
    const-string v6, "C30"

    invoke-virtual {p1, v6}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v6

    if-nez v6, :cond_2

    .line 1454
    const-string v6, "p"

    invoke-virtual {p1, v6}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v6

    if-nez v6, :cond_2

    .line 1458
    new-instance v6, Lcom/openvehicles/OVMS/RC4;

    iget-object v7, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->pmDigest:[B

    invoke-direct {v6, v7}, Lcom/openvehicles/OVMS/RC4;-><init>([B)V

    iput-object v6, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->pmcipher:Lcom/openvehicles/OVMS/RC4;

    .line 1460
    const-string v3, ""

    .line 1461
    .local v3, primeData:Ljava/lang/String;
    const/4 v0, 0x0

    .local v0, cnt:I
    :goto_1
    const/16 v6, 0x400

    if-lt v0, v6, :cond_1

    .line 1464
    iget-object v6, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->pmcipher:Lcom/openvehicles/OVMS/RC4;

    invoke-virtual {v3}, Ljava/lang/String;->getBytes()[B

    move-result-object v7

    invoke-virtual {v6, v7}, Lcom/openvehicles/OVMS/RC4;->rc4([B)[B

    .line 1465
    iget-object v6, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->pmcipher:Lcom/openvehicles/OVMS/RC4;

    invoke-virtual {p1}, Ljava/lang/String;->getBytes()[B

    move-result-object v7

    invoke-virtual {v6, v7}, Lcom/openvehicles/OVMS/RC4;->rc4([B)[B

    move-result-object v4

    .line 1468
    .local v4, txEnc:[B
    invoke-static {v4}, Lcom/openvehicles/OVMS/Base64;->encodeBytes([B)Ljava/lang/String;

    move-result-object v2

    .line 1469
    .local v2, encryptedCommand:Ljava/lang/String;
    new-instance v6, Ljava/lang/StringBuilder;

    const-string v7, "MP-0 EM"

    invoke-direct {v6, v7}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v6, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    .line 1471
    const-string v6, "TCP"

    .line 1472
    new-instance v7, Ljava/lang/StringBuilder;

    const-string v8, "TX (Paranoid-Mode Command): "

    invoke-direct {v7, v8}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v7, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    .line 1473
    const-string v8, " (using pmDigest: "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    .line 1474
    iget-object v8, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->pmDigest:[B

    invoke-static {v8}, Lcom/openvehicles/OVMS/Base64;->encodeBytes([B)Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    const-string v8, ")"

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    .line 1472
    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    .line 1471
    invoke-static {v6, v7}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1480
    .end local v0           #cnt:I
    .end local v2           #encryptedCommand:Ljava/lang/String;
    .end local v3           #primeData:Ljava/lang/String;
    .end local v4           #txEnc:[B
    :goto_2
    iget-object v6, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->txcipher:Lcom/openvehicles/OVMS/RC4;

    invoke-virtual {v5}, Ljava/lang/String;->getBytes()[B

    move-result-object v7

    invoke-virtual {v6, v7}, Lcom/openvehicles/OVMS/RC4;->rc4([B)[B

    move-result-object v4

    .line 1482
    .restart local v4       #txEnc:[B
    const-string v6, "TCP"

    new-instance v7, Ljava/lang/StringBuilder;

    const-string v8, "TX (Encrypted): "

    invoke-direct {v7, v8}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-static {v4}, Lcom/openvehicles/OVMS/Base64;->encodeBytes([B)Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 1485
    iget-object v6, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Outputstream:Ljava/io/PrintWriter;

    invoke-static {v4}, Lcom/openvehicles/OVMS/Base64;->encodeBytes([B)Ljava/lang/String;

    move-result-object v7

    invoke-virtual {v6, v7}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 1491
    .end local v4           #txEnc:[B
    .end local v5           #tx_String:Ljava/lang/String;
    :goto_3
    const/4 v6, 0x1

    goto/16 :goto_0

    .line 1462
    .restart local v0       #cnt:I
    .restart local v3       #primeData:Ljava/lang/String;
    .restart local v5       #tx_String:Ljava/lang/String;
    :cond_1
    new-instance v6, Ljava/lang/StringBuilder;

    invoke-static {v3}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v7

    invoke-direct {v6, v7}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v7, "0"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    .line 1461
    add-int/lit8 v0, v0, 0x1

    goto/16 :goto_1

    .line 1477
    .end local v0           #cnt:I
    .end local v3           #primeData:Ljava/lang/String;
    :cond_2
    new-instance v6, Ljava/lang/StringBuilder;

    const-string v7, "MP-0 "

    invoke-direct {v6, v7}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v6, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    move-result-object v5

    goto :goto_2

    .line 1487
    .end local v5           #tx_String:Ljava/lang/String;
    :catch_0
    move-exception v1

    .line 1488
    .local v1, e:Ljava/lang/Exception;
    invoke-virtual {v1}, Ljava/lang/Exception;->printStackTrace()V

    .line 1489
    iget-object v6, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #calls: Lcom/openvehicles/OVMS/OVMSActivity;->notifyServerSocketError(Ljava/lang/Exception;)V
    invoke-static {v6, v1}, Lcom/openvehicles/OVMS/OVMSActivity;->access$12(Lcom/openvehicles/OVMS/OVMSActivity;Ljava/lang/Exception;)V

    goto :goto_3
.end method

.method protected bridge varargs synthetic doInBackground([Ljava/lang/Object;)Ljava/lang/Object;
    .locals 1
    .parameter

    .prologue
    .line 1
    check-cast p1, [Ljava/lang/Void;

    invoke-virtual {p0, p1}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->doInBackground([Ljava/lang/Void;)Ljava/lang/Void;

    move-result-object v0

    return-object v0
.end method

.method protected varargs doInBackground([Ljava/lang/Void;)Ljava/lang/Void;
    .locals 10
    .parameter "arg0"

    .prologue
    const/4 v9, 0x0

    const/4 v8, 0x5

    const/4 v6, 0x0

    .line 812
    const-string v4, "TCP"

    const-string v5, "Starting background TCP thread"

    invoke-static {v4, v5}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 815
    iget-object v4, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    iput-boolean v6, v4, Lcom/openvehicles/OVMS/OVMSActivity;->SuppressServerErrorDialog:Z

    .line 816
    iput-boolean v6, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->socketMarkedClosed:Z

    .line 819
    :try_start_0
    invoke-direct {p0}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->ConnInit()V

    .line 820
    iget-object v4, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #getter for: Lcom/openvehicles/OVMS/OVMSActivity;->isLoggedIn:Z
    invoke-static {v4}, Lcom/openvehicles/OVMS/OVMSActivity;->access$1(Lcom/openvehicles/OVMS/OVMSActivity;)Z

    move-result v4

    if-eqz v4, :cond_1

    .line 821
    const-string v4, "TCP"

    const-string v5, "Background TCP ready"

    invoke-static {v4, v5}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 823
    const-string v3, ""

    .local v3, rx:Ljava/lang/String;
    const-string v1, ""

    .line 828
    .local v1, msg:Ljava/lang/String;
    iget-object v4, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Sock:Ljava/net/Socket;

    const/16 v5, 0x1388

    invoke-virtual {v4, v5}, Ljava/net/Socket;->setSoTimeout(I)V

    .line 830
    :cond_0
    :goto_0
    iget-object v4, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Sock:Ljava/net/Socket;

    invoke-virtual {v4}, Ljava/net/Socket;->isConnected()Z
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_1

    move-result v4

    if-nez v4, :cond_5

    .line 871
    .end local v1           #msg:Ljava/lang/String;
    .end local v3           #rx:Ljava/lang/String;
    :cond_1
    :goto_1
    iget-object v4, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Outputstream:Ljava/io/PrintWriter;

    if-eqz v4, :cond_2

    .line 872
    iget-object v4, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Outputstream:Ljava/io/PrintWriter;

    invoke-virtual {v4}, Ljava/io/PrintWriter;->close()V

    .line 874
    :cond_2
    :try_start_1
    iget-object v4, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Inputstream:Ljava/io/BufferedReader;

    if-eqz v4, :cond_3

    .line 875
    iget-object v4, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Inputstream:Ljava/io/BufferedReader;

    invoke-virtual {v4}, Ljava/io/BufferedReader;->close()V
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_4

    .line 879
    :cond_3
    :goto_2
    :try_start_2
    iget-object v4, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Sock:Ljava/net/Socket;

    if-eqz v4, :cond_4

    .line 880
    iget-object v4, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Sock:Ljava/net/Socket;

    invoke-virtual {v4}, Ljava/net/Socket;->close()V
    :try_end_2
    .catch Ljava/lang/Exception; {:try_start_2 .. :try_end_2} :catch_3

    .line 884
    :cond_4
    :goto_3
    iput-object v9, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Sock:Ljava/net/Socket;

    .line 886
    const-string v4, "TCP"

    const-string v5, "TCP thread ending"

    invoke-static {v4, v5}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 887
    return-object v9

    .line 831
    .restart local v1       #msg:Ljava/lang/String;
    .restart local v3       #rx:Ljava/lang/String;
    :cond_5
    :try_start_3
    const-string v3, ""
    :try_end_3
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_3} :catch_1

    .line 834
    :goto_4
    :try_start_4
    iget-object v4, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->Inputstream:Ljava/io/BufferedReader;

    invoke-virtual {v4}, Ljava/io/BufferedReader;->readLine()Ljava/lang/String;
    :try_end_4
    .catch Ljava/io/IOException; {:try_start_4 .. :try_end_4} :catch_2
    .catch Ljava/lang/Exception; {:try_start_4 .. :try_end_4} :catch_1

    move-result-object v3

    if-eqz v3, :cond_6

    .line 840
    :goto_5
    if-eqz v3, :cond_0

    :try_start_5
    invoke-virtual {v3}, Ljava/lang/String;->length()I

    move-result v4

    if-le v4, v8, :cond_0

    .line 841
    invoke-virtual {v3}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v3

    .line 842
    new-instance v1, Ljava/lang/String;

    .end local v1           #msg:Ljava/lang/String;
    iget-object v4, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->rxcipher:Lcom/openvehicles/OVMS/RC4;

    invoke-static {v3}, Lcom/openvehicles/OVMS/Base64;->decode(Ljava/lang/String;)[B

    move-result-object v5

    invoke-virtual {v4, v5}, Lcom/openvehicles/OVMS/RC4;->rc4([B)[B

    move-result-object v4

    invoke-direct {v1, v4}, Ljava/lang/String;-><init>([B)V

    .line 843
    .restart local v1       #msg:Ljava/lang/String;
    if-eqz v1, :cond_0

    invoke-virtual {v1}, Ljava/lang/String;->length()I

    move-result v4

    if-le v4, v8, :cond_0

    .line 844
    invoke-virtual {v1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v1

    .line 845
    const-string v4, "OVMS"

    .line 846
    const-string v5, "RX: %s (%s)"

    const/4 v6, 0x2

    new-array v6, v6, [Ljava/lang/Object;

    const/4 v7, 0x0

    aput-object v1, v6, v7

    const/4 v7, 0x1

    aput-object v3, v6, v7

    invoke-static {v5, v6}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v5

    .line 845
    invoke-static {v4, v5}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 848
    const/4 v4, 0x0

    const/4 v5, 0x5

    invoke-virtual {v1, v4, v5}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v4

    const-string v5, "MP-0 "

    invoke-virtual {v4, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z
    :try_end_5
    .catch Ljava/lang/Exception; {:try_start_5 .. :try_end_5} :catch_1

    move-result v4

    if-eqz v4, :cond_7

    .line 851
    const/4 v4, 0x5

    :try_start_6
    invoke-virtual {v1, v4}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object v4

    invoke-direct {p0, v4}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->processMessage(Ljava/lang/String;)V
    :try_end_6
    .catch Ljava/lang/Exception; {:try_start_6 .. :try_end_6} :catch_0

    goto/16 :goto_0

    .line 852
    :catch_0
    move-exception v2

    .line 854
    .local v2, parserException:Ljava/lang/Exception;
    :try_start_7
    new-instance v4, Ljava/lang/StringBuilder;

    const-string v5, "##ERROR## "

    invoke-direct {v4, v5}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    const-string v5, " - "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v4}, Lcom/openvehicles/OVMS/DataLog;->Log(Ljava/lang/String;)V

    .line 855
    invoke-virtual {v2}, Ljava/lang/Exception;->printStackTrace()V
    :try_end_7
    .catch Ljava/lang/Exception; {:try_start_7 .. :try_end_7} :catch_1

    goto/16 :goto_0

    .line 865
    .end local v1           #msg:Ljava/lang/String;
    .end local v2           #parserException:Ljava/lang/Exception;
    .end local v3           #rx:Ljava/lang/String;
    :catch_1
    move-exception v0

    .line 866
    .local v0, e:Ljava/lang/Exception;
    iget-boolean v4, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->socketMarkedClosed:Z

    if-nez v4, :cond_1

    .line 867
    iget-object v4, p0, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->this$0:Lcom/openvehicles/OVMS/OVMSActivity;

    #calls: Lcom/openvehicles/OVMS/OVMSActivity;->notifyServerSocketError(Ljava/lang/Exception;)V
    invoke-static {v4, v0}, Lcom/openvehicles/OVMS/OVMSActivity;->access$12(Lcom/openvehicles/OVMS/OVMSActivity;Ljava/lang/Exception;)V

    goto/16 :goto_1

    .line 835
    .end local v0           #e:Ljava/lang/Exception;
    .restart local v1       #msg:Ljava/lang/String;
    .restart local v3       #rx:Ljava/lang/String;
    :cond_6
    const-wide/16 v4, 0x64

    :try_start_8
    invoke-static {v4, v5}, Ljava/lang/Thread;->sleep(J)V
    :try_end_8
    .catch Ljava/io/IOException; {:try_start_8 .. :try_end_8} :catch_2
    .catch Ljava/lang/Exception; {:try_start_8 .. :try_end_8} :catch_1

    goto/16 :goto_4

    .line 837
    :catch_2
    move-exception v4

    goto/16 :goto_5

    .line 859
    :cond_7
    :try_start_9
    const-string v4, "OVMS"

    const-string v5, "Unknown protection scheme"

    invoke-static {v4, v5}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_9
    .catch Ljava/lang/Exception; {:try_start_9 .. :try_end_9} :catch_1

    goto/16 :goto_0

    .line 881
    .end local v1           #msg:Ljava/lang/String;
    .end local v3           #rx:Ljava/lang/String;
    :catch_3
    move-exception v4

    goto/16 :goto_3

    .line 876
    :catch_4
    move-exception v4

    goto/16 :goto_2
.end method

.method protected varargs onProgressUpdate([Ljava/lang/Integer;)V
    .locals 0
    .parameter "values"

    .prologue
    .line 808
    return-void
.end method

.method protected bridge varargs synthetic onProgressUpdate([Ljava/lang/Object;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    check-cast p1, [Ljava/lang/Integer;

    invoke-virtual {p0, p1}, Lcom/openvehicles/OVMS/OVMSActivity$TCPTask;->onProgressUpdate([Ljava/lang/Integer;)V

    return-void
.end method
