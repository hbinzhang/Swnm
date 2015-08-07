package com.entity;

public class CommonBean {
	
	private String id;
	
	private String name;
	
	public CommonBean(){
		
	}

	public CommonBean(String id, String name){
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "CommonBean [id=" + id + ", name=" + name + "]";
	}

}
