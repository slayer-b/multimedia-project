package com.adv.service.payment.webmoney;

import lv.flancer.wmt.xml.resp.X18Response;
import lv.flancer.wmt.xml.resp.X6Response;

/**
 * working with WebMoney.
 * @author Dmitriy_Demchuk
 */
public interface WebMoneyService {
    /**
     * Checks payment status using an inner transaction number(lmi_payment_no).
     * Protocol X18.
     * @param id_wm_transaction inner transaction message.
     * @return response for this protocol
     */
    X18Response checkPaymentStatus(Long id_wm_transaction);
    /**
     * Send a message to a targetWmid.
     * Protocol X6.
     * @param targetWmid message receiver.
     * @param message the message body.
     * @return response for this protocol.
     */
    X6Response sendMessage(String targetWmid, String subject, String message);
}
