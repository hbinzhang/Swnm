package com.util.zone;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.entity.linkagectl.IpcZoneMap;
import com.entity.linkagectl.SoundZoneMap;
import com.entity.zone.DefenceZoneMapBean;
import com.entity.zone.IntegrationZoneMapBean;
import com.entity.zone.PositionZoneMapBean;
import com.entity.zone.PulseZoneMapBean;
import com.entity.zone.ZoneBean;

public class ZoneConvertUtil {

	public static ZoneBean getZone(List<Object> linked) throws Exception{

		ZoneBean zoneBean = new ZoneBean();
		int size = linked.size();
		if (linked.get(0) != null) {
			String zoneID = (String) linked.get(0);
			zoneID = zoneID.trim();
			if (!zoneID.isEmpty())
				zoneBean.setZoneID(Integer.valueOf(zoneID));
		}
		if(linked.get(1)!=null){
			
			zoneBean.setZoneName(((String) linked.get(1)).trim());
			
		}
		if (linked.get(2) != null) {
			String fenceType = (String) linked.get(2);
			fenceType = fenceType.trim();
			if (!fenceType.isEmpty())
				zoneBean.setFenceType(Integer.valueOf(fenceType));
		}
		if(linked.get(3)!=null){
			
			zoneBean.setMgtID(((String) linked.get(3)).trim());
			
		}

/*		if (linked.get(4) != null) {
			String status = (String) linked.get(4);
			status = status.trim();
			if (!status.isEmpty())
				zoneBean.setStatus(Integer.valueOf(status));
		}*/
		if(linked.get(4)!=null){
			
			zoneBean.setOrientation(((String) linked.get(4)).trim());
			
		}
		if(linked.get(5)!=null){
			
			zoneBean.setStartLon(((String) linked.get(5)).trim());
			
		}
		if(linked.get(6)!=null){
			
			zoneBean.setStartLat(((String) linked.get(6)).trim());
			
		}
		if(linked.get(7)!=null){
			
			zoneBean.setEndLon(((String) linked.get(7)).trim());
			
		}
		if(linked.get(8)!=null){
			
			zoneBean.setEndLat(((String) linked.get(8)).trim());
			
		}
		if(linked.get(9)!=null){
			
			zoneBean.setBranchID(((String) linked.get(9)).trim());
			
		}
		if(size == 11){
			
			if(linked.get(10)!=null){
				
				zoneBean.setInfo(((String) linked.get(10)).trim());
				
			}
		}
		return zoneBean;
	}

	public static List<ZoneBean> getZones(Map<Integer, List<Object>> zones) throws Exception{

		List<ZoneBean> zoneList = new LinkedList<ZoneBean>();
		for (Integer key : zones.keySet()) {

			List linked = (LinkedList) zones.get(key);
			int size = linked.size();
			ZoneBean zoneBean = new ZoneBean();

			if (linked.get(0) != null) {
				String zoneID = (String) linked.get(0);
				zoneID = zoneID.trim();
				if (!zoneID.isEmpty())
					zoneBean.setZoneID(Integer.valueOf(zoneID));
			}
			if(linked.get(1)!=null){
				
				zoneBean.setZoneName(((String) linked.get(1)).trim());
				
			}
			if (linked.get(2) != null) {
				String fenceType = (String) linked.get(2);
				fenceType = fenceType.trim();
				if (!fenceType.isEmpty())
					zoneBean.setFenceType(Integer.valueOf(fenceType));
			}
			if(linked.get(3)!=null){
				
				zoneBean.setMgtID(((String) linked.get(3)).trim());
				
			}

			/*if (linked.get(4) != null) {
				String status = (String) linked.get(4);
				status = status.trim();
				if (!status.isEmpty())
					zoneBean.setStatus(Integer.valueOf(status));
			}*/
			if(linked.get(4)!=null){
				
				zoneBean.setOrientation(((String) linked.get(4)).trim());
				
			}
			if(linked.get(5)!=null){
				
				zoneBean.setStartLon(((String) linked.get(5)).trim());
				
			}
			if(linked.get(6)!=null){
				
				zoneBean.setStartLat(((String) linked.get(6)).trim());
				
			}
			if(linked.get(7)!=null){
				
				zoneBean.setEndLon(((String) linked.get(7)).trim());
				
			}
			if(linked.get(8)!=null){
				
				zoneBean.setEndLat(((String) linked.get(8)).trim());
				
			}
			if(linked.get(9)!=null){
				
				zoneBean.setBranchID(((String) linked.get(9)).trim());
				
			}
			if(size == 11){
				
				if(linked.get(10)!=null){
					
					zoneBean.setInfo(((String) linked.get(10)).trim());
					
				}
			}

			zoneList.add(zoneBean);
		}

		return zoneList;
	}

