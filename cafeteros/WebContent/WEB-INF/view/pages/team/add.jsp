<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="pageResources" class="ca.cafeteros.beans.PageResources" scope="request" />
<jsp:setProperty name="pageResources" property="addStyle" value="team.css" />
<jsp:setProperty name="pageResources" property="addScript" value="team.js" />

<c:set var="submitValue" scope="request" value="add"/>
<c:set var="buttonContent" scope="request"><span class="glyphicon glyphicon-plus"></span> ADD TEAM</c:set>

<jsp:include page="/WEB-INF/view/forms/team.jsp" />

<c:set var="content" scope="request">
	<h3>Add new Team</h3>
	${teamForm}
</c:set>

<jsp:include page="/WEB-INF/view/templates/template.jsp" />