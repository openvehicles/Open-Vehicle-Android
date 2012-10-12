// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.openvehicles.OVMS;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Base64
{
    public static class InputStream extends FilterInputStream
    {

        public int read()
            throws IOException
        {
            if(position >= 0) goto _L2; else goto _L1
_L1:
            if(!encode) goto _L4; else goto _L3
_L3:
            byte abyte2[];
            int i1;
            int j1;
            abyte2 = new byte[3];
            i1 = 0;
            j1 = 0;
_L14:
            int j;
            int k1;
            if(j1 < 3)
                if((k1 = in.read()) >= 0)
                {
                    abyte2[j1] = (byte)k1;
                    i1++;
                    j1++;
                    continue; /* Loop/switch isn't completed */
                }
            if(i1 <= 0) goto _L6; else goto _L5
_L5:
            Base64.encode3to4(abyte2, 0, i1, buffer, 0, options);
            position = 0;
            numSigBytes = 4;
_L2:
            if(position < 0) goto _L8; else goto _L7
_L7:
            byte abyte1[];
            int k;
            int l;
            if(position >= numSigBytes)
                j = -1;
            else
            if(encode && breakLines && lineLength >= 76)
            {
                lineLength = 0;
                j = 10;
            } else
            {
                lineLength = 1 + lineLength;
                byte abyte0[] = buffer;
                int i = position;
                position = i + 1;
                byte byte0 = abyte0[i];
                if(position >= bufferLength)
                    position = -1;
                j = byte0 & 0xff;
            }
            return j;
_L6:
            j = -1;
            break MISSING_BLOCK_LABEL_86;
_L4:
            abyte1 = new byte[4];
            k = 0;
_L12:
            if(k < 4) goto _L10; else goto _L9
_L9:
            if(k != 4)
                break MISSING_BLOCK_LABEL_222;
            numSigBytes = Base64.decode4to3(abyte1, 0, buffer, 0, options);
            position = 0;
              goto _L2
_L10:
            do
                l = in.read();
            while(l >= 0 && decodabet[l & 0x7f] <= -5);
            if(l < 0) goto _L9; else goto _L11
_L11:
            abyte1[k] = (byte)l;
            k++;
              goto _L12
            if(k == 0)
                j = -1;
            else
                throw new IOException("Improperly padded Base64 input.");
            break MISSING_BLOCK_LABEL_86;
_L8:
            throw new IOException("Error in Base64 code reading stream.");
            if(true) goto _L14; else goto _L13
_L13:
        }

        public int read(byte abyte0[], int i, int j)
            throws IOException
        {
            int k = 0;
_L3:
            if(k < j) goto _L2; else goto _L1
_L1:
            return k;
_L2:
label0:
            {
                int l = read();
                if(l < 0)
                    break label0;
                abyte0[i + k] = (byte)l;
                k++;
            }
              goto _L3
            if(k == 0)
                k = -1;
              goto _L1
        }

        private boolean breakLines;
        private byte buffer[];
        private int bufferLength;
        private byte decodabet[];
        private boolean encode;
        private int lineLength;
        private int numSigBytes;
        private int options;
        private int position;

        public InputStream(java.io.InputStream inputstream)
        {
            this(inputstream, 0);
        }

        public InputStream(java.io.InputStream inputstream, int i)
        {
            boolean flag = true;
            super(inputstream);
            options = i;
            boolean flag1;
            int j;
            if((i & 8) > 0)
                flag1 = flag;
            else
                flag1 = false;
            breakLines = flag1;
            if((i & 1) <= 0)
                flag = false;
            encode = flag;
            if(encode)
                j = 4;
            else
                j = 3;
            bufferLength = j;
            buffer = new byte[bufferLength];
            position = -1;
            lineLength = 0;
            decodabet = Base64.getDecodabet(i);
        }
    }

    public static class OutputStream extends FilterOutputStream
    {

        public void close()
            throws IOException
        {
            flushBase64();
            super.close();
            buffer = null;
            out = null;
        }

        public void flushBase64()
            throws IOException
        {
label0:
            {
                if(position > 0)
                {
                    if(!encode)
                        break label0;
                    out.write(Base64.encode3to4(b4, buffer, position, options));
                    position = 0;
                }
                return;
            }
            throw new IOException("Base64 input not properly padded.");
        }

        public void resumeEncoding()
        {
            suspendEncoding = false;
        }

        public void suspendEncoding()
            throws IOException
        {
            flushBase64();
            suspendEncoding = true;
        }

        public void write(int i)
            throws IOException
        {
            if(!suspendEncoding) goto _L2; else goto _L1
_L1:
            out.write(i);
_L4:
            return;
_L2:
            if(encode)
            {
                byte abyte1[] = buffer;
                int l = position;
                position = l + 1;
                abyte1[l] = (byte)i;
                if(position >= bufferLength)
                {
                    out.write(Base64.encode3to4(b4, buffer, bufferLength, options));
                    lineLength = 4 + lineLength;
                    if(breakLines && lineLength >= 76)
                    {
                        out.write(10);
                        lineLength = 0;
                    }
                    position = 0;
                }
                continue; /* Loop/switch isn't completed */
            }
            if(decodabet[i & 0x7f] <= -5)
                break; /* Loop/switch isn't completed */
            byte abyte0[] = buffer;
            int j = position;
            position = j + 1;
            abyte0[j] = (byte)i;
            if(position >= bufferLength)
            {
                int k = Base64.decode4to3(buffer, 0, b4, 0, options);
                out.write(b4, 0, k);
                position = 0;
            }
            if(true) goto _L4; else goto _L3
_L3:
            if(decodabet[i & 0x7f] == -5) goto _L4; else goto _L5
_L5:
            throw new IOException("Invalid character in Base64 data.");
        }

        public void write(byte abyte0[], int i, int j)
            throws IOException
        {
            if(suspendEncoding)
            {
                out.write(abyte0, i, j);
            } else
            {
                int k = 0;
                while(k < j) 
                {
                    write(abyte0[i + k]);
                    k++;
                }
            }
        }

        private byte b4[];
        private boolean breakLines;
        private byte buffer[];
        private int bufferLength;
        private byte decodabet[];
        private boolean encode;
        private int lineLength;
        private int options;
        private int position;
        private boolean suspendEncoding;

        public OutputStream(java.io.OutputStream outputstream)
        {
            this(outputstream, 1);
        }

        public OutputStream(java.io.OutputStream outputstream, int i)
        {
            boolean flag = true;
            super(outputstream);
            boolean flag1;
            int j;
            if((i & 8) != 0)
                flag1 = flag;
            else
                flag1 = false;
            breakLines = flag1;
            if((i & 1) == 0)
                flag = false;
            encode = flag;
            if(encode)
                j = 3;
            else
                j = 4;
            bufferLength = j;
            buffer = new byte[bufferLength];
            position = 0;
            lineLength = 0;
            suspendEncoding = false;
            b4 = new byte[4];
            options = i;
            decodabet = Base64.getDecodabet(i);
        }
    }


    private Base64()
    {
    }

    public static byte[] decode(String s)
        throws IOException
    {
        return decode(s, 0);
    }

    public static byte[] decode(String s, int i)
        throws IOException
    {
        if(s == null)
            throw new NullPointerException("Input string was null.");
        byte abyte4[] = s.getBytes("US-ASCII");
        byte abyte0[] = abyte4;
_L5:
        ByteArrayInputStream bytearrayinputstream;
        GZIPInputStream gzipinputstream;
        ByteArrayOutputStream bytearrayoutputstream;
        byte abyte2[];
        ByteArrayOutputStream bytearrayoutputstream1;
        ByteArrayInputStream bytearrayinputstream1;
        GZIPInputStream gzipinputstream1;
        int j;
        byte abyte1[] = decode(abyte0, 0, abyte0.length, i);
        UnsupportedEncodingException unsupportedencodingexception;
        boolean flag;
        byte abyte3[];
        if((i & 4) != 0)
            flag = true;
        else
            flag = false;
        if(abyte1 == null || abyte1.length < 4 || flag || 35615 != (0xff & abyte1[0] | 0xff00 & abyte1[1] << 8)) goto _L2; else goto _L1
_L1:
        bytearrayinputstream = null;
        gzipinputstream = null;
        bytearrayoutputstream = null;
        abyte2 = new byte[2048];
        bytearrayoutputstream1 = new ByteArrayOutputStream();
        bytearrayinputstream1 = new ByteArrayInputStream(abyte1);
        gzipinputstream1 = new GZIPInputStream(bytearrayinputstream1);
_L6:
        j = gzipinputstream1.read(abyte2);
        if(j >= 0) goto _L4; else goto _L3
_L3:
        abyte3 = bytearrayoutputstream1.toByteArray();
        Exception exception;
        IOException ioexception;
        abyte1 = abyte3;
        Exception exception1;
        Exception exception2;
        Exception exception3;
        Exception exception4;
        Exception exception5;
        Exception exception6;
        try
        {
            bytearrayoutputstream1.close();
        }
        catch(Exception exception7) { }
        try
        {
            gzipinputstream1.close();
        }
        catch(Exception exception8) { }
        try
        {
            bytearrayinputstream1.close();
        }
        catch(Exception exception9) { }
_L2:
        return abyte1;
        unsupportedencodingexception;
        abyte0 = s.getBytes();
          goto _L5
_L4:
        bytearrayoutputstream1.write(abyte2, 0, j);
          goto _L6
        ioexception;
        bytearrayoutputstream = bytearrayoutputstream1;
        gzipinputstream = gzipinputstream1;
        bytearrayinputstream = bytearrayinputstream1;
_L9:
        ioexception.printStackTrace();
        try
        {
            bytearrayoutputstream.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception4) { }
        try
        {
            gzipinputstream.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception5) { }
        try
        {
            bytearrayinputstream.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception6) { }
          goto _L2
        exception;
_L8:
        try
        {
            bytearrayoutputstream.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception1) { }
        try
        {
            gzipinputstream.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception2) { }
        try
        {
            bytearrayinputstream.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception3) { }
        throw exception;
        exception;
        bytearrayoutputstream = bytearrayoutputstream1;
        continue; /* Loop/switch isn't completed */
        exception;
        bytearrayoutputstream = bytearrayoutputstream1;
        bytearrayinputstream = bytearrayinputstream1;
        continue; /* Loop/switch isn't completed */
        exception;
        bytearrayoutputstream = bytearrayoutputstream1;
        gzipinputstream = gzipinputstream1;
        bytearrayinputstream = bytearrayinputstream1;
        if(true) goto _L8; else goto _L7
_L7:
        ioexception;
          goto _L9
        ioexception;
        bytearrayoutputstream = bytearrayoutputstream1;
          goto _L9
        ioexception;
        bytearrayoutputstream = bytearrayoutputstream1;
        bytearrayinputstream = bytearrayinputstream1;
          goto _L9
    }

    public static byte[] decode(byte abyte0[])
        throws IOException
    {
        return decode(abyte0, 0, abyte0.length, 0);
    }

    public static byte[] decode(byte abyte0[], int i, int j, int k)
        throws IOException
    {
        if(abyte0 == null)
            throw new NullPointerException("Cannot decode null source array.");
        if(i < 0 || i + j > abyte0.length)
        {
            Object aobj[] = new Object[3];
            aobj[0] = Integer.valueOf(abyte0.length);
            aobj[1] = Integer.valueOf(i);
            aobj[2] = Integer.valueOf(j);
            throw new IllegalArgumentException(String.format("Source array with length %d cannot have offset of %d and process %d bytes.", aobj));
        }
        if(j != 0) goto _L2; else goto _L1
_L1:
        byte abyte4[] = new byte[0];
_L5:
        return abyte4;
_L2:
        byte abyte1[];
        byte abyte2[];
        int l;
        byte abyte3[];
        int i1;
        int j1;
        if(j < 4)
            throw new IllegalArgumentException((new StringBuilder("Base64-encoded string must have at least four characters, but length specified was ")).append(j).toString());
        abyte1 = getDecodabet(k);
        abyte2 = new byte[(j * 3) / 4];
        l = 0;
        abyte3 = new byte[4];
        i1 = i;
        j1 = 0;
_L10:
        if(i1 < i + j) goto _L4; else goto _L3
_L3:
        j1;
_L9:
        abyte4 = new byte[l];
        System.arraycopy(abyte2, 0, abyte4, 0, l);
          goto _L5
_L4:
        byte byte0 = abyte1[0xff & abyte0[i1]];
        if(byte0 < -5) goto _L7; else goto _L6
_L6:
        int k1;
        if(byte0 < -1)
            break MISSING_BLOCK_LABEL_305;
        k1 = j1 + 1;
        abyte3[j1] = abyte0[i1];
        if(k1 <= 3)
            break; /* Loop/switch isn't completed */
        l += decode4to3(abyte3, 0, abyte2, l, k);
        k1 = 0;
        if(abyte0[i1] == 61) goto _L9; else goto _L8
_L8:
        i1++;
        j1 = k1;
          goto _L10
_L7:
        Object aobj1[] = new Object[2];
        aobj1[0] = Integer.valueOf(0xff & abyte0[i1]);
        aobj1[1] = Integer.valueOf(i1);
        throw new IOException(String.format("Bad Base64 input character decimal %d in array position %d", aobj1));
        k1 = j1;
          goto _L8
    }

    private static int decode4to3(byte abyte0[], int i, byte abyte1[], int j, int k)
    {
        int l = 1;
        if(abyte0 == null)
            throw new NullPointerException("Source array was null.");
        if(abyte1 == null)
            throw new NullPointerException("Destination array was null.");
        if(i < 0 || i + 3 >= abyte0.length)
        {
            Object aobj[] = new Object[2];
            aobj[0] = Integer.valueOf(abyte0.length);
            aobj[l] = Integer.valueOf(i);
            throw new IllegalArgumentException(String.format("Source array with length %d cannot have offset of %d and still process four bytes.", aobj));
        }
        if(j < 0 || j + 2 >= abyte1.length)
        {
            Object aobj1[] = new Object[2];
            aobj1[0] = Integer.valueOf(abyte1.length);
            aobj1[l] = Integer.valueOf(j);
            throw new IllegalArgumentException(String.format("Destination array with length %d cannot have offset of %d and still store three bytes.", aobj1));
        }
        byte abyte2[] = getDecodabet(k);
        if(abyte0[i + 2] == 61)
            abyte1[j] = (byte)(((0xff & abyte2[abyte0[i]]) << 18 | (0xff & abyte2[abyte0[i + 1]]) << 12) >>> 16);
        else
        if(abyte0[i + 3] == 61)
        {
            int j1 = (0xff & abyte2[abyte0[i]]) << 18 | (0xff & abyte2[abyte0[i + 1]]) << 12 | (0xff & abyte2[abyte0[i + 2]]) << 6;
            abyte1[j] = (byte)(j1 >>> 16);
            abyte1[j + 1] = (byte)(j1 >>> 8);
            l = 2;
        } else
        {
            int i1 = (0xff & abyte2[abyte0[i]]) << 18 | (0xff & abyte2[abyte0[i + 1]]) << 12 | (0xff & abyte2[abyte0[i + 2]]) << 6 | 0xff & abyte2[abyte0[i + 3]];
            abyte1[j] = (byte)(i1 >> 16);
            abyte1[j + 1] = (byte)(i1 >> 8);
            abyte1[j + 2] = (byte)i1;
            l = 3;
        }
        return l;
    }

    public static void decodeFileToFile(String s, String s1)
        throws IOException
    {
        byte abyte0[];
        BufferedOutputStream bufferedoutputstream;
        abyte0 = decodeFromFile(s);
        bufferedoutputstream = null;
        BufferedOutputStream bufferedoutputstream1 = new BufferedOutputStream(new FileOutputStream(s1));
        bufferedoutputstream1.write(abyte0);
        bufferedoutputstream1.close();
_L1:
        return;
        IOException ioexception;
        ioexception;
_L3:
        throw ioexception;
        Exception exception;
        exception;
_L2:
        try
        {
            bufferedoutputstream.close();
        }
        catch(Exception exception1) { }
        throw exception;
        Exception exception2;
        exception2;
          goto _L1
        exception;
        bufferedoutputstream = bufferedoutputstream1;
          goto _L2
        ioexception;
        bufferedoutputstream = bufferedoutputstream1;
          goto _L3
    }

    public static byte[] decodeFromFile(String s)
        throws IOException
    {
        InputStream inputstream = null;
        File file;
        int i;
        file = new File(s);
        i = 0;
        if(file.length() > 0x7fffffffL)
            throw new IOException((new StringBuilder("File is too big for this convenience method (")).append(file.length()).append(" bytes).").toString());
          goto _L1
        IOException ioexception;
        ioexception;
_L7:
        throw ioexception;
        Exception exception;
        exception;
_L5:
        InputStream inputstream1;
        byte abyte0[];
        int j;
        byte abyte1[];
        try
        {
            inputstream.close();
        }
        catch(Exception exception1) { }
        throw exception;
_L1:
        abyte0 = new byte[(int)file.length()];
        inputstream1 = new InputStream(new BufferedInputStream(new FileInputStream(file)), 0);
_L3:
        j = inputstream1.read(abyte0, i, 4096);
        if(j >= 0)
            break MISSING_BLOCK_LABEL_147;
        abyte1 = new byte[i];
        System.arraycopy(abyte0, 0, abyte1, 0, i);
        try
        {
            inputstream1.close();
        }
        catch(Exception exception2) { }
        return abyte1;
        i += j;
        if(true) goto _L3; else goto _L2
_L2:
        exception;
        inputstream = inputstream1;
        if(true) goto _L5; else goto _L4
_L4:
        ioexception;
        inputstream = inputstream1;
        if(true) goto _L7; else goto _L6
_L6:
    }

    public static void decodeToFile(String s, String s1)
        throws IOException
    {
        OutputStream outputstream = null;
        OutputStream outputstream1 = new OutputStream(new FileOutputStream(s1), 0);
        outputstream1.write(s.getBytes("US-ASCII"));
        outputstream1.close();
_L1:
        return;
        IOException ioexception;
        ioexception;
_L3:
        throw ioexception;
        Exception exception;
        exception;
_L2:
        try
        {
            outputstream.close();
        }
        catch(Exception exception1) { }
        throw exception;
        Exception exception2;
        exception2;
          goto _L1
        exception;
        outputstream = outputstream1;
          goto _L2
        ioexception;
        outputstream = outputstream1;
          goto _L3
    }

    public static Object decodeToObject(String s)
        throws IOException, ClassNotFoundException
    {
        return decodeToObject(s, 0, null);
    }

    public static Object decodeToObject(String s, int i, ClassLoader classloader)
        throws IOException, ClassNotFoundException
    {
        byte abyte0[];
        ByteArrayInputStream bytearrayinputstream;
        Object obj;
        abyte0 = decode(s, i);
        bytearrayinputstream = null;
        obj = null;
        final ByteArrayInputStream final_inputstream = new ByteArrayInputStream(abyte0);
        if(classloader != null) goto _L2; else goto _L1
_L1:
        obj = new ObjectInputStream(final_inputstream);
_L3:
        Object obj1 = ((ObjectInputStream) (obj)).readObject();
        IOException ioexception;
        Exception exception;
        ClassNotFoundException classnotfoundexception;
        ObjectInputStream objectinputstream;
        Exception exception1;
        Exception exception2;
        try
        {
            final_inputstream.close();
        }
        catch(Exception exception3) { }
        try
        {
            ((ObjectInputStream) (obj)).close();
        }
        catch(Exception exception4) { }
        return obj1;
_L2:
        objectinputstream = new ObjectInputStream(classloader) {

            public Class resolveClass(ObjectStreamClass objectstreamclass)
                throws IOException, ClassNotFoundException
            {
                Class class1 = Class.forName(objectstreamclass.getName(), false, loader);
                if(class1 == null)
                    class1 = super.resolveClass(objectstreamclass);
                return class1;
            }

            private final ClassLoader val$loader;

            
                throws StreamCorruptedException, IOException
            {
                loader = classloader;
                super(final_inputstream);
            }
        }
;
        obj = objectinputstream;
          goto _L3
        ioexception;
_L7:
        throw ioexception;
        exception;
_L4:
        try
        {
            bytearrayinputstream.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception1) { }
        try
        {
            ((ObjectInputStream) (obj)).close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception2) { }
        throw exception;
        classnotfoundexception;
_L5:
        throw classnotfoundexception;
        exception;
        bytearrayinputstream = final_inputstream;
          goto _L4
        classnotfoundexception;
        bytearrayinputstream = final_inputstream;
          goto _L5
        ioexception;
        bytearrayinputstream = final_inputstream;
        if(true) goto _L7; else goto _L6
_L6:
    }

    public static void encode(ByteBuffer bytebuffer, ByteBuffer bytebuffer1)
    {
        byte abyte0[] = new byte[3];
        byte abyte1[] = new byte[4];
        do
        {
            if(!bytebuffer.hasRemaining())
                return;
            int i = Math.min(3, bytebuffer.remaining());
            bytebuffer.get(abyte0, 0, i);
            encode3to4(abyte1, abyte0, i, 0);
            bytebuffer1.put(abyte1);
        } while(true);
    }

    public static void encode(ByteBuffer bytebuffer, CharBuffer charbuffer)
    {
        byte abyte0[] = new byte[3];
        byte abyte1[] = new byte[4];
        do
        {
            if(!bytebuffer.hasRemaining())
                return;
            int i = Math.min(3, bytebuffer.remaining());
            bytebuffer.get(abyte0, 0, i);
            encode3to4(abyte1, abyte0, i, 0);
            int j = 0;
            while(j < 4) 
            {
                charbuffer.put((char)(0xff & abyte1[j]));
                j++;
            }
        } while(true);
    }

    private static byte[] encode3to4(byte abyte0[], int i, int j, byte abyte1[], int k, int l)
    {
        byte abyte2[];
        int i2;
        int i1 = 0;
        abyte2 = getAlphabet(l);
        int j1;
        int k1;
        int l1;
        if(j > 0)
            j1 = (abyte0[i] << 24) >>> 8;
        else
            j1 = 0;
        if(j > 1)
            k1 = (abyte0[i + 1] << 24) >>> 16;
        else
            k1 = 0;
        l1 = k1 | j1;
        if(j > 2)
            i1 = (abyte0[i + 2] << 24) >>> 24;
        i2 = l1 | i1;
        j;
        JVM INSTR tableswitch 1 3: default 104
    //                   1 238
    //                   2 181
    //                   3 118;
           goto _L1 _L2 _L3 _L4
_L1:
        return abyte1;
_L4:
        abyte1[k] = abyte2[i2 >>> 18];
        abyte1[k + 1] = abyte2[0x3f & i2 >>> 12];
        abyte1[k + 2] = abyte2[0x3f & i2 >>> 6];
        abyte1[k + 3] = abyte2[i2 & 0x3f];
        continue; /* Loop/switch isn't completed */
_L3:
        abyte1[k] = abyte2[i2 >>> 18];
        abyte1[k + 1] = abyte2[0x3f & i2 >>> 12];
        abyte1[k + 2] = abyte2[0x3f & i2 >>> 6];
        abyte1[k + 3] = 61;
        continue; /* Loop/switch isn't completed */
_L2:
        abyte1[k] = abyte2[i2 >>> 18];
        abyte1[k + 1] = abyte2[0x3f & i2 >>> 12];
        abyte1[k + 2] = 61;
        abyte1[k + 3] = 61;
        if(true) goto _L1; else goto _L5
_L5:
    }

    private static byte[] encode3to4(byte abyte0[], byte abyte1[], int i, int j)
    {
        encode3to4(abyte1, 0, i, abyte0, 0, j);
        return abyte0;
    }

    public static String encodeBytes(byte abyte0[])
    {
        String s = null;
        String s1 = encodeBytes(abyte0, 0, abyte0.length, 0);
        s = s1;
_L1:
        IOException ioexception;
        if(!$assertionsDisabled && s == null)
            throw new AssertionError();
        else
            return s;
        ioexception;
        if(!$assertionsDisabled)
            throw new AssertionError(ioexception.getMessage());
          goto _L1
    }

    public static String encodeBytes(byte abyte0[], int i)
        throws IOException
    {
        return encodeBytes(abyte0, 0, abyte0.length, i);
    }

    public static String encodeBytes(byte abyte0[], int i, int j)
    {
        String s = null;
        String s1 = encodeBytes(abyte0, i, j, 0);
        s = s1;
_L1:
        IOException ioexception;
        if(!$assertionsDisabled && s == null)
            throw new AssertionError();
        else
            return s;
        ioexception;
        if(!$assertionsDisabled)
            throw new AssertionError(ioexception.getMessage());
          goto _L1
    }

    public static String encodeBytes(byte abyte0[], int i, int j, int k)
        throws IOException
    {
        byte abyte1[] = encodeBytesToBytes(abyte0, i, j, k);
        String s;
        try
        {
            s = new String(abyte1, "US-ASCII");
        }
        catch(UnsupportedEncodingException unsupportedencodingexception)
        {
            s = new String(abyte1);
        }
        return s;
    }

    public static byte[] encodeBytesToBytes(byte abyte0[])
    {
        byte abyte1[] = null;
        byte abyte2[] = encodeBytesToBytes(abyte0, 0, abyte0.length, 0);
        abyte1 = abyte2;
_L2:
        return abyte1;
        IOException ioexception;
        ioexception;
        if(!$assertionsDisabled)
            throw new AssertionError((new StringBuilder("IOExceptions only come from GZipping, which is turned off: ")).append(ioexception.getMessage()).toString());
        if(true) goto _L2; else goto _L1
_L1:
    }

    public static byte[] encodeBytesToBytes(byte abyte0[], int i, int j, int k)
        throws IOException
    {
        if(abyte0 == null)
            throw new NullPointerException("Cannot serialize a null array.");
        if(i < 0)
            throw new IllegalArgumentException((new StringBuilder("Cannot have negative offset: ")).append(i).toString());
        if(j < 0)
            throw new IllegalArgumentException((new StringBuilder("Cannot have length offset: ")).append(j).toString());
        if(i + j > abyte0.length)
        {
            Object aobj[] = new Object[3];
            aobj[0] = Integer.valueOf(i);
            aobj[1] = Integer.valueOf(j);
            aobj[2] = Integer.valueOf(abyte0.length);
            throw new IllegalArgumentException(String.format("Cannot have offset of %d and length of %d with array of length %d", aobj));
        }
        if((k & 2) == 0) goto _L2; else goto _L1
_L1:
        ByteArrayOutputStream bytearrayoutputstream;
        GZIPOutputStream gzipoutputstream;
        OutputStream outputstream;
        bytearrayoutputstream = null;
        gzipoutputstream = null;
        outputstream = null;
        ByteArrayOutputStream bytearrayoutputstream1 = new ByteArrayOutputStream();
        OutputStream outputstream1;
        GZIPOutputStream gzipoutputstream1;
        IOException ioexception;
        Exception exception;
        try
        {
            outputstream1 = new OutputStream(bytearrayoutputstream1, k | 1);
        }
        // Misplaced declaration of an exception variable
        catch(IOException ioexception)
        {
            bytearrayoutputstream = bytearrayoutputstream1;
            continue; /* Loop/switch isn't completed */
        }
        try
        {
            gzipoutputstream1 = new GZIPOutputStream(outputstream1);
        }
        // Misplaced declaration of an exception variable
        catch(IOException ioexception)
        {
            outputstream = outputstream1;
            bytearrayoutputstream = bytearrayoutputstream1;
            continue; /* Loop/switch isn't completed */
        }
        gzipoutputstream1.write(abyte0, i, j);
        gzipoutputstream1.close();
        boolean flag;
        int l;
        byte byte0;
        int i1;
        byte abyte1[];
        int j1;
        int k1;
        int l1;
        int i2;
        byte abyte2[];
        Exception exception1;
        Exception exception2;
        Exception exception3;
        try
        {
            gzipoutputstream1.close();
        }
        catch(Exception exception4) { }
        try
        {
            outputstream1.close();
        }
        catch(Exception exception5) { }
        try
        {
            bytearrayoutputstream1.close();
        }
        catch(Exception exception6) { }
        abyte2 = bytearrayoutputstream1.toByteArray();
        return abyte2;
        ioexception;
_L7:
        throw ioexception;
        exception;
_L5:
        try
        {
            gzipoutputstream.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception1) { }
        try
        {
            outputstream.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception2) { }
        try
        {
            bytearrayoutputstream.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception3) { }
        throw exception;
_L2:
        if((k & 8) != 0)
            flag = true;
        else
            flag = false;
        l = 4 * (j / 3);
        if(j % 3 > 0)
            byte0 = 4;
        else
            byte0 = 0;
        i1 = l + byte0;
        if(flag)
            i1 += i1 / 76;
        abyte1 = new byte[i1];
        j1 = 0;
        k1 = 0;
        l1 = j - 2;
        i2 = 0;
_L3:
label0:
        {
            if(j1 < l1)
                break label0;
            if(j1 < j)
            {
                encode3to4(abyte0, j1 + i, j - j1, abyte1, k1, k);
                k1 += 4;
            }
            if(k1 <= -1 + abyte1.length)
            {
                abyte2 = new byte[k1];
                System.arraycopy(abyte1, 0, abyte2, 0, k1);
            } else
            {
                abyte2 = abyte1;
            }
        }
        break MISSING_BLOCK_LABEL_212;
        encode3to4(abyte0, j1 + i, 3, abyte1, k1, k);
        i2 += 4;
        if(flag && i2 >= 76)
        {
            abyte1[k1 + 4] = 10;
            k1++;
            i2 = 0;
        }
        j1 += 3;
        k1 += 4;
          goto _L3
        exception;
        bytearrayoutputstream = bytearrayoutputstream1;
        continue; /* Loop/switch isn't completed */
        exception;
        outputstream = outputstream1;
        bytearrayoutputstream = bytearrayoutputstream1;
        continue; /* Loop/switch isn't completed */
        exception;
        outputstream = outputstream1;
        gzipoutputstream = gzipoutputstream1;
        bytearrayoutputstream = bytearrayoutputstream1;
        if(true) goto _L5; else goto _L4
_L4:
        break MISSING_BLOCK_LABEL_166;
        ioexception;
        outputstream = outputstream1;
        gzipoutputstream = gzipoutputstream1;
        bytearrayoutputstream = bytearrayoutputstream1;
        if(true) goto _L7; else goto _L6
_L6:
    }

    public static void encodeFileToFile(String s, String s1)
        throws IOException
    {
        String s2;
        BufferedOutputStream bufferedoutputstream;
        s2 = encodeFromFile(s);
        bufferedoutputstream = null;
        BufferedOutputStream bufferedoutputstream1 = new BufferedOutputStream(new FileOutputStream(s1));
        bufferedoutputstream1.write(s2.getBytes("US-ASCII"));
        bufferedoutputstream1.close();
_L1:
        return;
        IOException ioexception;
        ioexception;
_L3:
        throw ioexception;
        Exception exception;
        exception;
_L2:
        try
        {
            bufferedoutputstream.close();
        }
        catch(Exception exception1) { }
        throw exception;
        Exception exception2;
        exception2;
          goto _L1
        exception;
        bufferedoutputstream = bufferedoutputstream1;
          goto _L2
        ioexception;
        bufferedoutputstream = bufferedoutputstream1;
          goto _L3
    }

    public static String encodeFromFile(String s)
        throws IOException
    {
        InputStream inputstream = null;
        byte abyte0[];
        int i;
        InputStream inputstream1;
        File file = new File(s);
        abyte0 = new byte[Math.max((int)(1.0D + 1.3999999999999999D * (double)file.length()), 40)];
        i = 0;
        inputstream1 = new InputStream(new BufferedInputStream(new FileInputStream(file)), 1);
_L2:
        int j;
        String s1;
        j = inputstream1.read(abyte0, i, 4096);
        if(j >= 0)
            break MISSING_BLOCK_LABEL_103;
        s1 = new String(abyte0, 0, i, "US-ASCII");
        Exception exception;
        IOException ioexception;
        Exception exception1;
        try
        {
            inputstream1.close();
        }
        catch(Exception exception2) { }
        return s1;
        i += j;
        if(true) goto _L2; else goto _L1
_L1:
        ioexception;
_L6:
        throw ioexception;
        exception;
_L4:
        try
        {
            inputstream.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception1) { }
        throw exception;
        exception;
        inputstream = inputstream1;
        if(true) goto _L4; else goto _L3
_L3:
        ioexception;
        inputstream = inputstream1;
        if(true) goto _L6; else goto _L5
_L5:
    }

    public static String encodeObject(Serializable serializable)
        throws IOException
    {
        return encodeObject(serializable, 0);
    }

    public static String encodeObject(Serializable serializable, int i)
        throws IOException
    {
        ByteArrayOutputStream bytearrayoutputstream;
        OutputStream outputstream;
        GZIPOutputStream gzipoutputstream;
        ObjectOutputStream objectoutputstream;
        if(serializable == null)
            throw new NullPointerException("Cannot serialize a null object.");
        bytearrayoutputstream = null;
        outputstream = null;
        gzipoutputstream = null;
        objectoutputstream = null;
        ByteArrayOutputStream bytearrayoutputstream1 = new ByteArrayOutputStream();
        OutputStream outputstream1;
        IOException ioexception;
        Exception exception;
        GZIPOutputStream gzipoutputstream1;
        ObjectOutputStream objectoutputstream2;
        try
        {
            outputstream1 = new OutputStream(bytearrayoutputstream1, i | 1);
        }
        // Misplaced declaration of an exception variable
        catch(IOException ioexception)
        {
            bytearrayoutputstream = bytearrayoutputstream1;
            continue; /* Loop/switch isn't completed */
        }
        if((i & 2) == 0) goto _L2; else goto _L1
_L1:
        try
        {
            gzipoutputstream1 = new GZIPOutputStream(outputstream1);
        }
        // Misplaced declaration of an exception variable
        catch(IOException ioexception)
        {
            outputstream = outputstream1;
            bytearrayoutputstream = bytearrayoutputstream1;
            continue; /* Loop/switch isn't completed */
        }
        objectoutputstream2 = new ObjectOutputStream(gzipoutputstream1);
        objectoutputstream = objectoutputstream2;
        gzipoutputstream = gzipoutputstream1;
_L4:
        objectoutputstream.writeObject(serializable);
        ObjectOutputStream objectoutputstream1;
        Exception exception1;
        Exception exception2;
        Exception exception3;
        Exception exception4;
        String s;
        try
        {
            objectoutputstream.close();
        }
        catch(Exception exception5) { }
        try
        {
            gzipoutputstream.close();
        }
        catch(Exception exception6) { }
        try
        {
            outputstream1.close();
        }
        catch(Exception exception7) { }
        try
        {
            bytearrayoutputstream1.close();
        }
        catch(Exception exception8) { }
        try
        {
            s = new String(bytearrayoutputstream1.toByteArray(), "US-ASCII");
        }
        catch(UnsupportedEncodingException unsupportedencodingexception)
        {
            s = new String(bytearrayoutputstream1.toByteArray());
        }
        return s;
_L2:
        objectoutputstream1 = new ObjectOutputStream(outputstream1);
        objectoutputstream = objectoutputstream1;
        if(true) goto _L4; else goto _L3
_L3:
        ioexception;
_L8:
        throw ioexception;
        exception;
_L6:
        try
        {
            objectoutputstream.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception1) { }
        try
        {
            gzipoutputstream.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception2) { }
        try
        {
            outputstream.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception3) { }
        try
        {
            bytearrayoutputstream.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception4) { }
        throw exception;
        exception;
        bytearrayoutputstream = bytearrayoutputstream1;
        continue; /* Loop/switch isn't completed */
        exception;
        outputstream = outputstream1;
        bytearrayoutputstream = bytearrayoutputstream1;
        continue; /* Loop/switch isn't completed */
        exception;
        gzipoutputstream = gzipoutputstream1;
        outputstream = outputstream1;
        bytearrayoutputstream = bytearrayoutputstream1;
        if(true) goto _L6; else goto _L5
_L5:
        break MISSING_BLOCK_LABEL_48;
        ioexception;
        gzipoutputstream = gzipoutputstream1;
        outputstream = outputstream1;
        bytearrayoutputstream = bytearrayoutputstream1;
        if(true) goto _L8; else goto _L7
_L7:
    }

    public static void encodeToFile(byte abyte0[], String s)
        throws IOException
    {
        OutputStream outputstream;
        if(abyte0 == null)
            throw new NullPointerException("Data to encode was null.");
        outputstream = null;
        OutputStream outputstream1 = new OutputStream(new FileOutputStream(s), 1);
        outputstream1.write(abyte0);
        outputstream1.close();
_L1:
        return;
        IOException ioexception;
        ioexception;
_L3:
        throw ioexception;
        Exception exception;
        exception;
_L2:
        try
        {
            outputstream.close();
        }
        catch(Exception exception1) { }
        throw exception;
        Exception exception2;
        exception2;
          goto _L1
        exception;
        outputstream = outputstream1;
          goto _L2
        ioexception;
        outputstream = outputstream1;
          goto _L3
    }

    private static final byte[] getAlphabet(int i)
    {
        byte abyte0[];
        if((i & 0x10) == 16)
            abyte0 = _URL_SAFE_ALPHABET;
        else
        if((i & 0x20) == 32)
            abyte0 = _ORDERED_ALPHABET;
        else
            abyte0 = _STANDARD_ALPHABET;
        return abyte0;
    }

    private static final byte[] getDecodabet(int i)
    {
        byte abyte0[];
        if((i & 0x10) == 16)
            abyte0 = _URL_SAFE_DECODABET;
        else
        if((i & 0x20) == 32)
            abyte0 = _ORDERED_DECODABET;
        else
            abyte0 = _STANDARD_DECODABET;
        return abyte0;
    }

    static final boolean $assertionsDisabled = false;
    public static final int DECODE = 0;
    public static final int DONT_GUNZIP = 4;
    public static final int DO_BREAK_LINES = 8;
    public static final int ENCODE = 1;
    private static final byte EQUALS_SIGN = 61;
    private static final byte EQUALS_SIGN_ENC = -1;
    public static final int GZIP = 2;
    private static final int MAX_LINE_LENGTH = 76;
    private static final byte NEW_LINE = 10;
    public static final int NO_OPTIONS = 0;
    public static final int ORDERED = 32;
    private static final String PREFERRED_ENCODING = "US-ASCII";
    public static final int URL_SAFE = 16;
    private static final byte WHITE_SPACE_ENC = -5;
    private static final byte _ORDERED_ALPHABET[];
    private static final byte _ORDERED_DECODABET[];
    private static final byte _STANDARD_ALPHABET[];
    private static final byte _STANDARD_DECODABET[];
    private static final byte _URL_SAFE_ALPHABET[];
    private static final byte _URL_SAFE_DECODABET[];

    static 
    {
        boolean flag;
        byte abyte0[];
        byte abyte1[];
        byte abyte2[];
        byte abyte3[];
        byte abyte4[];
        byte abyte5[];
        if(!com/openvehicles/OVMS/Base64.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
        abyte0 = new byte[64];
        abyte0[0] = 65;
        abyte0[1] = 66;
        abyte0[2] = 67;
        abyte0[3] = 68;
        abyte0[4] = 69;
        abyte0[5] = 70;
        abyte0[6] = 71;
        abyte0[7] = 72;
        abyte0[8] = 73;
        abyte0[9] = 74;
        abyte0[10] = 75;
        abyte0[11] = 76;
        abyte0[12] = 77;
        abyte0[13] = 78;
        abyte0[14] = 79;
        abyte0[15] = 80;
        abyte0[16] = 81;
        abyte0[17] = 82;
        abyte0[18] = 83;
        abyte0[19] = 84;
        abyte0[20] = 85;
        abyte0[21] = 86;
        abyte0[22] = 87;
        abyte0[23] = 88;
        abyte0[24] = 89;
        abyte0[25] = 90;
        abyte0[26] = 97;
        abyte0[27] = 98;
        abyte0[28] = 99;
        abyte0[29] = 100;
        abyte0[30] = 101;
        abyte0[31] = 102;
        abyte0[32] = 103;
        abyte0[33] = 104;
        abyte0[34] = 105;
        abyte0[35] = 106;
        abyte0[36] = 107;
        abyte0[37] = 108;
        abyte0[38] = 109;
        abyte0[39] = 110;
        abyte0[40] = 111;
        abyte0[41] = 112;
        abyte0[42] = 113;
        abyte0[43] = 114;
        abyte0[44] = 115;
        abyte0[45] = 116;
        abyte0[46] = 117;
        abyte0[47] = 118;
        abyte0[48] = 119;
        abyte0[49] = 120;
        abyte0[50] = 121;
        abyte0[51] = 122;
        abyte0[52] = 48;
        abyte0[53] = 49;
        abyte0[54] = 50;
        abyte0[55] = 51;
        abyte0[56] = 52;
        abyte0[57] = 53;
        abyte0[58] = 54;
        abyte0[59] = 55;
        abyte0[60] = 56;
        abyte0[61] = 57;
        abyte0[62] = 43;
        abyte0[63] = 47;
        _STANDARD_ALPHABET = abyte0;
        abyte1 = new byte[256];
        abyte1[0] = -9;
        abyte1[1] = -9;
        abyte1[2] = -9;
        abyte1[3] = -9;
        abyte1[4] = -9;
        abyte1[5] = -9;
        abyte1[6] = -9;
        abyte1[7] = -9;
        abyte1[8] = -9;
        abyte1[9] = -5;
        abyte1[10] = -5;
        abyte1[11] = -9;
        abyte1[12] = -9;
        abyte1[13] = -5;
        abyte1[14] = -9;
        abyte1[15] = -9;
        abyte1[16] = -9;
        abyte1[17] = -9;
        abyte1[18] = -9;
        abyte1[19] = -9;
        abyte1[20] = -9;
        abyte1[21] = -9;
        abyte1[22] = -9;
        abyte1[23] = -9;
        abyte1[24] = -9;
        abyte1[25] = -9;
        abyte1[26] = -9;
        abyte1[27] = -9;
        abyte1[28] = -9;
        abyte1[29] = -9;
        abyte1[30] = -9;
        abyte1[31] = -9;
        abyte1[32] = -5;
        abyte1[33] = -9;
        abyte1[34] = -9;
        abyte1[35] = -9;
        abyte1[36] = -9;
        abyte1[37] = -9;
        abyte1[38] = -9;
        abyte1[39] = -9;
        abyte1[40] = -9;
        abyte1[41] = -9;
        abyte1[42] = -9;
        abyte1[43] = 62;
        abyte1[44] = -9;
        abyte1[45] = -9;
        abyte1[46] = -9;
        abyte1[47] = 63;
        abyte1[48] = 52;
        abyte1[49] = 53;
        abyte1[50] = 54;
        abyte1[51] = 55;
        abyte1[52] = 56;
        abyte1[53] = 57;
        abyte1[54] = 58;
        abyte1[55] = 59;
        abyte1[56] = 60;
        abyte1[57] = 61;
        abyte1[58] = -9;
        abyte1[59] = -9;
        abyte1[60] = -9;
        abyte1[61] = -1;
        abyte1[62] = -9;
        abyte1[63] = -9;
        abyte1[64] = -9;
        abyte1[66] = 1;
        abyte1[67] = 2;
        abyte1[68] = 3;
        abyte1[69] = 4;
        abyte1[70] = 5;
        abyte1[71] = 6;
        abyte1[72] = 7;
        abyte1[73] = 8;
        abyte1[74] = 9;
        abyte1[75] = 10;
        abyte1[76] = 11;
        abyte1[77] = 12;
        abyte1[78] = 13;
        abyte1[79] = 14;
        abyte1[80] = 15;
        abyte1[81] = 16;
        abyte1[82] = 17;
        abyte1[83] = 18;
        abyte1[84] = 19;
        abyte1[85] = 20;
        abyte1[86] = 21;
        abyte1[87] = 22;
        abyte1[88] = 23;
        abyte1[89] = 24;
        abyte1[90] = 25;
        abyte1[91] = -9;
        abyte1[92] = -9;
        abyte1[93] = -9;
        abyte1[94] = -9;
        abyte1[95] = -9;
        abyte1[96] = -9;
        abyte1[97] = 26;
        abyte1[98] = 27;
        abyte1[99] = 28;
        abyte1[100] = 29;
        abyte1[101] = 30;
        abyte1[102] = 31;
        abyte1[103] = 32;
        abyte1[104] = 33;
        abyte1[105] = 34;
        abyte1[106] = 35;
        abyte1[107] = 36;
        abyte1[108] = 37;
        abyte1[109] = 38;
        abyte1[110] = 39;
        abyte1[111] = 40;
        abyte1[112] = 41;
        abyte1[113] = 42;
        abyte1[114] = 43;
        abyte1[115] = 44;
        abyte1[116] = 45;
        abyte1[117] = 46;
        abyte1[118] = 47;
        abyte1[119] = 48;
        abyte1[120] = 49;
        abyte1[121] = 50;
        abyte1[122] = 51;
        abyte1[123] = -9;
        abyte1[124] = -9;
        abyte1[125] = -9;
        abyte1[126] = -9;
        abyte1[127] = -9;
        abyte1[128] = -9;
        abyte1[129] = -9;
        abyte1[130] = -9;
        abyte1[131] = -9;
        abyte1[132] = -9;
        abyte1[133] = -9;
        abyte1[134] = -9;
        abyte1[135] = -9;
        abyte1[136] = -9;
        abyte1[137] = -9;
        abyte1[138] = -9;
        abyte1[139] = -9;
        abyte1[140] = -9;
        abyte1[141] = -9;
        abyte1[142] = -9;
        abyte1[143] = -9;
        abyte1[144] = -9;
        abyte1[145] = -9;
        abyte1[146] = -9;
        abyte1[147] = -9;
        abyte1[148] = -9;
        abyte1[149] = -9;
        abyte1[150] = -9;
        abyte1[151] = -9;
        abyte1[152] = -9;
        abyte1[153] = -9;
        abyte1[154] = -9;
        abyte1[155] = -9;
        abyte1[156] = -9;
        abyte1[157] = -9;
        abyte1[158] = -9;
        abyte1[159] = -9;
        abyte1[160] = -9;
        abyte1[161] = -9;
        abyte1[162] = -9;
        abyte1[163] = -9;
        abyte1[164] = -9;
        abyte1[165] = -9;
        abyte1[166] = -9;
        abyte1[167] = -9;
        abyte1[168] = -9;
        abyte1[169] = -9;
        abyte1[170] = -9;
        abyte1[171] = -9;
        abyte1[172] = -9;
        abyte1[173] = -9;
        abyte1[174] = -9;
        abyte1[175] = -9;
        abyte1[176] = -9;
        abyte1[177] = -9;
        abyte1[178] = -9;
        abyte1[179] = -9;
        abyte1[180] = -9;
        abyte1[181] = -9;
        abyte1[182] = -9;
        abyte1[183] = -9;
        abyte1[184] = -9;
        abyte1[185] = -9;
        abyte1[186] = -9;
        abyte1[187] = -9;
        abyte1[188] = -9;
        abyte1[189] = -9;
        abyte1[190] = -9;
        abyte1[191] = -9;
        abyte1[192] = -9;
        abyte1[193] = -9;
        abyte1[194] = -9;
        abyte1[195] = -9;
        abyte1[196] = -9;
        abyte1[197] = -9;
        abyte1[198] = -9;
        abyte1[199] = -9;
        abyte1[200] = -9;
        abyte1[201] = -9;
        abyte1[202] = -9;
        abyte1[203] = -9;
        abyte1[204] = -9;
        abyte1[205] = -9;
        abyte1[206] = -9;
        abyte1[207] = -9;
        abyte1[208] = -9;
        abyte1[209] = -9;
        abyte1[210] = -9;
        abyte1[211] = -9;
        abyte1[212] = -9;
        abyte1[213] = -9;
        abyte1[214] = -9;
        abyte1[215] = -9;
        abyte1[216] = -9;
        abyte1[217] = -9;
        abyte1[218] = -9;
        abyte1[219] = -9;
        abyte1[220] = -9;
        abyte1[221] = -9;
        abyte1[222] = -9;
        abyte1[223] = -9;
        abyte1[224] = -9;
        abyte1[225] = -9;
        abyte1[226] = -9;
        abyte1[227] = -9;
        abyte1[228] = -9;
        abyte1[229] = -9;
        abyte1[230] = -9;
        abyte1[231] = -9;
        abyte1[232] = -9;
        abyte1[233] = -9;
        abyte1[234] = -9;
        abyte1[235] = -9;
        abyte1[236] = -9;
        abyte1[237] = -9;
        abyte1[238] = -9;
        abyte1[239] = -9;
        abyte1[240] = -9;
        abyte1[241] = -9;
        abyte1[242] = -9;
        abyte1[243] = -9;
        abyte1[244] = -9;
        abyte1[245] = -9;
        abyte1[246] = -9;
        abyte1[247] = -9;
        abyte1[248] = -9;
        abyte1[249] = -9;
        abyte1[250] = -9;
        abyte1[251] = -9;
        abyte1[252] = -9;
        abyte1[253] = -9;
        abyte1[254] = -9;
        abyte1[255] = -9;
        _STANDARD_DECODABET = abyte1;
        abyte2 = new byte[64];
        abyte2[0] = 65;
        abyte2[1] = 66;
        abyte2[2] = 67;
        abyte2[3] = 68;
        abyte2[4] = 69;
        abyte2[5] = 70;
        abyte2[6] = 71;
        abyte2[7] = 72;
        abyte2[8] = 73;
        abyte2[9] = 74;
        abyte2[10] = 75;
        abyte2[11] = 76;
        abyte2[12] = 77;
        abyte2[13] = 78;
        abyte2[14] = 79;
        abyte2[15] = 80;
        abyte2[16] = 81;
        abyte2[17] = 82;
        abyte2[18] = 83;
        abyte2[19] = 84;
        abyte2[20] = 85;
        abyte2[21] = 86;
        abyte2[22] = 87;
        abyte2[23] = 88;
        abyte2[24] = 89;
        abyte2[25] = 90;
        abyte2[26] = 97;
        abyte2[27] = 98;
        abyte2[28] = 99;
        abyte2[29] = 100;
        abyte2[30] = 101;
        abyte2[31] = 102;
        abyte2[32] = 103;
        abyte2[33] = 104;
        abyte2[34] = 105;
        abyte2[35] = 106;
        abyte2[36] = 107;
        abyte2[37] = 108;
        abyte2[38] = 109;
        abyte2[39] = 110;
        abyte2[40] = 111;
        abyte2[41] = 112;
        abyte2[42] = 113;
        abyte2[43] = 114;
        abyte2[44] = 115;
        abyte2[45] = 116;
        abyte2[46] = 117;
        abyte2[47] = 118;
        abyte2[48] = 119;
        abyte2[49] = 120;
        abyte2[50] = 121;
        abyte2[51] = 122;
        abyte2[52] = 48;
        abyte2[53] = 49;
        abyte2[54] = 50;
        abyte2[55] = 51;
        abyte2[56] = 52;
        abyte2[57] = 53;
        abyte2[58] = 54;
        abyte2[59] = 55;
        abyte2[60] = 56;
        abyte2[61] = 57;
        abyte2[62] = 45;
        abyte2[63] = 95;
        _URL_SAFE_ALPHABET = abyte2;
        abyte3 = new byte[256];
        abyte3[0] = -9;
        abyte3[1] = -9;
        abyte3[2] = -9;
        abyte3[3] = -9;
        abyte3[4] = -9;
        abyte3[5] = -9;
        abyte3[6] = -9;
        abyte3[7] = -9;
        abyte3[8] = -9;
        abyte3[9] = -5;
        abyte3[10] = -5;
        abyte3[11] = -9;
        abyte3[12] = -9;
        abyte3[13] = -5;
        abyte3[14] = -9;
        abyte3[15] = -9;
        abyte3[16] = -9;
        abyte3[17] = -9;
        abyte3[18] = -9;
        abyte3[19] = -9;
        abyte3[20] = -9;
        abyte3[21] = -9;
        abyte3[22] = -9;
        abyte3[23] = -9;
        abyte3[24] = -9;
        abyte3[25] = -9;
        abyte3[26] = -9;
        abyte3[27] = -9;
        abyte3[28] = -9;
        abyte3[29] = -9;
        abyte3[30] = -9;
        abyte3[31] = -9;
        abyte3[32] = -5;
        abyte3[33] = -9;
        abyte3[34] = -9;
        abyte3[35] = -9;
        abyte3[36] = -9;
        abyte3[37] = -9;
        abyte3[38] = -9;
        abyte3[39] = -9;
        abyte3[40] = -9;
        abyte3[41] = -9;
        abyte3[42] = -9;
        abyte3[43] = -9;
        abyte3[44] = -9;
        abyte3[45] = 62;
        abyte3[46] = -9;
        abyte3[47] = -9;
        abyte3[48] = 52;
        abyte3[49] = 53;
        abyte3[50] = 54;
        abyte3[51] = 55;
        abyte3[52] = 56;
        abyte3[53] = 57;
        abyte3[54] = 58;
        abyte3[55] = 59;
        abyte3[56] = 60;
        abyte3[57] = 61;
        abyte3[58] = -9;
        abyte3[59] = -9;
        abyte3[60] = -9;
        abyte3[61] = -1;
        abyte3[62] = -9;
        abyte3[63] = -9;
        abyte3[64] = -9;
        abyte3[66] = 1;
        abyte3[67] = 2;
        abyte3[68] = 3;
        abyte3[69] = 4;
        abyte3[70] = 5;
        abyte3[71] = 6;
        abyte3[72] = 7;
        abyte3[73] = 8;
        abyte3[74] = 9;
        abyte3[75] = 10;
        abyte3[76] = 11;
        abyte3[77] = 12;
        abyte3[78] = 13;
        abyte3[79] = 14;
        abyte3[80] = 15;
        abyte3[81] = 16;
        abyte3[82] = 17;
        abyte3[83] = 18;
        abyte3[84] = 19;
        abyte3[85] = 20;
        abyte3[86] = 21;
        abyte3[87] = 22;
        abyte3[88] = 23;
        abyte3[89] = 24;
        abyte3[90] = 25;
        abyte3[91] = -9;
        abyte3[92] = -9;
        abyte3[93] = -9;
        abyte3[94] = -9;
        abyte3[95] = 63;
        abyte3[96] = -9;
        abyte3[97] = 26;
        abyte3[98] = 27;
        abyte3[99] = 28;
        abyte3[100] = 29;
        abyte3[101] = 30;
        abyte3[102] = 31;
        abyte3[103] = 32;
        abyte3[104] = 33;
        abyte3[105] = 34;
        abyte3[106] = 35;
        abyte3[107] = 36;
        abyte3[108] = 37;
        abyte3[109] = 38;
        abyte3[110] = 39;
        abyte3[111] = 40;
        abyte3[112] = 41;
        abyte3[113] = 42;
        abyte3[114] = 43;
        abyte3[115] = 44;
        abyte3[116] = 45;
        abyte3[117] = 46;
        abyte3[118] = 47;
        abyte3[119] = 48;
        abyte3[120] = 49;
        abyte3[121] = 50;
        abyte3[122] = 51;
        abyte3[123] = -9;
        abyte3[124] = -9;
        abyte3[125] = -9;
        abyte3[126] = -9;
        abyte3[127] = -9;
        abyte3[128] = -9;
        abyte3[129] = -9;
        abyte3[130] = -9;
        abyte3[131] = -9;
        abyte3[132] = -9;
        abyte3[133] = -9;
        abyte3[134] = -9;
        abyte3[135] = -9;
        abyte3[136] = -9;
        abyte3[137] = -9;
        abyte3[138] = -9;
        abyte3[139] = -9;
        abyte3[140] = -9;
        abyte3[141] = -9;
        abyte3[142] = -9;
        abyte3[143] = -9;
        abyte3[144] = -9;
        abyte3[145] = -9;
        abyte3[146] = -9;
        abyte3[147] = -9;
        abyte3[148] = -9;
        abyte3[149] = -9;
        abyte3[150] = -9;
        abyte3[151] = -9;
        abyte3[152] = -9;
        abyte3[153] = -9;
        abyte3[154] = -9;
        abyte3[155] = -9;
        abyte3[156] = -9;
        abyte3[157] = -9;
        abyte3[158] = -9;
        abyte3[159] = -9;
        abyte3[160] = -9;
        abyte3[161] = -9;
        abyte3[162] = -9;
        abyte3[163] = -9;
        abyte3[164] = -9;
        abyte3[165] = -9;
        abyte3[166] = -9;
        abyte3[167] = -9;
        abyte3[168] = -9;
        abyte3[169] = -9;
        abyte3[170] = -9;
        abyte3[171] = -9;
        abyte3[172] = -9;
        abyte3[173] = -9;
        abyte3[174] = -9;
        abyte3[175] = -9;
        abyte3[176] = -9;
        abyte3[177] = -9;
        abyte3[178] = -9;
        abyte3[179] = -9;
        abyte3[180] = -9;
        abyte3[181] = -9;
        abyte3[182] = -9;
        abyte3[183] = -9;
        abyte3[184] = -9;
        abyte3[185] = -9;
        abyte3[186] = -9;
        abyte3[187] = -9;
        abyte3[188] = -9;
        abyte3[189] = -9;
        abyte3[190] = -9;
        abyte3[191] = -9;
        abyte3[192] = -9;
        abyte3[193] = -9;
        abyte3[194] = -9;
        abyte3[195] = -9;
        abyte3[196] = -9;
        abyte3[197] = -9;
        abyte3[198] = -9;
        abyte3[199] = -9;
        abyte3[200] = -9;
        abyte3[201] = -9;
        abyte3[202] = -9;
        abyte3[203] = -9;
        abyte3[204] = -9;
        abyte3[205] = -9;
        abyte3[206] = -9;
        abyte3[207] = -9;
        abyte3[208] = -9;
        abyte3[209] = -9;
        abyte3[210] = -9;
        abyte3[211] = -9;
        abyte3[212] = -9;
        abyte3[213] = -9;
        abyte3[214] = -9;
        abyte3[215] = -9;
        abyte3[216] = -9;
        abyte3[217] = -9;
        abyte3[218] = -9;
        abyte3[219] = -9;
        abyte3[220] = -9;
        abyte3[221] = -9;
        abyte3[222] = -9;
        abyte3[223] = -9;
        abyte3[224] = -9;
        abyte3[225] = -9;
        abyte3[226] = -9;
        abyte3[227] = -9;
        abyte3[228] = -9;
        abyte3[229] = -9;
        abyte3[230] = -9;
        abyte3[231] = -9;
        abyte3[232] = -9;
        abyte3[233] = -9;
        abyte3[234] = -9;
        abyte3[235] = -9;
        abyte3[236] = -9;
        abyte3[237] = -9;
        abyte3[238] = -9;
        abyte3[239] = -9;
        abyte3[240] = -9;
        abyte3[241] = -9;
        abyte3[242] = -9;
        abyte3[243] = -9;
        abyte3[244] = -9;
        abyte3[245] = -9;
        abyte3[246] = -9;
        abyte3[247] = -9;
        abyte3[248] = -9;
        abyte3[249] = -9;
        abyte3[250] = -9;
        abyte3[251] = -9;
        abyte3[252] = -9;
        abyte3[253] = -9;
        abyte3[254] = -9;
        abyte3[255] = -9;
        _URL_SAFE_DECODABET = abyte3;
        abyte4 = new byte[64];
        abyte4[0] = 45;
        abyte4[1] = 48;
        abyte4[2] = 49;
        abyte4[3] = 50;
        abyte4[4] = 51;
        abyte4[5] = 52;
        abyte4[6] = 53;
        abyte4[7] = 54;
        abyte4[8] = 55;
        abyte4[9] = 56;
        abyte4[10] = 57;
        abyte4[11] = 65;
        abyte4[12] = 66;
        abyte4[13] = 67;
        abyte4[14] = 68;
        abyte4[15] = 69;
        abyte4[16] = 70;
        abyte4[17] = 71;
        abyte4[18] = 72;
        abyte4[19] = 73;
        abyte4[20] = 74;
        abyte4[21] = 75;
        abyte4[22] = 76;
        abyte4[23] = 77;
        abyte4[24] = 78;
        abyte4[25] = 79;
        abyte4[26] = 80;
        abyte4[27] = 81;
        abyte4[28] = 82;
        abyte4[29] = 83;
        abyte4[30] = 84;
        abyte4[31] = 85;
        abyte4[32] = 86;
        abyte4[33] = 87;
        abyte4[34] = 88;
        abyte4[35] = 89;
        abyte4[36] = 90;
        abyte4[37] = 95;
        abyte4[38] = 97;
        abyte4[39] = 98;
        abyte4[40] = 99;
        abyte4[41] = 100;
        abyte4[42] = 101;
        abyte4[43] = 102;
        abyte4[44] = 103;
        abyte4[45] = 104;
        abyte4[46] = 105;
        abyte4[47] = 106;
        abyte4[48] = 107;
        abyte4[49] = 108;
        abyte4[50] = 109;
        abyte4[51] = 110;
        abyte4[52] = 111;
        abyte4[53] = 112;
        abyte4[54] = 113;
        abyte4[55] = 114;
        abyte4[56] = 115;
        abyte4[57] = 116;
        abyte4[58] = 117;
        abyte4[59] = 118;
        abyte4[60] = 119;
        abyte4[61] = 120;
        abyte4[62] = 121;
        abyte4[63] = 122;
        _ORDERED_ALPHABET = abyte4;
        abyte5 = new byte[257];
        abyte5[0] = -9;
        abyte5[1] = -9;
        abyte5[2] = -9;
        abyte5[3] = -9;
        abyte5[4] = -9;
        abyte5[5] = -9;
        abyte5[6] = -9;
        abyte5[7] = -9;
        abyte5[8] = -9;
        abyte5[9] = -5;
        abyte5[10] = -5;
        abyte5[11] = -9;
        abyte5[12] = -9;
        abyte5[13] = -5;
        abyte5[14] = -9;
        abyte5[15] = -9;
        abyte5[16] = -9;
        abyte5[17] = -9;
        abyte5[18] = -9;
        abyte5[19] = -9;
        abyte5[20] = -9;
        abyte5[21] = -9;
        abyte5[22] = -9;
        abyte5[23] = -9;
        abyte5[24] = -9;
        abyte5[25] = -9;
        abyte5[26] = -9;
        abyte5[27] = -9;
        abyte5[28] = -9;
        abyte5[29] = -9;
        abyte5[30] = -9;
        abyte5[31] = -9;
        abyte5[32] = -5;
        abyte5[33] = -9;
        abyte5[34] = -9;
        abyte5[35] = -9;
        abyte5[36] = -9;
        abyte5[37] = -9;
        abyte5[38] = -9;
        abyte5[39] = -9;
        abyte5[40] = -9;
        abyte5[41] = -9;
        abyte5[42] = -9;
        abyte5[43] = -9;
        abyte5[44] = -9;
        abyte5[46] = -9;
        abyte5[47] = -9;
        abyte5[48] = 1;
        abyte5[49] = 2;
        abyte5[50] = 3;
        abyte5[51] = 4;
        abyte5[52] = 5;
        abyte5[53] = 6;
        abyte5[54] = 7;
        abyte5[55] = 8;
        abyte5[56] = 9;
        abyte5[57] = 10;
        abyte5[58] = -9;
        abyte5[59] = -9;
        abyte5[60] = -9;
        abyte5[61] = -1;
        abyte5[62] = -9;
        abyte5[63] = -9;
        abyte5[64] = -9;
        abyte5[65] = 11;
        abyte5[66] = 12;
        abyte5[67] = 13;
        abyte5[68] = 14;
        abyte5[69] = 15;
        abyte5[70] = 16;
        abyte5[71] = 17;
        abyte5[72] = 18;
        abyte5[73] = 19;
        abyte5[74] = 20;
        abyte5[75] = 21;
        abyte5[76] = 22;
        abyte5[77] = 23;
        abyte5[78] = 24;
        abyte5[79] = 25;
        abyte5[80] = 26;
        abyte5[81] = 27;
        abyte5[82] = 28;
        abyte5[83] = 29;
        abyte5[84] = 30;
        abyte5[85] = 31;
        abyte5[86] = 32;
        abyte5[87] = 33;
        abyte5[88] = 34;
        abyte5[89] = 35;
        abyte5[90] = 36;
        abyte5[91] = -9;
        abyte5[92] = -9;
        abyte5[93] = -9;
        abyte5[94] = -9;
        abyte5[95] = 37;
        abyte5[96] = -9;
        abyte5[97] = 38;
        abyte5[98] = 39;
        abyte5[99] = 40;
        abyte5[100] = 41;
        abyte5[101] = 42;
        abyte5[102] = 43;
        abyte5[103] = 44;
        abyte5[104] = 45;
        abyte5[105] = 46;
        abyte5[106] = 47;
        abyte5[107] = 48;
        abyte5[108] = 49;
        abyte5[109] = 50;
        abyte5[110] = 51;
        abyte5[111] = 52;
        abyte5[112] = 53;
        abyte5[113] = 54;
        abyte5[114] = 55;
        abyte5[115] = 56;
        abyte5[116] = 57;
        abyte5[117] = 58;
        abyte5[118] = 59;
        abyte5[119] = 60;
        abyte5[120] = 61;
        abyte5[121] = 62;
        abyte5[122] = 63;
        abyte5[123] = -9;
        abyte5[124] = -9;
        abyte5[125] = -9;
        abyte5[126] = -9;
        abyte5[127] = -9;
        abyte5[128] = -9;
        abyte5[129] = -9;
        abyte5[130] = -9;
        abyte5[131] = -9;
        abyte5[132] = -9;
        abyte5[133] = -9;
        abyte5[134] = -9;
        abyte5[135] = -9;
        abyte5[136] = -9;
        abyte5[137] = -9;
        abyte5[138] = -9;
        abyte5[139] = -9;
        abyte5[140] = -9;
        abyte5[141] = -9;
        abyte5[142] = -9;
        abyte5[143] = -9;
        abyte5[144] = -9;
        abyte5[145] = -9;
        abyte5[146] = -9;
        abyte5[147] = -9;
        abyte5[148] = -9;
        abyte5[149] = -9;
        abyte5[150] = -9;
        abyte5[151] = -9;
        abyte5[152] = -9;
        abyte5[153] = -9;
        abyte5[154] = -9;
        abyte5[155] = -9;
        abyte5[156] = -9;
        abyte5[157] = -9;
        abyte5[158] = -9;
        abyte5[159] = -9;
        abyte5[160] = -9;
        abyte5[161] = -9;
        abyte5[162] = -9;
        abyte5[163] = -9;
        abyte5[164] = -9;
        abyte5[165] = -9;
        abyte5[166] = -9;
        abyte5[167] = -9;
        abyte5[168] = -9;
        abyte5[169] = -9;
        abyte5[170] = -9;
        abyte5[171] = -9;
        abyte5[172] = -9;
        abyte5[173] = -9;
        abyte5[174] = -9;
        abyte5[175] = -9;
        abyte5[176] = -9;
        abyte5[177] = -9;
        abyte5[178] = -9;
        abyte5[179] = -9;
        abyte5[180] = -9;
        abyte5[181] = -9;
        abyte5[182] = -9;
        abyte5[183] = -9;
        abyte5[184] = -9;
        abyte5[185] = -9;
        abyte5[186] = -9;
        abyte5[187] = -9;
        abyte5[188] = -9;
        abyte5[189] = -9;
        abyte5[190] = -9;
        abyte5[191] = -9;
        abyte5[192] = -9;
        abyte5[193] = -9;
        abyte5[194] = -9;
        abyte5[195] = -9;
        abyte5[196] = -9;
        abyte5[197] = -9;
        abyte5[198] = -9;
        abyte5[199] = -9;
        abyte5[200] = -9;
        abyte5[201] = -9;
        abyte5[202] = -9;
        abyte5[203] = -9;
        abyte5[204] = -9;
        abyte5[205] = -9;
        abyte5[206] = -9;
        abyte5[207] = -9;
        abyte5[208] = -9;
        abyte5[209] = -9;
        abyte5[210] = -9;
        abyte5[211] = -9;
        abyte5[212] = -9;
        abyte5[213] = -9;
        abyte5[214] = -9;
        abyte5[215] = -9;
        abyte5[216] = -9;
        abyte5[217] = -9;
        abyte5[218] = -9;
        abyte5[219] = -9;
        abyte5[220] = -9;
        abyte5[221] = -9;
        abyte5[222] = -9;
        abyte5[223] = -9;
        abyte5[224] = -9;
        abyte5[225] = -9;
        abyte5[226] = -9;
        abyte5[227] = -9;
        abyte5[228] = -9;
        abyte5[229] = -9;
        abyte5[230] = -9;
        abyte5[231] = -9;
        abyte5[232] = -9;
        abyte5[233] = -9;
        abyte5[234] = -9;
        abyte5[235] = -9;
        abyte5[236] = -9;
        abyte5[237] = -9;
        abyte5[238] = -9;
        abyte5[239] = -9;
        abyte5[240] = -9;
        abyte5[241] = -9;
        abyte5[242] = -9;
        abyte5[243] = -9;
        abyte5[244] = -9;
        abyte5[245] = -9;
        abyte5[246] = -9;
        abyte5[247] = -9;
        abyte5[248] = -9;
        abyte5[249] = -9;
        abyte5[250] = -9;
        abyte5[251] = -9;
        abyte5[252] = -9;
        abyte5[253] = -9;
        abyte5[254] = -9;
        abyte5[255] = -9;
        abyte5[256] = -9;
        _ORDERED_DECODABET = abyte5;
    }




}
