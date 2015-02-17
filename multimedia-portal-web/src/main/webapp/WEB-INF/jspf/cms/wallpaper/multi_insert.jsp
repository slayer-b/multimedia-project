<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/scripts/wallpaper.js"></script>
<script type="text/javascript">
function getElementCells() {
    myElementsArray = new Array('active_cell', '_active_cell', 'id_pages_cell', 'content_cell', 'description_cell');
    return myElementsArray;
}

function getElementNames() {
    myElementsArray = new Array('active', '_active', 'id_pages', 'content', 'description');
    return myElementsArray;
}

/**
 * adds attachment only if all content elements are not empty
 */
function addAttachment() {
    for (i = 0; i < index; i++) {
        element = document.getElementById('content.' + i);
        if ((element.files && element.files.length == 0) && (!element.disabled)) {
            return;
        }
    }
    createNextAttachment('attachments_table', 'store_table', 'store_div');
}
/**
 * append index to name and id of an element
 */
function setNameId(element) {
    element.id = element.name + '.' + index;
    setName(element, index);
}

function setName(element, number) {
    if (element.name[0] == '_') {
        element.name = '_data[' + number + '].' + element.name.substring(1);
    } else {
        element.name = 'data[' + number + '].' + element.name;
    }
}

/**
 * adds quantity of attachments
 */
function addAttachments(quantity) {
    for (i = 0; i < quantity; i++) {
        createNextAttachment('attachments_table', 'store_table', 'store_div');
    }
}

function check_all() {
    value = document.getElementById('check_all_checkbox').checked;
    for (i = 0, len = active_elements_array.length; i < len; i++) {
        active_elements_array[i].checked = value;
    }
}

function setOnePage() {
    one_select = document.getElementById('id_pages_one');
    if (one_select.disabled) {
        one_select.disabled = false;
        one_select.style.display = '';
        for (i = 0, len = id_pages_elements_array.length; i < len; i++) {
            id_pages_elements_array[i].style.display = 'none';
            id_pages_elements_array[i].disabled = true;
            document.getElementById(id_pages_elements_array[i].name + '_row').style.display = 'none';
        }
    } else {
        one_select.disabled = true;
        one_select.style.display = 'none';
        for (i = 0, len = id_pages_elements_array.length; i < len; i++) {
            document.getElementById(id_pages_elements_array[i].name + '_row').style.display = '';
            id_pages_elements_array[i].style.display = '';
            id_pages_elements_array[i].disabled = false;
        }
    }
}

function addFile(evt) {
    if (document.getElementById('new_attachment_checkbox').checked) {
        addAttachment();
    }
    if (document.getElementById('description_checkbox').checked) {
        if (evt && evt.target)    setDescription(evt.target);
    }
}

function setDescription(file) {
    if (file.files) {
        startI = file.id.lastIndexOf('.');
        cur_index = file.id.substring(startI + 1);
        startI = file.files[0].fileName.lastIndexOf('.');
        document.getElementById('description.' + cur_index).value = file.files[0].fileName.substring(0, startI);
    }
}

function showArray(elements, checkbox) {
    if (checkbox.checked) {
        for (i = 0, len = elements.length; i < len; i++) {
            elements[i].style.display = '';
            document.getElementById(elements[i].name + '_row').style.display = '';
        }
    } else {
        for (i = 0, len = elements.length; i < len; i++) {
            elements[i].style.display = 'none';
            document.getElementById(elements[i].name + '_row').style.display = 'none';
        }
    }
}

function setSent(evt) {
    //window.alert('setSend');
    checkbox = evt.target;
    cur_index = checkbox.getAttribute('index');
    if (checkbox.checked) {
        document.getElementById('id.' + cur_index).value = '0';
        disableWallpaper(cur_index, false);
    } else {
        document.getElementById('id.' + cur_index).value = '';
        disableWallpaper(cur_index, true);
    }
}

//the number last sended element
var last_send_num = 0;

function startUpload() {
    document.getElementById('uploadButton.0').disabled = true;
    document.getElementById('uploadButton.1').disabled = true;
    if (!makeUpload()) {
        document.getElementById('uploadButton.0').disabled = false;
        document.getElementById('uploadButton.1').disabled = false;
        return true;
    } else {
        return false;
    }
}

