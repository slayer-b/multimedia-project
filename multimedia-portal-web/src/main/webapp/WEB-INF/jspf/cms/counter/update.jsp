<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div align="center" class="form_head">Добавить</div><br>
<div align="center">* - обязательно заполнить</div>

<%@ include file="../../messages/error.jsp"%>
<%@ include file="../../messages/help.jsp"%>

<div align="center">
<form:form action="?do=update${keep_parameters}" method="post" commandName="content_data" htmlEscape="false">
<div class="form_frame">

    <input type="hidden" name="action" value="update">
	<input type="hidden" name="page_number" value="${page_number}">
	<form:hidden path="id"/>

	<table>
	<tr><td class="bold">Имя:&nbsp;<form:errors path="name" cssClass="error_msg"/></td></tr>
	<tr><td align="center"><form:input path="name" cssClass="long_text_area"/></td></tr>
	</table>
	<br>
	<table>
	<tr><td class="bold">Текст:&nbsp;<form:errors path="text" cssClass="error_msg"/></td></tr>
	<tr><td><form:textarea htmlEscape="false" path="text" rows="15" cssClass="long_text_area"/></td></tr>
	</table>
	<br>
	<div align="center" class="bold">Сортировка *:&nbsp;<form:errors path="sort" cssClass="error_msg"/>&nbsp;&nbsp;
	<form:input path="sort" cssClass="sort_edit"/>
	</div>
	<br>
    <div align="right"><input type="submit" value="Сохранить"></div>

</div>
</form:form>
</div>