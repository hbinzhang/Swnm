package com.entity.videomonitor;

import java.math.BigDecimal;

public class TVmIpcDTO {
	
	private Integer cordon;
	
	private Integer highTossAct;
	
	private Integer flotage;
	
	private Integer travelToTrack;
	
    private String ipcid;
 
    private String userid;
 
    private TVmManufacturer vendor;
 
    private TVmNvr nvr;
 
    private String ip;
 
    private BigDecimal port;
 
    private String netmask;
 
    private String gateway;
 
    private String username;
 
    private String password;
 
    private String devname;
 
    private String devfriendlyname;
 
    private String managementagency;
 
    private String ipclongitude;
 
    private String ipclatitude;
 
    private String version;
 
    private String branch;
    
    private String devtype;
    
    private Integer point;
    
    
    
    public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	private int ipcFault;
    
    
    public int getIpcFault() {
		return ipcFault;
	}

	public void setIpcFault(int ipcFault) {
		this.ipcFault = ipcFault;
	}

    public String getDevtype() {
		return devtype;
	}

	public void setDevtype(String devtype) {
		this.devtype = devtype;
	}
 
    public String getIpcid() {
        return ipcid;
    }
 
    public void setIpcid(String ipcid) {
        this.ipcid = ipcid;
    }
 
    public String getUserid() {
        return userid;
    }
 
    public void setUserid(String userid) {
        this.userid = userid;
    }
 
    public TVmManufacturer getVendor() {
        return vendor;
    }
 
    public void setVendor(TVmManufacturer vendor) {
        this.vendor = vendor;
    }
 
    public TVmNvr getNvr() {
        return nvr;
    }
 
    public void setNvr(TVmNvr nvr) {
        this.nvr = nvr;
    }
 
    public String getIp() {
        return ip;
    }
 
    public void setIp(String ip) {
        this.ip = ip;
    }
 
    public BigDecimal getPort() {
        return port;
    }
 
    public void setPort(BigDecimal port) {
        this.port = port;
    }
 
    public String getNetmask() {
        return netmask;
    }
 
    public void setNetmask(String netmask) {
        this.netmask = netmask;
    }
 
    public String getGateway() {
        return gateway;
    }
 
    public void setGateway(String gateway) {
        this.gateway = gateway;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    public String getDevname() {
        return devname;
    }
    public void setDevname(String devname) {
        this.devname = devname;
    }
    public String getDevfriendlyname() {
        return devfriendlyname;
    }
    public void setDevfriendlyname(String devfriendlyname) {
        this.devfriendlyname = devfriendlyname;
    }
    public String getManagementagency() {
        return managementagency;
    }
    public void setManagementagency(String managementagency) {
        this.managementagency = managementagency;
    }
    public String getIpclongitude() {
        return ipclongitude;
    }
    public void setIpclongitude(String ipclongitude) {
        this.ipclongitude = ipclongitude;
    }
    public String getIpclatitude() {
        return ipclatitude;
    }
    public void setIpclatitude(String ipclatitude) {
        this.ipclatitude = ipclatitude;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public String getBranch() {
        return branch;
    }
    public void setBranch(String branch) {
        this.branch = branch;
    }

	public Integer getCordon() {
		return cordon;
	}

	public void setCordon(Integer cordon) {
		this.cordon = cordon;
	}

	public Integer getHighTossAct() {
		return highTossAct;
	}

	public void setHighTossAct(Integer highTossAct) {
		this.highTossAct = highTossAct;
	}

	public Integer getFlotage() {
		return flotage;
	}

	public void setFlotage(Integer flotage) {
		this.flotage = flotage;
	}

	public Integer getTravelToTrack() {
		return travelToTrack;
	}

	public void setTravelToTrack(Integer travelToTrack) {
		this.travelToTrack = travelToTrack;
	}
}