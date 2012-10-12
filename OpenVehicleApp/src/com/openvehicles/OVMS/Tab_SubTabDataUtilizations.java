// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.openvehicles.OVMS;

import android.app.Activity;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.widget.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

// Referenced classes of package com.openvehicles.OVMS:
//            CarData, GPRSUtilization, GPRSUtilizationData, OVMSActivity

public class Tab_SubTabDataUtilizations extends Activity
{

    public Tab_SubTabDataUtilizations()
    {
        lastRefresh = null;
        lastVehicleID = "";
        chart = null;
        handler = new Handler() {

            public void handleMessage(Message message)
            {
                refreshChart();
_L1:
                return;
                Exception exception;
                exception;
                exception.printStackTrace();
                if(data.Data_GPRSUtilization != null)
                    data.Data_GPRSUtilization.Clear();
                Toast.makeText(Tab_SubTabDataUtilizations.this, "Tap refresh button to update data", 0).show();
                  goto _L1
            }

            final Tab_SubTabDataUtilizations this$0;

            
            {
                this$0 = Tab_SubTabDataUtilizations.this;
                super();
            }
        }
;
    }

    private void refreshChart()
    {
        if(data.Data_GPRSUtilization != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        SimpleDateFormat simpledateformat;
        SimpleDateFormat simpledateformat1;
        Date date;
        simpledateformat = new SimpleDateFormat("MMM dd");
        simpledateformat.setTimeZone(TimeZone.getDefault());
        simpledateformat1 = new SimpleDateFormat("yyyy-MM-dd");
        date = null;
        Date date5 = simpledateformat1.parse(simpledateformat1.format(new Date()));
        date = date5;
_L3:
        Date date2;
        long l;
        long l1;
        long l2;
        int i;
        Calendar calendar = Calendar.getInstance();
        calendar.add(2, -1);
        Date date1 = ((GPRSUtilizationData)data.Data_GPRSUtilization.Utilizations.get(-1 + data.Data_GPRSUtilization.Utilizations.size())).DataDate;
        date2 = ((GPRSUtilizationData)data.Data_GPRSUtilization.Utilizations.get(0)).DataDate;
        StringBuilder stringbuilder = (new StringBuilder("Summing data from: ")).append(date1.toLocaleString()).append(" to ").append(date2.toLocaleString()).append(" flags ");
        data.Data_GPRSUtilization;
        data.Data_GPRSUtilization;
        Log.d("CHART", stringbuilder.append(3).toString());
        GPRSUtilization gprsutilization = data.Data_GPRSUtilization;
        data.Data_GPRSUtilization;
        data.Data_GPRSUtilization;
        l = gprsutilization.GetUtilizationBytes(date1, 3);
        GPRSUtilization gprsutilization1 = data.Data_GPRSUtilization;
        Date date3 = calendar.getTime();
        data.Data_GPRSUtilization;
        data.Data_GPRSUtilization;
        l1 = gprsutilization1.GetUtilizationBytes(date3, 3);
        GPRSUtilization gprsutilization2 = data.Data_GPRSUtilization;
        data.Data_GPRSUtilization;
        data.Data_GPRSUtilization;
        l2 = gprsutilization2.GetUtilizationBytes(date, 3);
        i = 1 + (int)((date.getTime() - date1.getTime()) / 0x5265c00L);
        Log.d("CHART", (new StringBuilder("Total Bars: ")).append(i).toString());
        if(i != 0)
            break MISSING_BLOCK_LABEL_381;
        Toast.makeText(this, "No data to plot", 0).show();
          goto _L1
        ParseException parseexception;
        parseexception;
        parseexception.printStackTrace();
          goto _L3
        long l3;
        String as[];
        ArrayList arraylist;
        int j;
        int k;
        l3 = 0L;
        as = new String[2];
        as[0] = "CAR TX";
        as[1] = "CAR RX";
        arraylist = new ArrayList();
        arraylist.add(new double[i]);
        arraylist.add(new double[i]);
        j = i - 1;
        Log.d("CHART", (new StringBuilder("today: ")).append(date.toGMTString()).append(" data start: ").append(date2.toGMTString()).toString());
        if(date2.before(date))
        {
            int j2 = (int)((date2.getTime() - date.getTime()) / 0x5265c00L);
            Log.d("CHART", (new StringBuilder("initial skip: ")).append(j2).toString());
            j += j2;
        }
        k = 0;
_L6:
        int i1 = data.Data_GPRSUtilization.Utilizations.size();
        if(k < i1) goto _L5; else goto _L4
_L4:
        XYMultipleSeriesRenderer xymultipleseriesrenderer;
        Calendar calendar1;
        int i2;
        int ai[] = new int[2];
        ai[0] = -256;
        ai[1] = 0xff00ffff;
        xymultipleseriesrenderer = buildBarRenderer(ai);
        calendar1 = Calendar.getInstance();
        calendar1.add(5, -1);
        i2 = i - 1;
_L7:
        if(i2 > 0)
            break MISSING_BLOCK_LABEL_1317;
        setChartSettings(xymultipleseriesrenderer, "GPRS Data Utilization", "Date", "KB", 0.0D, i, 0.0D, 1.1000000000000001D * (double)l3, 0xff888888, -1);
        xymultipleseriesrenderer.getSeriesRendererAt(1).setDisplayChartValues(false);
        xymultipleseriesrenderer.getSeriesRendererAt(1).setGradientEnabled(true);
        xymultipleseriesrenderer.getSeriesRendererAt(1).setGradientStart(0.0D, -1);
        xymultipleseriesrenderer.getSeriesRendererAt(1).setGradientStop(5D, 0xff00ffff);
        xymultipleseriesrenderer.getSeriesRendererAt(1).setChartValuesTextSize(13F);
        xymultipleseriesrenderer.getSeriesRendererAt(0).setDisplayChartValues(true);
        xymultipleseriesrenderer.getSeriesRendererAt(0).setGradientEnabled(true);
        xymultipleseriesrenderer.getSeriesRendererAt(0).setGradientStart(0.0D, -1);
        xymultipleseriesrenderer.getSeriesRendererAt(0).setGradientStop(10D, -256);
        xymultipleseriesrenderer.getSeriesRendererAt(0).setChartValuesTextSize(13F);
        xymultipleseriesrenderer.setXLabels(0);
        xymultipleseriesrenderer.setYLabels(10);
        xymultipleseriesrenderer.setXLabelsAlign(android.graphics.Paint.Align.LEFT);
        xymultipleseriesrenderer.setYLabelsAlign(android.graphics.Paint.Align.LEFT);
        xymultipleseriesrenderer.setPanEnabled(true, false);
        xymultipleseriesrenderer.setZoomEnabled(false, false);
        xymultipleseriesrenderer.setBarSpacing(1.0D);
        LinearLayout linearlayout = (LinearLayout)findViewById(0x7f090015);
        if(chart != null)
            linearlayout.removeView(chart);
        chart = ChartFactory.getBarChartView(this, buildBarDataset(as, arraylist), xymultipleseriesrenderer, org.achartengine.chart.BarChart.Type.STACKED);
        linearlayout.addView(chart);
        TextView textview = (TextView)findViewById(0x7f090014);
        long l5 = (long)Math.ceil((double)l2 / 1024D);
        Object aobj[] = new Object[1];
        aobj[0] = Long.valueOf(l5);
        textview.setText(String.format("%s KB", aobj));
        TextView textview1 = (TextView)findViewById(0x7f090013);
        GPRSUtilizationData gprsutilizationdata;
        long l4;
        int j1;
        Date date4;
        int k1;
        TextView textview2;
        if(l1 > 0x100000L)
        {
            Object aobj4[] = new Object[1];
            aobj4[0] = Long.valueOf((long)Math.ceil((double)l1 / 1024D / 1024D));
            textview1.setText(String.format("%s MB", aobj4));
        } else
        {
            Object aobj1[] = new Object[1];
            aobj1[0] = Long.valueOf((long)Math.ceil((double)l1 / 1024D));
            textview1.setText(String.format("%s KB", aobj1));
        }
        textview2 = (TextView)findViewById(0x7f090012);
        if(l > 0x100000L)
        {
            Object aobj3[] = new Object[1];
            aobj3[0] = Long.valueOf((long)Math.ceil((double)l / 1024D / 1024D));
            textview2.setText(String.format("%s MB", aobj3));
        } else
        {
            Object aobj2[] = new Object[1];
            aobj2[0] = Long.valueOf((long)Math.ceil((double)l / 1024D));
            textview2.setText(String.format("%s KB", aobj2));
        }
          goto _L1
_L5:
        gprsutilizationdata = (GPRSUtilizationData)data.Data_GPRSUtilization.Utilizations.get(k);
        if(j < 0)
        {
            Log.d("CHART", "Ignoring a rendering error");
            j = 0;
        }
        ((double[])arraylist.get(0))[j] = Math.ceil((double)(gprsutilizationdata.Car_tx + gprsutilizationdata.Car_rx) / 1024D);
        ((double[])arraylist.get(1))[j] = Math.ceil((double)gprsutilizationdata.Car_rx / 1024D);
        l4 = (long)Math.ceil((double)(gprsutilizationdata.Car_rx + gprsutilizationdata.Car_tx) / 1024D);
        if(l4 > l3)
            l3 = l4;
        j1 = -1 + data.Data_GPRSUtilization.Utilizations.size();
        if(k < j1)
        {
            date4 = ((GPRSUtilizationData)data.Data_GPRSUtilization.Utilizations.get(k + 1)).DataDate;
            k1 = (int)((date4.getTime() - gprsutilizationdata.DataDate.getTime()) / 0x5265c00L);
            if(k1 < -1)
                Log.d("CHART", (new StringBuilder("curr: ")).append(gprsutilizationdata.DataDate.toGMTString()).append(" next: ").append(date4.toGMTString()).append(" skip: ").append(k1).toString());
            j += k1;
        }
        k++;
          goto _L6
        if(i2 % 7 == (i - 1) % 7)
            xymultipleseriesrenderer.addXTextLabel(i2, simpledateformat.format(calendar1.getTime()));
        calendar1.add(5, -1);
        i2--;
          goto _L7
    }

    private void requestData()
    {
        Toast.makeText(this, "Requesting Data...", 1).show();
        data.Data_GPRSUtilization.Clear();
        mOVMSActivity.SendServerCommand("C30");
    }

    public void Refresh(CarData cardata, boolean flag)
    {
        data = cardata;
        if(data.Data_GPRSUtilization == null || !lastVehicleID.equals(data.VehicleID) || data.Data_GPRSUtilization.LastDataRefresh != null && !data.Data_GPRSUtilization.LastDataRefresh.equals(lastRefresh))
        {
            lastVehicleID = data.VehicleID;
            lastRefresh = data.Data_GPRSUtilization.LastDataRefresh;
            handler.sendEmptyMessage(0);
        }
    }

    protected XYMultipleSeriesDataset buildBarDataset(String as[], List list)
    {
        XYMultipleSeriesDataset xymultipleseriesdataset;
        int i;
        int j;
        xymultipleseriesdataset = new XYMultipleSeriesDataset();
        i = as.length;
        j = 0;
_L2:
        if(j >= i)
            return xymultipleseriesdataset;
        CategorySeries categoryseries = new CategorySeries(as[j]);
        double ad[] = (double[])list.get(j);
        int k = ad.length;
        int l = 0;
        do
        {
label0:
            {
                if(l < k)
                    break label0;
                xymultipleseriesdataset.addSeries(categoryseries.toXYSeries());
                j++;
            }
            if(true)
                continue;
            categoryseries.add(ad[l]);
            l++;
        } while(true);
        if(true) goto _L2; else goto _L1
_L1:
    }

    protected XYMultipleSeriesRenderer buildBarRenderer(int ai[])
    {
        XYMultipleSeriesRenderer xymultipleseriesrenderer = new XYMultipleSeriesRenderer();
        xymultipleseriesrenderer.setAxisTitleTextSize(16F);
        xymultipleseriesrenderer.setChartTitleTextSize(20F);
        xymultipleseriesrenderer.setLabelsTextSize(15F);
        xymultipleseriesrenderer.setLegendTextSize(15F);
        int i = ai.length;
        int j = 0;
        do
        {
            if(j >= i)
                return xymultipleseriesrenderer;
            SimpleSeriesRenderer simpleseriesrenderer = new SimpleSeriesRenderer();
            simpleseriesrenderer.setColor(ai[j]);
            xymultipleseriesrenderer.addSeriesRenderer(simpleseriesrenderer);
            j++;
        } while(true);
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f030007);
        mOVMSActivity = (OVMSActivity)getParent().getParent();
        if(mOVMSActivity == null)
            mOVMSActivity = (OVMSActivity)getParent();
        if(mOVMSActivity == null)
            Toast.makeText(this, "Unexpected Layout Error - controls on this page may not work", 1).show();
        else
            ((ImageButton)findViewById(0x7f090011)).setOnClickListener(new android.view.View.OnClickListener() {

                public void onClick(View view)
                {
                    requestData();
                }

                final Tab_SubTabDataUtilizations this$0;

            
            {
                this$0 = Tab_SubTabDataUtilizations.this;
                super();
            }
            }
);
    }

    protected void setChartSettings(XYMultipleSeriesRenderer xymultipleseriesrenderer, String s, String s1, String s2, double d, double d1, double d2, double d3, int i, int j)
    {
        xymultipleseriesrenderer.setChartTitle(s);
        xymultipleseriesrenderer.setXTitle(s1);
        xymultipleseriesrenderer.setYTitle(s2);
        xymultipleseriesrenderer.setXAxisMin(d);
        xymultipleseriesrenderer.setXAxisMax(d1);
        xymultipleseriesrenderer.setYAxisMin(d2);
        xymultipleseriesrenderer.setYAxisMax(d3);
        xymultipleseriesrenderer.setAxesColor(i);
        xymultipleseriesrenderer.setLabelsColor(j);
    }

    View chart;
    private CarData data;
    private Handler handler;
    private boolean isLoggedIn;
    private Date lastRefresh;
    private String lastVehicleID;
    private OVMSActivity mOVMSActivity;



}
