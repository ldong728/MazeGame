package com.godlee.game.themaze;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class TestView extends SurfaceView implements SurfaceHolder.Callback{
	private boolean threadFlag;
	private SurfaceHolder mHolder;
	private Bitmap bmp1,bmp2,bmp3,bmp4;
	private Matrix matrix;

	public TestView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mHolder = getHolder();
		mHolder.addCallback(this);
		bmp1 = BitmapSupply.getBitmap(this, R.drawable.w, 50, 50);
		bmp2 = BitmapSupply.getBitmap(this, R.drawable.n, 50, 50);
		bmp3 = BitmapSupply.getBitmap(this, R.drawable.e, 50, 50);
		bmp4 = BitmapSupply.getBitmap(this, R.drawable.s, 50, 50);
		matrix = new Matrix();
//		mDrawer = new MazeDrawer(this, new MazeSeed());
	}

	public TestView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TestView(Context context) {
		this(context, null);
	}
	
	private void setTouchListener(){
	}
	
	

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		threadFlag = true;
		new Thread(new DrawThread()).start();
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		threadFlag= false;
		
	}
	
	private class DrawThread implements Runnable{
		private Canvas mCanvas;
		private Paint mPaint;
		public DrawThread(){
			mPaint = new Paint();
		}
		
		@Override
		public void run() {
			float f = 1f;
			
			while(threadFlag){
				try{
					mCanvas = mHolder.lockCanvas();
					mCanvas.drawColor(Color.WHITE);
					matrix.setTranslate(-50, -50);
					matrix.preScale(f, f, MazeActivity.scWidth/2+50, MazeActivity.scHeight/2+50);
					
					mCanvas.drawBitmap(bmp1, matrix, mPaint);
					mCanvas.drawBitmap(bmp2, matrix, mPaint);
					mCanvas.drawBitmap(bmp3, matrix, mPaint);
					mCanvas.drawBitmap(bmp4, matrix, mPaint);
					f-=0.01;
					Thread.sleep(200);
					
				}catch (Exception e) {
					D.e("»æÖÆ³ö´í");
				}finally{
					if(mCanvas!=null)mHolder.unlockCanvasAndPost(mCanvas);
					
				}
				
			}
			
		}
		
		
	}

}
