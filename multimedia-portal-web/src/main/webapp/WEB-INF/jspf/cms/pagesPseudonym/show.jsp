<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="utils" uri="http://download-multimedia.com/tags/utils" %>
<%@taglib prefix="common" tagdir="/WEB-INF/tags" %>

<script type="text/javascript" src="${pageContext.servletContext.contextPath}/scripts/script.js"></script>
<script type="text/javascript">
    var text = 'Вы действительно хотите удалить эту запись?';
    var prefixes = new Array('id.', 'text.', "page.", "item.", "_useInPages.", "_useInItems.");
    var count = 0;
</script>

<%@ include file="../../messages/error.jsp" %>
<%@ include file="../../messages/help.jsp" %>

<div align="center">
<span class="right_border"><a
        href="?<utils:replaceParam url="keep_parameters" parameterName="do" parameterNewValue="insert"/>"
        title="Добавить" class="link"><img alt="добавить" border="0"
                                           src="${pageContext.servletContext.contextPath}/img/add.jpg">
</a><a class="link" href="?<utils:replaceParam url="keep_parameters" parameterName="do" parameterNewValue="insert"/>"
       title="Добавить">Добавить</a></span>
<span class="right_border">
Показывать по:
<a class="link"
   href="?<utils:replaceParam url="keep_parameters" parameterName="page_size" parameterNewValue="5"/>">5</a>
<a class="link"
   href="?<utils:replaceParam url="keep_parameters" parameterName="page_size" parameterNewValue="10"/>">10</a>
<a class="link"
   href="?<utils:replaceParam url="keep_parameters" parameterName="page_size" parameterNewValue="25"/>">25</a>
<a class="link"
   href="?<utils:replaceParam url="keep_parameters" parameterName="page_size" parameterNewValue="50"/>">50</a>
</span>
</div>

<form id="form_1" method="post" action="?<utils:removeParam url="keep_parameters" parameterName="do"/>">

    <input type="hidden" name="page_number" value="${page_number}">
    <input type="hidden" name="page_size" value="${page_size}">

    <input type="hidden" name="action" value="multiUpdate" id="action_val">
    <input type="hidden" name="do" value="multiUpdate" id="do_val">

    <table align="center" border="1" cellpadding="2" cellspacing="0" style='border-collapse: collapse;'>
        <tr>
            <td colspan="8">Всего элементов: ${page_counter.itemsCount}</td>
        </tr>
        <%--header--%>
        <tr class="form_head_row">
            <td><input title="Снять/установить отметку у всех" type="checkbox" id="check_all_box"
                       onclick="reset_all(prefixes,'check_all_box',count);" checked></td>
            <td align="left" width="30">ID</td>
            <td align="center">Текст</td>
            <td align="center">Sort</td>
            <td align="center">Страница</td>
            <td align="center">Элемент</td>
            <td align="center">Просмотр</td>
            <td align="center" width="70">Выполнить</td>
        </tr>
        <%--row data--%>
        <c:forEach items="${content_data}" var="row" varStatus="i">
            <c:if test="${i.index mod 2==0}"><c:set var="tr_class" value="form_row_0"/></c:if>
            <c:if test="${i.index mod 2==1}"><c:set var="tr_class" value="form_row_1"/></c:if>
            <input type="hidden" name="data[${i.index}].id" id="id.${i.index}" value="${row.id}">
            <tr class="${tr_class}">
                <td><input type="checkbox" id="${i.index}" value="${row.id}"
                           onclick="reset_element(prefixes,'${i.index}');" checked></td>
                <td>${row.id}</td>
                <td><input id="text.${i.index}" name="data[${i.index}].text" type="text" value="${row.text}"
                           style="width:500px;"></td>
                <td><input id="sort.${i.index}" name="data[${i.index}].sort" type="text" value="${row.sort}"
                           class="sort_edit"></td>
                <td><input id="page.${i.index}" name="data[${i.index}].useInPages" type="checkbox" value="1"<c:if
                        test="${row.useInPages}"> checked</c:if>/><input id="_useInPages.${i.index}" type="hidden"
                                                                         name="_data[${i.index}].useInPages"
                                                                         value="on"/></td>
                <td><input id="item.${i.index}" name="data[${i.index}].useInItems" type="checkbox" value="1"<c:if
                        test="${row.useInItems}"> checked</c:if>/><input id="_useInItems.${i.index}" type="hidden"
                                                                         name="_data[${i.index}].useInItems"
                                                                         value="on"/></td>
                <td><a class="link" href="../pages/index.htm?do=view&id_pages_nav=${row.id_pages}">страница</a></td>
                <td align="center"><a
                        href="javascript:confirmation_('?do=delete&amp;id=${row.id}&amp;action=delete&amp;id_pages_nav=${param['id_pages_nav']}',text)"
                        title="удалить"><img alt="удалить" border="0"
                                             src="${pageContext.servletContext.contextPath}/img/kill.gif"></a></td>
            </tr>
            <script type="text/javascript">count++;</script>
        </c:forEach>
        <tr>
            <td align="left" colspan="8"><common:pager_noimage count="${page_counter}"/></td>
        </tr>
        <tr>
            <td align="right" colspan="8"><input type="submit" value="сохранить"></td>
        </tr>
    </table>
</form>

<form action="?do=insert&id_pages_nav=${param['id_pages_nav']}" method="post">
    <table align="center">
        <tr>
            <td align=center class="form_head">Добавить фразу</td>
        </tr>
        <input type=hidden name="action" value="insert">
        <input type=hidden name="id_pages" value="${param['id_pages_nav']}">
        <tr>
            <td align="center">
                <table>
                    <tr>
                        <td class=bold>Текст *:</td>
                        <td class="bold">Sort *:</td>
                        <td class=bold>Страница:</td>
                        <td class=bold>Элемент:</td>
                    </tr>
                    <tr>
                        <td align="center"><input type="text" class="long_text_area" name="text"/></td>
                        <td><input id="command_sort" type="text" name="sort" value="0" class="sort_edit"></td>
                        <td align="center"><input name="useInPages" type="checkbox" value="1" checked/><input
                                type="hidden" value="on" name="_useInPages"/></td>
                        <td align="center"><input name="useInItems" type="checkbox" value="1" checked/><input
                                type="hidden" value="on" name="_useInItems"/></td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr align="right">
            <td><input type="submit" value="Сохранить"></td>
        </tr>
    </table>
</form>