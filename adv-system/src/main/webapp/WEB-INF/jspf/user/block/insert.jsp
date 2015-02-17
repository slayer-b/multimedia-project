<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div align="center" class="form_head">Добавить</div><br>
<div align="center">* - обязательно заполнить</div>

<%@ include file="/WEB-INF/jspf/messages/error.jsp"%>
<%@ include file="/WEB-INF/jspf/messages/help.jsp"%>

<div align="center">
<form:form action="?do=insert${keep_parameters}" method="post" commandName="content_data" htmlEscape="false">
<div class="form_frame">

    <input type="hidden" name="action" value="insert">

	<table>
	<tr><td class="bold">Имя:&nbsp;<form:errors path="name" cssClass="error_msg"/></td></tr>
	<tr><td align="center"><form:input path="name" cssClass="long_text_area"/></td></tr>
	</table>
	<br>
    <div align="right"><input type="submit" value="Сохранить"></div>

</div>
</form:form>
</div>