<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>

<spring:form action="?do=insert" method="post" commandName="content_data" >
<table align="center">

<tr><td align=center class="form_head">${editForm_topHeader}</td></tr>
<tr><td align=center><br>* - обязательно заполнить</td></tr>
<%-- the page that handles error--%>
<tr align="center"><td><%@ include file="../../messages/error.jsp"%></td></tr>
<tr align="center"><td><%@ include file="../../messages/help.jsp" %></td></tr>
<tr align="right"><td>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" value="Отмена">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" value="Сохранить">
</td></tr>

<input type=hidden name="action" value="insert">
<%--input type="hidden" name="id" value="${param['id']}"--%>
<input type="hidden" name="lang" value="${pageContext.response.locale.language}">

<tr><td align="center"><table>
<tr><td class=bold>Код, который будет заменен*: <spring:errors path="localeParent.code" cssClass="error_msg"/><br></td></tr>
<tr><td align="center"><spring:input path="localeParent.code" cssClass="long_text_area"/></td></tr><%--<input type="text" name="localeParent.code" value="${content_data.localeParent.code}" class="long_text_area">--%>
</table></td></tr>

<tr><td align="center"><table>
<tr><td class=bold>Текст, который будет отображен вместо кода:</td></tr>
<tr><td><textarea name="text" cols="" rows=15 class="long_text_area">${content_data.text}</textarea></td></tr>
</table></td></tr>

<tr><td align="center" class=bold>Сортировка *:&nbsp;&nbsp;
<spring:input path="localeParent.sort" cssClass="sort_edit"/>&nbsp;&nbsp;Активный:&nbsp;&nbsp;
<select name="localeParent.active"><option value="1">да<option value="0" ${content_data.localeParent.activeHtml}>нет</select>&nbsp;&nbsp;
Язык: <font style="font-weight: normal;">${pageContext.response.locale.language}</font>
</td></tr>

<tr align="right"><td>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" value="Отмена">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" value="Сохранить">
</td></tr>

</table>
</spring:form>