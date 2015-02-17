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

package common.beans.propertyEditors;

import common.types.TypesCheckAndCorrect;
import common.types.typesCheck.classes.ITypeChecker;
import common.types.typesCorrect.classes.ITypeCorrector;

import java.beans.PropertyEditorSupport;

/**
 *
 * @author demchuck.dima@gmail.com
 */
public class StringArrayTypesPropertyEditor extends PropertyEditorSupport {
	private final ITypeChecker checker;
	private final ITypeCorrector corrector;

	/**
	 * Create a new StringTypesPropertyEditor.
	 * @param type string type that will be checked
	 * @param tc where to find checker and corrector for this editor
	 */
	public StringArrayTypesPropertyEditor(String type, TypesCheckAndCorrect tc) {
		checker = tc.getChecker(type);
        corrector = tc.getCorrecter(type);
	}

	/**
	 * Create a new StringTypesPropertyEditor.
	 */
	public StringArrayTypesPropertyEditor(ITypeChecker checker, ITypeCorrector corrector) {
		this.checker = checker;
		this.corrector = corrector;
	}


	/**
     * This implementation returns {@code null} to indicate that
     * there is no appropriate text representation.
     */
	@Override
	public String getAsText() {
		return null;
	}


	/**
	 * Convert the given text value to a Collection with a single element.
	 *
	 * @throws IllegalArgumentException 
	 */
	@Override
	public void setAsText(String text) {
		setValue(text);
	}

	/**
	 * Convert the given value to a Collection of the target type.
	 */
	@Override
	public void setValue(Object value) {
		if (value == null) {
			super.setValue(null);
		} else if (value.getClass().isArray()) {
			try{
				String[] target = (String[]) value;
				boolean err = false;
				for (int i=0;i<target.length;i++){
					if (checker.check(target[i])<0){
						err = true;
					}else{
						if (corrector !=null){
							target[i] = corrector.correct(corrector.recorrect(target[i]));
						}
					}
				}
				super.setValue(target);
				if (err) {
					throw new IllegalArgumentException("error");
				}
			}catch(Exception e){
				throw new IllegalArgumentException(e);
			}
		} else {
			if (value instanceof String){
				super.setValue(value);
			}else{
				throw new IllegalArgumentException("not a string");
			}
		}
	}
}
