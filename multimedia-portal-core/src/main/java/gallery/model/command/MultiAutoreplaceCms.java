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

package gallery.model.command;

import common.beans.IMultiupdateBean;
import common.services.IMultiupdateService;
import gallery.model.beans.AutoreplaceL;
import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.map.LazyMap;

import java.util.HashMap;
import java.util.Map;

/**
 * @author demchuck.dima@gmail.com
 */
public class MultiAutoreplaceCms implements IMultiupdateBean<AutoreplaceL, Long> {

    private Map<Integer, AutoreplaceL> autoreplaces;

    public MultiAutoreplaceCms() {
        autoreplaces = LazyMap.decorate(new HashMap<Integer, AutoreplaceL>(), FactoryUtils.instantiateFactory(AutoreplaceL.class));
    }

    public Map<Integer, AutoreplaceL> getAutoreplaces() {
        return autoreplaces;
    }

    //TODO: test and remove.
    public void setAutoreplaces(Map<Integer, AutoreplaceL> autoreplaces) {
        this.autoreplaces = autoreplaces;
    }

    @Override
    public int save(IMultiupdateService<AutoreplaceL, Long> service) {
        return service.saveOrUpdateCollection(getAutoreplaces().values());
    }

    public int getSize() {
        return getAutoreplaces().size();
    }

    @Override
    public boolean isModel() {
        return false;
    }

    @Override
    public Object getModel() {
        return autoreplaces.values();
    }

}
