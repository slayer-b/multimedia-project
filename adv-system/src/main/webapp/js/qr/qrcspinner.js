function QrSpinner(_defaultValue, _defaultSize, _Name){
	if(!_defaultValue) _defaultValue = "";
	if(!_defaultSize)  _defaultSize  = "4";
	
	this.id = QrSpinner.lastId++;
	this.defaultValue = _defaultValue;
	this.defaultSize  = _defaultSize;
	this.name = _Name;
	
	QrSpinner.instanceMap["QrSpinner"+this.id] = this;
}

QrSpinner.prototype.getHTML = function(){
	var html =  "<div class=\"QrSpinner\"><input id=\"$spinnerId#input\" class=\"QrSpinnerInput\" style=\"$IEPoint\" size=\"$defaultSize\" value=\"$defaultValue\" onkeyup=\"QrSpinner.onKeyup('$spinnerId')\" $NamePoint/><div id=\"$spinnerId_button\" class=\"QrSpinnerImg QrSpinnerImgNormal\" onmousemove=\"QrSpinner.onHover(event,'$spinnerId')\" onmouseout=\"QrSpinner.onOut(event,'$spinnerId')\" onmousedown=\"QrSpinner.onDown(event,'$spinnerId')\"></div></div>";
	if(QrXPCOM.isIE()) html=html.replace(/\$IEPoint/,"margin-top:-1px;");
	else html=html.replace(/\$IEPoint/,"");
	return html.replace(/\$spinnerId/g,"QrSpinner"+this.id)
			   .replace(/\$defaultSize/g,this.defaultSize)
			   .replace(/\$defaultValue/g,this.defaultValue)
			   .replace(/\$NamePoint/g,this.name);
}

QrSpinner.prototype.render = function(){
	document.write(this.getHTML());
}

QrSpinner.prototype.set = function(value){
	document.getElementById("QrSpinner"+this.id+"#input").value = value;
	if(QrSpinner.instanceMap["QrSpinner"+this.id].onChange){
		QrSpinner.instanceMap["QrSpinner"+this.id].onChange(value);
	}
}

QrSpinner.prototype.get = function(){
	return document.getElementById("QrSpinner"+this.id+"#input").value;
}

QrSpinner.lastId = 0;
QrSpinner.instanceMap = new Array;

QrSpinner.onHover = function(e, id){
	var p = QrXPCOM.getMousePoint(e);
	var d = QrXPCOM.getDivPoint(document.getElementById(id+"_button"));
    //alert(d.y +":" + p.y);
	if((p.y - d.y)<10) {
		$("#"+id+"_button").removeClass("QrSpinnerImgDown");
		$("#"+id+"_button").addClass("QrSpinnerImgUp");
	} //else {
	if((p.y - d.y)>=10) {
		$("#"+id+"_button").removeClass("QrSpinnerImgUp");
		$("#"+id+"_button").addClass("QrSpinnerImgDown");
	}
    $("#"+id+"_button").removeClass("QrSpinnerImgNormal");
}

QrSpinner.onOut = function(e, id){
	$("#"+id+"_button").removeClass("QrSpinnerImgUp");
	$("#"+id+"_button").removeClass("QrSpinnerImgDown");
	$("#"+id+"_button").addClass("QrSpinnerImgNormal");
}


QrSpinner.onKeyup = function(id){
	if(QrSpinner.instanceMap[id].onChange){
		QrSpinner.instanceMap[id].onChange(document.getElementById(id+"#input").value);
	}
}

QrSpinner.onDown = function(e, id){
	var p = QrXPCOM.getMousePoint(e);
	var d = QrXPCOM.getDivPoint(document.getElementById(id+"_button"));
	
	var v = parseInt(document.getElementById(id+"#input").value);
	if(!v) v = 0;
	if((p.y - d.y)<10){
		document.getElementById(id+"#input").value = ++v;
	}
	if((p.y - d.y)>10){
		document.getElementById(id+"#input").value = --v;
	}
	if(QrSpinner.instanceMap[id].onChange){
		QrSpinner.instanceMap[id].onChange(v);
	}
}

QrSpinner.prototype.appendTo = function (id) {
    $(this.getHTML()).appendTo("#"+id);
}