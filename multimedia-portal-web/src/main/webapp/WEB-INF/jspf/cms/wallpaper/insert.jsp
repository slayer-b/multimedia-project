<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div align=center class="form_head">Добавить wallpaper</div><br>
<div align=center>* - обязательно заполнить</div>
<%@ include file="../../messages/error.jsp"%>
<%@ include file="../../messages/help.jsp"%>

<form:form action="?do=insert&id_pages_nav=${param['id_pages_nav']}" method="post" commandName="content_data" enctype="multipart/form-data">

<input type=hidden name="action" value="insert">

<table align="center">
    <tr><td align="center"><table>
		<tr><td class=bold>Описание:&nbsp;<form:errors path="description" cssClass="error_msg"/></td></tr>
		<tr><td align="center"><form:input path="description" cssClass="long_text_area"/></td></tr>
    </table></td></tr>
    <tr><td align="center"><table>
		<tr><td class=bold>Фото *:&nbsp;<form:errors path="content" cssClass="error_msg"/><br></td></tr>
		<tr><td align="center"><input type="file" name="content" size="80px"></td></tr>
    </table></td></tr>
    <tr><td align="center"><table>
		<tr><td class=bold>Имя файла:&nbsp;<form:errors path="name" cssClass="error_msg"/></td></tr>
		<tr><td align="center"><form:input path="name" cssClass="long_text_area"/></td></tr>
    </table></td></tr>
    <tr><td align="center"><table>
        <tr><td class=bold>Папка:&nbsp;<form:errors path="folder" cssClass="error_msg"/></td></tr>
        <tr><td align="center"><form:input path="folder" cssClass="long_text_area"/></td></tr>
    </table></td></tr>
    <tr><td align="center"><table>
		<tr><td class=bold>Категория *:&nbsp;<form:errors path="id_pages" cssClass="error_msg"/><br></td></tr>
		<tr><td align="center">
			<form:select path="id_pages" cssClass="long_text_area" items="${categories_wallpaper_select}" itemLabel="name" itemValue="id"/>
		</td></tr>
    </table></td></tr>
    <tr><td align="center">
<label for="active1">Активный</label>:<form:errors path="active" cssClass="error_msg"/>&nbsp;<form:checkbox path="active" value="1"/>&nbsp;&nbsp;
<label for="optimized1">Оптимизированный</label>:<form:errors path="optimized" cssClass="error_msg"/>&nbsp;<form:checkbox path="optimized" value="1"/>&nbsp;&nbsp;
<label for="optimized_manual1">Оптимизированный вручную</label>:<form:errors path="optimized_manual" cssClass="error_msg"/>&nbsp;<form:checkbox path="optimized_manual" value="1"/>
	</td></tr>
    <tr><td align="center"><table>
		<tr><td class=bold>Титул:&nbsp;<form:errors path="title" cssClass="error_msg"/></td></tr>
		<tr><td align="center"><form:input path="title" cssClass="long_text_area"/></td></tr>
    </table></td></tr>
    <tr><td align="center"><table>
		<tr><td class=bold>Тэги (через запятую):&nbsp;<form:errors path="tags" cssClass="error_msg"/></td></tr>
		<tr><td align="center"><form:input path="tags" cssClass="long_text_area"/></td></tr>
    </table></td></tr>
    <tr align="right"><td><input type="submit" value="Сохранить"></td></tr>
</table>
</form:form>

<c:if test="${content_data.id ne null}"><div align="center"><a target="_blank" href="${pageContext.servletContext.contextPath}/index.htm?id_pages_nav=${content_data.id_pages}&amp;id_photo_nav=${content_data.id}">Перейти к обоям</a></div></c:if>