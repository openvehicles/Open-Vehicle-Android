.class public Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;
.super Landroid/app/Activity;
.source "Tab_SubTabDataUtilizations.java"


# instance fields
.field chart:Landroid/view/View;

.field private data:Lcom/openvehicles/OVMS/CarData;

.field private handler:Landroid/os/Handler;

.field private isLoggedIn:Z

.field private lastRefresh:Ljava/util/Date;

.field private lastVehicleID:Ljava/lang/String;

.field private mOVMSActivity:Lcom/openvehicles/OVMS/OVMSActivity;


# direct methods
.method public constructor <init>()V
    .locals 2

    .prologue
    const/4 v1, 0x0

    .line 37
    invoke-direct {p0}, Landroid/app/Activity;-><init>()V

    .line 65
    iput-object v1, p0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->lastRefresh:Ljava/util/Date;

    .line 66
    const-string v0, ""

    iput-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->lastVehicleID:Ljava/lang/String;

    .line 67
    iput-object v1, p0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->chart:Landroid/view/View;

    .line 318
    new-instance v0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations$1;

    invoke-direct {v0, p0}, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations$1;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;)V

    iput-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->handler:Landroid/os/Handler;

    .line 37
    return-void
.end method

.method static synthetic access$0(Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;)V
    .locals 0
    .parameter

    .prologue
    .line 69
    invoke-direct {p0}, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->refreshChart()V

    return-void
.end method

.method static synthetic access$1(Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;)Lcom/openvehicles/OVMS/CarData;
    .locals 1
    .parameter

    .prologue
    .line 63
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->data:Lcom/openvehicles/OVMS/CarData;

    return-object v0
.end method

.method static synthetic access$2(Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;)V
    .locals 0
    .parameter

    .prologue
    .line 349
    invoke-direct {p0}, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->requestData()V

    return-void
.end method

