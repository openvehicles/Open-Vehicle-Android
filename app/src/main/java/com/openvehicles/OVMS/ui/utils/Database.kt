package com.openvehicles.OVMS.ui.utils

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.openvehicles.OVMS.entities.ChargePoint
import com.openvehicles.OVMS.entities.StoredCommand
import com.openvehicles.OVMS.utils.NotificationData
import java.io.ObjectInputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale

class Database(

    private var context: Context

) : SQLiteOpenHelper(context, "sampledatabase", null, SCHEMA_VERSION) {

    private var db: SQLiteDatabase? = null
    private var isoDateTime: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

    fun open() {
        db = this.writableDatabase
    }

    fun beginWrite() {
        open()
        db!!.beginTransaction()
    }

    fun endWrite(commit: Boolean) {
        if (commit) {
            db!!.setTransactionSuccessful()
        }
        db!!.endTransaction()
    }

    override fun onCreate(db: SQLiteDatabase) {
        Log.i(TAG, "Database creation")
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS mapdetails(cpid INTEGER PRIMARY KEY," +
                    "Latitude TEXT, Longitude TEXT, Title TEXT," +
                    "OperatorInfo TEXT, StatusType TEXT, UsageType TEXT," +
                    "AddressLine1 TEXT, RelatedURL TEXT, UsageCost TEXT," +
                    "AccessComments TEXT, GeneralComments TEXT," +
                    "NumberOfPoints TEXT, DateLastStatusUpdate TEXT)"
        )
        db.execSQL("CREATE TABLE if not exists company(id INTEGER PRIMARY KEY AUTOINCREMENT,userid TEXT,instance TEXT,companyname TEXT)")
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS latlngdetail(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "lat INTEGER, lng INTEGER, last_update INTEGER)"
        )
        db.execSQL("CREATE TABLE if not exists ConnectionTypes(Id INTEGER PRIMARY KEY AUTOINCREMENT,tId TEXT,title TEXT,chec TEXT,CompanyName TEXT)")
        db.execSQL("CREATE TABLE if not exists ConnectionTypes_Main(Id TEXT,tId TEXT,title TEXT)")
        db.execSQL("CREATE TABLE if not exists companydetail(companyname TEXT,buffer TEXT)")
        db.execSQL("CREATE TABLE if not exists companydetails(companyname TEXT,buffer TEXT,bufferserver TEXT)")
        db.execSQL("CREATE TABLE if not exists interviewuser(companyname TEXT,username TEXT)")
        db.execSQL("CREATE TABLE if not exists interviewuserdetails(companyname TEXT,username TEXT,buffer TEXT,bufferserver TEXT)")

        // Version 3:
        // multiple connections per chargepoint:
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS Connection(" +
                    "conCpId INTEGER, conTypeId INTEGER," +
                    "conTypeTitle TEXT, conLevelTitle TEXT)"
        )
        db.execSQL("CREATE INDEX conCp ON Connection (conCpId)")
        db.execSQL("CREATE INDEX conType ON Connection (conTypeId)")

        // Version 6:
        // create Notifications table:
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS Notification(" +
                    "nID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nType TEXT, nTimestamp TEXT, nTitle TEXT, nMessage TEXT)"
        )

        // Version 7:
        // create StoredCommand table:
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS StoredCommand(" +
                    "scKey INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "scTitle TEXT, scCommand TEXT)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.i(TAG, "Database upgrade from version $oldVersion to version $newVersion")
        if (oldVersion < 2) {
            // Version 2:

            // update OCM POI details:
            db.execSQL("DROP TABLE mapdetails")
            db.execSQL(
                "CREATE TABLE mapdetails(cpid INTEGER PRIMARY KEY," +
                        "lat text,lng text,title text,optr text,status text,usage text," +
                        "AddressLine1 text,level1 text,level2 text,connction_id text,connction1 text," +
                        "numberofpoint TEXT)"
            )

            // update OCM POI cache:
            db.execSQL("DROP TABLE latlngdetail")
            db.execSQL(
                "CREATE TABLE latlngdetail(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "lat INTEGER, lng INTEGER, last_update INTEGER)"
            )
        }
        if (oldVersion < 3) {
            // Version 3:

            // multiple connections per chargepoint:
            db.execSQL(
                "CREATE TABLE IF NOT EXISTS Connection(" +
                        "conCpId INTEGER, conTypeId INTEGER," +
                        "conTypeTitle TEXT, conLevelTitle TEXT)"
            )
            db.execSQL("CREATE INDEX conCp ON Connection (conCpId)")
            db.execSQL("CREATE INDEX conType ON Connection (conTypeId)")

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
            db.execSQL("DROP TABLE mapdetails")
            db.execSQL(
                "CREATE TABLE mapdetails(cpid INTEGER PRIMARY KEY," +
                        "Latitude TEXT, Longitude TEXT, Title TEXT," +
                        "OperatorInfo TEXT, StatusType TEXT, UsageType TEXT," +
                        "AddressLine1 TEXT, RelatedURL TEXT, UsageCost TEXT," +
                        "AccessComments TEXT, GeneralComments TEXT," +
                        "NumberOfPoints TEXT)"
            )

            // clear cache:
            db.execSQL("DELETE FROM Connection")
            db.execSQL("DELETE FROM latlngdetail")
        }
        if (oldVersion < 5) {
            // Version 5:

            // create Notifications table:
            db.execSQL(
                "CREATE TABLE IF NOT EXISTS Notification(" +
                        "nID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "nType TEXT, nTimestamp TEXT, nTitle TEXT, nMessage TEXT)"
            )

            // migrate Notifications from file storage to table:
            val notifications: ArrayList<NotificationData>
            try {
                val filename = "OVMSSavedNotifications.obj"
                Log.d(
                    TAG,
                    "Migrating saved notifications list from internal storage file: $filename"
                )

                // read file:
                val fis = context.openFileInput(filename)
                val `is` = ObjectInputStream(fis)
                notifications = `is`.readObject() as ArrayList<NotificationData>
                `is`.close()
                Log.d(TAG, String.format("Loaded %d saved notifications.", notifications.size))

                // copy to db:
                for (i in notifications.indices) {
                    addNotification(notifications[i], db)
                }
                Log.d(TAG, String.format("Added %d notifications to table.", notifications.size))

                // done, remove file:
                context.deleteFile(filename)
            } catch (e: Exception) {
                Log.e(TAG, e.message!!)
            }
        }
        if (oldVersion < 6) {
            // Version 6:

            // mapdetails:
            // add DateLastStatusUpdate
            db.execSQL("DROP TABLE mapdetails")
            db.execSQL(
                "CREATE TABLE mapdetails(cpid INTEGER PRIMARY KEY," +
                        "Latitude TEXT, Longitude TEXT, Title TEXT," +
                        "OperatorInfo TEXT, StatusType TEXT, UsageType TEXT," +
                        "AddressLine1 TEXT, RelatedURL TEXT, UsageCost TEXT," +
                        "AccessComments TEXT, GeneralComments TEXT," +
                        "NumberOfPoints TEXT, DateLastStatusUpdate TEXT)"
            )

            // clear cache:
            db.execSQL("DELETE FROM Connection")
            db.execSQL("DELETE FROM latlngdetail")
        }
        if (oldVersion < 7) {
            // Version 7:

            // create StoredCommand table:
            db.execSQL(
                "CREATE TABLE IF NOT EXISTS StoredCommand(" +
                        "scKey INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "scTitle TEXT, scCommand TEXT)"
            )
        }
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // NOP = allow (to be able to redo upgrades for development)
    }

    fun insertMapDetails(cp: ChargePoint) {
        try {
            open()
            val contentValues = ContentValues()
            contentValues.put("cpid", cp.id) // primary key
            contentValues.put("Latitude", cp.addressInfo?.latitude ?: "unknown")
            contentValues.put("Longitude", cp.addressInfo?.longitude ?: "unknown")
            contentValues.put("Title", cp.addressInfo?.title ?: "untitled")
            contentValues.put("AddressLine1", cp.addressInfo?.addressLine1 ?: "")
            contentValues.put("AccessComments", cp.addressInfo?.accessComments ?: "")
            contentValues.put("RelatedURL", cp.addressInfo?.relatedUrl ?: "")
            contentValues.put("OperatorInfo", cp.operatorInfo?.title ?: "unknown")
            contentValues.put("StatusType", cp.statusType?.title ?: "unknown")
            contentValues.put("UsageType", cp.usageType?.title ?: "unknown")
            contentValues.put("UsageCost", cp.usageCost ?: "unknown")
            contentValues.put("GeneralComments", cp.generalComments ?: "")
            contentValues.put("NumberOfPoints", cp.numberOfPoints ?: "1")
            contentValues.put("DateLastStatusUpdate", cp.dateLastStatusUpdate ?: "")
            val addCon = ContentValues()
            var con: ChargePoint.Connection

            // rebuild associated connections:
            db!!.delete("Connection", "conCpId=" + cp.id, null)
            for (i in cp.connections.indices) {
                con = cp.connections[i]!!
                addCon.clear()
                addCon.put("conCpId", cp.id)
                if (con.connectionType != null) {
                    addCon.put("conTypeId", con.connectionType!!.id ?: "0")
                    addCon.put("conTypeTitle", con.connectionType!!.title ?: "unknown")
                }
                if (con.level != null) {
                    addCon.put("conLevelTitle", con.level!!.title ?: "unknown")
                }
                db!!.insert("Connection", null, addCon)
            }
            db!!.insertWithOnConflict(
                "mapdetails",
                null,
                contentValues,
                SQLiteDatabase.CONFLICT_REPLACE
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // update/add OCM latlng cache tile:
    fun addLatLngDetail(lat: Int, lng: Int) {
        open()
        val contentValues = ContentValues().apply {
            put("lat", lat)
            put("lng", lng)
            put("last_update", System.currentTimeMillis() / 1000)
        }
        val dbUpdateResult = db!!.update("latlngdetail", contentValues, "lat=$lat AND lng=$lng", null)
        if (dbUpdateResult == 0) {
            db!!.insert("latlngdetail", null, contentValues)
        }
    }

    // query OCM latlng cache tile:
    fun getLatLngDetail(lat: Int, lng: Int): Cursor {
        open()
        return db!!.rawQuery(
            "select * from latlngdetail where lat="
                    + lat + " and lng=" + lng, null
        )
    }

    // clear OCM cache:
    fun clearMapDetails() {
        open()
        db!!.delete("mapdetails", null, null)
        db!!.delete("latlngdetail", null, null)
    }

    fun addConnectionTypesMain(id: String?, tId: String?, title: String?) {
        open()
        val contentValues = ContentValues().apply {
            put("Id", id)
            put("tId", tId)
            put("Title", title)
        }
        db!!.insert("ConnectionTypes_Main", null, contentValues)
    }

    fun addConnectionTypesDetail(
        tId: String?,
        title: String?,
        check: String?,
        companyName: String?
    ) {
        open()
        val contentValues = ContentValues().apply {
            put("tId", tId)
            put("Title", title)
            put("chec", check)
            put("CompanyName", companyName)
        }
        db!!.insert("ConnectionTypes", null, contentValues)
    }

    fun updateConnectionTypesDetail(
        id: String?,
        check: String?,
        companyName: String?
    ) {
        open()
        val contentValues = ContentValues().apply {
            put("Id", id)
            put("chec", check)
            put("CompanyName", companyName)
        }
        val whereClause = "Id=$id"
        db!!.update("ConnectionTypes", contentValues, whereClause, null)
    }

    fun resetConnectionTypesDetail(companyName: String) {
        open()
        val contentValues = ContentValues().apply {
            put("chec", "false")
        }
        val whereClause = "CompanyName=?"
        val whereArgs = arrayOf(companyName)
        db!!.update("ConnectionTypes", contentValues, whereClause, whereArgs)
    }

    fun getMapDetails(): Cursor {
        open()
        return db!!.rawQuery("select * from mapdetails", null)
    }

    fun getMapDetails(filterConTypeIds: String): Cursor {
        open()
        return db!!.rawQuery(
            "SELECT DISTINCT mapdetails.*" +
                    "FROM mapdetails JOIN Connection ON ( conCpId = cpid )" +
                    "WHERE conTypeId IN (" + filterConTypeIds + ")", null
        )
    }

    fun getDateLastStatusUpdate(): String {
        open()
        val cursor = db!!.rawQuery("select MAX(DateLastStatusUpdate) from mapdetails", null)
        var result = ""
        if (cursor.moveToFirst()) {
            result = cursor.getString(0) ?: ""
        }
        cursor.close()
        return result
    }

    fun getDateLastStatusUpdate(lat: Int, lng: Int): String {
        // Note: the local Android timestamp will be used as the "modified since"
        // 	query parameter for OCM. We subtract 1 hour to accommodate for differences.
        val cursor = getLatLngDetail(lat, lng)
        val column = cursor.getColumnIndex("last_update")
        var result = ""
        if (column >= 0 && cursor.moveToFirst()) {
            val lastUpdate = cursor.getLong(column)
            if (lastUpdate > 0) {
                result = isoDateTime.format(Date((lastUpdate - 3600) * 1000))
            }
        }
        cursor.close()
        return result
    }

    fun getDateLastStatusUpdate(pos: LatLng): String {
        return getDateLastStatusUpdate(pos.latitude.toInt(), pos.longitude.toInt())
    }

    fun getChargePoint(chargePointId: String): Cursor {
        open()
        val cpId = if (chargePointId == "") "-1" else chargePointId

        return db!!.rawQuery(
            "SELECT * FROM mapdetails WHERE cpid=$cpId", null
        )
    }

    fun getChargePointConnections(chargePointId: String): Cursor {
        open()
        val cpId = if (chargePointId == "") "-1" else chargePointId

        return db!!.rawQuery(
            "SELECT * FROM Connection WHERE conCpId=$cpId", null
        )
    }

    fun getConnectionTypesMain(): Cursor {
        open()
        return db!!.rawQuery(
            "select * from ConnectionTypes_Main ORDER BY title", null
        )
    }

    fun getConnectionTypesDetails(companyName: String?): Cursor {
        open()
        return db!!.rawQuery(
            "select * from ConnectionTypes where CompanyName="
                    + DatabaseUtils.sqlEscapeString(companyName)
                    + " ORDER BY title", null
        )
    }

    fun getConnectionFilter(vehicleLabel: String?): String {
        open()
        val cursor = getConnectionTypesDetails(vehicleLabel)
        val colCheck = cursor.getColumnIndex("chec")
        val colID = cursor.getColumnIndex("tId")
        val idList = StringBuilder(1000)
        while (cursor.moveToNext()) {
            if (cursor.getString(colCheck) == "true") {
                idList
                    .append(",")
                    .append(cursor.getString(colID))
            }
        }
        cursor.close()
        return if (idList.length > 1) idList.substring(1) else ""
    }

    fun addNotification(notificationData: NotificationData) {
        open()
        addNotification(notificationData, db)
    }

    private fun addNotification(notificationData: NotificationData, db: SQLiteDatabase?) {
        val contentValues = ContentValues().apply {
            put("nType", notificationData.Type)
            put("nTimestamp", isoDateTime.format(notificationData.Timestamp))
            put("nTitle", notificationData.Title)
            put("nMessage", notificationData.Message)
        }
        db!!.insert("Notification", null, contentValues)
    }

    fun removeNotification(notificationData: NotificationData) {
        open()
        db!!.delete("Notification", "nID=" + notificationData.ID, null)
    }

    fun removeOldNotifications(keepSize: Int): Int {
        var deletedRows = 0
        open()
        val maxIdCursor = db!!.rawQuery("SELECT MAX(nID) AS maxID FROM Notifications", null)
        val colMaxId = maxIdCursor.getColumnIndex("maxID")
        if (maxIdCursor.moveToFirst()) {
            val maxId = maxIdCursor.getLong(colMaxId)
            deletedRows = db!!.delete("Notification", "nID <= " + (maxId - keepSize), null)
        }
        maxIdCursor.close()
        return deletedRows
    }

    fun getNotifications(): Cursor {
        open()

        // Due to a strange yet unknown bug sometimes the timestamp year of single notifications
        // changes to 2201 (always?), letting the message be stuck at the end of the list.
        // To fix, we need check & correct these here on every call :-/
        fixNotificationTimes()
        return db!!.rawQuery("SELECT * FROM Notification ORDER BY nTimestamp, nID", null)
    }

    private fun fixNotificationTimes() {
        var ts1: Date?
        var ts2: Date?
        var ts3: Date
        val cal1 = GregorianCalendar()
        val cal2 = GregorianCalendar()

        // get latest entry:
        val latest = db!!.rawQuery("SELECT * FROM Notification ORDER BY nID DESC LIMIT 1", null)
        val latestTimestamp = latest.getColumnIndex("nTimestamp")
        if (latestTimestamp < 0 || !latest.moveToNext()) {
            latest.close()
            return
        }
        ts1 = try {
            isoDateTime.parse(latest.getString(latestTimestamp))
        } catch (e: Exception) {
            Date()
        }
        if (ts1 == null) {
            ts1 = Date()
        }
        cal1.time = ts1

        // get entries with timestamps after latest:
        val wrongts = db!!.rawQuery(
            "SELECT * FROM Notification WHERE nTimestamp > ?",
            arrayOf(latest.getString(latestTimestamp))
        )
        val wrongtsTimestamp = wrongts.getColumnIndex("nTimestamp")
        val wrongtsId = wrongts.getColumnIndex("nID")
        while (wrongts.moveToNext()) {
            try {
                // fix timestamp by replacing the year with the current or last year:
                ts2 = isoDateTime.parse(wrongts.getString(wrongtsTimestamp))
                if (ts2 == null) continue
                cal2.time = ts2
                cal2[Calendar.YEAR] = cal1[Calendar.YEAR]
                if (cal2.after(cal1)) cal2[Calendar.YEAR] = cal1[Calendar.YEAR] - 1
                ts3 = Date(cal2.getTimeInMillis())
                Log.d(
                    TAG, "fixNotificationTimes: latest='" + ts1 +
                            "', found '" + ts2 + "' → update to '" + ts3 + "'"
                )
                db!!.execSQL(
                    "UPDATE Notification SET nTimestamp = ? WHERE nID = ?", arrayOf(
                        isoDateTime.format(ts3),
                        wrongts.getString(wrongtsId)
                    )
                )
            } catch (e: Exception) {
                // ignore
            }
        }
        wrongts.close()
        latest.close()
    }

    fun getNextNotification(cursor: Cursor): NotificationData? {
        val colTimestamp = cursor.getColumnIndex("nTimestamp")
        val colID = cursor.getColumnIndex("nID")
        val colType = cursor.getColumnIndex("nType")
        val colTitle = cursor.getColumnIndex("nTitle")
        val colMessage = cursor.getColumnIndex("nMessage")
        if (colTimestamp < 0 || colID < 0 || colType < 0 || colTitle < 0 || colMessage < 0 || !cursor.moveToNext()) return null
        val timestamp: Date? = try {
            isoDateTime.parse(cursor.getString(colTimestamp))
        } catch (e: Exception) {
            Date()
        }

        //Log.d(TAG, "getNextNotification: index=" + cursor.getPosition() + " => nID=" + data.ID
        //	+ " nTitle='" + data.Title + "' nTimestamp='" + data.Timestamp + "'");
        return NotificationData(
            cursor.getLong(colID),
            cursor.getInt(colType),
            timestamp!!,
            cursor.getString(colTitle),
            cursor.getString(colMessage)
        )
    }

    fun saveStoredCommand(cmd: StoredCommand?): Boolean {
        if (cmd == null) {
            return false
        }
        open()
        val contentValues = ContentValues().apply {
            put("scTitle", cmd.title)
            put("scCommand", cmd.command)
        }
        val rowId: Long
        if (cmd.key == 0L) {
            rowId = db!!.insert("StoredCommand", null, contentValues)
            if (rowId > 0) {
                cmd.key = rowId
            }
        } else {
            contentValues.put("scKey", cmd.key)
            rowId = db!!.replace("StoredCommand", null, contentValues)
        }
        return rowId > 0
    }

    fun deleteStoredCommand(cmd: StoredCommand?): Boolean {
        if (cmd == null) {
            return false
        }
        open()
        var rows = 0
        if (cmd.key > 0) {
            rows = db!!.delete("StoredCommand", "scKey=" + cmd.key, null)
        }
        return rows > 0
    }

    fun getStoredCommands(): ArrayList<StoredCommand> {
        open()
        val cursor = db!!.rawQuery("SELECT * FROM StoredCommand ORDER BY LOWER(scTitle), scKey", null)
        val colKey = cursor.getColumnIndex("scKey")
        val colTitle = cursor.getColumnIndex("scTitle")
        val colCommand = cursor.getColumnIndex("scCommand")
        val list = ArrayList<StoredCommand>(cursor.count)
        while (cursor.moveToNext()) {
            list.add(
                StoredCommand(
                    cursor.getLong(colKey),
                    cursor.getString(colTitle),
                    cursor.getString(colCommand)
                )
            )
        }
        cursor.close()
        return list
    }

    /*
     * Inner types
     */

    private companion object {
        private const val TAG = "Database"
        private const val SCHEMA_VERSION = 7
    }
}
