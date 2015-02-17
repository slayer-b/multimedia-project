<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.RequestDispatcher" %>
<%@ page import="javax.servlet.ServletException,javax.servlet.http.HttpServletRequest" %>
<%@ page
        import="javax.servlet.http.HttpServletRequestWrapper,javax.servlet.http.HttpServletResponse,javax.servlet.jsp.JspWriter" %>
<%@ page import="javax.servlet.jsp.PageContext" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Set" %>

<%!
    private void compileAllJsps(
            PageContext pageContext,
            JspWriter out,
            HttpServletRequest request,
            HttpServletResponse response,
            String uripath) throws IOException, ServletException {

        Set set = pageContext.getServletContext().getResourcePaths(uripath);
        for (Iterator iter = set.iterator(); iter.hasNext(); ) {
            String uri = (String) iter.next();

            if (uri.endsWith(".jsp")) {
                RequestDispatcher rd = getServletContext().getRequestDispatcher(uri);
                out.write("<li>");
                out.write(uri);
                if (rd == null) {
                    out.write(" - not found");
                } else {
                    try {
                        rd.include(request, response);
                        out.write(" - ok");
                    } catch (Exception e) {
                        out.write(" - error");
                    }
                }
                out.write("</li>");
                out.flush();
            } else if (uri.endsWith("/")) {
                compileAllJsps(pageContext, out, request, response, uri);
            }
        }
    }
%>

<html>
<head>
    <title>Precompiling *.JSPs</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/styles/styles.css">
</head>
<body>
<h4><a href="index.htm" style="text-decoration:none;color:black;font-size:16px;"><--Back</a></h4>
<h4>Precompiling *.JSPs:</h4>
<ol>
    <%
        HttpServletRequest req = new HttpServletRequestWrapper(request) {
            public String getQueryString() {
                // can be "jsp_precompile=true"
                return "jsp_precompile";
            }
        };
        compileAllJsps(pageContext, out, req, response, "/");
    %>
</ol>
<h4>Done.</h4>
<h4><a href="index.htm" style="text-decoration:none;color:black;font-size:16px;"><--Back</a></h4>
</body>
</html>