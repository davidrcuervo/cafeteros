<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="pageResources" class="ca.cafeteros.beans.PageResources" scope="request" />
<jsp:setProperty name="pageResources" property="addStyle" value="team.css" />
<jsp:setProperty name="pageResources" property="addScript" value="team.js" />

<c:set var="subMenuActive" value="Players" scope="request"/>
<c:set var="subContent" scope="request">
	<div>
		<h3>Players</h3>
		<div class="row">
			<c:forEach var="player" items="${teamBean.players}">
				<c:if test="${player.key == 'accepted'}">
					<div class="col-xs-12 col-sm-4 col-md-3 col-lg-2">
						<a href="${pageContext.request.contextPath}/player/#" class="divLink">
							<div class="thumbnail">
								<div style="height: 100px;" class="text-center">player picture</div>
							</div>
							<div class="caption">
								<h3 class="text-center">${player.value.fname} ${player.value.lname}</h3>
							</div>
						</a>
						<c:if test="${sessionUser.isTeamManager(teamBean.team)}">
							<form method="post">
								<div class="from-group">
									<label for="selectPlayerStatusInTeam">Change Status of player:</label>
									<input type="hidden" name="formName" value="changePlayerStatus"/>
									<input type="hidden" name="playerEmail" value="${player.value.email}" />
									<input type="hidden" name="teamName" value="${teamBean.team.name}" />
									<select name="playerStatusInTeam" onchange="this.form.submit()" id="selectPlayerStatusInTeam">
										<c:forEach var="status" items="${teamBean.allPlayerStatus }">
											<option value="${status.value}" ${status.value == player.key ? 'selected' : ''}>${status.value}</option>
										</c:forEach>
									</select>
								</div>
							</form>
						</c:if>
						<div class="caption text-center">Player stats and banner</div>	
					</div>
				</c:if>
			</c:forEach>
		</div>
	</div>
	<c:if test="${sessionUser.isTeamManager(teamBean.team)}">
		<h3>Other team members: </h3>
		<div class="row">
			<c:forEach var="member" items="${teamBean.players}">
				<c:if test="${member.key != 'accepted'}">
					<div class="col-xs-12 col-sm-4 col-md-3 col-lg-2">
						<a href="${pageContext.request.contextPath}/player/#" class="divLink">
							<div class="thumbnail">
								<div style="height: 100px;" class="text-center">player picture</div>
							</div>
							<div class="caption">
								<h3 class="text-center">${member.value.fname} ${member.value.lname}</h3>
							</div>
						</a>
						<form method="post">
							<input type="hidden" name="formName" value="changePlayerStatus"/>
							<input type="hidden" name="playerEmail" value="${member.value.email}" />
							<input type="hidden" name="teamName" value="${teamBean.team.name}" />
							<div class="from-group">
								<label for="selectPlayerStatusInTeam">Change Status of player:</label>
								<select name="playerStatusInTeam" onchange="this.form.submit();" id="selectPlayerStatusInTeam" class="form-control">
									<c:forEach var="status" items="${teamBean.allPlayerStatus }">
										<option value="${status.value}" ${status.value == member.key ? 'selected' : ''}>${status.value}</option>
									</c:forEach>
								</select>
							</div>
						</form>
					</div>
				</c:if>
			</c:forEach>
		</div>
	</c:if>
</c:set>

<jsp:include page="/WEB-INF/view/pages/team/teamTemplate.jsp"/>