<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<%@ include file="/WEB-INF/jspf/messages/error.jsp"%>
<%@ include file="/WEB-INF/jspf/messages/help.jsp" %>

<div align="center" class="form_head">Регистрация пользователя</div>
<br>
<div align="center">* - обязательно заполнить</div>
<form:form method="post" commandName="content_data" action="?action=register" >
<table align="center">
    <tr align="right"><td>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" value="Отмена">
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" value="Сохранить">
    </td></tr>
    <tr><td align="center"><table>
		<tr><td class=bold>Имя&nbsp;*:&nbsp;<form:errors path="name" cssClass="error_msg"/><br></td></tr>
		<tr><td align="center">
			<form:input path="name" cssClass="long_text_area"/>
		</td></tr>
    </table></td></tr>
    <tr><td align="center"><table>
		<tr><td class=bold>Логин&nbsp;*:&nbsp;<form:errors path="login" cssClass="error_msg"/><br></td></tr>
		<tr><td align="center">
			<form:input path="login" cssClass="long_text_area"/>
		</td></tr>
    </table></td></tr>
    <tr><td align="center"><table>
		<tr><td class=bold>Пароль&nbsp;*:&nbsp;<form:errors path="password" cssClass="error_msg"/><br></td></tr>
		<tr><td align="center">
			<form:password path="password" cssClass="long_text_area"/>
		</td></tr>
    </table></td></tr>
    <tr><td align="center"><table>
		<tr><td class=bold>Пароль повтор&nbsp;*:&nbsp;<form:errors path="password_repeat" cssClass="error_msg"/><br></td></tr>
		<tr><td align="center">
			<form:password path="password_repeat" cssClass="long_text_area"/>
		</td></tr>
    </table></td></tr>
    <tr><td align="center"><table>
		<tr><td class=bold>E-mail&nbsp;*:&nbsp;<form:errors path="email" cssClass="error_msg"/><br></td></tr>
		<tr><td align="center">
			<form:input path="email" cssClass="long_text_area"/>
		</td></tr>
    </table></td></tr>
    <tr align="right"><td>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" value="Отмена">
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" value="Сохранить">
    </td></tr>
</table>
</form:form>