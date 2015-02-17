<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/scripts/script.js"></script>
<center>Выберите настройки и нажмите 'Выполнить'.</center>
<form action="?">
<table align="center">
<tr><td></td><td align="right" onclick="show_hide(help);" style="cursor:pointer;"><a title="Показать/спрятать справку">?</a></td></tr>
<tr><td colspan="2"><div id="help" style="display:none;">
<center class="grey">Backup - сохранить все файлы картинок, которые есть в БД в папку на сервере.</center>
<center class="grey">Restore - восстановить все файлы картинок, которые есть в БД из папку на сервере.</center>
<center class="grey">Только файлы - сохраняет / загружает только файлы, если не выбранно также сохраняет / загружает описания картинок.</center>
<center class="grey">Перезаписать - если выбрано будут перезаписаны только новые файлы.</center>
</div></td></tr>
<tr><td>Тип</td>
<td>
<select name="do">
<option value="backup" title="Сделать резервную копию картинок.">Backup</option>
<option value="restore" title="Восстановить картинки, используя резервную копию.">Restore</option>
<%--option value="restore" title="Сделать резервную копию картинок, а также файлы с описанием.">Export</option>
<option value="restore" title="Восстановить картинки, используя резервную копию, включая файлы с описанием">Import</option--%>
<%--option title="Удалить записи для которых нет картинок." value="clear">clear</option--%>
</select>
</td></tr>
<tr>
<td><label for="append_checkbox">Перезаписать все/только новые</label></td>
<td><input type="checkbox" name="append_all" value="1" id="append_checkbox"></td>
</tr>
<%--tr>
<td><label for="files_checkbox">Только файлы</label></td>
<td><input type="checkbox" name="only_files" value="1" id="files_checkbox" checked></td>
</tr--%>
<tr><td colspan="2" align="right">
<input type="submit" value="Выполнить">
</td></tr>
</table>
</form>