package com.actions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.common.page.Page;
import com.common.page.PageUtil;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class BaseAction extends ActionSupport implements ServletRequestAware,
		ServletResponseAware {
	public final static Log logger = LogFactory.getLog(BaseAction.class);
	private HttpServletRequest request;
	private HttpServletResponse response;

	/* params for Paginater */
	protected int pageSize = 10;
	protected int pageNow = 1; // 初始化为1,默认从第一页开始显示
    protected int totalPage = -1; // 总页数
	protected int maxLinkedPages = 10; //页面显示分页最大个数，默认为10
	 
	// protected int firstLinkedPage = 1; // 从第几页输出
	// protected int lastLinkedPage = 1; // 到第几页结束

	/**
	 * descpription : return json for ajax
	 * 
	 * @param jsonData
	 */
	public void returnJSON(String jsonData) {
		returnJSON(jsonData, "json");
	}
 
	/**
	 * descpription : return json for ajax
	 * 
	 * @param jsonData
	 */
	public void returnJSON(String jsonData, String dataType) {
		HttpServletResponse response = ServletActionContext.getResponse();
		if ("text".equals(dataType)) {
			response.setContentType("text/html;charset=UTF-8");

		} else {
			response.setContentType("application/json;charset=UTF-8");
		}
		response.setHeader("Charset", "UTF-8");
		PrintWriter out = null;
		logger.info(jsonData);
		try {
			out = response.getWriter();
		} catch (IOException e) {
			logger.error("",e);
		}
		out.write(jsonData);
		out.flush();
	}
 

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public static Log getLogger() {
		return logger;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNow() {
		return pageNow;
	}

	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getMaxLinkedPages() {
		return maxLinkedPages;
	}

	public void setMaxLinkedPages(int maxLinkedPages) {
		this.maxLinkedPages = maxLinkedPages;
	}

}
