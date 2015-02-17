<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/scripts/script.js"></script>
<script type="text/javascript">var text='Вы действительно хотите удалить эту запись?';</script>

<table align="center">
<tr align="center"><td><%@ include file="../../messages/error.jsp" %></td></tr>
<tr align="center"><td><%@ include file="../../messages/help.jsp" %></td></tr>

<tr><td>

<table align="center" border="1" cellpadding="2" cellspacing="0" style='border-collapse: collapse;'>

<tr class="form_head_row">
<td align="left" width="30">ID</td>
<td align="left">email</td>
<td align="center">name</td>
<td align="center" title="Последнее время доступа">Время</td>
<td align="center" width="40" title="сводные таблицы">Просмотр</td>
<td align="center" width="40">Выполнить</td>
</tr>

<c:forEach items="${users}" var="row"  varStatus="i">
<c:if test="${i.index mod 2==0}"><c:set var="tr_class" value="form_row_0"/></c:if>
<c:if test="${i.index mod 2==1}"><c:set var="tr_class" value="form_row_1"/></c:if>
<tr class="${tr_class}">
<td align="left">${row.id}</td>
<td align="left">${row.email}</td>
<td align="center">${row.name}</td>
<td align="center">${row.last_accessed}</td>
<td align="center"><a class="link" href="../wallpaper/index.htm?do=view&amp;id_users_nav=${row.id}" title="Фото">Фото</a></td>
<td align="center">
	<a href="?do=update&amp;id=${row.id}" title="редактировать"><img alt="редактировать" border="0" src="${pageContext.servletContext.contextPath}/img/options.jpg"></a>
	<a href="javascript:confirmation_('?do=delete&amp;id=${row.id}&amp;action=delete',text)" title="удалить"><img alt="удалить" border="0" src="${pageContext.servletContext.contextPath}/img/kill.gif"></a>
</td>
</tr>
</c:forEach>

</table>
</td></tr>

</table>