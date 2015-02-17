createColorPickerForStyle({
    id:"QrCssLinkTextColor", style_name:"color", style_value:"#000000",
    target:["#link1>span", "#link2>span", "#link3>span"], cssClass:".adv_link>span"
});
createColorPickerForStyle({
    id:"QrCssLinkUrlColor", style_name:"color", style_value:"black",
    target:["#link1>a", "#link2>a", "#link3>a"], cssClass:".adv_link>a"
});
createColorPickerForStyle({
    id:"QrCssAddColor", style_name:"color", style_value:"black",
    target:["#link_add>a"], cssClass:".adv_add>a"
});
createColorPickerForStyle({
    id:"QrCssNavColor", style_name:"color", style_value:"black",
    target:["#navigation>a"], cssClass:".adv_nav>a"
});
createPulldownForStyle({
    id:"QrCssLinkUrlDecoration", style_name:"text-decoration",
    style_values:["none", "underline", "overline", "underline overline", "line-through", "blink"],
    target:["#link1>a", "#link2>a", "#link3>a"], cssClass:".adv_link>a"
});
//----------------------------------- Fonts -------------------------------------------
createPulldownForStyle({
    id:"QrCssPulldownFontFamily", style_name:"font-family",
    style_values:["Arial", "Times", "Verdana", "Tahoma"],
    target:["#target"], cssClass:".adv_block"});
createPulldownForStyle({
    id:"QrCssPulldownFontSize", style_name:"font-size",
    style_values:["9pt", "10pt", "12pt", "14pt", "16pt", "xx-small", "x-small",
                  "small", "large", "x-large"],
    target:["#target"], cssClass:".adv_block"});
createPulldownForStyle({
    id:"QrCssPulldownFontWeight", style_name:"font-weight",
    style_values:["normal", "bold", "600", "900"],
    target:["#target"], cssClass:".adv_block"});

//----------------------------------- Border -------------------------------------------
createSpinnerForStyle({
    id:"QrBorderWidth", style_name:"border-width", style_metric:"px",
    target:["#target"], cssClass:".adv_block"
});
createColorPickerForStyle({
    id:"border-color", style_name:"border-color", style_value:"transparent",
    target:["#target"], cssClass:".adv_block"});
initQrPulldownBorderStyle();

createSpinnerForStyle({
    id:"QrPadding", style_name:"padding", style_metric:"px",
    target:["#target"], cssClass:".adv_block"
});
createSpinnerForStyle({
    id:"QrMargin", style_name:"margin", style_metric:"px",
    target:["#target"], cssClass:".adv_block"
});

//----------------------------------- Background -----------------------------------
createColorPickerForStyle({
    id:"QrCssDesignerColorBackground", style_name:"background-color", style_value:"transparent",
    target:["#target"], cssClass:".adv_block"
});
createInputForStyle({
    id:"#QrCssPulldownBackgroundImage", elemId:"QrBackgroundImage", style_name:"background-image",
    target:["#target"], cssClass:".adv_block"
});
createPulldownForStyle({id:"QrCssPulldownBackgroundRepeat", style_name:"background-repeat",
                        style_values:["repeat", "repeat-x", "repeat-y", "no-repeat"], skip_style:true,
                        target:["#target"], cssClass:".adv_block"});
createPulldownForStyle({id:"QrCssPulldownBackgroundPosition", style_name:"background-position",
                        style_values:["left", "right", "top", "left top", "right bottom", "30% 50%"], skip_style:true,
                        target:["#target"], cssClass:".adv_block"});
