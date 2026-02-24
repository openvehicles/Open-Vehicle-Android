package com.openvehicles.OVMS.utils

import android.app.PendingIntent
import android.os.Build
import android.os.Bundle
import java.util.Random

object Sys {

    /**
     * getMutableFlags: Intent mutability flag compatibility helper, adds FLAG_MUTABLE
     * or FLAG_IMMUTABLE as requested and needed by the current device
     *
     * @param intentFlags -- Intent flags to extend
     * @param mutable -- add true=FLAG_MUTABLE / false=FLAG_IMMUTABLE
     * @return flags extended by mutability
     */
    fun getMutableFlags(intentFlags: Int, mutable: Boolean): Int {
        var flags = intentFlags
        if (mutable && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            flags = flags or PendingIntent.FLAG_MUTABLE
        } else if (!mutable && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flags = flags or PendingIntent.FLAG_IMMUTABLE
        }
        return flags
    }

    /**
     * getRandomString: key generation utility, returning a single "word" for easy user selectability
     * @param length -- length of string to generate
     * @return random key string
     */
    @JvmStatic
    fun getRandomString(length: Int): String {
        val charset = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
        val random = Random()
        val sb = StringBuilder(length)
        for (i in 0 until length) {
            sb.append(charset[random.nextInt(charset.length)])
        }
        return sb.toString()
    }

    /**
     * toString(Bundle): create text representation of any Bundle, parceled or unparceled
     * Credit: https://github.com/android-hacker/VirtualXposed/blob/master/VirtualApp/lib/src/main/java/com/lody/virtual/helper/utils/VLog.java
     * @param bundle parceled/standard Bundle
     * @return text representation suitable for logging, or null if bundle is null
     */
    fun toString(bundle: Bundle?): String? {
        if (bundle == null) {
            return null
        }

        if (bundle.get("mParcelledData") != null) {
            val keys = bundle.keySet()
            val stringBuilder = StringBuilder("Bundle[")
            if (keys != null) {
                for (key in keys) {
                    stringBuilder.append(key)
                    stringBuilder.append("=")
                    stringBuilder.append(bundle.get(key))
                    stringBuilder.append(",")
                }
            }
            stringBuilder.append("]")
            return stringBuilder.toString()
        }

        return bundle.toString()
    }
}
