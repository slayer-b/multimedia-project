<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="generic" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.00 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd"--%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html">
<meta http-equiv='Cache-Control' content="no-cache">
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/styles.css"/>">
<%--<link rel="stylesheet" type="text/css" href="<c:url value="/styles/bootstrap.min.css"/>">--%>
<title>${title}</title>
<script type="text/javascript" src="<c:url value="/scripts/jquery-1.8.3.min.js"/>"></script>
<%--<script type="text/javascript" src="<c:url value="/scripts/bootstrap.min.js"/>"></script>--%>
</head>
<body class="main_frame">
<%-- top --%>
<div align="right"><generic:locales/></div>
<table width="100%">
<tr>
<td width="33%" align="left" valign="top">
<div class="user_auth">Здравствуйте, <sec:authentication property="principal.name"/></div>
<a class="link" href="<c:url value='/logout'/>" title="Выйти">Выйти</a>
</td>
<td width="33%" align="center"><a href="index.htm?do=prepare" class="head_top" title="переход в корень раздела">${top_header}</a></td>
<td width="33%" align="right">
<a href="http://netstorm.com.ua"><img alt="Netstorm - Локальная система управления" src="${pageContext.servletContext.contextPath}/img/netstorm_logo.jpg"></a>
</td>
</tr>
</table>

<%--place for navigation--%>
<c:if test="${not empty navigation_url}"><div align="left"><jsp:include page="${navigation_url}"/></div></c:if>
<%--here the main content should be placed--%>
<div><jsp:include page="${content_url}"/></div>
</body>
</html>
