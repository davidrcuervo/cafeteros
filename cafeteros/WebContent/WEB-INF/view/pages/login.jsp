<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="pageResources" class="ca.cafeteros.beans.PageResources" scope="request" />
<jsp:setProperty name="pageResources" property="addStyle" value="login.css" />
<jsp:setProperty name="pageResources" property="addScript" value="login.js" />

<c:set var="title" scope="request">Cafeteros Club - Login</c:set>

<c:set var="content" scope="request">
<h1 class="text-center">Welcome</h1>
<h3 class="text-center"><small>By logging in... You accept to follow the code of respect stablished by the club.</small></h3>

<div id="login_form">
	
	
	<ul class="tabs">
		<li class="${signupError == null ? '' : 'active' }"><a href="#signup">SIGNUP</a></li>
		<li class="${signupError == null and forgetPasswordError == null ? 'active' : '' }"><a href="#login">LOGIN</a></li>
		<li class="${forgetPasswordError == null ? '' : 'active' }"><a href="#forget_password">FORGET PASSWORD</a></li>
	</ul>
	<div class="clr"></div>
		
	<section class="login_block">
		<article id="signup" style="${signupError == null ? 'display: none;' : '' }">
			<form method="post" action="${pageContext.request.contextPath}/signup" name="signup">
			
				<%-- THIS PART REQUEST FOR THE FIRST NAME OF THE SIGNUP FORM --%>
				<div class="form-group ${user == null ? '' : (user.errors['fname'] == null ? 'has-success has-feedback' : 'has-error has-feedback')}">
					<label class="control-label" for="signup_fname">Your first name:</label>
					<div class="input-group input-group-lg">
						<span class="input-group-addon">
							<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
						</span>
						<input type="text" class="form-control" id="signup_fname" name="fname" placeholder="Your first name" value="${user.fname}" aria-describedby="singup_fname_status">
					</div>
					<span class="glyphicon form-control-feedback ${user == null ? '' : (user.errors['fname'] == null ? 'glyphicon-ok' : 'glyphicon-remove')}" aria-hidden="true"></span>
					<span id="signup_fname_status" class="sr-only">${user == null ? '' : (user.errors['fname'] == null ? '(success)' : '(error)')}</span>
					<c:if test="${user.errors['fname'] != null}">
						<div class="text-danger text-center">
							<c:forEach var="error" items="${user.errors['fname'] }">
								<small>${error}</small><br />
							</c:forEach>
						</div>
					</c:if>
				</div>
				
				
				<%-- THIS PART REQUEST FOR THE LAST NAME OF THE SIGNUP FORM --%>
				<div class="form-group ${user == null ? '' : (user.errors['lname'] == null ? 'has-success has-feedback' : 'has-error has-feedback')}">
					<label class="control-label" for="signup_lname">Your last name:</label>
					<div class="input-group input-group-lg">
						<span class="input-group-addon">
							<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
						</span>
						<input type="text" class="form-control" id="signup_lname" name="lname" placeholder="Your last name" value="${user.lname}">
					</div>
					<span class="glyphicon form-control-feedback ${user == null ? '' : (user.errors['lname'] == null ? 'glyphicon-ok' : 'glyphicon-remove')}" aria-hidden="true"></span>
					<span id="signup_fname_status" class="sr-only">${user == null ? '' : (user.errors['lname'] == null ? '(success)' : '(error)')}</span>
					<c:if test="${user.errors['lname'] != null}">
						<div class="text-danger text-center">
							<c:forEach var="error" items="${user.errors['lname'] }">
								<small>${error}</small><br />
							</c:forEach>
						</div>
					</c:if>
				</div>
				
				
				<%-- THIS PART REQUEST FOR THE EMAIL OF THE SIGNUP FORM --%>
				<div class="form-group ${user == null ? '' : (user.errors['email'] == null ? 'has-success has-feedback' : 'has-error has-feedback')}">
					<label class="control-label" for="signup_email">Your email address:</label>
					<div class="input-group input-group-lg">
						<span class="input-group-addon">
							<span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>
						</span>
						<input type="email" class="form-control" id="signup_email" name="email" placeholder="your.email@cafeterosclub.ca" value="${user.email}">
					</div>
					<span class="glyphicon form-control-feedback ${user == null ? '' : (user.errors['email'] == null ? 'glyphicon-ok' : 'glyphicon-remove')}" aria-hidden="true"></span>
					<span id="signup_fname_status" class="sr-only">${user == null ? '' : (user.errors['email'] == null ? '(success)' : '(error)')}</span>
					<c:if test="${user.errors['email'] != null}">
						<div class="text-danger text-center">
							<c:forEach var="error" items="${user.errors['email'] }">
								<small>${error}</small><br />
							</c:forEach>
						</div>
					</c:if>
				</div>
				
				
				<%-- THIS PART REQUEST FOR THE PASSWORD OF THE SIGNUP FORM --%>
				<div class="form-group ${user != null and user.errors['password'] != null ? 'has-error has-feedback' : ''}">
					<label class="control-label" for="signup_password">Your password:</label>
					<div class="input-group input-group-lg">
						<span class="input-group-addon">
							<span class="glyphicon glyphicon-lock" aria-hidden="true"></span>
						</span>
						<input type="password" class="form-control" id="signup_password" name="password" placeholder="Your password">
					</div>
					<span class="glyphicon form-control-feedback ${user != null and user.errors['password'] != null ? 'glyphicon-remove' : ''}" aria-hidden="true"></span>
					<span id="signup_fname_status" class="sr-only">${user != null and user.errors['password'] != null ? '(error)' : ''}</span>
				</div>
				
				<div class="form-group ${user != null and user.errors['password'] != null ? 'has-error has-feedback' : ''}">
					<label class="control-label" for="signup_password_confirm">Your password again:</label>
					<div class="input-group input-group-lg">
						<span class="input-group-addon">
							<span class="glyphicon glyphicon-lock" aria-hidden="true"></span>
						</span>
						<input type="password" class="form-control" id="signup_password_confirm" name="password_confirm" placeholder="Your password again">
					</div>
					<span class="glyphicon form-control-feedback ${user != null and user.errors['password'] != null ? 'glyphicon-remove' : ''}" aria-hidden="true"></span>
					<span id="signup_fname_status" class="sr-only">${user != null and user.errors['password'] != null ? '(error)' : ''}</span>
					<c:if test="${user.errors['password'] != null}">
						<div class="text-danger text-center small">
							<c:forEach var="error" items="${user.errors['password'] }">
								${error}<br />
							</c:forEach>
						</div>
					</c:if>
				</div>
				
				
				<%-- ACCEPT AGREEMENT OF THE CLUB --%>
				<div class="${user == null ? '' : (user.errors['agreement'] == null ? 'has-success' : 'has-error') }">
					<div class="checkbox">
						<label><input type="checkbox" name="agreement" value="accepted">I agree with terms and conditions.</label>
						<c:if test="${user.errors['agreement'] != null}">
							<div class="text-danger">
								<c:forEach var="error" items="${user.errors['agreement'] }">
									<small>${error}</small><br />
								</c:forEach>
							</div>
						</c:if>
					</div>
				</div>
				
				
				<button type="submit" class="btn btn-primary btn-lg btn-block" name="form_name" value="signup">Sign Up</button>
				<c:if test="${user.errors['signup'] != null}">
					<div class="alert alert-danger">
						<c:forEach var="error" items="${user.errors['signup'] }">
							<small>${error}</small><br />
						</c:forEach>
					</div>
				</c:if>
			</form>
			
		</article>
		
		<%--================================================================================================================================
		***  LOGIN TAB ***
		=====================================================================================================================================--%>
		<article id="login" style="${signupError == null and forgetPasswordError == null ? '' : 'display: none;' }">
			<form method="post" action="j_security_check" onsubmit="return checkForEmail()">
				<div class="form-group">
					<label for="login_email">Your email:</label>
					<div class="input-group input-group-lg">
						<span class="input-group-addon">
							<span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>
						</span>
						<input type="text" class="form-control" id="login_email" name="j_username" placeholder="your.email@cafeterosclub.ca">	
					</div>
				</div>
				<div class="form-group">
					<label for="login_password"><span class="" aria-hidden="true"></span> Your password:</label>
					<div class="input-group input-group-lg">
						<span class="input-group-addon">
							<span class="glyphicon glyphicon-lock" aria-hidden="true"></span>
						</span>
						<input class="form-control" type="password" id="login_password" name="j_password" placeholder="Password">
					</div>
					
				</div>
				
				<button type="submit" class="btn btn-primary btn-lg btn-block">Log In</button>
			</form>
			<div id="ajaxResult_validateMail"></div>
			<c:if test="${loginError != null}">
				<div class="alert alert-danger text-center">
					<strong>Login error: </strong>${loginError}
				</div>
			</c:if>
			
		</article>
		
<%--================================================================================================================================
		***  LOGIN TAB ***
=====================================================================================================================================--%>		
		
		<article id="forget_password" style="${forgetPasswordError == null ? 'display: none;' : '' }">
			<form method="post" action="${pageContext.request.contextPath}/passwordRecovery" name="passwordRecovery">
				<div class="form-group">
					<label for="forget_password_email">Your email address:</label>
					<div class="input-group input-group-lg">
						<span class="input-group-addon">
							<span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>
						</span>
						<input type="email" class="form-control" id="forget_password_email" name="email" placeholder="your.email@cafeterosclub.ca">
					</div>
				</div>
				<button type="submit" class="btn btn-primary btn-lg btn-block" name="submit" value="passwordRecovery">Get new password</button>
			</form>
			<c:if test="${forgetPasswordError != null }">
				<div class="alert alert-danger text-center">
					<strong>Error while reseting password: </strong> ${forgetPasswordError}
				</div>
			</c:if>
		</article>
	</section>
</div>
</c:set>


<jsp:include page="/WEB-INF/view/templates/template.jsp" />