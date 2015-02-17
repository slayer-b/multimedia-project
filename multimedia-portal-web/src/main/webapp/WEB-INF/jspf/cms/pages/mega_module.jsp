<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/styles/cms_mega.css">
<table align="center">
<%-- the page that handles error--%>
<tr align="center"><td><%@ include file="../../messages/error.jsp" %></td></tr>
<tr align="center"><td><%@ include file="../../messages/help.jsp" %></td></tr>
<tr><td>
<c:forEach items="${content_data}" var="row" varStatus="i">
<div style="padding:2px;"><table align="center" cellpadding="2px" cellspacing="0" width="100%" style="background-color:#f0f0f0;border-collapse:collapse;"><tr>
<td align="left" bgcolor="#ffffff" width="${row.layer*30+30}"></td>
<td width="30px" class="border">${row.id}</td>
<td align="left" class="border">&nbsp;<a<c:if test="${not row.active}"> class="inactive"</c:if> target="_blank" href="../../index.htm?id_pages_nav=${row.id}">${row.name}</a>&nbsp;</td>
<td width="60px" class="border" align="right"><a class="<c:if test="${row.pseudonymsCount lt 1}">OF_empty</c:if>" href="../pagesPseudonym/index.htm?do=insert&id_pages_nav=${row.id}&id_pages=${row.id}">+ОПТ(${row.pseudonymsCount})</a></td>
<c:if test="${row.last}"><td class="border" width="90px" align="right"><a class="<c:if test="${row.wallpaperCount lt 1}">add_wallpaper_empty</c:if>" href="../wallpaper/index.htm?do=insertMulti&amp;id_pages_one=${row.id}">+wallpaper(${row.wallpaperCount})</a></td></c:if>
<td class="border" width="50px" align="right"><a href="index.htm?id_pages_nav=${row.id}&amp;do=optimize&amp;action=optimize" class="<c:if test="${row.optimized eq false}">optimize_wallpaper</c:if>">ОПТ</a></td>
<td class="border" width="70px" align="right"><a href="index.htm?id_pages_nav=${row.id}&amp;do=reset_optimize&amp;action=optimize">снять ОПТ</a></td>
</tr></table></div>
</c:forEach>
</td></tr>
</table>