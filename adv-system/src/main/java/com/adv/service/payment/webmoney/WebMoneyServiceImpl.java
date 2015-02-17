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

package com.adv.service.payment.webmoney;

import com.adv.payment.webmoney.WebMoneyConfig;
import lv.flancer.wmt.xml.WmService;
import lv.flancer.wmt.xml.dict.X18AuthType;
import lv.flancer.wmt.xml.resp.X18Response;
import lv.flancer.wmt.xml.resp.X6Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * implementation of a webmoney service.
 * @author Dmitriy_Demchuk
 */
@Service("webMoneyService")
public class WebMoneyServiceImpl implements WebMoneyService {
    private final Logger logger = LoggerFactory.getLogger(WebMoneyServiceImpl.class);

    private WebMoneyConfig webMoneyConfig;

    @Override
    public X18Response checkPaymentStatus(Long id_wm_transaction) {
        logger.debug("checking transaction [{}]", id_wm_transaction);
        try {
            WmService service = new WmService();
            service.initWmSignerKwm(webMoneyConfig.getWmid(),
                            webMoneyConfig.getKwmFileName(),
                            webMoneyConfig.getKwmPassword());
            return service.x18(webMoneyConfig.getWmid(),
                    webMoneyConfig.getPayeePurse(),
                    id_wm_transaction,
                    webMoneyConfig.getPayeeSecretKey(),
                    X18AuthType.WM_SIGNER_AUTH);
        } catch (Exception e) {
            logger.error("checking for transaction status", e);
        }
        return null;
    }

    @Override
    public X6Response sendMessage(String targetWmid, String subject, String message) {
        if (logger.isDebugEnabled()) {
            logger.debug("send a message to [" + targetWmid + "] subj [" + subject + "] msg [" + message + "]");
        }
        try {
            WmService service = new WmService();
            //TODO: put it into the configuration class...
            service.initWmLightKeyStore("D:/projects/jssecacerts", "changeit");
            service.initWmSignerKwm(webMoneyConfig.getWmid(),
                            webMoneyConfig.getKwmFileName(),
                            webMoneyConfig.getKwmPassword());
            return service.x6(targetWmid, subject, message);
        } catch (Exception e) {
            logger.error("checking for transaction status", e);
        }
        return null;
    }

//------------------------- dependency injection ----------------------------
    @Required
    @Resource(name = "webMoneyConfig")
    public void setWebMoneyConfig(WebMoneyConfig value) {
        this.webMoneyConfig = value;
    }

}
