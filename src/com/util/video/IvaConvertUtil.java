package com.util.video;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.entity.videomonitor.TVmIva;

public class IvaConvertUtil {

	public static TVmIva getTVmIva(List<Object> linked) throws Exception{

		TVmIva ivaBean = new TVmIva();
		int size = linked.size();
		
		if (linked.get(0) != null) {
			String ivaID = (String) linked.get(0);
			ivaID = ivaID.trim();
			if (!ivaID.isEmpty())
				ivaBean.setIvaID(ivaID);
		}
		if(size>1){
			if(linked.get(1)!=null){
				ivaBean.setIp(((String) linked.get(1)).trim());
			}
		}
		if(size>2){
			if(linked.get(2)!=null){
				
				String port = (String) linked.get(2);
				port = port.trim();
				if (!port.isEmpty())
					ivaBean.setPort(Integer.valueOf(port));
			}
		}
		if(size>3){
			if(linked.get(3)!=null){
				ivaBean.setManagementagency(((String) linked.get(3)).trim());
			}
		}
		if(size>4){
			if(linked.get(4)!=null){
				ivaBean.setName(((String) linked.get(4)).trim());
			}
		}
		if(size>5){
			if(linked.get(5)!=null){
				ivaBean.setBranch(((String) linked.get(5)).trim());
			}
		}
		return ivaBean;
	}

	public static List<TVmIva> getTVmIvas(Map<String, List<Object>> ivas) throws Exception{

		List<TVmIva> ivaList = new LinkedList<TVmIva>();
		for (String key : ivas.keySet()) {

			List linked = (LinkedList) ivas.get(key);
			TVmIva ivaBean = new TVmIva();
			int size = linked.size();
			
			if (linked.get(0) != null) {
				String ivaID = (String) linked.get(0);
				ivaID = ivaID.trim();
				if (!ivaID.isEmpty())
					ivaBean.setIvaID(ivaID);
			}
			if(size>1){
				if(linked.get(1)!=null){
					ivaBean.setIp(((String) linked.get(1)).trim());
				}
			}
			if(size>2){
				if(linked.get(2)!=null){
					
					String port = (String) linked.get(2);
					port = port.trim();
					if (!port.isEmpty())
						ivaBean.setPort(Integer.valueOf(port));
				}
			}
			if(size>3){
				if(linked.get(3)!=null){
					ivaBean.setManagementagency(((String) linked.get(3)).trim());
				}
			}
			if(size>4){
				if(linked.get(4)!=null){
					ivaBean.setName(((String) linked.get(4)).trim());
				}
			}
			if(size>5){
				if(linked.get(5)!=null){
					ivaBean.setBranch(((String) linked.get(5)).trim());
				}
			}
			ivaList.add(ivaBean);
		}

		return ivaList;
	}
}
