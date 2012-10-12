// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.openvehicles.OVMS;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import com.google.android.maps.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.openvehicles.OVMS:
//            RefreshStatusCallBack, CarData

public class TabMap extends MapActivity
    implements RefreshStatusCallBack
{
    private class CarMarkerOverlay extends ItemizedOverlay
    {

        public void addOverlay(OverlayItem overlayitem)
        {
            mOverlays.add(overlayitem);
            populate();
        }

        public void clearItems()
        {
            mOverlays.clear();
        }

        protected OverlayItem createItem(int i)
        {
            return (OverlayItem)mOverlays.get(i);
        }

        protected boolean onTap(int i)
        {
            OverlayItem overlayitem = (OverlayItem)mOverlays.get(i);
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
            builder.setTitle(overlayitem.getTitle());
            builder.setMessage(overlayitem.getSnippet());
            builder.show();
            return true;
        }

        public int size()
        {
            return mOverlays.size();
        }

        private Context mContext;
        private ArrayList mOverlays;
        final TabMap this$0;

        public CarMarkerOverlay(Drawable drawable, Context context)
        {
            this$0 = TabMap.this;
            super(boundCenterBottom(drawable));
            mOverlays = new ArrayList();
            mContext = context;
        }
    }


    public TabMap()
    {
        handler = new Handler() {

            public void handleMessage(Message message)
            {
                Log.d("OVMS", "Centering Map");
                GeoPoint geopoint = new GeoPoint((int)(1000000D * data.Data_Latitude), (int)(1000000D * data.Data_Longitude));
                carMarkers.clearItems();
                String s = "-";
                if(data.Data_LastCarUpdate != null)
                    s = (new SimpleDateFormat("MMM d, k:mm:ss")).format(data.Data_LastCarUpdate);
                String s1 = data.VehicleID;
                Object aobj[] = new Object[1];
                aobj[0] = s;
                OverlayItem overlayitem = new OverlayItem(geopoint, s1, String.format("Last reported: %s", aobj));
                carMarkers.addOverlay(overlayitem);
                mc.setCenter(geopoint);
                mc.setZoom(18);
                mapView.invalidate();
            }

            final TabMap this$0;

            
            {
                this$0 = TabMap.this;
                super();
            }
        }
;
    }

    public void RefreshStatus(CarData cardata)
    {
        data = cardata;
        mapOverlays = mapView.getOverlays();
        carMarkers = new CarMarkerOverlay(getResources().getDrawable(getResources().getIdentifier((new StringBuilder()).append(data.VehicleImageDrawable).append("32x32").toString(), "drawable", "com.openvehicles.OVMS")), this);
        mapOverlays.clear();
        mapOverlays.add(carMarkers);
        handler.sendEmptyMessage(0);
    }

    protected boolean isRouteDisplayed()
    {
        return false;
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f030009);
        ((ImageButton)findViewById(0x7f06003c)).setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                handler.sendEmptyMessage(0);
            }

            final TabMap this$0;

            
            {
                this$0 = TabMap.this;
                super();
            }
        }
);
        mapView = (MapView)findViewById(0x7f06003b);
        mc = mapView.getController();
        mapView.setBuiltInZoomControls(true);
    }

    private CarMarkerOverlay carMarkers;
    private CarData data;
    private Handler handler;
    private List mapOverlays;
    private MapView mapView;
    private MapController mc;





}
