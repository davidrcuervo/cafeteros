<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:useBean id="pageResources" class="ca.cafeteros.beans.PageResources" scope="request" />
<%--
<jsp:setProperty name="pageResources" property="addStyle" value="login.css" />
<jsp:setProperty name="pageResources" property="addScript" value="login.js" />
 --%>
<c:set var="title" scope="request">Cafeteros Club - Signup</c:set>

<c:set var="content" scope="request">
	<div>You have been succesfully registered in the web site of Cafeteros.</div>
	<p>
		Please don't forget to confirm your email and password by using the link that you will receive in your email.
	</p>
	
</c:set>

<jsp:include page="/WEB-INF/view/templates/thankyouTemplate.jsp"></jsp:include>