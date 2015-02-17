function confirmation_(href_val_par,text){
    var flag=confirm(text);
    if (flag){
        window.location.href=href_val_par;
    }
}

function set_sort_0(sortObjects){
	for(el in sortObjects){
		sortObjects[el].value=0;
	}
}

function sort_all(sortObjects){
	for(el in sortObjects){
		sortObjects[el].value=el;
	}
}

function show_hide(obj){
	if (obj){
		if (obj.style.display==''){
			obj.style.display='none';
		}else{
			obj.style.display='';
		}
	}
}

function show_hide_arr(obj_arr){
	for (i in obj_arr){
		if (obj[i]){
			if (obj[i].style.display==''){
				obj[i].style.display='none';
			}else{
				obj[i].style.display='';
			}
		}
	}
}
/* disables all elements with given prefixes id_val is an ending and name of an appropriate checkbox */
function reset_element(id_prefix,id_val){
	if (document.getElementById(id_val).checked){
		for (i=0;i<id_prefix.length;i++){
			el = document.getElementById(id_prefix[i]+id_val);
			el.disabled = false;
		}
	}else{
		for (i=0;i<id_prefix.length;i++){
			el = document.getElementById(id_prefix[i]+id_val);
			el.disabled = true;
		}
	}
}
/*
 *disables all elements with given prefixes
 * count is quantity of these elements
 * id_element is an id of element that specifies whether to disable or enable these element
 **/
function reset_all(id_prefix,id_element,count){
	if (document.getElementById(id_element).checked){
		for (j=0;j<count;j++){
			document.getElementById(j).checked = true;
			for (i=0;i<id_prefix.length;i++){
				el = document.getElementById(id_prefix[i]+j);
				el.disabled = false;
			}
		}
	}else{
		for (j=0;j<count;j++){
			document.getElementById(j).checked = false;
			for (i=0;i<id_prefix.length;i++){
				el = document.getElementById(id_prefix[i]+j);
				el.disabled = true;
			}
		}
	}
}
/*
 *disables all elements with given prefixes
 * count is quantity of these elements
 * new_val new value that will be set to a disabled element
 **/
function disable_all(id_prefix,count,new_val){
	for (j=0;j<count;j++){
		for (i=0;i<id_prefix.length;i++){
			el = document.getElementById(id_prefix[i]+j);
			el.disabled = new_val;
		}
	}
}

/**
 * sets new_value as a value of all elements with id = 'prefix'+i
 * where i=0..count
 */
function set_value(prefix,count,new_value){
	for (j=0;j<count;j++){
		el = document.getElementById(prefix+j);
		el.value = new_value;
	}
}
/**
 * sets i as a value of all elements with id = 'prefix'+i
 * where i=0..count
 */
function set_value_count(prefix,count){
	for (j=0;j<count;j++){
		el = document.getElementById(prefix+j);
		el.value = j;
	}
}
/**
 * replaces parameter value with name param_name by param_value
 */
function replace_param(params, param_name, param_value){
	reg = new RegExp("([;|?|&]{1}"+param_name+"=)([^&]*)");
	if (params.toString().match(reg)){
		return params.toString().replace(reg, "$1"+param_value);
	} else {
		return params.toString() + "&amp;"+param_name+"="+param_value;
	}
}