	public static DefenceZoneMapBean getDefenceZoneMap(List<Object> linked) throws Exception{

		DefenceZoneMapBean defenceZoneMapBean = new DefenceZoneMapBean();
		int size = linked.size();
		if (linked.get(0) != null) {
			String zoneID = (String) linked.get(0);
			zoneID = zoneID.trim();
			if (!zoneID.isEmpty())
				defenceZoneMapBean.setZoneID(Integer.valueOf(zoneID));
		}

		if(linked.get(1)!=null){
			
			defenceZoneMapBean.setHostID(((String) linked.get(1)).trim());
			
			
		}
		if(linked.get(2)!=null){
			
			defenceZoneMapBean.setInnerZoneID(((String) linked.get(2)).trim());
			
		}
		if(size == 4){
			if(linked.get(3)!=null){
				
				defenceZoneMapBean.setInfo(((String) linked.get(3)).trim());
				
			}
		}
		
		return defenceZoneMapBean;
	}

	public static List<DefenceZoneMapBean> getDefenceZoneMaps(
			List<List<Object>> defenceList) throws Exception{

		List<DefenceZoneMapBean> defenceZoneMapBeanList = new LinkedList<DefenceZoneMapBean>();

		/**
		 * List<List<Object>>表数据
		 * List<Object>为行数据
		 */
		for (List<Object> obj : defenceList) {

			List linked = (LinkedList) obj;
			int size = linked.size();
			DefenceZoneMapBean defenceZoneMapBean = new DefenceZoneMapBean();
			if (linked.get(0) != null) {
				String zoneID = (String) linked.get(0);
				zoneID = zoneID.trim();
				if (!zoneID.isEmpty())
					defenceZoneMapBean.setZoneID(Integer.valueOf(zoneID));
			}
			if(linked.get(1)!=null){
				
				defenceZoneMapBean.setHostID(((String) linked.get(1)).trim());
				
				
			}
			if(linked.get(2)!=null){
				
				defenceZoneMapBean.setInnerZoneID(((String) linked.get(2)).trim());
				
			}
			if(size==4){
				if(linked.get(3)!=null){
					
					defenceZoneMapBean.setInfo(((String) linked.get(3)).trim());
					
				}
			}

			defenceZoneMapBeanList.add(defenceZoneMapBean);
		}

		return defenceZoneMapBeanList;
	}

	public static List<PositionZoneMapBean> getPositionZoneMaps(
			List<List<Object>> positionList) throws Exception{
		List<PositionZoneMapBean> positionZoneMapBeanList = new LinkedList<PositionZoneMapBean>();

		/**
		 * List<List<Object>>表数据
		 * List<Object>为行数据
		 */
		for (List<Object> obj : positionList) {

			List linked = (LinkedList) obj;
			int size = linked.size();
			PositionZoneMapBean positionZoneMapBean = new PositionZoneMapBean();

			if (linked.get(0) != null) {
				String zoneID = (String) linked.get(0);
				zoneID = zoneID.trim();
				if (!zoneID.isEmpty())
					positionZoneMapBean.setZoneID(Integer.valueOf(zoneID));
			}
			if(linked.get(1)!=null){
				
				positionZoneMapBean.setHostID(((String) linked.get(1)).trim());
				
			}
			if (linked.get(2) != null) {
				String startPoint = (String) linked.get(2);
				startPoint = startPoint.trim();
				if (!startPoint.isEmpty())
					positionZoneMapBean.setStartPoint(Integer.valueOf(startPoint));
			}
			if (linked.get(3) != null) {
				String endPoint = (String) linked.get(3);
				endPoint = endPoint.trim();
				if (!endPoint.isEmpty())
					positionZoneMapBean.setEndPoint(Integer.valueOf(endPoint));
			}
			if (linked.get(4) != null) {
				String innerZoneID = (String) linked.get(4);
				innerZoneID = innerZoneID.trim();
				if (!innerZoneID.isEmpty())
					positionZoneMapBean.setInnerZoneID(Integer.valueOf(innerZoneID));
			}
			if (linked.get(5) != null) {
				String chanID = (String) linked.get(5);
				chanID = chanID.trim();
				if (!chanID.isEmpty())
					positionZoneMapBean.setChanID(Integer.valueOf(chanID));
			}
			if(size==7){
				
				if(linked.get(6)!=null){
					
					positionZoneMapBean.setInfo(((String) linked.get(6)).trim());
					
				}
			}

			positionZoneMapBeanList.add(positionZoneMapBean);
		}

		return positionZoneMapBeanList;
	}

