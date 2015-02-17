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
package common.cms.services2;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author demchuck.dima@gmail.com
 */
public interface IMultiupdateCmsService<T, ID extends Serializable> {

    /**
     * this method saves or updates collection of entities
     * default implementation: common.cms.services2.AGenericCmsService
     * updates all fields
     * if you want to update not all fields override and supply required field
     *
     * @param c collection
     * @return quantity of updated entities
     */
    int saveOrUpdateCollection(Collection<T> c);
}
