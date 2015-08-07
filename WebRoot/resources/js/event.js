$(function(){
	// 查看详细信息
	$(".event_detail").click(function(){
		var eventid = $(this).attr("eventid");
		$(".detail_table tbody tr .content_shijianmingcheng").html($("#event"+eventid+"_shijianmingcheng").text());
		$(".detail_table tbody tr .content_suoshujigou").html($("#event"+eventid+"_suoshujigou").text());
		$(".detail_table tbody tr .content_fashengshijian").html($("#event"+eventid+"_fashengshijian").text());
		$(".detail_table tbody tr .content_shijiandidian").html($("#event"+eventid+"_shijiandidian").text());
		$(".detail_table tbody tr .content_suoshuquyubumen").html($("#event"+eventid+"_suoshuquyubumen").text());
		$(".detail_table tbody tr .content_shijiandingji").html($("#event"+eventid+"_shijiandingji").text());
		$(".detail_table tbody tr .content_shijianyuanyin").html($("#event"+eventid+"_shijianyuanyin").text());
		$(".detail_table tbody tr .content_shijianguocheng").html($("#event"+eventid+"_shijianguocheng").text());
		$(".detail_table tbody tr .content_shijianjieguo").html($("#event"+eventid+"_shijianjieguo").text());
		$(".detail_table tbody tr .content_yingxiangfanwei").html($("#event"+eventid+"_yingxiangfanwei").text());
		$(".detail_table tbody tr .content_zerenbumen").html($("#event"+eventid+"_zerenbumen").text());
		$(".detail_table tbody tr .content_zerenbumenfuzeren").html($("#event"+eventid+"_zerenbumenfuzeren").text());
		$(".detail_table tbody tr .content_bujiucuoshi").html($("#event"+eventid+"_bujiucuoshi").text());
		$(".detail_table tbody tr .content_bujiujieguo").html($("#event"+eventid+"_bujiujieguo").text());
		$(".detail_table tbody tr .content_shihoupinggu").html($("#event"+eventid+"_shihoupinggu").text());
		$('#event_detail_container').dialogModal({
			onOkBut: function() {},
			onCancelBut: function() {},
			onLoad: function() {},
			onClose: function() {}
		});
	});
});