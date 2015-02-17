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
	<tr><td class="bold">Страница:&nbsp;<form:errors path="id_pages" cssClass="error_msg"/></td></tr>
	<tr><td align="center"><form:input path="id_pages" cssClass="long_text_area"/></td></tr>
	</table>
	<br>
	<table>
	<tr><td class="bold">Реклама:&nbsp;<form:errors path="id_advertisement" cssClass="error_msg"/></td></tr>
	<tr><td><form:select path="id_advertisement" cssClass="long_text_area" items="${advertisements}" itemLabel="name" itemValue="id"/></td></tr>
	</table>
	<br>
	<div align="center" class="bold">
	<label for="useInParent1">Разрешить к показу</label>:&nbsp;<form:errors path="useInParent" cssClass="error_msg"/>&nbsp;&nbsp;<form:checkbox path="useInParent"/>
	</div>
	<br>
	<div align="center" class="bold">
	<label for="useInChildren1">Показывать в детях</label>:&nbsp;<form:errors path="useInChildren" cssClass="error_msg"/>&nbsp;&nbsp;<form:checkbox path="useInChildren"/>
	</div>
	<br>
    <div align="right"><input type="submit" value="Сохранить"></div>

</div>
</form:form>
</div>