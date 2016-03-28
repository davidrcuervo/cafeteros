<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>${title}</title>
	
	<%-- LOADING STYLES --%>
	<%-- <link rel="stylesheet" href="${pageContext.request.contextPath}${assets}/bootstrap-3.3.6.css" /> --%>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
	<link rel="stylesheet" href="${pageContext.request.contextPath}${assets}/template.css" />
	
	<c:forEach var="style" items="${pageResources['styles']}" >
		<link rel="stylesheet" href="${pageContext.request.contextPath}${assets}/${style}" />
	</c:forEach>
	
	<%-- LOADING SCRIPTS --%>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<%--<script src="${pageContext.request.contextPath}${assets}/bootstrap-3.3.6.js"></script> --%>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
	 <script src='//cdn.tinymce.com/4/tinymce.min.js'></script>
	<c:forEach var="script" items="${pageResources['scripts']}" >
		<script src="${pageContext.request.contextPath}${assets}/${script}"></script>
	</c:forEach>
	<script>
		tinymce.init({
			selector: '.tinymceTextarea'
		});
	</script>

</head>
<body>
<header>
</header>
<nav class="navbar navbar-inverse">
	<div class="container-fluid">
    
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse-1" aria-expanded="false">
		    	<span class="sr-only">Toggle navigation</span>
		    	<span class="icon-bar"></span>
		    	<span class="icon-bar"></span>
		    	<span class="icon-bar"></span>
		  </button>
		  <a class="navbar-brand" href="#">Cafeteros</a>
		</div>
		
		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse" id="navbar-collapse-1">
			<ul class="nav navbar-nav">
		    	<li><a href="${pageContext.request.contextPath}/home"><span class="glyphicon glyphicon-home"></span> Home</a></li>
		    	<li><a href="${pageContext.request.contextPath}/teams">Teams</a></li>
		    	<li><a href='#'>News</a></li>
		    	<%--
		    	<li class="dropdown">
		      		<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
		      		<ul class="dropdown-menu">
		        		<li><a href="#">Action</a></li>
		        		<li><a href="#">Another action</a></li>
		        		<li><a href="#">Something else here</a></li>
		        		<li role="separator" class="divider"></li>
		        		<li><a href="#">Separated link</a></li>
		        		<li role="separator" class="divider"></li>
		        		<li><a href="#">One more separated link</a></li>
		      		</ul>
		    	</li> --%>
		  	</ul>
		  	<%--
		  	<form class="navbar-form navbar-left" role="search">
		    	<div class="form-group">
		      		<input type="text" class="form-control" placeholder="Search">
		    	</div>
		    	<button type="submit" class="btn btn-default">Submit</button>
		  	</form>
		  	 --%>
		  	<ul class="nav navbar-nav navbar-right">
		  		
		  		<c:choose>
		  			<c:when test="${sessionUser != null }">
			  			<c:if test="${sessionUser.isManager}">
			  				<li class="dropdown">
			  					<a href="#" class="dropdown-toggle text-capitalize" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">manage settings <span class="caret"></span></a>
			  					<ul class="dropdown-menu">
			  						<c:if test="${sessionUser.isTeamManager}">
			  							<li><a href="#">Teams</a></li>
			  						</c:if>
			  						<c:if test="${sessionUser.isExecutiveBoardMember}">
			  							<li><a href="${pageContext.request.contextPath}/manage/users">Users</a></li>
			  						</c:if>
			  					</ul>
			  				</li>
			  			</c:if>
		    			<li class="dropdown">
				      		<a href="#" class="dropdown-toggle text-capitalize" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-userglyphicon"></span> ${sessionUser.name} <span class="caret"></span></a>
				      		<ul class="dropdown-menu">
				        		<li><a href="#">My profile</a></li>
				        		<li><a href="#">Settings</a></li>
				        		<li><a href="#">Change password</a></li>
				        		<li role="separator" class="divider"></li>
				        		<li><a href="${pageContext.request.contextPath}/logout">Log Out</a></li>
				      		</ul>
				    	</li>
	    			</c:when>
	    			<c:otherwise>
	    				<li><a href="${pageContext.request.contextPath}/login">Log In</a></li>
	    			</c:otherwise>
		    	</c:choose>
		  	</ul>
		</div>
	</div>
</nav>
	
<div class="container-fluid max-width-1280">${content}</div>
<span id="contextPath" style="display:none;"><%=request.getContextPath()%></span>
<footer>
</footer>
</body>
</html>