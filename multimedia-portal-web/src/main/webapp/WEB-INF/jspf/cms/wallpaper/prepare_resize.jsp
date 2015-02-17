<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/scripts/script.js"></script>
<center>Выберите настройки и нажмите 'Выполнить'.</center>
<form action="?">
<input type="hidden" name="action" value="resize">
<table align="center">
<tr align="center"><td colspan="2"><%@ include file="../../messages/error.jsp"%></td></tr>
<tr align="center"><td colspan="2"><%@ include file="../../messages/help.jsp"%></td></tr>
<tr><td></td><td align="right" onclick="show_hide(help);" style="cursor:pointer;"><a title="Показать/спрятать справку">?</a></td></tr>
<tr><td colspan="2"><div id="help" style="display:none;">
<center class="grey">Перезаписать - если выбрано будут удалены текущие файлы картинок и записаны новые, если нет - будут только те для которых не найден файл определенного размера.</center>
</div></td></tr>
<tr>
<td><label for="append_checkbox">Перезаписать все/только несуществующие</label></td>
<td><input type="checkbox" name="append_all" value="true" id="append_checkbox"></td>
</tr>
<tr><td colspan="2" align="right">
<input type="submit" value="Выполнить">
</td></tr>
<c:if test="${time ne null}"><tr><td colspan="2">Время: ${time}</td></tr></c:if>
</table>
</form>