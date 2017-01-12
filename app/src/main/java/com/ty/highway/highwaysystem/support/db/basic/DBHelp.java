package com.ty.highway.highwaysystem.support.db.basic;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.ty.highway.highwaysystem.base.Constants;
import com.ty.highway.highwaysystem.support.utils.FileUtils;
import com.ty.highway.highwaysystem.support.utils.exception.ExceptionUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @className DBHelp
 * @author fuweiwei
 * @date 2014-3-7 AM 09:49:44
 * 数据库操作类，与DBHelp不同，该数据库放在根目录下
 */
public class DBHelp{
	public SQLiteDatabase sqlite;
	private String dbPath = Environment.getExternalStorageDirectory().getPath()+ "/.HighWay/db/";
	private String dbFile = dbPath + "highway.db";
	private File path = new File(dbPath);
	private File file = new File(dbFile);
	private AtomicInteger mOpenCounter = new AtomicInteger();
	private Context mContext;

	public DBHelp(Context context) {

		this.mContext = context;

		if (!path.exists()) {
			path.mkdirs();
		}

		if (!file.exists()) {
			try {
				InputStream inputStream = mContext.getAssets().open("highway.db");
				if(inputStream != null) {
					if(FileUtils.writeFile(file, inputStream)) {
						sqlite = SQLiteDatabase.openOrCreateDatabase(file, null);
						sqlite.setVersion(Constants.NEW_DB_VERSION);
					}
				}
				file.createNewFile();
				sqlite = SQLiteDatabase.openOrCreateDatabase(file, null);
			} catch (IOException e) {
				new ExceptionUtils().doExecInfo(e.toString(), mContext);
			}
		} else {
			sqlite = SQLiteDatabase.openOrCreateDatabase(file, null);
		}

		//由于数据库是在存在手动导入的情况，每次启动系统是都对数据库强制更新，防止数据库异常。   whl add
		int version = sqlite.getVersion();   //int version = 1; whl add
		if (version < Constants.NEW_DB_VERSION) {
			if (onUpgrade(sqlite, version, Constants.NEW_DB_VERSION)) {
				sqlite.setVersion(Constants.NEW_DB_VERSION);
			}
		}
	}



	/**
	 * 数据库的更新
	 * 数据库根据版本号进行更新。如从版本2更新到版本3，只进行对版本3的更新；如果从版本1更新到版本3则，则更新版本2、版本3。
	 * @param db 数据库
	 * @param oldVersion 数据库当前版本
	 * @param newVersion 数据库最新版本
	 * @Desc  以后每次更新版本的时候，都要注明每个版本新增修改删除了哪些。如：
	 *
	 *        v1.
	 *        v2.ResultsFile表新增CheckTaskCode字段
	 *        v3.CheckTaskInfo表新增CheckRemarks字段
	 *        v4.MetroCheckObjectDetail表新增ExistRingNum字段
	 *           z_ConstructionCheckDic(4条)
	 *           DamageInputOpreation(1条)
	 *           CheckTypeDamageRel(若干条)
	 *           DAMAGEINFOFIELDREL(若干条)
	 * 			 DAMAGETYPE(若干条)
	 * 			 DAMGELEVEL(若干条)
	 *       注意：目前在apk退版本时，数据库暂时无法支持退版本功能
	 *
	 * */
	public boolean onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (newVersion > oldVersion) {
			/*//删除之前数据库
			if (file.exists()) {
				file.delete();
				try {
					InputStream inputStream = mContext.getAssets().open("highway.db");
					if(inputStream != null) {
						if(FileUtils.writeFile(file, inputStream)) {
							sqlite = SQLiteDatabase.openOrCreateDatabase(file, null);
						}
					}
					file.createNewFile();
					sqlite = SQLiteDatabase.openOrCreateDatabase(file, null);
				} catch (IOException e) {
					new ExceptionUtils().doExecInfo(e.toString(), mContext);
				}
			}*/
			DBVersionHelper helper = new DBVersionHelper(sqlite,oldVersion,newVersion);
			helper.update();
		}
		return true;
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
		synchronized (DBHelp.class) {
			if(mOpenCounter.incrementAndGet() == 1) {
				// Opening new database
				sqlite = SQLiteDatabase.openOrCreateDatabase(file, null);
			}
			return sqlite;
		}
	}


}
