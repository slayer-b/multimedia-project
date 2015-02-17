/* 
 * This javascript initializes visual components such as colorpicker and pulldown boxes etc...
 */
function initQrPulldownBorderStyle() {
    function addBorderStyles(elem) {
        function addStyle(style) {
            elem.addItem("<div style='width:10px;height:10px;border-width:3px;border-style:"+style+";border-color:red;margin-right:5px;' align='middle'/> "+style, style);
        }
        elem.addItem("<div style='width:16px;height:16px;border-width:3px;border-style:none;border-color:red;margin-right:5px;' align='middle'/> none","none");
        addStyle("solid");
        addStyle("double");
        addStyle("groove");
        addStyle("ridge");
        addStyle("inset");
        addStyle("outset");
        addStyle("dashed");
        addStyle("dotted");
    }

    opts = {cssClass:".adv_block", target:["#target"], style_name:"border-style", style_value:""};
    setDefaultStyle(opts);
    pl = new QrPulldown({menuclass:"QrPulldownMenuEmpty", defaultValue:opts.style_value});
    pl.appendTo("border-style");
    addBorderStyles(pl);
    connectCSS(pl, opts);
}