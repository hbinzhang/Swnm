var AUTO_SWITCH_TIME = 4000; //

// 
var TIMER, TABS = 4, PRE_T = 'newes', PRE_C = 'newes0', TAB = 1, CLASSS = [' ', 'active'];
function stopSwitcher() {
	TIMER = clearInterval(TIMER);
}
function startSwitcher() {
	stopSwitcher();
	TIMER = setInterval(function(){
		var i = (TAB >= TABS || TAB <= 0) ? 1 : TAB + 1;
		switchTab(i);
	}, AUTO_SWITCH_TIME);
}
function toggle(id, hide) {
	var el = document.getElementById(id);
	if(el && el.style) el.style.display = hide ? 'none' : '';
}
function toggleCls(id, cls) {
	var el = document.getElementById(id);
	if(el) el.className = cls;
}
function switchTab(index, stop) {
	if(stop) stopSwitcher();
	if(TAB == index || index > TABS || index <= 0) return;
	for(var i = 1; i <= TABS; i++) {
		toggle(PRE_C + i, true);
		toggleCls(PRE_T + i, CLASSS[0]);
	}
	toggle(PRE_C + index);
	toggleCls(PRE_T + index, CLASSS[1]);
	TAB = index;
}
startSwitcher();


function set(name,cursel,n){
	  for(i=1;i<=n;i++){
	  var menu=document.getElementById(name+i);
	  var con=document.getElementById("con"+name+i);
	  menu.className=i==cursel?"active":"";
	  con.style.display=i==cursel?"block":"none";
	} 
  }
	function setab(name,cursel,n,link){
	  for(i=1;i<=n;i++){
	  var menu=document.getElementById(name+i);
	  var con=document.getElementById("con"+name+i);
	  menu.className=i==cursel?"active":"";
	  con.style.display=i==cursel?"block":"none";
	}
		if(link!=null && link!="")document.getElementById("TabLink"+name).href=link;
  }

function showList(id,num){
	if(num == 1){
		document.getElementById(id).style.display = "block";
	}
	else{
		document.getElementById(id).style.display = "none";
	}
}