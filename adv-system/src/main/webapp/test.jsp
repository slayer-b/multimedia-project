<%-- 
    Document   : test
    Created on : 08.01.2011, 23:16:12
    Author     : demchuck.dima@gmail.com
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
		The block is added below:
        <div><script type="text/javascript" src="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.servletContext.contextPath}/ads/adv-${param['user_id']}.js"></script></div>
    </body>
</html>