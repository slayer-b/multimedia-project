/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.adv.service.payment;

import com.adv.core.types.PaymentMethod;
import com.adv.payment.webmoney.WebMoneyConfig;
import java.util.EnumMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
/**
 * TODO: remake it to encapsulate every payment type handler to its own class.
 * @author Dmitriy_Demchuk
 */
@Service("paymentHandler")
public class PaymentHandlerImpl implements PaymentHandler {

    private WebMoneyConfig webMoneyConfig;
    /** urls for handling different payment methods. */
    private EnumMap<PaymentMethod, String> urls;

    public PaymentHandlerImpl() {
        urls = new EnumMap<PaymentMethod, String>(PaymentMethod.class);
        urls.put(PaymentMethod.WEB_MONEY, "/WEB-INF/jspf/block/adv_pay_webmoney.jsp");
        urls.put(PaymentMethod.FREE, "/WEB-INF/jspf/block/adv_pay_free.jsp");
    }

    @Override
    public String getUrl(PaymentMethod method) {
        return urls.get(method);
    }

    @Override
    public void initModel(PaymentMethod method, Map<String, Object> model, String currentUrl) {
        switch (method){
            case WEB_MONEY:
                model.put("payee_purse", webMoneyConfig.getPayeePurse());
                model.put("success_url",
                            currentUrl + "/payment/wm/succeed.htm");
                model.put("fail_url",
                            currentUrl + "/payment/wm/fail.htm");
                model.put("result_url",
                            currentUrl + "/payment/wm/result.htm");
                return;
            case FREE:
                model.put("action_url",
                            currentUrl + "/payment/free/result.htm");
                return;
            default:
                throw new UnsupportedOperationException("payment method [" + method + "] is not supported yet.");
        }
    }

    //------------------------------------ dependency injection -----------------------

    @Required
    @Resource(name = "webMoneyConfig")
    public void setWebMoneyConfig(WebMoneyConfig value) {
        this.webMoneyConfig = value;
    }
}
