// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.openvehicles.OVMS;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.google.android.maps.*;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.openvehicles.OVMS:
//            CarData

public final class Utilities
{
    public static class CarMarker extends OverlayItem
    {

        public int Direction;

        public CarMarker(GeoPoint geopoint, String s, String s1, int i)
        {
            super(geopoint, s, s1);
            Direction = i;
        }
    }

    public static class CarMarkerOverlay extends ItemizedOverlay
    {

        /**
         * @deprecated Method fireGroupCarTappedEvent is deprecated
         */

        private void fireGroupCarTappedEvent(String s)
        {
            this;
            JVM INSTR monitorenter ;
            Iterator iterator = _listeners.iterator();
_L1:
            boolean flag = iterator.hasNext();
            if(flag)
                break MISSING_BLOCK_LABEL_26;
            this;
            JVM INSTR monitorexit ;
            return;
            ((OnGroupCarTappedListener)iterator.next()).OnGroupCarTapped(s);
              goto _L1
            Exception exception;
            exception;
            throw exception;
        }

        /**
         * @deprecated Method addOnGroupCarTappedListener is deprecated
         */

        public void addOnGroupCarTappedListener(OnGroupCarTappedListener ongroupcartappedlistener)
        {
            this;
            JVM INSTR monitorenter ;
            _listeners.add(ongroupcartappedlistener);
            this;
            JVM INSTR monitorexit ;
            return;
            Exception exception;
            exception;
            throw exception;
        }

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

        public void draw(Canvas canvas, MapView mapview, boolean flag)
        {
            super.draw(canvas, mapview, flag);
            int i = 0;
            do
            {
                if(i >= mOverlays.size())
                    return;
                CarMarker carmarker = (CarMarker)mOverlays.get(i);
                GeoPoint geopoint = carmarker.getPoint();
                Point point = new Point();
                mapview.getProjection().toPixels(geopoint, point);
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setTextAlign(android.graphics.Paint.Align.CENTER);
                paint.setTextSize(mLabelTextSize);
                if(flag)
                {
                    paint.setARGB(100, 0, 0, 0);
                    canvas.drawText(carmarker.getTitle(), point.x + LABEL_SHADOW_XY, -32 + point.y + LABEL_SHADOW_XY, paint);
                } else
                {
                    paint.setARGB(255, 0, 0, 0);
                    canvas.drawText(carmarker.getTitle(), point.x, -32 + point.y, paint);
                    canvas.drawBitmap(Utilities.GetRotatedDirectionalMarker(DirectionalMarker, carmarker.Direction), -55 + point.x, -75 + point.y, paint);
                }
                i++;
            } while(true);
        }

        protected boolean onTap(int i)
        {
            OverlayItem overlayitem = (OverlayItem)mOverlays.get(i);
            if(overlayitem.getSnippet().length() > 0)
            {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
                builder.setTitle(overlayitem.getTitle());
                builder.setMessage(overlayitem.getSnippet());
                builder.show();
            } else
            {
                fireGroupCarTappedEvent(overlayitem.getTitle());
            }
            return true;
        }

        /**
         * @deprecated Method removeOnGroupCarTappedListener is deprecated
         */

        public void removeOnGroupCarTappedListener(OnGroupCarTappedListener ongroupcartappedlistener)
        {
            this;
            JVM INSTR monitorenter ;
            _listeners.remove(ongroupcartappedlistener);
            this;
            JVM INSTR monitorexit ;
            return;
            Exception exception;
            exception;
            throw exception;
        }

        public void removeOverlayAt(int i)
        {
            mOverlays.remove(i);
        }

        public void setOverlay(int i, OverlayItem overlayitem)
        {
            mOverlays.set(i, overlayitem);
            populate();
        }

        public int size()
        {
            return mOverlays.size();
        }

        private Bitmap DirectionalMarker;
        private int LABEL_SHADOW_XY;
        private ArrayList _listeners;
        private Context mContext;
        private int mLabelTextSize;
        private ArrayList mOverlays;

        public CarMarkerOverlay(Drawable drawable, int i, Context context, Bitmap bitmap, int j)
        {
            super(boundCenterBottom(drawable));
            mOverlays = new ArrayList();
            _listeners = new ArrayList();
            mContext = context;
            mLabelTextSize = i;
            DirectionalMarker = bitmap;
            LABEL_SHADOW_XY = j;
        }
    }

    public static interface OnGroupCarTappedListener
    {

        public abstract void OnGroupCarTapped(String s);
    }


    public Utilities()
    {
    }

    public static GeoPoint GetCarGeopoint(double d, double d1)
    {
        return new GeoPoint((int)(d * 1000000D), (int)(d1 * 1000000D));
    }

    public static GeoPoint GetCarGeopoint(CarData cardata)
    {
        return new GeoPoint((int)(1000000D * cardata.Data_Latitude), (int)(1000000D * cardata.Data_Longitude));
    }

    public static double GetDistanceBetweenCoordinatesKM(double d, double d1, double d2, double d3)
    {
        double d4 = Math.toRadians(d2 - d);
        double d5 = Math.toRadians(d3 - d1);
        double d6 = Math.toRadians(d);
        double d7 = Math.toRadians(d2);
        double d8 = Math.sin(d4 / 2D) * Math.sin(d4 / 2D) + Math.sin(d5 / 2D) * Math.sin(d5 / 2D) * Math.cos(d6) * Math.cos(d7);
        return 2D * Math.atan2(Math.sqrt(d8), Math.sqrt(1.0D - d8)) * (double)6371;
    }

    public static Bitmap GetRotatedDirectionalMarker(Bitmap bitmap, float f)
    {
        Bitmap bitmap1 = bitmap.copy(android.graphics.Bitmap.Config.ARGB_8888, true);
        bitmap1.eraseColor(0);
        Canvas canvas = new Canvas(bitmap1);
        Matrix matrix = new Matrix();
        matrix.setRotate(f, canvas.getWidth() / 2, canvas.getHeight() / 2);
        canvas.drawBitmap(bitmap, matrix, null);
        return bitmap1;
    }

    public static Bitmap GetScaledBatteryOverlay(int i, Bitmap bitmap)
    {
        Bitmap bitmap1 = null;
        if(bitmap != null) goto _L2; else goto _L1
_L1:
        Log.d("OVMS", "!!! Battery overlay resource not found !!!");
_L4:
        return bitmap1;
_L2:
        if(i > 0)
        {
            Matrix matrix = new Matrix();
            matrix.postScale((float)i / 100F, 1.0F);
            bitmap1 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        }
        if(true) goto _L4; else goto _L3
_L3:
    }
}
