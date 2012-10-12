.class public Lcom/openvehicles/OVMS/NotificationData;
.super Ljava/lang/Object;
.source "NotificationData.java"

# interfaces
.implements Ljava/io/Serializable;


# instance fields
.field public Message:Ljava/lang/String;

.field public Timestamp:Ljava/util/Date;

.field public Title:Ljava/lang/String;


# direct methods
.method public constructor <init>()V
    .locals 0

    .prologue
    .line 11
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 13
    return-void
.end method

.method public constructor <init>(Ljava/util/Date;Ljava/lang/String;Ljava/lang/String;)V
    .locals 0
    .parameter "timestamp"
    .parameter "title"
    .parameter "message"

    .prologue
    .line 15
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 16
    iput-object p1, p0, Lcom/openvehicles/OVMS/NotificationData;->Timestamp:Ljava/util/Date;

    .line 17
    iput-object p2, p0, Lcom/openvehicles/OVMS/NotificationData;->Title:Ljava/lang/String;

    .line 18
    iput-object p3, p0, Lcom/openvehicles/OVMS/NotificationData;->Message:Ljava/lang/String;

    .line 19
    return-void
.end method
