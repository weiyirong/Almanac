package com.ty.tyhuangli;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * 导入外部城市数据库
 * 
 * @author wyr
 * @since 2013.4.27
 * */
public class HuangliDBManager {
	public SQLiteDatabase database;
	Context context;
	public static final String DB_PATH = "/data/data/com.ty.tyhuangli/databases/";
	public static final String DB_NAME = "huangli.db";

	public HuangliDBManager(Context context) {
		this.context = context;
	}

	public void openDatabase() {
		if (!new File(DB_PATH + DB_NAME).exists()) {
			File f = new File(DB_PATH);
			if (!f.exists()) {
				f.mkdir();
			}
			try {
				InputStream is = context.getAssets().open(DB_NAME);
				OutputStream os = new FileOutputStream(DB_PATH + DB_NAME);
				byte[] buffer = new byte[1024];
				int length;
				while ((length = is.read(buffer)) > 0) {
					os.write(buffer, 0, length);
				}
				os.flush();
				os.close();
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			database = context.openOrCreateDatabase(DB_NAME, 0, null);
		} else {
			database = context.openOrCreateDatabase(DB_NAME, 0, null);
		}
	}

	public void closeDatabase() {
		if (database != null) {
			this.database.close();
			this.database = null;
		}
	}
}