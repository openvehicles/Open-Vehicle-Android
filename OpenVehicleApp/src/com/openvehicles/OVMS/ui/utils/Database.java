package com.openvehicles.OVMS.ui.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.openvehicles.OVMS.entities.ChargePoint;
import com.testflightapp.lib.core.StringUtils;

public class Database extends SQLiteOpenHelper {
	Context c;

	public Database(Context context) {
		super(context, "sampledatabase", null, 2);
		c = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.i("OCM", "Database creation");
		db.execSQL("CREATE TABLE IF NOT EXISTS mapdetails(cpid INTEGER PRIMARY KEY," +
				"lat text,lng text,title text,optr text,status text,usage text," +
				"AddressLine1 text,level1 text,level2 text,connction_id text,connction1 text," +
				"numberofpoint TEXT)");
		db.execSQL("CREATE TABLE if not exists company(id INTEGER PRIMARY KEY AUTOINCREMENT,userid TEXT,instance TEXT,companyname TEXT)");
		db.execSQL("CREATE TABLE IF NOT EXISTS latlngdetail(id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"lat INTEGER, lng INTEGER, last_update INTEGER)");
		db.execSQL("CREATE TABLE if not exists ConnectionTypes(Id INTEGER PRIMARY KEY AUTOINCREMENT,tId TEXT,title TEXT,chec TEXT,CompanyName TEXT)");
		db.execSQL("CREATE TABLE if not exists ConnectionTypes_Main(Id TEXT,tId TEXT,title TEXT)");
		db.execSQL("CREATE TABLE if not exists companydetail(companyname TEXT,buffer TEXT)");
		db.execSQL("CREATE TABLE if not exists companydetails(companyname TEXT,buffer TEXT,bufferserver TEXT)");
		db.execSQL("CREATE TABLE if not exists interviewuser(companyname TEXT,username TEXT)");
		db.execSQL("CREATE TABLE if not exists interviewuserdetails(companyname TEXT,username TEXT,buffer TEXT,bufferserver TEXT)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.i("OCM", "Database upgrade from version " + oldVersion + " to version " + newVersion);
		if (oldVersion == 1 && newVersion == 2) {

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
	}

	public void insert_mapdetails(ChargePoint cp) {
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues contentValues = new ContentValues();
			contentValues.put("cpid", cp.ID); // primary key
			if (cp.AddressInfo != null) {
				contentValues.put("lat", cp.AddressInfo.Latitude);
				contentValues.put("lng", cp.AddressInfo.Longitude);
				contentValues.put("title", ifNull(cp.AddressInfo.Title, "untitled"));
				contentValues.put("AddressLine1", ifNull(cp.AddressInfo.AddressLine1, ""));
			}
			if (cp.OperatorInfo != null) {
				contentValues.put("optr", ifNull(cp.OperatorInfo.Title, "unknown"));
			}
			if (cp.StatusType != null) {
				contentValues.put("status", ifNull(cp.StatusType.Title, "unknown"));
			}
			if (cp.UsageType != null) {
				contentValues.put("usage", ifNull(cp.UsageType.Title, "unknown"));
			}
			if (cp.Connections != null) {
				if (cp.Connections.length >= 1) {
					ChargePoint.Connection con = cp.Connections[0];
					if (con.Level != null)
						contentValues.put("level1", ifNull(con.Level.Title, "unknown"));
					if (con.ConnectionType != null) {
						contentValues.put("connction1", ifNull(con.ConnectionType.Title, "unknown"));
						contentValues.put("connction_id", ifNull(con.ConnectionType.ID, "0"));
					}
				}
				if (cp.Connections.length >= 2) {
					ChargePoint.Connection con = cp.Connections[1];
					if (con.Level != null)
						contentValues.put("level2", ifNull(con.Level.Title, "unknown"));
				}
			}
			contentValues.put("numberofpoint", ifNull(cp.NumberOfPoints, "1"));
			db.insertWithOnConflict("mapdetails", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
			db.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void addCompany(String userid, String companyname, String instance) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("userid", userid);
		contentValues.put("companyname", companyname);
		contentValues.put("instance", instance);
		db.insert("company", null, contentValues);
		db.close();
	}

	// update/add OCM latlng cache tile:
	public void addlatlngdetail(int lat, int lng) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("lat", lat);
		contentValues.put("lng", lng);
		contentValues.put("last_update", System.currentTimeMillis() / 1000);
		if (db.update("latlngdetail", contentValues, "lat=" + lat + " AND lng=" + lng, null) == 0) {
			db.insert("latlngdetail", null, contentValues);
		}
		db.close();
	}

	// query OCM latlng cache tile:
	public Cursor getlatlngdetail(int lat, int lng) {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from latlngdetail where lat="
				+ lat + " and lng=" + lng, null);
		return cursor;
	}

	// clear OCM latlng cache:
	public void clear_latlngdetail() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete("latlngdetail", null, null);
	}

