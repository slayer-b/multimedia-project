<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="spring"%>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/scripts/script.js"></script>
<script type="text/javascript">var text='Вы действительно хотите удалить эту запись?';</script>
<spring:form method="post" action="?do=multiUpdate" commandName="content_data">
<table align="center">
<%-- the page that handles error--%>
<tr align="center"><td><%@ include file="../../messages/error.jsp" %></td></tr>
<tr align="center"><td><%@ include file="../../messages/help.jsp" %></td></tr>
<tr><td><a href="?do=insert" title="Добавить" class="link"><img alt="добавить" border="0" src="${pageContext.servletContext.contextPath}/img/add.jpg">
</a><a class="link" href="?do=insert" title="Добавить">Добавить</a></td></tr>

<tr><td><table align="center" border="1" cellpadding="2" cellspacing="0"  style='border-collapse: collapse;'>
<%--header--%>
<tr class="form_head_row">
<td align="left" width="30">ID</td>
<td align="left">Код</td>
<td align="left">Текст</td>
<td align="center"><img alt="Сортировка" title="Сортировка" src="${pageContext.servletContext.contextPath}/img/prior.gif" align="middle"></td>
<td align="center" width="45">Активный</td>
<td align="center" width="70">Выполнить</td>
</tr>
<%--row data--%>
<c:forEach begin="0" end="${content_data.size}" varStatus="i">
<c:if test="${i.index mod 2==0}"><c:set var="tr_class" value="form_row_0"/></c:if>
<c:if test="${i.index mod 2==1}"><c:set var="tr_class" value="form_row_1"/></c:if>
<%--this hidden field is used to store id's of shown pages--%>
<input type="hidden" name="id" value="${content_data.id[i.index]}">
<input type="hidden" name="_marker" id="marker.${i.index}" value="1">
<tr class="${tr_class}">
<td>${content_data.id[i.index]}</td>
<td><spring:input path="code[${i.index}]" cssStyle="width:150px;"/></td>
<td><spring:input path="text[${i.index}]" cssStyle="width:150px;"/></td>
<td><spring:input path="sort[${i.index}]" cssStyle="width:50px;"/></td>
<td align="center"><spring:checkbox path="active[${i.index}]" value="1"/></td>
<td align="center">
<%--next line: 1-st argument is an url of page that will be opened on click--%>
<a href="javascript:confirmation_('?do=delete&amp;id=${content_data.id[i.index]}&amp;action=delete',text)" title="удалить"><img alt="удалить" border="0" src="${pageContext.servletContext.contextPath}/img/kill.gif"></a>
</td>
</tr>
</c:forEach>
</table></td></tr>
<input type=hidden name="action" value="multiUpdate">
<tr><td align="right">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" value="Отмена">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" value="Сохранить">
</td></tr>
</table>
</spring:form>