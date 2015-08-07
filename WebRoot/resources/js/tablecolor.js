;(function($){
	$.fn.colorTable = function(option){
		if (!this.is("table")){
			return false;
		}
		var defaultSetting = {
			'even' : '',
			'odd'  : '#f5f8fa'
		};
		var s = $.extend(defaultSetting,option);
		return this.each(function(){
			var $table = $(this);
			$table.find("tbody tr:even").css("background-color",s.even);
			$table.find("tbody tr:odd").css("background-color",s.odd);
			$table.find("tbody tr").hover(function(){
				$(this).css("background-color",'#e5ebee');
			 	},function(){
			 		$table.find("tbody tr:even").css("background-color",s.even);
					$table.find("tbody tr:odd").css("background-color",s.odd);
			}); 
		});
	};
})(jQuery);