<%@ page import="com.multimedia.security.model.User" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="generic" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%long time_all = System.currentTimeMillis();%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%long time_opt = System.currentTimeMillis();%>
<jsp:include page="${url.optimization}"/>
<% time_opt = System.currentTimeMillis() - time_opt; %>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/styles1.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/scripts/ui/css/ui-lightness/jquery-ui-1.9.2.custom.min.css'/>">
<%--<link rel="stylesheet" type="text/css" href="<c:url value="/styles/bootstrap.min.css"/>">--%>
<!--[if IE 6]>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/styles1-ie7.css"/>">
<![endif]-->
<!--[if IE 7]>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/styles1-ie7.css"/>">
<![endif]-->

<script type="text/javascript" src="<c:url value='/scripts/jquery-1.8.3.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/ui/js/jquery-ui-1.9.2.custom.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/script1.js'/>"></script>
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
<%long time_top = System.currentTimeMillis();%>
<%@include file="../../jspf/view/parts/top.jspf" %>
<% time_top = System.currentTimeMillis() - time_top; %>
<div class="top_bg3"></div>
<div>${system_add_wallpaper_page}</div>
<c:if test="${system_add_wallpaper_page ne null}">
<div class="add_wallpaper" align="center"><a href="?id_pages_nav=${system_add_wallpaper_page.id}&amp;id_pages=${param['id_pages_nav']}">${system_add_wallpaper_page.name}</a></div>
</c:if>
<table width="100%" cellpadding="0" cellspacing="0"><tr valign="top">
<td class="left">
<%long time_left = System.currentTimeMillis();%>
<%@include file="../../jspf/view/parts/left.jspf" %>
<% time_left = System.currentTimeMillis() - time_left; %>
</td>
<%--content with navigation--%>
<td class="content">
<%long time_nav = System.currentTimeMillis();%>
<div class="top_nav"><jsp:include page="${url.navigation}"/></div>
<% time_nav = System.currentTimeMillis() - time_nav; %>
<div class="adv_content_top">${advertisement['content_top']}</div>
<%long time_i_top = System.currentTimeMillis();%>
<div class="info_top">${page.info_top}<c:if test="${(empty page.info_top)&&(not empty url.page_top)}"><jsp:include page="${url.page_top}"/></c:if></div>
<% time_i_top = System.currentTimeMillis() - time_i_top; %>
<div class="adv_content_top">${advertisement['content_top_post']}</div>
<%long time_content = System.currentTimeMillis();%>
<div class="content"><jsp:include page="${url.content}"/></div>
<% time_content = System.currentTimeMillis() - time_content; %>
<div class="adv_content_bottom">${advertisement['content_bottom_pre']}</div>
<%long time_i_bottom = System.currentTimeMillis();%>
<div class="info_bottom">${page.info_bottom}<c:if test="${(empty page.info_bottom)&&(not empty url.page_bottom)}"><jsp:include page="${url.page_bottom}"/></c:if></div>
<% time_i_bottom = System.currentTimeMillis() - time_i_bottom; %>
<div class="adv_content_bottom">${advertisement['content_bottom']}</div>
</td>
<%--right--%>
<td class="right">
<%long time_right = System.currentTimeMillis();%>
<%@include file="../../jspf/view/parts/right.jspf" %>
<% time_right = System.currentTimeMillis() - time_right; %>
</td></tr></table>
<div class="bottom_bg1"></div>
<%long time_bottom = System.currentTimeMillis();%>
<div class="bottom"><%@include file="../../jspf/view/parts/bottom.jspf" %></div>
<% time_bottom = System.currentTimeMillis() - time_bottom; %>
<% time_all = System.currentTimeMillis() - time_all; %>
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
<div>Время отображения страницы: <%= time_all%></div>
<div>Время отображения оптимизации <%= time_opt%></div>
<div>Время отображения шапки <%= time_top%></div>
<div>Время отображения лева <%= time_left%></div>
<div>Время отображения навигации: <%= time_nav%></div>
<div>Время отображения инфо топ <%= time_i_top%></div>
<div>Время отображения контент <%= time_content%></div>
<div>Время отображения инфо боттом <%= time_i_bottom%></div>
<div>Время отображения права <%= time_right%></div>
<div>Время отображения низа <%= time_bottom%></div>
<div>lang = ${pageContext.response.locale}</div>
</td></tr></table>
</sec:authorize>
</body>
</html>