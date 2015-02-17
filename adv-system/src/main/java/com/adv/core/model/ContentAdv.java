
package com.adv.core.model;

import com.adv.payment.model.AdvPaymentUnits;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author demchuck.dima@gmail.com
 */
@Entity(name = "ContentAdv")
@DynamicInsert(true)
@Table(name = "content_adv")
@DiscriminatorValue("ADVERTISEMENT")
public abstract class ContentAdv extends BlockContent {
    private static final long serialVersionUID = 1090053561411934684L;

    private ConAdvProps properties;
    private AdvPaymentUnits paymentUnits;

    @Embedded
    public ConAdvProps getProperties() {
        return properties;
    }

    public void setProperties(ConAdvProps properties) {
        this.properties = properties;
    }

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_adv_content_payment_units", unique = true, updatable = false, nullable = false)
    public AdvPaymentUnits getPaymentUnits() {
        return paymentUnits;
    }

    public void setPaymentUnits(AdvPaymentUnits paymentUnits) {
        this.paymentUnits = paymentUnits;
    }
}
