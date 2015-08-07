package com.util.fence;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.entity.efence.FenceBean;
import com.entity.zone.ZoneBean;
import com.nsbd.fence.FenceInfo;
import com.nsbd.zone.ZoneInfo;

public class FenceConvertUtil {

	public static FenceInfo localToRemoteFence(FenceBean fenceBean,
			FenceInfo fenceInfo) {

		fenceInfo.setBaseInfo(fenceBean.getHostID(), fenceBean.getFenceType(),
				fenceBean.getIp(), fenceBean.getPort());
		fenceInfo.setdevInfo(fenceBean.getDevID(), fenceBean.getFenceName());
		fenceInfo.setInstitution(fenceBean.getSubComID(), fenceBean.getMntMentID());

		return fenceInfo;
	}
	
	public static List<FenceBean> getFences(Map<Integer,List<Object>> map)throws Exception{
		
		List<FenceBean> fences = new ArrayList<FenceBean>();
		
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
		
		for(Integer key:map.keySet()){
				
				List linked = (LinkedList)map.get(key);
				
				int size = linked.size();
				
				FenceBean fenceBean = new FenceBean();
				
				if(linked.get(0)!=null){
					
					fenceBean.setHostID(((String)linked.get(0)).trim());
				}
				
				if(linked.get(1)!=null){
					String vendorID =(String)linked.get(1);
					vendorID = vendorID.trim();
					if(!vendorID.isEmpty())
					fenceBean.setVendorID(vendorID);
				}
				if(linked.get(2)!=null){
					String fenceType =(String)linked.get(2);
					fenceType = fenceType.trim();
					if(!fenceType.isEmpty())
					fenceBean.setFenceType(Integer.valueOf(fenceType));
					
				}
				if(linked.get(3)!=null){
					
					fenceBean.setFenceName(((String)linked.get(3)).trim());
					
				}
				if(linked.get(4)!=null){
					
					fenceBean.setIp(((String)linked.get(4)).trim());
					
					
				}
				if(linked.get(5)!=null){
					String port =(String)linked.get(5);
					port = port.trim();
					if(!port.isEmpty())
					fenceBean.setPort(Integer.valueOf(port));
					
				}
				/*if(linked.get(6)!=null){
					String fenceStatus =(String)linked.get(6);
					fenceStatus = fenceStatus.trim();
					if(!fenceStatus.isEmpty())
					fenceBean.setFenceStatus(Integer.valueOf(fenceStatus));
				}*/
				if(linked.get(6)!=null){
					
					String installTime =(String)linked.get(6);
					installTime = installTime.trim();
					if(!installTime.isEmpty())
					fenceBean.setInstallTime(installTime);
					 
				}
				if(linked.get(7)!=null){
					
					fenceBean.setMntMentID(((String)linked.get(7)).trim());
					
				}
				if(linked.get(8)!=null){
					
					fenceBean.setSubComID(((String)linked.get(8)).trim());
					
				}
				if(linked.get(9)!=null){
					
					fenceBean.setHardwareVer(((String)linked.get(9)).trim());
					
				}
				if(linked.get(10)!=null){
					
					fenceBean.setSorfwareVer(((String)linked.get(10)).trim());
					
				}
				if(linked.get(11)!=null){
					
					fenceBean.setRemarks(((String)linked.get(11)).trim());
					
				}
				if(linked.get(12)!=null){
					
					fenceBean.setEfLongitude(((String)linked.get(12)).trim());
					
				}
				if(linked.get(13)!=null){
					
					fenceBean.setEfLatitude(((String)linked.get(13)).trim());
					
				}
				if(size == 15){
					
					if(linked.get(14)!=null){
						String iconID = (String)linked.get(14);
						iconID = iconID.trim();
						if(!iconID.isEmpty())
							fenceBean.setIconID(Integer.valueOf(iconID));
						
					}
				}
				/*if(linked.get(16)!=null){
					
					fenceBean.setDevID(((String)linked.get(16)).trim());
					
				}*/
				
				fences.add(fenceBean);
		}
		
		
		return fences;
	}
	
	public static FenceBean getFence(List<Object> linked)throws Exception{
		
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
		int size = linked.size();
		
		FenceBean fenceBean = new FenceBean();
		
		if(linked.get(0)!=null){
			
			fenceBean.setHostID(((String)linked.get(0)).trim());
		}
		
		if(linked.get(1)!=null){
			String vendorID =(String)linked.get(1);
			vendorID = vendorID.trim();
			if(!vendorID.isEmpty())
			fenceBean.setVendorID(vendorID);
		}
		if(linked.get(2)!=null){
			String fenceType =(String)linked.get(2);
			fenceType = fenceType.trim();
			if(!fenceType.isEmpty())
			fenceBean.setFenceType(Integer.valueOf(fenceType));
			
		}
		if(linked.get(3)!=null){
			
			fenceBean.setFenceName(((String)linked.get(3)).trim());
			
		}
		if(linked.get(4)!=null){
			
			fenceBean.setIp(((String)linked.get(4)).trim());
			
			
		}
		if(linked.get(5)!=null){
			String port =(String)linked.get(5);
			port = port.trim();
			if(!port.isEmpty())
			fenceBean.setPort(Integer.valueOf(port));
			
		}
		/*if(linked.get(6)!=null){
			String fenceStatus =(String)linked.get(6);
			fenceStatus = fenceStatus.trim();
			if(!fenceStatus.isEmpty())
			fenceBean.setFenceStatus(Integer.valueOf(fenceStatus));
		}*/
		if(linked.get(6)!=null){
			
			String installTime =(String)linked.get(6);
			installTime = installTime.trim();
			if(!installTime.isEmpty())
			fenceBean.setInstallTime(installTime);
			 
		}
		if(linked.get(7)!=null){
			
			fenceBean.setMntMentID(((String)linked.get(7)).trim());
			
		}
		if(linked.get(8)!=null){
			
			fenceBean.setSubComID(((String)linked.get(8)).trim());
			
		}
		if(linked.get(9)!=null){
			
			fenceBean.setHardwareVer(((String)linked.get(9)).trim());
			
		}
		if(linked.get(10)!=null){
			
			fenceBean.setSorfwareVer(((String)linked.get(10)).trim());
			
		}
		if(linked.get(11)!=null){
			
			fenceBean.setRemarks(((String)linked.get(11)).trim());
			
		}
		if(linked.get(12)!=null){
			
			fenceBean.setEfLongitude(((String)linked.get(12)).trim());
			
		}
		if(linked.get(13)!=null){
			
			fenceBean.setEfLatitude(((String)linked.get(13)).trim());
			
		}
		if(size == 15){
			if(linked.get(14)!=null){
				String iconID = (String)linked.get(14);
				iconID = iconID.trim();
				if(!iconID.isEmpty())
					fenceBean.setIconID(Integer.valueOf(iconID));
			}
		}
		/*if(linked.get(16)!=null){
			
			fenceBean.setDevID(((String)linked.get(16)).trim());
			
		}*/
		return fenceBean;
	}

}
