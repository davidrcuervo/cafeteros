$(function(){$("ul.tabs li").on("click",function(){$("ul.tabs li").removeClass("active");$(this).addClass("active");$(".login_block article").css({display:"none"});var a=$(this).find("a").attr("href");$(a).css({display:""});return!1})});
function checkForEmail(){var a=!1;$.ajax({type:"POST",url:$("#contextPath").text()+"/ajax/loginEmail",data:{email:$("#login_email").val()},success:function(b){"success"==b.status?a=!0:$("#ajaxResult_validateMail").addClass("alert alert-danger text-center").html(b.error)},dataType:"json",async:!1});return a};