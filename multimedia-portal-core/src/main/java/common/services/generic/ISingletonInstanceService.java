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

package common.services.generic;

/**
 * if database only one row
 * like for config
 * @param <T> 
 * @author demchuck.dima@gmail.com
 */
public interface ISingletonInstanceService<T> {
	/**
	 * @return an instance of given class
	 */
	T getInstance();

	/**
	 * saving changes to instance
	 * @param entity instance with changes
	 */
	void saveInstance(T entity);
}
