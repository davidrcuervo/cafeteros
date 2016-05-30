<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="pageResources" class="ca.cafeteros.beans.PageResources" scope="request" />
<jsp:setProperty name="pageResources" property="addStyle" value="team.css" />
<jsp:setProperty name="pageResources" property="addScript" value="team.js" />


<c:set var="subMenuActive" value="Team" scope="request"/>
<c:set var="subContent" scope="request">
	<h2>${teamBean.team.name }</h2>
	<div>${teamBean.team.introduction.text}</div>
	<div>
		<h3>Description:</h3>
		<div>${teamBean.team.description.text}</div>
	</div>
	<div>
		<h3>Agreement:</h3>
		<div>${teamBean.team.agreement.text}</div>
	</div>
	<c:if test="${!sessionUser.isTeamMember(teamBean.team)}">
		<a href="${pageContext.request.contextPath}/team/register/${team.urlEncodedName}" class="btn btn-primary btn-lg">Start playing with this team</a>
	</c:if>
</c:set>

<jsp:include page="/WEB-INF/view/pages/team/teamTemplate.jsp"/>