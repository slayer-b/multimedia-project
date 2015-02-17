/* 
 * Helper functions for initialization.
 */
/*
 * this function creates a pulldown element inside an element with given id.
 * @param opts consists of {...},
 *        where
 *        target is the name of elements where to apply style;
 *        cssClass is the name of class to generate;
 *        id of an element where to insert this pulldown box;
 *        style_name name of the style
 *        style_values values for this style
 *        skip_style if true do not apply style to output style values in pulldown box
 *        
 */
function createPulldownForStyle(opts) {
    function addStyle(style_value) {
        if (!opts.skip_style) {
            pl.addItem("<div style='"+opts.style_name+":"+style_value+"'>"+style_value+"</div>", style_value);
        } else {
            pl.addItem(style_value, style_value);
        }
    }

    setDefaultStyle(opts);
    var pl = new QrPulldown({defaultValue: opts.style_value});
    pl.appendTo(opts.id);
    for (i=0;i<opts.style_values.length;i++) {
        addStyle(opts.style_values[i]);
    }
    connectCSS(pl, opts);
    return pl;
}
/*
 * This function creates a spinner with metric selection
 * @param opts this param is a map of following:
 *  id of element where to insert this one
 *  style_name name of a style for this element
 *  style_value starting value
 *  style_metric starting metric
 *  target is the name of elements where to apply style;
 *  cssClass is the name of class to generate;
 */
function createSpinnerForStyle(opts) {
    setDefaultStyleMetric(opts);
    pl = new QrSpinner(opts.style_value);
    pl.appendTo(opts.id);
    tan = createTaniComponent(opts.style_metric, opts.id);
    connectCSS2(pl, tan, opts);
}

/*
 * this function creates a color picker element inside an element with given id.
 * @param opts consists of {...},
 *        where
 *        target is the name of elements where to apply style;
 *        cssClass is the name of class to generate;
 *        id of an element where to insert this pulldown box;
 *        style_name name of the style
 *        style_value default
 */
function createColorPickerForStyle(opts) {
    setDefaultStyle(opts);
    cp = new QrColorPicker(opts.style_value);
    cp.appendTo(opts.id);
    connectCSS(cp, opts);
}

function createInputForStyle(opts) {
    setDefaultStyle(opts);
    if (!opts.style_value) opts.style_value = "";
    if (opts.style_name=="background-image"&&opts.style_value.length>5) {
        opts.style_value = opts.style_value.substring(4, opts.style_value.length-1);
    }
    $(opts.id)
    .append("<input class=\"QrPulldownInput\" value=\""+opts.style_value+"\" size=\"16\" id=\"" + opts.elemId + 
        "\" onchange=\"setTargetStyle('"+opts.style_name+"', this.value, '"+opts.target+"', '"+opts.cssClass+"');\"/>")
}
var classStart = /[^{]*/gi;
var classEnd = /[^}]*/;
/*
 * this function parses param into an array [className][styleName]=styleValue 
 */
function parseParam(param, param2s) {
    var rez = {};
    if (param.length>0) {
        $.each(param.split("}"), function(i, classCur) {
            classCur = $.trim(classCur);
            if (classCur.length>0) {
                var classStr = classCur.split("{", 2);
                rez[classStr[0]] = {};
                $.each(classStr[1].split(";"), function(j, styleStr) {
                    if (styleStr) {
                        styleNameVal = styleStr.split(":", 2);
                        rez[classStr[0]][$.trim(styleNameVal[0])] = $.trim(styleNameVal[1]);
                    }
                });
            }
        });
    }

    return rez;
    /*$.each(rez, function(i, val_i) {
        $.each(val_i, function(j, val_j) {
            alert(i + "-->" + j + "-->" + val_j);
        });
    });*/
}