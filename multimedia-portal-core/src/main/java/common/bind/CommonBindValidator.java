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

package common.bind;

import common.beans.propertyEditors.StringTypesPropertyEditor;
import common.types.TypesCheckAndCorrect;
import common.web.filters.Antispam;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * it enables checking of required fields and types checking and antispam code
 * if you want to add some extra functionality do not forget to call method of a superclass<br>
 *
 * @author demchuck.dima@gmail.com
 */
public class CommonBindValidator extends ABindValidator {
    private Map<String, PropertyEditor> editors;
    private String[] required_fields;
    private String[] allowed_fields;

    private String anti_spam_code;

    private String required_field_arrays_marker;
    private String[] required_field_arrays;
    private String required_vector_name;
    private boolean required_field_vector = false;

    private final TypesCheckAndCorrect tc;

    public CommonBindValidator(TypesCheckAndCorrect tc) {
        this.tc = tc;
    }

    /*
      * Unused
      *  if want to use you must uncomment some lines below
      * types may not contain only a type name
      * [typeName][,][required]<br>
      * required - means that given field must be of type [typeName] and may not be null of an empty string
      * this is made for checking array types and collections ...<br>
      */

    @Override
    protected void initBinder(ServletRequestDataBinder binder) {
        binder.setBindEmptyMultipartFiles(false);
        binder.setRequiredFields(required_fields);
        binder.setAllowedFields(allowed_fields);
        binder.setFieldMarkerPrefix("_");
        binder.setFieldDefaultPrefix("!");
        if (editors != null) {
            for (Entry<String, PropertyEditor> e : editors.entrySet()) {
                binder.registerCustomEditor(String.class, e.getKey(), e.getValue());
            }
        }

        binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
    }

    @Override
    protected void validate(Object command, Errors err, HttpServletRequest request) {
        if (required_field_arrays_marker != null && required_field_arrays != null) {
            String[] marker = request.getParameterValues(required_field_arrays_marker);
            if (marker != null) {
                if (required_field_vector) {
                    for (int i = 0; i < required_field_arrays.length; i++) {
                        for (int j = 0; j < marker.length; j++) {
                            String s = request.getParameter(required_vector_name + '[' + marker[j] + "]." + required_field_arrays[i]);
                            if (s == null || s.isEmpty()) {
                                err.rejectValue(required_vector_name + '[' + marker[j] + ']', "required");
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < required_field_arrays.length; i++) {
                        for (int j = 0; j < marker.length; j++) {
                            String s = request.getParameter(required_field_arrays[i] + '[' + j + ']');
                            if (s == null || s.isEmpty()) {
                                err.rejectValue(required_field_arrays[i], "required");
                            }
                        }
                    }
                }
            }
        }
        if (anti_spam_code != null) {
            String errorCode = Antispam.canAccess(request, request.getParameter(anti_spam_code), true);
            if (errorCode != null) {
                err.reject(errorCode);
                common.utils.CommonAttributes.addErrorMessage(errorCode, request);
            }
        }
    }

    public void setRequiredFields(String[] value) {
        this.required_fields = value;
    }

    public void setAllowedFields(String[] value) {
        this.allowed_fields = value;
    }

    /**
     * name of parameter with antispam code
     *
     * @param name parameter
     */
    public void setAntiSpamCodeParam(String name) {
        this.anti_spam_code = name;
    }

    /**
     * is used with setRequiredFieldId
     * prefixes to check suffix is its number in array [i]
     * an array of field values that might not be null
     * field[0], field[1], ...
     *
     * @param value parameters prefixes to check
     */
    public void setRequiredFieldArrays(String[] value) {
        this.required_field_arrays = value;
    }

    /**
     * name of parameter that is actually checked for existence
     * and its length is length of array in setRequiredFieldArrays
     *
     * @param value parameter
     */
    public void setRequiredFieldArraysMarker(String value) {
        this.required_field_arrays_marker = value;
    }

    /**
     * if true the required field arrays is vector of beans
     * and is binded in form
     * required_vector_name[number].required_field_arrays[x]
     * default is false
     */
    public void setRequiredFieldArraysVector(boolean value) {
        this.required_field_vector = value;
    }

    /**
     * name of property with vector
     */
    public void setRequiredVectorName(String value) {
        this.required_vector_name = value;
    }

    public void setFieldTypes(Map<String, String> types) {
        editors = new HashMap<String, PropertyEditor>();
        for (Entry<String, String> e : types.entrySet()) {
            editors.put(e.getKey(), new StringTypesPropertyEditor(e.getValue(), tc, false));
        }
    }

}
