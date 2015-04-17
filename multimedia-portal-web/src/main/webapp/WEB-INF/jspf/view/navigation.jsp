<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt"  prefix="c"%>
<c:if test="${pages_navigation[0] ne null}"><a href="index.htm?id_pages_nav=${pages_navigation[0].id}<c:if test="${page.moduleName eq pages_navigation[0].moduleName}">${page_params}</c:if>">${pages_navigation[0].name}</a></c:if>
<c:forEach items="${pages_navigation}" var="navigItem" begin="1" varStatus="i">
<c:set var="r_q_params"><c:if test="${page.moduleName eq navigItem.moduleName}">${page_params}</c:if></c:set>
<c:choose><c:when test="${(i.last)&&(big_wallpaper ne null)}">
&gt;&nbsp;<a href="index.htm?id_pages_nav=${navigItem.id}&amp;page_number=${cur_page_number}${r_q_params}">${navigItem.name}</a>
&gt;&nbsp;<a href="index.htm?id_pages_nav=${navigItem.id}&amp;id_photo_nav=${big_wallpaper.id}${r_q_params}">Скачать обои</a></c:when><c:when test="${(i.last)&&(not empty param['page_number'])}">
&gt;&nbsp;<a href="index.htm?id_pages_nav=${navigItem.id}&amp;page_number=${param['page_number']}${r_q_params}">${navigItem.name}</a></c:when><c:otherwise>
&gt;&nbsp;<a href="index.htm?id_pages_nav=${navigItem.id}${r_q_params}">${navigItem.name}</a></c:otherwise></c:choose></c:forEach>

<script type="text/javascript">
    var text = 'Картинки содержатся только в последних разделах.\n\
 Чтобы скачать картинку зайдите, например, в "Обои для рабочего стола>Животные>Динозавры>Диплодок", выберите картинку которую хотите скачать.\n\
 Над картинкой вы видите разрешения для этой картинки, нажмите на подходящее вам.\n\
 Если вы не знаете какое разрешение выбрать, выберите оригинал.';
    function answer(){window.alert(text);}
</script>
<span style="float:right;"><a href="javascript:answer();">Как скачать?</a></span>