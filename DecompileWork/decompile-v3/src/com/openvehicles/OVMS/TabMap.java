// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.openvehicles.OVMS;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.*;
import android.os.*;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.google.android.maps.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.xml.parsers.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

// Referenced classes of package com.openvehicles.OVMS:
//            RouteOverlay, Utilities, CarData, CarData_Group, 
//            MyLocationOverlayCustom, OVMSActivity

public class TabMap extends MapActivity
{
    class CustomSpinnerAdapter extends SimpleAdapter
    {

        public View getView(int i, View view, ViewGroup viewgroup)
        {
            if(view == null)
                view = mInflater.inflate(0x7f03000b, null);
            HashMap hashmap = (HashMap)getItem(i);
            ImageView imageview = (ImageView)view.findViewById(0x7f090038);
            StringBuilder stringbuilder = new StringBuilder(String.valueOf(getCacheDir().getAbsolutePath()));
            Object aobj[] = new Object[1];
            aobj[0] = hashmap.get("Name").toString();
            Bitmap bitmap = BitmapFactory.decodeFile(stringbuilder.append(String.format("/%s.png", aobj)).toString());
            if(bitmap != null)
            {
                imageview.setImageBitmap(bitmap);
            } else
            {
                imageview.setImageBitmap(null);
                imageview.setBackgroundResource(((Integer)hashmap.get("Icon")).intValue());
            }
            return view;
        }

        LayoutInflater mInflater;
        final TabMap this$0;

        public CustomSpinnerAdapter(Context context, List list, int i, String as[], int ai[])
        {
            this$0 = TabMap.this;
            super(context, list, i, as, ai);
            mInflater = LayoutInflater.from(context);
        }
    }

    private class GroupCarsListViewAdapter extends ArrayAdapter
    {

        public View getView(int i, View view, ViewGroup viewgroup)
        {
            CarData_Group cardata_group = (CarData_Group)items[i];
            View view1;
            if(cardata_group == null)
            {
                view1 = null;
            } else
            {
                view1 = view;
                if(view1 == null)
                    view1 = ((LayoutInflater)getSystemService("layout_inflater")).inflate(0x7f030012, null);
                ((TextView)view1.findViewById(0x7f090095)).setText(cardata_group.VehicleID);
                TextView textview = (TextView)view1.findViewById(0x7f090096);
                Object aobj[] = new Object[2];
                aobj[0] = Double.valueOf(cardata_group.SOC);
                String s;
                double d;
                String s1;
                if(cardata_group.Speed > 0.0D)
                {
                    Object aobj2[] = new Object[2];
                    aobj2[0] = Double.valueOf(cardata_group.Speed);
                    TextView textview1;
                    Object aobj1[];
                    String s2;
                    if(data.Data_DistanceUnit.equals("K"))
                        s2 = "kph";
                    else
                        s2 = "mph";
                    aobj2[1] = s2;
                    s = String.format(" | %s%s", aobj2);
                } else
                {
                    s = "";
                }
                aobj[1] = s;
                textview.setText(String.format("%s%%", aobj));
                textview1 = (TextView)view1.findViewById(0x7f090097);
                d = Utilities.GetDistanceBetweenCoordinatesKM(cardata_group.Latitude, cardata_group.Longitude, data.Data_Latitude, data.Data_Longitude);
                aobj1 = new Object[2];
                if(!data.Data_DistanceUnit.equals("K"))
                    d *= 0.62137119200000002D;
                aobj1[0] = Double.valueOf(d);
                if(data.Data_DistanceUnit.equals("K"))
                    s1 = "km";
                else
                    s1 = "mi";
                aobj1[1] = s1;
                textview1.setText(String.format("%.1f%n %s", aobj1));
                ((ImageView)view1.findViewById(0x7f090094)).setImageResource(getResources().getIdentifier((new StringBuilder(String.valueOf(cardata_group.VehicleImageDrawable))).append("96x44").toString(), "drawable", "com.openvehicles.OVMS"));
            }
            return view1;
        }

        private Object items[];
        final TabMap this$0;

        public GroupCarsListViewAdapter(Context context, int i, Object aobj[])
        {
            this$0 = TabMap.this;
            super(context, i, aobj);
            items = aobj;
        }
    }

    private static interface OnCenteringModeChangedListener
    {

        public abstract void OnCenteringModeChanged(int i);
    }

    private class TouchOverlay extends Overlay
    {

        public boolean onTouchEvent(MotionEvent motionevent, MapView mapview)
        {
            if(motionevent.getAction() != 1) goto _L2; else goto _L1
_L1:
            hidePopup();
            if(centeringMode.getMode() != 4)
            {
                float f = motionevent.getX();
                float f1 = motionevent.getY();
                if(Math.sqrt(Math.pow(f - mapDragLastX, 2D) + Math.pow(f1 - mapDragLastY, 2D)) > 100D)
                    centeringMode.setMode(4);
            }
_L4:
            return false;
_L2:
            if(motionevent.getAction() == 0)
            {
                mapDragLastX = motionevent.getX();
                mapDragLastY = motionevent.getY();
            }
            if(true) goto _L4; else goto _L3
_L3:
        }

        final TabMap this$0;

        private TouchOverlay()
        {
            this$0 = TabMap.this;
            super();
        }

        TouchOverlay(TouchOverlay touchoverlay)
        {
            this();
        }
    }

    private class mapCenteringMode
    {

        /**
         * @deprecated Method fireCenteringModeChangedEvent is deprecated
         */

