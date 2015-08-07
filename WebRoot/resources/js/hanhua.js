function mic()
        {
            return document.getElementById('plugin3');
        }
        speech = mic;
        function addEvent(obj, name, func)
        {
            if (obj.attachEvent) {
                obj.attachEvent("on"+name, func);
            } else {
                obj.addEventListener(name, func, false); 
            }
        }
        
        function load()
        {
           
        }
        function pluginLoaded() {
            
        }
        
        function addTestEvent()
        {
            addEvent(speech(), 'echo', function(txt,count){
                alert(txt+count);
            });
        }
        
        function testEvent()
        {
           speech().testEvent();
        }

	function start()
        {
			var ip =$("#zonggongsiip").val();
            speech().StartSpeech(ip, 4936);
        }

	function stop()
        {
            speech().StopSpeech();
        }        
	
	function getfile()
	{
	   var fileid = speech().SpeechGetFileId();
	   return fileid;
           
	}

        function pluginValid()
        {
            if(speech().valid){
                alert(speech().echo("This plugin seems to be working!"));
            } else {
                alert("Plugin is not working :(");
            }
        }