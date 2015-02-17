<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div align="center" class="form_head">Редактировать</div><br>
<div align="center">* - обязательно заполнить</div>

<%@ include file="/WEB-INF/jspf/messages/error.jsp"%>
<%@ include file="/WEB-INF/jspf/messages/help.jsp"%>

<div align="center">
<form:form action="?do=update${keep_parameters}" method="post" commandName="content_data" htmlEscape="false">
<div class="form_frame">

    <input type="hidden" name="action" value="update">
	<input type="hidden" name="page_number" value="${page_number}">
	<form:hidden path="id"/>

	<div>pub_id:${content_data.pub_id}</div>

	<table>
	<tr><td class="bold">Ширина:&nbsp;<form:errors path="width" cssClass="error_msg"/></td></tr>
	<tr><td align="center"><form:input path="width" cssClass="long_text_area"/></td></tr>
	</table>
	<br>
	<table>
	<tr><td class="bold">Высота:&nbsp;<form:errors path="height" cssClass="error_msg"/></td></tr>
	<tr><td align="center"><form:input path="height" cssClass="long_text_area"/></td></tr>
	</table>
	<br>
	<table>
	<tr><td class="bold">Кол. обьявлений:&nbsp;<form:errors path="advQuantity" cssClass="error_msg"/></td></tr>
	<tr><td align="center"><form:input path="advQuantity" cssClass="long_text_area"/></td></tr>
	</table>
	<br>
	<table>
	<tr><td class="bold">Макс. страниц:&nbsp;<form:errors path="maxPages" cssClass="error_msg"/></td></tr>
	<tr><td align="center"><form:input path="maxPages" cssClass="long_text_area"/></td></tr>
	</table>
	<br>
	<div align="center" class="bold">Показывать навигацию:&nbsp;
	<form:errors path="navVisible" cssClass="error_msg"/>
	&nbsp;&nbsp;<form:checkbox path="navVisible"/>
	</div>
	<br>
    <div align="right"><input type="submit" value="Сохранить"></div>

</div>
</form:form>
</div>