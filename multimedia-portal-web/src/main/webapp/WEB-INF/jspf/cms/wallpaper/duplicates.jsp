<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript" src="${pageContext.servletContext.contextPath}/scripts/script.js"></script>
<script type="text/javascript">
var text='Вы действительно хотите удалить эту запись?';
var prefixes = new Array("id.","active.","_active.","optimized.","_optimized.");
var count = 0;
function delete_all(){
    var flag=confirm(text);
	if (flag){
		document.getElementById('form_1').submit();
	}
}
function delete_one(index){
    var flag=confirm(text);
    if (flag){
		document.getElementById(index).checked = false;
		reset_element(prefixes, index);
		alert('make ajax delete function');
    }
	//confirmation_('?id=${row.id}&amp;do=delete&amp;action=delete&amp;page_number=${page_number}',text)
}
</script>

<%@ include file="../../messages/error.jsp"%>
<%@ include file="../../messages/help.jsp"%>

<form id="form_1" method="post" action="index.htm?do=deleteMulti">

<input type="hidden" name="action" value="deleteMulti">

<table align="center">
<tr><td><table align="center" border="1" cellpadding="2" cellspacing="0"  style='border-collapse: collapse;'>
<%--header--%>
<tr class="form_head_row">
<td><input title="Снять/установить отметку у всех" type="checkbox" id="check_all_box" onclick="reset_all(prefixes,'check_all_box',count);" checked></td>
<td align="left" width="30">ID</td>
<td align="center">Фото</td>
<td align="center">Разрешение</td>
<td align="center" width="45">Активный</td>
<td align="center" width="45" title="Оптимизированный">Опт.</td>
<td align="center" width="45" title="Оптимизированный вручную">Опт. рук.</td>
<td align="center">Просмотр</td>
<td align="center" width="70">Выполнить</td>
</tr>
<c:set var="index" value="0"/>
<c:forEach items="${content_data}" var="duplicates">
<tr><td colspan="8"></td></tr>
<c:forEach items="${duplicates}" var="row" varStatus="i">
<c:if test="${i.index mod 2==0}"><c:set var="tr_class" value="form_row_0"/></c:if>
<c:if test="${i.index mod 2==1}"><c:set var="tr_class" value="form_row_1"/></c:if>
<input type="hidden" name="data[${index}].id" id="id.${index}" value="${row.id}">
<tr class="${tr_class}" id="tr_${index}}">
<td><input name="id" type="checkbox" id="${index}" value="${row.id}" onclick="reset_element(prefixes,'${index}');" checked></td>
<td>${row.id}</td>
<td><a href="${pageContext.servletContext.contextPath}/images/wallpaper/full/${row.folderAndName}" target="_blank" title="Увеличить"><img alt="${row.name}" border="0" src="${pageContext.servletContext.contextPath}/images/wallpaper/tiny/${row.folderAndName}"/></a></td>
<td align="center">${row.width}x${row.height}</td>
<td align="center"><input id="active.${index}" name="data[${index}].active" type="checkbox" value="1"<c:if test="${row.active}"> checked</c:if>/><input id="_active.${index}" type="hidden" name="_data[${index}].active" value="on"/></td>
<td align="center"><input id="optimized.${index}" name="data[${index}].optimized" type="checkbox" value="1"<c:if test="${row.optimized}"> checked</c:if>/><input id="_optimized.${index}" type="hidden" name="_data[${index}].optimized" value="on"/></td>
<td align="center"><input type="checkbox"<c:if test="${row.optimized_manual}"> checked</c:if> disabled/></td>
<td>
<div align="center"><a class="link" href="../pages/index.htm?do=view&id_pages_nav=${row.id_pages}">страница</a></div>
<div align="center"><a class="link" href="../wallpaperComment/index.htm?do=view&id_photo_nav=${row.id}">комментарии</a></div>
</td>
<td align="center">
<a href="?id=${row.id}&amp;do=update&amp;page_number=${page_number}" title="редактировать"><img alt="редактировать" border="0" src="${pageContext.servletContext.contextPath}/img/options.jpg"></a>
<a href="javascript:delete_one('${index}')" title="удалить"><img alt="удалить" border="0" src="${pageContext.servletContext.contextPath}/img/kill.gif"></a>
</td>
</tr>
<script type="text/javascript">count++;</script>
<c:set var="index" value="${index+1}"/>
</c:forEach>
</c:forEach>
</table></td></tr>

<tr><td align="center" width="100%"><table width="100%"><tr>
<td align="left"><input type="reset" value="Отмена"></td>
<td align="right"><input type="button" value="Удалить" onclick="javascript:delete_all();"></td>
</tr></table></td></tr>
</table>
</form>