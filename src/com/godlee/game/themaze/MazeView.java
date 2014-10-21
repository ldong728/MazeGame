package com.godlee.game.themaze;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MazeView extends SurfaceView implements SurfaceHolder.Callback {
	public static final long PAUSE_TIME = 5;
	public static MazeDrawer mMazeDrawer;
	public static MazeTile[][] baseMaze;
	public int Width, height;
	private boolean baseMazeReady;
	private int tempTileSize = 5;
	private boolean threadFlag;
	private SurfaceHolder mHolder;
	private Handler mHandler = MainActivity.mHandler;

	public Matrix mMatrix;
	// public Point screenPinMaze;
	// private Bitmap MazeMap;

	private Runnable getBaseMaze = new Runnable() {
		public void run() {
//			baseMaze = MazeCreator.getNewMaze(new MazeSeed(9,9,new int[]{
//			0,0,0,0,0,0,0,0,1,2,1,2,1,2,1,2,1,2,1,2,1,2,2,1,0,0,0,0,0,0,0,0		
//			}));
			baseMaze = MazeCreator.getNewMaze(new MazeSeed(15,15));
			baseMazeReady = true;
		}
	};

	public MazeView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mMazeDrawer = new MazeDrawer(this, new MazeSeed());
		ControlManage.initActor(this);
		if (!baseMazeReady)
			mHandler.post(getBaseMaze);
		mHolder = getHolder();
		mHolder.addCallback(this);
		setTouchListener();

		
	}

	public MazeView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MazeView(Context context) {
		this(context, null);
	}

	private void setTouchListener() {
		ControlManage.setTouchListenter(this);
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
		threadFlag = false;

	}

	private class DrawThread implements Runnable {
		private Canvas mCanvas;
		private Paint mPaint;

		public DrawThread() {
			mPaint = new Paint();
			// tempB = mMazeDrawer.getBuffer();
		}

		@Override
		public void run() {
			while (threadFlag) {
				try {
					mCanvas = mHolder.lockCanvas();
					mCanvas.drawColor(Color.WHITE);
					mMazeDrawer.drawMazePart(mCanvas, baseMaze,
							ControlManage.lastScrPInMaze, MazeDrawer.TILE_SIZE,
							ControlManage.mazeZoom);
					ControlManage.actor.drawSelf(mCanvas,
							ControlManage.lastScrPInMaze,
							ControlManage.mazeZoom);
					Thread.sleep(PAUSE_TIME);

				} catch (Exception e) {
					D.e("»æÖÆ³ö´í");
				} finally {
					if (mCanvas != null)
						mHolder.unlockCanvasAndPost(mCanvas);
				}
			}
		}
	}
	public static void moveActorInMaze(MotionEvent event, Sprite sp){
		
		
		
	}
}
