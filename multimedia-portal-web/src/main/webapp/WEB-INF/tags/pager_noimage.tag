<%@tag description="same as pager but has no imgs" body-content="empty" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@attribute name="count" rtexprvalue="true" required="true" type="common.beans.PagerBean"%>
<%--jsp:useBean id="count" scope="page" type="common.beans.PagerBean"/--%>
<c:if test="${count.pageCount gt 0}">
<table class="nav_pages" cellpadding="2" cellspacing="0"><tr>

<c:if test="${count.currentPage eq 0}"><c:set var="style_prev">display:none;</c:set></c:if>
<td class="nav_pages" title="на первую страницу" align="center"><a style="${style_prev}" href="index.htm?${count.curPageParam}=0${count.queryString}${page_params}">&lt;&lt;</a></td>
<td class="nav_pages" title="на предыдущую страницу" align="center"><a style="${style_prev}" href="index.htm?${count.curPageParam}=${count.currentPage-1}${count.queryString}${page_params}">&lt;</a></td>
<%-- from first to current-1 --%>
<c:if test="${count.currentPage gt 0}">
<c:forEach begin="${count.firstPage}" end="${count.currentPage-1}" varStatus="i">
<td class="nav_pages" align="center" onclick="window.location='?${count.curPageParam}=${i.index}${count.queryString}${page_params}'"><a href="?${count.curPageParam}=${i.index}${count.queryString}${page_params}">${i.index+1}</a></td>
</c:forEach>
</c:if>

<%-- current page--%>
<td class="nav_pages_cur" align="center" onclick="window.location='?${count.curPageParam}=${count.currentPage}${count.queryString}${page_params}'"><a href="?${count.curPageParam}=${count.currentPage}${count.queryString}${page_params}">${count.currentPage+1}</a></td>
<%-- from current+1 to last --%>
<c:forEach begin="${count.currentPage+1}" end="${count.lastPage}" varStatus="i">
<td class="nav_pages" align="center" onclick="window.location='?${count.curPageParam}=${i.index}${count.queryString}${page_params}'"><a href="?${count.curPageParam}=${i.index}${count.queryString}${page_params}">${i.index+1}</a></td>
</c:forEach>

<c:if test="${count.currentPage eq count.lastPage}"><c:set var="style_next">display:none;</c:set></c:if>
<td class="nav_pages" title="на следующую страницу" align="center"><a style="${style_next}" href="?${count.curPageParam}=${count.currentPage+1}${count.queryString}${page_params}">&gt;</a></td>
<td class="nav_pages" title="на последнюю страницу" align="center"><a style="${style_next}" href="?${count.curPageParam}=${count.pageCount}${count.queryString}${page_params}">&gt;&gt;</a></td>

</tr></table>
</c:if>