	public static PositionZoneMapBean getPositionZoneMap(List<Object> linked) throws Exception{
		
		PositionZoneMapBean positionZoneMapBean = new PositionZoneMapBean();
		int size = linked.size();

		if (linked.get(0) != null) {
			String zoneID = (String) linked.get(0);
			zoneID = zoneID.trim();
			if (!zoneID.isEmpty())
				positionZoneMapBean.setZoneID(Integer.valueOf(zoneID));
		}
		if(linked.get(1)!=null){
			
			positionZoneMapBean.setHostID(((String) linked.get(1)).trim());
			
		}
		if (linked.get(2) != null) {
			String startPoint = (String) linked.get(2);
			startPoint = startPoint.trim();
			if (!startPoint.isEmpty())
				positionZoneMapBean.setStartPoint(Integer.valueOf(startPoint));
		}
		if (linked.get(3) != null) {
			String endPoint = (String) linked.get(3);
			endPoint = endPoint.trim();
			if (!endPoint.isEmpty())
				positionZoneMapBean.setEndPoint(Integer.valueOf(endPoint));
		}
		if (linked.get(4) != null) {
			String innerZoneID = (String) linked.get(4);
			innerZoneID = innerZoneID.trim();
			if (!innerZoneID.isEmpty())
				positionZoneMapBean.setInnerZoneID(Integer.valueOf(innerZoneID));
		}
		if (linked.get(5) != null) {
			String chanID = (String) linked.get(5);
			chanID = chanID.trim();
			if (!chanID.isEmpty())
				positionZoneMapBean.setChanID(Integer.valueOf(chanID));
		}
		if(size==7){
			
			if(linked.get(6)!=null){
				
				positionZoneMapBean.setInfo(((String) linked.get(6)).trim());
				
			}
		}
		return positionZoneMapBean;
	}

	public static List<IntegrationZoneMapBean> getIntegrationZoneMaps(
			List<List<Object>> integrationList) throws Exception{
		List<IntegrationZoneMapBean> integrationZoneMapBeanList = new LinkedList<IntegrationZoneMapBean>();

		/**
		 * List<List<Object>>表数据
		 * List<Object>为行数据
		 */
		for (List<Object> obj : integrationList) {

			List linked = (LinkedList) obj;
			int size = linked.size();
			IntegrationZoneMapBean integrationZoneMapBean = new IntegrationZoneMapBean();

			if (linked.get(0) != null) {
				String zoneID = (String) linked.get(0);
				zoneID = zoneID.trim();
				if (!zoneID.isEmpty())
					integrationZoneMapBean.setZoneID(Integer.valueOf(zoneID));
			}
			if(linked.get(1)!=null){
				
				integrationZoneMapBean.setHostID(((String) linked.get(1)).trim());
				
			}
			if(linked.get(2)!=null){
				
				integrationZoneMapBean.setPoint(((String) linked.get(2)).trim());
				
			}
			if(size==4){
				if(linked.get(3)!=null){
					
					integrationZoneMapBean.setInfo(((String) linked.get(3)).trim());
				}
			}

			integrationZoneMapBeanList.add(integrationZoneMapBean);
		}

		return integrationZoneMapBeanList;
	}

