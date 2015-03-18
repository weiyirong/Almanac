package com.ty.tyhuangli;

import android.content.Context;

public class HuangLiItem {
	public String mCheng;
	public String mChong;
	public String mHuangLiDay;
	public String mHuangLiMonth;
	public String mHuangLiYear;
	public String mJi;
	public String mJieqi;
	public String mNongLiDate;
	public String mSha;
	public String mTaishen;
	public String mYi;
	public String mZhengchong;

	public HuangLiItem(Context context) {
		this.mHuangLiYear = context.getResources().getString(
				R.string.huangli_nodate2);
		this.mHuangLiMonth = "";
		this.mHuangLiDay = "";
		this.mNongLiDate = context.getResources().getString(
				R.string.huangli_nodate2);
		this.mYi = context.getResources().getString(R.string.huangli_nodate2);
		this.mJi = context.getResources().getString(R.string.huangli_nodate2);
		this.mChong = context.getResources()
				.getString(R.string.huangli_nodate2);
		this.mSha = context.getResources().getString(R.string.huangli_nodate2);
		this.mCheng = context.getResources()
				.getString(R.string.huangli_nodate2);
		this.mZhengchong = context.getResources().getString(
				R.string.huangli_nodate2);
		this.mTaishen = context.getResources().getString(
				R.string.huangli_nodate2);
		this.mJieqi = "";
	}
}