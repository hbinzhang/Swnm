package com.util.video;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.entity.videomonitor.TVmIpc;
import com.entity.videomonitor.TVmManufacturer;
import com.entity.videomonitor.TVmNvr;

public class IpcConvertUtil {

	public static TVmIpc getTVmIpc(List<Object> linked) throws Exception{

		TVmIpc ipcBean = new TVmIpc();
		int size = linked.size();
		
		if (linked.get(0) != null) {
			String ipcid = (String) linked.get(0);
			ipcid = ipcid.trim();
			if (!ipcid.isEmpty())
				ipcBean.setIpcid(ipcid);
		}
		if(size>1){
			if(linked.get(1)!=null){
				
				ipcBean.setUserid(((String) linked.get(1)).trim());
				
			}
		}
		if(size>2){
			if (linked.get(2) != null) {
				String vendorid = (String) linked.get(2);
				vendorid = vendorid.trim();
				TVmManufacturer v = new TVmManufacturer();
				v.setVendorid(vendorid);
				ipcBean.setVendor(v);
			}
		}
		if(size>3){
			if(linked.get(3)!=null){
				String nvrid = (String) linked.get(3);
				nvrid = nvrid.trim();
				TVmNvr nvr = new TVmNvr();
				nvr.setNvrid(nvrid);
				ipcBean.setNvr(nvr);
			}
		}
		if(size>4){
			if(linked.get(4)!=null){
				ipcBean.setIp(((String) linked.get(4)).trim());
			}
		}
		if(size>5){
			if(linked.get(5)!=null){
				
				String port = (String) linked.get(5);
				port = port.trim();
				if (!port.isEmpty())
					ipcBean.setPort(new BigDecimal(port));
			}
		}
		if(size>6){
			if(linked.get(6)!=null){
				ipcBean.setNetmask(((String) linked.get(6)).trim());
			}
		}
		if(size>7){
			if(linked.get(7)!=null){
				ipcBean.setGateway(((String) linked.get(7)).trim());
			}
		}
		if(size>8){
			if(linked.get(8)!=null){
				ipcBean.setUsername(((String) linked.get(8)).trim());
			}
		}
		if(size>9){
			if(linked.get(9)!=null){
				ipcBean.setPassword(((String) linked.get(9)).trim());
			}
		}
		if(size>10){
			if(linked.get(10)!=null){
				ipcBean.setDevname(((String) linked.get(10)).trim());
			}
		}
		if(size>11){
			if(linked.get(11)!=null){
				ipcBean.setDevfriendlyname(((String) linked.get(11)).trim());
			}
		}
		if(size>12){
			if(linked.get(12)!=null){
				ipcBean.setDevtype(((String) linked.get(12)).trim());
			}
		}
		if(size>13){
			if(linked.get(13)!=null){
				ipcBean.setManagementagency(((String) linked.get(13)).trim());
			}
		}
		if(size>14){
			if(linked.get(14)!=null){
				ipcBean.setIpclongitude(((String) linked.get(14)).trim());
			}
		}
		if(size>15){
			if(linked.get(15)!=null){
				ipcBean.setIpclatitude(((String) linked.get(15)).trim());
			}
		}
		if(size>16){
			if(linked.get(16)!=null){
				ipcBean.setVersion(((String) linked.get(16)).trim());
			}
		}
		if(size>17){
			if(linked.get(17)!=null){
				ipcBean.setBranch(((String) linked.get(17)).trim());
			}
		}
		if(size>18){
			if(linked.get(18)!=null){
				String ipcfault = (String) linked.get(18);
				ipcfault = ipcfault.trim();
				if (!ipcfault.isEmpty())
				ipcBean.setIpcFault(Integer.valueOf(ipcfault));
			}
		}
		return ipcBean;
	}

	public static List<TVmIpc> getTVmIpcs(Map<String, List<Object>> ipcs) throws Exception{

		List<TVmIpc> ipcList = new LinkedList<TVmIpc>();
		for (String key : ipcs.keySet()) {

			List linked = (LinkedList) ipcs.get(key);
			int size = linked.size();
			TVmIpc ipcBean = new TVmIpc();
			if (linked.get(0) != null) {
				String ipcid = (String) linked.get(0);
				ipcid = ipcid.trim();
				if (!ipcid.isEmpty())
					ipcBean.setIpcid(ipcid);
			}
			if(size>1){
				if(linked.get(1)!=null){
					
					ipcBean.setUserid(((String) linked.get(1)).trim());
					
				}
			}
			if(size>2){
				if (linked.get(2) != null) {
					String vendorid = (String) linked.get(2);
					vendorid = vendorid.trim();
					TVmManufacturer v = new TVmManufacturer();
					v.setVendorid(vendorid);
					ipcBean.setVendor(v);
				}
			}
			if(size>3){
				if(linked.get(3)!=null){
					String nvrid = (String) linked.get(3);
					nvrid = nvrid.trim();
					TVmNvr nvr = new TVmNvr();
					nvr.setNvrid(nvrid);
					ipcBean.setNvr(nvr);
				}
			}
			if(size>4){
				if(linked.get(4)!=null){
					ipcBean.setIp(((String) linked.get(4)).trim());
				}
			}
			if(size>5){
				if(linked.get(5)!=null){
					
					String port = (String) linked.get(5);
					port = port.trim();
					if (!port.isEmpty())
						ipcBean.setPort(new BigDecimal(port));
				}
			}
			if(size>6){
				if(linked.get(6)!=null){
					ipcBean.setNetmask(((String) linked.get(6)).trim());
				}
			}
			if(size>7){
				if(linked.get(7)!=null){
					ipcBean.setGateway(((String) linked.get(7)).trim());
				}
			}
			if(size>8){
				if(linked.get(8)!=null){
					ipcBean.setUsername(((String) linked.get(8)).trim());
				}
			}
			if(size>9){
				if(linked.get(9)!=null){
					ipcBean.setPassword(((String) linked.get(9)).trim());
				}
			}
			if(size>10){
				if(linked.get(10)!=null){
					ipcBean.setDevname(((String) linked.get(10)).trim());
				}
			}
			if(size>11){
				if(linked.get(11)!=null){
					ipcBean.setDevfriendlyname(((String) linked.get(11)).trim());
				}
			}
			if(size>12){
				if(linked.get(12)!=null){
					ipcBean.setDevtype(((String) linked.get(12)).trim());
				}
			}
			if(size>13){
				if(linked.get(13)!=null){
					ipcBean.setManagementagency(((String) linked.get(13)).trim());
				}
			}
			if(size>14){
				if(linked.get(14)!=null){
					ipcBean.setIpclongitude(((String) linked.get(14)).trim());
				}
			}
			if(size>15){
				if(linked.get(15)!=null){
					ipcBean.setIpclatitude(((String) linked.get(15)).trim());
				}
			}
			if(size>16){
				if(linked.get(16)!=null){
					ipcBean.setVersion(((String) linked.get(16)).trim());
				}
			}
			if(size>17){
				if(linked.get(17)!=null){
					ipcBean.setBranch(((String) linked.get(17)).trim());
				}
			}
			if(size>18){
				if(linked.get(18)!=null){
					String ipcfault = (String) linked.get(18);
					ipcfault = ipcfault.trim();
					if (!ipcfault.isEmpty())
					ipcBean.setIpcFault(Integer.valueOf(ipcfault));
				}
			}

			ipcList.add(ipcBean);
		}

		return ipcList;
	}
}
