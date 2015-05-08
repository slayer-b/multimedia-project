<%@ page import="com.multimedia.security.model.User" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="generic" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${url.optimization}"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/styles1.css"/>">
<%--<link rel="stylesheet" type="text/css" href="<c:url value='/scripts/ui/css/ui-lightness/jquery-ui-1.9.2.custom.min.css'/>">--%>
<%--<link rel="stylesheet" type="text/css" href="<c:url value="/styles/bootstrap.min.css"/>">--%>

<script async type="text/javascript" src="<c:url value='/scripts/jquery-1.8.3.min.js'/>"></script>
<script async type="text/javascript" src="<c:url value='/scripts/ui/js/jquery-ui-1.9.2.custom.min.js'/>"></script>
<script async type="text/javascript" src="<c:url value='/scripts/script1.js'/>"></script>
<%--<script type="text/javascript" src="<c:url value='/scripts/bootstrap.min.js'/>"></script>--%>
<script type="text/javascript">
var context_path="${pageContext.servletContext.contextPath}";
var module_name="${page.moduleName}";
var module_params="${page_params}";
</script>
<%-- icon in url begin --%>
<link rel='icon' href='<c:url value="/favicon.png"/>' type='image/x-icon'>
<link rel="shortcut icon" href="<c:url value="/favicon.ico"/>">
<%-- icon in url end --%>
<%-- rss --%>
<link rel="alternate" title="Новые обои на download-multimedia.com" href="${pageContext.servletContext.contextPath}/rss.xml" type="application/rss+xml">
<%-- rss --%>
</head>

<fmt:setBundle basename="/messages/template/template" var="template"/>

<body>
<div align="right"><generic:locales/></div>
<%@include file="../../jspf/view/parts/top.jspf" %>
<div class="top_bg3"></div>
<div>${system_add_wallpaper_page}</div>
<c:if test="${system_add_wallpaper_page ne null}">
<div class="add_wallpaper" align="center"><a href="?id_pages_nav=${system_add_wallpaper_page.id}&amp;id_pages=${param['id_pages_nav']}">${system_add_wallpaper_page.name}</a></div>
</c:if>
<table width="100%" cellpadding="0" cellspacing="0"><tr valign="top">
<td class="left">
<%@include file="../../jspf/view/parts/left.jspf" %>
</td>
<%--content with navigation--%>
<td class="content">
<div class="top_nav"><jsp:include page="${url.navigation}"/></div>
<div class="adv_content_top">${advertisement['content_top']}</div>
<div class="info_top">${page.info_top}<c:if test="${(empty page.info_top)&&(not empty url.page_top)}"><jsp:include page="${url.page_top}"/></c:if></div>
<div class="adv_content_top">${advertisement['content_top_post']}</div>
<div class="content"><jsp:include page="${url.content}"/></div>
<div class="adv_content_bottom">${advertisement['content_bottom_pre']}</div>
<div class="info_bottom">${page.info_bottom}<c:if test="${(empty page.info_bottom)&&(not empty url.page_bottom)}"><jsp:include page="${url.page_bottom}"/></c:if></div>
<div class="adv_content_bottom">${advertisement['content_bottom']}</div>
</td>
<%--right--%>
<td class="right">
<%@include file="../../jspf/view/parts/right.jspf" %>
</td></tr></table>
<div class="bottom_bg1"></div>
<div class="bottom"><%@include file="../../jspf/view/parts/bottom.jspf" %></div>
<sec:authorize ifAnyGranted="admin">
<table cellspacing="8px">
<tr align="center"><td>Команды</td><td>Инфо</td></tr><tr><td>
<a target="_blank" href="cms/pages/index.htm?do=update&amp;id=${page.id}&amp;id_pages_nav=${page.id_pages}">Редактировать текущую страницу</a>
<c:choose>
	<c:when test="${not empty big_wallpaper}">
<br><a target="_blank" href="cms/wallpaper/index.htm?do=update&amp;id=${big_wallpaper.id}&amp;id_pages_nav=${page.id}">Редактировать большое фото</a>
	</c:when>
</c:choose>
</td><td>
</td></tr></table>
</sec:authorize>
</body>
</html>