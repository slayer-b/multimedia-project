<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../../messages/error.jsp" %>
<%@include file="../../messages/help.jsp" %>
<br><div align="center" class="rubric_head">Подразделы</div><br>
<c:choose>
    <c:when test="${pageContext.request.getParameter('view') eq 'new'}">
        <div align="center"><%@include file="parts/wallpaper_main_view_2.jspf" %></div>
    </c:when>
    <c:otherwise>
        <div align="center"><%@include file="parts/wallpaper_main_view.jspf" %></div>
    </c:otherwise>
</c:choose>
