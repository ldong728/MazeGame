package com.godlee.game.themaze;

import java.util.Random;

public class MazeTile {
	public static final int N = 3;
	public static final int E = 0;
	public static final int S = 1;
	public static final int W = 2;
	public boolean[] direction; //四个方位的墙  true为隔断，false为联通
	private boolean isMarked; //是否已包含在迷宫集合中
	private boolean cached; //是否是迷宫的边缘块
	private int rowId,colId;

	private int[] uniDir; //可联通方位的数组,长度为四，除-1以外没有重复的值
	
	public MazeTile(int colId, int rowId){
		isMarked = false;
		this.rowId = rowId;
		this.colId = colId;
		direction = new boolean[]{
			true,true,true,true
		};
		uniDir = new int[]{
			-1,-1,-1,-1
		};
		
	}
	/**
	 * 去除某方位的墙壁
	 * @param dir 方位值
	 */
	public void merge(int dir){
		isMarked = true;
		direction[dir] = false;
		
//		openDirection = dir;
	}
	
	public void cache(){
		cached = true;
	}
	/**
	 * 设为起始块
	 */
	public void setRoot(){
		isMarked = true; 
		cached = true;
		int j = 0; 
		for(int i = 0 ; i<uniDir.length; i ++){  //填充可联通方位数组，使四个方位均设为可联通
			uniDir[i]= j;
			j++;
		}
		if(colId==0)uniDir[N] = -1;
		if(rowId==0)uniDir[W] = -1;
	}
	
	public void setUni(int dir){  //添加可联通方位
		for(int i=0; i< uniDir.length; i++){
			if(uniDir[i]==-1){
				uniDir[i]=dir;
				return;
			}
			
		}
	}
	public int getX(){
		return colId;
	}
	public int getY(){
		return rowId;
	}
	public boolean canCache(){
		return !isMarked&&!cached;
	}
	
	
	public boolean canMark(){
		return !isMarked;
	}
	
	/**
	 * 随机返回一个可联通方位
	 * @param random 随机数生成器
	 * @return
	 */
	public int getUniStr(Random random){
		int Count=0;
		for(int i : uniDir ){
			if(i==-1){
				break;
			}else{
				Count++;
			}
		}
		if(Count==1)return uniDir[0];
		return uniDir[random.nextInt(Count)];
	}
	
	/**
	 * 返回一个四个方向的墙的状态
	 * @return
	 */
	public boolean[] getDirection(){
		return direction;
	}
}
