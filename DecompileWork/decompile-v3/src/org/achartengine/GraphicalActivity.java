// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.achartengine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import org.achartengine.chart.AbstractChart;

// Referenced classes of package org.achartengine:
//            GraphicalView

public class GraphicalActivity extends Activity
{

    public GraphicalActivity()
    {
    }

    protected void onCreate(Bundle bundle)
    {
        String s;
        super.onCreate(bundle);
        Bundle bundle1 = getIntent().getExtras();
        mChart = (AbstractChart)bundle1.getSerializable("chart");
        mView = new GraphicalView(this, mChart);
        s = bundle1.getString("title");
        if(s != null) goto _L2; else goto _L1
_L1:
        requestWindowFeature(1);
_L4:
        setContentView(mView);
        return;
_L2:
        if(s.length() > 0)
            setTitle(s);
        if(true) goto _L4; else goto _L3
_L3:
    }

    private AbstractChart mChart;
    private GraphicalView mView;
}
