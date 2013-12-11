/**
 * 
 */
package com.luttu;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @author android
 * 
 */
public class AppPrefes {

	private SharedPreferences appSharedPrefs;
	private Editor prefsEditor;

	public AppPrefes(Context context, String Preferncename) {
		this.appSharedPrefs = context.getSharedPreferences(Preferncename,
				Activity.MODE_PRIVATE);
		this.prefsEditor = appSharedPrefs.edit();
	}

	/****
	 * 
	 * getdata() get the value from the preference
	 * 
	 * */
	public String getData(String key) {
		return appSharedPrefs.getString(key, "");
	}

	/****
	 * 
	 * SaveData() save the value to the preference
	 * 
	 * */
	public void SaveData(String Tag, String text) {
		prefsEditor.putString(Tag, text);
		prefsEditor.commit();
	}

	
}
