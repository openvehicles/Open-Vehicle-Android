package com.openvehicles.OVMS;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Tab_SubTabDataUtilizations extends Activity {
	View chart = null;
	private CarData data;
	private Handler handler = new Handler() {
		public void handleMessage(Message paramAnonymousMessage) {
			try {
				Tab_SubTabDataUtilizations.this.refreshChart();
				return;
			} catch (Exception localException) {
				while (true) {
					localException.printStackTrace();
					if (Tab_SubTabDataUtilizations.this.data.Data_GPRSUtilization != null)
						Tab_SubTabDataUtilizations.this.data.Data_GPRSUtilization
						.Clear();
					Toast.makeText(Tab_SubTabDataUtilizations.this,
							"Tap refresh button to update data", 0).show();
				}
			}
		}
	};
	private boolean isLoggedIn;
	private Date lastRefresh = null;
	private String lastVehicleID = "";
	private OVMSActivity mOVMSActivity;

	private void refreshChart()
	{
		if (this.data.Data_GPRSUtilization == null);
		while (true)
		{
			return;
			SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("MMM dd");
			localSimpleDateFormat1.setTimeZone(TimeZone.getDefault());
			SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
			Object localObject = null;
			try
			{
				Date localDate5 = localSimpleDateFormat2.parse(localSimpleDateFormat2.format(new Date()));
				localObject = localDate5;
				Calendar localCalendar1 = Calendar.getInstance();
				localCalendar1.add(2, -1);
				Date localDate1 = ((GPRSUtilizationData)this.data.Data_GPRSUtilization.Utilizations.get(-1 + this.data.Data_GPRSUtilization.Utilizations.size())).DataDate;
				localDate2 = ((GPRSUtilizationData)this.data.Data_GPRSUtilization.Utilizations.get(0)).DataDate;
				StringBuilder localStringBuilder = new StringBuilder("Summing data from: ").append(localDate1.toLocaleString()).append(" to ").append(localDate2.toLocaleString()).append(" flags ");
				Log.d("CHART", 3);
				GPRSUtilization localGPRSUtilization1 = this.data.Data_GPRSUtilization;
				l1 = localGPRSUtilization1.GetUtilizationBytes(localDate1, 3);
				GPRSUtilization localGPRSUtilization2 = this.data.Data_GPRSUtilization;
				Date localDate3 = localCalendar1.getTime();
				l2 = localGPRSUtilization2.GetUtilizationBytes(localDate3, 3);
				GPRSUtilization localGPRSUtilization3 = this.data.Data_GPRSUtilization;
				l3 = localGPRSUtilization3.GetUtilizationBytes(localObject, 3);
				i = 1 + (int)((localObject.getTime() - localDate1.getTime()) / 86400000L);
				Log.d("CHART", "Total Bars: " + i);
				if (i == 0)
					Toast.makeText(this, "No data to plot", 0).show();
			}
			catch (ParseException localParseException)
			{
				Date localDate2;
				long l1;
				long l2;
				long l3;
				int i;
				while (true)
					localParseException.printStackTrace();
				long l4 = 0L;
				String[] arrayOfString = new String[2];
				arrayOfString[0] = "CAR TX";
				arrayOfString[1] = "CAR RX";
				ArrayList localArrayList = new ArrayList();
				localArrayList.add(new double[i]);
				localArrayList.add(new double[i]);
				int j = i - 1;
				Log.d("CHART", "today: " + localObject.toGMTString() + " data start: " + localDate2.toGMTString());
				if (localDate2.before(localObject))
				{
					int i3 = (int)((localDate2.getTime() - localObject.getTime()) / 86400000L);
					Log.d("CHART", "initial skip: " + i3);
					j += i3;
				}
				int k = 0;
				label538: int m = this.data.Data_GPRSUtilization.Utilizations.size();
				XYMultipleSeriesRenderer localXYMultipleSeriesRenderer;
				Calendar localCalendar2;
				int i2;
				label605: TextView localTextView2;
				if (k >= m)
				{
					int[] arrayOfInt = new int[2];
					arrayOfInt[0] = -256;
					arrayOfInt[1] = -16711681;
					localXYMultipleSeriesRenderer = buildBarRenderer(arrayOfInt);
					localCalendar2 = Calendar.getInstance();
					localCalendar2.add(5, -1);
					i2 = i - 1;
					if (i2 > 0)
						break label1317;
					setChartSettings(localXYMultipleSeriesRenderer, "GPRS Data Utilization", "Date", "KB", 0.0D, i, 0.0D, 1.1D * l4, -7829368, -1);
					localXYMultipleSeriesRenderer.getSeriesRendererAt(1).setDisplayChartValues(false);
					localXYMultipleSeriesRenderer.getSeriesRendererAt(1).setGradientEnabled(true);
					localXYMultipleSeriesRenderer.getSeriesRendererAt(1).setGradientStart(0.0D, -1);
					localXYMultipleSeriesRenderer.getSeriesRendererAt(1).setGradientStop(5.0D, -16711681);
					localXYMultipleSeriesRenderer.getSeriesRendererAt(1).setChartValuesTextSize(13.0F);
					localXYMultipleSeriesRenderer.getSeriesRendererAt(0).setDisplayChartValues(true);
					localXYMultipleSeriesRenderer.getSeriesRendererAt(0).setGradientEnabled(true);
					localXYMultipleSeriesRenderer.getSeriesRendererAt(0).setGradientStart(0.0D, -1);
					localXYMultipleSeriesRenderer.getSeriesRendererAt(0).setGradientStop(10.0D, -256);
					localXYMultipleSeriesRenderer.getSeriesRendererAt(0).setChartValuesTextSize(13.0F);
					localXYMultipleSeriesRenderer.setXLabels(0);
					localXYMultipleSeriesRenderer.setYLabels(10);
					localXYMultipleSeriesRenderer.setXLabelsAlign(Paint.Align.LEFT);
					localXYMultipleSeriesRenderer.setYLabelsAlign(Paint.Align.LEFT);
					localXYMultipleSeriesRenderer.setPanEnabled(true, false);
					localXYMultipleSeriesRenderer.setZoomEnabled(false, false);
					localXYMultipleSeriesRenderer.setBarSpacing(1.0D);
					LinearLayout localLinearLayout = (LinearLayout)findViewById(2131296277);
					if (this.chart != null)
						localLinearLayout.removeView(this.chart);
					this.chart = ChartFactory.getBarChartView(this, buildBarDataset(arrayOfString, localArrayList), localXYMultipleSeriesRenderer, BarChart.Type.STACKED);
					localLinearLayout.addView(this.chart);
					TextView localTextView1 = (TextView)findViewById(2131296276);
					long l6 = ()Math.ceil(l3 / 1024.0D);
					Object[] arrayOfObject1 = new Object[1];
					arrayOfObject1[0] = Long.valueOf(l6);
					localTextView1.setText(String.format("%s KB", arrayOfObject1));
					localTextView2 = (TextView)findViewById(2131296275);
					if (l2 <= 1048576L)
						break label1363;
					Object[] arrayOfObject5 = new Object[1];
					arrayOfObject5[0] = Long.valueOf(()Math.ceil(l2 / 1024.0D / 1024.0D));
					localTextView2.setText(String.format("%s MB", arrayOfObject5));
				}
				TextView localTextView3;
				while (true)
				{
					localTextView3 = (TextView)findViewById(2131296274);
					if (l1 <= 1048576L)
						break label1403;
					Object[] arrayOfObject4 = new Object[1];
					arrayOfObject4[0] = Long.valueOf(()Math.ceil(l1 / 1024.0D / 1024.0D));
					localTextView3.setText(String.format("%s MB", arrayOfObject4));
					break;
					GPRSUtilizationData localGPRSUtilizationData = (GPRSUtilizationData)this.data.Data_GPRSUtilization.Utilizations.get(k);
					if (j < 0)
					{
						Log.d("CHART", "Ignoring a rendering error");
						j = 0;
					}
					((double[])localArrayList.get(0))[j] = Math.ceil(localGPRSUtilizationData.Car_tx + localGPRSUtilizationData.Car_rx / 1024.0D);
					((double[])localArrayList.get(1))[j] = Math.ceil(localGPRSUtilizationData.Car_rx / 1024.0D);
					long l5 = ()Math.ceil(localGPRSUtilizationData.Car_rx + localGPRSUtilizationData.Car_tx / 1024.0D);
					if (l5 > l4)
						l4 = l5;
					int n = -1 + this.data.Data_GPRSUtilization.Utilizations.size();
					if (k < n)
					{
						Date localDate4 = ((GPRSUtilizationData)this.data.Data_GPRSUtilization.Utilizations.get(k + 1)).DataDate;
						int i1 = (int)((localDate4.getTime() - localGPRSUtilizationData.DataDate.getTime()) / 86400000L);
						if (i1 < -1)
							Log.d("CHART", "curr: " + localGPRSUtilizationData.DataDate.toGMTString() + " next: " + localDate4.toGMTString() + " skip: " + i1);
						j += i1;
					}
					k++;
					break label538;
					label1317: if (i2 % 7 == (i - 1) % 7)
						localXYMultipleSeriesRenderer.addXTextLabel(i2, localSimpleDateFormat1.format(localCalendar2.getTime()));
					localCalendar2.add(5, -1);
					i2--;
					break label605;
					label1363: Object[] arrayOfObject2 = new Object[1];
					arrayOfObject2[0] = Long.valueOf(()Math.ceil(l2 / 1024.0D));
					localTextView2.setText(String.format("%s KB", arrayOfObject2));
				}
				label1403: Object[] arrayOfObject3 = new Object[1];
				arrayOfObject3[0] = Long.valueOf(()Math.ceil(l1 / 1024.0D));
				localTextView3.setText(String.format("%s KB", arrayOfObject3));
			}
		}
	}

	private void requestData() {
		Toast.makeText(this, "Requesting Data...", 1).show();
		this.data.Data_GPRSUtilization.Clear();
		this.mOVMSActivity.SendServerCommand("C30");
	}

	public void Refresh(CarData paramCarData, boolean paramBoolean) {
		this.data = paramCarData;
		if ((this.data.Data_GPRSUtilization == null)
				|| (!this.lastVehicleID.equals(this.data.VehicleID))
				|| ((this.data.Data_GPRSUtilization.LastDataRefresh != null) && (!this.data.Data_GPRSUtilization.LastDataRefresh
						.equals(this.lastRefresh)))) {
			this.lastVehicleID = this.data.VehicleID;
			this.lastRefresh = this.data.Data_GPRSUtilization.LastDataRefresh;
			this.handler.sendEmptyMessage(0);
		}
	}

	protected XYMultipleSeriesDataset buildBarDataset(
			String[] paramArrayOfString, List<double[]> paramList) {
		XYMultipleSeriesDataset localXYMultipleSeriesDataset = new XYMultipleSeriesDataset();
		int i = paramArrayOfString.length;
		int j = 0;
		if (j >= i)
			return localXYMultipleSeriesDataset;
		CategorySeries localCategorySeries = new CategorySeries(
				paramArrayOfString[j]);
		double[] arrayOfDouble = (double[]) paramList.get(j);
		int k = arrayOfDouble.length;
		for (int m = 0;; m++) {
			if (m >= k) {
				localXYMultipleSeriesDataset.addSeries(localCategorySeries
						.toXYSeries());
				j++;
				break;
			}
			localCategorySeries.add(arrayOfDouble[m]);
		}
	}

	protected XYMultipleSeriesRenderer buildBarRenderer(int[] paramArrayOfInt) {
		XYMultipleSeriesRenderer localXYMultipleSeriesRenderer = new XYMultipleSeriesRenderer();
		localXYMultipleSeriesRenderer.setAxisTitleTextSize(16.0F);
		localXYMultipleSeriesRenderer.setChartTitleTextSize(20.0F);
		localXYMultipleSeriesRenderer.setLabelsTextSize(15.0F);
		localXYMultipleSeriesRenderer.setLegendTextSize(15.0F);
		int i = paramArrayOfInt.length;
		for (int j = 0;; j++) {
			if (j >= i)
				return localXYMultipleSeriesRenderer;
			SimpleSeriesRenderer localSimpleSeriesRenderer = new SimpleSeriesRenderer();
			localSimpleSeriesRenderer.setColor(paramArrayOfInt[j]);
			localXYMultipleSeriesRenderer
			.addSeriesRenderer(localSimpleSeriesRenderer);
		}
	}

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(2130903047);
		this.mOVMSActivity = ((OVMSActivity) getParent().getParent());
		if (this.mOVMSActivity == null)
			this.mOVMSActivity = ((OVMSActivity) getParent());
		if (this.mOVMSActivity == null)
			Toast.makeText(
					this,
					"Unexpected Layout Error - controls on this page may not work",
					1).show();
		while (true) {
			return;
			((ImageButton) findViewById(2131296273))
			.setOnClickListener(new View.OnClickListener() {
				public void onClick(View paramAnonymousView) {
					Tab_SubTabDataUtilizations.this.requestData();
				}
			});
		}
	}

	protected void setChartSettings(
			XYMultipleSeriesRenderer paramXYMultipleSeriesRenderer,
			String paramString1, String paramString2, String paramString3,
			double paramDouble1, double paramDouble2, double paramDouble3,
			double paramDouble4, int paramInt1, int paramInt2) {
		paramXYMultipleSeriesRenderer.setChartTitle(paramString1);
		paramXYMultipleSeriesRenderer.setXTitle(paramString2);
		paramXYMultipleSeriesRenderer.setYTitle(paramString3);
		paramXYMultipleSeriesRenderer.setXAxisMin(paramDouble1);
		paramXYMultipleSeriesRenderer.setXAxisMax(paramDouble2);
		paramXYMultipleSeriesRenderer.setYAxisMin(paramDouble3);
		paramXYMultipleSeriesRenderer.setYAxisMax(paramDouble4);
		paramXYMultipleSeriesRenderer.setAxesColor(paramInt1);
		paramXYMultipleSeriesRenderer.setLabelsColor(paramInt2);
	}
}