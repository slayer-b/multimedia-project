<%-- 
    Document   : index
    Created on : 23.01.2011, 23:47:30
    Author     : demchuck.dima@gmail.com
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
  <head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script type="text/javascript">
		function goAndTest(){
			e = document.getElementById("user_id").value;
			window.open("test.jsp?user_id="+e, "_parent");
		}
	</script>
  </head>
  <body>
	  <div><a href="register.htm">register(1)</a></div>
	  <div><a href="private/defaults.htm">edit defaults(2)</a></div>
	  <div><a href="javascript:goAndTest()">test(3)</a><input type="text" value="" id="user_id"></div>
	  <div><a href="private/block.htm">block (view user blocks)</a></div>
  </body>
</html>
