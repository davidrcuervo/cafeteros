<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="pageResources" class="ca.cafeteros.beans.PageResources" scope="request" />
<%--
<jsp:setProperty name="pageResources" property="addStyle" value="login.css" />
<jsp:setProperty name="pageResources" property="addScript" value="login.js" />
 --%>
<c:set var="title" scope="request">Cafeteros Club - Password recovery</c:set>

<c:set var="content" scope="request">
	<div>Your password has been succesfully recovered.</div>
	<p>
		Please, check your email where you should receive your new password.
	</p>
	
</c:set>

<jsp:include page="/WEB-INF/view/templates/thankyouTemplate.jsp"></jsp:include>