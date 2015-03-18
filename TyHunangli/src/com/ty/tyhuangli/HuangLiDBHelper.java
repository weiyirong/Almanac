package com.ty.tyhuangli;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class HuangLiDBHelper {
	private static final String COLUMN_CHENG = "Cheng";
	private static final String COLUMN_CHONG = "Chong";
	private static final String COLUMN_DATE = "sDate";
	private static final String COLUMN_DAY = "nDay";
	private static final String COLUMN_JI = "Ji";
	private static final String COLUMN_JIE_QI = "SolarTerm";
	private static final String COLUMN_MONTH = "nMonth";
	private static final String COLUMN_SHA = "Sha";
	private static final String COLUMN_TAI_SHEN = "TaiShen";
	private static final String COLUMN_TODAY = "nToDay";
	private static final String COLUMN_YEAR = "nYear";
	private static final String COLUMN_YI = "Yi";
	private static final String COLUMN_ZHENG_CHONG = "ZhengChong";
	private static final String DATABASE_NAME = "huangli.db";
	private static final String TABLE_NAME = "HL_s";
	private static final String TAG = HuangLiDBHelper.class.getSimpleName();
	private Context mContext;

	HuangliDBManager huangliDBManager;

	public HuangLiDBHelper(Context paramContext) {
		this.mContext = paramContext;
		Context localContext = this.mContext;
		init(localContext);
	}

	private void init(Context localContext) {
		// TODO Auto-generated method stub
		huangliDBManager = new HuangliDBManager(localContext);
		huangliDBManager.openDatabase();
		huangliDBManager.closeDatabase();
	}

	/**
	 * 根据日期查询获取黄历数据
	 * 
	 * @param paramString
	 *            日期
	 * */
	public HuangLiItem getHuangLiData(String paramString) {
		huangliDBManager.openDatabase();
		String sql = "select * from HL_s where sDate='" + paramString + "'";
		HuangLiItem huangliItem = new HuangLiItem(mContext);
		Log.e("getHuangLiData:", huangliItem.mHuangLiMonth);
		Cursor cursor = null;
		try {
			if (huangliDBManager.database != null)
				cursor = huangliDBManager.database.rawQuery(sql, null);
			if ((cursor != null) && (cursor.moveToNext())) {
				huangliItem.mHuangLiYear = cursor.getString(cursor
						.getColumnIndex(COLUMN_YEAR));
				huangliItem.mHuangLiMonth = cursor.getString(cursor
						.getColumnIndex(COLUMN_MONTH));
				huangliItem.mHuangLiDay = cursor.getString(cursor
						.getColumnIndex(COLUMN_DAY));
				huangliItem.mNongLiDate = cursor.getString(cursor
						.getColumnIndex(COLUMN_TODAY));
				huangliItem.mYi = cursor.getString(cursor
						.getColumnIndex(COLUMN_YI));
				huangliItem.mJi = cursor.getString(cursor
						.getColumnIndex(COLUMN_JI));
				huangliItem.mChong = cursor.getString(cursor
						.getColumnIndex(COLUMN_CHONG));
				huangliItem.mSha = cursor.getString(cursor
						.getColumnIndex(COLUMN_SHA));
				huangliItem.mCheng = cursor.getString(cursor
						.getColumnIndex(COLUMN_CHENG));
				huangliItem.mZhengchong = cursor.getString(cursor
						.getColumnIndex(COLUMN_ZHENG_CHONG));
				huangliItem.mTaishen = cursor.getString(cursor
						.getColumnIndex(COLUMN_TAI_SHEN));
				huangliItem.mJieqi = cursor.getString(cursor
						.getColumnIndex(COLUMN_JIE_QI));
			}
		} catch (Exception e) {
			Log.e("Exception:", e.getMessage());
			e.printStackTrace();
		} finally {
			if (cursor != null)
				cursor.close();
			huangliDBManager.closeDatabase();
		}
		return huangliItem;
	}
}
