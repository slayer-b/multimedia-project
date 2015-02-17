<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="misc" tagdir="/WEB-INF/tags"%>

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
    <form:hidden path="content_type"/>

<br/>

	<table>
	<tr><td class="bold">Ширина:&nbsp;<form:errors path="width" cssClass="error_msg"/></td></tr>
	<tr><td align="center"><form:input path="width" cssClass="long_text_area"/></td></tr>
	</table>
	<br/>
	<table>
	<tr><td class="bold">Высота:&nbsp;<form:errors path="height" cssClass="error_msg"/></td></tr>
	<tr><td align="center"><form:input path="height" cssClass="long_text_area"/></td></tr>
	</table>
	<br/>
	<table>
	<tr><td class="bold">Количество обьявлений на странице:&nbsp;<form:errors path="properties.advQuantity" cssClass="error_msg"/></td></tr>
	<tr><td align="center"><form:input path="properties.advQuantity" cssClass="long_text_area"/></td></tr>
	</table>
	<br/>
	<table>
	<tr><td class="bold">Количество отображаемых страниц:&nbsp;<form:errors path="properties.maxPages" cssClass="error_msg"/></td></tr>
	<tr><td align="center"><form:input path="properties.maxPages" cssClass="long_text_area"/></td></tr>
	</table>
	<br/>
    <div class="bold"><label for="properties.navVisible1">Показывать навигацию</label>&nbsp;<form:checkbox path="properties.navVisible"/>&nbsp;<form:errors path="properties.navVisible" cssClass="error_msg"/></div>
	<br/>
	<div class="bold">Стиль:&nbsp;<form:errors path="css" cssClass="error_msg"/>
    &nbsp;&nbsp;<misc:link_block_css cssParam="css" cssValue="${content_data.css}"/>
	</div>
	<br/>

    <div align="center"><input type="submit" value="Сохранить"></div>
</div>
</form:form>
</div>