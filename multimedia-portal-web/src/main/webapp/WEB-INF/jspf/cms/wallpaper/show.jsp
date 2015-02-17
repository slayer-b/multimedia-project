<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="pages" tagdir="/WEB-INF/tags/pages" %>
<%@taglib prefix="utils" uri="http://download-multimedia.com/tags/utils" %>
<%@taglib prefix="common" tagdir="/WEB-INF/tags" %>

<script type="text/javascript" src="${pageContext.servletContext.contextPath}/scripts/script.js"></script>
<script type="text/javascript">
    var text = 'Вы действительно хотите удалить эту запись?';
    var prefixes = new Array("id.", "active.", "_active.", "optimized.", "_optimized.");
    var count = 0;
    function delete_all() {
        var flag = confirm('Вы действительно хотите удалить все выбранные фото?');
        if (flag) {
            disable_all(prefixes, count, true);
            document.getElementById('do_val').value = 'deleteMulti';
            document.getElementById('action_val').value = 'deleteMulti';
            document.getElementById('form_1').submit();
        }
    }
    function move_all() {
        var flag = confirm('Вы действительно хотите переместить все выбранные фото в другой раздел?');
        if (flag) {
            disable_all(prefixes, count, true);
            document.getElementById('do_val').value = 'moveMulti';
            document.getElementById('action_val').value = 'moveMulti';
            document.getElementById('form_1').submit();
        }
    }
</script>

<%@ include file="../../messages/error.jsp" %>
<%@ include file="../../messages/help.jsp" %>

<div align="center">
<span class="right_border"><a
        href="?<utils:replaceParam url="keep_parameters" parameterName="do" parameterNewValue="insert"/>"
        title="Добавить" class="link"><img alt="добавить" border="0"
                                           src="${pageContext.servletContext.contextPath}/img/add.jpg">
</a><a class="link" href="?<utils:replaceParam url="keep_parameters" parameterName="do" parameterNewValue="insert"/>"
       title="Добавить">Добавить</a></span>
    <span class="right_border"><a class="link"
                                  href="${pageContext.servletContext.contextPath}/cms/wallpaper/wallpaper_backup.htm">Резервное
        копирование</a></span>
    <span class="right_border"><a class="link"
                                  href="?<utils:replaceParam url="keep_parameters" parameterName="do" parameterNewValue="updateAjaxForm"/>"
                                  title="Редактировать">Редактировать</a></span>
<span class="right_border">
Показывать по:
<a class="link"
   href="?<utils:replaceParam url="keep_parameters" parameterName="page_size" parameterNewValue="5"/>">5</a>
<a class="link"
   href="?<utils:replaceParam url="keep_parameters" parameterName="page_size" parameterNewValue="10"/>">10</a>
<a class="link"
   href="?<utils:replaceParam url="keep_parameters" parameterName="page_size" parameterNewValue="25"/>">25</a>
<a class="link"
   href="?<utils:replaceParam url="keep_parameters" parameterName="page_size" parameterNewValue="50"/>">50</a>
</span>
    <span onclick="show_hide(help);" style="cursor:pointer;padding-left: 4px;"><a
            title="Показать/спрятать справку">?</a></span>
</div>

<div id="help" style="display:none;">
    <center class="grey">Сохранить - сохраняет выбранные* рядки в БД.</center>
    <center class="grey">Редактировать - открывает выбранные* рядки для редактирования.</center>
    <center class="grey">Удалить - удаляет выбранные* рядки.</center>
    <center class="grey">Выбранные - те, у которых в первом столбце стоит галочка.</center>
