//indicates that operation is allready in progress
var flag_wallpaper_update_ajax_operation = false;

function startUpload() {
    if (flag_wallpaper_update_ajax_operation) {
        alert('operation in progress');
        return false;
    }
    flag_wallpaper_update_ajax_operation = true;

    document.getElementById('uploadButton').disabled = true;
    form_one.target = 'upload_target';
    form_one.submit();
    document.getElementById('fail_process').style.display = 'none';
    document.getElementById('success_process').style.display = 'none';
    document.getElementById('upload_process').style.display = '';

    return true;
}

function stopUpload(rez) {
    if (rez == '0') {
        document.getElementById('fail_process').style.display = '';
    } else {
        getWallpaper(cur_index);
        document.getElementById('success_process').style.display = '';
    }
    document.getElementById('uploadButton').disabled = false;
    document.getElementById('upload_process').style.display = 'none';
    flag_wallpaper_update_ajax_operation = false;
}

/**
 * receive wallpaper with given index from server
 */
function getWallpaper(index) {
    if ((index < 0) || (index >= items.length)) {
        alert('no such wallpaper');
        return;
    }
    document.getElementById('fail_process').style.display = 'none';
    document.getElementById('success_process').style.display = 'none';

    url2 = url + '&id=' + items[index] + '&json_name=?';
    $.getJSON(url2, function (obj) {
        setWallpaper(obj);
        reset_navigation(index);
    });

}

/**
 * receive wallpaper with given index from server
 */
function startOptimize(index) {
    if (flag_wallpaper_update_ajax_operation) {
        alert('operation in progress');
        return;
    }
    if ((index < 0) || (index >= items.length)) {
        alert('no such wallpaper');
        return;
    }
    flag_wallpaper_update_ajax_operation = true;
    document.getElementById('fail_process').style.display = 'none';
    document.getElementById('success_process').style.display = 'none';
    document.getElementById('upload_process').style.display = '';

    url2 = url_opt + '&id=' + items[index] + '&json_name=?';
    $.getJSON(url2, function (obj) {
        flag_wallpaper_update_ajax_operation = false;
        document.getElementById('success_process').style.display = '';
        document.getElementById('upload_process').style.display = 'none';
        setWallpaper(obj);
        reset_navigation(index);
    });

}

/*
 *changes the style of previous and current navigation+
 *shows only quantity of items showen around cur page
 *also first and last is allways displayed other elements are masked
 **/
function reset_navigation(index) {
    cur_items_count = new Number(document.getElementById('nav_items_count').value);

    document.getElementById('link0.' + cur_index).className = 'vis_nav';
    document.getElementById('link1.' + cur_index).className = 'vis_nav';
    /*$('link0.'+cur_index).className='vis_nav';
     $('link1.'+cur_index).className='vis_nav';*/
    mask_navigation('link0.', 'invis_nav', cur_items_count);
    mask_navigation('link1.', 'invis_nav', cur_items_count);

    cur_index = index;

    mask_navigation('link0.', 'vis_nav', cur_items_count);
    mask_navigation('link1.', 'vis_nav', cur_items_count);
    /*$('link0.'+cur_index).className='cur_nav';
     $('link1.'+cur_index).className='cur_nav';*/
    document.getElementById('link0.' + cur_index).className = 'cur_nav';
    document.getElementById('link1.' + cur_index).className = 'cur_nav';
}

function resize_navigation() {
    cur_items_count = new Number(document.getElementById('nav_items_count').value);
    old_items_count = new Number(document.getElementById('nav_items_count').getAttribute('old_value'));

    if (cur_items_count < old_items_count) {
        mask_navigation('link0.', 'invis_nav', old_items_count);
        mask_navigation('link1.', 'invis_nav', old_items_count);
    }
    mask_navigation('link0.', 'vis_nav', cur_items_count);
    mask_navigation('link1.', 'vis_nav', cur_items_count);

    /*$('link0.'+cur_index).className='cur_nav';
     $('link1.'+cur_index).className='cur_nav';*/
    document.getElementById('link0.' + cur_index).className = 'cur_nav';
    document.getElementById('link1.' + cur_index).className = 'cur_nav';
    document.getElementById('nav_items_count').setAttribute('old_value', cur_items_count);
}

function mask_navigation(prefix, new_style, cur_items_count) {
    items_visible = 0;
    //*excluding first and last items
    i = cur_index - ((cur_items_count + cur_items_count % 2) / 2);
    len = cur_index + ((cur_items_count - cur_items_count % 2) / 2);
    if (i < 1) i = 1;
    if (len > items.length - 1) len = items.length - 1;
    for (i2 = i; i2 < len; i2++) {
        nav1 = document.getElementById(prefix + i2);
        nav1.className = new_style;
        items_visible = items_visible + 1;
    }
    //making that allways one quantity of elements is showen
    if (cur_items_count > items_visible) {
        //show items_visible elements
        i = i - 1;
        while ((cur_items_count > items_visible) && (i > 1)) {
            nav1 = document.getElementById(prefix + i);
            nav1.className = new_style;
            items_visible = items_visible + 1;
            i = i - 1;
        }
        len = len + 1;
        while ((cur_items_count > items_visible) && (len < items.length - 1)) {
            nav1 = document.getElementById(prefix + len);
            nav1.className = new_style;
            items_visible = items_visible + 1;
            len = len + 1;
        }
    }
}

