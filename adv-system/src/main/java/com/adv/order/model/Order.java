/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.adv.order.model;

import com.adv.core.types.PaymentMethod;
import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * ordering of item T.
 * @param <T> type of item to order
 * @author Dmitriy_Demchuk
 */
public class Order<T> {
    /**
     * quantity of units or duration or may be also omitted etc.
     */
    @Min(1)
    private Long quantity;
    /** the way you put money into the system. */
    private PaymentMethod paymentMehod;
    /**
     * actually an item.
     */
    @Valid
    private T item;

    /**
     * @return the quantity
     */
    public Long getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(Long value) {
        this.quantity = value;
    }

    /**
     * @return the item
     */
    public T getItem() {
        return item;
    }

    /**
     * @param item the item to set
     */
    public void setItem(T value) {
        this.item = value;
    }

    /**
     * the way you put money into the system.
     * @return the paymentMehod
     */
    public PaymentMethod getPaymentMehod() {
        return paymentMehod;
    }

    /**
     * the way you put money into the system.
     * @param paymentMehod the paymentMehod to set
     */
    public void setPaymentMehod(PaymentMethod value) {
        this.paymentMehod = value;
    }

}
