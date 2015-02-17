<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../messages/error.jsp"%>
<%@ include file="../messages/help.jsp"%>
<c:if test="${not empty content_data}">
<div align="center" title="Зеленое - ок, Красное - ошибка">регионы кеша:
<c:forEach items="${content_data}" var="item">
<c:if test="${not item.value}"><font style="color:red;">${item.key}</font></c:if>
<c:if test="${item.value}"><font style="color:green;">${item.key}</font></c:if>,
</c:forEach>
</div>
</c:if>
<table border="0" cellpadding="2" cellspacing="2" align="center" bgcolor="black">
<tr><td bgcolor="white">Модуль</td><td bgcolor="white">Действие</td></tr>
<tr><td bgcolor="white">Рубрикатор</td><td bgcolor="white"><a class="link" href="?do=refresh_rubricator">обновить</a>&nbsp;<a class="link" href="?do=clear_rubricator">очистить</a></td></tr>
<tr><td bgcolor="white">Рубрикатор(ajax)</td><td bgcolor="white"><a class="link" href="?do=region_clear&amp;name=ajax_rubrication">очистить</a></td></tr>
<tr><td bgcolor="white">Страницы(комбобокс)</td><td bgcolor="white"><a class="link" href="?do=region_clear&amp;name=pages_select_combobox">очистить</a></td></tr>
<tr><td bgcolor="white">Случайные картинки</td><td bgcolor="white"><a class="link" href="?do=region_clear&amp;name=random_wallpapers">очистить</a></td></tr>
<tr><td bgcolor="white">Облако тегов(wallpaper)</td><td bgcolor="white"><a class="link" href="?do=region_clear&amp;name=tag_cloud">очистить</a></td></tr>
<tr><td bgcolor="white">Разрешения(wallpaper)</td><td bgcolor="white"><a class="link" href="?do=region_clear&amp;name=wallpaper_resolutions">очистить</a></td></tr>
<tr><td bgcolor="white">Локализация</td><td bgcolor="white"><a class="link" href="?do=region_clear&amp;name=html_localization">очистить</a></td></tr>
<tr><td bgcolor="white">Реклама</td><td bgcolor="white"><a class="link" href="?do=region_clear&amp;name=advertisement&amp;name=advertisement_pages">очистить</a></td></tr>
</table>
<table border="0" cellpadding="2" cellspacing="2" align="center" bgcolor="black">
<tr><td bgcolor="white">Рубрики(главная)</td><td bgcolor="white"><a class="link" href="?do=refresh_rub_img">обновить</a>&nbsp;<a class="link" href="?do=clear_rub_img">очистить</a></td></tr>
</table>