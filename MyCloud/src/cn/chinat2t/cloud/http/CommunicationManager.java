package cn.chinat2t.cloud.http;


public class CommunicationManager {
	private final String TAG = "CommunicationManager";
	private final int PAGESIZE = 10;
	public static final byte FAIL = 0;
	public static final byte SUCCEED = 1;
    
	private static CommunicationManager instance;
	private NetworkManager networkManager;
	private JsonParserManager jsonParserManager;

	public CommunicationManager() {
		if (networkManager == null)
			networkManager = NetworkManager.getInstance();

		if (jsonParserManager == null)
			jsonParserManager = JsonParserManager.getInstance();
	}

	public static CommunicationManager getInstance() {
		if (instance == null)
			instance = new CommunicationManager();

		return instance;
	}

	/**
	 * 获得新闻详细内容
	 * 
	 */
	/**
	 * 获取新闻资讯
	 * @param listener
	 */
	public void getNewsDetail(String id, final CommunicationResultListener listener) {
		
		if (listener == null) {
			return;
		}
		
		String url = ServerAddress.GET_News_Detail+id;
		url=url.trim();
//		listener.setToken(System.currentTimeMillis());
		networkManager.requestGetString(url,new NetworkRequestListener() {
			@Override
			public void resultString(String str) {
				if (str == null || str.equals("")) {
					listener.resultListener(FAIL, "请求失败，请重试!");
					return;
				}
				jsonParserManager.parserNewsDetailResult(str, listener);
			}
		});
	}
	/**
	 * 获取新闻资讯
	 * @param listener
	 */
	public void getNews( final CommunicationResultListener listener) {

		if (listener == null) {
			return;
		}

		String url = ServerAddress.GET_NEWS;
		url=url.trim();
//		listener.setToken(System.currentTimeMillis());
		networkManager.requestGetString(url,new NetworkRequestListener() {
					@Override
					public void resultString(String str) {
						if (str == null || str.equals("")) {
							listener.resultListener(FAIL, "请求失败，请重试!");
							return;
						}
						jsonParserManager.parserNews(str, listener);
					}
				});
	}
	
	/**
	 * 获取幻灯片
	 * @param listener
	 */
	public void getGalleryImage( final CommunicationResultListener listener) {
		
		if (listener == null) {
			return;
		}
		
		String url = ServerAddress.GET_GALLERY;
		url=url.trim();
//		listener.setToken(System.currentTimeMillis());
		networkManager.requestGetString(url,new NetworkRequestListener() {
			@Override
			public void resultString(String str) {
				if (str == null || str.equals("")) {
					listener.resultListener(FAIL, "请求失败，请重试!");
					return;
				}
				jsonParserManager.parserGalleryResult(str, listener);
			}
		});
	}
	
	/**
	 * 获取首页面分类
	 * @param listener
	 */
	public void getShopSort( final CommunicationResultListener listener) {
		
		if (listener == null) {
			return;
		}
		
		String url = ServerAddress.GET_SORT;
		url=url.trim();
//		listener.setToken(System.currentTimeMillis());
		networkManager.requestGetString(url,new NetworkRequestListener() {
			@Override
			public void resultString(String str) {
				if (str == null || str.equals("")) {
					listener.resultListener(FAIL, "请求失败，请重试!");
					return;
				}
				jsonParserManager.parserSortResult(str, listener);
			}
		});
	}
	
	
	public void getLeastProduct( final CommunicationResultListener listener) {
		
		if (listener == null) {
			return;
		}
		
		String url = ServerAddress.GET_LEAST_PRODUCT;
		url=url.trim();
//		listener.setToken(System.currentTimeMillis());
		networkManager.requestGetString(url,new NetworkRequestListener() {
			@Override
			public void resultString(String str) {
				if (str == null || str.equals("")) {
					listener.resultListener(FAIL, "请求失败，请重试!");
					return;
				}
				jsonParserManager.parserProductResult1(str, listener);
			}
		});
	}
	/**
	 * @author wyq
	 * 2013.5.23 13:15
	 * 获得热门企业
	 */
public void getHotBusiness( final CommunicationResultListener listener) {
		
		if (listener == null) {
			return;
		}
		
		String url = ServerAddress.GET_HotBusinessList;
		url=url.trim();
//		listener.setToken(System.currentTimeMillis());
		networkManager.requestGetString(url,new NetworkRequestListener() {
			@Override
			public void resultString(String str) {
				if (str == null || str.equals("")) {
					listener.resultListener(FAIL, "请求失败，请重试!");
					return;
				}
				jsonParserManager.parserHotBusinessResult(str, listener);
			}
		});
	}
	
/**
 * @author wyq
 * 2013.5.23 13:15
 * 获得热门企业下边的三张图片
 */
public void getShopLogo( final CommunicationResultListener listener) {
	
	if (listener == null) {
		return;
	}
	
	String url = ServerAddress.GET_ShopLogo;
	url=url.trim();
//	listener.setToken(System.currentTimeMillis());
	networkManager.requestGetString(url,new NetworkRequestListener() {
		@Override
		public void resultString(String str) {
			if (str == null || str.equals("")) {
				listener.resultListener(FAIL, "请求失败，请重试!");
				return;
			}
			jsonParserManager.parserShopLogoResult(str, listener);
		}
	});
}

