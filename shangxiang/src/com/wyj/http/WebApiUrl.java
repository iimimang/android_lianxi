package com.wyj.http;



public class WebApiUrl  {
	
	public static final String IP = "http://demo123.shangxiang.com";
	public static final String PORT = "";
	
	public static final String GET_TEMPLELIST = IP + PORT + "/api/app/gettemplelist.php"; //寺庙列表接口
	public static final String GET_ORDERLIST = IP + PORT + "/api/app/getorderlist.php"; //发现列表接口
	public static final String GET_ORDER_DETAIL = IP + PORT + "/api/app/getorderinfo.php"; //发现详情接口
	public static final String REGSITER = IP + PORT + "/api/app/regdo.php"; //注册接口
	
	//public static final String CESHIURL = "http://192.168.1.30/sms/module.php?m=sms&c=index&a=ceshi";
	public static final String CESHIURL = "http://demo123.shangxiang.com/api/app/hfupload.php";
	public static final String LOGIN = IP + PORT + "/api/app/logindo.php";	//登录接口
	public static final String THREE_LOGIN = IP + PORT + "/api/app/ologindo.php";  //第三方登录接口
	public static final String Getwishtemplelist = IP + PORT + "/api/app/getwishtemplelist.php";  //委托求愿寺庙接口
	
}
