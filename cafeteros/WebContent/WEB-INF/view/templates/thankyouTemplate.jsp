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
	<c:forEach var="script" items="${pageResources['scripts']}" >
		<script src="${pageContext.request.contextPath}${assets}/${script}"></script>
	</c:forEach>

</head>
<body>
<header>
	<h1>Thank you page</h1>
</header>
<div class="container-fluid max-width-1280">${content}</div>
</body>
</html>