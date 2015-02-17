package com.adv.payment.webmoney;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author Dmitriy_Demchuk
 */
@Configuration("webMoneyConfig")
@PropertySource("classpath:/com/adv/misc/webmoney.properties")
public class WebMoneyConfigProperties implements WebMoneyConfig {
    @Value("${webmoney.id}") private String wmid;
    @Value("${webmoney.kwm.filename}") private String kwmFileName;
    @Value("${webmoney.kwm.password}") private String kwmPassword;
    @Value("${webmoney.payee.purse}") private String payeePurse;
    @Value("${webmoney.payee.secretKey}") private String payeeSecretKey;

    @Override
    public String getWmid() {
        return wmid;
    }

    @Override
    public String getKwmFileName() {
        return kwmFileName;
    }

    @Override
    public String getKwmPassword() {
        return kwmPassword;
    }

    @Override
    public String getPayeePurse() {
        return payeePurse;
    }

    @Override
    public String getPayeeSecretKey() {
        return payeeSecretKey;
    }

}
