<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script src="${pageContext.servletContext.contextPath}/scripts/wallpaper.js" type="text/javascript"></script>
<script src="${pageContext.servletContext.contextPath}/scripts/wallpaper_update_ajax.js"
        type="text/javascript"></script>
<script type="text/javascript">
    var contextPath = '${pageContext.servletContext.contextPath}';
    var threadID = -1;
    var url = '?do=get_wallpaper';
    var url_opt = '?do=optimize&action=optimize&ajax=1';
    var cur_index = 0;
    var cur_wallpaper;
    var cur_size = 'medium';
    var items = [];
</script>

<div align="center">
    <div class="form_frame">
        <table border="0">
            <tr>
                <td><label for="nav_items_count">Навигация по:</label><input onchange="resize_navigation()"
                                                                             style="width:30px;height:20px;" value="10"
                                                                             old_value="10" id="nav_items_count"
                                                                             type="text"></td>
                <td><label for="slideShow_input">Интервал(мс):</label><input style="width:70px;height:20px;" value="500"
                                                                             old_value="500" id="slideShow_input"
                                                                             type="text"></td>
                <td><input id="slideShow_checkbox" type="checkbox" onclick="start_slideShow()"><label
                        for="slideShow_checkbox">Показ слайдов start/stop</label></td>
                <td><label for="resize_select">Размер фото</label><select id="resize_select"
                                                                          onchange="resizeWallpaper()">
                    <option value="tiny">маленький</option>
                    <option selected value="medium">средний</option>
                    <option value="full">большой</option>
                </select></td>
            </tr>
        </table>

        <div>
            <label for="category_id_pages">Текущий раздел</label>
            <select id="category_id_pages" name="id_pages" class="long_text_area" onchange="redraw(this.value)">
                <option selected value="">----Выберите раздел----</option>
                <c:forEach items="${categories_wallpaper_select}" var="category">
                    <option value="${category.id}">${category.name}</option>
                </c:forEach>
            </select>
            <br>
            <hr>
        </div>
        <%-- top pagination --%>
        <table border="0" cellpadding="0" cellspacing="0">
            <tr id="link0.">
                <td><a class="vis_nav" href="javascript:getWallpaper(cur_index-1);">prev</a></td>
                <td><a class="vis_nav" href="javascript:getWallpaper(cur_index+1);">next</a></td>
            </tr>
        </table>
        <%-- wallpaper image --%>
        <table border="0">
            <tr>
                <td height="100%" style="border:solid black 1px;cursor:pointer;" title="prev"
                    onclick="getWallpaper(cur_index-1)">&lt;</td>
                <td id="wallpaper_img_cell"><img alt="loading" id="wallpaper_img"
                                                 src="${pageContext.servletContext.contextPath}/img/loading.gif"></td>
                <td height="100%" style="border:solid black 1px;cursor:pointer;" title="next"
                    onclick="getWallpaper(cur_index+1)">&gt;</td>
            <tr>
        </table>
        <%-- the form to update wallpaper --%>
        <form id="form_one" action="?" method="post" enctype="multipart/form-data" target="upload_target">
            <input type="hidden" name="do" value="ajax_update">
            <input type="hidden" name="action" value="ajax_update">
            <input type="hidden" name="id" id="wallpaper_id">

            <div class="form_row right"><label for="wallpaper_name">Имя файла:</label><input id="wallpaper_name"
                                                                                             type="text" name="name"
                                                                                             class="long_text_area"/>
            </div>
            <div class="form_row right"><label for="wallpaper_folder">Папка:</label><input id="wallpaper_folder"
                                                                                             type="text" name="folder"
                                                                                             class="long_text_area"/>
            </div>
            <div class="form_row right"><label for="wallpaper_descr">Описание:</label><input id="wallpaper_descr"
                                                                                             type="text"
                                                                                             name="description"
                                                                                             class="long_text_area"/>
            </div>
            <div class="form_row right"><label for="wallpaper_title">Титул:</label><input id="wallpaper_title"
                                                                                          type="text" name="title"
                                                                                          class="long_text_area"/></div>
            <div class="form_row right"><label for="wallpaper_tags">Теги:</label><input id="wallpaper_tags" type="text"
                                                                                        name="tags"
                                                                                        class="long_text_area"/></div>
            <div class="form_row right"><label for="wallpaper_id_pages">Страница:</label><select id="wallpaper_id_pages"
                                                                                                 name="id_pages"
                                                                                                 class="long_text_area">
                <c:forEach items="${categories_wallpaper_select}" var="category">
                    <option value="${category.id}">${category.name}</option>
                </c:forEach>
            </select>
            </div>
            <div class="form_row left">Разрешение: <span id="wallpaper_resolution"></span></div>
            <table class="form_row" border="0" width="100%">
                <tr>
                    <td align="left"><label for="wallpaper_active">Активный:</label><input id="wallpaper_active"
                                                                                           type="checkbox" name="active"
                                                                                           value="1"/><input
                            type="hidden" name="_active" value="on"/></td>
                    <td align="left"><label for="wallpaper_optimized">Оптимиз.:</label><input id="wallpaper_optimized"
                                                                                              type="checkbox"
                                                                                              name="optimized"
                                                                                              value="1"/><input
                            type="hidden" name="_optimized" value="on"/></td>
                    <td align="left"><label for="wallpaper_optimized_manual">Оптимиз. вручную:</label><input
                            id="wallpaper_optimized_manual" type="checkbox" name="optimized_manual" value="1"/><input
                            type="hidden" name="_optimized_manual" value="on"/></td>
                    <td align="right" id="wallpaper_content_cell"><label for="wallpaper_content">Фото:</label><input
                            type="file" name="content" id="wallpaper_content"></td>
                </tr>
            </table>
            <div align="right">
                <img alt="uploading" src="${pageContext.servletContext.contextPath}/img/loading.gif" id="upload_process"
                     style="display:none;"/>
                <img alt="success" src="${pageContext.servletContext.contextPath}/img/success.gif" id="success_process"
                     style="display:none;"/>
                <img alt="fail" src="${pageContext.servletContext.contextPath}/img/fail.gif" id="fail_process"
                     style="display:none;"/>
                <input id="uploadButton" type="button" value="Сохранить" onclick="startUpload()"/>
                <input id="optimize" type="button" value="Оптимизировать" onclick="startOptimize(cur_index)"/>
            </div>
        </form>
        <%-- bottom pagination --%>
        <table border="0" cellpadding="0" cellspacing="0">
            <tr id="link1.">
                <td><a class="vis_nav" href="javascript:getWallpaper(cur_index-1);">prev</a></td>
                <td><a class="vis_nav" href="javascript:getWallpaper(cur_index+1);">next</a></td>
            </tr>
        </table>
    </div>
    <%-- iframe for updating wallpaper (send a post request to server) --%>
    <iframe id="upload_target" name="upload_target" src="#" style="width:0;height:0;border:0 solid #fff;"></iframe>
</div>

<script type="text/javascript">
    <c:choose>
    <c:when test="${not empty paramValues['marker']}">
    <c:forEach items="${paramValues['marker']}" var="item" varStatus="i">
    items[${i.index}] = ${item};
    </c:forEach>
    draw_navigation('link0.');
    draw_navigation('link1.');
    getWallpaper(0);
    </c:when>
    <c:when test="${not empty param['id_pages_nav']}">
    document.getElementById('category_id_pages').value = ${param['id_pages_nav']};
    redraw('${param['id_pages_nav']}');
    </c:when>
    <c:otherwise>redraw('');
    </c:otherwise>
    </c:choose>
</script>