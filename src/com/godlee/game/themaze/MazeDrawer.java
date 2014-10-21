package com.godlee.game.themaze;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.View;

public class MazeDrawer {

	public static final int TILE_SIZE = MazeActivity.dopXToPx(50);
	public static final Point ZOOM_POINT = new Point(MazeActivity.dopXToPx(90), MazeActivity.dopYToPx(160));
	public static int startTileX, startTileY; //最左上角瓦片在数组中的位置
	public static float offsetX, offsetY;
	private static Bitmap[] dirBitmap;
	private static Paint drawPaint;
	private  int zoomTileOffsetX, zoomTileOffsetY;
	private Matrix matrix;
	private boolean isBitmapLoad;
	private float lastZoomValue;
	private float zoomTileSize;
	

	public MazeDrawer(View view, MazeSeed seed) {
//		lastPointInMaze = new Point(0, 0);
		matrix = new Matrix();
		if (drawPaint == null)
			drawPaint = new Paint();
		loadBitmap(view);

	}

	public void loadBitmap(View view, int tSize) {
		int tileSize =(int)(tSize*1.3);
		if (!isBitmapLoad) {
			dirBitmap = new Bitmap[4];
			dirBitmap[0] = BitmapSupply.getBitmap(view, R.drawable.e, tileSize,
					tileSize);
			dirBitmap[1] = BitmapSupply.getBitmap(view, R.drawable.s, tileSize,
					tileSize);
			dirBitmap[2] = BitmapSupply.getBitmap(view, R.drawable.w, tileSize,
					tileSize);
			dirBitmap[3] = BitmapSupply.getBitmap(view, R.drawable.n, tileSize,
					tileSize);
			isBitmapLoad = true;
		}

	}

	public void loadBitmap(View view) {
		loadBitmap(view, TILE_SIZE);
	}

	
	
	
	private static void drawMazetile(MazeTile mazeTile, Canvas canvas, int i, int j,
			int offsetX, int offsetY, int tileSize) {
		int dir = 0;
		int left = i * tileSize - offsetX;
		int top = j * tileSize - offsetY;
		for (boolean d : mazeTile.getDirection()) {
			if (d) {
				canvas.drawBitmap(dirBitmap[dir], left, top, drawPaint);
			}
			dir++;

		}

	}

	/*
	 * 卡马克卷轴实现
	 * 
	 * @param maze
	 *            迷宫碎片二维数组
	 * @param newPosition
	 *            位图在迷宫中的新位置
	 */

	// public void moveBufferBitmap(MazeTile[][] maze, Point newPosition){
	// bufferCanvas.drawColor(Color.WHITE);
	// drawMazePart(bufferCanvas, maze, newPosition, TILE_SIZE);
	// lastPoint = newPosition;
	// }

