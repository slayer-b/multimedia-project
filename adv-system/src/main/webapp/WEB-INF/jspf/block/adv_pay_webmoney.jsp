<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<title>Оплата заказа рекламы</title>
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/styles/styles-my.css">
</head>

<body>

<form id=pay name=pay method="POST" action="https://merchant.webmoney.ru/lmi/payment.asp">

<p>Платеж WebMoney (Web Merchant Interface)</p>
<p>заплатить ${price} WMZ ... </p>

<input type="hidden" name="LMI_PAYMENT_AMOUNT" value="${price}"/>
<input type="hidden" name="LMI_PAYMENT_DESC" value="test payment"/>
<input type="text" name="LMI_PAYMENT_NO" value="${payment_no}"/>
<input type="hidden" name="LMI_PAYEE_PURSE" value="${payee_purse}"/>
<input type="hidden" name="LMI_SIM_MODE" value="0"/>
<input type="hidden" name="LMI_SUCCESS_URL" value="${success_url}"/>
<input type="hidden" name="LMI_FAIL_URL" value="${fail_url}"/>
<input type="hidden" name="LMI_RESULT_URL" value="${result_url}"/>

<p><input type="submit" value="submit"></p>
</form>

</body>
</html>