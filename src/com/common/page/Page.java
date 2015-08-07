package com.common.page;

import java.util.List;


/**
 * Title: developerportal-guangdong<br>
 * Description: <br>
 * 
 * @author: mark Create DateTime: Jul 30, 2012 4:10:59 PM <br>
 */

public class Page<T> {
	private static final int UNKNOW = -1;
	
	private static final int UNLIMITED = Integer.MAX_VALUE;
	// 默认每页显示数量
	private static final int DEFAULT_size = 10;
	// 默认当前页码
	private static final int DEFAULT_PAGE_NO = 0;
	// 默认显示页码数量
	private static final int DEFAULT_MAX_LINKED_PAGES = 4;
	// 默认总页码数量
	private static final int DEFAULT_PAGE_NUM = 1;

	// 总记录数
	private long total = UNLIMITED;
	// 总页码数
	private int pages;
	// 每页显示记录数
	private int size = DEFAULT_size;
	// 当前第几页
	private int offset = DEFAULT_PAGE_NO;
	// 页码输出从第几页开始
	private int firstLinkedPage = DEFAULT_PAGE_NO;
	// 页码输出到第几页结束
	private int lastLinkedPage = DEFAULT_PAGE_NO;
	
	private String orderBy;
	
	private String order;
	
	//查询条件
	private Object objCondition;
	
	//查询结果集
	private List<T> datas;
	
	public Page(){
		initPage(UNKNOW,UNKNOW,UNKNOW,UNKNOW,UNKNOW);
	}
	
	public Page(int totalCount){
		initPage(UNKNOW,UNKNOW,totalCount,UNKNOW,UNKNOW);
	}
	
	public Page(int pageNo , int size){
		initPage(pageNo,size,UNKNOW,UNKNOW,UNKNOW);
	}
	
	public Page(int pageNo , int size , long totalCount){
		initPage(pageNo,size,totalCount,UNKNOW,UNKNOW);
	}
	
	public Page(int pageNo , int size , int totalPage){
		initPage(pageNo,size,UNKNOW,totalPage,UNKNOW);
	}
	
	public Page(int pageNo , int size , long totalCount ,int totalPage  ){
		initPage(pageNo,size,UNKNOW,totalPage ,UNKNOW);
	}
	
	public Page(int pageNo , int size , int totalPage , int maxLinkedPages ){
		initPage(pageNo,size,UNKNOW,totalPage,maxLinkedPages);
	}
	
	public Page(int pageNo , int size , long totalCount ,int totalPage , int maxLinkedPages ){
		initPage(pageNo,size,totalCount,totalPage,maxLinkedPages);
	}
	
	/**
	 * 
	 * @param pageNo
	 * @param size
	 * @param totalCount
	 * @param totalPage
	 * @param maxLinkedPages: 显示的页码数量
	 */
	private void initPage(	int offset,
					  		int size,
					  		long total,
					  		int pages,
					  		int maxLinkedPages){
		
		if(offset != UNKNOW){
			this.offset = offset;
		}
		
		if(size != UNKNOW){
			this.size = size;
		}
		
		if(total != UNKNOW){
			this.total = total;
			this.pages = _getTotalPage();
		}
		
		if(pages != UNKNOW){
			this.total = pages * size;
			this.pages = pages;
		}
		
		if(maxLinkedPages != UNKNOW){
			this.firstLinkedPage = _getFirstLinkedPage(this.offset,this.pages,maxLinkedPages);
			this.lastLinkedPage = _getLastLinkedPage(this.offset, this.pages, maxLinkedPages);
		}else{
			this.firstLinkedPage = _getFirstLinkedPage(this.offset,this.pages);
			this.lastLinkedPage = _getLastLinkedPage(this.offset, this.pages);
		}
		
	}
	
	/**
	 * 通过totalCount计算总页数
	 * @return
	 */
	private int _getTotalPage() {
		if (total != 0 && total != UNLIMITED) {
			return (int)( total % size == 0 ? total / size : total / size + 1);
		} else {
			return DEFAULT_PAGE_NUM;
		}
	}
	
	private  int _getFirstLinkedPage(int pageNo, int totalPage, int maxLinkedPages) {
        return Math.max(0, Math.min(totalPage - maxLinkedPages, pageNo - maxLinkedPages / 2))+1;
    }

	private  int _getFirstLinkedPage(int pageNo, int totalPage) {
    	 return _getFirstLinkedPage( pageNo,  totalPage,  DEFAULT_MAX_LINKED_PAGES);
    }
    
	private int _getLastLinkedPage(int pageNo, int totalPage, int maxLinkedPages) {
        return Math.min(_getFirstLinkedPage(pageNo, totalPage, maxLinkedPages) + maxLinkedPages - 1, totalPage - 1)+1;
    }

	private int _getLastLinkedPage(int pageNo, int totalPage) {
		return _getLastLinkedPage(pageNo,totalPage,DEFAULT_MAX_LINKED_PAGES);
    }

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public int getFirstLinkedPage() {
		return firstLinkedPage;
	}

	public void setFirstLinkedPage(int firstLinkedPage) {
		this.firstLinkedPage = firstLinkedPage;
	}

	public int getLastLinkedPage() {
		return lastLinkedPage;
	}

	public void setLastLinkedPage(int lastLinkedPage) {
		this.lastLinkedPage = lastLinkedPage;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Object getObjCondition() {
		return objCondition;
	}

	public void setObjCondition(Object objCondition) {
		this.objCondition = objCondition;
	}

	public List<T> getDatas() {
		return datas;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}
}
