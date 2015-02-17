<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form:form method="post" commandName="command" action="?do=insert&id_pages_nav=${param['id_pages_nav']}" >
<table align="center">
    <tr><td align=center class="form_head">${editForm_topHeader}</td></tr>
    <tr><td align=center><br>* - обязательно заполнить</td></tr>
    <%-- the page that handles error--%>
    <tr align="center"><td><%@ include file="../../messages/error.jsp"%></td></tr>
	<tr align="center"><td><%@ include file="../../messages/help.jsp" %></td></tr>
    <tr align="right"><td>
		<a class="link" href="index.htm?id_pages_nav=${param['id_pages_nav']}">[Назад]</a>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" value="Отмена">
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" value="Сохранить">
    </td></tr>
    <input type=hidden name="action" value="insert">
    <tr><td align="center"><table>
		<tr><td class="bold"><form:errors path="id_pages" cssClass="error_msg"/></td></tr>
		<tr><td align="center">
			<form:hidden path="id_pages"/>
		</td></tr>
    </table></td></tr>
    <tr><td align="center"><table>
		<tr><td class=bold>Название&nbsp;*:&nbsp;<form:errors path="name" cssClass="error_msg"/><br></td></tr>
		<tr><td align="center">
			<form:input path="name" cssClass="long_text_area"/>
		</td></tr>
    </table></td></tr>
    <tr><td align="center"><table cellSpacing=0 cellPadding=4 align=center border=1>
		<tr><td class="bold no_border">Поисковая оптимизация</td></tr>
		<tr><td class="bold no_border">Титул:<br>
			<form:input path="title" cssClass="long_text_area"/>
		</td></tr>
		<tr><td class="bold no_border">Описание:<br>
			<form:textarea path="description" rows="6" cssClass="long_text_area"/>
		</td></tr>
		<tr><td class="bold no_border">Ключевые слова:<br>
			<form:textarea path="keywords" rows="6" cssClass="long_text_area"/>
		</td></tr>
    </table></td></tr>
    <tr><td align="center"><table>
        <tr><td class=bold>Содержимое верхнее:</td></tr>
        <tr><td>
            <form:textarea path="info_top" rows="15" cssClass="long_text_area"/>
        </td></tr>
    </table></td></tr>
    <tr><td align="center"><table>
        <tr><td class=bold>Содержимое нижнее:</td></tr>
        <tr><td>
            <form:textarea path="info_bottom" rows="15" cssClass="long_text_area"/>
        </td></tr>
		</table></td></tr>
    <tr><td align="center"><table>
        <tr><td class="bold" align="center">Меню:</td></tr>
        <tr><td align="center"><select name="menu_type"><option selected value="no">нет</select></td></tr>
    </table></td></tr>
    <tr><td align="center"><table>
        <tr><td class="bold" align="center">Тип *:&nbsp;<form:errors path="type" cssClass="error_msg"/></td></tr>
        <tr><td align="center">
			<form:select path="type" items="${types}" itemValue="type"  itemLabel="typeRu"/>
        </td></tr>
    </table></td></tr>
    <tr><td align="center" class=bold>Сортировка *:&nbsp;<form:errors path="sort" cssClass="error_msg"/>&nbsp;
        <form:input path="sort" cssClass="sort_edit"/>&nbsp;&nbsp;Активный:&nbsp;&nbsp;
		<form:select path="active"><form:option value="true" label="да"/><form:option value="false" label="нет"/></form:select>
    </td></tr>
    <tr align="right"><td>
		<a class="link" href="index.htm?id_pages_nav=${param['id_pages_nav']}">[Назад]</a>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" value="Отмена">
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" value="Сохранить">
    </td></tr>
</table>
</form:form>