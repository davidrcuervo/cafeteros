<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

${page.addStyle('<link rel="stylesheet" href="/assets/notes.css">')}
${page.addScript('<script src="/assets/notes.js"></script>')}

<c:set scope="request" var="title" value="${notes.file.name}" />

<c:set scope="request" var="content">
	
	<c:if test="${notes.file.isDirectory()}">
		<h1 class="text-center">Verano 2017</h1>
		
		<table class="table table-striped">
			<c:forEach var="folder" items="${notes.folders}">
				<tr><td>
					<div onclick="divLink('${page.url}/${page.getEncodeUrl(folder)}')" ><span class="glyphicon glyphicon-folder-open"></span> &nbsp; ${folder}</div>
				</td></tr>
			</c:forEach>
			
			<c:forEach var="file" items="${notes.files}">
				<tr><td>
					<div onclick="divLink('${page.url}/${page.getEncodeUrl(file)}')" ><span class="glyphicon glyphicon-file"></span> &nbsp; ${file}</div>
				</td></tr>
			</c:forEach>
		</table>
	</c:if>
	
	<c:if test="${notes.file.isFile()}">
		<div>${notes.print }</div>
		<c:set scope="request" var="nav_fast_button">
			${nav_fast_button}
			<%--
				<a title="edit" href="${page.url}${page.addVar('action', 'edit')}" class="btn btn-default btn-sm" data-toggle="tooltip" data-placement="bottom"><span class="glyphicon glyphicon-pencil"></span></a>
				<a title="play" href="${page.url}${page.addVar('action', 'play')}" target="_blank" class="btn btn-default btn-sm" data-toggle="tooltip" data-placement="bottom" ><span class="glyphicon glyphicon-film"></span></a>
			 --%>
		</c:set>
	</c:if>
	
</c:set>

<jsp:include page="/WEB-INF/jsp/templates/web.jsp"></jsp:include>