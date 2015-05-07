package com.openvehicles.OVMS.ui;

import java.text.DecimalFormat;
import java.util.*;

import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.GeodesicData;
import pl.mg6.android.maps.extensions.GoogleMap.InfoWindowAdapter;
import pl.mg6.android.maps.extensions.Polyline;
import pl.mg6.android.maps.extensions.PolylineOptions;

import pl.mg6.android.maps.extensions.CircleOptions;
import pl.mg6.android.maps.extensions.ClusterGroup;
import pl.mg6.android.maps.extensions.ClusteringSettings;
import pl.mg6.android.maps.extensions.GoogleMap;
import pl.mg6.android.maps.extensions.GoogleMap.OnInfoWindowClickListener;
import pl.mg6.android.maps.extensions.Marker;
import pl.mg6.android.maps.extensions.MarkerOptions;
import pl.mg6.android.maps.extensions.SupportMapFragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.jjoe64.graphview.CustomDraw;
import com.jjoe64.graphview.CustomLabelFormatter;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.jjoe64.graphview.TouchEvent;
import com.luttu.AppPrefes;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.entities.CarData;
import com.openvehicles.OVMS.ui.GetMapDetails.afterasytask;
//import com.openvehicles.OVMS.ui.Settings.Updateclust;
import com.openvehicles.OVMS.ui.Settings.UpdateclustLoggedPath;
import com.openvehicles.OVMS.ui.utils.Database;
import com.openvehicles.OVMS.ui.utils.DemoClusterOptionsProvider;
import com.openvehicles.OVMS.ui.utils.MarkerGenerator;
import com.openvehicles.OVMS.ui.utils.Ui;
import com.openvehicles.OVMS.utils.MyAlertDialog;
import com.openvehicles.OVMS.utils.MyDate;
import com.openvehicles.OVMS.utils.MyElement;
import com.openvehicles.OVMS.utils.MyException;
import com.openvehicles.OVMS.utils.MyTimer;
import com.openvehicles.OVMS.utils.MyTimerTimeout;
import com.openvehicles.OVMS.utils.OnClickPositiveButton;

