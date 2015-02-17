<%@page contentType="text/javascript" pageEncoding="UTF-8"%>
${param['json_name']}({
"id":"${content_data.id}",
"id_pages":"${content_data.id_pages}",
"name":"${content_data.name}",
"folder":"${content_data.folder}",
"active":"${content_data.active}",
"width":"${content_data.width}",
"height":"${content_data.height}",
"description":"${content_data.description}",
"title":"${content_data.title}",
"tags":"${content_data.tags}",
"optimized":"${content_data.optimized}",
"optimized_manual":"${content_data.optimized_manual}"
})