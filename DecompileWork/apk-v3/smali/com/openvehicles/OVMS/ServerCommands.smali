.class public final Lcom/openvehicles/OVMS/ServerCommands;
.super Ljava/lang/Object;
.source "ServerCommands.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/openvehicles/OVMS/ServerCommands$CarLayoutDownloader;
    }
.end annotation


# static fields
.field public static final DEBUG_MODEM_RESPONSE:I = 0x31

.field public static final FEATURE_CANWRITE:I = 0xf

.field public static final FEATURE_CARBITS:I = 0xe

.field public static final FEATURE_DEBUGMODEM:I = 0x7

.field public static final FEATURE_LIST_REQUEST:Ljava/lang/String; = "C1"

.field public static final FEATURE_LIST_RESPONSE:I = 0x1

.field public static final FEATURE_MINSOC:I = 0x9

.field public static final FEATURE_SPEEDO:I = 0x0

.field public static final FEATURE_STREAM:I = 0x8

.field public static final GPRS_UTILIZATION_DATA_REQUEST:Ljava/lang/String; = "C30"

.field public static final GPRS_UTILIZATION_DATA_RESPONSE:I = 0x1e

.field public static final PARAMETER_LIST_REQUEST:Ljava/lang/String; = "C3"

.field public static final PARAMETER_LIST_RESPONSE:I = 0x3

.field public static final PARAM_FEATURE10:I = 0x1a

.field public static final PARAM_FEATURE11:I = 0x1b

.field public static final PARAM_FEATURE12:I = 0x1c

.field public static final PARAM_FEATURE13:I = 0x1d

.field public static final PARAM_FEATURE14:I = 0x1e

.field public static final PARAM_FEATURE15:I = 0x1f

.field public static final PARAM_FEATURE8:I = 0x18

.field public static final PARAM_FEATURE9:I = 0x19

.field public static final PARAM_FEATURE_E:I = 0x1f

.field public static final PARAM_FEATURE_S:I = 0x18

.field public static final PARAM_GPRSAPN:I = 0x5

.field public static final PARAM_GPRSPASS:I = 0x7

.field public static final PARAM_GPRSUSER:I = 0x6

.field public static final PARAM_MILESKM:I = 0x2

.field public static final PARAM_MYID:I = 0x8

.field public static final PARAM_NETPASS1:I = 0x9

.field public static final PARAM_NOTIFIES:I = 0x3

.field public static final PARAM_PARANOID:I = 0xa

.field public static final PARAM_REGPASS:I = 0x1

.field public static final PARAM_REGPHONE:I = 0x0

.field public static final PARAM_SERVERIP:I = 0x4

.field public static final PARAM_S_GROUP:I = 0xb

.field public static final START_CHARGE:Ljava/lang/String; = "C11"

.field public static final STOP_CHARGE:Ljava/lang/String; = "C12"

.field public static final SYSTEM_REBOOT:Ljava/lang/String; = "C5"

.field public static final WAKE_UP_CAR:Ljava/lang/String; = "C18"

.field public static final WAKE_UP_TEMP_SUBSYSTEM:Ljava/lang/String; = "C19"

.field public static final __ACTIVATE_VALET_MODE:Ljava/lang/String; = "C21"

.field public static final __DEACTIVATE_VALET_MODE:Ljava/lang/String; = "C23"

.field public static final __LAYOUT_IMAGE_URL_BASE:Ljava/lang/String; = "http://www.openvehicles.com/resources"

.field public static final __LOCK_CAR:Ljava/lang/String; = "C20"

.field public static final __SEND_AT_COMMAND:Ljava/lang/String; = "C49"

.field public static final __SEND_SMS:Ljava/lang/String; = "C40"

.field public static final __SEND_USSD:Ljava/lang/String; = "C41"

.field public static final __SET_CHARGE_CURRENT:Ljava/lang/String; = "C15"

.field public static final __SET_CHARGE_MODE:Ljava/lang/String; = "C10"

.field public static final __SET_CHARGE_MODE_AND_CURRENT:Ljava/lang/String; = "C16"

.field public static final __SET_FEATURE:Ljava/lang/String; = "C2"

.field public static final __SET_PARAMETER:Ljava/lang/String; = "C4"

.field public static final __SUBSCRIBE_GROUP:Ljava/lang/String; = "G"

.field public static final __SUBSCRIBE_PUSH_NOTIFICATIONS:Ljava/lang/String; = "p"

.field public static final __UNLOCK_CAR:Ljava/lang/String; = "C22"

