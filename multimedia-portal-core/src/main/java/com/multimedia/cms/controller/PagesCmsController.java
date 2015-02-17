/*
 * Copyright 2012 demchuck.dima@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.multimedia.cms.controller;

import com.multimedia.cms.config.PagesCmsConfig;
import com.multimedia.cms.service.IPagesServiceCms;
import com.multimedia.core.pages.types.IPagesType;
import com.multimedia.model.beans.PagesFolder;
import com.multimedia.service.IPagesService;
import common.bind.CommonBindValidator;
import common.types.TypesCheckAndCorrect;
import common.utils.CommonAttributes;
import common.utils.FileUtils;
import common.utils.RequestUtils;
import gallery.model.beans.Pages;
import gallery.web.controller.pages.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * @author demchuck.dima@gmail.com
 */
@Controller("pagesCmsController")
@RequestMapping("/cms/pages/index.htm")
public class PagesCmsController {
	/** config class is used to store some constants */
    @Autowired
    private PagesCmsConfig pagesCmsConfig;
	/** types of pages that will be handled */
    private IPagesType[] types;
	/** types of pages that will be handled */
	private Map<String, String> typesRus;
	@Autowired
	private IPagesService service;
	@Autowired
	private IPagesServiceCms serviceCms;
    @Autowired
    private TypesCheckAndCorrect typesCheckAndCorrect;

	private CommonBindValidator insertValidator;
	private CommonBindValidator updateValidator;

	private static final String SHOW_URL = "/WEB-INF/jspf/cms/pages/show.jsp";
	private static final String INSERT_URL = "/WEB-INF/jspf/cms/pages/insert.jsp";
	private static final String UPDATE_URL = "/WEB-INF/jspf/cms/pages/update.jsp";
	private static final String RELOCATE_URL = "/WEB-INF/jspf/cms/pages/relocate.jsp";
	private static final String MEGA_MODULE_URL = "/WEB-INF/jspf/cms/pages/mega_module.jsp";
	private static final String NAVIGATION_URL = "/WEB-INF/jspf/cms/pages/navigation.jsp";

	@PostConstruct
	public void init() {
	    String[] requiredProperties = {"sort", "type", "name"};
	    
	    insertValidator = new CommonBindValidator(typesCheckAndCorrect);
	    insertValidator.setRequiredFields(requiredProperties);

	    updateValidator = new CommonBindValidator(typesCheckAndCorrect);
	    updateValidator.setRequiredFields(requiredProperties);
	    String[] allowedProperties = {"id",
	            "name", "title", "type",
	            "sort", "active",
	            "description", "keywords", "info_top", "info_bottom",
	            "pagesFolder.id", "pagesFolder.name"};
	    updateValidator.setAllowedFields(allowedProperties);
	}

	public static final String TYPES_ATTR = "types";
	@RequestMapping(params = "do=view")
	public ModelAndView doView(HttpServletRequest req, HttpServletResponse resp){
		//logger.fine("do=view");
	    Map<String, Object> m = getCommonModel(req);
		m.put(pagesCmsConfig.getContentUrlAttribute(), SHOW_URL);
		m.put(TYPES_ATTR, typesRus);
		try{
            Long id_pages;
			String id_pages_temp = req.getParameter(pagesCmsConfig.getIdPagesParamName());
			if (id_pages_temp==null||id_pages_temp.isEmpty()){
				id_pages = null;
			}else{
				id_pages = Long.valueOf(id_pages_temp);
			}
			m.put("pages", service.getShortOrderedByPropertyValueCms("id_pages", id_pages));
		}catch(NumberFormatException nfe){
			CommonAttributes.addErrorMessage("form_errors", req);
			m.put("pages", service.getShortOrderedByPropertyValueCms("id_pages", null));
			//logger.log(Level.FINE,"id must not be null",nfe);
		}

        return new ModelAndView(pagesCmsConfig.getTemplateUrl(), m);
    }

	@RequestMapping(params = "do=insert")
	public ModelAndView doInsert(HttpServletRequest req, HttpServletResponse resp/*, Pages command*/){
	    Map<String, Object> m = getCommonModel(req);
		m.put(pagesCmsConfig.getContentUrlAttribute(), INSERT_URL);
		m.put("types", types);
		m.put("editForm_topHeader", "Добавление");

		String action = req.getParameter(gallery.web.controller.Config.ACTION_PARAM_NAME);
		/** bind command */
		Pages command = new Pages();
        BindingResult res = insertValidator.bindAndValidate(command, req);

		if ("insert".equals(action)){
			if (res.hasErrors()){
				//m.putAll(res.getModel());
				m.put(BindingResult.MODEL_KEY_PREFIX+"command", res);
				m.put("command", command);
				CommonAttributes.addErrorMessage("form_errors", req);
			}else{
				if (command.getPagesFolder()==null||command.getPagesFolder().getName().isEmpty()){
					PagesFolder pf = new PagesFolder();
					pf.setPages(command);
					pf.setName(FileUtils.toTranslit(command.getName()));
					command.setPagesFolder(pf);
				}
				service.save(command);
				m.put("command", command);
				CommonAttributes.addHelpMessage("operation_succeed", req);
			}
		}else{
			Long sort = (Long)service.getSinglePropertyU("max(sort)","id_pages",command.getId_pages());
			if (sort==null) {
			    sort = 0L;
			} else {
			    sort++;
			}
			command.setSort(sort);
			m.put("command", command);
		}
		
        return new ModelAndView(pagesCmsConfig.getTemplateUrl(), m);
    }

