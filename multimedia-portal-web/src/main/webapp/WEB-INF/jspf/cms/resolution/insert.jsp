<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form:form method="post" commandName="command" action="?do=insert" >
<table align="center">
    <tr><td align=center class="form_head">${editForm_topHeader}</td></tr>
    <tr><td align=center><br>* - обязательно заполнить</td></tr>
    <%-- the page that handles error--%>
    <tr align="center"><td><%@ include file="../../messages/error.jsp"%></td></tr>
	<tr align="center"><td><%@ include file="../../messages/help.jsp" %></td></tr>
	<tr align="center"><td><form:errors cssClass="error_msg"/></td></tr>
    <tr align="right"><td>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" value="Отмена">
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" value="Сохранить">
    </td></tr>
    <input type=hidden name="action" value="insert">
    <tr><td align="center"><table>
		<tr><td class="bold">Ширина&nbsp;*:&nbsp;<form:errors path="width" cssClass="error_msg"/></td></tr>
		<tr><td align="center">
			<form:input path="width"/>
		</td></tr>
    </table></td></tr>
    <tr><td align="center"><table>
		<tr><td class="bold">Высота&nbsp;*:&nbsp;<form:errors path="height" cssClass="error_msg"/></td></tr>
		<tr><td align="center">
			<form:input path="height"/>
		</td></tr>
    </table></td></tr>
    <tr align="right"><td>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" value="Отмена">
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" value="Сохранить">
    </td></tr>
</table>
</form:form>