	public void getTopNews( final CommunicationResultListener listener) {
		
		if (listener == null) {
			return;
		}
		
		String url = ServerAddress.GET_TOP_NEWS;
		url=url.trim();
//		listener.setToken(System.currentTimeMillis());
		networkManager.requestGetString(url,new NetworkRequestListener() {
			@Override
			public void resultString(String str) {
				if (str == null || str.equals("")) {
					listener.resultListener(FAIL, "请求失败，请重试!");
					return;
				}
				jsonParserManager.parserTopNewsResult(str, listener);
			}
		});
	}
	
	
	public void getHotNews(int page,String pagesize, final CommunicationResultListener listener) {
		
		if (listener == null) {
			return;
		}
		
		String url = ServerAddress.GET_HOT_NEWS+pagesize+"&page="+page;
		url=url.trim();
//		listener.setToken(System.currentTimeMillis());
		networkManager.requestGetString(url,new NetworkRequestListener() {
			@Override
			public void resultString(String str) {
				if (str == null || str.equals("")) {
					listener.resultListener(FAIL, "请求失败，请重试!");
					return;
				}
				jsonParserManager.parserHotNewsResult(str, listener);
			}
		});
	}
	
	
	public void getSearchBusiness(int page,String pagesize, String input,final CommunicationResultListener listener) {
		
		if (listener == null) {
			return;
		}
		
		String url = ServerAddress.GET_BUSINESS_LIST+input.trim()+"&pagesize="+pagesize+"&page="+page;
		url=url.trim();
//		listener.setToken(System.currentTimeMillis());
		networkManager.requestGetString(url,new NetworkRequestListener() {
			@Override
			public void resultString(String str) {
				if (str == null || str.equals("")) {
					listener.resultListener(FAIL, "请求失败，请重试!");
					return;
				}
				jsonParserManager.parserBusinessResult(str, listener);
			}
		});
	}
	
	
	public void getSearchNews(int currentPage,String pageSize, String input,final CommunicationResultListener listener) {
		
		if (listener == null) {
			return;
		}
		
		String url = (ServerAddress.GET_NEWS_SEARCH+input).trim()+"&pagesize="+pageSize+"&page="+currentPage;
		url=url.trim();
//		listener.setToken(System.currentTimeMillis());
		networkManager.requestGetString(url,new NetworkRequestListener() {
			@Override
			public void resultString(String str) {
				if (str == null || str.equals("")) {
					listener.resultListener(FAIL, "请求失败，请重试!");
					return;
				}
				jsonParserManager.parserNewsSearchResult(str, listener);
			}
		});
	}
	
	
	public void getPartCat(final CommunicationResultListener listener) {
		
		if (listener == null) {
			return;
		}
		
		String url = ServerAddress.GET_PART_CAT;
		url=url.trim();
//		listener.setToken(System.currentTimeMillis());
		networkManager.requestGetString(url,new NetworkRequestListener() {
			@Override
			public void resultString(String str) {
				if (str == null || str.equals("")) {
					listener.resultListener(FAIL, "请求失败，请重试!");
					return;
				}
				jsonParserManager.parserSortResult(str, listener);
			}
		});
	}
	
	
	public void getAllCat(final CommunicationResultListener listener) {
		
		if (listener == null) {
			return;
		}
		
		String url = ServerAddress.GET_ALL_CAT;
		url=url.trim();
//		listener.setToken(System.currentTimeMillis());
		networkManager.requestGetString(url,new NetworkRequestListener() {
			@Override
			public void resultString(String str) {
				if (str == null || str.equals("")) {
					listener.resultListener(FAIL, "请求失败，请重试!");
					return;
				}
				jsonParserManager.parserSortResult(str, listener);
			}
		});
	}
	
