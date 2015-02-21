<%@page pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt"  prefix="c"%>
<%@taglib uri="http://download-multimedia.com/tags/utils" prefix="util"%>
<a class="link" href="../index.htm">Главная</a>
<c:if test="${not empty param['id_users_nav']}">
&gt;&nbsp;<a class="link" href="../users/index.htm">Пользователи</a>
</c:if>
<c:if test="${pages_navigation ne null}">
&gt;&nbsp;<a class="link" href="../pages/index.htm">Страницы</a>
<c:forEach items="${pages_navigation}" var="navigItem">
&gt;&nbsp;<a class="link" href="../pages/index.htm?do=view&id_pages_nav=${navigItem.id}">${navigItem.name}</a>
</c:forEach>
</c:if>
&gt;&nbsp;<a class="link" href="index.htm?do=view<util:removeParam url="keep_parameters" parameterName="do"/>">Фото</a>
<span style="float:right;"><a class="link" href="../pages/index.htm?do=mega_module">Рубрикатор</a></span>