	// public void moveBufferBitmapCarmark(MazeTile[][] maze,Point newPosition){
	// int dx = newPosition.x-lastPoint.x;
	// int dy = newPosition.y-lastPoint.y;
	// // D.i("newP = " + newPosition.x+"," +newPosition.y);
	// // D.i("lastP = "+ lastPoint.x +"," +lastPoint.y);
	// // D.i("dx="+dx + " dy=" + dy);
	// Bitmap b = Bitmap.createBitmap(bufferBitmap);
	// // bufferCanvas.save();
	// bufferCanvas.drawColor(Color.WHITE);
	// // bufferCanvas.restore();
	// bufferCanvas.drawBitmap(b, -dx,-dy,drawPaint);
	//
	// Dx+=dx;
	// Dy+=dy;
	// D.i(""+ Dx +" "+Dy);
	//
	// if(Math.abs(Dx)>=TILE_SIZE||Math.abs(Dy)>=TILE_SIZE){
	// //当画面移动大于设定值时，对缓冲位图的空白区进行填充
	//
	// int w = bufferBitmap.getWidth();
	// int h = bufferBitmap.getHeight();
	// Rect r1,r2;
	// if(Dx>0){
	// r1 = new Rect(w-Dx,0,w,h);
	// if(Dy>0){
	// r2 = new Rect(0,h-Dy,w-Dx,h);
	// }else{
	// r2 = new Rect(0,0,w-Dx,-Dy);
	// }
	//
	// }else{
	// r1 = new Rect(0,0,-Dx,h);
	// if(Dy>0){
	// r2 = new Rect(-Dx,h-Dy,w,h);
	// }else{
	// r2 = new Rect(-Dx,0,w,-Dy);
	// }
	// }
	//
	// carmarkDraw(newPosition, maze, r1);
	// carmarkDraw(newPosition, maze, r2);
	// Dx = 0;
	// Dy = 0;
	// }
	// lastPoint = newPosition;
	// }
	/**
	 * 屏幕移动后补充绘制缓冲位图中的空白块
	 * 
	 * @param pInMaze
	 *            缓冲位图在迷宫中的坐标
	 * @param maze迷宫块组成的二维数组
	 * @param rect需要绘制的rect块
	 *            ，坐标为相对于缓冲位图（非迷宫）
	 */
	// public void carmarkDraw(Point pInMaze,MazeTile[][] maze, Rect rect){
	// Rect rectInMaze = new Rect (rect.left+pInMaze.x,rect.top+pInMaze.y,
	// rect.right+pInMaze.x,rect.bottom+pInMaze.y);//获取rect块在迷宫中的坐标
	// int tileXNum = rect.width()/TILE_SIZE+1;
	// int tileYNum = rect.height()/TILE_SIZE+1;
	// int offsetX = rectInMaze.left%TILE_SIZE;
	// int offsetY = rectInMaze.top%TILE_SIZE;
	// int startTileX = rectInMaze.left/TILE_SIZE;
	// int startTileY = rectInMaze.top/TILE_SIZE;
	// int rowMax = (startTileX+tileXNum>=maze.length-1?
	// maze.length-startTileX-1 : tileXNum);
	// int linMax = (startTileY+tileYNum>=maze[0].length-1 ?
	// maze[0].length-startTileY-1 : tileYNum);
	// bufferCanvas.save();
	// bufferCanvas.clipRect(rect);
	// for(int i = 0; i< rowMax; i++){
	// for(int j = 0; j< linMax; j++){
	//
	// drawCarmarktile(maze[i+startTileX][j+startTileY],rect,i,j,offsetX,offsetY,TILE_SIZE);
	//
	// }
	//
	// }
	// bufferCanvas.restore();
	// }
	//
	// private void drawCarmarktile(MazeTile mazeTile, Rect rect,
	// int i, int j, int offsetX, int offsetY, int tileSize) {
	// int dir = 0;
	// int left = i*tileSize-offsetX;
	// int top = j*tileSize-offsetY;
	// for(boolean d : mazeTile.getDirection()){
	// if(d){
	// bufferCanvas.drawBitmap(dirBitmap[dir], left+rect.left, top+rect.top,
	// drawPaint);
	// }
	// dir++;
	//
	// }
	//
	// }
	//
	//
	//
	// public Bitmap getBuffer(){
	// return bufferBitmap;
	//
	// }
	
