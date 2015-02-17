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

package com.netstorm.localization;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * is used for localization
 * @author demchuck.dima@gmail.com
 */
@MappedSuperclass
public class LocalizedBean<T> {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rel")
    @OnDelete(action = OnDeleteAction.CASCADE)
	private T localeParent;

	public T getLocaleParent() {return localeParent;}

	public void setLocaleParent(T localeParent) {this.localeParent = localeParent;}
}
