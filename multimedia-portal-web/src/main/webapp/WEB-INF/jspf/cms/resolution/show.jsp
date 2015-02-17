<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/scripts/script.js"></script>
<script type="text/javascript">var text='Вы действительно хотите удалить эту запись?';</script>

<form action="?do=multiUpdate" method="post">
<table align="center">
<tr align="center"><td><%@ include file="../../messages/error.jsp" %></td></tr>
<tr align="center"><td><%@ include file="../../messages/help.jsp" %></td></tr>

<tr><td><table cellpadding="0" cellspacing="0" border="0" width="100%"><tr>
<td align="left">
<a href="?do=insert" title="Добавить" class="link">
<img alt="добавить" border="0" src="${pageContext.servletContext.contextPath}/img/add.jpg">
</a><a class="link" href="?do=insert" title="Добавить">Добавить</a>
</td>
</tr></table></td></tr>

<tr><td>
<table align="center" border="1" cellpadding="2" cellspacing="0" style='border-collapse: collapse;'>

<tr class="form_head_row">
<td align="left" width="30">ID</td>
<td align="center">Ширина</td>
<td align="center">Высота</td>
<td align="center" width="70">Выполнить</td>
</tr>

<c:forEach items="${resolutions}" var="item" varStatus="i">
<c:if test="${i.index mod 2==0}"><c:set var="tr_class" value="form_row_0"/></c:if>
<c:if test="${i.index mod 2==1}"><c:set var="tr_class" value="form_row_1"/></c:if>

<input type="hidden" name="_marker" id="marker.${i.index}" value="1">
<input type="hidden" name="id" value="${item.id}">

<tr class="${tr_class}">
<td align="left" width="30">${item.id}</td>
<td align="center"><input class="sort_edit" value="${item.width}" id="width_${item.id}" name="width" type="text"></td>
<td align="center"><input class="sort_edit" value="${item.height}" id="height_${item.id}" name="height" type="text"></td>
<td align="center" width="70">
	<a href="javascript:confirmation_('?do=delete&amp;id=${item.id}&amp;action=delete',text)" title="удалить"><img alt="удалить" border="0" src="${pageContext.servletContext.contextPath}/img/kill.gif"></a>
</td></tr>
</c:forEach>

</table>
</td></tr>

<input type=hidden name="action" value="multiUpdate">
<tr><td align="right">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" value="Отмена">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" value="Сохранить">
</td></tr>

</table>
</form>