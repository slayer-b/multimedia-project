var spam_num = 0;
function refresh_anti_spam(){
	$("#wall_anti_spam").attr("src", context_path+"/images/code.jpg?p="+spam_num);
	spam_num++;
}
$("#wall_anti_spam").bind('click', refresh_anti_spam);