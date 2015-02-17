<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="common" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="utils" uri="http://download-multimedia.com/tags/utils" %>
<%@taglib tagdir="/WEB-INF/tags/pages" prefix="pages" %>

<script type="text/javascript" src="${pageContext.servletContext.contextPath}/scripts/script.js"></script>
<script type="text/javascript">
    var text = 'Вы действительно хотите удалить эту запись?';
    var prefixes = new Array("id.", "id_pages.", "id_advertisement.", "useInParent.", "_useInParent.", "useInChildren", "_useInChildren");
    var count = 0;
</script>

<%@ include file="../../messages/error.jsp" %>
<%@ include file="../../messages/help.jsp" %>
<div align="center">
    <a href="?do=insert${keep_parameters}" title="Добавить" class="link"><img alt="добавить" border="0"
                                                                              src="${pageContext.servletContext.contextPath}/img/add.jpg">
    </a><a class="link" href="?do=insert${keep_parameters}" title="Добавить">Добавить</a>&nbsp;&nbsp;&nbsp;
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
<div align="center">
    <form method="get" action="?do=view">
        Страница:<pages:selectAll param_name="id_pages_nav"/>&nbsp;&nbsp;
        <input type="submit" value="изменить">
    </form>
</div>

<form id="form_1" method="post" action="?do=multi_update&amp;action=multi_update${keep_parameters}">

    <input type="hidden" name="page_number" value="${page_number}">
    <input type="hidden" name="page_size" value="${page_size}">

    <table align="center" border="1" cellpadding="2" cellspacing="0" style='border-collapse: collapse;'>
        <tr>
            <td colspan="8">Всего элементов: ${page_counter.itemsCount}</td>
        </tr>
        <%--header--%>
        <tr class="form_head_row">
            <td><input title="Снять/установить отметку у всех" type="checkbox" id="check_all_box"
                       onclick="reset_all(prefixes,'check_all_box',count);" checked></td>
            <td align="left" width="30">ID</td>
            <td align="center">Страница</td>
            <td align="center">Реклама</td>
            <td align="center">В родителе</td>
            <td align="center">В детях</td>
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
                <td width="50px"><input id="id_pages.${i.index}" name="data[${i.index}].id_pages"
                                        value="${row.id_pages}"></td>
                <td width="100px">
                    <select id="id_advertisement.${i.index}" name="data[${i.index}].id_advertisement">
                        <c:forEach items="${advertisements}" var="advertisement">
                        <option
                        <c:if test="${advertisement.id eq row.id_advertisement}"> selected</c:if>
                                                                                  value="${advertisement.id}">${advertisement.name}
                            </c:forEach>
                    </select>
                </td>
                <td align="center"><input id="useInParent.${i.index}" name="data[${i.index}].useInParent"
                                          type="checkbox" value="1"${row.useInParent?" checked":""}/><input
                        id="_useInParent.${i.index}" type="hidden" name="_data[${i.index}].useInParent" value="on"/>
                </td>
                <td align="center"><input id="useInChildren.${i.index}" name="data[${i.index}].useInChildren"
                                          type="checkbox" value="1"${row.useInChildren?" checked":""}/><input
                        id="_useInChildren.${i.index}" type="hidden" name="_data[${i.index}].useInChildren" value="on"/>
                </td>
                <td align="center">
                    <a href="?do=update&amp;id=${row.id}&amp;page_number=${page_number}${keep_parameters}"
                       title="редактировать"><img alt="редактировать" border="0"
                                                  src="${pageContext.servletContext.contextPath}/img/options.jpg"></a>
                        <%--next line: 1-st argument is an url of page that will be opened on click--%>
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
        <tr>
            <td align="right" colspan="8"><input type="submit" value="сохранить"></td>
        </tr>
    </table>
</form>