function stopUpload(rez) {
    //getting sended table and putting it back to main table
    element = document.getElementById('store_table.' + last_send_num);
    form_one.removeChild(element);
    document.getElementById('attachments_table_cell.' + last_send_num).appendChild(element);
    document.getElementById('send.' + last_send_num).checked = false;
    document.getElementById('send.' + last_send_num).disabled = true;
    document.getElementById('id.' + last_send_num).value = '';

    setName(document.getElementById('description.' + i), i);
    setName(document.getElementById('active.' + i), i);
    setName(document.getElementById('_active.' + i), i);
    setName(document.getElementById('content.' + i), i);

    document.getElementById('upload_process').style.display = 'none';
    disableWallpaper(last_send_num, true);

    if (rez == 1) {
        pic = document.getElementById('success_process').cloneNode(true);
        pic.style.display = '';
        document.getElementById('attachments_table_cell2.' + i).appendChild(pic);
    } else {
        pic = document.getElementById('fail_process').cloneNode(true);
        pic.style.display = '';
        document.getElementById('attachments_table_cell2.' + i).appendChild(pic);
    }

    //check if we need to upload another file
    if (!makeUpload()) {
        document.getElementById('uploadButton.0').disabled = false;
        document.getElementById('uploadButton.1').disabled = false;
        return true;
    } else {
        return false;
    }
}

function makeUpload() {
    for (i = last_send_num; i < index; i++) {
        if (document.getElementById('id_pages_one').disabled) {
            document.getElementById('form_one_id_pages').value = document.getElementById('id_pages.' + i).value;
        } else {
            document.getElementById('form_one_id_pages').value = document.getElementById('id_pages_one').value;
        }
        //if (document.getElementById('send.'+i).checked&&(document.getElementById('content.'+i).files.length>0)){
        //alert(document.getElementById('content.'+i).value);
        if (document.getElementById('send.' + i).checked && (document.getElementById('content.' + i).value)) {
            element = document.getElementById('store_table.' + i);

            document.getElementById('id.' + i).disabled = true;
            document.getElementById('description.' + i).name = 'description';
            document.getElementById('active.' + i).name = 'active';
            document.getElementById('_active.' + i).name = '_active';
            document.getElementById('content.' + i).name = 'content';

            //upload_target = getIFrame('upload_target',i);

            document.getElementById('upload_process').style.display = '';
            document.getElementById('attachments_table_cell2.' + i).appendChild(document.getElementById('upload_process'));

            //setting names of table elements to appropriate value
            document.getElementById('form_one').target = 'upload_target';
            document.getElementById('form_one').appendChild(element);
            document.getElementById('form_one').submit();
            last_send_num = i;
            return true;
        }
    }
    window.alert('all files are send');
    return false;
}

//various scripts for wallpaper
var index = 0;
var active_elements_array = new Array();
var description_elements_array = new Array();
var id_pages_elements_array = new Array();
/**
 * set wallpaper elements disabled attribute to a given value
 */
function disableWallpaper(cur_index, value) {
    document.getElementById('description.' + cur_index).disabled = value;
    document.getElementById('active.' + cur_index).disabled = value;
    document.getElementById('_active.' + cur_index).disabled = value;
    document.getElementById('content.' + cur_index).disabled = value;
    document.getElementById('id_pages.' + cur_index).disabled = value;
}
/**
 * create a new cell in given table and append element to it
 */
function addNewElementToTable(table, element) {
    newRow = table.insertRow(0);
    newCell_1 = newRow.insertCell(newRow.cells.length);
    newCell_2 = newRow.insertCell(newRow.cells.length);

    element.style.display = '';
    element.id = element.id + '.' + index;

    newCell_1.id = 'attachments_table_cell.' + index;
    newCell_2.id = 'attachments_table_cell2.' + index;
    newCell_2.style.width = '25px';

    newCell_1.appendChild(element);
}

