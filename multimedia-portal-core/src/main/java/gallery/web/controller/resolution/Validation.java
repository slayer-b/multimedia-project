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

package gallery.web.controller.resolution;

import com.multimedia.service.IResolutionService;
import gallery.model.beans.Resolution;
import org.springframework.validation.Errors;

/**
 *
 * @author demchuck.dima@gmail.com
 */
public class Validation {
	private static final String[] PROPERTY_NAMES = {"width","height"};

    private final IResolutionService service;

    /**
     * @param service the service to set
     */
    public Validation(IResolutionService service) {
        this.service = service;
    }

	public void validateCMS(Object target, Errors errors) {
		Resolution p = (Resolution) target;
		if (p.getHeight()!=null&& p.getHeight() <1){
			errors.rejectValue("height", "typeMismatch.sort");
		}
		if (p.getWidth()!=null&& p.getWidth() <1){
			errors.rejectValue("width", "typeMismatch.sort");
		}
		//checks if such resolution is already in database
		if (!errors.hasErrors()){
			Long c = service.getRowCount(PROPERTY_NAMES, new Object[]{p.getWidth(),p.getHeight()});
			if (c>0L){
				errors.reject("resolution.duplicate");
			}
		}
	}
}
