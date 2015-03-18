package com.ty.tyhuangli;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

/**
 * 黄历显示功能，可选择查看日期，分享黄历信息
 * 
 * @author wyr
 * @version 1.0
 * */
public class HuangliActivity extends Activity implements
		DatePickerDialog.OnDateSetListener, OnClickListener, OnTouchListener {
	public static final String WEIBO_SHARE_BITMAP = "shareBitmap";
	public static final String WEIBO_SHARE_CONTENT = "shareContent";
	private static final String TAG = "HuangLiActivity";
	private static final String TRIANGLE = "▼";
	private static String[] sNongliDay1;
	private static String[] sNongliDay2;
	private static String[] sNongliMonth1;
	private static String[] sNongliMonth2;
	private Button mBackButton;
	private Calendar mCalendar;
	private Calendar mCalendar2;
	private TextView mChengTV;
	private TextView mChengTV2;
	private TextView mChongTV;
	private TextView mChongTV2;
	private HuangLiDBHelper mDBHelper;
	private Button mDateButton;
	private TextView mDateDay;
	private TextView mDateDay2;
	private final SimpleDateFormat mDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd 00:00:00");
	private final SimpleDateFormat mDateFormatWithDay = new SimpleDateFormat(
			"yyyy年MM月dd日");
	private final SimpleDateFormat mDateFormatWithDay2 = new SimpleDateFormat(
			"dd");
	private final SimpleDateFormat mDateFormatWithDayOfWeek = new SimpleDateFormat(
			"yyyy-MM-dd EEE");;
	private final SimpleDateFormat mDateFormatWithMonth = new SimpleDateFormat(
			"MM月");
	private final SimpleDateFormat mDateFormatWithWeek = new SimpleDateFormat(
			"EEE");
	private final SimpleDateFormat mDateFormatWithYear = new SimpleDateFormat(
			"yyyy");
	private TextView mDateMonthTV;
	private TextView mDateMonthTV2;
	private TextView mDateWeekTV;
	private TextView mDateWeekTV2;
	private TextView mDateYearTV;
	private TextView mDateYearTV2;
	private boolean mHaveData = true;
	private TextView mHuangLiDateDayTV;
	private TextView mHuangLiDateDayTV2;
	private TextView mHuangLiDateTV;
	private TextView mHuangLiDateTV2;
	private boolean mIsFirstPage = true;
	private TextView mJiTV;
	private TextView mJiTV2;
	private TextView mJieqiTV;
	private TextView mJieqiTV2;
	private TextView mNongLiDateTV;
	private TextView mNongLiDateTV2;
	private TextView mShaTV;
	private TextView mShaTV2;
	private Button mShareButton;
	private String mShareTextString = "";
	private TextView mTaishenTV;
	private TextView mTaishenTV2;
	private ViewFlipper mViewFlipper;
	private TextView mYiTV;
	private TextView mYiTV2;
	private TextView mZhengchongTV;
	private TextView mZhengchongTV2;
	private TextView mDateDayTV;
	private TextView mDateDayTV2;
	// 滑动View
	private MyScrollView mScrollView;
	private GestureDetector gestureDetector;
	// 主屏菜单
	private DatePickerDialog datePickerDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_huangli);
		initView();

		this.mCalendar = Calendar.getInstance();
		this.mCalendar2 = Calendar.getInstance();

		this.mDBHelper = new HuangLiDBHelper(this);

		gestureDetector = new GestureDetector(new MyGestureListener());
		// 日期选择器
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int monthOfYear = c.get(Calendar.MONTH);
		int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
		datePickerDialog = new DatePickerDialog(this, this, year, monthOfYear,
				dayOfMonth);
		// 监听器
		this.mDateButton.setOnClickListener(this);
		this.mShareButton.setOnClickListener(this);
		this.mBackButton.setOnClickListener(this);
		mScrollView.setOnTouchListener(this);
		mScrollView.setGestureDetector(gestureDetector);

		String str = this.mDateFormat.format(this.mCalendar.getTime());
		HuangLiItem huangLiItem = this.mDBHelper.getHuangLiData(str);
		setTexts(huangLiItem);
		setTexts2(huangLiItem);
	}

	protected void onDestroy() {
		this.mDBHelper.huangliDBManager.closeDatabase();
		super.onDestroy();
	}

	protected void onResume() {
		this.mShareButton.setEnabled(true);
		super.onResume();
	}

	protected void onStart() {
		this.mShareButton.setEnabled(true);
		super.onStart();
	}

	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return gestureDetector.onTouchEvent(event);
	}

	/**
	 * 初始化相关的View变量
	 * 
	 * @return
	 * */
	private void initView() {
		// TODO Auto-generated method stub
		/** 第一屏 */
		/** 头部 -公历日 */
		mDateYearTV = (TextView) findViewById(R.id.tv_date_year);
		mDateMonthTV = (TextView) findViewById(R.id.tv_date_month);
		mDateWeekTV = (TextView) findViewById(R.id.tv_date_week);
		mDateDayTV = (TextView) findViewById(R.id.tv_date_day);
		/** 黄历日 */
		mHuangLiDateTV = (TextView) findViewById(R.id.tv_huangli_date_year_month);
		mHuangLiDateDayTV = (TextView) findViewById(R.id.tv_huangli_date_day);
		mDateDay = (TextView) findViewById(R.id.tv_huangli_date_year_month);
		/** 农历日 */
		mNongLiDateTV = (TextView) findViewById(R.id.tv_nongli_date);
		mYiTV = (TextView) findViewById(R.id.tv_yi);
		mJiTV = (TextView) findViewById(R.id.tv_ji);
		mChongTV = (TextView) findViewById(R.id.tv_chong);
		mShaTV = (TextView) findViewById(R.id.tv_sha);
		mChengTV = (TextView) findViewById(R.id.tv_cheng);
		/** 左右->胎神and正冲 */
		mTaishenTV = (TextView) findViewById(R.id.tv_taishen);
		mZhengchongTV = (TextView) findViewById(R.id.tv_zhengchong);
		mJieqiTV = (TextView) findViewById(R.id.tv_jieqi);
		/** 第二屏 */
		/** 头部 -公历日 */
		mDateYearTV2 = (TextView) findViewById(R.id.tv_date_year2);
		mDateMonthTV2 = (TextView) findViewById(R.id.tv_date_month2);
		mDateWeekTV2 = (TextView) findViewById(R.id.tv_date_week2);
		mDateDayTV2 = (TextView) findViewById(R.id.tv_date_day2);
		/** 黄历日 */
		mHuangLiDateTV2 = (TextView) findViewById(R.id.tv_huangli_date_year_month2);
		mHuangLiDateDayTV2 = (TextView) findViewById(R.id.tv_huangli_date_day2);
		mDateDay2 = (TextView) findViewById(R.id.tv_huangli_date_year_month2);
		/** 农历日 */
		mNongLiDateTV2 = (TextView) findViewById(R.id.tv_nongli_date2);
		mYiTV2 = (TextView) findViewById(R.id.tv_yi2);
		mJiTV2 = (TextView) findViewById(R.id.tv_ji2);
		mChongTV2 = (TextView) findViewById(R.id.tv_chong2);
		mShaTV2 = (TextView) findViewById(R.id.tv_sha2);
		mChengTV2 = (TextView) findViewById(R.id.tv_cheng2);
		/** 左右->胎神and正冲 */
		mTaishenTV2 = (TextView) findViewById(R.id.tv_taishen2);
		mZhengchongTV2 = (TextView) findViewById(R.id.tv_zhengchong2);
		mJieqiTV2 = (TextView) findViewById(R.id.tv_jieqi2);

		/** 主屏 */
		mDateButton = (Button) findViewById(R.id.huangli_date_bt);
		mShareButton = (Button) findViewById(R.id.ShareButton);
		mBackButton = (Button) findViewById(R.id.huangli_back);// 点击返回今日黄历界面
		mScrollView = (MyScrollView) findViewById(R.id.ScrollView01);
		this.mViewFlipper = (ViewFlipper) findViewById(R.id.ViewFlipper01);
	}

	/**
	 * 设置第一屏黄历相关数据
	 * 
	 * @param huangli
	 *            黄历数据对象
	 * @return
	 * */
	private void setTexts(HuangLiItem huangli) {
		this.mDateButton.setText(this.mDateFormatWithDay.format(this.mCalendar
				.getTime()) + TRIANGLE);
		this.mDateMonthTV.setText(this.mDateFormatWithMonth
				.format(this.mCalendar.getTime()));
		this.mDateYearTV.setText(this.mDateFormatWithYear.format(this.mCalendar
				.getTime()));
		this.mDateWeekTV.setText(this.mDateFormatWithWeek.format(this.mCalendar
				.getTime()));
		this.mDateDay.setText(this.mDateFormatWithDay2.format(this.mCalendar
				.getTime()));
		this.mDateDayTV.setText(mDateFormatWithDay2.format(this.mCalendar
				.getTime()));
		this.mHuangLiDateDayTV.setText(huangli.mHuangLiDay);

		this.mHuangLiDateTV.setText(huangli.mHuangLiYear
				+ huangli.mHuangLiMonth);

		this.mNongLiDateTV.setText(getNongli(huangli));

		this.mYiTV.setText(huangli.mYi);

		this.mJiTV.setText(huangli.mJi);

		this.mChongTV.setText(huangli.mChong);

		this.mShaTV.setText(huangli.mSha);

		this.mChengTV.setText(huangli.mCheng);

		this.mZhengchongTV.setText(huangli.mZhengchong);

		this.mTaishenTV.setText(huangli.mTaishen);

		this.mJieqiTV.setText(huangli.mJieqi);

		/** 构造黄历分享的短句 */
		StringBuilder sb = new StringBuilder();
		sb.append(
				this.mDateFormatWithDayOfWeek.format(this.mCalendar.getTime()))
				.append("\n");
		sb.append(getNongli(huangli)).append("\n");
		sb.append(huangli.mHuangLiYear).append(" ");
		sb.append(huangli.mHuangLiMonth).append(" ");
		sb.append(huangli.mHuangLiDay).append("\n");
		sb.append(getString(R.string.huangli_yi)).append(huangli.mYi)
				.append("\n");
		sb.append(getString(R.string.huangli_ji)).append(huangli.mJi)
				.append("\n");
		sb.append(getString(R.string.huangli_chong)).append(huangli.mChong)
				.append("\n");
		sb.append(getString(R.string.huangli_sha)).append(huangli.mSha)
				.append("\n");
		sb.append(getString(R.string.huangli_cheng)).append(huangli.mCheng)
				.append("\n");
		sb.append(getString(R.string.huangli_zhengchong)).append("：")
				.append(huangli.mZhengchong).append("\n");
		sb.append(getString(R.string.huangli_taishen)).append("：")
				.append(huangli.mTaishen).append("\n");
		this.mShareTextString = sb.toString();
	}

	/**
	 * 设置第二屏黄历相关数据
	 * 
	 * @param huangli
	 *            黄历数据对象
	 * @return
	 * */
	private void setTexts2(HuangLiItem huangli) {
		this.mDateButton.setText(this.mDateFormatWithDay.format(this.mCalendar2
				.getTime()) + TRIANGLE);
		this.mDateMonthTV2.setText(this.mDateFormatWithMonth
				.format(this.mCalendar.getTime()));
		this.mDateYearTV2.setText(this.mDateFormatWithYear
				.format(this.mCalendar2.getTime()));
		this.mDateWeekTV2.setText(this.mDateFormatWithWeek
				.format(this.mCalendar2.getTime()));
		this.mDateDay2.setText(this.mDateFormatWithDay2.format(this.mCalendar2
				.getTime()));
		this.mDateDayTV2.setText(mDateFormatWithDay2.format(this.mCalendar2
				.getTime()));
		this.mHuangLiDateDayTV2.setText(huangli.mHuangLiDay);

		this.mHuangLiDateTV2.setText(huangli.mHuangLiYear
				+ huangli.mHuangLiMonth);

		this.mNongLiDateTV2.setText(getNongli(huangli));

		this.mYiTV2.setText(huangli.mYi);

		this.mJiTV2.setText(huangli.mJi);

		this.mChongTV2.setText(huangli.mChong);

		this.mShaTV2.setText(huangli.mSha);

		this.mChengTV2.setText(huangli.mCheng);

		this.mZhengchongTV2.setText(huangli.mZhengchong);

		this.mTaishenTV2.setText(huangli.mTaishen);

		this.mJieqiTV2.setText(huangli.mJieqi);

		/** 构造黄历分享的短句 */
		StringBuilder sb = new StringBuilder();
		sb.append(
				this.mDateFormatWithDayOfWeek.format(this.mCalendar2.getTime()))
				.append("\n");
		sb.append(getNongli(huangli)).append("\n");
		sb.append(huangli.mHuangLiYear).append(" ");
		sb.append(huangli.mHuangLiMonth).append(" ");
		sb.append(huangli.mHuangLiDay).append("\n");
		sb.append(getString(R.string.huangli_yi)).append(huangli.mYi)
				.append("\n");
		sb.append(getString(R.string.huangli_ji)).append(huangli.mJi)
				.append("\n");
		sb.append(getString(R.string.huangli_chong)).append(huangli.mChong)
				.append("\n");
		sb.append(getString(R.string.huangli_sha)).append(huangli.mSha)
				.append("\n");
		sb.append(getString(R.string.huangli_cheng)).append(huangli.mCheng)
				.append("\n");
		sb.append(getString(R.string.huangli_zhengchong)).append("：")
				.append(huangli.mZhengchong).append("\n");
		sb.append(getString(R.string.huangli_taishen)).append("：")
				.append(huangli.mTaishen).append("\n");
		this.mShareTextString = sb.toString();
	}

	/**
	 * 获取农历相关信息
	 * 
	 * @param huangli
	 *            黄历数据对象
	 * @return 返回格式化后的农历
	 * */
	private String getNongli(HuangLiItem huangLiItem) {
		if (sNongliDay1 == null) {
			sNongliDay1 = getResources().getStringArray(
					R.array.huangli_nongli_day);
			sNongliDay2 = getResources().getStringArray(
					R.array.huangli_nongli_day2);
			sNongliMonth1 = getResources().getStringArray(
					R.array.huangli_nongli_month);
			sNongliMonth2 = getResources().getStringArray(
					R.array.huangli_nongli_month2);
		}
		String str1 = huangLiItem.mNongLiDate;
		for (int i = 0; i < sNongliMonth1.length; ++i) {
			String str2 = sNongliMonth1[i];
			String str3 = sNongliMonth2[i];
			str1 = str1.replace(str2, str3);
		}
		for (int i = 0; i < sNongliDay1.length; ++i) {
			String str2 = sNongliDay1[i];
			String str3 = sNongliDay2[i];
			str1 = str1.replace(str2, str3);
		}
		return str1;
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, monthOfYear, dayOfMonth);
		mDateButton.setText(this.mDateFormatWithDay.format(calendar.getTime())
				+ TRIANGLE);
		HuangLiItem huangLiItem = this.mDBHelper.getHuangLiData(mDateFormat
				.format(calendar.getTime()));
		mCalendar.set(year, monthOfYear, dayOfMonth);
		mCalendar2.set(year, monthOfYear, dayOfMonth);
		// 开始翻页
		if (mIsFirstPage) {
			setTexts2(huangLiItem);
			mIsFirstPage = false;
			mViewFlipper.setInAnimation(getApplicationContext(),
					R.anim.push_left_in);
			mViewFlipper.setOutAnimation(getApplicationContext(),
					R.anim.push_left_out);
			mViewFlipper.showNext();
		} else {
			setTexts(huangLiItem);
			mIsFirstPage = true;
			mViewFlipper.setInAnimation(getApplicationContext(),
					R.anim.push_left_in);
			mViewFlipper.setOutAnimation(getApplicationContext(),
					R.anim.push_left_out);
			mViewFlipper.showPrevious();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ShareButton:
			Uri uri = BitmapUtils.saveMyBitmap("huangli",
					BitmapUtils.getBitmapByView(mScrollView));
			Intent intent = new Intent();
			intent.setData(uri);
			intent.putExtra(WEIBO_SHARE_CONTENT, mShareTextString);
			setResult(RESULT_OK, intent);
			finish();
			break;
		case R.id.huangli_back:
			toToday();
			break;
		case R.id.huangli_date_bt:
			datePickerDialog.show();
			break;
		}
	}

	/**
	 * 跳转至今天的黄历信息
	 * 
	 * @return
	 * */
	private void toToday() {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
		if (mCalendar.equals(calendar)) {
			Toast.makeText(HuangliActivity.this, "当前已是今天，无需跳转!",
					Toast.LENGTH_SHORT).show();
			return;
		}
		mDateButton.setText(mDateFormatWithDay.format(calendar.getTime())
				+ TRIANGLE);
		HuangLiItem huangLiItem = mDBHelper.getHuangLiData(mDateFormat
				.format(calendar.getTime()));
		mCalendar.setTime(new Date());
		mCalendar2.setTime(new Date());
		// 开始翻页
		if (mIsFirstPage) {
			setTexts2(huangLiItem);
			mIsFirstPage = false;
			mViewFlipper.setInAnimation(getApplicationContext(),
					R.anim.push_left_in);
			mViewFlipper.setOutAnimation(getApplicationContext(),
					R.anim.push_left_out);
			mViewFlipper.showNext();
		} else {
			setTexts(huangLiItem);
			mIsFirstPage = true;
			mViewFlipper.setInAnimation(getApplicationContext(),
					R.anim.push_left_in);
			mViewFlipper.setOutAnimation(getApplicationContext(),
					R.anim.push_left_out);
			mViewFlipper.showPrevious();
		}
	}

	public class MyGestureListener extends SimpleOnGestureListener {

		@Override
		public boolean onDown(MotionEvent e) {
			// TODO Auto-generated method stub
			Log.d(TAG + "QueryViewFlipper", "====>  do onDown...");
			return false;
		}

		@Override
		public void onShowPress(MotionEvent e) {
			// TODO Auto-generated method stub
			Log.d(TAG + "QueryViewFlipper", "====> do onShowPress...");
			super.onShowPress(e);
		}

		@Override
		public void onLongPress(MotionEvent e) {
			// TODO Auto-generated method stub
			Log.d(TAG + "QueryViewFlipper", "----> do onLongPress...");
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			// TODO Auto-generated method stub
			Log.d(TAG + "QueryViewFlipper", "====> do onSingleTapConfirmed...");
			return false;
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			// TODO Auto-generated method stub
			Log.d(TAG + "QueryViewFlipper", "====> do onSingleTapUp...");
			return false;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// TODO Auto-generated method stub
			Log.d(TAG + "QueryViewFlipper", "====> do onFling...");
			if (e1.getX() - e2.getX() > 100 && Math.abs(velocityX) > 50) {
				// 向左
				if (mIsFirstPage) {
					mCalendar2.add(Calendar.DATE, 1);
					mCalendar.add(Calendar.DATE, 1);
					String str2 = mDateFormat.format(mCalendar2.getTime());
					Log.e("next date:", str2);
					HuangLiItem huangLiItem = mDBHelper.getHuangLiData(str2);
					setTexts2(huangLiItem);
					mIsFirstPage = false;
					mViewFlipper.setInAnimation(getApplicationContext(),
							R.anim.push_left_in);
					mViewFlipper.setOutAnimation(getApplicationContext(),
							R.anim.push_left_out);
					mViewFlipper.showNext();
				} else {
					mCalendar.add(Calendar.DATE, 1);
					mCalendar2.add(Calendar.DATE, 1);
					HuangLiItem huangLiItem = mDBHelper
							.getHuangLiData(mDateFormat.format(mCalendar
									.getTime()));
					setTexts(huangLiItem);
					mIsFirstPage = true;
					mViewFlipper.setInAnimation(getApplicationContext(),
							R.anim.push_left_in);
					mViewFlipper.setOutAnimation(getApplicationContext(),
							R.anim.push_left_out);
					mViewFlipper.showPrevious();
				}
			} else if (e2.getX() - e1.getX() > 100 && Math.abs(velocityX) > 50) {
				// 向右
				if (mIsFirstPage) {
					mCalendar2.add(Calendar.DATE, -1);
					mCalendar.add(Calendar.DATE, -1);
					String str2 = mDateFormat.format(mCalendar2.getTime());
					Log.e("向右 next date:", str2);
					HuangLiItem huangLiItem = mDBHelper.getHuangLiData(str2);
					setTexts2(huangLiItem);
					mIsFirstPage = false;
					mViewFlipper.setInAnimation(getApplicationContext(),
							R.anim.push_right_in);
					mViewFlipper.setOutAnimation(getApplicationContext(),
							R.anim.push_right_out);
					mViewFlipper.showNext();
				} else {
					mCalendar.add(Calendar.DATE, -1);
					mCalendar2.add(Calendar.DATE, -1);
					HuangLiItem huangLiItem = mDBHelper
							.getHuangLiData(mDateFormat.format(mCalendar
									.getTime()));
					setTexts(huangLiItem);
					mIsFirstPage = true;
					mViewFlipper.setInAnimation(getApplicationContext(),
							R.anim.push_right_in);
					mViewFlipper.setOutAnimation(getApplicationContext(),
							R.anim.push_right_out);
					mViewFlipper.showPrevious();
				}
			}
			return true;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			// TODO Auto-generated method stub
			Log.d(TAG + "QueryViewFlipper", "====> do onScroll...");
			return super.onScroll(e1, e2, distanceX, distanceY);
		}
	}
}