function getElements() {
    active_el = document.createElement('input');
    active_el.name = 'active';
    active_el.type = 'checkbox';
    active_el.value = 'true';
    active_el.checked = document.getElementById('check_all_checkbox').checked;
    active_elements_array[index] = active_el;

    _active_el = document.createElement('input');
    _active_el.name = '_active';
    _active_el.type = 'hidden';
    _active_el.value = 'on';
    if (document.getElementById('show_active_checkbox').checked) {
        active_el.style.display = '';
        _active_el.style.display = '';
    } else {
        active_el.style.display = 'none';
        _active_el.style.display = 'none';
    }

    id_pages_el = document.createElement('select');
    id_pages_el.name = 'id_pages';
    id_pages_el.className = 'long_text_area';
    id_pages_elements_array[index] = id_pages_el;
    if (!document.getElementById('id_pages_one').disabled) {
        id_pages_el.style.display = 'none';
        id_pages_el.disabled = true;
    }
    options_el = document.getElementById('options_cell').cloneNode(true);
    while (options_el.firstChild) {
        id_pages_el.appendChild(options_el.firstChild);
    }

    description_el = document.createElement('input');
    description_el.name = 'description';
    description_el.type = 'text';
    description_el.className = 'long_text_area';
    if (document.getElementById('show_description_checkbox').checked)
        description_el.style.display = '';
    else
        description_el.style.display = 'none';
    description_elements_array[index] = description_el;

    content_el = document.createElement('input');
    content_el.name = 'content';
    content_el.type = 'file';

    if (content_el.addEventListener) {
        content_el.addEventListener('change', addFile, false);//Mozilla
    } else if (content_el.attachEvent) {
        content_el.attachEvent('onchange', addFile);//MSIE
    }
    content_el.style.width = '220px';

    myElementsArray = new Array(_active_el, active_el, id_pages_el, content_el, description_el);
    return myElementsArray;
}

function createNextAttachment(attachments_table, tableName, divName) {
    //checking if we now making all for one page (if true then drawing disabled elements)

    attachmentsTable = document.getElementById(attachments_table);
    oldItem = document.getElementById(tableName);
    storeDiv = document.getElementById(divName);

    elements_arr = getElements();
    element_names_arr = getElementNames();
    cells_arr = getElementCells();

    num_o = document.getElementById('number_cell');
    send_o = document.getElementById('send');
    send_o.setAttribute('index', index);

    newItem = oldItem.cloneNode(true);

    send_o.id = 'send.' + index;
    num_o.appendChild(document.createTextNode(index));
    num_o.id = '';

    for (e = 0, len = elements_arr.length; e < len; e++) {
        setNameId(elements_arr[e]);
        old_element = document.getElementById(element_names_arr[e]);
        old_cell = document.getElementById(cells_arr[e]);
        old_cell.replaceChild(elements_arr[e], old_element);
        old_cell.id = index;
        old_cell.parentNode.parentNode.parentNode.parentNode.id = elements_arr[e].name + '_row';
        old_cell.parentNode.parentNode.parentNode.parentNode.style.display = elements_arr[e].style.display;
    }

    id_o = document.createElement('input');
    id_o.name = 'id';
    id_o.type = 'hidden';
    id_o.id = 'id.' + index;
    id_o.value = index;

    oldItem.appendChild(id_o);

    storeDiv.appendChild(newItem);
    addNewElementToTable(attachmentsTable, oldItem);

    index = index + 1;
}
</script>
<div align="center" class="form_head">Добавить фото</div>
<%@ include file="../../messages/error.jsp" %>
<%@ include file="../../messages/help.jsp" %>
<c:if test="${wallpaper_count ne null}">
    <div align="center">Добавлено ${wallpaper_count} елементов из ${wallpaper_quantity}</div>
