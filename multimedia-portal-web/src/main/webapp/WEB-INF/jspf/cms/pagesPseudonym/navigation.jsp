<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt"  prefix="c"%>
<a class="link" href="../index.htm">Главная</a>
&gt;&nbsp;<a class="link" href="../pages/index.htm?do=view">Страницы</a>
<c:forEach items="${pages_navigation}" var="navigItem">
&gt;&nbsp;<a class="link" href="../pages/index.htm?id_pages_nav=${navigItem.id}">${navigItem.name}</a>
</c:forEach>
&gt;&nbsp;<a class="link" href="index.htm?do=view&amp;id_pages_nav=${param['id_pages_nav']}">Оптимизация</a>
<span style="float:right;"><a class="link" href="../pages/index.htm?do=mega_module">Рубрикатор</a></span>
