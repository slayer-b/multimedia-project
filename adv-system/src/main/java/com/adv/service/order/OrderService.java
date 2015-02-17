package com.adv.service.order;

import com.adv.core.types.PaymentMethod;

/**
 *
 * @author Dmitriy_Demchuk
 */
public interface OrderService {

    /**
     * try to pay for an order with given number and given price.
     * @param payment_no
     * @param price
     * @return true if succeed
     */
    boolean orderPaid(Integer payment_no, Double price, PaymentMethod paymentMethod);
}
