package com.adv.web.support;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.ModelFactory;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.adv.content.controller.ContentDelegate;
import com.adv.core.model.BlockContent;
import com.adv.core.types.ContentType;

public class BlockContentArgumentResolver implements HandlerMethodArgumentResolver {

    @Resource
    private ConversionService mvcConversionService;
    @Resource
    private ContentDelegate contentDelegate;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return
            parameter.getParameterType().equals(BlockContent.class) &&
            parameter.getParameterAnnotation(BlockContentParam.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
            ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) throws Exception
    {
        String typeParam = webRequest.getParameter(
                parameter.getParameterAnnotation(BlockContentParam.class).value());
        ContentType type = mvcConversionService.convert(typeParam, ContentType.class);
        if (type == null) {
            throw new IllegalArgumentException("Cannot find or convert ContentType.");
        }

        String name = ModelFactory.getNameForParameter(parameter);
        Object target = contentDelegate.getBean(type);

        WebDataBinder binder = binderFactory.createBinder(webRequest, target, name);
        bindObject(webRequest, binder);
        binder.validate();

        mavContainer.addAllAttributes(binder.getBindingResult().getModel());
        return binder.getTarget();
    }

    void bindObject(NativeWebRequest webRequest, WebDataBinder binder) {
        ServletRequest servletRequest = webRequest.getNativeRequest(ServletRequest.class);
        ServletRequestDataBinder servletBinder = (ServletRequestDataBinder) binder;
        servletBinder.bind(servletRequest);
    }

}
