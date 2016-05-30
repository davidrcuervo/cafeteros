<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="pageResources" class="ca.cafeteros.beans.PageResources" scope="request" />
<jsp:setProperty name="pageResources" property="addStyle" value="teams.css" />
<jsp:setProperty name="pageResources" property="addScript" value="teams.js" />

<jsp:useBean id="teams" class="ca.cafeteros.beans.Teams" scope="request" />
<jsp:setProperty name="teams" property="request" value="${pageContext.request}" />

<c:set var="content" scope="request">
	<div class="row">
		<c:forEach var="team" items="${teams.teams}">
			<div class="col-xs-12 col-sm-4 col-md-3 col-lg-2">
				<a href="${pageContext.request.contextPath}/team/${team.urlEncodedName}" class="divLink">
					<div class="thumbnail">
						<div style="height: 100px;" class="text-center">team picture</div>
					</div>
					<div class="caption">
						<h3 class="text-center">${team.name}</h3>
					</div>
				</a>
				<div class="caption text-center">${team.introduction.text}</div>	
			</div>
		</c:forEach>
		<c:if test="${sessionUser.isExecutiveBoardMember}">
			<div class="col-xs-12 col-sm-4 col-md-3 col-lg-2">
				<div class="thumbnail">
					<div style="height: 100px;" class="text-center"><span class="glyphicon glyphicon-plus"></span></div>
				</div>
				<div class="caption text-center" >
					<h3>Add Team</h3>
					<div>
						Make sure that not similar team exists before creating a new one.<br />
						
					</div>
					<a href="${pageContext.request.contextPath}/team/add" class="btn btn-primary" role="button"><span class="glyphicon glyphicon-plus text-center"></span> Add Team</a>
				</div>
			</div>
		</c:if>
	</div>
</c:set>

<jsp:include page="/WEB-INF/view/templates/template.jsp"/>