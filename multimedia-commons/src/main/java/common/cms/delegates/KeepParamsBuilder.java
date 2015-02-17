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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * is used to build keep parameters object.
 * we need it to make set of keep parameters immutable.
 *
 * @author Dmitriy_Demchuk
 */
public class KeepParamsBuilder {
    /**
     * names of keep parameters
     */
    private final Set<String> names = new HashSet<String>();

    public KeepParamsBuilder() {

    }

    /**
     * @return keepParamsDelegate object
     */
    public KeepParamsDelegate getObject() {
        return new KeepParamsDelegate(names);
    }

    /**
     * adds one or more parameters to this delegate.
     */
    public void addParameters(Collection<String> value) {
        names.addAll(value);
    }

    /**
     * adds one parameter to this delegate.
     *
     * @param value names to add
     */
    public void addParameter(String value) {
        names.add(value);
    }

    /**
     * remove the parameter from this delegate.
     *
     * @param value remove this parameter
     * @return true if this delegate contained the specified element
     */
    public boolean removeParameter(String value) {
        return names.remove(value);
    }

    /**
     * parameters that would be kept
     * by default returns page_size
     * , returns new array each time.
     */
    public Set<String> getKeepParameterNames() {
        return names;
    }

}