public class FragMap extends BaseFragment implements OnInfoWindowClickListener,
		afterasytask, OnClickListener, Settings.UpdateMap
		, UpdateclustLoggedPath
		, pl.mg6.android.maps.extensions.GoogleMap.OnMapClickListener
		, pl.mg6.android.maps.extensions.GoogleMap.OnMyLocationButtonClickListener {
	private static final String TAG = "FragMap";

	private String mstrError;
	private static GoogleMap map;//http://developer.android.com/reference/com/google/android/gms/maps/GoogleMap.html
	Database database;
	String slat, slng;
	AppPrefes appPrefes;

	static boolean flag = true;
	private static final double[] CLUSTER_SIZES = new double[] { 360, 180, 90, 45, 22 };
	View rootView;
	Menu optionsMenu;
	private ViewGroup mcontainer;
	boolean autotrack = true;
	static Settings.UpdateMap updateMap;

	private CarData mCarData;
	static double lat = 0, lng = 0;
	static int maxrange = 160;
	static String distance_units = "KM";

	protected static UpdateclustLoggedPath updateclustLoggedPath;
	private static MyTimer mmyTimer;
	private enum Retrieve_24_Hour_Log_Data_State {
		NoRetrieve// = 0;
		, Retrieving// = 1;
		, LastPoint// = 2;
		, Error// = 3;
		}
	private static Retrieve_24_Hour_Log_Data_State mRetrieve_24_Hour_Log_Data_State = Retrieve_24_Hour_Log_Data_State.NoRetrieve;
	
	public void onServerSocketError(String strError)
	{
		mstrError = strError;
		switch(mRetrieve_24_Hour_Log_Data_State)
		{
		case Retrieving:
			mRetrieve_24_Hour_Log_Data_State = Retrieve_24_Hour_Log_Data_State.Error;
		case Error:
			lastPoint();
			break;
		case NoRetrieve:
		case LastPoint:
			break;
		}
	}
	
	public void onLoginComplete()
	{
		switch(mRetrieve_24_Hour_Log_Data_State)
		{
		case Error://Retrieve of 24 hour log data failed.
        	new MyAlertDialog(getActivity()
        			, String.format(getString(R.string.Retrieve_24_Hour_Log_Data_again, mstrError))//Retrieve the logged path failed!\n\n%s\n\nProbably connecting to OVMS server is unstable.\n\nTry to retrieve again?
        			, new OnClickPositiveButton()
            	{
	            @Override
	            public void onClick()
	            	{//Try to retrieve of 24 hour log data again
		    			HideLoggedPath();
		    			ShowLoggedPath();
	            	}
            	}
        	);
			break;
		case Retrieving:
		case NoRetrieve:
		case LastPoint:
			break;
		}
	}
	
	class GraphViewData implements GraphViewDataInterface
	{
		public GraphViewData(MyDate date, LatLng latLng)
		{
			mlatLng = latLng;
			
			if(date == null)
				return;//Unknown timestamp of the current car location
			
			GraphViewDataInterface[] graphViewDataInterface = FragMap.mloggedPath.mgraphViewSeries.getGraphViewDataInterface();
			int nIndex = graphViewDataInterface.length;
			if(nIndex >= 1)
			{//not first point 
				Date datePrev = getPrevPointWithDateNotNull().getDate();//Timestamp of the previous point of the logged path
				if(datePrev == null)
					throw new MyException(MyElement.getName() + " datePrev == null");
				if(date.getTime() <= datePrev.getTime())
				{
	//				throw new MyException(MyElement.getName() + " current timestamp = " + date.toString() + " <= previous timestamp = " + datePrev.toString());
					Log.e("MyLine", MyElement.getName() + " current timestamp = " + date.toString() + " <= previous timestamp = " + datePrev.toString());
					return;
				}
			}
			mdate = date;
		}
		
		//X - time
		private MyDate mdate;//Timestamp of the current point of the logged path
		private MyDate getDate()
		{ return mdate;}
		
		// Sometimes mdate == null because timestamp is incorrect for current car location.
		// I am waiting from Lee new version of the "Car location message 0x4C "L"" command with timestamp of the car location. See:
		//
		//My plan for near future.
		//From:	Andrej Hristoliubov <anhr@mail.ru> 
		//To:	Lee Howard
		//19 February 2015, 7:37
		//
		// email for details
		//
		// I find a previous path point with date not equal null
		private GraphViewData getPrevPointWithDateNotNull()
		{
			GraphViewDataInterface[] graphViewDataInterface = FragMap.mloggedPath.mgraphViewSeries.getGraphViewDataInterface();
			for(int nIndex = graphViewDataInterface.length - 1; nIndex >= 0; nIndex--)
			{
				GraphViewData graphViewData = ((GraphViewData)(graphViewDataInterface[nIndex])); 
				if(graphViewData.getDate() != null)
					return graphViewData; 
			}
			throw new MyException(MyElement.getName() + " failed!");
		}
		
		public double getX()
		{
			if(mdate != null)
				return mdate.getTime();
			
			return getPrevPointWithDateNotNull().getDate().getTime();
		}
		public long//time between point and current point of the logged path in milliseconds. 
			getTimeToPoint(
					long ltime//time of point
				)
		{
			if(mdate == null)
				throw new MyException(MyElement.getName() + " mdate == null");
			return mdate.getTime() - ltime;
		}
		
		//Y - speed
		private LatLng mlatLng;
		private double mdDistance;//Distance between start point and current point of the logged path.
		public LatLng getLatLng()
		{ return mlatLng;}
		
		private double//speed in km or miles per hour
			getSpeed(
					GraphViewData point//previous car location
				)
		{
			if(point.getDate() == null)
			{
				Log.e(TAG, MyElement.getName() + " point.getDate() == null");
				return 0d;
			}
			if(mdate == null)
			{
//				throw new MyException(MyElement.getName() + " mdate == null");
				Log.e(TAG, MyElement.getName() + " mdate == null");
				return 0d;
			}
			long ltime = mdate.getTime() - point.getDate().getTime();//milliseconds
			if(ltime == 0)
				throw new MyException(MyElement.getName() + " time == 0. nIndex = " + getIndex());
			if(ltime < 0)
				ltime = - ltime;
			double time = ((double)ltime)/(1000d * 60d * 60d);//hours
			
			double dspeed = (GetDistance(point.mlatLng, mlatLng) / time);//km or miles per hour 
			//Log.d("MyLine", MyElement.getName() + " ddistance = " + ddistance + ", time = " + time + ", speed = " + dspeed);
			//		+ ", g.s12 = " + g.s12 + ", mdate.getTime() = " + mdate.getTime() + ", mdatePrev.getTime() = " + mdatePrev.getTime());
			return dspeed;
		}
		
		private double//speed in km per hour
			getSpeedKmPerHour(
					GraphViewData point//previous car location
				)
		{
			return getSpeed(point) / (carDistanceUnitsIsMiles() ? mmilesInKm : 1);
		}
		
		private int getIndex()
		{
			GraphViewDataInterface[] graphViewDataInterface = FragMap.mloggedPath.mgraphViewSeries.getGraphViewDataInterface();
			for(int nIndex = 0; nIndex < graphViewDataInterface.length; nIndex++)
			{
				if(graphViewDataInterface[nIndex].equals(this))
					return nIndex;
			}
			throw new MyException(MyElement.getName() + " failed!");
//			return -1;//error
		}
			
		public double getY()
		{
			try
			{
				GraphViewDataInterface[] graphViewDataInterface = FragMap.mloggedPath.mgraphViewSeries.getGraphViewDataInterface();
				int nIndex = getIndex();
				int nLoggedPathPointsCount = graphViewDataInterface.length;
				if(nIndex == 0)
				{//first point
					if(nLoggedPathPointsCount == 1)
						return 0d;
					return getSpeed((GraphViewData)(graphViewDataInterface[nIndex + 1]));
				}
				if(nIndex == (nLoggedPathPointsCount - 1))
				{//last point
					return getSpeed((GraphViewData)(graphViewDataInterface[nIndex - 1]));
				}
				return (
							getSpeed((GraphViewData)(graphViewDataInterface[nIndex + 1]))
							+ 
							getSpeed((GraphViewData)(graphViewDataInterface[nIndex - 1]))
						)
						/ 2;
			}
			catch (MyException e)
			{
				Log.e(TAG, MyElement.getName() + " " + e);
			}
			return 0d;//error
		}
		public double//Distance between first and current point of the logged path
			setDistance
				(
					double dDistance//Distance between previous and current point of the logged path
				)
		{
			int nIndex = getIndex();
			if(nIndex > 0)
			{//not first point
				mdDistance = dDistance + GetDistance
					(
							mlatLng
						, ((GraphViewData)(FragMap.mloggedPath.mgraphViewSeries.getGraphViewDataInterface()[nIndex - 1])).mlatLng
					);
			}
			return mdDistance;
		}
		
		public double//Distance between start point and current point of the logged path. 
			getDistanceFromStart()
		{ return mdDistance;}
		
		private double//Distance between two map locations in meters
			GetDistanceInMeters
				(
					LatLng latLngFirst//first map location
					, LatLng latLngSecond//second map location
				)
		{
			//http://geographiclib.sourceforge.net/html/java/
			GeodesicData g;
			g = Geodesic.WGS84.Inverse(latLngFirst.latitude, latLngFirst.longitude, latLngSecond.latitude, latLngSecond.longitude);
			return g.s12;//meters
		}
		
		final double mmilesInKm = 0.621371192;
		
		boolean carDistanceUnitsIsMiles()
		{
			//I can not use the getService().getLogginedCarData() function because it returns null if Internet connection was broken
			//return getService().getLogginedCarData().car_distance_units == "m";
			return getService().getCarData().car_distance_units == "m";
		}
		
		private double//Distance between two map locations in current units
			GetDistance
				(
					LatLng latLngFirst//first map location
					, LatLng latLngSecond//second map location
				)
		{
			double ddistance = GetDistanceInMeters(latLngFirst, latLngSecond);
			
			ddistance /= 1000;//kilometers
			 
			//Units convertor
			try
			{
				if(carDistanceUnitsIsMiles())
				{//miles
					ddistance = mmilesInKm * ddistance;
				}
			}
			catch (Exception e)
			{
				Log.e("MyLine", MyElement.getName() + " " + e.getMessage());
			}
			return ddistance;
		}
		
		private double//Distance between pointer on the map locations and current pointer of the logged path 
			GetDistance
				(
					LatLng point//pointer on the map location
				)
		{
			return GetDistanceInMeters(point, mlatLng);
		}
	}//class GraphViewData
	
	class LoggedPath
	{
		private LoggedPath()
		{
			mgraphViewSeries = new MyGraphViewSeries(new GraphViewData[] {});
			mnMapPointerIndex = -1;
		}
		
		public void MyDraw(Canvas canvas, Paint paint, float graphheight, float border)
		{
			if(mnMapPointerIndex < 0)
				return;
			
			// draw data
			paint.setStrokeWidth(1f);
			paint.setColor(Color.YELLOW);
			
			//draw vertical line 
			canvas.drawLine(mfSelectedPointX, border, mfSelectedPointX, border + graphheight, paint);
		}
		
		private boolean addPolyline()
		{
			if(mPolylineOptionsTrack == null)
				return false;
			
			FragMap.map.addPolyline(mPolylineOptionsTrack);
			return true;
		}
		private PolylineOptions getPolylineOptionsTrack()
		{
			if(mPolylineOptionsTrack == null)
			{//first point
				mPolylineOptionsTrack = new PolylineOptions().width(3).color(Color.BLUE);
				
				mnIncorrectLocationsCount = 0;
			}
	        return mPolylineOptionsTrack;
		}
		private List<LatLng> getPoints()
		{
	        return getPolylineOptionsTrack().getPoints();
		}
		private void appendData(GraphViewData graphViewData)
		{
			LatLng latLng = graphViewData.getLatLng();
			
			//test of time
			
			MyDate date = graphViewData.getDate();
			// Sometimes date == null because timestamp is incorrect for current car location.
			// I am waiting from Lee new version of the "Car location message 0x4C "L"" command with timestamp of the car location. See:
			//
			//My plan for near future.
			//From:	Andrej Hristoliubov <anhr@mail.ru> 
			//To:	Lee Howard
			//19 February 2015, 7:37
			//
			// email for details
			
			GraphViewDataInterface[] graphViewDataInterface = FragMap.mloggedPath.mgraphViewSeries.getGraphViewDataInterface();
			if(graphViewDataInterface.length > 0)
			{//not first point
				GraphViewData graphViewDataPrev = ((GraphViewData)graphViewDataInterface[graphViewDataInterface.length - 1]);
				Date datePrev = graphViewDataPrev.getDate();//Timestamp of the previous point of the logged path
				
				if(date != null)
				{
					if((datePrev != null) && (date.getTime() <= datePrev.getTime()))
						throw new MyException(MyElement.getName() + " current timestamp = " + graphViewData.getDate().getTime() + " <= previous timestamp = " + datePrev.getTime());
					double dspeed = graphViewData.getSpeedKmPerHour(graphViewDataPrev);
					if(dspeed > 300)
					{
						Log.e(TAG, MyElement.getName() 
							+ "The vehicle speed " + dspeed + " is more than 300 kilometers per hour. I think, it is not possible. I discarded this logged path point");
						return;
					}
				}
			}
			
			Log.d(TAG, MyElement.getName() + " latitude = "  + latLng.latitude + ", longitude = " + latLng.longitude + ", " + ((date == null) ? "" : new MyDate(date).toString()));
			getPolylineOptionsTrack().add(latLng);
			
	    	mgraphViewSeries.appendData(graphViewData);
	    	if(mDateStart == null)
	    		mDateStart = new MyDate(date);//first point
			
	    	mdDistance = graphViewData.setDistance(mdDistance);
	    	setGraphViewTitle();
		}
		
		private void setGraphViewTitle()
		{
		    if(mgraphView == null)
		    	return;
		    if(mgraphViewSeries.getGraphViewDataInterface().length <= 0)
		    	return;
		    int nLoggedPathPointerIndex = mgraphViewSeries.getGraphViewDataInterface().length - 1;
		    MyDate time = null;
		    if(((GraphViewData)mgraphViewSeries.getGraphViewDataInterface()[nLoggedPathPointerIndex]).getDate() != null)
		    	time = new MyDate(mgraphViewSeries.getLoggedPathPointerTime(nLoggedPathPointerIndex, mDateStart.getTime()));
	    	if((getActivity() == null) || (((MainActivity)getActivity()).getService() == null))
	    		throw new MyException(MyElement.getName() + " (getActivity() == null) || (((MainActivity)getActivity()).getService() == null)");
		    mgraphView.setTitle(getActivity().getString(R.string.Speed)
			      	+ " (" + ((MainActivity)getActivity()).getService().getCarData().car_speed_units + ")."
			      	
			      	//Distance
            		+ String.format(" %.2f", mdDistance)
        			+ " " + ((MainActivity)getActivity()).getService().getCarData().car_distance_units + "."
        			
        			//Time
        			+ " " +
        				(
        					(time == null) ?
        						getString(R.string.Under_constraction)
        						: (time.getMyHours() + ":" + time.getMinutes() + ":" + time.getSeconds())
        				)
 
		    	);
		}
		
	    /**
	     * Sets the marker on the map with information about current point on the drawn path
	     */
		private void SetLocationMarker
			(
				int nMapPointerIndex//mPolylineOptionsTrack index. Index of the current point on the drawn path
			)
		{
			if(nMapPointerIndex >= mgraphViewSeries.getGraphViewDataInterface().length)
				return;//Next point impossible
		    if(mnMapPointerIndex == nMapPointerIndex)
		    	return;
		    mnMapPointerIndex = nMapPointerIndex;
		    
		    boolean boEnable;
		    
		    //Enable of next location button
		    if(mnMapPointerIndex >= (mgraphViewSeries.getGraphViewDataInterface().length - 1))
		    	boEnable = false;
		    else boEnable = true;
		    rootView.findViewById(R.id.buttonNexpLocation).setEnabled(boEnable);
		    
		    //Enable of previous location button
		    if(mnMapPointerIndex <= 0)
		    	boEnable = false;
		    else boEnable = true;
		    rootView.findViewById(R.id.ButtonPrevLocation).setEnabled(boEnable);
		    
		    if(mFragMap == null)
		    	throw new MyException(MyElement.getName() + " mFragMap == null");
		    mFragMap.mgraphView.redrawAll();
		    
		    LatLng latLng = mPolylineOptionsTrack.getPoints().get(nMapPointerIndex);
		    
		    if(mmarker != null)
		    	mmarker.remove();
		    
		    GraphViewData graphViewData = ((GraphViewData)mgraphViewSeries.getGraphViewDataInterface()[nMapPointerIndex]);
		    
//////////////////////////////////////////////////////////////
//For debugging
/*		    
MyDate date1 = new MyDate(
		2014//int year
		, 10//int month
		, 25//int day
		, 4//int hour
		, 36//int minute
		, 45//int second
		);
MyDate date2 = new MyDate(
		2014 + 1//int year
		, 10 + 2//int month
		, 25 + 3//int day
		, 4 + 4//int hour
		, 36 + 5//int minute
		, 45 + 6//int second
		);
MyDate time = new MyDate(date2.getTime() - date1.getTime());
//int hours = time.getHours();
*/
//Time time = new Time(4, 3, 2);//4:03:02
/*
Calendar calendar = new Calendar();
calendar.get(Calendar.HOUR_OF_DAY);
*/
//////////////////////////////////////////////////////////////
		    
		    MyDate date = graphViewData.getDate();
		    MyDate time = null;
		    String strSpeed = "";
		    if(date != null)
		    {
		    	time = new MyDate(mgraphViewSeries.getLoggedPathPointerTime(nMapPointerIndex, mDateStart.getTime()));
		    	strSpeed = String.format("%.1f " + ((MainActivity)getActivity()).getService().getLogginedCarData().car_speed_units + "."//http://developer.android.com/reference/java/util/Formatter.html
        				, graphViewData.getY());
		    }
		    else
		    	strSpeed = getString(R.string.Under_constraction);
		    
		    mmarker = FragMap.map.addMarker(new MarkerOptions()
            .position(latLng)
            .title(nMapPointerIndex + ". " + ((date == null) ? getString(R.string.Under_constraction) : date.toString()))
            .snippet
            	(
            		//Distance
            		String.format("%s: %.2f"//http://developer.android.com/reference/java/util/Formatter.html
            				, getActivity().getString(R.string.Distance)
            				, mgraphViewSeries.getLoggedPathPointerDistance(nMapPointerIndex)
            			)
        			+ " " + ((MainActivity)getActivity()).getService().getLogginedCarData().car_distance_units + "."
        			
        			//Time
        			+ "\n" + getActivity().getString(R.string.TimeFromStart) + ": " + 
        				(
        					(time == null) ?
        						getString(R.string.Under_constraction)
        						: time.getMyHours() + ":" + time.getMinutes() + ":" + time.getSeconds()
        				)
        			
            		//speed
        			+ "\n" + getActivity().getString(R.string.Speed) + ": " + strSpeed 
           			
            		//latitude
            		+ String.format("\n%s: %f"//http://developer.android.com/reference/java/util/Formatter.html
            				, getActivity().getString(R.string.Latitude)
            				, latLng.latitude
            			)
           			+ " " + getActivity().getString(R.string.Degree) + "."
           			
            		//longitude
            		+ String.format("\n%s: %f"//http://developer.android.com/reference/java/util/Formatter.html
            				, getActivity().getString(R.string.Longitude)
            				, latLng.longitude
            			)
           			+ " " + getActivity().getString(R.string.Degree) + "."
            	)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.m_path_location))
            .infoWindowAnchor(0.5f, 0.5f));
	    	mmarker.showInfoWindow();
	    	
			map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
		}
		
		// Called when user clicks the Next button in the FragMap fragment
		public void NexpLocation()
		{
			if(mPolylineOptionsTrack == null)
				return;//Logged path is empty
			if(mnMapPointerIndex < 0)
				mnMapPointerIndex = 0;//Map pointer was not selected before. Selects the first pointer of the logged path
			if(mnMapPointerIndex >= (mPolylineOptionsTrack.getPoints().size() - 1))
				return;
			SetLocationMarker(mnMapPointerIndex + 1);
		    if(mFragMap == null)
		    	throw new MyException(MyElement.getName() + " mFragMap == null");
		    mFragMap.mgraphView.getSelectedPointX();  
		}
		
		// Called when user clicks the Previous button in the FragMap fragment
		public void PrevLocation()
		{
			if(mnMapPointerIndex < 1)
				return;
			SetLocationMarker(mnMapPointerIndex -1);
		    if(mFragMap == null)
		    	throw new MyException(MyElement.getName() + " mFragMap == null");
		    mFragMap.mgraphView.getSelectedPointX();  
		}
		private void Clear()
		{
			List<Polyline> listPolylines = FragMap.map.getPolylines();
			for(int location = 0; location < listPolylines.size(); location++)
			{
				Polyline polyline = listPolylines.get(location);
				polyline.remove();
			}
			if(mPolylineOptionsTrack != null)
			{
				List<LatLng> listLatLng = mPolylineOptionsTrack.getPoints();
				listLatLng.clear();
				mPolylineOptionsTrack = null;
			}
			
			if(mgraphViewSeries != null)
			{
				mgraphViewSeries.resetData(new GraphViewData[] {});
				mgraphViewSeries = null;
				mgraphView.clear();
				mgraphView = null;
			}
		}
		
		//http://jjoe64.github.io/GraphView/
		class MyGraphViewSeries extends GraphViewSeries
		{
			public MyGraphViewSeries(GraphViewDataInterface[] values) {
				super(values);
			}
			public double getLoggedPathPointerDistance(int nLoggedPathPointerIndex)
			{
			    return ((GraphViewData)getGraphViewDataInterface()[nLoggedPathPointerIndex]).getDistanceFromStart();
			}
			public long getLoggedPathPointerTime(int nLoggedPathPointerIndex, long ltime)
			{
			    return ((GraphViewData)getGraphViewDataInterface()[nLoggedPathPointerIndex]).getTimeToPoint(ltime);
			}
			public int//returns index of nearest pointer of the logged path
				onMapClick(
						LatLng point//User touched this point on the map
					) 
			{
				double dMinDistance = -1d;
				int nMapPointerIndex = -1;

				GraphViewDataInterface[] values = getGraphViewDataInterface();
				for (int i = 0; i < values.length; i++)
				{
					GraphViewData graphViewData = (GraphViewData)values[i];
					double ddistance = graphViewData.GetDistance(point);
					if((dMinDistance == -1) || (dMinDistance > ddistance))
					{
						dMinDistance = ddistance;
						nMapPointerIndex = i;
					}
				}//for (int i = 0; i < values.length; i++)
				if(dMinDistance == -1d)
					throw new MyException(MyElement.getName() + " index of nearest pointer of the logged path was not found");
				
				double dMapClickMaxDistance = getMapClickMaxDistance();
				
				/////////////////////////////////////
				//For debugging
				/*
				CircleOptions options = new CircleOptions().strokeWidth(2)//strokeWidth)
				.strokeColor(Color.GREEN);
				map.addCircle(options.center(point)
				.radius(dMapClickMaxDistance));
				*/
				/////////////////////////////////////

				if(dMinDistance > dMapClickMaxDistance)
					return -1;//I displays an information about point on the drawn path only if distance between logged path pointer and click of the user in the map is less mdMapClickMaxDistance
				return nMapPointerIndex;
			}
			
			//Maximal distance between logged path pointer and click of the user in the map (user's touch) in meters.
			//I displays an information about point on the drawn path only if distance between logged path pointer and click of the user in the map is less getMapClickMaxDistance()
			public double getMapClickMaxDistance()
			{
				float fzoom = FragMap.map.getCameraPosition().zoom;
				/////////////////////////////////////
				//For debugging
/*				
				//http://econom.misis.ru/s/Hel/Matem/Para_3t.htm
				double x1 = 21;        double y1 = 1;
				double x2 = 18;        double y2 = 10;
				double x3 = 12.441224; double y3 = 300;
//				double x3 = 2;         double y3 = 400000;
				double a = (y3 - ((x3*(y2-y1)+x2*y1-x1*y2)/(x2-x1)))/(x3*(x3-x1-x2)+x1*x2);
				double b = (y2-y1)/(x2-x1)-a*(x1+x2);
				double c = ((x2*y1-x1*y2)/(x2-x1)) + a*x1*x2;
*/				
/*				//first series
				if(fzoom == 21)
					return 1;
				if(fzoom == 20)
					return 2;
				if(fzoom == 19)
					return 4;
				if(fzoom == 18)
					return 10;
				if(fzoom == 17)
					return 20;
				
				//second series
				if(fzoom == 11)
					return 1000;
				if(fzoom == 10)
					return 2000;
				if(fzoom == 9)
					return 4000;
				if(fzoom == 8)
					return 8000;
				if(fzoom == 7)
					return 16000;
				return 400000;
*/				
				/////////////////////////////////////
/*				
				//zoom = 13.14 distance = 300
				//zoom = 18    distance = 10
				double dMapClickMaxDistance = -59.67 * fzoom + 1084;
*/
				
				//http://mathhelpplanet.com/static.php?p=onlayn-mnk-i-regressionniy-analiz
				double a;
				double b;
				double c;
				double d;
				if(fzoom > 11)
				{
					//X: 12.44 17 18 19 20 21
					//Y: 300 20 10 4 2 1
					a = -0.748;
					b = 44.2882;
					c = -874.9445;
					d = 5770.5247;
				}
				else
				{
					//X: 7 8 9 10 11
					//Y: 16000 8000 4000 2000 1000
					a = -250;
					b = 7892.8571;
					c = -84071.4286;
					d = 303485.7143;
				}
				double dMapClickMaxDistance = a * fzoom * fzoom * fzoom + b * fzoom * fzoom + c * fzoom + d;
				if(dMapClickMaxDistance < 1)
				{
					Log.e(TAG, MyElement.getName() + " dMapClickMaxDistance < 1");
					dMapClickMaxDistance = 1;
				}
				return dMapClickMaxDistance;
			}
			public void appendData(GraphViewData value) {
				///////////////////////////////////////////////////////
				//for debugging
				/*
				if(getGraphViewDataInterface().length > 2)
					return;
				*/
				///////////////////////////////////////////////////////
				appendData(value, false, getGraphViewDataInterface().length + 1);
			}
		}
		
		private MyGraphViewSeries mgraphViewSeries;
		
		public void onMapClick(LatLng point) 
		{
			int nMapPointerIndex = mgraphViewSeries.onMapClick(point);
			if(nMapPointerIndex < 0)
				return;//index of nearest pointer of the logged path was not found
			
			SetLocationMarker(nMapPointerIndex);
			mnMapPointerIndex = nMapPointerIndex;
		    if(mFragMap == null)
		    	throw new MyException(MyElement.getName() + " mFragMap == null");
		    mFragMap.mgraphView.getSelectedPointX();  
		}
		
		private Marker mmarker;
		private int mnMapPointerIndex;
		private float mfSelectedPointX;
		private PolylineOptions mPolylineOptionsTrack;
		private int mnIncorrectLocationsCount;//for debugging
		private double mdDistance;//Distance between start point and end point of the logged path.
		private MyDate mDateStart;//time of start (of the first point) of the logged path
		private FragMap mFragMap;
		public double//Maximal distance between logged path pointer and click of the user in the map (user's touch) in meters.
			getMapClickMaxDistance()
		{ return mgraphViewSeries.getMapClickMaxDistance();}
		
	}
	private static LoggedPath mloggedPath;
	
	class MyLineGraphView extends LineGraphView//https://github.com/jjoe64/GraphView
	{
		public MyLineGraphView(Context context)
		{
			super(context, getString(R.string.Speed)
			      	+ " (" + ((MainActivity)getActivity()).getService().getCarData().car_speed_units + ")");
			
			//GraphView font size
			//http://developer.android.com/reference/android/util/DisplayMetrics.html
			DisplayMetrics metrics = new DisplayMetrics();
			getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
			getGraphViewStyle().setTextSize
				(
					10f
					* metrics.density
					* android.provider.Settings.System.getFloat//get font scale
					(//User can change font scale from System Settings/Display/Font Size
							getActivity().getContentResolver()
							, android.provider.Settings.System.FONT_SCALE
							, 1f
					)
				);
			
			setCustomLabelFormatter(new CustomLabelFormatter()
			{
				public String formatLabel(double value, boolean isValueX)
				{
					if (isValueX)
					{
						Date date = new Date((long)value);
						return String.format("%d:%d", date.getHours(), date.getMinutes());
					}
					return null; // let graphview generate Y-axis label for us
				}
			});
			
			setTouchEvent(new TouchEvent()
			{
			     // To handle GraphView touch screen motion events.
			     //
			     // @param event The motion event.
			     // @return True if the event was handled, false otherwise.
				public boolean onTouchEvent(MotionEvent event) {
					
					//http://startandroid.ru/ru/uroki/vse-uroki-spiskom/167-urok-102-touch-obrabotka-kasanija.html
					
					float x;
					x = event.getX();
					FragMap.mloggedPath.SetLocationMarker(getMapPointerIndex(FragMap.mloggedPath.mgraphViewSeries.getGraphViewDataInterface(), x));
				    return false;
				}
			});
			
			setCustomDraw(new CustomDraw()
			{
				public void MyDraw(Canvas canvas, Paint paint, float graphheight, float border)
				{
					FragMap.mloggedPath.MyDraw(canvas, paint, graphheight, border);
				}
			});
			
			setDrawDataPoints(true);
			setDataPointsRadius(5f);
			addSeries(FragMap.mloggedPath.mgraphViewSeries);
			enableDisableOfNextAndPreviousLocationButtonsOfTheGraph();
			((LinearLayout) rootView.findViewById(R.id.graph)).addView(this);
			
		}
		
		private void clear()
		{
			((LinearLayout) rootView.findViewById(R.id.graph)).removeAllViews();
		}
		
		public int//index of the map pointer, what was touched by the user on the graph
			getMapPointerIndex( 
				GraphViewDataInterface[] values//array of the graph coodrinates
				, float fTouchEventX//The X coordinate of the graph in pixels, what was touched by the user 
				)
		{
			float graphwidth = getGraphWidth();
			double minX = getMinX(false);
			double diffX = getMaxX(false) - minX;
			float horstart = 0f;
			
			// draw background
			double lastEndX = 0;
			lastEndX = 0;
			for (int i = 0; i < values.length; i++)
			{
				double valX = values[i].getX() - minX;
				double ratX = valX / diffX;
				double x = graphwidth * ratX;

				if (i > 0) {
					float startX = (float) lastEndX + (horstart + 1);
					float endX = (float) x + (horstart + 1);
					if((fTouchEventX >= startX) && (fTouchEventX < endX))
					{
						if(((startX + endX) / 2) > fTouchEventX)
						{
							FragMap.mloggedPath.mfSelectedPointX = startX; 
							return i - 1;
						}
						FragMap.mloggedPath.mfSelectedPointX = endX; 
						return i;
					}
				}
				lastEndX = x;
			}//for (int i = 0; i < values.length; i++)
	    	throw new MyException(MyElement.getName() + " failed!");
		}
		
		public void getSelectedPointX()
		{
			GraphViewDataInterface[] values = FragMap.mloggedPath.mgraphViewSeries.getGraphViewDataInterface();
			int i = FragMap.mloggedPath.mnMapPointerIndex;
			float graphwidth = getGraphWidth();
			double minX = getMinX(false);
			double diffX = getMaxX(false) - minX;
			float horstart = 0f;
			
			// draw background
			double lastEndX = 0;
			
			double valX = values[i].getX() - minX;
			double ratX = valX / diffX;
			double x = graphwidth * ratX;
			lastEndX = x;

			float startX = (float) lastEndX + (horstart + 1);
			FragMap.mloggedPath.mfSelectedPointX = startX; 
		}
	}
	private MyLineGraphView mgraphView;//https://github.com/jjoe64/GraphView

	public interface UpdateLocation {
		public void updatelocation();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		mcontainer = container;
		
		mmyTimer = new MyTimer(getActivity());
		mpd = null;
		
		rootView = inflater.inflate(R.layout.mmap, null);
		appPrefes = new AppPrefes(getActivity(), "ovms");
		updateMap = this;

		lat = 37.410866;
		lng = -122.001946;
		maxrange = 285;
		distance_units = "KM";

		updateclustLoggedPath = this;
		
		database = new Database(getActivity());

		FragmentManager fm = getActivity().getSupportFragmentManager();
		SupportMapFragment f = (SupportMapFragment) fm
				.findFragmentById(R.id.mmap);
		map = f.getExtendedMap();
		map.setClustering(new ClusteringSettings().clusterOptionsProvider(
				new DemoClusterOptionsProvider(getResources()))
				.addMarkersDynamically(true));
		map.setOnInfoWindowClickListener(this);
    	map.getUiSettings().setRotateGesturesEnabled(false); // Disable two-finger rotation gesture
        map.setMyLocationEnabled(true);
        map.setOnMyLocationButtonClickListener(this);
		map.moveCamera(CameraUpdateFactory.zoomTo(15));
        map.setOnMapClickListener(this);
		map.setOnMyLocationButtonClickListener(this);
        
        //I see
        //
        //The method setOnMapLoadedCallback(FragMap) is undefined for the type GoogleMap
        //
		// It is not possible because current GoogleMap version is not support it.
		// I do not see same problem on maps application. I do not know how to change GoogleMap version.
        //
        //map.setOnMapLoadedCallback(this);
        
        //map marker
        //http://stackoverflow.com/questions/15783227/aligning-the-text-in-google-maps-marker-snippet
        map.setInfoWindowAdapter(new InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                View v = getActivity().getLayoutInflater().inflate(R.layout.marker, mcontainer, false);
                
                TextView title= (TextView) v.findViewById(R.id.title);
                title.setText(marker.getTitle());

                TextView info= (TextView) v.findViewById(R.id.info);
                info.setText(marker.getSnippet());

                return v;
            }
        });
        
		// setUpClusteringViews();

		setHasOptionsMenu(true);
		flag = true;

		// get config:
		autotrack = !appPrefes.getData("autotrack").equals("off");

		return rootView;
	}

	// Called when the user clicks the Next button in the FragMap fragment
	public void NexpLocation()
	{
		mloggedPath.NexpLocation();
	}
		
	// Called when the user clicks the Previous button in the FragMap fragment
	public void PrevLocation()
	{
		mloggedPath.PrevLocation();
	}
	
    @Override
    public void onStart() {
        super.onStart();
		
		((MainActivity)getActivity()).setSupportProgressBarIndeterminateVisibility(false);
		
		if(appPrefes.IsTrue(BaseApi.mKeyTurnLoggedPath, false))//"TurnLoggedPath"
			ShowLoggedPath();
		else
		{
			HideLoggedPath();
		}
    }
    
	void updateClusteringLoggedPath(int clusterSizeIndex, boolean enabled) {
		HideLoggedPath();
		ShowLoggedPath();
	}
	
	@Override
	public void updateClustering(int clusterSizeIndex, boolean enabled) {
		ClusteringSettings clusteringSettings = new ClusteringSettings();
		clusteringSettings.addMarkersDynamically(true);
		if (enabled) {
			after(false);
			clusteringSettings
					.clusterOptionsProvider(new DemoClusterOptionsProvider(
							getResources()));

			double clusterSize = CLUSTER_SIZES[clusterSizeIndex];
			clusteringSettings.clusterSize(clusterSize);
		} else {
			lis = map.getMarkers();
			for (int i = 0; i < lis.size(); i++) {
				Marker carmarker = lis.get(i);
				int j = carmarker.getClusterGroup();
				if (j == -1) {
					lis.remove(i);
				} else {
					carmarker.remove();
				}
			}
		}
		map.setClustering(clusteringSettings);
	}


	List<Marker> lis;

	private void replacefragment(Fragment frag) {
		// TODO Auto-generated method stub
		FragmentTransaction ft = getActivity().getSupportFragmentManager()
				.beginTransaction();
		ft.replace(R.id.fm, frag);
		ft.commit();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

		inflater.inflate(R.menu.map_options, menu);

		optionsMenu = menu;

		// set checkboxes:
		optionsMenu.findItem(R.id.mi_map_autotrack)
				.setChecked(autotrack);
		optionsMenu.findItem(R.id.mi_map_filter_connections)
				.setChecked(appPrefes.getData("filter").equals("on"));
		optionsMenu.findItem(R.id.mi_map_filter_range)
				.setChecked(appPrefes.getData("inrange").equals("on"));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int menuId = item.getItemId();
		boolean newState = !item.isChecked();

		switch(menuId) {

			case R.id.mi_map_autotrack:
				appPrefes.SaveData("autotrack", newState ? "on" : "off");
				item.setChecked(newState);
				autotrack = newState;
				if (autotrack)
					update();
				break;

			case R.id.mi_map_filter_connections:
				appPrefes.SaveData("filter", newState ? "on" : "off");
				item.setChecked(newState);
				after(false);
				break;

			case R.id.mi_map_filter_range:
				appPrefes.SaveData("inrange", newState ? "on" : "off");
				item.setChecked(newState);
				after(false);
				break;

			case R.id.mi_map_settings:
				Bundle args = new Bundle();
				BaseFragmentActivity.show(getActivity(), Settings.class, args,
						Configuration.ORIENTATION_UNDEFINED);
				break;
		}

		return false;
	}

	private void StartTrack() {
Log.d(TAG, MyElement.getName() + " enter");
Log.d(TAG, MyElement.getName() + " exit");
	}
	private void StopTrack() {
Log.d(TAG, MyElement.getName() + " enter");
Log.d(TAG, MyElement.getName() + " exit");
	}
			
	//http://startandroid.ru/en/uroki/vse-uroki-spiskom/128-urok-67-dialogi-progressdialog
	private ProgressDialog mpd;
	
	//Show or hide the logged path (24-hour log data)
	private void TurnLoggedPath(
				boolean boTurnLoggedPath//true  - show logged path
										//false - hide logged path
			)
	{
		if (boTurnLoggedPath) {
			ShowLoggedPath();
		} else {
			HideLoggedPath();
		}
		appPrefes.SaveBoolean(BaseApi.mKeyTurnLoggedPath, boTurnLoggedPath);
	}
	
	//for map scale
	private static LatLngBounds mlatLngBounds;
	private void MapScale()
	{
		try
		{
			map.moveCamera(CameraUpdateFactory.newLatLngBounds(mlatLngBounds, 10));
		}
		catch (Exception e)
		{
			Log.e(TAG, MyElement.getName() + " " + e);
		}
	}
	
	private void lastPoint()
	{
		((MainActivity)getActivity()).setSupportProgressBarIndeterminateVisibility(false);
		
		try
		{
			if(mpd != null)
				mpd.dismiss();//.cancel();
		}
		catch (Exception e)
		{
			Log.e(TAG, MyElement.getName() + " " + e);
		}
		cancelCommand();
		
		MapScale();
		
		AddGraphView();
		
	}
	
	private void AddGraphView()
	{
		if(mgraphView != null)
		{
			mgraphView.clear();
			mgraphView = null;
		}
		
		Activity activity = getActivity();
		mgraphView = new MyLineGraphView(activity);
		mloggedPath.setGraphViewTitle();
		mloggedPath.mFragMap = this; 
	}
	
	private boolean//true - next point of the car location inside of circle
		IsNextPointInsideCircle(
				double azimuth//azimuth from current car location to circle center (degrees)
				, LatLng latLngCurrent//Current car location
				, LatLng latLngNext//Next car location
				)
	{
		final double dCarMinTurningRadius = 5;//meters
	
		//http://geographiclib.sourceforge.net/html/java/
		//get azimuth from previous car location to current car location
		GeodesicData g;
		
		//go to circle center of the car's minimum turning
		g = Geodesic.WGS84.Direct(latLngCurrent.latitude// - latitude of point 1 (degrees).
				, latLngCurrent.longitude// - longitude of point 1 (degrees).
				, azimuth// - azimuth at point 1 (degrees).
				, dCarMinTurningRadius// - distance between point 1 and point 2 (meters); it can be negative.
		        );
		LatLng latLngCircleCenterOfCarMinTurning  = new LatLng(g.lat2, g.lon2);
		//Log.d(TAG, MyElement.getName() + " latLngCircleCenterOfCarMinTurning.latitude = " + latLngCircleCenterOfCarMinTurning.latitude + ", latLngCircleCenterOfCarMinTurning.longitude = "  + latLngCircleCenterOfCarMinTurning.longitude);
		/*		
		CircleOptions options = new CircleOptions().strokeWidth(2)//strokeWidth)
				.strokeColor(Color.GREEN);
		map.addCircle(options.center(latLngCircleCenterOfCarMinTurning)//.data("Circle center of car minimum turning")
				.radius(dCarMinTurningRadius));
		*/
		//draw radius
		//AppendNewPointToTrack(latLngCurrent);//return to point 2
		//AppendNewPointToTrack(latLngCircleCenterOfCarMinTurning);
		
		//get distance between circle center of the car's minimum turning and next car location
		// I can use map.getMyLocation().distanceBetween(startLatitude, startLongitude, endLatitude, endLongitude, results)
		g = Geodesic.WGS84.Inverse(latLngCircleCenterOfCarMinTurning.latitude, latLngCircleCenterOfCarMinTurning.longitude, latLngNext.latitude, latLngNext.longitude);
		if(g.s12 < dCarMinTurningRadius)
		{//Next car location inside of circle of the car's minimum turning
			mloggedPath.mnIncorrectLocationsCount++;
			Log.e(TAG, MyElement.getName() + " Car location inside of circle of the car's minimum turning was detected. mnIncorrectLocationsCount = " + mloggedPath.mnIncorrectLocationsCount);
			return true;
		}
		return false;
	}
	
	//Detecting of the error point of the car location
	private boolean//false - Next car location is correct
		IsGPS_Noise(
				LatLng latLngPrevious//Previous car location
				, LatLng latLngCurrent//Current car location
				, LatLng latLngNext//Next car location
				, MyDate date//Time of the car location
				)
	{
		//http://geographiclib.sourceforge.net/html/java/
		//get azimuth from previous car location to current car location
		GeodesicData g;
		g = Geodesic.WGS84.Inverse(latLngPrevious.latitude, latLngPrevious.longitude, latLngCurrent.latitude, latLngCurrent.longitude);
		if
			(
				//Test for the right turn of the car
				IsNextPointInsideCircle(
					g.azi1 + 90//azimuth from current car location to circle center (degrees)
					, latLngCurrent//Current car location
					, latLngNext//Next car location
					)
				//Test for the left turn of the car
				|| IsNextPointInsideCircle(
					g.azi1 - 90//azimuth from current car location to circle center (degrees)
					, latLngCurrent//Current car location
					, latLngNext//Next car location
					)
			)
			return true;//Next car location is incorrect
		
		return IsIntegratePointToAveragedPoint(latLngCurrent, latLngNext, date);
	}
	
	//Integrate points that are within of averaged points circle radius (meters) of each other to be one averaged point.
	private ArrayList<LatLng> mlistIntegratePoints = new ArrayList<LatLng>();//All points of the current averaged point.
											//I use it for calculating of the averaged point.
											//This list is empty if current point is correct.
											//In another words this list is empty if current point is not within of the circle
											//with center in previous point (or averaged point)
											//and averaged points circle radius
	private boolean//false - Not added of the next point to the averaged point
		IsIntegratePointToAveragedPoint(
				LatLng latLngCurrent//Current car location or averaged point
			, LatLng latLngNext//Next car location
			, MyDate date//Time of the car location
		)
	{
		GeodesicData g;
		g = Geodesic.WGS84.Inverse(latLngCurrent.latitude, latLngCurrent.longitude, latLngNext.latitude, latLngNext.longitude);
		if(g.s12 < Settings.GetAveragedPointsCircleRadius(appPrefes))//radius of circle of averaged points
		{//Next car location inside of circle of the averaged points
			mloggedPath.mnIncorrectLocationsCount++;
			Log.e(TAG, MyElement.getName() + " Car location inside of the circle of averaged points was detected. mnIncorrectLocationsCount = " + mloggedPath.mnIncorrectLocationsCount);
			if(mlistIntegratePoints.isEmpty())
				mlistIntegratePoints.add(latLngCurrent);//add first point into list of the integrate points 
			mlistIntegratePoints.add(latLngNext);
			return true;
		}
		
		//Next car location is correct
		
		if(mlistIntegratePoints.isEmpty())
			return false;
		
		//Stop adding of the new points into listIntegratePoints. Calculating of the averaged point
		double dLatitude = 0;
		double dLongitude = 0;
		int nIntegratePointsSize = mlistIntegratePoints.size();
		for(int i = 0; i < nIntegratePointsSize; i++)
		{
			LatLng latLngIndex = mlistIntegratePoints.get(i);
			dLatitude += latLngIndex.latitude;  
			dLongitude += latLngIndex.longitude;  
		}
		mlistIntegratePoints.clear();
		LatLng latLngAveragedPoint = new LatLng(dLatitude /= nIntegratePointsSize, dLongitude /= nIntegratePointsSize);
		Log.d(TAG, MyElement.getName() + " new averaged point was calculated. Latitude = "  + latLngAveragedPoint.latitude + ", longitude = " + latLngAveragedPoint.longitude);
		mloggedPath.appendData(new GraphViewData(date, latLngAveragedPoint));
		return true;
	}
	
	private boolean IsAppendNewPointToTrack(LatLng latLng, boolean boLastPoint
			, MyDate date//Time of the car location
			)
	{
		if(!appPrefes.IsTrue(BaseApi.mKeyTurnLoggedPath, false))
			return false;
		
		List<LatLng> points =  mloggedPath.getPoints();
        int nPointsSize = points.size();
        
        if((nPointsSize > 0) && points.get(nPointsSize - 1).equals(latLng))
        	return false;//Add new point only if car is moving to another location
        
        if((nPointsSize == 1) && IsIntegratePointToAveragedPoint(
        		points.get(nPointsSize - 1)//First car location
        		, latLng//second car location
    			, date//Time of the car location
        		)
        	)
        	return false;//second car location inside of circle with averaged points circle radius  
        
        if	(        	
        		!boLastPoint
        		&&
        		(nPointsSize > 1)//two or more points
        		&& IsGPS_Noise
        				(
        					  points.get(nPointsSize - 2)//Previous car location
			        		, points.get(nPointsSize - 1)//Current car location
			        		, latLng//Next car location
			    			, date//Time of the car location
			        	)
			 )
        	return false;//GPS noise was detected
        return true;
	}

	private boolean//true - success
		AppendNewPointToTrack(GraphViewData graphViewData, boolean boLastPoint)
	{
		try
		{
			if(!IsAppendNewPointToTrack(graphViewData.getLatLng(), boLastPoint, graphViewData.getDate()))
				return false;
	    	mloggedPath.appendData(graphViewData);
		}
		catch (Exception e)
		{
			Log.e(TAG, MyElement.getName() + " " + e);
			return false;
		}
		return true;
	}
	
	private boolean//true - success
		AppendNewPointToTrack(GraphViewData graphViewData)
	{ return AppendNewPointToTrack(graphViewData, false);}
	
	public class OnResultCommandListenerStatic
		implements com.openvehicles.OVMS.api.OnResultCommandListener
	{
//		private MainActivity mmainActivity;
		private MainActivity getMainActivity()
		{
			MainActivity mainActivity = (MainActivity)getActivity();
	    	if(mainActivity == null)
	    		throw new MyException(MyElement.getName() + " mainActivity == null");
	    	return mainActivity;
		}
		
	    @Override
		public void onResultCommand(String[] result)
		{
/*	    	
	    	if(mmainActivity == null)
	    		mmainActivity = (MainActivity)getActivity();
	    	if(mmainActivity == null)
	    		throw new MyException(MyElement.getName() + " mmainActivity == null");
*/	    		
			switch(Integer.parseInt(result[0]))
			{
			case BaseApi.mnCR_Retrieve_24_Hour_Log_Data://33
				
				mmyTimer.Stop();
				
				//Retrieve 24-hour log data
				//
				//For details see email:
				//Date: Tue, 30 Sep 2014 14:14:01 -0700
				//From: Lee Howard <lee.howard@mainpine.com>
				//Subject: Re: Samsung Galaxy S5
				//
				//Example:
				//
				//10-02 18:12:14.008: I/ApiTask(2144): TX: MP-0 C33,L
				//10-02 18:12:14.298: D/ApiTask(2144): RX: MP-0 c33,0,1,119,L,2014-10-01 21:58:49,47.288244,-123.054592,7,96,1,120 (Z5YhEtaokGO4255igaWatfTjp1Sch1igvcbnsA/YlpIl9zSM6UcmGM+IbpGfqYx274qn/rNgZOYYVZxvdy+LGjBL5UXhOp0=)
				//
				//result[0]	"33" (id=831943927496)					command ID - Retrieve 24-hour log data
				//result[1]	"0" (id=831943927592)					result (0=ok, 1=failed, 2=unsupported, 3=unimplemented). See "Command response 0x63 "c"" header of "OVMS_Protocol.docx" fro details
				//result[2]	"1" (id=831943927624) 					response record number	
				//result[3]	"126" (id=831943887176) 				maximum number of response records	
				//result[4]	"L" (id=831943887208)					data code type
				//result[5]	"2014-10-02 00:18:01" (id=831943887240)	data record timestamp
				//result[6]	"47.288012"   (id=831943887272)			Latitude	
				//result[7]	"-123.054400" (id=831943939664)			Longitude	
				//result[8]	"191"         (id=831943939696)			Car direction	
				//result[9]	"59"          (id=831943939728)			Car altitude	
				//result[10]"1"           (id=831943939760)			Car GPS lock (0=nogps, 1=goodgps)	
				//result[11]"120"         (id=831943939792)			Stale GPS indicator (-1=none, 0=stale, >0 ok)
				
				//result (0=ok, 1=failed, 2=unsupported, 3=unimplemented). See "Command response 0x63 "c"" header of "OVMS_Protocol.docx" for details
				String strResult = "";
				switch(Integer.parseInt(result[1]))
				{
				case 0://ok
					break;
				case 1://failed
					Toast.makeText(getMainActivity(), result[2], Toast.LENGTH_SHORT).show(); 
					return;
				case 2://unsupported
					strResult = "unsupported";
					break;
					
				case 3://unimplemented
					strResult = "unimplemented. " + result[2];
					break;
			
				default:
					strResult = "Unknown result: " + result[1];
				}
				if(!strResult.isEmpty())
				{
					Log.e(TAG, MyElement.getName() + " failed! " + strResult);
					
					/*
					 * I have temporary commented this code because currently the OVMS server returns the
					 * "unimplemented. Command unimplemented" error message if send the "C33,L" command.
					 * 
					 * Example:
					 * 
					04-27 22:26:40.687: I/ApiTask(3995): TX: MP-0 C33,L
					04-27 22:26:40.877: D/ApiTask(3995): RX: MP-0 c33,3,Command unimplemented
					
					 * Instead I simulate the correct response from the server.
					 * I will remove this comment after changing of code of OVMS server.
					 * 

	//				((MainActivity)getActivity()).setSupportProgressBarIndeterminateVisibility(false);
					mmainActivity.setSupportProgressBarIndeterminateVisibility(false);
					
					TurnLoggedPath(false);
					
	            	new MyAlertDialog((Activity)mmainActivity//getActivity()
	            			, String.format(mmainActivity.getString(R.string.Invalid_server_C33_response)
	            					, strResult)//Retrieve the logged path failed! %s. Probably your OVMS server is not supports the C33 \"Retrieve 24-hour log data\" command. Please choice another server in the car\'s settings. Do you want to edit the car\'s settings?
	            			, new OnClickPositiveButton()
		            	{
			            @Override
			            public void onClick()
			            	{
			            		//Open the Settings fragment
			            		mmainActivity.ChangePagerAdapterItem(MainActivity.PagerAdapterItems.settings);
			            	}
		            	}
		        	);
					return;
					*/
					
					/////////////////////////////////////////////////////////////////////
					//for debugging
					C33CommandResponseSimulator();
					return;
					//for debugging
					/////////////////////////////////////////////////////////////////////
				}
				if(onResultC33Command(result))
					return;
			}//switch(Integer.parseInt(result[0]))
			cancelCommand();
		}
	    
		/////////////////////////////////////////////////////////////////////
		//for debugging
		//
		// Simulate the correct C33,L command response from the server
		//
		//Example:
		//
		//04-27 22:26:40.687: I/ApiTask(3995): TX: MP-0 C33,L
		//10-02 18:12:14.298: D/ApiTask(2144): RX: MP-0 c33,0,1,119,L,2014-10-01 21:58:49,47.288244,-123.054592,7,96,1,120
		
	    private void C33CommandResponseSimulator()
	    {
			String[] result =
				{
					"33",//					command ID - Retrieve 24-hour log data
					"0",//					result (0=ok, 1=failed, 2=unsupported, 3=unimplemented). See "Command response 0x63 "c"" header of "OVMS_Protocol.docx" fro details
					"1",// 					response record number	
					"3",// 					maximum number of response records	
					"L",//					data code type
					"2014-10-02 00:18:01",//data record timestamp
					"37.3772583",//			Latitude
					"-121.9233856",//		Longitude
					"191",//				Car direction	
					"59",//					Car altitude
					"1",//					Car GPS lock (0=nogps, 1=goodgps)
					"120"//					Stale GPS indicator (-1=none, 0=stale, >0 ok)
				};
//			result = data;
			if(!onResultC33Command(result))
				return;
			result[2] = "2";					//response record number
			result[5] = "2014-10-02 00:19:01";	//data record timestamp
			result[6] = "37.3782583";			//Latitude	
			result[7] = "-121.9243856";			//Longitude
			if(!onResultC33Command(result))
				return;
			result[2] = "3";					//response record number
			result[5] = "2014-10-02 00:21:01";	//data record timestamp
			result[6] = "37.3792583";			//Latitude	
			result[7] = "-121.9243856";			//Longitude
			if(!onResultC33Command(result))
				return;
			return;
	    	
	    }
		
		//for debugging
		/////////////////////////////////////////////////////////////////////
	    
		private int mnResponseRecordNumber = 0;
	   
		private boolean//true - success 
			onResultC33Command(String[] result)
		{
			String strDataCodeType = result[4]; 
			if(strDataCodeType.contentEquals("L"))//Car location message result[4] == "L". See "OVMS_Protocol.pdf" page 21
			{
	//				mmainActivity.setSupportProgressBarIndeterminateVisibility(true);
	
				
				//result[6]	"47.288012"   (id=831943887272)	Latitude	
				//result[7]	"-123.054400" (id=831943939664)	Longitude	
				//result[8]	"191"         (id=831943939696)	Car direction	
				//result[9]	"59"          (id=831943939728)	Car altitude	
				//result[10]"1"           (id=831943939760)	Car GPS lock (0=nogps, 1=goodgps)	
				//result[11]"120"         (id=831943939792)	Stale GPS indicator (-1=none, 0=stale, >0 ok)
				
				final LatLng latLng = new LatLng(Double.parseDouble(result[6]), Double.parseDouble(result[7]));
				
				Log.d(TAG, MyElement.getName() + " Next point. Response record number: " + result[2] + " latitude = "  + latLng.latitude + " and longitude = " + latLng.longitude + " to the track");
				
				int nResponseRecordNumber = Integer.parseInt(result[2]);
				if((mnResponseRecordNumber + 1) != nResponseRecordNumber)
					throw new MyException(MyElement.getName() + " nResponseRecordNumber != " + (mnResponseRecordNumber + 1));
				mnResponseRecordNumber = nResponseRecordNumber;
				int nResponseRecordNumberMax = Integer.parseInt(result[3]);//maximum number of response records
				if(nResponseRecordNumber > nResponseRecordNumberMax)
					throw new MyException(MyElement.getName() + " nResponseRecordNumber > nResponseRecordNumberMax");
				
	//////////////////////////////////////////////////////////
	//For debugging
	//Simulating of unexpected disconnecting from Internet during receiving of the logged path
	/*					
	if(nResponseRecordNumber == 10)
	{
	Log.d("MyLine", MyElement.getName() + " disconnect from server");
	try
	{
		mmainActivity.getService().socketClose();
	} catch (Exception e) {}
	}
	*/
	//////////////////////////////////////////////////////////
				
				//Progress dialog
				if(mpd == null)
				{
					//http://startandroid.ru/en/uroki/vse-uroki-spiskom/128-urok-67-dialogi-progressdialog
					mpd = new ProgressDialog(getMainActivity());
					mpd.setTitle(R.string.Logged_path_progress);//Logged path progress
					mpd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
					mpd.setMax(Integer.parseInt(result[3]));//maximum number of response records
				}
				if(mpd.isShowing() != true)
					mpd.show();
				
				if(nResponseRecordNumber == 1)
				{//first point
					//for map scale
					mlatLngBounds = new LatLngBounds(latLng, latLng);
					
					mloggedPath = new LoggedPath();
				}
				else
				{
					//for map scale
					mlatLngBounds = mlatLngBounds.including(latLng);
				}
				if(mpd != null)
					mpd.setProgress(nResponseRecordNumber);
				
				boolean boLastPoint = false;
				if(nResponseRecordNumber == nResponseRecordNumberMax)
		        {//Last point
					Log.d(TAG, MyElement.getName() + " last point. mnIncorrectLocationsCount = " + mloggedPath.mnIncorrectLocationsCount);
					/*					
					///////////////////////////////////////
					//for debugging
					CircleOptions options = new CircleOptions().strokeWidth(2)//strokeWidth)
							.strokeColor(Color.GREEN);
					
					//drawing of radius of circle of averaged points
					map.addCircle(options.center(latLng).radius(Settings.GetAveragedPointsCircleRadius(appPrefes)));
					///////////////////////////////////////
					*/					
					boLastPoint = true;
					
					mRetrieve_24_Hour_Log_Data_State = Retrieve_24_Hour_Log_Data_State.LastPoint;
					
					lastPoint();
	/////////////////////////////////////////////////////////////////
	//For debugging
	/*					
	//LatLng latLng2 = new LatLng(latLng.latitude - 0.001, latLng.longitude);//to the south
	LatLng latLng2 = new LatLng(latLng.latitude - 0.001, latLng.longitude - 0.001);//to the south east
	//LatLng latLng2 = new LatLng(latLng.latitude, latLng.longitude - 1);//to the east
	//LatLng latLng3 = new LatLng(latLng2.latitude, latLng2.longitude - 0.001);//GPS noise
	//LatLng latLng3 = new LatLng(latLng2.latitude, latLng2.longitude - 0.002);//correct
	//LatLng latLng3 = new LatLng(latLng2.latitude, latLng2.longitude + 0.001);//GPS noise
	LatLng latLng3 = new LatLng(latLng2.latitude, latLng2.longitude + 0.003);//correct
	if(IsGPS_Noise(
				latLng//Previous car location
				, latLng2//Current car location
				, latLng3//Next car location
			)
		)
		Log.e(TAG, MyElement.getName() + " GPS Noise was detected");
	
	//LatLng latLng4 = new LatLng(latLng3.latitude - 0.0005, latLng3.longitude);//GPS noise
	//LatLng latLng4 = new LatLng(latLng3.latitude - 0.0005, latLng3.longitude - 0.0005);//GPS noise
	//LatLng latLng4 = new LatLng(latLng3.latitude - 0.001, latLng3.longitude - 0.001);//correct
	//LatLng latLng4 = new LatLng(latLng3.latitude - 0.001, latLng3.longitude + 0.001);//correct
	//LatLng latLng4 = new LatLng(latLng3.latitude - 0.0005, latLng3.longitude + 0.0005);//GPS noise
	//LatLng latLng4 = new LatLng(latLng3.latitude, latLng3.longitude + 0.001);//correct
	//LatLng latLng4 = new LatLng(latLng3.latitude + 0.001, latLng3.longitude + 0.001);//correct
	//LatLng latLng4 = new LatLng(latLng3.latitude + 0.0005, latLng3.longitude + 0.0005);//GPS noise
	//LatLng latLng4 = new LatLng(latLng3.latitude + 0.0005, latLng3.longitude);//GPS noise
	//LatLng latLng4 = new LatLng(latLng3.latitude + 0.001, latLng3.longitude);//correct
	//LatLng latLng4 = new LatLng(latLng3.latitude + 0.001, latLng3.longitude - 0.001);//correct
	//LatLng latLng4 = new LatLng(latLng3.latitude + 0.0005, latLng3.longitude - 0.0005);//GPS noise
	//LatLng latLng4 = new LatLng(latLng3.latitude + 0.002, latLng3.longitude + 0.002);//correct
	LatLng latLng4 = new LatLng(latLng3.latitude + 0.002, latLng3.longitude);//correct
	if(IsGPS_Noise(
			latLng2//Previous car location
			, latLng3//Current car location
			, latLng4//Next car location
		)
	)
		Log.e(TAG, MyElement.getName() + " GPS Noise was detected");
	
	//LatLng latLng5 = new LatLng(latLng4.latitude, latLng4.longitude + 0.001);//GPS noise
	//LatLng latLng5 = new LatLng(latLng4.latitude, latLng4.longitude + 0.002);//correct
	//LatLng latLng5 = new LatLng(latLng4.latitude, latLng4.longitude - 0.001);//GPS noise
	//LatLng latLng5 = new LatLng(latLng4.latitude, latLng4.longitude - 0.002);//correct
	//LatLng latLng5 = new LatLng(latLng4.latitude + 0.0005, latLng4.longitude);//correct
	//LatLng latLng5 = new LatLng(latLng4.latitude + 0.0005, latLng4.longitude + 0.0005);//correct
	//LatLng latLng5 = new LatLng(latLng4.latitude + 0.00025, latLng4.longitude + 0.00025);//GPS noise
	//LatLng latLng5 = new LatLng(latLng4.latitude + 0.00025, latLng4.longitude - 0.00025);//GPS noise
	//LatLng latLng5 = new LatLng(latLng4.latitude + 0.00025, latLng4.longitude - 0.001);//GPS noise
	LatLng latLng5 = new LatLng(latLng4.latitude + 0.00025, latLng4.longitude - 0.002);//
	if(IsGPS_Noise(
			latLng3//Previous car location
			, latLng4//Current car location
			, latLng5//Next car location
		)
	)
		Log.e(TAG, MyElement.getName() + " GPS Noise was detected");
	*/	
	/////////////////////////////////////////////////////////////////
		        }//last point
		        
				if(Integer.parseInt(result[10]) != 1)//Car GPS lock (0=nogps, 1=goodgps)
				{
					Log.e(TAG, MyElement.getName() + " failed! Car GPS lock " + result[10] + " is not 1=goodgps");
					
					if(boLastPoint)
					{
				        mloggedPath.addPolyline();
					}
					
					return false;
				}
				int nStaleGPS_Indicator = Integer.parseInt(result[11]); 
				if((nStaleGPS_Indicator == -1) || (nStaleGPS_Indicator == 0))//Stale GPS indicator (-1=none, 0=stale, >0 ok)
				{
					Log.e(TAG, MyElement.getName() + " failed! Stale GPS indicator " + result[11] + " is not ok");
					
					if(boLastPoint)
				        mloggedPath.addPolyline();
					
					return false;
				}
				if(getService().isLoggedIn() && (mRetrieve_24_Hour_Log_Data_State != Retrieve_24_Hour_Log_Data_State.Error))
				{
					try
					{
						GraphViewData graphViewData = new GraphViewData
						(
							new MyDate
							(
								"yyyy-MM-dd HH:mm:ss"//See http://www.tutorialspoint.com/java/java_date_time.htm for details
								, result[5]//"2014-10-02 00:18:01" (id=831943887240)	data record timestamp
							)
							, latLng
						);
						if(graphViewData.getDate() != null)
						{
							//Sometimes I have receiving similar lines from OVMS server:
	
							//03-10 22:05:12.541: D/ApiTask(3640): RX: MP-0 c33,0,92,226,L,2015-03-10 11:26:25,47.288412,-123.054400,0,59,1,120 
							//03-10 22:05:12.651: D/ApiTask(3640): RX: MP-0 c33,0,93,226,L,2015-03-10 11:26:25,47.288588,-123.054328,0,18,1,120 
	
							//I can not calculate speed of the car here because delta time equal zero.
							//Now I discard the second data point (graphViewData.getDate() == null) for resolving of the problem.
	
							AppendNewPointToTrack(graphViewData, boLastPoint);
						}
					}
					catch (MyException e)
					{
						Log.e("MyLine", MyElement.getName() + " " + e);
					}
				}
				else
				{
					if(mRetrieve_24_Hour_Log_Data_State != Retrieve_24_Hour_Log_Data_State.Error)
					{
						Log.e("MyLine", MyElement.getName() + " Unexpected disconnect from server");
						mRetrieve_24_Hour_Log_Data_State = Retrieve_24_Hour_Log_Data_State.Error;
					}
					boLastPoint = true;
				}
				
				if(boLastPoint)
			        mloggedPath.addPolyline();
				
				return true;
			}//if(strDataCodeType.contentEquals("L"))
			Log.e(TAG, MyElement.getName() + " failed! unknown data code type: " + strDataCodeType);
			return false;
		}//public void onResultC33Command(String[] result)
	}//class OnResultCommandListenerStatic
	
	private static OnResultCommandListenerStatic mOnResultCommandListenerRetrieve24hourLogData;

	private void enableDisableOfNextAndPreviousLocationButtonsOfTheGraph() 
	{
		boolean boEnableButtonNexpLocation,  boEnableButtonPrevLocation;
		if
			(
				(mloggedPath.mPolylineOptionsTrack != null)//logged path is not empty
				&& (mloggedPath.mgraphViewSeries.getGraphViewDataInterface().length > 1)//two or more points in the logged path
			)
		{
			boEnableButtonNexpLocation = true;
			if(mloggedPath.mnMapPointerIndex < 0)
				boEnableButtonPrevLocation = false;//Map pointer was not selected before.
			else boEnableButtonPrevLocation = true;
		}
		else
		{//logged path is empty or one point only
			boEnableButtonNexpLocation = false;
			boEnableButtonPrevLocation = false;
		}
	    rootView.findViewById(R.id.buttonNexpLocation).setEnabled(boEnableButtonNexpLocation);
		rootView.findViewById(R.id.ButtonPrevLocation).setEnabled(boEnableButtonPrevLocation);
	}

	private void ShowLoggedPath() {
		
		//Show graph 
		View graphParent = rootView.findViewById(R.id.graphParent);
		LinearLayout.LayoutParams layoutParamsGraph = (LinearLayout.LayoutParams)graphParent.getLayoutParams();
		int height = (int)(100f * rootView.getContext().getResources().getDisplayMetrics().density);//http://developer.alexanderklimov.ru/android/theory/scales.php
		if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)//http://developer.alexanderklimov.ru/android/orientation.php
			layoutParamsGraph.height = height;
		else
		{
			Activity activity = getActivity();
			/*
			 * Call requires API level 13 (current min is 9): android.view.Display#getSize
			Point outSize = new Point();
			activity.getWindowManager().getDefaultDisplay().getSize(outSize);
			*/
			//http://developer.alexanderklimov.ru/android/orientation.php
		    DisplayMetrics outMetrics = new DisplayMetrics();
		    activity.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		    if((outMetrics.heightPixels / outMetrics.ydpi) > 3)//inches
		    	layoutParamsGraph.height = height;
		    else layoutParamsGraph.height = 0;//I do not want see the graph of speed if screen orientation is landscape and screen height is less 3 inches
		}
		graphParent.setLayoutParams(layoutParamsGraph);
		LinearLayout graph = (LinearLayout)(rootView.findViewById(R.id.graph));
		if((mgraphView != null) && (graph.getChildCount() == 0) && (layoutParamsGraph.height != 0))
		{
			try
			{
				LinearLayout parent = (LinearLayout)(mgraphView.getParent());
				parent.removeView(mgraphView);
				graph.addView(mgraphView);
			}
			catch (Exception e)
			{
				Log.e(TAG, MyElement.getName() + " " + e);
			}
		}
		
		if((mloggedPath != null) && mloggedPath.addPolyline())
		{
			enableDisableOfNextAndPreviousLocationButtonsOfTheGraph();
			return;
		}
		
		//http://startandroid.ru/en/uroki/vse-uroki-spiskom/128-urok-67-dialogi-progressdialog
		mmyTimer.Start(10000, R.string.Invalid_server_C33//Server response timeout. Probably your OVMS server is not supports the C33 \"Retrieve 24-hour log data\" command. Please choice another server in the car\'s settings. Do you want to edit the car\'s settings?
				, new MyTimerTimeout()
	            	{
			            @Override
			            public void onTimeout()
		            	{
			            	((MainActivity)getActivity()).setSupportProgressBarIndeterminateVisibility(false);
							/////////////////////////////////////////////////////////////////////
							//for debugging
			            	((MainActivity)getActivity()).getFragMap().mOnResultCommandListenerRetrieve24hourLogData.C33CommandResponseSimulator();
							//for debugging
							/////////////////////////////////////////////////////////////////////
		            	}
	            	}				
				, new OnClickPositiveButton()
	            	{
			            @Override
			            public void onClick()
			            	{
			            		//Open the Settings fragment
			            		((MainActivity)getActivity()).ChangePagerAdapterItem(MainActivity.PagerAdapterItems.settings);
			            	}
	            	}				
				
				);
		
		if(mOnResultCommandListenerRetrieve24hourLogData == null)
			mOnResultCommandListenerRetrieve24hourLogData = new OnResultCommandListenerStatic();
		
		sendCommandRetrieve24hourLogData(R.string.msg_setting_charge_c, String.format(
			"%d,%s"
			, BaseApi.mnCR_Retrieve_24_Hour_Log_Data// -­ Retrieve 24-hour log data. See "Re: Samsung Galaxy S5" email from Lee Howard dated October 1 2014
				//10-02 18:12:14.008: I/ApiTask(2144): TX: MP-0 C33,L
				//10-02 18:12:14.298: D/ApiTask(2144): RX: MP-0 c33,0,1,119,L,2014-10-01 21:58:49,47.288244,-123.054592,7,96,1,120 (Z5YhEtaokGO4255igaWatfTjp1Sch1igvcbnsA/YlpIl9zSM6UcmGM+IbpGfqYx274qn/rNgZOYYVZxvdy+LGjBL5UXhOp0=)
				//etc...
			, "L"//Car location message 0x4C. See "OVMS_Protocol.pdf" page 21
		), mOnResultCommandListenerRetrieve24hourLogData);//this);
		
		mRetrieve_24_Hour_Log_Data_State = Retrieve_24_Hour_Log_Data_State.Retrieving;
		
	}
	
	private void HideLoggedPath() {
		
		if(mRetrieve_24_Hour_Log_Data_State == Retrieve_24_Hour_Log_Data_State.Retrieving)
		{
			((MainActivity)getActivity()).setSupportProgressBarIndeterminateVisibility(false);
			cancelCommand();
		}
		
		mRetrieve_24_Hour_Log_Data_State = Retrieve_24_Hour_Log_Data_State.NoRetrieve;
		
		//Hide  graph.  
		View graphParent = rootView.findViewById(R.id.graphParent);
		LinearLayout.LayoutParams layoutParamsGraph = (LinearLayout.LayoutParams)graphParent.getLayoutParams();
		layoutParamsGraph.height = 0;
		graphParent.setLayoutParams(layoutParamsGraph);
		
		if(mloggedPath != null)
			mloggedPath.Clear();
	}
	
	@Override
	public void onDestroyView() {
		try {
			if(mpd != null)
			{
				mpd.dismiss();
				mpd = null;
			}
			flag = true;
			SupportMapFragment fragment = ((SupportMapFragment) getFragmentManager()
					.findFragmentById(R.id.mmap));
			FragmentTransaction ft = getActivity().getSupportFragmentManager()
					.beginTransaction();
			ft.remove(fragment);
			ft.commit();
			database.close();
		} catch (Exception e) {
		}
		super.onDestroyView();
	}

	//For resolving of problem of rotation of screen.
	//
	//http://habrahabr.ru/post/131560/
	//
	//For testing:
	//	1. Check the Show Logged Path in the Settings window
	//	2. Rotate screen from portrait to landscape and rotate back
	//	3. Uncheck the Show Logged Path in the Settings window
	//	4. Check the Show Logged Path in the Settings window again
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
			
		// this is really important in order to save the state across screen
		// configuration changes for example
		setRetainInstance(true); 
	}

	private void direction() {
		// TODO Auto-generated method stub
		String directions = "https://maps.google.com/maps?saddr=Kanyakumari,+Tamil+Nadu,+India&daddr=Trivandrum,+Kerala,+India";
		directions = "https://maps.google.com/maps?saddr=37.410866,-122.001946&daddr="
				+ slat + "," + slng;
		// Create Google Maps intent from current location to target location
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
				Uri.parse(directions));
		intent.setClassName("com.google.android.apps.maps",
				"com.google.android.maps.MapsActivity");
		startActivity(intent);
	}


	// marker click event
	@Override
	public void onInfoWindowClick(Marker marker) {
		// TODO Auto-generated method stub
		int j = marker.getClusterGroup();
		Log.d(TAG, "click: ClusterGroup=" + j);
		if (j == 0) {
			dialog(marker);
		}
	}

	// after fetch value from server
	@Override
	public void after(boolean clearmap) {

		Log.d(TAG, "after: clearmap=" + clearmap);

		if (clearmap) {
			map.clear();
			if(mloggedPath != null)
				mloggedPath.addPolyline();
		} else {
			lis = map.getMarkers();
			for (int i = 0; i < lis.size(); i++) {
				Marker carmarker = lis.get(i);
				int j = carmarker.getClusterGroup();
				if (j == -1) {
					lis.remove(i);
				} else {
					carmarker.remove();
				}
			}
		}


		// Load charge points from database:

		Cursor cursor;
		boolean check_range = false;
		double maxrange_m = 0;

		if (appPrefes.getData("filter").equals("on")) {
			// check if filter is defined, else fallback to all stations:
			String connectionList = appPrefes.getData("Id");
			Log.d(TAG, "after: connectionList=(" + connectionList + ")");
			if (!connectionList.equals(""))
				cursor = database.get_mapdetails(connectionList);
			else
				cursor = database.get_mapdetails();
		} else {
			// filter off:
			cursor = database.get_mapdetails();
		}

		if (appPrefes.getData("inrange").equals("on")) {
			check_range = true;
			if (distance_units.equals("Miles"))
				maxrange_m = maxrange * 1.609344 * 1000;
			else
				maxrange_m = maxrange * 1000;
		}

		Log.d(TAG, "after: addMarkers avail=" + cursor.getCount());

		if (cursor.getCount() != 0) {
			int cnt_added = 0;
			if (cursor.moveToFirst()) {
				do {
					// check position:

					double Latitude = Double.parseDouble(cursor
							.getString(cursor.getColumnIndex("Latitude")));
					double Longitude = Double.parseDouble(cursor
							.getString(cursor.getColumnIndex("Longitude")));

					if (check_range) {
						if (distance(lat, lng, Latitude, Longitude) > maxrange_m)
							continue;
					}

					// add marker:

					String cpid = cursor.getString(cursor.getColumnIndex("cpid"));
					String title = cursor.getString(cursor.getColumnIndex("Title"));
					String snippet = cursor.getString(cursor.getColumnIndex("OperatorInfo"));

					MarkerGenerator.addMarkers(map,
							title, snippet,
							new LatLng(Latitude, Longitude),
							cpid);

					cnt_added++;

				} while (cursor.moveToNext());
			}

			Log.d(TAG, "after: addMarkers added=" + cnt_added);
		}
	}


	double roundTwoDecimals(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		//Log.d(TAG, "roundTwoDecimals=" + twoDForm.format(d));
		return Double.valueOf(twoDForm.format(d));
	}


	// click marker event
	private void dialog(Marker marker) {
		// open dialog:
		Bundle args = new Bundle();
		args.putString("cpId", (String) marker.getData());
		BaseFragmentActivity.show(getActivity(), DetailFragment.class, args,
				Configuration.ORIENTATION_UNDEFINED);
	}

	private static LatLng mlatLng;

	@Override
	public void update(CarData pCarData) {
		Log.d(TAG, "Car on map: " + pCarData.car_latitude + " lng"
				+ pCarData.car_longitude);
		mlatLng = new LatLng(pCarData.car_latitude, pCarData.car_longitude);
		mCarData = pCarData;
		update();
	}


	public void update() {

		if (mCarData == null)
			return;

		// get last known car position:

		lat = mCarData.car_latitude;
		lng = mCarData.car_longitude;
		maxrange = Math.max(mCarData.car_range_estimated_raw, mCarData.car_range_ideal_raw);
		distance_units = (mCarData.car_distance_units_raw.equals("M") ? "Miles" : "KM");

		Log.d(TAG, "update: Car on map: lat=" + lat + " lng=" + lng
				+ " maxrange=" + maxrange + distance_units);

		// update charge point markers:

		after(true);

		// update car position marker:

		final LatLng MELBOURNE = new LatLng(lat, lng);
		Drawable drawable = getResources().getDrawable(
				Ui.getDrawableIdentifier(getActivity(), "map_"
						+ mCarData.sel_vehicle_image));
		Bitmap myLogo = ((BitmapDrawable) drawable).getBitmap();
		MarkerOptions marker = new MarkerOptions().position(MELBOURNE)
				.title(mCarData.sel_vehicle_label)
				.rotation((float) mCarData.car_direction)
				.icon(BitmapDescriptorFactory.fromBitmap(myLogo));
		Marker carmarker = map.addMarker(marker);
		carmarker.setClusterGroup(ClusterGroup.NOT_CLUSTERED);

		if (flag) {
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(MELBOURNE, 15));
			flag = false;
		}