        private void fireCenteringModeChangedEvent()
        {
            this;
            JVM INSTR monitorenter ;
            if(_listener != null)
                _listener.OnCenteringModeChanged(_centeringMode);
            this;
            JVM INSTR monitorexit ;
            return;
            Exception exception;
            exception;
            throw exception;
        }

        public int getMode()
        {
            return _centeringMode;
        }

        /**
         * @deprecated Method removeOnGroupCarTappedListener is deprecated
         */

        public void removeOnGroupCarTappedListener()
        {
            this;
            JVM INSTR monitorenter ;
            _listener = null;
            this;
            JVM INSTR monitorexit ;
            return;
            Exception exception;
            exception;
            throw exception;
        }

        public void setMode(int i)
        {
            if(_centeringMode != i)
            {
                _centeringMode = i;
                fireCenteringModeChangedEvent();
            }
        }

        /**
         * @deprecated Method setOnMapCenteringModeChangedListener is deprecated
         */

        public void setOnMapCenteringModeChangedListener(OnCenteringModeChangedListener oncenteringmodechangedlistener)
        {
            this;
            JVM INSTR monitorenter ;
            _listener = oncenteringmodechangedlistener;
            this;
            JVM INSTR monitorexit ;
            return;
            Exception exception;
            exception;
            throw exception;
        }

        public static final int CAR = 2;
        public static final int CUSTOM = 4;
        public static final int DEFAULT = 0;
        public static final int DEVICE = 1;
        public static final int GROUPCAR = 5;
        public static final int ROUTE = 3;
        public String GroupCar_VehicleID;
        private int _centeringMode;
        private OnCenteringModeChangedListener _listener;
        final TabMap this$0;

        private mapCenteringMode()
        {
            this$0 = TabMap.this;
            super();
            _centeringMode = 0;
            GroupCar_VehicleID = "";
            _listener = null;
        }

        mapCenteringMode(mapCenteringMode mapcenteringmode)
        {
            this();
        }
    }