</div>
<form id="form_1" method="post" action="?<utils:removeParam url="keep_parameters" parameterName="do"/>">

    <input type="hidden" name="page_number" value="${page_number}">
    <input type="hidden" name="page_size" value="${page_size}">

    <input type="hidden" name="action" value="multi_update" id="action_val">
    <input type="hidden" name="do" value="multi_update" id="do_val">

    <table align="center">
        <tr>
            <td>
                <table align="center" border="1" cellpadding="2" cellspacing="0" style='border-collapse: collapse;'>
                    <%--header--%>
                    <tr class="form_head_row">
                        <td><input title="Снять/установить отметку у всех" type="checkbox" id="check_all_box"
                                   onclick="reset_all(prefixes,'check_all_box',count);" checked></td>
                        <td align="left" width="30">ID</td>
                        <td align="center">Фото</td>
                        <td align="center">Разрешение</td>
                        <td align="center">Папка</td>
                        <td align="center" width="45">Активный</td>
                        <td align="center" width="45" title="Оптимизированный">Оптимиз.</td>
                        <td align="center">Просмотр</td>
                        <td align="center" width="70">Выполнить</td>
                    </tr>
                    <%--row data--%>
                    <c:forEach items="${content_data}" var="row" varStatus="i">
                        <c:if test="${i.index mod 2==0}"><c:set var="tr_class" value="form_row_0"/></c:if>
                        <c:if test="${i.index mod 2==1}"><c:set var="tr_class" value="form_row_1"/></c:if>
                        <input type="hidden" name="data[${i.index}].id" id="id.${i.index}" value="${row.id}">
                        <tr class="${tr_class}">
                            <td><input name="id" type="checkbox" id="${i.index}" value="${row.id}"
                                       onclick="reset_element(prefixes,'${i.index}');" checked></td>
                            <td>${row.id}</td>
                            <td><a href="${pageContext.servletContext.contextPath}/images/wallpaper/full/${row.folderAndName}"
                                   target="_blank" title="Увеличить"><img alt="${row.name}" border="0"
                                                                          src="${pageContext.servletContext.contextPath}/images/wallpaper/tiny/${row.folderAndName}"/></a>
                            </td>
                            <td align="center">${row.width}x${row.height}</td>
                            <td align="center">${row.folder}</td>
                            <td align="center"><input id="active.${i.index}" name="data[${i.index}].active"
                                                      type="checkbox" value="1"<c:if
                                    test="${row.active}"> checked</c:if>/><input id="_active.${i.index}" type="hidden"
                                                                                 name="_data[${i.index}].active"
                                                                                 value="on"/></td>
                            <td align="center"><input id="optimized.${i.index}" name="data[${i.index}].optimized"
                                                      type="checkbox" value="1"<c:if
                                    test="${row.optimized}"> checked</c:if>/><input id="_optimized.${i.index}"
                                                                                    type="hidden"
                                                                                    name="_data[${i.index}].optimized"
                                                                                    value="on"/></td>
                            <td>
                                <div align="center"><a class="link"
                                                       href="../pages/index.htm?do=view&id_pages_nav=${row.id_pages}">страница</a>
                                </div>
                                <div align="center"><a class="link"
                                                       href="../wallpaperComment/index.htm?do=view&id_photo_nav=${row.id}">комментарии</a>
                                </div>
                            </td>
                            <td align="center">
                                <a href="?id=${row.id}<utils:replaceParam url="keep_parameters" parameterName="do" parameterNewValue="update"/>&amp;page_number=${page_number}"
                                   title="редактировать"><img alt="редактировать" border="0"
                                                              src="${pageContext.servletContext.contextPath}/img/options.jpg"></a>
                                <a href="javascript:confirmation_('?id=${row.id}<utils:replaceParam url="keep_parameters" parameterName="do" parameterNewValue="delete"/>&amp;action=delete&amp;page_number=${page_number}',text)"
                                   title="удалить"><img alt="удалить" border="0"
                                                        src="${pageContext.servletContext.contextPath}/img/kill.gif"></a>
                                <a href="?id=${row.id}<utils:replaceParam url="keep_parameters" parameterName="do" parameterNewValue="optimize"/>&amp;action=optimize&amp;page_number=${page_number}"
                                   title="WEB оптимизпация">оптимизация</a>
                            </td>
                        </tr>
                        <script type="text/javascript">count++;</script>
                    </c:forEach>
                </table>
            </td>
        </tr>
        <tr>
            <td align="center"><common:pager_noimage count="${page_counter}"/></td>
        </tr>

        <tr>
            <td align="center" width="100%">
                <table width="100%">
                    <tr>
                        <td align="left"><input type="reset" value="Отмена"></td>
                        <td align="center"><input type="button" value="Удалить" onclick="javascript:delete_all();"></td>
                        <td align="right"><input type="submit" value="Сохранить"
                                                 onclick="javascript:document.getElementById('do_val').value='multi_update';document.getElementById('action_val').value='multi_update'">
                        </td>
                    </tr>
                </table>
                <div>
                    Переместить все выбранные элементы в: <pages:selectAll param_name="id_pages_new"/>
                    <input type="submit" value="Переместить" onclick="javascript:move_all();">
                </div>
            </td>
        </tr>
    </table>
</form>