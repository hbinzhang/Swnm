package com.util.video;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.entity.videomonitor.TVmDecoder;

public class DecoderConvertUtil {

	public static TVmDecoder getTVmDecoder(List<Object> linked) throws Exception{

		TVmDecoder decoderBean = new TVmDecoder();
		int size = linked.size();
		
		if (linked.get(0) != null) {
			String decoderid = (String) linked.get(0);
			decoderid = decoderid.trim();
			if (!decoderid.isEmpty())
				decoderBean.setDecoderid(decoderid);
		}
		if(size>1){
			if(linked.get(1)!=null){
				decoderBean.setIp(((String) linked.get(1)).trim());
			}
		}
		if(size>2){
			if(linked.get(2)!=null){
				
				String port = (String) linked.get(2);
				port = port.trim();
				if (!port.isEmpty())
					decoderBean.setPort(new BigDecimal(port));
			}
		}
		if(size>3){
			if(linked.get(3)!=null){
				decoderBean.setUsername(((String) linked.get(3)).trim());
			}
		}
		if(size>4){
			if(linked.get(4)!=null){
				decoderBean.setPassword(((String) linked.get(4)).trim());
			}
		}
		if(size>5){
			if(linked.get(5)!=null){
				decoderBean.setDevname(((String) linked.get(5)).trim());
			}
		}
		if(size>6){
			if(linked.get(6)!=null){
				decoderBean.setDevictype(((String) linked.get(6)).trim());
			}
		}
		if(size>7){
			if(linked.get(7)!=null){
				decoderBean.setManagementagency(((String) linked.get(7)).trim());
			}
		}
		if(size>8){
			if(linked.get(8)!=null){
				decoderBean.setBranch(((String) linked.get(8)).trim());
			}
		}
		if(size>9){
			if(linked.get(9)!=null){
				String ordernumber = (String) linked.get(9);
				ordernumber = ordernumber.trim();
				if (!ordernumber.isEmpty())
					decoderBean.setOrdernumber(new BigDecimal(ordernumber));
			}
		}
		return decoderBean;
	}

	public static List<TVmDecoder> getTVmDecoders(Map<String, List<Object>> decoders) throws Exception{

		List<TVmDecoder> decoderList = new LinkedList<TVmDecoder>();
		for (String key : decoders.keySet()) {

			List linked = (LinkedList) decoders.get(key);
			TVmDecoder decoderBean = new TVmDecoder();
			int size = linked.size();
			
			if (linked.get(0) != null) {
				String decoderid = (String) linked.get(0);
				decoderid = decoderid.trim();
				if (!decoderid.isEmpty())
					decoderBean.setDecoderid(decoderid);
			}
			if(size>1){
				if(linked.get(1)!=null){
					decoderBean.setIp(((String) linked.get(1)).trim());
				}
			}
			if(size>2){
				if(linked.get(2)!=null){
					
					String port = (String) linked.get(2);
					port = port.trim();
					if (!port.isEmpty())
						decoderBean.setPort(new BigDecimal(port));
				}
			}
			if(size>3){
				if(linked.get(3)!=null){
					decoderBean.setUsername(((String) linked.get(3)).trim());
				}
			}
			if(size>4){
				if(linked.get(4)!=null){
					decoderBean.setPassword(((String) linked.get(4)).trim());
				}
			}
			if(size>5){
				if(linked.get(5)!=null){
					decoderBean.setDevname(((String) linked.get(5)).trim());
				}
			}
			if(size>6){
				if(linked.get(6)!=null){
					decoderBean.setDevictype(((String) linked.get(6)).trim());
				}
			}
			if(size>7){
				if(linked.get(7)!=null){
					decoderBean.setManagementagency(((String) linked.get(7)).trim());
				}
			}
			if(size>8){
				if(linked.get(8)!=null){
					decoderBean.setBranch(((String) linked.get(8)).trim());
				}
			}
			if(size>9){
				if(linked.get(9)!=null){
					String ordernumber = (String) linked.get(9);
					ordernumber = ordernumber.trim();
					if (!ordernumber.isEmpty())
						decoderBean.setOrdernumber(new BigDecimal(ordernumber));
				}
			}

			decoderList.add(decoderBean);
		}

		return decoderList;
	}
}
