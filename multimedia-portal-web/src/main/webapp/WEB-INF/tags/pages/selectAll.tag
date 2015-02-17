<%@tag description="generates an HTML select with all available pages" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://download-multimedia.com/tags/cache" prefix="cache"%>
<%@attribute name="param_name" description="name of generated select" required="true"%>
<cache:cacheRegion key="pages_select_combobox" cacheKey="${param_name}">
<input type="text" id="page_select_inputbox">
<select id="page_select_combobox" name="${param_name}"><c:forEach  items="${categories_wallpaper_select.data}" var="item">
<option value="${item.id}" label="${item.name}">${item.name}</c:forEach></select>
<script type="text/javascript">
$('#page_select_inputbox').bind('keypress', function(event) {
   if (event.keyCode == '13') {
     event.preventDefault();
   }else{
       el = document.getElementById('page_select_inputbox').value.toLowerCase();
       //+String.fromCharCode(event.keyCode).toLowerCase()
        options = document.getElementById('page_select_combobox').options;
        for (i = 0 ;i<options.length;i++){
           if (options.item(i).label.toLowerCase().indexOf(el, 0)>-1){
               options[i].selected = true;
               break;
           }
        }
   }
});
</script>
</cache:cacheRegion>