	public void getSearchProduct(int page,String pageSize, String input,final CommunicationResultListener listener) {
		
		if (listener == null) {
			return;
		}
		
		String url = ServerAddress.GET_Product_SearchLIST+input.trim()+"&pagesize="+pageSize+"&page="+page;
		url=url.trim();
//		listener.setToken(System.currentTimeMillis());
		networkManager.requestGetString(url,new NetworkRequestListener() {
			@Override
			public void resultString(String str) {
				if (str == null || str.equals("")) {
					listener.resultListener(FAIL, "请求失败，请重试!");
					return;
				}
				jsonParserManager.parserProductResult(str, listener);
			}
		});
	}
	
	public void getProductSortList(int page,String pageSize,String pid, final CommunicationResultListener listener) {
		
		if (listener == null) {
			return;
		}
		
		String url = ServerAddress.GET_HotSortList+pid+"&pagesize="+pageSize+"&page="+page;
		url=url.trim();
//		listener.setToken(System.currentTimeMillis());
		networkManager.requestGetString(url,new NetworkRequestListener() {
			@Override
			public void resultString(String str) {
				if (str == null || str.equals("")) {
					listener.resultListener(FAIL, "请求失败，请重试!");
					return;
				}
				jsonParserManager.parserProductResult(str, listener);
			}
		});
	}
	
	public void getProductList(int page,String pageSize, final CommunicationResultListener listener) {
		
		if (listener == null) {
			return;
		}
		
		String url = ServerAddress.GET_PRODUCT_LIST+pageSize+"&page="+page;
		url=url.trim();
//		listener.setToken(System.currentTimeMillis());
		networkManager.requestGetString(url,new NetworkRequestListener() {
			@Override
			public void resultString(String str) {
				if (str == null || str.equals("")) {
					listener.resultListener(FAIL, "请求失败，请重试!");
					return;
				}
				jsonParserManager.parserProductResult(str, listener);
			}
		});
	}
	
	public void getProductDetail(String id, final CommunicationResultListener listener) {
		
		if (listener == null) {
			return;
		}
		
		String url = ServerAddress.GET_PRODUCT_DETAIL+id;
		url=url.trim();
//		listener.setToken(System.currentTimeMillis());
		networkManager.requestGetString(url,new NetworkRequestListener() {
			@Override
			public void resultString(String str) {
				if (str == null || str.equals("")) {
					listener.resultListener(FAIL, "请求失败，请重试!");
					return;
				}
				jsonParserManager.parserProductDetailResult(str, listener);
			}
		});
	}
	
	
	public void getProductComments(String id, final CommunicationResultListener listener) {
		
		if (listener == null) {
			return;
		}
		
		String url = ServerAddress.GET_COMMENTS+id;
		url=url.trim();
//		listener.setToken(System.currentTimeMillis());
		networkManager.requestGetString(url,new NetworkRequestListener() {
			@Override
			public void resultString(String str) {
				if (str == null || str.equals("")) {
					listener.resultListener(FAIL, "请求失败，请重试!");
					return;
				}
				jsonParserManager.parserCommentResult(str, listener);
			}
		});
	}
	
