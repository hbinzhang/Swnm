function TablePage(id,size,divid,num,arg){  
	var $table = $(id);
	var $div = $(divid);  	
    var currentPage = 0;  //当前页  
    var pageSize = size;  //每页行数（不包括表头）  
    $table.bind("repaginate", function()  
    {  
       //console.log($table.find("tbody tr").eq(0));  
      $table.find("tbody tr").hide().slice(currentPage * pageSize, (currentPage + 1) * pageSize).show();  
     // $table.find("tbody tr").eq(0).show();  
    });  
    var numRows = $table.find("tbody tr").length;  //记录宗条数  
    var numPages = Math.ceil(numRows/pageSize);    //总页数  
    //console.log(numPages);  
    var $pager = $("<div class='page' id='di"+num+"'><a class='firstspan' href='javascript:void(0)'><span id='Prev"+num+"' style=''>上页</span></a></div>");  //分页div  
    for( var page = 0; page < numPages; page++ )  
    {  
  
      //为分页标签加上链接  
      //if(page==0){$("<a href='javascript:void(0)'><span id='1' class="click_page"></span></a>")}  
      $("<a href='javascript:void(0)'><span name='"+(page+1)+arg+"' id='"+(page+1)+"'>"+ (page+1) +"</span></a>")  
           .bind("click", { "newPage": page }, function(event){  
                currentPage = event.data["newPage"];  
                //console.log($(this).children("span"));  
                $(this).children("span").attr("class","click_page");  
                //$(this).children("span").css({"color":"#FFFFFF"});  
                $(".page a span").not($(this).children("span")).attr("class","unclick");  
                //$(".page a span").not($(this).children("span")).css({"color":"#01adef"});  
                $table.trigger("repaginate");                  $("#custompage_current").text(currentPage+1);
            })  
            .appendTo($pager);  
  
        $pager.append("  ");  
    }  
    //$table.trigger("repaginate");  
    var next=$("<a class='lastspan' href='javascript:void(0)'><span id='Next"+num+"' style=''>下页</span></a><div class='clear'></div>");  
    $pager.append(next);  
    //$pager.insertAfter($table);//分页div插入table  
    $div.empty();    $div.append('<div class="message left">共<a class="blue" href="javascript:void(0);">'+numRows+'</a>条记录，共&nbsp;<a class="blue" href="javascript:void(0);">'+numPages+'&nbsp;页&nbsp;，</a>当前显示第&nbsp;<a class="blue" id="custompage_current" href="javascript:void(0);">'+1+'&nbsp;</a>页</div>');	$div.append($pager);
    $("#1").attr("class","click_page");  
	//$("span[name='1"+arg+"']").attr("class","click_page");  
	
    //$("#1").css({"color":"#FFFFFF"});  
    $table.trigger("repaginate");  
    //console.log($("#1"));  
    //$("#1").attr("class","click_page");  
    //$("#1").css({"background":"#FFFFFF"}); 
	
    $("#"+"Prev"+num).bind("click",function(){ 
		
       var prev=Number($(".click_page").text())-2;  
       currentPage=prev;  
       //$(this).css({"background":"#000000"});  
       if(currentPage<0) {  
         //$(this).css({"background":"#c0c0c0"});  
         return;  
		}  
       $("#"+(prev+1)).attr("class","click_page");  
       //$("#"+(prev+1)).css({"color":"#FFFFFF"});  
       $(".page a span").not($("#"+(prev+1))).attr("class","unclick");  
       //$(".page a span").not($("#"+(prev+1))).css({"color":"#01adef"});  
       //console.log(currentPage);  
       $table.trigger("repaginate");         $("#custompage_current").text(currentPage+1);
    });  
     $("#"+"Next"+num).bind("click",function(){  
       var next=$(".click_page").attr("id");  
       currentPage=Number(next);  
       //console.log($(".click_page").text());  
       //$(this).css({"background":"#FFFFFF"});  
       if((currentPage+1)>numPages) {  
         //$(this).css({"background":"#c0c0c0"});  
         return;  
         }  
       $("#"+(currentPage+1)).attr("class","click_page");  
       //$("#"+(currentPage+1)).css({"color":"#FFFFFF"});  
       $(".page a span").not($("#"+(currentPage+1))).attr("class","unclick");  
       //$(".page a span").not($("#"+(currentPage+1))).css({"color":"#01adef"});  
       $table.trigger("repaginate");        $("#custompage_current").text(currentPage+1);
    });  
}  