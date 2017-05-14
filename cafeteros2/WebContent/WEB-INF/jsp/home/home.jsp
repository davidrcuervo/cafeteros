<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set scope="request" var="title" value="Home" />

<c:set scope="request" var="content">
hello word
</c:set>
<jsp:include page="/WEB-INF/jsp/templates/web.jsp"></jsp:include>