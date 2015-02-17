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

import common.services.IMultiupdateService;

import java.io.Serializable;

/**
 *
 * @author demchuck.dima@gmail.com
 */
public interface IMultiupdateBean<T, ID extends Serializable> {
	/**
	 * saves to database using an appropriate method :)
	 * actually you must just specify fields for update
	 * and algorithm for value extraction
	 * @param service used for saving
	 * @return rows updated
	 */
	int save(IMultiupdateService<T, ID> service);

	/**
	 * determine if this bean contains all required information to be set as a model
	 * @return false if another model should be created
	 */
	boolean isModel();

	/**
	 * get model represented by this bean
	 * @return model object or null if isModel is false
	 */
	Object getModel();
}
