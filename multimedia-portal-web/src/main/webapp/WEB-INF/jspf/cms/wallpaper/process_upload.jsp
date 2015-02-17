<%@page contentType="text/html" pageEncoding="UTF-8"%>
<center><%@ include file="../../messages/error.jsp" %></center>
<center><%@ include file="../../messages/help.jsp" %></center>
<br>
<div align="center"><a href="?do=upload">Обновить</a></div>
<br>
<table border="1" align="center">
<tr><td align="left">Всего фото обработано</td><td align="right">${applicationScope['wallpaper_upload_progress'].done}</td></tr>
<tr><td align="left">Всего фото было:</td><td align="right">${applicationScope['wallpaper_upload_progress'].total}</td></tr>
<tr><td align="left">Сейчас обрабатывается:</td><td align="right">${applicationScope['wallpaper_upload_progress'].cur_name}</td></tr>
</table>