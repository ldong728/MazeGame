package com.godlee.game.themaze;

import java.util.Random;

public class MazeTile {
	public static final int N = 3;
	public static final int E = 0;
	public static final int S = 1;
	public static final int W = 2;
	public boolean[] direction; //�ĸ���λ��ǽ  trueΪ���ϣ�falseΪ��ͨ
	private boolean isMarked; //�Ƿ��Ѱ������Թ�������
	private boolean cached; //�Ƿ����Թ��ı�Ե��
	private int rowId,colId;

	private int[] uniDir; //����ͨ��λ������,����Ϊ�ģ���-1����û���ظ���ֵ
	
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
	 * ȥ��ĳ��λ��ǽ��
	 * @param dir ��λֵ
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
	 * ��Ϊ��ʼ��
	 */
	public void setRoot(){
		isMarked = true; 
		cached = true;
		int j = 0; 
		for(int i = 0 ; i<uniDir.length; i ++){  //������ͨ��λ���飬ʹ�ĸ���λ����Ϊ����ͨ
			uniDir[i]= j;
			j++;
		}
		if(colId==0)uniDir[N] = -1;
		if(rowId==0)uniDir[W] = -1;
	}
	
	public void setUni(int dir){  //��ӿ���ͨ��λ
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
	 * �������һ������ͨ��λ
	 * @param random �����������
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
	 * ����һ���ĸ������ǽ��״̬
	 * @return
	 */
	public boolean[] getDirection(){
		return direction;
	}
}
