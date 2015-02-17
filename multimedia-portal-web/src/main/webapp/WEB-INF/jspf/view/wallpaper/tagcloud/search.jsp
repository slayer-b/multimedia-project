<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://download-multimedia.com/tags/cache" prefix="cache"%>
<%@include file="../../../messages/error.jsp" %>
<%@include file="../../../messages/help.jsp" %>
<h1>${page.name}</h1><br>
<div>Поиск по тегам. В данный момент можно осуществлять поиск только по одному тэгу.</div>
<br>
<%--form class="wall_tag_search" action="?">
<div align="left"><input type="text" name="tag" value="${content_data.tag}"> <input type="submit" value="Поиск"></div>
<div align="left"><label for="exact_field">Не точное совпадение</label> <input id="exact_field" type="checkbox" name="exact"<c:if test="${content_data.exact}"> checked</c:if>></div>
<input type="hidden" name="id_pages_nav" value="${page.id}">
</form--%>
<form class="wall_tag_search" action="?">
<div align="left"><input type="text" name="tag" value=""> <input type="submit" value="Поиск"></div>
<div align="left"><label for="exact_field">Не точное совпадение</label> <input id="exact_field" type="checkbox" name="exact"></div>
<input type="hidden" name="id_pages_nav" value="${page.id}">
</form>
<br>
<h2>Все теги</h2>
<cache:cacheRegion key="tag_cloud" cacheKey="all_tags"><noindex>
<div class="tags"><c:forEach items="${content_data.data}" var="tags">
<div><h3>${tags.key}</h3>
<ul><c:forEach items="${tags.value}" var="item">
<li><a rel="nofollow" href="index.htm?id_pages_nav=${page.id}&amp;tag=${item}">${item}</a>
</c:forEach></ul></div></c:forEach></div>
</noindex></cache:cacheRegion>