	public static IntegrationZoneMapBean getIntegrationZoneMap(
			List<Object> linked) throws Exception{
		IntegrationZoneMapBean integrationZoneMapBean = new IntegrationZoneMapBean();
		
		int size = linked.size();
		if (linked.get(0) != null) {
			String zoneID = (String) linked.get(0);
			zoneID = zoneID.trim();
			if (!zoneID.isEmpty())
				integrationZoneMapBean.setZoneID(Integer.valueOf(zoneID));
		}
		if(linked.get(1)!=null){
			
			integrationZoneMapBean.setHostID(((String) linked.get(1)).trim());
			
		}
		if(linked.get(2)!=null){
			
			integrationZoneMapBean.setPoint(((String) linked.get(2)).trim());
			
		}
		if(size == 4){
			if(linked.get(3)!=null){
				
				integrationZoneMapBean.setInfo(((String) linked.get(3)).trim());
			}
		}
		
		return integrationZoneMapBean;
	}

	public static List<PulseZoneMapBean> getPulseZoneMaps(
			List<List<Object>> pulseList) throws Exception{
		List<PulseZoneMapBean> pulseZoneMapBeanList = new LinkedList<PulseZoneMapBean>();

		/**
		 * List<List<Object>>表数据
		 * List<Object>为行数据
		 */
		for (List<Object> obj : pulseList) {

			List linked = (LinkedList) obj;
			int size = linked.size();
			PulseZoneMapBean pulseZoneMapBean = new PulseZoneMapBean();

			if (linked.get(0) != null) {
				String zoneID = (String) linked.get(0);
				zoneID = zoneID.trim();
				if (!zoneID.isEmpty())
					pulseZoneMapBean.setZoneID(Integer.valueOf(zoneID));
			}
			if(linked.get(1)!=null){
				
				pulseZoneMapBean.setHostID(((String) linked.get(1)).trim());
			}
			if(linked.get(2)!=null){
				
				pulseZoneMapBean.setSubZone(((String) linked.get(2)).trim());
				
			}
			if(size == 4){
				
				if(linked.get(3)!=null){
					
					pulseZoneMapBean.setInfo(((String) linked.get(3)).trim());
				}
			}

		    pulseZoneMapBeanList.add(pulseZoneMapBean);
		}

		return pulseZoneMapBeanList;
	}

	public static PulseZoneMapBean getPulseZoneMap(List<Object> linked) throws Exception{
		
		PulseZoneMapBean pulseZoneMapBean = new PulseZoneMapBean();
		int size = linked.size();

		if (linked.get(0) != null) {
			String zoneID = (String) linked.get(0);
			zoneID = zoneID.trim();
			if (!zoneID.isEmpty())
				pulseZoneMapBean.setZoneID(Integer.valueOf(zoneID));
		}
		if(linked.get(1)!=null){
			
			pulseZoneMapBean.setHostID(((String) linked.get(1)).trim());
		}
		if(linked.get(2)!=null){
			
			pulseZoneMapBean.setSubZone(((String) linked.get(2)).trim());
			
		}
		if(size == 4){
			if(linked.get(3)!=null){
				
				pulseZoneMapBean.setInfo(((String) linked.get(3)).trim());
			}
		}
		
		return pulseZoneMapBean;
	}

	public static IpcZoneMap getIpcZoneMap(List<Object> linked) {
		
		IpcZoneMap ipcZoneMap = new IpcZoneMap();
		int size = linked.size();

		if (linked.get(0) != null) {
			String zoneID = (String) linked.get(0);
			zoneID = zoneID.trim();
			if (!zoneID.isEmpty())
				ipcZoneMap.setZoneID(Integer.valueOf(zoneID));
		}
		if(linked.get(1)!=null){
			
			ipcZoneMap.setIpcId(((String) linked.get(1)).trim());
		}
		if(linked.get(2)!=null){
			String point = (String) linked.get(2);
			point = point.trim();
			if (!point.isEmpty())
				ipcZoneMap.setPoint(Integer.valueOf(point));
			
		}
		if(linked.get(3)!=null){
			String mainIpc = (String) linked.get(3);
			mainIpc = mainIpc.trim();
			if (!mainIpc.isEmpty())
				ipcZoneMap.setMainIpc(Integer.valueOf(mainIpc));
		}
		if(size == 5){
			if(linked.get(4)!=null){
				
				ipcZoneMap.setInfo(((String) linked.get(4)).trim());
			}
		}
		
		return ipcZoneMap;
	}
	
