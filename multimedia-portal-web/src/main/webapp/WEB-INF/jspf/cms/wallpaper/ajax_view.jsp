<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="common" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="utils" uri="http://download-multimedia.com/tags/utils" %>

<script type="text/javascript" src="${pageContext.servletContext.contextPath}/scripts/wallpaper.js"></script>
<script type="text/javascript">
    var base_url = '${pageContext.servletContext.contextPath}/images/wallpaper/tiny/';
    var wallpapers = new Array();
    <c:forEach items="${content_data}" var="item" varStatus="i">
    p = new Wallpaper();
    p.active =${item.active};
    p.id =${item.id};
    p.id_pages =${item.id_pages};
    p.name = '${item.name}';
    p.folder = '${item.folder}';
    p.folderAndName = '${item.folderAndName}';
    wallpapers[${i.index}] = p;
    </c:forEach>

    function create_table() {
        y = new Number(document.getElementById('table_height_input').value);
        if (isNaN(y)) {
            document.getElementById('table_height_input').value = table.rows.length;
            return;
        }
        table = document.getElementById('main_table');
        while (table.rows.length > 0) {
            table.deleteRow(0);
        }
        count = 0;
        newRows = new Array();
        for (i = 0; i < y; i++) {
            newRows[i] = table.insertRow(table.rows.length);
        }
        while (count < wallpapers.length) {
            for (i = 0; i < y; i++) {
                element = document.createElement('img');
                element.id = 'img.' + count;
                element.src = base_url + wallpapers[count].folderAndName;

                element_href = document.createElement('a');
                element_href.id = 'img_cell.' + count;
                element_href.target = 'blank';
                element_href.href = '?do=update&id=' + wallpapers[count].id;
                element_href.appendChild(element);

                newCell = newRows[i].insertCell(newRows[i].cells.length);
                newCell.style.border = 'black solid 1px';
                newCell.appendChild(element_href);
                count++;
                if (count >= wallpapers.length) {
                    break;
                }
            }
        }
    }

    function set_table_width() {
        new_width = document.getElementById('table_width_input').value;
        if (/[0-9]+(px|%)$/.test(new_width)) {
            document.getElementById('main_div').style.width = new_width;
        } else {
            document.getElementById('table_width_input').value = document.getElementById('main_div').style.width;
        }
    }

</script>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td>
            <table>
                <tr>
                    <td><label for="table_height_input">Выводить по:</label><input type="text" id="table_height_input"
                                                                                   value="3" onchange="create_table()">
                    </td>
                    <td><label for="table_width_input">Ширина:</label><input type="text" id="table_width_input"
                                                                             value="500px" onchange="set_table_width()">
                    </td>
                    <td>
                        Картинок на странице:
                        <a class="link"
                           href="?<utils:replaceParam url="keep_parameters" parameterName="page_size" parameterNewValue="5"/>">5</a>
                        <a class="link"
                           href="?<utils:replaceParam url="keep_parameters" parameterName="page_size" parameterNewValue="10"/>">10</a>
                        <a class="link"
                           href="?<utils:replaceParam url="keep_parameters" parameterName="page_size" parameterNewValue="25"/>">25</a>
                        <a class="link"
                           href="?<utils:replaceParam url="keep_parameters" parameterName="page_size" parameterNewValue="50"/>">50</a>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <hr>
        </td>
    </tr>
    <%-- div for wallpaper preview --%>
    <tr>
        <td align="center">
            <div id="main_div" style="overflow:scroll;width:500px;height:100%;border:black solid 1px;">
                <table id="main_table"></table>
            </div>
        </td>
    </tr>
    <tr>
        <td align="center"><common:pager_noimage count="${page_counter}"/></td>
    </tr>
</table>
<script type="text/javascript">
    create_table();
    set_table_width();
</script>