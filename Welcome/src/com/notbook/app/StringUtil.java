package com.notbook.app;

public class StringUtil {

	public static int ignoreIndexOf(String subject,String search){
		return ignoreIndexOf(subject,search,0);
	}
	
	public static int ignoreIndexOf(String subject,String search,int soffset){
		//当被查找字符串或查找子字符串为空时，抛出空指针异常。
		if (subject == null || search == null) {
			throw new NullPointerException("输入的参数为空");
		}
		if(soffset>=subject.length() && search.equals("")){
			return subject.length();
		}
		for (int i = soffset; i < subject.length(); i++) {
			if(subject.regionMatches(true, i, search, 0, search.length())){
				return i;
			}
		}
		return -1;
	}
	
	public static int ignoreLastIndexOf(String subject,String search) {
		return ignoreLastIndexOf(subject, search, subject.length());
	}
	
	public static int ignoreLastIndexOf(String subject,String search,int soffset) {
		//当被查找字符串或查找子字符串为空时，抛出空指针异常。
		if (subject == null || search == null) {
			throw new NullPointerException("输入的参数为空");
		}
		if(soffset<=0 && search.equals("")){
			return 0;
		}
		for (int i = soffset; 0 < i; i--) {
			if(subject.regionMatches(true, i, search, 0, search.length())){
				return i;
			}
		}
		
		return -1;
	}
}
