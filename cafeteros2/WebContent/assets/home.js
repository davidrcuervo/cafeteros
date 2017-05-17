var googleMapsKey = "AIzaSyA6Zjz6HdxJDdZJ0t0PS86jhA-Jpc3sTLY";

$(document).ready(function() {
	var s = document.createElement('script');
	s.setAttribute('src', 'https://maps.googleapis.com/maps/api/js?key=' + googleMapsKey + '&callback=initMap');
	s.setAttribute('async', 'async');
	s.setAttribute('defer', 'defer');
	document.body.appendChild(s);
});


function initMap() {
	
	var kent = {lat: 45.504186, lng: -73.631820};
	var BoisDeBoulogne = {lat: 45.538361, lng: -73.676752};


	
	var map1 = new google.maps.Map(document.getElementById('map_marketPark'), {
        center: kent,
        scrollwheel: false,
        zoom: 15
	});
	
	var map2 = new google.maps.Map(document.getElementById('map_boitBoilounPark'), {
        center: BoisDeBoulogne,
        scrollwheel: false,
        zoom: 15
	});
	
	var marker1 = new google.maps.Marker({
        position: kent,
        animation: google.maps.Animation.DROP,
        map: map1
	});
	
	var marker2 = new google.maps.Marker({
        position: BoisDeBoulogne,
        animation: google.maps.Animation.DROP,
        map: map2
	});
}