/*		
		else if (autotrack) {
			map.moveCamera(CameraUpdateFactory.newLatLng(MELBOURNE));
		}
*/
		// update range circles:

		Log.i(TAG, "update: adding range circles:"
				+ " ideal=" + mCarData.car_range_ideal_raw
				+ " estimated=" + mCarData.car_range_estimated_raw);
		addCircles(mCarData.car_range_ideal_raw,
				mCarData.car_range_estimated_raw);


		// start chargepoint data update:

		appPrefes.SaveData("lat_main", "" + mCarData.car_latitude);
		appPrefes.SaveData("lng_main", "" + mCarData.car_longitude);

		MainActivity.updateLocation.updatelocation();

		if
		(
			(mRetrieve_24_Hour_Log_Data_State == Retrieve_24_Hour_Log_Data_State.LastPoint)//Append of the current LatLng point only if retrieving 24-hour log data was completed
			&&
			(
/*					
					appPrefes.IsTrue(mKeyStartTrack, false)
					||
*/					
					appPrefes.IsTrue(BaseApi.mKeyTurnLoggedPath, false)//Retrieving of 24-hour log data was ended (last point was received).
															//Now is adding current car location to the end of 24-hour log data.
			)
		)
		{
			try
			{
				if(!AppendNewPointToTrack(new GraphViewData
						(
							// I can not use pCarData.car_lastupdated if my car and my smartphone have different time zones.
							// In another words pCarData.car_lastupdated is incorrect if the clock of the car is not equal the clock of the smartphone.
							// I waiting from Lee new version of the "Car location message 0x4C "L"" with timestamp of the car location for resolving of problem.
							// Currently I am pass null instead of timestamp of the car location.
							// As result, I can not calculate speed of the car for current location.
							null//new MyDate(pCarData.car_lastupdated)
							
							, mlatLng)
						)
					)
					return;
				mloggedPath.addPolyline();
				try
				{
					AddGraphView();
				}
				catch (Exception e)
				{
					Log.e(TAG, MyElement.getName() + " " + e);
				}
			}
			catch (MyException e)
			{
				Log.e(TAG, MyElement.getName() + " " + e);
			}
		}
	}

	// draw circle in a map
	private void addCircles(int rd1, int rd2) {
		float strokeWidth = getResources().getDimension(
				R.dimen.circle_stroke_width);
		CircleOptions options = new CircleOptions().strokeWidth(strokeWidth)
				.strokeColor(Color.BLUE);
		rd1 = rd1 * 1000;
		rd2 = rd2 * 1000;
		map.addCircle(options.center(new LatLng(lat, lng)).data("first circle")
				.radius(rd1));
		options = new CircleOptions().strokeWidth(strokeWidth).strokeColor(
				Color.RED);
		map.addCircle(options.center(new LatLng(lat, lng))
				.data("second circle").radius(rd2));
	}

	// calculate distance in meters:
	public static double distance(double lat1, double lon1, double lat2, double lon2) {
		double dLat = Math.toRadians(lat2-lat1);
		double dLon = Math.toRadians(lon2-lon1);
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
				Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
						Math.sin(dLon/2) * Math.sin(dLon/2);
		double c = 2 * Math.asin(Math.sqrt(a));
		return 6371000 * c;
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt_route:
			direction();
			break;

		default:
			break;
		}
	}

	@Override
	public void clearCache() {
		database.clear_latlngdetail();
		MainActivity.updateLocation.updatelocation();
	}

	@Override
	public void updateFilter(String connectionList) {
		// update markers:
		after(false);
	}
	
	@Override
	public void updateclustLoggedPath(int clusterSizeIndex, boolean enabled) {
		updateClusteringLoggedPath(clusterSizeIndex, enabled);
	}
	
    @Override
    public boolean onMyLocationButtonClick() {
    	
		if(mlatLng != null)
			map.moveCamera(CameraUpdateFactory.newLatLng(mlatLng));
		else Toast.makeText(getActivity(), R.string.Undefined_location, Toast.LENGTH_SHORT).show();//Location of a vehicle is not defined
		
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return true;//false;
    }

    @Override
    public void onMapClick(LatLng point) {
//    	Log.d(TAG, MyElement.getName());
    	mloggedPath.onMapClick(point);
    }
}
