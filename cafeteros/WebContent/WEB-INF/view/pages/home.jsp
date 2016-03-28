<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="pageResources" class="ca.cafeteros.beans.PageResources" scope="request" />
<jsp:setProperty name="pageResources" property="addStyle" value="home.css" />
<jsp:setProperty name="pageResources" property="addScript" value="home.js" />
<%--
<jsp:useBean id="sessionUser" class="ca.cafeteros.beans.SessionUser" scope="session" />
<jsp:setProperty name="sessionUser" property="request" value="${pageContext.request}" />
--%>
<c:set var="title" scope="request">Cafeteros Club</c:set>

<c:set var="content" scope="request">

	<a href="#">link test</a>
	<div><%= org.apache.catalina.realm.RealmBase.Digest("clave1234", "SHA-256", "UTF-8") %></div>
	<div>Is a user logged: ${sessionUser != null }</div>
	<div>User session name: ${sessionUser.name}</div>
	<div>User has visitor role: ${sessionUser.isVisitor}</div>
	<div>User has manager role: ${sessionUser.isTeamManager(team)}</div>
	<div>User has executive board role: ${sessionUser.isExecutiveBoardMember }</div>
</c:set>

<jsp:include page="/WEB-INF/view/templates/template.jsp"></jsp:include>