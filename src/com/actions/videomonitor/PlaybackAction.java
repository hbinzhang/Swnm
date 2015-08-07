package com.actions.videomonitor;

import org.apache.commons.logging.LogFactory;

public class PlaybackAction extends LoadVideoViewAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PlaybackAction(){
		log = LogFactory.getLog(PlaybackAction.class);
	}
	
	public String loadVideoPlayback(){
		String res = SUCCESS;
		return res;
	}

}
