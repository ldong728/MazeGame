package com.godlee.game.themaze;

import android.graphics.Point;

public class MazePoint extends Point {
	public int xInScreen,yInScreen;
	public int offsetX,offsetY;
	public int mazeIndexX,mazeIndexY;
	
	
	public MazePoint(){
		super();
	}
	
	public MazePoint(int x,int y){
		super(x,y);
	}
	public MazePoint(MazeTile[][] maze,int tileSize,int x, int y){
		this(x,y);
		mazeIndexX = x/tileSize;
		mazeIndexY = y/tileSize;
		offsetX = x%tileSize;
		offsetY = y%tileSize;
		
	}
	public void set(MazeTile[][] maze, int tileSize, int x,int y){
		this.set(x,y);
		mazeIndexX = x/tileSize;
		mazeIndexY = y/tileSize;
		offsetX = x%tileSize;
		offsetY = y%tileSize;
	
	}
	
		
}
