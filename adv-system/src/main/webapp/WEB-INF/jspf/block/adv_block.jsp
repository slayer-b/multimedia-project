<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">${content.css}</style>
<script type="text/javascript">
var adv_sys_tracker = "http://${pageContext.request.serverName}:${pageContext.request.serverPort}"+
"${pageContext.servletContext.contextPath}"+
"/adv.htm?do=track&pub_id=${pub_id}&item_id=";

var url;
function gogogo(url_001, item) {
	url = url_001;
    xmlhttprequest = createRequestObject();
    xmlhttprequest.open("GET", adv_sys_tracker+item, true);
    xmlhttprequest.onreadystatechange = getData;
    xmlhttprequest.send(null);
}

function createRequestObject()
{
	if (window.XMLHttpRequest){
		return xmlhttprequest = new XMLHttpRequest();
	} else if (window.ActiveXObject) {
		return xmlhttprequest = new ActiveXObject("Microsoft.XMLHTTP");
	}
}
function getData()
{
	//is called on succeed
}
</script>
</head>

<body>
<div class="adv_block">
<c:forEach items="${advertisements}" var="adv">
<div class="adv_link">${adv.title}: <a onclick="gogogo('${adv.url}', '${adv.id}')" target="_blank" href="${adv.url}">${adv.text}</a></div>
</c:forEach>
<div class="adv_add"><a target="_blank" href="adv.htm?pub_id=${pub_id}&amp;do=addLink">добавить</a></div>
<c:if test="${nav_visible ne null}">
<div class="adv_nav">
<c:if test="${prev_page ne null}">
<a href="adv.htm?pub_id=${pub_id}&amp;adv_p=${prev_page}"><</a>
</c:if>
<c:if test="${next_page ne null}">
<a href="adv.htm?pub_id=${pub_id}&amp;adv_p=${next_page}">></a>
</c:if>
</div>
</c:if>
</div>
</body>
</html>
