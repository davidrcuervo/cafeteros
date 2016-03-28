<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="java.util.Base64, ca.cafeteros.entities.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:useBean id="pageResources" class="ca.cafeteros.beans.PageResources" scope="request" />
<jsp:setProperty name="pageResources" property="scheme" value="${pageContext.request.scheme}" />
<jsp:setProperty name="pageResources" property="serverName" value="${pageContext.request.serverName}" />
<jsp:setProperty name="pageResources" property="serverPort" value="${pageContext.request.serverPort}" />
<jsp:setProperty name="pageResources" property="contextPath" value="${pageContext.request.contextPath}" />
<jsp:setProperty name="pageResources" property="word" value="${user.email}" />

<c:set var="content" scope="request">
	<h2>Signup succesfull</h2>
	<p>
		Name: ${user.fname} ${user.lname}.<br />
		You have been registered with the following email: ${user.email}.<br />
	</p>
	<p>
		Please validate your email address and your password by using the following link:<br />
		<a href="${pageResources['baseUrl']}/validateMail?user_email=${pageResources['encodedWord']}">click here to validate your credentials.</a>
	</p>
</c:set>

<jsp:include page="/WEB-INF/view/templates/emailTemplate.jsp" />