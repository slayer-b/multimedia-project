<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Enumeration" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%Enumeration<String> e = request.getParameterNames();%>
<fmt:setLocale value="${param['lang']}"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Введите атиспам код</title>
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/styles/antispam.css">
<%-- icon in url begin --%>
<link rel='icon' href='${pageContext.servletContext.contextPath}/favicon.png' type='image/x-icon'>
<link rel="shortcut icon" href="${pageContext.servletContext.contextPath}/favicon.ico">
<%-- icon in url end --%>
</head>
<body>
<h1>Введите антиспам код,<br> чтобы скачать картинку.</h1>
<div><%@ include file="/WEB-INF/jspf/messages/error.jsp"%></div>
<div><%@ include file="/WEB-INF/jspf/messages/help.jsp"%></div>
<form action="" method="get"><%while (e.hasMoreElements()){
String name = e.nextElement();
if (!"anti_spam_code".equals(name)){%><input type="hidden" name="<%= name%>" value="<%= request.getParameter(name)%>"><%}
}%>
<b>Код&nbsp;*:</b><br><br>
<div><input type=text name="anti_spam_code" maxlength='4' class=antispam_code><input type="submit" value="ok"></div><br>
<img alt="включите рисунки чтобы увидеть защиный код" title="Введите код, который изображен на картинке" src="${pageContext.servletContext.contextPath}/code.jpg">
</form>
</body>
</html>
