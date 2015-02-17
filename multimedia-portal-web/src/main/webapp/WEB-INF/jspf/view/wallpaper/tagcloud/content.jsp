<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="common"%>
<%@include file="../../../messages/error.jsp" %>
<%@include file="../../../messages/help.jsp" %>
<div align="left" class="walls_tag_res">По запросу &quot;${content_data.tag}&quot; найдено элементов - ${content_data.pager.itemsCount}.</div>
<div align="center" class="walls_small"><c:forEach items="${content_data.data}" var="item"><%@include file="../../wallpaper/parts/wallpaper_tag.jspf" %></c:forEach></div>
<div align="center"><common:pager count="${content_data.pager}"/></div>