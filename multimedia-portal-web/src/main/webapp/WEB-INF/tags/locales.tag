<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="cache" uri="http://download-multimedia.com/tags/cache"%>
<cache:cacheRegion key="html_localization" cacheKey="${localization.key}"><c:forEach items="${localization.data}" var="item">
<a class="locale" href="${pageContext.servletContext.contextPath}${item.path}"><img src="${pageContext.servletContext.contextPath}/img/${item.name}/flag.jpg" alt="${item.name}"></a>
</c:forEach></cache:cacheRegion>