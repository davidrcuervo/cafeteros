<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="subMenu" scope="request">
	<ul class="nav nav-pills nav-stacked">
		<li class="${subMenuActive == 'Team' ? 'active' : '' }"><a href="${pageContext.request.contextPath}/team/${team.urlEncodedName}" class="text-center">Team</a></li>
		<li class="${subMenuActive == 'Players' ? 'active' : '' }"><a href="${pageContext.request.contextPath}/team/players/${team.urlEncodedName}" class="text-center">players</a></li>
		<c:if test="${!sessionUser.isTeamMember(team)}">
			<li class="${subMenuActive == 'Enroll me' ? 'active' : '' }"><a href="${pageContext.request.contextPath}/team/register/${team.urlEncodedName}" class="text-center">Enroll me</a></li>
		</c:if>
		<c:if test="${sessionUser.isExecutiveBoardMember}">
			<li role="separator" class="nav-divider"></li>
			<li class="${subMenuActive == 'Edit' ? 'active' : '' }"><a href="${pageContext.request.contextPath}/team/edit/${team.urlEncodedName}" class="text-center">Edit team</a></li>
		</c:if>
	</ul>
</c:set>

<jsp:include page="/WEB-INF/view/templates/subMenuTemplate.jsp"/>