<%@tag description="put the tag description here" pageEncoding="UTF-8"%>

<%-- Original style editor --%>
<%-- The list of normal or fragment attributes can be specified here: --%>
<%--@attribute name="message" --%>

<style type="text/css" media="all">@import "styles/qr/style.css";</style>
<link rel="stylesheet" type="text/css" href="styles/qr/qreditor.css"/>
<link rel="stylesheet" type="text/css" href="styles/qr/qrtable.css"/>
<link rel="stylesheet" type="text/css" href="styles/qr/qrpulldown.css"/>

<style type="text/css"> <!--

    .QrCSSDesignerPad{
        margin-top:40px;
    }

    .QrCSSDesignerPadLeft{
        width:150px;
    }

    --></style>

<script type="text/javascript" src="js/qr/qrxpcom.js"></script>
<script type="text/javascript" src="js/qr/qrcpicker.js"></script>
<script type="text/javascript" src="js/qr/qrcpulldown.js"></script>
<script type="text/javascript" src="js/qr/qrcspinner.js"></script>
<script type="text/javascript" src="js/qr/setup.js"></script>

<script type="text/javascript" src="js/jquery-1.6.min.js"></script>
<script type="text/javascript" src="js/qr/ddaccordion.js"></script>
<script type="text/javascript" src="js/qr/accordion.js"></script>

<script type="text/javascript" src="js/qr/init_helper.js"></script>
<script type="text/javascript" src="js/qr/init.js"></script>

