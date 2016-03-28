<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="pageResources" class="ca.cafeteros.beans.PageResources" scope="request" />
<jsp:setProperty name="pageResources" property="addStyle" value="validateEmail.css" />
<jsp:setProperty name="pageResources" property="addScript" value="validateEmail.js" />

<c:set var="title" scope="request">Validate Email</c:set>

<c:set var="content" scope="request">

	<form method="post" name="validateEmail">
		
		<div class="form-group">
			<label class="control-label" for="user_email">e-mail:</label>
			<div class="input-group">
				<span class="input-group-addon">
					<span class="glyphicon glyphicon-envelope"></span>
				</span>
				<input type="email" class="form-control" id="user_email" name="email" value="${userEmail}" readonly/>
			</div>
		</div>
		
		<div class="form-group">
			<label class="control-label" for="user_password">Password:</label>
			<div class="input-group">
				<span class="input-group-addon">
					<span class="glyphicon glyphicon-lock"></span>
				</span>
				<input type="password" class="form-control" id="user_password" name="password" placeholder="Your password" />
			</div>
		</div>
		
		<button type="submit" class="btn btn-primary btn-lg btn-block" name="form_name" value="validateEmail">Validate your e-Mail</button>
	</form>
	<c:if test="${error_validateEmail != null}">
		<div class="text-center">${error_validateEmail}</div>
	</c:if>

</c:set>

<jsp:include page="/WEB-INF/view/templates/template.jsp"></jsp:include>