	@RequestMapping(params = "do=update")
	public ModelAndView doUpdate(HttpServletRequest req, HttpServletResponse resp){
	    Map<String, Object> m = getCommonModel(req);
		m.put(pagesCmsConfig.getContentUrlAttribute(), UPDATE_URL);
		m.put("types", types);
		m.put("editForm_topHeader", "Редактирование");

		String action = req.getParameter(gallery.web.controller.Config.ACTION_PARAM_NAME);
		boolean b = false;
		//getting pages with an appropriate id
		Pages command;
		try{
			Long id = Long.valueOf(req.getParameter("id"));
			command = service.getById(id);
			if (command==null) {
			    command = new Pages();
			}
		}catch(NumberFormatException nfe){
			command = new Pages();
			b = true;
			CommonAttributes.addErrorMessage("form_errors", req);
			//logger.log(Level.FINE,"id must not be null",nfe);
		}

		if ("update".equals(action)){
			/** bind command */
            BindingResult res = updateValidator.bindAndValidate(command, req);

			if (res.hasErrors()){
				m.put(BindingResult.MODEL_KEY_PREFIX+"command", res);
				if (!b) {
				    CommonAttributes.addErrorMessage("form_errors", req);
				}
				//logger.fine("hasErrors");
			}else{
				service.save(command);
				//command = new Pages();
				CommonAttributes.addHelpMessage("operation_succeed", req);
				//logger.fine("not hasErrors");
			}
		}
		m.put("command", command);

        return new ModelAndView(pagesCmsConfig.getTemplateUrl(), m);
    }

	@RequestMapping(params = "do=multiUpdate")
	public ModelAndView doMultiUpdate(HttpServletRequest req, HttpServletResponse resp){
		//logger.fine("do=multi update");
		String action = req.getParameter("action");
		if ("multiUpdate".equals(action)) {
			PagesCms command = new PagesCms();

			ServletRequestDataBinder binder = new ServletRequestDataBinder(command);
			binder.bind(req);
			if (binder.getBindingResult().hasErrors()) {
				CommonAttributes.addErrorMessage("form_errors", req);
			//logger.fine("hasErrors");
			} else {
				int rez = service.updateObjectArrayShortById(new String[]{"sort", "active", "last"}, command.getId(), command.getSort(), command.getActive(), command.getLast());
				if (rez > 0) {
					CommonAttributes.addHelpMessage("operation_succeed", req);
				//logger.fine("not hasErrors");
				} else {
					CommonAttributes.addErrorMessage("operation_fail", req);
				//logger.fine("hasErrors");
				}
			}
		}
		//after updating values shoving all pages(doView)
        return doView(req, resp);
    }

	@RequestMapping(params = "do=delete")
	public ModelAndView doDelete(HttpServletRequest req, HttpServletResponse resp){
		//logger.fine("do=delete");
		String action = req.getParameter("action");
		if ("delete".equals(action)) {
			try{
				Long id = Long.valueOf(req.getParameter("id"));
				if (service.deleteById(id)>0){
					CommonAttributes.addHelpMessage("operation_succeed", req);
					//logger.fine("not hasErrors");
				}else{
					CommonAttributes.addErrorMessage("operation_fail", req);
					//logger.fine("hasErrors");
				}
			}catch(NumberFormatException nfe){
				CommonAttributes.addErrorMessage("form_errors", req);
				//logger.log(Level.FINE,"id must not be null",nfe);
			}
		}
        return doView(req, resp);
    }

	/** recalculate 'last' field in all pages */
	@RequestMapping(params = "do=last")
	public ModelAndView doLast(HttpServletRequest req, HttpServletResponse resp){
		String action = req.getParameter("action");
		if ("last".equals(action)) {
			service.recalculateLast(null);
			CommonAttributes.addHelpMessage("operation_succeed", req);
		}
        return doView(req, resp);
    }

	/**
	 * recalculate 'active' field in all pages
     * true if a page itself or its child pages contains any elements.
     * false if a page and its child pages contains no elements.
	 */
	@RequestMapping(params = "do=reactivate")
	public ModelAndView doReactivate(HttpServletRequest req, HttpServletResponse resp){
		String action = req.getParameter("action");
		if ("reactivate".equals(action)) {
		    serviceCms.reactivate();
			CommonAttributes.addHelpMessage("operation_succeed", req);
		}
        return doView(req, resp);
    }

