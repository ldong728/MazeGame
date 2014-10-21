package com.godlee.game.themaze;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class ControlManage {
	public static final float ACTOR_X = MainActivity.dopXToPx(90);
	public static final float ACTOR_Y = MainActivity.dopYToPx(160);
	public static final int SCN_STEP_X = MainActivity.dopXToPx(4);
	public static final int SCN_STEP_Y = MainActivity.dopYToPx(3);
	public static final RectF STATIC_ZONE = new RectF(MainActivity.dopXToPx(45),MainActivity.dopYToPx(80),MainActivity.dopXToPx(135),MainActivity.dopYToPx(240));
	public static final float MIN_STEP =  25;
	public static boolean CMThreadFlag; // �̱߳�ǣ����������߳�����
	public static boolean CMThreadRunning; // �߳����б�ʶ����ȷ��ͬʱֻ��һ���߳�������
	public static boolean isHorizon;
	public static float dPadValue;
	public static float moveValue;
	public static int dPadDirection;
	public static int moveDirection;
	private static boolean inited; // ��ʼ����ʶ
	private static boolean isMazeMoving;
	private static MazeView mView; // ��ͼ�����˳�ʱ��Ҫ����
	public static Point startTouchPoint = new Point(60, 60); // ��һ����ָ������������
	private static float startX, startY; // ��һ����ָ�����������ľ�γֵ
	private static float startX2,startY2; //ͬ�ϣ�����������Ļ�ƶ�
	private static float cPadCenterX, cPadCenterY; // ��Ϊ���ⷽ���ʹ��ʱ������������ĵ�
	private static float lastDistence; // ��������һ��������ָ��ࣨƽ��������Ϊ����ʱ����ʼ�ο�ֵ
	public static float mazeZoom = 1f; // ����ֵ
	public static PointF lastScrPInMaze = new PointF(0, 0); // ��Ļ���Ͻ���Maze�е������
	public static Sprite actor;// ���Թ�������

	public static void initActor(View view) {
		actor = new Sprite(view, 0, 0);
		CMThreadFlag = true;
		if (!CMThreadRunning) {
			Thread cMThread = new Thread() {

				@Override
				public void run() {

					while (CMThreadFlag) {
						if(!isMazeMoving){
							actor.moveOneStep(moveDirection, moveValue);
						}else{
							moveMaze(actor);
						}
						if(dPadValue>20){
//							moveMaze(actor);
						}else{
							
						}
						if (!inited) {
							inited = initScreenPoisition(actor);
						}
						try {
							Thread.sleep(25);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					super.run();
				}

			};
			cMThread.start();
			CMThreadRunning = true;
		}
		// inited = true;
	}

	public static void setStartPoint(MotionEvent event) {
		startX = event.getX();
		startY = event.getY();
		startX2 = event.getX();
		startY2 = event.getY();
//		startTouchPoint = new Point((int) startX, (int) startY);
	}

	public static void setView(MazeView view) {

	}

//	public static Point getStartPoint() {
//		return startTouchPoint;
//	}

	public static void movePointWithFinger(MotionEvent event, PointF movePoint) {
		float dX = (event.getX(0) - startX2) / mazeZoom;
		float dY = (event.getY(0) - startY2) / mazeZoom;
		if (dX > 100 || dY > 100)
			return;
		movePoint.x -= dX;
		movePoint.y -= dY;
		startX2 = event.getX(0);
		startY2 = event.getY(0);


	}

	
	public static void moveActor(MotionEvent event) {
		float dX = (event.getX(0) - startX) / mazeZoom;
		float dY = (event.getY(0) - startY) / mazeZoom;
		if (dX > 100 || dY > 100)
			return;
//		startX = event.getX(0);
//		startY = event.getY(0);
		if (Math.abs(dX)-Math.abs(dY)>SCN_STEP_X) {
			if (dX > 0)
				moveDirection = MazeTile.E;
			else
				moveDirection = MazeTile.W;
			moveValue = Math.abs(dX);
			startX = event.getX(0);
			startY = event.getY(0);
			
		} else if(Math.abs(dY)-Math.abs(dX)>SCN_STEP_Y){
			if (dY > 0)
				moveDirection = MazeTile.S;
			else
				moveDirection = MazeTile.N;
			moveValue = Math.abs(dY);
			startX = event.getX(0);
			startY = event.getY(0);
		} 

	}
	


	/**
	 * ������㴥����ľ��룬ȷ��������������е�λ��
	 * 
	 * @param event
	 *            �����¼�
	 * @return ��������ƽ��
	 */
	private static float getPointDistence(MotionEvent event) {
		float dx = event.getX(1) - event.getX(0);
		float dy = event.getY(1) - event.getY(0);
		return dx * dx + dy * dy;

	}

	public static void getDistence(MotionEvent event) {
		lastDistence = getPointDistence(event);
	}

	public static void zoomMaze(MotionEvent event) {
		float nowDistence = getPointDistence(event);
		float dz = (float) (Math.sqrt(nowDistence) / Math.sqrt(lastDistence));
		float nowzoom = mazeZoom * dz;
		if (nowzoom > 0.5 && nowzoom < 2) {
			mazeZoom = mazeZoom * dz;
			lastDistence = nowDistence;
		}
	}

	public static void setTouchListenter(MazeView view) {

		OnTouchListener sListener = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				v = (MazeView) v;
				int fingerCount = event.getPointerCount();
				int action = event.getAction();
				switch (action & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN: {

					inited = true;
					setStartPoint(event);

//					actor.setActivity(event, mazeZoom);
					actor.isActivity = true;
					initCPadCenter(event.getX(),event.getY());
					break;
				}

				case MotionEvent.ACTION_POINTER_DOWN: {
					getDistence(event);
//					actor.setActivity(event, mazeZoom);
					actor.isActivity = true;

					break;
				}
				case MotionEvent.ACTION_UP: {
					actor.isActivity = false;
					inited = false;
//					mazeMoveFlag = false;
					initCPadValue();
					break;
				}
				case MotionEvent.ACTION_POINTER_UP: {

					break;
				}
				case MotionEvent.ACTION_MOVE: {
					switch (fingerCount) {
					case 1: {
						if (actor.isActivity) {
							    jugMazeMoving(actor);
							if(isMazeMoving){
								
								movePointWithFinger(event, lastScrPInMaze);
								getDpadValue(event);
							}else{
								moveActor(event);
							}

						} else {
							movePointWithFinger(event, lastScrPInMaze);
						}

						setStartPoint(event);
						break;
					}
					case 2: {
						zoomMaze(event);
					}
					}

					break;
				}
				}

				return true;
			}
		};
		view.setOnTouchListener(sListener);

	}

	/**
	 * �ƶ���ͼ���ý�ɫ�������м�
	 * 
	 * @param sp
	 *            �ƶ��Ľ�ɫ����
	 * @return
	 */
	public static boolean initScreenPoisition(Sprite sp) {
		PointF pIns = sp.getPositionInScreen();
		float dx =  ACTOR_X-(MazeDrawer.TILE_SIZE*mazeZoom)/2 - pIns.x;
		float dy =  ACTOR_Y-(MazeDrawer.TILE_SIZE*mazeZoom)/2 - pIns.y;

		if (Math.abs(dx) > SCN_STEP_X) {
			lastScrPInMaze.x -= Math.abs(dx) / dx * SCN_STEP_X;
			lastScrPInMaze.y -= dy / Math.abs(dx) * SCN_STEP_X;
			return false;
		} else if (Math.abs(dy) > SCN_STEP_Y) {
			lastScrPInMaze.y -= Math.abs(dy) / dy * SCN_STEP_Y;
			lastScrPInMaze.x -= dx / Math.abs(dy) * SCN_STEP_Y;
			return false;
		} else {
			lastScrPInMaze.x = sp.xInMaze - ACTOR_X+MazeDrawer.TILE_SIZE/2;
			lastScrPInMaze.y = sp.yInMaze - ACTOR_Y+MazeDrawer.TILE_SIZE/2;
			
			return true;
		}

	}

	private static void initCPadCenter(float x, float y) {

		cPadCenterX = x;
		cPadCenterY = y;
	}

	private static void initCPadValue() {
		
		dPadDirection = 0;
		dPadValue = 0;
		moveValue = 0;
	}

	private static void getDpadValue(MotionEvent event) {
		
		float dx = event.getX(0) - cPadCenterX;
		float dy = event.getY(0) - cPadCenterY;
		if (Math.abs(dx)>Math.abs(dy)) {
			if (dx > 0)
				dPadDirection = MazeTile.E;
			else
				dPadDirection = MazeTile.W;
			dPadValue = Math.abs(dx/MIN_STEP);
			
		} else if(Math.abs(dx)<Math.abs(dy)){
			if (dy > 0)
				dPadDirection = MazeTile.S;
			else
				dPadDirection = MazeTile.N;
			dPadValue = Math.abs(dy/MIN_STEP);
		} 
	}
	


	private static void moveMaze(Sprite sp) {
		float dv = sp.moveOneStep(dPadDirection, dPadValue);
		if (dv == 0){
			return;
		}
		else {
			switch (sp.movingDirection) {
			case MazeTile.E:
				lastScrPInMaze.x += dv;
				break;
			case MazeTile.S:
				lastScrPInMaze.y += dv;
				break;
			case MazeTile.W:
				lastScrPInMaze.x -= dv;
				break;
			case MazeTile.N:
				lastScrPInMaze.y -= dv;
				break;
			}
		}
	}
	
	private static void jugMazeMoving(Sprite sp){
		PointF spp = sp.getPositionInScreen();
		float x = spp.x+Sprite.SIZE*mazeZoom/2;
		float y = spp.y+Sprite.SIZE*mazeZoom/2;
		if(STATIC_ZONE.contains(x, y)){
			isMazeMoving = false;
		}else{
			isMazeMoving = true;
//			initCPadCenter(x, y);
		}
	}


	public static void releaseAll() {
		if (mView != null) {
			mView.setOnTouchListener(null);
			mView = null;
		}
	}

}
