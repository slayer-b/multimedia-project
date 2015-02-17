<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/scripts/script.js"></script>
<script type="text/javascript">var text='Вы действительно хотите удалить эту запись?';</script>
<script type="text/javascript">
<!--
var sortObjects = new Array();
//-->
</script>

<%@ include file="../../messages/error.jsp" %>
<%@ include file="../../messages/help.jsp" %>

<form action="?do=multiUpdate&amp;id_pages_nav=${param['id_pages_nav']}" method="post">
<table align="center">

<tr><td><table cellpadding="0" cellspacing="0" border="0" width="100%"><tr>
<td align="left">
<a href="?do=insert&amp;id_pages_nav=${param['id_pages_nav']}&amp;id_pages=${param['id_pages_nav']}" title="Добавить" class="link">
<img alt="добавить" border="0" src="${pageContext.servletContext.contextPath}/img/add.jpg">
</a><a class="link" href="?do=insert&amp;id_pages_nav=${param['id_pages_nav']}&amp;id_pages=${param['id_pages_nav']}" title="Добавить">Добавить</a>
</td>
<td>
<a class="link" href="?do=last&amp;action=last&amp;id_pages_nav=${param['id_pages_nav']}" title="Пересчет поля last во всех страницах.">Пересчет</a>
</td>
<td>
<a class="link" href="?do=reactivate&amp;action=reactivate&amp;id_pages_nav=${param['id_pages_nav']}" title="Пересчет поля active во всех страницах. Делает активными все страницы у которых есть дочерние элементы(не страницы), а неактивными - те у кого их нет.(под дочерними элементами подразумеваются, например Обои, если они есть у текущей страницы, или ее детей)">Реактивация</a>
</td>
<td align="right" valign="bottom">
<a class="link" href="javascript:sort_all(sortObjects)">Отсортировать</a>
/
<a class="link" href="javascript:set_sort_0(sortObjects)">Сбросить сортировку</a>
</td>
</tr></table></td></tr>

<tr><td>
<table align="center" border="1" cellpadding="2" cellspacing="0" style='border-collapse: collapse;'>

<tr class="form_head_row">
<td align="left" width="30">ID</td>
<td align="left">Название</td>
<td align="center">Модуль(тип)</td>
<td align="center">Папка</td>
<td align="center"><img alt="Сортировка" title="Сортировка" src="${pageContext.servletContext.contextPath}/img/prior.gif" align="middle"></td>
<td align="center" width="45">Активный</td>
<td align="center" width="45">Последний</td>
<td align="center" width="70">Выполнить</td>
</tr>

<c:forEach items="${pages}" var="page" varStatus="i">
<c:if test="${i.index mod 2==0}"><c:set var="tr_class" value="form_row_0"/></c:if>
<c:if test="${i.index mod 2==1}"><c:set var="tr_class" value="form_row_1"/></c:if>

<input type="hidden" name="_marker" id="marker.${i.index}" value="1">
<input type="hidden" name="id" value="${page.id}">

<tr class="${tr_class}">
<td align="left" width="30">${page.id}</td>
<td align="left"><a href="../../index.htm?id_pages_nav=${page.id}" target="_blank">${page.name}</a></td>
<td align="center">
<table border="0">
<tr><td class="bold" align="center">${types[page.type]}</td></tr>
<tr><td align="center"><a class="link" href="?do=view&amp;id_pages_nav=${page.id}" title="Страницы">Страницы</a></td></tr>
<tr><td align="center"><a class="link" href="../pagesPseudonym/index.htm?do=view&amp;id_pages_nav=${page.id}" title="Оптимизация">Оптимизация</a></td></tr>
<c:choose>
<c:when test="${page.type eq 'general_wallpaper_gallery'}">
<tr><td align="center"><a class="link" href="../wallpaper/index.htm?do=view&amp;id_pages_nav=${page.id}" title="Фото">Фото</a></td></tr>
</c:when>
<c:when test="${page.type eq 'general_common_item'}">
<tr><td align="center"><a class="link" href="../commonItem/index.htm?do=view&amp;id_pages_nav=${page.id}" title="Элементы">Элементы</a></td></tr>
</c:when>
</c:choose>
</table>
</td>
<td align="center">${page.pagesFolder.folder_name}</td>
<td align="center"><input class="sort_edit" value="${page.sort}" id="sort_${page.id}" name="sort" type="text"></td>
<td align="center" width="45">
<select name="active" class="sort_edit">
<c:choose>
<c:when test="${page.active}"><option value="true" selected>да</option><option value="false">нет</option></c:when>
<c:otherwise><option value="true">да</option><option value="false" selected>нет</option></c:otherwise>
</c:choose>
</select>
</td>
<td align="center" width="45">
<select name="last" class="sort_edit">
<c:choose>
<c:when test="${page.last}"><option value="true" selected>да</option><option value="false">нет</option></c:when>
<c:otherwise><option value="true">да</option><option value="false" selected>нет</option></c:otherwise>
</c:choose>
</select>
</td>
<td align="center" width="70">
<a href="?do=update&amp;id=${page.id}&amp;id_pages_nav=${param['id_pages_nav']}" title="редактировать"><img alt="редактировать" border="0" src="${pageContext.servletContext.contextPath}/img/options.jpg"></a>
<a href="?do=relocate&amp;id=${page.id}&amp;id_pages_nav=${param['id_pages_nav']}" title="переместить"><img alt="переместить" border="0" src="${pageContext.servletContext.contextPath}/img/replace.gif"></a>
<a href="javascript:confirmation_('?do=delete&amp;id=${page.id}&amp;action=delete&amp;id_pages_nav=${param['id_pages_nav']}',text)" title="удалить"><img alt="удалить" border="0" src="${pageContext.servletContext.contextPath}/img/kill.gif"></a>
<a href="../advertisementPages/index.htm?id_pages_nav=${page.id}" title="разрешения/запрещения рекламы для страниц">разрешения</a>
</td>
</tr>
<script type="text/javascript"><!--
sortObjects[${i.index}]=document.getElementById('sort_${page.id}');
//--></script>
</c:forEach>

</table>
</td></tr>

<input type=hidden name="action" value="multiUpdate">
<tr><td align="right">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" value="Отмена">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" value="Сохранить">
</td></tr>

</table>
</form>