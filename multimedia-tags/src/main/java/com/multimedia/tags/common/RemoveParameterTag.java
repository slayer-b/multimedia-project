/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.multimedia.tags.common;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;


/**
 *
 * @author demchuck.dima@gmail.com
 */
public class RemoveParameterTag extends SimpleTagSupport {

	private String url_name;
	private String parameterName;

	@Override
	public void doTag() throws JspException, IOException {

        JspWriter out=getJspContext().getOut();
		Object obj = getJspContext().getAttribute(url_name, PageContext.REQUEST_SCOPE);

		if (obj!=null){
			String url = (String)obj;
			Matcher m = Pattern.compile("((&amp;|\\?{1})"+parameterName+"=[^&]*)").matcher(url);
			if (m.find()){
				out.write(url, 0, m.start(1));
				out.write(url, m.end(1), url.length()-m.end(1));
			} else {
				out.write(url);
			}
		}

	}

	public void setUrl(String url) {this.url_name = url;}
	public void setParameterName(String parameterName) {this.parameterName = parameterName;}

	public static void main(String[] args){
		String parameterName = "page_size";
		String url = "&amp;page_size=5&amp;id_pages_nav=70&amp;age_size=148";
		Matcher m = Pattern.compile("((&amp;|\\?{1})"+parameterName+"=[^&]*)").matcher(url);
		//Matcher m = Pattern.compile("((&amp;|\\?{1})age)").matcher(url);
		if (m.find()){
			System.out.append("start = "+m.start(1)+"; end = "+m.end(1));
			System.out.append(url, 0, m.start(1));
			System.out.append(url, m.end(1), url.length());
		} else {
			System.out.append("not found");
		}
	}
	
}
