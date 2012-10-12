// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.achartengine.model;

import java.io.Serializable;

public final class Point
    implements Serializable
{

    public Point()
    {
    }

    public Point(float f, float f1)
    {
        mX = f;
        mY = f1;
    }

    public float getX()
    {
        return mX;
    }

    public float getY()
    {
        return mY;
    }

    public void setX(float f)
    {
        mX = f;
    }

    public void setY(float f)
    {
        mY = f;
    }

    private float mX;
    private float mY;
}
