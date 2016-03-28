<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

   

<jsp:useBean id="pageResources" class="ca.cafeteros.beans.PageResources" scope="request" />
<%--
<jsp:setProperty name="pageResources" property="addStyle" value="login.css" />
<jsp:setProperty name="pageResources" property="addScript" value="login.js" />
 --%>
<c:set var="title" scope="request">Cafeteros Club - Login</c:set>

<c:set var="content" scope="request">
	<div>Your password has been validated correctly</div>
	<div>${pageContext.request.pathInfo}</div>
	
</c:set>

<jsp:include page="/WEB-INF/view/templates/thankyouTemplate.jsp"></jsp:include>