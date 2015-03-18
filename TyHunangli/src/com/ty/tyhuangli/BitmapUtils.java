package com.ty.tyhuangli;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.view.View;

public class BitmapUtils {
	/**
	 * 获取View截图
	 * 
	 * @param view
	 * @return 截图结果Bitmap
	 * */
	public static Bitmap getBitmapByView(View view) {
		view.setDrawingCacheEnabled(true);
		view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
		view.buildDrawingCache();
		return view.getDrawingCache();
	}

	/**
	 * Bitmap转换成字节
	 * 
	 * @param view
	 * @return byte[]
	 * */
	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * 字节转换成Bitmap
	 * 
	 * @param view
	 * @return Bitmap
	 * */
	public static Bitmap Bytes2Bimap(byte[] b) {
		if (b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		} else {
			return null;
		}
	}

	/**
	 * 保存为Bitmap
	 * 
	 * @param bitName
	 *            位图名称
	 * @param bitmap
	 *            待保存位图
	 * @return 返回位图保存后的URI
	 * */
	public static Uri saveMyBitmap(String bitName, Bitmap bitmap) {
		File photo = new File(Environment.getExternalStorageDirectory(),
				bitName);

		FileOutputStream fOut = null;
		try {
			photo.createNewFile();
			fOut = new FileOutputStream(photo);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Uri.fromFile(photo);
	}
}
