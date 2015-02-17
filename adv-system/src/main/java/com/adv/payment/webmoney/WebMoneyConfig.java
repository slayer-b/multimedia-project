package com.adv.payment.webmoney;

/**
 *
 * @author Dmitriy_Demchuk
 */
public interface WebMoneyConfig {
    /** get id of webmoney user which manages (receives) payments. */
    String getWmid();
    /** get path to file wwith with wm key. */
    String getKwmFileName();
    /** get password for kwm storage. */
    String getKwmPassword();
    /** get receivers purse */
    String getPayeePurse();
    /** get secret key for a purse. */
    String getPayeeSecretKey();

    /**
     * maximum number allowed for webmoney inner transaction number.
     */
    int WEB_MONEY_MAX_NUMBER = 2147483647;
}
