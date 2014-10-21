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
	public static int startTileX, startTileY; //�����Ͻ���Ƭ�������е�λ��
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
	 * ����˾���ʵ��
	 * 
	 * @param maze
	 *            �Թ���Ƭ��ά����
	 * @param newPosition
	 *            λͼ���Թ��е���λ��
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
	// //�������ƶ������趨ֵʱ���Ի���λͼ�Ŀհ����������
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
	 * ��Ļ�ƶ��󲹳���ƻ���λͼ�еĿհ׿�
	 * 
	 * @param pInMaze
	 *            ����λͼ���Թ��е�����
	 * @param maze�Թ�����ɵĶ�ά����
	 * @param rect��Ҫ���Ƶ�rect��
	 *            ������Ϊ����ڻ���λͼ�����Թ���
	 */
	// public void carmarkDraw(Point pInMaze,MazeTile[][] maze, Rect rect){
	// Rect rectInMaze = new Rect (rect.left+pInMaze.x,rect.top+pInMaze.y,
	// rect.right+pInMaze.x,rect.bottom+pInMaze.y);//��ȡrect�����Թ��е�����
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
	 * ����ָ����С�����Թ�
	 * @param maze
	 * 			�Թ�����
	 * @param tileSize
	 * 			�Թ������С
	 * @return
	 * 			�Թ�λͼ
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
	 * ��ȡ����λͼ���Ͻ����Թ��е����λ��
	 * 
	 * @return ����λ������ĵ�
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
 * ��ָ�������ϻ����Թ���ֻ���ƻ������Թ��е�����
 * @param canvas ָ���Ļ���
 * @param maze �Թ�Ƭ����
 * @param pInMaze ��Ļ���Թ��е�λ��
 * @param tileSize ��Ƭ�ߴ�
 * @param zoom
 */


	public void drawMazePart(Canvas canvas, MazeTile[][] maze, PointF pInMaze,
			int tileSize, float zoom) {
//		int startTileX = 0;   //��ʼ�������
//		int startTileY = 0;		//����
		int width = canvas.getWidth();  //��Ļ�ߴ�
		int height = canvas.getHeight();
		zoomTileSize = tileSize * zoom;  //���ź����Ƭ�ߴ�
		int tileXNum = (int) (width / zoomTileSize + 2);
		int tileYNum = (int) (height / zoomTileSize + 2);
		if (lastZoomValue != zoom) { // �ж��Ƿ������ţ��Ե�һ����ָΪ���ĵ�
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
