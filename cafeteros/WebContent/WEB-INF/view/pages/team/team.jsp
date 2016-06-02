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
	<div>${!sessionUser.isTeamMember(teamBean.team)}</div>
	<div>
		<h3>Players</h3>
		<div class="row">
			<c:forEach var="player" items="${teamBean.players}">
				<div class="col-xs-12 col-sm-4 col-md-3 col-lg-2">
					<div class="thumbnail">
						<div style="height: 100px;" class="text-center">player picture</div>
					</div>
					<div class="caption">
						<h3 class="text-center">${player.name}</h3>
					</div>
					<c:if test="${sessionUser.isTeamManager }">
						<div>
							<form method="post">
								<input type="hidden" name="userID" value="${player.id}" />
								<button type="submit" name="submit" value="removePlayer" class="btn btn-primary btn-sm btn-block"><span class="glyphicon glyphicon-ok"><</span> ACCEPT CANDIDATE</button>
							</form>
						</div>
					</c:if>
				</div>
			</c:forEach>
		</div>
	</div>
	<c:if test="${sessionUser.isTeamManager }">
		<h3>Candidates</h3>
		<div class="row">
			<c:forEach var="candidate" items="${teamBean.candidates}">
				<div class="col-xs-12 col-sm-4 col-md-3 col-lg-2">
					<div class="thumbnail">
						<div style="height: 100px;" class="text-center">player picture</div>
					</div>
					<div class="caption">
						<h3 class="text-center">${cadidate.name}</h3>
					</div>
					<div>
						<form method="post">
							<input type="hidden" name="userID" value="${cadidate.id}" />
							<button type="submit" name="submit" value="acceptPlayer" class="btn btn-primary btn-sm btn-block"><span class="glyphicon glyphicon-ok"><</span> ACCEPT CANDIDATE</button>
						</form>
					</div>
				</div>
			</c:forEach>
		</div>
	</c:if>
</c:set>

<jsp:include page="/WEB-INF/view/pages/team/teamTemplate.jsp"/>