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

package common.web.controller;

import common.cms.ICmsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * @author demchuck.dima@gmail.com
 */
public class StatisticController implements Controller {
    private final Logger logger = LoggerFactory.getLogger(StatisticController.class);
    
    private static final String CREATE_MODEL_ERROR = "Failed to find os specific stats.";

	protected final ICmsConfig config;

	protected final String content_url;
	protected final String navigation_url;

    public StatisticController(ICmsConfig config, String content_url, String navigation_url) {
        this.config = config;
        this.content_url = content_url;
        this.navigation_url = navigation_url;
    }

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)	{
		request.setAttribute(config.getContentUrlAttribute(), content_url);
		request.setAttribute(config.getNavigationUrlAttribute(), navigation_url);

		request.setAttribute("title", "Статистика");
		request.setAttribute("top_header", "Статистика");

		createModel(request);

		return new ModelAndView(config.getTemplateUrl());
	}

	public void createModel(HttpServletRequest request){
        try {
            long[] result = new long[16];
            String methodName = "info";
            Class<?>[] paramTypes = new Class[1];
            paramTypes[0] = result.getClass();
            Object[] paramValues = new Object[1];
            paramValues[0] = result;
            Method method =
                Class.forName("org.apache.tomcat.jni.OS")
                .getMethod(methodName, paramTypes);
            method.invoke(null, paramValues);
            Long time_kernel = result[11] / 1000L;
            Long time_user = result[12] / 1000L;
            Long time_work = System.currentTimeMillis() - result[10] / 1000L;
            Double percent_work = (double) time_user * 100f / (double) time_work;
            request.setAttribute("time_user", formatTime(time_user, true));
            request.setAttribute("time_kernel", formatTime(time_kernel, true));
            request.setAttribute("time_work", formatTime(time_work, true));
            request.setAttribute("percent_work", percent_work);
        } catch (SecurityException e) {
            logger.error(CREATE_MODEL_ERROR, e);
        } catch (NoSuchMethodException e) {
            logger.error(CREATE_MODEL_ERROR, e);
        } catch (ClassNotFoundException e) {
            logger.error(CREATE_MODEL_ERROR, e);
        } catch (IllegalArgumentException e) {
            logger.error(CREATE_MODEL_ERROR, e);
        } catch (IllegalAccessException e) {
            logger.error(CREATE_MODEL_ERROR, e);
        } catch (InvocationTargetException e) {
            logger.error(CREATE_MODEL_ERROR, e);
        }

	}

    /**
     * Display the given time in ms, either as ms or s.
     *
     * @param seconds true to display seconds, false for milliseconds
     */
    public static String formatTime(Object obj, boolean seconds) {

        long time = -1L;

        if (obj instanceof Long) {
            time = (Long) obj;
        } else if (obj instanceof Integer) {
            time = (Integer) obj;
        }

        if (seconds) {
            return ((((float) time ) / 1000F) + " s");
        } else {
            return (time + " ms");
        }
    }

}