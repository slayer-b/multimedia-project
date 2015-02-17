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

package common.beans;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author demchuck.dima@gmail.com
 */
public class KeepParameters {
    private final String[] paramNames;
    private final String[] paramPseudo;

    private final boolean first;

    /**
     * Creates a new instance of KeepParameters
	 * params_pseudo are same as param_names, first delimeter is &amp;
     * @param paramNames names of parameters that should be kept in all urls
     */
    public KeepParameters(String[] paramNames) {
		this(paramNames, paramNames, false);
	}
    
    /**
     * Creates a new instance of KeepParameters
     * @param paramNames names of parameters that should be kept in all urls
	 * @param first if true first delimiter is ? else &amp;
     */
    public KeepParameters(String[] paramNames, boolean first) {
		this(paramNames, paramNames, first);
	}

    /**
     * Creates a new instance of KeepParameters
	 * first delim is &amp;
     * @param paramNames names of parameters that should be kept in all urls
     * @param paramPseudo names of parameters that should be kept set after request
     */
    public KeepParameters(String[] paramNames, String[] paramPseudo) {
		this(paramNames, paramPseudo, false);
    }

    /**
     * Creates a new instance of KeepParameters
     * @param paramNames names of parameters that should be kept in all urls
     * @param paramPseudo names of parameters that should be kept set after request
	 * @param first if true first delimiter is ? else &amp;
     */
    public KeepParameters(String[] paramNames, String[] paramPseudo, boolean first) {
        this.paramNames = paramNames;
		this.paramPseudo = paramPseudo;
		this.first = first;
    }
    
    /**
     * this function returns a string containing all parameters that should be
     * added to url before sending redirect.
     * it looks like:
     * pseud1=request.getParameter(parName1)&...&pseudN=request.getParameter(parNameN)
     * @param req actually the request we are handling
     * @return actually query string
     */
    public String getKeepParameters(HttpServletRequest req){
        StringBuilder rez=new StringBuilder();
		boolean firstMeet = first;
		String param;
        for (int i=0;i<paramNames.length;i++){
            param=req.getParameter(paramNames[i]);
			if (param!=null&&!param.isEmpty()){
				rez.append(firstMeet? "?" : "&amp;");
				rez.append(paramPseudo[i]);
				rez.append('=');
				rez.append(param);
				firstMeet = false;
			}
        }
        return rez.toString();
    }
    
}