</c:if>
<%-- main table --%>
<form:form action="?id_pages_nav=${param['id_pages_nav']}" method="post" commandName="content_data"
           enctype="multipart/form-data">
    <table align="center">
        <input type=hidden name="action" value="insertMulti">
        <input type=hidden name="do" value="insertMulti">
        <tr>
            <td colspan="2" align=center>Меню</td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <table border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td valign="middle"><input id="show_description_checkbox" type="checkbox"
                                                   onclick="showArray(description_elements_array,this);"></td>
                        <td valign="middle"><label for="show_description_checkbox">Показать описание</label></td>
                        <td style="width:10px;"></td>
                        <td valign="middle"><input id="show_active_checkbox" type="checkbox"
                                                   onclick="showArray(active_elements_array,this);"></td>
                        <td valign="middle"><label for="show_active_checkbox">Показать активность</label></td>
                        <td style="width:10px;"></td>
                        <td valign="middle"><input id="one_page_checkbox" type="checkbox" checked
                                                   onclick="setOnePage();"></td>
                        <td valign="middle"><label for="one_page_checkbox">Одна страница для всех картинок</label></td>
                        <td style="width:10px;"></td>
                        <td valign="middle"><input id="check_all_checkbox" type="checkbox" checked
                                                   onclick="check_all();"></td>
                        <td valign="middle"><label for="check_all_checkbox">Все выбраны</label></td>
                        <td style="width:10px;"></td>
                        <td valign="middle"><input id="description_checkbox" checked type="checkbox"/></td>
                        <td valign="middle"><label for="description_checkbox">Описание как имя файла</label></td>
                        <td style="width:10px;"></td>
                        <td valign="middle"><input id="new_attachment_checkbox" checked type="checkbox"/></td>
                        <td valign="middle"><label for="new_attachment_checkbox">Новое поле после прикрепления
                            файла</label></td>
                    </tr>
                    <tr>
                        <td colspan="17">
                            <hr/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" colspan="17">
                            <a class="link" id="add_new_attachment_a" href="javascript:addAttachments(10);">+10
                                полей</a>&nbsp;&nbsp;&nbsp;
                            <input type="submit" value="Сохранить">
                            <input id="uploadButton.1" type="button" value="Сохранить по одной" onclick="startUpload()">
                        </td>
                    </tr>
                </table>
        <tr>
            <td align="center" colspan="2"><form:select id="id_pages_one" path="id_pages_one" cssClass="long_text_area"
                                                        items="${categories_wallpaper_select}" itemLabel="name"
                                                        itemValue="id"/></td>
        </tr>

        <tr>
            <td>
                <table id="attachments_table" align="center">
                </table>
            </td>
        </tr>
        <tr>
            <td align="center">
                <input type="submit" value="Сохранить">
                <input id="uploadButton.0" type="button" value="Сохранить по одной" onclick="startUpload()">
            </td>
        </tr>

    </table>
</form:form>
<%-- HTML template for making new attachments(fields) --%>
<form:form commandName="empty_object">
    <div id="store_div" style="display:none;">
        <table id="store_table" align="center" style="background-color:#F5F5F5;" border="0" cellpadding="0"
               cellspacing="0">
            <input type=hidden name="_marker" value="1">
            <tr>
                <td rowspan="6" id="number_cell"></td>
            </tr>
            <tr>
                <td rowspan="6" valign="middle" style="border-right:white solid 4px;"><input id="send" type="checkbox"
                                                                                             checked
                                                                                             onchange="setSent(event)">
                </td>
            </tr>
            <tr>
                <td align="center">
                    <table>
                        <tr>
                            <td class=bold>Описание *:</td>
                        </tr>
                        <tr>
                            <td align="center" id="description_cell"><input type="text" name="description"
                                                                            id="description" class="long_text_area"/>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td align="center">
                    <table>
                        <tr>
                            <td class=bold>Фото *:</td>
                            <td align="center" id="content_cell"><input type="file" name="content" id="content"
                                                                        size="80px"></td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr id="id_pages_row">
                <td align="center">
                    <table>
                        <tr>
                            <td class=bold>Категория *:</td>
                        </tr>
                        <tr>
                            <td align="center" id="id_pages_cell">
                                <form:select path="id_pages" cssClass="long_text_area">
                                    <optgroup label="страницы" id="options_cell"><form:options
                                            items="${categories_wallpaper_select}" itemLabel="name"
                                            itemValue="id"/></optgroup>
                                </form:select>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td align="center">
                    <table>
                        <tr>
                            <td valign="middle" class="bold">Активный *:</td>
                            <td id="active_cell"><input id="active" name="active" type="checkbox" value="true"
                                                        checked="true"/></td>
                            <td id="_active_cell"><input type="hidden" id="_active" name="_active" value="on"/></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </div>
</form:form>
<%-- for sending files by one --%>
<form id="form_one" action="?" method="post" enctype="multipart/form-data" target="upload_target" style="display:none;">
    <input type="hidden" name="do" value="ajax_insert">
    <input type="hidden" name="action" value="ajax_insert">
    <input type="hidden" name="id_pages" id="form_one_id_pages">
    <input type="hidden" name="id_pages_nav" value="${param['id_pages_nav']}">
</form>
<iframe id="upload_target" name="upload_target" src="#" style="width:0;height:0;border:0 solid #fff;"></iframe>
<img alt="loading" src="${pageContext.servletContext.contextPath}/img/loading.gif" id="upload_process"
     style="display:none;"/>
<img alt="success" src="${pageContext.servletContext.contextPath}/img/success.gif" id="success_process"
     style="display:none;"/>
<img alt="fail" src="${pageContext.servletContext.contextPath}/img/fail.gif" id="fail_process" style="display:none;"/>

<script type="text/javascript">
    createNextAttachment('attachments_table', 'store_table', 'store_div');
</script>