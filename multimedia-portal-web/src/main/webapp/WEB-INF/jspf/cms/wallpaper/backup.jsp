<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/scripts/script.js"></script>
<script type="text/javascript">var text='Вы действительно хотите удалить эту запись?';</script>
<center>Ниже отображены те фото для которых небыло ни одного фото(их можно удалить из базы)</center>

<%@ include file="../../messages/error.jsp" %>
<%@ include file="../../messages/help.jsp" %>

<form action="index.htm?do=deleteMulti" method="post">
<table align="center">
<tr><td>
<table class="form_head_row" align="center" border="1" cellpadding="2" cellspacing="0"  style='border-collapse: collapse;'>
<tr><td>
ID
</td><td>
имя
</td><td align="center">
действия
</td></tr>
<c:forEach items="${content_data}" var="row" varStatus="i">
<c:if test="${i.index mod 2==0}"><c:set var="tr_class" value="form_row_0"/></c:if>
<c:if test="${i.index mod 2==1}"><c:set var="tr_class" value="form_row_1"/></c:if>
<input type="hidden" name="id" value="${row.id}">
<tr class="${tr_class}"><td>
${row.id}
</td><td>
${row.name}
</td><td align="center">
<a href="index.htm?do=update&amp;id=${row.id}&amp;id_pages_nav=${param['id_pages_nav']}" title="редактировать"><img alt="редактировать" border="0" src="${pageContext.servletContext.contextPath}/img/options.jpg"></a>
<a href="javascript:confirmation_('index.htm?do=delete&amp;id=${row.id}&amp;action=delete',text)" title="удалить"><img alt="удалить" border="0" src="${pageContext.servletContext.contextPath}/img/kill.gif"></a>
</td></tr>
</c:forEach>
</table></td></tr>
<input type=hidden name="action" value="deleteMulti">
<tr><td align="right">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" value="Отмена">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" value="Удалить все">
</td></tr>
</table>
</form>
<center>Done in ${time} ms</center>