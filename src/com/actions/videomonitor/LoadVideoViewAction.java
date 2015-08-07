package com.actions.videomonitor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.opensymphony.xwork2.ActionSupport;

public class LoadVideoViewAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static Log log = LogFactory.getLog(LoadVideoViewAction.class);
	private String pluginVersion;
	
	public String getPluginVersion() {
		return pluginVersion;
	}

	public void setPluginVersion(String pluginVersion) {
		this.pluginVersion = pluginVersion;
	}
}
