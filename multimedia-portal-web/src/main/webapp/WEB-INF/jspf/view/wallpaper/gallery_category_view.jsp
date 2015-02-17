<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="common" tagdir="/WEB-INF/tags"%>
<%@ include file="../../messages/error.jsp" %>
<%@ include file="../../messages/help.jsp" %>
<div align="center" class="walls_small"><c:forEach items="${wallpapers}" var="item"><%@include file="parts/wallpaper_category.jspf"%></c:forEach></div>
<div align="center"><common:pager count="${count}"/></div>