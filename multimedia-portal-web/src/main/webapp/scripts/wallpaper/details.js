/* 
 *  Copyright 2010 demchuck.dima@gmail.com
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
if (window.addEventListener) {
    window.addEventListener('keydown', respondToClickMuzilla, false);
} else if (window.attachEvent) {
    document.body.attachEvent("onkeydown", respondToClickMSIE);
}
function respondToClickMuzilla(event) {
    //if (event.keyCode==Event.KEY_LEFT){
    if (event.keyCode == 37) {
        prev_pic();
    } else if (event.keyCode == 39) {
        next_pic();
    }
}
function respondToClickMSIE() {
    //if (event.keyCode==Event.KEY_LEFT){
    if (event.keyCode == 37) {
        prev_pic();
    } else if (event.keyCode == 39) {
        next_pic();
    }
}

function rate_wallpaper(rate) {
    document.getElementById("rating_img").style.display = '';
    document.getElementById("rating_table").style.display = 'none';
    $.ajax({
        type: "get",
        url: "index.htm",
        dataType: "text",
        data: {
            rate: rate,
            id_pages_nav: wallpaper_rate_id,
            id_photo: wallpaper_id
        },
        success: function (data) {
            window.console.log(data);
            if (data.match(/0/)) {
                alert('Ваш голос защитан.');
            } else {
                alert('Ваш голос не защитан.');
            }
            $("#rating_div").hide("slow");
        }
    });
}
function comment_wallpaper(event) {
    event.preventDefault();

    $.ajax({
        type: "get",
        url: "index.htm?id_pages_nav=" + wallpaper_comment_id + "&action=insert&id_photo=" + wallpaper_id + "&" + $("#wall_comment_form").serialize(),
        dataType: "text",
        success: function (data) {
                if (data.match(/1/)) {
                    $("<div class='wall_comment'></div>").append($("<span>добавлено сейчас</span>")).fadeIn(500).appendTo("#wall_comment_new");
                    $("#wall_comment_new>div:last-child").append($("<p class='data'></p>").text($("#wall_comment_area").val()));

                    refresh_anti_spam();
                    $("#wall_comment_area").val("");
                    $("#wall_comment_code").val("");
                } else {
                    alert('Ошибка.');
                    refresh_anti_spam();
                }
            }
    });
}