	/**
	 * @author 汪亚强
	 * @2013.5.20 14：41
	 * 企业搜索页面获取企业列表信息
	 */
	public void getBusinessList(int page,String pagesize, final CommunicationResultListener listener) {
		
		if (listener == null) {
			return;
		}
		
		String url = ServerAddress.GET_BUSINESS_LIST+"&pagesize="+pagesize+"&page="+page;
		url=url.trim();
//		listener.setToken(System.currentTimeMillis());
		networkManager.requestGetString(url,new NetworkRequestListener() {
			@Override
			public void resultString(String str) {
				if (str == null || str.equals("")) {
					listener.resultListener(FAIL, "请求失败，请重试!");
					return;
				}
				jsonParserManager.parserBusinessResult(str, listener);
			}
		});
	}
	
public void getBusinessDetail(String id, final CommunicationResultListener listener) {
		
		if (listener == null) {
			return;
		}
		
		String url = ServerAddress.GET_BUSINESS_DETAIL+id;
		url=url.trim();
//		listener.setToken(System.currentTimeMillis());
		networkManager.requestGetString(url,new NetworkRequestListener() {
			@Override
			public void resultString(String str) {
				if (str == null || str.equals("")) {
					listener.resultListener(FAIL, "请求失败，请重试!");
					return;
				}
				jsonParserManager.parserBusinessDetailResult(str, listener);
			}
		});
	}
	
/**
 * @author 汪亚强
 * @2013.5.21 14：44
 * 招商列表信息
 */
public void getBusinessOrderList(int page,String pageSize, final CommunicationResultListener listener) {
	
	if (listener == null) {
		return;
	}
	
	String url = ServerAddress.GET_BusinessOrder_List+"&pagesize="+pageSize+"&page="+page;;
	url=url.trim();
//	listener.setToken(System.currentTimeMillis());
	networkManager.requestGetString(url,new NetworkRequestListener() {
		@Override
		public void resultString(String str) {
			if (str == null || str.equals("")) {
				listener.resultListener(FAIL, "请求失败，请重试!");
				return;
			}
			jsonParserManager.parserBusinessOrderResult(str, listener);
		}
	});
}
/**
 * @author 汪亚强
 * @2013.5.21 15：24
 * 招商搜索
 */
public void getSearchBusinessOrder(int page,String pageSize, String input,final CommunicationResultListener listener) {
	
	if (listener == null) {
		return;
	}
	
	String url = ServerAddress.GET_BusinessOrder_List+input.trim()+"&pagesize="+pageSize+"&page="+page;
	url=url.trim();
//	listener.setToken(System.currentTimeMillis());
	networkManager.requestGetString(url,new NetworkRequestListener() {
		@Override
		public void resultString(String str) {
			if (str == null || str.equals("")) {
				listener.resultListener(FAIL, "请求失败，请重试!");
				return;
			}
			jsonParserManager.parserBusinessOrderResult(str, listener);
		}
	});
}
/**
 * 
 * @author 汪亚强
 * 2013.5.21 16:20
 * @param id
 * @param listener
 */
public void getBusinessOrderDetail(String id, final CommunicationResultListener listener) {
	
	if (listener == null) {
		return;
	}
	
	String url = ServerAddress.GET_BusinessOrderDetail+id;
	url=url.trim();
//	listener.setToken(System.currentTimeMillis());
	networkManager.requestGetString(url,new NetworkRequestListener() {
		@Override
		public void resultString(String str) {
			if (str == null || str.equals("")) {
				listener.resultListener(FAIL, "请求失败，请重试!");
				return;
			}
			jsonParserManager.parserBusinessOrderDetailResult(str, listener);
		}
	});
}

/**
 * @author 汪亚强
 * @2013.5.21 14：44
 * 供求商机列表信息
 */
public void getBusinessSupplyList(int page,String pageSize, final CommunicationResultListener listener) {
	
	if (listener == null) {
		return;
	}
	
	String url = ServerAddress.GET_BusinessSupplyList+"&pagesize="+pageSize+"&page="+page;
	url=url.trim();
//	listener.setToken(System.currentTimeMillis());
	networkManager.requestGetString(url,new NetworkRequestListener() {
		@Override
		public void resultString(String str) {
			if (str == null || str.equals("")) {
				listener.resultListener(FAIL, "请求失败，请重试!");
				return;
			}
			jsonParserManager.parserBusinessSupplyResult(str, listener);
		}
	});
}
/**
 * @author 汪亚强
 * @2013.5.21 15：24
 * 供求商机搜索
 */
public void getSearchBusinessSupply(int page,String pageSize, String input,final CommunicationResultListener listener) {
	
	if (listener == null) {
		return;
	}
	
	String url = ServerAddress.GET_BusinessSupplyList+input.trim()+"&pagesize="+pageSize+"&page="+page;
	url=url.trim();
//	listener.setToken(System.currentTimeMillis());
	networkManager.requestGetString(url,new NetworkRequestListener() {
		@Override
		public void resultString(String str) {
			if (str == null || str.equals("")) {
				listener.resultListener(FAIL, "请求失败，请重试!");
				return;
			}
			jsonParserManager.parserBusinessSupplyResult(str, listener);
		}
	});
}


/**
 * @author 汪亚强
 * @2013.5.21 14：44
 * 行情价格列表信息
 */
public void getBusinessPriceList(int page,String pageSize, final CommunicationResultListener listener) {
	
	if (listener == null) {
		return;
	}
	
	String url = ServerAddress.GET_BusinessPriceList+"&pagesize="+pageSize+"&page="+page;
	url=url.trim();
//	listener.setToken(System.currentTimeMillis());
	networkManager.requestGetString(url,new NetworkRequestListener() {
		@Override
		public void resultString(String str) {
			if (str == null || str.equals("")) {
				listener.resultListener(FAIL, "请求失败，请重试!");
				return;
			}
			jsonParserManager.parserBusinessPriceResult(str, listener);
		}
	});
}
/**
 * @author 汪亚强
 * @2013.5.21 15：24
 * 行情价格搜索
 */
public void getSearchBusinessPrice(int page,String pageSize, String input,final CommunicationResultListener listener) {
	
	if (listener == null) {
		return;
	}
	
	String url = ServerAddress.GET_BusinessPriceList+input.trim()+"&pagesize="+pageSize+"&page="+page;
	url=url.trim();
//	listener.setToken(System.currentTimeMillis());
	networkManager.requestGetString(url,new NetworkRequestListener() {
		@Override
		public void resultString(String str) {
			if (str == null || str.equals("")) {
				listener.resultListener(FAIL, "请求失败，请重试!");
				return;
			}
			jsonParserManager.parserBusinessPriceResult(str, listener);
		}
	});
}

/**
 * @author 汪亚强
 * @2013.5.21 14：44
 * 行情价格列表信息
 */
public void getBusinessExhibitionList(int page,String pageSize, final CommunicationResultListener listener) {
	
	if (listener == null) {
		return;
	}
	
	String url = ServerAddress.GET_BusinessExhibitionList+"&pagesize="+pageSize+"&page="+page;
	url=url.trim();
//	listener.setToken(System.currentTimeMillis());
	networkManager.requestGetString(url,new NetworkRequestListener() {
		@Override
		public void resultString(String str) {
			if (str == null || str.equals("")) {
				listener.resultListener(FAIL, "请求失败，请重试!");
				return;
			}
			jsonParserManager.parserBusinessExhibitionResult(str, listener);
		}
	});
}
/**
 * @author 汪亚强
 * @2013.5.21 15：24
 * 展会价格搜索
 */
public void getSearchBusinessExhibition(int page,String pageSize, String input,final CommunicationResultListener listener) {
	
	if (listener == null) {
		return;
	}
	
	String url = ServerAddress.GET_BusinessExhibitionList+input.trim()+"&pagesize="+pageSize+"&page="+page;
	url=url.trim();
//	listener.setToken(System.currentTimeMillis());
	networkManager.requestGetString(url,new NetworkRequestListener() {
		@Override
		public void resultString(String str) {
			if (str == null || str.equals("")) {
				listener.resultListener(FAIL, "请求失败，请重试!");
				return;
			}
			jsonParserManager.parserBusinessExhibitionResult(str, listener);
		}
	});
}


/**
 * @author 汪亚强
 * @2013.5.20 14：41
 * 品牌榜
 */
public void getBrandPicsList( final CommunicationResultListener listener) {
	
	if (listener == null) {
		return;
	}
	
	String url = ServerAddress.GET_BrandPicsList;
	url=url.trim();
//	listener.setToken(System.currentTimeMillis());
	networkManager.requestGetString(url,new NetworkRequestListener() {
		@Override
		public void resultString(String str) {
			if (str == null || str.equals("")) {
				listener.resultListener(FAIL, "请求失败，请重试!");
				return;
			}
			jsonParserManager.parserBrandPicsResult(str, listener);
		}
	});
}



/**
 * @author 汪亚强
 * @2013.5.20 14：41
 * 品牌榜
 */
public void getBrandPicsList1(int page,String pageSize, final CommunicationResultListener listener) {
	
	if (listener == null) {
		return;
	}
	
	String url = ServerAddress.GET_BrandPicsList+"&pagesize="+pageSize+"&page="+page;
	url=url.trim();
//	listener.setToken(System.currentTimeMillis());
	networkManager.requestGetString(url,new NetworkRequestListener() {
		@Override
		public void resultString(String str) {
			if (str == null || str.equals("")) {
				listener.resultListener(FAIL, "请求失败，请重试!");
				return;
			}
			jsonParserManager.parserBrandPicsResult(str, listener);
		}
	});
}

/**
 * 获取新闻资讯
 * @param listener
 */
public void getPriceDetail(String id, final CommunicationResultListener listener) {
	
	if (listener == null) {
		return;
	}
	
	String url = ServerAddress.GET_BusinessPriceDetail+id;
	url=url.trim();
//	listener.setToken(System.currentTimeMillis());
	networkManager.requestGetString(url,new NetworkRequestListener() {
		@Override
		public void resultString(String str) {
			if (str == null || str.equals("")) {
				listener.resultListener(FAIL, "请求失败，请重试!");
				return;
			}
			jsonParserManager.parserPriceDetailResult(str, listener);
		}
	});
}
/**
 * 获取展览展会详细页
 * @param listener
 */
public void getExhibitionDetail(String id, final CommunicationResultListener listener) {
	
	if (listener == null) {
		return;
	}
	
	String url = ServerAddress.GET_BusinessExhibitionDetail+id;
	url=url.trim();
//	listener.setToken(System.currentTimeMillis());
	networkManager.requestGetString(url,new NetworkRequestListener() {
		@Override
		public void resultString(String str) {
			if (str == null || str.equals("")) {
				listener.resultListener(FAIL, "请求失败，请重试!");
				return;
			}
			jsonParserManager.parserExhibitionDetailResult(str, listener);
		}
	});
}
/**
 * 获取供求商机详细内容
 * @param listener
 */
public void getSupplyDetail(String id, final CommunicationResultListener listener) {
	
	if (listener == null) {
		return;
	}
	
	String url = ServerAddress.GET_BusinessSupplyDetail+id;
	url=url.trim();
//	listener.setToken(System.currentTimeMillis());
	networkManager.requestGetString(url,new NetworkRequestListener() {
		@Override
		public void resultString(String str) {
			if (str == null || str.equals("")) {
				listener.resultListener(FAIL, "请求失败，请重试!");
				return;
			}
			jsonParserManager.parserSupplyDetailResult(str, listener);
		}
	});
}

/**
 * 获取品牌详细内容
 * @param listener
 */
public void getBrandPicDetail(String id, final CommunicationResultListener listener) {
	
	if (listener == null) {
		return;
	}
	
	String url = ServerAddress.GET_BrandDetail+id;
	url=url.trim();
//	listener.setToken(System.currentTimeMillis());
	networkManager.requestGetString(url,new NetworkRequestListener() {
		@Override
		public void resultString(String str) {
			if (str == null || str.equals("")) {
				listener.resultListener(FAIL, "请求失败，请重试!");
				return;
			}
			jsonParserManager.parserBrandPicDetailResult(str, listener);
		}
	});
}
/**
 * 产品页面点击分类
 * @param listener
 */
public void getProductSort(String id,final CommunicationResultListener listener) {
	
	if (listener == null) {
		return;
	}
	
	String url = ServerAddress.GET_HotSortList+id;
	url=url.trim();
//	listener.setToken(System.currentTimeMillis());
	networkManager.requestGetString(url,new NetworkRequestListener() {
		@Override
		public void resultString(String str) {
			if (str == null || str.equals("")) {
				listener.resultListener(FAIL, "请求失败，请重试!");
				return;
			}
			jsonParserManager.parserProductResult(str, listener);
		}
	});
}
}
