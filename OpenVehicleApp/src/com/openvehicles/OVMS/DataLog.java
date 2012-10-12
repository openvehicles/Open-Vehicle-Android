// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.openvehicles.OVMS;

import java.util.LinkedList;

public final class DataLog
{

    public DataLog()
    {
    }

    public static void Log(String s)
    {
        if(log != null) goto _L2; else goto _L1
_L1:
        log = new LinkedList();
_L4:
        log.add(s);
        return;
_L2:
        if(log.size() > 100)
            log.remove();
        if(true) goto _L4; else goto _L3
_L3:
    }

    public static String getLog()
    {
        StringBuilder stringbuilder = new StringBuilder();
        int i = 0;
        do
        {
            if(i >= log.size())
                return stringbuilder.toString();
            stringbuilder.append((new StringBuilder(String.valueOf((String)log.get(i)))).append("\n").toString());
            i++;
        } while(true);
    }

    public static final int LOG_SIZE = 100;
    private static LinkedList log;
}
