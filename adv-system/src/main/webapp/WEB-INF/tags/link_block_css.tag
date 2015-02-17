<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@taglib prefix="utils" uri="http://download-multimedia.com/tags/utils"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="cssParam" required="false" type="java.lang.String" description="Name of http param that contains css style"%>
<%@attribute name="cssValue" required="false" type="java.lang.String" description="Default value" %>

<style type="text/css" media="all">@import "${pageContext.servletContext.contextPath}/styles/qr/style.css";</style>
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/styles/qr/qreditor.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/styles/qr/qrtable.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/styles/qr/qrpulldown.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/styles/qr/qrcpicker.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/styles/qr/qrspinner.css"/>

<style type="text/css"> <!--

    .QrCSSDesignerPad{
        margin-top:40px;
    }

    .QrCSSDesignerPadLeft{
        width:150px;
    }

    --></style>

<script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/jquery-1.6.min.js"></script>

<script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/qr/qrxpcom.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/qr/qrcpicker.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/qr/qrcpulldown.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/qr/qrcspinner.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/qr/setup.js"></script>

<script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/qr/ddaccordion.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/qr/accordion.js"></script>

<script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/qr/init_helper.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/qr/init.js"></script>

<script type="text/javascript">
var cssStyles = parseParam('<utils:replaceInString string="${empty param[cssParam]?cssValue:param[cssParam]}" target="\r\n" replacement="\\\n"/>');
</script>

<div id="container">
    <table><tr><td>
                <table class="QrCSSDesignerPad">
                    <tr>
                        <td class="QrCSSDesignerPadLeft"><p>link text color : </p></td>
                        <td><div id="QrCssLinkTextColor"></div></td>
                    </tr>
                    <tr>
                        <td class="QrCSSDesignerPadLeft"><p>link url color : </p></td>
                        <td><div id="QrCssLinkUrlColor"></div></td>
                    </tr>
                    <tr>
                        <td class="QrCSSDesignerPadLeft"><p>add color : </p></td>
                        <td><div id="QrCssAddColor"></div></td>
                    </tr>
                    <tr>
                        <td class="QrCSSDesignerPadLeft"><p>nav color : </p></td>
                        <td><div id="QrCssNavColor"></div></td>
                    </tr>
                    <tr>
                        <td class="QrCSSDesignerPadLeft"><p>link url decoration : </p></td>
                        <td><div id="QrCssLinkUrlDecoration"></div></td>
                    </tr>

                    <tr><td colspan="2" align="center"><hr/></td></tr>

                    <tr>
                        <td class="QrCSSDesignerPadLeft"><p>font-family : </p></td>
                        <td><div id="QrCssPulldownFontFamily"></div></td>
                    </tr>
                    <tr>
                        <td class="QrCSSDesignerPadLeft"><p>font-size : </p></td>
                        <td><div id="QrCssPulldownFontSize"></div></td>
                    </tr>
                    <tr>
                        <td class="QrCSSDesignerPadLeft"><p>font-weight : </p></td>
                        <td><div id="QrCssPulldownFontWeight"></div></td>
                    </tr>

                    <tr><td colspan="2" align="center"><hr/></td></tr>

                    <tr>
                        <td class="QrCSSDesignerPadLeft"><p>border-width : </p></td>
                        <td><div id="QrBorderWidth"></div></td>
                    </tr>

                    <tr>
                        <td class="QrCSSDesignerPadLeft"><p>border-color : </p></td>
                        <td><span id="border-color"></span></td>
                    </tr>

                    <tr>
                        <td class="QrCSSDesignerPadLeft"><p>border-style : </p></td>
                        <td><span id="border-style"></span></td>
                    </tr>

                    <tr><td colspan="2" align="center"><hr/></td></tr>

                    <tr>
                        <td class="QrCSSDesignerPadLeft"><p>padding : </p></td>
                        <td><span id="QrPadding"></span></td>
                    </tr>

                    <tr><td colspan="2" align="center"><hr/></td></tr>

                    <tr>
                        <td class="QrCSSDesignerPadLeft"><p>margin : </p></td>
                        <td><span id="QrMargin"></span></td>
                    </tr>

                    <tr><td colspan="2" align="center"><hr/></td></tr>

                    <tr>
                        <td class="QrCSSDesignerPadLeft"><p>background-color : </p></td>
                        <td><div id="QrCssDesignerColorBackground"></div></td>
                    </tr>
                    <tr>
                        <td class="QrCSSDesignerPadLeft"><p>background-image : </p></td>
                        <td><div id="QrCssPulldownBackgroundImage"></div></td>
                    </tr>
                    <tr>
                        <td class="QrCSSDesignerPadLeft"><p>background-repeat : </p></td>
                        <td><div id="QrCssPulldownBackgroundRepeat"></div></td>
                    </tr>
                    <tr>
                        <td class="QrCSSDesignerPadLeft"><p>background-position : </p></td>
                        <td><div id="QrCssPulldownBackgroundPosition"></div></td>
                    </tr>
                </table>
            </td><td>
                <div id="tool_box_area">
                    <div>
                        <div>
                            <div id="target">
                                <div id="link1"><span>link1:</span><a href="#">text1</a></div>
                                <div id="link2"><span>link2:</span><a href="#">text2</a></div>
                                <div id="link3"><span>link3:</span><a href="#">text3</a></div>
                                <div id="link_add"><a target="_blank" href="#">добавить</a></div>
                                <div id="navigation">
                                    <a href="#">&lt;</a>
                                    <a href="#">&gt;</a>
                                </div>
                            </div>
                        </div>

                        <textarea name="${cssParam}" id="output" rows="7">${empty param[cssParam]?cssValue:param[cssParam]}</textarea>
                    </div>
                </div>
            </td>
        </tr>
    </table>
</div>

<script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/qr/link_block_init.js"></script>
