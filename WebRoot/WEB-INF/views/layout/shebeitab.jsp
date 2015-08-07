<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include.inc.jsp"%>
<script type="text/javascript">
	$(function(){
		var currentSTabLink = '${param.sTabLink}';
		$("#stab_"+currentSTabLink).addClass("selected");
	});
</script>
<div class="normal_tab">
  	<ul>
  		<li><a id="stab_weilan" href="<c:url value='/fence/queryFence'></c:url>">围栏主机</a></li>
  		<li><a id="stab_fangqu" href="<c:url value='/zone/queryZoneByPage'></c:url>">防区管理</a></li>
		<li><a id="stab_ipc" href="<c:url value='/videomonitor/loadIpc.action'></c:url>">IPC设备</a></li>
		<li><a id="stab_nvr" href="<c:url value='/videomonitor/loadNvr.action'></c:url>">NVR设备</a></li>
		<li><a id="stab_decoder" href="<c:url value='/videomonitor/loadDecoder.action'></c:url>">解码器设备</a></li>
		<li><a id="stab_yinpin" href="<c:url value='/sounddevEnter'></c:url>">音频设备</a></li>
		<li><a id="stab_iva" href="<c:url value='/videomonitor/loadIva.action'></c:url>">智能视频设备</a></li>
  	</ul>
   </div>