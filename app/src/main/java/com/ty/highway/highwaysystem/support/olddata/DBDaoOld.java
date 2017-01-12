/**
 *
 */
package com.ty.highway.highwaysystem.support.olddata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


/**
 * @ClassName: DBDao
 * @Description: TODO(数据库访问父类)
 * @author fuweiwei
 * @date 2015-5-22 上午8:58:31
 *
 */
public class DBDaoOld {protected static DBHelpOld dbHelper;

	protected DBDaoOld(Context context) {
		if (dbHelper == null) {
			synchronized (DBDaoOld.class) {
				if (dbHelper == null) {
					dbHelper = new DBHelpOld(context);
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