<div id="container">
    <div>
        <div id="results">
            <p> Чтобы очистить нажмите &#39;Start Again&#39; </p>
            <div id="tool_box_area">
                <div>
                    <div>
                        <div id="target">
                            <div>
                                <div>link1:<a href="#">text1</a></div>
                                <div>link2:<a href="#">text2</a></div>
                                <div>link3:<a href="#">text3</a></div>
                                <div><a target="_blank" href="#">добавить</a></div>
                            </div>
                        </div>
                    </div>

                    <textarea id="output" rows="7"></textarea>
                    <p>Click <img alt="" src="img/qr/arrowclose.gif" width="16" height="16"/> to view more options.</p><br/><br/>

                    <input type="reset" value="Start Again" onclick="history.go()"/>
                </div>
            </div>

            <table class="QrCSSDesignerPad">

                <tr>
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>color : </p></td>
                    <td><div id="QrCssDesignerColor"></div></td>
                </tr>

                <tr>
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>background-color : </p></td>
                    <td><div id="QrCssDesignerColorBackground"></div></td>
                </tr>

                <tr><td colspan="3" align="center"><hr/></td></tr>

                <tr>
                    <td><img id="border-width#switch" src="img/qr/arrowclose.gif" onclick="collapseSwitch('border-width');" width="16" height="16"/></td>
                    <td class="QrCSSDesignerPadLeft"><p>border-width : </p></td>
                    <td><span id="border-width"></span></td>
                </tr>
                <tr id="border-top-width" style="display:none;">
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>border-top-width : </p></td>
                    <td><span id="QrCssSpinnerBorderTop"></span></td>
                </tr>
                <tr id="border-left-width" style="display:none;">
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>border-left-width : </p></td>
                    <td><span id="QrCssSpinnerBorderLeft"></span></td>
                </tr>
                <tr id="border-right-width" style="display:none;">
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>border-right-width : </p></td>
                    <td><span id="QrCssSpinnerBorderRight"></span></td>
                </tr>
                <tr id="border-bottom-width" style="display:none;">
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>border-bottom-width : </p></td>
                    <td><span id="QrCssSpinnerBorderBottom"></span></td>
                </tr>

                <tr>
                    <td><img id="border-color#switch" src="img/qr/arrowclose.gif" onclick="collapseSwitch('border-color');" width="16" height="16"/></td>
                    <td class="QrCSSDesignerPadLeft"><p>border-color : </p></td>
                    <td><span id="border-color"></span></td>
                </tr>
                <tr id="border-top-color" style="display:none;">
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>border-top-color : </p></td>
                    <td><div id="QrCssColorPickerBorderTop"></div></td>
                </tr>
                <tr id="border-left-color" style="display:none;">
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>border-left-color : </p></td>
                    <td><div id="QrCssColorPickerBorderLeft"></div></td>
                </tr>
                <tr id="border-right-color" style="display:none;">
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>border-right-color : </p></td>
                    <td><div id="QrCssColorPickerBorderRight"></div></td>
                </tr>
                <tr id="border-bottom-color" style="display:none;">
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>border-bottom-color : </p></td>
                    <td><div id="QrCssColorPickerBorderBottom"></div></td>
                </tr>

                <tr>
                    <td><img id="border-style#switch" src="img/qr/arrowclose.gif" onclick="collapseSwitch('border-style');" width="16" height="16"/></td>
                    <td class="QrCSSDesignerPadLeft"><p>border-style : </p></td>
                    <td><span id="border-style"></span></td>
                </tr>
                <tr id="border-top-style" style="display:none;">
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>border-top-style : </p></td>
                    <td><div id="QrCssPulldownBorderStyleTop"></div></td>
                </tr>
                <tr id="border-left-style" style="display:none;">
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>border-left-style : </p></td>
                    <td><div id="QrCssPulldownBorderStyleLeft"></div></td>
                </tr>
                <tr id="border-right-style" style="display:none;">
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>border-right-style : </p></td>
                    <td><div id="QrCssPulldownBorderStyleRight"></div></td>
                </tr>
                <tr id="border-bottom-style" style="display:none;">
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>border-bottom-style : </p></td>
                    <td><div id="QrCssPulldownBorderStyleBottom"></div></td>
                </tr>

                <tr><td colspan="3" align="center"><hr/></td></tr>

                <tr>
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>width : </p></td>
                    <td><div id="QrCssSpinnerWidth"></div></td>
                </tr>

                <tr>
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>height : </p></td>
                    <td><div id="QrCssSpinnerHeight"></div></td>
                </tr>

                <tr>
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>overflow : </p></td>
                    <td><div id="QrCssPulldownOverflow"></div></td>
                </tr>

                <tr><td colspan="3" align="center"><hr/></td></tr>

                <tr>
                    <td><img id="padding#switch" src="img/qr/arrowclose.gif" onclick="collapseSwitch('padding');" width="16" height="16"/></td>
                    <td class="QrCSSDesignerPadLeft"><p>padding : </p></td>
                    <td><span id="padding"></span></td>
                </tr>
                <tr id="padding-top" style="display:none">
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>padding-top : </p></td>
                    <td><div id="QrCssSpinnerPaddingTop"></div></td>
                </tr>
                <tr id="padding-left" style="display:none">
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>padding-left : </p></td>
                    <td><div id="QrCssSpinnerPaddingLeft"></div></td>
                </tr>
                <tr id="padding-right" style="display:none">
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>padding-right : </p></td>
                    <td><div id="QrCssSpinnerPaddingRight"></div></td>
                </tr>
                <tr id="padding-bottom" style="display:none">
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>padding-bottom : </p></td>
                    <td><div id="QrCssSpinnerPaddingBottom"></div></td>
                </tr>

                <tr><td colspan="3" align="center"><hr/></td></tr>

                <tr>
                    <td><img id="margin#switch" src="img/qr/arrowclose.gif" onclick="collapseSwitch('margin');" width="16" height="16"/></td>
                    <td class="QrCSSDesignerPadLeft"><p>margin : </p></td>
                    <td><span id="margin"></span></td>
                </tr>
                <tr id="margin-top" style="display:none">
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>margin-top :</p></td>
                    <td><div id="QrCssSpinnerMarginTop"></div></td>
                </tr>
                <tr id="margin-left" style="display:none">
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>margin-left : </p></td>
                    <td><div id="QrCssSpinnerMarginLeft"></div></td>
                </tr>
                <tr id="margin-right" style="display:none">
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>margin-right :</p></td>
                    <td><div id="QrCssSpinnerMarginRight"></div></td>
                </tr>
                <tr id="margin-bottom" style="display:none">
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>margin-bottom :</p></td>
                    <td><div id="QrCssSpinnerMarginBottom"></div></td>
                </tr>

                <tr><td colspan="3" align="center"><hr/></td></tr>

                <tr>
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>font-family : </p></td>
                    <td><div id="QrCssPulldownFontFamily"></div></td>
                </tr>
                <tr>
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>font-size : </p></td>
                    <td><div id="QrCssPulldownFontSize"></div></td>
                </tr>
                <tr>
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>font-weight : </p></td>
                    <td><div id="QrCssPulldownFontWeight"></div></td>
                </tr>
                <tr>
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>font-style : </p></td>
                    <td><div id="QrCssPulldownFontStyle"></div></td>
                </tr>
                <tr>
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>font-variant : </p></td>
                    <td><div id="QrCssPulldownFontVariant"></div></td>
                </tr>
                <tr>
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>line-height : </p></td>
                    <td><div id="QrCssSpinnerLineHeight"></div></td>
                </tr>

                <tr><td colspan="3" align="center"><hr/></td></tr>

                <tr>
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>clear : </p></td>
                    <td><div id="QrCssPulldownClear"></div></td>
                </tr>

                <tr>
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>text-align : </p></td>
                    <td><div id="QrCssPulldownTextAlign"></div></td>
                </tr>

                <tr>
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>text-decoration : </p></td>
                    <td><div id="QrCssPulldownTextDecoration"></div></td>
                </tr>

                <tr>
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>text-indent : </p></td>
                    <td><div id="QrCssSpinnerTextIndent"></div></td>
                </tr>

                <tr>
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>letter-spacing : </p></td>
                    <td><div id="QrCssSpinnerLetterSpacing"></div></td>
                </tr>

                <tr>
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>word-spacing : </p></td>
                    <td><div id="QrCssSpinnerWordSpacing"></div></td>
                </tr>

                <tr>
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>text-transform : </p></td>
                    <td><div id="QrCssPulldownTextTransform"></div></td>
                </tr>

                <tr><td colspan="3" align="center"><hr/></td></tr>

                <tr>
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>background-image : </p></td>
                    <td><div id="QrCssPulldownBackgroundImage"></div></td>
                </tr>
                <tr>
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>background-repeat : </p></td>
                    <td><div id="QrCssPulldownBackgroundRepeat"></div></td>
                </tr>
                <tr>
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>background-position : </p></td>
                    <td><div id="QrCssPulldownBackgroundPosition"></div></td>
                </tr>
                <tr>
                    <td></td>
                    <td class="QrCSSDesignerPadLeft"><p>background-attachment : </p></td>
                    <td><div id="QrCssPulldownBackgroundAttachment"></div></td>
                </tr>
            </table>

        </div>
    </div>
</div>

<script type="text/javascript" src="js/qr/link_block_init.js"></script>