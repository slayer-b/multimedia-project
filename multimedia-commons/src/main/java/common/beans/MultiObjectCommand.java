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

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;

import java.util.LinkedList;
import java.util.List;

/**
 * is used when you need to bind an array of objects
 *
 * @author demchuck.dima@gmail.com
 */
public class MultiObjectCommand<T> {

    private List<T> data;

    /**
     * call this method to init data as lazy list of an appropriate class
     */
    public void initData(Class<T> clazz) {
        data = LazyList.decorate(new LinkedList<T>(), FactoryUtils.instantiateFactory(clazz));
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

}
