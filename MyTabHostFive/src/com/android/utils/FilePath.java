package com.android.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.StringTokenizer;

import android.util.Log;

public class FilePath {
	public static final String SDCARD = "/sdcard";

	public static final String CHINAT2T = "/mytab";
	public static final String ROOT_DIRECTORY = SDCARD + CHINAT2T + "/";

	public static final String PICTURE = SDCARD + CHINAT2T + "/.picture";

	public static final String PORTRAIT = SDCARD + CHINAT2T + "/.portrait";

	public static final String TEMP = SDCARD + CHINAT2T + "/temp";

	private static FilePath fp;

	private FilePath() {

	}

	public static FilePath getInstance() {
		if (fp == null) {
			fp = new FilePath();
		}
		return fp;
	}

	/**
	 * �ж�sd���Ƿ����
	 * 
	 * @return
	 */
	public boolean isSDCardExist() {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			return true;
		} else
			return false;
	}

	/**
	 * �ж��ļ����Ƿ���� �� �������� �򴴽�
	 * 
	 * @param path
	 * @return
	 */
	public void isExists(String path) {
		//Log.i("aaaaaa",path+"9999999999999999999");
		StringTokenizer st = new StringTokenizer(path, "/");
		String path1 = st.nextToken() + "/";
		String path2 = path1;
		
		
		
		while (st.hasMoreTokens()) {
			path1 = st.nextToken() + "/";
			path2 += path1;
			
			File inbox = new File(path2);
			if (!inbox.exists())
				inbox.mkdir();
		//	Log.i("aaaaaa",inbox+"7777777");
		}
	}

	public String getFileName(String url) {
		return MD5.getMD5(url);
	}

	/**
	 * ɾ��ĳ���ļ����µ������ļ��к��ļ�
	 * 
	 * @param delpath
	 *            String
	 * @return boolean
	 */
	public  boolean deletefile(String delpath){
		try {

			File file = new File(delpath);
			if (!file.isDirectory()) {
				file.delete();
			} else if (file.isDirectory()) {
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File delfile = new File(delpath + "/" + filelist[i]);
					if (!delfile.isDirectory()) {
						delfile.delete();
					} else if (delfile.isDirectory()) {
						deletefile(delpath + "/" + filelist[i]);
					}
				}
//				file.delete();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

}
