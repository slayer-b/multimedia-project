<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="misc" tagdir="/WEB-INF/tags"%>

<div align="center" class="form_head">Редактировать</div><br>
<div align="center">* - обязательно заполнить</div>

<%@ include file="/WEB-INF/jspf/messages/error.jsp"%>
<%@ include file="/WEB-INF/jspf/messages/help.jsp"%>

<div>
<textarea style="width:600px; height:50px;">
<script type="text/javascript" src="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.servletContext.contextPath}/js/adv-${pub_id}.js"></script>
</textarea>
</div>

<div align="center">
<form:form action="?do=editLink${keep_parameters}" method="post" commandName="content_data" htmlEscape="false">
<div class="form_frame">

    <input type="hidden" name="action" value="update">
	<input type="hidden" name="page_number" value="${page_number}">
	<form:hidden path="id"/>

<div align="center" class="bold">
<label for="useDefaults1">Создавать новый блок, при входе с незарегистрированного сайта:</label>&nbsp;
&nbsp;&nbsp;<input id="useDefaults1" name="useDefaults" type="checkbox" value="true"${useDefaults?" checked":""}>
<input type="hidden" name="_useDefaults" value="on">
</div>
<br>

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
	<tr><td class="bold">Количество обьявлений на странице:&nbsp;<form:errors path="properties.advQuantity" cssClass="error_msg"/></td></tr>
	<tr><td align="center"><form:input path="properties.advQuantity" cssClass="long_text_area"/></td></tr>
	</table>
	<br>
	<table>
	<tr><td class="bold">Количество отображаемых страниц:&nbsp;<form:errors path="properties.maxPages" cssClass="error_msg"/></td></tr>
	<tr><td align="center"><form:input path="properties.maxPages" cssClass="long_text_area"/></td></tr>
	</table>
	<br>
	<div align="center" class="bold">
		<label for="properties.navVisible1">Показывать навигацию:</label>&nbsp;
	<form:errors path="properties.navVisible" cssClass="error_msg"/>
	&nbsp;&nbsp;<form:checkbox path="properties.navVisible"/>
	</div>
	<br>
	<div class="bold">Стиль:&nbsp;
	<form:errors path="css" cssClass="error_msg"/>
	<%--&nbsp;&nbsp;<form:input path="css" cssClass="long_text_area"/>--%>
    &nbsp;&nbsp;<misc:link_block_css cssParam="css" cssValue="${content_data.css}"/>
	</div>
	<br>
    <div align="center"><input type="submit" value="Сохранить"></div>

</div>
</form:form>
</div>