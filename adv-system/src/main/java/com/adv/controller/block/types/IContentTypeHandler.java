/*
 *  Copyright 2011 Dmitriy_Demchuk.
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

package com.adv.controller.block.types;

import com.adv.core.model.BlockContent;
import com.adv.core.types.ContentType;
import com.adv.order.model.Order;
import com.adv.order.model.OrderUnits;
import java.util.Map;

/**
 * handles a particular type of content.
 * @param Advertisement type of advertisement, that will be added
 * @author Dmitriy_Demchuk
 */
public interface IContentTypeHandler<Advertisement, Content extends BlockContent> {
    /** get content type, that is handled by this handler */
    ContentType getContent_type();

    /**
     * create a model for rendering advertisements to a page.
     * @param content an object representing needed content type
     * @param page_number number of page that we are rendering
     * @param model will be populated to attributes
     * @return URL for content
     */
    String createModelForContentLink(Content content, Integer page_number, Map<String, Object> model);

    /**
     * render page for adding an advertisement for block.
     * @param model will be populated to attributes
     * @param block for which we will add advertisement
     * @return URL for content
     */
    String addAdvertisementPrepare(Content content, Map<String, Object> model);

    /**
     * return bean for an advertisement.
     * @param content the block content for which to get order bean.
     * @return a newly created bean
     */
    Order<Advertisement> getAdvertisementOrder(Content content);

    /**
     * adding advertisement for block.
     * @param model will be populated to attributes
     * @param content the block content for which to attach the advertisement
     * @param adv advertisement to attach
     * @return order number, if succeed.
     */
    OrderUnits addAdvertisementForBlock(Content content, Order<Advertisement> order, Map<String, Object> model);

    //TODO: mb delete.
    /**
     * increases click count by 1 for given advertisement.
     */
    void increaseClickCount(Long id_adv);
}
