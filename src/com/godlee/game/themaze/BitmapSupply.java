package com.godlee.game.themaze;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
/*
 * λͼ��Ӧ�࣬����һ��λͼ���飬��Ϸ�����ж������д�λͼ�����ṩ
 */
public class BitmapSupply {
	private static boolean animationLoaded;
	private static View view;
//	static Bitmap[] bitmaps;
	
	
	public BitmapSupply(View view) {
	}
	/**
	 * ����Դ���鷵��һ��λͼ����
	 * @param view ���������View
	 * @param width λͼ�Ŀ��
	 * @param height λͼ�ĸ߶�
	 * @param resId ��Դ����
	 * @return �ߴ���ͬ���ܶ���ͬ�� λͼ����
	 */
	public static Bitmap[] loadBitmap(View view,int width, int height,int[] resId){ //����ͼƬ����̬�࣬ͼƬ��������,��С����ѡ�����
		Bitmap[] bitmaps = new Bitmap[resId.length];
		for(int i=0; i<resId.length; i++){
			Bitmap b = getBitmap(view,resId[i]);
			bitmaps[i] = BitmapSupply.changeSize(b, width,height);
			
		}
		return bitmaps;

	}
	//���·�������һ��λͼ���飬�ߴ�һ�£��ɲ�������
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
//				Log.e("godlee","��Դ����ʧ��");
//			}
//			Log.v("godlee","ͼƬ��Դ����");
//		}
//		animationLoaded = false;
//	}
	

}