<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jspf/messages/help.jsp" %>
<%@include file="/WEB-INF/jspf/messages/error.jsp" %>
<script src="/scripts/script1.js" type="text/javascript"></script>
<script type="text/javascript">
var text_upload = 'Вы уверены, что хотите начать загрузку?';
var text_upload_pre = 'Вы уверены, что хотите начать подготовку загрузки?';
</script>
<div align="center"><a class="link" href="?do=prepare">Обновить</a></div>
<table align="center">
<tr><td align="left"><a class="link" href="?do=view&amp;id_pages_nav=${param['id_pages_nav']}" title="Добавить">Просмотр/редактирование(Html)</a></td></tr>
<tr><td align="left"><a class="link" href="?do=updateAjaxForm&amp;id_pages_nav=${param['id_pages_nav']}" title="Редактировать">Просмотр/редактирование(Ajax)</a></td></tr>
<tr><td align="left"><a class="link" href="?do=view_onlyImages&amp;id_pages_nav=${param['id_pages_nav']}" title="Добавить">Просмотр (маленькие фото)</a></td></tr>
<tr><td><hr width="100%"></td></tr>
<tr><td align="left"><a class="link" href="?do=insert&amp;id_pages_nav=${param['id_pages_nav']}" title="Добавить">Добавить</a></td></tr>
<tr><td align="left"><a class="link" href="?do=insertMulti&amp;id_pages_nav=${param['id_pages_nav']}" title="Добавить">Добавить много</a></td></tr>
<tr><td align="left"><a class="link" href="javascript:confirmation_('?do=upload&amp;action=upload', text_upload)" title="Сразу приступает к загрузке элементов">Загрузить с папки на сервере</a><br><font class="hint">(Загрузка начнется сразу после нажатия)</font></td></tr>
<tr><td align="left"><a class="link" href="javascript:confirmation_('?do=pre_upload', text_upload_pre)" title="Осуществляет resize элементов во все необходимые размеры, без добавления в БД. Записывает в папку pre_upload. Исходные элементы удаляются.">Подготовить загрузку с папки на сервере</a></td></tr>
<tr><td align="left"><a class="link" href="?do=upload" title="Показывает папки и сколько в них элементов, и создает сами папки.">Загрузить с папки на сервере(расширенное)</a></td></tr>
<tr><td><hr width="100%"></td></tr>
<tr><td align="left"><a class="link" href="${pageContext.servletContext.contextPath}/cms/wallpaper/wallpaper_backup.htm" title="Резервное копирование">Резервное копирование</a></td></tr>
<tr><td align="left"><a class="link" href="${pageContext.servletContext.contextPath}/cms/wallpaper/wallpaper_resize.htm" title="Добавляет у всех фото копии в недостающем размере">Изменение размера фото</a></td></tr>
<tr><td align="left"><a class="link" href="?do=wallpaper_resolution" title="У всех wallpaper перезачитывает размер из файлов(картинок) и сохраняет в базу данных">Заполнение размера фото</a></td></tr>
<tr><td align="left"><a class="link" href="?do=optimize_wallpapers" title="Оптимизация wallpaper(заполнить поля значениями автоматом)">Оптимизация</a></td></tr>
<tr><td align="left"><a class="link" href="?do=find_duplicates" title="Поиск одинаковых wallpaper">Поиск дубликатов</a></td></tr>
</table>
<div>Заполнение размера фото: ${applicationScope['wallpaper_renewResolution_progress']}</div>
<div>Загрузка с папки на сервере: ${applicationScope['wallpaper_upload_progress']}</div>