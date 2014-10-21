package com.godlee.game.themaze;

import java.text.RuleBasedCollator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import android.R.integer;
import android.graphics.Bitmap;

public class MazeCreator {
	public static int totalRowCount, totalLinCount;
	public static int topRow, topLine;
	private static LinkedList<MazeTile[][]> MazeList;
	private static Random random = new Random(System.currentTimeMillis());
	private static MazeTile currentTile;

	public static MazeTile[][] getNewMaze(MazeSeed seed) {
		totalRowCount = seed.colNum;
		totalLinCount = seed.rowNum;
		topRow = totalRowCount - 1;
		topLine = totalLinCount - 1;
		int totalTileNum = totalRowCount * totalLinCount;
		int MergeCount = 0;
		MazeTile[][] sTiles = getinitTiles(seed);
		MazeTile rootTile = sTiles[seed.startCol][seed.startRow];
		rootTile.setRoot();
		currentTile = rootTile;
//		MergeCount++;
		ArrayList<MazeTile> cacheTileList = new ArrayList<MazeTile>();
		while (MergeCount < totalTileNum) {
			mergeTileWithRule(cacheTileList, sTiles,seed.ruleList);
			MergeCount++;
		}

		return sTiles;

	}

	public static Bitmap getDrawbleMaze(MazeSeed seed, int tileSize) {
		MazeTile[][] maze = getNewMaze(seed);
		Bitmap m = MazeDrawer.getWholeMazeBimap(maze, tileSize);
		return m;
	}

	public static void getMaze(MazeSeed seed) {
		if (MazeList.size() < seed.mazeNumber) {
			MazeTile[][] slist = getNewMaze(seed);
			synchronized (MazeList) {
				MazeList.add(slist);
			}
		}
	}

	private static void resetCache(MazeTile[][] sTiles,
			ArrayList<MazeTile> list, MazeTile tile) {
		int x = tile.getX();
		int y = tile.getY();
		if (x < topRow) {
			sTiles[x + 1][y].setUni(MazeTile.W);
			addToCache(list, sTiles[x + 1][y]);
		}
		if (y < topLine) {
			sTiles[x][y + 1].setUni(MazeTile.N);
			addToCache(list, sTiles[x][y + 1]);
		}
		if (x > 0) {
			sTiles[x - 1][y].setUni(MazeTile.E);
			addToCache(list, sTiles[x - 1][y]);
		}
		if (y > 0) {
			sTiles[x][y - 1].setUni(MazeTile.S);
			addToCache(list, sTiles[x][y - 1]);
		}
		list.remove(tile);
	}

	private static void addToCache(ArrayList<MazeTile> list, MazeTile tile) {
		if (tile.canCache()) {
			tile.cache();
			list.add(tile);
		}

	}

	private static void mergeTile(ArrayList<MazeTile> list,
			MazeTile[][] totallist) {
		MazeTile tile = list.remove(random.nextInt(list.size()));
		int x = tile.getX();
		int y = tile.getY();
		int str = tile.getUniStr(random);
		tile.merge(str);
		
		switch (str) {
		case MazeTile.E: {
			totallist[x + 1][y].merge(MazeTile.W);
			break;
		}
		case MazeTile.S: {
			totallist[x][y + 1].merge(MazeTile.N);
			break;

		}
		case MazeTile.W: {
			totallist[x - 1][y].merge(MazeTile.E);
			break;

		}
		case MazeTile.N: {
			totallist[x][y - 1].merge(MazeTile.S);
			break;

		}
		}
		resetCache(totallist, list, tile);

	}

	/**
	 * 按照规则建立迷宫的方法，如迷宫数组不为空，则按照数组的内容顺次联通迷宫碎片，直到规则数组内容用尽
	 * 再开始随机生成
	 * @param cachelist
	 * 缓存数组，用来储存待联通的碎片  
	 * @param totallist
	 * 总的碎片数组，用来提供待联通碎片
	 * @param mazeRule
	 * 规则数组，可为空数组
	 */
	
	private static void mergeTileWithRule(ArrayList<MazeTile> cachelist,
			MazeTile[][] totallist, LinkedList<Integer> mazeRule) {
		
		int x = currentTile.getX();
		int y = currentTile.getY();
		int cacheListSize = cachelist.size();
		int mazeRuleSize = mazeRule.size();
		int str;
		if (mazeRuleSize > 0) {
			resetCache(totallist, cachelist, currentTile);
			str = mazeRule.remove();
			currentTile.merge(str);
			switch (str) {
			case MazeTile.E: {
				currentTile = totallist[x + 1][y];
				currentTile.merge(MazeTile.W);
				break;
			}
			case MazeTile.S: {
				currentTile = totallist[x][y + 1];
				currentTile.merge(MazeTile.N);
				break;
			}
			case MazeTile.W: {
				currentTile = totallist[x - 1][y];
				currentTile.merge(MazeTile.E);
				break;

			}
			case MazeTile.N: {
				currentTile = totallist[x][y - 1];
				currentTile.merge(MazeTile.S);
				break;

			}
			}
			cachelist.remove(currentTile);

		} else {
			str = currentTile.getUniStr(random);
			currentTile.merge(str);
			switch (str) {
			case MazeTile.E: {
				totallist[x + 1][y].merge(MazeTile.W);
				break;
			}
			case MazeTile.S: {
				totallist[x][y + 1].merge(MazeTile.N);
				break;

			}
			case MazeTile.W: {
				totallist[x - 1][y].merge(MazeTile.E);
				break;

			}
			case MazeTile.N: {
				totallist[x][y - 1].merge(MazeTile.S);
				break;

			}
			}

			if(cacheListSize>0)currentTile = cachelist.remove(random.nextInt(cacheListSize));
			resetCache(totallist, cachelist, currentTile);

		}

		// currentTile.merge(str);

	}

	private static MazeTile[][] getinitTiles(MazeSeed seed) {
		int x = seed.colNum;
		int y = seed.rowNum;
		MazeTile[][] totalTiles = new MazeTile[x][y];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				totalTiles[i][j] = new MazeTile(i, j);
			}
		}

		return totalTiles;

	}

}
