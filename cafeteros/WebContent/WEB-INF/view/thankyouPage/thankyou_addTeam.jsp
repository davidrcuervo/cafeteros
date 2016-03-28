<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="title" scope="request">Cafeteros Club - Add Team</c:set>

<c:set var="content" scope="request">
	<div>You have succesfully created a new team</div>
	<p>
		Do not forget to inform the club members to register to the new team.
	</p>
	
</c:set>

<jsp:include page="/WEB-INF/view/templates/thankyouTemplate.jsp"></jsp:include>