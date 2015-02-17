<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<spring:form method="post" commandName="content_data" action="index.htm?id_pages_nav=${param['id_pages_nav']}">
<table width="100%">
    <tr><td align=center class="form_head">Восстановление пароля</td></tr>
	<tr><td align=center><br>* - обязательно заполнить</td></tr>
    <tr align="center"><td><%@ include file="../../messages/error.jsp"%></td></tr>
	<tr align="center"><td><%@ include file="../../messages/help.jsp"%></td></tr>
    <tr><td align="center"><table>
		<tr><td class=bold>E-mail *:<spring:errors path="email" cssClass="error_msg"/><br></td></tr>
		<tr><td align="center"><spring:input path="email" cssClass="long_text_area"/></td></tr>
    </table></td></tr>
    <tr><td align="center"><table>
		<tr><td class=bold>Код *:</td>
		<td><input type=text name="antispam_code" maxlength='4' class=user_field_text_spam></td>
		<td><img alt="включите рисунки чтобы увидеть защиный код" title="Введите код, который изображен на картинке" src="${pageContext.servletContext.contextPath}/images/code.jpg"></td></tr>
    </table></td></tr>
    <input type=hidden name="action" value="insert">
    <tr align="right"><td><input type="submit" value="Восстановить"></td></tr>
</table>
</spring:form>