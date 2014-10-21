package com.godlee.game.themaze;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
/*
 * 位图供应类，包含一个位图数组，游戏中所有动画均有此位图数组提供
 */
public class BitmapSupply {
	private static boolean animationLoaded;
	private static View view;
//	static Bitmap[] bitmaps;
	
	
	public BitmapSupply(View view) {
	}
	/**
	 * 由资源数组返回一个位图数组
	 * @param view 调用所需的View
	 * @param width 位图的宽度
	 * @param height 位图的高度
	 * @param resId 资源数组
	 * @return 尺寸相同，密度相同的 位图数组
	 */
	public static Bitmap[] loadBitmap(View view,int width, int height,int[] resId){ //载入图片至静态类，图片比例不变,大小有首选项决定
		Bitmap[] bitmaps = new Bitmap[resId.length];
		for(int i=0; i<resId.length; i++){
			Bitmap b = getBitmap(view,resId[i]);
			bitmaps[i] = BitmapSupply.changeSize(b, width,height);
			
		}
		return bitmaps;

	}
	//以下方法返回一个位图数组，尺寸一致，由参数决定
	public static Bitmap[] loadBitmaps(View view,int[] resId,int width,int height){ 
		Bitmap[] b = new Bitmap[resId.length];
		for(int i=0 ; i<resId.length; i++){
			b[i]= getBitmap(view, resId[i], width, height);
		}
		
		
		
		return b;
	}
	public static boolean isLoaded(){
		return animationLoaded;
	}

	public static Bitmap getBitmap(View view, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		opt.inScaled = false;
//		opt.
		return BitmapFactory.decodeResource(view.getResources(), resId, opt);
	}

	public Bitmap getBitmap(int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		opt.inScaled = false;

		return BitmapFactory.decodeResource(view.getResources(), resId, opt);

	}


	public static Bitmap changeSize(Bitmap sourse, int width, int height) {
		return Bitmap.createScaledBitmap(sourse, width, height, false);
	}
	public static Bitmap getBitmap(View view,int resId,int width,int height){
		Bitmap bm = getBitmap(view, resId);
		return changeSize(bm,width,height);
	}
//	public static void destroy(){
//		if(bitmaps!=null){
//			try{
//			for(Bitmap b : bitmaps){
//				b.recycle();
//			}
//			}catch (Exception e) {
//				Log.e("godlee","资源回收失败");
//			}
//			Log.v("godlee","图片资源回收");
//		}
//		animationLoaded = false;
//	}
	

}