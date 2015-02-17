<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/scripts/script.js"></script>
<script type="text/javascript">
function check_all(){
	el = document.getElementById('test_001');
	val = el.checked;
	list = document.getElementsByName('id');
	for (i=0;i<list.length;i++){
		list[i].checked=val;
	}
}
function check_id(c_id, num){
	val = document.getElementById(c_id).checked;
	if (children_123[num]){
		for (i=0;i<children_123[num].length;i++){
			document.getElementById(children_123[num][i]).checked = val;
		}
	}
}
function do_optimize(){
	document.getElementById('o1').value = 'optimize_wallpapers';
}
function do_set(){
	document.getElementById('o1').value = 'set_optimized';
	document.getElementById('o2').value = 'true';
	document.getElementById('o2').disabled = false;
}
function do_reset(){
	document.getElementById('o1').value = 'set_optimized';
	document.getElementById('o2').value = 'false';
	document.getElementById('o2').disabled = false;
}
var numbers_123 = new Array();//temp array for saving parent num
var children_123 = new Array();
</script>

<%@ include file="../../messages/error.jsp" %>
<%@ include file="../../messages/help.jsp" %>

<form method="post" action="?do=optimize_wallpapers">
<table align="center">
<tr><td align="right" onclick="show_hide(help);" style="cursor:pointer;"><a title="Показать/спрятать справку">?</a></td></tr>
<tr><td><div id="help" style="display:none;width:400px;">
Выберите те разделы, фото в которых вы хотите прооптимизировать.
будут прооптимизированны только те, которые еще не прооптимизированны.
</div></td></tr>
<tr><td><input onchange="check_all();" type="checkbox" id="test_001"><label for="test_001">Все</label></td></tr>
<tr><td>
<c:forEach items="${content_data}" var="row" varStatus="i">
<div style="padding:2px;"><table cellpadding="0" cellspacing="0" width="100%" style="border-collapse:collapse;"><tr>
<td align="left" bgcolor="#ffffff" width="${row.layer*30+30}"></td>
<td width="30px" style="border:1px solid black;">${row.id}</td>
<td width="20px" style="border:1px solid black;">
<c:choose>
<c:when test="${(content_data[i.index].layer ge content_data[i.index+1].layer) || i.last}">
<input type="checkbox" name="id" id="id.${row.id}" value="${row.id}">
<script type="text/javascript">
children_123[${i.index}]=false;
for (i=0;i<${row.layer};i++){
	children_123[numbers_123[i]][children_123[numbers_123[i]].length]='id.${row.id}';
}
</script>
</c:when>
<c:otherwise>
<script type="text/javascript">
numbers_123[${row.layer}]=${i.index};
children_123[${i.index}]=new Array();
for (i=0;i<${row.layer};i++){
	children_123[numbers_123[i]][children_123[numbers_123[i]].length]='id.${row.id}';
}
</script>
<input type="checkbox" id="id.${row.id}" onchange="check_id('id.${row.id}', ${i.index});">
</c:otherwise>
</c:choose>
</td>
<td class="link" style="border:1px solid black;"><label for="id.${row.id}">&nbsp;${row.name}&nbsp;</label></td>
</tr></table></div>
</c:forEach>
</td></tr>
<input type="hidden" name="action" id="o1" value="optimize_wallpapers">
<input type="hidden" name="type" id="o2" value="true" disabled>
<tr><td align="right">
&nbsp;&nbsp;&nbsp;<input type="reset" value="Отмена">
&nbsp;&nbsp;&nbsp;<input type="submit" onclick="do_set();" value="Установить" title="устанавливает флажек оптимизации у всех картинок выбранных страниц">
&nbsp;&nbsp;&nbsp;<input type="submit" onclick="do_reset();" value="Обнулить" title="сбрасывает флажек оптимизации у всех картинок выбранных страниц">
&nbsp;&nbsp;&nbsp;<input type="submit" onclick="do_optimize();" value="Оптимиз." title="применить поисковую оптимизацию для всех картинок в выбранных страницах">
</td></tr>
</table>
</form>
<%-- show all pages subpages in script array
<div id="test_div"></div>
<script type="text/javascript">
	var test_text = '';
	for (i=0;i<children_123.length;i++){
		test_text += '<br>'+i+':';
		if (children_123[i]){
			for (j=0;j<children_123[i].length;j++){
				test_text +=children_123[i][j]+',';
			}
		}
	}
	document.getElementById('test_div').innerHTML=test_text;
</script>
--%>