    public TabMap()
    {
        currentVehicleID = "";
        lastUpdateTimerHandler = new Handler();
        carMarkerAnimationTimerHandler = new Handler();
        carMarkerAnimationFrame = 0;
        lastUpdateTimer = new Runnable() {

            public void run()
            {
                updateLastUpdatedView();
                lastUpdateTimerHandler.postDelayed(lastUpdateTimer, 5000L);
            }

            final TabMap this$0;

            
            {
                this$0 = TabMap.this;
                super();
            }
        }
;
        routeError = new Runnable() {

            public void run()
            {
                Log.d("Routing", "Route Failed");
                Toast.makeText(TabMap.this, "Route Failed", 0);
            }

            final TabMap this$0;

            
            {
                this$0 = TabMap.this;
                super();
            }
        }
;
        myLocationEnable = new Runnable() {

            public void run()
            {
                myLocationOverlay.enableMyLocation();
            }

            final TabMap this$0;

            
            {
                this$0 = TabMap.this;
                super();
            }
        }
;
        myLocationDisable = new Runnable() {

            public void run()
            {
                myLocationOverlay.disableMyLocation();
            }

            final TabMap this$0;

            
            {
                this$0 = TabMap.this;
                super();
            }
        }
;
        animateCarMarker = new Runnable() {

            public void run()
            {
                CarData_Group cardata_group;
                String as[];
                int i;
                cardata_group = null;
                as = new String[data.Group.size()];
                data.Group.keySet().toArray(as);
                i = 0;
_L5:
                if(i < carMarkers.size()) goto _L2; else goto _L1
_L1:
                mapView.invalidate();
                TabMap tabmap = TabMap.this;
                int l = 1 + tabmap.carMarkerAnimationFrame;
                tabmap.carMarkerAnimationFrame = l;
                if(l < 40)
                    carMarkerAnimationTimerHandler.postDelayed(animateCarMarker, 50L);
                return;
_L2:
                if(i <= 0)
                    break MISSING_BLOCK_LABEL_203;
                if(as.length >= i)
                    break; /* Loop/switch isn't completed */
                Object aobj1[] = new Object[2];
                aobj1[0] = Integer.valueOf(carMarkers.size());
                aobj1[1] = Integer.valueOf(1 + as.length);
                Log.d("MAP", String.format("ERROR! Found %s markers but only %s car data.", aobj1));
                if(true) goto _L1; else goto _L3
_L3:
                cardata_group = (CarData_Group)data.Group.get(as[i - 1]);
                GeoPoint geopoint;
                if(i == 0)
                    geopoint = Utilities.GetCarGeopoint(data);
                else
                    geopoint = Utilities.GetCarGeopoint(cardata_group.Latitude, cardata_group.Longitude);
                if(!carMarkers.getItem(i).getPoint().equals(geopoint))
                    break; /* Loop/switch isn't completed */
_L6:
                i++;
                if(true) goto _L5; else goto _L4
_L4:
                int j = (geopoint.getLatitudeE6() - lastCarGeoPoints[i].getLatitudeE6()) / 40;
                int k = (geopoint.getLongitudeE6() - lastCarGeoPoints[i].getLongitudeE6()) / 40;
                GeoPoint geopoint1;
                Utilities.CarMarker carmarker;
                if(carMarkerAnimationFrame == 39)
                    geopoint1 = geopoint;
                else
                    geopoint1 = new GeoPoint(lastCarGeoPoints[i].getLatitudeE6() + j * carMarkerAnimationFrame, lastCarGeoPoints[i].getLongitudeE6() + k * carMarkerAnimationFrame);
                Log.d("MAP", (new StringBuilder("Car Marker ")).append(i).append(" Transitional Point: ").append(geopoint1.getLatitudeE6()).append(", ").append(geopoint1.getLongitudeE6()).toString());
                if(i == 0)
                {
                    String s = "-";
                    if(data.Data_LastCarUpdate != null)
                        s = (new SimpleDateFormat("MMM d, K:mm:ss a")).format(data.Data_LastCarUpdate);
                    String s1 = data.VehicleID;
                    Object aobj[] = new Object[1];
                    aobj[0] = s;
                    carmarker = new Utilities.CarMarker(geopoint1, s1, String.format("Last reported: %s", aobj), (int)data.Data_Direction);
                } else
                {
                    carmarker = new Utilities.CarMarker(geopoint1, cardata_group.VehicleID, "", (int)cardata_group.Direction);
                }
                carMarkers.setOverlay(i, carmarker);
                  goto _L6
                if(true) goto _L5; else goto _L7
_L7:
            }

            final TabMap this$0;

            
            {
                this$0 = TabMap.this;
                super();
            }
        }
;
        refreshUIHandler = new Handler() {

            public void handleMessage(Message message)
            {
                GeoPoint geopoint;
                Log.d("OVMS", "Refreshing Map");
                geopoint = Utilities.GetCarGeopoint(data);
                if(carMarkers.size() <= 0) goto _L2; else goto _L1
_L1:
                int j;
                lastCarGeoPoints = new GeoPoint[carMarkers.size()];
                j = 0;
_L14:
                if(j < carMarkers.size()) goto _L4; else goto _L3
_L3:
                carMarkerAnimationTimerHandler.removeCallbacks(animateCarMarker);
                carMarkerAnimationFrame = 0;
                carMarkerAnimationTimerHandler.postDelayed(animateCarMarker, 0L);
_L15:
                int i;
                Iterator iterator;
                TextView textview = (TextView)findViewById(0x7f090083);
                String s2 = (String)data.Data_Parameters.get(Integer.valueOf(11));
                if(s2 != null && s2.length() > 0)
                {
                    if(data.Group != null)
                    {
                        Object aobj2[] = new Object[3];
                        aobj2[0] = s2;
                        aobj2[1] = Integer.valueOf(data.Group.size());
                        String s;
                        String s1;
                        Object aobj[];
                        Utilities.CarMarker carmarker;
                        HashMap hashmap;
                        String s4;
                        if(data.Group.size() > 1)
                            s4 = "s";
                        else
                            s4 = "";
                        aobj2[2] = s4;
                        textview.setText(String.format("Group: %s (%s vehicle%s)", aobj2));
                    } else
                    {
                        Object aobj1[] = new Object[1];
                        aobj1[0] = s2;
                        textview.setText(String.format("Group: %s", aobj1));
                    }
                    textview.setVisibility(0);
                } else
                {
                    textview.setVisibility(8);
                }
                if(data.Group == null || data.Group.size() <= 0) goto _L6; else goto _L5
_L5:
                i = 1;
                iterator = data.Group.keySet().iterator();
_L16:
                if(iterator.hasNext()) goto _L7; else goto _L6
_L6:
                centeringMode.getMode();
                JVM INSTR tableswitch 1 5: default 360
            //                           1 991
            //                           2 1044
            //                           3 1058
            //                           4 384
            //                           5 1111;
                   goto _L8 _L9 _L10 _L11 _L12 _L13
_L13:
                break MISSING_BLOCK_LABEL_1111;
_L12:
                break; /* Loop/switch isn't completed */
_L8:
                mc.animateTo(geopoint);
                mc.setZoom(18);
_L17:
                ListView listview = (ListView)findViewById(0x7f09008e);
                String s3;
                CarData_Group cardata_group;
                Utilities.CarMarker carmarker1;
                Drawable drawable;
                if(data.Group != null)
                {
                    hashmap = (HashMap)data.Group.clone();
                    hashmap.remove(data.VehicleID);
                    groupCarsListAdapter = new GroupCarsListViewAdapter(TabMap.this, 0x7f030012, hashmap.values().toArray());
                    listview.setAdapter(groupCarsListAdapter);
                } else
                if(groupCarsListAdapter != null)
                {
                    groupCarsListAdapter.clear();
                    listview.setAdapter(groupCarsListAdapter);
                }
                Log.d("Routing", (new StringBuilder("Redrawing Map with ")).append(-3 + mapOverlays.size()).append(" waypoints").toString());
                mapView.invalidate();
                return;
_L4:
                lastCarGeoPoints[j] = carMarkers.getItem(j).getPoint();
                j++;
                  goto _L14
_L2:
                s = "-";
                if(data.Data_LastCarUpdate != null)
                    s = (new SimpleDateFormat("MMM d, K:mm:ss a")).format(data.Data_LastCarUpdate);
                s1 = data.VehicleID;
                aobj = new Object[1];
                aobj[0] = s;
                carmarker = new Utilities.CarMarker(geopoint, s1, String.format("Last reported: %s", aobj), (int)data.Data_Direction);
                carMarkers.addOverlay(carmarker);
                  goto _L15
_L7:
                s3 = (String)iterator.next();
                if(!s3.equals(data.VehicleID))
                {
                    cardata_group = (CarData_Group)data.Group.get(s3);
                    carmarker1 = new Utilities.CarMarker(Utilities.GetCarGeopoint(cardata_group.Latitude, cardata_group.Longitude), cardata_group.VehicleID, "", (int)cardata_group.Direction);
                    drawable = getResources().getDrawable(getResources().getIdentifier((new StringBuilder(String.valueOf(cardata_group.VehicleImageDrawable))).append("32x32").toString(), "drawable", "com.openvehicles.OVMS"));
                    if(drawable == null)
                        drawable = getResources().getDrawable(getResources().getIdentifier("car_roadster_arcticwhite32x32", "drawable", "com.openvehicles.OVMS"));
                    drawable.setBounds(-drawable.getIntrinsicWidth() / 2, -drawable.getIntrinsicHeight(), drawable.getIntrinsicWidth() / 2, 0);
                    carmarker1.setMarker(drawable);
                    if(carMarkers.size() > i)
                        carMarkers.setOverlay(i, carmarker1);
                    else
                        carMarkers.addOverlay(carmarker1);
                    i++;
                }
                  goto _L16
_L9:
                if(myLocationOverlay.getMyLocation() != null)
                    mc.animateTo(myLocationOverlay.getMyLocation());
                else
                    Toast.makeText(TabMap.this, "Waiting for device location...", 0).show();
                  goto _L17
_L10:
                mc.animateTo(geopoint);
                  goto _L17
_L11:
                if(myLocationOverlay.getMyLocation() != null)
                    mc.animateTo(myLocationOverlay.getMyLocation());
                else
                    Toast.makeText(TabMap.this, "Waiting for device location...", 0).show();
                  goto _L17
                if(data.Group.get(centeringMode.GroupCar_VehicleID) == null)
                    centeringMode.setMode(4);
                else
                    mc.animateTo(Utilities.GetCarGeopoint(((CarData_Group)data.Group.get(centeringMode.GroupCar_VehicleID)).Latitude, ((CarData_Group)data.Group.get(centeringMode.GroupCar_VehicleID)).Longitude));
                  goto _L17
            }

            final TabMap this$0;

            
            {
                this$0 = TabMap.this;
                super();
            }
        }
;
    }

