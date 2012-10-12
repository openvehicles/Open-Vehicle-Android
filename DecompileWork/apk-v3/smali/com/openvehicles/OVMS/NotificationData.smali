.class public Lcom/openvehicles/OVMS/NotificationData;
.super Ljava/lang/Object;
.source "NotificationData.java"

# interfaces
.implements Ljava/io/Serializable;


# static fields
.field private static final serialVersionUID:J = -0x2c09a40fdd1e3391L


# instance fields
.field public Message:Ljava/lang/String;

.field public Timestamp:Ljava/util/Date;

.field public Title:Ljava/lang/String;


# direct methods
.method public constructor <init>()V
    .locals 1

    .prologue
    .line 15
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 16
    new-instance v0, Ljava/util/Date;

    invoke-direct {v0}, Ljava/util/Date;-><init>()V

    iput-object v0, p0, Lcom/openvehicles/OVMS/NotificationData;->Timestamp:Ljava/util/Date;

    .line 17
    const-string v0, ""

    iput-object v0, p0, Lcom/openvehicles/OVMS/NotificationData;->Title:Ljava/lang/String;

    .line 18
    const-string v0, ""

    iput-object v0, p0, Lcom/openvehicles/OVMS/NotificationData;->Message:Ljava/lang/String;

    .line 19
    return-void
.end method

.method public constructor <init>(Ljava/util/Date;Ljava/lang/String;Ljava/lang/String;)V
    .locals 0
    .parameter "timestamp"
    .parameter "title"
    .parameter "message"

    .prologue
    .line 21
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 22
    iput-object p1, p0, Lcom/openvehicles/OVMS/NotificationData;->Timestamp:Ljava/util/Date;

    .line 23
    iput-object p2, p0, Lcom/openvehicles/OVMS/NotificationData;->Title:Ljava/lang/String;

    .line 24
    iput-object p3, p0, Lcom/openvehicles/OVMS/NotificationData;->Message:Ljava/lang/String;

    .line 25
    return-void
.end method
