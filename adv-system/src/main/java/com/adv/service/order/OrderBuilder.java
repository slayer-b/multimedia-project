package com.adv.service.order;

import com.adv.core.model.ContentAdv;
import com.adv.order.model.Order;
import com.adv.order.model.OrderUnits;

/**
 * is used to create an order: order price + quantity.
 * @author Dmitriy_Demchuk
 */
public interface OrderBuilder {
    /**
     * create an order price and quantity.
     * @param order order to create from
     * @param content content to get defaults
     * @return order units
     */
    OrderUnits createOrder(Order<?> order, ContentAdv content);
}
