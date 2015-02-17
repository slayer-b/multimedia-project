/*
 *  Copyright 2010 demchuck.dima@gmail.com
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

package common.services;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * @param <T> 
 * @author demchuck.dima@gmail.com
 */
public interface IUpdateService<T, ID extends Serializable> {
	/**
	 * @param id of object to update
	 * @return object for update
	 */
	T getUpdateBean(ID id);

	/**
	 * prepare attributes for update
	 * @return map with attributes
	 */
	Map<String, Object> initUpdate();

	/**
	 * updates a command
	 * @param command object to update
	 * @return quantity of objects updated
	 */
	int update(T command);

}
