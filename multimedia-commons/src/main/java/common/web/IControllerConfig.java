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
package common.web;

/**
 *
 * @author demchuck.dima@gmail.com
 */
public interface IControllerConfig {

    /**
     * url of page that serves as a template (may include places for including navigation and content pages).
     * @return url
     */
    String getTemplateUrl();

    /**
     * model name for navigation.
     * @return name of attribute
     */
    String getNavigationDataAttribute();

    /**
     * model name for content.
     * @return name of attribute
     */
    String getContentDataAttribute();

    /**
     * contains all urls for parts of templates.
     * @return name of attribute with bean
     */
    String getUrlAttribute();
}