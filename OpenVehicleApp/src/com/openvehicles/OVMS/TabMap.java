package com.openvehicles.OVMS;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.SlidingDrawer;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class TabMap extends MapActivity
{
	private final int CAR_MARKER_ANIMATION_DURATION_MS = 2000;
	private final int CAR_MARKER_ANIMATION_FRAMES = 40;
	private Bitmap DirectionalMarker;
	private final int LABEL_SHADOW_XY = 1;
	private final int LABEL_TEXT_SIZE = 20;
	private final int SYSTEM_OVERLAY_COUNT = 3;
	private Runnable animateCarMarker = new Runnable()
	{
		public void run()
		{
			CarData_Group localCarData_Group = null;
			String[] arrayOfString = new String[TabMap.this.data.Group.size()];
			TabMap.this.data.Group.keySet().toArray(arrayOfString);
			int i = 0;
			if (i >= TabMap.this.carMarkers.size());
			while (true)
			{
				TabMap.this.mapView.invalidate();
				TabMap localTabMap = TabMap.this;
				int m = 1 + localTabMap.carMarkerAnimationFrame;
				localTabMap.carMarkerAnimationFrame = m;
				if (m < 40)
					TabMap.this.carMarkerAnimationTimerHandler.postDelayed(TabMap.this.animateCarMarker, 50L);
				return;
				if (i <= 0)
					break label203;
				if (arrayOfString.length >= i)
					break;
				Object[] arrayOfObject2 = new Object[2];
				arrayOfObject2[0] = Integer.valueOf(TabMap.this.carMarkers.size());
				arrayOfObject2[1] = Integer.valueOf(1 + arrayOfString.length);
				Log.d("MAP", String.format("ERROR! Found %s markers but only %s car data.", arrayOfObject2));
			}
			localCarData_Group = (CarData_Group)TabMap.this.data.Group.get(arrayOfString[(i - 1)]);
			label203: if (i == 0);
			for (GeoPoint localGeoPoint1 = Utilities.GetCarGeopoint(TabMap.this.data); ; localGeoPoint1 = Utilities.GetCarGeopoint(localCarData_Group.Latitude, localCarData_Group.Longitude))
			{
				if (!TabMap.this.carMarkers.getItem(i).getPoint().equals(localGeoPoint1))
					break label265;
				i++;
				break;
			}
			label265: int j = (localGeoPoint1.getLatitudeE6() - TabMap.this.lastCarGeoPoints[i].getLatitudeE6()) / 40;
			int k = (localGeoPoint1.getLongitudeE6() - TabMap.this.lastCarGeoPoints[i].getLongitudeE6()) / 40;
			GeoPoint localGeoPoint2;
			label329: String str2;
			Object[] arrayOfObject1;
			if (TabMap.this.carMarkerAnimationFrame == 39)
			{
				localGeoPoint2 = localGeoPoint1;
				Log.d("MAP", "Car Marker " + i + " Transitional Point: " + localGeoPoint2.getLatitudeE6() + ", " + localGeoPoint2.getLongitudeE6());
				if (i != 0)
					break label556;
				String str1 = "-";
				if (TabMap.this.data.Data_LastCarUpdate != null)
					str1 = new SimpleDateFormat("MMM d, K:mm:ss a").format(TabMap.this.data.Data_LastCarUpdate);
				str2 = TabMap.this.data.VehicleID;
				arrayOfObject1 = new Object[1];
				arrayOfObject1[0] = str1;
			}
			label556: for (Utilities.CarMarker localCarMarker = new Utilities.CarMarker(localGeoPoint2, str2, String.format("Last reported: %s", arrayOfObject1), (int)TabMap.this.data.Data_Direction); ; localCarMarker = new Utilities.CarMarker(localGeoPoint2, localCarData_Group.VehicleID, "", (int)localCarData_Group.Direction))
			{
				TabMap.this.carMarkers.setOverlay(i, localCarMarker);
				break;
				localGeoPoint2 = new GeoPoint(TabMap.this.lastCarGeoPoints[i].getLatitudeE6() + j * TabMap.this.carMarkerAnimationFrame, TabMap.this.lastCarGeoPoints[i].getLongitudeE6() + k * TabMap.this.carMarkerAnimationFrame);
				break label329;
			}
		}
	};
	private ArrayList<HashMap<String, Object>> availableCarColors;
	private CustomSpinnerAdapter availableCarColorsSpinnerAdapter;
	private int carMarkerAnimationFrame = 0;
	private Handler carMarkerAnimationTimerHandler = new Handler();
	private Utilities.CarMarkerOverlay carMarkers;
	mapCenteringMode centeringMode;
	private String currentVehicleID = "";
	private CarData data;
	private GroupCarsListViewAdapter groupCarsListAdapter;
	final Runnable initializeMapCentering = new Runnable()
	{
		public void run()
		{
			Log.d("OVMS", "Centering Map");
			GeoPoint localGeoPoint = Utilities.GetCarGeopoint(TabMap.this.data);
			switch (TabMap.this.centeringMode.getMode())
			{
			default:
				TabMap.this.mc.animateTo(localGeoPoint);
				TabMap.this.mc.setZoom(18);
			case 4:
			case 1:
			case 2:
			case 3:
			case 5:
			}
			while (true)
			{
				TabMap.this.mapView.invalidate();
				return;
				if (TabMap.this.myLocationOverlay.getMyLocation() != null)
					TabMap.this.mc.animateTo(TabMap.this.myLocationOverlay.getMyLocation());
				TabMap.this.mc.setZoom(17);
				continue;
				TabMap.this.mc.animateTo(localGeoPoint);
				TabMap.this.mc.setZoom(17);
				continue;
				if (TabMap.this.mapOverlays.size() <= 3)
				{
					TabMap.this.centeringMode.setMode(2);
					TabMap.this.mc.animateTo(localGeoPoint);
					TabMap.this.mc.setZoom(17);
				}
				else
				{
					RouteOverlay localRouteOverlay1 = (RouteOverlay)TabMap.this.mapOverlays.get(3);
					int i = localRouteOverlay1.gp1.getLatitudeE6();
					int j = localRouteOverlay1.gp1.getLongitudeE6();
					int k = localRouteOverlay1.gp1.getLatitudeE6();
					int m = localRouteOverlay1.gp1.getLongitudeE6();
					for (int n = 3; ; n++)
					{
						if (n >= TabMap.this.mapOverlays.size())
						{
							Object[] arrayOfObject = new Object[4];
							arrayOfObject[0] = Integer.valueOf(i);
							arrayOfObject[1] = Integer.valueOf(k);
							arrayOfObject[2] = Integer.valueOf(j);
							arrayOfObject[3] = Integer.valueOf(m);
							Log.d("Map", String.format("Zoom Span: %s %s %s %s", arrayOfObject));
							TabMap.this.mapView.getController().zoomToSpan(100 + (k - i), 100 + (m - j));
							TabMap.this.mapView.getController().animateTo(new GeoPoint((k + i) / 2, (m + j) / 2));
							((RadioButton)TabMap.this.findViewById(2131296397)).setChecked(false);
							break;
						}
						RouteOverlay localRouteOverlay2 = (RouteOverlay)TabMap.this.mapOverlays.get(n);
						if (i > localRouteOverlay2.gp1.getLatitudeE6())
							i = localRouteOverlay2.gp1.getLatitudeE6();
						if (k < localRouteOverlay2.gp1.getLatitudeE6())
							k = localRouteOverlay2.gp1.getLatitudeE6();
						if (j > localRouteOverlay2.gp1.getLongitudeE6())
							j = localRouteOverlay2.gp1.getLongitudeE6();
						if (m < localRouteOverlay2.gp1.getLongitudeE6())
							m = localRouteOverlay2.gp1.getLongitudeE6();
					}
					if (TabMap.this.data.Group.get(TabMap.this.centeringMode.GroupCar_VehicleID) == null)
					{
						TabMap.this.centeringMode.setMode(4);
					}
					else
					{
						TabMap.this.mc.animateTo(Utilities.GetCarGeopoint(((CarData_Group)TabMap.this.data.Group.get(TabMap.this.centeringMode.GroupCar_VehicleID)).Latitude, ((CarData_Group)TabMap.this.data.Group.get(TabMap.this.centeringMode.GroupCar_VehicleID)).Longitude));
						TabMap.this.mc.setZoom(18);
					}
				}
			}
		}
	};
	private boolean isLoggedIn;
	private GeoPoint[] lastCarGeoPoints;
	private GeoPoint lastKnownDeviceGeoPoint;
	private Runnable lastUpdateTimer = new Runnable()
	{
		public void run()
		{
			TabMap.this.updateLastUpdatedView();
			TabMap.this.lastUpdateTimerHandler.postDelayed(TabMap.this.lastUpdateTimer, 5000L);
		}
	};
	private Handler lastUpdateTimerHandler = new Handler();
	private LocationListener locationListener;
	private LocationManager locationManager;
	private float mapDragLastX;
	private float mapDragLastY;
	private List<Overlay> mapOverlays;
	private MapView mapView;
	private MapController mc;
	private Runnable myLocationDisable = new Runnable()
	{
		public void run()
		{
			TabMap.this.myLocationOverlay.disableMyLocation();
		}
	};
	private Runnable myLocationEnable = new Runnable()
	{
		public void run()
		{
			TabMap.this.myLocationOverlay.enableMyLocation();
		}
	};
	private MyLocationOverlayCustom myLocationOverlay;
	private boolean planWalkingDirection;
	private Handler refreshUIHandler = new Handler()
	{
		public void handleMessage(Message paramAnonymousMessage)
		{
			Log.d("OVMS", "Refreshing Map");
			GeoPoint localGeoPoint = Utilities.GetCarGeopoint(TabMap.this.data);
			int j;
			label114: TextView localTextView;
			String str3;
			String str5;
			label230: label248: label254: int i;
			Iterator localIterator;
			label306: ListView localListView;
			if (TabMap.this.carMarkers.size() > 0)
			{
				TabMap.this.lastCarGeoPoints = new GeoPoint[TabMap.this.carMarkers.size()];
				j = 0;
				if (j >= TabMap.this.carMarkers.size())
				{
					TabMap.this.carMarkerAnimationTimerHandler.removeCallbacks(TabMap.this.animateCarMarker);
					TabMap.this.carMarkerAnimationFrame = 0;
					TabMap.this.carMarkerAnimationTimerHandler.postDelayed(TabMap.this.animateCarMarker, 0L);
					localTextView = (TextView)TabMap.this.findViewById(2131296387);
					str3 = (String)TabMap.this.data.Data_Parameters.get(Integer.valueOf(11));
					if ((str3 == null) || (str3.length() <= 0))
						break label723;
					if (TabMap.this.data.Group == null)
						break label695;
					Object[] arrayOfObject3 = new Object[3];
					arrayOfObject3[0] = str3;
					arrayOfObject3[1] = Integer.valueOf(TabMap.this.data.Group.size());
					if (TabMap.this.data.Group.size() <= 1)
						break label687;
					str5 = "s";
					arrayOfObject3[2] = str5;
					localTextView.setText(String.format("Group: %s (%s vehicle%s)", arrayOfObject3));
					localTextView.setVisibility(0);
					if ((TabMap.this.data.Group != null) && (TabMap.this.data.Group.size() > 0))
					{
						i = 1;
						localIterator = TabMap.this.data.Group.keySet().iterator();
						if (localIterator.hasNext())
							break label733;
					}
					switch (TabMap.this.centeringMode.getMode())
					{
					default:
						TabMap.this.mc.animateTo(localGeoPoint);
						TabMap.this.mc.setZoom(18);
					case 4:
						label384: localListView = (ListView)TabMap.this.findViewById(2131296398);
					if (TabMap.this.data.Group != null)
					{
						HashMap localHashMap = (HashMap)TabMap.this.data.Group.clone();
						localHashMap.remove(TabMap.this.data.VehicleID);
						TabMap.this.groupCarsListAdapter = new TabMap.GroupCarsListViewAdapter(TabMap.this, TabMap.this, 2130903058, localHashMap.values().toArray());
						localListView.setAdapter(TabMap.this.groupCarsListAdapter);
					}
					break;
					case 1:
					case 2:
					case 3:
					case 5:
					}
				}
			}
			while (true)
			{
				Log.d("Routing", "Redrawing Map with " + (-3 + TabMap.this.mapOverlays.size()) + " waypoints");
				TabMap.this.mapView.invalidate();
				return;
				TabMap.this.lastCarGeoPoints[j] = TabMap.this.carMarkers.getItem(j).getPoint();
				j++;
				break;
				String str1 = "-";
				if (TabMap.this.data.Data_LastCarUpdate != null)
					str1 = new SimpleDateFormat("MMM d, K:mm:ss a").format(TabMap.this.data.Data_LastCarUpdate);
				String str2 = TabMap.this.data.VehicleID;
				Object[] arrayOfObject1 = new Object[1];
				arrayOfObject1[0] = str1;
				Utilities.CarMarker localCarMarker1 = new Utilities.CarMarker(localGeoPoint, str2, String.format("Last reported: %s", arrayOfObject1), (int)TabMap.this.data.Data_Direction);
				TabMap.this.carMarkers.addOverlay(localCarMarker1);
				break label114;
				label687: str5 = "";
				break label230;
				label695: Object[] arrayOfObject2 = new Object[1];
				arrayOfObject2[0] = str3;
				localTextView.setText(String.format("Group: %s", arrayOfObject2));
				break label248;
				label723: localTextView.setVisibility(8);
				break label254;
				label733: String str4 = (String)localIterator.next();
				if (str4.equals(TabMap.this.data.VehicleID))
					break label306;
				CarData_Group localCarData_Group = (CarData_Group)TabMap.this.data.Group.get(str4);
				Utilities.CarMarker localCarMarker2 = new Utilities.CarMarker(Utilities.GetCarGeopoint(localCarData_Group.Latitude, localCarData_Group.Longitude), localCarData_Group.VehicleID, "", (int)localCarData_Group.Direction);
				Drawable localDrawable = TabMap.this.getResources().getDrawable(TabMap.this.getResources().getIdentifier(localCarData_Group.VehicleImageDrawable + "32x32", "drawable", "com.openvehicles.OVMS"));
				if (localDrawable == null)
					localDrawable = TabMap.this.getResources().getDrawable(TabMap.this.getResources().getIdentifier("car_roadster_arcticwhite32x32", "drawable", "com.openvehicles.OVMS"));
				localDrawable.setBounds(-localDrawable.getIntrinsicWidth() / 2, -localDrawable.getIntrinsicHeight(), localDrawable.getIntrinsicWidth() / 2, 0);
				localCarMarker2.setMarker(localDrawable);
				if (TabMap.this.carMarkers.size() > i)
					TabMap.this.carMarkers.setOverlay(i, localCarMarker2);
				while (true)
				{
					i++;
					break;
					TabMap.this.carMarkers.addOverlay(localCarMarker2);
				}
				if (TabMap.this.myLocationOverlay.getMyLocation() != null)
				{
					TabMap.this.mc.animateTo(TabMap.this.myLocationOverlay.getMyLocation());
					break label384;
				}
				Toast.makeText(TabMap.this, "Waiting for device location...", 0).show();
				break label384;
				TabMap.this.mc.animateTo(localGeoPoint);
				break label384;
				if (TabMap.this.myLocationOverlay.getMyLocation() != null)
				{
					TabMap.this.mc.animateTo(TabMap.this.myLocationOverlay.getMyLocation());
					break label384;
				}
				Toast.makeText(TabMap.this, "Waiting for device location...", 0).show();
				break label384;
				if (TabMap.this.data.Group.get(TabMap.this.centeringMode.GroupCar_VehicleID) == null)
				{
					TabMap.this.centeringMode.setMode(4);
					break label384;
				}
				TabMap.this.mc.animateTo(Utilities.GetCarGeopoint(((CarData_Group)TabMap.this.data.Group.get(TabMap.this.centeringMode.GroupCar_VehicleID)).Latitude, ((CarData_Group)TabMap.this.data.Group.get(TabMap.this.centeringMode.GroupCar_VehicleID)).Longitude));
				break label384;
				if (TabMap.this.groupCarsListAdapter != null)
				{
					TabMap.this.groupCarsListAdapter.clear();
					localListView.setAdapter(TabMap.this.groupCarsListAdapter);
				}
			}
		}
	};
	private Runnable routeError = new Runnable()
	{
		public void run()
		{
			Log.d("Routing", "Route Failed");
			Toast.makeText(TabMap.this, "Route Failed", 0);
		}
	};
	final Runnable updateCenteringOptions = new Runnable()
	{
		public void run()
		{
			switch (TabMap.this.centeringMode.getMode())
			{
			default:
			case 0:
			case 2:
			case 1:
			case 3:
			case 4:
			case 5:
			}
			while (true)
			{
				return;
				((RadioButton)TabMap.this.findViewById(2131296391)).setChecked(true);
				continue;
				((RadioButton)TabMap.this.findViewById(2131296392)).setChecked(true);
				continue;
				((RadioButton)TabMap.this.findViewById(2131296393)).setChecked(true);
				continue;
				((RadioButton)TabMap.this.findViewById(2131296394)).setChecked(true);
			}
		}
	};

	private void cancelRoute()
	{
		if ((this.centeringMode.getMode() == 4) || (this.centeringMode.getMode() == 3))
			this.centeringMode.setMode(2);
		clearRoute();
		((RadioButton)findViewById(2131296397)).setChecked(true);
		this.refreshUIHandler.sendEmptyMessage(0);
		Toast.makeText(this, "Route Cancelled", 0).show();
	}

	private void clearRoute()
	{
		for (int i = -1 + this.mapOverlays.size(); ; i--)
		{
			if (i < 3)
				return;
			this.mapOverlays.remove(i);
		}
	}

	private void drawRoute(List<GeoPoint> paramList, int paramInt)
	{
		Log.d("Route", "Creating overlay");
		if (this.mapOverlays.size() > 3)
			clearRoute();
		for (int i = 1; ; i++)
		{
			if (i >= paramList.size())
				return;
			this.mapOverlays.add(new RouteOverlay((GeoPoint)paramList.get(i - 1), (GeoPoint)paramList.get(i), paramInt));
		}
	}

	private List getRouteGeoPoints()
	{
		Log.d("Route", "Getting waypoints from google");
		String s = getMapKMLUrl(lastKnownDeviceGeoPoint, Utilities.GetCarGeopoint(data), planWalkingDirection);
		HttpGet httpget;
		DefaultHttpClient defaulthttpclient;
		Log.d("Route", (new StringBuilder("Request URL: ")).append(s).toString());
		httpget = new HttpGet(s);
		defaulthttpclient = new DefaultHttpClient();
		HttpResponse httpresponse = defaulthttpclient.execute(httpget);
		BufferedInputStream bufferedinputstream;
		BufferedReader bufferedreader;
		StringBuilder stringbuilder;
		bufferedinputstream = new BufferedInputStream(httpresponse.getEntity().getContent());
		InputStreamReader inputstreamreader = new InputStreamReader(bufferedinputstream);
		bufferedreader = new BufferedReader(inputstreamreader, 40960);
		stringbuilder = new StringBuilder();
		_L6:
			String s1 = bufferedreader.readLine();
		if(s1 != null) goto _L2; else goto _L1
		_L1:
			String s2;
		bufferedreader.close();
		bufferedinputstream.close();
		s2 = stringbuilder.toString();
		Log.d("Route", (new StringBuilder("KML Data Length: ")).append(s2.length()).toString());
		if(s2.length() > 0 && s2.indexOf("<LineString>") >= 0) goto _L4; else goto _L3
		_L3:
			Object obj = null;
		_L5:
			return ((List) (obj));
		Exception exception;
		exception;
		exception.printStackTrace();
		obj = null;
		goto _L5
		Exception exception1;
		exception1;
		exception1.printStackTrace();
		obj = null;
		goto _L5
		_L2:
			stringbuilder.append(s1);
		goto _L6
		Exception exception2;
		exception2;
		obj = null;
		goto _L5
		_L4:
			DocumentBuilderFactory documentbuilderfactory;
		DocumentBuilder documentbuilder;
		Document document;
		documentbuilderfactory = DocumentBuilderFactory.newInstance();
		documentbuilder = null;
		document = null;
		DocumentBuilder documentbuilder1 = documentbuilderfactory.newDocumentBuilder();
		documentbuilder = documentbuilder1;
		_L7:
			Document document1;
		StringReader stringreader = new StringReader(s2);
		document1 = documentbuilder.parse(new InputSource(stringreader));
		document = document1;
		_L8:
			String s3 = document.getElementsByTagName("LineString").item(0).getChildNodes().item(0).getFirstChild().getNodeValue();
		if(s3.length() != 0)
			break MISSING_BLOCK_LABEL_435;
		obj = null;
		goto _L5
		ParserConfigurationException parserconfigurationexception;
		parserconfigurationexception;
		parserconfigurationexception.printStackTrace();
		goto _L7
		SAXException saxexception;
		saxexception;
		saxexception.printStackTrace();
		goto _L8
		IOException ioexception;
		ioexception;
		ioexception.printStackTrace();
		goto _L8
		Exception exception4;
		exception4;
		if("".length() == 0)
			obj = null;
		else
			obj = null;
		goto _L5
		Exception exception3;
		exception3;
		if("".length() == 0)
			obj = null;
		else
			throw exception3;
		goto _L5
		String as[];
		int i;
		int j;
		Log.d("Route", (new StringBuilder("KML lineStrings: ")).append(s3).toString());
		as = s3.split(" ");
		obj = new ArrayList();
		i = as.length;
		j = 0;
		_L9:
			label0:
			{
			if(j < i)
				break label0;
			Log.d("Route", (new StringBuilder("Waypoint count: ")).append(((List) (obj)).size()).toString());
			}
		goto _L5
		String s4 = as[j];
		int k = (int)(1000000D * Double.parseDouble(s4.split(",")[0]));
		int l = (int)(1000000D * Double.parseDouble(s4.split(",")[1]));
		GeoPoint geopoint = new GeoPoint(l, k);
		((List) (obj)).add(geopoint);
		j++;
		goto _L9
	}

	private void hidePopup()
	{
		SlidingDrawer localSlidingDrawer = (SlidingDrawer)findViewById(2131296388);
		if (localSlidingDrawer.isOpened())
			localSlidingDrawer.close();
	}

	private void initPopup()
	{
		SlidingDrawer localSlidingDrawer = (SlidingDrawer)findViewById(2131296388);
		localSlidingDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener()
		{
			public void onDrawerOpened()
			{
				((TextView)TabMap.this.findViewById(2131296390)).setText("▼ Close Panel ▼");
			}
		});
		localSlidingDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener()
		{
			public void onDrawerClosed()
			{
				((TextView)TabMap.this.findViewById(2131296390)).setText("▲ Map Options ▲");
			}
		});
	}

	private void planRoute()
	{
		Toast.makeText(this, "Routing...", 0).show();
		try
		{
			Thread.sleep(100L);
			new Thread()
			{
				public void run()
				{
					Log.d("Route", "Starting routing thread");
					TabMap.this.updateRoute();
					TabMap.this.centeringMode.setMode(3);
					TabMap.this.refreshUIHandler.post(TabMap.this.initializeMapCentering);
				}
			}
			.start();
			return;
		}
		catch (InterruptedException localInterruptedException)
		{
			while (true)
				localInterruptedException.printStackTrace();
		}
	}

	private void showGroupCarPopup(String paramString)
	{
		if (this.data.Group == null)
			return;
		Object localObject1 = null;
		Iterator localIterator = this.data.Group.values().iterator();
		label29: label38: final Object localObject2;
		View localView;
		final Spinner localSpinner;
		TextView localTextView4;
		label153: String str1;
		label223: String str2;
		label293: Object[] arrayOfObject4;
		String str3;
		if (!localIterator.hasNext())
		{
			localObject2 = localObject1;
			localView = LayoutInflater.from(this).inflate(2130903057, null);
			localSpinner = (Spinner)localView.findViewById(2131296399);
			TextView localTextView1 = (TextView)localView.findViewById(2131296400);
			TextView localTextView2 = (TextView)localView.findViewById(2131296401);
			TextView localTextView3 = (TextView)localView.findViewById(2131296402);
			localTextView4 = (TextView)localView.findViewById(2131296403);
			localSpinner.setAdapter(this.availableCarColorsSpinnerAdapter);
			if ((localObject2.VehicleImageDrawable != null) && (localObject2.VehicleImageDrawable.length() != 0))
				break label539;
			localSpinner.setSelection(0);
			Object[] arrayOfObject1 = new Object[1];
			arrayOfObject1[0] = Double.valueOf(localObject2.SOC);
			localTextView1.setText(String.format("%s%%", arrayOfObject1));
			Object[] arrayOfObject2 = new Object[2];
			arrayOfObject2[0] = Double.valueOf(localObject2.Speed);
			if (!this.data.Data_DistanceUnit.equals("K"))
				break label602;
			str1 = "kph";
			arrayOfObject2[1] = str1;
			localTextView2.setText(String.format("%s %s", arrayOfObject2));
			Object[] arrayOfObject3 = new Object[3];
			arrayOfObject3[0] = Double.valueOf(localObject2.Direction);
			arrayOfObject3[1] = Double.valueOf(localObject2.Altitude);
			if (!this.data.Data_DistanceUnit.equals("K"))
				break label610;
			str2 = "m";
			arrayOfObject3[2] = str2;
			localTextView3.setText(String.format("%s' %s%s", arrayOfObject3));
			arrayOfObject4 = new Object[2];
			if (!localObject2.GPSLocked)
				break label618;
			str3 = "LOCK";
			label331: arrayOfObject4[0] = str3;
			if (!localObject2.GPSDataStale)
				break label626;
		}
		label539: label600: label602: label610: label618: label626: for (String str4 = "(STALE)"; ; str4 = "")
		{
			arrayOfObject4[1] = str4;
			localTextView4.setText(String.format("%s %s", arrayOfObject4));
			AlertDialog.Builder localBuilder1 = new AlertDialog.Builder(this);
			Object[] arrayOfObject5 = new Object[2];
			arrayOfObject5[0] = localObject2.VehicleID;
			arrayOfObject5[1] = this.data.Data_Parameters.get(Integer.valueOf(11));
			AlertDialog.Builder localBuilder2 = localBuilder1.setTitle(String.format("%s (%s)", arrayOfObject5)).setView(localView).setCancelable(true);
			DialogInterface.OnClickListener local18 = new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
				{
					if ((localObject2.VehicleImageDrawable == null) || (!localObject2.VehicleImageDrawable.equals(((HashMap)TabMap.this.availableCarColors.get(localSpinner.getSelectedItemPosition())).get("Name").toString())))
					{
						localObject2.VehicleImageDrawable = ((HashMap)TabMap.this.availableCarColors.get(localSpinner.getSelectedItemPosition())).get("Name").toString();
						((OVMSActivity)TabMap.this.getParent()).saveCars();
					}
					Toast.makeText(TabMap.this, "Locating " + localObject2.VehicleID, 0).show();
					TabMap.this.centeringMode.GroupCar_VehicleID = localObject2.VehicleID;
					TabMap.this.centeringMode.setMode(5);
					TabMap.this.refreshUIHandler.post(TabMap.this.initializeMapCentering);
					paramAnonymousDialogInterface.dismiss();
					TabMap.this.hidePopup();
				}
			};
			AlertDialog.Builder localBuilder3 = localBuilder2.setPositiveButton("Goto", local18);
			DialogInterface.OnClickListener local19 = new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
				{
					if ((localObject2.VehicleImageDrawable == null) || (!localObject2.VehicleImageDrawable.equals(((HashMap)TabMap.this.availableCarColors.get(localSpinner.getSelectedItemPosition())).get("Name").toString())))
					{
						localObject2.VehicleImageDrawable = ((HashMap)TabMap.this.availableCarColors.get(localSpinner.getSelectedItemPosition())).get("Name").toString();
						((OVMSActivity)TabMap.this.getParent()).saveCars();
						TabMap.this.refreshUIHandler.sendEmptyMessage(0);
					}
					paramAnonymousDialogInterface.dismiss();
				}
			};
			localBuilder3.setNegativeButton("Close", local19);
			AlertDialog localAlertDialog = localBuilder1.create();
			if (isFinishing())
				break;
			localAlertDialog.show();
			break;
			CarData_Group localCarData_Group = (CarData_Group)localIterator.next();
			if (!localCarData_Group.VehicleID.equals(paramString))
				break label29;
			localObject1 = localCarData_Group;
			break label38;
			for (int i = 0; ; i++)
			{
				if (i >= this.availableCarColors.size())
					break label600;
				if (((HashMap)this.availableCarColors.get(i)).get("Name").toString().equals(localObject2.VehicleImageDrawable))
				{
					localSpinner.setSelection(i);
					break;
				}
			}
			break label153;
			str1 = "mph";
			break label223;
			str2 = "ft";
			break label293;
			str3 = "Searching...";
			break label331;
		}
	}

	private void showPopup()
	{
		((SlidingDrawer)findViewById(2131296388)).open();
	}

	private void updateLastUpdatedView()
	{
	}

	private void updateRoute()
	{
		this.myLocationOverlay.disableMyLocation();
		List localList = getRouteGeoPoints();
		if (localList != null)
			drawRoute(localList, -16711936);
		while (true)
		{
			this.refreshUIHandler.postDelayed(this.myLocationEnable, 200L);
			Log.d("Route", "Route complete");
			return;
			runOnUiThread(this.routeError);
		}
	}

	public void Refresh(CarData paramCarData, boolean paramBoolean)
	{
		this.data = paramCarData;
		this.isLoggedIn = paramBoolean;
		if (this.data.Group == null)
			this.data.Group = new HashMap();
		if (!this.currentVehicleID.equals(paramCarData.VehicleID))
		{
			this.currentVehicleID = paramCarData.VehicleID;
			this.carMarkers = new Utilities.CarMarkerOverlay(getResources().getDrawable(getResources().getIdentifier(this.data.VehicleImageDrawable + "32x32", "drawable", "com.openvehicles.OVMS")), 20, this, this.DirectionalMarker, 1);
			this.mapOverlays.set(0, this.carMarkers);
			this.carMarkers.addOnGroupCarTappedListener(new Utilities.OnGroupCarTappedListener()
			{
				public void OnGroupCarTapped(String paramAnonymousString)
				{
					TabMap.this.showGroupCarPopup(paramAnonymousString);
				}
			});
		}
		this.refreshUIHandler.sendEmptyMessage(0);
	}

	public String getMapKMLUrl(GeoPoint paramGeoPoint1, GeoPoint paramGeoPoint2, boolean paramBoolean)
	{
		StringBuilder localStringBuilder = new StringBuilder();
		localStringBuilder.append("http://maps.google.com/maps?f=d&hl=en");
		localStringBuilder.append("&saddr=");
		localStringBuilder.append(Double.toString(paramGeoPoint1.getLatitudeE6() / 1000000.0D));
		localStringBuilder.append(",");
		localStringBuilder.append(Double.toString(paramGeoPoint1.getLongitudeE6() / 1000000.0D));
		localStringBuilder.append("&daddr=");
		localStringBuilder.append(Double.toString(paramGeoPoint2.getLatitudeE6() / 1000000.0D));
		localStringBuilder.append(",");
		localStringBuilder.append(Double.toString(paramGeoPoint2.getLongitudeE6() / 1000000.0D));
		localStringBuilder.append("&ie=UTF8&0&om=0&output=kml");
		if (paramBoolean)
			localStringBuilder.append("&dirflg=w");
		return localStringBuilder.toString();
	}

	protected boolean isRouteDisplayed()
	{
		return false;
	}

	public void onBackPressed()
	{
		SlidingDrawer localSlidingDrawer = (SlidingDrawer)findViewById(2131296388);
		if (localSlidingDrawer.isOpened())
			localSlidingDrawer.close();
		while (true)
		{
			return;
			super.onBackPressed();
		}
	}

	public void onCreate(Bundle paramBundle)
	{
		super.onCreate(paramBundle);
		setContentView(2130903056);
		this.mapView = ((MapView)findViewById(2131296386));
		this.mc = this.mapView.getController();
		this.mapView.setBuiltInZoomControls(true);
		this.centeringMode = new mapCenteringMode(null);
		this.centeringMode.setOnMapCenteringModeChangedListener(new OnCenteringModeChangedListener()
		{
			public void OnCenteringModeChanged(int paramAnonymousInt)
			{
				TabMap.this.refreshUIHandler.post(TabMap.this.updateCenteringOptions);
			}
		});
		this.DirectionalMarker = BitmapFactory.decodeResource(getResources(), 2130837512);
		this.mapOverlays = this.mapView.getOverlays();
		this.carMarkers = new Utilities.CarMarkerOverlay(getResources().getDrawable(2130837534), 20, this, this.DirectionalMarker, 1);
		this.mapOverlays.add(0, this.carMarkers);
		this.myLocationOverlay = new MyLocationOverlayCustom(this, this.mapView);
		this.mapOverlays.add(1, this.myLocationOverlay);
		TouchOverlay localTouchOverlay = new TouchOverlay(null);
		this.mapOverlays.add(2, localTouchOverlay);
		this.locationManager = ((LocationManager)getSystemService("location"));
		this.locationListener = new LocationListener()
		{
			public void onLocationChanged(Location paramAnonymousLocation)
			{
				int i = (int)(1000000.0D * paramAnonymousLocation.getLatitude());
				int j = (int)(1000000.0D * paramAnonymousLocation.getLongitude());
				TabMap.this.lastKnownDeviceGeoPoint = new GeoPoint(i, j);
				Object[] arrayOfObject = new Object[2];
				arrayOfObject[0] = Integer.valueOf(i);
				arrayOfObject[1] = Integer.valueOf(j);
				Log.d("GPS", String.format("lat: %s lng %s", arrayOfObject));
				if (TabMap.this.centeringMode.getMode() == 1)
					TabMap.this.refreshUIHandler.sendEmptyMessage(0);
			}

			public void onProviderDisabled(String paramAnonymousString)
			{
			}

			public void onProviderEnabled(String paramAnonymousString)
			{
			}

			public void onStatusChanged(String paramAnonymousString, int paramAnonymousInt, Bundle paramAnonymousBundle)
			{
			}
		};
		RadioButton localRadioButton1 = (RadioButton)findViewById(2131296391);
		localRadioButton1.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				if (!((RadioButton)paramAnonymousView).isChecked());
				while (true)
				{
					return;
					Toast.makeText(TabMap.this, "Car Location", 0).show();
					TabMap.this.centeringMode.setMode(2);
					TabMap.this.refreshUIHandler.post(TabMap.this.initializeMapCentering);
					TabMap.this.hidePopup();
				}
			}
		});
		localRadioButton1.setChecked(true);
		((RadioButton)findViewById(2131296392)).setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				if (!((RadioButton)paramAnonymousView).isChecked())
					return;
				if (TabMap.this.lastKnownDeviceGeoPoint == null)
					Toast.makeText(TabMap.this, "Waiting for device location...", 0).show();
				while (true)
				{
					TabMap.this.centeringMode.setMode(1);
					TabMap.this.refreshUIHandler.post(TabMap.this.initializeMapCentering);
					TabMap.this.hidePopup();
					break;
					Toast.makeText(TabMap.this, "Your Location", 0).show();
				}
			}
		});
		((RadioButton)findViewById(2131296393)).setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				if (!((RadioButton)paramAnonymousView).isChecked());
				while (true)
				{
					return;
					if (TabMap.this.mapOverlays.size() > 3)
						Toast.makeText(TabMap.this, "Fitting Route", 0).show();
					TabMap.this.centeringMode.setMode(3);
					TabMap.this.refreshUIHandler.post(TabMap.this.initializeMapCentering);
					TabMap.this.hidePopup();
				}
			}
		});
		((RadioButton)findViewById(2131296396)).setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				if (!((RadioButton)paramAnonymousView).isChecked());
				while (true)
				{
					return;
					if (TabMap.this.mapOverlays.size() > 3)
						TabMap.this.clearRoute();
					if (TabMap.this.lastKnownDeviceGeoPoint == null)
					{
						((RadioButton)TabMap.this.findViewById(2131296397)).setChecked(true);
						Toast.makeText(TabMap.this, "Waiting for device location...", 0).show();
					}
					else
					{
						TabMap.this.planRoute();
						TabMap.this.hidePopup();
					}
				}
			}
		});
		((RadioButton)findViewById(2131296395)).setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				if (!((RadioButton)paramAnonymousView).isChecked());
				while (true)
				{
					return;
					if (TabMap.this.mapOverlays.size() > 3)
						TabMap.this.clearRoute();
					if (TabMap.this.lastKnownDeviceGeoPoint == null)
					{
						Toast.makeText(TabMap.this, "Waiting for device location...", 0).show();
					}
					else
					{
						TabMap.this.planRoute();
						TabMap.this.hidePopup();
					}
				}
			}
		});
		RadioButton localRadioButton2 = (RadioButton)findViewById(2131296397);
		localRadioButton2.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				if (!((RadioButton)paramAnonymousView).isChecked());
				while (true)
				{
					return;
					TabMap.this.cancelRoute();
					TabMap.this.hidePopup();
				}
			}
		});
		localRadioButton2.setChecked(true);
		((ListView)findViewById(2131296398)).setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
			{
				TextView localTextView = (TextView)paramAnonymousView.findViewById(2131296405);
				TabMap.this.showGroupCarPopup(localTextView.getText().toString());
			}
		});
		initPopup();
		String[] arrayOfString1 = new String[23];
		arrayOfString1[0] = "car_roadster_arcticwhite";
		arrayOfString1[1] = "car_roadster_brilliantyellow";
		arrayOfString1[2] = "car_roadster_electricblue";
		arrayOfString1[3] = "car_roadster_fushionred";
		arrayOfString1[4] = "car_roadster_glacierblue";
		arrayOfString1[5] = "car_roadster_jetblack";
		arrayOfString1[6] = "car_roadster_lightninggreen";
		arrayOfString1[7] = "car_roadster_obsidianblack";
		arrayOfString1[8] = "car_roadster_racinggreen";
		arrayOfString1[9] = "car_roadster_radiantred";
		arrayOfString1[10] = "car_roadster_sterlingsilver";
		arrayOfString1[11] = "car_roadster_thundergray";
		arrayOfString1[12] = "car_roadster_twilightblue";
		arrayOfString1[13] = "car_roadster_veryorange";
		arrayOfString1[14] = "car_models_anzabrown";
		arrayOfString1[15] = "car_models_catalinawhite";
		arrayOfString1[16] = "car_models_montereyblue";
		arrayOfString1[17] = "car_models_sansimeonsilver";
		arrayOfString1[18] = "car_models_sequolagreen";
		arrayOfString1[19] = "car_models_shastapearlwhite";
		arrayOfString1[20] = "car_models_sierrablack";
		arrayOfString1[21] = "car_models_signaturered";
		arrayOfString1[22] = "car_models_tiburongrey";
		this.availableCarColors = new ArrayList();
		for (int i = 0; ; i++)
		{
			if (i >= arrayOfString1.length)
			{
				ArrayList localArrayList = this.availableCarColors;
				String[] arrayOfString2 = new String[1];
				arrayOfString2[0] = "Icon";
				int[] arrayOfInt = new int[1];
				arrayOfInt[0] = 2131296312;
				this.availableCarColorsSpinnerAdapter = new CustomSpinnerAdapter(this, localArrayList, 2130903051, arrayOfString2, arrayOfInt);
				return;
			}
			HashMap localHashMap = new HashMap();
			localHashMap.put("Name", arrayOfString1[i]);
			Resources localResources = getResources();
			Object[] arrayOfObject = new Object[1];
			arrayOfObject[0] = arrayOfString1[i];
			localHashMap.put("Icon", Integer.valueOf(localResources.getIdentifier(String.format("%s96x44", arrayOfObject), "drawable", "com.openvehicles.OVMS")));
			this.availableCarColors.add(localHashMap);
		}
	}

	protected void onPause()
	{
		try
		{
			Log.d("GPS", "OFF");
			this.locationManager.removeUpdates(this.locationListener);
			this.myLocationOverlay.disableCompass();
			this.myLocationOverlay.disableMyLocation();
			label35: this.lastUpdateTimerHandler.removeCallbacks(this.lastUpdateTimer);
			super.onPause();
			return;
		}
		catch (Exception localException)
		{
			break label35;
		}
	}

	public void onRestoreInstanceState(Bundle paramBundle)
	{
		super.onRestoreInstanceState(paramBundle);
		this.planWalkingDirection = paramBundle.getBoolean("planWalkingDirection");
		this.centeringMode.setMode(paramBundle.getInt("centeringMode"));
	}

	protected void onResume()
	{
		super.onResume();
		if (this.locationManager.isProviderEnabled("gps"))
		{
			Log.d("GPS", "ON");
			this.locationManager.requestLocationUpdates("network", 5000L, 5.0F, this.locationListener);
			this.locationManager.requestLocationUpdates("gps", 5000L, 5.0F, this.locationListener);
		}
		this.lastKnownDeviceGeoPoint = null;
		this.myLocationOverlay.enableCompass();
		this.myLocationOverlay.enableMyLocation();
		this.lastUpdateTimerHandler.postDelayed(this.lastUpdateTimer, 5000L);
	}

	public void onSaveInstanceState(Bundle paramBundle)
	{
		paramBundle.putBoolean("planWalkingDirection", this.planWalkingDirection);
		paramBundle.putInt("centeringMode", this.centeringMode.getMode());
		super.onSaveInstanceState(paramBundle);
	}

	class CustomSpinnerAdapter extends SimpleAdapter
	{
		LayoutInflater mInflater;

		public CustomSpinnerAdapter(List<? extends Map<String, ?>> paramInt, int paramArrayOfString, String[] paramArrayOfInt, int[] arg5)
		{
			super(paramArrayOfString, paramArrayOfInt, arrayOfString, arrayOfInt);
			this.mInflater = LayoutInflater.from(paramInt);
		}

		public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
		{
			if (paramView == null)
				paramView = this.mInflater.inflate(2130903051, null);
			HashMap localHashMap = (HashMap)getItem(paramInt);
			ImageView localImageView = (ImageView)paramView.findViewById(2131296312);
			StringBuilder localStringBuilder = new StringBuilder(String.valueOf(TabMap.this.getCacheDir().getAbsolutePath()));
			Object[] arrayOfObject = new Object[1];
			arrayOfObject[0] = localHashMap.get("Name").toString();
			Bitmap localBitmap = BitmapFactory.decodeFile(String.format("/%s.png", arrayOfObject));
			if (localBitmap != null)
				localImageView.setImageBitmap(localBitmap);
			while (true)
			{
				return paramView;
				localImageView.setImageBitmap(null);
				localImageView.setBackgroundResource(((Integer)localHashMap.get("Icon")).intValue());
			}
		}
	}

	private class GroupCarsListViewAdapter extends ArrayAdapter<Object>
	{
		private Object[] items;

		public GroupCarsListViewAdapter(Context paramInt, int paramArrayOfObject, Object[] arg4)
		{
			super(paramArrayOfObject, arrayOfObject);
			this.items = arrayOfObject;
		}

		public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
		{
			CarData_Group localCarData_Group = (CarData_Group)this.items[paramInt];
			if (localCarData_Group == null)
			{
				localView = null;
				return localView;
			}
			View localView = paramView;
			if (localView == null)
				localView = ((LayoutInflater)TabMap.this.getSystemService("layout_inflater")).inflate(2130903058, null);
			((TextView)localView.findViewById(2131296405)).setText(localCarData_Group.VehicleID);
			TextView localTextView1 = (TextView)localView.findViewById(2131296406);
			Object[] arrayOfObject1 = new Object[2];
			arrayOfObject1[0] = Double.valueOf(localCarData_Group.SOC);
			String str3;
			label148: String str1;
			label163: TextView localTextView2;
			double d;
			Object[] arrayOfObject2;
			if (localCarData_Group.Speed > 0.0D)
			{
				Object[] arrayOfObject3 = new Object[2];
				arrayOfObject3[0] = Double.valueOf(localCarData_Group.Speed);
				if (TabMap.this.data.Data_DistanceUnit.equals("K"))
				{
					str3 = "kph";
					arrayOfObject3[1] = str3;
					str1 = String.format(" | %s%s", arrayOfObject3);
					arrayOfObject1[1] = str1;
					localTextView1.setText(String.format("%s%%", arrayOfObject1));
					localTextView2 = (TextView)localView.findViewById(2131296407);
					d = Utilities.GetDistanceBetweenCoordinatesKM(localCarData_Group.Latitude, localCarData_Group.Longitude, TabMap.this.data.Data_Latitude, TabMap.this.data.Data_Longitude);
					arrayOfObject2 = new Object[2];
					if (!TabMap.this.data.Data_DistanceUnit.equals("K"))
						break label368;
					label252: arrayOfObject2[0] = Double.valueOf(d);
					if (!TabMap.this.data.Data_DistanceUnit.equals("K"))
						break label379;
				}
			}
			label368: label379: for (String str2 = "km"; ; str2 = "mi")
			{
				arrayOfObject2[1] = str2;
				localTextView2.setText(String.format("%.1f%n %s", arrayOfObject2));
				((ImageView)localView.findViewById(2131296404)).setImageResource(TabMap.this.getResources().getIdentifier(localCarData_Group.VehicleImageDrawable + "96x44", "drawable", "com.openvehicles.OVMS"));
				break;
				str3 = "mph";
				break label148;
				str1 = "";
				break label163;
				d *= 0.621371192D;
				break label252;
			}
		}
	}

	private static abstract interface OnCenteringModeChangedListener
	{
		public abstract void OnCenteringModeChanged(int paramInt);
	}

	private class TouchOverlay extends Overlay
	{
		private TouchOverlay()
		{
		}

		public boolean onTouchEvent(MotionEvent paramMotionEvent, MapView paramMapView)
		{
			if (paramMotionEvent.getAction() == 1)
			{
				TabMap.this.hidePopup();
				if (TabMap.this.centeringMode.getMode() != 4)
				{
					float f1 = paramMotionEvent.getX();
					float f2 = paramMotionEvent.getY();
					if (Math.sqrt(Math.pow(f1 - TabMap.this.mapDragLastX, 2.0D) + Math.pow(f2 - TabMap.this.mapDragLastY, 2.0D)) > 100.0D)
						TabMap.this.centeringMode.setMode(4);
				}
			}
			while (true)
			{
				return false;
				if (paramMotionEvent.getAction() == 0)
				{
					TabMap.this.mapDragLastX = paramMotionEvent.getX();
					TabMap.this.mapDragLastY = paramMotionEvent.getY();
				}
			}
		}
	}

	private class mapCenteringMode
	{
		public static final int CAR = 2;
		public static final int CUSTOM = 4;
		public static final int DEFAULT = 0;
		public static final int DEVICE = 1;
		public static final int GROUPCAR = 5;
		public static final int ROUTE = 3;
		public String GroupCar_VehicleID = "";
		private int _centeringMode = 0;
		private TabMap.OnCenteringModeChangedListener _listener = null;

		private mapCenteringMode()
		{
		}

		/** @deprecated */
		private void fireCenteringModeChangedEvent()
		{
			try
			{
				if (this._listener != null)
					this._listener.OnCenteringModeChanged(this._centeringMode);
				return;
			}
			finally
			{
				localObject = finally;
				throw localObject;
			}
		}

		public int getMode()
		{
			return this._centeringMode;
		}

		/** @deprecated */
		public void removeOnGroupCarTappedListener()
		{
			try
			{
				this._listener = null;
				return;
			}
			finally
			{
				localObject = finally;
				throw localObject;
			}
		}

		public void setMode(int paramInt)
		{
			if (this._centeringMode == paramInt);
			while (true)
			{
				return;
				this._centeringMode = paramInt;
				fireCenteringModeChangedEvent();
			}
		}

		/** @deprecated */
		public void setOnMapCenteringModeChangedListener(TabMap.OnCenteringModeChangedListener paramOnCenteringModeChangedListener)
		{
			try
			{
				this._listener = paramOnCenteringModeChangedListener;
				return;
			}
			finally
			{
				localObject = finally;
				throw localObject;
			}
		}
	}
}