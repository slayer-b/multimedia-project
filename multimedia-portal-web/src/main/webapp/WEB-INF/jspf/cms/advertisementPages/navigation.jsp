<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt"  prefix="c"%>
<a class="link" href="../index.htm">Главная</a>
<c:if test="${id_pages_nav ne null}">
&gt;&nbsp;<a class="link" href="../pages/index.htm?do=view&id_pages_nav=${id_pages_nav}">Страницы</a>
</c:if>
&gt;&nbsp;<a class="link" href="index.htm?do=view${keep_parameters}">Реклама_страницы</a>
