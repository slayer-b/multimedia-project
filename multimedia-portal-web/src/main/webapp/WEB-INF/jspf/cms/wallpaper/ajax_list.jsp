<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
${content_data[0]}<c:forEach begin="1" items="${content_data}" var="item">,${item}</c:forEach>