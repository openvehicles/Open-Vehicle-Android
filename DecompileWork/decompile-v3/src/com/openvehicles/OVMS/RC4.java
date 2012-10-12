// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.openvehicles.OVMS;


public class RC4
{

    public RC4(String s)
        throws NullPointerException
    {
        this(s.getBytes());
    }

    public RC4(byte abyte0[])
        throws NullPointerException
    {
        state = new byte[256];
        int i = 0;
        int j;
        int k;
        do
        {
            if(i >= 256)
            {
                x = 0;
                y = 0;
                j = 0;
                k = 0;
                if(abyte0 == null || abyte0.length == 0)
                    throw new NullPointerException();
                break;
            }
            state[i] = (byte)i;
            i++;
        } while(true);
        int l = 0;
        do
        {
            if(l >= 256)
                return;
            k = 0xff & k + ((0xff & abyte0[j]) + (0xff & state[l]));
            byte byte0 = state[l];
            state[l] = state[k];
            state[k] = byte0;
            j = (j + 1) % abyte0.length;
            l++;
        } while(true);
    }

    public byte[] rc4(String s)
    {
        byte abyte0[];
        if(s == null)
        {
            abyte0 = null;
        } else
        {
            abyte0 = s.getBytes();
            rc4(abyte0);
        }
        return abyte0;
    }

    public byte[] rc4(byte abyte0[])
    {
        byte abyte1[];
        if(abyte0 == null)
        {
            abyte1 = null;
        } else
        {
            abyte1 = new byte[abyte0.length];
            int i = 0;
            while(i < abyte0.length) 
            {
                x = 0xff & 1 + x;
                y = 0xff & (0xff & state[x]) + y;
                byte byte0 = state[x];
                state[x] = state[y];
                state[y] = byte0;
                int j = 0xff & (0xff & state[x]) + (0xff & state[y]);
                abyte1[i] = (byte)(abyte0[i] ^ state[j]);
                i++;
            }
        }
        return abyte1;
    }

    private byte state[];
    private int x;
    private int y;
}
