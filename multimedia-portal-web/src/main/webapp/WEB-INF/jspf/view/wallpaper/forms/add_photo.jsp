<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<form:form action="index.htm?id_pages_nav=${param['id_pages_nav']}" method="post" commandName="command" enctype="multipart/form-data">
<table width="100%">
    <tr><td align=center class="form_head">${page.title}</td></tr>
    <tr><td align=center><br>* - обязательно заполнить</td></tr>
	<tr align="center"><td><%@ include file="../../../messages/error.jsp"%></td></tr>
	<tr align="center"><td><%@ include file="../../../messages/help.jsp"%></td></tr>
    <input type=hidden name="action" value="addWallpaper">
    <tr><td align="center"><table>
		<tr><td class=bold>Описание *:&nbsp;<form:errors path="description" cssClass="error_msg"/></td></tr>
		<tr><td align="center"><form:input path="description" cssClass="long_text_area"/></td></tr>
    </table></td></tr>
    <tr><td align="center"><table>
		<tr><td class=bold>Фото *:&nbsp;<form:errors path="content" cssClass="error_msg"/><br></td></tr>
		<tr><td align="center"><input type="file" name="content" size="80px"></td></tr>
    </table></td></tr>
    <tr><td align="center"><table>
		<tr><td class=bold>Категория *:&nbsp;<form:errors path="id_pages" cssClass="error_msg"/><br></td></tr>
		<tr><td align="center">
			<form:select path="id_pages" cssClass="long_text_area" items="${categories_wallpaper_select}" itemLabel="name" itemValue="id"/>
		</td></tr>
    </table></td></tr>
    <tr><td align="center"><table>
		<tr><td class=bold>Код *:</td>
		<td><input type=text name="code" maxlength='4' class=antispam_code></td>
		<td><img alt="включите рисунки чтобы увидеть защиный код" title="Введите код, который изображен на картинке" src="${pageContext.servletContext.contextPath}/code.jpg"></td></tr>
    </table></td></tr>
    <tr align="right"><td><input type="submit" value="Сохранить"></td></tr>
    <tr align="center"><td class="banner_content">${banner_content_center.html}</td></tr>
</table>
</form:form>