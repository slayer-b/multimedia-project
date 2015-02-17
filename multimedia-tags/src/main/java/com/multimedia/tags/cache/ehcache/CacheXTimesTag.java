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

package com.multimedia.tags.cache.ehcache;

import java.util.Random;

/**
 *
 * @author demchuck.dima@gmail.com
 */
public class CacheXTimesTag extends ACacheTag {
    private static final long serialVersionUID = 4724562412208415867L;
    /**
	 * quantity of times to cache this fragment
	 */
	private int quantity;
	private Random r = new Random();

	public CacheXTimesTag() {super();}

	@Override
	protected Object generateKey() {return r.nextInt(quantity);}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
