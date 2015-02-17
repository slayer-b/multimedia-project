<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title><c:if test="${empty page.title}">${pages_navigation[0].name}<c:forEach items="${pages_navigation}" var="item" begin="1">, ${item.name}</c:forEach></c:if>${page.title}</title>
<meta name="keywords" content="<c:if test="${empty page.keywords}">${pages_navigation[0].name}<c:forEach items="${pages_navigation}" var="item" begin="1">, ${item.name}</c:forEach></c:if>${page.keywords}">
<meta name="description" content="<c:if test="${empty page.description}">${pages_navigation[0].name}<c:forEach items="${pages_navigation}" var="item" begin="1">, ${item.name}</c:forEach></c:if>${page.description}">