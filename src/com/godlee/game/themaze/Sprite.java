package com.godlee.game.themaze;

import java.util.LinkedList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

public class Sprite {
	public static final int SIZE = MazeDrawer.TILE_SIZE;
	public static final int MIN_STEP = MazeDrawer.TILE_SIZE / 6;
	public float xInMaze, yInMaze;
	public int colIndex, rowIndex;
	private RectF mRect;
	private Bitmap mBitmap;
	private Matrix matrix;
	private float[] matrixValues; // 此数组为了获取在屏幕中的准确坐标 commit by btbj
	private boolean hasPosition = false;
	private float tempValue, dv; // 在地图中移动是用到的临时变量 commit by btbj
	public boolean isActivity;
	public boolean inMoving;
	public int movingDirection = -1;
	private MazeTile currentTile;
	private Paint mPaint;

	public Sprite(View view, int colNum, int rowNum) {

		initPaint();
		tempValue = 0;
		matrixValues = new float[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };//
		colIndex = colNum;
		rowIndex = rowNum;
		xInMaze = colNum * SIZE;
		yInMaze = rowNum * SIZE;
		mBitmap = BitmapSupply.getBitmap(view, R.drawable.ic_launcher, SIZE,
				SIZE);
		matrix = new Matrix();
		mRect = new RectF();

	}

	public void setActivity(MotionEvent event, float zoom) { // 设置是否处于活动状态
		if (event.getPointerCount() > 1)
			isActivity = false; // 当有多点触摸时设置为非活动
		else {
			float sZoom = (zoom > 1 ? zoom : 1);
			mRect.set(matrixValues[2], matrixValues[5], (matrixValues[2] + SIZE
					* sZoom), (matrixValues[5] + SIZE * sZoom));
			if (mRect.contains(event.getX(0), event.getY(0)))
				isActivity = true;
			else
				isActivity = false;
		}
	}

	public PointF getPositionInScreen() {
		return new PointF(matrixValues[2], matrixValues[5]);
	}

	private void initPaint() {
		mPaint = new Paint();
		mPaint.setColor(Color.RED);
		mPaint.setAntiAlias(true);

	}

	public void drawSelf(Canvas canvas, PointF screenInMaze, float zoom) {
		float left = xInMaze - screenInMaze.x;
		float top = yInMaze - screenInMaze.y;
		matrix.setTranslate(left, top);
		matrix.preScale(zoom, zoom, MazeDrawer.ZOOM_POINT.x - left,
				MazeDrawer.ZOOM_POINT.y - top);
		matrix.getValues(matrixValues); // 获取矩阵变化后的坐标
		canvas.drawBitmap(mBitmap, matrix, mPaint);
		// D.v("xInMaze = " + xInMaze + " yInMaze = " + yInMaze +
		// " colIndex = "+ colIndex + " rowIndex = "+ rowIndex);
	}

	public void quitActivity() {
		isActivity = false;
	}

	/**
	 * 在迷宫中移动的方法，使XInMaze和YInMaze两个变量发生变化，
	 * 
	 * @param direction
	 *            移动方向
	 * @param v
	 *            需要移动的量
	 * @return 实际在迷宫中移动的量
	 */

	public float moveOneStep(int direction, float v) {
		dv = v;
		if (v == 0 && !inMoving)
			return 0;
		else if (!inMoving) { // 判断上次移动是否完成
			movingDirection = direction;
		} else {
			if (v == 0)
				dv = MIN_STEP;
			if (movingDirection != direction && tempValue != 0) { // 设置在移动过程中180度转向问题

				if (movingDirection + direction == 2) {
					movingDirection = direction;
					if (movingDirection == MazeTile.E) {
						tempValue = tempValue + MazeDrawer.TILE_SIZE;
						colIndex--;
					} else {
						tempValue = tempValue - MazeDrawer.TILE_SIZE;
						colIndex++;
					}
				}else	if (movingDirection + direction == 4) {
					movingDirection = direction;
					if (movingDirection == 1) {
						tempValue = tempValue + MazeDrawer.TILE_SIZE;
						rowIndex--;
					} else {
						tempValue = tempValue - MazeDrawer.TILE_SIZE;
						rowIndex++;
					}
				}
			}
		}
		float realMovedValue = tempValue;
		switch (movingDirection) {
		case MazeTile.E: {
			if (!MazeView.baseMaze[colIndex][rowIndex].direction[movingDirection]) {
				tempValue += dv;
				if (tempValue < MazeDrawer.TILE_SIZE) {
					inMoving = true;
					xInMaze += dv;
					realMovedValue = dv;
				} else {
					inMoving = false;
					tempValue = 0;
					colIndex++;
					xInMaze = MazeDrawer.TILE_SIZE * colIndex;
					realMovedValue = MazeDrawer.TILE_SIZE - realMovedValue;
				}
			} else {
				inMoving = false;
				realMovedValue = 0;
			}
			break;
		}
		case MazeTile.S: {
			if (!MazeView.baseMaze[colIndex][rowIndex].direction[movingDirection]) {
				tempValue += dv;
				if (tempValue < MazeDrawer.TILE_SIZE) {
					inMoving = true;
					yInMaze += dv;
					realMovedValue = dv;
				} else {
					inMoving = false;
					tempValue = 0;
					rowIndex++;
					yInMaze = MazeDrawer.TILE_SIZE * rowIndex;
					realMovedValue = MazeDrawer.TILE_SIZE - realMovedValue;
				}

			} else {
				inMoving = false;
				realMovedValue = 0;
			}

			break;
		}
		case MazeTile.W: {
			if (!MazeView.baseMaze[colIndex][rowIndex].direction[movingDirection]) {
				tempValue -= dv;
				if (-tempValue < MazeDrawer.TILE_SIZE) {
					inMoving = true;
					xInMaze -= dv;
					realMovedValue = dv;
				} else {
					inMoving = false;
					tempValue = 0;
					colIndex--;
					xInMaze = MazeDrawer.TILE_SIZE * colIndex;
					realMovedValue = MazeDrawer.TILE_SIZE + realMovedValue;
				}

			} else {
				inMoving = false;
				realMovedValue = 0;
			}

			break;
		}
		case MazeTile.N: {
			if (!MazeView.baseMaze[colIndex][rowIndex].direction[movingDirection]) {
				tempValue -= dv;
				if (-tempValue < MazeDrawer.TILE_SIZE) {
					inMoving = true;
					yInMaze -= dv;
					realMovedValue = dv;
				} else {
					inMoving = false;
					tempValue = 0;
					rowIndex--;
					yInMaze = MazeDrawer.TILE_SIZE * rowIndex;
					realMovedValue = MazeDrawer.TILE_SIZE + realMovedValue;
				}

			} else {
				inMoving = false;
				realMovedValue = 0;
			}
			break;
		}
		}
		return realMovedValue;
	}

}