	public static List<IpcZoneMap> getIpcZoneMaps(
			List<List<Object>> ipcList) throws Exception{
		List<IpcZoneMap> ipcZoneMapBeanList = new LinkedList<IpcZoneMap>();
		/**
		 * List<List<Object>>表数据
		 * List<Object>为行数据
		 */
		for (List<Object> obj : ipcList) {

			List linked = (LinkedList) obj;
			int size= linked.size();
			IpcZoneMap ipcZoneMap = new IpcZoneMap();

			if (linked.get(0) != null) {
				String zoneID = (String) linked.get(0);
				zoneID = zoneID.trim();
				if (!zoneID.isEmpty())
					ipcZoneMap.setZoneID(Integer.valueOf(zoneID));
			}
			if(linked.get(1)!=null){
				
				ipcZoneMap.setIpcId(((String) linked.get(1)).trim());
			}
			if(linked.get(2)!=null){
				String point = (String) linked.get(2);
				point = point.trim();
				if (!point.isEmpty())
					ipcZoneMap.setPoint(Integer.valueOf(point));
				
			}
			if(linked.get(3)!=null){
				String mainIpc = (String) linked.get(3);
				mainIpc = mainIpc.trim();
				if (!mainIpc.isEmpty())
					ipcZoneMap.setMainIpc(Integer.valueOf(mainIpc));
			}
			if(size == 5){
				
				if(linked.get(4)!=null){
					
					ipcZoneMap.setInfo(((String) linked.get(4)).trim());
				}
			}

			ipcZoneMapBeanList.add(ipcZoneMap);
		}

		return ipcZoneMapBeanList;
	}

	public static SoundZoneMap getSoundZoneMap(List<Object> linked) {
		SoundZoneMap soundZoneMap = new SoundZoneMap();
		
		int size = linked.size();

		if (linked.get(0) != null) {
			String zoneID = (String) linked.get(0);
			zoneID = zoneID.trim();
			if (!zoneID.isEmpty())
				soundZoneMap.setZoneID(Integer.valueOf(zoneID));
		}
		if(linked.get(1)!=null){
			soundZoneMap.setAudioID(((String) linked.get(1)).trim());
		}
		if(linked.get(2)!=null){
			String main = (String) linked.get(2);
			main = main.trim();
			if (!main.isEmpty())
				soundZoneMap.setMain(Integer.valueOf(main));
			
		}
		if(size == 4){
			
			if(linked.get(3)!=null){
				
				soundZoneMap.setInfo(((String) linked.get(3)).trim());
			}
		}
		
		return soundZoneMap;
	}
	
	public static List<SoundZoneMap> getSoundZoneMaps(
			List<List<Object>> soundList) throws Exception{
		List<SoundZoneMap> soundZoneMapBeanList = new LinkedList<SoundZoneMap>();
		/**
		 * List<List<Object>>表数据
		 * List<Object>为行数据
		 */
		for (List<Object> obj : soundList) {

			List linked = (LinkedList) obj;
			int size = linked.size();
			SoundZoneMap soundZoneMap = new SoundZoneMap();

			if (linked.get(0) != null) {
				String zoneID = (String) linked.get(0);
				zoneID = zoneID.trim();
				if (!zoneID.isEmpty())
					soundZoneMap.setZoneID(Integer.valueOf(zoneID));
			}
			if(linked.get(1)!=null){
				soundZoneMap.setAudioID(((String) linked.get(1)).trim());
			}
			if(linked.get(2)!=null){
				String main = (String) linked.get(2);
				main = main.trim();
				if (!main.isEmpty())
					soundZoneMap.setMain(Integer.valueOf(main));
				
			}
			if(size == 4){
				if(linked.get(3)!=null){
					
					soundZoneMap.setInfo(((String) linked.get(3)).trim());
				}
			}

			soundZoneMapBeanList.add(soundZoneMap);
		}

		return soundZoneMapBeanList;
	}

}
