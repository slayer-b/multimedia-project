package com.adv.service.payment;

import com.adv.payment.model.PricesSystem;

/**
 * service for saving and getting min prices.
 * @author demchuck.dima@gmail.com
 */
public interface IMinPricesService {
    /**
     * get minimum prices for system.
     */
    PricesSystem getMinPrices();

    /**
     * set new minimum prices for system.
     */
    void setMinPrices(PricesSystem value);
}
