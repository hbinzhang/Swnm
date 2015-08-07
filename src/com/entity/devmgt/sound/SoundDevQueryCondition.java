package com.entity.devmgt.sound;

import java.util.List;

import com.entity.CommonBean;

/**
 * 音频页面查询条件
 * 
 * @author maming 2015-4-9下午5:29:12
 * 
 */
public class SoundDevQueryCondition {
	public String id = null;
	public String ipAddress = null;
	public String devType = null;
	public String brench = null;
	private String filter = "1";
	private String mgtQuery = null;
	// 当前第几页
	public int offset = 1;

	// 每页多少条
	public int size = 10;

	public String searchUri;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	// public List<String> mgt = null;

	// public List<String> getMgt() {
	// return mgt;
	// }
	//
	// public void setMgt(List<String> mgt) {
	// if(!"".equals(mgt))
	// this.mgt = mgt;
	// }

	public String mgt = null;

	public String getMgt() {
		return mgt;
	}

	public void setMgt(String mgt) {
		this.mgt = mgt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		if (!"".equals(id))
			this.id = id;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		if (!"".equals(ipAddress))
			this.ipAddress = ipAddress;
	}

	public String getDevType() {
		return devType;
	}

	public void init() {
		if (this.filter.equals("1")) {
			this.ipAddress = null;
			this.devType = null;
		} else if (this.filter.equals("2")) {
			this.devType = null;
			this.id = null;
		} else {
			this.ipAddress = null;
			this.id = null;
		}
	}

	public void initMgt(List<CommonBean> mgts) {
		if (this.brench != null && this.brench.length() > 0) {
			if (this.mgt == null || this.mgt.length() == 0) {
				if (mgts == null || mgts.size() == 0) {
					this.mgtQuery = null;
				} else {
					StringBuffer sb = new StringBuffer("(");
					for (CommonBean commonBean : mgts) {
						sb.append(commonBean.getId()).append(",");
					}
					sb.deleteCharAt(sb.length() - 1);
					sb.append(")");
					this.mgtQuery = sb.toString();
				}
			}
			else {
				StringBuffer sb = new StringBuffer("(").append(this.mgt).append(")");
				this.mgtQuery = sb.toString();
			}
		}
		else {
			this.mgtQuery = null;
		}
	}

	public void setSearchUri(String searchUri) {
		this.searchUri = searchUri;
	}

	public String getSearchUri() {
		StringBuffer sb = new StringBuffer();
		sb.append("queryCondition.brench=")
				.append(this.brench == null ? "" : this.brench).append("&");
		sb.append("queryCondition.mgt=")
				.append(this.mgt == null ? "" : this.mgt).append("&");
		sb.append("queryCondition.filter=")
				.append(this.filter == null ? "1" : this.filter).append("&");
		if (this.filter.equals("1")) {
			sb.append("queryCondition.id=").append(
					this.id == null ? "" : this.id);
		} else if (this.filter.equals("2")) {
			sb.append("queryCondition.ipAddress=").append(
					this.ipAddress == null ? "" : this.ipAddress);
		} else {
			sb.append("queryCondition.devType=").append(
					this.devType == null ? "" : this.devType);
		}
		searchUri = sb.toString();
		return searchUri;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public String getFilter() {
		return filter;
	}

	public String getBrench() {
		return brench;
	}

	public void setBrench(String brench) {
		if (!"".equals(brench))
			this.brench = brench;
		else
			this.brench = null;
	}

	public void setDevType(String devType) {
		if (!"".equals(devType))
			this.devType = devType;
		else
			this.devType = null;
	}

	public void setMgtQuery(String mgtQuery) {
		if(!"".equals(mgtQuery))
		this.mgtQuery = mgtQuery;
		else
			this.mgtQuery = null;
	}

	public String getMgtQuery() {
		return mgtQuery;
	}
}
