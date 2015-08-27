var plus_img = context_path+'/img/rubrication/plus.gif';
var minus_img = context_path+'/img/rubrication/minus.gif';
function show_hide(id) {
	var switcher = document.getElementById('img_'+id);
	var element = document.getElementById('id_'+id);
	if ($("#id_"+id).is(":empty")){
        $.ajax({
            url: context_path + "/ajax.json",
            data: {
                id_pages:id
            },
            dataType: "json",
            type: "get",
            success: function(data) {
                for (i=0;i<data.length;i++){
                    var url = context_path+"/index.htm?id_pages_nav="+data[i].id;
                    if (data[i].last==true){
                        url = url + "&amp;page_number=0";
                        if (module_name==data[i].moduleName)url = url + module_params;
                        $("<div class='r_x r_b r_l'></div>")
                            .append("<a href='"+url+"'>"+data[i].name+"</a>")
                            .appendTo("#id_"+id);
                    }else{
                        if (module_name==data[i].moduleName)url = url + module_params;
                        $("<div class='r_x r_b'></div>")
                            .append("<img id='img_"+data[i].id+"' src='"+plus_img+"' alt='+' onclick='show_hide("+data[i].id+")'> ")
                            .append("<a href='"+url+"'>"+data[i].name+"</a>")
                            .appendTo("#id_"+id);
                        $("<div id='id_"+data[i].id+"' class='r_m' style='display:none;'></div>")
                            .appendTo("#id_"+id);
                    }
                }
                $("#id_"+id).slideDown(500);
                switcher.src = minus_img;
                switcher.alt = '-';
                adjustHeight();
            }
        });
	} else {
		if (element.style.display == 'none'){
			$("#id_"+id).slideDown(500);
			switcher.src = minus_img;
			switcher.alt = '-';
		} else {
			$("#id_"+id).slideUp(500);
			switcher.src = plus_img;
			switcher.alt = '+';
		}
        adjustHeight()
	}
}