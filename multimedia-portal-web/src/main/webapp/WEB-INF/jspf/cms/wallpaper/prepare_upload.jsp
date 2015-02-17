<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/scripts/script.js"></script>
<script type="text/javascript">
var text='Вы действительно хотите удалить эту запись?';
function test(){
	el = document.getElementById('test_001');
	val = true;
	if (el.checked){
		val = false;
	}
	list = document.getElementsByName('id');
	for (i=0;i<list.length;i++){
		list[i].disabled=val;
	}
}
</script>
<%@ include file="../../messages/error.jsp" %>
<%@ include file="../../messages/help.jsp" %>
<form method="post" action="?">
<table align="center">
<tr><td><table width="100%"><tr>
<td class="right_border" align="left"><a class="link" href="?do=upload&amp;id_pages_nav=${param['id_pages_nav']}" title="Обновить">Обновить</a></td>
<td class="right_border" align="center"><a class="link" href="?do=upload&amp;id_pages_nav=${param['id_pages_nav']}&amp;action=insertMulti" title="Добавить все">Добавить все</a></td>
<td align="right" onclick="show_hide(help);" style="cursor:pointer;"><a title="Показать/спрятать справку">?</a></td>
</tr></table></td></tr>
<tr><td><div id="help" style="display:none;width:400px;">
Сначала залейте фото (картинки) в папку(соответствующего раздела/сраницы) на сервере.
Те, что находятся в корневой папки. будут добавлены в выбранную(radio button) страницу(рубрику).
Количество фото показывается только в тех папках, которые создал сервер. (заливка будет производится из всех папок)
</div></td></tr>

<tr><td><table align="center" border="1" cellpadding="2" cellspacing="0"  style='border-collapse: collapse;'>
<%--header--%>
<tr class="form_head_row">
<td><input id="test_001" type="checkbox" onchange="test()" checked></td>
<td align="left" width="30px">#</td>
<td align="center">Папка</td>
<td align="center">Страница</td>
<td align="center">Кол. фото</td>
</tr>
<%--row data--%>
<c:forEach items="${content_data}" var="row" varStatus="i">
<c:if test="${i.index mod 2==0}"><c:set var="tr_class" value="form_row_0"/></c:if>
<c:if test="${i.index mod 2==1}"><c:set var="tr_class" value="form_row_1"/></c:if>
<tr class="${tr_class}">
<td><input type="radio" name="id" value="${row.id}"></td>
<td>${i.index}</td>
<td>${row.folder_name}</td>
<td>${row.page_name}</td>
<td>${row.item_count}</td>
</tr>
</c:forEach>
</table></td></tr>
<input type="hidden" name="do" value="upload">
<input type="hidden" name="action" value="upload">
<input type="hidden" name="id_pages_nav" value="${param['id_pages_nav']}">
<tr><td align="right">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" value="Отмена">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" value="Сохранить">
</td></tr>
</table>
</form>