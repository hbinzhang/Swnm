<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">

        var onAppData = function(event){ 
            //remoteEvent = "/linkageCtrl/"+orgID+"/"+userID+"/MC";
            //localWarning = "/warning/gis/"+orgID;
            //localOperation = "/operation/gis/"+orgID;
            alertInfo("Enter onData");
            alertInfo("event.getSubject() value is :"+event.getSubject());
            //return;
            var subject = event.getSubject();
            
            if (subject == remoteEvent) {//联动发来的消息
                var msgType = event.get(PUSHLET_FIELD_NAME_CTL_MSGTYPE);//消息类型:设备同步，告警同步，防区同步……
                var message = event.get(PUSHLET_FIELD_NAME_CTL_MSSSAGE);
                alertInfo("msgType from ctl is : " + msgType);
                alertInfo("message from ctl is : " + message);
                focusPoint(msgType, message);
            }
            else if (subject == localWarning) {//告警推送发来的消息
                var alarmOperation = event.get(PUSHLET_FIELD_NAME_ALARM_OPERATION);//消息处理类型 add 或者 del
                var alarmType = event.get(PUSHLET_FIELD_NAME_ALARM_ALARMTYPE);//消息类型 securityAlarm或者devAlarm
                var message = event.get(PUSHLET_FIELD_NAME_ALARM_MESSAGE);//消息体
                //var alarmID = event.get("alarmID");
                if (alarmOperation == OPERATION_ADD)
                {                
                    displayAlarm(alarmType, message);
                }
                else if (alarmOperation == OPERATION_DELETE)
                {
                    deleteAlarm(alarmType,message);
                }
            }
            else if (subject == localOperation) {//操作推送发来的消息
                var devOperation = event.get("devOperation");//消息处理类型 自行定义，statchange,add,delete
                var devType = event.get("devType");//设备类型 ，camera,fence,zone
                var message = event.get("message");
                
                if(devType == "camera"||devType == "fence"){//摄像头、围栏
                    if(devOperation == OPERATION_CHANGE){//只要包含当前的状态
                        operateDevice(devType,message);
                    }else if(devOperation == OPERATION_ADD){//要包含完整数据
                        displayDevice(devType,message);
                    }else if(devOperation == OPERATION_DELETE){//只要能指明即可
                        deleteDevice(devType,message);
                    }
                    
                }else if(devType == "zone"){//防区
                    var msgObj = eval('(' + message + ')');
                    var zoneID = msgObj.zoneID;
                    if(devOperation == OPERATION_CHANGE){//只要包含当前的状态
                        operateZone(zoneID,message);
                    }else if(devOperation == OPERATION_ADD){//要包含完整数据
                        displayZone(zoneID,message);
                    }else if(devOperation == OPERATION_DELETE){//只要能指明即可
                        deleteZone(zoneID);
                    }
                }
            }
        }
        
       //$(function(){
       //alert(${sessionScope.session.id});
       //alert(${sessionScope.session.organizationId});
        linkageInit('${sessionScope.session.id}','${sessionScope.session.organizationId}',"gispage");
    //});
            
        /*** EOF 处理 pushlet 消息 ***/
</script>