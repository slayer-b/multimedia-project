<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/scripts/script.js"></script>
<script type="text/javascript">
var text='Вы действительно хотите удалить эту запись?';
var prefixes = new Array("id_","parent_id_","lang_","parent_code_","text_","parent_sort_","parent_active_");
var count = 0;
</script>
<form method="post" action="?do=multiUpdate">
<table align="center">
<%-- the page that handles error--%>
<tr align="center"><td><%@ include file="../../messages/error.jsp" %></td></tr>
<tr align="center"><td><%@ include file="../../messages/help.jsp" %></td></tr>
<tr><td><a href="?do=insert" title="Добавить" class="link"><img alt="добавить" border="0" src="${pageContext.servletContext.contextPath}/img/add.jpg">
</a><a class="link" href="?do=insert" title="Добавить">Добавить</a></td></tr>

<tr><td><table align="center" border="1" cellpadding="2" cellspacing="0"  style='border-collapse: collapse;'>
<%--header--%>
<tr class="form_head_row">
<td><input title="Снять/установить отметку у всех" type="checkbox" id="check_all_box" onclick="reset_all(prefixes,'check_all_box',count);" checked></td>
<td align="left" width="30">ID</td>
<td align="left" width="30">Язык</td>
<td align="left">Код</td>
<td align="left">Текст</td>
<td align="center"><img alt="Сортировка" title="Сортировка" src="${pageContext.servletContext.contextPath}/img/prior.gif" align="middle"></td>
<td align="center" width="45">Активный</td>
<td align="center" width="70">Выполнить</td>
</tr>
<%--row data--%>
<c:forEach items="${content_data.current}" var="row" varStatus="i">
<c:if test="${i.index mod 2==0}"><c:set var="tr_class" value="form_row_0"/></c:if>
<c:if test="${i.index mod 2==1}"><c:set var="tr_class" value="form_row_1"/></c:if>
<%--this hidden field is used to store id's of shown pages--%>
<input type="hidden" name="autoreplaces[${i.index}].id" id="id_${i.index}" value="${row.id}">
<input type="hidden" name="autoreplaces[${i.index}].localeParent.id" id="parent_id_${i.index}" value="${row.localeParent.id}">
<input type="hidden" name="autoreplaces[${i.index}].lang" id="lang_${i.index}" value="${row.lang}">
<tr class="${tr_class}">
<td><input type="checkbox" id="${i.index}" name="_marker" value="${i.index}" onclick="reset_element(prefixes,'${i.index}');" checked></td>
<td>${row.id}</td>
<td>${row.lang}</td>
<td><input type="text" value="${row.localeParent.code}" name="autoreplaces[${i.index}].localeParent.code" id="parent_code_${i.index}" style="width:150px;"></td>
<td><input style="width:150px;" value="${row.text}" name="autoreplaces[${i.index}].text" id="text_${i.index}" type="text"></td>
<td><input style="width:50px;" value="${row.localeParent.sort}" name="autoreplaces[${i.index}].localeParent.sort" id="parent_sort_${i.index}" type="text"></td>
<td align="center"><input name="autoreplaces[${i.index}].localeParent.active" type="checkbox" value="1" id="parent_active_${i.index}" ${row.localeParent.activeHtml}></td>
<td align="center">
<a href="javascript:confirmation_('?do=delete&amp;id=${row.id}&amp;action=delete',text)" title="удалить"><img alt="удалить" border="0" src="${pageContext.servletContext.contextPath}/img/kill.gif"></a>
</td>
</tr>
<c:set var="count" value="${i.index}"/>
</c:forEach>
<c:set var="count" value="${count+1}"/>
<script type="text/javascript">count = ${count};</script>
<c:forEach items="${content_data.other}" var="row" varStatus="i">
<c:if test="${i.index mod 2==0}"><c:set var="tr_class" value="form_row_0"/></c:if>
<c:if test="${i.index mod 2==1}"><c:set var="tr_class" value="form_row_1"/></c:if>
<%--this hidden field is used to store id's of shown pages--%>
<input disabled type="hidden" name="autoreplaces[${count+i.index}].id" id="id_${count+i.index}" value="">
<input disabled type="hidden" name="autoreplaces[${count+i.index}].localeParent.id" id="parent_id_${count+i.index}" value="${row.localeParent.id}">
<input disabled type="hidden" name="autoreplaces[${count+i.index}].lang" id="lang_${count+i.index}" value="${pageContext.response.locale.language}">
<tr class="${tr_class}">
<td><input type="checkbox" id="${count+i.index}" name="_marker" value="${count+i.index}" onclick="reset_element(prefixes,'${count+i.index}');"></td>
<td>-</td>
<td>-</td>
<td><input disabled type="text" value="${row.localeParent.code}" name="autoreplaces[${count+i.index}].localeParent.code" id="parent_code_${count+i.index}" style="width:150px;"></td>
<td><input disabled style="width:150px;" value="${row.text}" name="autoreplaces[${count+i.index}].text" id="text_${count+i.index}" type="text"></td>
<td><input disabled style="width:50px;" value="${row.localeParent.sort}" name="autoreplaces[${count+i.index}].localeParent.sort" id="parent_sort_${count+i.index}" type="text"></td>
<td align="center"><input disabled name="autoreplaces[${count+i.index}].localeParent.active" type="checkbox" value="1" id="parent_active_${count+i.index}" ${row.localeParent.activeHtml}></td>
<td align="center">
</td>
</tr>
<script type="text/javascript">count++;</script>
</c:forEach>
</table></td></tr>
<input type=hidden name="action" value="multiUpdate">
<tr><td align="right">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" value="Отмена">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" value="Сохранить">
</td></tr>
</table>
</form>