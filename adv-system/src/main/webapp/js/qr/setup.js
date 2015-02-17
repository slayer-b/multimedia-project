var cssClasses = new Array;//elements where styles are applyes i.e. class = id
var curStyles = {};//current values of styles per class

function connectCSS(obj, opts) {
	obj.onChange = function(value) {
		setTargetStyle(opts.style_name, value, opts.target, opts.cssClass);
	}
}
function connectCSS2(obj, objB, opts) {
	obj.onChange = function(value) {
        if (value!==''&&objB.get()!=='') {
            value = value + objB.get();
        } else {
            value = '';
        }
        setTargetStyle(opts.style_name, value, opts.target, opts.cssClass);
	}
	objB.onChange = function(value) {
        if (value!==''&&obj.get()!=='') {
            value = obj.get() + value;
        } else {
            value = '';
        }
		setTargetStyle(opts.style_name, value, opts.target, opts.cssClass);
	}
}
/*
 * from the given params sets value that are obtained by default
 */
function setDefaultStyle(opts) {
    if (cssStyles&&cssStyles[opts.cssClass]&&cssStyles[opts.cssClass][opts.style_name]) {
       opts.style_value = cssStyles[opts.cssClass][opts.style_name];
       $.each(opts.target, function(index, value) {
           setTargetStyle(opts.style_name, opts.style_value, value, opts.cssClass);
       });
    }
}
/*
 * from the given params sets value that are obtained by default.
 * sets opts.style_value = Number and opts.style_metric = metric(px, em, pt ...)
 */
function setDefaultStyleMetric(opts) {
    if (cssStyles&&cssStyles[opts.cssClass]&&cssStyles[opts.cssClass][opts.style_name]) {
       opts.style_value = cssStyles[opts.cssClass][opts.style_name].match(/\d*/)[0];
       opts.style_metric = cssStyles[opts.cssClass][opts.style_name].substr(opts.style_value.length);
       $.each(opts.target, function(index, value) {
           setTargetStyle(opts.style_name, cssStyles[opts.cssClass][opts.style_name], value, opts.cssClass);
       });
    }
}

function setTargetStyle(style, value, target, cssClass) {
    function handle(elementId) {
        try{
            curStyles[cssClass][style] = value;
            $(elementId).css(style, value);
        }catch(e){
            curStyles[cssClass][style] = "";
            $(elementId).css(style, "");
        }
    }

    if (!target) target = "#target";
    if (!cssClass) cssClass = ".unknown";
    if (!curStyles[cssClass]) curStyles[cssClass] = {};
    if (style=='background-image') {
        if (value==undefined||value==''||value.search(/url\(/)==0) {
        } else {
            value = 'url(' + value + ')';
        }
    }
    if (target instanceof Array) {
        cssClasses[cssClass] = target[0];
        for (i = 0; i < target.length; i++) {
            handle(target[i]);
        }
    } else {
        cssClasses[cssClass] = target;
        handle(target);
    }

    var cssStr = "";
    $.each(curStyles, function (i, val_i) {
        cssStr += i + "{";
        $.each(val_i, function (j, val_j) {
            if (val_j) cssStr += j + ":" + val_j + ";";
        });
        cssStr += "}\n";
    });
    //this produces different values in Internet explorer and mozilla + chrome
    /*for (style in cssClasses) {
        cssStr += style + "{";
        cssStr += $(cssClasses[style]).attr('style');
        cssStr += "}\n";
    }*/
    document.getElementById("output").value = cssStr;
}


function collapseSwitch(style) {
	var st;
	if(document.getElementById(style).style.visibility != "hidden") {
		for(st in connectInstanceOverrideMap[style]){
			document.getElementById(st).style.display = "";
		}
		document.getElementById(style+"#switch").src = "img/qr/arrowopen.gif";
		document.getElementById(style).style.visibility = "hidden";
	}else{
		for(st in connectInstanceOverrideMap[style]){
			document.getElementById(st).style.display = "none";
		}
		document.getElementById(style+"#switch").src = "img/qr/arrowclose.gif";
		document.getElementById(style).style.visibility = "";
	}
}

function createTaniComponent2(def) {
	var tan = new QrPulldown({defaultValue:def, defaultSize:3});
	tan.render();
	tan.addItem("px = pixels", "px");
	tan.addItem("pt = 1/72in", "pt");
	tan.addItem("em = font-size", "em");
	tan.addItem("ex = x-height of font", "ex");
	tan.addItem("pc = 12pt", "pc");
	tan.addItem("cm","cm");
	tan.addItem("mm", "mm");
	tan.addItem("in", "in");
	tan.addItem("%", "%");
	return tan;
}

function createTaniComponent(def, id) {
	if (!def) def = "px";
    if (!id){
        return createTaniComponent2(def);
    }
	var tan = new QrPulldown({defaultValue:def, defaultSize:3});
	tan.appendTo(id);
	tan.addItem("px = pixels", "px");
	tan.addItem("pt = 1/72in", "pt");
	tan.addItem("em = font-size", "em");
	tan.addItem("ex = x-height of font", "ex");
	tan.addItem("pc = 12pt", "pc");
	tan.addItem("cm","cm");
	tan.addItem("mm", "mm");
	tan.addItem("in", "in");
	tan.addItem("%", "%");
	return tan;
}

function innerset() {
	document.getElementById("target").innerHTML = document.getElementById("innerset").value;
}