$(document).ready(function() {
	$('div.bootstrap_table').children('table').addClass('table table-bordered');
	
});

$(function () {
	  $('[data-toggle="tooltip"]').tooltip()
});

function divLink(address){
	window.location.href = address;
}