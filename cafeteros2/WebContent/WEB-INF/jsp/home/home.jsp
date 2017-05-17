<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

${page.addStyle('<link rel="stylesheet" type="text/css" href="/assets/home.css">')}
${page.addScript('<script src="/assets/home.js"></script>')}

<c:set scope="request" var="title" value="Home" />

<c:set scope="page" var="mailto" value="mailto:board@cafeterosclub.ca?subject=Cuadrangular de verano&body=Quiero participar en el torneo de este verano." />


<c:set scope="page" var="highlighted" value="style='color: #286090'" />
<c:set scope="request" var="content">
	<div class="title">
		<h3 class="text-center" style="margin-top: 0px; padding-top: 45px;"><strong>Cuadrangular Verano 2017</strong></h3>
		<h4 class="text-center" style="margin-top: 0px; padding-top: 20px;">15 Domingos de 9am a 10am, parque kent y College de Bois-de-Boulogne por solo <strong ${highlighted}>$65.00</strong> </h4>
	</div>
	
	<div class="row row1">
	
		<div class="col-xs-12 col-sm-4 col-sm-push-8 col-md-3 col-md-push-8 col-lg-2 col-lg-push-8" >
			<a class="btn btn-block btn-primary btn-lg" href="${mailto}">Reservar cupo</a>
			<table class="contactInfo">
				<tr>
					<td>
						<img src="/media/image/mail.svg?width=20" alt="" /> 
					</td>
					<td>
						<a href="mailto:board@cafeterosclub.ca"> board@cafeterosclub.ca</a>
					</td>
				</tr>
				<tr>
					<td>
						<img src="/media/image/phone.svg?width=20" alt="" />
					</td>
					<td>
						<a href="tel:14388043951"> +1 438-8043951</a>
					</td>
				</tr>
			</table>
		</div>
	
		<div class="col-xs-12 col-sm-8 col-sm-pull-4 col-md-offset-1 col-md-7 col-md-pull-3 col-lg-offset-2 col-lg-6 col-lg-pull-2">
			<div class="description">
				<ul>
					<li><span ${highlighted}>4 equipos</span> todos contra todos. Pa' no jugar siempre contra los mismos de siempre.</li>
					<li>Desde el <span ${highlighted}>21 de Mayo</span> hasta el <span ${highlighted}>24 de Septiembre</span></li>
					<li><strong>Hora:</strong> 9:00am - 10:00am</li>
					<li>
						<strong>Lugar:</strong>
						<ul>
							<li><span ${highlighted}>Parque kent</span>. Cote-des-Neiges Rd, Montreal. QC H3s 2T3</li>
							<li><span ${highlighted}>College de Bois-de-Boulogne</span>. 10555 Avenue de Bois de Boulogne, Montreal. QC H4N 1L4</li>
						</ul>
					</li>
					<li><strong>Precio: </strong> Solo <span ${highlighted}>$65.00</span> dolares canadienses por todo el verano.</li>
					<li><span ${highlighted}><strong>Cupo limitado.</strong></span> Cuando se completen los equipo no se recibiran mas jugadores.</li>
				</ul>
			</div>
		</div>
	</div>
	
	<div class="row">
		<div class="col-sm-4">
			<div class="thumbnail">
				<h4 class="text-center mapCaption" style="">Parque Kent</h4>
				<div id="map_marketPark" class="googleMap"></div>
			</div>
		</div>
		<div class="col-sm-4">
			<div class="thumbnail">
				<h4 class="text-center mapCaption">College de Bois-de-Boulogne</h4>
				<div id="map_boitBoilounPark" class="googleMap"></div>
			</div>
		</div>
		<div class="col-sm-4">
			<div class="thumbnail">
				<h4 class="text-center mapCaption">Viejos tiempos de Cafeteros</h4>
				<video controls style="max-width: 100%; height: auto;">
					<source src="/media/src/Cafeteros-Vs-TotalUtd_HIGHLIGHTS_2013-11-08.mp4" type="video/mp4" />
					<source src="/media/src/Cafeteros-Vs-TotalUtd_HIGHLIGHTS_2013-11-08.webm" type="video/webm" />
					<p>Your browser cannot play this video.</p>
				</video>
			</div>
		</div>
	</div>
</c:set>
<jsp:include page="/WEB-INF/jsp/templates/web.jsp"></jsp:include>