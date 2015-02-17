<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="common" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="utils" uri="http://download-multimedia.com/tags/utils" %>

<script type="text/javascript" src="${pageContext.servletContext.contextPath}/scripts/script.js"></script>
<script type="text/javascript">
    var text = 'Вы действительно хотите удалить эту запись?';
</script>

<%@ include file="../../messages/error.jsp" %>
<%@ include file="../../messages/help.jsp" %>
<div align="center">
    <a href="?do=insert&amp;id_photo=${id_photo_nav}${keep_parameters}" title="Добавить" class="link"><img
            alt="добавить" border="0" src="${pageContext.servletContext.contextPath}/img/add.jpg">
    </a><a class="link" href="?do=insert&amp;id_photo=${id_photo_nav}${keep_parameters}" title="Добавить">Добавить</a>&nbsp;&nbsp;&nbsp;
    Показывать по:
    <a class="link"
       href="?do=view<utils:replaceParam url="keep_parameters" parameterName="page_size" parameterNewValue="5"/>">5</a>
    <a class="link"
       href="?do=view<utils:replaceParam url="keep_parameters" parameterName="page_size" parameterNewValue="10"/>">10</a>
    <a class="link"
       href="?do=view<utils:replaceParam url="keep_parameters" parameterName="page_size" parameterNewValue="25"/>">25</a>
    <a class="link"
       href="?do=view<utils:replaceParam url="keep_parameters" parameterName="page_size" parameterNewValue="50"/>">50</a>
</div>

<%--form id="form_1" method="post" action="?do=multi_update&amp;action=multi_update${keep_parameters}"--%>

<%--input type="hidden" name="page_number" value="${page_number}">
<input type="hidden" name="page_size" value="${page_size}"--%>

<table align="center" border="1" cellpadding="2" cellspacing="0" style='border-collapse: collapse;'>
    <tr>
        <td colspan="8">Всего элементов: ${page_counter.itemsCount}</td>
    </tr>
    <%--header--%>
    <tr class="form_head_row">
        <td><input title="Снять/установить отметку у всех" type="checkbox" id="check_all_box"
                   onclick="reset_all(prefixes,'check_all_box',count);" checked></td>
        <td align="left" width="30px">ID</td>
        <td align="center">text</td>
        <td align="center">wallpaper</td>
        <td align="center">Выполнить</td>
    </tr>
    <%--row data--%>
    <c:forEach items="${content_data}" var="row" varStatus="i">
        <c:if test="${i.index mod 2==0}"><c:set var="tr_class" value="form_row_0"/></c:if>
        <c:if test="${i.index mod 2==1}"><c:set var="tr_class" value="form_row_1"/></c:if>
        <input type="hidden" name="data[${i.index}].id" id="id.${i.index}" value="${row.id}">
        <tr class="${tr_class}">
            <td><input type="checkbox" id="${i.index}" value="${row.id}" onclick="reset_element(prefixes,'${i.index}');"
                       checked></td>
            <td>${row.id}</td>
            <td>${row.text}</td>
            <td><a href="../wallpaper/index.htm?do=update&amp;id=${row.id_photo}">${row.id_photo}</a></td>
            <td align="center">
                <a href="?do=update&amp;id=${row.id}&amp;page_number=${page_number}${keep_parameters}"><img
                        alt="редактировать" title="редактировать" border="0"
                        src="${pageContext.servletContext.contextPath}/img/options.jpg"></a>
                <a href="javascript:confirmation_('?do=delete&amp;action=delete&amp;id=${row.id}&amp;page_number=${page_number}${keep_parameters}',text)"
                   title="удалить"><img alt="удалить" border="0"
                                        src="${pageContext.servletContext.contextPath}/img/kill.gif"></a>
            </td>
        </tr>
        <script type="text/javascript">count++;</script>
    </c:forEach>
    <tr>
        <td align="left" colspan="8"><common:pager_noimage count="${page_counter}"/></td>
    </tr>
    <%--tr><td align="right" colspan="8"><input type="submit" value="сохранить"></td></tr--%>
</table>
<%--/form--%>