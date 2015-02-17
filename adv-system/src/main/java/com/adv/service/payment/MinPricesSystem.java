/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adv.service.payment;

import com.adv.core.types.PaymentAdvUnitsType;

/**
 *
 * @author Dmitriy_Demchuk
 */
public final class MinPricesSystem {
    public static final Double MIN_CLICK_COST = 0.01;
    public static final Double MIN_DURATION_COST = 0.01;
    public static final Double MIN_SUPPLANTATION_COST = 0.01;
    public static final Double MIN_VIEW_COST = 0.01;

    public static final PaymentAdvUnitsType DEFAULT_ADV_UNITS_TYPE = PaymentAdvUnitsType.SUPPLANTATION;

    /**
     * because this is utility class, it must not be inheritable.
     */
    private MinPricesSystem() {
    }
}
