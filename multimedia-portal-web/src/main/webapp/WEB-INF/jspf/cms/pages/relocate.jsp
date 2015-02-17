<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<table border="1" align="center" cellpadding="0" cellspacing="26">
<tr align="center" class="form_head"><td class="no_border">${editForm_topHeader}</td></tr>
<tr align="center"><td class="no_border"><%@ include file="../../messages/error.jsp" %></td></tr>
<tr align="center"><td class="no_border"><%@ include file="../../messages/help.jsp" %></td></tr>

<tr><td class="no_border">
<table>
<tr><td>
	<table cellpadding="0" cellspacing="0" width="100%" style="border-collapse:collapse;"><tr>
		<td align="left" bgcolor="#ffffff" width="0"></td>
		<td width="30"></td>
		<td class="link" style="border:1px solid black;">&nbsp;/&nbsp;</td>
		<td width="20" align="center" style="border:1px solid black;">
			<a href="?id=${param['id']}&amp;action=relocate&amp;do=relocate&amp;id_pages_nav=${param['id_pages_nav']}" title="Переместить" class="border"><img src="${pageContext.servletContext.contextPath}/img/replace.gif" alt="Переместить" border="0"></a>
		</td>
	</tr></table>
</td></tr>

<c:forEach items="${pages}" var="row">
<tr><td>
	<table cellpadding="0" cellspacing="0" width="100%" style="border-collapse:collapse;"><tr>
		<td align="left" bgcolor="#ffffff" width="${row.layer*30+30}"></td>
		<td width="30" style="border:1px solid black;">${row.id}</td>
		<td class="link" style="border:1px solid black;">&nbsp;${row.name}&nbsp;</td>
		<td width="20" align="center" style="border:1px solid black;">
			<a href="?id=${param['id']}&amp;id_pages=${row.id}&amp;action=relocate&amp;do=relocate&amp;id_pages_nav=${param['id_pages_nav']}" title="Переместить"><img src="${pageContext.servletContext.contextPath}/img/replace.gif" alt="Переместить" border="0"></a>
		</td>
	</tr></table>
</td></tr>
</c:forEach>

</table>
</td></tr>

</table>