.method private refreshChart()V
    .locals 48

    .prologue
    .line 71
    move-object/from16 v0, p0

    iget-object v3, v0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v3, v3, Lcom/openvehicles/OVMS/CarData;->Data_GPRSUtilization:Lcom/openvehicles/OVMS/GPRSUtilization;

    if-nez v3, :cond_0

    .line 230
    :goto_0
    return-void

    .line 74
    :cond_0
    const/16 v18, -0x1

    .line 75
    .local v18, BAR_LABEL_OFFSET:I
    const/16 v19, 0x7

    .line 77
    .local v19, BAR_LABEL_STEP:I
    new-instance v27, Ljava/text/SimpleDateFormat;

    const-string v3, "MMM dd"

    move-object/from16 v0, v27

    invoke-direct {v0, v3}, Ljava/text/SimpleDateFormat;-><init>(Ljava/lang/String;)V

    .line 78
    .local v27, dateFormat:Ljava/text/SimpleDateFormat;
    invoke-static {}, Ljava/util/TimeZone;->getDefault()Ljava/util/TimeZone;

    move-result-object v3

    move-object/from16 v0, v27

    invoke-virtual {v0, v3}, Ljava/text/SimpleDateFormat;->setTimeZone(Ljava/util/TimeZone;)V

    .line 80
    new-instance v28, Ljava/text/SimpleDateFormat;

    const-string v3, "yyyy-MM-dd"

    move-object/from16 v0, v28

    invoke-direct {v0, v3}, Ljava/text/SimpleDateFormat;-><init>(Ljava/lang/String;)V

    .line 81
    .local v28, dateOnlyParser:Ljava/text/SimpleDateFormat;
    const/16 v36, 0x0

    .line 83
    .local v36, today:Ljava/util/Date;
    :try_start_0
    new-instance v3, Ljava/util/Date;

    invoke-direct {v3}, Ljava/util/Date;-><init>()V

    move-object/from16 v0, v28

    invoke-virtual {v0, v3}, Ljava/text/SimpleDateFormat;->format(Ljava/util/Date;)Ljava/lang/String;

    move-result-object v3

    move-object/from16 v0, v28

    invoke-virtual {v0, v3}, Ljava/text/SimpleDateFormat;->parse(Ljava/lang/String;)Ljava/util/Date;
    :try_end_0
    .catch Ljava/text/ParseException; {:try_start_0 .. :try_end_0} :catch_0

    move-result-object v36

    .line 89
    :goto_1
    invoke-static {}, Ljava/util/Calendar;->getInstance()Ljava/util/Calendar;

    move-result-object v22

    .line 91
    .local v22, calendar:Ljava/util/Calendar;
    const/4 v3, 0x2

    const/4 v5, -0x1

    move-object/from16 v0, v22

    invoke-virtual {v0, v3, v5}, Ljava/util/Calendar;->add(II)V

    .line 94
    move-object/from16 v0, p0

    iget-object v3, v0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v3, v3, Lcom/openvehicles/OVMS/CarData;->Data_GPRSUtilization:Lcom/openvehicles/OVMS/GPRSUtilization;

    iget-object v3, v3, Lcom/openvehicles/OVMS/GPRSUtilization;->Utilizations:Ljava/util/ArrayList;

    .line 95
    move-object/from16 v0, p0

    iget-object v5, v0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v5, v5, Lcom/openvehicles/OVMS/CarData;->Data_GPRSUtilization:Lcom/openvehicles/OVMS/GPRSUtilization;

    iget-object v5, v5, Lcom/openvehicles/OVMS/GPRSUtilization;->Utilizations:Ljava/util/ArrayList;

    invoke-virtual {v5}, Ljava/util/ArrayList;->size()I

    move-result v5

    add-int/lit8 v5, v5, -0x1

    invoke-virtual {v3, v5}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/openvehicles/OVMS/GPRSUtilizationData;

    iget-object v0, v3, Lcom/openvehicles/OVMS/GPRSUtilizationData;->DataDate:Ljava/util/Date;

    move-object/from16 v26, v0

    .line 96
    .local v26, dataStartDate:Ljava/util/Date;
    move-object/from16 v0, p0

    iget-object v3, v0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v3, v3, Lcom/openvehicles/OVMS/CarData;->Data_GPRSUtilization:Lcom/openvehicles/OVMS/GPRSUtilization;

    iget-object v3, v3, Lcom/openvehicles/OVMS/GPRSUtilization;->Utilizations:Ljava/util/ArrayList;

    .line 97
    const/4 v5, 0x0

    invoke-virtual {v3, v5}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/openvehicles/OVMS/GPRSUtilizationData;

    iget-object v0, v3, Lcom/openvehicles/OVMS/GPRSUtilizationData;->DataDate:Ljava/util/Date;

    move-object/from16 v25, v0

    .line 99
    .local v25, dataEndDate:Ljava/util/Date;
    const-string v3, "CHART"

    new-instance v5, Ljava/lang/StringBuilder;

    const-string v6, "Summing data from: "

    invoke-direct {v5, v6}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual/range {v26 .. v26}, Ljava/util/Date;->toLocaleString()Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, " to "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual/range {v25 .. v25}, Ljava/util/Date;->toLocaleString()Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, " flags "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    move-object/from16 v0, p0

    iget-object v6, v0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_GPRSUtilization:Lcom/openvehicles/OVMS/GPRSUtilization;

    move-object/from16 v0, p0

    iget-object v6, v0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_GPRSUtilization:Lcom/openvehicles/OVMS/GPRSUtilization;

    const/4 v6, 0x3

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v3, v5}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 101
    move-object/from16 v0, p0

    iget-object v3, v0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v3, v3, Lcom/openvehicles/OVMS/CarData;->Data_GPRSUtilization:Lcom/openvehicles/OVMS/GPRSUtilization;

    move-object/from16 v0, p0

    iget-object v5, v0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v5, v5, Lcom/openvehicles/OVMS/CarData;->Data_GPRSUtilization:Lcom/openvehicles/OVMS/GPRSUtilization;

    move-object/from16 v0, p0

    iget-object v5, v0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v5, v5, Lcom/openvehicles/OVMS/CarData;->Data_GPRSUtilization:Lcom/openvehicles/OVMS/GPRSUtilization;

    const/4 v5, 0x3

    move-object/from16 v0, v26

    invoke-virtual {v3, v0, v5}, Lcom/openvehicles/OVMS/GPRSUtilization;->GetUtilizationBytes(Ljava/util/Date;I)J

    move-result-wide v41

    .line 102
    .local v41, total_lifetime:J
    move-object/from16 v0, p0

    iget-object v3, v0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v3, v3, Lcom/openvehicles/OVMS/CarData;->Data_GPRSUtilization:Lcom/openvehicles/OVMS/GPRSUtilization;

    invoke-virtual/range {v22 .. v22}, Ljava/util/Calendar;->getTime()Ljava/util/Date;

    move-result-object v5

    move-object/from16 v0, p0

    iget-object v6, v0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_GPRSUtilization:Lcom/openvehicles/OVMS/GPRSUtilization;

    move-object/from16 v0, p0

    iget-object v6, v0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v6, v6, Lcom/openvehicles/OVMS/CarData;->Data_GPRSUtilization:Lcom/openvehicles/OVMS/GPRSUtilization;

    const/4 v6, 0x3

    invoke-virtual {v3, v5, v6}, Lcom/openvehicles/OVMS/GPRSUtilization;->GetUtilizationBytes(Ljava/util/Date;I)J

    move-result-wide v39

    .line 103
    .local v39, total_30day:J
    move-object/from16 v0, p0

    iget-object v3, v0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v3, v3, Lcom/openvehicles/OVMS/CarData;->Data_GPRSUtilization:Lcom/openvehicles/OVMS/GPRSUtilization;

    move-object/from16 v0, p0

    iget-object v5, v0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v5, v5, Lcom/openvehicles/OVMS/CarData;->Data_GPRSUtilization:Lcom/openvehicles/OVMS/GPRSUtilization;

    move-object/from16 v0, p0

    iget-object v5, v0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v5, v5, Lcom/openvehicles/OVMS/CarData;->Data_GPRSUtilization:Lcom/openvehicles/OVMS/GPRSUtilization;

    const/4 v5, 0x3

    move-object/from16 v0, v36

    invoke-virtual {v3, v0, v5}, Lcom/openvehicles/OVMS/GPRSUtilization;->GetUtilizationBytes(Ljava/util/Date;I)J

    move-result-wide v43

    .line 105
    .local v43, total_today:J
    invoke-virtual/range {v36 .. v36}, Ljava/util/Date;->getTime()J

    move-result-wide v5

    invoke-virtual/range {v26 .. v26}, Ljava/util/Date;->getTime()J

    move-result-wide v7

    sub-long/2addr v5, v7

    const-wide/32 v7, 0x5265c00

    div-long/2addr v5, v7

    long-to-int v3, v5

    add-int/lit8 v21, v3, 0x1

    .line 106
    .local v21, barsCount:I
    const-string v3, "CHART"

    new-instance v5, Ljava/lang/StringBuilder;

    const-string v6, "Total Bars: "

    invoke-direct {v5, v6}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    move/from16 v0, v21

    invoke-virtual {v5, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v3, v5}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 108
    if-nez v21, :cond_1

    .line 110
    const-string v3, "No data to plot"

    const/4 v5, 0x0

    move-object/from16 v0, p0

    invoke-static {v0, v3, v5}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v3

    invoke-virtual {v3}, Landroid/widget/Toast;->show()V

    goto/16 :goto_0

    .line 84
    .end local v21           #barsCount:I
    .end local v22           #calendar:Ljava/util/Calendar;
    .end local v25           #dataEndDate:Ljava/util/Date;
    .end local v26           #dataStartDate:Ljava/util/Date;
    .end local v39           #total_30day:J
    .end local v41           #total_lifetime:J
    .end local v43           #total_today:J
    :catch_0
    move-exception v30

    .line 85
    .local v30, e:Ljava/text/ParseException;
    invoke-virtual/range {v30 .. v30}, Ljava/text/ParseException;->printStackTrace()V

    goto/16 :goto_1

    .line 115
    .end local v30           #e:Ljava/text/ParseException;
    .restart local v21       #barsCount:I
    .restart local v22       #calendar:Ljava/util/Calendar;
    .restart local v25       #dataEndDate:Ljava/util/Date;
    .restart local v26       #dataStartDate:Ljava/util/Date;
    .restart local v39       #total_30day:J
    .restart local v41       #total_lifetime:J
    .restart local v43       #total_today:J
    :cond_1
    const-wide/16 v32, 0x0

    .line 117
    .local v32, maxUtil:J
    const/4 v3, 0x2

    new-array v0, v3, [Ljava/lang/String;

    move-object/from16 v35, v0

    const/4 v3, 0x0

    const-string v5, "CAR TX"

    aput-object v5, v35, v3

    const/4 v3, 0x1

    const-string v5, "CAR RX"

    aput-object v5, v35, v3

    .line 118
    .local v35, titles:[Ljava/lang/String;
    new-instance v47, Ljava/util/ArrayList;

    invoke-direct/range {v47 .. v47}, Ljava/util/ArrayList;-><init>()V

    .line 119
    .local v47, values:Ljava/util/List;,"Ljava/util/List<[D>;"
    move/from16 v0, v21

    new-array v3, v0, [D

    move-object/from16 v0, v47

    invoke-interface {v0, v3}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 120
    move/from16 v0, v21

    new-array v3, v0, [D

    move-object/from16 v0, v47

    invoke-interface {v0, v3}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 122
    add-int/lit8 v20, v21, -0x1

    .line 125
    .local v20, barIndex:I
    const-string v3, "CHART"

    new-instance v5, Ljava/lang/StringBuilder;

    const-string v6, "today: "

    invoke-direct {v5, v6}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual/range {v36 .. v36}, Ljava/util/Date;->toGMTString()Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, " data start: "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual/range {v25 .. v25}, Ljava/util/Date;->toGMTString()Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v3, v5}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 126
    move-object/from16 v0, v25

    move-object/from16 v1, v36

    invoke-virtual {v0, v1}, Ljava/util/Date;->before(Ljava/util/Date;)Z

    move-result v3

    if-eqz v3, :cond_2

    .line 128
    invoke-virtual/range {v25 .. v25}, Ljava/util/Date;->getTime()J

    move-result-wide v5

    invoke-virtual/range {v36 .. v36}, Ljava/util/Date;->getTime()J

    move-result-wide v7

    sub-long/2addr v5, v7

    const-wide/32 v7, 0x5265c00

    div-long/2addr v5, v7

    long-to-int v0, v5

    move/from16 v29, v0

    .line 129
    .local v29, daysDiff:I
    const-string v3, "CHART"

    new-instance v5, Ljava/lang/StringBuilder;

    const-string v6, "initial skip: "

    invoke-direct {v5, v6}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    move/from16 v0, v29

    invoke-virtual {v5, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v3, v5}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 130
    add-int v20, v20, v29

    .line 135
    .end local v29           #daysDiff:I
    :cond_2
    const/16 v24, 0x0

    .local v24, count:I
    :goto_2
    move-object/from16 v0, p0

    iget-object v3, v0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v3, v3, Lcom/openvehicles/OVMS/CarData;->Data_GPRSUtilization:Lcom/openvehicles/OVMS/GPRSUtilization;

    iget-object v3, v3, Lcom/openvehicles/OVMS/GPRSUtilization;->Utilizations:Ljava/util/ArrayList;

    invoke-virtual {v3}, Ljava/util/ArrayList;->size()I

    move-result v3

    move/from16 v0, v24

    if-lt v0, v3, :cond_4

    .line 168
    const/4 v3, 0x2

    new-array v0, v3, [I

    move-object/from16 v23, v0

    fill-array-data v23, :array_0

    .line 169
    .local v23, colors:[I
    move-object/from16 v0, p0

    move-object/from16 v1, v23

    invoke-virtual {v0, v1}, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->buildBarRenderer([I)Lorg/achartengine/renderer/XYMultipleSeriesRenderer;

    move-result-object v4

    .line 172
    .local v4, renderer:Lorg/achartengine/renderer/XYMultipleSeriesRenderer;
    invoke-static {}, Ljava/util/Calendar;->getInstance()Ljava/util/Calendar;

    move-result-object v22

    .line 174
    const/4 v3, 0x5

    const/4 v5, -0x1

    move-object/from16 v0, v22

    invoke-virtual {v0, v3, v5}, Ljava/util/Calendar;->add(II)V

    .line 175
    add-int/lit8 v20, v21, -0x1

    :goto_3
    if-gtz v20, :cond_9

    .line 187
    const-string v5, "GPRS Data Utilization"

    const-string v6, "Date"

    const-string v7, "KB"

    const-wide/16 v8, 0x0

    .line 188
    move/from16 v0, v21

    int-to-double v10, v0

    const-wide/16 v12, 0x0

    move-wide/from16 v0, v32

    long-to-double v14, v0

    const-wide v16, 0x3ff199999999999aL

    mul-double v14, v14, v16

    const v16, -0x777778

    const/16 v17, -0x1

    move-object/from16 v3, p0

    .line 187
    invoke-virtual/range {v3 .. v17}, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->setChartSettings(Lorg/achartengine/renderer/XYMultipleSeriesRenderer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;DDDDII)V

    .line 189
    const/4 v3, 0x1

    invoke-virtual {v4, v3}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->getSeriesRendererAt(I)Lorg/achartengine/renderer/SimpleSeriesRenderer;

    move-result-object v3

    const/4 v5, 0x0

    invoke-virtual {v3, v5}, Lorg/achartengine/renderer/SimpleSeriesRenderer;->setDisplayChartValues(Z)V

    .line 190
    const/4 v3, 0x1

    invoke-virtual {v4, v3}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->getSeriesRendererAt(I)Lorg/achartengine/renderer/SimpleSeriesRenderer;

    move-result-object v3

    const/4 v5, 0x1

    invoke-virtual {v3, v5}, Lorg/achartengine/renderer/SimpleSeriesRenderer;->setGradientEnabled(Z)V

    .line 191
    const/4 v3, 0x1

    invoke-virtual {v4, v3}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->getSeriesRendererAt(I)Lorg/achartengine/renderer/SimpleSeriesRenderer;

    move-result-object v3

    const-wide/16 v5, 0x0

    const/4 v7, -0x1

    invoke-virtual {v3, v5, v6, v7}, Lorg/achartengine/renderer/SimpleSeriesRenderer;->setGradientStart(DI)V

    .line 192
    const/4 v3, 0x1

    invoke-virtual {v4, v3}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->getSeriesRendererAt(I)Lorg/achartengine/renderer/SimpleSeriesRenderer;

    move-result-object v3

    const-wide/high16 v5, 0x4014

    const v7, -0xff0001

    invoke-virtual {v3, v5, v6, v7}, Lorg/achartengine/renderer/SimpleSeriesRenderer;->setGradientStop(DI)V

    .line 193
    const/4 v3, 0x1

    invoke-virtual {v4, v3}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->getSeriesRendererAt(I)Lorg/achartengine/renderer/SimpleSeriesRenderer;

    move-result-object v3

    const/high16 v5, 0x4150

    invoke-virtual {v3, v5}, Lorg/achartengine/renderer/SimpleSeriesRenderer;->setChartValuesTextSize(F)V

    .line 194
    const/4 v3, 0x0

    invoke-virtual {v4, v3}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->getSeriesRendererAt(I)Lorg/achartengine/renderer/SimpleSeriesRenderer;

    move-result-object v3

    const/4 v5, 0x1

    invoke-virtual {v3, v5}, Lorg/achartengine/renderer/SimpleSeriesRenderer;->setDisplayChartValues(Z)V

    .line 195
    const/4 v3, 0x0

    invoke-virtual {v4, v3}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->getSeriesRendererAt(I)Lorg/achartengine/renderer/SimpleSeriesRenderer;

    move-result-object v3

    const/4 v5, 0x1

    invoke-virtual {v3, v5}, Lorg/achartengine/renderer/SimpleSeriesRenderer;->setGradientEnabled(Z)V

    .line 196
    const/4 v3, 0x0

    invoke-virtual {v4, v3}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->getSeriesRendererAt(I)Lorg/achartengine/renderer/SimpleSeriesRenderer;

    move-result-object v3

    const-wide/16 v5, 0x0

    const/4 v7, -0x1

    invoke-virtual {v3, v5, v6, v7}, Lorg/achartengine/renderer/SimpleSeriesRenderer;->setGradientStart(DI)V

    .line 197
    const/4 v3, 0x0

    invoke-virtual {v4, v3}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->getSeriesRendererAt(I)Lorg/achartengine/renderer/SimpleSeriesRenderer;

    move-result-object v3

    const-wide/high16 v5, 0x4024

    const/16 v7, -0x100

    invoke-virtual {v3, v5, v6, v7}, Lorg/achartengine/renderer/SimpleSeriesRenderer;->setGradientStop(DI)V

    .line 198
    const/4 v3, 0x0

    invoke-virtual {v4, v3}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->getSeriesRendererAt(I)Lorg/achartengine/renderer/SimpleSeriesRenderer;

    move-result-object v3

    const/high16 v5, 0x4150

    invoke-virtual {v3, v5}, Lorg/achartengine/renderer/SimpleSeriesRenderer;->setChartValuesTextSize(F)V

    .line 199
    const/4 v3, 0x0

    invoke-virtual {v4, v3}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->setXLabels(I)V

    .line 200
    const/16 v3, 0xa

    invoke-virtual {v4, v3}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->setYLabels(I)V

    .line 201
    sget-object v3, Landroid/graphics/Paint$Align;->LEFT:Landroid/graphics/Paint$Align;

    invoke-virtual {v4, v3}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->setXLabelsAlign(Landroid/graphics/Paint$Align;)V

    .line 202
    sget-object v3, Landroid/graphics/Paint$Align;->LEFT:Landroid/graphics/Paint$Align;

    invoke-virtual {v4, v3}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->setYLabelsAlign(Landroid/graphics/Paint$Align;)V

    .line 203
    const/4 v3, 0x1

    const/4 v5, 0x0

    invoke-virtual {v4, v3, v5}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->setPanEnabled(ZZ)V

    .line 205
    const/4 v3, 0x0

    const/4 v5, 0x0

    invoke-virtual {v4, v3, v5}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->setZoomEnabled(ZZ)V

    .line 206
    const-wide/high16 v5, 0x3ff0

    invoke-virtual {v4, v5, v6}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->setBarSpacing(D)V

    .line 208
    const v3, 0x7f090015

    move-object/from16 v0, p0

    invoke-virtual {v0, v3}, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->findViewById(I)Landroid/view/View;

    move-result-object v31

    check-cast v31, Landroid/widget/LinearLayout;

    .line 209
    .local v31, layout:Landroid/widget/LinearLayout;
    move-object/from16 v0, p0

    iget-object v3, v0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->chart:Landroid/view/View;

    if-eqz v3, :cond_3

    .line 210
    move-object/from16 v0, p0

    iget-object v3, v0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->chart:Landroid/view/View;

    move-object/from16 v0, v31

    invoke-virtual {v0, v3}, Landroid/widget/LinearLayout;->removeView(Landroid/view/View;)V

    .line 212
    :cond_3
    move-object/from16 v0, p0

    move-object/from16 v1, v35

    move-object/from16 v2, v47

    invoke-virtual {v0, v1, v2}, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->buildBarDataset([Ljava/lang/String;Ljava/util/List;)Lorg/achartengine/model/XYMultipleSeriesDataset;

    move-result-object v3

    sget-object v5, Lorg/achartengine/chart/BarChart$Type;->STACKED:Lorg/achartengine/chart/BarChart$Type;

    .line 211
    move-object/from16 v0, p0

    invoke-static {v0, v3, v4, v5}, Lorg/achartengine/ChartFactory;->getBarChartView(Landroid/content/Context;Lorg/achartengine/model/XYMultipleSeriesDataset;Lorg/achartengine/renderer/XYMultipleSeriesRenderer;Lorg/achartengine/chart/BarChart$Type;)Lorg/achartengine/GraphicalView;

    move-result-object v3

    move-object/from16 v0, p0

    iput-object v3, v0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->chart:Landroid/view/View;

    .line 213
    move-object/from16 v0, p0

    iget-object v3, v0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->chart:Landroid/view/View;

    move-object/from16 v0, v31

    invoke-virtual {v0, v3}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;)V

    .line 215
    const v3, 0x7f090014

    move-object/from16 v0, p0

    invoke-virtual {v0, v3}, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->findViewById(I)Landroid/view/View;

    move-result-object v45

    check-cast v45, Landroid/widget/TextView;

    .line 216
    .local v45, tv:Landroid/widget/TextView;
    move-wide/from16 v0, v43

    long-to-double v5, v0

    const-wide/high16 v7, 0x4090

    div-double/2addr v5, v7

    invoke-static {v5, v6}, Ljava/lang/Math;->ceil(D)D

    move-result-wide v5

    double-to-long v0, v5

    move-wide/from16 v43, v0

    .line 217
    const-string v3, "%s KB"

    const/4 v5, 0x1

    new-array v5, v5, [Ljava/lang/Object;

    const/4 v6, 0x0

    invoke-static/range {v43 .. v44}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v7

    aput-object v7, v5, v6

    invoke-static {v3, v5}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v3

    move-object/from16 v0, v45

    invoke-virtual {v0, v3}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 219
    const v3, 0x7f090013

    move-object/from16 v0, p0

    invoke-virtual {v0, v3}, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->findViewById(I)Landroid/view/View;

    move-result-object v45

    .end local v45           #tv:Landroid/widget/TextView;
    check-cast v45, Landroid/widget/TextView;

    .line 220
    .restart local v45       #tv:Landroid/widget/TextView;
    const-wide/32 v5, 0x100000

    cmp-long v3, v39, v5

    if-lez v3, :cond_b

    .line 221
    const-string v3, "%s MB"

    const/4 v5, 0x1

    new-array v5, v5, [Ljava/lang/Object;

    const/4 v6, 0x0

    move-wide/from16 v0, v39

    long-to-double v7, v0

    const-wide/high16 v9, 0x4090

    div-double/2addr v7, v9

    const-wide/high16 v9, 0x4090

    div-double/2addr v7, v9

    invoke-static {v7, v8}, Ljava/lang/Math;->ceil(D)D

    move-result-wide v7

    double-to-long v7, v7

    invoke-static {v7, v8}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v7

    aput-object v7, v5, v6

    invoke-static {v3, v5}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v3

    move-object/from16 v0, v45

    invoke-virtual {v0, v3}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 225
    :goto_4
    const v3, 0x7f090012

    move-object/from16 v0, p0

    invoke-virtual {v0, v3}, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->findViewById(I)Landroid/view/View;

    move-result-object v45

    .end local v45           #tv:Landroid/widget/TextView;
    check-cast v45, Landroid/widget/TextView;

    .line 226
    .restart local v45       #tv:Landroid/widget/TextView;
    const-wide/32 v5, 0x100000

    cmp-long v3, v41, v5

    if-lez v3, :cond_c

    .line 227
    const-string v3, "%s MB"

    const/4 v5, 0x1

    new-array v5, v5, [Ljava/lang/Object;

    const/4 v6, 0x0

    move-wide/from16 v0, v41

    long-to-double v7, v0

    const-wide/high16 v9, 0x4090

    div-double/2addr v7, v9

    const-wide/high16 v9, 0x4090

    div-double/2addr v7, v9

    invoke-static {v7, v8}, Ljava/lang/Math;->ceil(D)D

    move-result-wide v7

    double-to-long v7, v7

    invoke-static {v7, v8}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v7

    aput-object v7, v5, v6

    invoke-static {v3, v5}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v3

    move-object/from16 v0, v45

    invoke-virtual {v0, v3}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_0

    .line 136
    .end local v4           #renderer:Lorg/achartengine/renderer/XYMultipleSeriesRenderer;
    .end local v23           #colors:[I
    .end local v31           #layout:Landroid/widget/LinearLayout;
    .end local v45           #tv:Landroid/widget/TextView;
    :cond_4
    move-object/from16 v0, p0

    iget-object v3, v0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v3, v3, Lcom/openvehicles/OVMS/CarData;->Data_GPRSUtilization:Lcom/openvehicles/OVMS/GPRSUtilization;

    iget-object v3, v3, Lcom/openvehicles/OVMS/GPRSUtilization;->Utilizations:Ljava/util/ArrayList;

    .line 137
    move/from16 v0, v24

    invoke-virtual {v3, v0}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v46

    check-cast v46, Lcom/openvehicles/OVMS/GPRSUtilizationData;

    .line 139
    .local v46, util:Lcom/openvehicles/OVMS/GPRSUtilizationData;
    if-gez v20, :cond_5

    .line 141
    const-string v3, "CHART"

    const-string v5, "Ignoring a rendering error"

    invoke-static {v3, v5}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 142
    const/16 v20, 0x0

    .line 145
    :cond_5
    const/4 v3, 0x0

    move-object/from16 v0, v47

    invoke-interface {v0, v3}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, [D

    move-object/from16 v0, v46

    iget-wide v5, v0, Lcom/openvehicles/OVMS/GPRSUtilizationData;->Car_tx:J

    move-object/from16 v0, v46

    iget-wide v7, v0, Lcom/openvehicles/OVMS/GPRSUtilizationData;->Car_rx:J

    add-long/2addr v5, v7

    long-to-double v5, v5

    const-wide/high16 v7, 0x4090

    div-double/2addr v5, v7

    invoke-static {v5, v6}, Ljava/lang/Math;->ceil(D)D

    move-result-wide v5

    aput-wide v5, v3, v20

    .line 146
    const/4 v3, 0x1

    move-object/from16 v0, v47

    invoke-interface {v0, v3}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, [D

    move-object/from16 v0, v46

    iget-wide v5, v0, Lcom/openvehicles/OVMS/GPRSUtilizationData;->Car_rx:J

    long-to-double v5, v5

    const-wide/high16 v7, 0x4090

    div-double/2addr v5, v7

    invoke-static {v5, v6}, Ljava/lang/Math;->ceil(D)D

    move-result-wide v5

    aput-wide v5, v3, v20

    .line 149
    move-object/from16 v0, v46

    iget-wide v5, v0, Lcom/openvehicles/OVMS/GPRSUtilizationData;->Car_rx:J

    move-object/from16 v0, v46

    iget-wide v7, v0, Lcom/openvehicles/OVMS/GPRSUtilizationData;->Car_tx:J

    add-long/2addr v5, v7

    long-to-double v5, v5

    const-wide/high16 v7, 0x4090

    div-double/2addr v5, v7

    invoke-static {v5, v6}, Ljava/lang/Math;->ceil(D)D

    move-result-wide v5

    double-to-long v0, v5

    move-wide/from16 v37, v0

    .line 151
    .local v37, totalUtil:J
    cmp-long v3, v37, v32

    if-lez v3, :cond_6

    .line 152
    move-wide/from16 v32, v37

    .line 155
    :cond_6
    move-object/from16 v0, p0

    iget-object v3, v0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v3, v3, Lcom/openvehicles/OVMS/CarData;->Data_GPRSUtilization:Lcom/openvehicles/OVMS/GPRSUtilization;

    iget-object v3, v3, Lcom/openvehicles/OVMS/GPRSUtilization;->Utilizations:Ljava/util/ArrayList;

    invoke-virtual {v3}, Ljava/util/ArrayList;->size()I

    move-result v3

    add-int/lit8 v3, v3, -0x1

    move/from16 v0, v24

    if-ge v0, v3, :cond_8

    .line 156
    move-object/from16 v0, p0

    iget-object v3, v0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v3, v3, Lcom/openvehicles/OVMS/CarData;->Data_GPRSUtilization:Lcom/openvehicles/OVMS/GPRSUtilization;

    iget-object v3, v3, Lcom/openvehicles/OVMS/GPRSUtilization;->Utilizations:Ljava/util/ArrayList;

    .line 157
    add-int/lit8 v5, v24, 0x1

    invoke-virtual {v3, v5}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/openvehicles/OVMS/GPRSUtilizationData;

    iget-object v0, v3, Lcom/openvehicles/OVMS/GPRSUtilizationData;->DataDate:Ljava/util/Date;

    move-object/from16 v34, v0

    .line 159
    .local v34, nextDate:Ljava/util/Date;
    invoke-virtual/range {v34 .. v34}, Ljava/util/Date;->getTime()J

    move-result-wide v5

    move-object/from16 v0, v46

    iget-object v3, v0, Lcom/openvehicles/OVMS/GPRSUtilizationData;->DataDate:Ljava/util/Date;

    invoke-virtual {v3}, Ljava/util/Date;->getTime()J

    move-result-wide v7

    sub-long/2addr v5, v7

    const-wide/32 v7, 0x5265c00

    div-long/2addr v5, v7

    long-to-int v0, v5

    move/from16 v29, v0

    .line 160
    .restart local v29       #daysDiff:I
    const/4 v3, -0x1

    move/from16 v0, v29

    if-ge v0, v3, :cond_7

    .line 161
    const-string v3, "CHART"

    new-instance v5, Ljava/lang/StringBuilder;

    const-string v6, "curr: "

    invoke-direct {v5, v6}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    move-object/from16 v0, v46

    iget-object v6, v0, Lcom/openvehicles/OVMS/GPRSUtilizationData;->DataDate:Ljava/util/Date;

    invoke-virtual {v6}, Ljava/util/Date;->toGMTString()Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, " next: "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual/range {v34 .. v34}, Ljava/util/Date;->toGMTString()Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, " skip: "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    move/from16 v0, v29

    invoke-virtual {v5, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v3, v5}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 163
    :cond_7
    add-int v20, v20, v29

    .line 135
    .end local v29           #daysDiff:I
    .end local v34           #nextDate:Ljava/util/Date;
    :cond_8
    add-int/lit8 v24, v24, 0x1

    goto/16 :goto_2

    .line 178
    .end local v37           #totalUtil:J
    .end local v46           #util:Lcom/openvehicles/OVMS/GPRSUtilizationData;
    .restart local v4       #renderer:Lorg/achartengine/renderer/XYMultipleSeriesRenderer;
    .restart local v23       #colors:[I
    :cond_9
    rem-int/lit8 v3, v20, 0x7

    add-int/lit8 v5, v21, -0x1

    rem-int/lit8 v5, v5, 0x7

    if-ne v3, v5, :cond_a

    .line 179
    move/from16 v0, v20

    int-to-double v5, v0

    .line 180
    invoke-virtual/range {v22 .. v22}, Ljava/util/Calendar;->getTime()Ljava/util/Date;

    move-result-object v3

    move-object/from16 v0, v27

    invoke-virtual {v0, v3}, Ljava/text/SimpleDateFormat;->format(Ljava/util/Date;)Ljava/lang/String;

    move-result-object v3

    .line 179
    invoke-virtual {v4, v5, v6, v3}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->addXTextLabel(DLjava/lang/String;)V

    .line 184
    :cond_a
    const/4 v3, 0x5

    const/4 v5, -0x1

    move-object/from16 v0, v22

    invoke-virtual {v0, v3, v5}, Ljava/util/Calendar;->add(II)V

    .line 175
    add-int/lit8 v20, v20, -0x1

    goto/16 :goto_3

    .line 223
    .restart local v31       #layout:Landroid/widget/LinearLayout;
    .restart local v45       #tv:Landroid/widget/TextView;
    :cond_b
    const-string v3, "%s KB"

    const/4 v5, 0x1

    new-array v5, v5, [Ljava/lang/Object;

    const/4 v6, 0x0

    move-wide/from16 v0, v39

    long-to-double v7, v0

    const-wide/high16 v9, 0x4090

    div-double/2addr v7, v9

    invoke-static {v7, v8}, Ljava/lang/Math;->ceil(D)D

    move-result-wide v7

    double-to-long v7, v7

    invoke-static {v7, v8}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v7

    aput-object v7, v5, v6

    invoke-static {v3, v5}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v3

    move-object/from16 v0, v45

    invoke-virtual {v0, v3}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_4

    .line 229
    :cond_c
    const-string v3, "%s KB"

    const/4 v5, 0x1

    new-array v5, v5, [Ljava/lang/Object;

    const/4 v6, 0x0

    move-wide/from16 v0, v41

    long-to-double v7, v0

    const-wide/high16 v9, 0x4090

    div-double/2addr v7, v9

    invoke-static {v7, v8}, Ljava/lang/Math;->ceil(D)D

    move-result-wide v7

    double-to-long v7, v7

    invoke-static {v7, v8}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v7

    aput-object v7, v5, v6

    invoke-static {v3, v5}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v3

    move-object/from16 v0, v45

    invoke-virtual {v0, v3}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto/16 :goto_0

    .line 168
    :array_0
    .array-data 0x4
        0x0t 0xfft 0xfft 0xfft
        0xfft 0xfft 0x0t 0xfft
    .end array-data
.end method

.method private requestData()V
    .locals 2

    .prologue
    .line 350
    const-string v0, "Requesting Data..."

    const/4 v1, 0x1

    invoke-static {p0, v0, v1}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v0

    invoke-virtual {v0}, Landroid/widget/Toast;->show()V

    .line 351
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->Data_GPRSUtilization:Lcom/openvehicles/OVMS/GPRSUtilization;

    invoke-virtual {v0}, Lcom/openvehicles/OVMS/GPRSUtilization;->Clear()V

    .line 352
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->mOVMSActivity:Lcom/openvehicles/OVMS/OVMSActivity;

    const-string v1, "C30"

    invoke-virtual {v0, v1}, Lcom/openvehicles/OVMS/OVMSActivity;->SendServerCommand(Ljava/lang/String;)Z

    .line 354
    return-void
.end method


# virtual methods
.method public Refresh(Lcom/openvehicles/OVMS/CarData;Z)V
    .locals 2
    .parameter "carData"
    .parameter "isLoggedIn"

    .prologue
    .line 335
    iput-object p1, p0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->data:Lcom/openvehicles/OVMS/CarData;

    .line 339
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->Data_GPRSUtilization:Lcom/openvehicles/OVMS/GPRSUtilization;

    if-eqz v0, :cond_0

    .line 340
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->lastVehicleID:Ljava/lang/String;

    iget-object v1, p0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v1, v1, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    .line 341
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->Data_GPRSUtilization:Lcom/openvehicles/OVMS/GPRSUtilization;

    iget-object v0, v0, Lcom/openvehicles/OVMS/GPRSUtilization;->LastDataRefresh:Ljava/util/Date;

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->Data_GPRSUtilization:Lcom/openvehicles/OVMS/GPRSUtilization;

    iget-object v0, v0, Lcom/openvehicles/OVMS/GPRSUtilization;->LastDataRefresh:Ljava/util/Date;

    .line 342
    iget-object v1, p0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->lastRefresh:Ljava/util/Date;

    invoke-virtual {v0, v1}, Ljava/util/Date;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_1

    .line 343
    :cond_0
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->VehicleID:Ljava/lang/String;

    iput-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->lastVehicleID:Ljava/lang/String;

    .line 344
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->data:Lcom/openvehicles/OVMS/CarData;

    iget-object v0, v0, Lcom/openvehicles/OVMS/CarData;->Data_GPRSUtilization:Lcom/openvehicles/OVMS/GPRSUtilization;

    iget-object v0, v0, Lcom/openvehicles/OVMS/GPRSUtilization;->LastDataRefresh:Ljava/util/Date;

    iput-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->lastRefresh:Ljava/util/Date;

    .line 345
    iget-object v0, p0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->handler:Landroid/os/Handler;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/os/Handler;->sendEmptyMessage(I)Z

    .line 347
    :cond_1
    return-void
.end method

.method protected buildBarDataset([Ljava/lang/String;Ljava/util/List;)Lorg/achartengine/model/XYMultipleSeriesDataset;
    .locals 9
    .parameter "titles"
    .parameter
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "([",
            "Ljava/lang/String;",
            "Ljava/util/List",
            "<[D>;)",
            "Lorg/achartengine/model/XYMultipleSeriesDataset;"
        }
    .end annotation

    .prologue
    .line 243
    .local p2, values:Ljava/util/List;,"Ljava/util/List<[D>;"
    new-instance v0, Lorg/achartengine/model/XYMultipleSeriesDataset;

    invoke-direct {v0}, Lorg/achartengine/model/XYMultipleSeriesDataset;-><init>()V

    .line 244
    .local v0, dataset:Lorg/achartengine/model/XYMultipleSeriesDataset;
    array-length v3, p1

    .line 245
    .local v3, length:I
    const/4 v1, 0x0

    .local v1, i:I
    :goto_0
    if-lt v1, v3, :cond_0

    .line 254
    return-object v0

    .line 246
    :cond_0
    new-instance v4, Lorg/achartengine/model/CategorySeries;

    aget-object v7, p1, v1

    invoke-direct {v4, v7}, Lorg/achartengine/model/CategorySeries;-><init>(Ljava/lang/String;)V

    .line 247
    .local v4, series:Lorg/achartengine/model/CategorySeries;
    invoke-interface {p2, v1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v6

    check-cast v6, [D

    .line 248
    .local v6, v:[D
    array-length v5, v6

    .line 249
    .local v5, seriesLength:I
    const/4 v2, 0x0

    .local v2, k:I
    :goto_1
    if-lt v2, v5, :cond_1

    .line 252
    invoke-virtual {v4}, Lorg/achartengine/model/CategorySeries;->toXYSeries()Lorg/achartengine/model/XYSeries;

    move-result-object v7

    invoke-virtual {v0, v7}, Lorg/achartengine/model/XYMultipleSeriesDataset;->addSeries(Lorg/achartengine/model/XYSeries;)V

    .line 245
    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    .line 250
    :cond_1
    aget-wide v7, v6, v2

    invoke-virtual {v4, v7, v8}, Lorg/achartengine/model/CategorySeries;->add(D)V

    .line 249
    add-int/lit8 v2, v2, 0x1

    goto :goto_1
.end method

.method protected buildBarRenderer([I)Lorg/achartengine/renderer/XYMultipleSeriesRenderer;
    .locals 6
    .parameter "colors"

    .prologue
    const/high16 v5, 0x4170

    .line 304
    new-instance v3, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;

    invoke-direct {v3}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;-><init>()V

    .line 305
    .local v3, renderer:Lorg/achartengine/renderer/XYMultipleSeriesRenderer;
    const/high16 v4, 0x4180

    invoke-virtual {v3, v4}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->setAxisTitleTextSize(F)V

    .line 306
    const/high16 v4, 0x41a0

    invoke-virtual {v3, v4}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->setChartTitleTextSize(F)V

    .line 307
    invoke-virtual {v3, v5}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->setLabelsTextSize(F)V

    .line 308
    invoke-virtual {v3, v5}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->setLegendTextSize(F)V

    .line 309
    array-length v1, p1

    .line 310
    .local v1, length:I
    const/4 v0, 0x0

    .local v0, i:I
    :goto_0
    if-lt v0, v1, :cond_0

    .line 315
    return-object v3

    .line 311
    :cond_0
    new-instance v2, Lorg/achartengine/renderer/SimpleSeriesRenderer;

    invoke-direct {v2}, Lorg/achartengine/renderer/SimpleSeriesRenderer;-><init>()V

    .line 312
    .local v2, r:Lorg/achartengine/renderer/SimpleSeriesRenderer;
    aget v4, p1, v0

    invoke-virtual {v2, v4}, Lorg/achartengine/renderer/SimpleSeriesRenderer;->setColor(I)V

    .line 313
    invoke-virtual {v3, v2}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->addSeriesRenderer(Lorg/achartengine/renderer/SimpleSeriesRenderer;)V

    .line 310
    add-int/lit8 v0, v0, 0x1

    goto :goto_0
.end method

.method public onCreate(Landroid/os/Bundle;)V
    .locals 3
    .parameter "savedInstanceState"

    .prologue
    .line 42
    invoke-super {p0, p1}, Landroid/app/Activity;->onCreate(Landroid/os/Bundle;)V

    .line 43
    const v1, 0x7f030007

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->setContentView(I)V

    .line 45
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->getParent()Landroid/app/Activity;

    move-result-object v1

    invoke-virtual {v1}, Landroid/app/Activity;->getParent()Landroid/app/Activity;

    move-result-object v1

    check-cast v1, Lcom/openvehicles/OVMS/OVMSActivity;

    iput-object v1, p0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->mOVMSActivity:Lcom/openvehicles/OVMS/OVMSActivity;

    .line 46
    iget-object v1, p0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->mOVMSActivity:Lcom/openvehicles/OVMS/OVMSActivity;

    if-nez v1, :cond_0

    .line 47
    invoke-virtual {p0}, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->getParent()Landroid/app/Activity;

    move-result-object v1

    check-cast v1, Lcom/openvehicles/OVMS/OVMSActivity;

    iput-object v1, p0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->mOVMSActivity:Lcom/openvehicles/OVMS/OVMSActivity;

    .line 48
    :cond_0
    iget-object v1, p0, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->mOVMSActivity:Lcom/openvehicles/OVMS/OVMSActivity;

    if-nez v1, :cond_1

    .line 50
    const-string v1, "Unexpected Layout Error - controls on this page may not work"

    const/4 v2, 0x1

    invoke-static {p0, v1, v2}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v1

    invoke-virtual {v1}, Landroid/widget/Toast;->show()V

    .line 60
    :goto_0
    return-void

    .line 54
    :cond_1
    const v1, 0x7f090011

    invoke-virtual {p0, v1}, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageButton;

    .line 55
    .local v0, btn:Landroid/widget/ImageButton;
    new-instance v1, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations$2;

    invoke-direct {v1, p0}, Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations$2;-><init>(Lcom/openvehicles/OVMS/Tab_SubTabDataUtilizations;)V

    invoke-virtual {v0, v1}, Landroid/widget/ImageButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    goto :goto_0
.end method

.method protected setChartSettings(Lorg/achartengine/renderer/XYMultipleSeriesRenderer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;DDDDII)V
    .locals 0
    .parameter "renderer"
    .parameter "title"
    .parameter "xTitle"
    .parameter "yTitle"
    .parameter "xMin"
    .parameter "xMax"
    .parameter "yMin"
    .parameter "yMax"
    .parameter "axesColor"
    .parameter "labelsColor"

    .prologue
    .line 285
    invoke-virtual {p1, p2}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->setChartTitle(Ljava/lang/String;)V

    .line 286
    invoke-virtual {p1, p3}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->setXTitle(Ljava/lang/String;)V

    .line 287
    invoke-virtual {p1, p4}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->setYTitle(Ljava/lang/String;)V

    .line 288
    invoke-virtual {p1, p5, p6}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->setXAxisMin(D)V

    .line 289
    invoke-virtual {p1, p7, p8}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->setXAxisMax(D)V

    .line 290
    invoke-virtual {p1, p9, p10}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->setYAxisMin(D)V

    .line 291
    invoke-virtual {p1, p11, p12}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->setYAxisMax(D)V

    .line 292
    invoke-virtual {p1, p13}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->setAxesColor(I)V

    .line 293
    invoke-virtual {p1, p14}, Lorg/achartengine/renderer/XYMultipleSeriesRenderer;->setLabelsColor(I)V

    .line 294
    return-void
.end method
