<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Добавить</title>
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/styles/styles-my.css">
</head>

<body>
<%@ include file="/WEB-INF/jspf/messages/error.jsp"%>
<%@ include file="/WEB-INF/jspf/messages/help.jsp" %>

<div align="center" class="form_head">Добавить ссылку</div>
<br>
<div>
<fmt:bundle basename="msg/prices/unit">
<div>Тип оплаты: <fmt:message key="${payment.type}"/></div>
</fmt:bundle>
<fmt:bundle basename="msg/prices/descr">
<div><fmt:message key="${payment.type}"/>: <fmt:parseNumber value="${payment.cost}" pattern="#.##"/></div>
</fmt:bundle>
</div>
<br>
<div align="center">* - обязательно заполнить</div>
<form:form method="post" commandName="content_data" action="?do=addLink" >
<table align="center">
<input type="hidden" name="action" value="addLink">
<input type="hidden" name="pub_id" value="${pub_id}">
<tr><td align="center"><table>
<tr><td class=bold>Текст&nbsp;*:&nbsp;<form:errors path="item.text" cssClass="error_msg"/><br></td></tr>
<tr><td align="center">
<form:input path="item.text" cssClass="long_text_area"/>
</td></tr>
</table></td></tr>
<tr><td align="center"><table>
<tr><td class=bold>Урл&nbsp;*:&nbsp;<form:errors path="item.url" cssClass="error_msg"/><br></td></tr>
<tr><td align="center">
<form:input path="item.url" cssClass="long_text_area"/>
</td></tr>
</table></td></tr>
<tr><td align="center"><table>
<tr><td class=bold>Титул&nbsp;*:&nbsp;<form:errors path="item.title" cssClass="error_msg"/><br></td></tr>
<tr><td align="center">
<form:input path="item.title" cssClass="long_text_area"/>
</td></tr>
</table></td></tr>
<tr><td align="center"><table>
<tr><td class=bold>E-mail&nbsp;*:&nbsp;<form:errors path="item.email" cssClass="error_msg"/><br></td></tr>
<tr><td align="center">
<form:input path="item.email" cssClass="long_text_area"/>
</td></tr>
</table></td></tr>

<c:if test="${payment.type eq 'CLICKS'}">
<tr><td align="center"><table>
<tr><td class=bold>Количество&nbsp;*:&nbsp;<form:errors path="quantity" cssClass="error_msg"/><br></td></tr>
<tr><td align="center">
<form:input path="quantity" cssClass="long_text_area"/>
</td></tr>
</table></td></tr>
</c:if>

<tr><td align="center"><table>
<tr><td class=bold>Способ оплаты&nbsp;*:&nbsp;<form:errors path="paymentMehod" cssClass="error_msg"/><br></td></tr>
<tr><td align="center">
<select name="paymentMehod" class="long_text_area">
<fmt:bundle basename="msg/payment/systems">
<c:forEach items="${paymentMethods}" var="item">
<option label="<fmt:message key="${item}"/>" value="${item}"><fmt:message key="${item}"/>
</c:forEach>
</fmt:bundle>
</select>
</td></tr>
</table></td></tr>
<tr align="right"><td>
<a href="adv.htm?pub_id=${pub_id}">[Назад]</a>
<input type="submit" value="Сохранить">
</td></tr>
</table>
</form:form>
</body>

</html>