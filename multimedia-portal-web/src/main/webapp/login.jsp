<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>OpenID Login</title>

    <!-- Simple OpenID Selector -->
    <link rel="stylesheet" href="<c:url value='/scripts/openid-selector/css/openid.css'/>" />
    <script type="text/javascript" src="<c:url value='/scripts/jquery-1.8.3.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/openid-selector/js/openid-jquery.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/openid-selector/js/openid-en.js'/>"></script>

    <script type="text/javascript">
        $(document).ready(function() {
            openid.init('openid_identifier');
            $('#openid-providers').children('img').each(
                    function(index, item) {
                        var element = $(item);
                        element.bind("click", function() {
                            openid.signin(element.attr("data-name"));
                        });
                    }
            );
        });
    </script>
    <!-- /Simple OpenID Selector -->
</head>

<body>

<c:if test="${not empty param.login_error}">
    <font color="red">
        Your login attempt was not successful, try again.<br/><br/>
        Reason: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.
    </font>
</c:if>

<!-- Simple OpenID Selector -->
<form action="<c:url value='j_spring_openid_security_check'/>" method="post" id="openid_form">
    <input type="hidden" name="action" value="verify" />

    <fieldset>
        <legend>Sign-in or Create New Account</legend>

        <div id="openid-providers">
            <img alt="google" src="<c:url value="scripts/openid-selector/images.large/google.gif"/>" data-name="google">
            <img alt="vkontakte" src="<c:url value="scripts/openid-selector/images.large/vkontakte.gif"/>" data-name="vkontakte">
        </div>
    </fieldset>
</form>
<!-- /Simple OpenID Selector -->

</body>
</html>