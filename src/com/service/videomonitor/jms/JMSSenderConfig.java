package com.service.videomonitor.jms;
public class JMSSenderConfig {
	protected String jndiFactory;
	protected String providerUrl;
	protected String connFactoryJNDI;	public String getJndiFactory() {		return jndiFactory;	}	public void setJndiFactory(String jndiFactory) {		this.jndiFactory = jndiFactory;	}	public String getProviderUrl() {		return providerUrl;	}	public void setProviderUrl(String providerUrl) {		this.providerUrl = providerUrl;	}	public String getConnFactoryJNDI() {		return connFactoryJNDI;	}	public void setConnFactoryJNDI(String connFactoryJNDI) {		this.connFactoryJNDI = connFactoryJNDI;	}
}