/**
 * draw received wallpaper
 */
function setWallpaper(p) {
    //cur_size = 'medium';

    if (p.folder) {
        document.getElementById('wallpaper_img').src = contextPath + '/images/wallpaper/' + cur_size + '/' + p.folder + '/' + p.name;
    } else {
        document.getElementById('wallpaper_img').src = contextPath + '/images/wallpaper/' + cur_size + '/' + p.name;
    }
    document.getElementById('wallpaper_name').value = p.name;
    document.getElementById('wallpaper_folder').value = p.folder;
    document.getElementById('wallpaper_descr').value = p.description;
    document.getElementById('wallpaper_title').value = p.title;
    document.getElementById('wallpaper_tags').value = p.tags;
    document.getElementById('wallpaper_id').value = p.id;
    document.getElementById('wallpaper_id_pages').value = p.id_pages;
    document.getElementById('wallpaper_resolution').innerHTML = p.width + "x" + p.height;
    document.getElementById('wallpaper_active').checked = p.active == 'true';
    document.getElementById('wallpaper_optimized').checked = p.optimized == 'true';
    document.getElementById('wallpaper_optimized_manual').checked = p.optimized_manual == 'true';

    cur_wallpaper = p;

    //deleting old element with wallpaper file and creating new (if it is not empty)
    wallpaper_file = document.getElementById('wallpaper_content');
    if (wallpaper_file.value != '') {
        document.getElementById('wallpaper_content_cell').removeChild(wallpaper_file);
        wallpaper_file = document.createElement('input');
        wallpaper_file.type = 'file';
        wallpaper_file.name = 'content';
        wallpaper_file.id = 'wallpaper_content';
        document.getElementById('wallpaper_content_cell').appendChild(wallpaper_file);
    }
}

/**
 * set path to 'size' wallpapers
 */
function resizeWallpaper() {
    cur_size = document.getElementById('resize_select').value;
    if (cur_wallpaper.folder) {
        document.getElementById('wallpaper_img').src = contextPath + '/images/wallpaper/' + cur_size + '/' + cur_wallpaper.folder + '/' + cur_wallpaper.name;
    } else {
        document.getElementById('wallpaper_img').src = contextPath + '/images/wallpaper/' + cur_size + '/' + cur_wallpaper.name;
    }
}

/* changes content of this page to new items */
function redraw(id_pages_nav) {
    url2 = url + '&id_pages_nav=' + id_pages_nav;

    $.post(url2, function (resp) {
            parsed = resp.split(',');
            //checking if parsed has at least one not empty element
            is_empty = true;
            for (i = 0, len = parsed.length; i < len; i++) {
                if (parsed[i] != '') {
                    is_empty = false;
                    break;
                } else {
                    parsed[i] = null;
                }
            }
            if (!is_empty) {
                cur_index = 0;
                delete_navigation('link0.');
                delete_navigation('link1.');
                items = new Array();
                k = 0;
                for (i = 0, len = parsed.length; i < len; i++) {
                    if (parsed[i] != null) {
                        items[k] = parsed[i];
                        k++;
                    }
                }
                draw_navigation('link0.');
                draw_navigation('link1.');
                getWallpaper(0);
            } else {
                alert('В етом разделе нет фото.');
            }
        }
    );
}

/* draws navigation using 'items' collection */
function delete_navigation(prefix) {
    nav_tr = document.getElementById(prefix);
    while (nav_tr.cells.length > 2) {
        nav_tr.deleteCell(1);
    }
}

/* draws navigation using 'items' collection */
function draw_navigation(prefix) {
    nav_tr = document.getElementById(prefix);
    for (i = 0, len = items.length; i < len; i++) {
        nav1 = document.createElement('a');
        nav1.id = prefix + i;
        nav1.className = 'invis_nav';
        nav1.href = 'javascript:getWallpaper(' + i + ');';
        nav1.innerHTML = items[i];
        cell1 = nav_tr.insertCell(nav_tr.cells.length - 1);
        cell1.appendChild(nav1);
    }
    document.getElementById(prefix + 0).className = 'vis_nav';
    document.getElementById(prefix + (items.length - 1)).className = 'vis_nav';
}

var slideShowMask = /[0-9]+/;
var slideShowDefault = 500;
/* starts slideshow */
function start_slideShow() {
    if (threadID == -1) {
        val = document.getElementById('slideShow_input').value;
        if (!slideShowMask.test(val)) {
            val = slideShowDefault;
            document.getElementById('slideShow_input').value = slideShowDefault;
        }
        threadID = window.setInterval(show_slide, val);
    } else {
        window.clearInterval(threadID);
        threadID = -1;
    }
}

function stop_slideshow() {
    window.clearInterval(threadID);
    threadID = -1;
    document.getElementById('slideShow_checkbox').checked = false;
}

function show_slide() {
    if ((cur_index + 1) >= items.length) {
        stop_slideshow();
    } else {
        getWallpaper(cur_index + 1);
    }
}