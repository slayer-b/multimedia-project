<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%--table border="1" align="center">
	<tr><td align="left">Всего времени:</td><td align="right">${applicationScope['STAT_NAME_FILTER'].total_time/1000000} ms</td></tr>
	<tr><td align="left">Всего посещений:</td><td align="right">${applicationScope['STAT_NAME_FILTER'].total_count}</td></tr>
	<tr><td align="left">% в обработке:</td><td align="right">${applicationScope['STAT_NAME_FILTER'].percent_working} %</td></tr>
</table--%>
<table border="1" align="center">
	<tr><td align="left">Времени ядра:</td><td align="right">${time_kernel}</td></tr>
	<tr><td align="left">Времени пользователя:</td><td align="right">${time_user}</td></tr>
	<tr><td align="left">Времени работы:</td><td align="right">${time_work}</td></tr>
	<tr><td align="left">% в обработке:</td><td align="right">${percent_work} %</td></tr>
</table>
<br>
<fmt:bundle basename="messages/forms/stats">
<c:forEach items="${gallery_stats}" var="stat">
<div align="center"><fmt:message key="${stat.key}"/>: ${stat.value}</div>
</c:forEach>
</fmt:bundle>
<table border="1" align="center">
	<tr><td align="left"></td><td align="center">new</td><td align="center">old</td></tr>
	<tr><td align="left">время</td><td align="right">${applicationScope['NewIO'].time/1000000} ms</td><td align="right">${applicationScope['OldIO'].time/1000000}</td></tr>
	<tr><td align="left">количество</td><td align="right">${applicationScope['NewIO'].count}</td><td align="right">${applicationScope['OldIO'].count}</td></tr>
	<tr><td align="left">обьем:</td><td align="right">${applicationScope['NewIO'].size}</td><td align="right">${applicationScope['OldIO'].size}</td></tr>
</table>