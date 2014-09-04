package com.notbook.app.cache;


 


public class FileManager {

	public static String getSaveFilePath() {
		if (CommonUtil.hasSDCard()) {
//			return CommonUtil.getRootFilePath() + "com.geniuseoe2012/files/";
			
			//filePath:/sdcard/Maddocks/ImageCache/
			return AppConstants.imgCachePath;	
		} else {
			
			//filePath: /data/data/Maddocks/ImageCache
			return CommonUtil.getRootFilePath() + "yashily/ImageCache";
		}
	}
}
