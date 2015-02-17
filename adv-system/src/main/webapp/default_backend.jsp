<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <link href="${pageContext.servletContext.contextPath}/styles/xeonline.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.servletContext.contextPath}/styles/xeonline_menu1.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.servletContext.contextPath}/styles/xeonline_menu2.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.servletContext.contextPath}/styles/xeonline_auth.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.servletContext.contextPath}/styles/xeonline_texts.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.servletContext.contextPath}/styles/xeonline_btm.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.servletContext.contextPath}/styles/xeonline_hello_text.css" rel="stylesheet" type="text/css" />
</head>
<body>
    <table width="100%">
        <tr>
            <td align="center">
                <table width="1024px">
                    <tr>
                        <td id="td_header">
                            <table id="menu1">
                                <tr>
                                    <td class="menu1_left">
                                    </td>
                                    <td class="menu1_item">
                                        <a href="#">Статистика</a>
                                    </td>
                                    <td class="menu1_separator">
                                    </td>
                                    <td class="menu1_item">
                                        <a href="#">Сообщения</a>
                                    </td>
                                    <td class="menu1_separator">
                                    </td>
                                    <td class="menu1_item">
                                        <a href="#">FAQ</a>
                                    </td>
                                    <td class="menu1_separator">
                                    </td>
                                    <td class="menu1_item">
                                        <a href="#">Контакты</a>
                                    </td>
                                    <td class="menu1_right">
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                    </td>
                                    <td>
                                        <img alt="Selector" id="menu1_selected" src="${pageContext.servletContext.contextPath}/imgs/marker.png" />
                                    </td>
                                    <td>
                                    </td>
                                    <td>
                                    </td>
                                    <td>
                                    </td>
                                    <td>
                                    </td>
                                    <td>
                                    </td>
                                    <td>
                                    </td>
                                    <td>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                    </td>
                                    <td colspan="8" align="left">
                                        <div id="menu1_submenu">
                                            <a href="#">Вебмастерам</a>
                                                <span class="menu1_sub_separator"></span>
                                            <a href="#">Рекламодателям</a>
                                                <span class="menu1_sub_separator"></span>
                                            <a href="#">Партнерам</a>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                            <table id="menu2b">
                                <tr>
                                    <td class="menu2_left">
                                    </td>
                                    <td class="menu2_item">
                                        <a href="#">Вебмастерам</a>
                                    </td>
                                    <td class="menu2_separator">
                                    </td>
                                    <td class="menu2_item">
                                        <a href="#">Рекламодателям</a>
                                    </td>
                                    <td class="menu2_separator">
                                    </td>
                                    <td class="menu2_item">
                                        <a href="#">Партнёрам</a>
                                    </td>
                                    <td class="menu2_right">
                                    </td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td>
                                        <img src="${pageContext.servletContext.contextPath}/imgs/marker_b.png" /></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td colspan="6" align="left">
                                    <div id="menu2_submenu">
                                            <a href="#">Мои сайты</a>
                                                <span class="menu2_sub_separator"></span>
                                            <a href="#">Мои блоки</a>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                            <form  method="post" action="auth.html">
                            <table id="authb">
                                <tr>
                                    <td class="auth_left">
                                    </td>
                                    <td class="authb_item">
                                        <a href="registration.html">Вывод средств</a>
                                    </td>
                                    <td class="authb_item">
                                        <a href="pass-renew.html">Личные данные</a>
                                    </td>
                                    <td class="authb_item">
                                        <a href="pass-renew.html">Пополнить счет</a>
                                    </td>
                                    <td class="authb_item">
                                        <input type="image" name="btn_login" id="btn_login" src="${pageContext.servletContext.contextPath}/imgs/unauth-login-btn.png" />
                                    </td>
                                    <td class="auth_right">
                                    </td>
                                </tr>
                                
                            </table>
                            </form>
                            <table id="hello_text">
                                <tr>
                                    <td>Здравствуйте, <span id="username">Angelnet</span>
                                    </td>
                                </tr>
                                <tr>
                                    <td id="balance">
                                    Ваш баланс: 12,34$
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td id="td_body">
                            <table width="100%">
                                <tr>
                                    <td align="center">
                                    <div style="text-align:left; width: 300px;">
                                        <table width="100%">
                                            <tr>
                                                <td colspan="2" style="font-size: 14pt; padding-bottom: 10px; color: Grey;">Пополнить счёт
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="font-size: 12px;padding-bottom: 10px">Способ 
                                                </td>
                                                <td style="font-size: 12px;padding-bottom: 10px"><select style="font-size: 12px; width: 107px;">
                                            <option value="1">Способ 1</option>
                                            <option value="2">Способ 2</option>
                                            <option value="3">Способ 3</option>
                                            <option value="4">Способ 4</option>
                                        </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="font-size: 12px;padding-bottom: 10px">Сумма 
                                                </td>
                                                <td style="font-size: 12px;padding-bottom: 10px"><input type="text" style="font-size: 12px; width: 100px;" />
                                                </td>
                                            </tr>
                                            <tr>
                                                <td colspan="2" style="padding-bottom: 10px">
                                                <a href="" style="color: Black">
                                                Выполнить платёж
                                                </a>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                    </td>
                                </tr>
                                <tr>
                                <td style="height: 404px"></td>
                                </tr>
                            </table>

                        </td>
                    </tr>
                    <tr>
                        <td id="td_btm_back">
                        <div class="btm_links">
                        © 2011 XeoLine
                        <span style="width: 40px"></span>
                        Дизайн сайта:<a href="http://ipr-design.com">IPR design</a>
                        </div>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</body>
</html>
