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
</head>
<body>
    <table width="100%" border="0">
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
                                        <a href="#">О проекте</a>
                                    </td>
                                    <td class="menu1_separator">
                                    </td>
                                    <td class="menu1_item">
                                        <a href="#">Сервисы</a>
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
                            <table id="menu2">
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
                            </table>
                            <form  method="post" action="auth.html">
                            <table id="auth" style="height: 50px">
                                <tr style="height: 50px">
                                    <td class="auth_left">
                                    </td>
                                    <td class="auth_item">
                                        <input id="txt_username" type="text" value="логин" /><br />
                                        <a href="registration.html">Зарегистрироваться</a>
                                    </td>
                                    <td class="auth_item">
                                        <input id="txt_pass" type="text" value="пароль" /><br />
                                        <a href="pass-renew.html">Забыли пароль?</a>
                                    </td>
                                    <td class="auth_item">
                                        <input type="image" name="btn_login" id="btn_login" src="${pageContext.servletContext.contextPath}/imgs/auth-login-btn.png" />
                                    </td>
                                    <td class="auth_right">
                                    </td>
                                </tr>
                                
                            </table>
                            </form>
                        </td>
                    </tr>
                    <tr>
                        <td id="td_body">
                            <table border="0">
                                <tr>
                                    <td colspan="4" style="height: 20px"></td>
                                </tr>
                                <tr>
                                    <td>
                                    <table width="100%" border="0">
                                    <tr>
                                    <td class="empty-left"></td>
                                    <td id="text1">
                                    <p>Система Nolix предлагает веб-мастерам (владельцам сайтов)
                                    автоматизировать на своих сайтах работу с прямыми рекламодателями и организовать
                                    удобный прием средств за рекламные строчки или контекстные объявления.</p>
                                    <p>Преимущества работы с Nolix:</p>

                                        <ul type="square">
                                    <li>
                                        
           
           Вы получаете 90.5% от стоимости рекламного размещения. 
           </li>
           <li>
           Ваш сайт все время получает дополнительных общесистемных рекламодателей. 
           </li>
           <li>
           Минимальная сумма к выплате всего 5$, частота выплат неограниченна. 
           </li>
           <li>
           Отзывчивая служба поддержки ответит на все ваши вопросы. 
           </li>
           <li>
           От вас не требуется наличие персонального аттестата Webmoney и каких-либо дополнительных знаний языков программирования.
           </li>
           </ul>
           <div style="font-size: 16pt; text-align: center; width: 100%;">
           <a style="color: Red;" href="registration.html">Зарегистрироваться</a>
           </div>
                                    
                                    </td>
                                    </tr>
                                    <tr>
                                    <td colspan="2" style="height:10px"></td>
                                    </tr>
                                    <tr>
                                    <td colspan="2" id="mdl-separator-1"></td>
                                    </tr>
                                    <tr>
                                    <td class="empty-left"></td>
                                    <td id="text2">
                                    <div id="services">
                                    Cервисы
                                    </div>
                                        <table border="0">
                                            <tr>
                                                <td class="txt_block2 pic">
                                                    <img src="${pageContext.servletContext.contextPath}/imgs/content-pic-1.png" />
                                                </td>
                                                <td class="txt_block2 text">
                                                <div class="text_header">
                                                Время-деньги
                                                </div>
                                                XeoLine — это простой, удобный, а главное эффективный инструмент в руках любого рекламодателя. XeoLine — это простой, удобный, а главное эффективный инструмент в руках любого рекламодателя.XeoLine — это простой, удобный, а главное эффективный инструмент в руках любого рекламодателя.XeoLine — это простой, удобный, а главное эффективный инструмент в руках любого рекламодателя.
                                                </td>
                                            </tr>
                                            <tr>
                                                <td colspan="2" class="block2-text-separator"></td>
                                            </tr>
                                            <tr>
                                                <td class="txt_block2 pic">
                                                    <img src="${pageContext.servletContext.contextPath}/imgs/content-pic-2.png" />
                                                </td>
                                                <td class="txt_block2 text">
                                                <div class="text_header">
                                                Эффективные рекламные кампании
                                                </div>
                                                XeoLine — это простой, удобный, а главное эффективный инструмент в руках любого рекламодателя. XeoLine — это простой, удобный, а главное эффективный инструмент в руках любого рекламодателя.XeoLine — это простой, удобный, а главное эффективный инструмент в руках любого рекламодателя.XeoLine — это простой, удобный, а главное эффективный инструмент в руках любого рекламодателя.
                                                </td>
                                            </tr>
                                            <tr>
                                                <td colspan="2" class="block2-text-separator"></td>
                                            </tr>
                                            <tr>
                                                <td class="txt_block2 pic">
                                                    <img src="${pageContext.servletContext.contextPath}/imgs/content-pic-3.png" />
                                                </td>
                                                <td class="txt_block2 text">
                                                <div class="text_header">
                                                Вывод средств
                                                </div>
                                                XeoLine — это простой, удобный, а главное эффективный инструмент в руках любого рекламодателя. XeoLine — это простой, удобный, а главное эффективный инструмент в руках любого рекламодателя.XeoLine — это простой, удобный, а главное эффективный инструмент в руках любого рекламодателя.XeoLine — это простой, удобный, а главное эффективный инструмент в руках любого рекламодателя.
                                                </td>
                                            </tr>
                                            <tr>
                                                <td colspan="2" class="block2-text-separator"></td>
                                            </tr>
                                            <tr>
                                                <td class="txt_block2 pic">
                                                    <img src="${pageContext.servletContext.contextPath}/imgs/content-pic-4.png" />
                                                </td>
                                                <td class="txt_block2 text">
                                                <div class="text_header">
                                                Пополнить счёт
                                                </div>
                                                XeoLine — это простой, удобный, а главное эффективный инструмент в руках любого рекламодателя. XeoLine — это простой, удобный, а главное эффективный инструмент в руках любого рекламодателя.XeoLine — это простой, удобный, а главное эффективный инструмент в руках любого рекламодателя.XeoLine — это простой, удобный, а главное эффективный инструмент в руках любого рекламодателя.
                                                </td>
                                            </tr>
                                        </table>
                                    
                                    </td>
                                    </tr>
                                    </table>
                                    
                                    
                                    </td>
                                    <td class="empty-middle"></td>
                                    <td>
                                        <table border="0">
                                            <tr>
                                                <td id="score">
                                                <table border="0">
                                            <tr>
                                                <td class="score-header" colspan="6">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="score-left">
                                                </td>
                                                <td colspan="4" class="score-text header">
                                                    Рейтинг площадок
                                                </td>
                                                <td class="score-right">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="score-left">
                                                </td>
                                                <td class="score-text stxtcol1">
                                                    Название площадки
                                                </td>
                                                <td class="score-text stxtcol2">
                                                    Средний CTR
                                                </td>
                                                <td class="score-text stxtcol3">
                                                    Показов в сутки
                                                </td>
                                                <td class="score-text stxtcol4">
                                                    Xeoline Rank
                                                </td>
                                                <td class="score-right">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="score-left">
                                                </td>
                                                <td colspan="4" class="score-separator-white">
                                                </td>
                                                <td class="score-right">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="score-left">
                                                </td>
                                                <td class="score-text stxtcol1">
                                                    SEO блог Титова
                                                </td>
                                                <td class="score-text stxtcol2">
                                                    12
                                                </td>
                                                <td class="score-text stxtcol3">
                                                    435
                                                </td>
                                                <td class="score-text stxtcol4">
                                                    654
                                                </td>
                                                <td class="score-right">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="score-left">
                                                </td>
                                                <td colspan="4" class="score-separator-grey">
                                                </td>
                                                <td class="score-right">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="score-left">
                                                </td>
                                                <td class="score-text stxtcol1">
                                                    SEO блог Титова
                                                </td>
                                                <td class="score-text stxtcol2">
                                                    8
                                                </td>
                                                <td class="score-text stxtcol3">
                                                    335
                                                </td>
                                                <td class="score-text stxtcol4">
                                                    624
                                                </td>
                                                <td class="score-right">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="score-left">
                                                </td>
                                                <td colspan="4" class="score-separator-grey">
                                                </td>
                                                <td class="score-right">
                                                </td>
                                            </tr>
                                             <tr>
                                                <td class="score-left">
                                                </td>
                                                <td class="score-text stxtcol1">
                                                    SEO блог Титова
                                                </td>
                                                <td class="score-text stxtcol2">
                                                    12
                                                </td>
                                                <td class="score-text stxtcol3">
                                                    435
                                                </td>
                                                <td class="score-text stxtcol4">
                                                    654
                                                </td>
                                                <td class="score-right">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="score-left">
                                                </td>
                                                <td colspan="4" class="score-separator-grey">
                                                </td>
                                                <td class="score-right">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="score-left">
                                                </td>
                                                <td class="score-text stxtcol1">
                                                    SEO блог Титова
                                                </td>
                                                <td class="score-text stxtcol2">
                                                    8
                                                </td>
                                                <td class="score-text stxtcol3">
                                                    335
                                                </td>
                                                <td class="score-text stxtcol4">
                                                    624
                                                </td>
                                                <td class="score-right">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="score-left">
                                                </td>
                                                <td colspan="4" class="score-separator-grey">
                                                </td>
                                                <td class="score-right">
                                                </td>
                                            </tr>
                                             <tr>
                                                <td class="score-left">
                                                </td>
                                                <td class="score-text stxtcol1">
                                                    SEO блог Титова
                                                </td>
                                                <td class="score-text stxtcol2">
                                                    12
                                                </td>
                                                <td class="score-text stxtcol3">
                                                    435
                                                </td>
                                                <td class="score-text stxtcol4">
                                                    654
                                                </td>
                                                <td class="score-right">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="score-left">
                                                </td>
                                                <td colspan="4" class="score-separator-grey">
                                                </td>
                                                <td class="score-right">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="score-left">
                                                </td>
                                                <td class="score-text stxtcol1">
                                                    SEO блог Титова
                                                </td>
                                                <td class="score-text stxtcol2">
                                                    8
                                                </td>
                                                <td class="score-text stxtcol3">
                                                    335
                                                </td>
                                                <td class="score-text stxtcol4">
                                                    624
                                                </td>
                                                <td class="score-right">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="score-left">
                                                </td>
                                                <td colspan="4" class="score-separator-grey">
                                                </td>
                                                <td class="score-right">
                                                </td>
                                            </tr>
                                             <tr>
                                                <td class="score-left">
                                                </td>
                                                <td class="score-text stxtcol1">
                                                    SEO блог Титова
                                                </td>
                                                <td class="score-text stxtcol2">
                                                    12
                                                </td>
                                                <td class="score-text stxtcol3">
                                                    435
                                                </td>
                                                <td class="score-text stxtcol4">
                                                    654
                                                </td>
                                                <td class="score-right">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="score-left">
                                                </td>
                                                <td colspan="4" class="score-separator-grey">
                                                </td>
                                                <td class="score-right">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="score-left">
                                                </td>
                                                <td class="score-text stxtcol1">
                                                    SEO блог Титова
                                                </td>
                                                <td class="score-text stxtcol2">
                                                    8
                                                </td>
                                                <td class="score-text stxtcol3">
                                                    335
                                                </td>
                                                <td class="score-text stxtcol4">
                                                    624
                                                </td>
                                                <td class="score-right">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="score-left">
                                                </td>
                                                <td colspan="4" class="score-separator-grey">
                                                </td>
                                                <td class="score-right">
                                                </td>
                                            </tr>
                                             <tr>
                                                <td class="score-left">
                                                </td>
                                                <td class="score-text stxtcol1">
                                                    SEO блог Титова
                                                </td>
                                                <td class="score-text stxtcol2">
                                                    12
                                                </td>
                                                <td class="score-text stxtcol3">
                                                    435
                                                </td>
                                                <td class="score-text stxtcol4">
                                                    654
                                                </td>
                                                <td class="score-right">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="score-left">
                                                </td>
                                                <td colspan="4" class="score-separator-grey">
                                                </td>
                                                <td class="score-right">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="score-left">
                                                </td>
                                                <td class="score-text stxtcol1">
                                                    SEO блог Титова
                                                </td>
                                                <td class="score-text stxtcol2">
                                                    8
                                                </td>
                                                <td class="score-text stxtcol3">
                                                    335
                                                </td>
                                                <td class="score-text stxtcol4">
                                                    624
                                                </td>
                                                <td class="score-right">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="score-left">
                                                </td>
                                                <td colspan="4" class="score-separator-grey">
                                                </td>
                                                <td class="score-right">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="score-left">
                                                </td>
                                                <td colspan="4" id="more" class="score-text">
                                                <a href="score-details.html">Подробнее</a>
                                                </td>
                                                <td class="score-right">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="score-btm" colspan="6">
                                                </td>
                                            </tr>
                                        </table>
                                                </td>
                                            </tr>
                                            <tr>
                                            <td  id="news">
                                            <table border="0">
                                            <tr>
                                                <td class="news-header" colspan="3">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="news-left">
                                                </td>
                                                <td class="news-text header">
                                                    Новости системы
                                                </td>
                                                <td class="news-right">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="news-left">
                                                </td>
                                                <td class="news-text">
                                                    07.12.2011 <span>Удобный инструмент</span>
                                                    <div>
                                                    XeoLine — это простой, удобный, а главное эффективный инструмент в руках любого рекламодателя.
                                                    </div>
                                                </td>
                                                <td class="news-right">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="news-left">
                                                </td>
                                                <td  class="news-separator">
                                                </td>
                                                <td class="news-right">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="news-left">
                                                </td>
                                                <td class="news-text">
                                                    07.12.2011 <span>Эффективные рекламные кампании</span>
                                                    <div>
                                                    В отличие от баннерной рекламы, интернет- пользователи лояльны к формату рекламной строки, поэтому ваши рекламные кампании с XeoLine будут максимально эффектными.
                                                    </div>
                                                </td>
                                                <td class="news-right">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="news-left">
                                                </td>
                                                <td  class="news-separator">
                                                </td>
                                                <td class="news-right">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="news-left">
                                                </td>
                                                <td class="news-text">
                                                    07.12.2011 <span>Эффективные рекламные кампании</span>
                                                    <div>
                                                    Ваши рекламные кампании с XeoLine будут максимально эффектными. 
                                                    </div>
                                                </td>
                                                <td class="news-right">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="news-left">
                                                </td>
                                                <td  class="news-separator">
                                                </td>
                                                <td class="news-right">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="news-left">
                                                </td>
                                                <td class="news-text">
                                                    07.12.2011 <span>Удобный инструмент любого рекламодателя</span>
                                                    <div>
                                                    XeoLine — это простой, удобный, а главное эффективный инструмент в руках любого рекламодателя.
                                                    </div>
                                                </td>
                                                <td class="news-right">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="news-left">
                                                </td>
                                                <td  class="news-separator">
                                                </td>
                                                <td class="news-right">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="news-left">
                                                </td>
                                                <td class="news-text">
                                                    07.12.2011 <span>Удобный инструмент</span>
                                                    <div>
                                                    XeoLine — это простой, удобный, а главное эффективный инструмент в руках любого рекламодателя.
                                                    </div>
                                                </td>
                                                <td class="news-right">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="news-left">
                                                </td>
                                                <td  class="news-separator">
                                                </td>
                                                <td class="news-right">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="news-left">
                                                </td>
                                                <td id="more_news" class="news-text" style="">
                                                <a href="news-details.html">Все новости</a>
                                                </td>
                                                <td class="news-right">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="news-btm" colspan="3">
                                                </td>
                                            </tr>
                                        </table>
                                            </td>
                                            </tr>
                                        </table>
                                        
                                    </td>
                                    <td class="empty-right"></td>
                                </tr>
                                
                            </table>

                        </td>
                    </tr>
                    <tr>
                        <td id="td_btm">
                        <table width="100%">
                        <tr>
                        <td width="240px">
                        Мы работаем с платежными системами:
                        </td><td>
                            <a href=""><img src="${pageContext.servletContext.contextPath}/imgs/btn-money-visa.png" /></a>
                            <a href=""><img src="${pageContext.servletContext.contextPath}/imgs/btn-money-web-money.png" /></a>
                            <a href=""><img src="${pageContext.servletContext.contextPath}/imgs/btn-money-yandex.png" /></a>
                        </td>
                        <td align="right" valign="middle">
                        Мы есть в:
                        </td><td width="100px" align="right">
                            <a href=""><img src="${pageContext.servletContext.contextPath}/imgs/btm-sn-tweeter.png" /></a>
                            <a href=""><img src="${pageContext.servletContext.contextPath}/imgs/btm-sn-facebook.png" /></a>
                            <a href=""><img src="${pageContext.servletContext.contextPath}/imgs/btm-sn-in.png" /></a>
                        </td>
                        </tr>
                        </table>
                        <div style="height: 15px"></div>
                        <div class="btm_links">
                        <a href="">Вебмастерам</a>|
                        <a href="">Рекламодателям</a>|
                        <a href="">Партнёрам</a>|
                        <a href="">Сервисы</a>|
                        <a href="">Рейтинги</a>|
                        <a href="">FAQ</a>|
                        <a href="">Контакты</a>
                        </div>
                        <div style="height: 15px"></div>
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