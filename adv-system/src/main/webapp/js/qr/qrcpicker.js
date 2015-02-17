function QrColorPicker(_defaultColor) {
	if(!_defaultColor) _defaultColor = "#FFFFFF";
	
	QrXPCOM.init();
	this.id = QrColorPicker.lastid++;
	this.defaultColor = _defaultColor;
	QrColorPicker.instanceMap["QrColorPicker"+this.id] = this;
}

QrColorPicker.prototype.getHTML = function() {
    var html = "<span class=\"QrComponent\" id=\"$pickerId\" onclick=\"javascript:void(QrColorPicker.popupPicker('$pickerId'));\"><div id=\"$pickerId_color\" class=\"QRchosenColor\" style=\"background-color:\$defaultColor;\"></div>\n<a href=\"javascript:void('QrColorPicker$pickerId');\"><span id=\"$pickerId_text\">$defaultColor</span></A></span>\n<DIV style=\"display:none; position:absolute; border:solid 1px gray;background-color:white;z-index:2;\" id=\"$pickerId_menu\"\n onmouseout=\"javascript:QrColorPicker.restoreColor('$pickerId');\" onclick=\"javascript:QrXPCOM.onPopup();\">\n\n<nobr><div class=\"QRcolorpicker\" onMouseMove=\"javascript:QrColorPicker.setColor(event,'$pickerId');\" onClick=\"javascript:QrColorPicker.selectColor(event,'$pickerId');\"></div></nobr><br><norm><div class=\"QRgraybar\" onMouseMove=\"javascript:QrColorPicker.setColor(event,'$pickerId');\" onClick=\"javascript:QrColorPicker.selectColor(event,'$pickerId');\"></div></nobr><br>\n<nobr><input type=\"text\" size=\"8\" id=\"$pickerId_input\" style=\"border:solid 1px gray;font-size:12pt;margin:1px;\" onkeyup=\"QrColorPicker.keyColor('$pickerId')\" value=\"$defaultColor\"/> <a href=\"javascript:QrColorPicker.transparent('$pickerId');\"><div class=\"QRtransparent\"></div> transparent</nobr></div>";
	return html.replace(/\$pickerId/g, "QrColorPicker" + this.id).replace(/\$defaultColor/g, this.defaultColor);
}

QrColorPicker.prototype.render = function(){
	document.write(this.getHTML());
}

QrColorPicker.prototype.set = function(color){
	if(QrColorPicker.instanceMap["QrColorPicker"+this.id].onChange){
		QrColorPicker.instanceMap["QrColorPicker"+this.id].onChange(color);
	}
	if(color == "") color = "transparent";
	document.getElementById("QrColorPicker"+this.id+"_input").value = color;
	document.getElementById("QrColorPicker"+this.id+"_text").innerHTML = color;
	document.getElementById("QrColorPicker"+this.id+"_color").style.background = color;
}

QrColorPicker.prototype.get = function(){
	return document.getElementById("QrColorPicker"+this.id+"_input").value;
}

QrColorPicker.lastid = 0;

QrColorPicker.instanceMap = new Array;
QrColorPicker.restorePool = new Array;

QrColorPicker.transparent= function(id){
	QrColorPicker.instanceMap[id].set("transparent");
	document.getElementById(id+"_menu").style.display = "none";
	if(QrColorPicker.instanceMap[id].onChange){
		QrColorPicker.instanceMap[id].onChange("transparent");
	}
}

QrColorPicker.popupPicker= function(id){
	var pop = document.getElementById(id);
	var p = QrXPCOM.getDivPoint(pop);
	QrXPCOM.setDivPoint(document.getElementById(id+"_menu"), p.x, p.y+ 20);
	
	document.getElementById(id+"_menu").style.display = "";
	QrXPCOM.onPopup(document.getElementById(id+"_menu"));
}

QrColorPicker.setColor = function(event,id) {
	if(!QrColorPicker.restorePool[id]) QrColorPicker.restorePool[id] = document.getElementById(id+"_input").value;
	
	var d = QrXPCOM.getMousePoint(event,document.getElementById(id+"_menu"));
	var picked = QrColorPicker.colorpicker(d.x, d.y).toUpperCase();
	
	document.getElementById(id+"_input").value = picked;
	document.getElementById(id+"_text").innerHTML = picked;
	$("#" + id + "_color").css("background-color", picked);
	if(QrColorPicker.instanceMap[id].onChange) {
		QrColorPicker.instanceMap[id].onChange(picked);
	}
	return picked;
};


