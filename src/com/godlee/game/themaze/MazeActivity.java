package com.godlee.game.themaze;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
	

public class MazeActivity extends Activity{
	public static boolean IS_DEBUG = true;
	public static final int W_DOP = 180;
	public static final int H_DOP = 320;
	public static int scWidth, scHeight;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		scWidth = dm.widthPixels;
		scHeight = dm.heightPixels;
		
	}
	
	
	public static int dopXToPx(int dopX) {
		float dopXf = (float) dopX;
		int pxX = (int) (dopXf / W_DOP * scWidth + 0.5f);
		return pxX;

	}
	public static int dopYToPx(int dopY) {
		float dopYf = (float) dopY;
		int pxY = (int) (dopYf / H_DOP * scHeight + 0.5f);
		return pxY;

	}

	public static int pxXToDop(int pxX) {
		return (int) ((float) pxX / W_DOP + 0.5f);
	}
	public static int pxYToDop(int pxY) {
		return (int) ((float) pxY / H_DOP + 0.5f);
	}



	@Override
	protected void onDestroy() {
		ControlManage.CMThreadFlag = false;
		ControlManage.releaseAll();
		System.exit(0);
		super.onDestroy();
	}
	
	
}