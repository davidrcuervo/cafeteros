<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
<jsp:useBean id="pageResources" class="ca.cafeteros.beans.PageResources" scope="request" />
<jsp:setProperty name="pageResources" property="addStyle" value="login.css" />
<jsp:setProperty name="pageResources" property="addScript" value="login.js" />
 --%>
<c:set var="title" scope="request">Cafeteros Club - Login</c:set>

<c:set var="content" scope="request">
	<div>Page not found: error 404</div>
	<div><a href="#">Go to home page</a></div>
</c:set>

<jsp:include page="/WEB-INF/view/templates/template.jsp" />