    private void cancelRoute()
    {
        if(centeringMode.getMode() == 4 || centeringMode.getMode() == 3)
            centeringMode.setMode(2);
        clearRoute();
        ((RadioButton)findViewById(0x7f09008d)).setChecked(true);
        refreshUIHandler.sendEmptyMessage(0);
        Toast.makeText(this, "Route Cancelled", 0).show();
    }

    private void clearRoute()
    {
        int i = -1 + mapOverlays.size();
        do
        {
            if(i < 3)
                return;
            mapOverlays.remove(i);
            i--;
        } while(true);
    }

    private void drawRoute(List list, int i)
    {
        Log.d("Route", "Creating overlay");
        if(mapOverlays.size() > 3)
            clearRoute();
        int j = 1;
        do
        {
            if(j >= list.size())
                return;
            mapOverlays.add(new RouteOverlay((GeoPoint)list.get(j - 1), (GeoPoint)list.get(j), i));
            j++;
        } while(true);
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
        SlidingDrawer slidingdrawer = (SlidingDrawer)findViewById(0x7f090084);
        if(slidingdrawer.isOpened())
            slidingdrawer.close();
    }

    private void initPopup()
    {
        SlidingDrawer slidingdrawer = (SlidingDrawer)findViewById(0x7f090084);
        slidingdrawer.setOnDrawerOpenListener(new android.widget.SlidingDrawer.OnDrawerOpenListener() {

            public void onDrawerOpened()
            {
                ((TextView)findViewById(0x7f090086)).setText("\u25BC Close Panel \u25BC");
            }

            final TabMap this$0;

            
            {
                this$0 = TabMap.this;
                super();
            }
        }
);
        slidingdrawer.setOnDrawerCloseListener(new android.widget.SlidingDrawer.OnDrawerCloseListener() {

            public void onDrawerClosed()
            {
                ((TextView)findViewById(0x7f090086)).setText("\u25B2 Map Options \u25B2");
            }

            final TabMap this$0;

            
            {
                this$0 = TabMap.this;
                super();
            }
        }
);
    }

    private void planRoute()
    {
        Toast.makeText(this, "Routing...", 0).show();
        try
        {
            Thread.sleep(100L);
        }
        catch(InterruptedException interruptedexception)
        {
            interruptedexception.printStackTrace();
        }
        (new Thread() {

            public void run()
            {
                Log.d("Route", "Starting routing thread");
                updateRoute();
                centeringMode.setMode(3);
                refreshUIHandler.post(initializeMapCentering);
            }

            final TabMap this$0;

            
            {
                this$0 = TabMap.this;
                super();
            }
        }
).start();
    }

