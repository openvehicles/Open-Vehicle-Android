package com.openvehicles.OVMS.ui.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.openvehicles.OVMS.R;
import com.openvehicles.OVMS.entities.ChargePoint;
import com.openvehicles.OVMS.utils.NotificationData;
import com.openvehicles.OVMS.utils.OVMSNotifications;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Database extends SQLiteOpenHelper {
	private static final String TAG = "Database";
	private static final int SCHEMA_VERSION = 6;

	Context context;
	SQLiteDatabase db;
	public SimpleDateFormat isoDateTime;

	public Database(Context context) {
		super(context, "sampledatabase", null, SCHEMA_VERSION);
		this.context = context;
		this.isoDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
	}

	public void open() {
		db = this.getWritableDatabase();
	}

	public void beginWrite() {
		open();
		db.beginTransaction();
	}

	public void endWrite(boolean commit) {
		if (commit)
			db.setTransactionSuccessful();
		db.endTransaction();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(TAG, "Database creation");

		db.execSQL("CREATE TABLE mapdetails(cpid INTEGER PRIMARY KEY," +
				"Latitude TEXT, Longitude TEXT, Title TEXT," +
				"OperatorInfo TEXT, StatusType TEXT, UsageType TEXT," +
				"AddressLine1 TEXT, RelatedURL TEXT, UsageCost TEXT," +
				"AccessComments TEXT, GeneralComments TEXT," +
				"NumberOfPoints TEXT, DateLastStatusUpdate TEXT)");

		db.execSQL("CREATE TABLE if not exists company(id INTEGER PRIMARY KEY AUTOINCREMENT,userid TEXT,instance TEXT,companyname TEXT)");

		db.execSQL("CREATE TABLE IF NOT EXISTS latlngdetail(id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"lat INTEGER, lng INTEGER, last_update INTEGER)");

		db.execSQL("CREATE TABLE if not exists ConnectionTypes(Id INTEGER PRIMARY KEY AUTOINCREMENT,tId TEXT,title TEXT,chec TEXT,CompanyName TEXT)");
		db.execSQL("CREATE TABLE if not exists ConnectionTypes_Main(Id TEXT,tId TEXT,title TEXT)");

		db.execSQL("CREATE TABLE if not exists companydetail(companyname TEXT,buffer TEXT)");
		db.execSQL("CREATE TABLE if not exists companydetails(companyname TEXT,buffer TEXT,bufferserver TEXT)");
		db.execSQL("CREATE TABLE if not exists interviewuser(companyname TEXT,username TEXT)");
		db.execSQL("CREATE TABLE if not exists interviewuserdetails(companyname TEXT,username TEXT,buffer TEXT,bufferserver TEXT)");

		// Version 3:
		// multiple connections per chargepoint:
		db.execSQL("CREATE TABLE IF NOT EXISTS Connection(" +
				"conCpId INTEGER, conTypeId INTEGER," +
				"conTypeTitle TEXT, conLevelTitle TEXT)");
		db.execSQL("CREATE INDEX conCp ON Connection (conCpId)");
		db.execSQL("CREATE INDEX conType ON Connection (conTypeId)");

		// create Notifications table:
		db.execSQL("CREATE TABLE IF NOT EXISTS Notification(" +
				"nID INTEGER PRIMARY KEY AUTOINCREMENT," +
				"nType TEXT, nTimestamp TEXT, nTitle TEXT, nMessage TEXT)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		Log.i(TAG, "Database upgrade from version " + oldVersion + " to version " + newVersion);

		if (oldVersion < 2) {
			// Version 2:

			// update OCM POI details:
			db.execSQL("DROP TABLE mapdetails");
			db.execSQL("CREATE TABLE mapdetails(cpid INTEGER PRIMARY KEY," +
					"lat text,lng text,title text,optr text,status text,usage text," +
					"AddressLine1 text,level1 text,level2 text,connction_id text,connction1 text," +
					"numberofpoint TEXT)");

			// update OCM POI cache:
			db.execSQL("DROP TABLE latlngdetail");
			db.execSQL("CREATE TABLE latlngdetail(id INTEGER PRIMARY KEY AUTOINCREMENT," +
					"lat INTEGER, lng INTEGER, last_update INTEGER)");

		}

		if (oldVersion < 3) {
			// Version 3:

			// multiple connections per chargepoint:
			db.execSQL("CREATE TABLE IF NOT EXISTS Connection(" +
					"conCpId INTEGER, conTypeId INTEGER," +
					"conTypeTitle TEXT, conLevelTitle TEXT)");
			db.execSQL("CREATE INDEX conCp ON Connection (conCpId)");
			db.execSQL("CREATE INDEX conType ON Connection (conTypeId)");

			// this obsoletes mapdetails fields:
			// 		level1 text,level2 text,connction_id text,connction1 text
			// but SQLite doesn't support DROP COLUMN

			// Todo:
			// could conTypeTitle be fetched from ConnectionTypes_Main?
			// conLevelTitle: no Level core reference data available?
		}

		if (oldVersion < 4) {
			// Version 4:

			// mapdetails:
			// remove level1, level2, connction_id, connction1
			// add UsageCost, AccessComments, RelatedURL, GeneralComments
			db.execSQL("DROP TABLE mapdetails");
			db.execSQL("CREATE TABLE mapdetails(cpid INTEGER PRIMARY KEY," +
					"Latitude TEXT, Longitude TEXT, Title TEXT," +
					"OperatorInfo TEXT, StatusType TEXT, UsageType TEXT," +
					"AddressLine1 TEXT, RelatedURL TEXT, UsageCost TEXT," +
					"AccessComments TEXT, GeneralComments TEXT," +
					"NumberOfPoints TEXT)");

			// clear cache:
			db.execSQL("DELETE FROM Connection");
			db.execSQL("DELETE FROM latlngdetail");
		}

		if (oldVersion < 5) {
			// Version 5:

			// create Notifications table:
			db.execSQL("CREATE TABLE IF NOT EXISTS Notification(" +
					"nID INTEGER PRIMARY KEY AUTOINCREMENT," +
					"nType TEXT, nTimestamp TEXT, nTitle TEXT, nMessage TEXT)");

			// migrate Notifications from file storage to table:
			ArrayList<NotificationData> notifications;
			try {
				String filename = "OVMSSavedNotifications.obj";
				Log.d(TAG, "Migrating saved notifications list from internal storage file: " + filename);

				// read file:
				FileInputStream fis = context.openFileInput(filename);
				ObjectInputStream is = new ObjectInputStream(fis);
				notifications = (ArrayList<NotificationData>) is.readObject();
				is.close();
				Log.d(TAG, String.format("Loaded %d saved notifications.", notifications.size()));

				// copy to db:
				for (int i=0; i < notifications.size(); i++) {
					addNotificationInt(notifications.get(i), db);
				}
				Log.d(TAG, String.format("Added %d notifications to table.", notifications.size()));

				// done, remove file:
				context.deleteFile(filename);

			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
			}

		}

		if (oldVersion < 6) {
			// Version 6:

			// mapdetails:
			// add DateLastStatusUpdate
			db.execSQL("DROP TABLE mapdetails");
			db.execSQL("CREATE TABLE mapdetails(cpid INTEGER PRIMARY KEY," +
					"Latitude TEXT, Longitude TEXT, Title TEXT," +
					"OperatorInfo TEXT, StatusType TEXT, UsageType TEXT," +
					"AddressLine1 TEXT, RelatedURL TEXT, UsageCost TEXT," +
					"AccessComments TEXT, GeneralComments TEXT," +
					"NumberOfPoints TEXT, DateLastStatusUpdate TEXT)");

			// clear cache:
			db.execSQL("DELETE FROM Connection");
			db.execSQL("DELETE FROM latlngdetail");
		}

	}


	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// NOP = allow (to be able to redo upgrades for development)
	}


	public void insert_mapdetails(ChargePoint cp) {
		try {
			open();

			ContentValues contentValues = new ContentValues();

			contentValues.put("cpid", cp.ID); // primary key

			if (cp.AddressInfo != null) {
				contentValues.put("Latitude", cp.AddressInfo.Latitude);
				contentValues.put("Longitude", cp.AddressInfo.Longitude);
				contentValues.put("Title", ifNull(cp.AddressInfo.Title, "untitled"));
				contentValues.put("AddressLine1", ifNull(cp.AddressInfo.AddressLine1, ""));
				contentValues.put("AccessComments", ifNull(cp.AddressInfo.AccessComments, ""));
				contentValues.put("RelatedURL", ifNull(cp.AddressInfo.RelatedURL, ""));
			}

			if (cp.OperatorInfo != null) {
				contentValues.put("OperatorInfo", ifNull(cp.OperatorInfo.Title, "unknown"));
			}

			if (cp.StatusType != null) {
				contentValues.put("StatusType", ifNull(cp.StatusType.Title, "unknown"));
			}

			if (cp.UsageType != null) {
				contentValues.put("UsageType", ifNull(cp.UsageType.Title, "unknown"));
			}
			contentValues.put("UsageCost", ifNull(cp.UsageCost, "unknown"));

			contentValues.put("GeneralComments", ifNull(cp.GeneralComments, ""));
			contentValues.put("NumberOfPoints", ifNull(cp.NumberOfPoints, "1"));
			contentValues.put("DateLastStatusUpdate", ifNull(cp.DateLastStatusUpdate, ""));

			if (cp.Connections != null) {

				ContentValues addCon = new ContentValues();
				ChargePoint.Connection con;

				// rebuild associated connections:

				db.delete("Connection", "conCpId=" + cp.ID, null);

				for (int i=0; i < cp.Connections.length; i++) {

					con = cp.Connections[i];

					addCon.clear();
					addCon.put("conCpId", cp.ID);
					if (con.ConnectionType != null) {
						addCon.put("conTypeId", ifNull(con.ConnectionType.ID, "0"));
						addCon.put("conTypeTitle", ifNull(con.ConnectionType.Title, "unknown"));
					}
					if (con.Level != null) {
						addCon.put("conLevelTitle", ifNull(con.Level.Title, "unknown"));
					}

					db.insert("Connection", null, addCon);
				}

			}

			db.insertWithOnConflict("mapdetails", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	// update/add OCM latlng cache tile:
	public void addlatlngdetail(int lat, int lng) {
		open();
		ContentValues contentValues = new ContentValues();
		contentValues.put("lat", lat);
		contentValues.put("lng", lng);
		contentValues.put("last_update", System.currentTimeMillis() / 1000);
		if (db.update("latlngdetail", contentValues, "lat=" + lat + " AND lng=" + lng, null) == 0) {
			db.insert("latlngdetail", null, contentValues);
		}
	}

	// query OCM latlng cache tile:
	public Cursor getlatlngdetail(int lat, int lng) {
		open();
		Cursor cursor = db.rawQuery("select * from latlngdetail where lat="
				+ lat + " and lng=" + lng, null);
		return cursor;
	}

	// clear OCM cache:
	public void clear_mapdetails() {
		open();
		db.delete("mapdetails", null, null);
		db.delete("latlngdetail", null, null);
	}

	public void addConnectionTypes_Main(String Id, String tId, String Title) {
		open();
		ContentValues contentValues = new ContentValues();
		contentValues.put("Id", Id);
		contentValues.put("tId", tId);
		contentValues.put("Title", Title);
		db.insert("ConnectionTypes_Main", null, contentValues);
	}

	public void addConnectionTypesDetail(String tId, String Title,
										 String check, String CompanyName) {
		open();
		ContentValues contentValues = new ContentValues();
		contentValues.put("tId", tId);
		contentValues.put("Title", Title);
		contentValues.put("chec", check);
		contentValues.put("CompanyName", CompanyName);
		db.insert("ConnectionTypes", null, contentValues);
	}

	public void updateConnectionTypesDetail(String Id, String check,
											String CompanyName) {
		open();
		ContentValues contentValues = new ContentValues();
		contentValues.put("Id", Id);
		contentValues.put("chec", check);
		String whereClause = "Id=" + Id;
		contentValues.put("CompanyName", CompanyName);
		db.update("ConnectionTypes", contentValues, whereClause, null);
	}

	public void resetConnectionTypesDetail(String CompanyName) {
		open();
		ContentValues contentValues = new ContentValues();
		contentValues.put("chec", "false");
		String whereClause = "CompanyName=?";
		String whereArgs[] = { CompanyName };
		db.update("ConnectionTypes", contentValues, whereClause, whereArgs);
	}

	public Cursor get_mapdetails() {
		open();
		Cursor cursor = db.rawQuery("select * from mapdetails", null);
		return cursor;
	}

	public Cursor get_mapdetails(String filterConTypeIds) {
		open();
		Cursor cursor = db.rawQuery(
				"SELECT DISTINCT mapdetails.*" +
						"FROM mapdetails JOIN Connection ON ( conCpId = cpid )" +
						"WHERE conTypeId IN (" + filterConTypeIds + ")", null);
		return cursor;
	}

	public String get_DateLastStatusUpdate() {
		open();
		Cursor cursor = db.rawQuery("select MAX(DateLastStatusUpdate) from mapdetails", null);
		if (cursor.moveToFirst()) {
			return ifNull(cursor.getString(0), "");
		}
		return "";
	}

	public String get_DateLastStatusUpdate(int lat, int lng) {
		// Note: the local Android timestamp will be used as the "modified since"
		// 	query parameter for OCM. We subtract 1 hour to accomodate for differences.
		Cursor cursor = getlatlngdetail(lat, lng);
		if (cursor.moveToFirst()) {
			long last_update = cursor.getLong(cursor.getColumnIndex("last_update"));
			if (last_update > 0) {
				return isoDateTime.format(new Date((last_update - 3600) * 1000));
			}
		}
		return "";
	}
	public String get_DateLastStatusUpdate(LatLng pos) {
		return get_DateLastStatusUpdate((int)pos.latitude, (int)pos.longitude);
	}

	public Cursor getChargePoint(String cpId) {
		open();
		if (cpId == "") cpId = "-1";
		Cursor cursor = db.rawQuery(
				"SELECT * FROM mapdetails WHERE cpid=" + cpId, null);
		return cursor;
	}

	public Cursor getChargePointConnections(String cpId) {
		open();
		if (cpId == "") cpId = "-1";
		Cursor cursor = db.rawQuery(
				"SELECT * FROM Connection WHERE conCpId=" + cpId, null);
		return cursor;
	}

	public Cursor get_ConnectionTypes_Main() {
		open();
		Cursor cursor = db.rawQuery(
				"select * from ConnectionTypes_Main ORDER BY title", null);
		return cursor;
	}

	public Cursor get_ConnectionTypesdetails(String cmpname) {
		open();
		Cursor cursor = db.rawQuery(
				"select * from ConnectionTypes where CompanyName="
						+ DatabaseUtils.sqlEscapeString(cmpname)
						+ " ORDER BY title", null);
		return cursor;
	}

	public String getConnectionFilter(String vehicleLabel) {
		open();
		Cursor cursor = get_ConnectionTypesdetails(vehicleLabel);
		StringBuffer idList = new StringBuffer(1000);
		while (cursor.moveToNext()) {
			if (cursor.getString(cursor.getColumnIndex("chec")).equals("true"))
				idList.append("," + cursor.getString(cursor.getColumnIndex("tId")));
		}
		cursor.close();
		return (idList.length() > 1) ? idList.substring(1) : "";
	}

	public void addNotification(NotificationData notificationData) {
		open();
		addNotificationInt(notificationData, db);
	}

	private void addNotificationInt(NotificationData notificationData, SQLiteDatabase db) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("nType", notificationData.Type);
		contentValues.put("nTimestamp", isoDateTime.format(notificationData.Timestamp));
		contentValues.put("nTitle", notificationData.Title);
		contentValues.put("nMessage", notificationData.Message);
		db.insert("Notification", null, contentValues);
	}

	public void removeNotification(NotificationData notificationData) {
		open();
		db.delete("Notification", "nID=" + notificationData.ID, null);
	}

	public int removeOldNotifications(int keepSize) {
		int deletedRows = 0;
		open();
		Cursor maxIdCursor = db.rawQuery("SELECT MAX(nID) AS maxID FROM Notifications", null);
		if (maxIdCursor.moveToFirst()) {
			long maxId = maxIdCursor.getLong(maxIdCursor.getColumnIndex("maxID"));
			deletedRows = db.delete("Notification", "nID <= " + (maxId - keepSize), null);
		}
		return deletedRows;
	}

	public Cursor getNotifications() {
		open();

		// Due to a strange yet unknown bug sometimes the timestamp year of single notifications
		// changes to 2201 (always?), letting the message be stuck at the end of the list.
		// To fix, we need check & correct these here on every call :-/
		fixNotificationTimes();

		Cursor cursor = db.rawQuery("SELECT * FROM Notification ORDER BY nTimestamp, nID", null);
		return cursor;
	}

	private void fixNotificationTimes() {

		Date ts1, ts2, ts3;
		GregorianCalendar cal1 = new GregorianCalendar(), cal2 = new GregorianCalendar();

		// get latest entry:

		Cursor latest = db.rawQuery("SELECT * FROM Notification ORDER BY nID DESC LIMIT 1", null);
		if (latest == null || !latest.moveToNext())
			return;

		try {
			ts1 = isoDateTime.parse(latest.getString(latest.getColumnIndex("nTimestamp")));
		} catch (Exception e) {
			ts1 = new Date();
		}
		cal1.setTime(ts1);

		// get entries with timestamps after latest:

		Cursor wrongts = db.rawQuery(
				"SELECT * FROM Notification WHERE nTimestamp > ?",
				new String[] { latest.getString(latest.getColumnIndex("nTimestamp")) });

		while (wrongts != null && wrongts.moveToNext()) {
			try {

				// fix timestamp by replacing the year with the current or last year:

				ts2 = isoDateTime.parse(wrongts.getString(wrongts.getColumnIndex("nTimestamp")));

				cal2.setTime(ts2);
				cal2.set(Calendar.YEAR, cal1.get(Calendar.YEAR));
				if (cal2.after(cal1))
					cal2.set(Calendar.YEAR, cal1.get(Calendar.YEAR)-1);

				ts3 = new Date(cal2.getTimeInMillis());

				Log.d(TAG, "fixNotificationTimes: latest='" + ts1 +
						"', found '" + ts2 + "' → update to '" + ts3 + "'");

				db.execSQL(
						"UPDATE Notification SET nTimestamp = ? WHERE nID = ?",
						new String[] {
								isoDateTime.format(ts3),
								wrongts.getString(wrongts.getColumnIndex("nID"))
						});

			} catch (Exception e) {
				// ignore
			}
		}

		if (wrongts != null)
			wrongts.close();
		latest.close();
	}

	public NotificationData getNextNotification(Cursor cursor) {
		if (cursor == null || !cursor.moveToNext())
			return null;

		Date timestamp;
		try {
			timestamp = isoDateTime.parse(cursor.getString(cursor.getColumnIndex("nTimestamp")));
		} catch (Exception e) {
			timestamp = new Date();
		}

		NotificationData data = new NotificationData(
				cursor.getLong(cursor.getColumnIndex("nID")),
				cursor.getInt(cursor.getColumnIndex("nType")),
				timestamp,
				cursor.getString(cursor.getColumnIndex("nTitle")),
				cursor.getString(cursor.getColumnIndex("nMessage")));

		//Log.d(TAG, "getNextNotification: index=" + cursor.getPosition() + " => nID=" + data.ID
		//	+ " nTitle='" + data.Title + "' nTimestamp='" + data.Timestamp + "'");

		return data;
	}


	public static <T> T ifNull(T toCheck, T ifNull){
		if(toCheck == null){
			return ifNull;
		}
		return toCheck;
	}

}
