<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/view/forms/team.jsp" />

<c:set var="subMenuActive" value="Edit" scope="request"/>
<c:set var="subContent" scope="request">
	<h3>Add new Team</h3>
	<div>${teamForm}</div>
</c:set>

<jsp:include page="/WEB-INF/view/pages/team/teamTemplate.jsp"/>