    private void showGroupCarPopup(String s)
    {
        if(data.Group != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        CarData_Group cardata_group;
        Iterator iterator;
        cardata_group = null;
        iterator = data.Group.values().iterator();
_L8:
        if(iterator.hasNext()) goto _L4; else goto _L3
_L3:
        final CarData_Group groupCar;
        View view;
        final Spinner spin;
        TextView textview;
        TextView textview1;
        TextView textview2;
        TextView textview3;
        groupCar = cardata_group;
        view = LayoutInflater.from(this).inflate(0x7f030011, null);
        spin = (Spinner)view.findViewById(0x7f09008f);
        textview = (TextView)view.findViewById(0x7f090090);
        textview1 = (TextView)view.findViewById(0x7f090091);
        textview2 = (TextView)view.findViewById(0x7f090092);
        textview3 = (TextView)view.findViewById(0x7f090093);
        spin.setAdapter(availableCarColorsSpinnerAdapter);
        if(groupCar.VehicleImageDrawable != null && groupCar.VehicleImageDrawable.length() != 0) goto _L6; else goto _L5
_L5:
        spin.setSelection(0);
_L9:
        Object aobj[] = new Object[1];
        aobj[0] = Double.valueOf(groupCar.SOC);
        textview.setText(String.format("%s%%", aobj));
        Object aobj1[] = new Object[2];
        aobj1[0] = Double.valueOf(groupCar.Speed);
        CarData_Group cardata_group1;
        String s1;
        Object aobj2[];
        String s2;
        Object aobj3[];
        String s3;
        String s4;
        android.app.AlertDialog.Builder builder;
        Object aobj4[];
        android.app.AlertDialog.Builder builder1;
        android.content.DialogInterface.OnClickListener onclicklistener;
        android.app.AlertDialog.Builder builder2;
        android.content.DialogInterface.OnClickListener onclicklistener1;
        AlertDialog alertdialog;
        int i;
        if(data.Data_DistanceUnit.equals("K"))
            s1 = "kph";
        else
            s1 = "mph";
        aobj1[1] = s1;
        textview1.setText(String.format("%s %s", aobj1));
        aobj2 = new Object[3];
        aobj2[0] = Double.valueOf(groupCar.Direction);
        aobj2[1] = Double.valueOf(groupCar.Altitude);
        if(data.Data_DistanceUnit.equals("K"))
            s2 = "m";
        else
            s2 = "ft";
        aobj2[2] = s2;
        textview2.setText(String.format("%s' %s%s", aobj2));
        aobj3 = new Object[2];
        if(groupCar.GPSLocked)
            s3 = "LOCK";
        else
            s3 = "Searching...";
        aobj3[0] = s3;
        if(groupCar.GPSDataStale)
            s4 = "(STALE)";
        else
            s4 = "";
        aobj3[1] = s4;
        textview3.setText(String.format("%s %s", aobj3));
        builder = new android.app.AlertDialog.Builder(this);
        aobj4 = new Object[2];
        aobj4[0] = groupCar.VehicleID;
        aobj4[1] = data.Data_Parameters.get(Integer.valueOf(11));
        builder1 = builder.setTitle(String.format("%s (%s)", aobj4)).setView(view).setCancelable(true);
        onclicklistener = new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int j)
            {
                if(groupCar.VehicleImageDrawable == null || !groupCar.VehicleImageDrawable.equals(((HashMap)availableCarColors.get(spin.getSelectedItemPosition())).get("Name").toString()))
                {
                    groupCar.VehicleImageDrawable = ((HashMap)availableCarColors.get(spin.getSelectedItemPosition())).get("Name").toString();
                    ((OVMSActivity)getParent()).saveCars();
                }
                Toast.makeText(TabMap.this, (new StringBuilder("Locating ")).append(groupCar.VehicleID).toString(), 0).show();
                centeringMode.GroupCar_VehicleID = groupCar.VehicleID;
                centeringMode.setMode(5);
                refreshUIHandler.post(initializeMapCentering);
                dialoginterface.dismiss();
                hidePopup();
            }

            final TabMap this$0;
            private final CarData_Group val$groupCar;
            private final Spinner val$spin;

            
            {
                this$0 = TabMap.this;
                groupCar = cardata_group;
                spin = spinner;
                super();
            }
        }
;
        builder2 = builder1.setPositiveButton("Goto", onclicklistener);
        onclicklistener1 = new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int j)
            {
                if(groupCar.VehicleImageDrawable == null || !groupCar.VehicleImageDrawable.equals(((HashMap)availableCarColors.get(spin.getSelectedItemPosition())).get("Name").toString()))
                {
                    groupCar.VehicleImageDrawable = ((HashMap)availableCarColors.get(spin.getSelectedItemPosition())).get("Name").toString();
                    ((OVMSActivity)getParent()).saveCars();
                    refreshUIHandler.sendEmptyMessage(0);
                }
                dialoginterface.dismiss();
            }

            final TabMap this$0;
            private final CarData_Group val$groupCar;
            private final Spinner val$spin;

            
            {
                this$0 = TabMap.this;
                groupCar = cardata_group;
                spin = spinner;
                super();
            }
        }
;
        builder2.setNegativeButton("Close", onclicklistener1);
        alertdialog = builder.create();
        if(!isFinishing())
            alertdialog.show();
          goto _L1
_L4:
        cardata_group1 = (CarData_Group)iterator.next();
        if(!cardata_group1.VehicleID.equals(s)) goto _L8; else goto _L7
_L7:
        cardata_group = cardata_group1;
          goto _L3
_L6:
        i = 0;
