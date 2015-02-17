<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		  <style type="text/css">
			  .error_msg{color:red;}
		</style>
    </head>
    <body>
        <h1>ERROR</h1>
		<h3><a href="/">back</a></h3>
		<table>
			<tr><td>Запрашиваемая вами страница не найдена на сервере.</td></tr>
			<tr><td>

<form method="post" action="">

<table class="auth" cellpadding="0" cellspacing="2px">

<tr><td class="auth_spacer"></td><td colspan='2'></td></tr>

<c:if test="${requestScope['Authorization.LOGIN_ERROR'] ne null}">
<tr><td></td><td colspan="2" class="error_msg">login error</td></tr>
</c:if>

<tr><td></td>
 <td><font class="auth">Логин</font></td>
 <td><input class="auth" type="text" name="user_login"></td>
</tr>

<tr><td></td>
 <td><font class="auth">Пароль</font></td>
 <td><input class="auth" type="password" name="user_password"></td>
</tr>

<tr><td></td><td colspan="2"><table cellspacing="0" cellpadding="0"><tr>
    <td nowrap="true" width="150px"><input id="auth_box" class="auth_box" type="checkbox" name="user_remember" value="yes">&nbsp;<label for="auth_box" class="auth">Запомнить меня</label></td>
	<td><input type="submit" value="Enter"></td>
</tr></table></td></tr>

<tr><td height="30px"></td></tr>

</table>
</form>
			</td></tr>
		</table>
    </body>
</html>