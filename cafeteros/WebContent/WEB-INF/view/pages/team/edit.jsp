<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="submitValue" scope="request" value="edit"/>
<c:set var="buttonContent" scope="request"><span class="glyphicon glyphicon-plus"></span> EDIT TEAM</c:set>

<jsp:include page="/WEB-INF/view/forms/team.jsp" />

<c:set var="subMenuActive" value="Edit" scope="request"/>
<c:set var="subContent" scope="request">
	<h3>Edit Team</h3>
	<div>${teamForm}</div>
</c:set>

<jsp:include page="/WEB-INF/view/pages/team/teamTemplate.jsp"/>