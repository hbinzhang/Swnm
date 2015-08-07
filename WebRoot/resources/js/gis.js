var closeGisWindow = function (){
    alert("尊敬的用户，您还没有登录！\n请您登录后，从安防综合监控系统主界面打开地理信息窗口，谢谢！\n地理信息窗口将关闭！");
    window.close();
}
 
//通过组织id查询信息，用于补充推送信息
var queryOrgInfoById = function (orgId)
{
    var timenow = new Date().getTime();
    var location = (window.location+'').split('/'); 
    var basePath = location[0]+'//'+location[2]+'/'+location[3];

    //var targetUrl = "${pageContext.request.contextPath}/gis/queryOrgInfoById?orgId="+ orgId + "&d="+timenow;
    var targetUrl = basePath + "/gis/queryOrgInfoById?orgId="+ orgId + "&d="+timenow;
    $.ajax({
        url : targetUrl, 
        type : 'POST',
        dataType : 'json', 
        contentType: "application/json; charset=utf-8",
        success : function(data) {
            if(data){
                if(data.result == 1){//正常
                	//alert(data.object.orgNm);
                    return data.object.orgNm;
                }
                else if(data.result == 0){
                    return "";
                }
                else if(data.result == -100){
                    closeGisWindow();
                }
            }
        },
        error : function() {
            return "";
        }
    });
}

/* 数据类型定义：地图初始化参数*/
function mapInitParam(xmin, ymin, xmax, ymax)
{
    this.xmin = xmin;//左下角
    this.ymin = ymin;
    this.xmax = xmax;//右上角
    this.ymax = ymax;
}