_L10:
        if(i < availableCarColors.size())
        {
label0:
            {
                if(!((HashMap)availableCarColors.get(i)).get("Name").toString().equals(groupCar.VehicleImageDrawable))
                    break label0;
                spin.setSelection(i);
            }
        }
          goto _L9
        i++;
          goto _L10
    }

    private void showPopup()
    {
        ((SlidingDrawer)findViewById(0x7f090084)).open();
    }

    private void updateLastUpdatedView()
    {
    }

    private void updateRoute()
    {
        myLocationOverlay.disableMyLocation();
        List list = getRouteGeoPoints();
        if(list != null)
            drawRoute(list, 0xff00ff00);
        else
            runOnUiThread(routeError);
        refreshUIHandler.postDelayed(myLocationEnable, 200L);
        Log.d("Route", "Route complete");
    }

    public void Refresh(CarData cardata, boolean flag)
    {
        data = cardata;
        isLoggedIn = flag;
        if(data.Group == null)
            data.Group = new HashMap();
        if(!currentVehicleID.equals(cardata.VehicleID))
        {
            currentVehicleID = cardata.VehicleID;
            carMarkers = new Utilities.CarMarkerOverlay(getResources().getDrawable(getResources().getIdentifier((new StringBuilder(String.valueOf(data.VehicleImageDrawable))).append("32x32").toString(), "drawable", "com.openvehicles.OVMS")), 20, this, DirectionalMarker, 1);
            mapOverlays.set(0, carMarkers);
            carMarkers.addOnGroupCarTappedListener(new Utilities.OnGroupCarTappedListener() {

                public void OnGroupCarTapped(String s)
                {
                    showGroupCarPopup(s);
                }

                final TabMap this$0;

            
            {
                this$0 = TabMap.this;
                super();
            }
            }
);
        }
        refreshUIHandler.sendEmptyMessage(0);
    }

    public String getMapKMLUrl(GeoPoint geopoint, GeoPoint geopoint1, boolean flag)
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("http://maps.google.com/maps?f=d&hl=en");
        stringbuilder.append("&saddr=");
        stringbuilder.append(Double.toString((double)geopoint.getLatitudeE6() / 1000000D));
        stringbuilder.append(",");
        stringbuilder.append(Double.toString((double)geopoint.getLongitudeE6() / 1000000D));
        stringbuilder.append("&daddr=");
        stringbuilder.append(Double.toString((double)geopoint1.getLatitudeE6() / 1000000D));
        stringbuilder.append(",");
        stringbuilder.append(Double.toString((double)geopoint1.getLongitudeE6() / 1000000D));
        stringbuilder.append("&ie=UTF8&0&om=0&output=kml");
        if(flag)
            stringbuilder.append("&dirflg=w");
        return stringbuilder.toString();
    }

    protected boolean isRouteDisplayed()
    {
        return false;
    }

    public void onBackPressed()
    {
        SlidingDrawer slidingdrawer = (SlidingDrawer)findViewById(0x7f090084);
        if(slidingdrawer.isOpened())
            slidingdrawer.close();
        else
            super.onBackPressed();
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f030010);
        mapView = (MapView)findViewById(0x7f090082);
        mc = mapView.getController();
        mapView.setBuiltInZoomControls(true);
        centeringMode = new mapCenteringMode(null);
        centeringMode.setOnMapCenteringModeChangedListener(new OnCenteringModeChangedListener() {

            public void OnCenteringModeChanged(int j)
            {
                refreshUIHandler.post(updateCenteringOptions);
            }

            final TabMap this$0;

            
            {
                this$0 = TabMap.this;
                super();
            }
        }
);
        DirectionalMarker = BitmapFactory.decodeResource(getResources(), 0x7f020008);
        mapOverlays = mapView.getOverlays();
        carMarkers = new Utilities.CarMarkerOverlay(getResources().getDrawable(0x7f02001e), 20, this, DirectionalMarker, 1);
        mapOverlays.add(0, carMarkers);
        myLocationOverlay = new MyLocationOverlayCustom(this, mapView);
        mapOverlays.add(1, myLocationOverlay);
        TouchOverlay touchoverlay = new TouchOverlay(null);
        mapOverlays.add(2, touchoverlay);
        locationManager = (LocationManager)getSystemService("location");
        locationListener = new LocationListener() {

            public void onLocationChanged(Location location)
            {
                int j = (int)(1000000D * location.getLatitude());
                int k = (int)(1000000D * location.getLongitude());
                lastKnownDeviceGeoPoint = new GeoPoint(j, k);
                Object aobj1[] = new Object[2];
                aobj1[0] = Integer.valueOf(j);
                aobj1[1] = Integer.valueOf(k);
                Log.d("GPS", String.format("lat: %s lng %s", aobj1));
                if(centeringMode.getMode() == 1)
                    refreshUIHandler.sendEmptyMessage(0);
            }

            public void onProviderDisabled(String s)
            {
            }

            public void onProviderEnabled(String s)
            {
            }

            public void onStatusChanged(String s, int j, Bundle bundle1)
            {
            }

            final TabMap this$0;

            
            {
                this$0 = TabMap.this;
                super();
            }
        }
