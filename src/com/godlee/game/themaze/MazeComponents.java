package com.godlee.game.themaze;

import java.util.ArrayList;


public class MazeComponents {
	private int mergeCount;
	private int totalTileNum;
	private MazeTile[][] totalTiles;
	private ArrayList<MazeTile> cacheTile;

	
	public MazeComponents(MazeSeed seed){
		mergeCount = 0;
		
		int x = seed.colNum;
		int y = seed.rowNum;
		totalTileNum = x * y; 
		totalTiles = new MazeTile[x][y];
		for(int i = 0; i < x; i++){
			for(int j = 0; j < y; j++){
				totalTiles[i][j] = new MazeTile(i, j);
			}
		}
		cacheTile = new ArrayList<MazeTile>();
	}
	
	public void merge(MazeTile tile){
		
		mergeCount++;
	}
	
	public MazeTile getRoot(int x, int y){
		return totalTiles[x][y];
	}
	
	public int getSize(){
//		return mComponents.size();
		return mergeCount;
		
	}
	
}
