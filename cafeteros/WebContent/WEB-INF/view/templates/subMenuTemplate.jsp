<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="content" scope="request">
	<div class="row">
		<div class="col-xs-12 col-sm-3 col-md-3 col-lg-2" style="padding: 0px;">
			<div class="navbar navbar-default" role="navigation">
				<div class="container-fluid">
					<div class="navbar-header">
						<button type="button" class="navbar-toggle collapsed btn-block" data-toggle="collapse" data-target="#subMenu" aria-expanded="false" style="margin: 8px auto; float: none; width: 90%">${subMenuActive} <span class="caret"></span></button>
					</div><!-- /.navbar-header -->
					
					<div class="collapse navbar-collapse" id="subMenu" style="padding: 0px">
						${subMenu}
					</div>
				</div><!-- /.container-fluid -->
			</div>
			
		
		</div>
		<div class="col-xs-12 col-sm-9 col-md-9 col-lg-10">${subContent}</div>
	</div>
</c:set>

<jsp:include page="/WEB-INF/view/templates/template.jsp"/>