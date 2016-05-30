<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="pageResources" class="ca.cafeteros.beans.PageResources" scope="request" />
<jsp:setProperty name="pageResources" property="addStyle" value="teamForm.css" />
<jsp:setProperty name="pageResources" property="addScript" value="teamForm.js" />

<c:set var="teamForm" scope="request">
	<form method="post" name="addTeam">
		
		<div class="form-group">
			<label class="control-label" for="inputTeamName">Name:</label>
			<input type="text" name="teamName" class="form-control" id="inputTeamName" value="${team.name}" placeholder="Type here the name of the team" aria-describedby=""/>
			<c:if test="${team.errors['name'] != null}">
				<div class="text-danger text-center">
					<c:forEach var="error" items="${team.errors['name'] }">
						<small>${error}</small><br />
					</c:forEach>
				</div>
			</c:if>
		</div>
		
		<div class="form-group">
			<label class="control-label" for="inputIntroduction">Small description of the team:</label>
			
			<!-- This introduction will be show along with all the teams of the club. This small description will help player to identify it this team fits on his needs -->
			<textarea class="form-control" rows="2" name="teamIntroduction" id="inputIntroduction" placeholder="Type here the description of the team">${team.introduction.text}</textarea>
			<c:if test="${team.errors['introduction'] != null}">
				<div class="text-danger text-center">
					<c:forEach var="error" items="${team.errors['introduction'] }">
						<small>${error}</small><br />
					</c:forEach>
				</div>
			</c:if>
		</div>
		
		<div class="form-group">
			<label class="control-label" for="inputTeamDescription">Description: </label>
			<textarea class="tinymceTextarea form-control" name="teamDescription" id="inputTeamDescription" placeholder="Type here the description of the team">${team.description.text}</textarea>
			<c:if test="${team.errors['description'] != null}">
				<div class="text-danger text-center">
					<c:forEach var="error" items="${team.errors['description'] }">
						<small>${error}</small><br />
					</c:forEach>
				</div>
			</c:if>
		</div>
		
		<div class="form-group">
			<label class="control-label" for="inputTeamAgreement">Enrollment agreement: </label>
			<textarea class="tinymceTextarea form-control" name="teamEnrollmentAgreement" id="inputTeamAgreement" placeholder="Type here the agreement of the team">
				${team.agreement.text}
			</textarea>
			<c:if test="${team.errors['agreement'] != null}">
				<div class="text-danger text-center">
					<c:forEach var="error" items="${team.errors['agreement'] }">
						<small>${error}</small><br />
					</c:forEach>
				</div>
			</c:if>	
		</div>
		
		<div class="form-group">
			<label class="control-label">Select the managers for the team:</label>
			<div class="row">
				<div class="col-xs-6 col-sm-3 col-md-4 col-lg-2">
					<div class="checkbox"><label><input type="checkbox" name="teamManager" value="name1" />Name 1</label></div>
				</div>
			</div>
		</div>
		
		<input type="hidden" name="urlEncodedName" value="${team.urlEncodedName }"/>
		<button type="submit" name="submit" value="add" class="btn btn-primary btn-lg btn-block">
			<span class="glyphicon glyphicon-plus"></span> ADD TEAM
		</button>
		<c:if test="${team.errors['addTeam'] != null}">
			<div class="text-danger text-center">
				<c:forEach var="error" items="${team.errors['addTeam'] }">
					<small>${error}</small><br />
				</c:forEach>
			</div>
		</c:if>
	</form>
</c:set>

