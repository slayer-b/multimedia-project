<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../../messages/error.jsp" %>
<%@include file="../../messages/help.jsp" %>
<div align="center"><h1><a href="" class="head">${page.name}</a></h1></div>
<div align="center" class="wallpaper_random">
<c:forEach items="${content_data}" var="row" varStatus="i"><a href="${pageContext.servletContext.contextPath}/index.htm?id_pages_nav=${row.id_pages}&amp;id_photo_nav=${row.id}"><img alt="${row.title}" src="${pageContext.servletContext.contextPath}/images/wallpaper/small/${row.folderAndName}" title="${row.title}"></a></c:forEach>
</div>