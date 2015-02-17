<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>${big_wallpaper.description}. ${big_wallpaper.title} - <%@include file="/WEB-INF/jspf/view/wallpaper/parts/big_wallpaper_resolutions.jspf"%>. ${site_config.optimization_phrase}</title>
<meta name="keywords" content="${big_wallpaper.title}<c:if test="${empty big_wallpaper.title}"><%@include file="/WEB-INF/jspf/view/parts/navigation_chain.jspf"%></c:if>.">
<meta name="description" content="${big_wallpaper.title}. Разрешения: <%@include file="/WEB-INF/jspf/view/wallpaper/parts/big_wallpaper_resolutions.jspf"%>. Теги: ${big_wallpaper.tags}. ${big_wallpaper.description}. ${site_config.optimization_phrase}">