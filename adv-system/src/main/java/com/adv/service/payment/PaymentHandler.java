package com.adv.service.payment;

import com.adv.core.types.PaymentMethod;
import java.util.Map;

/**
 * Handles different payment types.
 * @author Dmitriy_Demchuk
 */
public interface PaymentHandler {
    /**
     * get an url for handling this payment method.
     * @param method payment method for which you need to get view url.
     * @return urls
     */
    String getUrl(PaymentMethod method);

    /**
     * initialize model for handling this payment method.
     * @param method method for which to init model parameters.
     * @param model model where to set new parameters.
     * @param currentUrl url of current request.
     */
    void initModel(PaymentMethod method, Map<String, Object> model, String currentUrl);
}
