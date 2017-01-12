package com.ty.highway.highwaysystem.support.olddata;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @className DBHelp
 * @author fuweiwei
 * @date 2015-10-16 AM 09:49:44
 * 数据库操作类，处理旧版数据
 */
public class DBHelpOld {
	public SQLiteDatabase sqlite;
	private String dbPath = Environment.getExternalStorageDirectory().getPath()+ "/";
	private String dbFile = dbPath + "test1.db";
	private File file = new File(dbFile);
	private AtomicInteger mOpenCounter = new AtomicInteger();
	private Context mContext;

	public DBHelpOld(Context mContext) {

		this.mContext = mContext;
			sqlite = SQLiteDatabase.openOrCreateDatabase(file, null);
	}
	public boolean insert(ContentValues values, String table_name) {
		if(mOpenCounter.incrementAndGet() == 1) {
			// Opening new database
			sqlite = SQLiteDatabase.openOrCreateDatabase(file, null);
		}
		return insert(values, table_name, sqlite);
	}

	public boolean insert(ContentValues values, String table_name, SQLiteDatabase db){
		db.beginTransaction();
		Long rowId = db.insert(table_name, null, values);
		db.setTransactionSuccessful();
		db.endTransaction();
		if (rowId == -1) {
			return false;
		}
		return true;
	}


	public void close() {
		if(mOpenCounter.decrementAndGet() == 0) {
			// Closing database
			if (sqlite != null) {
				sqlite.close();
			}

		}
	}

	public SQLiteDatabase getDatabase() {
		synchronized (DBHelpOld.class) {
			if(mOpenCounter.incrementAndGet() == 1) {
				// Opening new database
				sqlite = SQLiteDatabase.openOrCreateDatabase(file, null);
			}
			return sqlite;
		}
	}


}
