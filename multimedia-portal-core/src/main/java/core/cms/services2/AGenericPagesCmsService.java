/*
 *  Copyright 2010 demchuck.dima@gmail.com.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package core.cms.services2;

import common.cms.services2.AGenericCmsService;
import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import com.multimedia.service.IPagesService;

/**
 * this controller adds a pages service as a property
 * and initializes pages collection for select boxes in insert and update
 * @author demchuck.dima@gmail.com
 */
public abstract class AGenericPagesCmsService<T, ID extends Serializable> extends AGenericCmsService<T, ID>{
	@Autowired
    protected IPagesService pagesService;
}
