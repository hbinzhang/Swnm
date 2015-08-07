package com.service.linkagectl.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import sun.misc.BASE64Decoder;

import com.service.linkagectl.IBase64ToJPG;

public class Base64ToJPG implements IBase64ToJPG {

	private String absolutePATH;
	
	@Override
	/*
	 * (non-Javadoc)
	 * @see com.service.linkagectl.IBase64ToJPG#base64ToJPG(int, java.lang.String, java.util.ArrayList)
	 * return value: (alarmID_0,-1,alarmID_1,-1)
	 * 	 0:             the input vars(path or imgStrList is empty)
	 *  -1:             base64 to jpg error or save jpg to file error
	 *  alarmID_number: the jpg file name
	 */
	public StringBuffer base64ToJPG(int alarmID, String path, ArrayList<String> imgStrList) {
		 
		StringBuffer imgStrData = new StringBuffer();
		String fileName;
		
		if (imgStrList.isEmpty() || path.isEmpty()) {
	        return imgStrData;
		}
		
		absolutePATH = path + "/image/";
		//System.out.println(absolutePATH);
		
        for (int i = 0; i<imgStrList.size(); i++) {
            if (imgStrList.get(i) == null || imgStrList.get(i).isEmpty()) {
            	imgStrData.append("0" + ",");
            } else {
                fileName =  alarmID + "_" + i + ".jpg";
                if (GenerateImage(absolutePATH + fileName, imgStrList.get(i))) {
                    imgStrData.append("image/"+fileName + ",");	
                } else {
            	    imgStrData.append("-1" + ",");
                }
            }
        }
        imgStrData.deleteCharAt(imgStrData.length()-1);

		return imgStrData;
	}
    //Init the DIR 
	private boolean initPath() {
		File dir = new File(absolutePATH);
		if (!dir.exists() && !dir.isDirectory()) {
    	    dir.mkdir();
        }
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		System.out.println(wac.getResource("/image"));
        System.out.println("###");
		//System.out.println(this.getClass().getClassLoader().getResource("/").getPath());
       
       	return true;
	}
	//generate jpg file form base64
	private boolean GenerateImage(String fileName, String imgStr) {
		if (imgStr == null) {
			return false;
		}
		//System.out.println(imgStr);
		imgStr = imgStr.substring(22);
		//System.out.println(imgStr);
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(imgStr);
			for(int i = 0; i < b.length; ++i) {
				if (b[i]<0) {
					b[i] += 256;
				}
			}
			FileOutputStream out = new FileOutputStream(fileName);
			out.write(b);
			out.flush();
			out.close();
			return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
	}
}