	@RequestMapping(params = "do=relocate")
	public ModelAndView doRelocate(HttpServletRequest req, HttpServletResponse resp){
		//logger.fine("do=relocate");
	    Map<String, Object> m = getCommonModel(req);
		m.put(pagesCmsConfig.getContentUrlAttribute(), RELOCATE_URL);
		m.put("editForm_topHeader", "Перемещение");

		String action = req.getParameter("action");
		Long id = null;
		try{
			id = Long.valueOf(req.getParameter("id"));
		}catch(Exception nfe){
			CommonAttributes.addErrorMessage("form_errors", req);
			//logger.log(Level.FINE,"id must not be null",nfe);
			action = null;
		}
		if ("relocate".equals(action)) {
			Long id_pages = RequestUtils.getLongParam(req, "id_pages");
			if (service.relocatePages(id, id_pages)){
				CommonAttributes.addHelpMessage("operation_succeed", req);
			}else{
				CommonAttributes.addErrorMessage("operation_fail", req);
				CommonAttributes.addErrorMessage("error_recursion", req);
			}
			//logger.fine("not hasErrors");
		}
		m.put("pages", service.getPagesForRelocate(id));

        return new ModelAndView(pagesCmsConfig.getTemplateUrl(), m);
    }

	@RequestMapping(params = "do=mega_module")
    public ModelAndView doMegaModule(HttpServletRequest req, HttpServletResponse resp){
		Map<String, Object> m = getCommonModel(req);
		req.setAttribute(pagesCmsConfig.getContentUrlAttribute(), MEGA_MODULE_URL);
        req.setAttribute(pagesCmsConfig.getContentDataAttribute(), serviceCms.getCategoriesFull());
        return new ModelAndView(pagesCmsConfig.getTemplateUrl(),m);
    }

	@RequestMapping(params = "do=optimize")
    public ModelAndView doOptimize(HttpServletRequest req, HttpServletResponse resp){
        Long id = RequestUtils.getLongParam(req, pagesCmsConfig.getIdPagesParamName());
		String action = req.getParameter("action");
        if (id!=null&&"optimize".equals(action)){
            serviceCms.optimizeCategory(id);
			CommonAttributes.addHelpMessage("operation_succeed", req);
        } else {
			CommonAttributes.addErrorMessage("operation_fail", req);
        }
        return doMegaModule(req, resp);
    }

	@RequestMapping(params = "do=reset_optimize")
    public ModelAndView doResetOptimize(HttpServletRequest req, HttpServletResponse resp){
        Long id = RequestUtils.getLongParam(req, pagesCmsConfig.getIdPagesParamName());
		String action = req.getParameter("action");
        if (id!=null&&"optimize".equals(action)) {
            serviceCms.resetOptimizationCategory(id, Boolean.FALSE);
			CommonAttributes.addHelpMessage("operation_succeed", req);
        } else {
			CommonAttributes.addErrorMessage("operation_fail", req);
        }
        return doMegaModule(req, resp);
    }

    public Map<String, Object> getCommonModel(HttpServletRequest req){
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("title","Страницы");
        m.put("top_header","Страницы");

        m.put(pagesCmsConfig.getNavigationUrlAttribute(), NAVIGATION_URL);
        Long id_pages_nav = RequestUtils.getLongParam(req, pagesCmsConfig.getIdPagesParamName());
        m.put(pagesCmsConfig.getNavigationDataAttribute(), service.getAllPagesParents(id_pages_nav, Config.NAVIGATION_PSEUDONYMES));
        return m;
    }

    @Resource
	public void setTypes(IPagesType[] types){
		this.types = types;

		typesRus = new HashMap<String, String>();
		for (IPagesType type : types) {
			typesRus.put(type.getType(), type.getTypeRu());
		}
	}

}

class PagesCms {
	protected List<Pages> pages;

	private Long[] id;
	private Long[] sort;
	private Boolean[] active;
	private Boolean[] last;

	public Long[] getId() {return id;}
	public void setId(Long[] id) {this.id = id;}

	public Long[] getSort() {return sort;}
	public void setSort(Long[] sort) {this.sort = sort;}

	public Boolean[] getActive() {return active;}
	public void setActive(Boolean[] active) {this.active = active;}

	public Boolean[] getLast() {return last;}
	public void setLast(Boolean[] value) {this.last = value;}

	public List<Pages> getPages() {
		if (sort==null||id==null||active==null||last==null){
			return null;
		}else{
			pages = new ArrayList<Pages>();
			int size = sort.length;
			if (id.length<size){
				size=id.length;
			}
			if (active.length<size){
				size=active.length;
			}
			if (last.length<size){
				size=last.length;
			}
			for (int i=0;i<size;i++){
				Pages p= new Pages();
				p.setActive(active[i]);
				p.setId(id[i]);
				p.setSort(sort[i]);
				p.setLast(last[i]);
				pages.add(p);
			}
			return pages;
		}
	}
}