	public void addConnectionTypesdetail(String tId, String Title,
			String check, String CompanyName) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("tId", tId);
		contentValues.put("Title", Title);
		contentValues.put("chec", check);
		contentValues.put("CompanyName", CompanyName);
		db.insert("ConnectionTypes", null, contentValues);
		db.close();
	}
	public void addConnectionTypes_Main(String Id, String tId, String Title) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("Id", Id);
		contentValues.put("tId", tId);
		contentValues.put("Title", Title);
		db.insert("ConnectionTypes_Main", null, contentValues);
		db.close();
	}
	
	public void updaetConnectionTypesdetail(String Id, String check,
			String CompanyName) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("Id", Id);
		contentValues.put("chec", check);
		String whereClause = "Id=" + Id;
		contentValues.put("CompanyName", CompanyName);
		db.update("ConnectionTypes", contentValues, whereClause, null);
		db.close();
	}

	public void addcompanydetail(String companyname, String buffer) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("buffer", buffer);
		contentValues.put("companyname", companyname);
		db.insert("companydetail", null, contentValues);
		db.close();
	}

	public void addcompanydetail1(String companyname, String buffer,
			String bufferserver) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("buffer", buffer);
		contentValues.put("bufferserver", bufferserver);
		contentValues.put("companyname", companyname);
		db.insert("companydetails", null, contentValues);
		db.close();
	}

	public void updatecompanydetail(String companyname, String buffer,
			String bufferserver) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("buffer", buffer);
		contentValues.put("bufferserver", bufferserver);
		contentValues.put("companyname", companyname);
		db.update("companydetails", contentValues, "companyname='"
				+ companyname + "'", null);
		db.close();
	}

	public void addinterviewuser(String companyname, String user) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("username", user);
		contentValues.put("companyname", companyname);
		db.insert("interviewuser", null, contentValues);
		db.close();
	}

	public void addinterviewuserdetails(String companyname, String user,
			String bufferserver) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("username", user);
		contentValues.put("buffer", bufferserver);
		contentValues.put("bufferserver", bufferserver);
		contentValues.put("companyname", companyname);
		db.insert("interviewuserdetails", null, contentValues);
		db.close();
	}

	public void updateinterviewuser(String companyname, String user) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("username", user);
		contentValues.put("companyname", companyname);
		db.update("interviewuser", contentValues, "companyname='" + companyname
				+ "'", null);
		db.close();
	}

	public void updateinterviewuserdetails(String companyname, String user,
			String bufferserver) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("username", user);
		contentValues.put("buffer", bufferserver);
		contentValues.put("bufferserver", bufferserver);
		contentValues.put("companyname", companyname);
		db.update("interviewuserdetails", contentValues, "companyname='"
				+ companyname + "'", null);
		db.close();
	}

	public Cursor get_mapdetails() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from mapdetails", null);
		return cursor;
	}

	public Cursor get_mapdetails(String f_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db
				.rawQuery("select * from mapdetails where connction_id  in("
						+ f_id + ")", null);
		System.out.println("connnn" + cursor.getCount());
		return cursor;
	}

//	public Cursor get_ConnectionTypesdetails() {
//		SQLiteDatabase db = this.getWritableDatabase();
//		Cursor cursor = db.rawQuery(
//				"select * from ConnectionTypes ORDER BY title", null);
//		return cursor;
//	}
	public Cursor get_ConnectionTypes_Main() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(
				"select * from ConnectionTypes_Main ORDER BY title", null);
		return cursor;
	}

	public Cursor get_ConnectionTypesdetails(String cmpname) {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(
				"select * from ConnectionTypes where CompanyName='" + cmpname
						+ "' ORDER BY title", null);
		return cursor;
	}

	public Cursor getCompanydetails() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from company", null);
		return cursor;
	}

	public Cursor getinterviewuserdetails() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from interviewuser", null);
		return cursor;
	}

	public Cursor getinterviewuserdetailsdetails() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from interviewuserdetails", null);
		return cursor;
	}

	public Cursor getCdetails() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from companydetail", null);
		return cursor;
	}

	public Cursor getCdetails1() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from companydetails", null);
		return cursor;
	}

	public Cursor getCompanydetails(String instance) {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from company where instance='"
				+ instance + "'", null);
		return cursor;
	}

	public Cursor getsearchvalues(String name) {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(
				"SELECT * FROM producttable WHERE productidno LIKE '%" + name
						+ "%'", null);
		return cursor;
	}

	public Cursor del(String name, String company) {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(
				"delete FROM interviewuserdetails WHERE username='" + name
						+ "'&companyname='" + company + "'", null);
		return cursor;
	}

	public void dbclose() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.close();
	}

	public void deleteTable() {
		SQLiteDatabase db = this.getWritableDatabase();
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
