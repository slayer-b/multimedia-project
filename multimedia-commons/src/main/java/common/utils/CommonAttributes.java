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
package common.utils;

import javax.servlet.ServletRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author demchuck.dima@gmail.com
 */
public final class CommonAttributes {

    /**
     * an attribute where an error message is stored
     */
    private static final String ERROR_MESSAGE_ATTR = "common_error_message";
    /**
     * an attribute where help message is stored
     * help message is stored as vector of string each string is help message
     */
    private static final String HELP_MESSAGE_ATTR = "common_help_message";

    /**
     * adds help message to an attribute from the request
     *
     * @param msg     a message to be added
     * @param request a request where to find help messages
     */
    public static void addHelpMessage(String msg, ServletRequest request) {
        if (msg == null) {
            return;
        }
        List<String> help = (List<String>) request.getAttribute(HELP_MESSAGE_ATTR);
        if (help != null) {
            help.add(msg);
        } else {
            help = new LinkedList<String>();
            help.add(msg);
            request.setAttribute(HELP_MESSAGE_ATTR, help);
        }
    }

    /**
     * adds error message to an attribute from the request
     *
     * @param msg     a message to be added
     * @param request a request where to find help messages
     */
    public static void addErrorMessage(String msg, ServletRequest request) {
        if (msg == null) {
            return;
        }
        List<String> error = (List<String>) request.getAttribute(ERROR_MESSAGE_ATTR);
        if (error != null) {
            error.add(msg);
        } else {
            error = new LinkedList<String>();
            error.add(msg);
            request.setAttribute(ERROR_MESSAGE_ATTR, error);
        }
    }

    /**
     * adds help message to an attribute from the model
     *
     * @param msg   a message to be added
     * @param model a model with required params
     */
    public static void addHelpMessage(String msg, Map<String, Object> model) {
        if (msg == null) {
            return;
        }
        List<String> help = (List<String>) model.get(HELP_MESSAGE_ATTR);
        if (help == null) {
            help = new LinkedList<String>();
            model.put(HELP_MESSAGE_ATTR, help);
        }
        help.add(msg);
    }

    /**
     * adds error message to an attribute from the model
     *
     * @param msg   a message to be added
     * @param model a model with required params
     */
    public static void addErrorMessage(String msg, Map<String, Object> model) {
        if (msg == null) {
            return;
        }
        List<String> error = (List<String>) model.get(ERROR_MESSAGE_ATTR);
        if (error == null) {
            error = new LinkedList<String>();
            model.put(ERROR_MESSAGE_ATTR, error);
        }
        error.add(msg);
    }

    private CommonAttributes() {
    }
}
