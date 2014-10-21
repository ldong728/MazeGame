package com.godlee.game.themaze;

import java.util.LinkedList;


public class MazeSeed {
	public  int rowNum;
	public  int colNum;
	public  int startCol;
	public  int startRow;
	public  int mazeNumber;
	public final LinkedList<Integer> ruleList;
	
	
	public MazeSeed(){
		ruleList = new LinkedList<Integer>();
		startCol = 0;
		startRow = 0;
		colNum = 10;
		rowNum = 10;
		mazeNumber = 3;
	}
	public MazeSeed(int width, int height){
		this();
		colNum = width;
		rowNum = height;
	}
	public MazeSeed(int width, int height, int[] ruleArray){
		this(width, height);
		for(int i : ruleArray)ruleList.add(i);
		
	}
	public MazeSeed(int width, int height, int startCol, int startRow, int[] ruleArray){
		this(width,height,ruleArray);
		this.startCol = startCol;
		this.startRow = startRow;
		
	}
}
