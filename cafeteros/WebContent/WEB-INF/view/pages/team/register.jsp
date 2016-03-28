<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="subMenuActive" value="Enroll me" scope="request"/>
<c:set var="subContent" scope="request">
	<c:if test="${!sessionUser.isTeamMember(teamBean.team)}">
		<div>
			<h3>Please read and sign the enrollment agreement</h3>
			<div>${team.agreement}</div>
		</div>
		<form method="post" name="enrollme">
			<div class="checkbox">
				<label>
					<input type="checkbox" name="acceptAgreement" value="oui"> I have read and I agree with the enrollment agreement.
				</label>
			</div>
			<input type="hidden" name="teamName" value="${team.name}" />
			<input type="hidden" name="userEmail" value="${sessionUser.email}" />
			<button type="submit" name="submit" value="enrollUser" class="btn btn-primary btn-lg">Enroll me. Now</button>
		</form>
		<c:if test="${registrationError != null}">
			<div>${registrationError}</div>
		</c:if>
	</c:if>
	<c:if test="${sessionUser.isTeamMember(teamBean.team)}">
		<div>You have already enrolled in this team.</div>
	</c:if>
</c:set>

<jsp:include page="/WEB-INF/view/pages/team/teamTemplate.jsp"/>