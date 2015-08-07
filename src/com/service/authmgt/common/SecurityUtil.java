package com.service.authmgt.common;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class SecurityUtil {

    //Server Public Key:

    private static final BigInteger SE = new BigInteger("65537");

    private static final BigInteger SN = new BigInteger("26370887695424693732883718485246242387129963801194829761640380358978224364140895336163537045567479006126813468811161471093853192569181511399288286159881900527456800359099043580180893622163217699702319716175467325238553087958846916470946141710528370354094364858387295627176500587882348900703569070110838948354996381501708044159559703885786833266841284224272237790436079264376115188493495523168483403848771410294135296356013033507505239302248808937583927522417668161176396269509979744743694778843508908264137155762054345819426096309487541189229933203021736651631908947636345625313892638556758438130691384374679004522517");

    public static final CipherKey SERVER_KEY = new CipherKey(SE, SN);
    
    public static final int SECURITY_ALARM_CODE = 5001;
    
    public static final int SECURITY_ALARM_TYPE_QOS = 5001;
    
    public static final int SECURITY_ALARM_SEVERITY_CRITICAL = 1;


	// 集合做差集
	@SuppressWarnings("unchecked")
	public static List separateList(List origList, List adderList) {
		String adderElement = null;
		int adderSize = adderList.size();
		for (int i = 0; i < adderSize; i++) {
			adderElement = (String) adderList.get(i);
			if (origList.contains(adderElement)) {
				origList.remove(adderElement);
			}
		}
		return origList;

	}

	// 集合合并
	@SuppressWarnings("unchecked")
	public static List combineList(List origList, List adderList) {
		String adderElement = null;
		int adderSize = adderList.size();
		for (int i = 0; i < adderSize; i++) {
			adderElement = (String) adderList.get(i);
			if (!origList.contains(adderElement)) {
				origList.add(adderElement);
			}
		}
		return origList;
	}
	

	@SuppressWarnings("unchecked")
	public static boolean compare(List oldList, List newList) {
		Object[] oldArray = oldList.toArray();
		Object[] newArray = newList.toArray();
		Arrays.sort(oldArray);
		Arrays.sort(newArray);
		if (!Arrays.equals(oldArray, newArray)) {
			return false;
		}
		return true;
	}
}

