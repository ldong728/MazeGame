package com.godlee.game.themaze;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.graphics.Bitmap;
import android.os.Environment;

public class MazeOutPut {
	/**
	 * ��Bitmapѹ��������ļ�������ΪPNG��ʽ
	 * 
	 * @param mBitmap
	 *            ��Ҫ�����Bitmapʵ��
	 * @param bitName
	 *            ����ļ���
	 */
	public static void outPutToFile(Bitmap mBitmap, String bitName) {
		// public void saveMyBitmap(String bitName) throws IOException {
		File f = new File(Environment.getExternalStorageDirectory().getPath() + bitName + ".png");
		try {
			f.createNewFile();
			D.i("creatNewFile");
		} catch (IOException e) {
			e.printStackTrace();
		}
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
			D.i("creatNewStream");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try {
			fOut.flush();
			D.i("flush");
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
			D.i("fileClosed");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// }

	}

}
