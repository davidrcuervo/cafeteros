<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="pageResources" class="ca.cafeteros.beans.PageResources" scope="request" />
<jsp:setProperty name="pageResources" property="scheme" value="${pageContext.request.scheme}" />
<jsp:setProperty name="pageResources" property="serverName" value="${pageContext.request.serverName}" />
<jsp:setProperty name="pageResources" property="serverPort" value="${pageContext.request.serverPort}" />
<jsp:setProperty name="pageResources" property="contextPath" value="${pageContext.request.contextPath}" />
<jsp:setProperty name="pageResources" property="word" value="${user.email}" />

<c:set var="content" scope="request">
	<h2>Password recovery</h2>
	<p>
		Name: ${user.fname} ${user.lname}.<br />
	</p>
	<p>
		Your password has been reset. Your temporary password is:
	</p>
	<p>
		Password: <b>${password}</b>
	</p>
	<p>
		Please use the following link to reset your password: <br />
		<a href="${pageResources['baseUrl']}/home?user_email=${pageResources['encodedWord']}">click here to change your temporary password.</a>
	</p>
</c:set>

<jsp:include page="/WEB-INF/view/templates/emailTemplate.jsp" />