;
        RadioButton radiobutton = (RadioButton)findViewById(0x7f090087);
        radiobutton.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                if(((RadioButton)view).isChecked())
                {
                    Toast.makeText(TabMap.this, "Car Location", 0).show();
                    centeringMode.setMode(2);
                    refreshUIHandler.post(initializeMapCentering);
                    hidePopup();
                }
            }

            final TabMap this$0;

            
            {
                this$0 = TabMap.this;
                super();
            }
        }
);
        radiobutton.setChecked(true);
        ((RadioButton)findViewById(0x7f090088)).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                if(((RadioButton)view).isChecked())
                {
                    if(lastKnownDeviceGeoPoint == null)
                        Toast.makeText(TabMap.this, "Waiting for device location...", 0).show();
                    else
                        Toast.makeText(TabMap.this, "Your Location", 0).show();
                    centeringMode.setMode(1);
                    refreshUIHandler.post(initializeMapCentering);
                    hidePopup();
                }
            }

            final TabMap this$0;

            
            {
                this$0 = TabMap.this;
                super();
            }
        }
);
        ((RadioButton)findViewById(0x7f090089)).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                if(((RadioButton)view).isChecked())
                {
                    if(mapOverlays.size() > 3)
                        Toast.makeText(TabMap.this, "Fitting Route", 0).show();
                    centeringMode.setMode(3);
                    refreshUIHandler.post(initializeMapCentering);
                    hidePopup();
                }
            }

            final TabMap this$0;

            
            {
                this$0 = TabMap.this;
                super();
            }
        }
);
        ((RadioButton)findViewById(0x7f09008c)).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                if(((RadioButton)view).isChecked())
                {
                    if(mapOverlays.size() > 3)
                        clearRoute();
                    if(lastKnownDeviceGeoPoint == null)
                    {
                        ((RadioButton)findViewById(0x7f09008d)).setChecked(true);
                        Toast.makeText(TabMap.this, "Waiting for device location...", 0).show();
                    } else
                    {
                        planRoute();
                        hidePopup();
                    }
                }
            }

            final TabMap this$0;

            
            {
                this$0 = TabMap.this;
                super();
            }
        }
);
        ((RadioButton)findViewById(0x7f09008b)).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                if(((RadioButton)view).isChecked())
                {
                    if(mapOverlays.size() > 3)
                        clearRoute();
                    if(lastKnownDeviceGeoPoint == null)
                    {
                        Toast.makeText(TabMap.this, "Waiting for device location...", 0).show();
                    } else
                    {
                        planRoute();
                        hidePopup();
                    }
                }
            }

            final TabMap this$0;

            
            {
                this$0 = TabMap.this;
                super();
            }
        }
);
        RadioButton radiobutton1 = (RadioButton)findViewById(0x7f09008d);
        radiobutton1.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                if(((RadioButton)view).isChecked())
                {
                    cancelRoute();
                    hidePopup();
                }
            }

            final TabMap this$0;

            
            {
                this$0 = TabMap.this;
                super();
            }
        }
);
        radiobutton1.setChecked(true);
        ((ListView)findViewById(0x7f09008e)).setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView adapterview, View view, int j, long l)
            {
                TextView textview = (TextView)view.findViewById(0x7f090095);
                showGroupCarPopup(textview.getText().toString());
            }

            final TabMap this$0;

            
            {
                this$0 = TabMap.this;
                super();
            }
        }
);
        initPopup();
        String as[] = new String[23];
        as[0] = "car_roadster_arcticwhite";
        as[1] = "car_roadster_brilliantyellow";
        as[2] = "car_roadster_electricblue";
        as[3] = "car_roadster_fushionred";
        as[4] = "car_roadster_glacierblue";
        as[5] = "car_roadster_jetblack";
        as[6] = "car_roadster_lightninggreen";
        as[7] = "car_roadster_obsidianblack";
        as[8] = "car_roadster_racinggreen";
        as[9] = "car_roadster_radiantred";
        as[10] = "car_roadster_sterlingsilver";
        as[11] = "car_roadster_thundergray";
        as[12] = "car_roadster_twilightblue";
        as[13] = "car_roadster_veryorange";
        as[14] = "car_models_anzabrown";
        as[15] = "car_models_catalinawhite";
        as[16] = "car_models_montereyblue";
        as[17] = "car_models_sansimeonsilver";
        as[18] = "car_models_sequolagreen";
        as[19] = "car_models_shastapearlwhite";
        as[20] = "car_models_sierrablack";
        as[21] = "car_models_signaturered";
        as[22] = "car_models_tiburongrey";
        availableCarColors = new ArrayList();
        int i = 0;
        do
        {
            if(i >= as.length)
            {
                ArrayList arraylist = availableCarColors;
                String as1[] = new String[1];
                as1[0] = "Icon";
                int ai[] = new int[1];
                ai[0] = 0x7f090038;
                availableCarColorsSpinnerAdapter = new CustomSpinnerAdapter(this, arraylist, 0x7f03000b, as1, ai);
                return;
            }
            HashMap hashmap = new HashMap();
            hashmap.put("Name", as[i]);
            Resources resources = getResources();
            Object aobj[] = new Object[1];
            aobj[0] = as[i];
            hashmap.put("Icon", Integer.valueOf(resources.getIdentifier(String.format("%s96x44", aobj), "drawable", "com.openvehicles.OVMS")));
            availableCarColors.add(hashmap);
            i++;
        } while(true);
    }

    protected void onPause()
    {
        try
        {
            Log.d("GPS", "OFF");
            locationManager.removeUpdates(locationListener);
            myLocationOverlay.disableCompass();
            myLocationOverlay.disableMyLocation();
        }
        catch(Exception exception) { }
        lastUpdateTimerHandler.removeCallbacks(lastUpdateTimer);
        super.onPause();
    }

    public void onRestoreInstanceState(Bundle bundle)
    {
        super.onRestoreInstanceState(bundle);
        planWalkingDirection = bundle.getBoolean("planWalkingDirection");
        centeringMode.setMode(bundle.getInt("centeringMode"));
    }

    protected void onResume()
    {
        super.onResume();
        if(locationManager.isProviderEnabled("gps"))
        {
            Log.d("GPS", "ON");
            locationManager.requestLocationUpdates("network", 5000L, 5F, locationListener);
            locationManager.requestLocationUpdates("gps", 5000L, 5F, locationListener);
        }
        lastKnownDeviceGeoPoint = null;
        myLocationOverlay.enableCompass();
        myLocationOverlay.enableMyLocation();
        lastUpdateTimerHandler.postDelayed(lastUpdateTimer, 5000L);
    }

    public void onSaveInstanceState(Bundle bundle)
    {
        bundle.putBoolean("planWalkingDirection", planWalkingDirection);
        bundle.putInt("centeringMode", centeringMode.getMode());
        super.onSaveInstanceState(bundle);
    }

    private final int CAR_MARKER_ANIMATION_DURATION_MS = 2000;
    private final int CAR_MARKER_ANIMATION_FRAMES = 40;
    private Bitmap DirectionalMarker;
    private final int LABEL_SHADOW_XY = 1;
    private final int LABEL_TEXT_SIZE = 20;
    private final int SYSTEM_OVERLAY_COUNT = 3;
    private Runnable animateCarMarker;
    private ArrayList availableCarColors;
    private CustomSpinnerAdapter availableCarColorsSpinnerAdapter;
    private int carMarkerAnimationFrame;
    private Handler carMarkerAnimationTimerHandler;
    private Utilities.CarMarkerOverlay carMarkers;
    mapCenteringMode centeringMode;
    private String currentVehicleID;
    private CarData data;
    private GroupCarsListViewAdapter groupCarsListAdapter;
    final Runnable initializeMapCentering = new Runnable() {

        public void run()
        {
            GeoPoint geopoint;
            Log.d("OVMS", "Centering Map");
            geopoint = Utilities.GetCarGeopoint(data);
            centeringMode.getMode();
            JVM INSTR tableswitch 1 5: default 64
        //                       1 99
        //                       2 148
        //                       3 175
        //                       4 88
        //                       5 561;
               goto _L1 _L2 _L3 _L4 _L5 _L6
_L5:
            break; /* Loop/switch isn't completed */
_L1:
            mc.animateTo(geopoint);
            mc.setZoom(18);
_L8:
            mapView.invalidate();
            return;
_L2:
            if(myLocationOverlay.getMyLocation() != null)
                mc.animateTo(myLocationOverlay.getMyLocation());
            mc.setZoom(17);
            continue; /* Loop/switch isn't completed */
_L3:
            mc.animateTo(geopoint);
            mc.setZoom(17);
            continue; /* Loop/switch isn't completed */
_L4:
            if(mapOverlays.size() <= 3)
            {
                centeringMode.setMode(2);
                mc.animateTo(geopoint);
                mc.setZoom(17);
                continue; /* Loop/switch isn't completed */
            }
            RouteOverlay routeoverlay = (RouteOverlay)mapOverlays.get(3);
            int i = routeoverlay.gp1.getLatitudeE6();
            int j = routeoverlay.gp1.getLongitudeE6();
            int k = routeoverlay.gp1.getLatitudeE6();
            int l = routeoverlay.gp1.getLongitudeE6();
            int i1 = 3;
            do
            {
                if(i1 >= mapOverlays.size())
                {
                    Object aobj[] = new Object[4];
                    aobj[0] = Integer.valueOf(i);
                    aobj[1] = Integer.valueOf(k);
                    aobj[2] = Integer.valueOf(j);
                    aobj[3] = Integer.valueOf(l);
                    Log.d("Map", String.format("Zoom Span: %s %s %s %s", aobj));
                    mapView.getController().zoomToSpan(100 + (k - i), 100 + (l - j));
                    mapView.getController().animateTo(new GeoPoint((k + i) / 2, (l + j) / 2));
                    ((RadioButton)findViewById(0x7f09008d)).setChecked(false);
                    continue; /* Loop/switch isn't completed */
                }
                RouteOverlay routeoverlay1 = (RouteOverlay)mapOverlays.get(i1);
                if(i > routeoverlay1.gp1.getLatitudeE6())
                    i = routeoverlay1.gp1.getLatitudeE6();
                if(k < routeoverlay1.gp1.getLatitudeE6())
                    k = routeoverlay1.gp1.getLatitudeE6();
                if(j > routeoverlay1.gp1.getLongitudeE6())
                    j = routeoverlay1.gp1.getLongitudeE6();
                if(l < routeoverlay1.gp1.getLongitudeE6())
                    l = routeoverlay1.gp1.getLongitudeE6();
                i1++;
            } while(true);
_L6:
            if(data.Group.get(centeringMode.GroupCar_VehicleID) == null)
            {
                centeringMode.setMode(4);
            } else
            {
                mc.animateTo(Utilities.GetCarGeopoint(((CarData_Group)data.Group.get(centeringMode.GroupCar_VehicleID)).Latitude, ((CarData_Group)data.Group.get(centeringMode.GroupCar_VehicleID)).Longitude));
                mc.setZoom(18);
            }
            if(true) goto _L8; else goto _L7
_L7:
        }

        final TabMap this$0;

            
            {
                this$0 = TabMap.this;
                super();
            }
    }
