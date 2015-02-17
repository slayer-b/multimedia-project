<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt"  prefix="c"%>
<a class="link" href="../index.htm">Главная</a>
<c:if test="${id_photo_nav ne null}">
&gt;&nbsp;<a class="link" href="../wallpaper/index.htm?id_photo_nav=${id_photo_nav}">Wallpaper</a>
</c:if>
&gt;&nbsp;<a class="link" href="../wallpaperComment/index.htm?do=view${keep_parameters}">Комменты</a>