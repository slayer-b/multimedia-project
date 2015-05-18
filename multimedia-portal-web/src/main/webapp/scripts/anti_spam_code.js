var spam_num = 0;
function refresh_anti_spam(){
	$("#wall_anti_spam").attr("src", context_path+"/code.jpg?p="+spam_num);
	spam_num++;
}