;
    private boolean isLoggedIn;
    private GeoPoint lastCarGeoPoints[];
    private GeoPoint lastKnownDeviceGeoPoint;
    private Runnable lastUpdateTimer;
    private Handler lastUpdateTimerHandler;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private float mapDragLastX;
    private float mapDragLastY;
    private List mapOverlays;
    private MapView mapView;
    private MapController mc;
    private Runnable myLocationDisable;
    private Runnable myLocationEnable;
    private MyLocationOverlayCustom myLocationOverlay;
    private boolean planWalkingDirection;
    private Handler refreshUIHandler;
    private Runnable routeError;
    final Runnable updateCenteringOptions = new Runnable() {

        public void run()
        {
            centeringMode.getMode();
            JVM INSTR tableswitch 0 5: default 48
        //                       0 49
        //                       1 68
        //                       2 49
        //                       3 87
        //                       4 106
        //                       5 106;
               goto _L1 _L2 _L3 _L2 _L4 _L5 _L5
_L1:
            return;
_L2:
            ((RadioButton)findViewById(0x7f090087)).setChecked(true);
            continue; /* Loop/switch isn't completed */
_L3:
            ((RadioButton)findViewById(0x7f090088)).setChecked(true);
            continue; /* Loop/switch isn't completed */
_L4:
            ((RadioButton)findViewById(0x7f090089)).setChecked(true);
            continue; /* Loop/switch isn't completed */
_L5:
            ((RadioButton)findViewById(0x7f09008a)).setChecked(true);
            if(true) goto _L1; else goto _L6
_L6:
        }

        final TabMap this$0;

            
            {
                this$0 = TabMap.this;
                super();
            }
    }
;































}
