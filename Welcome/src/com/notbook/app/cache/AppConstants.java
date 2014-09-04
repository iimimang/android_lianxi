package com.notbook.app.cache;

import java.io.File;
import android.os.Environment;
import java.io.File;
import android.os.Environment;



/**
 * ���� ���̳��ó�
 * @author zhangguoyue
 * @CrateTime 2013-3-5
 */
public class AppConstants {
	
/*	public static LoginVo appLoginInfo;	// ȫ�ֵ��û���Ϣ,��Login��onCreate�г�ʼ��
	
	public static  UserInfoSPUtil userInfoSP;	// ȫ�ֵ���ѡ�����ʵ��,��Login��onCreate�г�ʼ��
*/	
	public static int practiceTab = 1;	// TabPractice��tab��ʶ
	
	public static String sdRootPath = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator;	// sd����Ŀ¼
	
	public static String imgCachePath = sdRootPath+"notebook"+File.separator+"ImageCache"+File.separator;	// sd����Ŀ¼
		
}