	/**
	 * 根据指定大小绘制迷宫
	 * @param maze
	 * 			迷宫数组
	 * @param tileSize
	 * 			迷宫单块大小
	 * @return
	 * 			迷宫位图
	 */			
	public static Bitmap getWholeMazeBimap(MazeTile[][] maze, int tileSize) {

		Bitmap m = Bitmap.createBitmap(maze.length * tileSize+10, maze[0].length
				* tileSize+10, Config.ARGB_8888);
		m.setDensity(Bitmap.DENSITY_NONE);
		Canvas c = new Canvas(m);
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[0].length; j++) {
				c.save();
				drawMazetile(maze[i][j], c, i, j, 0, 0, tileSize);
				c.restore();
			}
		}

		return m;

	}

	/**
	 * 获取缓冲位图右上角在迷宫中的最后位置
	 * 
	 * @return 含有位置坐标的点
	 */

	public Bitmap getMazePart(MazeTile[][] maze, Point pInMaze, int width,
			int height, int tileSize) {
		if (pInMaze.x <= 0)
			pInMaze.x = 0;
		if (pInMaze.y <= 0)
			pInMaze.y = 0;
		Bitmap b = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas c = new Canvas(b);
		int tileXNum = width / TILE_SIZE + 1;
		int tileYNum = height / TILE_SIZE + 1;
		int offsetX = pInMaze.x % TILE_SIZE;
		int offsetY = pInMaze.y % TILE_SIZE;
		int startTileX = pInMaze.x / TILE_SIZE;
		int startTileY = pInMaze.y / TILE_SIZE;
		int rowMax = (startTileX + tileXNum >= maze.length  ? maze.length
				- startTileX  : tileXNum);
		int linMax = (startTileY + tileYNum >= maze[0].length  ? maze[0].length
				- startTileY  : tileYNum);
		for (int i = 0; i < rowMax; i++) {
			for (int j = 0; j < linMax; j++) {
				c.save();
				drawMazetile(maze[i + startTileX][j + startTileY], c, i, j,
						offsetX, offsetY, tileSize);
			}
		}

		return b;
	}


	public void drawWholeMap(Canvas canvas, Bitmap bitmap, Point ScreenPInMap,
			Point zoomCenter, float zoom) {
		matrix.setTranslate(-ScreenPInMap.x, -ScreenPInMap.y);
		matrix.preScale(zoom, zoom, zoomCenter.x, zoomCenter.y);

		canvas.drawBitmap(bitmap, matrix, drawPaint);

	}
/**
 * 在指定画布上绘制迷宫，只绘制画布在迷宫中的区域
 * @param canvas 指定的画布
 * @param maze 迷宫片数组
 * @param pInMaze 屏幕在迷宫中的位置
 * @param tileSize 瓦片尺寸
 * @param zoom
 */


	public void drawMazePart(Canvas canvas, MazeTile[][] maze, PointF pInMaze,
			int tileSize, float zoom) {
//		int startTileX = 0;   //起始块的列数
//		int startTileY = 0;		//行数
		int width = canvas.getWidth();  //屏幕尺寸
		int height = canvas.getHeight();
		zoomTileSize = tileSize * zoom;  //缩放后的瓦片尺寸
		int tileXNum = (int) (width / zoomTileSize + 2);
		int tileYNum = (int) (height / zoomTileSize + 2);
		if (lastZoomValue != zoom) { // 判断是否有缩放，以第一个手指为中心点
			zoomTileOffsetX = (int) (ZOOM_POINT.x*(1-zoom))/(int)zoomTileSize ;
			zoomTileOffsetY = (int) (ZOOM_POINT.y*(1-zoom))/(int)zoomTileSize ;
			
		}
		
		startTileX = (int) (pInMaze.x / tileSize)-zoomTileOffsetX;
		startTileY = (int) (pInMaze.y / tileSize)-zoomTileOffsetY;
		offsetX = (pInMaze.x % tileSize)+zoomTileOffsetX*tileSize;
		offsetY = (pInMaze.y % tileSize)+zoomTileOffsetY*tileSize;
		int rowMax = (startTileX + tileXNum >= maze.length - 1 ? maze.length
				- startTileX : tileXNum);
		int linMax = (startTileY + tileYNum >= maze[0].length - 1 ? maze[0].length
				- startTileY : tileYNum);
		for (int i = -Math.abs(zoomTileOffsetX); i < rowMax; i++) {
			if (i + startTileX < 0)
				continue;
			for (int j = -Math.abs(zoomTileOffsetY); j < linMax; j++) {
				if (j + startTileY < 0)
					continue;
				drawMazetile(maze[i + startTileX][j + startTileY], canvas, i,
						j, offsetX, offsetY, zoom);
			}
		}
		lastZoomValue = zoom;
	}

	
	private void drawMazetile(MazeTile mazeTile, Canvas canvas, int i, int j,
			float offsetX, float offsetY, float zoom) {
		int dir = 0;
		float left = i * TILE_SIZE-offsetX;
		float top = j * TILE_SIZE-offsetY;
		matrix.setTranslate(left, top);
		matrix.preScale(zoom, zoom, ZOOM_POINT.x-left, ZOOM_POINT.y-top);
		for (boolean d : mazeTile.getDirection()) {
			if (d) {
				canvas.drawBitmap(dirBitmap[dir], matrix, drawPaint);
			}
			dir++;

		}

	}

}
