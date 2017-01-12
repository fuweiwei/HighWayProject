/**
 *
 */
package com.ty.highway.highwaysystem.support.db.basic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


/**
 * @ClassName: DBDao
 * @Description: TODO(数据库访问父类)
 * @author fuweiwei
 * @date 2015-5-22 上午8:58:31
 *
 */
public class DBDao {
	protected static DBHelp dbHelper;

	protected DBDao(Context context) {
		if (dbHelper == null) {
			synchronized (DBDao.class) {
				if (dbHelper == null) {
					dbHelper = new DBHelp(context);
				}
			}
		}
	}

	/**
	 * 关闭数据库
	 */
	protected void closeDB() {
		if (dbHelper != null) {
			dbHelper.close();
		}
	}

	/**
	 *获取一个数据库
	 */
	protected SQLiteDatabase getDataBase(){
		SQLiteDatabase db = null;
		if (dbHelper != null) {
			db= dbHelper.getDatabase();
		}
		return db;
	}
}
