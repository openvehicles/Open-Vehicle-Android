.class public Lcom/openvehicles/OVMS/ServerCommands$CarLayoutDownloader;
.super Landroid/os/AsyncTask;
.source "ServerCommands.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/openvehicles/OVMS/ServerCommands;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x9
    name = "CarLayoutDownloader"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Landroid/os/AsyncTask",
        "<",
        "Ljava/lang/String;",
        "Ljava/lang/Integer;",
        "Ljava/lang/Boolean;",
        ">;"
    }
.end annotation


# instance fields
.field private mProgressDialog:Landroid/app/ProgressDialog;


# direct methods
.method public constructor <init>(Landroid/app/ProgressDialog;)V
    .locals 0
    .parameter "progressDialog"

    .prologue
    .line 100
    invoke-direct {p0}, Landroid/os/AsyncTask;-><init>()V

    .line 101
    iput-object p1, p0, Lcom/openvehicles/OVMS/ServerCommands$CarLayoutDownloader;->mProgressDialog:Landroid/app/ProgressDialog;

    .line 102
    return-void
.end method


# virtual methods
.method protected varargs doInBackground([Ljava/lang/String;)Ljava/lang/Boolean;
    .locals 21
    .parameter "params"

    .prologue
    .line 107
    move-object/from16 v0, p1

    array-length v15, v0

    const/16 v16, 0x2

    move/from16 v0, v16

    if-ge v15, v0, :cond_0

    .line 108
    const-string v15, "TCP"

    .line 109
    const-string v16, "!!! Car Layout Download Error: params incorrect !!!"

    .line 108
    invoke-static/range {v15 .. v16}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 110
    const/4 v15, 0x0

    invoke-static {v15}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v15

    .line 187
    :goto_0
    return-object v15

    .line 114
    :cond_0
    :try_start_0
    new-instance v13, Ljava/net/URL;

    const-string v15, "%s/ol_%s.png"

    const/16 v16, 0x2

    move/from16 v0, v16

    new-array v0, v0, [Ljava/lang/Object;

    move-object/from16 v16, v0

    const/16 v17, 0x0

    .line 115
    const-string v18, "http://www.openvehicles.com/resources"

    aput-object v18, v16, v17

    const/16 v17, 0x1

    const/16 v18, 0x0

    aget-object v18, p1, v18

    aput-object v18, v16, v17

    .line 114
    invoke-static/range {v15 .. v16}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v15

    invoke-direct {v13, v15}, Ljava/net/URL;-><init>(Ljava/lang/String;)V

    .line 116
    .local v13, url:Ljava/net/URL;
    invoke-virtual {v13}, Ljava/net/URL;->openConnection()Ljava/net/URLConnection;

    move-result-object v3

    .line 117
    .local v3, connection:Ljava/net/URLConnection;
    const/16 v15, 0x1388

    invoke-virtual {v3, v15}, Ljava/net/URLConnection;->setConnectTimeout(I)V

    .line 118
    const/16 v15, 0x1388

    invoke-virtual {v3, v15}, Ljava/net/URLConnection;->setReadTimeout(I)V

    .line 119
    invoke-virtual {v3}, Ljava/net/URLConnection;->connect()V

    .line 122
    invoke-virtual {v3}, Ljava/net/URLConnection;->getContentLength()I

    move-result v6

    .line 125
    .local v6, fileLength:I
    new-instance v7, Ljava/io/BufferedInputStream;

    invoke-virtual {v13}, Ljava/net/URL;->openStream()Ljava/io/InputStream;

    move-result-object v15

    invoke-direct {v7, v15}, Ljava/io/BufferedInputStream;-><init>(Ljava/io/InputStream;)V

    .line 126
    .local v7, input:Ljava/io/InputStream;
    new-instance v9, Ljava/io/FileOutputStream;

    .line 127
    const-string v15, "%s/ol_%s.png"

    const/16 v16, 0x2

    move/from16 v0, v16

    new-array v0, v0, [Ljava/lang/Object;

    move-object/from16 v16, v0

    const/16 v17, 0x0

    const/16 v18, 0x1

    aget-object v18, p1, v18

    aput-object v18, v16, v17

    const/16 v17, 0x1

    const/16 v18, 0x0

    aget-object v18, p1, v18

    aput-object v18, v16, v17

    .line 126
    invoke-static/range {v15 .. v16}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v15

    .line 127
    const/16 v16, 0x0

    .line 126
    move/from16 v0, v16

    invoke-direct {v9, v15, v0}, Ljava/io/FileOutputStream;-><init>(Ljava/lang/String;Z)V

    .line 129
    .local v9, output:Ljava/io/OutputStream;
    const/16 v15, 0x400

    new-array v4, v15, [B

    .line 131
    .local v4, data:[B
    const-wide/16 v11, 0x0

    .line 132
    .local v11, totalBytesReceived:J
    const/4 v2, 0x0

    .line 134
    .local v2, bytesCount:I
    :goto_1
    invoke-virtual {v7, v4}, Ljava/io/InputStream;->read([B)I

    move-result v2

    const/4 v15, -0x1

    if-ne v2, v15, :cond_1

    .line 141
    invoke-virtual {v9}, Ljava/io/OutputStream;->flush()V

    .line 142
    invoke-virtual {v9}, Ljava/io/OutputStream;->close()V

    .line 143
    invoke-virtual {v7}, Ljava/io/InputStream;->close()V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 151
    :try_start_1
    new-instance v14, Ljava/net/URL;

    const-string v15, "%s/%s.png"

    const/16 v16, 0x2

    move/from16 v0, v16

    new-array v0, v0, [Ljava/lang/Object;

    move-object/from16 v16, v0

    const/16 v17, 0x0

    .line 152
    const-string v18, "http://www.openvehicles.com/resources"

    aput-object v18, v16, v17

    const/16 v17, 0x1

    const/16 v18, 0x0

    aget-object v18, p1, v18

    aput-object v18, v16, v17

    .line 151
    invoke-static/range {v15 .. v16}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v15

    invoke-direct {v14, v15}, Ljava/net/URL;-><init>(Ljava/lang/String;)V
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_2

    .line 153
    .end local v13           #url:Ljava/net/URL;
    .local v14, url:Ljava/net/URL;
    :try_start_2
    invoke-virtual {v14}, Ljava/net/URL;->openConnection()Ljava/net/URLConnection;

    move-result-object v3

    .line 154
    const/16 v15, 0x1388

    invoke-virtual {v3, v15}, Ljava/net/URLConnection;->setConnectTimeout(I)V

    .line 155
    const/16 v15, 0x1388

    invoke-virtual {v3, v15}, Ljava/net/URLConnection;->setReadTimeout(I)V

    .line 156
    invoke-virtual {v3}, Ljava/net/URLConnection;->connect()V

    .line 159
    invoke-virtual {v3}, Ljava/net/URLConnection;->getContentLength()I

    move-result v6

    .line 162
    new-instance v8, Ljava/io/BufferedInputStream;

    invoke-virtual {v14}, Ljava/net/URL;->openStream()Ljava/io/InputStream;

    move-result-object v15

    invoke-direct {v8, v15}, Ljava/io/BufferedInputStream;-><init>(Ljava/io/InputStream;)V
    :try_end_2
    .catch Ljava/lang/Exception; {:try_start_2 .. :try_end_2} :catch_3

    .line 163
    .end local v7           #input:Ljava/io/InputStream;
    .local v8, input:Ljava/io/InputStream;
    :try_start_3
    new-instance v10, Ljava/io/FileOutputStream;

    .line 164
    const-string v15, "%s/%s.png"

    const/16 v16, 0x2

    move/from16 v0, v16

    new-array v0, v0, [Ljava/lang/Object;

    move-object/from16 v16, v0

    const/16 v17, 0x0

    const/16 v18, 0x1

    aget-object v18, p1, v18

    aput-object v18, v16, v17

    const/16 v17, 0x1

    const/16 v18, 0x0

    aget-object v18, p1, v18

    aput-object v18, v16, v17

    .line 163
    invoke-static/range {v15 .. v16}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v15

    .line 164
    const/16 v16, 0x0

    .line 163
    move/from16 v0, v16

    invoke-direct {v10, v15, v0}, Ljava/io/FileOutputStream;-><init>(Ljava/lang/String;Z)V
    :try_end_3
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_3} :catch_4

    .line 166
    .end local v9           #output:Ljava/io/OutputStream;
    .local v10, output:Ljava/io/OutputStream;
    const/16 v15, 0x400

    :try_start_4
    new-array v4, v15, [B

    .line 168
    const-wide/16 v11, 0x0

    .line 169
    const/4 v2, 0x0

    .line 171
    :goto_2
    invoke-virtual {v8, v4}, Ljava/io/InputStream;->read([B)I

    move-result v2

    const/4 v15, -0x1

    if-ne v2, v15, :cond_2

    .line 178
    invoke-virtual {v10}, Ljava/io/OutputStream;->flush()V

    .line 179
    invoke-virtual {v10}, Ljava/io/OutputStream;->close()V

    .line 180
    invoke-virtual {v8}, Ljava/io/InputStream;->close()V
    :try_end_4
    .catch Ljava/lang/Exception; {:try_start_4 .. :try_end_4} :catch_1

    .line 187
    const/4 v15, 0x1

    invoke-static {v15}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v15

    goto/16 :goto_0

    .line 135
    .end local v8           #input:Ljava/io/InputStream;
    .end local v10           #output:Ljava/io/OutputStream;
    .end local v14           #url:Ljava/net/URL;
    .restart local v7       #input:Ljava/io/InputStream;
    .restart local v9       #output:Ljava/io/OutputStream;
    .restart local v13       #url:Ljava/net/URL;
    :cond_1
    int-to-long v15, v2

    add-long/2addr v11, v15

    .line 137
    const/4 v15, 0x1

    :try_start_5
    new-array v15, v15, [Ljava/lang/Integer;

    const/16 v16, 0x0

    long-to-double v0, v11

    move-wide/from16 v17, v0

    const-wide/high16 v19, 0x4059

    mul-double v17, v17, v19

    int-to-double v0, v6

    move-wide/from16 v19, v0

    div-double v17, v17, v19

    move-wide/from16 v0, v17

    double-to-int v0, v0

    move/from16 v17, v0

    invoke-static/range {v17 .. v17}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v17

    aput-object v17, v15, v16

    move-object/from16 v0, p0

    invoke-virtual {v0, v15}, Lcom/openvehicles/OVMS/ServerCommands$CarLayoutDownloader;->publishProgress([Ljava/lang/Object;)V

    .line 138
    const/4 v15, 0x0

    invoke-virtual {v9, v4, v15, v2}, Ljava/io/OutputStream;->write([BII)V
    :try_end_5
    .catch Ljava/lang/Exception; {:try_start_5 .. :try_end_5} :catch_0

    goto/16 :goto_1

    .line 144
    .end local v2           #bytesCount:I
    .end local v3           #connection:Ljava/net/URLConnection;
    .end local v4           #data:[B
    .end local v6           #fileLength:I
    .end local v7           #input:Ljava/io/InputStream;
    .end local v9           #output:Ljava/io/OutputStream;
    .end local v11           #totalBytesReceived:J
    .end local v13           #url:Ljava/net/URL;
    :catch_0
    move-exception v5

    .line 145
    .local v5, e:Ljava/lang/Exception;
    const-string v15, "TCP"

    const-string v16, "!!! Car Layout Download Error !!!"

    invoke-static/range {v15 .. v16}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 146
    invoke-virtual {v5}, Ljava/lang/Exception;->printStackTrace()V

    .line 147
    const/4 v15, 0x0

    invoke-static {v15}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v15

    goto/16 :goto_0

    .line 172
    .end local v5           #e:Ljava/lang/Exception;
    .restart local v2       #bytesCount:I
    .restart local v3       #connection:Ljava/net/URLConnection;
    .restart local v4       #data:[B
    .restart local v6       #fileLength:I
    .restart local v8       #input:Ljava/io/InputStream;
    .restart local v10       #output:Ljava/io/OutputStream;
    .restart local v11       #totalBytesReceived:J
    .restart local v14       #url:Ljava/net/URL;
    :cond_2
    int-to-long v15, v2

    add-long/2addr v11, v15

    .line 174
    const/4 v15, 0x1

    :try_start_6
    new-array v15, v15, [Ljava/lang/Integer;

    const/16 v16, 0x0

    long-to-double v0, v11

    move-wide/from16 v17, v0

    const-wide/high16 v19, 0x4059

    mul-double v17, v17, v19

    int-to-double v0, v6

    move-wide/from16 v19, v0

    div-double v17, v17, v19

    move-wide/from16 v0, v17

    double-to-int v0, v0

    move/from16 v17, v0

    invoke-static/range {v17 .. v17}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v17

    aput-object v17, v15, v16

    move-object/from16 v0, p0

    invoke-virtual {v0, v15}, Lcom/openvehicles/OVMS/ServerCommands$CarLayoutDownloader;->publishProgress([Ljava/lang/Object;)V

    .line 175
    const/4 v15, 0x0

    invoke-virtual {v10, v4, v15, v2}, Ljava/io/OutputStream;->write([BII)V
    :try_end_6
    .catch Ljava/lang/Exception; {:try_start_6 .. :try_end_6} :catch_1

    goto :goto_2

    .line 181
    :catch_1
    move-exception v5

    move-object v9, v10

    .end local v10           #output:Ljava/io/OutputStream;
    .restart local v9       #output:Ljava/io/OutputStream;
    move-object v7, v8

    .end local v8           #input:Ljava/io/InputStream;
    .restart local v7       #input:Ljava/io/InputStream;
    move-object v13, v14

    .line 182
    .end local v14           #url:Ljava/net/URL;
    .restart local v5       #e:Ljava/lang/Exception;
    .restart local v13       #url:Ljava/net/URL;
    :goto_3
    const-string v15, "TCP"

    const-string v16, "!!! Car Layout Download Error !!!"

    invoke-static/range {v15 .. v16}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 183
    invoke-virtual {v5}, Ljava/lang/Exception;->printStackTrace()V

    .line 184
    const/4 v15, 0x0

    invoke-static {v15}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v15

    goto/16 :goto_0

    .line 181
    .end local v5           #e:Ljava/lang/Exception;
    :catch_2
    move-exception v5

    goto :goto_3

    .end local v13           #url:Ljava/net/URL;
    .restart local v14       #url:Ljava/net/URL;
    :catch_3
    move-exception v5

    move-object v13, v14

    .end local v14           #url:Ljava/net/URL;
    .restart local v13       #url:Ljava/net/URL;
    goto :goto_3

    .end local v7           #input:Ljava/io/InputStream;
    .end local v13           #url:Ljava/net/URL;
    .restart local v8       #input:Ljava/io/InputStream;
    .restart local v14       #url:Ljava/net/URL;
    :catch_4
    move-exception v5

    move-object v7, v8

    .end local v8           #input:Ljava/io/InputStream;
    .restart local v7       #input:Ljava/io/InputStream;
    move-object v13, v14

    .end local v14           #url:Ljava/net/URL;
    .restart local v13       #url:Ljava/net/URL;
    goto :goto_3
.end method

.method protected bridge varargs synthetic doInBackground([Ljava/lang/Object;)Ljava/lang/Object;
    .locals 1
    .parameter

    .prologue
    .line 1
    check-cast p1, [Ljava/lang/String;

    invoke-virtual {p0, p1}, Lcom/openvehicles/OVMS/ServerCommands$CarLayoutDownloader;->doInBackground([Ljava/lang/String;)Ljava/lang/Boolean;

    move-result-object v0

    return-object v0
.end method

.method protected onCancelled()V
    .locals 1

    .prologue
    .line 209
    iget-object v0, p0, Lcom/openvehicles/OVMS/ServerCommands$CarLayoutDownloader;->mProgressDialog:Landroid/app/ProgressDialog;

    invoke-virtual {v0}, Landroid/app/ProgressDialog;->dismiss()V

    .line 210
    return-void
.end method

.method protected onPostExecute(Ljava/lang/Boolean;)V
    .locals 1
    .parameter "result"

    .prologue
    .line 204
    iget-object v0, p0, Lcom/openvehicles/OVMS/ServerCommands$CarLayoutDownloader;->mProgressDialog:Landroid/app/ProgressDialog;

    invoke-virtual {v0}, Landroid/app/ProgressDialog;->dismiss()V

    .line 205
    return-void
.end method

.method protected bridge synthetic onPostExecute(Ljava/lang/Object;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    check-cast p1, Ljava/lang/Boolean;

    invoke-virtual {p0, p1}, Lcom/openvehicles/OVMS/ServerCommands$CarLayoutDownloader;->onPostExecute(Ljava/lang/Boolean;)V

    return-void
.end method

.method protected onPreExecute()V
    .locals 2

    .prologue
    .line 197
    iget-object v0, p0, Lcom/openvehicles/OVMS/ServerCommands$CarLayoutDownloader;->mProgressDialog:Landroid/app/ProgressDialog;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/app/ProgressDialog;->setIndeterminate(Z)V

    .line 198
    iget-object v0, p0, Lcom/openvehicles/OVMS/ServerCommands$CarLayoutDownloader;->mProgressDialog:Landroid/app/ProgressDialog;

    invoke-virtual {v0}, Landroid/app/ProgressDialog;->isShowing()Z

    move-result v0

    if-nez v0, :cond_0

    .line 199
    iget-object v0, p0, Lcom/openvehicles/OVMS/ServerCommands$CarLayoutDownloader;->mProgressDialog:Landroid/app/ProgressDialog;

    invoke-virtual {v0}, Landroid/app/ProgressDialog;->show()V

    .line 200
    :cond_0
    return-void
.end method

.method protected varargs onProgressUpdate([Ljava/lang/Integer;)V
    .locals 2
    .parameter "progress"

    .prologue
    .line 192
    iget-object v0, p0, Lcom/openvehicles/OVMS/ServerCommands$CarLayoutDownloader;->mProgressDialog:Landroid/app/ProgressDialog;

    const/4 v1, 0x0

    aget-object v1, p1, v1

    invoke-virtual {v1}, Ljava/lang/Integer;->intValue()I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/app/ProgressDialog;->setProgress(I)V

    .line 193
    return-void
.end method

.method protected bridge varargs synthetic onProgressUpdate([Ljava/lang/Object;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    check-cast p1, [Ljava/lang/Integer;

    invoke-virtual {p0, p1}, Lcom/openvehicles/OVMS/ServerCommands$CarLayoutDownloader;->onProgressUpdate([Ljava/lang/Integer;)V

    return-void
.end method
