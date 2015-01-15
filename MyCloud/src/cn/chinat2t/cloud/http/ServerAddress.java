package cn.chinat2t.cloud.http;

public class ServerAddress {
	public static final String IP = "http://122.49.1.116";
	public static final String PORT = "";
	
	public static final String GET_NEWS = IP + PORT + "/appapi/index.php?userid=1006&act=index_hot";
	public static final String GET_GALLERY = IP + PORT + "/appapi/index.php?userid=1006&act=index_slide";
	public static final String GET_SORT = IP + PORT + "/appapi/index.php?userid=1006&act=index_cat";
	public static final String GET_LEAST_PRODUCT = IP + PORT + "/appapi/index.php?userid=1006&act=index_product";
	public static final String GET_TOP_NEWS = IP + PORT + "/appapi/index.php?userid=1006&act=news_top";
	public static final String GET_HOT_NEWS = IP + PORT + "/appapi/index.php?userid=1006&act=news_index&pagesize=";
	//public static final String GET_NEWS_SEARCH = IP + PORT + "/appapi/index.php?userid=1006&act=search_news&s=";
	public static final String GET_NEWS_SEARCH=IP + PORT + "/appapi/index.php?userid=1006&act=search_news&s=";
	//http://122.49.1.116/appapi/index.php?userid=1006&act=search_news&s=&pagesize=2&page=1
	public static final String GET_PART_CAT = IP + PORT + "/appapi/index.php?userid=1006&act=product_cat";
	public static final String GET_ALL_CAT = IP + PORT + "/appapi/index.php?userid=1006&act=product_all_cat";
	public static final String GET_PRODUCT_LIST = IP + PORT + "/appapi/index.php?userid=1006&act=product_index_list&pagesize=";
	public static final String GET_PRODUCT_DETAIL = IP + PORT + "/appapi/index.php?userid=1006&act=product_detail&id=";
	public static final String GET_COMMENTS = IP + PORT + "/appapi/index.php?userid=1006&act=product_detail_comment&id=";
	
	//企业搜索
	public static final String GET_BUSINESS_LIST = IP + PORT + "/appapi/index.php?userid=1006&act=search_qy&s=";
	//企业内容详细页面
	public static final String GET_BUSINESS_DETAIL=IP+PORT+"/appapi/index.php?userid=1006&act=qy&id=";
	//产品搜索
	public static final String GET_Product_SearchLIST = IP + PORT + "/appapi/index.php?userid=1006&act=search_product&s=";
	//新闻详细页
	public static final String GET_News_Detail = IP + PORT + "/appapi/index.php?userid=1006&act=news&id=";
	//招商列表
	public static final String GET_BusinessOrder_List = IP + PORT + "/appapi/index.php?userid=1006&act=search_zsdl&s=";
	//招商详细页
	public static final String GET_BusinessOrderDetail = IP + PORT + "/appapi/index.php?userid=1006&act=zsdl&id=";
	//供求商机搜索页
	public static final String GET_BusinessSupplyList = IP + PORT + "/appapi/index.php?userid=1006&act=search_gqsj&s=";
	//供求商机详细页
	public static final String GET_BusinessSupplyDetail = IP + PORT + "/appapi/index.php?userid=1006&act=gqsj&id=";
	//行情价格搜索
	public static final String GET_BusinessPriceList = IP + PORT + "/appapi/index.php?userid=1006&act=search_hqjg&s=";
	//行情价格详细页
	public static final String GET_BusinessPriceDetail = IP + PORT + "/appapi/index.php?userid=1006&act=hqjg&id=";
	//展会展览搜索
	public static final String GET_BusinessExhibitionList = IP + PORT + "/appapi/index.php?userid=1006&act=search_zlzh&s=";
	//展会展览详细页
	public static final String GET_BusinessExhibitionDetail = IP + PORT + "/appapi/index.php?userid=1006&act=zlzh&id=";
	//品牌榜
	public static final String GET_BrandPicsList = IP + PORT + "/appapi/index.php?userid=1006&act=brand_cat";
	//最热企业
	public static final String GET_HotBusinessList = IP + PORT + "/appapi/index.php?userid=1006&act=index_member";
	//产品分类
	public static final String GET_HotSortList = IP + PORT + "/appapi/index.php?userid=1006&act=product_index_list&linkageid=";
	//http://admin.yuncms.com/appapi/index.php?userid=1006&act=product_index_list&linkageid=
	//品牌详细信息
	public static final String GET_BrandDetail = IP + PORT + "/appapi/index.php?userid=1006&act=brand&id=";
	//首页企业下
	public static final String GET_ShopLogo = IP + PORT + "/appapi/index.php?userid=1006&act=index_brand_cat";
		
}
