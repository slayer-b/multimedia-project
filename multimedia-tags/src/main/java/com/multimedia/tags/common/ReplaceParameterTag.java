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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.multimedia.tags.common;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author demchuck.dima@gmail.com
 */
public class ReplaceParameterTag extends SimpleTagSupport {

    private String url_name;
    private String parameterName;
    private String parameterNewValue;

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        Object obj = getJspContext().getAttribute(url_name, PageContext.REQUEST_SCOPE);

        //String url = "&amp;page_size=5&amp;id_pages_nav=70";
        if (obj != null) {
            String url = (String) obj;
            Matcher m = Pattern.compile("([;|?|&]{1}" + parameterName + "=)([^&]*)").matcher(url);
            if (m.find()) {
                out.write(url, 0, m.start(2));
                out.write(parameterNewValue);
                out.write(url, m.end(2), url.length() - m.end(2));
            } else {
                out.write(url);
                out.write("&amp;");
                out.write(parameterName);
                out.write("=");
                out.write(parameterNewValue);
            }
        }

    }

    public void setUrl(String url) {
        this.url_name = url;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public void setParameterNewValue(String parameterNewValue) {
        this.parameterNewValue = parameterNewValue;
    }

    public static void main(String[] args) {
        ReplaceParameterTag rpt = new ReplaceParameterTag();
        rpt.setUrl("&amp;page_size=5&amp;id_pages_nav=70&amp;age_size=148");
        rpt.setParameterName("page_size");
        rpt.setParameterNewValue("TEST");
        try {
            rpt.doTag();
        } catch (JspException ex) {
            Logger.getLogger(ReplaceParameterTag.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReplaceParameterTag.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
