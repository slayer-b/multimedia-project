<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setBundle basename="messages/forms/common" var="form_common"/>

<spring:form method="post" commandName="content_data" action="index.htm?id_pages_nav=${param['id_pages_nav']}"><div align="center">
<div class="form_head">${page.name}</div>
<br>
<div>* - <fmt:message bundle="${form_common}" key="required_fill"/></div>
<%@ include file="../../messages/error.jsp"%>
<%@ include file="../../messages/help.jsp"%>
<table>
<tr><td class="bold"><fmt:message bundle="${form_common}" key="your"/> E-mail *:<spring:errors path="email_from" cssClass="error_msg"/></td></tr>
<tr><td><spring:input path="email_from" cssClass="long_text_area"/></td></tr>
<tr><td class="bold"><fmt:message bundle="${form_common}" key="text"/> *:<spring:errors path="text" cssClass="error_msg"/></td></tr>
<tr><td><spring:textarea path="text" cssClass="long_text_area"/></td></tr>
</table>
<div align="center"><%@include file="../parts/anti_spam_code.jspf"%></div>
<input type=hidden name="action" value="insert">
<div align="right"><input type="submit" value="<fmt:message bundle="${form_common}" key="send"/>"></div>
</div></spring:form>