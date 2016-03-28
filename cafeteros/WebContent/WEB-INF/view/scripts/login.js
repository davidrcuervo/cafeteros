$(function(){
	//$("ul.tabs li:first").addClass("active");
	//$(".login_block article").hide();
	//$(".login_block article:first").show();
	
	$("ul.tabs li").on("click", function(){
		$("ul.tabs li").removeClass("active");
		$(this).addClass("active");
		$(".login_block article").css({"display":"none"});
		
		var activeTab = $(this).find("a").attr("href");
		$(activeTab).css({"display": ""});
		
		return false;
	});
})

function checkForEmail(){
	
	var result = false;
	$.ajax({type: 'POST', url: $("#contextPath").text() + '/ajax/loginEmail',
		data: {"email": $('#login_email').val()},
		success: function(dataResult){
			if(dataResult.status == "success"){
				result = true;
			} else{
				
				$('#ajaxResult_validateMail').addClass("alert alert-danger text-center").html(dataResult.error);
			}
		},
		dataType: "json",
		async: false
	});
		
	return result;
}