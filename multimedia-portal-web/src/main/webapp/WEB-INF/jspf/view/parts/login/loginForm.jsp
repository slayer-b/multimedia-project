<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize access="permitAll" var="canAccess"/>

<div class="auth">
<c:choose>
<c:when test="${canAccess}">
Not ready yet
</c:when>
<c:otherwise>
<%@ include file="loginedForm.jspf"%>
</c:otherwise>
</c:choose>
</div>