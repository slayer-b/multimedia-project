<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<form:form action="?do=insert&id_pages_nav=${param['id_pages_nav']}" method="post" commandName="content_data">
<table align="center">
    <tr><td align=center class="form_head">Добавить фразу</td></tr>
    <tr><td align=center><br>* - обязательно заполнить</td></tr>
	<tr align="center"><td><%@ include file="../../messages/error.jsp"%></td></tr>
	<tr align="center"><td><%@ include file="../../messages/help.jsp"%></td></tr>
    <input type=hidden name="action" value="insert">
    <tr><td align="center"><table>
		<tr><td class=bold>Текст *:&nbsp;<form:errors path="text" cssClass="error_msg"/></td></tr>
		<tr><td align="center"><form:input path="text" cssClass="long_text_area"/></td></tr>
    </table></td></tr>
    <tr><td align="center"><table>
		<tr><td class=bold>Категория *:&nbsp;<form:errors path="id_pages" cssClass="error_msg"/><br></td></tr>
		<tr><td align="center">
			<form:select path="id_pages" cssClass="long_text_area" items="${categories}" itemLabel="name" itemValue="id"/>
		</td></tr>
    </table></td></tr>
    <tr><td align="center">
		<div class=bold>Sort *:&nbsp;<form:errors path="sort" cssClass="error_msg"/>&nbsp;<form:input path="sort" cssClass="sort_edit"/></div>
        <div class=bold>Страница *:&nbsp;<form:errors path="useInPages" cssClass="error_msg"/>&nbsp;<form:checkbox path="useInPages"/></div>
        <div class=bold>Элемент *:&nbsp;<form:errors path="useInItems" cssClass="error_msg"/>&nbsp;<form:checkbox path="useInItems"/></div>
    </td></tr>
    <tr align="right"><td><input type="submit" value="Сохранить"></td></tr>
</table>
</form:form>