.field private static final chargeModes:[Ljava/lang/CharSequence;


# direct methods
.method static constructor <clinit>()V
    .locals 3

    .prologue
    .line 404
    const/4 v0, 0x4

    new-array v0, v0, [Ljava/lang/CharSequence;

    const/4 v1, 0x0

    const-string v2, "Standard"

    aput-object v2, v0, v1

    const/4 v1, 0x1

    const-string v2, "Storage"

    aput-object v2, v0, v1

    const/4 v1, 0x2

    const-string v2, "Range"

    aput-object v2, v0, v1

    const/4 v1, 0x3

    const-string v2, "Performance"

    aput-object v2, v0, v1

    sput-object v0, Lcom/openvehicles/OVMS/ServerCommands;->chargeModes:[Ljava/lang/CharSequence;

    .line 25
    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .prologue
    .line 25
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static ACTIVATE_VALET_MODE(Ljava/lang/String;)Ljava/lang/String;
    .locals 4
    .parameter "pin"

    .prologue
    .line 539
    const-string v0, "%s,%s"

    const/4 v1, 0x2

    new-array v1, v1, [Ljava/lang/Object;

    const/4 v2, 0x0

    const-string v3, "C21"

    aput-object v3, v1, v2

    const/4 v2, 0x1

    aput-object p0, v1, v2

    invoke-static {v0, v1}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static DEACTIVATE_VALET_MODE(Ljava/lang/String;)Ljava/lang/String;
    .locals 4
    .parameter "pin"

    .prologue
    .line 543
    const-string v0, "%s,%s"

    const/4 v1, 0x2

    new-array v1, v1, [Ljava/lang/Object;

    const/4 v2, 0x0

    const-string v3, "C23"

    aput-object v3, v1, v2

    const/4 v2, 0x1

    aput-object p0, v1, v2

    invoke-static {v0, v1}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static LOCK_CAR(Ljava/lang/String;)Ljava/lang/String;
    .locals 4
    .parameter "pin"

    .prologue
    .line 531
    const-string v0, "%s,%s"

    const/4 v1, 0x2

    new-array v1, v1, [Ljava/lang/Object;

    const/4 v2, 0x0

    const-string v3, "C20"

    aput-object v3, v1, v2

    const/4 v2, 0x1

    aput-object p0, v1, v2

    invoke-static {v0, v1}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static LockUnlockCar(Landroid/content/Context;Lcom/openvehicles/OVMS/OVMSActivity;Landroid/widget/Toast;Z)Landroid/app/AlertDialog;
    .locals 12
    .parameter "mContext"
    .parameter "mApp"
    .parameter "toastDisplayed"
    .parameter "lock"

    .prologue
    const/4 v4, 0x1

    const/4 v2, 0x0

    .line 216
    new-array v8, v4, [Landroid/text/InputFilter;

    .line 217
    .local v8, pinFieldLengthLimiter:[Landroid/text/InputFilter;
    new-instance v0, Landroid/text/InputFilter$LengthFilter;

    const/16 v1, 0x8

    invoke-direct {v0, v1}, Landroid/text/InputFilter$LengthFilter;-><init>(I)V

    aput-object v0, v8, v2

    .line 219
    new-instance v9, Landroid/text/method/DigitsKeyListener;

    invoke-direct {v9, v2, v2}, Landroid/text/method/DigitsKeyListener;-><init>(ZZ)V

    .line 221
    .local v9, pinFieldListener:Landroid/text/method/DigitsKeyListener;
    new-instance v3, Landroid/widget/EditText;

    invoke-direct {v3, p0}, Landroid/widget/EditText;-><init>(Landroid/content/Context;)V

    .line 222
    .local v3, input:Landroid/widget/EditText;
    invoke-virtual {v3, v8}, Landroid/widget/EditText;->setFilters([Landroid/text/InputFilter;)V

    .line 223
    const/16 v0, 0x2000

    invoke-virtual {v3, v0}, Landroid/widget/EditText;->setInputType(I)V

    .line 224
    invoke-static {}, Landroid/text/method/PasswordTransformationMethod;->getInstance()Landroid/text/method/PasswordTransformationMethod;

    move-result-object v0

    invoke-virtual {v3, v0}, Landroid/widget/EditText;->setTransformationMethod(Landroid/text/method/TransformationMethod;)V

    .line 225
    const-string v0, "Vehicle PIN"

    invoke-virtual {v3, v0}, Landroid/widget/EditText;->setHint(Ljava/lang/CharSequence;)V

    .line 226
    invoke-virtual {v3, v9}, Landroid/widget/EditText;->setKeyListener(Landroid/text/method/KeyListener;)V

    .line 227
    new-instance v7, Landroid/app/AlertDialog$Builder;

    invoke-direct {v7, p0}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 229
    .local v7, builder:Landroid/app/AlertDialog$Builder;
    const-string v0, "Vehicle PIN:"

    invoke-virtual {v7, v0}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v1

    .line 230
    if-eqz p3, :cond_0

    const-string v0, "Lock Car"

    :goto_0
    invoke-virtual {v1, v0}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    .line 231
    invoke-virtual {v0, v4}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    .line 232
    invoke-virtual {v0, v3}, Landroid/app/AlertDialog$Builder;->setView(Landroid/view/View;)Landroid/app/AlertDialog$Builder;

    move-result-object v11

    .line 233
    if-eqz p3, :cond_1

    const-string v0, "Lock"

    move-object v10, v0

    .line 234
    :goto_1
    new-instance v0, Lcom/openvehicles/OVMS/ServerCommands$1;

    move v1, p3

    move-object v2, p1

    move-object v4, p2

    move-object v5, p0

    invoke-direct/range {v0 .. v5}, Lcom/openvehicles/OVMS/ServerCommands$1;-><init>(ZLcom/openvehicles/OVMS/OVMSActivity;Landroid/widget/EditText;Landroid/widget/Toast;Landroid/content/Context;)V

    .line 233
    invoke-virtual {v11, v10, v0}, Landroid/app/AlertDialog$Builder;->setPositiveButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    .line 250
    const-string v1, "Cancel"

    .line 251
    new-instance v2, Lcom/openvehicles/OVMS/ServerCommands$2;

    invoke-direct {v2}, Lcom/openvehicles/OVMS/ServerCommands$2;-><init>()V

    .line 250
    invoke-virtual {v0, v1, v2}, Landroid/app/AlertDialog$Builder;->setNegativeButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 257
    invoke-virtual {v7}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v6

    .line 258
    .local v6, alertDialog:Landroid/app/AlertDialog;
    invoke-virtual {v6}, Landroid/app/AlertDialog;->show()V

    .line 260
    return-object v6

    .line 230
    .end local v6           #alertDialog:Landroid/app/AlertDialog;
    :cond_0
    const-string v0, "Unlock Car"

    goto :goto_0

    .line 233
    :cond_1
    const-string v0, "Unlock"

    move-object v10, v0

    goto :goto_1
.end method

.method public static RequestC2DMRegistrationID(Landroid/content/Context;)V
    .locals 4
    .parameter "mContext"

    .prologue
    const/4 v3, 0x0

    .line 491
    const-string v1, "Initializing push notification"

    invoke-static {p0, v1, v3}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v1

    invoke-virtual {v1}, Landroid/widget/Toast;->show()V

    .line 492
    new-instance v0, Landroid/content/Intent;

    .line 493
    const-string v1, "com.google.android.c2dm.intent.REGISTER"

    .line 492
    invoke-direct {v0, v1}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 494
    .local v0, registrationIntent:Landroid/content/Intent;
    const-string v1, "app"

    .line 495
    new-instance v2, Landroid/content/Intent;

    invoke-direct {v2}, Landroid/content/Intent;-><init>()V

    invoke-static {p0, v3, v2, v3}, Landroid/app/PendingIntent;->getBroadcast(Landroid/content/Context;ILandroid/content/Intent;I)Landroid/app/PendingIntent;

    move-result-object v2

    .line 494
    invoke-virtual {v0, v1, v2}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Landroid/os/Parcelable;)Landroid/content/Intent;

    .line 496
    const-string v1, "sender"

    const-string v2, "openvehicles@gmail.com"

    invoke-virtual {v0, v1, v2}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    .line 497
    invoke-virtual {p0, v0}, Landroid/content/Context;->startService(Landroid/content/Intent;)Landroid/content/ComponentName;

    .line 507
    return-void
.end method

.method public static SEND_AT_COMMAND(Ljava/lang/String;)Ljava/lang/String;
    .locals 4
    .parameter "command"

    .prologue
    .line 547
    const-string v0, "%s,%s"

    const/4 v1, 0x2

    new-array v1, v1, [Ljava/lang/Object;

    const/4 v2, 0x0

    const-string v3, "C49"

    aput-object v3, v1, v2

    const/4 v2, 0x1

    aput-object p0, v1, v2

    invoke-static {v0, v1}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static SEND_SMS(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    .locals 4
    .parameter "destination_phone_number"
    .parameter "message_content"

    .prologue
    .line 556
    const-string v0, "%s,%s,%s"

    const/4 v1, 0x3

    new-array v1, v1, [Ljava/lang/Object;

    const/4 v2, 0x0

    const-string v3, "C40"

    aput-object v3, v1, v2

    const/4 v2, 0x1

    aput-object p0, v1, v2

    const/4 v2, 0x2

    .line 557
    aput-object p1, v1, v2

    .line 556
    invoke-static {v0, v1}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static SEND_USSD(Ljava/lang/String;)Ljava/lang/String;
    .locals 4
    .parameter "code"

    .prologue
    .line 551
    const-string v0, "%s,%s"

    const/4 v1, 0x2

    new-array v1, v1, [Ljava/lang/Object;

    const/4 v2, 0x0

    const-string v3, "C41"

    aput-object v3, v1, v2

    const/4 v2, 0x1

    aput-object p0, v1, v2

    invoke-static {v0, v1}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static SET_CHARGE_CURRENT(I)Ljava/lang/String;
    .locals 4
    .parameter "amps"

    .prologue
    .line 523
    const-string v0, "%s,%s"

    const/4 v1, 0x2

    new-array v1, v1, [Ljava/lang/Object;

    const/4 v2, 0x0

    const-string v3, "C15"

    aput-object v3, v1, v2

    const/4 v2, 0x1

    invoke-static {p0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    aput-object v3, v1, v2

    invoke-static {v0, v1}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static SET_CHARGE_MODE(I)Ljava/lang/String;
    .locals 4
    .parameter "mode"

    .prologue
    .line 519
    const-string v0, "%s,%s"

    const/4 v1, 0x2

    new-array v1, v1, [Ljava/lang/Object;

    const/4 v2, 0x0

    const-string v3, "C10"

    aput-object v3, v1, v2

    const/4 v2, 0x1

    invoke-static {p0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    aput-object v3, v1, v2

    invoke-static {v0, v1}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static SET_CHARGE_MODE_AND_CURRENT(II)Ljava/lang/String;
    .locals 4
    .parameter "mode"
    .parameter "amps"

    .prologue
    .line 527
    const-string v0, "%s,%s,%s"

    const/4 v1, 0x3

    new-array v1, v1, [Ljava/lang/Object;

    const/4 v2, 0x0

    const-string v3, "C16"

    aput-object v3, v1, v2

    const/4 v2, 0x1

    invoke-static {p0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    aput-object v3, v1, v2

    const/4 v2, 0x2

    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    aput-object v3, v1, v2

    invoke-static {v0, v1}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static SET_FEATURE(ILjava/lang/String;)Ljava/lang/String;
    .locals 4
    .parameter "feature"
    .parameter "value"

    .prologue
    .line 511
    const-string v0, "%s,%s,%s"

    const/4 v1, 0x3

    new-array v1, v1, [Ljava/lang/Object;

    const/4 v2, 0x0

    const-string v3, "C2"

    aput-object v3, v1, v2

    const/4 v2, 0x1

    invoke-static {p0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    aput-object v3, v1, v2

    const/4 v2, 0x2

    aput-object p1, v1, v2

    invoke-static {v0, v1}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static SET_PARAMETER(ILjava/lang/String;)Ljava/lang/String;
    .locals 4
    .parameter "param"
    .parameter "value"

    .prologue
    .line 515
    const-string v0, "%s,%s,%s"

    const/4 v1, 0x3

    new-array v1, v1, [Ljava/lang/Object;

    const/4 v2, 0x0

    const-string v3, "C4"

    aput-object v3, v1, v2

    const/4 v2, 0x1

    invoke-static {p0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    aput-object v3, v1, v2

    const/4 v2, 0x2

    aput-object p1, v1, v2

    invoke-static {v0, v1}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static SUBSCRIBE_GROUP(Ljava/lang/String;)Ljava/lang/String;
    .locals 4
    .parameter "groupName"

    .prologue
    .line 568
    const-string v0, "%s%s,1"

    const/4 v1, 0x2

    new-array v1, v1, [Ljava/lang/Object;

    const/4 v2, 0x0

    const-string v3, "G"

    aput-object v3, v1, v2

    const/4 v2, 0x1

    aput-object p0, v1, v2

    invoke-static {v0, v1}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static SUBSCRIBE_PUSH_NOTIFICATIONS(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    .locals 4
    .parameter "appID"
    .parameter "vehicleID"
    .parameter "netPass"
    .parameter "c2dmRegistrationID"

    .prologue
    .line 562
    const-string v0, "%s%s,c2dm,production,%s,%s,%s"

    const/4 v1, 0x5

    new-array v1, v1, [Ljava/lang/Object;

    const/4 v2, 0x0

    .line 563
    const-string v3, "p"

    aput-object v3, v1, v2

    const/4 v2, 0x1

    aput-object p0, v1, v2

    const/4 v2, 0x2

    aput-object p1, v1, v2

    const/4 v2, 0x3

    aput-object p2, v1, v2

    const/4 v2, 0x4

    .line 564
    aput-object p3, v1, v2

    .line 562
    invoke-static {v0, v1}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static SetChargeCurrent(Landroid/content/Context;Lcom/openvehicles/OVMS/OVMSActivity;Landroid/widget/Toast;I)Landroid/app/AlertDialog;
    .locals 11
    .parameter "mContext"
    .parameter "mApp"
    .parameter "toastDisplayed"
    .parameter "initialCurrent"

    .prologue
    const/4 v10, 0x1

    const/4 v9, 0x0

    .line 358
    new-array v5, v10, [Landroid/text/InputFilter;

    .line 359
    .local v5, pinFieldLengthLimiter:[Landroid/text/InputFilter;
    new-instance v6, Landroid/text/InputFilter$LengthFilter;

    const/16 v7, 0x8

    invoke-direct {v6, v7}, Landroid/text/InputFilter$LengthFilter;-><init>(I)V

    aput-object v6, v5, v9

    .line 360
    new-instance v4, Landroid/text/method/DigitsKeyListener;

    invoke-direct {v4, v9, v9}, Landroid/text/method/DigitsKeyListener;-><init>(ZZ)V

    .line 362
    .local v4, numericFieldListener:Landroid/text/method/DigitsKeyListener;
    new-array v1, v10, [Landroid/text/InputFilter;

    .line 363
    .local v1, ampsFieldLengthLimiter:[Landroid/text/InputFilter;
    new-instance v6, Landroid/text/InputFilter$LengthFilter;

    const/4 v7, 0x2

    invoke-direct {v6, v7}, Landroid/text/InputFilter$LengthFilter;-><init>(I)V

    aput-object v6, v1, v9

    .line 365
    new-instance v3, Landroid/widget/EditText;

    invoke-direct {v3, p0}, Landroid/widget/EditText;-><init>(Landroid/content/Context;)V

    .line 366
    .local v3, input:Landroid/widget/EditText;
    invoke-virtual {v3, v1}, Landroid/widget/EditText;->setFilters([Landroid/text/InputFilter;)V

    .line 367
    const/16 v6, 0x2000

    invoke-virtual {v3, v6}, Landroid/widget/EditText;->setInputType(I)V

    .line 368
    invoke-virtual {v3, v4}, Landroid/widget/EditText;->setKeyListener(Landroid/text/method/KeyListener;)V

    .line 369
    const-string v6, "Charge Current (Amps)"

    invoke-virtual {v3, v6}, Landroid/widget/EditText;->setHint(Ljava/lang/CharSequence;)V

    .line 370
    const-string v6, "%s"

    new-array v7, v10, [Ljava/lang/Object;

    invoke-static {p3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v8

    aput-object v8, v7, v9

    invoke-static {v6, v7}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v3, v6}, Landroid/widget/EditText;->setText(Ljava/lang/CharSequence;)V

    .line 371
    new-instance v2, Landroid/app/AlertDialog$Builder;

    invoke-direct {v2, p0}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 373
    .local v2, builder:Landroid/app/AlertDialog$Builder;
    const-string v6, "Enter desired amps (10 - 70):"

    invoke-virtual {v2, v6}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v6

    .line 374
    const-string v7, "Set Maximum Current"

    invoke-virtual {v6, v7}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v6

    .line 375
    invoke-virtual {v6, v10}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    move-result-object v6

    .line 376
    invoke-virtual {v6, v3}, Landroid/app/AlertDialog$Builder;->setView(Landroid/view/View;)Landroid/app/AlertDialog$Builder;

    move-result-object v6

    .line 377
    const-string v7, "Set"

    .line 378
    new-instance v8, Lcom/openvehicles/OVMS/ServerCommands$7;

    invoke-direct {v8, v3, p1, p2, p0}, Lcom/openvehicles/OVMS/ServerCommands$7;-><init>(Landroid/widget/EditText;Lcom/openvehicles/OVMS/OVMSActivity;Landroid/widget/Toast;Landroid/content/Context;)V

    .line 377
    invoke-virtual {v6, v7, v8}, Landroid/app/AlertDialog$Builder;->setPositiveButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v6

    .line 391
    const-string v7, "Cancel"

    .line 392
    new-instance v8, Lcom/openvehicles/OVMS/ServerCommands$8;

    invoke-direct {v8}, Lcom/openvehicles/OVMS/ServerCommands$8;-><init>()V

    .line 391
    invoke-virtual {v6, v7, v8}, Landroid/app/AlertDialog$Builder;->setNegativeButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 398
    invoke-virtual {v2}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v0

    .line 399
    .local v0, alertDialog:Landroid/app/AlertDialog;
    invoke-virtual {v0}, Landroid/app/AlertDialog;->show()V

    .line 401
    return-object v0
.end method

.method public static SetChargeMode(Landroid/content/Context;Lcom/openvehicles/OVMS/OVMSActivity;Landroid/widget/Toast;)Landroid/app/AlertDialog;
    .locals 5
    .parameter "mContext"
    .parameter "mApp"
    .parameter "toastDisplayed"

    .prologue
    .line 407
    new-instance v1, Landroid/app/AlertDialog$Builder;

    invoke-direct {v1, p0}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 409
    .local v1, builder:Landroid/app/AlertDialog$Builder;
    const-string v2, "Set Charge Mode"

    invoke-virtual {v1, v2}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v2

    .line 410
    const/4 v3, 0x1

    invoke-virtual {v2, v3}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    move-result-object v2

    .line 411
    sget-object v3, Lcom/openvehicles/OVMS/ServerCommands;->chargeModes:[Ljava/lang/CharSequence;

    .line 412
    new-instance v4, Lcom/openvehicles/OVMS/ServerCommands$9;

    invoke-direct {v4, p1, p2, p0}, Lcom/openvehicles/OVMS/ServerCommands$9;-><init>(Lcom/openvehicles/OVMS/OVMSActivity;Landroid/widget/Toast;Landroid/content/Context;)V

    .line 411
    invoke-virtual {v2, v3, v4}, Landroid/app/AlertDialog$Builder;->setItems([Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 422
    invoke-virtual {v1}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v0

    .line 423
    .local v0, alertDialog:Landroid/app/AlertDialog;
    invoke-virtual {v0}, Landroid/app/AlertDialog;->show()V

    .line 425
    return-object v0
.end method

.method public static StartCharge(Landroid/content/Context;Lcom/openvehicles/OVMS/OVMSActivity;Landroid/widget/Toast;)Landroid/app/AlertDialog;
    .locals 5
    .parameter "mContext"
    .parameter "mApp"
    .parameter "toastDisplayed"

    .prologue
    .line 461
    new-instance v1, Landroid/app/AlertDialog$Builder;

    invoke-direct {v1, p0}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 463
    .local v1, builder:Landroid/app/AlertDialog$Builder;
    const-string v2, "Do you want to start charging the car now?"

    invoke-virtual {v1, v2}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v2

    .line 464
    const-string v3, "Start Charging"

    invoke-virtual {v2, v3}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v2

    .line 465
    const/4 v3, 0x1

    invoke-virtual {v2, v3}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    move-result-object v2

    .line 466
    const-string v3, "Start"

    .line 467
    new-instance v4, Lcom/openvehicles/OVMS/ServerCommands$12;

    invoke-direct {v4, p1, p2, p0}, Lcom/openvehicles/OVMS/ServerCommands$12;-><init>(Lcom/openvehicles/OVMS/OVMSActivity;Landroid/widget/Toast;Landroid/content/Context;)V

    .line 466
    invoke-virtual {v2, v3, v4}, Landroid/app/AlertDialog$Builder;->setPositiveButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v2

    .line 477
    const-string v3, "Cancel"

    .line 478
    new-instance v4, Lcom/openvehicles/OVMS/ServerCommands$13;

    invoke-direct {v4}, Lcom/openvehicles/OVMS/ServerCommands$13;-><init>()V

    .line 477
    invoke-virtual {v2, v3, v4}, Landroid/app/AlertDialog$Builder;->setNegativeButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 484
    invoke-virtual {v1}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v0

    .line 485
    .local v0, alertDialog:Landroid/app/AlertDialog;
    invoke-virtual {v0}, Landroid/app/AlertDialog;->show()V

    .line 486
    return-object v0
.end method

.method public static StopCharge(Landroid/content/Context;Lcom/openvehicles/OVMS/OVMSActivity;Landroid/widget/Toast;)Landroid/app/AlertDialog;
    .locals 5
    .parameter "mContext"
    .parameter "mApp"
    .parameter "toastDisplayed"

    .prologue
    .line 431
    new-instance v1, Landroid/app/AlertDialog$Builder;

    invoke-direct {v1, p0}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 433
    .local v1, builder:Landroid/app/AlertDialog$Builder;
    const-string v2, "Do you want to stop the car from charging now?"

    invoke-virtual {v1, v2}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v2

    .line 434
    const-string v3, "Stop Charging"

    invoke-virtual {v2, v3}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v2

    .line 435
    const/4 v3, 0x1

    invoke-virtual {v2, v3}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    move-result-object v2

    .line 436
    const-string v3, "Stop"

    .line 437
    new-instance v4, Lcom/openvehicles/OVMS/ServerCommands$10;

    invoke-direct {v4, p1, p2, p0}, Lcom/openvehicles/OVMS/ServerCommands$10;-><init>(Lcom/openvehicles/OVMS/OVMSActivity;Landroid/widget/Toast;Landroid/content/Context;)V

    .line 436
    invoke-virtual {v2, v3, v4}, Landroid/app/AlertDialog$Builder;->setPositiveButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v2

    .line 447
    const-string v3, "Cancel"

    .line 448
    new-instance v4, Lcom/openvehicles/OVMS/ServerCommands$11;

    invoke-direct {v4}, Lcom/openvehicles/OVMS/ServerCommands$11;-><init>()V

    .line 447
    invoke-virtual {v2, v3, v4}, Landroid/app/AlertDialog$Builder;->setNegativeButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 454
    invoke-virtual {v1}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v0

    .line 455
    .local v0, alertDialog:Landroid/app/AlertDialog;
    invoke-virtual {v0}, Landroid/app/AlertDialog;->show()V

    .line 456
    return-object v0
.end method

.method public static UNLOCK_CAR(Ljava/lang/String;)Ljava/lang/String;
    .locals 4
    .parameter "pin"

    .prologue
    .line 535
    const-string v0, "%s,%s"

    const/4 v1, 0x2

    new-array v1, v1, [Ljava/lang/Object;

    const/4 v2, 0x0

    const-string v3, "C22"

    aput-object v3, v1, v2

    const/4 v2, 0x1

    aput-object p0, v1, v2

    invoke-static {v0, v1}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static ValetModeOnOff(Landroid/content/Context;Lcom/openvehicles/OVMS/OVMSActivity;Landroid/widget/Toast;Z)Landroid/app/AlertDialog;
    .locals 12
    .parameter "mContext"
    .parameter "mApp"
    .parameter "toastDisplayed"
    .parameter "valetOn"

    .prologue
    const/4 v4, 0x1

    const/4 v2, 0x0

    .line 275
    new-array v8, v4, [Landroid/text/InputFilter;

    .line 276
    .local v8, pinFieldLengthLimiter:[Landroid/text/InputFilter;
    new-instance v0, Landroid/text/InputFilter$LengthFilter;

    const/16 v1, 0x8

    invoke-direct {v0, v1}, Landroid/text/InputFilter$LengthFilter;-><init>(I)V

    aput-object v0, v8, v2

    .line 278
    new-instance v9, Landroid/text/method/DigitsKeyListener;

    invoke-direct {v9, v2, v2}, Landroid/text/method/DigitsKeyListener;-><init>(ZZ)V

    .line 280
    .local v9, pinFieldListener:Landroid/text/method/DigitsKeyListener;
    new-instance v3, Landroid/widget/EditText;

    invoke-direct {v3, p0}, Landroid/widget/EditText;-><init>(Landroid/content/Context;)V

    .line 281
    .local v3, input:Landroid/widget/EditText;
    invoke-virtual {v3, v8}, Landroid/widget/EditText;->setFilters([Landroid/text/InputFilter;)V

    .line 282
    const/16 v0, 0x2000

    invoke-virtual {v3, v0}, Landroid/widget/EditText;->setInputType(I)V

    .line 283
    invoke-static {}, Landroid/text/method/PasswordTransformationMethod;->getInstance()Landroid/text/method/PasswordTransformationMethod;

    move-result-object v0

    invoke-virtual {v3, v0}, Landroid/widget/EditText;->setTransformationMethod(Landroid/text/method/TransformationMethod;)V

    .line 284
    const-string v0, "Vehicle PIN"

    invoke-virtual {v3, v0}, Landroid/widget/EditText;->setHint(Ljava/lang/CharSequence;)V

    .line 285
    invoke-virtual {v3, v9}, Landroid/widget/EditText;->setKeyListener(Landroid/text/method/KeyListener;)V

    .line 286
    new-instance v7, Landroid/app/AlertDialog$Builder;

    invoke-direct {v7, p0}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 288
    .local v7, builder:Landroid/app/AlertDialog$Builder;
    const-string v0, "Vehicle PIN:"

    invoke-virtual {v7, v0}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v1

    .line 289
    if-eqz p3, :cond_0

    const-string v0, "Activate Valet Mode"

    :goto_0
    invoke-virtual {v1, v0}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    .line 290
    invoke-virtual {v0, v4}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    .line 291
    invoke-virtual {v0, v3}, Landroid/app/AlertDialog$Builder;->setView(Landroid/view/View;)Landroid/app/AlertDialog$Builder;

    move-result-object v11

    .line 292
    if-eqz p3, :cond_1

    const-string v0, "Activate"

    move-object v10, v0

    .line 293
    :goto_1
    new-instance v0, Lcom/openvehicles/OVMS/ServerCommands$3;

    move v1, p3

    move-object v2, p1

    move-object v4, p2

    move-object v5, p0

    invoke-direct/range {v0 .. v5}, Lcom/openvehicles/OVMS/ServerCommands$3;-><init>(ZLcom/openvehicles/OVMS/OVMSActivity;Landroid/widget/EditText;Landroid/widget/Toast;Landroid/content/Context;)V

    .line 292
    invoke-virtual {v11, v10, v0}, Landroid/app/AlertDialog$Builder;->setPositiveButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v0

    .line 309
    const-string v1, "Cancel"

    .line 310
    new-instance v2, Lcom/openvehicles/OVMS/ServerCommands$4;

    invoke-direct {v2}, Lcom/openvehicles/OVMS/ServerCommands$4;-><init>()V

    .line 309
    invoke-virtual {v0, v1, v2}, Landroid/app/AlertDialog$Builder;->setNegativeButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 316
    invoke-virtual {v7}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v6

    .line 317
    .local v6, alertDialog:Landroid/app/AlertDialog;
    invoke-virtual {v6}, Landroid/app/AlertDialog;->show()V

    .line 319
    return-object v6

    .line 289
    .end local v6           #alertDialog:Landroid/app/AlertDialog;
    :cond_0
    const-string v0, "Deactivate Valet Mode"

    goto :goto_0

    .line 292
    :cond_1
    const-string v0, "Deactivate"

    move-object v10, v0

    goto :goto_1
.end method

.method public static WakeUp(Landroid/content/Context;Lcom/openvehicles/OVMS/OVMSActivity;Landroid/widget/Toast;Z)Landroid/app/AlertDialog;
    .locals 5
    .parameter "mContext"
    .parameter "mApp"
    .parameter "toastDisplayed"
    .parameter "wakeUpSensorsOnly"

    .prologue
    .line 324
    new-instance v1, Landroid/app/AlertDialog$Builder;

    invoke-direct {v1, p0}, Landroid/app/AlertDialog$Builder;-><init>(Landroid/content/Context;)V

    .line 326
    .local v1, builder:Landroid/app/AlertDialog$Builder;
    if-eqz p3, :cond_0

    const-string v2, "Wake up the sensor systems now?"

    :goto_0
    invoke-virtual {v1, v2}, Landroid/app/AlertDialog$Builder;->setMessage(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v3

    .line 327
    if-eqz p3, :cond_1

    const-string v2, "Wake Up Sensors"

    :goto_1
    invoke-virtual {v3, v2}, Landroid/app/AlertDialog$Builder;->setTitle(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;

    move-result-object v2

    .line 328
    const/4 v3, 0x1

    invoke-virtual {v2, v3}, Landroid/app/AlertDialog$Builder;->setCancelable(Z)Landroid/app/AlertDialog$Builder;

    move-result-object v2

    .line 329
    const-string v3, "Wake Up"

    .line 330
    new-instance v4, Lcom/openvehicles/OVMS/ServerCommands$5;

    invoke-direct {v4, p3, p1, p2, p0}, Lcom/openvehicles/OVMS/ServerCommands$5;-><init>(ZLcom/openvehicles/OVMS/OVMSActivity;Landroid/widget/Toast;Landroid/content/Context;)V

    .line 329
    invoke-virtual {v2, v3, v4}, Landroid/app/AlertDialog$Builder;->setPositiveButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    move-result-object v2

    .line 342
    const-string v3, "Cancel"

    .line 343
    new-instance v4, Lcom/openvehicles/OVMS/ServerCommands$6;

    invoke-direct {v4}, Lcom/openvehicles/OVMS/ServerCommands$6;-><init>()V

    .line 342
    invoke-virtual {v2, v3, v4}, Landroid/app/AlertDialog$Builder;->setNegativeButton(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;

    .line 349
    invoke-virtual {v1}, Landroid/app/AlertDialog$Builder;->create()Landroid/app/AlertDialog;

    move-result-object v0

    .line 350
    .local v0, alertDialog:Landroid/app/AlertDialog;
    invoke-virtual {v0}, Landroid/app/AlertDialog;->show()V

    .line 352
    return-object v0

    .line 326
    .end local v0           #alertDialog:Landroid/app/AlertDialog;
    :cond_0
    const-string v2, "Wake up the car and its sensor systems now?"

    goto :goto_0

    .line 327
    :cond_1
    const-string v2, "Wake Up Car"

    goto :goto_1
.end method

.method public static makeToast(Landroid/widget/Toast;Landroid/content/Context;Ljava/lang/String;I)V
    .locals 0
    .parameter "toastDisplayed"
    .parameter "mContext"
    .parameter "text"
    .parameter "duration"

    .prologue
    .line 265
    if-eqz p0, :cond_0

    .line 266
    invoke-virtual {p0}, Landroid/widget/Toast;->cancel()V

    .line 267
    const/4 p0, 0x0

    .line 269
    :cond_0
    invoke-static {p1, p2, p3}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object p0

    .line 270
    invoke-virtual {p0}, Landroid/widget/Toast;->show()V

    .line 271
    return-void
.end method

.method public static toString(I)Ljava/lang/String;
    .locals 1
    .parameter "commandID"

    .prologue
    .line 572
    sparse-switch p0, :sswitch_data_0

    .line 606
    const/4 v0, 0x0

    :goto_0
    return-object v0

    .line 574
    :sswitch_0
    const-string v0, "Features Request"

    goto :goto_0

    .line 576
    :sswitch_1
    const-string v0, "Set Feature"

    goto :goto_0

    .line 578
    :sswitch_2
    const-string v0, "Parameters Request"

    goto :goto_0

    .line 580
    :sswitch_3
    const-string v0, "Set Parameter"

    goto :goto_0

    .line 582
    :sswitch_4
    const-string v0, "System Reboot"

    goto :goto_0

    .line 584
    :sswitch_5
    const-string v0, "Set Charge Mode"

    goto :goto_0

    .line 586
    :sswitch_6
    const-string v0, "Start Charge"

    goto :goto_0

    .line 588
    :sswitch_7
    const-string v0, "Stop Charge"

    goto :goto_0

    .line 590
    :sswitch_8
    const-string v0, "Lock Car"

    goto :goto_0

    .line 592
    :sswitch_9
    const-string v0, "Activate Valet Mode"

    goto :goto_0

    .line 594
    :sswitch_a
    const-string v0, "Unlock Car"

    goto :goto_0

    .line 596
    :sswitch_b
    const-string v0, "Deactivate Valet Mode"

    goto :goto_0

    .line 598
    :sswitch_c
    const-string v0, "GPRS Utilization Request"

    goto :goto_0

    .line 600
    :sswitch_d
    const-string v0, "SMS Relay"

    goto :goto_0

    .line 602
    :sswitch_e
    const-string v0, "USSD Command"

    goto :goto_0

    .line 604
    :sswitch_f
    const-string v0, "AT Command"

    goto :goto_0

    .line 572
    nop

    :sswitch_data_0
    .sparse-switch
        0x1 -> :sswitch_0
        0x2 -> :sswitch_1
        0x3 -> :sswitch_2
        0x4 -> :sswitch_3
        0x5 -> :sswitch_4
        0xa -> :sswitch_5
        0xb -> :sswitch_6
        0xc -> :sswitch_7
        0x14 -> :sswitch_8
        0x15 -> :sswitch_9
        0x16 -> :sswitch_a
        0x17 -> :sswitch_b
        0x1e -> :sswitch_c
        0x28 -> :sswitch_d
        0x29 -> :sswitch_e
        0x31 -> :sswitch_f
    .end sparse-switch
.end method
