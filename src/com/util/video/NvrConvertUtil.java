package com.util.video;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.entity.videomonitor.TVmManufacturer;
import com.entity.videomonitor.TVmNvr;

public class NvrConvertUtil {

	public static TVmNvr getTVmNvr(List<Object> linked) throws Exception{

		TVmNvr nvrBean = new TVmNvr();
		int size = linked.size();
		
		if (linked.get(0) != null) {
			String nvrID = (String) linked.get(0);
			nvrID = nvrID.trim();
			if (!nvrID.isEmpty())
				nvrBean.setNvrid(nvrID);
		}
		if(size>1){
			if (linked.get(1) != null) {
				String vendorid = (String) linked.get(1);
				vendorid = vendorid.trim();
				TVmManufacturer v = new TVmManufacturer();
				v.setVendorid(vendorid);
				nvrBean.setVendor(v);
			}
		}
		if(size>2){
			if(linked.get(2)!=null){
				nvrBean.setIp(((String) linked.get(2)).trim());
			}
		}
		if(size>3){
			if(linked.get(3)!=null){
				
				String port = (String) linked.get(3);
				port = port.trim();
				if (!port.isEmpty())
					nvrBean.setPort(new BigDecimal(port));
			}
		}
		if(size>4){
			if(linked.get(4)!=null){
				nvrBean.setNetmask(((String) linked.get(4)).trim());
			}
		}
		if(size>5){
			if(linked.get(5)!=null){
				nvrBean.setGateway(((String) linked.get(5)).trim());
			}
		}
		if(size>6){
			if(linked.get(6)!=null){
				nvrBean.setUsername2(((String) linked.get(6)).trim());
			}
		}
		if(size>7){
			if(linked.get(7)!=null){
				nvrBean.setPassword(((String) linked.get(7)).trim());
			}
		}
		if(size>8){
			if(linked.get(8)!=null){
				nvrBean.setDevname(((String) linked.get(8)).trim());
			}
		}
		if(size>9){
			if(linked.get(9)!=null){
				nvrBean.setDevfriendlyname(((String) linked.get(9)).trim());
			}
		}
		if(size>10){
			if(linked.get(10)!=null){
				nvrBean.setDevtype(((String) linked.get(10)).trim());
			}
		}
		if(size>11){
			if(linked.get(11)!=null){
				String diskNum = (String) linked.get(3);
				diskNum = diskNum.trim();
				if (!diskNum.isEmpty())
					nvrBean.setDisknum(new BigDecimal(diskNum));
			}
		}
		if(size>12){
			if(linked.get(12)!=null){
				nvrBean.setManagementagency(((String) linked.get(12)).trim());
			}
		}
		if(size>13){
			if(linked.get(13)!=null){
				nvrBean.setVersion(((String) linked.get(13)).trim());
			}
		}
		if(size>14){
			if(linked.get(14)!=null){
				nvrBean.setBranch(((String) linked.get(14)).trim());
			}
		}
		if(size>15){
			if(linked.get(15)!=null){
				String nvrfault = (String) linked.get(15);
				nvrfault = nvrfault.trim();
				if (!nvrfault.isEmpty())
					nvrBean.setNvrFault(Integer.valueOf(nvrfault));
			}
		}
		return nvrBean;
	}

	public static List<TVmNvr> getTVmNvrs(Map<String, List<Object>> nvrs) throws Exception{

		List<TVmNvr> nvrList = new LinkedList<TVmNvr>();
		for (String key : nvrs.keySet()) {

			List linked = (LinkedList) nvrs.get(key);
			TVmNvr nvrBean = new TVmNvr();
			int size = linked.size();
			
			if (linked.get(0) != null) {
				String nvrID = (String) linked.get(0);
				nvrID = nvrID.trim();
				if (!nvrID.isEmpty())
					nvrBean.setNvrid(nvrID);
			}
			if(size>1){
				if (linked.get(1) != null) {
					String vendorid = (String) linked.get(1);
					vendorid = vendorid.trim();
					TVmManufacturer v = new TVmManufacturer();
					v.setVendorid(vendorid);
					nvrBean.setVendor(v);
				}
			}
			if(size>2){
				if(linked.get(2)!=null){
					nvrBean.setIp(((String) linked.get(2)).trim());
				}
			}
			if(size>3){
				if(linked.get(3)!=null){
					
					String port = (String) linked.get(3);
					port = port.trim();
					if (!port.isEmpty())
						nvrBean.setPort(new BigDecimal(port));
				}
			}
			if(size>4){
				if(linked.get(4)!=null){
					nvrBean.setNetmask(((String) linked.get(4)).trim());
				}
			}
			if(size>5){
				if(linked.get(5)!=null){
					nvrBean.setGateway(((String) linked.get(5)).trim());
				}
			}
			if(size>6){
				if(linked.get(6)!=null){
					nvrBean.setUsername2(((String) linked.get(6)).trim());
				}
			}
			if(size>7){
				if(linked.get(7)!=null){
					nvrBean.setPassword(((String) linked.get(7)).trim());
				}
			}
			if(size>8){
				if(linked.get(8)!=null){
					nvrBean.setDevname(((String) linked.get(8)).trim());
				}
			}
			if(size>9){
				if(linked.get(9)!=null){
					nvrBean.setDevfriendlyname(((String) linked.get(9)).trim());
				}
			}
			if(size>10){
				if(linked.get(10)!=null){
					nvrBean.setDevtype(((String) linked.get(10)).trim());
				}
			}
			if(size>11){
				if(linked.get(11)!=null){
					String diskNum = (String) linked.get(3);
					diskNum = diskNum.trim();
					if (!diskNum.isEmpty())
						nvrBean.setDisknum(new BigDecimal(diskNum));
				}
			}
			if(size>12){
				if(linked.get(12)!=null){
					nvrBean.setManagementagency(((String) linked.get(12)).trim());
				}
			}
			if(size>13){
				if(linked.get(13)!=null){
					nvrBean.setVersion(((String) linked.get(13)).trim());
				}
			}
			if(size>14){
				if(linked.get(14)!=null){
					nvrBean.setBranch(((String) linked.get(14)).trim());
				}
			}
			if(size>15){
				if(linked.get(15)!=null){
					String nvrfault = (String) linked.get(15);
					nvrfault = nvrfault.trim();
					if (!nvrfault.isEmpty())
						nvrBean.setNvrFault(Integer.valueOf(nvrfault));
				}
			}

			nvrList.add(nvrBean);
		}

		return nvrList;
	}
}
