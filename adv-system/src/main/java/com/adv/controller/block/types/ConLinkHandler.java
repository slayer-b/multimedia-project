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
 *  under the License.
 */

package com.adv.controller.block.types;

import com.adv.core.model.ContentLink;
import com.adv.core.model.ItemLink;
import com.adv.core.types.ContentType;
import com.adv.order.model.Order;
import com.adv.order.model.OrderUnits;
import com.adv.service.block.IContentLinkService;
import com.adv.service.order.OrderBuilder;
import java.util.Map;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

/**
 *
 * @author Dmitriy_Demchuk
 */
@Service("ContentLinkHandler")
public class ConLinkHandler implements IContentTypeHandler<ItemLink, ContentLink> {

    private static final ContentType content_type = ContentType.LINK;

    private IContentLinkService content_link_service;

    private OrderBuilder orderBuilder;

    /** url for rendering. */
    private final String link_url = "/WEB-INF/jspf/block/adv_block.jsp";
    /** url for adding. */
    private final String adv_url = "/WEB-INF/jspf/block/adv_add.jsp";

    @Override
    public final ContentType getContent_type() {
        return content_type;
    }

    @Override
    public final String createModelForContentLink(ContentLink content, Integer page_number, Map<String, Object> model) {
        if (content.getProperties().getNavVisible()) {
            boolean nav = false;
            if (content_link_service.isAdvertisementPageValid(content, page_number + 1)) {
                model.put("next_page", page_number + 1);
                nav = true;
            }
            if (content_link_service.isAdvertisementPageValid(content, page_number - 1)) {
                model.put("prev_page", page_number - 1);
                nav = true;
            }
            if (nav) {
                model.put("nav_visible", Boolean.TRUE);
            }
        }
        model.put("advertisements", content_link_service.getAdvertisements(content, page_number));
        return link_url;
    }

    @Override
    public final String addAdvertisementPrepare(ContentLink content, Map<String, Object> model) {
        model.put("payment", content.getPaymentUnits());
        return adv_url;
    }

    @Override
    public final Order<ItemLink> getAdvertisementOrder(ContentLink content) {
        Order<ItemLink> order = new Order<ItemLink>();
        order.setItem(new ItemLink());
        switch (content.getPaymentUnits().getType()) {
            case CLICKS:
                order.setQuantity(1L);
                break;
            case SUPPLANTATION:
                break;
            default:
                throw new UnsupportedOperationException("cannot handle [" + content.getPaymentUnits().getType() + "] payment unit type");
        }
        return order;
    }

    @Override
    public final OrderUnits addAdvertisementForBlock(ContentLink content, Order<ItemLink> order, Map<String, Object> model) {
        OrderUnits orderUnits = orderBuilder.createOrder(order, content);
        if (orderUnits == null) {
            common.utils.CommonAttributes.addErrorMessage("operation_fail", model);
            return null;
        } else {
            common.utils.CommonAttributes.addHelpMessage("operation_succeed", model);
            content_link_service.addAdvertisement(content, order.getItem(), orderUnits);
            return orderUnits;
        }
    }

    @Override
    public final void increaseClickCount(Long id_adv) {
        content_link_service.increaseAdvertisementClicks(id_adv);
    }

    //------------------------------------ dependency injection -----------------------
    @Required
    @Resource(name = "contentLinkService")
    public final void setContentLinkService(IContentLinkService value) {
        this.content_link_service = value;
    }
    @Required
    @Resource(name = "orderBuilder")
    public final void setOrderBuilder(OrderBuilder value) {
        this.orderBuilder = value;
    }
}
