package com.service.authmgt.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import com.entity.authmgt.GroupAndOperation;
import com.entity.authmgt.Operation;
import com.entity.authmgt.OperationGroup;

public class OperationAuthorizationImp {
	
	public static List<GroupAndOperation> queryAllGroupAndOperation() {

		List<GroupAndOperation> groupAndOperations=new ArrayList();	
		
		List<OperationGroup> groups = AuthorizationManager
				.getOperationGroups();
		
		List<Operation> operations = AuthorizationManager
				.getOperations();
		List<OperationGroup> groupsTmp =new ArrayList();
		for(int i=1;i<groups.size();i++){
			groupsTmp.add(groups.get(i));
		}
//		groups.remove(0);
		for(OperationGroup operationGroup:groupsTmp){
			GroupAndOperation groupAndOperation = new GroupAndOperation();
			groupAndOperation.setOpGroupName(operationGroup.getOpGroupDisplayName());
			List<String> opDisplayNames=new ArrayList();			
			List<String> opNames =operationGroup.getChildren();
			groupAndOperation.setOpName(getOperationDisplayNames(opNames));
			groupAndOperations.add(groupAndOperation);
		}
	
		return groupAndOperations;
	}


	private static OperationGroup getOperationGroup(
			List<OperationGroup> operationGroups, String name) {
		for (OperationGroup og : operationGroups) {
			if (name.equals(og.getOpGroupName())) {
				return og;
			}
		}
		return null;
	}

	private static Operation getOperation(List<Operation> operations, String name) {
		for (Operation o : operations) {
			if (name.equals(o.getOpName())) {
				return o;
			}
		}
		return null;
	}


//	public static List<String> getHigherOperationDisplayNames(String pName){	
//		Map<String, List<String>> higherMap=AuthorizationManager.getHigherOperationMap();
//		List<String> higherOpNames=higherMap.get(pName);
//		return getOperationDisplayNames(higherOpNames);
//	}
//
//	public static List<String> getLowerOperationDisplayNames(String pName){	
//		Map<String, List<String>> lowerMap=AuthorizationManager.getLowerOperationMap();
//		List<String> lowerOpNames=lowerMap.get(pName);
//		return getOperationDisplayNames(lowerOpNames);
//	}

	public static List<String> getOperationDisplayNames(List<String> opNames){	
		List<String> opDisplayNames=new ArrayList();
		Properties nameAndDisplays =AuthorizationManager.getOperationNameAndDisplay();
		Set<Object> names=nameAndDisplays.keySet();
		Iterator<Object> nameIterator = names.iterator();
		String display = null;
		String name = null;
		while (nameIterator.hasNext()) {
			name=(String)nameIterator.next();
			for(String opName:opNames){
				if(name.compareToIgnoreCase(opName)==0){
					display = (String)nameAndDisplays.get(name);
					opDisplayNames.add(display);
				}
			}
		}
		return opDisplayNames;
	}

	
//    public static void main(String[] s) {
//    	List<GroupAndOperation> groupAndOperations=queryAllGroupAndOperation();
//
//    }	
}
