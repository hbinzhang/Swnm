package com.service.linkagectl;

import java.util.ArrayList;

public interface IBase64ToJPG {

    StringBuffer base64ToJPG(int alarmID, String path, ArrayList<String> imgStr);

	
}
