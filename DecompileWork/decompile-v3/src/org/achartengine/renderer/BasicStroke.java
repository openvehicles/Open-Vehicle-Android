// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.achartengine.renderer;

import java.io.Serializable;

public class BasicStroke
    implements Serializable
{

    public BasicStroke(android.graphics.Paint.Cap cap, android.graphics.Paint.Join join, float f, float af[], float f1)
    {
        mCap = cap;
        mJoin = join;
        mMiter = f;
        mIntervals = af;
    }

    public android.graphics.Paint.Cap getCap()
    {
        return mCap;
    }

    public float[] getIntervals()
    {
        return mIntervals;
    }

    public android.graphics.Paint.Join getJoin()
    {
        return mJoin;
    }

    public float getMiter()
    {
        return mMiter;
    }

    public float getPhase()
    {
        return mPhase;
    }

    public static final BasicStroke DASHED;
    public static final BasicStroke DOTTED;
    public static final BasicStroke SOLID;
    private android.graphics.Paint.Cap mCap;
    private float mIntervals[];
    private android.graphics.Paint.Join mJoin;
    private float mMiter;
    private float mPhase;

    static 
    {
        SOLID = new BasicStroke(android.graphics.Paint.Cap.BUTT, android.graphics.Paint.Join.MITER, 4F, null, 0.0F);
        android.graphics.Paint.Cap cap = android.graphics.Paint.Cap.ROUND;
        android.graphics.Paint.Join join = android.graphics.Paint.Join.BEVEL;
        float af[] = new float[2];
        af[0] = 10F;
        af[1] = 10F;
        DASHED = new BasicStroke(cap, join, 10F, af, 1.0F);
        android.graphics.Paint.Cap cap1 = android.graphics.Paint.Cap.ROUND;
        android.graphics.Paint.Join join1 = android.graphics.Paint.Join.BEVEL;
        float af1[] = new float[2];
        af1[0] = 2.0F;
        af1[1] = 10F;
        DOTTED = new BasicStroke(cap1, join1, 5F, af1, 1.0F);
    }
}
