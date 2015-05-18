<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setBundle basename="messages/forms/common" var="form_common"/>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/scripts/wallpaper/details.js"></script>
<script type="text/javascript">
wallpaper_id = ${big_wallpaper.id};
wallpaper_rate_id = ${system_rate_wallpaper_page.id};
wallpaper_comment_id = ${system_wallpaperComment_add_page.id};
</script>

<%@ include file="../../messages/error.jsp" %>
<%@ include file="../../messages/help.jsp" %>

<h1>${big_wallpaper.description}</h1>

<c:if test="${big_wallpaper ne null}">
<div align="center">Скачать эти обои для рабочего стола в разрешении:</div>
<div align="center" class="res"><c:forEach items="${big_wallpaper.resolutions}" var="item">
<a target="_blank" href="${pageContext.servletContext.contextPath}/images/wallpaper/resolution/${item.width}x${item.height}/${big_wallpaper.folderAndName}?lang=${pageContext.response.locale}">${item.width}x${item.height}</a>
</c:forEach><a target="_blank" href="${pageContext.servletContext.contextPath}/images/wallpaper/full/${big_wallpaper.folderAndName}?lang=${pageContext.response.locale}">Оригинал (${big_wallpaper.width}x${big_wallpaper.height})</a>
</div>

<div align="center"><a href="javascript:next_pic();"><img alt="${big_wallpaper.title}" src="${pageContext.servletContext.contextPath}/images/wallpaper/medium/${big_wallpaper.folderAndName}" title="${big_wallpaper.description} (Кликните, чтобы перейти на след. картинку)"></a></div>

<c:if test="${system_rate_wallpaper_page ne null}">
<div align="center">
<span class="wallpaper_rate">Просмотров: ${big_wallpaper.views}</span>
<span class="wallpaper_rate">Рейтинг: ${big_wallpaper.rating}</span>
<div style="height:15px;" id="rating_div"><img alt="loading" id="rating_img" style="display:none;" src="${pageContext.servletContext.contextPath}/img/loading.gif"><table id="rating_table" cellpadding="3px" cellspacing="5px"><tr>
<td><a title="${system_rate_wallpaper_page.name}" href="javascript:rate_wallpaper(1);">+1</a></td>
<td><a title="${system_rate_wallpaper_page.name}" href="javascript:rate_wallpaper(2);">+2</a></td>
<td><a title="${system_rate_wallpaper_page.name}" href="javascript:rate_wallpaper(3);">+3</a></td>
<td><a title="${system_rate_wallpaper_page.name}" href="javascript:rate_wallpaper(4);">+4</a></td>
<td><a title="${system_rate_wallpaper_page.name}" href="javascript:rate_wallpaper(5);">+5</a></td>
<td><a title="${system_rate_wallpaper_page.name}" href="javascript:rate_wallpaper(6);">+6</a></td>
<td><a title="${system_rate_wallpaper_page.name}" href="javascript:rate_wallpaper(7);">+7</a></td>
<td><a title="${system_rate_wallpaper_page.name}" href="javascript:rate_wallpaper(8);">+8</a></td>
<td><a title="${system_rate_wallpaper_page.name}" href="javascript:rate_wallpaper(9);">+9</a></td>
<td><a title="${system_rate_wallpaper_page.name}" href="javascript:rate_wallpaper(10);">+10</a></td>
</tr></table></div>
</div>
</c:if>
<br><div align="center">${big_wallpaper.title}</div>
<c:if test="${general_wallpaper_tagcloud_submodule ne null}"><div align="center">
Теги: <a href="index.htm?id_pages_nav=${general_wallpaper_tagcloud_submodule.page.id}&amp;tag=${big_wallpaper.tagsList[0]}">${big_wallpaper.tagsList[0]}</a><c:forEach items="${big_wallpaper.tagsList}" var="tag" begin="1">, <a href="index.htm?id_pages_nav=${general_wallpaper_tagcloud_submodule.page.id}&amp;tag=${tag}">${tag}</a></c:forEach>
</div></c:if>
<br>

<div align="center"><%@include file="parts/wallpaper_details_pagination.jspf" %></div>
<div align="center">Можно перемещатся на следующую/предыдущую картинку, используя клавиши влево/вправо на клавиатуре.</div>
<c:if test="${not empty big_wallpaper.comments}">
<div align="left">Комментарии:</div>
<c:forEach items="${big_wallpaper.comments}" var="comment">
<div class="wall_comment"><span class="title"><c:if test="${comment.user ne null}">
<font>${comment.user.name}</font>, </c:if>${comment.creationTime}</span>

<p class="data">${comment.text}</p></div>
</c:forEach>
</c:if>
<div id="wall_comment_new"></div>

<c:if test="${system_wallpaperComment_add_page ne null}">
<div class="wall_add_comment">
<form action="" id="wall_comment_form">
<div align="left">Добавить комментарий:</div>
<div><textarea cols="" rows="" id="wall_comment_area" class="wallpaper_comment" name="text">${comment.text}</textarea></div>
<div align="center"><%@include file="../parts/anti_spam_code.jspf"%></div>
<div align="right"><input type="submit" value="${system_wallpaperComment_add_page.name}"></div>
</form>
</div>
</c:if>

</c:if>