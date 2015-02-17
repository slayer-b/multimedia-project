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

package common.cms.delegates;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @author Dmitriy_Demchuk
 */
public class KeepParamsDelegate {

    private static final String KEEP_PARAMETERS_ATTRIBUTE = "keep_parameters";
    /**
     * names of keep parameters
     */
    private final Set<String> names;

    /**
     * creates a new instance.
     * keep param names are made immutable.
     *
     * @param keepParamsNames names of params to keep.
     */
    public KeepParamsDelegate(Set<String> keepParamsNames) {
        names = Collections.unmodifiableSet(keepParamsNames);
    }

    /**
     * searches keep_parameters in model and saves them as stringBuilder in model
     */
    public StringBuilder getKeepParameters(Map<String, Object> model) {
        StringBuilder keepParameters = (StringBuilder) model.get(KEEP_PARAMETERS_ATTRIBUTE);
        if (keepParameters == null) {
            keepParameters = new StringBuilder();
        }

        for (String name : names) {
            Object value = model.get(name);
            if (value != null) {
                keepParameters.append("&amp;").append(name).append("=").append(value);
            }
        }
        model.put(KeepParamsDelegate.KEEP_PARAMETERS_ATTRIBUTE, keepParameters.toString());
        return keepParameters;
    }

    /**
     * copy all keep parameters from request parameters into model.
     * if parameter is in model map it is not copied.
     *
     * @param model  destination for copying
     * @param params source for copying
     */
    public void copyParameters(Map<String, Object> model, Map<String, String> params) {
        for (String name : names) {
            if (!model.containsKey(name)) {
                String val = params.get(name);
                if (val != null && val.length() > 0) {
                    model.put(name, val);
                }
            }
        }
    }

}
