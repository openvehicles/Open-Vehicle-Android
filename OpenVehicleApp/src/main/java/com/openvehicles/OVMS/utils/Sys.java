package com.openvehicles.OVMS.utils;

import android.app.PendingIntent;
import android.os.Build;

import java.util.Random;

public class Sys {

	/**
	 * getMutableFlags: Intent mutability flag compatibility helper, adds FLAG_MUTABLE
	 * 	or FLAG_IMMUTABLE as requested and needed by the current device
	 *
	 * @param flags -- Intent flags to extend
	 * @param mutable -- add true=FLAG_MUTABLE / false=FLAG_IMMUTABLE
	 * @return flags extended by mutability
	 */
	public static int getMutableFlags(int flags, boolean mutable) {
		if (mutable && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
			flags |= PendingIntent.FLAG_MUTABLE;
		}
		else if (!mutable && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			flags |= PendingIntent.FLAG_IMMUTABLE;
		}
		return flags;
	}

	/**
	 * getRandomString: key generation utility, returning a single "word" for easy user selectability
	 * @param length -- length of string to generate
	 * @return random key string
	 */
	public static String getRandomString(final int length) {
		final String charset ="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		final Random random = new Random();
		final StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; ++i) {
			sb.append(charset.charAt(random.nextInt(charset.length())));
		}
		return sb.toString();
	}

}
