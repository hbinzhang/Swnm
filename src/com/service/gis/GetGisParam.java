package com.service.gis;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.entity.CommonBean;
import com.entity.authmgt.Session;
import com.entity.gis.BranchInfo;
import com.service.authmgt.IOrganizationManagerService;
import com.service.authmgt.impl.OrganizationManagerServiceImp;

public class GetGisParam {

	private IOrganizationManagerService orgService;//

	public BranchInfo queryBranchRangeByUserID(HttpServletRequest request) {
		try {
			Session session = (Session) request.getSession().getAttribute(
					"session");
			String userId = session.getId();
			String orgId = session.getOrganizationId();

			WebApplicationContext ctx = WebApplicationContextUtils
					.getWebApplicationContext(request.getSession()
							.getServletContext());
			// ctx.getBean("OrganizationManagerServiceImp");
			orgService = (IOrganizationManagerService) ctx
					.getBean("OrganizationManagerService");
			
			String orgLev = session.getLev();
			// if(orgLev)
			/*
			 * 如果是管理处，直接得到管辖范围坐标 如果是分公司，所有管辖坐标里，左下角是最小的，右上角是最大的
			 * 如果是总公司，就装载全部中线地图
			 */

			// 查询结果映射，中线地图
			BranchInfo branchInfo = new BranchInfo();
			branchInfo.setXmin(105.374747);
			branchInfo.setYmin(30.903097);
			branchInfo.setXmax(125.053125);
			branchInfo.setYmax(41.038121);
			//startPoint = new mapInitParam(105.374747, 30.903097, 125.053125, 41.038121);//中线地图
			
			return branchInfo;
		} catch (Exception e) {
			return null;
		}
	}
	
}
