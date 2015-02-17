package com.adv.controller.user;

import com.adv.config.web.ITemplateConfig;
import com.adv.core.model.User;
import com.adv.service.user.IUserService;
import java.util.Map;
import javax.annotation.Resource;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Controller("registerUserController")
@RequestMapping("/register.htm")
public class RegisterController {
    private Logger logger = LoggerFactory.getLogger(RegisterController.class);

    private ITemplateConfig config;
    private IUserService userService;

    private static final String register_url = "/WEB-INF/jspf/user/register.jsp";

    @RequestMapping
    public String doRegisterPrepare(Map<String, Object> model) {
        logger.debug("do=register prepare");

        model.put(config.getContentUrlAttribute(), register_url);
        model.put(config.getContentDataAttribute(), new User());
        return config.getTemplateUrl();
    }

    @RequestMapping(params = {"action=register"})
    public String doRegister(Map<String, Object> model, @Valid User obj, BindingResult res) {
        logger.debug("do=register");

        model.put(config.getContentUrlAttribute(), register_url);
        model.put(config.getContentDataAttribute(), obj);
        model.put(BindingResult.MODEL_KEY_PREFIX + config.getContentDataAttribute(), res);

        if (obj.getPassword() == null || !obj.getPassword().equals(obj.getPassword_repeat())) {
            res.rejectValue("password", "password_repeat.different");
        }
        if (res.hasErrors()) {
            common.utils.CommonAttributes.addErrorMessage("form_errors", model);
        } else {
            if (userService.registerUser(obj)) {
                common.utils.CommonAttributes.addHelpMessage("operation_succeed", model);
            } else {
                common.utils.CommonAttributes.addErrorMessage("operation_fail", model);
            }
        }

        return config.getTemplateUrl();
    }

    //---------------------------- setters ---------------------------

    @Resource(name = "templateConfig")
    public void setConfig(ITemplateConfig value) {
        this.config = value;
    }

    @Resource(name = "userService")
    public void setUserService(IUserService value) {
        this.userService = value;
    }

}
