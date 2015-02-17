<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
<head>
<title>Оплата заказа рекламы</title>
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/styles/styles-my.css">
</head>
<body>

<form method="POST" action="${action_url}">

<p>Платеж в тестовом режиме !!!</p>
<p>заплатить ${price}</p>

<input type="hidden" name="price" value="${price}"/>
<input type="text" name="payment_no" value="${payment_no}"/>

<p><input type="submit" value="submit"></p>
</form>
</body>
</html>
