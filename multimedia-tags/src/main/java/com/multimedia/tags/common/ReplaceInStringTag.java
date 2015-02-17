/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.multimedia.tags.common;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author Dmitriy_Demchuk
 */
public class ReplaceInStringTag extends SimpleTagSupport {
    private String string;
    private String target;
    private String replacement;

	@Override
	public void doTag() throws JspException, IOException {
		//String url = "&amp;page_size=5&amp;id_pages_nav=70";
		if (string!=null&&!string.isEmpty()) {
            JspWriter out=getJspContext().getOut();
            String tmp = string.replaceAll(target, replacement);
            out.write(tmp, 0, tmp.length());
		}

	}

    /**
     * @param string the string to set
     */
    public void setString(String string) {this.string = string;}

    /**
     * @param target the target to set
     */
    public void setTarget(String target) {this.target = target;}

    /**
     * @param replacement the replacement to set
     */
    public void setReplacement(String replacement) {this.replacement = replacement;}
}
