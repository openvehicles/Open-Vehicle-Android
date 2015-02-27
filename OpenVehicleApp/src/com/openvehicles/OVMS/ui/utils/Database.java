package com.openvehicles.OVMS.ui.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.openvehicles.OVMS.entities.ChargePoint;
import com.testflightapp.lib.core.StringUtils;

import java.util.HashMap;

public class Database extends SQLiteOpenHelper {
	private static final String TAG = "Database";

	Context c;
	SQLiteDatabase db;

	public Database(Context context) {
		super(context, "sampledatabase", null, 4);
		c = context;
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

		db.execSQL("CREATE TABLE IF NOT EXISTS mapdetails(cpid INTEGER PRIMARY KEY," +
				"lat text, lng text, title text, optr text, status text, usage text," +
				"AddressLine1 text, numberofpoint TEXT)");

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

	public void addCompany(String userid, String companyname, String instance) {
		open();
		ContentValues contentValues = new ContentValues();
		contentValues.put("userid", userid);
		contentValues.put("companyname", companyname);
		contentValues.put("instance", instance);
		db.insert("company", null, contentValues);
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

	// clear OCM latlng cache:
	public void clear_latlngdetail() {
		open();
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

	public void addcompanydetail(String companyname, String buffer) {
		open();
		ContentValues contentValues = new ContentValues();
		contentValues.put("buffer", buffer);
		contentValues.put("companyname", companyname);
		db.insert("companydetail", null, contentValues);
	}

	public void addcompanydetail1(String companyname, String buffer,
			String bufferserver) {
		open();
		ContentValues contentValues = new ContentValues();
		contentValues.put("buffer", buffer);
		contentValues.put("bufferserver", bufferserver);
		contentValues.put("companyname", companyname);
		db.insert("companydetails", null, contentValues);
	}

	public void updatecompanydetail(String companyname, String buffer,
			String bufferserver) {
		open();
		ContentValues contentValues = new ContentValues();
		contentValues.put("buffer", buffer);
		contentValues.put("bufferserver", bufferserver);
		contentValues.put("companyname", companyname);
		db.update("companydetails", contentValues, "companyname='"
				+ companyname + "'", null);
	}

	public void addinterviewuser(String companyname, String user) {
		open();
		ContentValues contentValues = new ContentValues();
		contentValues.put("username", user);
		contentValues.put("companyname", companyname);
		db.insert("interviewuser", null, contentValues);
	}

	public void addinterviewuserdetails(String companyname, String user,
			String bufferserver) {
		open();
		ContentValues contentValues = new ContentValues();
		contentValues.put("username", user);
		contentValues.put("buffer", bufferserver);
		contentValues.put("bufferserver", bufferserver);
		contentValues.put("companyname", companyname);
		db.insert("interviewuserdetails", null, contentValues);
	}

	public void updateinterviewuser(String companyname, String user) {
		open();
		ContentValues contentValues = new ContentValues();
		contentValues.put("username", user);
		contentValues.put("companyname", companyname);
		db.update("interviewuser", contentValues, "companyname='" + companyname
				+ "'", null);
	}

	public void updateinterviewuserdetails(String companyname, String user,
			String bufferserver) {
		open();
		ContentValues contentValues = new ContentValues();
		contentValues.put("username", user);
		contentValues.put("buffer", bufferserver);
		contentValues.put("bufferserver", bufferserver);
		contentValues.put("companyname", companyname);
		db.update("interviewuserdetails", contentValues, "companyname='"
				+ companyname + "'", null);
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

	public Cursor getChargePoint(String cpId) {
		open();
		Cursor cursor = db.rawQuery(
				"SELECT * FROM mapdetails WHERE cpid=" + cpId, null);
		return cursor;
	}

	public Cursor getChargePointConnections(String cpId) {
		open();
		Cursor cursor = db.rawQuery(
				"SELECT * FROM Connection WHERE conCpId=" + cpId, null);
		return cursor;
	}

	//	public Cursor get_ConnectionTypesdetails() {
//		SQLiteDatabase db = this.getWritableDatabase();
//		Cursor cursor = db.rawQuery(
//				"select * from ConnectionTypes ORDER BY title", null);
//		return cursor;
//	}
	public Cursor get_ConnectionTypes_Main() {
		open();
		Cursor cursor = db.rawQuery(
				"select * from ConnectionTypes_Main ORDER BY title", null);
		return cursor;
	}

	public Cursor get_ConnectionTypesdetails(String cmpname) {
		open();
		Cursor cursor = db.rawQuery(
				"select * from ConnectionTypes where CompanyName='" + cmpname
						+ "' ORDER BY title", null);
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

	public Cursor getCompanydetails() {
		open();
		Cursor cursor = db.rawQuery("select * from company", null);
		return cursor;
	}

	public Cursor getinterviewuserdetails() {
		open();
		Cursor cursor = db.rawQuery("select * from interviewuser", null);
		return cursor;
	}

	public Cursor getinterviewuserdetailsdetails() {
		open();
		Cursor cursor = db.rawQuery("select * from interviewuserdetails", null);
		return cursor;
	}

	public Cursor getCdetails() {
		open();
		Cursor cursor = db.rawQuery("select * from companydetail", null);
		return cursor;
	}

	public Cursor getCdetails1() {
		open();
		Cursor cursor = db.rawQuery("select * from companydetails", null);
		return cursor;
	}

	public Cursor getCompanydetails(String instance) {
		open();
		Cursor cursor = db.rawQuery("select * from company where instance='"
				+ instance + "'", null);
		return cursor;
	}

	public Cursor getsearchvalues(String name) {
		open();
		Cursor cursor = db.rawQuery(
				"SELECT * FROM producttable WHERE productidno LIKE '%" + name
						+ "%'", null);
		return cursor;
	}

	public Cursor del(String name, String company) {
		open();
		Cursor cursor = db.rawQuery(
				"delete FROM interviewuserdetails WHERE username='" + name
						+ "'&companyname='" + company + "'", null);
		return cursor;
	}

	public void deleteTable() {
		open();
		String deleteSQL = "delete from producttable";
		db.execSQL(deleteSQL);
	}


	public static <T> T ifNull(T toCheck, T ifNull){
		if(toCheck == null){
			return ifNull;
		}
		return toCheck;
	}

}
