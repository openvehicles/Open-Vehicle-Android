package com.openvehicles.OVMS.ui.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
	Context c;

	public Database(Context context) {
		super(context, "sampledatabase", null, 1);
		c = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("Create table mapdetails(lat text,lng text,title text,optr text,status text,usage text,AddressLine1 text,level1 text,level2 text,connction_id text,connction1 text,numberofpoint TEXT)");
		db.execSQL("CREATE TABLE if not exists company(id INTEGER PRIMARY KEY AUTOINCREMENT,userid TEXT,instance TEXT,companyname TEXT)");
		db.execSQL("CREATE TABLE if not exists latlngdetail(id INTEGER PRIMARY KEY AUTOINCREMENT,lat INTEGER,lng INTEGER)");
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

	}

	public void insert_mapdetails(String lat, String lng, String title,
			String optr, String status, String usage, String AddressLine1,
			String level1, String level2, String conn_id, String conn,
			String numberofpoint) {
		SQLiteDatabase db1 = this.getWritableDatabase();
		Cursor cursor = db1.rawQuery("select * from mapdetails where lat='"
				+ lat + "'", null);
		if (cursor.getCount() == 0) {
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues contentValues = new ContentValues();
			contentValues.put("lat", lat);
			contentValues.put("lng", lng);
			contentValues.put("title", title);
			contentValues.put("optr", optr);
			contentValues.put("status", status);
			contentValues.put("usage", usage);
			contentValues.put("AddressLine1", AddressLine1);
			contentValues.put("level1", level1);
			contentValues.put("level2", level2);
			contentValues.put("connction1", conn);
			contentValues.put("connction_id", conn_id);
			contentValues.put("numberofpoint", numberofpoint);
			db.insert("mapdetails", null, contentValues);
			db.close();
		}
		db1.close();
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

	public void addlatlngdetail(int lat, int lng) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("lat", lat);
		contentValues.put("lng", lng);
		db.insert("latlngdetail", null, contentValues);
		db.close();
	}

	public Cursor getlatlngdetail(int lat, int lng) {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from latlngdetail where lat="
				+ lat + " and lng=" + lng, null);
		return cursor;
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
}
