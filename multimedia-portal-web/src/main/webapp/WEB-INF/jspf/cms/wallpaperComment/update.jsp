<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div align="center" class="form_head">Изменить</div><br>
<div align="center">* - обязательно заполнить</div>

<%@ include file="../../messages/error.jsp"%>
<%@ include file="../../messages/help.jsp"%>

<div align="center">
<form:form action="?do=update${keep_parameters}" method="post" commandName="content_data" htmlEscape="false">
<div class="form_frame">

	<input type="hidden" name="page_number" value="${page_number}">
    <input type="hidden" name="action" value="update"/>
	<form:hidden path="id"/>

    <table>
		<tr><td class="bold">Текст:&nbsp;<form:errors path="text" cssClass="error_msg"/></td></tr>
		<tr><td align="center"><form:textarea path="text" htmlEscape="false" cssClass="long_text_area"/></td></tr>
    </table>
	<br>
    <div>
<font class="bold">Wallpaper</font> *:&nbsp;<form:errors path="id_photo" cssClass="error_msg"/>&nbsp;${content_data.id_photo}
<form:hidden path="id_photo"/>
	</div>
	<br>
    <div>
<font class="bold">Дата создания</font>: <form:errors path="creationTime" cssClass="error_msg"/>
${content_data.creationTime}
<form:hidden path="creationTime"/>
	</div>
	<br>
    <div align="right"><input type="submit" value="Сохранить"></div>
</div>
</form:form>
</div>