QrColorPicker.keyColor = function(id){
	try{
		document.getElementById(id+"_color").style.background = document.getElementById(id+"_input").value;
		QrColorPicker.restorePool[id] = document.getElementById(id+"_input").value;
		document.getElementById(id+"_text").innerHTML = QrColorPicker.restorePool[id];
	}catch(e){}
};


QrColorPicker.selectColor = function(event,id){
	var picked = QrColorPicker.setColor(event,id);
	
	document.getElementById(id+"_menu").style.display = "none";
	QrColorPicker.restorePool[id] = picked;
	if(QrColorPicker.instanceMap[id].onSelect){
		QrColorPicker.instanceMap[id].onSelect(picked);
	}
};

QrColorPicker.restoreColor = function(id){
	if(QrColorPicker.restorePool[id]){
		document.getElementById(id+"_input").value = QrColorPicker.restorePool[id];
		document.getElementById(id+"_text").innerHTML = QrColorPicker.restorePool[id];
		document.getElementById(id+"_color").style.background = QrColorPicker.restorePool[id];
		if(QrColorPicker.instanceMap[id].onChange){
			QrColorPicker.instanceMap[id].onChange(QrColorPicker.restorePool[id]);
		}
		QrColorPicker.restorePool[id] = null;
	}
};

QrColorPicker.colorpicker = function(prtX,prtY){
	var colorR = 0;
	var colorG = 0;
	var colorB = 0;
	
	if(prtX < 32){
		colorR = 256;
		colorG = prtX * 8;
		colorB = 1;
	}
	if(prtX >= 32 && prtX < 64){
		colorR = 256 - (prtX - 32 ) * 8;
		colorG = 256;
		colorB = 1;
	}
	if(prtX >= 64 && prtX < 96){
		colorR = 1;
		colorG = 256;
		colorB = (prtX - 64) * 8;
	}
	if(prtX >= 96 && prtX < 128){
		colorR = 1;
		colorG = 256 - (prtX - 96) * 8;
		colorB = 256;
	}
	if(prtX >= 128 && prtX < 160){
		colorR = (prtX - 128) * 8;
		colorG = 1;
		colorB = 256;
	}
	if(prtX >= 160){
		colorR = 256;
		colorG = 1;
		colorB = 256 - (prtX - 160) * 8;
	}
	
	if(prtY < 64){
		colorR = colorR + (256 - colorR) * (64 - prtY) / 64;
		colorG = colorG + (256 - colorG) * (64 - prtY) / 64;
		colorB = colorB + (256 - colorB) * (64 - prtY) / 64;
	}
	if(prtY > 64 && prtY <= 128){
		colorR = colorR - colorR * (prtY - 64) / 64;
		colorG = colorG - colorG * (prtY - 64) / 64;
		colorB = colorB - colorB * (prtY - 64) / 64;
	}
	if(prtY > 128){
		colorR = 256 - ( prtX / 192 * 256 );
		colorG = 256 - ( prtX / 192 * 256 );
		colorB = 256 - ( prtX / 192 * 256 );
	}
	
	colorR = parseInt(colorR);
	colorG = parseInt(colorG);
	colorB = parseInt(colorB);
	
	if(colorR >= 256){
		colorR = 255;
	}
	if(colorG >= 256){
		colorG = 255;
	}
	if(colorB >= 256){
		colorB = 255;
	}
	
	colorR = colorR.toString(16);
	colorG = colorG.toString(16);
	colorB = colorB.toString(16);
	
	if(colorR.length < 2){
	colorR = 0 + colorR;
	}
	if(colorG.length < 2){
	colorG = 0 + colorG;
	}
	if(colorB.length < 2){
	colorB = 0 + colorB;
	}
	
	return "#" + colorR + colorG + colorB;
}

QrColorPicker.prototype.appendTo = function (id) {
    $(this.getHTML()).appendTo("#"+id);
}