
/*
 * Copyright 2012 demchuck.dima@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.adv.controller.block;

import com.adv.core.types.PaymentMethod;
import com.adv.service.order.OrderService;
import com.adv.service.payment.webmoney.WebMoneyService;
import common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Dmitriy_Demchuk
 */
@Controller("paymentController")
@RequestMapping("/payment")
public class PaymentController {
    private final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private WebMoneyService webMoneyService;
    @Autowired
    private OrderService orderService;

    @RequestMapping("/wm/fail.htm")
    @ResponseBody public String webmoneyFail() {
        logger.debug("webmoney fail");
        return "<html>payment failed</html>";
    }

    @RequestMapping("/wm/succeed.htm")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody public String webmoneySucceed() {
        logger.debug("webmoney succeed");
        return "<html>payment succeed</html>";
    }

    /** when webmoney confirms the payment. */
    @RequestMapping("/wm/result.htm")
    @ResponseStatus(HttpStatus.OK)
    public void webmoneyResult(HttpServletRequest request) {
        logger.debug("webmoney result");
//        String lmi_payer_wm = request.getParameter("LMI_PAYER_WM");
//        String lmi_hash = request.getParameter("LMI_HASH");
//        String lmi_mode = request.getParameter("LMI_MODE");
        String lmi_payment_no = request.getParameter("LMI_PAYMENT_NO");
//        String lmi_payee_purse = request.getParameter("LMI_PAYEE_PURSE"); //where webmoney were received
//        String lmi_payer_purse = request.getParameter("LMI_PAYER_PURSE"); //who pays the webmoney
//        String lmi_sys_trans_date = request.getParameter("LMI_SYS_TRANS_DATE");
//        String lmi_sys_invs_no = request.getParameter("LMI_SYS_INVS_NO");
//        String lmi_sys_trans_no = request.getParameter("LMI_SYS_TRANS_NO");
        String lmi_payment_amount = request.getParameter("LMI_PAYMENT_AMOUNT");
//        String lmi_dbl_chk = request.getParameter("LMI_DBL_CHK");

        //TODO: insert webmoney.checkPaymentStatus here
        if (lmi_payment_no == null || lmi_payment_amount == null){
            logger.debug("dummy request into webmoney result");
        } else {
            boolean succeed = orderService.orderPaid(
                    StringUtils.getInteger(lmi_payment_no),
                    StringUtils.getDouble(lmi_payment_amount),
                    PaymentMethod.WEB_MONEY);
            if (logger.isDebugEnabled()) {
                logger.debug((succeed ? "" : "not ") + "activated order [" + lmi_payment_no + "], amount [" + lmi_payment_amount + "]");
            }
        }
    }

    /**
     * checks whether a transaction with given number was ran successfully.
     * @param id_wm_transaction id of transaction which will be checked.
     */
    @RequestMapping("/wm/check.htm")
    public void checkTransaction(@RequestParam("id_wm_t") Long id_wm_transaction) {
        webMoneyService.checkPaymentStatus(id_wm_transaction);
        //TODO: implement after testing
    }

    @RequestMapping("/wm/msg.htm")
    public void sendMessage(@RequestParam("target") String targetWmid, @RequestParam("msg") String message) {
        webMoneyService.sendMessage(targetWmid, "adv-system message", message);
    }


    @RequestMapping("/free/result.htm")
    @ResponseBody public String freeResult(@RequestParam("payment_no") Integer payment_no, @RequestParam("price") Double price) {
        logger.debug("free payment (test mode): no={}, price={}", payment_no, price);
        if (orderService.orderPaid(
                    payment_no,
                    price,
                    PaymentMethod.FREE)) {
            return "<html>Free payment succeed.</html>";
        } else {
            return "<html>Free payment failed.</html>";
        }
    }
}
